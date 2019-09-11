//package project.spring.travel.cron;
//
//import java.io.InputStream;
//
//import org.apache.ibatis.session.SqlSession;
//import org.json.JSONArray;
//import org.json.JSONObject;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Controller;
//
//import project.spring.helper.HttpHelper;
//import project.spring.helper.JsonHelper;
//import project.spring.travel.model.Weather;
//import project.spring.travel.service.WeatherService;
//
//@Controller
//public class WeatherCronController {
//    /** cron 설정 시간 */
//    // 휴게소 API 정보 추가를 위한 액션 컨트롤러 (cron="0 0 0 * * ?")                 매일 00시
//    
//    /** (1) 사용하고자 하는 Helper + Service 객체 선언 */
//    // -> import org.slf4j.Logger;
//    // -> import org.slf4j.LoggerFactory;
//    Logger logger = LoggerFactory.getLogger(WeatherCronController.class); // Log4j 객체 직접 생성
//    // -> import org.apache.ibatis.session.SqlSession;
//    @Autowired
//    SqlSession sqlSession;
//    // -> import project.spring.helper.HttpHelper;
//    @Autowired
//    HttpHelper httpHelper;
//    // -> import project.spring.helper.JsonHelper;
//    @Autowired
//    JsonHelper jsonHelper;
//    @Autowired
//    WeatherService weatherservice;
//    
//    @Scheduled(cron="0 0 */3 * * ?")
//    public void weatherApiInsertCron() {
//    	// 날씨 초기화
//    	try {
//			weatherservice.deleteWeather();
//		} catch (Exception e) {
//			logger.warn(e.getLocalizedMessage());
//		}
//    	
//    	String[] query = {"Seoul", "Hongsung", "Yangpyong", "Kwangju", "Kumi", "Gimcheon", "Seongnam",
//    			"Kosong", "Ulsan", "Incheon", "Daegu", "Busan", "Kimhae", "Daejeon", "Muan", "Yeoju", "Kangwŏn-do", "Jeju", "Jeonju", "Chuncheon", "Cheongju"};
//    	
//    	for(int i = 0; i < query.length; i++) {
//    		String url = "https://api.openweathermap.org/data/2.5/forecast?q=" + query[i] + "&units=metric&appid=e30d4696c920a51c37940402bc9cdf18";
//    		InputStream is = httpHelper.getWebData(url, "utf-8");
//    		JSONObject json = jsonHelper.getJSONObject(is, "utf-8");
//    		String cod = "" + json.get("cod");
//    		
//    		if(cod.equals("200")) {
//    			// json 배열까지 접근한다.
//    			JSONArray list = json.getJSONArray("list");
//    			
//    			// 배열 데이터이므로 반복문 안에서 처리해야 한다.
//    			// 배열의 길이만큼 반복한다.
//    			for(int j = 0; j < list.length(); j++) {
//    				// 배열의 j번째 JSON을 꺼낸다.
//    				JSONObject listTemp = list.getJSONObject(j);
//    				
//    				JSONObject main = listTemp.getJSONObject("main");
//    				// 데이터를 추출
//    				float temp = main.getFloat("temp");
//    				float temp_min = main.getFloat("temp_min");
//    				float temp_max = main.getFloat("temp_max");
//    				int humidity = main.getInt("humidity");
//    				
//    				JSONArray weather = listTemp.getJSONArray("weather");
//    				JSONObject weatherTemp = weather.getJSONObject(0);
//    				String icon = weatherTemp.getString("icon");
//    				
//    				JSONObject wind = listTemp.getJSONObject("wind");
//    				float speed = wind.getFloat("speed");
//    				
//    				String dt_txt = listTemp.getString("dt_txt");
//    				
//    				// 추출한 데이터를 날씨 Beans에 주입
//    				Weather weatherItem = new Weather();
//    				weatherItem.setTemp(temp);
//    				weatherItem.setTemp_min(temp_min);
//    				weatherItem.setTemp_max(temp_max);
//    				weatherItem.setHumidity(humidity);
//    				weatherItem.setIcon(icon);
//    				weatherItem.setSpeed(speed);
//    				weatherItem.setDt_txt(dt_txt);
//    				weatherItem.setQuery(query[i]);
//
//    				try {
//    					// 저장하기 위한 Service를 호출
//    					weatherservice.insertWeather(weatherItem);
//    				} catch (Exception e) {
//    					logger.warn(e.getLocalizedMessage());;
//    				}
//    			} // End for
//    		} else {
//    			logger.warn("날씨 API 통신 실패");
//    		} // End if~else
//    	} // End for
//    } // End weatherApiInsertCron Method
//    
//}
