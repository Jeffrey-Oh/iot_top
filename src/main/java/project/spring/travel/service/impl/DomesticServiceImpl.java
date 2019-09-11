package project.spring.travel.service.impl;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

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
import project.spring.travel.model.Domestic;
import project.spring.travel.service.DomesticService;

/**
 * 출발지 | 도착지 | 항공사명 | 편명 선택 조회
 * 
 * @author - JEFFREY_OH
 * @lastupdate - 2019. 2. 13.
 * @filename - DomesticServiceImpl.java
 */
@Service
public class DomesticServiceImpl implements DomesticService {

	private static Logger logger = LoggerFactory.getLogger(CodeServiceImpl.class); 

	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	HttpHelper httpHelper;
	
	@Autowired
	XmltoJsonHelper XMLHelper;
	
	private int backUpGroupId = 1;

	@Override
	public void getAPI() throws Exception {

		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMdd");

		Calendar cal = Calendar.getInstance();

		String schDate = fm.format(cal.getTime());

		// 국내 스케줄
		String url = "http://openapi.airport.co.kr/service/rest/FlightScheduleList/getDflightScheduleList"
				+ "?serviceKey=fYBhG2Kslxd%2F0QukHv2mRRXzz%2BIqWHqdezWmeCEsI2IfSUBQ2BQcAmXjZzvsA0vaVkcz6uD%2BmAGY58MjtsJcXQ%3D%3D&schDate="
				+ schDate + "&numOfRows=1000";
		// &schDeptCityCode=GMP&schArrvCityCode=PUS GMP랑 PUS 부분에 변수 넣어서 검색
		String encType = "UTF-8";
		
		InputStream is = httpHelper.getWebData(url, encType, null);
		JSONObject json = XMLHelper.getJSONObject(is, encType);
		JSONObject response = json.getJSONObject("response");
		JSONObject body = response.getJSONObject("body");
		JSONObject items = body.getJSONObject("items");
		JSONArray item = items.getJSONArray("item");

		Code res = null;

		for (int i = 0; i < item.length(); i++) {
			JSONObject temp = item.getJSONObject(i);

			Domestic db = new Domestic();

			db.setAirlineKorean(temp.getString("airlineKorean"));
			db.setDomesticNum(temp.getString("domesticNum"));
			db.setStartCity(temp.getString("startcity"));
			res = sqlSession.selectOne("CodeMapper.getCodeOne", db.getStartCity());
			db.setStartCityCode(res.getCityCode());
			db.setArrivalCity(temp.getString("arrivalcity"));
			res = sqlSession.selectOne("CodeMapper.getCodeOne", db.getArrivalCity());
			db.setArrivalCityCode(res.getCityCode());
			db.setDomesticStartTime(String.valueOf(temp.get("domesticStartTime")).substring(0, 2) + ":" + String.valueOf(temp.get("domesticStartTime")).substring(2));
			db.setDomesticArrivalTime(String.valueOf(temp.get("domesticArrivalTime")).substring(0, 2) + ":" + String.valueOf(temp.get("domesticArrivalTime")).substring(2));
			db.setDomesticStdate(temp.getString("domesticStdate").substring(0, 10));
			db.setDomesticEddate(temp.getString("domesticEddate").substring(0, 10));
			db.setDomesticMon(temp.getString("domesticMon"));
			db.setDomesticTue(temp.getString("domesticTue"));
			db.setDomesticWed(temp.getString("domesticWed"));
			db.setDomesticThu(temp.getString("domesticThu"));
			db.setDomesticFri(temp.getString("domesticFri"));
			db.setDomesticSat(temp.getString("domesticSat"));
			db.setDomesticSun(temp.getString("domesticSun"));
			db.setLogo("logo_" + temp.getString("domesticNum").substring(0, 2));
			
			db.setGroupId(backUpGroupId);
			
			sqlSession.insert("DomesticMapper.addDomestic", db);
		}
		
		backUpGroupId += 1;
		logger.warn("backUpGroupId >>> " + backUpGroupId);
		
	}

	@Override
	public List<Domestic> getItemAll(Domestic params) throws Exception {
		List<Domestic> result = null;

		try {
			result = sqlSession.selectList("DomesticMapper.getDomesticList", params);
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
	public int getCount(Domestic params) throws Exception {
		int result = 0;
		try {
			result = sqlSession.selectOne("DomesticMapper.getCount", params);
		} catch (Exception e) {
			// 에러가 발생했으므로 SQL 수행 내역을 되돌림
			System.out.println(e.getLocalizedMessage());
			throw new Exception("데이터 통신에 실패했습니다.");
		}
		return result;
	}

}
