package project.spring.travel.test.controller.member;
//package project.spring.travel.controller.member;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.ibatis.session.SqlSession;
//import org.apache.logging.log4j.LogManager;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import project.java.travel.dao.MyBatisConnectionFactory;
//import project.java.travel.model.MemberPolicy;
//import project.java.travel.service.MemberPolicyService;
//import project.java.travel.service.impl.MemberPolicyServiceImpl;
//import project.spring.helper.BaseController;
//import project.spring.helper.RegexHelper;
//import project.spring.helper.WebHelper;
//
///**
// * @fileName    : MemberPolicySelect.java
// * @author      : 홍준성
// * @description : 정책안내 하나의 항목을 조회하기 위한 컨트롤러
// * @lastUpdate  : 2019. 4. 3.
// */
//@WebServlet("/member/member_policy_select.do")
//public class MemberPolicySelect extends BaseController {
//    private static final long serialVersionUID = 1068071267537332613L;
//    
//    /** (1) 사용하고자 하는 Helper + Service 객체 선언 */
//    // Logger logger 는 BaseController에서 이미 객체 선언이 되어 있음으로 여기서는 또 선언할 필요가 없다.
//    // -> import org.apache.ibatis.session.SqlSession;
//    SqlSession sqlSession;
//    // -> import project.spring.helper.WebHelper
//    WebHelper web;
//    // -> import project.spring.helper.RegexHelper
//    RegexHelper regex;
//    // -> import project.java.travel.service.MemberService
//    MemberPolicyService memberPolicyService;
//
//    @Override
//    public String doRun(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        /** crossdomain 접속 허용 */
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with, origin, content-type, accept");
//        /** 컨텐츠 타입 명시 */
//        response.setContentType("application/json");
//        
//        /** (2) 사용하고자 하는 Helper + Service 객체 생성 */
//        // -> import org.apache.logging.log4j.LogManager;
//        logger = LogManager.getFormatterLogger(request.getRequestURI());
//        // -> import study.jsp.myschool.dao.MyBatisConnectionFactory;
//        sqlSession = MyBatisConnectionFactory.getSqlSession();
//        web = WebHelper.getInstance(request, response);
//        regex = RegexHelper.getInstance();
//        // 정책안내 View 처리를 위한 Service객체
//        // -> import study.jsp.mysite.service.impl.MemberServiceImpl
//        memberPolicyService = new MemberPolicyServiceImpl(sqlSession, logger);
//        
//        /** ===== 악의적인 접근에 대한 방어 코드 */
//        String referer = request.getHeader("referer");
//        if(referer == null) {
//            // 정상적인 접근 방법이 아닐 경우
//            sqlSession.close();
//            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
//            response.sendRedirect(web.getRootPath() + "/error/error_page.do");
//            return null;
//        } else {
//            if(regex.isIndexCheck(referer, "member_policy_view.do")) {
//                // 거쳐야할 절차를 건너뛴 경우
//                sqlSession.close();
//                logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
//                response.sendRedirect(web.getRootPath() + "/error/error_page.do");
//                return null;
//            }
//        }
//        
//        /** (4) 사용자 선택값 받기 */
//        int policyId = web.getInt("policyId");
//        // 로그로 기록
//        logger.debug("policyId = " + policyId);
//        
//        /** (5) 유효성 검사 */
//        if(policyId == 0) {
//            sqlSession.close();
//            web.printJsonRt("선택된 날짜가 없습니다.");
//            return null;
//        }
//        
//        /** (6) 전달받은 파라미터를 Beans에 설정한다. */
//        MemberPolicy item = new MemberPolicy();
//        item.setPolicyId(policyId);
//        
//        /** (7) Service를 통한 하나의 항목을 조회 */
//        try {
//            item = memberPolicyService.selectPolicy(item);
//        } catch (Exception e) {
//            web.printJsonRt(e.getLocalizedMessage());
//            return null;
//        } finally {
//            sqlSession.close();
//        }
////        logger.debug(item.toString());
////        item.setAgreementDoc(web.convertHtmlTag(item.getAgreementDoc()));
////        item.setInfoCollectionDoc(web.convertHtmlTag(item.getInfoCollectionDoc()));
////        item.setCommunityDoc(web.convertHtmlTag(item.getCommunityDoc()));
//        item.setEmailCollectionDoc(web.convertHtmlTag(item.getEmailCollectionDoc()));
//        
//        // ->import java.util.HashMap;
//        // -> import java.util.Map;
//        Map<String, Object> data = new HashMap<String, Object>();
//        data.put("rt", "OK");
//        data.put("item", item);
//        
//        // -> import com.fasterxml.jackson.databind.ObjectMapper;
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.writeValue(response.getWriter(), data);
//        
//        return null;
//    }
//
//}
