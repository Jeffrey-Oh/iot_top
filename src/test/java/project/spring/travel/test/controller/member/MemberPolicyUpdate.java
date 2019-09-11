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
//import org.apache.ibatis.session.SqlSession;
//import org.apache.logging.log4j.LogManager;
//
//import project.java.travel.dao.MyBatisConnectionFactory;
//import project.java.travel.model.Member;
//import project.java.travel.model.MemberPolicy;
//import project.java.travel.service.MemberPolicyService;
//import project.java.travel.service.impl.MemberPolicyServiceImpl;
//import project.spring.helper.BaseController;
//import project.spring.helper.RegexHelper;
//import project.spring.helper.WebHelper;
//
///**
// * @fileName    : MemberPolicyUpdate.java
// * @author      : 홍준성
// * @description : 정책 안내 수정을 위한 View 컨트롤러
// * @lastUpdate  : 2019. 4. 12.
// */
//@WebServlet("/member/member_policy_update.do")
//public class MemberPolicyUpdate extends BaseController {
//    private static final long serialVersionUID = 4697535868521891192L;
//    
//    /**
//     * ===== 관리자 외에는 접근할 수 없는 페이지 =====
//     * - 정책안내 관리 페이지는 관리계정 상태가 아니라면 접근할 수 없도록 처리해야 하기 때문에
//     *     세션이 존재한다면 (null이 아니라면) 관리자 계정인지 확인 한 후 접근할 수 있도록 제어한다.
//     */
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
//        /** (2) 사용하고자 하는 Helper + Service 객체 생성 */
//        // -> import org.apache.logging.log4j.LogManager;
//        logger = LogManager.getFormatterLogger(request.getRequestURI());
//        // -> import study.jsp.myschool.dao.MyBatisConnectionFactory;
//        sqlSession = MyBatisConnectionFactory.getSqlSession();
//        web = WebHelper.getInstance(request, response);
//        regex = RegexHelper.getInstance();
//        // 정책안내 수정 처리를 위한 Service객체
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
//        } else {
//            if(regex.isIndexCheck(referer, "member_policy_view.do")) {
//                // 거쳐야할 절차를 건너뛴 경우
//                sqlSession.close();
//                logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
//                web.redirect(web.getRootPath() + "/error/error_page.do", null);
//                return null;
//            }
//        }
//        
//        /** (3) 로그인 여부 검사 */
//        // 관리자가 아니라면 이 페이지를 동작시켜서는 안된다.
//        Member loginInfo = (Member) web.getSession("loginInfo");
//        if(!loginInfo.getGrade().equals("Master")) {
//            sqlSession.close();
//            web.redirect(web.getRootPath() + "/index.do", "관리자 계정만 접근 가능합니다.");
//            return null;
//        }
//        
//        /**
//         * ===== 이전에 작성된 내용을 조회하기 위한 파라미터 처리 =====
//         */
//        /** (4) 전달받은 파라미터 추출 */
//        int policyId = web.getInt("policyId");
//        // 로그에 기록
//        logger.debug("policyId = " + policyId);
//        
//        /** (5) 유효성 검사 */
//        if(policyId == 0) {
//            sqlSession.close();
//            web.redirect(null, "정책안내 번호가 없습니다.");
//            return null;
//        }
//        
//        /** (6) 수정 대상을 조회하기 위한 Beans 생성*/
//        // MyBatis의 Where절에 사용할 값을 담은 객체
//        MemberPolicy policy = new MemberPolicy();
//        policy.setPolicyId(policyId);
//        
//        /** (7) Service를 통한 SQL 수행 */
//        // 이전에 작성한 내용 조회 결과를 저장하기 위한 객체
//        MemberPolicy item = null;
//        try {
//            item = memberPolicyService.selectPolicy(policy);
//        } catch (Exception e) {
//            web.redirect(null, e.getLocalizedMessage());
//            return null;
//        } finally {
//            sqlSession.close();
//        }
//        
//        /** (8) View에서 사용하기 위해 등록 */
//        request.setAttribute("item", item);
//        
//        return "member/member_policy_update";
//    }
//
//}
