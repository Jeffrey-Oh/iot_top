package project.spring.travel.controller.tour;


import java.util.List;
import java.util.Locale;

import org.apache.ibatis.session.SqlSession;
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
import project.spring.helper.WebHelper;
import project.spring.travel.model.HotKeyword;
import project.spring.travel.model.TourModel1;
import project.spring.travel.service.HotKeywordService;
import project.spring.travel.service.TourModel1Service;



@Controller
public class TourDataController {

	/** log4j 객체 생성 및 주입
	 *
	 */

	private static final Logger logger = LoggerFactory.getLogger(TourDataController.class);

	@Autowired
	WebHelper web;

	@Autowired
	PageHelper page;


	@Autowired
	SqlSession sqlSession;

	@Autowired
	TourModel1Service tourModel1Service;
	
	// -> import project.spring.travel.service.HotKeywordService;
	@Autowired
	HotKeywordService hotKeywordService;
	
	@Scheduled(cron = "0 0 0 1 1 ?")
	public void getAPI() {
		try {
			tourModel1Service.ApiDB();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/tour/TourList.do", method = RequestMethod.GET)
	public ModelAndView TourMain(Locale locale, Model model) {
	    web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
	    
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

		List<TourModel1> list = null;

		try {
			list = tourModel1Service.getTourMainList();


			if (list == null) {

				throw new NullPointerException();

			} else {

				tourModel1Service.updateTour();
			}

			for (int i = 0; i < list.size(); i++) {
				TourModel1 item = list.get(i);
				logger.debug("조회된 데이터 >>" + item.toString());
			}

		} catch (NullPointerException e) {
			// TODO: handle exception
			logger.error("저장된 데이터가 없습니다.");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("데이터 저장에 실패했습니다." + e.getMessage());

			return web.redirect(null, e.getLocalizedMessage());
		}


		/* View 처리 */
		model.addAttribute("list", list);




		return new ModelAndView("tour/TourList");

	}


	@RequestMapping( value = "/tour/TourList2.do" , method = RequestMethod.GET)
	public ModelAndView TourMain2 (Locale locale, Model model  ) {
		
		// WebHelper 초기화 처리 
		web.init();
		
		int areacode = web.getInt("keyword");
		 
		/** 인기검색어에 등록 시작 */
		try {
		    if(areacode != 0) {
                HotKeyword hotKeywordInsert = new HotKeyword(); // 검색어를 Beans에 묶는다.
                if(areacode == 1) {
                    hotKeywordInsert.setKeyword("서울");
                } else if(areacode == 2) {
                    hotKeywordInsert.setKeyword("인천");
                } else if(areacode == 32) {
                    hotKeywordInsert.setKeyword("강원");
                } else if(areacode == 6) {
                    hotKeywordInsert.setKeyword("부산");
                } else if(areacode == 5) {
                    hotKeywordInsert.setKeyword("광주");
                } else if(areacode == 34) {
                    hotKeywordInsert.setKeyword("충남");
                } else if(areacode == 33) {
                    hotKeywordInsert.setKeyword("충북");
                } else if(areacode == 3) {
                    hotKeywordInsert.setKeyword("대전");
                } else if(areacode == 36) {
                    hotKeywordInsert.setKeyword("경상남도");
                } else if(areacode == 35) {
                    hotKeywordInsert.setKeyword("경상북도");
                } else if(areacode == 38) {
                    hotKeywordInsert.setKeyword("전라도");
                } else if(areacode == 39) {
                    hotKeywordInsert.setKeyword("제주도");
                } else {
                    hotKeywordInsert.setKeyword("관광");
                }
		        hotKeywordService.insertHotKeyword(hotKeywordInsert); // 검색어를 저장
		    }
		} catch (Exception e) {
		    return web.redirect(null, e.getLocalizedMessage());
		}
		/** 인기검색어에 등록 끝 */
		
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


		List<TourModel1> list = null;
		
		TourModel1 tourModel1 = new TourModel1();
		
		tourModel1.setAreacode(areacode);
		System.out.println(areacode);
		System.out.println(areacode);
		System.out.println(areacode);

		try {
			

			list= tourModel1Service.getTourViewList(tourModel1);

			if (list == null) {
				throw new NullPointerException();

			} else {

				list= tourModel1Service.getTourViewList(tourModel1);
				System.out.println(list);
			}


		} catch (NullPointerException e) {
			// TODO: handle exception
			logger.debug("조회목록이 없습니다.");
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug("목록 조회에 실패했습니다.");

			return web.redirect("tour/TourList", e.getLocalizedMessage());
		}


		/* View 처리 */
		model.addAttribute("list", list);

		return new ModelAndView("tour/TourList2");

	}





	@RequestMapping(value = "/tour/TourList3.do" ,method = RequestMethod.GET)
	public ModelAndView TourMain3(Locale locale , Model model) {
		
		/* WebHelper 초기화 */
		web.init();
		
		int tourId = web.getInt("tourId");
		int contentid = web.getInt("contentid");
		
		logger.debug("tourId=" + tourId);
		logger.debug("contentid=" + contentid);
		
		if (tourId == 0) {
			return web.redirect(null, "투어 목록번호가 없습니다.");
		}
		
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
		
		TourModel1 tourModel1 = new TourModel1();
		tourModel1.setTourId(tourId);
		tourModel1.setContentid(contentid);
		
		TourModel1 item = null;
		
		try {
		item = tourModel1Service.getTouritem(tourModel1);
		
		} catch (Exception e) {
			// TODO: handle exception
			return web.redirect(null, e.getLocalizedMessage());
			
		}  
		model.addAttribute("item", item);

		return new ModelAndView("tour/TourList3");

	}

	@RequestMapping(value = "/tour/documentWrite.do", method = RequestMethod.GET)
	public ModelAndView TourWrite(Locale locale, Model model) {
	    /* WebHelper 초기화 */
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

		return new ModelAndView("/tour/documentWrite");



	}

	@RequestMapping(value = "/tour/documentWriter.do", method = RequestMethod.GET)
	public ModelAndView TourWriter(Locale locale, Model model) {
	    /* WebHelper 초기화 */
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
        
		return new ModelAndView("/tour/documentWriter");

	}





}
