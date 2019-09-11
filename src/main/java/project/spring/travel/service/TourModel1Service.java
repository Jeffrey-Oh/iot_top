/**
 * @Author: choseonjun
 * @Date:   2019-04-10T15:03:37+09:00
 * @Email:  seonjun92@naver.com
 * @ProjectName:
 * @Filename: TourModel1Service.java
 * @Last modified by:   choseonjun
 * @Last modified time: 2019-04-10T17:32:19+09:00
 */

package project.spring.travel.service;

import java.util.List;

import project.spring.travel.model.BoardList;
import project.spring.travel.model.TourModel1;



public interface TourModel1Service {

	/**
	 * API DB에 저장
	 * [ApiDB description]
	 * @method ApiDB
	 * @throws Exception [description]
	 */
	public void ApiDB() throws Exception;

	/**
	 * 투어 데이터 전체 조회.
	 * @param params
	 * @return 조회된 데이터 값을 List<DomesticBeans> 객체로 리턴.
	 * @throws Exception
	 */
	public List<TourModel1> getTourList() throws Exception;
	
	/**투어 데이터 DB 업데이트.
	 * 
	 * @throws Exception
	 */
	public  void updateTour() throws Exception;
	
	
	/**
	 *  투어 메인 이미지 데이터 
	 * @return
	 * @throws Exception
	 */
	public List<BoardList> TourImageList() throws Exception;
	
	/**
	 *  투어 선택지역 조회 
	 * @return
	 * @throws Exception
	 */
	public List<TourModel1> getTourViewList(TourModel1 param) throws Exception;
	
	/**
	 * 투어 메인 뷰어 조회 
	 * @return
	 * @throws Exception
	 */
	public List<TourModel1> getTourMainList() throws Exception;
	
	
	/**
	 * 투어 뷰어를 상세조회 한다.
	 * @throws Exception
	 */
	public TourModel1 getTouritem(TourModel1 param) throws Exception;
	
	/**
	 * 투어 info 정보를 추출.
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public TourModel1 getTourinformation(TourModel1 param) throws Exception;
	
	/**
	 * 업데이트 info 
	 * @param item2
	 * @throws Exception
	 */
	public void UpTourView(TourModel1 item2) throws Exception; 
	
	/**
	 *  info 추출
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public TourModel1 getTourMaininfo (TourModel1 param) throws Exception;



	
	
	

}
