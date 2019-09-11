package project.spring.travel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import project.spring.travel.model.CultureFestival;

@Service
public interface CultureFestivalService {
	
	/**
	 * 문화 축제 API를 받아와서 DB에 저장하는 서비스
	 * @param cultureFestival
	 * @throws Exception
	 */
	public void insertCultureFestival(CultureFestival cultureFestival) 
		throws Exception;
	
	public List<CultureFestival> selectCultureFestivalList(CultureFestival cultureFestival)
		throws Exception;
	

	
	public CultureFestival selectCultureFestivalItem(CultureFestival cultureFestival)
		throws Exception;
	
	/**
	 * 문화 공연 DB의 총 개수를 리턴하는 서비스(페이지 구현용)
	 * @return 
	 * @throws Exception
	 */
	public int countCultureFestivalList(CultureFestival cultureFestival)
		throws Exception;
	
	
	/**
	 * 좋아요 개수를 증가시키는 서비스
	 * @throws Exception
	 */
	public void updateCultureFestByLikeUp(CultureFestival cultureFestival)
		throws Exception;
	
	/**
	 * 좋아요 개수를 감소시키는 서비스
	 * @throws Exception
	 */
	public void updateCultureFestByLikeDown(CultureFestival cultureFestival)
		throws Exception;
	
	/**
	 * 좋아요 개수를 세는 서비스
	 * @param cultureFestival
	 * @return
	 */
	public CultureFestival selectCultureCount(CultureFestival cultureFestival)
		throws Exception;
	
	/**
	 * 처음과 끝 조회
	 * @MethodName - selectCultureFestivalItem
	 * @param cultureFestival
	 * @return
	 * @throws Exception
	 * @author     - Administrator
	 * @lastUpdate - 2019. 5. 10.
	 */
	public CultureFestival selectCultureFestivalItemFL(CultureFestival cultureFestival) 
			throws Exception;
}
