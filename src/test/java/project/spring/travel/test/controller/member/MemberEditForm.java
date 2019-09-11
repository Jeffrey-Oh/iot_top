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
// * @fileName    : MemberEditForm.java
// * @author      : 홍준성
// * @description : 회원정보 수정을 위한 정보입력 View 컨트롤러
// * @lastUpdate  : 2019. 4. 5.
// */
//@WebServlet("/member/member_edit_form.do")
//public class MemberEditForm extends BaseController {
//    private static final long serialVersionUID = -8510037687140057355L;
//    
//    /**
//     * ===== 로그인 중이 아니라면 접근할 수 없는 페이지 =====
//     * - 마이홈 페이지는 로그인 상태가 아니라면 접근할 수 없도록 처리해야 하기 때문에
//     *     세션이 존재한지 않는다면 (null이라면) 접근할 수 없도록 차단한다.
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
//    MemberService memberService;
//
//    @Override
//    public String doRun(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        /** (2) 사용하고자 하는 Helper + Service 객체 생성 */
//        // -> import org.apache.logging.log4j.LogManager;
//        logger = LogManager.getFormatterLogger(request.getRequestURI());
//        // -> import project.spring.travel.dao.MyBatisConnectionFactory;
//        sqlSession = MyBatisConnectionFactory.getSqlSession();
//        web = WebHelper.getInstance(request, response);
//        regex = RegexHelper.getInstance();
//        // 로그인 처리를 위한 Service객체
//        // -> import project.spring.travel.service.impl.MemberServiceImpl
//        memberService = new MemberServiceImpl(sqlSession, logger);
//        
//        /** ===== 악의적인 접근에 대한 방어 코드 */
//        String referer = request.getHeader("referer");
//        if(referer == null || regex.isIndexCheck(referer, "member_edit_door.do")) {
//            // 정상적인 접근 방법이 아닐 경우
//            sqlSession.close();
//            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
//            web.redirect(web.getRootPath() + "/error/error_page.do", null);
//            return null;
//        }
//        
//        /** (3) 로그인 여부 검사 */
//        // 로그인 중이 아니라면 이 페이지를 동작시켜서는 안된다.
//        Member loginInfo = (Member) web.getSession("loginInfo");
//        if(loginInfo == null) {
//            sqlSession.close();
//            web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용 가능합니다.");
//            return null;
//        }
//        
//        /** (3) 파라미터 받기 */
//        String userPw = web.getString("userPw");
//        // 로그에 기록
//        logger.debug("userPw = " + userPw);
//        
//        if(!regex.isValue(userPw)) {
//            sqlSession.close();
//            web.redirect(null, "비밀번호를 입력해 주세요.");
//            return null;
//        }
//        
//        // 비밀번호 검사를 하기 위한 Beans로 묶기
//        Member member = new Member();
//        member.setMemberId(loginInfo.getMemberId());
//        member.setUserPw(userPw);
//        logger.debug("member.toString >> " + member.toString());
//        
//        try {
//            // 입력한 비밀번호와 로그인 상태의 아이디가 일치하는 회원인지 조회
//            memberService.selectMemberPasswordCount(member);
//        } catch (Exception e) {
//            web.redirect(null, e.getLocalizedMessage());
//            return null;
//        } finally {
//            sqlSession.close();
//        }
//        
//        // member_edit_ok에서 사용하기 위해 세션 등록
//        web.setSession("edit_door_userPw", userPw);
//        
//        String birthDate = loginInfo.getBirthDate();
//        logger.debug("birthDate = " + birthDate);
//        // View에 보여질 년월일 형식으로 변경
//        String year = birthDate.substring(0, 4);
//        String month = birthDate.substring(5, 7);
//        String day = birthDate.substring(8, 10);
//        String tempBirthDate = String.format("%s년 %s월 %s일", year, month, day);
//        // View에서 사용하기 위해 등록
//        request.setAttribute("tempBirthDate", tempBirthDate);
//        
//        return "member/member_edit_form";
//    }
//
//}
