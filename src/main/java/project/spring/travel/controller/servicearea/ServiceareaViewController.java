package project.spring.travel.controller.servicearea;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import project.spring.helper.RegexHelper;
import project.spring.helper.WebHelper;
import project.spring.travel.model.CategoryLike;
import project.spring.travel.model.Favorite;
import project.spring.travel.model.HotKeyword;
import project.spring.travel.model.Member;
import project.spring.travel.model.ServiceareaFoodAll;
import project.spring.travel.model.ServiceareaGroup;
import project.spring.travel.model.ServiceareaPs;
import project.spring.travel.service.CategoryLikeService;
import project.spring.travel.service.FavoriteService;
import project.spring.travel.service.HotKeywordService;
import project.spring.travel.service.ServiceareaFoodAllService;
import project.spring.travel.service.ServiceareaGroupService;
import project.spring.travel.service.ServiceareaPsService;

/**
 * @fileName    : ServiceareaViewController.java
 * @author      : 홍준성
 * @description : 휴게소 View 관련 컨트롤러 모음
 * @lastUpdate  : 2019. 5. 5.
 */
/** 컨트롤러 선언 */
@Controller
public class ServiceareaViewController {
    /** (1) 사용하고자 하는 Helper + Service 객체 선언 */
    // -> import org.slf4j.Logger;
    // -> import org.slf4j.LoggerFactory;
    Logger logger = LoggerFactory.getLogger(ServiceareaSearchController.class); // Log4j 객체 직접 생성
    // -> import org.apache.ibatis.session.SqlSession;
    @Autowired
    SqlSession sqlSession;
    // -> import project.spring.helper.WebHelper
    @Autowired
    WebHelper web;
    // -> import project.spring.helper.RegexHelper
    @Autowired
    RegexHelper regex;
    // -> import project.spring.helper.HttpHelper;
    @Autowired
    HttpHelper httpHelper;
    // -> import project.spring.helper.JsonHelper;
    @Autowired
    JsonHelper jsonHelper;
    // -> import project.spring.helper.PageHelper;
    @Autowired
    PageHelper pageHelper;
    // -> import project.java.travel.service.ServiceareaGroupService;
    @Autowired
    ServiceareaGroupService serviceareaGroupService;
    // -> import project.java.travel.service.ServiceareaPsService;
    @Autowired
    ServiceareaPsService serviceareaPsService;
    // -> import project.java.travel.service.ServiceareaFoodAllService;
    @Autowired
    ServiceareaFoodAllService serviceareaFoodAllService;
    // -> import project.spring.travel.service.FavoriteService;
    @Autowired
    FavoriteService favoriteService;
    // -> import project.spring.travel.service.CategoryLikeService;
    @Autowired
    CategoryLikeService categoryLikeService;
    // -> import project.spring.travel.service.HotKeywordService;
    @Autowired
    HotKeywordService hotKeywordService;

    /**
     * 휴게소 상세 View를 위한 컨트롤러
     */
    @RequestMapping(value = "/servicearea/servicearea_view_item.do", method = RequestMethod.GET)
    public ModelAndView serviceareaViewItem(Locale locale, Model model,
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.

        /** (3) 검색어 파라미터 받기 + Beans 설정 */
        int servicearea_groupId = web.getInt("servicearea_groupId");
        // 로그에 기록
        logger.debug("servicearea_groupId = " + servicearea_groupId);

        /** (4) 유효성 검사 */
        if(servicearea_groupId == 0) {
            return web.redirect(null, "전달된 휴게소 일련번호가 없습니다.");
        }
        // 일련번호 유지를 위해 View에 전달
        model.addAttribute("servicearea_groupId", servicearea_groupId);

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

        // Beans에 묶어 전달
        ServiceareaGroup serviceareaGroup = new ServiceareaGroup();
        serviceareaGroup.setServicearea_groupId(servicearea_groupId);

        /** (5) Service를 통한 SQL 수행 */
        // 휴게소 조회 결과를 저장하기 위한 객체
        ServiceareaGroup item = null;

        int memberId = 0;
        Member loginInfo = (Member) web.getSession("loginInfo");
        if (loginInfo != null) {
            memberId = loginInfo.getMemberId();
        }

        // 주소복사 기능을 위한 처리
        String curUrl = request.getRequestURL() + "?" + request.getQueryString();
        logger.debug("curUrl = " + curUrl);
        model.addAttribute("curUrl", curUrl);

        try {
            // 휴게소 정보를 추출
            item = serviceareaGroupService.selectServiceareaGroup(serviceareaGroup);
            if(item == null) {
                return web.redirect(null, "조회된 휴게소가 없습니다.");
            }

            // 좋아요 선택사항 조회
            CategoryLike like = new CategoryLike();
            like.setServicearea_groupId(item.getServicearea_groupId());
            like.setMemberId(memberId);
            // 찜리스트 선택사항 조회
            Favorite favorite = new Favorite();
            favorite.setServicearea_groupId(item.getServicearea_groupId());
            favorite.setMemberId(memberId);
            // View에서 사용하기 위한 처리
            boolean favoriteTarget = false;
            boolean likeTarget = false;

            if (favoriteService.favoriteExist(favorite) == 1) {
                favoriteTarget = true;
            }
            if (categoryLikeService.selectCategoryLike(like) == 1) {
                likeTarget = true;
            }
            // View에서 사용하기 위해 등록
            model.addAttribute("favoriteTarget", favoriteTarget);
            model.addAttribute("likeTarget", likeTarget);
        } catch (Exception e) {
            // 예외 발생 시, Service Layer에서 전달하는 메시지 내용을
            // 사용자에게 제시하고 이전페이지로 이동한다.
            return web.redirect(null, e.getLocalizedMessage());
        }

        // View에서 사용하기 위해 등록
        model.addAttribute("item", item);

        // 전체메뉴 목록을 조회하기 위한 Beans
        ServiceareaFoodAll serviceareaFoodAll = new ServiceareaFoodAll();
        serviceareaFoodAll.setStdRestNm(item.getServiceareaName());
        List<ServiceareaFoodAll> foodAllList = null;

        // 편의시설 목록과 운영시간을 취득하기 위해 휴게소이름으로 Beans를 묶어 조회
        ServiceareaPs serviceareaPs = new ServiceareaPs();
        serviceareaPs.setStdRestNm(item.getServiceareaName());
        List<ServiceareaPs> psList = null;

        try {
            foodAllList = serviceareaFoodAllService.selectServiceareaFoodAllListByStdRestNm(serviceareaFoodAll);
            psList = serviceareaPsService.selectServiceareaPsListByStdRestNm(serviceareaPs);
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        if(foodAllList.size() > 0) {
            // View에서 사용하기 위해 등록
            model.addAttribute("foodAllList", foodAllList);
        }

        if(psList.size() > 0) {
            String psNameBak = "";
            for(int i = 0; i < psList.size(); i++) {
                ServiceareaPs psItem = psList.get(i);
                String psName = null;
                if(psNameBak.indexOf(psItem.getPsName()) == -1) {
                    if(psList.size() > i + 1) {
                        psNameBak += psItem.getPsName() + ",";
                        psName = psItem.getPsName();
                    } else {
                        psNameBak += psItem.getPsName();
                        psName = psItem.getPsName();
                    }
                }
                String psStime = psItem.getStime();
                String psEtime = psItem.getEtime();

                if(psName != null) {
                    if(psName.equals("ATM")) {
                        model.addAttribute("atm", psName);
                        model.addAttribute("atmStime", psStime);
                        model.addAttribute("atmEtime", psEtime);
                    } else if(psName.equals("경정비")) {
                        model.addAttribute("carrepair", psName);
                        model.addAttribute("carrepairStime", psStime);
                        model.addAttribute("carrepairEtime", psEtime);
                    } else if(psName.equals("내고장특산물")) {
                        model.addAttribute("fruit", psName);
                        model.addAttribute("fruitStime", psStime);
                        model.addAttribute("fruitEtime", psEtime);
                    } else if(psName.equals("매점")) {
                        model.addAttribute("food", psName);
                        model.addAttribute("foodStime", psStime);
                        model.addAttribute("foodEtime", psEtime);
                    } else if(psName.equals("버스환승")) {
                        model.addAttribute("bustransfer", psName);
                        model.addAttribute("bustransferStime", psStime);
                        model.addAttribute("bustransferEtime", psEtime);
                    } else if(psName.equals("샤워실")) {
                        model.addAttribute("shower", psName);
                        model.addAttribute("showerStime", psStime);
                        model.addAttribute("showerEtime", psEtime);
                    } else if(psName.equals("세차장")) {
                        model.addAttribute("carwash", psName);
                        model.addAttribute("carwashStime", psStime);
                        model.addAttribute("carwashEtime", psEtime);
                    } else if(psName.equals("세탁실")) {
                        model.addAttribute("laundry", psName);
                        model.addAttribute("laundryStime", psStime);
                        model.addAttribute("laundryEtime", psEtime);
                    } else if(psName.equals("수면실")) {
                        model.addAttribute("sleep", psName);
                        model.addAttribute("sleepStime", psStime);
                        model.addAttribute("sleepEtime", psEtime);
                    } else if(psName.equals("수유실")) {
                        model.addAttribute("feeding", psName);
                        model.addAttribute("feedingStime", psStime);
                        model.addAttribute("feedingEtime", psEtime);
                    } else if(psName.equals("쉼터")) {
                        model.addAttribute("bench", psName);
                        model.addAttribute("benchStime", psStime);
                        model.addAttribute("benchEtime", psEtime);
                    } else if(psName.equals("약국")) {
                        model.addAttribute("pharmacy", psName);
                        model.addAttribute("pharmacyStime", psStime);
                        model.addAttribute("pharmacyEtime", psEtime);
                    } else if(psName.equals("휴게텔")) {
                        model.addAttribute("hotel", psName);
                        model.addAttribute("hotelStime", psStime);
                        model.addAttribute("hotelEtime", psEtime);
                    } else if(psName.equals("이발소")) {
                        model.addAttribute("barber", psName);
                        model.addAttribute("barberStime", psStime);
                        model.addAttribute("barberEtime", psEtime);
                    }
                } // End if
            } // End for
        } // End if

        return new ModelAndView("servicearea/servicearea_view_item");
    } // End serviceareaViewItem Method

    /**
     * 휴게소 게시물 좋아요를 처리하기 위한 액션 컨트롤러
     */
    @RequestMapping(value = "/servicearea/servicearea_like.do", method = RequestMethod.POST)
    public ModelAndView serviceareaLike(Locale locale, Model model,
            HttpServletRequest request, HttpServletResponse response) {
        /** 컨텐츠 타입 명시 */
        response.setContentType("application/json");

        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.

        Member loginInfo = (Member) web.getSession("loginInfo");
        if (loginInfo == null) {
            web.printJsonRt("X-LOGIN");
            return null;
        }

        logger.debug("memberId = " + loginInfo.getMemberId());

        int servicearea_groupId = web.getInt("servicearea_groupId"); // 게시물 일련번호 구분을 위한 파라미터
        logger.debug("servicearea_groupId = " + servicearea_groupId);

        String chk = web.getString("chk");
        logger.debug("chk = " + chk);

        int serviceaeraLike = Integer.parseInt(web.getString("serviceaeraLike")); // JSON에 넣기 전 String으로 파라미터를 받는다
        logger.debug("serviceaeraLike = " + serviceaeraLike);

        ServiceareaGroup serviceareaGroup = new ServiceareaGroup();
        serviceareaGroup.setServicearea_groupId(servicearea_groupId);
        serviceareaGroup.setServiceareaLike(serviceaeraLike);

        ServiceareaGroup likeItem = null; // 업데이트 후 받을 객체

        CategoryLike categoryLike = new CategoryLike(); // 선택된 게시물을 조회하기 위한 BoardList 객체 생성
        categoryLike.setServicearea_groupId(servicearea_groupId); // servicearea_groupId 파라미터 값을 객체에 servicearea_groupId에 저장
        categoryLike.setMemberId(loginInfo.getMemberId());

        try {
            // 타입에 따라 분류하여 증가 및 감소 한 값으로 키 재설정
            if (chk.equals("Y")) {
                if (categoryLikeService.selectCategoryLike(categoryLike) == 0) {
                    // 회원정보로부터 해당 휴게소에 좋아요 갯수가 0인 경우에만 좋아요 증가 처리
                    categoryLikeService.addCategoryLike(categoryLike); // 휴게소에 좋아요 한 정보를 추가
                    serviceareaGroupService.updateServiceareaGroupByLikeUp(serviceareaGroup); // 휴게소 좋아요 증가 처리
                    likeItem = serviceareaGroupService.selectServiceareaGroup(serviceareaGroup); // 휴게소 좋아요 갯수 조회
                }
            } else if (chk.equals("N")) {
                if (categoryLikeService.selectCategoryLike(categoryLike) == 1) {
                    // 회원정보로부터 해당 휴게소에 좋아요 갯수가 1인 경우에만 좋아요 감소 처리
                    categoryLikeService.deleteCategoryLike(categoryLike); // 휴게소에 좋아요 한 정보를 삭제
                    serviceareaGroupService.updateServiceareaGroupByLikeDown(serviceareaGroup); // 휴게소 좋아요 감소 처리
                    likeItem = serviceareaGroupService.selectServiceareaGroup(serviceareaGroup); // 휴게소 좋아요 갯수 조회
                }
            }
        } catch (Exception e) {
            web.printJsonRt(e.getLocalizedMessage());
            return null;
        }

        /** 인기검색어에 등록 시작 */
        try {
            if(likeItem != null) {
                if(likeItem.getServiceareaName() != null && !likeItem.getServiceareaName().trim().replace(" ", "").equals("")) {
                    HotKeyword hotKeywordInsert = new HotKeyword(); // 검색어를 Beans에 묶는다.
                    hotKeywordInsert.setKeyword(likeItem.getServiceareaName());
                    hotKeywordService.insertHotKeyword(hotKeywordInsert); // 검색어를 저장
                }
            }
        } catch (Exception e) {
            web.printJsonRt(e.getLocalizedMessage());
            return null;
        }
        /** 인기검색어에 등록 끝 */

        /** 처리 결과를 JSON으로 출력하기 */
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("rt", "OK");
        data.put("serviceareaLike", likeItem.getServiceareaLike());

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
    } // End serviceareaLike Method

    /**
     * 노선별 휴게소 목록 View를 위한 컨트롤러
     */
    @RequestMapping(value = "/servicearea/servicearea_line_in.do", method = RequestMethod.GET)
    public ModelAndView serviceareaLineIn(Locale locale, Model model,
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.

        /** (3) 검색어 파라미터 받기 + Beans 설정 */
        String routeName = web.getString("routeName");
        // 로그에 기록
        logger.debug("routeName = " + routeName);

        /** (4) 유효성 검사 */
        if(!regex.isValue(routeName)) {
            return web.redirect(null, "전달된 노선명이 없습니다.");
        }
        // 노선명 유지를 위해 View에 전달
        model.addAttribute("routeName", routeName);

        /** 인기검색어에 등록 시작  */
        try {
            if(routeName != null && !routeName.trim().replace(" ", "").equals("")) {
                HotKeyword hotKeywordInsert = new HotKeyword(); // 검색어를 Beans에 묶는다.
                hotKeywordInsert.setKeyword(routeName);
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

        // Beans에 묶어 전달
        ServiceareaGroup serviceareaGroup = new ServiceareaGroup();
        serviceareaGroup.setRouteName(routeName);

        /**
         * ===== 페이지 번호 파라미터 받기 =====
         * - 처음 페이지가 열릴 때 기본값 1로 설정된다.
         * - 페이지 번호를 클릭할 때 마다
         *     JSP페이지가 자기 스스로에게 page번호값을 전송하게 된다.
         */
        // 현재 페이지 번호에 대한 파라미터 받기
        int nowPage = Integer.parseInt(web.getString("page", "1"));
        // 로그에 기록
        logger.debug("nowPage = " + nowPage);

        /** (4) Service를 통한 SQL 수행 */
        /**
         * ===== 전체 게시물 수 구하기 =====
         * - 검색어가 있는 경우 검색결과를 반영해야 하기 때문에,
         *     전체 데이터 수를 구하는 작업은
         *     검색어 파라미터 처리 이후에 수행되어야 한다.
         */
        // 전체 데이터 수 조회하기
        int totalCount = 0;

        try {
            totalCount = serviceareaGroupService.selectServiceareaGroupCountByRouteName(serviceareaGroup);
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }

        // 페이지 번호에 대한 연산 수행 후, 조회조건값 지정을 위한 Beans에 추가하기
        pageHelper.pageProcess(nowPage, totalCount, 10, 5);
        serviceareaGroup.setLimitStart(pageHelper.getLimitStart());
        serviceareaGroup.setListCount(pageHelper.getListCount());
        // View의 페이지 구현에 사용하기 위해 등록
        model.addAttribute("pageHelper", pageHelper);

        /** (5) Service를 통한 SQL 수행 */
        // 휴게소 목록 조회 결과를 저장하기 위한 객체
        List<ServiceareaGroup> groupList = null;

        try {
            // 휴게소 목록 정보를 추출
            groupList = serviceareaGroupService.selectServiceareaGroupListByRouteName(serviceareaGroup);
            if(groupList == null) {
                return web.redirect(null, "조회된 휴게소 목록이 없습니다.");
            }
        } catch (Exception e) {
            // 예외 발생 시, Service Layer에서 전달하는 메시지 내용을
            // 사용자에게 제시하고 이전페이지로 이동한다.
            return web.redirect(null, e.getLocalizedMessage());
        }

        // View에서 사용하기 위해 등록
        model.addAttribute("groupList", groupList);

        return new ModelAndView("servicearea/servicearea_line_in");
    } // End serviceareaLineIn Method

    /**
     * 주변검색 액션을 처리하기 위한 컨트롤러
     */
    @RequestMapping(value = "/servicearea/servicearea_radar_ok.do", method = RequestMethod.GET)
    public String serviceareaRadarOk(Locale locale, Model model,
            HttpServletRequest request, HttpServletResponse response) {
        /** 컨텐츠 타입 명시 */
        response.setContentType("application/json");
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.

        /** (3) 위치 파라미터 받기 */
        String lng = web.getString("lng");
        String lat = web.getString("lat");
        // 로그에 기록
        logger.debug("lng = " + lng);
        logger.debug("lat = " + lat);

        /** (4) 유효성 검사 */
        if(!regex.isValue(lng) || !regex.isValue(lat)) {
            web.printJsonRt("위치 정보 동의를 하셔야 이용 가능합니다.");
            return null;
        }

        List<ServiceareaGroup> groupList = null;

        try {
            groupList = serviceareaGroupService.selectServiceareaGroupList(null);
        } catch (Exception e) {
            web.printJsonRt(e.getLocalizedMessage());
            return null;
        }

        /** 처리 결과를 JSON으로 출력하기 위해 List에 값을 담기 */
        List<String> items = new ArrayList<String>();

        Map<String, String> header = new HashMap<String, String>();
        header.put("Authorization", "KakaoAK 917780780abfbb0009a7f44cfb307f98");
        int i = 1;
        Boolean is_end = false;

        while(!is_end) {
            String url = "https://dapi.kakao.com/v2/local/search/keyword.json?page=" + i + "&query=휴게소&x=" + lng + "&y=" + lat + "&radius=20000";
            InputStream is = httpHelper.getWebData(url, "utf-8", header);
            JSONObject json = jsonHelper.getJSONObject(is, "utf-8");
            JSONObject meta = json.getJSONObject("meta");
            is_end = meta.getBoolean("is_end");

            // json 배열까지 접근한다.
            JSONArray documents = json.getJSONArray("documents");

            // 배열 데이터이므로 반복문 안에서 처리해야 한다.
            // 배열의 길이만큼 반복한다.
            for(int j = 0; j < documents.length(); j++) {
                // 배열의 j번째 JSON을 꺼낸다.
                JSONObject temp = documents.getJSONObject(j);
                // 데이터를 추출
                String id = temp.getString("id");

                for(int k = 0; k < groupList.size(); k++) {
                    ServiceareaGroup item = groupList.get(k);
                    String placeId = item.getPlaceId();
                    // DB에 저장된 장소ID와 검색된휴게소id와 일치하는지 검사
                    if(id != null && placeId != null) {
                        if(placeId.equals(id)) {
                            // 데이터 추가하기
                            items.add(item.getPlaceId());
                        } // End if
                    } // End if
                } // End for
            } // End for

            i++;
            // 연동해온 json을 로그에 기록
        } // End while

        if(items.size() > 0) {
            /** 처리 결과를 JSON으로 출력하기 */
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("rt", "OK");
            String placeId = "";
            for(int j = 0; j < items.size(); j++) {
                String item = items.get(j);
                if(placeId.indexOf(item) == -1) {
                    if(items.size() > j + 1) {
                        placeId += "'" + item + "'" + ",";
                    } else {
                        placeId += "'" + item + "'";
                    }
                }
            }
            data.put("placeId", placeId);

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
        } else {
            web.printJsonRt("주변에 조회된 휴게소가 없습니다.");
        }

        return null;
    } // End serviceareaRadarOk Method

    /**
     * 주변검색 View를 위한 컨트롤러
     */
    @RequestMapping(value = "/servicearea/servicearea_radar.do", method = RequestMethod.GET)
    public ModelAndView serviceareaRadar(Locale locale, Model model,
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.

        /** (3) 쿠키에 저장된 placeId를 추출 */
        String placeId = web.getString("placeId");

        // 유효성 검사
        if(!regex.isValue(placeId)) {
            return web.redirect(null, "전달된 위치 정보가 없습니다.");
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

        ServiceareaGroup serviceareaGroup = new ServiceareaGroup();
        serviceareaGroup.setPlaceId(placeId);

        /**
         * ===== 페이지 번호 파라미터 받기 =====
         * - 처음 페이지가 열릴 때 기본값 1로 설정된다.
         * - 페이지 번호를 클릭할 때 마다
         *     JSP페이지가 자기 스스로에게 page번호값을 전송하게 된다.
         */
        // 현재 페이지 번호에 대한 파라미터 받기
        int nowPage = Integer.parseInt(web.getString("page", "1"));
        // 로그에 기록
        logger.debug("nowPage = " + nowPage);

        /** (4) Service를 통한 SQL 수행 */
        /**
         * ===== 전체 게시물 수 구하기 =====
         * - 검색어가 있는 경우 검색결과를 반영해야 하기 때문에,
         *     전체 데이터 수를 구하는 작업은
         *     검색어 파라미터 처리 이후에 수행되어야 한다.
         */
        // 전체 데이터 수 조회하기
        int totalCount = 0;

        try {
            totalCount = serviceareaGroupService.selectServiceareaGroupCountByPlaceId(serviceareaGroup);
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }

        // 페이지 번호에 대한 연산 수행 후, 조회조건값 지정을 위한 Beans에 추가하기
        pageHelper.pageProcess(nowPage, totalCount, 10, 5);
        serviceareaGroup.setLimitStart(pageHelper.getLimitStart());
        serviceareaGroup.setListCount(pageHelper.getListCount());
        // View의 페이지 구현에 사용하기 위해 등록
        model.addAttribute("pageHelper", pageHelper);

        /** (5) Service를 통한 SQL 수행 */
        // 휴게소 목록 조회 결과를 저장하기 위한 객체
        List<ServiceareaGroup> groupList = null;

        try {
            // 휴게소 목록 정보를 추출
            groupList = serviceareaGroupService.selectServiceareaGroupListByPlaceId(serviceareaGroup);
            if(groupList == null) {
                return web.redirect(null, "조회된 휴게소 목록이 없습니다.");
            }
        } catch (Exception e) {
            // 예외 발생 시, Service Layer에서 전달하는 메시지 내용을
            // 사용자에게 제시하고 이전페이지로 이동한다.
            return web.redirect(null, e.getLocalizedMessage());
        }

        // View에서 사용하기 위해 등록
        model.addAttribute("groupList", groupList);

        return new ModelAndView("servicearea/servicearea_radar");
    } // End serviceareaRadar Method

    /**
     * 휴게소 유가정보 노선별 View를 위한 컨트롤러
     */
    @RequestMapping(value = "/servicearea/servicearea_oil_result.do", method = RequestMethod.GET)
    public ModelAndView serviceareaOilResult(Locale locale, Model model,
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.

        /** (3) 검색어 파라미터 받기 + Beans 설정 */
        String routeName = web.getString("routeName");
        // 로그에 기록
        logger.debug("routeName = " + routeName);

        /** (4) 유효성 검사 */
        if(!regex.isValue(routeName)) {
            return web.redirect(null, "전달된 노선명이 없습니다.");
        }
        // 노선명 유지를 위해 View에 전달
        model.addAttribute("routeName", routeName);

        /** 인기검색어에 등록 시작  */
        try {
            if(routeName != null && !routeName.trim().replace(" ", "").equals("")) {
                HotKeyword hotKeywordInsert = new HotKeyword(); // 검색어를 Beans에 묶는다.
                hotKeywordInsert.setKeyword(routeName);
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

        // Beans에 묶어 전달
        ServiceareaGroup serviceareaGroup = new ServiceareaGroup();
        serviceareaGroup.setRouteName(routeName);

        /**
         * ===== 페이지 번호 파라미터 받기 =====
         * - 처음 페이지가 열릴 때 기본값 1로 설정된다.
         * - 페이지 번호를 클릭할 때 마다
         *     JSP페이지가 자기 스스로에게 page번호값을 전송하게 된다.
         */
        // 현재 페이지 번호에 대한 파라미터 받기
        int nowPage = Integer.parseInt(web.getString("page", "1"));
        // 로그에 기록
        logger.debug("nowPage = " + nowPage);

        /** (4) Service를 통한 SQL 수행 */
        /**
         * ===== 전체 게시물 수 구하기 =====
         * - 검색어가 있는 경우 검색결과를 반영해야 하기 때문에,
         *     전체 데이터 수를 구하는 작업은
         *     검색어 파라미터 처리 이후에 수행되어야 한다.
         */
        // 전체 데이터 수 조회하기
        int totalCount = 0;

        try {
            totalCount = serviceareaGroupService.selectServiceareaGroupCountByRouteName(serviceareaGroup);
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }

        // 페이지 번호에 대한 연산 수행 후, 조회조건값 지정을 위한 Beans에 추가하기
        pageHelper.pageProcess(nowPage, totalCount, 10, 5);
        serviceareaGroup.setLimitStart(pageHelper.getLimitStart());
        serviceareaGroup.setListCount(pageHelper.getListCount());
        // View의 페이지 구현에 사용하기 위해 등록
        model.addAttribute("pageHelper", pageHelper);

        /** (5) Service를 통한 SQL 수행 */
        // 휴게소 목록 조회 결과를 저장하기 위한 객체
        List<ServiceareaGroup> groupList = null;

        try {
            // 휴게소 목록 정보를 추출
            groupList = serviceareaGroupService.selectServiceareaGroupListByRouteName(serviceareaGroup);
            if(groupList == null) {
                return web.redirect(null, "조회된 휴게소 목록이 없습니다.");
            }
        } catch (Exception e) {
            // 예외 발생 시, Service Layer에서 전달하는 메시지 내용을
            // 사용자에게 제시하고 이전페이지로 이동한다.
            return web.redirect(null, e.getLocalizedMessage());
        }

        // View에서 사용하기 위해 등록
        model.addAttribute("groupList", groupList);

        return new ModelAndView("servicearea/servicearea_oil_result");
    } // End serviceareaOilResult Method

    /**
     * 휴게소 전체 유가정보 View를 위한 컨트롤러
     */
    @RequestMapping(value = "/servicearea/servicearea_oil_all.do", method = RequestMethod.GET)
    public ModelAndView serviceareaOilAll(Locale locale, Model model,
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
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

        /**
         * ===== 페이지 번호 파라미터 받기 =====
         * - 처음 페이지가 열릴 때 기본값 1로 설정된다.
         * - 페이지 번호를 클릭할 때 마다
         *     JSP페이지가 자기 스스로에게 page번호값을 전송하게 된다.
         */
        // 현재 페이지 번호에 대한 파라미터 받기
        int nowPage = Integer.parseInt(web.getString("page", "1"));
        // 로그에 기록
        logger.debug("nowPage = " + nowPage);

        /** (4) Service를 통한 SQL 수행 */
        /**
         * ===== 전체 게시물 수 구하기 =====
         * - 검색어가 있는 경우 검색결과를 반영해야 하기 때문에,
         *     전체 데이터 수를 구하는 작업은
         *     검색어 파라미터 처리 이후에 수행되어야 한다.
         */
        // 전체 데이터 수 조회하기
        int totalCount = 0;

        try {
            totalCount = serviceareaGroupService.selectServiceareaGroupCount(null);
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }

        // 페이지 번호에 대한 연산 수행 후, 조회조건값 지정을 위한 Beans에 추가하기
        pageHelper.pageProcess(nowPage, totalCount, 10, 5);
        // Beans에 묶어 전달
        ServiceareaGroup serviceareaGroup = new ServiceareaGroup();
        serviceareaGroup.setLimitStart(pageHelper.getLimitStart());
        serviceareaGroup.setListCount(pageHelper.getListCount());
        // View의 페이지 구현에 사용하기 위해 등록
        model.addAttribute("pageHelper", pageHelper);

        /** (5) Service를 통한 SQL 수행 */
        // 휴게소 목록 조회 결과를 저장하기 위한 객체
        List<ServiceareaGroup> groupList = null;

        try {
            // 휴게소 목록 정보를 추출
            groupList = serviceareaGroupService.selectServiceareaGroupList(serviceareaGroup);
            if(groupList == null) {
                return web.redirect(null, "조회된 휴게소 목록이 없습니다.");
            }
        } catch (Exception e) {
            // 예외 발생 시, Service Layer에서 전달하는 메시지 내용을
            // 사용자에게 제시하고 이전페이지로 이동한다.
            return web.redirect(null, e.getLocalizedMessage());
        }

        // View에서 사용하기 위해 등록
        model.addAttribute("groupList", groupList);

        return new ModelAndView("servicearea/servicearea_oil_all");
    } // End serviceareaOilAll Method

    /**
     * 휴게소 대표메뉴 노선별 View를 위한 컨트롤러
     */
    @RequestMapping(value = "/servicearea/servicearea_food_result.do", method = RequestMethod.GET)
    public ModelAndView serviceareaFoodResult(Locale locale, Model model,
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.

        /** (3) 검색어 파라미터 받기 + Beans 설정 */
        String routeName = web.getString("routeName");
        // 로그에 기록
        logger.debug("routeName = " + routeName);

        /** (4) 유효성 검사 */
        if(!regex.isValue(routeName)) {
            return web.redirect(null, "전달된 노선명이 없습니다.");
        }
        // 노선명 유지를 위해 View에 전달
        model.addAttribute("routeName", routeName);

        /** 인기검색어에 등록 시작  */
        try {
            if(routeName != null && !routeName.trim().replace(" ", "").equals("")) {
                HotKeyword hotKeywordInsert = new HotKeyword(); // 검색어를 Beans에 묶는다.
                hotKeywordInsert.setKeyword(routeName);
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

        // Beans에 묶어 전달
        ServiceareaGroup serviceareaGroup = new ServiceareaGroup();
        serviceareaGroup.setRouteName(routeName);

        /**
         * ===== 페이지 번호 파라미터 받기 =====
         * - 처음 페이지가 열릴 때 기본값 1로 설정된다.
         * - 페이지 번호를 클릭할 때 마다
         *     JSP페이지가 자기 스스로에게 page번호값을 전송하게 된다.
         */
        // 현재 페이지 번호에 대한 파라미터 받기
        int nowPage = Integer.parseInt(web.getString("page", "1"));
        // 로그에 기록
        logger.debug("nowPage = " + nowPage);

        /** (4) Service를 통한 SQL 수행 */
        /**
         * ===== 전체 게시물 수 구하기 =====
         * - 검색어가 있는 경우 검색결과를 반영해야 하기 때문에,
         *     전체 데이터 수를 구하는 작업은
         *     검색어 파라미터 처리 이후에 수행되어야 한다.
         */
        // 전체 데이터 수 조회하기
        int totalCount = 0;

        try {
            totalCount = serviceareaGroupService.selectServiceareaGroupCountByRouteName(serviceareaGroup);
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }

        // 페이지 번호에 대한 연산 수행 후, 조회조건값 지정을 위한 Beans에 추가하기
        pageHelper.pageProcess(nowPage, totalCount, 10, 5);
        serviceareaGroup.setLimitStart(pageHelper.getLimitStart());
        serviceareaGroup.setListCount(pageHelper.getListCount());
        // View의 페이지 구현에 사용하기 위해 등록
        model.addAttribute("pageHelper", pageHelper);

        /** (5) Service를 통한 SQL 수행 */
        // 휴게소 목록 조회 결과를 저장하기 위한 객체
        List<ServiceareaGroup> groupList = null;

        try {
            // 휴게소 목록 정보를 추출
            groupList = serviceareaGroupService.selectServiceareaGroupListByRouteName(serviceareaGroup);
            if(groupList == null) {
                return web.redirect(null, "조회된 휴게소 목록이 없습니다.");
            }
        } catch (Exception e) {
            // 예외 발생 시, Service Layer에서 전달하는 메시지 내용을
            // 사용자에게 제시하고 이전페이지로 이동한다.
            return web.redirect(null, e.getLocalizedMessage());
        }

        // View에서 사용하기 위해 등록
        model.addAttribute("groupList", groupList);

        return new ModelAndView("servicearea/servicearea_food_result");
    } // End serviceareaFoodResult Method

    /**
     * 테마휴게소 노선별 View를 위한 컨트롤러
     */
    @RequestMapping(value = "/servicearea/servicearea_theme_result.do", method = RequestMethod.GET)
    public ModelAndView serviceareaThemeResult(Locale locale, Model model,
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.

        /** (3) 검색어 파라미터 받기 + Beans 설정 */
        String routeName = web.getString("routeName");
        // 로그에 기록
        logger.debug("routeName = " + routeName);

        /** (4) 유효성 검사 */
        if(!regex.isValue(routeName)) {
            return web.redirect(null, "전달된 노선명이 없습니다.");
        }
        // 노선명 유지를 위해 View에 전달
        model.addAttribute("routeName", routeName);

        /** 인기검색어에 등록 시작  */
        try {
            if(routeName != null && !routeName.trim().replace(" ", "").equals("")) {
                HotKeyword hotKeywordInsert = new HotKeyword(); // 검색어를 Beans에 묶는다.
                hotKeywordInsert.setKeyword(routeName);
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

        // Beans에 묶어 전달
        ServiceareaGroup serviceareaGroup = new ServiceareaGroup();
        serviceareaGroup.setRouteName(routeName);

        /**
         * ===== 페이지 번호 파라미터 받기 =====
         * - 처음 페이지가 열릴 때 기본값 1로 설정된다.
         * - 페이지 번호를 클릭할 때 마다
         *     JSP페이지가 자기 스스로에게 page번호값을 전송하게 된다.
         */
        // 현재 페이지 번호에 대한 파라미터 받기
        int nowPage = Integer.parseInt(web.getString("page", "1"));
        // 로그에 기록
        logger.debug("nowPage = " + nowPage);

        /** (4) Service를 통한 SQL 수행 */
        /**
         * ===== 전체 게시물 수 구하기 =====
         * - 검색어가 있는 경우 검색결과를 반영해야 하기 때문에,
         *     전체 데이터 수를 구하는 작업은
         *     검색어 파라미터 처리 이후에 수행되어야 한다.
         */
        // 전체 데이터 수 조회하기
        int totalCount = 0;

        try {
            totalCount = serviceareaGroupService.selectServiceareaGroupCountByRouteName(serviceareaGroup);
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }

        // 페이지 번호에 대한 연산 수행 후, 조회조건값 지정을 위한 Beans에 추가하기
        pageHelper.pageProcess(nowPage, totalCount, 10, 5);
        serviceareaGroup.setLimitStart(pageHelper.getLimitStart());
        serviceareaGroup.setListCount(pageHelper.getListCount());
        // View의 페이지 구현에 사용하기 위해 등록
        model.addAttribute("pageHelper", pageHelper);

        /** (5) Service를 통한 SQL 수행 */
        // 휴게소 목록 조회 결과를 저장하기 위한 객체
        List<ServiceareaGroup> groupList = null;

        try {
            // 휴게소 목록 정보를 추출
            groupList = serviceareaGroupService.selectServiceareaGroupListByRouteName(serviceareaGroup);
            if(groupList == null) {
                return web.redirect(null, "조회된 휴게소 목록이 없습니다.");
            }
        } catch (Exception e) {
            // 예외 발생 시, Service Layer에서 전달하는 메시지 내용을
            // 사용자에게 제시하고 이전페이지로 이동한다.
            return web.redirect(null, e.getLocalizedMessage());
        }

        // View에서 사용하기 위해 등록
        model.addAttribute("groupList", groupList);

        return new ModelAndView("servicearea/servicearea_theme_result");
    } // End serviceareaThemeResult Method

    /**
     * 경로탐색 결과 View를 위한 컨트롤러
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "/servicearea/servicearea_route_result.do", method = RequestMethod.GET)
    public ModelAndView serviceareaRouteResult(Locale locale, Model model,
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.

        /** (3) 검색어 파라미터 받기 + Beans 설정 */
        String start = web.getString("start");
        String goal = web.getString("goal");
        // 로그에 기록
        logger.debug("start = " + start);
        logger.debug("goal = " + goal);

        /** (4) 유효성 검사 */
        if(!regex.isValue(start)) {
            return web.redirect(web.getRootPath() + "/servicearea/servicearea_route.do", "전달된 출발지가 없습니다.");
        }
        if(!regex.isValue(goal)) {
            return web.redirect(web.getRootPath() + "/servicearea/servicearea_route.do", "전달된 도착지가 없습니다.");
        }
        // 선택값 유지를 위해 View에 전달
        model.addAttribute("start", start);
        model.addAttribute("goal", goal);

        // 휴게소 조회 결과를 저장하기 위한 객체
        List<ServiceareaGroup> groupList = null;
        groupList = (List<ServiceareaGroup>) web.getSession("groupList");
        if(groupList == null) {
        	return web.redirect(web.getRootPath() + "/servicearea/servicearea_route.do", "출발지와 도착지를 선택해 주세요.");
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

        // View에서 사용하기 위해 등록
        model.addAttribute("groupList", groupList);

        boolean code = false;
        Map<String, String> header = new HashMap<String, String>();
        header.put("X-NCP-APIGW-API-KEY-ID", "xt0wdpixgv");
        header.put("X-NCP-APIGW-API-KEY", "Lt8VinB2ui4bDNnZDAT8BQNfi1ryjyTWG3uEwE1y");

        String url = "https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving?start=" + start + "&goal=" + goal;
        InputStream is = httpHelper.getWebData(url, "utf-8", header);
        JSONObject json = jsonHelper.getJSONObject(is, "utf-8");
        logger.debug("json = " + json);
        // 응답 결과 코드 (0 = 정상)
        String message = json.getString("message"); // 응답결과 메시지 추출
        model.addAttribute("message", message);
        if(json.getInt("code") == 0) {
            code = !code;

            // json 배열까지 접근한다.
            JSONObject route = json.getJSONObject("route");
            JSONArray traoptimal = route.getJSONArray("traoptimal");

            // 배열의 j번째 JSON을 꺼낸다.
            JSONObject traoptimalTemp = traoptimal.getJSONObject(0);
            // temp 안의 JSON을 꺼낸다.
            JSONObject summary = traoptimalTemp.getJSONObject("summary");
            JSONArray path = traoptimalTemp.getJSONArray("path");
            model.addAttribute("pathLength", path.length());
            JSONArray section = traoptimalTemp.getJSONArray("section");
            JSONArray guide = traoptimalTemp.getJSONArray("guide");
            model.addAttribute("guideLength", guide.length());

            // 데이터를 추출
            int summaryDistance = summary.getInt("distance"); // 전체 경로 거리(meters)
            int summaryDuration = summary.getInt("duration"); // 전체 경로 소요 시간(millisecond(1/1000초))
            // 사용자에게 보여질 값으로 변환
            double kmForm = (double) summaryDistance;
            double summaryDistanceForm = (kmForm / 1000);              // Km
            int hours = (int) ((summaryDuration / (1000*60*60)) % 24); // 시
            int minutes = (int) ((summaryDuration / (1000*60)) % 60);  // 분
            int seconds = (int) (summaryDuration / 1000) % 60 ;        // 초
            model.addAttribute("summaryDistanceForm", summaryDistanceForm);
            model.addAttribute("hours", hours);
            model.addAttribute("minutes", minutes);
            model.addAttribute("seconds", seconds);

            String pathX = "";
            String pathY = "";
            for(int i = 0; i < path.length(); i++) {
                JSONArray pathTemp = path.getJSONArray(i);
                double pathXTemp = pathTemp.getDouble(0); // 주행 경로 X 좌표
                double pathYTemp = pathTemp.getDouble(1); // 주행 경로 Y 좌표
                if(pathX.indexOf("" + pathXTemp) == -1) {
                    if(path.length() > i + 1) {
                        pathX += pathXTemp + ",";
                    } else {
                        pathX += pathXTemp;
                    }
                }
                if(pathY.indexOf("" + pathYTemp) == -1) {
                    if(path.length() > i + 1) {
                        pathY += pathYTemp + ",";
                    } else {
                        pathY += pathYTemp;
                    }
                }
            }
            model.addAttribute("pathX", pathX);
            model.addAttribute("pathY", pathY);

            String name = "";
            for(int i = 0; i < section.length(); i++) {
                JSONObject sectionTemp = section.getJSONObject(i);
                String nameTemp = sectionTemp.getString("name"); // 도로명
                if(name.indexOf(nameTemp) == -1) {
                    if(section.length() > i + 1) {
                        name += nameTemp + ", ";
                    } else {
                        name += nameTemp;
                    }
                }
            }
            model.addAttribute("name", name);

            String[] instructions = new String[guide.length()];
            double[] guideDistance = new double[guide.length()];
            int[] nextHours = new int[guide.length()];
            int[] nextMinutes = new int[guide.length()];
            int[] nextSeconds = new int[guide.length()];
            for(int i = 0; i < guide.length(); i++) {
                JSONObject guideTemp = guide.getJSONObject(i);
                String instructionsTemp = guideTemp.getString("instructions"); // 안내 문구
                int guideDistanceTem = guideTemp.getInt("distance"); // 이전 경로로부터 거리(meters)
                int guideDurationTemp = guideTemp.getInt("duration"); // 이전 경로로부터 소요 시간(1/1000초))
                // 사용자에게 보여질 값으로 변환
                double guideKmForm = (double) guideDistanceTem;
                double guideDistanceTempD = (guideKmForm / 1000); // Km
                int nextHoursI = (int) ((guideDurationTemp / (1000*60*60)) % 24); // 시
                int nextMinutesI = (int) ((guideDurationTemp / (1000*60)) % 60);  // 분
                int nextSecondsI = (int) (guideDurationTemp / 1000) % 60 ;        // 초
                instructions[i] = instructionsTemp;
                guideDistance[i] = guideDistanceTempD;
                nextHours[i] = nextHoursI;
                nextMinutes[i] = nextMinutesI;
                nextSeconds[i] = nextSecondsI;
            }
            model.addAttribute("instructions", instructions);
            model.addAttribute("guideDistance", guideDistance);
            model.addAttribute("nextHours", nextHours);
            model.addAttribute("nextMinutes", nextMinutes);
            model.addAttribute("nextSeconds", nextSeconds);
        } else {
            return web.redirect(null, message);
        } // End if~else

        return new ModelAndView("servicearea/servicearea_route_result");
    } // End serviceareaRouteResult Method

}
