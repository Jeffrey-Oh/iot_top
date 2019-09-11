package project.spring.travel.test.controller.member;
//package project.spring.travel.controller.member;
//
//import java.io.IOException;
//import java.util.List;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.ibatis.session.SqlSession;
//import org.apache.logging.log4j.LogManager;
//
//import project.java.travel.dao.MyBatisConnectionFactory;
//import project.java.travel.model.MemberPolicy;
//import project.java.travel.service.MemberPolicyService;
//import project.java.travel.service.impl.MemberPolicyServiceImpl;
//import project.spring.helper.BaseController;
//import project.spring.helper.WebHelper;
//
///**
// * @fileName    : MemberPolicyView.java
// * @author      : 홍준성
// * @description : 정책안내 View를 위한 컨트롤러
// * @lastUpdate  : 2019. 4. 2.
// */
//@WebServlet("/member/member_policy_view.do")
//public class MemberPolicyView extends BaseController {
//    private static final long serialVersionUID = -3575014912837946138L;
//    
//    /** (1) 사용하고자 하는 Helper + Service 객체 선언 */
//    // Logger logger 는 BaseController에서 이미 객체 선언이 되어 있음으로 여기서는 또 선언할 필요가 없다.
//    // -> import org.apache.ibatis.session.SqlSession;
//    SqlSession sqlSession;
//    // -> import project.spring.helper.WebHelper
//    WebHelper web;
//    // -> import project.java.travel.service.MemberService
//    MemberPolicyService memberPolicyService;
//
//    @Override
//    public String doRun(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        /** (2) 사용하고자 하는 Helper + Service 객체 생성 */
//        // -> import org.apache.logging.log4j.LogManager;
//        logger = LogManager.getFormatterLogger(request.getRequestURI());
//        // -> import study.jsp.myschool.dao.MyBatisConnectionFactory;
//        sqlSession = MyBatisConnectionFactory.getSqlSession();
//        web = WebHelper.getInstance(request, response);
//        // 정책안내 View 처리를 위한 Service객체
//        // -> import study.jsp.mysite.service.impl.MemberServiceImpl
//        memberPolicyService = new MemberPolicyServiceImpl(sqlSession, logger);
//        
//        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
//        String referer = request.getHeader("referer");
//        if(referer == null) {
//            // 정상적인 접근 방법이 아닐 경우
//            sqlSession.close();
//            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
//            web.redirect(web.getRootPath() + "/error/error_page.do", null);
//            return null;
//        }
//        
//        List<MemberPolicy> policyList = null;
//        try {
//            policyList = memberPolicyService.selectPolicyList();
//        } catch (Exception e) {
//            web.redirect(null, e.getLocalizedMessage());
//            return null;
//        } finally {
//            sqlSession.close();
//        }
//        
//        for(int i = 0; i < policyList.size(); i++) {
//            MemberPolicy temp = policyList.get(i);
//            // View에 보여질 년월일 형식으로 변경
//            String year = temp.getRegDate().substring(0, 4);
//            String month = temp.getRegDate().substring(5, 7);
//            String day = temp.getRegDate().substring(8, 10);
//            String newDate = String.format("%s년%s월%s일", year, month, day);
//            temp.setRegDate(newDate);
//        }
//        // View에서 사용하기 위해 등록
//        request.setAttribute("policyList", policyList);
//        
//        return "member/member_policy_view";
//    }
//
//}
