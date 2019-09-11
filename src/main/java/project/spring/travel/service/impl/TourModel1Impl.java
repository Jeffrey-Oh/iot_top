/**
 * @Author: choseonjun
 * @Date:   2019-04-10T15:06:25+09:00
 * @Email:  seonjun92@naver.com
 * @ProjectName:
 * @Filename: TourModel1Impl.java
 * @Last modified by:   choseonjun
 * @Last modified time: 2019-04-11T17:11:40+09:00
 */

package project.spring.travel.service.impl;



import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.spring.helper.HttpHelper;
import project.spring.helper.JsonHelper;
import project.spring.helper.WebHelper;
import project.spring.travel.model.BoardList;
import project.spring.travel.model.TourModel1;
import project.spring.travel.service.TourModel1Service;

@Service
public class TourModel1Impl implements TourModel1Service {

	private static Logger logger = LoggerFactory.getLogger(TourModel1Impl.class);

	@Autowired
	SqlSession sqlSession;

	@Autowired
	HttpHelper httpHelper;

	@Autowired
	JsonHelper jsonHelper;

	@Autowired
	WebHelper web;

	@Override
	public List<TourModel1> getTourList() throws Exception {
		// TODO Auto-generated method stub
		List<TourModel1> result = null;

		try {
			result = sqlSession.selectList("TourMapper.getTourList");

			if (result == null) {
				throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			// TODO: handle exception
			throw new Exception("조회된 데이터가 없습니다.");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getLocalizedMessage());
			throw new Exception("데이터 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public void ApiDB() throws Exception {
		// TODO Auto-generated method stub

		String[] keyValue = { "서울", "인천", "강원", "부산", "광주", "충남", "충북", "경상남도", "경상북도", "전라", "제주도", "대전" };

		for (int i = 0; i < keyValue.length; i++) {

			String addr = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchKeyword?ServiceKey=";
			String servicekey = "jlqwmvLJg1mfY2FrnI2dWSq624frmaOFZYLixlo7hXAGug%2B0dvGO%2B517BZKAJJ3Deq8nVKkVjOLCwVmOmg8Cqg%3D%3D";
			String parameter = "";
//		String keyword = request.getParameter("keyword");
			// String keyword = "";
//			String addr2 = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailImage?serviceKey="
//					+ "jlqwmvLJg1mfY2FrnI2dWSq624frmaOFZYLixlo7hXAGug%2B0dvGO%2B517BZKAJJ3Deq8nVKkVjOLCwVmOmg8"
//					+ "Cqg%3D%3D&numOfRows=30&pageNo=1&MobileOS=ETC&MobileApp=AppTest&contentId=1095732&ima"
//					+ "geYN=Y&subImageYN=Y&_type=json";

			parameter += "&" + "MobileOS=ETC";
			parameter += "&" + "MobileApp=AppTest";
			parameter += "&" + "numOfRows=30";
			parameter += "&" + "keyword=" + URLEncoder.encode(keyValue[i], "UTF-8");

			// parameter += "&" + "keyword=" + URLEncoder.encode("서울", "UTF-8");

			// parameter += "&" + "keyword=" + URLEncoder.encode(keyword, "UTF-8");

			/* parameter = parameter + "&" + "numOfRows=10"; */
			// parameter = parameter + "&" + "listYN=Y";
			// parameter = parameter + "&" + "arrange=A";
			parameter += "&" + "_type=json";

			addr = addr + servicekey + parameter;
			System.out.println(addr);
			System.out.println(addr);
			System.out.println(addr);

			InputStream is = httpHelper.getWebData(addr, "UTF-8", null);

			if (is == null) {
				System.out.println("is1 데이터 다운로드 실패.");
			}

			JSONObject json = jsonHelper.getJSONObject(is, "UTF-8");
			System.out.println(json);
			JSONObject res = json.getJSONObject("response");
			System.out.println(res);
			JSONObject body = res.getJSONObject("body");
			System.out.println(body);
			JSONObject items = body.getJSONObject("items");
			System.out.println(items);
			JSONArray item = items.getJSONArray("item");

			// InputStream is2 = httpHelper.getWebData(addr2, "UTF-8", null);

			// if (is == null) {
			// System.out.println("is2 데이터 다운로드 실패.");
			// }

			// JSONObject json2 = jsonHelper.getJSONObject(is2, "UTF-8");

			// JSONObject res2 = json2.getJSONObject("response");

			// JSONObject body2 = res2.getJSONObject("body");

			// JSONObject items2 = body2.getJSONObject("items");

			// JSONArray item2 = items2.getJSONArray("item");

			// 변동사항 카운트
			int cnt = 0;

			for (int j = 0; j < item.length(); j++) {
				JSONObject obj = item.getJSONObject(j);

				// JSONObject obj2 = item2.getJSONObject(i);

				TourModel1 model = new TourModel1();

				// model.setFirstimage3(obj2.getString("smallimageurl"));

				if (obj.has("addr1")) {

					model.setAddr1(obj.getString("addr1"));
				} else {
					model.setAddr1("주소가 없습니다.");
				}

				if (obj.has("addr2")) {

					model.setAddr2(obj.getString("addr2"));
				} else {
					model.setAddr2("주소가 없습니다.");
				}

				model.setAreacode(obj.getInt("areacode"));

				/* 다른API 값 */
//			model.setTuMapInfo(obj.getInt("tuMapInfo"));

				model.setContentid(obj.getInt("contentid"));
				model.setCreatedtime(obj.getLong("createdtime"));

				if (obj.has("firstimage")) {
					model.setFirstimage(obj.getString("firstimage"));
				} else {
					model.setFirstimage("https://jigsawoosh.com.au/Content/Admin/images/no-image.jpg");
				}

				if (obj.has("firstimage2")) {
					model.setFirstimage2(obj.getString("firstimage2"));
				} else {
					model.setFirstimage2("");

				}

				if (obj.has("mlevel")) {
					model.setMlevel(obj.getInt("mlevel"));
				} else {
					model.setMlevel(0);
				}

				model.setModifiedtime(obj.getLong("modifiedtime"));

//			model.setTuInformationText(obj.getString(""));

				if (obj.has("tel")) {
					model.setTel(obj.getString("tel"));
				} else {
					model.setTel("전화번호가 없습니다.");
				}

				model.setTitle(obj.getString("title"));
				model.setTuInformationText(null);

				try {
					cnt = sqlSession.insert("TourMapper.updateTour", model);

					if (cnt == 0) {
						cnt = sqlSession.insert("TourMapper.addTour", model);
						if (cnt == 0) {
							throw new NullPointerException();
						}
					}

				} catch (NullPointerException e) {
					// TODO: handle exception
//				sqlSession.rollback();
					throw new Exception("저장된 데이터가 없습니다.");

				} catch (Exception e) {
					// TODO: handle exception
					logger.error(e.getLocalizedMessage());
//				sqlSession.rollback();
					throw new Exception("데이터 저장에 실패했습니다.");
				} /*
					 * finally { // DB저장 sqlSession.commit(); }
					 */
			}
		}

	}

	@Override
	public void updateTour() throws Exception {

	}

	@Override
	public List<BoardList> TourImageList() throws Exception {
		// TODO Auto-generated method stub
		List<BoardList> result = null;

		try {
			result = sqlSession.selectList("TourImageMapper.getTourImageList");

			if (result == null) {
				throw new NullPointerException();
			}

		} catch (NullPointerException e) {
			// TODO: handle exception
			throw new Exception("조회된 데이터가 없습니다.");
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("데이터 조회에 실패했습니다.");
		}

		return result;
	}

	@Override
	public List<TourModel1> getTourViewList(TourModel1 param) throws Exception {
		// TODO Auto-generated method stub

		List<TourModel1> result = null;

		try {
			result = sqlSession.selectList("TourMapper.getTourViewList", param);

			if (result == null) {
				throw new NullPointerException();
			}

		} catch (NullPointerException e) {
			// TODO: handle exception
			throw new Exception("조회된 데이터가 없습니다.");
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("데이터 조회에 실패했습니다.");
		}

		return result;

	}

	@Override
	public List<TourModel1> getTourMainList() throws Exception {
		// TODO Auto-generated method stub

		List<TourModel1> result = null;

		try {
			result = sqlSession.selectList("TourMapper.getTourMainList");

			if (result == null) {
				throw new NullPointerException();
			}

		} catch (NullPointerException e) {
			// TODO: handle exception
			throw new Exception("조회된 데이터가 없습니다.");
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("데이터 조회에 실패했습니다.");
		}

		return result;

	}

	@Override
	public TourModel1 getTouritem(TourModel1 param) throws Exception {
		// TODO Auto-generated method stub

		TourModel1 result = null;

		try {
			result = sqlSession.selectOne("TourMapper.getTouritem", param);
			if (result == null) {

				throw new NullPointerException();
			}

		} catch (NullPointerException e) {
			// TODO: handle exception
			throw new Exception("조회된 데이터가 없습니다.");
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("데이터 조회에 실패했습니다.");
		}

		return result;

	}

	@Override
	public TourModel1 getTourinformation(TourModel1 param) throws Exception {
		// TODO Auto-generated method stub

		TourModel1 result = new TourModel1();

		String addr = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon?serviceKey=";
		String servicekey = "jlqwmvLJg1mfY2FrnI2dWSq624frmaOFZYLixlo7hXAGug%2B0dvGO%2B517BZKAJJ3Deq8nVKkVjOLCwVmOmg8Cqg%3D%3D";
		String parameter = "";
//	String keyword = request.getParameter("keyword");
		// String keyword = "";

		parameter += "&" + "numOfRows=10";
		parameter += "&" + "pageNo=1";
		parameter += "&" + "MobileOS=ETC";
		parameter += "&" + "MobileApp=AppTest";
		parameter += "&" + "numOfRows=30";
		/* 전달되는값 */
		parameter += "&" + "contentId=" + param.getContentid();
		parameter += "&" + "overviewYN=Y";
		parameter += "&" + "defaultYN=Y";
		parameter += "&" + "_type=json";

		addr = addr + servicekey + parameter;

		InputStream is = httpHelper.getWebData(addr, "UTF-8", null);

		if (is == null) {
			System.out.println("is1 데이터 다운로드 실패.");
		}

		JSONObject json = jsonHelper.getJSONObject(is, "UTF-8");
		// System.out.println(json);
		JSONObject res = json.getJSONObject("response");
		// System.out.println(res);
		JSONObject body = res.getJSONObject("body");
		// System.out.println(body);
		JSONObject items = body.getJSONObject("items");
		System.out.println("JSONObject=" + items);
		JSONObject item = items.getJSONObject("item");
		System.out.println("JSONArray=" + item);

		for (int i = 0; i < item.length(); i++) {

			// JSONObject obj = item.getJSONObject(i);
			System.out.println("obj=" + item);

			//TourModel1 model = new TourModel1();

			if (item.has("overview")) {

				result.setTuInformationText(item.getString("overview"));
				result.setContentid(item.getInt("contentid"));

			} else {
				result.setTuInformationText("정보가 없습니다.");
			}

             //model = result.setTuInformationText(tuInformationText);
		}
		  return result;
	
	}

	@Override
	public void UpTourView(TourModel1 item2) throws Exception {
		// TODO Auto-generated method stub
		
		try {
			sqlSession.update("TourMapper.UpTourView", item2);
		} catch (Exception e) {
			// TODO: handle exception
			
			
		}
		
	}

	@Override
	public TourModel1 getTourMaininfo(TourModel1 param) throws Exception {
		// TODO Auto-generated method stub
		TourModel1 result = null;

		try {
			result = sqlSession.selectOne("TourMapper.getTourMaininfo", param);

		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("데이터 조회에 실패했습니다.");
		}

		
		return result;
		
		
	
	}
}
