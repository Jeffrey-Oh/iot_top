package project.spring.travel.controller.culture;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.spring.helper.HttpHelper;
import project.spring.helper.JsonHelper;
import project.spring.helper.PageHelper;
import project.spring.helper.WebHelper;
import project.spring.helper.XmltoJsonHelper;
import project.spring.travel.model.CategoryLike;
import project.spring.travel.model.CultureFestival;
import project.spring.travel.model.Favorite;
import project.spring.travel.model.Member;
import project.spring.travel.service.CategoryLikeService;
import project.spring.travel.service.CultureFestivalService;
import project.spring.travel.service.FavoriteService;

@Controller
public class CultureFestivalController {
	
	Logger logger = LoggerFactory.getLogger(CultureFestivalController.class);
	
	@Autowired
	HttpHelper http;
	@Autowired
	XmltoJsonHelper xmltoJson;
	@Autowired
	HttpHelper httpHelper;
	@Autowired
	WebHelper web;
	@Autowired
	XmltoJsonHelper xmltoJsonHelper;
	@Autowired
	JsonHelper jsonHelper;
	@Autowired
	PageHelper pageHelper;
	@Autowired
	CultureFestivalService cultureFestivalService;
	@Autowired
	FavoriteService favoriteService;
	@Autowired
	CategoryLikeService categoryLikeService;
	
	@Scheduled(cron="0 0 07 * * ?")
	public void CultureFestivalAPIInsert() {
		String url = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchFestival?serviceKey=fYBhG2Kslxd%2F0QukHv2mRRXzz%2BIqWHqdezWmeCEsI2IfSUBQ2BQcAmXjZzvsA0vaVkcz6uD%2BmAGY58MjtsJcXQ%3D%3D&numOfRows=300&MobileOS=ETC&MobileApp=AppTest";
		InputStream is = httpHelper.getWebData(url, "utf-8");
		JSONObject json = xmltoJsonHelper.getJSONObject(is, "utf-8");
		
		
		JSONObject res = json.getJSONObject("response");
		JSONObject body = res.getJSONObject("body");
		JSONObject items = body.getJSONObject("items");
		JSONArray item = items.getJSONArray("item");

		for (int i=0; i<item.length(); i++) {
			logger.warn("contentId >>> " + item.get(i).toString());
		}

		// 배열 데이터이므로 반복문 안에서 처리해야 한다.
		// 배열의 길이만큼 반복한다.
		for (int i = 0; i < item.length(); i++) {
			// 배열의 i번째 JSON을 꺼낸다.
			JSONObject temp = item.getJSONObject(i);

			// 데이터를 추출
			int contentId = temp.getInt("contentid");
			logger.warn("contentId="+contentId );
			String title = temp.getString("title");
			int eventStartDate = temp.getInt("eventstartdate");
			int eventEndDate = temp.getInt("eventenddate");
			long createdTime = temp.getLong("createdtime");
			
			
			
			

			
			
			
			
			
			
			// 추출한 데이터를 휴게소 Beans에 주입
			CultureFestival culture = new CultureFestival();
			culture.setContentId(contentId);
			culture.setTitle(title);
			culture.setEventStartDate(eventStartDate);
			culture.setEventEndDate(eventEndDate);
			culture.setCreatedTime(createdTime);
			

			if (temp.has("tel")) {
				String tel = ""+ temp.get("tel");
				culture.setTel(tel);
			}
				
			
			if (temp.has("firstimage")) {
				String firstImage = temp.getString("firstimage");
				String firstImage2 = temp.getString("firstimage2");
				culture.setFirstImage(firstImage);
				culture.setFirstImage2(firstImage2);
			}
			if (temp.has("addr1")) {
				String addr1 = temp.getString("addr1");
				culture.setAddr1(addr1);
			}
			
			
			if (temp.has("addr2")) {
				
				String addr2 = "" + temp.get("addr2");
				culture.setAddr2(addr2);
			}
			
			String mapX = null;
			if (temp.has("mapx")) {
				mapX = "" + temp.get("mapx");
				culture.setMapX(mapX);
			}
			String mapY = null;
			if (temp.has("mapy")) {
				mapY = "" + temp.get("mapy");
				culture.setMapY(mapY);
			}
			
			
			
			
			String detailUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailInfo?serviceKey=fYBhG2Kslxd%2F0QukHv2mRRXzz%2BIqWHqdezWmeCEsI2IfSUBQ2BQcAmXjZzvsA0vaVkcz6uD%2BmAGY58MjtsJcXQ%3D%3D&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&contentTypeId=15&contentId="
					+ contentId;
			is = httpHelper.getWebData(detailUrl, "utf-8");
			json = xmltoJsonHelper.getJSONObject(is, "utf-8");
			
			
		
			
			JSONObject detailRes = json.getJSONObject("response");
			JSONObject detailBody = detailRes.getJSONObject("body");
			String temp1 = "" + detailBody.get("totalCount");
		if (!temp1.equals("0")) {
			
			
			JSONObject detailItems = detailBody.getJSONObject("items");
			
			if (detailItems.has("item")) {
				
				try {
					JSONArray detailItem = detailItems.getJSONArray("item");
					JSONObject detailItem1 = detailItem.getJSONObject(0);					  
					JSONObject detailItem2 = detailItem.getJSONObject(1); 
					String infoText2 = "" + detailItem2.get("infotext"); 
					culture.setInfoText2(infoText2); 
					String infoText = "" + detailItem1.get("infotext");
					culture.setInfoText(infoText);
				} catch (JSONException e) {
					logger.warn("" + detailItems.toString());
					try {
						JSONObject detailItem = detailItems.getJSONObject("item");
						String infoText = "" + detailItem.get("infotext");
						culture.setInfoText(infoText);
					} catch (JSONException e1) {
						
					}					


					

					
				}
				
				
			}
		}
			String detailUrl2 = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro?serviceKey=fYBhG2Kslxd%2F0QukHv2mRRXzz%2BIqWHqdezWmeCEsI2IfSUBQ2BQcAmXjZzvsA0vaVkcz6uD%2BmAGY58MjtsJcXQ%3D%3D&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&contentTypeId=15&contentId="
					+ contentId;

			is = httpHelper.getWebData(detailUrl2, "utf-8");
			json = xmltoJsonHelper.getJSONObject(is, "utf-8");
			

			JSONObject detail2Res = json.getJSONObject("response");
			JSONObject detail2Body = detail2Res.getJSONObject("body");
			JSONObject detail2Items = detail2Body.getJSONObject("items");
			JSONObject detail2Item = detail2Items.getJSONObject("item");
			
			if (detail2Item.has("agelimit")) {
				String ageLimit = detail2Item.getString("agelimit");
				culture.setAgeLimit(ageLimit);
			}
			if (detail2Item.has("spendtimefestival")) {
				String spendTimeFestival = detail2Item.getString("spendtimefestival");
				culture.setSpendTimeFestival(spendTimeFestival);
			}
			
			if (detail2Item.has("sponsor2")) {
				String sponsor2 = ""+ detail2Item.get("sponsor2");
				culture.setSponsor2(sponsor2);
			}
			
			if (detail2Item.has("sponsor2tel")) {
				String sponsor2Tel = "" + detail2Item.get("sponsor2tel");
				culture.setSponsor2Tel(sponsor2Tel);
			}
			
			if (detail2Item.has("placeinfo")) {
				String placeInfo = detail2Item.getString("placeinfo");
				culture.setPlaceInfo(placeInfo);
			}
			

			if (detail2Item.has("playtime")) {
				String playTime = detail2Item.getString("playtime");
				culture.setPlayTime(playTime);
			}
			
			if (detail2Item.has("program")) {
				String program = detail2Item.getString("program");
				culture.setProgram(program);
			}
			
			
			
			
			
			if (detail2Item.has("sponsor1")) {
				String sponsor1 = ""+ detail2Item.get("sponsor1");
				culture.setSponsor1(sponsor1);
			}
			
			if (detail2Item.has("sponsor1tel")) {
				String sponsor1Tel = "" + detail2Item.get("sponsor1tel");
				culture.setSponsor2Tel(sponsor1Tel);
			}
			

			
			
			
			
			
			try {
				cultureFestivalService.insertCultureFestival(culture);
			} catch (Exception e) {
				logger.error("데이터 저장에 실패했습니다." + e.getMessage());
			} // end try-catch

			
		} // End for
	}
	
	
	/**
	 * culture API 정보 추가를 위한 액션 컨트롤러
	 */
	@RequestMapping(value = "/culture/cultureFest_API_insert.do", method = RequestMethod.GET)
	public ModelAndView CultureFestivalAPIInsert(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		/** (2) WebHelper 초기화 */
		web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.

		/** (3) 로그인 여부 검사 */
		// 관리자가 아니라면 이 페이지를 동작시켜서는 안된다.
		Member loginInfo = (Member) web.getSession("loginInfo");

		if (loginInfo == null) {
			return web.redirect(web.getRootPath() + "/index.do", "관리자 계정만 접근 가능합니다.");
		}

		if (loginInfo.getGrade().equals("Master")) {
			return web.redirect(web.getRootPath() + "/index.do", "관리자 계정만 접근 가능합니다.");
		}

		/** (4) 문화 API를 연동하기 + Service를 통한 문화DB 저장 */

		
				String url = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchFestival?serviceKey=fYBhG2Kslxd%2F0QukHv2mRRXzz%2BIqWHqdezWmeCEsI2IfSUBQ2BQcAmXjZzvsA0vaVkcz6uD%2BmAGY58MjtsJcXQ%3D%3D&numOfRows=300&MobileOS=ETC&MobileApp=AppTest";
				InputStream is = httpHelper.getWebData(url, "utf-8");
				JSONObject json = xmltoJsonHelper.getJSONObject(is, "utf-8");
				
				
				JSONObject res = json.getJSONObject("response");
				JSONObject body = res.getJSONObject("body");
				JSONObject items = body.getJSONObject("items");
				JSONArray item = items.getJSONArray("item");

				for (int i=0; i<item.length(); i++) {
					logger.warn("contentId >>> " + item.get(i).toString());
				}

				// 배열 데이터이므로 반복문 안에서 처리해야 한다.
				// 배열의 길이만큼 반복한다.
				for (int i = 0; i < item.length(); i++) {
					// 배열의 i번째 JSON을 꺼낸다.
					JSONObject temp = item.getJSONObject(i);

					// 데이터를 추출
					int contentId = temp.getInt("contentid");
					logger.warn("contentId="+contentId );
					String title = temp.getString("title");
					int eventStartDate = temp.getInt("eventstartdate");
					int eventEndDate = temp.getInt("eventenddate");
					long createdTime = temp.getLong("createdtime");
					
					
					
					

					
					
					
					
					
					
					// 추출한 데이터를 휴게소 Beans에 주입
					CultureFestival culture = new CultureFestival();
					culture.setContentId(contentId);
					culture.setTitle(title);
					culture.setEventStartDate(eventStartDate);
					culture.setEventEndDate(eventEndDate);
					culture.setCreatedTime(createdTime);
					

					if (temp.has("tel")) {
						String tel = ""+ temp.get("tel");
						culture.setTel(tel);
					}
						
					
					if (temp.has("firstimage")) {
						String firstImage = temp.getString("firstimage");
						String firstImage2 = temp.getString("firstimage2");
						culture.setFirstImage(firstImage);
						culture.setFirstImage2(firstImage2);
					}
					if (temp.has("addr1")) {
						String addr1 = temp.getString("addr1");
						culture.setAddr1(addr1);
					}
					
					
					if (temp.has("addr2")) {
						
						String addr2 = "" + temp.get("addr2");
						culture.setAddr2(addr2);
					}
					
					String mapX = null;
					if (temp.has("mapx")) {
						mapX = "" + temp.get("mapx");
						culture.setMapX(mapX);
					}
					String mapY = null;
					if (temp.has("mapy")) {
						mapY = "" + temp.get("mapy");
						culture.setMapY(mapY);
					}
					
					
					
					
					String detailUrl = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailInfo?serviceKey=fYBhG2Kslxd%2F0QukHv2mRRXzz%2BIqWHqdezWmeCEsI2IfSUBQ2BQcAmXjZzvsA0vaVkcz6uD%2BmAGY58MjtsJcXQ%3D%3D&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&contentTypeId=15&contentId="
							+ contentId;
					is = httpHelper.getWebData(detailUrl, "utf-8");
					json = xmltoJsonHelper.getJSONObject(is, "utf-8");
					
					
				
					
					JSONObject detailRes = json.getJSONObject("response");
					JSONObject detailBody = detailRes.getJSONObject("body");
					String temp1 = "" + detailBody.get("totalCount");
				if (!temp1.equals("0")) {
					
					
					JSONObject detailItems = detailBody.getJSONObject("items");
					
					if (detailItems.has("item")) {
						
						try {
							JSONArray detailItem = detailItems.getJSONArray("item");
							JSONObject detailItem1 = detailItem.getJSONObject(0);					  
							JSONObject detailItem2 = detailItem.getJSONObject(1); 
							String infoText2 = "" + detailItem2.get("infotext"); 
							culture.setInfoText2(infoText2); 
							String infoText = "" + detailItem1.get("infotext");
							culture.setInfoText(infoText);
						} catch (JSONException e) {
							logger.warn("" + detailItems.toString());
							try {
								JSONObject detailItem = detailItems.getJSONObject("item");
								String infoText = "" + detailItem.get("infotext");
								culture.setInfoText(infoText);
							} catch (JSONException e1) {
								
							}					


							

							
						}
						
						
					}
				}
					String detailUrl2 = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro?serviceKey=fYBhG2Kslxd%2F0QukHv2mRRXzz%2BIqWHqdezWmeCEsI2IfSUBQ2BQcAmXjZzvsA0vaVkcz6uD%2BmAGY58MjtsJcXQ%3D%3D&numOfRows=10&pageNo=1&MobileOS=ETC&MobileApp=AppTest&contentTypeId=15&contentId="
							+ contentId;

					is = httpHelper.getWebData(detailUrl2, "utf-8");
					json = xmltoJsonHelper.getJSONObject(is, "utf-8");
					

					JSONObject detail2Res = json.getJSONObject("response");
					JSONObject detail2Body = detail2Res.getJSONObject("body");
					JSONObject detail2Items = detail2Body.getJSONObject("items");
					JSONObject detail2Item = detail2Items.getJSONObject("item");
					
					if (detail2Item.has("agelimit")) {
						String ageLimit = detail2Item.getString("agelimit");
						culture.setAgeLimit(ageLimit);
					}
					if (detail2Item.has("spendtimefestival")) {
						String spendTimeFestival = detail2Item.getString("spendtimefestival");
						culture.setSpendTimeFestival(spendTimeFestival);
					}
					
					if (detail2Item.has("sponsor2")) {
						String sponsor2 = ""+ detail2Item.get("sponsor2");
						culture.setSponsor2(sponsor2);
					}
					
					if (detail2Item.has("sponsor2tel")) {
						String sponsor2Tel = "" + detail2Item.get("sponsor2tel");
						culture.setSponsor2Tel(sponsor2Tel);
					}
					
					if (detail2Item.has("placeinfo")) {
						String placeInfo = detail2Item.getString("placeinfo");
						culture.setPlaceInfo(placeInfo);
					}
					

					if (detail2Item.has("playtime")) {
						String playTime = detail2Item.getString("playtime");
						culture.setPlayTime(playTime);
					}
					
					if (detail2Item.has("program")) {
						String program = detail2Item.getString("program");
						culture.setProgram(program);
					}
					
					
					
					
					
					if (detail2Item.has("sponsor1")) {
						String sponsor1 = ""+ detail2Item.get("sponsor1");
						culture.setSponsor1(sponsor1);
					}
					
					if (detail2Item.has("sponsor1tel")) {
						String sponsor1Tel = "" + detail2Item.get("sponsor1tel");
						culture.setSponsor2Tel(sponsor1Tel);
					}
					

					
					
					
					
					
					try {
						cultureFestivalService.insertCultureFestival(culture);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						return web.redirect(web.getRootPath() + "/culture/cultureFestival.do", e.getLocalizedMessage());
					} // end try-catch
	
					
				} // End for
			
		/** (5) 저장이 완료되었으므로 휴게소 메인페이지로 이동 */
		return web.redirect(web.getRootPath() + "/culture/cultureFestival.do", "저장이 완료되었습니다.");
		
	} // End cultureFestApiInsert Method
	
	
	@RequestMapping(value = "/culture/culture_fest_like.do", method = RequestMethod.POST)
    public ModelAndView cultureFestLike(Locale locale, Model model,
            HttpServletRequest request, HttpServletResponse response) {



    	/** 컨텐츠 타입 명시 */
        response.setContentType("application/json");

        /** WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.

        Member loginInfo = (Member) web.getSession("loginInfo");
        logger.debug("loginInfo = " + loginInfo);
        if(loginInfo == null) {
        	web.printJsonRt("X-LOGIN");
           return null;

        }


        logger.debug("memberId = " + loginInfo.getMemberId());

        int cultureId = Integer.parseInt(web.getString("cultureId")); // 게시물 일련번호 구분을 위한 파라미터
        logger.debug("cultureId = " + cultureId);

        String chk = web.getString("chk");
        logger.debug("chk = " + chk);

        int cultureLike = Integer.parseInt(web.getString("cultureLike")); // JSON에 넣기 전 String으로 파라미터를 받는다
        logger.debug("cultureLike = " + cultureLike);

        CultureFestival culture = new CultureFestival();
        culture.setContentId(cultureId);
        

        CultureFestival likeItem = null; // 업데이트 후 받을 객체

        CategoryLike categoryLike = new CategoryLike(); // 선택된 게시물을 조회하기 위한 BoardList 객체 생성
        categoryLike.setCultureId(cultureId); // cultureId 파라미터 값을 객체에 cultureId에 저장
        categoryLike.setMemberId(loginInfo.getMemberId());

        try {
            // 타입에 따라 분류하여 증가 및 감소 한 값으로 키 재설정
            if (chk.equals("Y")) {
                if (categoryLikeService.selectCategoryLike(categoryLike) == 0) {
                    // 회원정보로부터 해당 휴게소에 좋아요 갯수가 0인 경우에만 좋아요 증가 처리
                    categoryLikeService.addCategoryLike(categoryLike); // 문화 행사 좋아요 한 정보를 추가
                    cultureFestivalService.updateCultureFestByLikeUp(culture); // 문화 행사 좋아요 증가 처리
                    likeItem = cultureFestivalService.selectCultureCount(culture); // 문화 행사 좋아요 수 조회
                }
            } else if (chk.equals("N")) {
                if (categoryLikeService.selectCategoryLike(categoryLike) == 1) {
                    // 회원정보로부터 해당 행사 정보의 좋아요 갯수가 1인 경우에만 좋아요 감소 처리
                    categoryLikeService.deleteCategoryLike(categoryLike); // 문화 행사 좋아요 한 정보를 삭제
                    cultureFestivalService.updateCultureFestByLikeDown(culture); // 문화 행사 좋아요 감소 처리
                    likeItem = cultureFestivalService.selectCultureCount(culture); // 문화 행사 좋아요 수 조회
                }
            }
        } catch (Exception e) {
            web.printJsonRt(e.getLocalizedMessage());
            return null;
        }
        System.out.println(likeItem + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

       

        /** 처리 결과를 JSON으로 출력하기 */
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("rt", "OK");
        data.put("cultureLike", likeItem.getFestLike());

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

        return null;


    } // End festLike Method
	
	
	@RequestMapping(value = "/culture/cultureFestival.do", method = RequestMethod.GET)
	public ModelAndView cultureFest(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		web.init();
		
		
		
		CultureFestival culture = new CultureFestival();
		
		String from = web.getString("from");
		String to = web.getString("to");
		model.addAttribute("from", from);
		model.addAttribute("to", to);
		String keyword = web.getString("keyword");
		if (keyword != null) {			
				culture.setTitle(keyword);
		}
		
		if (from != null && to != null) {
			int intFrom = Integer.parseInt(from.substring(0,4)+from.substring(5,7)+from.substring(8));
			int intTo = Integer.parseInt(to.substring(0,4)+to.substring(5,7)+to.substring(8));

			culture.setEventStartDate(intFrom);
			culture.setEventEndDate(intTo);
		}
		
		int totalCount = 0;
		

		try {
			totalCount = cultureFestivalService.countCultureFestivalList(culture); // DB 전체 개수

		} catch (Exception e) {
			logger.debug(e.getLocalizedMessage());
		}
		
		int nowPage = web.getInt("page", 1);
		pageHelper.pageProcess(nowPage, totalCount, 10, 5);
		culture.setLimitStart(pageHelper.getLimitStart());
		culture.setListCount(pageHelper.getListCount());
		model.addAttribute("pageHelper", pageHelper);
		
		List<CultureFestival> list = null;
		
		
		
		try {
			list = cultureFestivalService.selectCultureFestivalList(culture);
		} catch (Exception e) {

			return web.redirect(null, e.getLocalizedMessage());
		}		
		
		
		model.addAttribute("cultureFestList", list);
		

		return new ModelAndView("culture/cultureFestival");

	}
	
	
	@RequestMapping(value="/culture/cultureFestivalView.do", method=RequestMethod.GET)
   	public ModelAndView cultureFestView(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		web.init();
		
		int contentId = web.getInt("contentId");
		CultureFestival culture = new CultureFestival();
   		culture.setContentId(contentId);

   		try {
			CultureFestival list = cultureFestivalService.selectCultureFestivalItem(culture);
			model.addAttribute("perforItem", list);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
   		int memberId = 0;

		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo != null) {

			memberId = loginInfo.getMemberId();
		}


        /** 즐겨찾기와 좋아요 현황 검사 */
       Favorite favor = new Favorite();
       favor.setMemberId(memberId);
       favor.setCultureId(contentId);

       CategoryLike like = new CategoryLike();
		like.setCultureId(contentId);
		like.setMemberId(memberId);
       boolean favoriteTarget = false;
		try {
			if (favoriteService.favoriteExist(favor) == 1) {
				favoriteTarget = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("favoriteTarget", favoriteTarget);

		boolean likeTarget = false;
		try {
			if (categoryLikeService.selectCategoryLike(like) == 1) {
				likeTarget = true;
			}
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		model.addAttribute("likeTarget", likeTarget);
		
		
		
		return new ModelAndView("culture/cultureFestivalView");
		
	}	
	
}
