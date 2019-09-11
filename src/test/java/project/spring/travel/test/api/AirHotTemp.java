package project.spring.travel.test.api;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import project.spring.helper.Util;
import project.spring.travel.model.AirHot;
import project.spring.travel.service.AirHotService;

/**
 * 항공 추천특가 임시 데이터 DB에 저장
 * @author     - JEFFREY_OH
 * @lastupdate - 2019. 5. 10.
 * @filename   - Live.java
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class AirHotTemp {
	
	// 프로그램 로그 저장
	Logger logger = LoggerFactory.getLogger(AirHotTemp.class.getName());
	
	@Autowired
	Util util;

	// DB session
	@Autowired
	SqlSession sqlSession;

	// Service 객체 생성
	@Autowired
	AirHotService airHotService;
	
	@Test
	public void AirHot() {
		List<String> city = new ArrayList<String>();
		city.add("서울/김포");
		city.add("부산/김해");
		city.add("제주");
		city.add("광주");
		city.add("울산");
		city.add("포항");
		city.add("진주");
		city.add("여수");
		city.add("무안");
		city.add("대구");
		
		List<String> imageUrl = new ArrayList<String>();
		imageUrl.add("/images/air/hot_bg_5.jpg"); // 서울
		imageUrl.add("/images/air/hot_bg_2.jpg"); // 부산
		imageUrl.add("/images/air/hot_bg_1.jpg"); // 제주
		imageUrl.add("/images/air/hot_bg_11.png"); // 광주
		imageUrl.add("/images/air/hot_bg_12.jpg"); // 울산
		imageUrl.add("/images/air/hot_bg_13.jpg"); // 포항
		imageUrl.add("/images/air/hot_bg_14.jpg"); // 진주
		imageUrl.add("/images/air/hot_bg_15.jpg"); // 여수
		imageUrl.add("/images/air/hot_bg_16.jpg"); // 무안
		imageUrl.add("/images/air/hot_bg_17.png"); // 대구
		
		List<String> mainStrList = new ArrayList<String>();
		
		mainStrList.add("힐링이 필요하다면 ?");
		mainStrList.add("자유시간 확보 !");
		mainStrList.add("여행을 가볍게 !");
		mainStrList.add("감성과 흥이 충만한 도시");
		mainStrList.add("여행에 미치다 !");
		
		List<String> subStrList = new ArrayList<String>();
		
		subStrList.add("새로운 추억을 만들자 !!");
		subStrList.add("관광지로 떠나보자 ~ !");
		subStrList.add("맛있는 음식과 멋진 야경을 만나보세요 ♬");
		subStrList.add("지금 당장 떠나세요. 당신을 기다립니다.");
		subStrList.add("나중에 말고 지금 당장 찾아보고 떠나자 !!");
		
		/** 일련번호 초기화 시작 */
		String sql = "ALTER TABLE air_hot AUTO_INCREMENT=1";
		
		AirHot airHot = new AirHot();
		airHot.setSql(sql);
		
		try {
			airHotService.sqlReset(airHot);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
		}
		/** 일련번호 초기화 끝 */
		
		for (int i=0; i<1000; i++) {
			int randomIdx = util.getRandom(0, 9);
			
			String title = mainStrList.get(util.getRandom(0, 4));
			String content = subStrList.get(util.getRandom(0, 4)); 
			String imgUrl = imageUrl.get(randomIdx);
			String arrivalCity = city.get(randomIdx);
			
			sql = "INSERT INTO air_hot (title, content, imageUrl, arrivalCity)"
					+ " VALUES "
					+ "('" + title + "', '" + content + "', '" + imgUrl + "', '" + arrivalCity + "');";
			
			airHot = new AirHot();
			airHot.setSql(sql);
			
			try {
				airHotService.addSql(airHot);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getLocalizedMessage());
			}
		}
	}
}
