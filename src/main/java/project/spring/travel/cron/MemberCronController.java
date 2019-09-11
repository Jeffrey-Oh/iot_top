//package project.spring.travel.cron;
//
//import java.util.Calendar;
//import java.util.List;
//
//import javax.mail.MessagingException;
//
//import org.apache.ibatis.session.SqlSession;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Controller;
//
//import project.spring.helper.MailHelper;
//import project.spring.helper.UploadHelper;
//import project.spring.travel.model.BoardList;
//import project.spring.travel.model.BoardReply;
//import project.spring.travel.model.BoardReplyLike;
//import project.spring.travel.model.BoardReplyReply;
//import project.spring.travel.model.BoardReplyReplyLike;
//import project.spring.travel.model.CategoryLike;
//import project.spring.travel.model.Favorite;
//import project.spring.travel.model.Member;
//import project.spring.travel.service.BoardService;
//import project.spring.travel.service.CategoryLikeService;
//import project.spring.travel.service.FavoriteService;
//import project.spring.travel.service.MemberService;
//import project.spring.travel.service.ReplyLikeService;
//import project.spring.travel.service.ReplyService;
//
///**
// * @fileName    : MemberCronController.java
// * @author      : 홍준성
// * @description : 회원 관리 cron
// * @lastUpdate  : 2019. 5. 10.
// */
///** 컨트롤러 선언 */
//@Controller
//public class MemberCronController {
//    /** cron 설정 시간 */
//    // 휴면계정 정보 삭제를 위한 액션 컨트롤러 (cron="0 44 04 * * ?")                 매일 04시 44분 */
//    
//    /** (1) 사용하고자 하는 Helper + Service 객체 선언 */
//    // -> import org.slf4j.Logger;
//    // -> import org.slf4j.LoggerFactory;
//    Logger logger = LoggerFactory.getLogger(MemberCronController.class); // Log4j 객체 직접 생성
//    // -> import org.apache.ibatis.session.SqlSession;
//    @Autowired
//    SqlSession sqlSession;
//    // -> import project.spring.helper.MailHelper
//    @Autowired
//    MailHelper mail;
//    // -> import java.util.Calendar;
//    Calendar cal1 = Calendar.getInstance();
//    Calendar cal2 = Calendar.getInstance();
//    // -> import project.spring.helper.UploadHelper
//    @Autowired
//    UploadHelper upload;
//    // -> import project.java.travel.service.BoardService;
//    @Autowired
//    BoardService boardService;
//    // -> import project.java.travel.service.ReplyService;
//    @Autowired
//    ReplyService replyService;
//    // -> import project.java.travel.service.CategoryLikeService;
//    @Autowired
//    CategoryLikeService categoryLikeService;
//    // -> import project.java.travel.service.ReplyLikeService;
//    @Autowired
//    ReplyLikeService replyLikeService;
//    // -> import project.java.travel.service.MemberService
//    @Autowired
//    MemberService memberService;
//    // -> import project.spring.travel.service.FavoriteService;
//    @Autowired
//    FavoriteService favoriteService;
//    
//    /**
//     * 휴면계정 회원 삭제를 처리하기 위한 cron
//     */
//    @Scheduled(cron="0 44 04 * * ?")
//    public void memberManagamentInactiveDeleteOkCron() {
//        /** Service를 통한 SQL 수행 */
//        // 조회 결과를 저장하기 위한 객체
//        List<Member> list = null;
//        try {
//            list = memberService.selectMemberList(null);
//        } catch (Exception e) {
//            logger.debug(e.getLocalizedMessage());
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
//                        boardList.setMemberId(member.getMemberId());
//                        // 게시물 덧글과의 참조 관계 해제를 위한 조건값 설정
//                        BoardReply boardReply = new BoardReply();
//                        boardReply.setMemberId(member.getMemberId());
//                        // 댓글에 등급을 유지시켜주기 위한 값
//                        boardReply.setGrade(member.getGrade());
//                        // 게시물 좋아요의 참조 관계 해제를 위한 조건값 설정
//                        CategoryLike categoryLike = new CategoryLike();
//                        categoryLike.setMemberId(member.getMemberId());
//                        // 게시물 덧글의 좋아요 참조 관계 해제를 위한 조건값 설정
//                        BoardReplyLike boardReplyLike = new BoardReplyLike();
//                        boardReplyLike.setMemberId(member.getMemberId());
//                        // 게시물 대댓글의 참조 관계 해를 위한 조건값 설정
//                        BoardReplyReply boardReplyReply = new BoardReplyReply();
//                        boardReplyReply.setMemberId(member.getMemberId());
//                        // 대댓글에 등급을 유지시켜주기 위한 값
//                        boardReplyReply.setGrade(member.getGrade());
//                        // 게시물 대댓글의 좋아요 참조 관계 해제를 위한 조건값 설정
//                        BoardReplyReplyLike boardReplyReplyLike = new BoardReplyReplyLike();
//                        boardReplyReplyLike.setMemberId(member.getMemberId());
//                        // 찜리스트 참조 관계 해제를 위한 조건값 설정
//                        Favorite favorite = new Favorite();
//                        favorite.setMemberId(member.getMemberId());
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
//                            // 댓글과 대댓글에 등급값을 저장
//                            replyService.updateReplyLastGrade(boardReply);
//                            replyService.updateReplyReplyLastGrade(boardReplyReply);
//                            
//                            // 참조관계 해제
//                            boardService.updateDocumentMemberOut(boardList);
//                            replyService.updateCommentMemberOut(boardReply);
//                            categoryLikeService.updateCategoryLikeMemberOut(categoryLike);
//                            replyLikeService.updateBoardReplyLikeMemberOut(boardReplyLike);
//                            replyService.updateReCommentMemberOut(boardReplyReply);
//                            replyLikeService.updateBoardReplyReplyLikeMemberOut(boardReplyReplyLike);
//                            favoriteService.updateFavoriteByMemberOut(favorite);
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
//                            logger.debug(e.getLocalizedMessage());
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
//                            logger.debug(e.getLocalizedMessage());
//                        } catch(Exception e) {
//                            logger.debug(e.getLocalizedMessage());
//                        }
//                    } // End if~else
//                } // End if
//            } // End if
//        } // End for
//    } // End memberManagamentInactiveDeleteOkCron Method
//    
//}
