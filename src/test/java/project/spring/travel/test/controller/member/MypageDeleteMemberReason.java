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
//import org.apache.ibatis.session.SqlSession;
//import org.apache.logging.log4j.LogManager;
//
//import project.java.travel.dao.MyBatisConnectionFactory;
//import project.java.travel.model.Member;
//import project.java.travel.service.MemberService;
//import project.java.travel.service.impl.MemberServiceImpl;
//import project.jsp.helper.BaseController;
//import project.jsp.helper.RegexHelper;
//import project.jsp.helper.WebHelper;
//
///**
// * @fileName    : MypageDeleteMemberReason.java
// * @author      : 홍준성
// * @description : 회원탈퇴 View를 위한 컨트롤러
// * @lastUpdate  : 2019. 4. 8.
// */
//@WebServlet("/mypage/mypage_delete_member_reason.do")
//public class MypageDeleteMemberReason extends BaseController {
//    private static final long serialVersionUID = 9211924023477189161L;
//    
//    /** 사용하고자 하는 Helper + Service 객체 선언 */
//    // Logger logger 는 BaseController에서 이미 객체 선언이 되어 있음으로 여기서는 또 선언할 필요가 없다.
//    // -> import org.apache.ibatis.session.SqlSession;
//    SqlSession sqlSession;
//    // -> import project.jsp.helper.WebHelper
//    WebHelper web;
//    // -> import project.jsp.helper.RegexHelper
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
//        // 회원탈퇴 전 본인인증을 위한 Service객체
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
//            if(regex.isIndexCheck(referer, "member_edit_form.do")) {
//                // 거쳐야할 절차를 건너뛴 경우
//                sqlSession.close();
//                logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
//                web.redirect(web.getRootPath() + "/error/error_page.do", null);
//                return null;
//            }
//        }
//        
//        /** (3) 로그인 여부 검사 */
//        // 로그인 중이라면 이 페이지를 동작시켜서는 안된다.
//        Member loginInfo = (Member) web.getSession("loginInfo");
//        if(loginInfo == null) {
//            sqlSession.close();
//            web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
//            return null;
//        }
//        
//        /** member_edit_door에서 전달한 세션 받기 */
//        String edit_door_userPw = (String) web.getSession("edit_door_userPw");
//        // 로그에 기록
//        logger.debug("edit_door_userPw = " + edit_door_userPw);
//        // 사용한 세션 삭제
//        web.removeSession("edit_door_userPw");
//        
//        /** 본인 검사 */
//        if(!regex.isValue(edit_door_userPw)) {
//            sqlSession.close();
//            web.redirect(null, "비밀번호가 잘못되었습니다.");
//            return null;
//        }
//        
//        // 본인이 맞는지 검사할 Beans
//        Member userPwMember = new Member();
//        userPwMember.setMemberId(loginInfo.getMemberId());
//        userPwMember.setUserPw(edit_door_userPw);
//        
//        try {
//            memberService.selectMemberPasswordCount(userPwMember);
//        } catch (Exception e) {
//            web.redirect(null, e.getLocalizedMessage());
//            return null;
//        } finally {
//            sqlSession.close();
//        }
//        
//        // MemberOutOk에서 사용할 세션 등록
//        web.setSession("edit_door_userPw", edit_door_userPw);
//        
//        return "mypage/MypageDeleteMemberReason";
//    }
//}
