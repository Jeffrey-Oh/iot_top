package project.spring.travel.service;

import java.util.List;

import project.spring.travel.model.Domestic;

/**
 * 출발지 | 도착지 | 항공사명 | 편명 선택 조회
 * @author     - JEFFREY_OH
 * @lastupdate - 2019. 2. 13.
 * @filename   - DomesticService.java
 */
public interface DomesticService {
	
	/**
	 * 운항 스케줄 API 연동하여 DB에 데이터 저장
	 * @MethodName - getAPI
	 * @throws NullPointerException
	 * @throws Exception
	 * @author	   - JEFFREY_OH
	 * @lastUpdate - 2019. 2. 13.
	 */
	public void getAPI() throws Exception;

	/**
	 * 운항 스케줄 데이터 전체 조회
	 * @MethodName - domesticScheduleAll
	 * @param params
	 * @return 조회된 데이터 값을 List<DomesticBeans> 객체로 리턴
	 * @throws NullPointerException
	 * @throws Exception
	 * @author     - JeffreyOh
	 * @lastUpdate - 2019. 3. 10.
	 */
	public List<Domestic> getItemAll(Domestic params) throws Exception;
	
	public int getCount(Domestic params) throws Exception;
}
