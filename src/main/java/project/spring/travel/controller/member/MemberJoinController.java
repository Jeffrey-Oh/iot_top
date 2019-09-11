package project.spring.travel.controller.member;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import project.spring.helper.FileInfo;
import project.spring.helper.RegexHelper;
import project.spring.helper.UploadHelper;
import project.spring.helper.Util;
import project.spring.helper.WebHelper;
import project.spring.travel.model.HotKeyword;
import project.spring.travel.model.Member;
import project.spring.travel.model.MemberPolicy;
import project.spring.travel.service.HotKeywordService;
import project.spring.travel.service.MemberPolicyService;
import project.spring.travel.service.MemberService;

/**
 * @fileName    : MemberJoinController.java
 * @author      : 홍준성
 * @description : 회원 가입 관련 컨트롤러 모음
 * @lastUpdate  : 2019. 4. 18.
 */
/** 컨트롤러 선언 */
@Controller
public class MemberJoinController {
    /** (1) 사용하고자 하는 Helper + Service 객체 선언 */
    // -> import org.slf4j.Logger;
    // -> import org.slf4j.LoggerFactory;
    Logger logger = LoggerFactory.getLogger(MemberJoinController.class); // Log4j 객체 직접 생성
    // -> import org.apache.ibatis.session.SqlSession;
    @Autowired
    SqlSession sqlSession;
    // -> import project.spring.helper.WebHelper
    @Autowired
    WebHelper web;
    // -> import project.spring.helper.RegexHelper
    @Autowired
    RegexHelper regex;
    // -> import project.spring.helper.UploadHelper
    @Autowired
    UploadHelper upload;
    // -> import project.spring.helper.Util;
    @Autowired
    Util util;
    // -> import java.util.Calendar;
    Calendar cal = Calendar.getInstance();
    // -> import project.java.travel.service.MemberService
    @Autowired
    MemberService memberService;
    // -> import project.spring.travel.service.HotKeywordService;
    @Autowired
    HotKeywordService hotKeywordService;
    // -> import project.spring.travel.service.MemberPolicyService;
    @Autowired
    MemberPolicyService memberPolicyService;
    
    /**
     * 회원가입 이메일인증 View를 사용하기 위한 컨트롤러 기본 구성
     */
    @RequestMapping(value = "/member/member_join.do", method = RequestMethod.GET)
    public ModelAndView memberJoin(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 */
        String referer = request.getHeader("referer");
        if(referer == null) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
        }
        
        /** (3) 로그인 여부 검사 */
        // 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
        if(web.getSession("loginInfo") != null) {
            return web.redirect(web.getRootPath() + "/index.do", "이미 로그인 하셨습니다.");
        }
        
        /** 인기검색어 영역 시작 */
        try {
            List<HotKeyword> hotKeywordList = null; // 조회된 검색어 리스트를 담을 리스트
            hotKeywordList = hotKeywordService.selectHotKeywordList(); // 최근 1달 검색어 조회
            // xss 처리
            for(int i = 0; i < hotKeywordList.size(); i++) {
                HotKeyword hotKeywordtemp = hotKeywordList.get(i);
                // 공격 코드를 막기 위한 처리
                hotKeywordtemp.setKeyword(web.convertHtmlTag(hotKeywordtemp.getKeyword()));
                if(hotKeywordtemp.getKeyword().indexOf("<script>") > -1) {
                    hotKeywordtemp.setKeyword(web.getString(null, hotKeywordtemp.getKeyword(), true));
                }
            }
            model.addAttribute("hotKeywordList", hotKeywordList); // View에서 사용하기 위해 등록
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        /** 인기검색어 영역 끝 */
        
        /** (4) 사용할 View의 이름 리턴 */
        return new ModelAndView("member/member_join");
    } // End memberJoin Method
    
    /**
     * 회원가입 이메일인증 1단계 액션을 위한 컨트롤러
     */
    @RequestMapping(value = "/member/member_join_ok.do", method = RequestMethod.POST)
    public ModelAndView memberJoinOk(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "member_join.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
        }
        
        /** ===== (3) 로그인 여부 검사 ===== */
        // 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
        if(web.getSession("loginInfo") != null) {
            return web.redirect(web.getRootPath() + "/index.do", "이미 로그인 하셨습니다.");
        }
        
        /** ===== (4) 파라미터 받기 ===== */
        // 입력된 이메일 인증 폼의 값을 받는다.
        String cert_ok = web.getString("cert_ok");
        String userName = web.getString("userName");
        String gender = web.getString("gender");
        String year = web.getString("year");
        String month = web.getString("month");
        String day = web.getString("day");
        String email = web.getString("email");
        String certNum = web.getString("certNum");
        
        /** 우선 로그에 기록 */
        // member_join에서 보낸 파라미터를 기록한다.
        logger.debug("cert_ok = " + cert_ok);
        logger.debug("userName = " + userName);
        logger.debug("gender = " + gender);
        logger.debug("year = " + year);
        logger.debug("month = " + month);
        logger.debug("day = " + day);
        logger.debug("email = " + email);
        logger.debug("certNum = " + certNum);
        
        /** ===== (5) 입력값의 유효성 검사 ===== */
        // member_join에서 보낸 파라미터를 검사한다.
        // 이메일 인증 검사
        if(!cert_ok.equals("true")) {
            return web.redirect(null, "이메일 인증에 성공하지 못하였습니다.");
        }
        
        // 이름 검사
        if(!regex.isValue(userName)) {
            return web.redirect(null, "이름을 입력하세요.");
        }
        if(!regex.isKor(userName)) {
            return web.redirect(null, "이름은 한글만 가능합니다.");
        }
        if(!regex.isMinLength(userName, 2)) {
            return web.redirect(null, "이름은 최소 2자 이상 입력해야 합니다.");
        }
        if(!regex.isMaxLength(userName, 20)) {
            return web.redirect(null, "이름은 최대 20자 까지만 입력 가능합니다.");
        }
        
        // 성별 검사
        if(!regex.isValue(gender)) {
            return web.redirect(null, "성별을 선택해 주세요.");
        }
        if(!gender.equals("M") && !gender.equals("F")) {
            return web.redirect(null, "성별이 잘못되었습니다.");
        }
        
        // 생년월일 검사
        if(!regex.isValue(year)) {
            return web.redirect(null, "생년월일을 선택해 주세요.");
        }
        if(!regex.isValue(month)) {
            return web.redirect(null, "생년월일을 선택해 주세요.");
        }
        if(!regex.isValue(day)) {
            return web.redirect(null, "생년월일을 선택해 주세요.");
        }
        
        // 이메일 검사
        if(!regex.isValue(email)) {
            return web.redirect(null, "이메일을 입력해 주세요.");
        }
        if(!regex.isEmail(email)) {
            return web.redirect(null, "이메일 주소의 형식이 잘못되었습니다.");
        }
        
        // 이메일 인증번호 검사
        if(!regex.isValue(certNum)) {
            return web.redirect(null, "이메일 인증번호를 입력해 주세요.");
        }
        
        /** (6) 전달받은 파라미터를 Beans에 설정한다. */
        Member member = new Member();
        member.setUserName(userName);
        member.setGender(gender);
        // 생년월일을 위해 문자 조합을 한다.
        /** mm, dd 형식에 맞게끔 작업 */
        if(Integer.parseInt(month) < 10) {
            month = "0" + month;
        }
        if(Integer.parseInt(day) < 10) {
            day = "0" + day;
        }
        String birthDate = year + "-" + month + "-" + day;
        member.setBirthDate(birthDate);
        member.setEmail(email);
        
        /**
         * ===== Service 계층의 기능을 통한 사용자 인증 =====
         * - 파라미터가 저장된 Beans를 Service에게 전달한다.
         * - Service는 MyBatis의 Mapper를 호출하고 조회 결과를 리턴할 것이다.
         */
        /** (7) Service를 통한 검사 */
        try {
            memberService.memberCheck(member);
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        
        /** ===== MemberJoinAgreement 컨트롤러에서 사용하기 위한 세션 저장 ===== */
        web.setSession("cert_ok", cert_ok);
        web.setSession("userName", userName);
        web.setSession("gender", gender);
        web.setSession("year", year);
        web.setSession("month", month);
        web.setSession("day", day);
        web.setSession("email", email);
        web.setSession("certNum", certNum);
        
        /**
         * Insert, Upadte, Delete 처리를 수행하는 action 페이지들은
         * 자체적으로 View를 갖지 않고 결과를 확인할 수 있는
         * 다른 페이지로 강제 이동시켜야 한다. (중복실행 방지)
         * 그러므로 View의 경로를 리턴하지 않는다.
         */
        return web.redirect(web.getRootPath() + "/member/member_join_agreement.do", null);
    } // End memberJoinOk Method
    
    /**
     * 회원가입 2단계 약관동의 View를 위한 컨트롤러
     */
    @RequestMapping(value = "/member/member_join_agreement.do", method = RequestMethod.GET)
    public ModelAndView memberJoinAgreement(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "member_join_ok.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
        }
        
        /** (3) 로그인 여부 검사 */
        // 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
        if(web.getSession("loginInfo") != null) {
            return web.redirect(web.getRootPath() + "/index.do", "이미 로그인 하셨습니다.");
        }
        
        /** ===== (4) 파라미터 받기 ===== */
        // 입력된 이메일 인증 폼의 값을 받는다.
        String cert_ok = (String) web.getSession("cert_ok");
        String userName = (String) web.getSession("userName");
        String gender = (String) web.getSession("gender");
        String year = (String) web.getSession("year");
        String month = (String) web.getSession("month");
        String day = (String) web.getSession("day");
        String email = (String) web.getSession("email");
        String certNum = (String) web.getSession("certNum");
        
        // 모든 세션 삭제
        web.removeSession("cert_ok");
        web.removeSession("userName");
        web.removeSession("gender");
        web.removeSession("year");
        web.removeSession("month");
        web.removeSession("day");
        web.removeSession("email");
        web.removeSession("certNum");
        
        // 우선 로그에 기록
        logger.debug("cert_ok = " + cert_ok);
        logger.debug("userName = " + userName);
        logger.debug("gender = " + gender);
        logger.debug("year = " + year);
        logger.debug("month = " + month);
        logger.debug("day = " + day);
        logger.debug("email = " + email);
        logger.debug("certNum = " + certNum);
        
        /** ===== (5) 입력값의 유효성 검사 ===== */
        // 이메일 인증 검사
        if(!cert_ok.equals("true")) {
            return web.redirect(null, "이메일 인증에 성공하지 못하였습니다.");
        }
        
        // 이름 검사
        if(!regex.isValue(userName)) {
            return web.redirect(null, "이름을 입력하세요.");
        }
        if(!regex.isKor(userName)) {
            return web.redirect(null, "이름은 한글만 가능합니다.");
        }
        if(!regex.isMinLength(userName, 2)) {
            return web.redirect(null, "이름은 최소 2자 이상 입력해야 합니다.");
        }
        if(!regex.isMaxLength(userName, 20)) {
            return web.redirect(null, "이름은 최대 20자 까지만 입력 가능합니다.");
        }
        
        // 성별 검사
        if(!regex.isValue(gender)) {
            return web.redirect(null, "성별을 선택해 주세요.");
        }
        if(!gender.equals("M") && !gender.equals("F")) {
            return web.redirect(null, "성별이 잘못되었습니다.");
        }
        
        // 생년월일 검사
        if(!regex.isValue(year)) {
            return web.redirect(null, "생년월일을 선택해 주세요.");
        }
        if(!regex.isValue(month)) {
            return web.redirect(null, "생년월일을 선택해 주세요.");
        }
        if(!regex.isValue(day)) {
            return web.redirect(null, "생년월일을 선택해 주세요.");
        }
        
        // 이메일 검사
        if(!regex.isValue(email)) {
            return web.redirect(null, "이메일을 입력해 주세요.");
        }
        if(!regex.isEmail(email)) {
            return web.redirect(null, "이메일 주소의 형식이 잘못되었습니다.");
        }
        
        // 이메일 인증번호 검사
        if(!regex.isValue(certNum)) {
            return web.redirect(null, "이메일 인증번호를 입력해 주세요.");
        }
        
        /** 인기검색어 영역 시작 */
        try {
            List<HotKeyword> hotKeywordList = null; // 조회된 검색어 리스트를 담을 리스트
            hotKeywordList = hotKeywordService.selectHotKeywordList(); // 최근 1달 검색어 조회
            // xss 처리
            for(int i = 0; i < hotKeywordList.size(); i++) {
                HotKeyword hotKeywordtemp = hotKeywordList.get(i);
                // 공격 코드를 막기 위한 처리
                hotKeywordtemp.setKeyword(web.convertHtmlTag(hotKeywordtemp.getKeyword()));
                if(hotKeywordtemp.getKeyword().indexOf("<script>") > -1) {
                    hotKeywordtemp.setKeyword(web.getString(null, hotKeywordtemp.getKeyword(), true));
                }
            }
            model.addAttribute("hotKeywordList", hotKeywordList); // View에서 사용하기 위해 등록
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        /** 인기검색어 영역 끝 */
        
        List<MemberPolicy> policyList = null;
        try {
            policyList = memberPolicyService.selectPolicyList();
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        
        if(policyList != null) {
            MemberPolicy policyItem = policyList.get(0);
            // View에서 사용하기 위해 등록
            model.addAttribute("policyItem", policyItem);
        }
        
        /** ===== MemberJoinAgreementOk 컨트롤러에서 사용하기 위한 세션 저장 ===== */
        web.setSession("cert_ok", cert_ok);
        web.setSession("userName", userName);
        web.setSession("gender", gender);
        web.setSession("year", year);
        web.setSession("month", month);
        web.setSession("day", day);
        web.setSession("email", email);
        web.setSession("certNum", certNum);
        
        /** (4) 사용할 View의 이름 리턴 */
        return new ModelAndView("member/member_join_agreement");
    } // End MemberJoinAgreement Method
    
    /**
     * 회원가입 2단계 약관동의 액션을 위한 컨트롤러
     */
    @RequestMapping(value = "/member/member_join_agreement_ok.do", method = RequestMethod.POST)
    public ModelAndView memberJoinAgreementOk(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "member_join_agreement.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
        }
        
        /** (3) 로그인 여부 검사 */
        // 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
        if(web.getSession("loginInfo") != null) {
            return web.redirect(web.getRootPath() + "/index.do", "이미 로그인 하셨습니다.");
        }
        
        /** ===== (4) 파라미터 받기 ===== */
        // 입력된 이메일 인증 폼의 값을 받는다.
        String cert_ok = (String) web.getSession("cert_ok");
        String userName = (String) web.getSession("userName");
        String gender = (String) web.getSession("gender");
        String year = (String) web.getSession("year");
        String month = (String) web.getSession("month");
        String day = (String) web.getSession("day");
        String email = (String) web.getSession("email");
        String certNum = (String) web.getSession("certNum");
        // member_join_agreement에서 보낸 폼의 값을 받는다.
        String serviceCheck = web.getString("serviceCheck");
        String infoCollectionCheck = web.getString("infoCollectionCheck");
        String consignmentCheck = web.getString("consignmentCheck");
        String marketingCheck = web.getString("marketingCheck");
        String allCheck = web.getString("allCheck");
        
        // 모든 세션 삭제
        web.removeSession("cert_ok");
        web.removeSession("userName");
        web.removeSession("gender");
        web.removeSession("year");
        web.removeSession("month");
        web.removeSession("day");
        web.removeSession("email");
        web.removeSession("certNum");
        web.removeSession("serviceCheck");
        web.removeSession("infoCollectionCheck");
        web.removeSession("consignmentCheck");
        web.removeSession("marketingCheck");
        web.removeSession("allCheck");
        
        /** 우선 로그에 기록 */
        // member_join에서 보낸 파라미터를 기록한다.
        logger.debug("cert_ok = " + cert_ok);
        logger.debug("userName = " + userName);
        logger.debug("gender = " + gender);
        logger.debug("year = " + year);
        logger.debug("month = " + month);
        logger.debug("day = " + day);
        logger.debug("email = " + email);
        logger.debug("certNum = " + certNum);
        // member_join_agreement에서 보낸 파라미터를 기록한다.
        logger.debug("serviceCheck = " + serviceCheck);
        logger.debug("infoCollectionCheck = " + infoCollectionCheck);
        logger.debug("consignmentCheck = " + consignmentCheck);
        logger.debug("marketingCheck = " + marketingCheck);
        logger.debug("allCheck = " + allCheck);
        
        /** ===== (5) 입력값의 유효성 검사 ===== */
        // member_join에서 보낸 파라미터를 검사한다.
        // 이메일 인증 검사
        if(!cert_ok.equals("true")) {
            return web.redirect(null, "이메일 인증에 성공하지 못하였습니다.");
        }
        
        // 이름 검사
        if(!regex.isValue(userName)) {
            return web.redirect(null, "이름을 입력하세요.");
        }
        if(!regex.isKor(userName)) {
            return web.redirect(null, "이름은 한글만 가능합니다.");
        }
        if(!regex.isMinLength(userName, 2)) {
            return web.redirect(null, "이름은 최소 2자 이상 입력해야 합니다.");
        }
        if(!regex.isMaxLength(userName, 20)) {
            return web.redirect(null, "이름은 최대 20자 까지만 입력 가능합니다.");
        }
        
        // 성별 검사
        if(!regex.isValue(gender)) {
            return web.redirect(null, "성별을 선택해 주세요.");
        }
        if(!gender.equals("M") && !gender.equals("F")) {
            return web.redirect(null, "성별이 잘못되었습니다.");
        }
        
        // 생년월일 검사
        if(!regex.isValue(year)) {
            return web.redirect(null, "생년월일을 선택해 주세요.");
        }
        if(!regex.isValue(month)) {
            return web.redirect(null, "생년월일을 선택해 주세요.");
        }
        if(!regex.isValue(day)) {
            return web.redirect(null, "생년월일을 선택해 주세요.");
        }
        
        // 이메일 검사
        if(!regex.isValue(email)) {
            return web.redirect(null, "이메일을 입력해 주세요.");
        }
        if(!regex.isEmail(email)) {
            return web.redirect(null, "이메일 주소의 형식이 잘못되었습니다.");
        }
        
        // 이메일 인증번호 검사
        if(!regex.isValue(certNum)) {
            return web.redirect(null, "이메일 인증번호를 입력해 주세요.");
        }
        
        // member_join_agreement에서 보낸 파라미터를 검사한다.
        // 서비스 이용약관 동의 검사
        if(!regex.isValue(serviceCheck)) {
            if(!serviceCheck.equals("on")) {
                return web.redirect(null, "서비스 이용약관에 동의하셔야 합니다.");
            }
        }
        // 서비스 이용약관 동의 검사
        if(!regex.isValue(infoCollectionCheck)) {
            if(!infoCollectionCheck.equals("on")) {
                return web.redirect(null, "개인정보 수집 및 이용에 동의하셔야 합니다.");
            }
        }
        // 서비스 이용약관 동의 검사
        if(!regex.isValue(consignmentCheck)) {
            if(!consignmentCheck.equals("on")) {
                return web.redirect(null, "개인정보 처리 업무 위탁에 동의하셔야 합니다.");
            }
        }
        // 마케팅 목적 이용에 관한 검사
        if(marketingCheck != null) {
            if(!marketingCheck.equals("on")) {
                return web.redirect(null, "마케팅 목적 이용에 관한 동의가 잘못되었습니다.");
            }
        }
        
        /** ===== MemberJoinAgreementForm 컨트롤러에서 사용하기 위한 세션 저장 ===== */
        web.setSession("cert_ok", cert_ok);
        web.setSession("userName", userName);
        web.setSession("gender", gender);
        web.setSession("year", year);
        web.setSession("month", month);
        web.setSession("day", day);
        web.setSession("email", email);
        web.setSession("certNum", certNum);
        web.setSession("serviceCheck", serviceCheck);
        web.setSession("infoCollectionCheck", infoCollectionCheck);
        web.setSession("consignmentCheck", consignmentCheck);
        web.setSession("marketingCheck", marketingCheck);
        web.setSession("allCheck", allCheck);
        
        return web.redirect(web.getRootPath() + "/member/member_join_form.do", null);
    } // End memberJoinAgreementOk Method
    
    /**
     * 회원가입 3단계 정보입력 View를 위한 컨트롤러
     */
    @RequestMapping(value = "/member/member_join_form.do", method = RequestMethod.GET)
    public ModelAndView memberJoinForm(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "member_join_agreement_ok.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
        }
        
        /** (3) 로그인 여부 검사 */
        // 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
        if(web.getSession("loginInfo") != null) {
            return web.redirect(web.getRootPath() + "/index.do", "이미 로그인 하셨습니다.");
        }
        
        /** ===== (4) 파라미터 받기 ===== */
        // 입력된 이메일 인증 폼의 값을 받는다.
        String cert_ok = (String) web.getSession("cert_ok");
        String userName = (String) web.getSession("userName");
        String gender = (String) web.getSession("gender");
        String year = (String) web.getSession("year");
        String month = (String) web.getSession("month");
        String day = (String) web.getSession("day");
        String email = (String) web.getSession("email");
        String certNum = (String) web.getSession("certNum");
        // member_join_agreement에서 보낸 파라미터를 추가한다.
        String serviceCheck = (String) web.getSession("serviceCheck");
        String infoCollectionCheck = (String) web.getSession("infoCollectionCheck");
        String consignmentCheck = (String) web.getSession("consignmentCheck");
        String marketingCheck = (String) web.getSession("marketingCheck");
        String allCheck = (String) web.getSession("allCheck");
        
        // 모든 세션 삭제
        web.removeSession("cert_ok");
        web.removeSession("userName");
        web.removeSession("gender");
        web.removeSession("year");
        web.removeSession("month");
        web.removeSession("day");
        web.removeSession("email");
        web.removeSession("certNum");
        web.removeSession("serviceCheck");
        web.removeSession("infoCollectionCheck");
        web.removeSession("consignmentCheck");
        web.removeSession("marketingCheck");
        web.removeSession("allCheck");
        
        /** 우선 로그에 기록 */
        // member_join에서 보낸 파라미터를 기록한다.
        logger.debug("cert_ok = " + cert_ok);
        logger.debug("userName = " + userName);
        logger.debug("gender = " + gender);
        logger.debug("year = " + year);
        logger.debug("month = " + month);
        logger.debug("day = " + day);
        logger.debug("email = " + email);
        logger.debug("certNum = " + certNum);
        // member_join_agreement에서 보낸 파라미터를 기록한다.
        logger.debug("serviceCheck = " + serviceCheck);
        logger.debug("infoCollectionCheck = " + infoCollectionCheck);
        logger.debug("consignmentCheck = " + consignmentCheck);
        logger.debug("marketingCheck = " + marketingCheck);
        logger.debug("allCheck = " + allCheck);
        
        /** ===== (5) 입력값의 유효성 검사 ===== */
        // member_join에서 보낸 파라미터를 검사한다.
        // 이메일 인증 검사
        if(!cert_ok.equals("true")) {
            return web.redirect(null, "이메일 인증에 성공하지 못하였습니다.");
        }
        
        // 이름 검사
        if(!regex.isValue(userName)) {
            return web.redirect(null, "이름을 입력하세요.");
        }
        if(!regex.isKor(userName)) {
            return web.redirect(null, "이름은 한글만 가능합니다.");
        }
        if(!regex.isMinLength(userName, 2)) {
            return web.redirect(null, "이름은 최소 2자 이상 입력해야 합니다.");
        }
        if(!regex.isMaxLength(userName, 20)) {
            return web.redirect(null, "이름은 최대 20자 까지만 입력 가능합니다.");
        }
        
        // 성별 검사
        if(!regex.isValue(gender)) {
            return web.redirect(null, "성별을 선택해 주세요.");
        }
        if(!gender.equals("M") && !gender.equals("F")) {
            return web.redirect(null, "성별이 잘못되었습니다.");
        }
        
        // 생년월일 검사
        if(!regex.isValue(year)) {
            return web.redirect(null, "생년월일을 선택해 주세요.");
        }
        if(!regex.isValue(month)) {
            return web.redirect(null, "생년월일을 선택해 주세요.");
        }
        if(!regex.isValue(day)) {
            return web.redirect(null, "생년월일을 선택해 주세요.");
        }
        
        // 이메일 검사
        if(!regex.isValue(email)) {
            return web.redirect(null, "이메일을 입력해 주세요.");
        }
        if(!regex.isEmail(email)) {
            return web.redirect(null, "이메일 주소의 형식이 잘못되었습니다.");
        }
        
        // 이메일 인증번호 검사
        if(!regex.isValue(certNum)) {
            return web.redirect(null, "이메일 인증번호를 입력해 주세요.");
        }
        
        // member_join_agreement에서 보낸 파라미터를 검사한다.
        // 서비스 이용약관 동의 검사
        if(!regex.isValue(serviceCheck)) {
            if(!serviceCheck.equals("on")) {
                return web.redirect(null, "서비스 이용약관에 동의하셔야 합니다.");
            }
        }
        // 서비스 이용약관 동의 검사
        if(!regex.isValue(infoCollectionCheck)) {
            if(!infoCollectionCheck.equals("on")) {
                return web.redirect(null, "개인정보 수집 및 이용에 동의하셔야 합니다.");
            }
        }
        // 서비스 이용약관 동의 검사
        if(!regex.isValue(consignmentCheck)) {
            if(!consignmentCheck.equals("on")) {
                return web.redirect(null, "개인정보 처리 업무 위탁에 동의하셔야 합니다.");
            }
        }
        // 마케팅 목적 이용에 관한 검사
        if(marketingCheck != null) {
            if(!marketingCheck.equals("on")) {
                return web.redirect(null, "마케팅 목적 이용에 관한 동의가 잘못되었습니다.");
            }
        }
        
        /** 인기검색어 영역 시작 */
        try {
            List<HotKeyword> hotKeywordList = null; // 조회된 검색어 리스트를 담을 리스트
            hotKeywordList = hotKeywordService.selectHotKeywordList(); // 최근 1달 검색어 조회
            // xss 처리
            for(int i = 0; i < hotKeywordList.size(); i++) {
                HotKeyword hotKeywordtemp = hotKeywordList.get(i);
                // 공격 코드를 막기 위한 처리
                hotKeywordtemp.setKeyword(web.convertHtmlTag(hotKeywordtemp.getKeyword()));
                if(hotKeywordtemp.getKeyword().indexOf("<script>") > -1) {
                    hotKeywordtemp.setKeyword(web.getString(null, hotKeywordtemp.getKeyword(), true));
                }
            }
            model.addAttribute("hotKeywordList", hotKeywordList); // View에서 사용하기 위해 등록
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        /** 인기검색어 영역 끝 */
        
        /** ===== MemberJoinFormOk 컨트롤러에서 사용하기 위한 세션 저장 ===== */
        web.setSession("cert_ok", cert_ok);
        web.setSession("userName", userName);
        web.setSession("gender", gender);
        web.setSession("year", year);
        web.setSession("month", month);
        web.setSession("day", day);
        web.setSession("email", email);
        web.setSession("certNum", certNum);
        web.setSession("serviceCheck", serviceCheck);
        web.setSession("infoCollectionCheck", infoCollectionCheck);
        web.setSession("consignmentCheck", consignmentCheck);
        web.setSession("marketingCheck", marketingCheck);
        web.setSession("allCheck", allCheck);
        
        model.addAttribute("userName", userName);
        model.addAttribute("gender", gender);
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("day", day);
        model.addAttribute("email", email);
        
        return new ModelAndView("member/member_join_form");
    } // End memberJoinForm Method
    
    /**
     * 회원가입 3단계 정보입력 액션 컨트롤러
     */
    @RequestMapping(value = "/member/member_join_form_ok.do", method = RequestMethod.POST)
    public ModelAndView memberJoinFormOk(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "member_join_form.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
        }
        
        /** (3) 로그인 여부 검사 */
        // 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
        if(web.getSession("loginInfo") != null) {
            return web.redirect(web.getRootPath() + "/index.do", "이미 로그인 하셨습니다.");
        }
        
        /** ===== (4) 파일이 포함된 POST 파라미터 받기 ===== */
        // 입력된 이메일 인증 폼의 값을 받는다.
        String cert_ok = (String) web.getSession("cert_ok");
        String userName = (String) web.getSession("userName");
        String gender = (String) web.getSession("gender");
        String year = (String) web.getSession("year");
        String month = (String) web.getSession("month");
        String day = (String) web.getSession("day");
        String email = (String) web.getSession("email");
        String certNum = (String) web.getSession("certNum");
        // member_join_agreement에서 보낸 파라미터를 추가한다.
        String serviceCheck = (String) web.getSession("serviceCheck");
        String infoCollectionCheck = (String) web.getSession("infoCollectionCheck");
        String consignmentCheck = (String) web.getSession("consignmentCheck");
        String marketingCheck = (String) web.getSession("marketingCheck");
        String allCheck = (String) web.getSession("allCheck");
        /**
         * ===== 사용자의 입력값 받기 =====
         * - 회원가입 페이지에 포함된 프로필 이미지 업로드로 인해
         *     <form>태그에 ecntype="multipart/form-data"가 적용되어 있다.
         * - 이 경우 WebHelper를 통한 파라미터 수신이 동작하지 않기 때문에,
         *     UploadHelper를 통해 받아야 한다.
         *     - WebHelper의 getString()|getInt() 메서드는 더 이상 사용할 수 없다.
         */
        try {
            upload.multipartRequest();
        } catch (Exception e) {
            return web.redirect(null, "multipart 데이터가 아닙니다.");
        }
        // member_join_form에서 보낸 파라미터를 추가한다.
        // UploadHelper에서 텍스트 형식의 파라미터를 분류한 Map을 리턴받아서 값을 추출한다.
        /**
         * ===== UploadHelper에 저장되어 있는 파라미터 추출하기 =====
         * - UploadHelper는 내부적으로 텍스트 파라미터를 Map 객체로 분류해 둔다.
         * - 이 변수를 추출하여 로그를 통해 값을 확인한다.
         */
        Map<String, String> paramMap = upload.getParamMap();
        String userId_check_ok = paramMap.get("userId_check_ok");
        String userId = paramMap.get("userId");
        String userPw = paramMap.get("userPw");
        String userPwCheck = paramMap.get("userPwCheck");
        String phone = paramMap.get("phone");
        String postcode = paramMap.get("postcode");
        String addr1 = paramMap.get("addr1");
        String addr2 = paramMap.get("addr2");
        String emailCheck = paramMap.get("emailCheck");
        String smsCheck = paramMap.get("smsCheck");
        
        // 모든 세션 삭제
        web.removeSession("cert_ok");
        web.removeSession("userName");
        web.removeSession("gender");
        web.removeSession("year");
        web.removeSession("month");
        web.removeSession("day");
        web.removeSession("email");
        web.removeSession("certNum");
        web.removeSession("serviceCheck");
        web.removeSession("infoCollectionCheck");
        web.removeSession("consignmentCheck");
        web.removeSession("marketingCheck");
        web.removeSession("allCheck");
        
        /** 우선 로그에 기록 */
        // member_join에서 보낸 파라미터를 기록한다.
        logger.debug("cert_ok = " + cert_ok);
        logger.debug("userName = " + userName);
        logger.debug("gender = " + gender);
        logger.debug("year = " + year);
        logger.debug("month = " + month);
        logger.debug("day = " + day);
        logger.debug("email = " + email);
        logger.debug("certNum = " + certNum);
        // member_join_agreement에서 보낸 파라미터를 기록한다.
        logger.debug("serviceCheck = " + serviceCheck);
        logger.debug("infoCollectionCheck = " + infoCollectionCheck);
        logger.debug("consignmentCheck = " + consignmentCheck);
        logger.debug("marketingCheck = " + marketingCheck);
        logger.debug("allCheck = " + allCheck);
        // member_join_form에서 보낸 파라미터를 기록한다.
        logger.debug("userId_check_ok = " + userId_check_ok);
        logger.debug("userId = " + userId);
        logger.debug("userPw = " + userPw);
        logger.debug("userPwCheck = " + userPwCheck);
        logger.debug("phone = " + phone);
        logger.debug("postcode = " + postcode);
        logger.debug("addr1 = " + addr1);
        logger.debug("addr2 = " + addr2);
        logger.debug("emailCheck = " + emailCheck);
        logger.debug("smsCheck = " + smsCheck);
        
        /**
         * ===== 가입시에 입력한 내용에 대한 유효성 검사 =====
         * - 회원가입시 입력되는 내용들은
         *     데이터베이스 테이블에 저장될 항목들이기 때문에
         *     데이터베이스의 제약조건에 위배되지 않도록
         *     최대한 철저하게 검증해야 한다.
         */
        /** ===== (5) 입력값의 유효성 검사 ===== */
        // member_join에서 보낸 파라미터를 검사한다.
        // 이메일 인증 검사
        if(!cert_ok.equals("true")) {
            upload.removeTempFile();
            return web.redirect(null, "이메일 인증에 성공하지 못하였습니다.");
        }
        
        // 이름 검사
        if(!regex.isValue(userName)) {
            upload.removeTempFile();
            return web.redirect(null, "이름을 입력하세요.");
        }
        if(!regex.isKor(userName)) {
            upload.removeTempFile();
            return web.redirect(null, "이름은 한글만 가능합니다.");
        }
        if(!regex.isMinLength(userName, 2)) {
            upload.removeTempFile();
            return web.redirect(null, "이름은 최소 2자 이상 입력해야 합니다.");
        }
        if(!regex.isMaxLength(userName, 20)) {
            upload.removeTempFile();
            return web.redirect(null, "이름은 최대 20자 까지만 입력 가능합니다.");
        }
        
        // 성별 검사
        if(!regex.isValue(gender)) {
            upload.removeTempFile();
            return web.redirect(null, "성별을 선택해 주세요.");
        }
        if(!gender.equals("M") && !gender.equals("F")) {
            upload.removeTempFile();
            return web.redirect(null, "성별이 잘못되었습니다.");
        }
        
        // 생년월일 검사
        if(!regex.isValue(year)) {
            upload.removeTempFile();
            return web.redirect(null, "생년월일을 선택해 주세요.");
        }
        if(!regex.isValue(month)) {
            upload.removeTempFile();
            return web.redirect(null, "생년월일을 선택해 주세요.");
        }
        if(!regex.isValue(day)) {
            upload.removeTempFile();
            return web.redirect(null, "생년월일을 선택해 주세요.");
        }
        
        // 이메일 검사
        if(!regex.isValue(email)) {
            upload.removeTempFile();
            return web.redirect(null, "이메일을 입력해 주세요.");
        }
        if(!regex.isEmail(email)) {
            upload.removeTempFile();
            return web.redirect(null, "이메일 주소의 형식이 잘못되었습니다.");
        }
        
        // 이메일 인증번호 검사
        if(!regex.isValue(certNum)) {
            upload.removeTempFile();
            return web.redirect(null, "이메일 인증번호를 입력해 주세요.");
        }
        
        // member_join_agreement에서 보낸 파라미터를 검사한다.
        // 서비스 이용약관 동의 검사
        if(!regex.isValue(serviceCheck)) {
            if(!serviceCheck.equals("on")) {
                upload.removeTempFile();
                return web.redirect(null, "서비스 이용약관에 동의하셔야 합니다.");
            }
        }
        // 서비스 이용약관 동의 검사
        if(!regex.isValue(infoCollectionCheck)) {
            if(!infoCollectionCheck.equals("on")) {
                upload.removeTempFile();
                return web.redirect(null, "개인정보 수집 및 이용에 동의하셔야 합니다.");
            }
        }
        // 서비스 이용약관 동의 검사
        if(!regex.isValue(consignmentCheck)) {
            if(!consignmentCheck.equals("on")) {
                upload.removeTempFile();
                return web.redirect(null, "개인정보 처리 업무 위탁에 동의하셔야 합니다.");
            }
        }
        // 마케팅 목적 이용에 관한 검사
        if(marketingCheck != null) {
            if(!marketingCheck.equals("on")) {
                upload.removeTempFile();
                return web.redirect(null, "마케팅 목적 이용에 관한 동의가 잘못되었습니다.");
            }
        }
        
        // member_join_agreement에서 보낸 파라미터를 검사한다.
        // 아이디 중복 검사
        if(!userId_check_ok.equals("true")) {
            upload.removeTempFile();
            return web.redirect(null, "사용할수 없는 아이디 입니다.");
        }
        
        // 아이디 검사
        if(!regex.isValue(userId)) {
            upload.removeTempFile();
            return web.redirect(null, "아이디를 입력하세요.");
        }
        if(!regex.isEngNum(userId)) {
            upload.removeTempFile();
            return web.redirect(null, "아이디는 영어와 숫자 조합만 입력 가능합니다.");
        }
        if(!regex.isMinLength(userId, 4)) {
            upload.removeTempFile();
            return web.redirect(null, "아이디는 최소 4자 이상 입력하셔야 합니다.");
        }
        if(!regex.isMaxLength(userId, 20)) {
            upload.removeTempFile();
            return web.redirect(null, "아이디는 최대 20자 까지만 입력 가능합니다.");
        }
        
        // 비밀번호 값 검사
        if(!regex.isValue(userPw)) {
            upload.removeTempFile();
            return web.redirect(null, "비밀번호를 입력해 주세요.");
        }
        if(!regex.isMinLength(userPw, 6)) {
            upload.removeTempFile();
            return web.redirect(null, "비밀번호는 최소 6자 이상 입력하셔야 합니다.");
        }
        if(!regex.isMaxLength(userPw, 20)) {
            upload.removeTempFile();
            return web.redirect(null, "비밀번호는 최대 20자 까지만 입력 가능합니다.");
        }
        if(!regex.isPassword(userPw)) {
            upload.removeTempFile();
            return web.redirect(null, "비밀번호는 적어도 1개 이상의 숫자와 1개 이상의 특수문자가 포함되어야 합니다.");
        }
        if(!regex.isIndexCheck(userPw, userId)) {
            upload.removeTempFile();
            return web.redirect(null, "비밀번호에는 아이디가 포함되면 안됩니다.");
        }
        // 비밀번호 확인 검사
        if(!regex.isValue(userPwCheck)) {
            upload.removeTempFile();
            return web.redirect(null, "비밀번호 확인을 입력해 주세요.");
        }
        if(!regex.isCompareTo(userPw, userPwCheck)) {
            upload.removeTempFile();
            return web.redirect(null, "비밀번호 확인이 잘못되었습니다.");
        }
        
        // 연락처 검사
        if(!regex.isValue(phone)) {
            upload.removeTempFile();
            return web.redirect(null, "연락처를 입력해 주세요.");
        }
        if(!regex.isPhone(phone)) {
            upload.removeTempFile();
            return web.redirect(null, "연락처의 형식이 잘못되었습니다.");
        }
        
        // 우편번호, 주소, 상세주소 검사
        if(!regex.isValue(postcode)) {
            upload.removeTempFile();
            return web.redirect(null, "우편번호를 입력해 주세요.");
        }
        if(!regex.isValue(addr1)) {
            upload.removeTempFile();
            return web.redirect(null, "주소를 입력해 주세요.");
        }
        if(!regex.isValue(addr2)) {
            upload.removeTempFile();
            return web.redirect(null, "상세주소를 입력해 주세요.");
        }
        
        // 이메일 수신여부 검사
        if(!regex.isValue(emailCheck)) {
            upload.removeTempFile();
            return web.redirect(null, "이메일 수신여부를 선택해 주세요.");
        }
        // SMS 수신여부 검사
        if(!regex.isValue(smsCheck)) {
            upload.removeTempFile();
            return web.redirect(null, "SMS 수신여부를 선택해 주세요.");
        }
        
        /**
         * ===== 업로드 된 첨부파일이 존재할 경우의 처리 =====
         * - 업로드 된 파일이 존재한다면 업로드 파일이 저장된 경로를 List 객체로 리턴받는다.
         * - 멀티업로드가 아니기 때문에 List의 0번째 데이터만을 추출하여 처리할 수 있다.
         */
        /** (6) 업로드 된 파일 정보 추출 */
        List<FileInfo> fileList = upload.getFileList();
        // 업로드 된 프로필 사진 경로가 저장될 변수
        String profileImg = null;
        
        // 업로드 된 파일이 존재할 경우만 변수값을 할당한다.
        if(fileList.size() > 0) {
            // 단일 업로드이므로 0번째 항목만 가져온다.
            FileInfo info = fileList.get(0);
            profileImg = info.getFileDir() + "/" + info.getFileName();
            
            // 파일 확장자 체크
            String ext = info.getOrginName().substring(info.getOrginName().indexOf(".") + 1).toLowerCase();
            if (!ext.equals("gif") && !ext.equals("jpg") && !ext.equals("jpeg") && !ext.equals("png")) {
                upload.removeTempFile();
                return web.redirect(null , "gif, jpg, jpeg, png 파일만 업로드 해주세요.");
            }
            
            // 파일 용량 체크
            int fileSize = (int) info.getFileSize();
            float fileSizeF = fileSize;
            String fSMB = String.format("%.2f", (fileSizeF / (1024 * 1024)));
            int maxSize = 1024 * 1024 * 5;
            int mSMB = (maxSize / (1024 * 1024));
            if (info.getFileSize() > maxSize) {
                upload.removeTempFile();
                return web.redirect(null , info.getOrginName() + "(이)가 용량 5MB을 초과했습니다.\n\n" + fSMB + "MB / " + mSMB + "MB");
            }
        } // End if
        
        // 파일 경로를 로그로 기록
        logger.debug("profileImg = " + profileImg);
        
        /**
         * ===== 준비된 모든 데이터를 JavaBeans로 묶은 후 Service에 전달 =====
         * - Service를 통해 Mapper가 호출되고 Database에 Insert 된다.
         */
        /** (7) 전달받은 파라미터를 Beans 객체에 담는다. */
        Member member = new Member();
        member.setUserName(userName);
        member.setGender(gender);
        // 생년월일을 위해 문자 조합을 한다.
        /** mm, dd 형식에 맞게끔 작업 */
        if(Integer.parseInt(month) < 10) {
            month = "0" + month;
        }
        if(Integer.parseInt(day) < 10) {
            day = "0" + day;
        }
        String birthDate = year + "-" + month + "-" + day;
        member.setBirthDate(birthDate);
        member.setUserId(userId);
        member.setUserPw(userPw);
        member.setPhone(phone);
        member.setEmail(email);
        member.setPostcode(postcode);
        member.setAddress1(addr1);
        member.setAddress2(addr2);
        // 오늘 날짜를 형식에 맞게 조합을 한다.
        String nowDate = util.getPrintDate2(cal);
        member.setMarketingCheckedDate(marketingCheck != null ? nowDate : null);
        member.setToEmailCheckedDate(emailCheck.equals("agree") ? nowDate : null);
        member.setToSmsCheckedDate(smsCheck.equals("agree") ? nowDate : null);
        member.setProfileImg(profileImg);
        
        /** (8) Service를 통한 데이터베이스 저장 처리 */
        int emailCheckResult = 0;
        int userIdCheckResult = 0;
        try {
            emailCheckResult = memberService.emailCheck(member);
            userIdCheckResult = memberService.userIdCheck(member);
        } catch (Exception e) {
            upload.removeTempFile();
            // 예외가 발생한 경우이므로, 더이상 진행하지 않는다.
            return web.redirect(null, e.getLocalizedMessage());
        }
        
        if(emailCheckResult > 0) {
            upload.removeTempFile();
            // 예외가 발생한 경우이므로, 더이상 진행하지 않는다.
            return web.redirect(null, "이미 사용중인 이메일 입니다.");
        }
        if(userIdCheckResult > 0) {
            upload.removeTempFile();
            // 예외가 발생한 경우이므로, 더이상 진행하지 않는다.
            return web.redirect(null, "이미 사용중인 아이디 입니다.");
        }
        
        try {
            memberService.memberCheck(member);
            memberService.addMember(member);
        } catch (Exception e) {
            upload.removeTempFile();
            // 예외가 발생한 경우이므로, 더이상 진행하지 않는다.
            return web.redirect(null, e.getLocalizedMessage());
        }
        
        // 가입 성공 페이지로 보낼 이름 세션에 저장
        web.setSession("userName", userName);
        /**
         * ===== 가입 완료 후 페이지 이동 처리 =====
         */
        /** (9) 가입이 완료되었으므로 가입 성공 페이지로 이동 */
        return web.redirect(web.getRootPath() + "/member/member_join_success.do", null);
    } // End memberJoinFormOk Method
    
    /**
     * 회원가입 성공 View 페이지를 위한 컨트롤러
     */
    @RequestMapping(value = "/member/member_join_success.do", method = RequestMethod.GET)
    public ModelAndView memberJoinSuccess(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 */
        String referer = request.getHeader("referer");
        if(referer == null) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
        } else {
            if(regex.isIndexCheck(referer, "member_join_form_ok.do")) {
                // 거쳐야할 절차를 건너뛴 경우
                logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
                return web.redirect(web.getRootPath() + "/error/error_page.do", null);
            }
        }
        
        /** (3) 로그인 여부 검사 */
        // 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
        if(web.getSession("loginInfo") != null) {
            return web.redirect(web.getRootPath() + "/index.do", "이미 로그인 하셨습니다.");
        }
        
        /** 인기검색어 영역 시작 */
        try {
            List<HotKeyword> hotKeywordList = null; // 조회된 검색어 리스트를 담을 리스트
            hotKeywordList = hotKeywordService.selectHotKeywordList(); // 최근 1달 검색어 조회
            // xss 처리
            for(int i = 0; i < hotKeywordList.size(); i++) {
                HotKeyword hotKeywordtemp = hotKeywordList.get(i);
                // 공격 코드를 막기 위한 처리
                hotKeywordtemp.setKeyword(web.convertHtmlTag(hotKeywordtemp.getKeyword()));
                if(hotKeywordtemp.getKeyword().indexOf("<script>") > -1) {
                    hotKeywordtemp.setKeyword(web.getString(null, hotKeywordtemp.getKeyword(), true));
                }
            }
            model.addAttribute("hotKeywordList", hotKeywordList); // View에서 사용하기 위해 등록
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        /** 인기검색어 영역 끝 */
        
        /** (4) 가입 성공 페이지에 보여질 사용자 이름 추출 */
        String userName = (String) web.getSession("userName");
        // 사용자의 이름을 기록
        logger.debug("userName = ", userName);
        // 추출한 세션은 삭제한다.
        web.removeSession("userName");
        // View에 보여질 값을 등록
        model.addAttribute("userName", userName);
        
        return new ModelAndView("member/member_join_success");
    } // End memberJoinSuccess Method
    
}
