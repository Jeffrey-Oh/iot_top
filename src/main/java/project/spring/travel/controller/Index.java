package project.spring.travel.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import project.spring.helper.PageHelper;
import project.spring.helper.Util;
import project.spring.helper.WebHelper;
import project.spring.travel.model.AirHot;
import project.spring.travel.model.CultureFestival;
import project.spring.travel.model.CulturePerformance;
import project.spring.travel.model.HotKeyword;
import project.spring.travel.model.ServiceareaGroup;
import project.spring.travel.model.TourModel1;
import project.spring.travel.service.AirHotService;
import project.spring.travel.service.CultureFestivalService;
import project.spring.travel.service.CulturePerformanceService;
import project.spring.travel.service.HotKeywordService;
import project.spring.travel.service.ServiceareaGroupService;
import project.spring.travel.service.TourModel1Service;

/**
 * @fileName    : Index.java
 * @author      : 오태현
 * @description : 메인 페이지 View를 위한 컨트롤러
 * @lastUpdate    : 2019. 5. 11.
 */
@Controller
public class Index {
	
	Logger logger = LoggerFactory.getLogger(Index.class);
	@Autowired
	WebHelper web;
	@Autowired
	Util util;
    // -> import project.spring.helper.PageHelper;
    @Autowired
    PageHelper pageHelper;
	@Autowired
	ServiceareaGroupService serviceareaGroupService;
	// -> import project.spring.travel.service.HotKeywordService;
	@Autowired
	HotKeywordService hotKeywordService;
	
	@Autowired
	CulturePerformanceService culturePerformanceService;
	
	@Autowired
	CultureFestivalService cultureFestivalService;
	
	@Autowired
	TourModel1Service tourModel1Service;
	
	@Autowired
	AirHotService airHotService;
	
	/**
	 * index 메인페이지를 위한 컨트롤러
	 */
	@RequestMapping(value = {"/", "index.do"}, method = RequestMethod.GET)
	public ModelAndView index(Locale locale, Model model,
	        HttpServletRequest request, HttpServletResponse response) {

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
		
		/** 관광 영역에 들어갈 정보를 조회하기 위한 작업 시작 */
		// 관광 목록 조회
		List<TourModel1> tourList = null;
		try {
			tourList = tourModel1Service.getTourMainList();
		} catch (Exception e) {
			e.printStackTrace();
			// 실패 시 DB 확인 필요
			return web.redirect(null, "관광 목록 조회 실패");
		}
		
		boolean[] sameChk = new boolean[tourList.size()];
		
		// 랜덤 8개만 넣은 리스트
		List<TourModel1> tourItem = new ArrayList<TourModel1>();
		for(int i=0; i<8; i++) {
			int randomNum = util.getRandom(0, tourList.size()-1);
			String fImg = tourList.get(randomNum).getFirstimage();
			// 중복 검사
			if (!sameChk[randomNum]) {
				if (fImg == null || fImg.indexOf("no-image") > -1) {
					i--;
					continue;
				}
			} else {
				i--;
				continue;
			}
			sameChk[randomNum] = true;
			tourItem.add(tourList.get(randomNum));
			
		}
		
		/* View 처리 */
		model.addAttribute("tourItem", tourItem);
		
		/** 관광 영역에 들어갈 정보를 조회하기 위한 작업 끝 */
		
		/** 휴게소 영역에 들어갈 정보를 조회하기 위한 작업 시작 */
		try {
			// 휴게소 전체 개수 조회
			int serviceareaCount = serviceareaGroupService.selectServiceareaGroupCount(null);
			// 휴게소 일련번호의 처음과 끝을 알아내기 위한 Beans 묶음
			ServiceareaGroup serviceareaStart = new ServiceareaGroup();
			serviceareaStart.setLimitStart(0);
			serviceareaStart.setListCount(1);
			ServiceareaGroup serviceareaEnd  = new ServiceareaGroup();
			serviceareaEnd.setLimitStart(serviceareaCount - 1);
			serviceareaEnd.setListCount(1);
			// 휴게소 정보의 처음과 끝이 담길 List 
			List<ServiceareaGroup> serviceareaStartId = null;
			List<ServiceareaGroup> serviceareaEndId = null;
			// 휴게소 정보의 처음과 끝을 조회
			serviceareaStartId = serviceareaGroupService.selectServiceareaGroupList(serviceareaStart);
			serviceareaEndId = serviceareaGroupService.selectServiceareaGroupList(serviceareaEnd);
			
			// 랜덤으로 일련번호를 추출
			int targetIdx = util.getRandom(serviceareaStartId.get(0).getServicearea_groupId(), serviceareaEndId.get(0).getServicearea_groupId());
			// 랜덤으로 정해진 일련번호를 통해 휴게소 정보를 조회를 위해 Beans에 묶음
			ServiceareaGroup serviceareaTarget  = new ServiceareaGroup();
			serviceareaTarget.setServicearea_groupId(targetIdx);
			// 휴게소 정보를 조회
			ServiceareaGroup serviceareaTargetItem = serviceareaGroupService.selectServiceareaGroup(serviceareaTarget);
			
			// 해당 휴게소 정보에 대표음식 이미지가 있는지 조회
			boolean imageChk = false;
			while(!imageChk) {
				if (serviceareaTargetItem.getImageUrl() != null) {
					imageChk = true;
				} else {
					targetIdx = util.getRandom(serviceareaStartId.get(0).getServicearea_groupId(), serviceareaEndId.get(0).getServicearea_groupId());
					serviceareaTarget.setServicearea_groupId(targetIdx);
					serviceareaTargetItem = serviceareaGroupService.selectServiceareaGroup(serviceareaTarget);
				}
			}
			
			logger.debug(serviceareaTargetItem.toString());
			model.addAttribute("serviceareaItem", serviceareaTargetItem);
			
//			boolean[] serviceareaSameChk = new boolean[serviceareaEndId.get(0).getServicearea_groupId() - serviceareaStartId.get(0).getServicearea_groupId() + 1];
			
			// 휴게소 캐러셀을 위한 12곳 조회
			List<ServiceareaGroup> serviceareaGroupList = new ArrayList<ServiceareaGroup>();
			
			// 중복 검사를 위한 임시 리스트
//        	List<String> tempList = new ArrayList<String>();
			
			for (int i=0; i<12; i++) {
				// 랜덤으로 일련번호를 추출
				targetIdx = util.getRandom(serviceareaStartId.get(0).getServicearea_groupId(), serviceareaEndId.get(0).getServicearea_groupId());
				// 랜덤으로 정해진 일련번호를 통해 휴게소 정보를 조회를 위해 Beans에 묶음
				serviceareaTarget.setServicearea_groupId(targetIdx);
				serviceareaTargetItem = serviceareaGroupService.selectServiceareaGroup(serviceareaTarget);
				
				// 해당 휴게소 정보에 대표음식 이미지가 있는지 조회
				imageChk = false;
				while(!imageChk) {
					if (serviceareaTargetItem.getImageUrl() != null) {
						imageChk = true;
					} else {
						targetIdx = util.getRandom(serviceareaStartId.get(0).getServicearea_groupId(), serviceareaEndId.get(0).getServicearea_groupId());
						serviceareaTarget.setServicearea_groupId(targetIdx);
						serviceareaTargetItem = serviceareaGroupService.selectServiceareaGroup(serviceareaTarget);
					}
				}
				
//				targetIdx -= 1;
//        		
//	        	// 중복 검사
//        		boolean tempChk = false;
//				if (!serviceareaSameChk[targetIdx]) {
//					for (int j=0; j<tempList.size(); j++) {
//						if (tempList.get(j).equals(serviceareaTargetItem.getImageUrl())) {
//							i--;
//							tempChk = true;
//						}
//					}
//					
//				} else {
//					i--;
//				}
//				
//				if (tempChk == false) {
//					tempList.add(serviceareaTargetItem.getImageUrl());
//					serviceareaSameChk[targetIdx] = true;
					serviceareaGroupList.add(serviceareaTargetItem);
//				}
			}
			
			model.addAttribute("serviceareaGroupList", serviceareaGroupList);
			
			
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		/** 휴게소 영역에 들어갈 정보를 조회하기 위한 작업 끝 */
		
		/** 항공 영역에 들어갈 정보를 조회하기 위한 작업 시작 */
		
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
        	
        	for (int i=0; i<4; i++) {
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
		
		/** 항공 영역에 들어갈 정보를 조회하기 위한 작업 끝 */
		
		/** 문화 영역에 들어갈 정보를 조회하기 위한 작업 시작 */
		
		try {
			// 문화 전체 개수 조회
			int cultureCount = culturePerformanceService.countCulturePerformanceList(null);
			// 문화 일련번호의 처음과 끝을 알아내기 위한 Beans 묶음
			CulturePerformance cultureStart = new CulturePerformance();
			cultureStart.setLimitStart(0);
			cultureStart.setListCount(1);
			CulturePerformance cultureEnd  = new CulturePerformance();
			cultureEnd.setLimitStart(cultureCount - 1);
			cultureEnd.setListCount(1);
			// 문화 정보의 처음과 끝이 담길 List 
			CulturePerformance cultureStartId = null;
			CulturePerformance cultureEndId = null;
			// 문화 정보의 처음과 끝을 조회
			cultureStartId = culturePerformanceService.selectCulturePerformanceItemFL(cultureStart);
			cultureEndId = culturePerformanceService.selectCulturePerformanceItemFL(cultureEnd);
			
			int randomCultureId = util.getRandom(cultureStartId.getCultureId(), cultureEndId.getCultureId());
			
			CulturePerformance obj = new CulturePerformance();
			obj.setCultureId(randomCultureId);
			
			CulturePerformance randomItem = null;
			randomItem = culturePerformanceService.selectCulturePerformanceItem(obj);
			
			List<CulturePerformance> cultureItem = new ArrayList<CulturePerformance>();
			
//			boolean[] cultureSameChk = new boolean[cultureEndId.getCultureId() - cultureStartId.getCultureId() + 1];
			
			// 중복 검사를 위한 임시 리스트
//        	List<String> tempList = new ArrayList<String>();
			
			for(int i=0; i<8; i++) {
				int randomIdx = util.getRandom(0, cultureEndId.getCultureId() - cultureStartId.getCultureId());
				CulturePerformance temp = new CulturePerformance();
				temp.setLimitStart(randomIdx);
				temp.setListCount(1);
				
				CulturePerformance listItem = null;
				listItem = culturePerformanceService.selectCulturePerformanceItemFL(temp);
				
//				randomIdx -= 1;
//        		
//	        	// 중복 검사
//        		boolean tempChk = false;
//				if (!cultureSameChk[randomIdx]) {
//					for (int j=0; j<tempList.size(); j++) {
//						if (tempList.get(j).equals(listItem.getImgUrl())) {
//							i--;
//							tempChk = true;
//						}
//					}
//					
//				} else {
//					i--;
//				}
//				
//				if (tempChk == false) {
//					tempList.add(listItem.getImgUrl());
//					cultureSameChk[randomIdx] = true;
					cultureItem.add(listItem);
//				}
			}
			
			cultureCount = cultureFestivalService.countCultureFestivalList(null);
			
			CultureFestival cultureFStart = new CultureFestival();
			cultureFStart.setLimitStart(0);
			cultureFStart.setListCount(1);
			CultureFestival cultureFEnd  = new CultureFestival();
			cultureFEnd.setLimitStart(cultureCount - 1);
			cultureFEnd.setListCount(1);
			// 문화 정보의 처음과 끝이 담길 List 
			CultureFestival cultureFStartId = null;
			CultureFestival cultureFEndId = null;
			// 문화 정보의 처음과 끝을 조회
			cultureFStartId = cultureFestivalService.selectCultureFestivalItemFL(cultureFStart);
			cultureFEndId = cultureFestivalService.selectCultureFestivalItemFL(cultureFEnd);
			
			randomCultureId = util.getRandom(cultureFStartId.getFestId(), cultureFEndId.getFestId());
			
			CultureFestival cf = new CultureFestival();
			cf.setFestId(randomCultureId);
			
			CultureFestival randomFItem = null;
			randomFItem = cultureFestivalService.selectCultureFestivalItem(cf);
			
			
			model.addAttribute("cultureItem", cultureItem);
			model.addAttribute("randomCultureItem", randomItem);
			model.addAttribute("randomFestivalItem", randomFItem);
			
		} catch (Exception e) {
			e.printStackTrace();
			return web.redirect(null, e.getLocalizedMessage());
		}
		

		
		/** 문화 영역에 들어갈 정보를 조회하기 위한 작업 끝 */
		
		return new ModelAndView("index");
	} // End index Method

}
