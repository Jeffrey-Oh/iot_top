package project.spring.travel.service.impl;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.spring.helper.HttpHelper;
import project.spring.helper.XmltoJsonHelper;
import project.spring.travel.model.Code;
import project.spring.travel.model.Live;
import project.spring.travel.service.CodeService;
import project.spring.travel.service.LiveService;

/**
 * 실시간 항공 API 연동 후 DB 저장 및 데이터 조회
 * 
 * @author - JEFFREY_OH
 * @lastupdate - 2019. 2. 13.
 * @filename - LiveServiceImpl.java
 */

@Service
public class LiveServiceImpl implements LiveService {

	private static Logger logger = LoggerFactory.getLogger(CodeServiceImpl.class); 

	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	HttpHelper httpHelper;
	
	@Autowired
	XmltoJsonHelper XMLHelper;
	
	@Autowired
	CodeService codeService;
	
	private int backUpGroupId = 1;

	@SuppressWarnings("static-access")
	@Override
	public void getAPI() throws Exception {
		
		Calendar cal = Calendar.getInstance();
		cal.set(cal.MINUTE, -120);
		Date date = new Date();
		date.setTime(cal.getTimeInMillis());
		String pattern = "HHmm";
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		
		int timeChk = Integer.parseInt(format.format(date));
		logger.warn("timeChk >>> " + timeChk);
		
		String timeChange = "";
		
		if (timeChk == 2200 || timeChk == 2300 || timeChk == 0 || timeChk == 100) {
			timeChange = "0000";
		} else {
			timeChange = "" + format.format(date);
		}
		
		logger.warn("timeChange >>> " + timeChange);

		String url = "http://openapi.airport.co.kr/service/rest/FlightStatusList/getFlightStatusList"
				+ "?serviceKey=fYBhG2Kslxd%2F0QukHv2mRRXzz%2BIqWHqdezWmeCEsI2IfSUBQ2BQcAmXjZzvsA0vaVkcz6uD%2BmAGY58MjtsJcXQ%3D%3D&schLineType=D&schStTime=" + timeChange + "&schEdTime=2400&numOfRows=10000";

		String encType = "UTF-8";
		
		long time = System.currentTimeMillis();

		InputStream is = httpHelper.getWebData(url, encType, null);
		JSONObject json = XMLHelper.getJSONObject(is, encType);
		JSONObject response = json.getJSONObject("response");
		JSONObject body = response.getJSONObject("body");
		if (!body.has("items")) {
			boolean itemsChk = false;
			
			while(!itemsChk) {
				int cnt = 1;
				logger.warn("items 없음 >>> " + cnt + "번째 실행");
				is = httpHelper.getWebData(url, encType, null);
				json = XMLHelper.getJSONObject(is, encType);
				response = json.getJSONObject("response");
				body = response.getJSONObject("body");
				
				if (body.has("items")) {
					logger.warn("items 있음 >>> " + cnt + "번 추가 실행 됨");
					itemsChk = true;
				}
			}
		}
		JSONObject items = body.getJSONObject("items");
		JSONArray item = items.getJSONArray("item");
		
		List<Code> list = codeService.getItemList();
		Map<String, String> codeMap = new HashMap<String, String>();
		
		for (int i=0; i<list.size(); i++) {
			codeMap.put(list.get(i).getCityKor(), list.get(i).getCityCode());
		}

		for (int i = 0; i < item.length(); i++) {
			JSONObject temp = item.getJSONObject(i);

			Live lb = new Live();

			lb.setAirlineKorean(temp.getString("airlineKorean"));
			lb.setAirFln(temp.getString("airFln"));
			lb.setBoardingKor(temp.getString("boardingKor"));
			
			String tempCode = codeMap.get(temp.getString("boardingKor"));
			if (!tempCode.equals(temp.getString("airport"))) {
				lb.setAirport(temp.getString("city"));
				lb.setCity(temp.getString("airport"));
			} else {
				lb.setAirport(temp.getString("airport"));
				lb.setCity(temp.getString("city"));
			}
			
			lb.setArrivedKor(temp.getString("arrivedKor"));
			
			if (!(item.getJSONObject(i).isNull("std"))) {
				lb.setStd(String.valueOf(temp.get("std")).substring(0, 2) + ":" + String.valueOf(temp.get("std")).substring(2));
			}
			if (!(item.getJSONObject(i).isNull("etd"))) {
				lb.setEtd(String.valueOf(temp.get("etd")).substring(0, 2) + ":" + String.valueOf(temp.get("etd")).substring(2));
			}
			if (!(item.getJSONObject(i).isNull("rmkKor"))) {
				lb.setRmkKor(temp.getString("rmkKor"));
			}
			
			lb.setTime(time);
			lb.setGroupId(backUpGroupId);
			
			try {
				sqlSession.insert("LiveMapper.addLive", lb);
			} catch (Exception e) {
				logger.warn("API JSON >>> " + temp.toString());
				logger.error(e.getLocalizedMessage());
				throw new Exception();
			}
			
		}
		
		backUpGroupId += 1;
		
		if (backUpGroupId == 3) {
			backUpGroupId = 1;
		}
		
	}

	@Override
	public List<Live> getItemAll(Live params) throws Exception {
		List<Live> result = null;

		try {
			result = sqlSession.selectList("LiveMapper.getLiveList", params);
			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			throw new Exception("조회된 데이터가 없습니다.");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new Exception("데이터 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public int getCount(Live params) throws Exception {
		int result = 0;
		try {
			result = sqlSession.selectOne("LiveMapper.getCount", params);
		} catch (Exception e) {
			// 에러가 발생했으므로 SQL 수행 내역을 되돌림
			throw new Exception("데이터 통신에 실패했습니다.");
		}
		return result;
	}

	@Override
	public void resetSql() throws Exception {
		try {
			sqlSession.update("LiveMapper.resetSql");
		} catch (Exception e) {
			// 에러가 발생했으므로 SQL 수행 내역을 되돌림
			logger.warn("일련번호 초기화에 실패했습니다.");
			throw new Exception("일련번호 초기화에 실패했습니다.");
		}
	}

	@Override
	public void deleteAll() throws Exception {
		try {
			sqlSession.delete("LiveMapper.deleteAll");
		} catch (Exception e) {
			// 에러가 발생했으므로 SQL 수행 내역을 되돌림
			logger.warn("데이터 전체 삭제에 실패했습니다.");
			throw new Exception("데이터 전체 삭제에 실패했습니다.");
		}
	}
}
