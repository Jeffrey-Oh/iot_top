package project.spring.travel.test.api;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import project.spring.helper.Util;
import project.spring.travel.model.AirSearch;
import project.spring.travel.model.Code;
import project.spring.travel.service.CodeService;
import project.spring.travel.service.SearchService;


/**
 * 실시간 항공 API 연동 및 DB에 데이터 저장
 * @author     - JEFFREY_OH
 * @lastupdate - 2019. 2. 13.
 * @filename   - Live.java
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
public class AirSearchTemp {
	
	// 프로그램 로그 저장
	Logger logger = LoggerFactory.getLogger(AirSearchTemp.class.getName());
	
	@Autowired
	Util util;

	// DB session
	@Autowired
	SqlSession sqlSession;

	// Service 객체 생성
	@Autowired
	SearchService searchService;
	
	@Autowired
	CodeService codeService;
	
	private String iRandomMonth;
	private String iRandomDay;
	
	public String getiRandomMonth() {
		return iRandomMonth;
	}

	public String getiRandomDay() {
		return iRandomDay;
	}

	public void setCalendars()
    {
        int[] maxDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        
        int iRandomMonth = util.getRandom(0, 11);
        
        int dateIndex = maxDays[iRandomMonth];
        
        iRandomMonth += 1;
        
        if (iRandomMonth < 10) {
        	this.iRandomMonth = "0" + String.valueOf(iRandomMonth);
        } else {
        	this.iRandomMonth = String.valueOf(iRandomMonth);
        }
        this.iRandomDay = String.valueOf(util.getRandom(1, dateIndex));

    }
	
	@SuppressWarnings("static-access")
	@Test
    public void LiveAPI() {
		
		List<Code> list = null;
		try {
			list = codeService.getItemList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, String> codeMap = new HashMap<String, String>();
		
		for (int i=0; i<list.size(); i++) {
			codeMap.put(list.get(i).getCityKor(), list.get(i).getCityCode());
		}
		
		Map<String, String> airline = new HashMap<String, String>();
		
		airline.put("KE", "대한항공");
		airline.put("BX", "에어부산");
		airline.put("OZ", "아시아나항공");
		airline.put("7C", "제주항공");
		airline.put("LJ", "진에어");
		airline.put("ZE", "이스타항공");
		airline.put("TW", "티웨이항공");
		
		List<String> airFlnList = new ArrayList<String>();
		
		airFlnList.add("BX");
		airFlnList.add("OZ");
		airFlnList.add("LJ");
		airFlnList.add("ZE");
		airFlnList.add("7C");
		airFlnList.add("KE");
		airFlnList.add("TW");
		
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
		
		/** 일련번호 초기화 시작 */
		String sql = "ALTER TABLE air_search AUTO_INCREMENT=1";

		AirSearch airSearch = new AirSearch();
		airSearch.setSql(sql);
		
		try {
			searchService.sqlReset(airSearch);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
		}
		/** 일련번호 초기화 끝 */
		
		for (int i=0; i<10000; i++) {
		
			String startCity = city.get(util.getRandom(0, 9));
			String startCode = codeMap.get(startCity);
			if (startCity.equals("진주")) {
				startCode = "HIN";
			}
			String endCity = city.get(util.getRandom(0, 9));
			if (endCity.equals(startCity)) {
				boolean tempBool = false;
				
				while (!tempBool) {
					endCity = city.get(util.getRandom(0, 9));
					
					if (!endCity.equals(startCity)) {
						tempBool = true;
					}
				}
			}
			
			String endCode = codeMap.get(endCity);
			if (endCity.equals("진주")) {
				endCode = "HIN";
			}
			
			String logo = "logo_" + airFlnList.get(util.getRandom(0, 6));
			
			String airFln = logo.substring(5);
			
			String airlineKorean = airline.get(airFln);
			
			for (int j=0; j<4; j++) {
				airFln += util.getRandom(0, 6);
			}
		
		
			String std = "";
			
			std += util.getRandom(0, 2);
			
			if (std.equals("0") || std.equals("1")) {
				std += util.getRandom(0, 9) + ":" + util.getRandom(0, 5) + util.getRandom(0, 9);
			} else {
				std += util.getRandom(0, 3) + ":" + util.getRandom(0, 5) + util.getRandom(0, 9);
			}
			
			int hh = Integer.parseInt(std.substring(0, 2));
			int mm = Integer.parseInt(std.substring(3));
			
			Calendar cal = Calendar.getInstance();
			
			cal.set(cal.HOUR_OF_DAY, hh);
			cal.set(cal.MINUTE, mm);
			
			String newStd = "";
			
			if (cal.get(cal.HOUR_OF_DAY) < 10) {
				newStd += "0" + cal.get(cal.HOUR_OF_DAY);
			} else {
				newStd += cal.get(cal.HOUR_OF_DAY);
			}
			
			if (cal.get(cal.MINUTE) < 10) {
				newStd += ":0" + cal.get(cal.MINUTE);
			} else {
				newStd += ":" + cal.get(cal.MINUTE);
			}
			
			cal.add(cal.MINUTE, +55);
			
			String pattern = "a HH:mm";
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			String newEtd = (String)format.format(new Timestamp(cal.getTimeInMillis()));
			String amPm = newEtd.substring(0, 2);
			
			String price = util.getRandom(400, 1400)+"00";			
			
			setCalendars();
			
			// 김포 부산
			sql = "INSERT INTO air_search (airlineKorean, domesticNum, startCity, startCityCode, arrivalCity,"
					+ "arrivalCityCode, domesticStartTime, domesticArrivalTime, std, etd, price, logo) VALUES"
					+ "('" + airlineKorean + "', '" + airFln + "', '" + startCity + "', '" + startCode + "','"
					+ endCity + "', '" + endCode + "', '" + amPm + " " + newStd + "', '" + newEtd
					+ "', '2019-" + iRandomMonth + "-" + iRandomDay;
			
			setCalendars();
			
			sql += "', '2020-" + iRandomMonth + "-" + iRandomDay + "', " + price + ", '" + logo + "');";

			airSearch.setSql(sql);
			
			try {
				searchService.addSql(airSearch);
			} catch (Exception e) {
				logger.error("데이터 저장에 실패했습니다." + e.getMessage());
			}
		}
	}
}
