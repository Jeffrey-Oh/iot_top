package project.spring.travel.test.controller.member;
//package project.spring.travel.controller.member;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.mail.MessagingException;
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
//import project.java.travel.model.Member;
//import project.java.travel.service.MemberService;
//import project.java.travel.service.impl.MemberServiceImpl;
//import project.spring.helper.BaseController;
//import project.spring.helper.MailHelper;
//import project.spring.helper.RegexHelper;
//import project.spring.helper.Util;
//import project.spring.helper.WebHelper;
//
///**
// * @fileName    : CertificationOk.java
// * @author      : 홍준성
// * @description : 이메일 인증번호 발송 컨트롤러 기본 구성
// * @lastDate    : 2019. 3. 25.
// */
//@WebServlet("/member/certification_ok.do")
//public class CertificationOk extends BaseController {
//    private static final long serialVersionUID = -7558808435296721180L;
//
//    /** 사용하고자 하는 Helper + Service 객체 선언 */
//    // Logger logger 는 BaseController에서 이미 객체 선언이 되어 있음으로 여기서는 또 선언할 필요가 없다.
//    // -> import org.apache.ibatis.session.SqlSession;
//    SqlSession sqlSession;
//    // -> import project.spring.helper.WebHelper
//    WebHelper web;
//    // -> import project.spring.helper.MailHelper
//    MailHelper mail;
//    // -> import project.spring.helper.RegexHelper
//    RegexHelper regex;
//    // -> import project.spring.helper.Util
//    Util util;
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
//        /** 사용하고자 하는 Helper + Service 객체 생성 */
//        // -> import org.apache.logging.log4j.LogManager;
//        logger = LogManager.getFormatterLogger(request.getRequestURI());
//        // -> import study.jsp.myschool.dao.MyBatisConnectionFactory;
//        sqlSession = MyBatisConnectionFactory.getSqlSession();
//        web = WebHelper.getInstance(request, response);
//        mail = MailHelper.getInstance();
//        regex = RegexHelper.getInstance();
//        util = Util.getInstance();
//        // 회원가입 처리 또는 아이디 찾기와 비밀번호 찾기를 위한 Service객체
//        // -> import project.java.travel.service.impl.MemberServiceImpl
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
//            if(regex.isIndexCheck(referer, "member_join.do") && regex.isIndexCheck(referer, "member_userId_search.do") && regex.isIndexCheck(referer, "member_userPw_search.do")) {
//                // 거쳐야할 절차를 건너뛴 경우
//                sqlSession.close();
//                logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
//                response.sendRedirect(web.getRootPath() + "/error/error_page.do");
//                return null;
//            }
//        }
//
//        /** 로그인 여부 검사 */
//        // 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
//        if(web.getSession("loginInfo") != null) {
//            sqlSession.close();
//            web.printJsonRt("LOGIN_OK");
//            return null;
//        }
//        
//        /** 인증번호를 생성한다. */
//        String getCertification = util.getCertification(6, 2);
//        
//        /** 파라미터 받기 */
//        // 입력된 메일 주소를 받는다.
//        String email = web.getString("email");
//        String userName = web.getString("userName");
//        logger.debug("email = " + email);
//        logger.debug("userName = " + userName);
//
//        /**
//         * ===== 이메일 발송 전에 이메일 존재유무 검사 =====
//         * - emailCheck Method를 호출하여 이곳에 들어온 페이지에 따라 분기한다.
//         */
//        Member member = new Member();
//        member.setEmail(email);
//        int result = 0;
//        if(!regex.isIndexCheck(referer, "member_join.do")) {
//            try {
//                result = memberService.emailCheck(member);
//            } catch (Exception e) {
//                sqlSession.close();
//                web.printJsonRt(e.getLocalizedMessage());
//                return null;
//            }
//            // 기존에 가입된 회원일 경우
//            if(result > 0) {
//                sqlSession.close();
//                web.printJsonRt("이미 가입된 이메일 입니다.");
//                return null;
//            }
//        } else if(!regex.isIndexCheck(referer, "member_userId_search.do") || !regex.isIndexCheck(referer, "member_userPw_search.do")) {
//            try {
//                result = memberService.emailCheck(member);
//            } catch (Exception e) {
//                sqlSession.close();
//                web.printJsonRt(e.getLocalizedMessage());
//                return null;
//            }
//            // 존재하지 않는 회원일 경우
//            if(result == 0) {
//                sqlSession.close();
//                web.printJsonRt("가입되지 않은 이메일 입니다.");
//                return null;
//            }
//        }
//        
//        /** 발급된 인증번호를 메일로 발송하기 */
//        String sender = "ghdwnstjd13@gmail.com";
//        String subject = userName + "님께 완벽한 여행 회원가입을 위한 인증번호가 도착했습니다.";
//        String content = "<span style='font-size: 20px;'>" + userName + "님의 인증번호는 <strong>" + getCertification + "</strong>입니다.</span>";
//        
//        /** 메일 발송 처리 */
//        try {
//            // sendMail() 메서드 선언시 throws를 정의했기 때문에 예외처리가 요구된다.
//            mail.sendMail(sender, email, subject, content);
//        } catch(MessagingException e) {
//            sqlSession.close();
//            logger.debug(e.getLocalizedMessage());
//            web.printJsonRt("메일 발송에 실패했습니다. 관리자에게 문의 바랍니다.");
//            return null;
//        } catch(Exception e) {
//            sqlSession.close();
//            logger.debug(e.getLocalizedMessage());
//            web.printJsonRt("알 수 없는 이유로 메일 발송에 실패했습니다.");
//            return null;
//        }
//        /** 처리 결과를 JSON으로 출력하기 */
//        Map<String, Object> data = new HashMap<String, Object>();
//        data.put("rt", "OK");
//        data.put("certificationNum", getCertification);
//        logger.debug(userName + "님의 인증번호 = ", getCertification);
//        System.out.println(userName + "님의 인증번호 = " + getCertification);
//        
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.writeValue(response.getWriter(), data);
//        
//        return null;
//    }
//	
//}
