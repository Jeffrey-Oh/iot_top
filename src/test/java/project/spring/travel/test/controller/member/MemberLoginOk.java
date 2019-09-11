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
//import project.java.travel.model.BoardLike;
//import project.java.travel.model.BoardList;
//import project.java.travel.model.BoardReply;
//import project.java.travel.model.BoardReplyLike;
//import project.java.travel.model.Member;
//import project.java.travel.service.BoardLikeService;
//import project.java.travel.service.ReplyLikeService;
//import project.java.travel.service.BoardService;
//import project.java.travel.service.MemberService;
//import project.java.travel.service.ReplyService;
//import project.java.travel.service.impl.BoardLikeServiceImpl;
//import project.java.travel.service.impl.ReplyLikeServiceImpl;
//import project.java.travel.service.impl.BoardServiceImpl;
//import project.java.travel.service.impl.MemberServiceImpl;
//import project.java.travel.service.impl.ReplyServiceImpl;
//import project.spring.helper.BaseController;
//import project.spring.helper.RegexHelper;
//import project.spring.helper.UploadHelper;
//import project.spring.helper.WebHelper;
//
///**
// * @fileName    : MemberLoginOk.java
// * @author      : 홍준성
// * @description : 로그인 처리 과정을 위한 액션 컨트롤러
// * @lastDate    : 2019. 3. 31.
// */
//@WebServlet("/member/member_login_ok.do")
//public class MemberLoginOk extends BaseController {
//    private static final long serialVersionUID = -4495360630380160456L;
//    
//    /**
//     * ===== 로그인 중에는 접근할 수 없는 페이지 =====
//     * - 로그인 페이지는 로그인 상태에서는 접근할 수 없도록 처리해야 하기 때문에
//     *     세션이 존재한다면 (null이 아니라면) 접근할 수 없도록 차단한다.
//     */
//    /** (1) 사용하고자 하는 Helper + Service 객체 선언 */
//    // Logger logger 는 BaseController에서 이미 객체 선언이 되어 있음으로 여기서는 또 선언할 필요가 없다.
//    // -> import org.apache.ibatis.session.SqlSession;
//    SqlSession sqlSession;
//    // -> import project.spring.helper.WebHelper
//    WebHelper web;
//    // -> import project.spring.helper.RegexHelper
//    RegexHelper regex;
//    // -> import project.spring.helper.UploadHelper
//    UploadHelper upload;
//    // -> import project.java.travel.service.BoardService;
//    BoardService boardService;
//    // -> import project.java.travel.service.ReplyService;
//    ReplyService replyService;
//    // -> import project.java.travel.service.BoardLikeService;
//    BoardLikeService boardLikeService;
//    // -> import project.java.travel.service.ReplyLikeService;
//    ReplyLikeService replyLikeService;
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
//        upload = UploadHelper.getInstance();
//        // 게시물에 대한 Count로 등급 적용을 위한 Service객체
//        // -> import project.java.travel.service.impl.BoardServiceImpl;
//        boardService = new BoardServiceImpl(logger, sqlSession);
//        // 게시물 덧글에 대한 Count로 등급 적용을 위한 Service객체
//        // -> import project.java.travel.service.impl.ReplyServiceImpl;
//        replyService = new ReplyServiceImpl(logger, sqlSession);
//        // 게시물에 좋아요에 대한 Count로 등급 적용을 위한 Service객체
//        // -> import project.java.travel.service.impl.BoardLikeServiceImpl;
//        boardLikeService = new BoardLikeServiceImpl(logger, sqlSession);
//        // 게시물 덧글의 좋아요에 대한 Count로 등급 적용을 위한 Service객체
//        // -> import project.java.travel.service.impl.ReplyLikeServiceImpl;
//        replyLikeService = new ReplyLikeServiceImpl(logger, sqlSession);
//        // 로그인 처리를 위한 Service객체
//        // -> import project.spring.travel.service.impl.MemberServiceImpl
//        memberService = new MemberServiceImpl(sqlSession, logger);
//        
//        /** ===== 악의적인 접근에 대한 방어 코드 */
//        String referer = request.getHeader("referer");
//        if(referer == null) {
//            // 정상적인 접근 방법이 아닐 경우
//            sqlSession.close();
//            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
//            web.redirect(web.getRootPath() + "/error/error_page.do", null);
//            return null;
//        } else {
//            if(regex.isIndexCheck(referer, "member_login.do")) {
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
//        if(web.getSession("loginInfo") != null) {
//            sqlSession.close();
//            web.redirect(web.getRootPath() + "/index.do", "이미 로그인 하셨습니다.");
//            return null;
//        }
//        
//        /** (4) 사용자 입력값 받기 */
//        String userId = web.getString("userId");
//        String userPw = web.getString("userPw");
//        String remember_userId = web.getString("remember_userId");
//        
//        // 사용자 입력값 기록
//        logger.debug("userId = " + userId);
//        logger.debug("userPw = " + userPw);
//        logger.debug("remember_userId = " + remember_userId);
//        
//        /** (5) 필수 값의 존재여부 검사 */
//        // 아이디, 비밀번호 검사
//        if(!regex.isValue(userId) || !regex.isValue(userPw)) {
//            sqlSession.close();
//            web.redirect(null, "아이디나 비밀번호가 없습니다.");
//            return null;
//        }
//        
//        /** (6) 전달받은 파라미터를 Beans에 설정한다. */
//        Member member = new Member();
//        member.setUserId(userId);
//        member.setUserPw(userPw);
//        
//        /**
//         * ===== Service 계층의 기능을 통한 사용자 인증 =====
//         * - 파라미터가 저장된 Beans를 Service에게 전달한다.
//         * - Service는 MyBatis의 Mapper를 호출하고 조회 결과를 리턴할 것이다.
//         */
//        /** (7) Service를 통한 회원 인증 */
//        Member loginInfo = null;
//        
//        try {
//            loginInfo = memberService.memberSearch(member);
//            
//            // 휴면계정일 경우 활성화 처리
//            if(loginInfo != null) {
//                memberService.activeMember(loginInfo);
//            }
//            
//            // 관리자 계정이 아닐 경우에만 등급 조정
//            if(!loginInfo.getGrade().equals("Master")) {
//                /** ===== 회원등급을 적용하기 위한 처리 ===== */
//                // 게시물과의 Count로 등급 적용을 위한 조건값 설정
//                BoardList boardList = new BoardList();
//                boardList.setMemberId(loginInfo.getMemberId());
//                // 게시물 덧글과의 Count로 등급 적용을 위한 조건값 설정
//                BoardReply boardReply = new BoardReply();
//                boardReply.setMemberId(loginInfo.getMemberId());
//                // 게시물 좋아요의 Count로 등급 적용을 위한 조건값 설정
//                BoardLike boardLike = new BoardLike();
//                boardLike.setMemberId(loginInfo.getMemberId());
//                // 게시물 덧글의 좋아요 Count로 등급 적용을 위한 조건값 설정
//                BoardReplyLike boardReplyLike = new BoardReplyLike();
//                boardReplyLike.setMemberId(loginInfo.getMemberId());
//                
//                // Count
//                int b_count = boardService.countByMemberId(boardList) * 10;
//                logger.debug("사용자 게시물 등록 점수 = " + b_count);
//                int r_count = replyService.countByMemberId(boardReply) * 8;
//                logger.debug("사용자 게시물 덧글 등록 점수 = " + r_count);
//                int bl_count = boardLikeService.countByMemberId(boardLike) * 6;
//                logger.debug("사용자 게시물 좋아요 등록 점수 = " + bl_count);
//                int brl_count = replyLikeService.countByMemberId(boardReplyLike) * 4;
//                logger.debug("사용자 게시물 덧글 좋아요 등록 점수 = " + brl_count);
//                int blu_count = boardLikeService.countByUser(boardLike) * 2;
//                logger.debug("타인에게서 게시물 좋아요 받은 점수 = " + blu_count);
//                int brlu_count = replyLikeService.countByUser(boardReplyLike) * 1;
//                logger.debug("타인에게서 게시물 덧글 좋아요 받은 점수 = " + brlu_count);
//                // 경험치 적용
//                int grade_exp = b_count + r_count + bl_count + brl_count + blu_count + brlu_count;
//                logger.debug("grade_exp = " + grade_exp);
//                // 등급 계산
//                String grade = "lv1"; // 토트백
//                if(grade_exp >= 50 && grade_exp < 100) {
//                    grade = "lv2"; // 쇼퍼백
//                } else if(grade_exp >= 100 && grade_exp < 150) {
//                    grade = "lv3"; // 소형백팩
//                } else if(grade_exp >= 150 && grade_exp < 200) {
//                    grade = "lv4"; // 비즈니스백
//                } else if(grade_exp >= 200 && grade_exp < 250) {
//                    grade = "lv5"; // 대형배낭여행백
//                } else if(grade_exp >= 250) {
//                    grade = "lv6"; // 캐리어
//                }
//                // 갱신할 등급을 Beans에 담는다.
//                Member updateGrade = new Member();
//                updateGrade.setGrade(grade);
//                updateGrade.setMemberId(loginInfo.getMemberId());
//                // 등급 적용
//                memberService.updateMemberGrade(updateGrade);
//                // 수정된 정보로 다시 조회
//                loginInfo = memberService.memberSearch(member);
//            } // End if
//        } catch (Exception e) {
//            sqlSession.close();
//            web.redirect(null, e.getLocalizedMessage());
//            return null;
//        }
//        
//        
//        /**
//         * ===== 조회된 회원정보 객체에 프로필 이미지의 경로가 있는 경우 =====
//         * - 원본 프로필 이미지를 썸네일로 구성한다. 생성된 썸네일의 경로는
//         *     사이트 내의 모든 경로에서 접근할 수 있도록 쿠키에 저장한다.
//         * - 리턴된 회원 정보는 사이트 내의 모든 페이지에서 접근할 수 있도록
//         *     세션에 저장한다.
//         */
//        /** (8) 프로필 이미지 처리 */
//        // 프로필 이미지가 있을 경우 썸네일을 생성하여 쿠키에 별도로 저장
//        String profileImg = loginInfo.getProfileImg();
//        if(profileImg != null) {
//            try {
//                String profileThumbnail = upload.createThumbnail(profileImg, 24, 24, true);
//                web.setCookie("profileThumbnail", profileThumbnail, -1);
//            } catch (Exception e) {
//                sqlSession.close();
//                web.redirect(null, e.getLocalizedMessage());
//                return null;
//            }
//        }
//        
//        /** (9) 조회된 회원 정보를 세션에 저장 */
//        /**
//         * 로그인 처리된 아이디와 비밀번호를 기반으로 조회된 정보를
//         * 세션에 보관하는 과정을 말한다.
//         * 로그인에 대한 판별은 저장된 세션정보의 존재 여부를 판별한다.
//         */
//        web.setSession("loginInfo", loginInfo);
//        /**
//         * ### 썸네일 정보를 세션에 보관하는 것도 가능하지만,
//         *     모든 기능을 활용하는 것이 더 적합하다고 생각되어
//         *     쿠키를 활용하였다. (서버에 부담이 가지 않도록 한다.)
//         */
//        
//        /** 기억하기가 체크된 경우 아이디만 쿠키로 생성 */
//        // -> 30일
//        if(remember_userId != null) {
//            web.setCookie("remember_userId", userId, 60 * 60 * 24 * 30);
//        } else {
//            web.removeCookie("remember_userId");
//        }
//        
//        /**
//         * ===== 로그인 완료 후 페이지 이동 =====
//         * String movePage : 이전 페이지로의 URL을 조회한다.
//         * if : 이전 페이지의 URL이 존재하지 않는 경우 사이트의 첫 페이지로 강제 설정한다.
//         * sqlSession.close() : 페이지 이동 직전에 데이터베이스와의 연결을 해제한다.
//         */
//        /** (10) 페이지 이동 */
//        // 이전 페이지 구하기 (Javascript로 이동된 경우 조회 안됨)
//        String movePage = web.getCookie("movePage");
//        if(movePage == null) {
//            movePage = web.getRootPath() + "/index.do";
//        }
//        
//        sqlSession.close();
//        web.redirect(movePage, null);
//        
//        return null;
//    }
//
//}
