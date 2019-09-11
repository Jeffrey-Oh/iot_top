package project.spring.travel.service;

import java.util.List;

import project.spring.travel.model.Live;

/**
 * 실시간 항공 API 연동 후 DB 저장
 * 실시간 항공 DB 값 추출
 * @author     - JEFFREY_OH
 * @lastupdate - 2019. 2. 13.
 * @filename   - LiveService.java
 */
public interface LiveService {
	
	/**
	 * 실시간 항공 API 연동 및 DB 데이터 저장
	 * @MethodName - getAPI
	 * @throws NullPointerException
	 * @throws Exception
	 * @author	   - JEFFREY_OH
	 * @lastUpdate - 2019. 2. 13.
	 */
	public void getAPI() throws Exception;
	
	/**
	 * 실시간 항공 데이터 전체 조회
	 * @MethodName - getLive
	 * @return 조회된 데이터 값을 List<Live> 객체로 리턴
	 * @throws NullPointerException
	 * @throws Exception
	 * @author	   - JEFFREY_OH
	 * @lastUpdate - 2019. 2. 13.
	 */
	public List<Live> getItemAll(Live params) throws Exception;
	
	/**
	 * 총 개수
	 * @MethodName - getCount
	 * @param params
	 * @return
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 4. 22.
	 */
	public int getCount(Live params) throws Exception;
	
	/**
	 * 일련번호 리셋
	 * @MethodName - resetSql
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 5. 16.
	 */
	public void resetSql() throws Exception;
	
	/**
	 * 데이터 전체 삭제
	 * @MethodName - deleteAll
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 5. 16.
	 */
	public void deleteAll() throws Exception;
}
