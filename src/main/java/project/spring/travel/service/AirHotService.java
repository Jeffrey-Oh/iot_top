package project.spring.travel.service;

import java.util.List;

import project.spring.travel.model.AirHot;

public interface AirHotService {
	
	/**
	 * 일련번호 초기화
	 * @MethodName - sqlReset
	 * @param airHot
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 5. 11.
	 */
	public void sqlReset(AirHot airHot) throws Exception;
	
	/**
	 * 추천특가 임시 데이터 저장
	 * @MethodName - addSql
	 * @param airHot
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 5. 10.
	 */
	public void addSql(AirHot airHot) throws Exception;
	
	/**
	 * 추천특가 데이터 상세 조회
	 * @MethodName - getAirHotItem
	 * @param airHot
	 * @return
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 5. 10.
	 */
	public AirHot getAirHotItem(AirHot airHot) throws Exception;
	
	/**
	 * 추천특가 데이터 목록 조회
	 * @MethodName - getAirHotList
	 * @param airHot
	 * @return
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 5. 10.
	 */
	public List<AirHot> getAirHotList(AirHot airHot) throws Exception;
	
	/**
	 * 추천특가 총 개수
	 * @MethodName - getAirHotCnt
	 * @param airHot
	 * @return
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 5. 10.
	 */
	public int getAirHotCnt() throws Exception;
}
