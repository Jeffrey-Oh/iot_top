package project.spring.travel.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.spring.travel.model.BoardReply;
import project.spring.travel.model.BoardReplyReply;
import project.spring.travel.service.ReplyService;

@Service
public class ReplyServiceImpl implements ReplyService {

	private static Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);
	
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public void insertComment(BoardReply reply) throws Exception {
		try {
			int result = sqlSession.insert("BoardReplyMapper.insertComment", reply);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("저장된 댓글이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("댓글 등록에 실패했습니다.");
		}
	}

	@Override
	public BoardReply selectComment(BoardReply reply) throws Exception {
		BoardReply result = null;
		
		try {
			result = sqlSession.selectOne("BoardReplyMapper.selectComment", reply);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 댓글이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("댓글 조회에 실패했습니다.");
		}
		
		return result;
	}

	@Override
	public List<BoardReply> selectCommentList(BoardReply reply) throws Exception {
		List<BoardReply> result = null;
		
		try {
			result = sqlSession.selectList("BoardReplyMapper.selectCommentList", reply);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("댓글 목록 조회에 실패했습니다.");
		}
		
		return result;
	}

	@Override
	public void deleteComment(BoardReply reply) throws Exception {
		try {
			sqlSession.delete("BoardReplyMapper.deleteComment", reply);
		} catch (NullPointerException e) {
			throw new Exception("존재하지 않는 댓글에 대한 요청입니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("댓글 삭제에 실패했습니다.");
		}
	}

	@Override
	public void updateComment(BoardReply reply) throws Exception {
		try {
			int result = sqlSession.update("BoardReplyMapper.updateComment", reply);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("존재하지 않는 댓글에 대한 요청입니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("댓글 수정에 실패했습니다.");
		}
	}

	@Override
	public void deleteCommentAll(BoardReply reply) throws Exception {
		try {
			sqlSession.delete("BoardReplyMapper.deleteCommentAll", reply);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("댓글 삭제에 실패했습니다.");
		}
	}

	@Override
	public void updateCommentMemberOut(BoardReply reply) throws Exception {
		try {
			// 댓글을 작성한 적이 없는 회원이 있을 수 있기 때문에,
			// Null 예외처리는 하지 않는다.
			sqlSession.update("BoardReplyMapper.updateCommentMemberOut", reply);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("댓글 참조관계 해제에 실패했습니다.");
		}
	}

	@Override
	public int selectCommentCountByMemberId(BoardReply reply) throws Exception {
		int result = 0;
		
		try {
			// 자신의 댓글이 아닌 경우도 있으므로,
			// 결과값이 0인 경우에 대한 예외처리는 하지 않는다.
			result = sqlSession.selectOne("BoardReplyMapper.selectCommentCountByMemberId", reply);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("댓글 수 조회에 실패했습니다.");
		}
		
		return result;
	}

	@Override
	public void editBoardReplyByLikeUp(BoardReply reply) throws Exception {
		int result = 0;
		try {
			result = sqlSession.update("BoardReplyMapper.editBoardReplyByLikeUp", reply);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("좋아요 처리할 게시물이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 좋아요처리 실패했습니다.");
		}
	}

	@Override
	public void editBoardReplyByLikeDown(BoardReply reply) throws Exception {
		int result = 0;
		try {
			result = sqlSession.update("BoardReplyMapper.editBoardReplyByLikeDown", reply);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("좋아요 처리할 게시물이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 좋아요처리 실패했습니다.");
		}
	}

	@Override
	public int selectCount(BoardReply reply) throws Exception {
		int result = 0;
		try {
			result = sqlSession.selectOne("BoardReplyMapper.selectCount", reply);
		} catch (Exception e) {
			// 에러가 발생했으므로 SQL 수행 내역을 되돌림
			logger.error(e.getLocalizedMessage());
			throw new Exception("댓글 카운트 조회 실패");
		}
		
		return result;
	}

	@Override
	public int countByMemberId(BoardReply reply) throws Exception {
		int result = 0;
		
		try {
			// 좋아요 하기 전인 사람들은 데이터가 없으니 0에 대한 예외처리 하지않음
			result = sqlSession.selectOne("BoardReplyMapper.countByMemberId", reply);
		} catch (NullPointerException e) {
			throw new Exception("게시물이 없습니다.");
		} catch (Exception e) {
			throw new Exception("댓글 등록 카운트 조회 실패");
		}
		
		return result;
	}

	@Override
	public void deleteReplyLike(BoardReply reply) throws Exception {
		try {
			// 좋아요 하기 전인 사람들은 데이터가 없으니 0에 대한 예외처리 하지않음
			sqlSession.delete("BoardReplyMapper.deleteReplyLike", reply);
		} catch (Exception e) {
			throw new Exception("댓글 좋아요 취소 실패");
		}
	}

	@Override
	public void insertReComment(BoardReplyReply replyReply) throws Exception {
		try {
			int result = sqlSession.insert("BoardReplyMapper.insertReComment", replyReply);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("저장된 댓글이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("댓글 등록에 실패했습니다.");
		}
	}

	@Override
	public BoardReplyReply selectReComment(BoardReplyReply replyReply) throws Exception {
		BoardReplyReply result = null;
		
		try {
			result = sqlSession.selectOne("BoardReplyMapper.selectReComment", replyReply);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 댓글이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("댓글 조회에 실패했습니다.");
		}
		
		return result;
	}

	@Override
	public List<BoardReplyReply> selectReCommentList(BoardReplyReply replyReply) throws Exception {
		List<BoardReplyReply> result = null;
		
		try {
			result = sqlSession.selectList("BoardReplyMapper.selectReCommentList", replyReply);
		} catch (NullPointerException e) {
			throw new Exception("조회된 댓글이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("댓글 목록 조회에 실패했습니다.");
		}
		
		return result;
	}

	@Override
	public void deleteReComment(BoardReplyReply replyReply) throws Exception {
		try {
			sqlSession.delete("BoardReplyMapper.deleteReComment", replyReply);
		} catch (NullPointerException e) {
			throw new Exception("존재하지 않는 댓글에 대한 요청입니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("댓글 삭제에 실패했습니다.");
		}
	}

	@Override
	public void updateReComment(BoardReplyReply replyReply) throws Exception {
		try {
			int result = sqlSession.update("BoardReplyMapper.updateReComment", replyReply);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("존재하지 않는 댓글에 대한 요청입니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("댓글 수정에 실패했습니다.");
		}
	}

	@Override
	public void updateReCommentMemberOut(BoardReplyReply replyReply) throws Exception {
		try {
			// 댓글을 작성한 적이 없는 회원이 있을 수 있기 때문에,
			// Null 예외처리는 하지 않는다.
			sqlSession.update("BoardReplyMapper.updateReCommentMemberOut", replyReply);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("댓글 참조관계 해제에 실패했습니다.");
		}
	}

	@Override
	public int selectReCommentCountByMemberId(BoardReplyReply replyReply) throws Exception {
		int result = 0;
		
		try {
			// 자신의 댓글이 아닌 경우도 있으므로,
			// 결과값이 0인 경우에 대한 예외처리는 하지 않는다.
			result = sqlSession.selectOne("BoardReplyMapper.selectReCommentCountByMemberId", replyReply);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("댓글 수 조회에 실패했습니다.");
		}
		
		return result;
	}

	@Override
	public void editBoardReplyReplyByLikeUp(BoardReplyReply replyReply) throws Exception {
		int result = 0;
		try {
			result = sqlSession.update("BoardReplyMapper.editBoardReplyReplyByLikeUp", replyReply);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("좋아요 처리할 게시물이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 좋아요처리 실패했습니다.");
		}
	}

	@Override
	public void editBoardReplyReplyByLikeDown(BoardReplyReply replyReply) throws Exception {
		int result = 0;
		try {
			result = sqlSession.update("BoardReplyMapper.editBoardReplyReplyByLikeDown", replyReply);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("좋아요 처리할 게시물이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 좋아요처리 실패했습니다.");
		}
	}

	@Override
	public int selectReCount(BoardReplyReply replyReply) throws Exception {
		int result = 0;
		try {
			result = sqlSession.selectOne("BoardReplyMapper.selectReCount", replyReply);
		} catch (Exception e) {
			// 에러가 발생했으므로 SQL 수행 내역을 되돌림
			logger.error(e.getLocalizedMessage());
			throw new Exception("대댓글 불러오기 실패 ㅠㅠ");
		}
		
		return result;
	}

	@Override
	public int reCountByMemberId(BoardReplyReply replyReply) throws Exception {
		int result = 0;
		
		try {
			// 좋아요 하기 전인 사람들은 데이터가 없으니 0에 대한 예외처리 하지않음
			result = sqlSession.selectOne("BoardReplyMapper.reCountByMemberId", replyReply);
		} catch (Exception e) {
			throw new Exception("대댓글 좋아요 현황 조회 실패");
		}
		
		return result;
	}

	@Override
	public void deleteReplyReplyLike(BoardReplyReply replyReply) throws Exception {
		try {
			// 좋아요 하기 전인 사람들은 데이터가 없으니 0에 대한 예외처리 하지않음
			sqlSession.delete("BoardReplyMapper.deleteReplyReplyLike", replyReply);
		} catch (Exception e) {
			throw new Exception("대댓글 좋아요 취소 실패");
		}
	}

	@Override
	public BoardReplyReply selectReplyIdByReplyReply(BoardReplyReply replyReply) throws Exception {
		BoardReplyReply result = null;
		
		try {
			result = sqlSession.selectOne("BoardReplyMapper.selectReplyIdByReplyReply", replyReply);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("댓글 일련번호 조회에 실패했습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("데이터 통신에 실패하여 댓글 일련번호 조회에 실패함.");
		}
		
		return result;
	}

	@Override
	public void updateReplyLastGrade(BoardReply reply) throws Exception {
		try {
			sqlSession.update("BoardReplyMapper.updateReplyLastGrade", reply);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("댓글의 등급 업데이트에 실패했습니다.");
		}
	}

	@Override
	public void updateReplyReplyLastGrade(BoardReplyReply replyReply) throws Exception {
		try {
			sqlSession.update("BoardReplyMapper.updateReplyReplyLastGrade", replyReply);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("대댓글의 등급 업데이트에 실패했습니다.");
		}
	}

	@Override
	public List<BoardReply> selectCommentListByReplyId(BoardReply reply) throws Exception {
		List<BoardReply> result = null;
		
		try {
			result = sqlSession.selectList("BoardReplyMapper.selectCommentListByReplyId", reply);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("댓글 목록 조회에 실패했습니다.");
		}
		
		return result;
	}

	@Override
	public int selectCheckThisReplyIdByBoard(BoardReply reply) throws Exception {
		int result = 0;
		
		try {
			result = sqlSession.selectOne("BoardReplyMapper.selectCheckThisReplyIdByBoard", reply);
		} catch (Exception e) {
			throw new Exception("대댓글 좋아요 현황 조회 실패");
		}
		
		return result;
	}
}
