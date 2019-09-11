package project.spring.travel.test.controller.servicearea;
//package project.spring.travel.controller.servicearea;
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
//import project.spring.helper.WebHelper;
//
///**
// * @fileName    : ServiceareaIndex.java
// * @author      : 홍준성
// * @description : 휴게소 메인 View를 위한 컨트롤러
// * @lastUpdate  : 2019. 4. 16.
// */
//@WebServlet("/servicearea/servicearea_index.do")
//public class ServiceareaIndex extends BaseController {
//    private static final long serialVersionUID = -3686914249250330949L;
//    
//    /** (1) 사용하고자 하는 Helper + Service 객체 선언 */
//    // Logger logger 는 BaseController에서 이미 객체 선언이 되어 있음으로 여기서는 또 선언할 필요가 없다.
//    // -> import project.spring.helper.WebHelper
//    WebHelper web;
//
//    @Override
//    public String doRun(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        /** (2) 사용하고자 하는 Helper + Service 객체 생성 */
//        // -> import org.apache.logging.log4j.LogManager;
//        logger = LogManager.getFormatterLogger(request.getRequestURI());
//        web = WebHelper.getInstance(request, response);
//        
//        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
//        String referer = request.getHeader("referer");
//        if(referer == null) {
//            // 정상적인 접근 방법이 아닐 경우
//            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
//            web.redirect(web.getRootPath() + "/error/error_page.do", null);
//            return null;
//        }
//        
//        return "servicearea/servicearea_index";
//    }
//
//}
