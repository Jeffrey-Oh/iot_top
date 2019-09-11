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
//import project.java.travel.model.MemberOutReason;
//import project.java.travel.service.BoardLikeService;
//import project.java.travel.service.ReplyLikeService;
//import project.java.travel.service.BoardService;
//import project.java.travel.service.MemberOutReasonService;
//import project.java.travel.service.MemberService;
//import project.java.travel.service.ReplyService;
//import project.java.travel.service.impl.BoardLikeServiceImpl;
//import project.java.travel.service.impl.ReplyLikeServiceImpl;
//import project.java.travel.service.impl.BoardServiceImpl;
//import project.java.travel.service.impl.MemberOutReasonServiceImpl;
//import project.java.travel.service.impl.MemberServiceImpl;
//import project.java.travel.service.impl.ReplyServiceImpl;
//import project.spring.helper.BaseController;
//import project.spring.helper.RegexHelper;
//import project.spring.helper.UploadHelper;
//import project.spring.helper.WebHelper;
//
///**
// * @fileName    : MemberOutOk.java
// * @author      : 홍준성
// * @description : 회원 탈퇴를 처리하기 위한 액션 컨트롤러
// * @lastUpdate  : 2019. 4. 9.
// */
//@WebServlet("/member/member_out_ok.do")
//public class MemberOutOk extends BaseController {
//    private static final long serialVersionUID = -5038556976692522259L;
//    
//    /** 사용하고자 하는 Helper + Service 객체 선언 */
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
//    // -> import project.java.travel.service.MemberOutReasonService;
//    MemberOutReasonService memberOutReasonService;
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
//        upload = UploadHelper.getInstance();
//        // 게시물에 대한 참조관계 해제를 위한 Service객체
//        // -> import project.java.travel.service.impl.BoardServiceImpl;
//        boardService = new BoardServiceImpl(logger, sqlSession);
//        // 게시물 덧글에 대한 참조관계 해제를 위한 Service객체
//        // -> import project.java.travel.service.impl.ReplyServiceImpl;
//        replyService = new ReplyServiceImpl(logger, sqlSession);
//        // 게시물에 좋아요에 대한 참조관계 해제를 위한 Service객체
//        // -> import project.java.travel.service.impl.BoardLikeServiceImpl;
//        boardLikeService = new BoardLikeServiceImpl(logger, sqlSession);
//        // 게시물 덧글의 좋아요에 대한 참조관계 해제를 위한 Service객체
//        // -> import project.java.travel.service.impl.ReplyLikeServiceImpl;
//        replyLikeService = new ReplyLikeServiceImpl(logger, sqlSession);
//        // 회원탈퇴 처리를 위한 Service객체
//        // -> import study.jsp.mysite.service.impl.MemberServiceImpl
//        memberService = new MemberServiceImpl(sqlSession, logger);
//        // 회원 탈퇴 사유를 저장하기 위한 Service객체
//        // -> import project.java.travel.service.impl.MemberOutReasonServiceImpl;
//        memberOutReasonService = new MemberOutReasonServiceImpl(sqlSession, logger);
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
//            if(regex.isIndexCheck(referer, "mypage_delete_member_reason.do")) {
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
//        /** (4) 파라미터 받기 */
//        /** member_edit_door에서 전달한 세션 받기 */
//        String edit_door_userPw = (String) web.getSession("edit_door_userPw");
//        // 로그에 기록
//        logger.debug("edit_door_userPw = " + edit_door_userPw);
//        // 사용한 세션 삭제
//        web.removeSession("edit_door_userPw");
//        
//        // mypage_delete_member_reason에서 전달된 파라미터 받기
//        String[] reason = web.getStringArray("reason");
//        String reasonEtc = web.getString("reasonEtc");
//        // 전달받은 값 로그에 기록
//        logger.debug("reason = " + reason);
//        logger.debug("reasonEtc = " + reasonEtc);
//        
//        /** 유효성 검사 */
//        // 비밀번호 검사
//        if(!regex.isValue(edit_door_userPw)) {
//            sqlSession.close();
//            web.redirect(null, "비밀번호가 잘못되었습니다.");
//            return null;
//        }
//        // 체크박스 검사
//        if(!regex.isCheck(reason)) {
//            sqlSession.close();
//            web.redirect(null, "탈퇴 사유를 선택하셔야 합니다.");
//            return null;
//        }
//        
//        // 배열을 Beans에 담을 문자열로 변환 
//        String reasonStr = "";
//        for(int i = 0; i < reason.length; i++) {
//            // 기타 사유일 경우
//            if(reason[i].equals("기타")) {
//                if(!regex.isValue(reasonEtc)) {
//                    sqlSession.close();
//                    web.redirect(null, "기타를 선택하신 경우 내용을 적어주셔야 합니다.");
//                    return null;
//                }
//            }
//            
//            reasonStr += reason[i];
//            if(i + 1 < reason.length) {
//                reasonStr += ",";
//            }
//        }
//        
//        /** (5) 서비스에 전달하기 위하여 파라미터를 Beans로 묶는다. */
//        // 게시물과의 참조 관계 해제를 위한 조건값 설정
//        BoardList boardList = new BoardList();
//        boardList.setMemberId(loginInfo.getMemberId());
//        // 게시물 덧글과의 참조 관계 해제를 위한 조건값 설정
//        BoardReply boardReply = new BoardReply();
//        boardReply.setMemberId(loginInfo.getMemberId());
//        // 게시물 좋아요의 참조 관계 해제를 위한 조건값 설정
//        BoardLike boardLike = new BoardLike();
//        boardLike.setMemberId(loginInfo.getMemberId());
//        // 게시물 덧글의 좋아요 참조 관계 해제를 위한 조건값 설정
//        BoardReplyLike boardReplyLike = new BoardReplyLike();
//        boardReplyLike.setMemberId(loginInfo.getMemberId());
//        
//        // 회원정보를 담은 Beans
//        Member member = new Member();
//        member.setMemberId(loginInfo.getMemberId()); // 회원번호는 세션을 통해서 획득한 로그인 정보에서 취득
//        member.setUserPw(edit_door_userPw); // 입력한 비밀번호
//        
//        // 탈퇴사유를 담은 Beans
//        MemberOutReason memberOutReason = new MemberOutReason();
//        memberOutReason.setReason(reasonStr);
//        memberOutReason.setReasonEtc(reasonEtc);
//        
//        /** (6) Service를 통한 탈퇴 시도 */
//        try {
//            /**
//             * ===== 회원 탈퇴의 경우 발생되는 문제점 인식 =====
//             * - 작성한 게시물이나 덧글이 존재하는 회원의 경우
//             *     탈퇴가 불가능한 문제점 발생
//             * - 참조키 제약조건 때문에 발생되는 문제
//             * - 회원 탈퇴에 따라 게시물이 모두 삭제된다면
//             *     사이트 운영상 컨텐츠 수집에 지장을 초래한다.
//             *     - 이 경우 회원이 탈퇴하더라도 게시물은 남겨놓는 것이
//             *         일반적인 처리 방법인데,
//             *         이를 위해 회원 일련번호를 참조하는 컬럼을 Null로 변경해야 한다.
//             *         - 참조키 컬럼에서 제약조건에 위배되지 않는 유일한 경우는
//             *             Null값인 상태 뿐이다.
//             *         - 게시물 테이블과 덧글 테이블은 이러한 상황을 대비하여
//             *             회원 일련번호 필드에 Null을 허용해 두었다.
//             */
//            // 참조관계 해제
//            boardService.updateDocumentMemberOut(boardList);
//            replyService.updateCommentMemberOut(boardReply);
//            boardLikeService.updateBoardLikeMemberOut(boardLike);
//            replyLikeService.updateBoardReplyLikeMemberOut(boardReplyLike);
//            
//            // 비밀번호 검사 -> 비밀번호가 잘못된 경우 예외발생
//            memberService.selectMemberPasswordCount(member);
//            // 탈퇴처리
//            memberService.deleteMember(member);
//            // 탈퇴 사유를 저장
//            memberOutReasonService.insertMemberOutReason(memberOutReason);
//        } catch (Exception e) {
//            web.redirect(null, e.getLocalizedMessage());
//            return null;
//        } finally {
//            sqlSession.close();
//        }
//        /**
//         * - 회원 프로필 이미지의 경로를 UploadHelper.removeFile() 메서드에게 전달한다.
//         * - 전달된 경로를 분석하여 원본 이미지와 썸네일 이미지를 일괄 삭제한다.
//         */
//        // 탈퇴되었다면 프로필 이미지를 삭제한다.
//        upload.removeFile(loginInfo.getProfileImg());
//        
//        /** (7) 정상적으로 탈퇴된 경우 강제 로그아웃 및 페이지 이동 */
//        web.removeAllSession();
//        web.redirect(web.getRootPath() + "/index.do", "탈퇴되었습니다.");
//        
//        return null;
//    }
//
//}
