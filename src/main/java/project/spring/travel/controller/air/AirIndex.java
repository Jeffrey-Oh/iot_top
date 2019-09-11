package project.spring.travel.controller.air;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import project.spring.helper.PageHelper;
import project.spring.helper.RegexHelper;
import project.spring.helper.Util;
import project.spring.helper.WebHelper;
import project.spring.travel.model.AirHot;
import project.spring.travel.model.AirSearch;
import project.spring.travel.model.Domestic;
import project.spring.travel.model.HotKeyword;
import project.spring.travel.model.Live;
import project.spring.travel.service.AirHotService;
import project.spring.travel.service.DomesticService;
import project.spring.travel.service.HotKeywordService;
import project.spring.travel.service.LiveService;
import project.spring.travel.service.SearchService;

/**
 * Servlet implementation class AirIndex
 */
@Controller
public class AirIndex {

	Logger logger = LoggerFactory.getLogger(AirIndex.class);

	@Autowired
	WebHelper web;

	@Autowired
	Util util;

	@Autowired
	PageHelper pageHelper;

	@Autowired
	RegexHelper regex;

	@Autowired
	LiveService liveService;

	@Autowired
	DomesticService domesticService;

	@Autowired
	SearchService searchService;

	@Autowired
	AirHotService airHotService;

    // -> import project.spring.travel.service.HotKeywordService;
    @Autowired
    HotKeywordService hotKeywordService;
    
//    public int backUpLiveGroupId = 0;
//    public int backUpDomesticGroupId = 0;
    
//	@Scheduled(cron = "*/60 * * * * ?")
//	@Scheduled(cron = "*/60 * 6-23 * * ?")
	public void LiveAPI() {
		
		
//		Calendar cal = Calendar.getInstance();
//		cal.set(cal.MINUTE, -120);
//		Date date = new Date();
//		date.setTime(cal.getTimeInMillis());
//		String pattern = "HHmm";
//		SimpleDateFormat format = new SimpleDateFormat(pattern);
//		
//		int timeChk = Integer.parseInt(format.format(date));
//		
//		if (timeChk == 2200) {
//			try {
//				Thread.sleep(120000);
//			} catch (Exception e) {
//				e.printStackTrace();
//				logger.warn(e.getLocalizedMessage());
//			}
//		}
		
//		if (backUpLiveGroupId == 2) {
//			backUpLiveGroupId = 0;
//			try {
//				liveService.deleteAll();
//				liveService.resetSql();
//			} catch (Exception e) {
//				e.printStackTrace();
//				logger.warn("전체 삭제 혹은 일련번호 초기화 실패");
//			}
//		}
//		
//		try {
//			// API 연동 및 DB 데이터 저장 후 데이터 조회
//			liveService.getAPI(); // DB 새롭게 저장
//		} catch (Exception e) {
//			logger.error("데이터 저장에 실패했습니다." + e.getMessage());
//		}
//		
//		backUpLiveGroupId += 1;
//		logger.warn("backUpLiveGroupId >>> " + backUpLiveGroupId);
//		
//		if (backUpDomesticGroupId == 0) {
//			this.DomesticAPI();
//		}
		
		

	}

//	@Scheduled(cron="0 10 01 2 * ?")
//	public void DomesticAPI() {
//
//		try {
//			// API 연동 및 DB 데이터 저장 후 데이터 조회
//			domesticService.getAPI();
//		} catch (Exception e) {
//			logger.error("데이터 저장에 실패했습니다." + e.getMessage());
//		}
//		
//		backUpDomesticGroupId += 1;
//		logger.warn("backUpDomesticGroupId >>> " + backUpDomesticGroupId);
//
//	}

	@RequestMapping(value = "/AirMain.do", method = RequestMethod.GET)
	public ModelAndView AirMain(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		web.init();

        /** 인기검색어 영역 시작 */
        try {
            List<HotKeyword> hotKeywordList = null; // 조회된 검색어 리스트를 담을 리스트
            hotKeywordList = hotKeywordService.selectHotKeywordList(); // 최근 1달 검색어 조회
            // xss 처리
            for (int i=0; i<hotKeywordList.size(); i++) {
                HotKeyword hotKeywordtemp = hotKeywordList.get(i);
                // 공격 코드를 막기 위한 처리
                hotKeywordtemp.setKeyword(web.convertHtmlTag(hotKeywordtemp.getKeyword()));
                if (hotKeywordtemp.getKeyword().indexOf("<script>") > -1) {
                    hotKeywordtemp.setKeyword(web.getString(null, hotKeywordtemp.getKeyword(), true));
                }
            }
            model.addAttribute("hotKeywordList", hotKeywordList); // View에서 사용하기 위해 등록
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        /** 인기검색어 영역 끝 */
        
        /** 추천특가 조회 시작 */
        try {
        	// 추천특가 전체 개수 조회
        	int airHotCount = airHotService.getAirHotCnt();
        	// 추천특가 일련번호의 처음과 끝을 알아내기 위한 Beans 묶음
        	AirHot airHotStart = new AirHot();
        	airHotStart.setLimitStart(0);
        	airHotStart.setListCount(1);
        	AirHot airHotEnd  = new AirHot();
        	airHotEnd.setLimitStart(airHotCount - 1);
        	airHotEnd.setListCount(1);
        	// 추천특가 정보의 처음과 끝이 담길 List
        	List<AirHot> airHotStartId = null;
        	List<AirHot> airHotEndId = null;
        	// 추천특가 정보의 처음과 끝을 조회
        	airHotStartId = airHotService.getAirHotList(airHotStart);
        	airHotEndId = airHotService.getAirHotList(airHotEnd);

        	boolean[] airHotSameChk = new boolean[airHotEndId.get(0).getHotId() - airHotStartId.get(0).getHotId() + 1];
        	
        	// 랜덤 8개만 넣은 리스트
        	List<AirHot> airHotList = new ArrayList<AirHot>();
        	
        	// 중복 검사를 위한 임시 리스트
        	List<String> tempList = new ArrayList<String>();
        	
        	for (int i=0; i<6; i++) {
	        	// 랜덤으로 일련번호를 추출
	        	int targetIdx = util.getRandom(airHotStartId.get(0).getHotId(), airHotEndId.get(0).getHotId());
	        	// 랜덤으로 정해진 일련번호를 통해 추천특가 정보를 조회를 위해 Beans에 묶음
	        	AirHot airHotTarget  = new AirHot();
	        	airHotTarget.setHotId(targetIdx);
	        	// 추천특가 정보를 조회
	        	AirHot airHotTargetItem = airHotService.getAirHotItem(airHotTarget);
	        	
	        	targetIdx -= 1;
	        	
	        	// 중복 검사
        		boolean tempChk = false;
				if (!airHotSameChk[targetIdx]) {
					for (int j=0; j<tempList.size(); j++) {
						if (tempList.get(j).equals(airHotTargetItem.getImageUrl())) {
							i--;
							tempChk = true;
						}
					}
					
				} else {
					i--;
				}
				
				if (tempChk == false) {
					tempList.add(airHotTargetItem.getImageUrl());
					airHotSameChk[targetIdx] = true;
					airHotList.add(airHotTargetItem);
				}
        	}

        	model.addAttribute("airHotList", airHotList);

		} catch (Exception e) {
			e.printStackTrace();
			return web.redirect(null, e.getLocalizedMessage());
		}
        
        Date date = new Date();
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
		String year = (String) simpleDate.format(date);
		model.addAttribute("date", year);
		/** 추천특가 조회 끝 */
		
		/** 실시간 항공 조회 시작 */
		List<Live> liveList = null;
		
		Live params = new Live();
		
		int totalCount = 0;
		
//		int groupId = backUpLiveGroupId;
//		
//		params.setGroupId(groupId);
		
		try {
			totalCount = liveService.getCount(params); // 게시물 전체 개수
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		pageHelper.pageProcess(1, totalCount, 7, 1);
		params.setLimitStart(pageHelper.getLimitStart());
		params.setListCount(pageHelper.getListCount());
		model.addAttribute("pageHelper", pageHelper);
		
//		Calendar cal = Calendar.getInstance();
//		date.setTime(cal.getTimeInMillis());
//		String pattern = "HHmm";
//		simpleDate = new SimpleDateFormat(pattern);
//		
//		int timeChk = Integer.parseInt(simpleDate.format(date));
//		logger.warn("timeChk >>> " + timeChk);
//		
//		boolean liveChk = false;
//		if (timeChk >= 0 && timeChk < 600) {
//			liveChk = true;
//		}
		
		try {
			liveList = liveService.getItemAll(params);
//			model.addAttribute("liveChk", liveChk);
			model.addAttribute("liveList", liveList);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		/** 실시간 항공 조회 끝 */
		
		/** 운항스케줄 조회 시작 */
		Domestic dParams = new Domestic();

		List<Domestic> domesticList = null;

		totalCount = 0;
		
//		int dGroupId = backUpDomesticGroupId;
//		
//		dParams.setGroupId(dGroupId);

		try {
			totalCount = domesticService.getCount(dParams); // 게시물 전체 개수
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		pageHelper.pageProcess(1, totalCount, 5, 1);
		dParams.setLimitStart(pageHelper.getLimitStart());
		dParams.setListCount(pageHelper.getListCount());
		model.addAttribute("pageHelper", pageHelper);
		
		

		try {
			domesticList = domesticService.getItemAll(dParams);
			model.addAttribute("domesticList", domesticList);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		/** 운항스케줄 조회 끝 */

		return new ModelAndView("air/AirIndex");
	}

	@RequestMapping(value = "/AirHot.do", method = RequestMethod.GET)
	public ModelAndView AirHot(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		web.init();

        /** 인기검색어 영역 시작 */
        try {
            List<HotKeyword> hotKeywordList = null; // 조회된 검색어 리스트를 담을 리스트
            hotKeywordList = hotKeywordService.selectHotKeywordList(); // 최근 1달 검색어 조회
            // xss 처리
            for (int i=0; i<hotKeywordList.size(); i++) {
                HotKeyword hotKeywordtemp = hotKeywordList.get(i);
                // 공격 코드를 막기 위한 처리
                hotKeywordtemp.setKeyword(web.convertHtmlTag(hotKeywordtemp.getKeyword()));
                if (hotKeywordtemp.getKeyword().indexOf("<script>") > -1) {
                    hotKeywordtemp.setKeyword(web.getString(null, hotKeywordtemp.getKeyword(), true));
                }
            }
            model.addAttribute("hotKeywordList", hotKeywordList); // View에서 사용하기 위해 등록
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        /** 인기검색어 영역 끝 */

        try {
        	// 추천특가 전체 개수 조회
        	int airHotCount = airHotService.getAirHotCnt();
        	// 추천특가 일련번호의 처음과 끝을 알아내기 위한 Beans 묶음
        	AirHot airHotStart = new AirHot();
        	airHotStart.setLimitStart(0);
        	airHotStart.setListCount(1);
        	AirHot airHotEnd  = new AirHot();
        	airHotEnd.setLimitStart(airHotCount - 1);
        	airHotEnd.setListCount(1);
        	// 추천특가 정보의 처음과 끝이 담길 List
        	List<AirHot> airHotStartId = null;
        	List<AirHot> airHotEndId = null;
        	// 추천특가 정보의 처음과 끝을 조회
        	airHotStartId = airHotService.getAirHotList(airHotStart);
        	airHotEndId = airHotService.getAirHotList(airHotEnd);

        	boolean[] airHotSameChk = new boolean[airHotEndId.get(0).getHotId() - airHotStartId.get(0).getHotId() + 1];
        	
        	// 랜덤 8개만 넣은 리스트
        	List<AirHot> airHotList = new ArrayList<AirHot>();
        	
        	// 중복 검사를 위한 임시 리스트
        	List<String> tempList = new ArrayList<String>();
        	
        	for (int i=0; i<7; i++) {
	        	// 랜덤으로 일련번호를 추출
	        	int targetIdx = util.getRandom(airHotStartId.get(0).getHotId(), airHotEndId.get(0).getHotId());
	        	// 랜덤으로 정해진 일련번호를 통해 추천특가 정보를 조회를 위해 Beans에 묶음
	        	AirHot airHotTarget  = new AirHot();
	        	airHotTarget.setHotId(targetIdx);
	        	// 추천특가 정보를 조회
	        	AirHot airHotTargetItem = airHotService.getAirHotItem(airHotTarget);
	        	
	        	targetIdx -= 1;
	        	
	        	// 중복 검사
        		boolean tempChk = false;
				if (!airHotSameChk[targetIdx]) {
					for (int j=0; j<tempList.size(); j++) {
						if (tempList.get(j).equals(airHotTargetItem.getImageUrl())) {
							i--;
							tempChk = true;
						}
					}
					
				} else {
					i--;
				}
				
				if (tempChk == false) {
					tempList.add(airHotTargetItem.getImageUrl());
					airHotSameChk[targetIdx] = true;
					airHotList.add(airHotTargetItem);
				}
        	}

        	model.addAttribute("list", airHotList);

		} catch (Exception e) {
			e.printStackTrace();
			return web.redirect(null, e.getLocalizedMessage());
		}
        
        Date date = new Date();
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
		String year = (String) simpleDate.format(date);
		model.addAttribute("date", year);

		return new ModelAndView("air/AirHot");
	}

	@RequestMapping(value = "/AirLive.do", method = RequestMethod.GET)
	public ModelAndView AirLive(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		web.init();

        /** 인기검색어 영역 시작 */
        try {
            List<HotKeyword> hotKeywordList = null; // 조회된 검색어 리스트를 담을 리스트
            hotKeywordList = hotKeywordService.selectHotKeywordList(); // 최근 1달 검색어 조회
            // xss 처리
            for (int i=0; i<hotKeywordList.size(); i++) {
                HotKeyword hotKeywordtemp = hotKeywordList.get(i);
                // 공격 코드를 막기 위한 처리
                hotKeywordtemp.setKeyword(web.convertHtmlTag(hotKeywordtemp.getKeyword()));
                if (hotKeywordtemp.getKeyword().indexOf("<script>") > -1) {
                    hotKeywordtemp.setKeyword(web.getString(null, hotKeywordtemp.getKeyword(), true));
                }
            }
            model.addAttribute("hotKeywordList", hotKeywordList); // View에서 사용하기 위해 등록
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        /** 인기검색어 영역 끝 */

		String boardingKor = web.getString("boardingKor");
		logger.debug("boardingKor = " + boardingKor);
		model.addAttribute("boardingKor", boardingKor);

		String arrivedKor = web.getString("arrivedKor");
		logger.debug("arrivedKor = " + arrivedKor);
		model.addAttribute("arrivedKor", arrivedKor);

		String airlineKorean = web.getString("airlineKorean", "");
		logger.debug("airlineKorean = " + airlineKorean);
		model.addAttribute("airlineKorean", airlineKorean);

		/** 인기검색어에 등록 시작  */
		try {
		    HotKeyword hotKeywordInsert = new HotKeyword(); // 검색어를 Beans에 묶는다.
		    if(boardingKor != null && !boardingKor.trim().replace(" ", "").equals("")) {
		        hotKeywordInsert.setKeyword(boardingKor);
		        hotKeywordService.insertHotKeyword(hotKeywordInsert); // 검색어를 저장
		    }
		    if(arrivedKor != null && !arrivedKor.trim().replace(" ", "").equals("")) {
                hotKeywordInsert.setKeyword(arrivedKor);
                hotKeywordService.insertHotKeyword(hotKeywordInsert); // 검색어를 저장
            }
		    if(airlineKorean != null && !airlineKorean.trim().replace(" ", "").equals("")) {
                hotKeywordInsert.setKeyword(airlineKorean);
                hotKeywordService.insertHotKeyword(hotKeywordInsert); // 검색어를 저장
            }
		} catch (Exception e) {
		    return web.redirect(null, e.getLocalizedMessage());
		}
		/** 인기검색어에 등록 끝 */

		String airFln = web.getString("airFln", "");
		logger.debug("airFln = " + airFln);
		model.addAttribute("airFln", airFln);

		int nowPage = web.getInt("list", 1);
		logger.debug("list = " + nowPage);

		Live params = new Live();

		List<Live> list = null;

		if (boardingKor != null && boardingKor != "" && arrivedKor != null && arrivedKor != "") {
			params.setBoardingKor(boardingKor);
			params.setArrivedKor(arrivedKor);
		}
		if (airlineKorean != null && airlineKorean != "") {
			params.setAirlineKorean(airlineKorean);
		}
		if (airFln != null && airFln != "") {
			params.setAirFln(airFln);
		}

		int totalCount = 0;
		
//		int groupId = backUpLiveGroupId;
//		
//		params.setGroupId(groupId);
		
		try {
			totalCount = liveService.getCount(params); // 게시물 전체 개수
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		pageHelper.pageProcess(nowPage, totalCount, 10, 5);
		params.setLimitStart(pageHelper.getLimitStart());
		params.setListCount(pageHelper.getListCount());
		model.addAttribute("pageHelper", pageHelper);
		
//		Calendar cal = Calendar.getInstance();
//		Date date = new Date();
//		date.setTime(cal.getTimeInMillis());
//		String pattern = "HHmm";
//		SimpleDateFormat simpleDate = new SimpleDateFormat(pattern);
//		
//		int timeChk = Integer.parseInt(simpleDate.format(date));
//		logger.warn("timeChk >>> " + timeChk);
//		
//		boolean liveChk = false;
//		if (timeChk >= 0 && timeChk < 600) {
//			liveChk = true;
//		}
		
		try {
			list = liveService.getItemAll(params);
//			model.addAttribute("liveChk", liveChk);
			model.addAttribute("list", list);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		return new ModelAndView("air/AirLive");
	}

	@RequestMapping(value = "/AirSchedule.do", method = RequestMethod.GET)
	public ModelAndView AirSchedule(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		web.init();

        /** 인기검색어 영역 시작 */
        try {
            List<HotKeyword> hotKeywordList = null; // 조회된 검색어 리스트를 담을 리스트
            hotKeywordList = hotKeywordService.selectHotKeywordList(); // 최근 1달 검색어 조회
            // xss 처리
            for (int i=0; i<hotKeywordList.size(); i++) {
                HotKeyword hotKeywordtemp = hotKeywordList.get(i);
                // 공격 코드를 막기 위한 처리
                hotKeywordtemp.setKeyword(web.convertHtmlTag(hotKeywordtemp.getKeyword()));
                if (hotKeywordtemp.getKeyword().indexOf("<script>") > -1) {
                    hotKeywordtemp.setKeyword(web.getString(null, hotKeywordtemp.getKeyword(), true));
                }
            }
            model.addAttribute("hotKeywordList", hotKeywordList); // View에서 사용하기 위해 등록
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        /** 인기검색어 영역 끝 */

		String arrivalCity = web.getString("arrivalCity", "");
		logger.debug("arrivalCity = " + arrivalCity);
		model.addAttribute("arrivalCity", arrivalCity);

		String airlineKorean = web.getString("airlineKorean", "");
		logger.debug("airlineKorean = " + airlineKorean);
		model.addAttribute("airlineKorean", airlineKorean);


        /** 인기검색어에 등록 시작  */
        try {
            HotKeyword hotKeywordInsert = new HotKeyword(); // 검색어를 Beans에 묶는다.
            if(arrivalCity != null && !arrivalCity.trim().replace(" ", "").equals("")) {
                hotKeywordInsert.setKeyword(arrivalCity);
                hotKeywordService.insertHotKeyword(hotKeywordInsert); // 검색어를 저장
            }
            if(airlineKorean != null && !airlineKorean.trim().replace(" ", "").equals("")) {
                hotKeywordInsert.setKeyword(airlineKorean);
                hotKeywordService.insertHotKeyword(hotKeywordInsert); // 검색어를 저장
            }
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        /** 인기검색어에 등록 끝 */

		String domesticNum = web.getString("domesticNum", "").toUpperCase();
		logger.debug("domesticNum = " + domesticNum);
		model.addAttribute("domesticNum", domesticNum);

		int nowPage = web.getInt("list", 1);
		logger.debug("list = " + nowPage);

		Domestic params = new Domestic();

		List<Domestic> list = null;

		if (arrivalCity != null) {
			params.setArrivalCity(arrivalCity);
		}
		if (airlineKorean != null) {
			params.setAirlineKorean(airlineKorean);
		}
		if (domesticNum != null) {
			params.setDomesticNum(domesticNum);
		}

		int totalCount = 0;
		
//		int groupId = backUpDomesticGroupId;
//		
//		params.setGroupId(groupId);

		try {
			totalCount = domesticService.getCount(params); // 게시물 전체 개수
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		pageHelper.pageProcess(nowPage, totalCount, 10, 5);
		params.setLimitStart(pageHelper.getLimitStart());
		params.setListCount(pageHelper.getListCount());
		model.addAttribute("pageHelper", pageHelper);
		
		try {
			list = domesticService.getItemAll(params);
			model.addAttribute("list", list);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		return new ModelAndView("air/AirSchedule");
	}

	@RequestMapping(value = "/AirSearch.do", method = RequestMethod.GET)
	public ModelAndView AirSearch(Locale locale, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		web.init();

        /** 인기검색어 영역 시작 */
        try {
            List<HotKeyword> hotKeywordList = null; // 조회된 검색어 리스트를 담을 리스트
            hotKeywordList = hotKeywordService.selectHotKeywordList(); // 최근 1달 검색어 조회
            // xss 처리
            for (int i=0; i<hotKeywordList.size(); i++) {
                HotKeyword hotKeywordtemp = hotKeywordList.get(i);
                // 공격 코드를 막기 위한 처리
                hotKeywordtemp.setKeyword(web.convertHtmlTag(hotKeywordtemp.getKeyword()));
                if (hotKeywordtemp.getKeyword().indexOf("<script>") > -1) {
                    hotKeywordtemp.setKeyword(web.getString(null, hotKeywordtemp.getKeyword(), true));
                }
            }
            model.addAttribute("hotKeywordList", hotKeywordList); // View에서 사용하기 위해 등록
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        /** 인기검색어 영역 끝 */

        String referer = request.getHeader("referer");

        List<AirSearch> list = null;

        String tab = web.getString("tab", "round");

        /** 메인페이지에서 검색했을 경우 (왕복조회 밖에안됨) */
        if (!regex.isIndexCheck(referer, "AirMain.do")) {
        	if(request.getQueryString() != null && request.getQueryString().indexOf("tab=oneway") > -1) {
            	tab = "oneway";
        	} else {
        		tab = "round";
        	}
        	
        }


		logger.debug("tab = " + tab);
		model.addAttribute("tab", tab);

		String sdate = web.getString("sdate");
		logger.debug("sdate = " + sdate);
		model.addAttribute("sdate", sdate);

		String edate = web.getString("edate");
		logger.debug("edate = " + edate);
		model.addAttribute("edate", edate);

		String boardingKor = web.getString("boardingKor");
		logger.debug("boardingKor = " + boardingKor);
		model.addAttribute("boardingKor", boardingKor);

		String arrivedKor = web.getString("arrivedKor");
		logger.debug("arrivedKor = " + arrivedKor);
		model.addAttribute("arrivedKor", arrivedKor);

        /** 인기검색어에 등록 시작  */
        try {
            HotKeyword hotKeywordInsert = new HotKeyword(); // 검색어를 Beans에 묶는다.
            if(boardingKor != null && !boardingKor.trim().replace(" ", "").equals("")) {
                hotKeywordInsert.setKeyword(boardingKor);
                hotKeywordService.insertHotKeyword(hotKeywordInsert); // 검색어를 저장
            }
            if(arrivedKor != null && !arrivedKor.trim().replace(" ", "").equals("")) {
                hotKeywordInsert.setKeyword(arrivedKor);
                hotKeywordService.insertHotKeyword(hotKeywordInsert); // 검색어를 저장
            }
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        /** 인기검색어에 등록 끝 */

		int nowPage = web.getInt("list", 1);
		logger.debug("list = " + nowPage);

		AirSearch airSearch = new AirSearch();
		// 출발할 때
		if (boardingKor != null) {
			airSearch.setStartCity(boardingKor); // 출발지
		}
		if (arrivedKor != null) {
			airSearch.setArrivalCity(arrivedKor); // 도착지
		}
		if (sdate != null) {
			airSearch.setStd(Integer.parseInt(sdate.substring(2, 4) + sdate.substring(5, 7) + sdate.substring(8))); // 운항시작기간
		}
		if (edate != null) {
			airSearch.setEtd(Integer.parseInt(edate.substring(2, 4) + edate.substring(5, 7) + edate.substring(8))); // 운항마감기간
		}
		
		int totalCount = 0;

		try {
			totalCount = searchService.getCountList(airSearch); // 게시물 전체 개수
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		model.addAttribute("listTotalCount", totalCount);

		pageHelper.pageProcess(nowPage, totalCount, 5, 5);
		airSearch.setLimitStart(pageHelper.getLimitStart());
		airSearch.setListCount(pageHelper.getListCount());
		model.addAttribute("pageHelper", pageHelper);
		
		try {
			if (airSearch.getStartCity() != null || airSearch.getArrivalCity() != null || airSearch.getStd() != 0 || airSearch.getEtd() != 0) {
				list = searchService.selectTicketOneWay(airSearch);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return web.redirect(null, e.getLocalizedMessage());
		}

		if (list != null) {
		
			for (int i=0; i<list.size(); i++) {
				AirSearch temp = list.get(i);
	
				if (temp.getPrice().length() < 6) {
					temp.setPrice(temp.getPrice().substring(0, 2) + "," + temp.getPrice().substring(2));
				} else {
					temp.setPrice(temp.getPrice().substring(0, 3) + "," + temp.getPrice().substring(3));
				}
			}
		
		}

		model.addAttribute("slist", list);


		if (tab.equals("round")) {
			// 돌아올 때
 			airSearch.setStartCity(arrivedKor); // 출발지
    		airSearch.setArrivalCity(boardingKor); // 도착지

    		try {
    			if (airSearch.getStartCity() != null || airSearch.getArrivalCity() != null || airSearch.getStd() != 0 || airSearch.getEtd() != 0) {
    				list = searchService.selectTicketOneWay(airSearch);
    			}
			} catch (Exception e) {
				e.printStackTrace();
				return web.redirect(null, e.getLocalizedMessage());
			}

    		if (list != null) {
    			
	    		for (int i=0; i<list.size(); i++) {
	    			AirSearch temp = list.get(i);
	
	    			if (temp.getPrice().length() < 6) {
	    				temp.setPrice(temp.getPrice().substring(0, 2) + "," + temp.getPrice().substring(2));
	    			} else {
	    				temp.setPrice(temp.getPrice().substring(0, 3) + "," + temp.getPrice().substring(3));
	    			}
	    		}
    		
    		}

 			model.addAttribute("blist", list);
		}
		
		return new ModelAndView("air/AirSearch");
	}
}
