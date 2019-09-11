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
// * @fileName    : MemberJoinSuccess.java
// * @author      : 홍준성
// * @description : 회원가입 성공 View 페이지를 위한 컨트롤러
// * @lastUpdate    : 2019. 3. 31.
// */
//@WebServlet("/member/member_join_success.do")
//public class MemberJoinSuccess extends BaseController {
//    private static final long serialVersionUID = -3390213383878265071L;
//    
//    /**
//     * ===== 로그인 중에는 접근할 수 없는 페이지 =====
//     * - 회원가입 페이지는 로그인 상태에서는 접근할 수 없도록 처리해야 하기 때문에
//     *     세션이 존재한다면 (null이 아니라면) 접근할 수 없도록 차단한다.
//     */
//    /** (1) 사용하고자 하는 Helper + Service 객체 선언 */
//    // -> import project.spring.helper.WebHelper;
//    WebHelper web;
//    // -> import project.spring.helper.RegexHelper
//    RegexHelper regex;
//
//    @Override
//    public String doRun(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        /** (2) 사용하고자 하는 Helper + Service 객체 생성 */
//        logger = LogManager.getFormatterLogger(request.getRequestURI());
//        web = WebHelper.getInstance(request, response);
//        regex = RegexHelper.getInstance();
//        
//        /** ===== 악의적인 접근에 대한 방어 코드 */
//        String referer = request.getHeader("referer");
//        if(referer == null) {
//            // 정상적인 접근 방법이 아닐 경우
//            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
//            web.redirect(web.getRootPath() + "/error/error_page.do", null);
//            return null;
//        } else {
//            if(regex.isIndexCheck(referer, "member_join_form_ok.do")) {
//                // 거쳐야할 절차를 건너뛴 경우
//                logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
//                web.redirect(web.getRootPath() + "/error/error_page.do", null);
//                return null;
//            }
//        }
//        
//        /** (3) 로그인 여부 검사 */
//        // 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
//        if(web.getSession("loginInfo") != null) {
//            web.redirect(web.getRootPath() + "/index.do", "이미 로그인 하셨습니다.");
//            return null;
//        }
//        
//        /** (4) 가입 성공 페이지에 보여질 사용자 이름 추출 */
//        String userName = (String) web.getSession("userName");
//        // 사용자의 이름을 기록
//        logger.debug("userName = ", userName);
//        // 추출한 세션은 삭제한다.
//        web.removeSession("userName");
//        // View에 보여질 값을 등록
//        request.setAttribute("userName", userName);
//        
//        return "member/member_join_success";
//    }
//}
