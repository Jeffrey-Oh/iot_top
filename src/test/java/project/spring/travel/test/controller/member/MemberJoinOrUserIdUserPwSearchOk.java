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
// * @fileName    : MemberJoinOrUserIdUserPwSearchOk.java
// * @author      : 홍준성
// * @description : 회원가입 이메일인증 1단계 또는 아이디 찾기와 비밀번호 찾기 액션을 위한 컨트롤러
// * @lastDate    : 2019. 04. 01.
// */
//@WebServlet("/member/member_join_or_userId_userPw_search_ok.do")
//public class MemberJoinOrUserIdUserPwSearchOk extends BaseController {
//    private static final long serialVersionUID = -5475354942617234663L;
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
//        
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
//            if(regex.isIndexCheck(referer, "member_join.do") && regex.isIndexCheck(referer, "member_userId_search.do") && regex.isIndexCheck(referer, "member_userPw_search.do")) {
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
//        // 입력된 이메일 인증 폼의 값을 받는다.
//        String cert_ok = web.getString("cert_ok");
//        String userName = web.getString("userName");
//        String gender = web.getString("gender");
//        String year = web.getString("year");
//        String month = web.getString("month");
//        String day = web.getString("day");
//        String email = web.getString("email");
//        String certNum = web.getString("certNum");
//        // member_userPw_search에서는 아이디 파라미터가 넘어온다.
//        String userId = web.getString("userId");
//        
//        /** 우선 로그에 기록 */
//        // member_join에서 보낸 파라미터를 기록한다.
//        logger.debug("cert_ok = " + cert_ok);
//        logger.debug("userName = " + userName);
//        logger.debug("gender = " + gender);
//        logger.debug("year = " + year);
//        logger.debug("month = " + month);
//        logger.debug("day = " + day);
//        logger.debug("email = " + email);
//        logger.debug("certNum = " + certNum);
//        // member_userPw_search에서 넘어온 아이디를 기록한다.
//        logger.debug("userId = " + userId);
//        
//        /** ===== (5) 입력값의 유효성 검사 ===== */
//        // member_join에서 보낸 파라미터를 검사한다.
//        // 이메일 인증 검사
//        if(!cert_ok.equals("true")) {
//            sqlSession.close();
//            web.redirect(null, "이메일 인증에 성공하지 못하였습니다.");
//            return null;
//        }
//        
//        // 이름 검사
//        if(!regex.isValue(userName)) {
//            sqlSession.close();
//            web.redirect(null, "이름을 입력하세요.");
//            return null;
//        }
//        if(!regex.isKor(userName)) {
//            sqlSession.close();
//            web.redirect(null, "이름은 한글만 가능합니다.");
//            return null;
//        }
//        if(!regex.isMinLength(userName, 2)) {
//            sqlSession.close();
//            web.redirect(null, "이름은 최소 2자 이상 입력해야 합니다.");
//            return null;
//        }
//        if(!regex.isMaxLength(userName, 20)) {
//            sqlSession.close();
//            web.redirect(null, "이름은 최대 20자 까지만 입력 가능합니다.");
//            return null;
//        }
//        
//        // 성별 검사
//        if(!regex.isValue(gender)) {
//            sqlSession.close();
//            web.redirect(null, "성별을 선택해 주세요.");
//            return null;
//        }
//        if(!gender.equals("M") && !gender.equals("F")) {
//            sqlSession.close();
//            web.redirect(null, "성별이 잘못되었습니다.");
//            return null;
//        }
//        
//        // 생년월일 검사
//        if(!regex.isValue(year)) {
//            sqlSession.close();
//            web.redirect(null, "생년월일을 선택해 주세요.");
//            return null;
//        }
//        if(!regex.isValue(month)) {
//            sqlSession.close();
//            web.redirect(null, "생년월일을 선택해 주세요.");
//            return null;
//        }
//        if(!regex.isValue(day)) {
//            sqlSession.close();
//            web.redirect(null, "생년월일을 선택해 주세요.");
//            return null;
//        }
//        
//        // 이메일 검사
//        if(!regex.isValue(email)) {
//            sqlSession.close();
//            web.redirect(null, "이메일을 입력해 주세요.");
//            return null;
//        }
//        if(!regex.isEmail(email)) {
//            sqlSession.close();
//            web.redirect(null, "이메일 주소의 형식이 잘못되었습니다.");
//            return null;
//        }
//        
//        // 이메일 인증번호 검사
//        if(!regex.isValue(certNum)) {
//            sqlSession.close();
//            web.redirect(null, "이메일 인증번호를 입력해 주세요.");
//            return null;
//        }
//        
//        // member_userPw_search 넘어온 파라미터를 검사한다.
//        if(userId != null) {
//            // 아이디 검사
//            if(!regex.isValue(userId)) {
//                sqlSession.close();
//                web.redirect(null, "아이디를 입력하세요.");
//                return null;
//            }
//            if(!regex.isEngNum(userId)) {
//                sqlSession.close();
//                web.redirect(null, "아이디는 영어와 숫자 조합만 입력 가능합니다.");
//                return null;
//            }
//            if(!regex.isMinLength(userId, 4)) {
//                sqlSession.close();
//                web.redirect(null, "아이디는 최소 4자 이상 입력하셔야 합니다.");
//                return null;
//            }
//            if(!regex.isMaxLength(userId, 20)) {
//                sqlSession.close();
//                web.redirect(null, "아이디는 최대 20자 까지만 입력 가능합니다.");
//                return null;
//            }
//        }
//        
//        /** (6) 전달받은 파라미터를 Beans에 설정한다. */
//        Member member = new Member();
//        member.setUserName(userName);
//        member.setGender(gender);
//        // 생년월일을 위해 문자 조합을 한다.
//        /** mm, dd 형식에 맞게끔 작업 */
//        if(Integer.parseInt(month) < 10) {
//            month = "0" + month;
//        }
//        if(Integer.parseInt(day) < 10) {
//            day = "0" + day;
//        }
//        String birthDate = year + "-" + month + "-" + day;
//        member.setBirthDate(birthDate);
//        member.setEmail(email);
//        // 비밀번호 찾기일 경우에만 넣게 될 userId setter
//        if(userId != null) {
//            member.setUserId(userId);
//        }
//        
//        /**
//         * ===== Service 계층의 기능을 통한 사용자 인증 =====
//         * - 파라미터가 저장된 Beans를 Service에게 전달한다.
//         * - Service는 MyBatis의 Mapper를 호출하고 조회 결과를 리턴할 것이다.
//         */
//        /** (7) Service를 통한 검사 */
//        Member userIdSearch = new Member();
//        Member userPwSearch = new Member();
//        if(!regex.isIndexCheck(referer, "member_join.do")) {
//            try {
//                memberService.memberCheck(member);
//            } catch (Exception e) {
//                web.redirect(null, e.getLocalizedMessage());
//                return null;
//            } finally {
//                sqlSession.close();
//            }
//        } else if(!regex.isIndexCheck(referer, "member_userId_search.do")) {
//            try {
//                userIdSearch = memberService.userIdSearch(member);
//                logger.debug("userIdSearch.toString = " + userIdSearch.toString());
//            } catch (Exception e) {
//                web.redirect(null, e.getLocalizedMessage());
//                return null;
//            } finally {
//                sqlSession.close();
//            }
//            web.setSession("memberId", userIdSearch.getMemberId());
//            web.setSession("userId", userIdSearch.getUserId());
//            web.setSession("regDate", userIdSearch.getRegDate());
//        } else if(!regex.isIndexCheck(referer, "member_userPw_search.do")) {
//            try {
//                userPwSearch = memberService.userPwSearch(member);
//                logger.debug("userPwSearch.toString = " + userPwSearch.toString());
//                
//            } catch (Exception e) {
//                web.redirect(null, e.getLocalizedMessage());
//                return null;
//            } finally {
//                sqlSession.close();
//            }
//            
//            web.setSession("memberId", userPwSearch.getMemberId());
//            web.setSession("userId", userPwSearch.getUserId());
//        }
//        // 조회된 정보가 없을 경우
//        if(userIdSearch == null || userPwSearch == null) {
//            web.redirect(null, "일치하는 회원정보가 없습니다.");
//            return null;
//        }
//        
//        /** ===== MemberJoinAgreement 또는 MemberUserIdSearchSuccess과 MemberUserPwUpdate 컨트롤러에서 사용하기 위한 세션 저장 ===== */
//        web.setSession("cert_ok", cert_ok);
//        web.setSession("userName", userName);
//        web.setSession("gender", gender);
//        web.setSession("year", year);
//        web.setSession("month", month);
//        web.setSession("day", day);
//        web.setSession("email", email);
//        web.setSession("certNum", certNum);
//        
//        if(!regex.isIndexCheck(referer, "member_join.do")) {
//            web.redirect(web.getRootPath() + "/member/member_join_agreement.do", null);
//        } else if(!regex.isIndexCheck(referer, "member_userId_search.do")) {
//            web.redirect(web.getRootPath() + "/member/member_userId_search_success.do", null);
//        } else if(!regex.isIndexCheck(referer, "member_userPw_search.do")) {
//            web.redirect(web.getRootPath() + "/member/member_userPw_update.do", null);
//        }
//        
//        /**
//         * Insert, Upadte, Delete 처리를 수행하는 action 페이지들은
//         * 자체적으로 View를 갖지 않고 결과를 확인할 수 있는
//         * 다른 페이지로 강제 이동시켜야 한다. (중복실행 방지)
//         * 그러므로 View의 경로를 리턴하지 않는다.
//         */
//        return null;
//    }
//}
