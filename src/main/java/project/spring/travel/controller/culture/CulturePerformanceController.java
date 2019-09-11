package project.spring.travel.controller.culture;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
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
import project.spring.travel.model.CulturePerformance;
import project.spring.travel.model.Favorite;
import project.spring.travel.model.HotKeyword;
import project.spring.travel.model.Member;
import project.spring.travel.service.CategoryLikeService;
import project.spring.travel.service.CulturePerformanceService;
import project.spring.travel.service.FavoriteService;
import project.spring.travel.service.HotKeywordService;

/**
 * Servlet implementation class CulturePerformanceHome
 */
@Controller
public class CulturePerformanceController {

	Logger logger = LoggerFactory.getLogger(CulturePerformanceController.class);

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
	CulturePerformanceService culturePerformanceService;
	@Autowired
	FavoriteService favoriteService;
	@Autowired
	CategoryLikeService categoryLikeService;
	// -> import project.spring.travel.service.HotKeywordService;
	@Autowired
	HotKeywordService hotKeywordService;
	
	@Scheduled(cron="0 0 07 * * ?")
	public void CulturePerformanceAPIInsertCron() {

       


        // 문화 기본 API 불안정으로 JSON을 얻지 못하기에 얻을 때까지 반복
            String successYN = "N";

            if(successYN.equals("N")) {
                boolean bool = true;

                while(bool) {
                	String url = "http://www.culture.go.kr/openapi/rest/publicperformancedisplays/period?rows=10000&sortStdr=1&serviceKey=iU%2FuXcyh6MH%2FAvC2XqJKyjCnqzq8RSrQla2TkmYIgbizDxi%2BjK%2BFpD7x43Taz7tm6lMfdbbusFbeoMIOa8sEuA%3D%3D&";
                    InputStream is = httpHelper.getWebData(url, "utf-8");
                    JSONObject json = xmltoJsonHelper.getJSONObject(is, "utf-8");
                    successYN = "" + json.getJSONObject("response").getJSONObject("comMsgHeader").get("SuccessYN");

                    if(successYN.equals("Y")) {
                        bool = false;
                        // json 배열까지 접근한다.
                        JSONObject res = json.getJSONObject("response");
                        JSONObject msgBody = res.getJSONObject("msgBody");
                        JSONArray perforList = msgBody.getJSONArray("perforList");

                        // 배열 데이터이므로 반복문 안에서 처리해야 한다.
                        // 배열의 길이만큼 반복한다.
                        for(int j = 0; j < perforList.length(); j++) {
                            // 배열의 j번째 JSON을 꺼낸다.
                            JSONObject temp = perforList.getJSONObject(j);

                            // 데이터를 추출
                            int seq = temp.getInt("seq");
                            String title = temp.getString("title");
                            int startDate = temp.getInt("startDate");
                            int endDate = temp.getInt("endDate");
                            String realmName = temp.getString("realmName");
                            String place = temp.getString("place");
                            String area = temp.getString("area");
                            String gpsX = null;
                            if(temp.get("gpsX") != null) {
                            	gpsX = "" + temp.get("gpsX");
                            }
                            String gpsY = null;
                            if(temp.get("gpsY") != null) {
                            	gpsY = "" + temp.get("gpsY");
                            }
                            String thumbnail = temp.getString("thumbnail");

                            // 추출한 데이터를 휴게소 Beans에 주입
                            CulturePerformance culture = new CulturePerformance();
                            culture.setSeq(seq);
                            culture.setTitle(title);
                            culture.setStartDate(startDate);
                            culture.setEndDate(endDate);
                            culture.setRealmName(realmName);
                            culture.setPlace(place);
                            culture.setArea(area);
                            culture.setGpsX(gpsX);
                            culture.setGpsY(gpsY);
                            culture.setThumbnail(thumbnail);




                            String detailUrl = "http://www.culture.go.kr/openapi/rest/publicperformancedisplays/d/?serviceKey=iU%2FuXcyh6MH%2FAvC2XqJKyjCnqzq8RSrQla2TkmYIgbizDxi%2BjK%2BFpD7x43Taz7tm6lMfdbbusFbeoMIOa8sEuA%3D%3D&seq="+seq;
                            is = httpHelper.getWebData(detailUrl, "utf-8");
                            json = xmltoJsonHelper.getJSONObject(is, "utf-8");
                            successYN = "" + json.getJSONObject("response").getJSONObject("comMsgHeader").get("SuccessYN");

                            JSONObject detailRes = json.getJSONObject("response");
                            JSONObject detailMsgBody = detailRes.getJSONObject("msgBody");
                            JSONObject perforInfo = detailMsgBody.getJSONObject("perforInfo");

                            String subTitle = perforInfo.getString("subTitle");
                            String price = perforInfo.getString("price");
                            String contents1 = perforInfo.getString("contents1");
                            String contents2 = perforInfo.getString("contents2");
                            String jsonUrl = perforInfo.getString("url");
                            String phone = perforInfo.getString("phone");
                            String imgUrl = perforInfo.getString("imgUrl");
                            String placeUrl = perforInfo.getString("placeUrl");
                            String placeAddr = perforInfo.getString("placeAddr");

                            culture.setSubTitle(subTitle);
                            culture.setPrice(price);
                            culture.setContents1(contents1);
                            culture.setContents2(contents2);
                            culture.setUrl(jsonUrl);
                            culture.setPhone(phone);
                            culture.setImgUrl(imgUrl);
                            culture.setPlaceUrl(placeUrl);
                            culture.setPlaceAddr(placeAddr);



                           

                            try {
								culturePerformanceService.insertCulturePerformance(culture);
							} catch (Exception e) {
								logger.error("데이터 저장에 실패했습니다." + e.getMessage());
							}




                        } // End for
                } // End if

             } // End While
         } // End if
        
        
	}
	
	/**
     * culture API 정보 추가를 위한 액션 컨트롤러
     */
    @RequestMapping(value = "/culture/culturePerf_API_insert.do", method = RequestMethod.GET)
    public ModelAndView CulturePerformanceAPIInsert(Locale locale, Model model,
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.

        /** (3) 로그인 여부 검사 */
        // 관리자가 아니라면 이 페이지를 동작시켜서는 안된다.
        Member loginInfo = (Member) web.getSession("loginInfo");

        if (loginInfo == null) {
        	return web.redirect(web.getRootPath() + "/index.do", "관리자 계정만 접근 가능합니다.");
        }


        if(loginInfo.getGrade().equals("Master")) {
            return web.redirect(web.getRootPath() + "/index.do", "관리자 계정만 접근 가능합니다.");
        }

        /** (4) 문화 API를 연동하기 + Service를 통한 문화DB 저장 */


        // 문화 기본 API 불안정으로 JSON을 얻지 못하기에 얻을 때까지 반복
            String successYN = "N";

            if(successYN.equals("N")) {
                boolean bool = true;

                while(bool) {
                	String url = "http://www.culture.go.kr/openapi/rest/publicperformancedisplays/period?rows=10000&sortStdr=1&serviceKey=iU%2FuXcyh6MH%2FAvC2XqJKyjCnqzq8RSrQla2TkmYIgbizDxi%2BjK%2BFpD7x43Taz7tm6lMfdbbusFbeoMIOa8sEuA%3D%3D&";
                    InputStream is = httpHelper.getWebData(url, "utf-8");
                    JSONObject json = xmltoJsonHelper.getJSONObject(is, "utf-8");
                    successYN = "" + json.getJSONObject("response").getJSONObject("comMsgHeader").get("SuccessYN");

                    if(successYN.equals("Y")) {
                        bool = false;
                        // json 배열까지 접근한다.
                        JSONObject res = json.getJSONObject("response");
                        JSONObject msgBody = res.getJSONObject("msgBody");
                        JSONArray perforList = msgBody.getJSONArray("perforList");

                        // 배열 데이터이므로 반복문 안에서 처리해야 한다.
                        // 배열의 길이만큼 반복한다.
                        for(int j = 0; j < perforList.length(); j++) {
                            // 배열의 j번째 JSON을 꺼낸다.
                            JSONObject temp = perforList.getJSONObject(j);

                            // 데이터를 추출
                            int seq = temp.getInt("seq");
                            String title = temp.getString("title");
                            int startDate = temp.getInt("startDate");
                            int endDate = temp.getInt("endDate");
                            String realmName = temp.getString("realmName");
                            String place = temp.getString("place");
                            String area = temp.getString("area");
                            String gpsX = null;
                            if(temp.get("gpsX") != null) {
                            	gpsX = "" + temp.get("gpsX");
                            }
                            String gpsY = null;
                            if(temp.get("gpsY") != null) {
                            	gpsY = "" + temp.get("gpsY");
                            }
                            String thumbnail = temp.getString("thumbnail");

                            // 추출한 데이터를 휴게소 Beans에 주입
                            CulturePerformance culture = new CulturePerformance();
                            culture.setSeq(seq);
                            culture.setTitle(title);
                            culture.setStartDate(startDate);
                            culture.setEndDate(endDate);
                            culture.setRealmName(realmName);
                            culture.setPlace(place);
                            culture.setArea(area);
                            culture.setGpsX(gpsX);
                            culture.setGpsY(gpsY);
                            culture.setThumbnail(thumbnail);




                            String detailUrl = "http://www.culture.go.kr/openapi/rest/publicperformancedisplays/d/?serviceKey=iU%2FuXcyh6MH%2FAvC2XqJKyjCnqzq8RSrQla2TkmYIgbizDxi%2BjK%2BFpD7x43Taz7tm6lMfdbbusFbeoMIOa8sEuA%3D%3D&seq="+seq;
                            is = httpHelper.getWebData(detailUrl, "utf-8");
                            json = xmltoJsonHelper.getJSONObject(is, "utf-8");
                            successYN = "" + json.getJSONObject("response").getJSONObject("comMsgHeader").get("SuccessYN");

                            JSONObject detailRes = json.getJSONObject("response");
                            JSONObject detailMsgBody = detailRes.getJSONObject("msgBody");
                            JSONObject perforInfo = detailMsgBody.getJSONObject("perforInfo");

                            String subTitle = perforInfo.getString("subTitle");
                            String price = perforInfo.getString("price");
                            String contents1 = perforInfo.getString("contents1");
                            String contents2 = perforInfo.getString("contents2");
                            String jsonUrl = perforInfo.getString("url");
                            String phone = perforInfo.getString("phone");
                            String imgUrl = perforInfo.getString("imgUrl");
                            String placeUrl = perforInfo.getString("placeUrl");
                            String placeAddr = perforInfo.getString("placeAddr");

                            culture.setSubTitle(subTitle);
                            culture.setPrice(price);
                            culture.setContents1(contents1);
                            culture.setContents2(contents2);
                            culture.setUrl(jsonUrl);
                            culture.setPhone(phone);
                            culture.setImgUrl(imgUrl);
                            culture.setPlaceUrl(placeUrl);
                            culture.setPlaceAddr(placeAddr);

                            try {
								culturePerformanceService.insertCulturePerformance(culture);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}




                        } // End for
                } // End if

             } // End While
         } // End if
        /** (5) 저장이 완료되었으므로 휴게소 메인페이지로 이동 */
        return web.redirect(web.getRootPath() + "/culture/culturePerformance.do", "저장이 완료되었습니다.");

    } // End culturePerfApiInsert Method
    
    
    
    @RequestMapping(value = "/culture/culture_like.do", method = RequestMethod.POST)
    public ModelAndView cultureLike(Locale locale, Model model,
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

        CulturePerformance culture = new CulturePerformance();
        culture.setSeq(cultureId);


        CulturePerformance likeItem = null; // 업데이트 후 받을 객체

        CategoryLike categoryLike = new CategoryLike(); // 선택된 게시물을 조회하기 위한 BoardList 객체 생성
        categoryLike.setCultureId(cultureId); // cultureId 파라미터 값을 객체에 cultureId에 저장
        categoryLike.setMemberId(loginInfo.getMemberId());

        try {
            // 타입에 따라 분류하여 증가 및 감소 한 값으로 키 재설정
            if (chk.equals("Y")) {
                if (categoryLikeService.selectCategoryLike(categoryLike) == 0) {
                    // 회원정보로부터 해당 휴게소에 좋아요 갯수가 0인 경우에만 좋아요 증가 처리
                    categoryLikeService.addCategoryLike(categoryLike); // 문화 공연 좋아요 한 정보를 추가
                    culturePerformanceService.updateCulturePerfByLikeUp(culture); // 문화 공연 좋아요 증가 처리
                    likeItem = culturePerformanceService.selectCultureCount(culture); // 문화 공연 좋아요 수 조회
                }
            } else if (chk.equals("N")) {
                if (categoryLikeService.selectCategoryLike(categoryLike) == 1) {
                    // 회원정보로부터 해당 공연 정보의 좋아요 갯수가 1인 경우에만 좋아요 감소 처리
                    categoryLikeService.deleteCategoryLike(categoryLike); // 문화 공연 좋아요 한 정보를 삭제
                    culturePerformanceService.updateCulturePerfByLikeDown(culture); // 문화 공연 좋아요 감소 처리
                    likeItem = culturePerformanceService.selectCultureCount(culture); // 문화 공연 좋아요 수 조회
                }
            }
        } catch (Exception e) {
            web.printJsonRt(e.getLocalizedMessage());
            return null;
        }
        System.out.println(likeItem + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

        /** 인기검색어에 등록 시작  */
        try {
            if(likeItem != null) {
                if(likeItem.getTitle() != null && !likeItem.getTitle().equals("")) {
                    HotKeyword hotKeywordInsert = new HotKeyword(); // 검색어를 Beans에 묶는다.
                    hotKeywordInsert.setKeyword(likeItem.getTitle());
                    hotKeywordService.insertHotKeyword(hotKeywordInsert); // 검색어를 저장
                }
            }
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        /** 인기검색어에 등록 끝 */

        /** 처리 결과를 JSON으로 출력하기 */
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("rt", "OK");
        data.put("cultureLike", likeItem.getCultureLike());

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


    } // End cultureLike Method


    /**
     * 문화 공연 목록페이지
     * @param locale
     * @param model
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value="/culture/culturePerformance.do", method=RequestMethod.GET)
	public ModelAndView cultureHome(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		web.init();

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

    	/* url과 javascript (예정) 표시를 위한 date 객체 생성 */
		Calendar cal = Calendar.getInstance();
		int yy = cal.get(Calendar.YEAR);
		int mm = cal.get(Calendar.MONTH) + 1;
		int dd = cal.get(Calendar.DAY_OF_MONTH);

		String yys = "" + yy;
		String mms;
		String dds;

		/* mm-dd 형태로 만들어주기 위한 처리 */
		if (mm < 10) {
			mms = "0" + mm;
		} else {
			mms = "" + mm;
		}

		if (dd < 10) {
			dds = "0" + dd;
		} else {
			dds = "" + dd;
		}
		String date = yys + mms + dds;
		int dateInt = Integer.parseInt(date);
		model.addAttribute("dateInt", dateInt);

		int nowPage = web.getInt("page", 1); // 현재 페이지
		logger.debug("page = " + nowPage);
		model.addAttribute("nowPage", nowPage);



		CulturePerformance culture = new CulturePerformance();

		String from = web.getString("from");
		String to = web.getString("to");
		model.addAttribute("from", from);
		model.addAttribute("to", to);

		logger.warn("from="+from);
		logger.warn("to="+to);
		String area = web.getString("performArea");
		logger.warn("area="+area);
		model.addAttribute("performArea", area);
		String realmName = web.getString("realmName");
		logger.warn("realmName="+realmName);
		model.addAttribute("realmName", realmName);

		String key = web.getString("condition");
		model.addAttribute("condition", key);
		String keyword = web.getString("keyword");
		model.addAttribute("keyword", keyword);

		/** 인기검색어에 등록 시작  */
		try {
		    HotKeyword hotKeywordInsert = new HotKeyword(); // 검색어를 Beans에 묶는다.
		    if(area != null && !area.trim().replace(" ", "").equals("")) {
		        hotKeywordInsert.setKeyword(area);
		        hotKeywordService.insertHotKeyword(hotKeywordInsert); // 검색어를 저장
		    }
		    if(realmName != null && !realmName.trim().replace(" ", "").equals("")) {
		        hotKeywordInsert.setKeyword(realmName);
		        hotKeywordService.insertHotKeyword(hotKeywordInsert); // 검색어를 저장
		    }
		    if(keyword != null && !keyword.trim().replace(" ", "").equals("")) {
		        hotKeywordInsert.setKeyword(keyword);
		        hotKeywordService.insertHotKeyword(hotKeywordInsert); // 검색어를 저장
		    }
		} catch (Exception e) {
		    return web.redirect(null, e.getLocalizedMessage());
		}
		/** 인기검색어에 등록 끝 */

		if (from != null && to != null) {
			int intFrom = Integer.parseInt(from.substring(0,4)+from.substring(5,7)+from.substring(8));
			int intTo = Integer.parseInt(to.substring(0,4)+to.substring(5,7)+to.substring(8));

			culture.setStartDate(intFrom);
			culture.setEndDate(intTo);
		}


		if (area != null && !area.equals("")) {
			culture.setArea(area);
		}

		if (realmName != null && !realmName.equals("")) {
			culture.setRealmName(realmName);
		}


		if (keyword != null) {
			if (key.equals("title")) {
				culture.setTitle(keyword);
			} else if (key.equals("content")) {
				culture.setContents1(keyword);
			} else if (key.equals("titleContent")) {
				culture.setTitle(keyword);
				culture.setContents1(keyword);
			} else if (key.equals("place")) {
				culture.setPlace(keyword);
			}
		}





		int totalCount = 0;
		List<CulturePerformance> list = null;

		try {
			totalCount = culturePerformanceService.countCulturePerformanceList(culture); // DB 전체 개수

		} catch (Exception e) {
			logger.debug(e.getLocalizedMessage());
		}
		pageHelper.pageProcess(nowPage, totalCount, 10, 5);
		culture.setLimitStart(pageHelper.getLimitStart());
		culture.setListCount(pageHelper.getListCount());
		try {
			list = culturePerformanceService.selectCulturePerformanceList(culture);
		} catch (Exception e) {

			return web.redirect(null, e.getLocalizedMessage());
		}		
		
		
		model.addAttribute("culturePerfList", list);	

		


		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("pageHelper", pageHelper);


		return new ModelAndView("culture/culturePerformance");
	}

    @RequestMapping(value="/culture/culturePerformanceView.do", method=RequestMethod.GET)
   	public ModelAndView culturePerfView(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   		web.init();

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

    	/* url과 javascript (예정) 표시를 위한 date 객체 생성 */
   		Calendar cal = Calendar.getInstance();
   		int yy = cal.get(Calendar.YEAR);
   		int mm = cal.get(Calendar.MONTH) + 1;
   		int dd = cal.get(Calendar.DAY_OF_MONTH);

   		String yys = "" + yy;
   		String mms;
   		String dds;

   		/* mm-dd 형태로 만들어주기 위한 처리 */
   		if (mm < 10) {
   			mms = "0" + mm;
   		} else {
   			mms = "" + mm;
   		}

   		if (dd < 10) {
   			dds = "0" + dd;
   		} else {
   			dds = "" + dd;
   		}
   		String date = yys + mms + dds;
   		int dateInt = Integer.parseInt(date);
   		model.addAttribute("dateInt", dateInt);

   		int seq = web.getInt("seq");
   		CulturePerformance culture = new CulturePerformance();
   		culture.setSeq(seq);

   		try {
			CulturePerformance list = culturePerformanceService.selectCulturePerformanceItem(culture);
			model.addAttribute("perforItem", list);
		} catch (Exception e) {
			logger.debug(e.getLocalizedMessage());
		}





        /** 로그인 여부 검사 */
        // 세션 정보를 받아온다.



		int memberId = 0;

		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo != null) {

			memberId = loginInfo.getMemberId();
		}


        /** 즐겨찾기와 좋아요 현황 검사 */
       Favorite favor = new Favorite();
       favor.setMemberId(memberId);
       favor.setCultureId(seq);

       CategoryLike like = new CategoryLike();
		like.setCultureId(seq);
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
			e.printStackTrace();
		}
		model.addAttribute("likeTarget", likeTarget);










   		return new ModelAndView("culture/culturePerformanceView");





   	}



}
