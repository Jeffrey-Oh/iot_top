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
//import project.java.travel.service.MemberService;
//import project.java.travel.service.impl.MemberServiceImpl;
//import project.spring.helper.BaseController;
//import project.spring.helper.RegexHelper;
//import project.spring.helper.WebHelper;
//
///**
// * @fileName    : MemberManagementView.java
// * @author      : 홍준성
// * @description : 회원관리에서 회원 상세 정보 View를 위한 컨트롤러
// * @lastUpdate  : 2019. 4. 11.
// */
//@WebServlet("/member/member_management_view.do")
//public class MemberManagementView extends BaseController {
//    private static final long serialVersionUID = 1260550547222746644L;
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
//    MemberService memberService;
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
//        // 회원관리에서 회원 상세 정보 View 처리를 위한 Service객체
//        // -> import study.jsp.mysite.service.impl.MemberServiceImpl
//        memberService = new MemberServiceImpl(sqlSession, logger);
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
//            if(regex.isIndexCheck(referer, "member_management.do")) {
//                // 거쳐야할 절차를 건너뛴 경우
//                sqlSession.close();
//                logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
//                response.sendRedirect(web.getRootPath() + "/error/error_page.do");
//                return null;
//            }
//        }
//        
//        /** (3) 관리자 계정 로그인 여부 검사 */
//        // 관리자 계정으로 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
//        Member loginInfo = (Member) web.getSession("loginInfo");
//        if(loginInfo == null) {
//            sqlSession.close();
//            web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
//            return null;
//        } else if(!loginInfo.getGrade().equals("Master")) {
//            sqlSession.close();
//            web.redirect(web.getRootPath() + "/index.do", "관리자 계정만 접근 가능합니다.");
//            return null;
//        }
//        
//        /**
//         * ===== 목록에서 선택한 항목을 식별하기 위한 파라미터 처리 =====
//         * - 목록에서 상세 페이지로 회원의 일련번호(Primary Key)값을
//         *     GET 파라미터로 전달하고, 상세페이지는 전달받은 값을 사용하여
//         *     조회 대상을 식별한다.
//         */
//        /** (4) 이전 페이지에서 전달된 회원번호 받기 */
//        int memberId = web.getInt("memberId");
//        // 전달된 값 로그에 기록
//        logger.debug("memberId = " + memberId);
//        // View에서 사용하기 위해 등록
//        request.setAttribute("memberId", memberId);
//        
//        /** (5) 유효성 검사 */
//        if(memberId == 0) {
//            sqlSession.close();
//            web.redirect(null, "회원번호가 없습니다.");
//            return null;
//        }
//        
//        /** (6) Beans 생성 */
//        // MyBatis의 Where절에 사용할 값을 담은 객체
//        Member member = new Member();
//        member.setMemberId(memberId);
//        
//        /**
//         * ===== Service 계층을 통한 데이터 조회 처리 =====
//         * - 목록에서 전달받은 파라미터를 Beans로 묶어서 Service에 전달한다.
//         * - Service는 MyBatis (DAO계층) 와의 연동을 통해서
//         *     상세 정보를 조회하여 리턴하게 된다.
//         */
//        /** (7) Service를 통한 SQL 수행 */
//        // 조회 결과를 저장하기 위한 객체
//        Member item = null;
//        try {
//            item = memberService.selectMember(member);
//        } catch (Exception e) {
//            web.redirect(null, e.getLocalizedMessage());
//            return null;
//        } finally {
//            sqlSession.close();
//        }
//        // 로그에 기록
//        logger.debug("item = " + item);
//        // View에서 사용하기 위해 등록
//        request.setAttribute("item", item);
//        
//        return "member/member_management_view";
//    }
//
//}
