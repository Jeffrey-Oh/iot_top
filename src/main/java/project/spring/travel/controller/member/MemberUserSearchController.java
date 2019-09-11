package project.spring.travel.controller.member;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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

import project.spring.helper.RegexHelper;
import project.spring.helper.WebHelper;
import project.spring.travel.model.HotKeyword;
import project.spring.travel.model.Member;
import project.spring.travel.service.HotKeywordService;
import project.spring.travel.service.MemberService;

/**
 * @fileName    : MemberUserSearchController.java
 * @author      : 홍준성
 * @description : 아이디 찾기와 비밀번호 찾기에 관련된 컨트롤러 모음
 * @lastUpdate  : 2019. 4. 18.
 */
/** 컨트롤러 선언 */
@Controller
public class MemberUserSearchController {
    /** (1) 사용하고자 하는 Helper + Service 객체 선언 */
    // -> import org.slf4j.Logger;
    // -> import org.slf4j.LoggerFactory;
    Logger logger = LoggerFactory.getLogger(MemberUserSearchController.class); // Log4j 객체 직접 생성
    // -> import org.apache.ibatis.session.SqlSession;
    @Autowired
    SqlSession sqlSession;
    // -> import project.spring.helper.WebHelper
    @Autowired
    WebHelper web;
    // -> import project.spring.helper.RegexHelper
    @Autowired
    RegexHelper regex;
    // -> import project.java.travel.service.MemberService
    @Autowired
    MemberService memberService;
    // -> import project.spring.travel.service.HotKeywordService;
    @Autowired
    HotKeywordService hotKeywordService;
    
    /**
     * 회원 아이디 찾기 View를 위한 컨트롤러
     */
    @RequestMapping(value = "/member/member_userId_search.do", method = RequestMethod.GET)
    public ModelAndView memberUserIdSearch(Locale locale, Model model, 
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
        
        return new ModelAndView("member/member_userId_search");
    } // End memberUserIdSearch Method
    
    /**
     * 아이디 찾기 액션을 위한 컨트롤러
     */
    @RequestMapping(value = "/member/member_userId_search_ok.do", method = RequestMethod.POST)
    public ModelAndView memberUserIdSearchOk(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "member_userId_search.do"))) {
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
        // member_userId_serach에서 보낸 파라미터를 기록한다.
        logger.debug("cert_ok = " + cert_ok);
        logger.debug("userName = " + userName);
        logger.debug("gender = " + gender);
        logger.debug("year = " + year);
        logger.debug("month = " + month);
        logger.debug("day = " + day);
        logger.debug("email = " + email);
        logger.debug("certNum = " + certNum);
        
        /** ===== (5) 입력값의 유효성 검사 ===== */
        // member_userId_serach에서 보낸 파라미터를 검사한다.
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
        Member userIdSearch = new Member();
        
        try {
            userIdSearch = memberService.userIdSearch(member);
            // 조회된 정보가 없을 경우
            if(userIdSearch == null) {
                return web.redirect(null, "일치하는 회원정보가 없습니다.");
            }
            logger.debug("userIdSearch.toString = " + userIdSearch.toString());
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        web.setSession("memberId", userIdSearch.getMemberId());
        web.setSession("userId", userIdSearch.getUserId());
        web.setSession("regDate", userIdSearch.getRegDate());
        
        /** ===== MemberUserIdSearchSuccess 컨트롤러에서 사용하기 위한 세션 저장 ===== */
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
        return web.redirect(web.getRootPath() + "/member/member_userId_search_success.do", null);
    } // End memberUserIdSearchOk Method
    
    /**
     * 아이디 찾기 성공 View를 위한 컨트롤러
     */
    @RequestMapping(value = "/member/member_userId_search_success.do", method = RequestMethod.GET)
    public ModelAndView memberUserIdSearchSuccess(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "member_userId_search_ok.do"))) {
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
        String cert_ok = (String) web.getSession("cert_ok");
        String userName = (String) web.getSession("userName");
        String gender = (String) web.getSession("gender");
        String year = (String) web.getSession("year");
        String month = (String) web.getSession("month");
        String day = (String) web.getSession("day");
        String email = (String) web.getSession("email");
        String certNum = (String) web.getSession("certNum");
        // 찾아온 회원의 정보를 받는다.
        String userId = (String) web.getSession("userId");
        String regDate = (String) web.getSession("regDate");
        int memberId = (Integer) web.getSession("memberId");
        
        // 모든 세션 삭제
        web.removeSession("cert_ok");
        web.removeSession("userName");
        web.removeSession("gender");
        web.removeSession("year");
        web.removeSession("month");
        web.removeSession("day");
        web.removeSession("email");
        web.removeSession("certNum");
        web.removeSession("userId");
        web.removeSession("regDate");
        web.removeSession("memberId");
        
        // 우선 로그에 기록
        logger.debug("cert_ok = " + cert_ok);
        logger.debug("userName = " + userName);
        logger.debug("gender = " + gender);
        logger.debug("year = " + year);
        logger.debug("month = " + month);
        logger.debug("day = " + day);
        logger.debug("email = " + email);
        logger.debug("certNum = " + certNum);
        logger.debug("userId = " + userId);
        logger.debug("regDate = " + regDate);
        logger.debug("memberId = " + memberId);
        
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
        
        // 들고온 아이디, 생성일, 회원 일련번호 검사
        if(!regex.isValue(userId) || !regex.isValue(regDate) || memberId == 0) {
            return web.redirect(null, "회원님의 정보를 찾을 수 없습니다.");
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
        
        // View에 출력할 아이디와 생성일을 JSTL변수에 담는다.
        model.addAttribute("userId", userId);
        String[] regDateSplit = regDate.split("-");
        model.addAttribute("year", regDateSplit[0]);
        model.addAttribute("month", regDateSplit[1]);
        model.addAttribute("day", regDateSplit[2]);
        model.addAttribute("memberId", memberId);
        
        return new ModelAndView("member/member_userId_search_success");
    } // End memberUserIdSearchSuccess Method
    
    /**
     * 비밀번호 찾기를 위한 View 컨트롤러
     */
    @RequestMapping(value = "/member/member_userPw_search.do", method = RequestMethod.GET)
    public ModelAndView memberUserPwSearch(Locale locale, Model model, 
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
        
        return new ModelAndView("member/member_userPw_search");
    } // End memberUserPwSearch Method
    
    /**
     * 비밀번호 찾기 액션을 위한 컨트롤러
     */
    @RequestMapping(value = "/member/member_userPw_search_ok.do", method = RequestMethod.POST)
    public ModelAndView memberUserPwSearchOk(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "member_userPw_search.do"))) {
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
        // member_userPw_search에서는 아이디 파라미터가 넘어온다.
        String userId = web.getString("userId");
        
        /** 우선 로그에 기록 */
        // member_userPw_search에서 보낸 파라미터를 기록한다.
        logger.debug("cert_ok = " + cert_ok);
        logger.debug("userName = " + userName);
        logger.debug("gender = " + gender);
        logger.debug("year = " + year);
        logger.debug("month = " + month);
        logger.debug("day = " + day);
        logger.debug("email = " + email);
        logger.debug("certNum = " + certNum);
        logger.debug("userId = " + userId);
        
        /** ===== (5) 입력값의 유효성 검사 ===== */
        // member_userPw_search에서 보낸 파라미터를 검사한다.
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
            return web.redirect(null, "성별이 잘못되었습니다.");
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
        
        // 아이디 검사
        if(!regex.isValue(userId)) {
            return web.redirect(null, "아이디를 입력하세요.");
        }
        if(!regex.isEngNum(userId)) {
            return web.redirect(null, "아이디는 영어와 숫자 조합만 입력 가능합니다.");
        }
        if(!regex.isMinLength(userId, 4)) {
            return web.redirect(null, "아이디는 최소 4자 이상 입력하셔야 합니다.");
        }
        if(!regex.isMaxLength(userId, 20)) {
            return web.redirect(null, "아이디는 최대 20자 까지만 입력 가능합니다.");
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
        member.setUserId(userId);
        
        /**
         * ===== Service 계층의 기능을 통한 사용자 인증 =====
         * - 파라미터가 저장된 Beans를 Service에게 전달한다.
         * - Service는 MyBatis의 Mapper를 호출하고 조회 결과를 리턴할 것이다.
         */
        /** (7) Service를 통한 검사 */
        Member userPwSearch = new Member();
        try {
            userPwSearch = memberService.userPwSearch(member);
            // 조회된 정보가 없을 경우
            if(userPwSearch == null) {
                return web.redirect(null, "일치하는 회원정보가 없습니다.");
            }
            logger.debug("userPwSearch.toString = " + userPwSearch.toString());
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        
        /** ===== MemberUserPwUpdate 컨트롤러에서 사용하기 위한 세션 저장 ===== */
        web.setSession("memberId", userPwSearch.getMemberId());
        web.setSession("userId", userPwSearch.getUserId());
        
        web.setSession("cert_ok", cert_ok);
        web.setSession("userName", userName);
        web.setSession("gender", gender);
        web.setSession("year", year);
        web.setSession("month", month);
        web.setSession("day", day);
        web.setSession("email", email);
        web.setSession("certNum", certNum);
        
        return web.redirect(web.getRootPath() + "/member/member_userPw_update.do", null);
    } // End memberUserPwSearchOk Method
    
    /**
     * 비밀번호 재설정 View를 위한 컨트롤러
     */
    @RequestMapping(value = "/member/member_userPw_update.do")
    public ModelAndView memberUserPwUpdate(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "member_userId_search_success.do") && regex.isIndexCheck(referer, "member_userPw_search_ok.do"))) {
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
        int memberId = 0;
        String userId = null;
        if(!regex.isIndexCheck(referer, "member_userId_search_success.do")) {
            memberId = web.getInt("memberId");
            userId = web.getString("userId");
        } else if(!regex.isIndexCheck(referer, "member_userPw_search_ok.do")) {
            memberId = (Integer) web.getSession("memberId");
            userId = (String) web.getSession("userId");
            web.removeSession("memberId");
            web.removeSession("userId");
        }
        
        /** 우선 로그에 기록 */
        logger.debug("memberId = ", memberId);
        logger.debug("userId = " + userId);
        
        /** ===== (5) 입력값의 유효성 검사 ===== */
        // 들고온 회원정보 검사
        if(memberId == 0) {
            return web.redirect(null, "회원님의 정보를 찾을 수 없습니다.");
        }
        // 들고온 아이디 검사
        if(!regex.isValue(userId)) {
            return web.redirect(null, "회원님의 아이디를 찾을 수 없습니다.");
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
        
        // View에 담아서 userPw에 userId가 포함되는지 비교해야 함
        model.addAttribute("userId", userId);
        
        // MemberUserPwUpdateOk에서 사용할 회원 정보
        web.setSession("memberId", memberId);
        web.setSession("userId", userId);
        
        return new ModelAndView("member/member_userPw_update");
    } // End memberUserPwUpdate Method
    
    /**
     * 비밀번호 재설정을 처리하기 위한 액션 컨트롤러
     */
    @RequestMapping(value = "/member/member_userPw_update_ok.do", method = RequestMethod.POST)
    public String memberUserPwUpdateOk(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** 컨텐츠 타입 명시 */
        response.setContentType("application/json");
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "member_userPw_update.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            try {
                response.sendRedirect(web.getRootPath() + "/error/error_page.do");
            } catch (IOException e) {
                logger.debug(e.getLocalizedMessage());
                return null;
            }
            return null;
        }
        
        /** ===== (3) 로그인 여부 검사 ===== */
        // 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
        if(web.getSession("loginInfo") != null) {
            web.printJsonRt("LOGIN_OK");
            return null;
        }
        
        /** ===== (4) 파라미터 받기 ===== */
        // 세션에 저장된 회원정보를 꺼낸다.
        int memberId = (Integer) web.getSession("memberId");
        String userId = (String) web.getSession("userId");
        // member_userPw_update에서 넘어온 파라미터를 받는다.
        String userPw = web.getString("userPw");
        String userPwCheck = web.getString("userPwCheck");
        
        /** 우선 로그에 기록 */
        logger.debug("memberId = ", memberId);
        logger.debug("userId = ", userId);
        logger.debug("userPw = ", userPw);
        logger.debug("userPwCheck = ", userPwCheck);
        
        // 사용한 세션은 모두 삭제
        web.removeSession("memberId");
        web.removeSession("userId");
        
        /** ===== (5) 유효성 검사 ===== */
        // 들고온 회원정보 검사
        if(memberId == 0) {
            web.printJsonRt("회원님의 정보를 찾을 수 없습니다.");
            return null;
        }
        // 들고온 아이디 검사
        if(!regex.isValue(userId)) {
            web.printJsonRt("회원님의 아이디를 찾을 수 없습니다.");
            return null;
        }
        
        // member_userPw_update에서 입력받은 값을 검사한다.
        // 비밀번호 값 검사
        if(!regex.isValue(userPw)) {
            web.printJsonRt("비밀번호를 입력해 주세요.");
            return null;
        }
        if(!regex.isMinLength(userPw, 6)) {
            web.printJsonRt("비밀번호는 최소 6자 이상 입력하셔야 합니다.");
            return null;
        }
        if(!regex.isMaxLength(userPw, 20)) {
            web.printJsonRt("비밀번호는 최대 20자 까지만 입력 가능합니다.");
            return null;
        }
        if(!regex.isPassword(userPw)) {
            web.printJsonRt("비밀번호는 적어도 1개 이상의 숫자와 1개 이상의 특수문자가 포함되어야 합니다.");
            return null;
        }
        if(!regex.isIndexCheck(userPw, userId)) {
            web.printJsonRt("비밀번호에는 아이디가 포함되면 안됩니다.");
            return null;
        }
        // 비밀번호 확인 검사
        if(!regex.isValue(userPwCheck)) {
            web.printJsonRt("비밀번호 확인을 입력해 주세요.");
            return null;
        }
        if(!regex.isCompareTo(userPw, userPwCheck)) {
            web.printJsonRt("비밀번호 확인이 잘못되었습니다.");
            return null;
        }
        
        /**
         * ===== 준비된 모든 데이터를 JavaBeans로 묶은 후 Service에 전달 =====
         * - Service를 통해 Mapper가 호출되고 Database에 Insert 된다.
         */
        /** (6) 전달받은 파라미터를 Beans 객체에 담는다. */
        Member member = new Member();
        member.setMemberId(memberId);
        member.setUserPw(userPw);
        
        /** (8) Service를 통한 데이터베이스 업데이트 처리 */
        try {
            memberService.userPwChange(member);
        } catch (Exception e) {
            web.printJsonRt(e.getLocalizedMessage());
            // 예외가 발생한 경우이므로, 더이상 진행하지 않는다.
            return null;
        }
        
        /** (9) 비밀번호 재설정이 완료되었으므로 로그인 페이지로 이동 */
        web.printJsonRt("OK");
        
        return null;
    } // End memberUserPwUpdateOk Method
    
}
