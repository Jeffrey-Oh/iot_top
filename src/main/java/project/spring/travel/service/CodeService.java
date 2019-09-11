package project.spring.travel.service;

import java.util.List;

import project.spring.travel.model.Code;

/**
 * 공항 코드 조회
 * @author     - JEFFREY_OH
 * @lastupdate - 2019. 2. 13.
 * @filename   - CodeService.java
 */
public interface CodeService {
	
	/**
	 * 공항 코드 API 연동하여 DB에 데이터 저장
	 * @MethodName - getAPI
	 * @throws NullPointerException
	 * @throws Exception
	 * @author	   - JEFFREY_OH
	 * @lastUpdate - 2019. 2. 13.
	 */
	public void getAPI() throws Exception;
	
	/**
	 * 데이터 조회
	 * @MethodName - getItemList
	 * @return 조회된 데이터 값을 List<Code> 객체로 리턴
	 * @throws NullPointerException
	 * @throws Exception
	 * @author	   - JEFFREY_OH
	 * @lastUpdate - 2019. 2. 13.
	 */
	public List<Code> getItemList() throws Exception;
}
