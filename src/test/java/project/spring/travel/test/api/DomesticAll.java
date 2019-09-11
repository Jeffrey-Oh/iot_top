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

import project.spring.travel.model.Domestic;
import project.spring.travel.service.DomesticService;

/**
 * 운항 스케줄 API 연동 및 DB에 데이터 저장
 * @author     - JEFFREY_OH
 * @lastupdate - 2019. 2. 13.
 * @filename   - DomesticAll.java
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class DomesticAll {
	
	// 프로그램 로그 저장
	Logger logger = LoggerFactory.getLogger(CodeAll.class.getName());
	
	// DB session
	@Autowired
	SqlSession sqlSession;
	
	// Service 객체 생성
	@Autowired
	DomesticService domesticService;
	
	@Test
    public void DomesticAPI() {
		
		// DomesticBeans 형태의 List 결과를 담을 객체 생성
		List<Domestic> list = null;
		
		Domestic params = new Domestic();
		
		params.setLimitStart(0);
		params.setListCount(1500);

		try {
			// API 연동 및 DB 데이터 저장 후 데이터 조회
			domesticService.getAPI();
			list = domesticService.getItemAll(params);
			
//			FileHelper f = FileHelper.getInstance();
//			String filePath = "C:/Users/OhEun/Downloads/1.csv";
//			String content = "";
//			String encType = "EUC-KR";
			
			// 조회한 데이터 추출
			for(int i = 0; i<list.size(); i++) {
				Domestic item = list.get(i);
//				content +=  item.getAirlineKorean() + ", " + "\n";
				logger.debug("조회된 데이터 >> " + item.toString());
			}
//			f.writeString(filePath, content, encType);
		} catch (Exception e) {
			logger.error("데이터 저장에 실패했습니다." + e.getMessage());
		}
	}
}
