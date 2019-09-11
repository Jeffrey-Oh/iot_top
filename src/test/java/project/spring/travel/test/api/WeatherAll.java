package project.spring.travel.test.api;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import project.spring.helper.HttpHelper;
import project.spring.helper.JsonHelper;
import project.spring.travel.model.Weather;
import project.spring.travel.service.WeatherService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class WeatherAll {
	
	// 프로그램 로그 저장
	Logger logger = LoggerFactory.getLogger(CodeAll.class.getName());

	// DB session
	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	WeatherService weatherService;
	
	@Autowired
	HttpHelper httpHelper;
	
	@Autowired
	JsonHelper jsonHelper;
	
	@Test
	public void weatherApiInsertCron() {
    	// 날씨 초기화
    	try {
    		weatherService.deleteWeather();
		} catch (Exception e) {
			logger.warn(e.getLocalizedMessage());
		}
    	
    	String[] query = {"Seoul", "Hongsung", "Yangpyong", "Kwangju", "Kumi", "Gimcheon", "Seongnam",
    			"Kosong", "Ulsan", "Incheon", "Daegu", "Busan", "Kimhae", "Daejeon", "Muan", "Yeoju", "Kangwŏn-do", "Jeju", "Jeonju", "Chuncheon", "Cheongju"};
    	
    	for(int i = 0; i < query.length; i++) {
    		String url = "https://api.openweathermap.org/data/2.5/forecast?q=" + query[i] + "&units=metric&appid=e30d4696c920a51c37940402bc9cdf18";
    		InputStream is = httpHelper.getWebData(url, "utf-8");
    		JSONObject json = jsonHelper.getJSONObject(is, "utf-8");
    		String cod = "" + json.get("cod");
    		
    		if(cod.equals("200")) {
    			// json 배열까지 접근한다.
    			JSONArray list = json.getJSONArray("list");
    			
    			// 배열 데이터이므로 반복문 안에서 처리해야 한다.
    			// 배열의 길이만큼 반복한다.
    			for(int j = 0; j < list.length(); j++) {
    				// 배열의 j번째 JSON을 꺼낸다.
    				JSONObject listTemp = list.getJSONObject(j);
    				
    				JSONObject main = listTemp.getJSONObject("main");
    				// 데이터를 추출
    				float temp = main.getFloat("temp");
    				float temp_min = main.getFloat("temp_min");
    				float temp_max = main.getFloat("temp_max");
    				int humidity = main.getInt("humidity");
    				
    				JSONArray weather = listTemp.getJSONArray("weather");
    				JSONObject weatherTemp = weather.getJSONObject(0);
    				String icon = weatherTemp.getString("icon");
    				
    				JSONObject wind = listTemp.getJSONObject("wind");
    				float speed = wind.getFloat("speed");
    				
    				String dt_txt = listTemp.getString("dt_txt");
    				
    				// 추출한 데이터를 날씨 Beans에 주입
    				Weather weatherItem = new Weather();
    				weatherItem.setTemp(temp);
    				weatherItem.setTemp_min(temp_min);
    				weatherItem.setTemp_max(temp_max);
    				weatherItem.setHumidity(humidity);
    				weatherItem.setIcon(icon);
    				weatherItem.setSpeed(speed);
    				weatherItem.setDt_txt(dt_txt);
    				weatherItem.setQuery(query[i]);

    				try {
    					// 저장하기 위한 Service를 호출
    					weatherService.insertWeather(weatherItem);
    				} catch (Exception e) {
    					logger.warn(e.getLocalizedMessage());;
    				}
    			} // End for
    		} else {
    			logger.warn("날씨 API 통신 실패");
    		} // End if~else
    	} // End for
    } // End weatherApiInsertCron Method
}
