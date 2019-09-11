package project.spring.travel.service;

import java.util.List;

import project.spring.travel.model.AirSearch;

/**
 * 항공권 데이터 DB 추가
 * @author     - JEFFREY_OH
 * @lastupdate - 2019. 2. 13.
 * @filename   - SearchService.java
 */
public interface SearchService {
	
	/**
	 * 일련번호 초기화
	 * @MethodName - sqlReset
	 * @param airHot
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 5. 11.
	 */
	public void sqlReset(AirSearch airSearch) throws Exception;
	
	/**
	 * 항공권 임의 값 넣기
	 * @MethodName - addItem
	 * @param airSearch
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 4. 30.
	 */
	public void addSql(AirSearch airSearch) throws Exception;
	
	/**
	 * 항공권 편도 조회
	 * @MethodName - selectItemList
	 * @param airSearch
	 * @return
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 4. 30.
	 */
	public List<AirSearch> selectTicketOneWay(AirSearch airSearch) throws Exception;
	
	/**
	 * 항공권 검색 목록 총 개수
	 * @MethodName - getCountList
	 * @param airSearch
	 * @return
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 5. 1.
	 */
	public int getCountList(AirSearch airSearch) throws Exception;
	
}
