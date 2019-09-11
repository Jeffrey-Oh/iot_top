package project.spring.travel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import project.spring.travel.model.CulturePerformance;

/**
 * 문화 공연 정보를 조회, 저장하는 서비스
 * @author     - 임형진
 * @lastUpdate - 2019. 5. 01.
 * @fileName   - CulturePerformanceService.java
 */
@Service
public interface CulturePerformanceService {
	/**
	 * 문화 공연 API를 받아와서 DB에 저장하는 서비스
	 * @param culturePerformance
	 * @throws Exception
	 */
	public void insertCulturePerformance(CulturePerformance culturePerformance) 
		throws Exception;
	/**
	 * 문화 공연 리스트를 불러오는 서비스
	 * @param culturePerformanceService
	 * @return
	 * @throws Exception
	 */
	public List<CulturePerformance> selectCulturePerformanceList(CulturePerformance culturePerformance) 
		throws Exception;
	/**
	 * 문화 공연 아이템을 불러오는 서비스
	 * @param culturePerformance
	 * @return
	 * @throws Exception
	 */
	public CulturePerformance selectCulturePerformanceItem(CulturePerformance culturePerformance) 
		throws Exception;
	
	/**
	 * 문화 공연 DB의 총 개수를 리턴하는 서비스(페이지 구현용)
	 * @return 
	 * @throws Exception
	 */
	public int countCulturePerformanceList(CulturePerformance culturePerformance)
		throws Exception;
	
	
	/**
	 * 좋아요 개수를 증가시키는 서비스
	 * @throws Exception
	 */
	public void updateCulturePerfByLikeUp(CulturePerformance culturePerformance)
		throws Exception;
	
	/**
	 * 좋아요 개수를 감소시키는 서비스
	 * @throws Exception
	 */
	public void updateCulturePerfByLikeDown(CulturePerformance culturePerformance)
		throws Exception;
	
	/**
	 * 좋아요 개수를 세는 서비스
	 * @param culturePerformance
	 * @return
	 */
	public CulturePerformance selectCultureCount(CulturePerformance culturePerformance)
		throws Exception;
	
	/**
	 * 처음과 끝 조회
	 * @MethodName - selectCulturePerformanceItem
	 * @param culturePerformance
	 * @return
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 5. 10.
	 */
	public CulturePerformance selectCulturePerformanceItemFL(CulturePerformance culturePerformance) 
			throws Exception;
}
