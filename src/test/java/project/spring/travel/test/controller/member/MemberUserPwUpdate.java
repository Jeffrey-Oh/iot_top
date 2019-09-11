package project.spring.travel.test.controller.member;
//package project.spring.travel.controller.member;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.logging.log4j.LogManager;
//
//import project.spring.helper.BaseController;
//import project.spring.helper.RegexHelper;
//import project.spring.helper.WebHelper;
//
///**
// * @fileName    : MemberUserPwUpdate.java
// * @author      : 홍준성
// * @description : 비밀번호 재설정 View를 위한 컨트롤러
// * @lastUpdate  : 2019. 4. 1.
// */
//@WebServlet("/member/member_userPw_update.do")
//public class MemberUserPwUpdate extends BaseController {
//    private static final long serialVersionUID = 1805472139317922413L;
//    
//    /** ===== (1) 사용하고자 하는 Helper + Service 객체 선언 ===== */
//    // Logger logger 는 BaseController에서 이미 객체 선언이 되어 있음으로 여기서는 또 선언할 필요가 없다.
//    // -> import project.spring.helper.WebHelper
//    WebHelper web;
//    // -> import project.spring.helper.RegexHelper
//    RegexHelper regex;
//
//    @Override
//    public String doRun(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        /** ===== (2) 사용하고자 하는 Helper + Service 객체 생성 ===== */
//        // -> import org.apache.logging.log4j.LogManager;
//        logger = LogManager.getFormatterLogger(request.getRequestURI());
//        web = WebHelper.getInstance(request, response);
//        regex = RegexHelper.getInstance();
//        
//        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
//        String referer = request.getHeader("referer");
//        if(referer == null) {
//            // 정상적인 접근 방법이 아닐 경우
//            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
//            web.redirect(web.getRootPath() + "/error/error_page.do", null);
//            return null;
//        } else {
//            if(regex.isIndexCheck(referer, "member_userId_search_success.do") && regex.isIndexCheck(referer, "member_join_or_userId_userPw_search_ok.do")) {
//                // 거쳐야할 절차를 건너뛴 경우
//                logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
//                web.redirect(web.getRootPath() + "/error/error_page.do", null);
//                return null;
//            }
//        }
//        
//        /** ===== (3) 로그인 여부 검사 ===== */
//        // 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
//        if(web.getSession("loginInfo") != null) {
//            web.redirect(web.getRootPath() + "/index.do", "이미 로그인 하셨습니다.");
//            return null;
//        }
//        
//        /** ===== (4) 파라미터 받기 ===== */
//        int memberId = 0;
//        String userId = null;
//        if(!regex.isIndexCheck(referer, "member_userId_search_success.do")) {
//            memberId = web.getInt("memberId");
//            userId = web.getString("userId");
//        } else if(!regex.isIndexCheck(referer, "member_userPw_search_ok.do")) {
//            memberId = (int) web.getSession("memberId");
//            userId = (String) web.getSession("userId");
//            web.removeSession("memberId");
//            web.removeSession("userId");
//        }
//        
//        /** 우선 로그에 기록 */
//        logger.debug("memberId = ", memberId);
//        logger.debug("userId = " + userId);
//        
//        /** ===== (5) 입력값의 유효성 검사 ===== */
//        // 들고온 회원정보 검사
//        if(memberId == 0) {
//            web.redirect(null, "회원님의 정보를 찾을 수 없습니다.");
//            return null;
//        }
//        // 들고온 아이디 검사
//        if(!regex.isValue(userId)) {
//            web.redirect(null, "회원님의 아이디를 찾을 수 없습니다.");
//            return null;
//        }
//        
//        // View에 담아서 userPw에 userId가 포함되는지 비교해야 함
//        request.setAttribute("userId", userId);
//        
//        // MemberUserPwUpdateOk에서 사용할 회원 정보
//        web.setSession("memberId", memberId);
//        web.setSession("userId", userId);
//        
//        return "member/member_userPw_update";
//    }
//    
//}
