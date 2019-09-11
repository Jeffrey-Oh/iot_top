package project.spring.travel.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.spring.travel.model.BoardReplyLike;
import project.spring.travel.model.BoardReplyReplyLike;
import project.spring.travel.service.ReplyLikeService;

@Service
public class ReplyLikeServiceImpl implements ReplyLikeService{

	private static Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);
	
	@Autowired
	SqlSession sqlSession;

	@Override
	public void addBoardReplyLike(BoardReplyLike boardReplyLike) throws Exception {
		int result = 0;
		
		try {
			result = sqlSession.insert("BoardReplyLikeMapper.addBoardReplyLike", boardReplyLike);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("게시물 좋아요 등록할 게시물이 없습니다.");
		} catch (Exception e) {
			throw new Exception("댓글 좋아요 등록 실패");
		}
		
	}

	@Override
	public void deleteBoardReplyLike(BoardReplyLike boardReplyLike) throws Exception {
		try {
			sqlSession.delete("BoardReplyLikeMapper.deleteBoardReplyLike", boardReplyLike);
		} catch (NullPointerException e) {
			throw new Exception("게시물 좋아요 취소할 게시물이 없습니다.");
		} catch (Exception e) {
			throw new Exception("댓글 좋아요 취소 실패");
		}
	}

	@Override
	public void updateBoardReplyLikeMemberOut(BoardReplyLike boardReplyLike) throws Exception {
		try {
			sqlSession.update("BoardReplyLikeMapper.updateBoardReplyLikeMemberOut", boardReplyLike);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 댓글의 좋아요에 대해 참조관계 해제에 실패했습니다.");
		}
	}

	@Override
	public int countByMemberId(BoardReplyLike boardReplyLike) throws Exception {
		int result = 0;
		
		try {
			// 좋아요 하기 전인 사람들은 데이터가 없으니 0에 대한 예외처리 하지않음
			result = sqlSession.selectOne("BoardReplyLikeMapper.countByMemberId", boardReplyLike);
		} catch (NullPointerException e) {
			throw new Exception("게시물이 없습니다.");
		} catch (Exception e) {
			throw new Exception("댓글 사용자 좋아요 카운트 실패");
		}
		
		return result;
	}

	@Override
	public int countByUser(BoardReplyLike boardReplyLike) throws Exception {
		int result = 0;
		
		try {
			// 좋아요 하기 전인 사람들은 데이터가 없으니 0에 대한 예외처리 하지않음
			result = sqlSession.selectOne("BoardReplyLikeMapper.countByUser", boardReplyLike);
		} catch (NullPointerException e) {
			throw new Exception("게시물이 없습니다.");
		} catch (Exception e) {
			throw new Exception("댓글 유저 좋아요 카운트 실패");
		}
		
		return result;
	}
	
	@Override
	public int replyCheck(BoardReplyLike boardReplyLike) throws Exception {
		int result = 0;
		
		try {
			// 좋아요 하기 전인 사람들은 데이터가 없으니 0에 대한 예외처리 하지않음
			result = sqlSession.selectOne("BoardReplyLikeMapper.replyCheck", boardReplyLike);
		} catch (NullPointerException e) {
			throw new Exception("게시물이 없습니다.");
		} catch (Exception e) {
			throw new Exception("댓글 좋아요 현황 조회 실패");
		}
		
		return result;
	}

	@Override
	public void addBoardReplyReplyLike(BoardReplyReplyLike boardReplyReplyLike) throws Exception {
		int result = 0;
		
		try {
			result = sqlSession.insert("BoardReplyLikeMapper.addBoardReplyReplyLike", boardReplyReplyLike);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("게시물 좋아요 등록할 게시물이 없습니다.");
		} catch (Exception e) {
			throw new Exception("대댓글 좋아요 등록 실패");
		}
	}

	@Override
	public void deleteBoardReplyReplyLike(BoardReplyReplyLike boardReplyReplyLike) throws Exception {
		try {
			sqlSession.delete("BoardReplyLikeMapper.deleteBoardReplyReplyLike", boardReplyReplyLike);
		} catch (NullPointerException e) {
			throw new Exception("게시물 좋아요 취소할 게시물이 없습니다.");
		} catch (Exception e) {
			throw new Exception("대댓글 좋아요 취소 실패");
		}
	}

	@Override
	public void updateBoardReplyReplyLikeMemberOut(BoardReplyReplyLike boardReplyReplyLike) throws Exception {
		try {
			sqlSession.update("BoardReplyLikeMapper.updateBoardReplyReplyLikeMemberOut", boardReplyReplyLike);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 댓글의 좋아요에 대해 참조관계 해제에 실패했습니다.");
		}
	}

	@Override
	public int reCountByMemberId(BoardReplyReplyLike boardReplyReplyLike) throws Exception {
		int result = 0;
		
		try {
			// 좋아요 하기 전인 사람들은 데이터가 없으니 0에 대한 예외처리 하지않음
			result = sqlSession.selectOne("BoardReplyLikeMapper.reCountByMemberId", boardReplyReplyLike);
		} catch (NullPointerException e) {
			throw new Exception("게시물이 없습니다.");
		} catch (Exception e) {
			throw new Exception("대댓글 사용자 좋아요 카운트 실패");
		}
		
		return result;
	}

	@Override
	public int reCountByUser(BoardReplyReplyLike boardReplyReplyLike) throws Exception {
		int result = 0;
		
		try {
			// 좋아요 하기 전인 사람들은 데이터가 없으니 0에 대한 예외처리 하지않음
			result = sqlSession.selectOne("BoardReplyLikeMapper.reCountByUser", boardReplyReplyLike);
		} catch (NullPointerException e) {
			throw new Exception("게시물이 없습니다.");
		} catch (Exception e) {
			throw new Exception("대댓글 유저 좋아요 카운트 실패");
		}
		
		return result;
	}

	@Override
	public int replyReplyCheck(BoardReplyReplyLike boardReplyReplyLike) throws Exception {
		int result = 0;
		
		try {
			// 좋아요 하기 전인 사람들은 데이터가 없으니 0에 대한 예외처리 하지않음
			result = sqlSession.selectOne("BoardReplyLikeMapper.replyReplyCheck", boardReplyReplyLike);
		} catch (NullPointerException e) {
			throw new Exception("게시물이 없습니다.");
		} catch (Exception e) {
			throw new Exception("대댓글 좋아요 유지 조회 실패");
		}
		
		return result;
	}

}
