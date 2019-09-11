/**
 * @Author: choseonjun
 * @Date:   2019-04-11T17:50:50+09:00
 * @Email:  seonjun92@naver.com
 * @ProjectName:
 * @Filename: TourServiceApi.java
 * @Last modified by:   choseonjun
 * @Last modified time: 2019-04-11T18:29:09+09:00
 */

package project.spring.travel.test.api;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import project.spring.travel.model.TourModel1;
import project.spring.travel.service.TourModel1Service;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class TourServiceApi {

	// 프로그램 로그 저장
	Logger logger = LoggerFactory.getLogger(TourServiceApi.class.getName());
	
	// DB session
	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	TourModel1Service tourModel1Service;
	
	@Test
	public void main() {

		List<TourModel1> list = null;

		try {
			// 구현체 실행(저장) 후 데이터 조회.
			tourModel1Service.ApiDB();
			list = tourModel1Service.getTourList();

			if (list == null) {
				throw new NullPointerException(); 
			}

			for (int i = 0; i < list.size(); i++) {
				TourModel1 item = list.get(i);
				logger.debug("조회된 데이터 >>" + item.toString());
				
			}

		} catch (NullPointerException e) {
			// TODO: handle exception
			logger.error("저장된 데이터가 없습니다.");

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("데이터 저장에 실패했습니다." + e.getMessage());
		} 

	}

}
