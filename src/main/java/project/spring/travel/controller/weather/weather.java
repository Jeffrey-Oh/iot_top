package project.spring.travel.controller.weather;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.spring.helper.HttpHelper;
import project.spring.helper.JsonHelper;
import project.spring.helper.WebHelper;
import project.spring.travel.model.HotKeyword;
import project.spring.travel.model.Weather;
import project.spring.travel.service.HotKeywordService;
import project.spring.travel.service.WeatherService;

/**
 * Servlet implementation class test
 */
@Controller
public class weather {
    /** (1) 사용하고자 하는 Helper + Service 객체 선언 */
    // -> import org.slf4j.Logger;
    // -> import org.slf4j.LoggerFactory;
    Logger logger = LoggerFactory.getLogger(weather.class); // Log4j 객체 직접 생성
    // -> import org.apache.ibatis.session.SqlSession;
    @Autowired
    SqlSession sqlSession;
    // -> import project.spring.helper.WebHelper
    @Autowired
    WebHelper web;
    // -> import project.spring.travel.service.HotKeywordService;
    @Autowired
    HotKeywordService hotKeywordService;
    // -> import project.spring.helper.HttpHelper;
    @Autowired
    HttpHelper httpHelper;
    // -> import project.spring.helper.JsonHelper;
    @Autowired
    JsonHelper jsonHelper;
    @Autowired
    WeatherService weatherservice;
    
	@RequestMapping(value="/weather.do")
	public ModelAndView weatherIndex(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
	    /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        String location = web.getString("location", "Seoul"); // 지역 선택 값을 받아온다.
        if(location.equals("")) {
        	return web.redirect(null, "지역을 선택해 주세요.");
        } // End if
        // View에서 사용하기 위해 상태유지
        model.addAttribute("location", location); // 사용 연구 필요
        
        /** 인기검색어 영역 시작 */
        try {
            List<HotKeyword> hotKeywordList = null; // 조회된 검색어 리스트를 담을 리스트
            hotKeywordList = hotKeywordService.selectHotKeywordList(); // 최근 1달 검색어 조회
            // xss 처리
            for(int i = 0; i < hotKeywordList.size(); i++) {
                HotKeyword hotKeywordtemp = hotKeywordList.get(i);
                // 공격 코드를 막기 위한 처리
                hotKeywordtemp.setKeyword(web.convertHtmlTag(hotKeywordtemp.getKeyword()));
                if(hotKeywordtemp.getKeyword().indexOf("<script>") > -1) {
                    hotKeywordtemp.setKeyword(web.getString(null, hotKeywordtemp.getKeyword(), true));
                }
            }
            model.addAttribute("hotKeywordList", hotKeywordList); // View에서 사용하기 위해 등록
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        /** 인기검색어 영역 끝 */
        
        // 해당 지역을 검색하기 위해 Beans로 묶는다.
        Weather weather = new Weather();
        weather.setQuery(location);
        
        try {
        	List<Weather> weatherList = null;
        	
        	weatherList = weatherservice.selectWeatherList(weather);        	
        	String temp_min_array = "'최저온도',";
        	String temp = "'기온',";
        	String humidity_array = "'습도',";
        	String speed_array = "'풍속',";
        	String dt_txt_array = "";
        	for(int i = 0; i < weatherList.size(); i++) {
        		Weather weatherTemp = weatherList.get(i);
            	temp_min_array += "'" + weatherTemp.getTemp_min() + "'" + ",";
            	temp += "'" + weatherTemp.getTemp() + "'" + ",";
            	humidity_array += "'" + weatherTemp.getHumidity() + "'" + ",";
            	speed_array += "'" + weatherTemp.getSpeed() + "'" + ",";
            	int indexStr = weatherTemp.getDt_txt().indexOf(":");
            	String timeDay = weatherTemp.getDt_txt().substring(indexStr - 5, indexStr - 3);
            	String timeHour = weatherTemp.getDt_txt().substring(indexStr - 2, indexStr);
            	dt_txt_array += "'" + timeDay + "일" + timeHour + "시" + "'" + ",";
            }
        	temp_min_array = temp_min_array.substring(0, temp_min_array.length() -1);
        	temp = temp.substring(0, temp.length() -1);
        	humidity_array = humidity_array.substring(0, humidity_array.length() -1);
        	speed_array = speed_array.substring(0, speed_array.length() -1);
        	dt_txt_array = dt_txt_array.substring(0, dt_txt_array.length() -1);
        	
        	model.addAttribute("temp_min_array", temp_min_array);
        	model.addAttribute("temp_max_array", temp);
        	model.addAttribute("humidity_array", humidity_array);
        	model.addAttribute("speed_array", speed_array);
        	model.addAttribute("dt_txt_array", dt_txt_array);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
        
		return new ModelAndView("weather/weather");
	}
	
	@RequestMapping(value="/weather_ok.do")
	public String weatherOk(Locale locale, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		/** 컨텐츠 타입 명시 */
        response.setContentType("application/json");
	    /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        String query = web.getString("query"); // 지역 선택 값을 받아온다.
        
        // 해당 지역을 검색하기 위해 Beans로 묶는다.
        Weather weather = new Weather();
        weather.setQuery(query);
        Weather weatherMax = null;
        
        
        try {        	
        	weather = weatherservice.selectWeather(weather);
        	weatherMax = weatherservice.selectWeatherListMaxMin(weather);
        	/** 처리 결과를 JSON으로 출력하기 */
        	Map<String, Object> data = new HashMap<String, Object>();
    		float temp = weather.getTemp();
    		float temp_min = weather.getTemp_min();
        	float temp_max = weather.getTemp_max();
        	float maxTemp = weatherMax.getMaxTemp();
        	float minTemp = weatherMax.getMinTemp();
        	int humidity = weather.getHumidity();
        	String icon = weather.getIcon();
        	float speed = weather.getSpeed();
            data.put("rt", "OK");
            data.put("temp", temp);
            data.put("temp_min", temp_min);
            data.put("temp_max", temp_max);
            data.put("humidity", humidity);
            data.put("icon", icon);
            data.put("speed", speed);
            data.put("maxTemp", maxTemp);
            data.put("minTemp", minTemp);
            
            ObjectMapper mapper = new ObjectMapper();
            try {
                mapper.writeValue(response.getWriter(), data);
            } catch (JsonGenerationException e) {
                logger.debug(e.getLocalizedMessage());
                web.printJsonRt("알 수 없는 에러가 발생했습니다. 문의바랍니다.");
                return null;
            } catch (JsonMappingException e) {
                logger.debug(e.getLocalizedMessage());
                web.printJsonRt("알 수 없는 에러가 발생했습니다. 문의바랍니다.");
                return null;
            } catch (IOException e) {
                logger.debug(e.getLocalizedMessage());
                web.printJsonRt("알 수 없는 에러가 발생했습니다. 문의바랍니다.");
                return null;
            }
        	
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
			return null;
		}
        
		return null;
	}
}
