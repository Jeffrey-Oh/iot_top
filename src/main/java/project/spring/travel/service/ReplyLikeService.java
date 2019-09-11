package project.spring.travel.service;

import project.spring.travel.model.BoardReplyLike;
import project.spring.travel.model.BoardReplyReplyLike;

public interface ReplyLikeService {
	/**
	 * 댓글 좋아요 추가
	 * @MethodName - addBoardReplyLike
	 * @param boardReplyLike
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 4. 8.
	 */
	public void addBoardReplyLike(BoardReplyLike boardReplyLike) throws Exception;
	
	/**
	 * 댓글의 댓글 좋아요 추가
	 * @MethodName - addBoardReplyReplyLike
	 * @param boardReplyReplyLike
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 14.
	 */
	public void addBoardReplyReplyLike(BoardReplyReplyLike boardReplyReplyLike) throws Exception;
	
	/**
	 * 댓글 좋아요 삭제
	 * @MethodName - deleteBoardReplyLike
	 * @param boardReplyLike
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 4. 8.
	 */
	public void deleteBoardReplyLike(BoardReplyLike boardReplyLike) throws Exception;
	
	/**
	 * 댓글의 댓글 좋아요 삭제
	 * @MethodName - deleteBoardReplyReplyLike
	 * @param boardReplyReplyLike
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 4. 26.
	 */
	public void deleteBoardReplyReplyLike(BoardReplyReplyLike boardReplyReplyLike) throws Exception;
	
	/**
	 * 특정 회원의 댓글 좋아요 참조관계 해제
	 * @MethodName - updateBoardReplyLikeMemberOut
	 * @param boardReplyLike
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 4. 9.
	 */
	public void updateBoardReplyLikeMemberOut(BoardReplyLike boardReplyLike) throws Exception;
	
	/**
	 * 특정 회원의 댓글의 댓글 좋아요 참조관계 해제
	 * @MethodName - updateBoardReplyReplyLikeMemberOut
	 * @param boardReplyReplyLike
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 14.
	 */
	public void updateBoardReplyReplyLikeMemberOut(BoardReplyReplyLike boardReplyReplyLike) throws Exception;
	
	/**
	 * 등급제를 위한 카운트
	 * @MethodName - countByMemberId
	 * @param boardReplyLike
	 * @return
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 13.
	 */
	public int countByMemberId(BoardReplyLike boardReplyLike) throws Exception;
	
	/**
	 * 등급제를 위한 카운트
	 * @MethodName - reCountByMemberId
	 * @param boardReplyReplyLike
	 * @return
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 14.
	 */
	public int reCountByMemberId(BoardReplyReplyLike boardReplyReplyLike) throws Exception;
	
	/**
	 * 등급제를 위한 카운트 (사용자)
	 * @MethodName - countByUser
	 * @param boardLike
	 * @return
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 13.
	 */
	public int countByUser(BoardReplyLike boardReplyLike) throws Exception;
	
	/**
	 * 등급제를 위한 카운트 (사용자)
	 * @MethodName - reCountByUser
	 * @param boardReplyLike
	 * @return
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 14.
	 */
	public int reCountByUser(BoardReplyReplyLike boardReplyReplyLike) throws Exception;
	
	/**
	 * 게시물 댓글 좋아요 현황
	 * @MethodName - replyCheck
	 * @param boardReplyLike
	 * @return
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 13.
	 */
	public int replyCheck(BoardReplyLike boardReplyLike) throws Exception;
	
	/**
	 * 댓글의 댓글 좋아요 현황
	 * @MethodName - replyReplyCheck
	 * @param boardReplyReplyLike
	 * @return
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 14.
	 */
	public int replyReplyCheck(BoardReplyReplyLike boardReplyReplyLike) throws Exception;
	
}
