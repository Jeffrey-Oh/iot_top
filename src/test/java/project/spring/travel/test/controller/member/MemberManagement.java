package project.spring.travel.test.controller.member;
//package project.jsp.travel.controller.member;
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
//import project.java.travel.model.Member;
//import project.java.travel.service.MemberService;
//import project.java.travel.service.impl.MemberServiceImpl;
//import project.jsp.helper.BaseController;
//import project.jsp.helper.PageHelper;
//import project.jsp.helper.WebHelper;
//
///**
// * @fileName    : MemberManagement.java
// * @author      : 홍준성
// * @description : 회원 관리 View를 위한 컨트롤러
// * @lastUpdate  : 2019. 4. 9.
// */
//@WebServlet("/member/member_management.do")
//public class MemberManagement extends BaseController {
//    private static final long serialVersionUID = 5641518376297303310L;
//    
//    /** (1) 사용하고자 하는 Helper + Service 객체 선언 */
//    // Logger logger 는 BaseController에서 이미 객체 선언이 되어 있음으로 여기서는 또 선언할 필요가 없다.
//    // -> import org.apache.ibatis.session.SqlSession;
//    SqlSession sqlSession;
//    // -> import project.jsp.helper.WebHelper
//    WebHelper web;
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
//        // 회원관리 View 처리를 위한 Service객체
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
//         * ===== 검색어 파라미터 전달받기 =====
//         * - 사용자가 입력한 검색어는 최종적으로
//         *     SQL의 Where 조건에 사용되어야 한다.
//         * - SQL을 수행하는 Mapper에게 검색어를 전달하기 위하여
//         *     JavaBeans 객체에 사용자의 입력값을 저장한다.
//         */
//        /** (4) 검색어 파라미터 받기 + Beans 설정 */
//        String keyword = web.getString("keyword", "");
//        // 로그에 기록
//        logger.debug("keyword = " + keyword);
//        // View의 검색영역에 사용하기 위해 등록
//        request.setAttribute("keyword", keyword);
//        Member member = new Member();
//        member.setUserName(keyword);
//        
//        /**
//         * ===== 페이지 번호 파라미터 받기 =====
//         * - 처음 페이지가 열릴 때 기본값 1로 설정된다.
//         * - 페이지 번호를 클릭할 때 마다
//         *     JSP페이지가 자기 스스로에게 page번호값을 전송하게 된다.
//         */
//        // 현재 페이지 번호에 대한 파라미터 받기
//        int nowPage = web.getInt("page", 1);
//        // 로그에 기록
//        logger.debug("nowPage = " + nowPage);
//        
//        /** (5) Service를 통한 SQL 수행 */
//        /**
//         * ===== 전체 게시물 수 구하기 =====
//         * - 검색어가 있는 경우 검색결과를 반영해야 하기 때문에,
//         *     전체 데이터 수를 구하는 작업은
//         *     검색어 파라미터 처리 이후에 수행되어야 한다.
//         */
//        // 전체 데이터 수 조회하기
//        int totalCount = 0;
//        
//        try {
//            totalCount = memberService.selectMemberCount(member);
//        } catch (Exception e) {
//            // 예외 발생 시, Service Layer에서 전달하는 메시지 내용을
//            // 사용자에게 제시하고 이전페이지로 이동한다.
//            sqlSession.close();
//            web.redirect(null, e.getLocalizedMessage());
//            return null;
//        }
//        
//        // 페이지 번호에 대한 연산 수행 후, 조회조건값 지정을 위한 Beans에 추가하기
//        PageHelper pageHelper = PageHelper.getInstance(nowPage, totalCount, 10, 5);
//        member.setLimitStart(pageHelper.getLimitStart());
//        member.setListCount(pageHelper.getListCount());
//        // View의 페이지 구현에 사용하기 위해 등록
//        request.setAttribute("pageHelper", pageHelper);
//        
//        // 조회 결과를 저장하기 위한 객체
//        List<Member> list = null;
//        
//        try {
//            /**
//             * ===== 검색어를 저장하고 있는 Beans를 Service 객체에게 주입하기 =====
//             * - GET 파라미터로 전달받은 검색어를 JavaBeans에 담은 상태로
//             *     Service 객체에 전달한다.
//             * - 이렇게 전달된 객체는 MyBatis의 Mapper에게 전달될 것이다.
//             */
//            list = memberService.selectMemberList(member);
//        } catch (Exception e) {
//            // 예외 발생 시, Service Layer에서 전달하는 메시지 내용을
//            // 사용자에게 제시하고 이전페이지로 이동한다.
//            web.redirect(null, e.getLocalizedMessage());
//            return null;
//        } finally {
//            sqlSession.close();
//        }
//        
//        request.setAttribute("list", list);
//        
//        return "member/member_management";
//    }
//}
