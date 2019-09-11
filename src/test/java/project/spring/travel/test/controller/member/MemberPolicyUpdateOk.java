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
// * @fileName    : MemberPolicyUpdateOk.java
// * @author      : 홍준성
// * @description : 정책안내 수정을 처리하기 위한 액션 컨트롤러
// * @lastUpdate  : 2019. 4. 13.
// */
//@WebServlet("/member/member_policy_update_ok.do")
//public class MemberPolicyUpdateOk extends BaseController {
//    private static final long serialVersionUID = 2695753276687280054L;
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
//            if(regex.isIndexCheck(referer, "member_policy_update.do")) {
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
//        /** (4) 파라미터 받기 */
//        int policyId = web.getInt("policyId");
//        String agreementDoc = web.getString("agreementDoc");
//        String infoCollectionDoc = web.getString("infoCollectionDoc");
//        String communityDoc = web.getString("communityDoc");
//        String emailCollectionDoc = web.getString("emailCollectionDoc");
//        
//        // 전달된 파라미터는 로그로 확인한다.
//        logger.debug("policyId = " + policyId);
//        logger.debug("agreementDoc = " + agreementDoc);
//        logger.debug("infoCollectionDoc = " + infoCollectionDoc);
//        logger.debug("communityDoc = " + communityDoc);
//        logger.debug("emailCollectionDoc = " + emailCollectionDoc);
//        
//        /** (5) 입력 받은 파라미터를 Beans로 묶기 */
//        MemberPolicy policy = new MemberPolicy();
//        policy.setPolicyId(policyId);
//        policy.setAgreementDoc(agreementDoc);
//        policy.setInfoCollectionDoc(infoCollectionDoc);
//        policy.setCommunityDoc(communityDoc);
//        policy.setEmailCollectionDoc(emailCollectionDoc);
////        logger.debug("policy.toString >> " + policy.toString());
//        
//        /** (6) Service를 통한 정책안내 수정 */
//        try {
//            memberPolicyService.updatePolicy(policy);
//        } catch (Exception e) {
//            web.redirect(null, e.getLocalizedMessage());
//            return null;
//        } finally {
//            sqlSession.close();
//        }
//        
//        /** (7) 수정이 완료되었으므로 목록 페이지로 이동 */
//        web.redirect(web.getRootPath() + "/member/member_policy_view.do", null);
//        
//        return null;
//    }
//}
