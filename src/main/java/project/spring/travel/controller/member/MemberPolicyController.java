package project.spring.travel.controller.member;

import java.io.IOException;
import java.util.HashMap;
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

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.spring.helper.RegexHelper;
import project.spring.helper.WebHelper;
import project.spring.travel.model.HotKeyword;
import project.spring.travel.model.Member;
import project.spring.travel.model.MemberPolicy;
import project.spring.travel.service.HotKeywordService;
import project.spring.travel.service.MemberPolicyService;

/**
 * @fileName    : MemberPolicyController.java
 * @author      : 홍준성
 * @description : 정책안내 관련된 컨틀로러 모음
 * @lastUpdate  : 2019. 4. 18.
 */
/** 컨트롤러 선언 */
@Controller
public class MemberPolicyController {
    /** (1) 사용하고자 하는 Helper + Service 객체 선언 */
    // -> import org.slf4j.Logger;
    // -> import org.slf4j.LoggerFactory;
    Logger logger = LoggerFactory.getLogger(MemberPolicyController.class); // Log4j 객체 직접 생성
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
    MemberPolicyService memberPolicyService;
    // -> import project.spring.travel.service.HotKeywordService;
    @Autowired
    HotKeywordService hotKeywordService;
    
    /**
     * 정책안내 View를 위한 컨트롤러
     */
    @RequestMapping(value = "/member/member_policy_view.do", method = RequestMethod.GET)
    public ModelAndView memberPolicyView(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
        String referer = request.getHeader("referer");
        if(referer == null) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
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
        
        for(int i = 0; i < policyList.size(); i++) {
            MemberPolicy temp = policyList.get(i);
            // View에 보여질 년월일 형식으로 변경
            String year = temp.getRegDate().substring(0, 4);
            String month = temp.getRegDate().substring(5, 7);
            String day = temp.getRegDate().substring(8, 10);
            String newDate = String.format("%s년%s월%s일", year, month, day);
            temp.setRegDate(newDate);
        }
        // View에서 사용하기 위해 등록
        model.addAttribute("policyList", policyList);
        
        return new ModelAndView("member/member_policy_view");
    } // End memberPolicyView Method
    
    /**
     * 정책안내 하나의 항목을 조회하기 위한 컨트롤러
     */
    @RequestMapping(value = "/member/member_policy_select.do", method = RequestMethod.GET)
    public String memberPolicySelect(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** 컨텐츠 타입 명시 */
        response.setContentType("application/json");
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "member_policy_view.do"))) {
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
        
        /** (4) 사용자 선택값 받기 */
        int policyId = web.getInt("policyId");
        // 로그로 기록
        logger.debug("policyId = " + policyId);
        
        /** (5) 유효성 검사 */
        if(policyId == 0) {
            web.printJsonRt("선택된 날짜가 없습니다.");
            return null;
        }
        
        /** (6) 전달받은 파라미터를 Beans에 설정한다. */
        MemberPolicy item = new MemberPolicy();
        item.setPolicyId(policyId);
        
        /** (7) Service를 통한 하나의 항목을 조회 */
        try {
            item = memberPolicyService.selectPolicy(item);
        } catch (Exception e) {
            web.printJsonRt(e.getLocalizedMessage());
            return null;
        }
//        logger.debug(item.toString());
//        item.setAgreementDoc(web.convertHtmlTag(item.getAgreementDoc()));
//        item.setInfoCollectionDoc(web.convertHtmlTag(item.getInfoCollectionDoc()));
//        item.setCommunityDoc(web.convertHtmlTag(item.getCommunityDoc()));
        item.setEmailCollectionDoc(web.convertHtmlTag(item.getEmailCollectionDoc()));
        
        // ->import java.util.HashMap;
        // -> import java.util.Map;
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("rt", "OK");
        data.put("item", item);
        
        // -> import com.fasterxml.jackson.databind.ObjectMapper;
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(response.getWriter(), data);
        } catch (JsonGenerationException e) {
            logger.debug(e.getLocalizedMessage());
            web.printJsonRt("알 수 없는 에러가 발생했습니다. 문의바랍니다.");
            return null;
        } catch (JsonMappingException e) {
            logger.debug(e.getLocalizedMessage());
            web.printJsonRt("알 수 없는 에러가 발생했습니다. 문의바랍니다.");
            return null;
        } catch (IOException e) {
            logger.debug(e.getLocalizedMessage());
            web.printJsonRt("알 수 없는 에러가 발생했습니다. 문의바랍니다.");
            return null;
        }
        
        return null;
    } // End memberPolicySelect Method
    
    /**
     * 정책안내 저장 View를 위한 컨트롤러
     */
    @RequestMapping(value = "/member/member_policy_insert.do", method = RequestMethod.GET)
    public ModelAndView memberPolicyInsert(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "member_policy_view.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            web.redirect(web.getRootPath() + "/error/error_page.do", null);
            return null;
        }
        
        /** (3) 로그인 여부 검사 */
        // 관리자가 아니라면 이 페이지를 동작시켜서는 안된다.
        Member loginInfo = (Member) web.getSession("loginInfo");
        if(!loginInfo.getGrade().equals("Master")) {
            return web.redirect(web.getRootPath() + "/index.do", "관리자 계정만 접근 가능합니다.");
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
        
        return new ModelAndView("member/member_policy_insert");
    } // End memberPolicyInsert Method
    
    /**
     * 정책안내 추가를 위한 액션 컨트롤러
     */
    @RequestMapping(value = "/member/member_policy_insert_ok.do", method = RequestMethod.POST)
    public ModelAndView memberPolicyInsertOk(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "member_policy_insert.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
        }
        
        /** (3) 로그인 여부 검사 */
        // 관리자가 아니라면 이 페이지를 동작시켜서는 안된다.
        Member loginInfo = (Member) web.getSession("loginInfo");
        if(!loginInfo.getGrade().equals("Master")) {
            return web.redirect(web.getRootPath() + "/index.do", "관리자 계정만 접근 가능합니다.");
        }
        
        /** (4) 파라미터 받기 */
        String agreementDoc = web.getString("agreementDoc");
        String infoCollectionDoc = web.getString("infoCollectionDoc");
        String communityDoc = web.getString("communityDoc");
        String emailCollectionDoc = web.getString("emailCollectionDoc");
        
        // 전달된 파라미터는 로그로 확인한다.
        logger.debug("agreementDoc = " + agreementDoc);
        logger.debug("infoCollectionDoc = " + infoCollectionDoc);
        logger.debug("communityDoc = " + communityDoc);
        logger.debug("emailCollectionDoc = " + emailCollectionDoc);
        
        // 유효성 검사
        if(!regex.isValue(agreementDoc)) {
            return web.redirect(null, "이용약관 내용을 입력해 주세요.");
        }
        if(!regex.isValue(infoCollectionDoc)) {
            return web.redirect(null, "개인정보처리방침 내용을 입력해 주세요.");
        }
        if(!regex.isValue(communityDoc)) {
            return web.redirect(null, "게시글관리규정 내용을 입력해 주세요.");
        }
        if(!regex.isValue(emailCollectionDoc)) {
            return web.redirect(null, "이메일무단수집거부 내용을 입력해 주세요.");
        }
        
        /** (5) 입력 받은 파라미터를 Beans로 묶기 */
        MemberPolicy policy = new MemberPolicy();
        policy.setAgreementDoc(agreementDoc);
        policy.setInfoCollectionDoc(infoCollectionDoc);
        policy.setCommunityDoc(communityDoc);
        policy.setEmailCollectionDoc(emailCollectionDoc);
        
        /** (6) Service를 통한 정책안내 저장 */
        try {
            memberPolicyService.insertPolicy(policy);
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        
        /** (7) 저장이 완료되었으므로 목록 페이지로 이동 */
        return web.redirect(web.getRootPath() + "/member/member_policy_view.do", null);
    } // End memberPolicyInsertOk Method
    
    /**
     * 정책 안내 수정을 위한 View 컨트롤러
     */
    @RequestMapping(value = "/member/member_policy_update.do", method = RequestMethod.GET)
    public ModelAndView memberPolicyUpdate(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "member_policy_view.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
        }
        
        /** (3) 로그인 여부 검사 */
        // 관리자가 아니라면 이 페이지를 동작시켜서는 안된다.
        Member loginInfo = (Member) web.getSession("loginInfo");
        if(!loginInfo.getGrade().equals("Master")) {
            return web.redirect(web.getRootPath() + "/index.do", "관리자 계정만 접근 가능합니다.");
        }
        
        /**
         * ===== 이전에 작성된 내용을 조회하기 위한 파라미터 처리 =====
         */
        /** (4) 전달받은 파라미터 추출 */
        int policyId = web.getInt("policyId");
        // 로그에 기록
        logger.debug("policyId = " + policyId);
        
        /** (5) 유효성 검사 */
        if(policyId == 0) {
            return web.redirect(null, "정책안내 번호가 없습니다.");
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
        
        /** (6) 수정 대상을 조회하기 위한 Beans 생성*/
        // MyBatis의 Where절에 사용할 값을 담은 객체
        MemberPolicy policy = new MemberPolicy();
        policy.setPolicyId(policyId);
        
        /** (7) Service를 통한 SQL 수행 */
        // 이전에 작성한 내용 조회 결과를 저장하기 위한 객체
        MemberPolicy item = null;
        try {
            item = memberPolicyService.selectPolicy(policy);
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        
        /** (8) View에서 사용하기 위해 등록 */
        model.addAttribute("item", item);
        
        return new ModelAndView("member/member_policy_update");
    } // End memberPolicyUpdate Method
    
    /**
     * 정책안내 수정을 처리하기 위한 액션 컨트롤러
     */
    @RequestMapping(value = "/member/member_policy_update_ok.do", method = RequestMethod.POST)
    public ModelAndView memberPolicyUpdateOk(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "member_policy_update.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
        }
        
        /** (3) 로그인 여부 검사 */
        // 관리자가 아니라면 이 페이지를 동작시켜서는 안된다.
        Member loginInfo = (Member) web.getSession("loginInfo");
        if(!loginInfo.getGrade().equals("Master")) {
            return web.redirect(web.getRootPath() + "/index.do", "관리자 계정만 접근 가능합니다.");
        }
        
        /** (4) 파라미터 받기 */
        int policyId = web.getInt("policyId");
        String agreementDoc = web.getString("agreementDoc");
        String infoCollectionDoc = web.getString("infoCollectionDoc");
        String communityDoc = web.getString("communityDoc");
        String emailCollectionDoc = web.getString("emailCollectionDoc");
        
        // 전달된 파라미터는 로그로 확인한다.
        logger.debug("policyId = " + policyId);
        logger.debug("agreementDoc = " + agreementDoc);
        logger.debug("infoCollectionDoc = " + infoCollectionDoc);
        logger.debug("communityDoc = " + communityDoc);
        logger.debug("emailCollectionDoc = " + emailCollectionDoc);
        
        // 유효성 검사
        if(!regex.isValue(agreementDoc)) {
            return web.redirect(null, "이용약관 내용을 입력해 주세요.");
        }
        if(!regex.isValue(infoCollectionDoc)) {
            return web.redirect(null, "개인정보처리방침 내용을 입력해 주세요.");
        }
        if(!regex.isValue(communityDoc)) {
            return web.redirect(null, "게시글관리규정 내용을 입력해 주세요.");
        }
        if(!regex.isValue(emailCollectionDoc)) {
            return web.redirect(null, "이메일무단수집거부 내용을 입력해 주세요.");
        }
        
        /** (5) 입력 받은 파라미터를 Beans로 묶기 */
        MemberPolicy policy = new MemberPolicy();
        policy.setPolicyId(policyId);
        policy.setAgreementDoc(agreementDoc);
        policy.setInfoCollectionDoc(infoCollectionDoc);
        policy.setCommunityDoc(communityDoc);
        policy.setEmailCollectionDoc(emailCollectionDoc);
        
        /** (6) Service를 통한 정책안내 수정 */
        try {
            memberPolicyService.updatePolicy(policy);
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        
        /** (7) 수정이 완료되었으므로 목록 페이지로 이동 */
        return web.redirect(web.getRootPath() + "/member/member_policy_view.do", null);
    } // End memberPolicyUpdateOk Method
    
    /**
     * 정책안내 삭제를 위한 액션 컨트롤러
     */
    @RequestMapping(value = "/member/member_policy_delete.do", method = RequestMethod.GET)
    public ModelAndView memberPolicyDelete(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "member_policy_view.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
        }
        
        /** (3) 로그인 여부 검사 */
        // 관리자가 아니라면 이 페이지를 동작시켜서는 안된다.
        Member loginInfo = (Member) web.getSession("loginInfo");
        if(!loginInfo.getGrade().equals("Master")) {
            return web.redirect(web.getRootPath() + "/index.do", "관리자 계정만 접근 가능합니다.");
        }
        
        /**
         * ===== 파라미터 처리 =====
         * - 전달받은 일련번호(Primary Key)값을
         *     삭제를 수행하기 위한 Where절의 조건값으로 사용해야 한다.
         */
        /** (4) 전달받은 파라미터 추출 */
        int policyId = web.getInt("policyId");
        // 로그에 기록
        logger.debug("policyId = " + policyId);
        
        /** (5) 유효성 검사 */
        if(policyId == 0) {
            return web.redirect(null, "정책안내 번호가 없습니다.");
        }
        
        /** (6) 삭제를 위한 Beans 생성*/
        // MyBatis의 Where절에 사용할 값을 담은 객체
        MemberPolicy policy = new MemberPolicy();
        policy.setPolicyId(policyId);
        
        /** (7) Service를 통한 SQL 수행 */
        try {
            memberPolicyService.deletePolicy(policy);
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        
        /** (7) 삭제가 완료되었으므로 목록 페이지로 이동 */
        return web.redirect(web.getRootPath() + "/member/member_policy_view.do", null);
    } // End memberPolicyDelete Method
    
}
