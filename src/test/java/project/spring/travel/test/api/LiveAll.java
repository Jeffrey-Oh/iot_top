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

import project.spring.travel.model.Live;
import project.spring.travel.service.LiveService;

/**
 * 실시간 항공 API 연동 및 DB에 데이터 저장
 * @author     - JEFFREY_OH
 * @lastupdate - 2019. 2. 13.
 * @filename   - Live.java
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class LiveAll {
	
	// 프로그램 로그 저장
	Logger logger = LoggerFactory.getLogger(CodeAll.class.getName());

	// DB session
	@Autowired
	SqlSession sqlSession;

	// Service 객체 생성
	@Autowired
	LiveService liveService;
	
	@Test
    public void LiveAPI() {

		// LiveBeans 형태의 List 결과를 담을 객체 생성
		List<Live> list = null;
		
		Live params = new Live();
		
		params.setLimitStart(0);
		params.setListCount(1500);

		try {
			// API 연동 및 DB 데이터 저장 후 데이터 조회
			liveService.getAPI(); // DB 새롭게 저장
			list = liveService.getItemAll(params);
//			list = liveService.liveByScAc(params); // 저장된 DB 데이터 호출
			
			// 조회한 데이터 추출
			for (int i = 0; i < list.size(); i++) {
				Live temp = list.get(i);
				logger.debug("조회된 데이터 >> " + temp.toString());
			}
		} catch (Exception e) {
			logger.error("데이터 저장에 실패했습니다." + e.getMessage());
		}
	}
}
