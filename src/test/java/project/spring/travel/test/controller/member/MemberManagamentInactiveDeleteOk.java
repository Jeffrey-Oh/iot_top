package project.spring.travel.test.controller.member;
//package project.spring.travel.controller.member;
//
//import java.io.IOException;
//import java.util.Calendar;
//import java.util.List;
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
//import project.spring.helper.MailHelper;
//import project.spring.helper.RegexHelper;
//import project.spring.helper.UploadHelper;
//import project.spring.helper.WebHelper;
//
///**
// * @fileName    : MemberManagamentInactiveDeleteOk.java
// * @author      : 홍준성
// * @description : 휴면계정 회원 삭제를 처리하기 위한 액션 컨트롤러
// * @lastUpdate  : 2019. 4. 10.
// */
//@WebServlet("/member/member_management_inactive_delete_ok.do")
//public class MemberManagamentInactiveDeleteOk extends BaseController {
//    private static final long serialVersionUID = 607778259631707983L;
//    
//    /** (1) 사용하고자 하는 Helper + Service 객체 선언 */
//    // Logger logger 는 BaseController에서 이미 객체 선언이 되어 있음으로 여기서는 또 선언할 필요가 없다.
//    // -> import org.apache.ibatis.session.SqlSession;
//    SqlSession sqlSession;
//    // -> import project.spring.helper.WebHelper
//    WebHelper web;
//    // -> import project.spring.helper.MailHelper
//    MailHelper mail;
//    RegexHelper regex;
//    Calendar cal1;
//    Calendar cal2;
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
//        mail = MailHelper.getInstance();
//        regex = RegexHelper.getInstance();
//        cal1 = Calendar.getInstance();
//        cal2 = Calendar.getInstance();
//        upload = UploadHelper.getInstance();
//        // 게시물에 대한 참조관계 해제를 위한 Service객체
//        // -> import project.java.travel.service.impl.BoardServiceImpl;
//        boardService = new BoardServiceImpl(logger, sqlSession);
//        // 덧글에 대한 참조관계 해제를 위한 Service객체
//        // -> import project.java.travel.service.impl.ReplyServiceImpl;
//        replyService = new ReplyServiceImpl(logger, sqlSession);
//        // 좋아요에 대한 참조관계 해제를 위한 Service객체
//        // -> import project.java.travel.service.impl.BoardLikeServiceImpl;
//        boardLikeService = new BoardLikeServiceImpl(logger, sqlSession);
//        // 게시물 덧글의 좋아요에 대한 참조관계 해제를 위한 Service객체
//        // -> import project.java.travel.service.impl.ReplyLikeServiceImpl;
//        replyLikeService = new ReplyLikeServiceImpl(logger, sqlSession);
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
//            response.sendRedirect(web.getRootPath() + "/error/error_page.do");
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
//            web.printJsonRt("X-LOGIN");
//            return null;
//        } else if(!loginInfo.getGrade().equals("Master")) {
//            sqlSession.close();
//            web.printJsonRt("X-MASTER");
//            return null;
//        }
//        
//        /** (4) Service를 통한 SQL 수행 */
//        // 조회 결과를 저장하기 위한 객체
//        List<Member> list = null;
//        try {
//            list = memberService.selectMemberList(null);
//        } catch (Exception e) {
//            // 예외 발생 시, Service Layer에서 전달하는 메시지 내용을
//            // 사용자에게 제시하고 이전페이지로 이동한다.
//            sqlSession.close();
//            web.printJsonRt(e.getLocalizedMessage());
//            return null;
//        }
//        
//        for(int i = 0; i < list.size(); i++) {
//            Member member = list.get(i);
//            if(member.getDeleteDate() != null) {
//                // 회원정보에서 삭제예정일을 추출
//                String deleteDate = member.getDeleteDate();
//                // TimeStamp에서 사용할 년월일 값으로 추출
//                int year = Integer.parseInt(deleteDate.substring(0, 4));
//                int month = Integer.parseInt(deleteDate.substring(5, 7));
//                int day = Integer.parseInt(deleteDate.substring(8, 10));
//                cal2.set(year, month - 1, day);
//                // 삭제 예정일과 현재 시각에 대한 TimeStamp를 밀리세컨드 단위로 얻기
//                long delMs = cal2.getTimeInMillis();
//                long nowMs = cal1.getTimeInMillis();
//                long resultMs = delMs - nowMs;
//                // 일 단위로 변환
//                int date = (int) Math.floor(resultMs / (24 * 60 * 60 * 1000));
//                logger.debug("date = " + date);
//                // 삭제예정일까지 한달 이내라면?
//                if(date <= 30) {
//                    // 삭제 대상일 경우
//                    if(date <= 0) {
//                        logger.debug("삭제 완료 member.toString >> " + member.toString());
//                        // 게시물과의 참조 관계 해제를 위한 조건값 설정
//                        BoardList boardList = new BoardList();
//                        boardList.setMemberId(loginInfo.getMemberId());
//                        // 게시물 덧글과의 참조 관계 해제를 위한 조건값 설정
//                        BoardReply boardReply = new BoardReply();
//                        boardReply.setMemberId(loginInfo.getMemberId());
//                        // 게시물 좋아요의 참조 관계 해제를 위한 조건값 설정
//                        BoardLike boardLike = new BoardLike();
//                        boardLike.setMemberId(loginInfo.getMemberId());
//                        // 게시물 덧글의 좋아요 참조 관계 해제를 위한 조건값 설정
//                        BoardReplyLike boardReplyLike = new BoardReplyLike();
//                        boardReplyLike.setMemberId(loginInfo.getMemberId());
//                        
//                        /** 삭제예정에 해당하는 회원을 삭제처리 */
//                        try {
//                            /**
//                             * ===== 회원 삭제의 경우 발생되는 문제점 인식 =====
//                             * - 작성한 게시물이나 덧글이 존재하는 회원의 경우
//                             *     삭제가 불가능한 문제점 발생
//                             * - 참조키 제약조건 때문에 발생되는 문제
//                             * - 회원 삭제에 따라 게시물이 모두 삭제된다면
//                             *     사이트 운영상 컨텐츠 수집에 지장을 초래한다.
//                             *     - 이 경우 회원을 삭제하더라도 게시물은 남겨놓는 것이
//                             *         일반적인 처리 방법인데,
//                             *         이를 위해 회원 일련번호를 참조하는 컬럼을 Null로 변경해야 한다.
//                             *         - 참조키 컬럼에서 제약조건에 위배되지 않는 유일한 경우는
//                             *             Null값인 상태 뿐이다.
//                             *         - 게시물 테이블과 덧글 테이블은 이러한 상황을 대비하여
//                             *             회원 일련번호 필드에 Null을 허용해 두었다.
//                             */
//                            // 참조관계 해제
//                            boardService.updateDocumentMemberOut(boardList);
//                            replyService.updateCommentMemberOut(boardReply);
//                            boardLikeService.updateBoardLikeMemberOut(boardLike);
//                            replyLikeService.updateBoardReplyLikeMemberOut(boardReplyLike);
//                            
//                            // 회원 삭제 성공 시 진행할 값을 미리 저장
//                            String removeFilePath = member.getProfileImg();
//                            
//                            // 회원 삭제
//                            memberService.inactiveMemberDelete(null);
//                            
//                            /**
//                             * - 회원 프로필 이미지의 경로를 UploadHelper.removeFile() 메서드에게 전달한다.
//                             * - 전달된 경로를 분석하여 원본 이미지와 썸네일 이미지를 일괄 삭제한다.
//                             */
//                            // 탈퇴되었다면 프로필 이미지를 삭제한다.
//                            upload.removeFile(removeFilePath);
//                        } catch (Exception e) {
//                            sqlSession.close();
//                            web.printJsonRt(e.getLocalizedMessage());
//                            return null;
//                        } // End try~catch
//                    } else {
//                        logger.debug("삭제 예정 member.toString >> " + member.toString());
//                        /** 발급된 인증번호를 메일로 발송하기 */
//                        String sender = "ghdwnstjd13@gmail.com";
//                        String email = member.getEmail();
//                        String subject = member.getUserName() + "님! 완벽한 여행 휴면계정에 대한 안내문이 도착했습니다.";
//                        String content = 
//                                "<span style='font-size: 20px;'>" + member.getUserName() + 
//                                "님! 오랜기간 접속을 안하셨군요... 아쉽네요. 휴면계정에 대한 삭제 예정까지 남은 시간은 <strong>" + date + 
//                                "일</strong> 입니다. 사이트에 방문하셔서 <strong>로그인 성공 시</strong> 휴면계정에서 벗어나 삭제가 이루어지지 않습니다.</span>";
//                        
//                        /** 메일 발송 처리 */
//                        try {
//                            // sendMail() 메서드 선언시 throws를 정의했기 때문에 예외처리가 요구된다.
//                            mail.sendMail(sender, email, subject, content);
//                        } catch(MessagingException e) {
//                            sqlSession.close();
//                            logger.debug(e.getLocalizedMessage());
//                            web.printJsonRt("메일 발송에 실패했습니다. 관리자에게 문의 바랍니다.");
//                            return null;
//                        } catch(Exception e) {
//                            sqlSession.close();
//                            logger.debug(e.getLocalizedMessage());
//                            web.printJsonRt("알 수 없는 이유로 메일 발송에 실패했습니다.");
//                            return null;
//                        }
//                    } // End if~else
//                } // End if
//            } // End if
//        } // End for
//        
//        
//        sqlSession.close();
//        
//        /** 처리 결과를 JSON으로 출력하기 */
//        web.printJsonRt("OK");
//        
//        return null;
//    }
//
//}
