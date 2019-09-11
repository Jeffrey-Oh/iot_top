package project.spring.travel.service;

import java.util.List;

import project.spring.travel.model.BoardList;

public interface BoardService {
	
	/**
	 * 게시물 추가
	 * @MethodName - addItem
	 * @return boolean - true 면 추가 성공 / false 면 추가 실패
	 * @throws Exception
	 * @author	   - JEFFREY_OH
	 * @lastUpdate - 2019. 2. 17.
	 */
	public void addItem(BoardList params) throws Exception;
	
	/**
	 * 게시물 수정
	 * @MethodName - editItem
	 * @param params - BoardList 형태로 수정할 값들을 파라미터로 전달
	 * @return boolean - true 면 수정 성공 / false 면 수정 실패
	 * @throws NullPointerException
	 * @throws Exception
	 * @author	   - JEFFREY_OH
	 * @lastUpdate - 2019. 2. 17.
	 */
	public void editItem(BoardList params) throws Exception;
	
	/**
	 * 게시물 1개 삭제
	 * @MethodName - removeItem
	 * @param params - BoardList의 boardId 값을 파라미터로 전달
	 * @return boolean - true 면 삭제 성공 / false 면 삭제 실패
	 * @throws NullPointerException
	 * @throws Exception
	 * @author	   - JEFFREY_OH
	 * @lastUpdate - 2019. 2. 17.
	 */
	public void removeItem(BoardList params) throws Exception;
	
	/**
	 * 게시물 1개 조회
	 * memberId와 userId를 BoardList 형태로 파라미터 전달
	 * boardId + memberId + userId 3개가 일치해야 함
	 * 일치할 경우 게시물 추가, 수정, 삭제 가능
	 * 조회수 1 증가
	 * @MethodName - getItem
	 * @param params - BoardList 형태로 저장된 값을 파라미터로 전달
	 * @return BoardList로 저장된 값들을 리턴
	 * @throws NullPointerException
	 * @throws Exception
	 * @author	   - JEFFREY_OH
	 * @lastUpdate - 2019. 2. 17.
	 */
	public BoardList getItem(BoardList params) throws Exception;
	
	/**
	 * 게시물 전체 조회 (페이징)
	 * @param params - 페이징 할 숫자만 파라미터를 전달
	 * @return BoardList 형태로 저장된 값들을 List<BoardList>로 리턴
	 * @throws NullPointerException
	 * @throws Exception
	 */
	public List<BoardList> getItemAll(BoardList params) throws Exception;
	
	/**
	 * 게시물 개수 구하기
	 * @MethodName - getCount
	 * @param params - 모든 경우의 수를 고려해서 개수를 구함
	 * @return int 형태로 카운트 리턴
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 3. 19.
	 */
	public int getCount(BoardList params) throws Exception;
	
	/**
	 * 조회수 1 증가시킨다.
	 * @MethodName - updateBoard
	 * @param params - 현재글에 대한 게시물 번호가 저장된 Beans
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 3. 27.
	 */
	public void updateBoard(BoardList params) throws Exception;
	
	/**
	 * 자신의 게시물인지 검사한다.
	 * @MethodName - selectDocumentCountByMemberId
	 * @param params
	 * @return int
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 4. 1.
	 */
	public int selectDocumentCountByMemberId(BoardList params) throws Exception;
	
	/**
	 * 회원과 게시물의 참조관계를 해제한다.
	 * @MethodName - updateDocumentMemberOut
	 * @param params
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 6.
	 */
	public void updateDocumentMemberOut(BoardList params) throws Exception;
	
	/**
	 * 게시물 좋아요 + 1
	 * @MethodName - editBoardByLike
	 * @param params
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 4. 9.
	 */
	public void editBoardByLikeUp(BoardList params) throws Exception;
	
	/**
	 * 게시물 좋아요 - 1
	 * @MethodName - editBoardByLikeDown
	 * @param params
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 4. 9.
	 */
	public void editBoardByLikeDown(BoardList params) throws Exception;
	
	/**
	 * 등급제를 위한 카운트
	 * @MethodName - countByMemberId
	 * @param params
	 * @return
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 13.
	 */
	public int countByMemberId(BoardList params) throws Exception;
	
	/**
	 * 고객센터 게시물 조회
	 * @MethodName - getBoardItemCenter
	 * @param params
	 * @return
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 4. 17.
	 */
	public List<BoardList> getBoardItemCenter(BoardList params) throws Exception;
	
	/**
	 * 고객센터 전체 게시물 조회
	 * @MethodName - getBoardListCenter
	 * @param params
	 * @return
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 4. 17.
	 */
	public List<BoardList> getBoardListCenter(BoardList params) throws Exception;
	
	/**
	 * 고객센터 전체 게시물 개수
	 * @MethodName - getCountCenter
	 * @param params
	 * @return
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 4. 17.
	 */
	public int getCountCenter(BoardList params) throws Exception;
	
	/**
	 * 마이페이지 내 게시글 목록 조회
	 * @MethodName - getItemListByMemberId
	 * @param params
	 * @return
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 4. 25.
	 */
	public List<BoardList> getItemListByMemberId(BoardList params) throws Exception;
	
	/**
	 * 공지사항 글 목록 조회
	 * @MethodName - getBoardNotice
	 * @param params
	 * @return
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 27.
	 */
	public List<BoardList> getBoardNotice() throws Exception;
	
	/**
	 * 공지사항 수정
	 * @MethodName - editNotice
	 * @param params
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 27.
	 */
	public void editNotice(BoardList params) throws Exception;
	
	/**
	 * 공지사항 상세 조회
	 * @MethodName - getBoardNoticeItem
	 * @param params
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 4. 27.
	 */
	public BoardList getBoardNoticeItem(BoardList params) throws Exception;
	
	/**
	 * 공지사항 공지 등록된 개수 조회
	 * @MethodName - getBoardNoticeCount
	 * @return
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 5. 8.
	 */
	public int getBoardNoticeCount() throws Exception;
	
	/**
	 * 공지사항 총 개수
	 * @MethodName - getBoardNoticeCountAll
	 * @return
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 5. 8.
	 */
	public int getBoardNoticeCountAll() throws Exception;
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public int getCountCenterByMemberId(BoardList params) throws Exception;
	

}