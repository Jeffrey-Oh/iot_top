package project.spring.travel.service;

import java.util.List;

import project.spring.travel.model.BoardReply;
import project.spring.travel.model.BoardReplyReply;

public interface ReplyService {
	/**
	 * 댓글을 저장한다.
	 * @MethodName - insertComment
	 * @param reply
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 14.
	 */
	public void insertComment(BoardReply reply) throws Exception;
	
	/**
	 * 댓글의 댓글을 저장한다.
	 * @MethodName - insertReComment
	 * @param replyReply
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 14.
	 */
	public void insertReComment(BoardReplyReply replyReply) throws Exception;
	
	/**
	 * 하나의 댓글을 읽어들인다.
	 * @MethodName - selectComment
	 * @param reply - 읽어들일 게시물 일련번호가 저장된 Beans
	 * @return BoardReply - 읽어들인 게시물 내용
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 3.
	 */
	public BoardReply selectComment(BoardReply reply) throws Exception;
	
	/**
	 * 하나의 댓글의 댓글을 읽어들인다.
	 * @MethodName - selectReComment
	 * @param replyReply
	 * @return
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 14.
	 */
	public BoardReplyReply selectReComment(BoardReplyReply replyReply) throws Exception;
	
	/**
	 * 하나의 게시물에 속한 모든 댓글 목록을 조회한다.
	 * @MethodName - selectCommentList
	 * @param comment - 읽어들일 게시물 일련번호가 저장된 Beans
	 * @return List - 읽어들인 댓글 목록
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 3.
	 */
	public List<BoardReply> selectCommentList(BoardReply reply) throws Exception;
	
	/**
	 * 하나의 댓글에 속한 모든 댓글 목록을 조회한다.
	 * @MethodName - selectReCommentList
	 * @param replyReply
	 * @return
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 14.
	 */
	public List<BoardReplyReply> selectReCommentList(BoardReplyReply replyReply) throws Exception;
	
	/**
	 * 댓글 삭제
	 * @MethodName - deleteComment
	 * @param comment
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 4.
	 */
	public void deleteComment(BoardReply reply) throws Exception;
	
	/**
	 * 댓글의 댓글 삭제
	 * @MethodName - deleteReComment
	 * @param replyReply
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 14.
	 */
	public void deleteReComment(BoardReplyReply replyReply) throws Exception;
	
	/**
	 * 댓글 수정
	 * @MethodName - updateComment
	 * @param comment
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 4.
	 */
	public void updateComment(BoardReply reply) throws Exception;
	
	/**
	 * 댓글의 댓글 수정
	 * @MethodName - updateReComment
	 * @param replyReply
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 14.
	 */
	public void updateReComment(BoardReplyReply replyReply) throws Exception;
	
	/**
	 * 특정 게시물에 속한 모든 댓글을 삭제
	 * @MethodName - deleteCommentAll
	 * @param comment
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 4.
	 */
	public void deleteCommentAll(BoardReply reply) throws Exception;
	
	/**
	 * 회원과 댓글간의 참조관계를 해제한다.
	 * @MethodName - updateCommentMemberOut
	 * @param comment
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 4.
	 */
	public void updateCommentMemberOut(BoardReply reply) throws Exception;
	
	/**
	 * 회원과 댓글의 댓글간의 참조관계를 해제한다.
	 * @MethodName - updateReCommentMemberOut
	 * @param replyReply
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 14.
	 */
	public void updateReCommentMemberOut(BoardReplyReply replyReply) throws Exception;
	
	/**
	 * 자신의 댓글인지 검사
	 * @MethodName - selectCommentCountByMemberId
	 * @param reply
	 * @return
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 5.
	 */
	public int selectCommentCountByMemberId(BoardReply reply) throws Exception;
	
	/**
	 * 자신의 댓글인지 검사
	 * @MethodName - selectReCommentCountByMemberId
	 * @param replyReply
	 * @return
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 14.
	 */
	public int selectReCommentCountByMemberId(BoardReplyReply replyReply) throws Exception;
	
	/**
	 * 게시물 댓글 좋아요 + 1
	 * @MethodName - editBoardReplyByLike
	 * @param params
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 4. 9.
	 */
	public void editBoardReplyByLikeUp(BoardReply reply) throws Exception;
	
	/**
	 * 댓글의 댓글 좋아요 + 1
	 * @MethodName - editBoardReplyReplyByLikeUp
	 * @param replyReply
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 14.
	 */
	public void editBoardReplyReplyByLikeUp(BoardReplyReply replyReply) throws Exception;
	
	/**
	 * 게시물 댓글 좋아요 - 1
	 * @MethodName - editBoardReplyByLikeDown
	 * @param params
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 4. 9.
	 */
	public void editBoardReplyByLikeDown(BoardReply reply) throws Exception;
	
	/**
	 * 댓글의 댓글 좋아요 - 1
	 * @MethodName - editBoardReplyReplyByLikeDown
	 * @param replyReply
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 14.
	 */
	public void editBoardReplyReplyByLikeDown(BoardReplyReply replyReply) throws Exception;
	
	/**
	 * 게시물 댓글 총 개수
	 * @MethodName - selectCount
	 * @param reply
	 * @return int
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 4. 12.
	 */
	public int selectCount(BoardReply reply) throws Exception;
	
	/**
	 * 댓글의 댓글 총 개수
	 * @MethodName - selectReCount
	 * @param replyReply
	 * @return
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 14.
	 */
	public int selectReCount(BoardReplyReply replyReply) throws Exception;
	
	/**
	 * 등급제를 위한 카운트
	 * @MethodName - countByMemberId
	 * @param reply
	 * @return
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 13.
	 */
	public int countByMemberId(BoardReply reply) throws Exception;
	
	/**
	 * 등급제를 위한 카운트
	 * @MethodName - reCountByMemberId
	 * @param replyReply
	 * @return
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 14.
	 */
	public int reCountByMemberId(BoardReplyReply replyReply) throws Exception;
	
	/**
	 * 원본 댓글 삭제 시 좋아요 참조관계 해제 및 삭제
	 * @MethodName - deleteReplyLike
	 * @param reply
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 13.
	 */
	public void deleteReplyLike(BoardReply reply) throws Exception;
	
	/**
	 * 댓글의 댓글 삭제 시 좋아요 참조관계 해제 및 삭제
	 * @MethodName - deleteReplyReplyLike
	 * @param replyReply
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 14.
	 */
	public void deleteReplyReplyLike(BoardReplyReply replyReply) throws Exception;
	
	/**
	 * 댓글의 댓글 원본 댓글일련번호 구하기
	 * @MethodName - selectReplyIdByReplyReply
	 * @param replyReply
	 * @return
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 4. 23.
	 */
	public BoardReplyReply selectReplyIdByReplyReply(BoardReplyReply replyReply) throws Exception;
	
	/**
	 * 회원탈퇴 시 유저의 댓글에 대해 마지막 등급 업데이트
	 * @MethodName - updateReplyLastGrade
	 * @param replyReply
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 4. 24.
	 */
	public void updateReplyLastGrade(BoardReply reply) throws Exception;
	
	/**
	 * 회원탈퇴 시 유저의 댓글의 댓글에 대해 마지막 등급 업데이트
	 * @MethodName - updateReplyReplyLastGrade
	 * @param replyReply
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 4. 24.
	 */
	public void updateReplyReplyLastGrade(BoardReplyReply replyReply) throws Exception;
	
	
	/**
	 * 게시물 삭제 시 댓글의 일련번호를 수집
	 * @MethodName - selectCommentListByReplyId
	 * @param reply
	 * @return
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 4. 26.
	 */
	public List<BoardReply> selectCommentListByReplyId(BoardReply reply) throws Exception;
	
	/**
	 * 현재 게시물 안에 있는 댓글인지 확인
	 * @MethodName - selectCheckThisReplyIdByBoard
	 * @param reply
	 * @return
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 5. 9.
	 */
	public int selectCheckThisReplyIdByBoard(BoardReply reply) throws Exception;
}
