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
// * @fileName    : UserIdCheckOk.java
// * @author      : 홍준성
// * @description : 아이디 중복검사를 위한 액션 컨트롤러
// * @lastDate    : 2019. 3. 31.
// */
//@WebServlet("/member/user_id_check_ok.do")
//public class UserIdCheckOk extends BaseController {
//    private static final long serialVersionUID = 3703605167446024692L;
//    
//    /** 사용하고자 하는 Helper + Service 객체 선언 */
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
//        // 아이디 중복검사 처리를 위한 Service객체
//        // -> import study.jsp.mysite.service.impl.MemberServiceImpl
//        memberService = new MemberServiceImpl(sqlSession, logger);
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
//            if(regex.isIndexCheck(referer, "member_join_form.do")) {
//                // 거쳐야할 절차를 건너뛴 경우
//                sqlSession.close();
//                logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
//                response.sendRedirect(web.getRootPath() + "/error/error_page.do");
//                return null;
//            }
//        }
//        
//        /** (3) 로그인 여부 검사 */
//        // 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
//        if(web.getSession("loginInfo") != null) {
//            sqlSession.close();
//            web.printJsonRt("LOGIN_OK");
//            return null;
//        }
//        
//        /** (4) 사용자 입력값 받기 */
//        String userId = web.getString("userId");
//        // 사용자 입력값 기록
//        logger.debug("userId = " + userId);
//        
//        /** (5) 필수 값의 존재여부 검사 */
//        // 아이디 검사
//        if(!regex.isValue(userId)) {
//            sqlSession.close();
//            web.printJsonRt("아이디를 입력해 주세요.");
//            return null;
//        }
//        if(!regex.isEngNum(userId)) {
//            sqlSession.close();
//            web.printJsonRt("아이디는 영어와 숫자 조합만 입력 가능합니다.");
//            return null;
//        }
//        if(!regex.isMinLength(userId, 4)) {
//            sqlSession.close();
//            web.printJsonRt("아이디는 최소 4자 이상 입력하셔야 합니다.");
//            return null;
//        }
//        if(!regex.isMaxLength(userId, 20)) {
//            sqlSession.close();
//            web.printJsonRt("아이디는 최대 20자 까지만 입력 가능합니다.");
//            return null;
//        }
//        
//        /** (6) 전달받은 파라미터를 Beans에 설정한다. */
//        Member member = new Member();
//        member.setUserId(userId);
//        
//        /**
//         * ===== Service 계층의 기능을 통한 사용자 인증 =====
//         * - 파라미터가 저장된 Beans를 Service에게 전달한다.
//         * - Service는 MyBatis의 Mapper를 호출하고 조회 결과를 리턴할 것이다.
//         */
//        /** (7) Service를 통한 아이디 중복 검사 */
//        int result = 0;
//        try {
//            result = memberService.userIdCheck(member);
//        } catch (Exception e) {
//            web.printJsonRt(e.getLocalizedMessage());
//            return null;
//        } finally {
//            sqlSession.close();
//        }
//        
//        /** (8) 처리 결과를 JSON으로 출력하기 */
//        if(result > 0) {
//            web.printJsonRt("사용 불가능한 아이디 입니다.");
//        } else {
//            web.printJsonRt("OK");
//        }
//        
//        return null;
//    }
//
//}
