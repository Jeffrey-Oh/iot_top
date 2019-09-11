package project.spring.travel.test.controller.member;
//package project.jsp.travel.controller.mypage;
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
//import project.jsp.helper.BaseController;
//import project.jsp.helper.RegexHelper;
//import project.jsp.helper.WebHelper;
//
///**
// * @fileName    : MypageEditDoor.java
// * @author      : 홍준성
// * @description : 회원정보 수정하기 전 비밀번호 입력을 위한 View 컨트롤러
// * @lastUpdate  : 2019. 4. 2.
// */
//@WebServlet("/mypage/member_edit_door.do")
//public class MypageEditDoor extends BaseController {
//    private static final long serialVersionUID = 6932674013346444800L;
//    
//    /**
//     * ===== 로그인 중이 아니라면 접근할 수 없는 페이지 =====
//     * - 마이홈 페이지는 로그인 상태가 아니라면 접근할 수 없도록 처리해야 하기 때문에
//     *     세션이 존재한지 않는다면 (null이라면) 접근할 수 없도록 차단한다.
//     */
//    /** (1) 사용하고자 하는 Helper + Service 객체 선언 */
//    // -> import project.jsp.helper.WebHelper
//    WebHelper web;
//    // -> import project.jsp.helper.RegexHelper
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
//        }
//        
//        /** (3) 로그인 여부 검사 */
//        // 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
//        if(web.getSession("loginInfo") == null) {
//            web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
//            return null;
//        }
//        
//        return "mypage/MypageEditDoor";
//    }
//
//}
