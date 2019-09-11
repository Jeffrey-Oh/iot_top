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
//import org.json.JSONObject;
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
// * @fileName    : MemberUserPwUpdateOk.java
// * @author      : 홍준성
// * @description : 비밀번호 재설정을 처리하기 위한 액션 컨트롤러
// * @lastUpdate  : 2019. 4. 1.
// */
//@WebServlet("/member/member_userPw_update_ok.do")
//public class MemberUserPwUpdateOk extends BaseController {
//    private static final long serialVersionUID = -4369261880321933074L;
//    
//    /** ===== (1) 사용하고자 하는 Helper + Service 객체 선언 ===== */
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
//        /** ===== (2) 사용하고자 하는 Helper + Service 객체 생성 ===== */
//        // -> import org.apache.logging.log4j.LogManager;
//        logger = LogManager.getFormatterLogger(request.getRequestURI());
//        // -> import study.jsp.myschool.dao.MyBatisConnectionFactory;
//        sqlSession = MyBatisConnectionFactory.getSqlSession();
//        web = WebHelper.getInstance(request, response);
//        regex = RegexHelper.getInstance();
//        // 회원가입 처리 또는 아이디 찾기를 위한 Service객체
//        // -> import project.java.travel.service.impl.MemberServiceImpl
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
//            if(regex.isIndexCheck(referer, "member_userPw_update.do")) {
//                // 거쳐야할 절차를 건너뛴 경우
//                sqlSession.close();
//                logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
//                web.redirect(web.getRootPath() + "/error/error_page.do", null);
//                return null;
//            }
//        }
//        
//        /** ===== (3) 로그인 여부 검사 ===== */
//        // 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
//        if(web.getSession("loginInfo") != null) {
//            sqlSession.close();
//            web.redirect(web.getRootPath() + "/index.do", "이미 로그인 하셨습니다.");
//            return null;
//        }
//        
//        /** ===== (4) 파라미터 받기 ===== */
//        // 세션에 저장된 회원정보를 꺼낸다.
//        int memberId = (int) web.getSession("memberId");
//        String userId = (String) web.getSession("userId");
//        // member_userPw_update에서 넘어온 파라미터를 받는다.
//        String userPw = web.getString("userPw");
//        String userPwCheck = web.getString("userPwCheck");
//        
//        /** 우선 로그에 기록 */
//        logger.debug("memberId = ", memberId);
//        logger.debug("userId = ", userId);
//        logger.debug("userPw = ", userPw);
//        logger.debug("userPwCheck = ", userPwCheck);
//        
//        // 사용한 세션은 모두 삭제
//        web.removeSession("memberId");
//        web.removeSession("userId");
//        
//        /** ===== (5) 유효성 검사 ===== */
//        // 들고온 회원정보 검사
//        if(memberId == 0) {
//            sqlSession.close();
//            web.redirect(null, "회원님의 정보를 찾을 수 없습니다.");
//            return null;
//        }
//        // 들고온 아이디 검사
//        if(!regex.isValue(userId)) {
//            sqlSession.close();
//            web.redirect(null, "회원님의 아이디를 찾을 수 없습니다.");
//            return null;
//        }
//        
//        // member_userPw_update에서 입력받은 값을 검사한다.
//        // 비밀번호 값 검사
//        if(!regex.isValue(userPw)) {
//            sqlSession.close();
//            web.redirect(null, "비밀번호를 입력해 주세요.");
//            return null;
//        }
//        if(!regex.isMinLength(userPw, 6)) {
//            sqlSession.close();
//            web.redirect(null, "비밀번호는 최소 6자 이상 입력하셔야 합니다.");
//            return null;
//        }
//        if(!regex.isMaxLength(userPw, 20)) {
//            sqlSession.close();
//            web.redirect(null, "비밀번호는 최대 20자 까지만 입력 가능합니다.");
//            return null;
//        }
//        if(!regex.isPassword(userPw)) {
//            sqlSession.close();
//            web.redirect(null, "비밀번호는 적어도 1개 이상의 숫자와 1개 이상의 특수문자가 포함되어야 합니다.");
//            return null;
//        }
//        if(!regex.isIndexCheck(userPw, userId)) {
//            sqlSession.close();
//            web.redirect(null, "비밀번호에는 아이디가 포함되면 안됩니다.");
//            return null;
//        }
//        // 비밀번호 확인 검사
//        if(!regex.isValue(userPwCheck)) {
//            sqlSession.close();
//            web.redirect(null, "비밀번호 확인을 입력해 주세요.");
//            return null;
//        }
//        if(!regex.isCompareTo(userPw, userPwCheck)) {
//            sqlSession.close();
//            web.redirect(null, "비밀번호 확인이 잘못되었습니다.");
//            return null;
//        }
//        
//        /**
//         * ===== 준비된 모든 데이터를 JavaBeans로 묶은 후 Service에 전달 =====
//         * - Service를 통해 Mapper가 호출되고 Database에 Insert 된다.
//         */
//        /** (6) 전달받은 파라미터를 Beans 객체에 담는다. */
//        Member member = new Member();
//        member.setMemberId(memberId);
//        member.setUserPw(userPw);
//        
//        /** (8) Service를 통한 데이터베이스 업데이트 처리 */
//        try {
//            memberService.userPwChange(member);
//        } catch (Exception e) {
//            sqlSession.close();
//            web.redirect(null, e.getLocalizedMessage());
//            // 예외가 발생한 경우이므로, 더이상 진행하지 않는다.
//            return null;
//        }
//        
//        /**
//         * ===== 비밀번호 재설정 완료 후 페이지 이동 처리 =====
//         */
//        /** (9) 비밀번호 재설정이 완료되었으므로 로그인 페이지로 이동 */
//        sqlSession.close();
////        web.redirect(web.getRootPath() + "/member/member_login.do", "비밀번호 재설정에 성공하였습니다! 로그인을 해주세요.");
//        JSONObject json = new JSONObject();
//        json.put("userPw_change_ok", "true");
//        response.getWriter().print(json);
//        
//        return null;
//    }
//
//}
