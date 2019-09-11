package project.spring.travel.service.impl;

import java.io.InputStream;
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
import project.spring.travel.service.CodeService;

/**
 * 공항 코드 API 연동 후 DB 저장 후 데이터 조회
 * 
 * @author - JEFFREY_OH
 * @lastupdate - 2019. 2. 13.
 * @filename - CodeServiceImpl.java
 */
@Service
public class CodeServiceImpl implements CodeService {

	private static Logger logger = LoggerFactory.getLogger(CodeServiceImpl.class); 
	
	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	HttpHelper httpHelper;
	
	@Autowired
	XmltoJsonHelper XMLHelper;

	@Override
	public void getAPI() throws Exception {
		
		// API 연동 URL
		String url = "http://openapi.airport.co.kr/service/rest/AirportCodeList/getAirportCodeList"
				+ "?ServiceKey=fYBhG2Kslxd%2F0QukHv2mRRXzz%2BIqWHqdezWmeCEsI2IfSUBQ2BQcAmXjZzvsA0vaVkcz6uD%2BmAGY58MjtsJcXQ%3D%3D&numOfRows=955";

		// encType "UTF-8"
		String encType = "UTF-8";

		// HTTP 통신
		InputStream is = httpHelper.getWebData(url, encType, null);

		// 통신 결과 XML 형태를 JSON으로 변환
		JSONObject json = XMLHelper.getJSONObject(is, encType);
		JSONObject response = json.getJSONObject("response");
		JSONObject body = response.getJSONObject("body");
		JSONObject items = body.getJSONObject("items");
		JSONArray item = items.getJSONArray("item");

		// 결과의 변동사항을 카운트
		int cnt = 0;

		// JSON 데이터를 CodeBeans 형태로 추출
		for (int i = 0; i < item.length(); i++) {
			JSONObject temp = item.getJSONObject(i);
			Code model = new Code();
			model.setCityKor(temp.getString("cityKor"));
			model.setCityCode(temp.getString("cityCode"));

			try {
				cnt = sqlSession.insert("CodeMapper.addCode", model);
				
				if (cnt == 0) {
					// 저장된 행이 없다면 강제로 예외를 발생시킨다.
					// --> 이 예외를 처리 가능한 catch블록으로 제어가 이동한다.
					throw new NullPointerException();
				}
			} catch (NullPointerException e) {
				// 에러가 발생했으므로 SQL 수행 내역을 되돌림
				throw new Exception("저장된 데이터가 없습니다.");
			} catch (Exception e) {
				logger.error(e.getLocalizedMessage());
				throw new Exception("데이터 저장에 실패했습니다.");
			}
		}
	}

	@Override
	public List<Code> getItemList() throws Exception {
		// DB 전체 조회
		List<Code> result = null;

		try {
			result = sqlSession.selectList("CodeMapper.getCodeList");
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

}
