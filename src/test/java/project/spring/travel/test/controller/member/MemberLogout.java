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
//import project.java.travel.model.Member;
//import project.spring.helper.BaseController;
//import project.spring.helper.WebHelper;
//
///**
// * @fileName    : MemberLogout.java
// * @author      : 홍준성
// * @description : 로그아웃 처리를 위한 서블릿
// * @lastDate    : 2019. 3. 31.
// */
//@WebServlet("/member/member_logout.do")
//public class MemberLogout extends BaseController {
//    private static final long serialVersionUID = -7326448342897763137L;
//    
//    /** (1) 사용하고자 하는 Helper + Service 객체 선언 */
//    // -> import project.spring.helper.WebHelper
//    WebHelper web;
//
//    @Override
//    public String doRun(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        /**
//         * ===== 로그아웃 구현 =====
//         * - 로그인 여부를 세션 객체의 존재 여부로 판단하기 때문에
//         *     로그아웃은 세션 데이터를 삭제하여 처리 할 수 있다.
//         */
//        /** (2) 필요한 헬퍼 객체 생성 */
//        logger = LogManager.getFormatterLogger(request.getRequestURI());
//        web = WebHelper.getInstance(request, response);
//        
//        /** ===== 악의적인 접근에 대한 방어 코드 */
//        String referer = request.getHeader("referer");
//        if(referer == null) {
//            // 정상적인 접근 방법이 아닐 경우
//            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
//            web.redirect(web.getRootPath() + "/error/error_page.do", null);
//            return null;
//        }
//        
//        /** (3) 로그인 여부 검사 */
//        // 로그인중인 회원 정보 가져오기
//        Member loginInfo = (Member) web.getSession("loginInfo");
//        // 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
//        if(loginInfo == null) {
//            web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
//            return null;
//        }
//        
//        /** (4) 로그아웃 */
//        // 로그아웃은 모든 세션 정보를 삭제하는 처리
//        web.removeAllSession();
//        web.removeCookie("profileThumbnail");
//        
//        /** (5) 페이지 이동 */
//        /** 로그인을 시도하기 전의 페이지를 기록한다. */
//        String movePage = request.getHeader("referer");
//        if(movePage == null) {
//            movePage = web.getRootPath() + "/index.do";
//        }
//        
//        web.redirect(movePage, null);
//        
//        return null;
//    }
//}
