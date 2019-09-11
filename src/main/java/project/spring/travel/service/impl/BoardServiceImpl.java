package project.spring.travel.service.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.spring.travel.model.BoardList;
import project.spring.travel.service.BoardService;

@Service
public class BoardServiceImpl implements BoardService {

	private static Logger logger = LoggerFactory.getLogger(BoardServiceImpl.class);
	
	@Autowired
	SqlSession sqlSession;
	
	@Override
	public void addItem(BoardList params) throws Exception {
		try {
			
			int	cnt = sqlSession.insert("BoardMapper.addBoard", params);
			if (cnt == 0) {
				// 저장된 행이 없다면 강제로 예외를 발생시킨다.
				// --> 이 예외를 처리 가능한 catch블록으로 제어가 이동한다.
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			// 에러가 발생했으므로 SQL 수행 내역을 되돌림
			throw new Exception("저장된 데이터가 없습니다.");
		} catch (Exception e) {
			// 에러가 발생했으므로 SQL 수행 내역을 되돌림
			logger.error(e.getLocalizedMessage());
			throw new Exception("데이터 저장에 실패했습니다.");
		}
	}

	@Override
	public void editItem(BoardList params) throws Exception {
		try {
			int cnt = sqlSession.update("BoardMapper.editBoard", params);

			if (cnt == 0) {
				// 저장된 행이 없다면 강제로 예외를 발생시킨다.
				// --> 이 예외를 처리 가능한 catch블록으로 제어가 이동한다.
				throw new NullPointerException("수정할 데이터가 없습니다.");
			}
		} catch (NullPointerException e) {
			// 에러가 발생했으므로 SQL 수행 내역을 되돌림
			throw new Exception("수정된 데이터가 없습니다.");
		} catch (Exception e) {
			// 에러가 발생했으므로 SQL 수행 내역을 되돌림
			logger.error(e.getLocalizedMessage());
			throw new Exception("데이터 수정에 실패했습니다.");
		}
	}

	@Override
	public void removeItem(BoardList params) throws Exception {
		try {
			int cnt = sqlSession.delete("BoardMapper.removeBoardById", params);

			if (cnt == 0) {
				// 저장된 행이 없다면 강제로 예외를 발생시킨다.
				// --> 이 예외를 처리 가능한 catch블록으로 제어가 이동한다.
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			// 에러가 발생했으므로 SQL 수행 내역을 되돌림
			throw new Exception("게시물 삭제 권한이 없습니다.");
		} catch (Exception e) {
			// 에러가 발생했으므로 SQL 수행 내역을 되돌림
			logger.error(e.getLocalizedMessage());
			throw new Exception("데이터 삭제에 실패했습니다.");
		}
	}

	@Override
	public BoardList getItem(BoardList params) throws Exception {
		BoardList result = null;
		
		try {
			result = sqlSession.selectOne("BoardMapper.getBoardItem", params);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("조회된 게시물이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 조회에 실패했습니다.");
		}

		return result;
	}


	@Override
	public List<BoardList> getItemAll(BoardList params) throws Exception {
		List<BoardList> result = null;

		try {
			result = sqlSession.selectList("BoardMapper.getBoardList", params);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("데이터 조회에 실패했습니다.");
		}

		return result;
	}
	
	@Override
	public int getCount(BoardList params) throws Exception {
		int result = 0;
		
		try {
			result = sqlSession.selectOne("BoardMapper.getCount", params);
		} catch (Exception e) {
			// 에러가 발생했으므로 SQL 수행 내역을 되돌림
			logger.error(e.getLocalizedMessage());
			throw new Exception("데이터 통신에 실패했습니다.");
		}
		
		return result;
		
	}

	@Override
	public void updateBoard(BoardList params) throws Exception {
		try {
			int result = sqlSession.update("BoardMapper.editBoardByCount", params);
			if (result == 0) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("존재하지 않는 게시물에 대한 요청입니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("조회수 갱신에 실패했습니다.");
		}
	}

	@Override
	public int selectDocumentCountByMemberId(BoardList params) throws Exception {
		int result = 0;
		try {
			// 자신의 게시물이 아닌 경우도 있으므로,
			// 결과값이 0인 경우에 대한 예외를 발생시키지 않는다.
			result = sqlSession.selectOne("BoardMapper.selectDocumentCountByMemberId", params);
			
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("왜 남의것을 건드려요.");
		}
		
		return result;
	}

	@Override
	public void updateDocumentMemberOut(BoardList params) throws Exception {
		try {
			// 게시글을 작성한 적이 없는 회원이 있을 수 있기 때문에,
			// Null 예외처리는 하지 않는다.
			sqlSession.update("BoardMapper.updateDocumentMemberOut", params);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 참조관계 해제에 실패했습니다.");
		}
	}

	@Override
	public void editBoardByLikeUp(BoardList params) throws Exception {
		int result = 0;
		try {
			result = sqlSession.update("BoardMapper.editBoardByLikeUp", params);
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
	public void editBoardByLikeDown(BoardList params) throws Exception {
		int result = 0;
		try {
			result = sqlSession.update("BoardMapper.editBoardByLikeDown", params);
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
	public int countByMemberId(BoardList params) throws Exception {
		int result = 0;
		
		try {
			// 좋아요 하기 전인 사람들은 데이터가 없으니 0에 대한 예외처리 하지않음
			result = sqlSession.selectOne("BoardMapper.CountByMemberId", params);
		} catch (NullPointerException e) {
			throw new Exception("게시물이 없습니다.");
		} catch (Exception e) {
			throw new Exception("데이터 조회에 실패했습니다.");
		}
		return result;
	}

	@Override
	public List<BoardList> getBoardItemCenter(BoardList params) throws Exception {
		List<BoardList> result = null;
		
		try {
			result = sqlSession.selectList("BoardMapper.getBoardItemCenter", params);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public List<BoardList> getBoardListCenter(BoardList params) throws Exception {
		List<BoardList> result = null;

		try {
			result = sqlSession.selectList("BoardMapper.getBoardListCenter", params);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("데이터 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public int getCountCenter(BoardList params) throws Exception {
		int result = 0;
		try {
			result = sqlSession.selectOne("BoardMapper.getCountCenter", params);
		} catch (Exception e) {
			// 에러가 발생했으므로 SQL 수행 내역을 되돌림
			logger.error(e.getLocalizedMessage());
			throw new Exception("데이터 통신에 실패했습니다.");
		}
		return result;
	}

	@Override
	public List<BoardList> getItemListByMemberId(BoardList params) throws Exception {
		List<BoardList> result = null;

		try {
			result = sqlSession.selectList("BoardMapper.getItemListByMemberId", params);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("마이페이지 내 게시글 목록조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public List<BoardList> getBoardNotice() throws Exception {
		List<BoardList> result = null;

		try {
			result = sqlSession.selectList("BoardMapper.getBoardNotice");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("공지사항 목록조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public void editNotice(BoardList params) throws Exception {
		try {
			int cnt = sqlSession.update("BoardMapper.editNotice", params);

			if (cnt == 0) {
				// 저장된 행이 없다면 강제로 예외를 발생시킨다.
				// --> 이 예외를 처리 가능한 catch블록으로 제어가 이동한다.
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			// 에러가 발생했으므로 SQL 수행 내역을 되돌림
			throw new Exception("수정할 공지사항이 없습니다.");
		} catch (Exception e) {
			// 에러가 발생했으므로 SQL 수행 내역을 되돌림
			logger.error(e.getLocalizedMessage());
			throw new Exception("데이터 수정에 실패했습니다.");
		}
	}

	@Override
	public BoardList getBoardNoticeItem(BoardList params) throws Exception {
		BoardList result = null;
		
		try {
			result = sqlSession.selectOne("BoardMapper.getBoardNoticeItem", params);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("조회된 게시물이 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("게시물 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public int getBoardNoticeCount() throws Exception {
		int cnt = 0;
		
		try {
			cnt = sqlSession.selectOne("BoardMapper.getBoardNoticeCount");
		} catch (Exception e) {
			// 에러가 발생했으므로 SQL 수행 내역을 되돌림
			logger.error(e.getLocalizedMessage());
			throw new Exception("고정 중인 공지사항 목록 개수 조회에 실패했습니다.");
		}
		
		return cnt;
	}

	@Override
	public int getBoardNoticeCountAll() throws Exception {
		int cnt = 0;
		
		try {
			cnt = sqlSession.selectOne("BoardMapper.getBoardNoticeCountAll");
		} catch (Exception e) {
			// 에러가 발생했으므로 SQL 수행 내역을 되돌림
			logger.error(e.getLocalizedMessage());
			throw new Exception("공지사항 총 개수 조회에 실패했습니다.");
		}
		
		return cnt;
	}

	@Override
	public int getCountCenterByMemberId(BoardList params) throws Exception {
		int cnt = 0;
		
		try {
			cnt = sqlSession.selectOne("BoardMapper.getCountCenterByMemberId", params);
		} catch (Exception e) {
			// 에러가 발생했으므로 SQL 수행 내역을 되돌림
			logger.error(e.getLocalizedMessage());
			throw new Exception("회원님의 문의내역 총 개수 조회에 실패했습니다.");
		}
		
		return cnt;
	}
	
	
}
