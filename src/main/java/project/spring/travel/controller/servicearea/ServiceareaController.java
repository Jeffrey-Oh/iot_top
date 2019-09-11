package project.spring.travel.controller.servicearea;

import java.io.InputStream;
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

import project.spring.helper.HttpHelper;
import project.spring.helper.JsonHelper;
import project.spring.helper.RegexHelper;
import project.spring.helper.WebHelper;
import project.spring.helper.XmltoJsonHelper;
import project.spring.travel.model.HotKeyword;
import project.spring.travel.model.Member;
import project.spring.travel.model.Servicearea;
import project.spring.travel.model.ServiceareaCs;
import project.spring.travel.model.ServiceareaFood;
import project.spring.travel.model.ServiceareaFoodAll;
import project.spring.travel.model.ServiceareaGroup;
import project.spring.travel.model.ServiceareaImage;
import project.spring.travel.model.ServiceareaOil;
import project.spring.travel.model.ServiceareaPlace;
import project.spring.travel.model.ServiceareaPs;
import project.spring.travel.model.ServiceareaTheme;
import project.spring.travel.service.HotKeywordService;
import project.spring.travel.service.ServiceareaCsService;
import project.spring.travel.service.ServiceareaFoodAllService;
import project.spring.travel.service.ServiceareaFoodService;
import project.spring.travel.service.ServiceareaGroupService;
import project.spring.travel.service.ServiceareaImageService;
import project.spring.travel.service.ServiceareaOilService;
import project.spring.travel.service.ServiceareaPlaceService;
import project.spring.travel.service.ServiceareaPsService;
import project.spring.travel.service.ServiceareaService;
import project.spring.travel.service.ServiceareaThemeService;

/**
 * @fileName    : ServiceareaController.java
 * @author      : 홍준성
 * @description : 휴게소 API 및 DB 처리 관련 컨트롤러 모음
 * @lastUpdate  : 2019. 4. 29.
 */
/** 컨트롤러 선언 */
@Controller
public class ServiceareaController {
    /** (1) 사용하고자 하는 Helper + Service 객체 선언 */
    // -> import org.slf4j.Logger;
    // -> import org.slf4j.LoggerFactory;
    Logger logger = LoggerFactory.getLogger(ServiceareaController.class); // Log4j 객체 직접 생성
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
    // -> import project.spring.helper.XmltoJsonHelper;
    @Autowired
    XmltoJsonHelper xmltoJsonHelper;
    // -> import project.java.travel.service.ServiceareaService;
    @Autowired
    ServiceareaService serviceareaService;
    // -> import project.java.travel.service.ServiceareaCsService;
    @Autowired
    ServiceareaCsService serviceareaCsService;
    // -> import project.spring.travel.service.ServiceareaFoodAllService;
    @Autowired
    ServiceareaFoodAllService serviceareaFoodAllService;
    // -> import project.spring.travel.service.ServiceareaFoodService;
    @Autowired
    ServiceareaFoodService serviceareaFoodService;
    // -> import project.spring.travel.service.ServiceareaOilService;
    @Autowired
    ServiceareaOilService serviceareaOilService;
    // -> import project.spring.travel.service.ServiceareaPsService;
    @Autowired
    ServiceareaPsService serviceareaPsService;
    // -> import project.spring.travel.service.ServiceareaThemeService;
    @Autowired
    ServiceareaThemeService serviceareaThemeService;
    // -> import project.spring.travel.service.ServiceareaPlaceService;
    @Autowired
    ServiceareaPlaceService serviceareaPlaceService;
    // -> import project.spring.travel.service.ServiceareaImageService;
    @Autowired
    ServiceareaImageService serviceareaImageService;
    // -> import project.spring.travel.service.ServiceareaGroupService;
    @Autowired
    ServiceareaGroupService serviceareaGroupService;
    // -> import project.spring.travel.service.HotKeywordService;
    @Autowired
    HotKeywordService hotKeywordService;
    
    /**
     * 휴게소 메인 View를 위한 컨트롤러
     */
    @RequestMapping(value = "/servicearea/servicearea_index.do", method = RequestMethod.GET)
    public ModelAndView serviceareaIndex(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** 인기검색어 영역 시작 */
        try {
            List<HotKeyword> hotKeywordList = null; // 조회된 검색어 리스트를 담을 리스트
            hotKeywordList = hotKeywordService.selectHotKeywordList(); // 최근 1달 검색어 조회
            model.addAttribute("hotKeywordList", hotKeywordList); // View에서 사용하기 위해 등록
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        /** 인기검색어 영역 끝 */
        
        return new ModelAndView("servicearea/servicearea_index");
    } // End serviceareaIndex Method
    
    /**
     * 휴게소 API 정보 추가를 위한 액션 컨트롤러
     */
    @RequestMapping(value = "/servicearea/servicearea_api_insert.do", method = RequestMethod.GET)
    public ModelAndView serviceareaApiInsert(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "servicearea_index.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
        }
        
        /** (3) 로그인 여부 검사 */
        // 관리자가 아니라면 이 페이지를 동작시켜서는 안된다.
        Member loginInfo = (Member) web.getSession("loginInfo");
        if(!loginInfo.getGrade().equals("Master")) {
            return web.redirect(web.getRootPath() + "/index.do", "관리자 계정만 접근 가능합니다.");
        }
        
        /** (4) 휴게소 API를 연동하기 + Service를 통한 휴게소 정보 저장 */
        String pageSize = "1";
        
        // 휴게소 기본 API 불안정으로 JSON을 얻지 못하기에 얻을 때까지 반복
        for(int i = 1; i <= Integer.parseInt(pageSize); i++) {
            String code = "ERROR";
            
            if(code.equals("ERROR")) {
                boolean bool = true;
                
                while(bool) {
                    String url = "http://data.ex.co.kr/openapi/locationinfo/locationinfoRest?key=2017172116&type=json&numOfRows=10000&pageNo=" + i;
                    InputStream is = httpHelper.getWebData(url, "utf-8");
                    JSONObject json = jsonHelper.getJSONObject(is, "utf-8");
                    pageSize = "" + json.get("pageSize");
                    code = "" + json.get("code");
                    
                    if(code.equals("SUCCESS")) {
                        bool = false;
                        // json 배열까지 접근한다.
                        JSONArray list = json.getJSONArray("list");
                        
                        // 배열 데이터이므로 반복문 안에서 처리해야 한다.
                        // 배열의 길이만큼 반복한다.
                        for(int j = 0; j < list.length(); j++) {
                            // 배열의 j번째 JSON을 꺼낸다.
                            JSONObject temp = list.getJSONObject(j);
                            
                            // 데이터를 추출
                            String unitName = temp.getString("unitName");
                            // 최신화 하기 위하 값을 변경
                            if(unitName.equals("천안(삼)휴게소(서울)")) {
                                unitName = "천안삼거리휴게소(서울)";
                            } else if(unitName.equals("구리휴게소(퇴계원)")) {
                                unitName = "구리휴게소(일산)";
                            } else if(unitName.equals("마장휴게소(남이)")) {
                                unitName = "마장프리미엄휴게소(하남)";
                            } else if(unitName.equals("마장휴게소(통영)")) {
                                unitName = "마장프리미엄휴게소(통영)";
                            } else if(unitName.equals("벌곡휴게소(회덕)")) {
                                unitName = "벌곡휴게소(대전)";
                            } else if(unitName.equals("보성녹차휴게소(무안)")) {
                                unitName = "보성녹차휴게소(목포)";
                            } else if(unitName.equals("시흥하늘휴게소(구리)")) {
                                unitName = "시흥하늘휴게소(브릿지스퀘어 판교)";
                            } else if(unitName.equals("시흥하늘휴게소(일산)")) {
                                unitName = "시흥하늘휴게소(브릿지스퀘어 일산)";
                            } else if(unitName.equals("오수휴게소(순천)")) {
                                unitName = "오수휴게소(광양)";
                            } else if(unitName.equals("오수휴게소(완주)")) {
                                unitName = "오수휴게소(전주)";
                            } else if(unitName.equals("의성휴게소(청주)")) {
                                unitName = "의성휴게소(당진)";
                            } else if(unitName.equals("청송휴게소(청주)")) {
                                unitName = "청송휴게소(상주)";
                            } else if(unitName.equals("함양산삼골휴게소(고서)")) {
                                unitName = "함양산삼골휴게소(광주)";
                            } else if(unitName.equals("황전휴게소(순천)")) {
                                unitName = "황전휴게소(광양)";
                            } else if(unitName.equals("황전휴게소(완주)")) {
                                unitName = "황전휴게소(전주)";
                            } else if(unitName.equals("가평휴게소(양양)")) {
                                unitName = "가평휴게소(춘천)";
                            } else if(unitName.equals("강릉휴게소(강릉)")) {
                                unitName = "강릉대관령휴게소(강릉)";
                            } else if(unitName.equals("강릉휴게소(인천)")) {
                                unitName = "강릉대관령휴게소(서창)";
                            } else if(unitName.equals("거창휴게소(고서)")) {
                                unitName = "거창한휴게소(광주)";
                            } else if(unitName.equals("계룡휴게소(대전)")) {
                                unitName = "계룡휴게소";
                            } else if(unitName.equals("고창고인돌휴게소(서울)")) {
                                unitName = "고창고인돌휴게소(시흥)";
                            } else if(unitName.equals("곡성휴게소(순천)")) {
                                unitName = "곡성기차마을휴게소(순천)";
                            } else if(unitName.equals("곡성휴게소(천안)")) {
                                unitName = "곡성기차마을휴게소(논산)";
                            } else if(unitName.equals("관촌휴게소(광양)")) {
                                unitName = "관촌휴게소(순천)";
                            } else if(unitName.equals("관촌휴게소(전주)")) {
                                unitName = "관촌휴게소(완주)";
                            } else if(unitName.equals("괴산휴게소(내서)")) {
                                unitName = "괴산휴게소(마산)";
                            } else if(unitName.equals("구정휴게소(주문진)")) {
                                unitName = "구정휴게소(속초)";
                            } else if(unitName.equals("금강휴게소(서울)")) {
                                continue;
                            } else if(unitName.equals("남성주휴게소(김천)")) {
                                unitName = "남성주휴게소(양평)";
                            } else if(unitName.equals("남성주휴게소(마산)")) {
                                unitName = "남성주휴게소(창원)";
                            } else if(unitName.equals("논공휴게소(고서)")) {
                                unitName = "논공휴게소(광주)";
                            } else if(unitName.equals("대천휴게소(서울)")) {
                                unitName = "대천휴게소(시흥)";
                            } else if(unitName.equals("동해휴게소(삼척)")) {
                                unitName = "동해휴게소(동해)";
                            } else if(unitName.equals("마이산휴게소(익산)")) {
                                unitName = "진안마이산휴게소(익산)";
                            } else if(unitName.equals("마이산휴게소(장수)")) {
                                unitName = "진안마이산휴게소(포항)";
                            } else if(unitName.equals("만남의광장")) {
                                unitName = "서울만남의광장휴게소(부산)";
                            } else if(unitName.equals("만남의광장(하남)")) {
                                continue;
                            } else if(unitName.equals("만남의광장(남이)")) {
                                unitName = "양촌만남의광장";
                            } else if(unitName.equals("면천임시휴게소")) {
                                unitName = "면천휴게소(영덕)";
                            } else if(unitName.equals("문경휴게소(내서)")) {
                                unitName = "문경휴게소(창원)";
                            } else if(unitName.equals("백양사휴게소(천안)")) {
                                unitName = "백양사휴게소(논산)";
                            } else if(unitName.equals("부여휴게소(동서천)")) {
                                unitName = "부여백제휴게소(서천)";
                            } else if(unitName.equals("부여휴게소(서공주)")) {
                                unitName = "부여백제휴게소(공주)";
                            } else if(unitName.equals("서여주휴게소(강릉)")) {
                                unitName = "서여주휴게소(양평)";
                            } else if(unitName.equals("서여주휴게소(인천)")) {
                                unitName = "서여주휴게소(마산)";
                            } else if(unitName.equals("서천휴게소(서울)")) {
                                unitName = "서천휴게소(시흥)";
                            } else if(unitName.equals("선산휴게소(내서)")) {
                                unitName = "선산휴게소(마산)";
                            } else if(unitName.equals("성주휴게소(마산)")) {
                                unitName = "성주휴게소(창원)";
                            } else if(unitName.equals("속리산휴게소")) {
                                unitName = "속리산휴게소(청주)";
                            } else if(unitName.equals("신풍휴게소(당진)")) {
                                unitName = "신풍휴게소(청원)";
                            } else if(unitName.equals("신풍휴게소(대전)")) {
                                unitName = "신풍휴게소(상주)";
                            } else if(unitName.equals("안성맞춤휴게소(안성)")) {
                                unitName = "안성맞춤휴게소(평택)";
                            } else if(unitName.equals("안성맞춤휴게소(음성)")) {
                                unitName = "안성맞춤휴게소(충주)";
                            } else if(unitName.equals("양촌휴게소(대전)")) {
                                continue;
                            } else if(unitName.equals("영산휴게소(내서)")) {
                                unitName = "영산휴게소(마산)";
                            } else if(unitName.equals("이서휴게소(천안)")) {
                                unitName = "이서휴게소(논산)";
                            } else if(unitName.equals("정읍휴게소(순천)")) {
                                unitName = "정읍녹두장군휴게소(순천)";
                            } else if(unitName.equals("정읍휴게소(천안)")) {
                                unitName = "정읍녹두장군휴게소(논산)";
                            } else if(unitName.equals("죽전휴게소")) {
                                unitName = "죽전휴게소(서울)";
                            } else if(unitName.equals("지리산휴게소(고서)")) {
                                unitName = "지리산휴게소(광주)";
                            } else if(unitName.equals("진안휴게소(익산)")) {
                                continue;
                            } else if(unitName.equals("진안휴게소(장수)")) {
                                continue;
                            } else if(unitName.equals("탄천휴게소(순천)")) {
                                unitName = "탄천휴게소(광주)";
                            } else if(unitName.equals("함평휴게소(목포)")) {
                                unitName = "함평천지휴게소(목포)";
                            } else if(unitName.equals("함평휴게소(서울)")) {
                                unitName = "함평천지휴게소(시흥)";
                            } else if(unitName.equals("행담도휴게소(목포)")) {
                                unitName = "행담도휴게소";
                            } else if(unitName.equals("홍성휴게소(무안)")) {
                                unitName = "홍성휴게소(목포)";
                            } else if(unitName.equals("홍성휴게소(서울)")) {
                                unitName = "홍성휴게소(시흥)";
                            } else if(unitName.equals("화성휴게소(서울)")) {
                                unitName = "화성휴게소(시흥)";
                            } else if(unitName.equals("청양휴게소(동서천)")) {
                                unitName = "청양휴게소(서천)";
                            } else if(unitName.equals("청양휴게소(서공주)")) {
                                unitName = "청양휴게소(공주)";
                            } else if(unitName.equals("충주휴게소(내서)")) {
                                unitName = "충주휴게소(마산)";
                            } else if(unitName.equals("와촌휴게소")) {
                                unitName = "와촌휴게소(포항)";
                            } else if(unitName.equals("청통휴게소")) {
                                unitName = "청통휴게소(대구)";
                            } else if(unitName.equals("홍천강휴게소")) {
                                unitName = "홍천강휴게소(춘천)";
                            } else if(unitName.equals("화서휴게소")) {
                                unitName = "화서휴게소(상주)";
                            }
                            String unitCode = temp.getString("unitCode");
                            String routeNo = temp.getString("routeNo");
                            String routeName = temp.getString("routeName");
                            if(unitName.equals("장유휴게소(부산)") && routeName.equals("남해선(순천-부산)")) {
                                continue;
                            }
                            // 노선명에 괄호가 들어간 부분을 수정 후 저장
                            if(routeName.indexOf("(") > -1) {
                                routeName = routeName.substring(0, routeName.indexOf("("));
                            }
                            String xValue = null;
                            if(temp.get("xValue") != null) {
                                xValue = "" + temp.get("xValue");
                            }
                            String yValue = null;
                            if(temp.get("yValue") != null) {
                                yValue = "" + temp.get("yValue");
                            }
                            
                            // 추출한 데이터를 휴게소 Beans에 주입
                            Servicearea servicearea = new Servicearea();
                            servicearea.setUnitName(unitName);
                            servicearea.setUnitCode(unitCode);
                            servicearea.setRouteNo(routeNo);
                            servicearea.setRouteName(routeName);
                            servicearea.setxValue(xValue);
                            servicearea.setyValue(yValue);
                            
                            try {
                                // 천등산휴게소(제천)이 중복되어 값이 제공됨으로 추출하여 분기 저장
                                if(servicearea.getUnitName().equals("천등산휴게소(제천)")) {
                                    Servicearea result = null;
                                    result = serviceareaService.selectServiceareaByUnitName(servicearea);
                                    
                                    // 이미 저장된 값이 있을 경우
                                    if(result != null) {
                                        servicearea.setUnitName("천등산휴게소(평택)");
                                    }
                                }
                                
                                // 저장하기 위한 Service를 호출
                                serviceareaService.insertServicearea(servicearea);
                            } catch (Exception e) {
                                return web.redirect(null, e.getLocalizedMessage());
                            }
                        } // End for
                    } // End if
                    
                    // 연동해온 json을 로그에 기록
                    logger.debug("휴게소 기본 API json " + i + " >> " + json);
                } // End while
            } // End if
        } // End for
        
        /** (5) 저장이 완료되었으므로 휴게소 메인페이지로 이동 */
        return web.redirect(web.getRootPath() + "/servicearea/servicearea_index.do", null);
    } // End serviceareaApiInsert Method
    
    /**
     * 휴게소 전기차 충전소API 정보 추가를 위한 액션 컨트롤러
     */
    @RequestMapping(value = "/servicearea/servicearea_cs_api_insert.do", method = RequestMethod.GET)
    public ModelAndView serviceareaCsApiInsert(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "servicearea_index.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
        }
        
        /** (3) 로그인 여부 검사 */
        // 관리자가 아니라면 이 페이지를 동작시켜서는 안된다.
        Member loginInfo = (Member) web.getSession("loginInfo");
        if(!loginInfo.getGrade().equals("Master")) {
            return web.redirect(web.getRootPath() + "/index.do", "관리자 계정만 접근 가능합니다.");
        }
        
        /** (4) 휴게소 API를 연동하기 + Service를 통한 휴게소 정보 저장 */
        boolean bool = true;
        
        // 휴게소 전기차 충전소 API 불안정으로 JSON을 얻지 못하기에 얻을 때까지 반복
        while(bool) {
            String url = "http://open.ev.or.kr:8080/openapi/services/rest/EvChargerService?serviceKey=%2FjScMOzMIX%2FZogYSNYCp951DXnY6OW9XLerAqh4rVFbywmEGFrmOBtcm6pxVzf6sH7nCDN1FbpPd8ZqiTK5IhQ%3D%3D";
            InputStream is = httpHelper.getWebData(url, "utf-8");
            JSONObject json = xmltoJsonHelper.getJSONObject(is, "utf-8");
            JSONObject responseBody = json.getJSONObject("response");
            JSONObject header = responseBody.getJSONObject("header");
            String resultCode = header.getString("resultCode");
            
            if(resultCode.equals("00")) {
                bool = false;
                
                // json 배열까지 접근한다.
                JSONObject body = responseBody.getJSONObject("body");
                JSONObject items = body.getJSONObject("items");
                JSONArray item = items.getJSONArray("item");
                
                // 배열 데이터이므로 반복문 안에서 처리해야 한다.
                // 배열의 길이만큼 반복한다.
                for(int j = 0; j < item.length(); j++) {
                    // 배열의 j번째 JSON을 꺼낸다.
                    JSONObject temp = item.getJSONObject(j);
                    
                    // 데이터를 추출
                    String chgerId = "" + temp.getInt("chgerId");
                    String statId = "" + temp.get("statId");
                    int stat = temp.getInt("stat");
                    String chgerType = temp.getString("chgerType");
                    String lat = "" + temp.getDouble("lat");
                    String lng = "" + temp.getDouble("lng");
                    String addrDoro = temp.getString("addrDoro");
                    String statNm = temp.getString("statNm");
                    // 값을 최신화 하기 위해 변경
                    if(statNm.indexOf("(하행선)") > -1) {
                        statNm = statNm.replace("(하행선)", "");
                    } else if(statNm.indexOf("(상행선)") > -1) {
                        statNm = statNm.replace("(상행선)", "");
                    } else if(statNm.indexOf("(하행)") > -1) {
                        statNm = statNm.replace("(하행)", "");
                    }
                    // 괄호의 위치를 일관시키기 위해 뒤로 이동
                    int start = statNm.indexOf("(");
                    int end = statNm.indexOf(")");
                    int length = statNm.length();
                    if(length > end && start > -1 && end > -1) {
                        String direction = statNm.substring(start, end + 1);
                        String first = statNm.substring(0, start);
                        String last = statNm.substring(end + 1);
                        statNm = first + last + direction;
                    }
                    if(statNm.equals("구리휴게소(외측방향)")) {
                        statNm = "구리휴게소(일산방향)";
                    } else if(statNm.equals("내린천휴게소 서울방향")) {
                        statNm = "내린천휴게소(서울방향)";
                    } else if(statNm.equals("내린천휴게소 양양방향")) {
                        statNm = "내린천휴게소(양양방향)";
                    } else if(statNm.equals("마장복합휴게소")) {
                        statNm = "마장프리미엄휴게소";
                    } else if(statNm.equals("시흥본선상공형휴게시설휴게소(일산방향)")) {
                        statNm = "시흥하늘휴게소(브릿지스퀘어 일산방향)";
                    } else if(statNm.equals("시흥본선상공형휴게시설휴게소(판교방향)")) {
                        statNm = "시흥하늘휴게소(브릿지스퀘어 판교방향)";
                    } else if(statNm.equals("의성휴게소(상주방향)")) {
                        statNm = "의성휴게소(당진방향)";
                    } else if(statNm.equals("홍천휴게소 서울방향")) {
                        statNm = "홍천휴게소(서울방향)";
                    } else if(statNm.equals("홍천휴게소 양양방향")) {
                        statNm = "홍천휴게소(양양방향)";
                    } else if(statNm.equals("황전휴게소(순천방향)")) {
                        statNm = "황전휴게소(광양방향)";
                    } else if(statNm.equals("강릉휴게소(강릉방향)")) {
                        statNm = "강릉대관령휴게소(강릉방향)";
                    } else if(statNm.equals("강릉휴게소(인천방향)")) {
                        statNm = "강릉대관령휴게소(서창방향)";
                    } else if(statNm.equals("거창휴게소(고서방향)")) {
                        statNm = "거창한휴게소(광주방향)";
                    } else if(statNm.equals("거창휴게소(옥포방향)")) {
                        statNm = "거창휴게소(대구방향)";
                    } else if(statNm.equals("곡성휴게소(논산방향)")) {
                        statNm = "곡성기차마을휴게소(논산방향)";
                    } else if(statNm.equals("곡성휴게소(순천방향)")) {
                        statNm = "곡성기차마을휴게소(순천방향)";
                    } else if(statNm.equals("김해휴게소 기장방향")) {
                        statNm = "김해금관가야휴게소(기장방향)";
                    } else if(statNm.equals("김해휴게소 창원방향")) {
                        statNm = "김해금관가야휴게소(창원방향)";
                    } else if(statNm.equals("낙동강구미휴게소(상행)")) {
                        statNm = "낙동강구미휴게소(상주방향)";
                    } else if(statNm.equals("낙동강의성휴게소(하행)")) {
                        statNm = "낙동강의성휴게소(영천방향)";
                    } else if(statNm.equals("롯데마트 마장휴게소점(통영방향)")) {
                        continue;
                    } else if(statNm.equals("진안휴게소(익산방향)")) {
                        statNm = "진안마이산휴게소(익산방향)";
                    } else if(statNm.equals("진안휴게소(장수방향)")) {
                        statNm = "진안마이산휴게소(포항방향)";
                    } else if(statNm.equals("서울만남휴게소(부산방향)")) {
                        statNm = "서울만남의광장휴게소(부산방향)";
                    } else if(statNm.equals("하남만남휴게소(통영방향)")) {
                        statNm = "하남드림휴게소(통영방향)";
                    } else if(statNm.equals("문경휴게소(마산방향)")) {
                        statNm = "문경휴게소(창원방향)";
                    } else if(statNm.equals("속리산휴게소(청원방향)")) {
                        statNm = "속리산휴게소(청주방향)";
                    } else if(statNm.equals("안성맞춤휴게소(음성방향)")) {
                        statNm = "안성맞춤휴게소(충주방향)";
                    } else if(statNm.equals("안성맞춤휴게소(제천방향)")) {
                        continue;
                    } else if(statNm.equals("이서휴게소(천안방향)")) {
                        statNm = "이서휴게소(논산방향)";
                    } else if(statNm.equals("정읍휴게소(논산방향)")) {
                        continue;
                    } else if(statNm.equals("정읍휴게소(순천방향)")) {
                        continue;
                    } else if(statNm.equals("지리산휴게소(고서방향)")) {
                        statNm = "지리산휴게소(광주방향)";
                    } else if(statNm.equals("지리산휴게소(옥포방향)")) {
                        statNm = "지리산휴게소(대구방향)";
                    } else if(statNm.equals("천안휴게소(서울방향)")) {
                        statNm = "천안삼거리휴게소(서울방향)";
                    } else if(statNm.equals("탄천휴게소(논산방향)")) {
                        statNm = "탄천휴게소(광주방향)";
                    } else if(statNm.equals("영종대교 휴게소 주차장")) {
                        statNm = "영종대교휴게소";
                    } else if(statNm.equals("38선 휴게소")) {
                        statNm = "38선휴게소";
                    } else if(statNm.equals("거제휴게소(거제해양파크)")) {
                        statNm = "거제해양파크";
                    } else if(statNm.equals("KH양주휴게소")) {
                        statNm = "양주휴게소";
                    }
                    // 일관된 네이밍을 위해 방향 값을 제거
                    if(statNm.indexOf("방향") > -1) {
                        statNm = statNm.replace("방향", "");
                    }
                    if(statNm.equals("성주휴게소 양평")) {
                        statNm = "성주휴게소(양평)";
                    } else if(statNm.equals("성주휴게소 창원")) {
                        statNm = "성주휴게소(창원)";
                    } else if(statNm.equals("청도새마을휴게소(대구)")) {
                        statNm = "청도휴게소(대구)";
                    } else if(statNm.equals("청도새마을휴게소(부산)")) {
                        statNm = "청도휴게소(부산)";
                    }
                    String useTime = null;
                    if(json.has("useTime")) {
                        useTime = temp.getString("useTime");
                    }
                    if(statNm.indexOf("휴게소") > -1) {
                        // 추출한 데이터를 휴게소 전기차 충전소 Beans에 주입
                        ServiceareaCs serviceareaCs = new ServiceareaCs();
                        serviceareaCs.setChgerId(chgerId);
                        serviceareaCs.setStatId(statId);
                        serviceareaCs.setStat(stat);
                        serviceareaCs.setChgerType(chgerType);
                        serviceareaCs.setLat(lat);
                        serviceareaCs.setLng(lng);
                        serviceareaCs.setAddrDoro(addrDoro);
                        serviceareaCs.setStatNm(statNm);
                        serviceareaCs.setUseTime(useTime != null ? useTime : "24시간 이용가능");
                        
                        try {
                            // 저장하기 위한 Service를 호출
                            serviceareaCsService.insertServiceareaCs(serviceareaCs);
                        } catch (Exception e) {
                            return web.redirect(null, e.getLocalizedMessage());
                        }
                    } // End if
                } // End for
            } // End if
            
            // 연동해온 json을 로그에 기록
            logger.debug("휴게소 전기차 충전소 API json >> " + json);
        } // End while
        
        /** (5) 휴게서 전기차 충전소에 저장된 위치 값을 기본 휴게소에서 비어있는 위치 값에 대체 저장 */
        List<Servicearea> list = null;
        try {
            list = serviceareaService.selectServiceareaList(null);
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        
        // 휴게소 정보를 한개씩 추출
        for(int i = 0; i < list.size(); i++) {
            Servicearea item = list.get(i);
            
            String query = item.getUnitName().trim().replace(" ", "");
            int queryIdx = query.indexOf("(");

            if(item.getxValue().equals("null") && item.getyValue().equals("null")) {
                // 기존 API에 위치 값이 없을 경우 전기차 충전소에 저장된 위치로 대체
                String queryCs = query;
                if(queryIdx > -1) {
                    queryCs = query.substring(0, queryIdx) + "(" +  query.substring(queryIdx + 1, queryIdx + 3);
                }
                // x, y 좌표를 대신할 값을 추출하기 위해 전기차 충전소 위치를 조회한다.
                ServiceareaCs serviceareaCs = new ServiceareaCs();
                serviceareaCs.setStatNm(queryCs);
                ServiceareaCs result = null;
                try {
                    result = serviceareaCsService.selectServiceareaCsByStatNm(serviceareaCs);
                } catch (Exception e) {
                    return web.redirect(null, e.getLocalizedMessage());
                }
                
                if(result != null) {
                    // x가 경도 lng도 경도
                    item.setxValue(result.getLng());
                    item.setyValue(result.getLat());
                    
                    // 새롭게 얻어온 위치 정보로 기본 휴게소 정보를 수정
                    try {
                        serviceareaService.updateServiceareaByServiceareaCsOrServiceareaPlace(item);
                    } catch (Exception e) {
                        return web.redirect(null, e.getLocalizedMessage());
                    }
                }
            } // End if
        } // End for
        
        /** (6) 저장이 완료되었으므로 휴게소 메인페이지로 이동 */
        return web.redirect(web.getRootPath() + "/servicearea/servicearea_index.do", null);
    } // End serviceareaCsApiInsert Method
    
    /**
     * 휴게소 전체 푸드메뉴 API 정보 추가를 위한 액션 컨트롤러
     */
    @RequestMapping(value = "/servicearea/servicearea_food_all_api_insert.do", method = RequestMethod.GET)
    public ModelAndView serviceareaFoodAllApiInsert(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "servicearea_index.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
        }
        
        /** (3) 로그인 여부 검사 */
        // 관리자가 아니라면 이 페이지를 동작시켜서는 안된다.
        Member loginInfo = (Member) web.getSession("loginInfo");
        if(!loginInfo.getGrade().equals("Master")) {
            return web.redirect(web.getRootPath() + "/index.do", "관리자 계정만 접근 가능합니다.");
        }
        
        /** (4) 휴게소 API를 연동하기 + Service를 통한 휴게소 정보 저장 */
        String pageSize = "1";
        
        // 휴게소 전체 푸드메뉴 API 불안정으로 JSON을 얻지 못하기에 얻을 때까지 반복
        for(int i = 1; i <= Integer.parseInt(pageSize); i++) {
            String code = "ERROR";
            
            if(code.equals("ERROR")) {
                boolean bool = true;
                
                while(bool) {
                    String url = "http://data.ex.co.kr/openapi/restinfo/restBestfoodList?key=2017172116&type=json&numOfRows=10000&pageNo=" + i;
                    InputStream is = httpHelper.getWebData(url, "utf-8");
                    JSONObject json = jsonHelper.getJSONObject(is, "utf-8");
                    pageSize = "" + json.get("pageSize");
                    code = "" + json.get("code");
                    
                    if(code.equals("SUCCESS")) {
                        bool = false;
                        
                        // json 배열까지 접근한다.
                        JSONArray list = json.getJSONArray("list");
                        
                        // 배열 데이터이므로 반복문 안에서 처리해야 한다.
                        // 배열의 길이만큼 반복한다.
                        for(int j = 0; j < list.length(); j++) {
                            // 배열의 j번째 JSON을 꺼낸다.
                            JSONObject temp = list.getJSONObject(j);
                            
                            // 데이터를 추출
                            String restCd = temp.getString("restCd");
                            String stdRestCd = temp.getString("stdRestCd");
                            String stdRestNm = null;
                            if(temp.getString("stdRestNm") != null) {
                                stdRestNm = temp.getString("stdRestNm");
                                // "민자" 값이 들어간 경우 제거하고 유지
                                if(stdRestNm.indexOf("(민자)") > -1) {
                                    stdRestNm = stdRestNm.replace("(민자)", "");
                                }
                                // 괄호의 위치를 일관시키기 위해 뒤로 이동
                                int start = stdRestNm.indexOf("(");
                                int end = stdRestNm.indexOf(")");
                                int length = stdRestNm.length();
                                if(length > end && start > -1 && end > -1) {
                                    String direction = stdRestNm.substring(start, end + 1);
                                    String first = stdRestNm.substring(0, start);
                                    String last = stdRestNm.substring(end + 1);
                                    stdRestNm = first + last + direction;
                                }
                                // 값을 최신화 작업
                                if(stdRestNm.equals("거창휴게소(고서)")) {
                                    stdRestNm = "거창한휴게소(광주)";
                                } else if(stdRestNm.equals("거창휴게소(옥포)")) {
                                    stdRestNm = "거창휴게소(대구)";
                                } else if(stdRestNm.equals("고창휴게소(목포)")) {
                                    stdRestNm = "고창고인돌휴게소(목포)";
                                } else if(stdRestNm.equals("고창휴게소(시흥)")) {
                                    stdRestNm = "고창고인돌휴게소(시흥)";
                                } else if(stdRestNm.equals("곡성휴게소(논산)")) {
                                    stdRestNm = "곡성기차마을휴게소(논산)";
                                } else if(stdRestNm.equals("곡성휴게소(순천)")) {
                                    stdRestNm = "곡성기차마을휴게소(순천)";
                                } else if(stdRestNm.equals("광주휴게소(광주)")) {
                                    stdRestNm = "경기광주휴게소(서울)";
                                } else if(stdRestNm.equals("광주휴게소(원주)")) {
                                    stdRestNm = "경기광주휴게소(원주)";
                                } else if(stdRestNm.equals("구리휴게소")) {
                                    stdRestNm = "구리휴게소(일산)";
                                } else if(stdRestNm.equals("군산휴게소(시흥)")) {
                                    stdRestNm = "군산휴게소(서울)";
                                } else if(stdRestNm.equals("금강휴게소")) {
                                    stdRestNm = "금강휴게소(부산)";
                                } else if(stdRestNm.equals("김해휴게소")) {
                                    stdRestNm = "김해금관가야휴게소";
                                } else if(stdRestNm.equals("남성주휴게소(김천)")) {
                                    stdRestNm = "남성주휴게소(양평)";
                                } else if(stdRestNm.equals("남성주휴게소(현풍)")) {
                                    stdRestNm = "남성주휴게소(창원)";
                                } else if(stdRestNm.equals("덕평휴게소")) {
                                    stdRestNm = "덕평휴게소(강릉)덕평휴게소(인천)";
                                } else if(stdRestNm.equals("마장복합휴게소")) {
                                    stdRestNm = "마장프리미엄휴게소(통영)마장프리미엄휴게소(하남)";
                                } else if(stdRestNm.equals("목감휴게소(시흥)")) {
                                    stdRestNm = "목감휴게소(서울)";
                                } else if(stdRestNm.equals("문경휴게소(마산)")) {
                                    stdRestNm = "문경휴게소(창원)";
                                } else if(stdRestNm.equals("서울만남휴게소(부산)")) {
                                    stdRestNm = "서울만남의광장휴게소(부산)";
                                } else if(stdRestNm.equals("성주휴게소(마산)")) {
                                    stdRestNm = "성주휴게소(창원)";
                                } else if(stdRestNm.equals("안성맞춤휴게소(음성)")) {
                                    stdRestNm = "안성맞춤휴게소(충주)";
                                } else if(stdRestNm.equals("양주휴게소(일산)")) {
                                    stdRestNm = "양주휴게소";
                                } else if(stdRestNm.equals("양평휴게소(광주)")) {
                                    stdRestNm = "양평휴게소";
                                } else if(stdRestNm.equals("양평휴게소(원주)")) {
                                    stdRestNm = "양평휴게소";
                                } else if(stdRestNm.equals("여산휴게소(논산)")) {
                                    stdRestNm = "여산휴게소(천안)";
                                } else if(stdRestNm.equals("여주휴게소(서창)")) {
                                    stdRestNm = "여주휴게소(인천)";
                                } else if(stdRestNm.equals("용인휴게소(서창)")) {
                                    stdRestNm = "용인휴게소(인천)";
                                } else if(stdRestNm.equals("의성휴게소(상주)")) {
                                    stdRestNm = "의성휴게소(당진)";
                                } else if(stdRestNm.equals("이서휴게소(천안)")) {
                                    stdRestNm = "이서휴게소(논산)";
                                } else if(stdRestNm.equals("이인휴게소(상)")) {
                                    stdRestNm = "이인휴게소(천안)";
                                } else if(stdRestNm.equals("장안휴게소(상)")) {
                                    stdRestNm = "장안휴게소(울산)";
                                } else if(stdRestNm.equals("장안휴게소(하)")) {
                                    stdRestNm = "장안휴게소(부산)";
                                } else if(stdRestNm.equals("정읍휴게소(순천)")) {
                                    stdRestNm = "정읍녹두장군휴게소(순천)";
                                } else if(stdRestNm.equals("정읍휴게소(천안)")) {
                                    stdRestNm = "정읍녹두장군휴게소(논산)";
                                } else if(stdRestNm.equals("진안휴게소(익산)")) {
                                    stdRestNm = "진안마이산휴게소(익산)";
                                } else if(stdRestNm.equals("진안휴게소(장수)")) {
                                    stdRestNm = "진안마이산휴게소(포항)";
                                } else if(stdRestNm.equals("천안휴게소(서울)")) {
                                    stdRestNm = "천안삼거리휴게소(서울)";
                                } else if(stdRestNm.equals("탄천휴게소(하)")) {
                                    stdRestNm = "탄천휴게소(광주)";
                                } else if(stdRestNm.equals("평창휴게소(서창)")) {
                                    stdRestNm = "평창휴게소(인천)";
                                } else if(stdRestNm.equals("함평나비휴게소(담양)")) {
                                    stdRestNm = "함평나비휴게소(무안)";
                                } else if(stdRestNm.equals("함평나비휴게소(대구)")) {
                                    stdRestNm = "함평나비휴게소(광주)";
                                } else if(stdRestNm.equals("함평휴게소(목포)")) {
                                    stdRestNm = "함평천지휴게소(목포)";
                                } else if(stdRestNm.equals("함평휴게소(서울)")) {
                                    stdRestNm = "함평천지휴게소(시흥)";
                                } else if(stdRestNm.equals("화서휴게소(영덕)")) {
                                    stdRestNm = "화서휴게소(상주)";
                                } else if(stdRestNm.equals("황전휴게소(순천)")) {
                                    stdRestNm = "황전휴게소(광양)";
                                } else if(stdRestNm.equals("횡성휴게소(서창)")) {
                                    stdRestNm = "횡성휴게소(인천)";
                                }
                            } else {
                                // 값이 없다면 저장하지 않는다.
                                continue;
                            }
                            String seq = temp.getString("seq");
                            String foodNm = temp.getString("foodNm");
                            String foodCost = null;
                            if(temp.getString("foodCost") != null) {
                                foodCost = temp.getString("foodCost").trim().replace(" ", "").toLowerCase();
                                if(!temp.getString("foodCost").equals("null")) {
                                    foodCost = temp.getString("foodCost");
                                    
                                    if(foodCost.indexOf("￦") == 0) {
                                        foodCost = foodCost.substring(1);
                                    }
                                    
                                    if(foodCost.indexOf("원") < 0) {
                                        foodCost += "원";
                                    }
                                    
                                    if(foodCost.length() >= 4 && foodCost.indexOf(",") < 0) {
                                        String start = foodCost.substring(0, foodCost.length() - 4);
                                        String end = foodCost.substring(foodCost.length() - 4);
                                        foodCost = start + "," + end;
                                    }
                                } else {
                                    continue;
                                }
                            }
                            String foodMaterial = null;
                            if(temp.get("foodMaterial") != null) {
                                foodMaterial = "" + temp.get("foodMaterial");
                            }
                            String etc = null;
                            if(temp.get("etc") != null) {
                                etc = "" + temp.get("etc");
                            }
                            String recommendyn = temp.getString("recommendyn");
                            String bestfoodyn = temp.getString("bestfoodyn");
                            String premiumyn = temp.getString("premiumyn");
                            String seasonMenu = temp.getString("seasonMenu");
                            String app = temp.getString("app");
                            String lastDtime = temp.getString("lastDtime");
                            String lsttmAltrDttm = temp.getString("lsttmAltrDttm");
                            
                            // 추출한 데이터를 휴게소 전체 푸드메뉴 Beans에 주입
                            ServiceareaFoodAll serviceareaFoodAll = new ServiceareaFoodAll();
                            serviceareaFoodAll.setRestCd(restCd);
                            serviceareaFoodAll.setStdRestCd(stdRestCd);
                            serviceareaFoodAll.setStdRestNm(stdRestNm);
                            serviceareaFoodAll.setSeq(seq);
                            serviceareaFoodAll.setFoodNm(foodNm);
                            serviceareaFoodAll.setFoodCost(foodCost);
                            serviceareaFoodAll.setFoodMaterial(foodMaterial.equals("null") ? null : foodMaterial);
                            serviceareaFoodAll.setEtc(etc.equals("null") ? null : etc);
                            serviceareaFoodAll.setRecommendyn(recommendyn);
                            serviceareaFoodAll.setBestfoodyn(bestfoodyn);
                            serviceareaFoodAll.setPremiumyn(premiumyn);
                            serviceareaFoodAll.setSeasonMenu(seasonMenu);
                            serviceareaFoodAll.setApp(app);
                            serviceareaFoodAll.setLastDtime(lastDtime);
                            serviceareaFoodAll.setLsttmAltrDttm(lsttmAltrDttm);
                            
                            try {
                                // 저장하기 위한 Service를 호출
                                serviceareaFoodAllService.insertServiceareaFoodAll(serviceareaFoodAll);
                            } catch (Exception e) {
                                return web.redirect(null, e.getLocalizedMessage());
                            }
                        } // End for
                    } // End if
                    
                    // 연동해온 json을 로그에 기록
                    logger.debug("휴게소 전체 푸드메뉴 API json >> " + json);
                } // End while
            } // End if
        } // End for
        
        /** (5) 저장이 완료되었으므로 휴게소 메인페이지로 이동 */
        return web.redirect(web.getRootPath() + "/servicearea/servicearea_index.do", null);
    } // End serviceareaFoodAllApiInsert Method
    
    /**
     * 휴게소 대표음식 API 정보 추가를 위한 액션 컨트롤러
     */
    @RequestMapping(value = "/servicearea/servicearea_food_api_insert.do", method = RequestMethod.GET)
    public ModelAndView serviceareaFoodApiInsert(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "servicearea_index.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
        }
        
        /** (3) 로그인 여부 검사 */
        // 관리자가 아니라면 이 페이지를 동작시켜서는 안된다.
        Member loginInfo = (Member) web.getSession("loginInfo");
        if(!loginInfo.getGrade().equals("Master")) {
            return web.redirect(web.getRootPath() + "/index.do", "관리자 계정만 접근 가능합니다.");
        }
        
        /** (4) 휴게소 API를 연동하기 + Service를 통한 휴게소 정보 저장 */
        String pageSize = "1";
        
        // 휴게소 대표메뉴 API 불안정으로 JSON을 얻지 못하기에 얻을 때까지 반복
        for(int i = 1; i <= Integer.parseInt(pageSize); i++) {
            String code = "ERROR";
            
            if(code.equals("ERROR")) {
                boolean bool = true;
                
                while(bool) {
                    String url = "http://data.ex.co.kr/openapi/business/representFoodServiceArea?key=2017172116&type=json&numOfRows=10000&pageNo=" + i;
                    InputStream is = httpHelper.getWebData(url, "utf-8");
                    JSONObject json = jsonHelper.getJSONObject(is, "utf-8");
                    pageSize = "" + json.get("pageSize");
                    code = "" + json.get("code");
                    
                    if(code.equals("SUCCESS")) {
                        bool = false;
                        
                        // json 배열까지 접근한다.
                        JSONArray list = json.getJSONArray("list");
                        
                        // 배열 데이터이므로 반복문 안에서 처리해야 한다.
                        // 배열의 길이만큼 반복한다.
                        for(int j = 0; j < list.length(); j++) {
                            // 배열의 j번째 JSON을 꺼낸다.
                            JSONObject temp = list.getJSONObject(j);
                            
                            // 데이터를 추출
                            String serviceAreaCode = temp.getString("serviceAreaCode");
                            String direction = temp.getString("direction");
                            String serviceAreaName = temp.getString("serviceAreaName");
                            if(serviceAreaName.indexOf("휴게소") < 0) {
                                serviceAreaName += "휴게소";
                            }
                            serviceAreaName += ("(" + direction + ")");
                            // 값 최신화 작업
                            if(serviceAreaName.equals("강릉휴게소(강릉")) {
                                serviceAreaName = "강릉대관령휴게소(강릉)";
                            } else if(serviceAreaName.equals("강릉휴게소(인천)")) {
                                serviceAreaName = "강릉대관령휴게소(서창)";
                            } else if(serviceAreaName.equals("강천산휴게소(무안)")) {
                                serviceAreaName = "강천산휴게소(광주)";
                            } else if(serviceAreaName.equals("거창휴게소(무안)")) {
                                serviceAreaName = "거창한휴게소(광주)";
                            } else if(serviceAreaName.equals("고창휴게소(목포)")) {
                                serviceAreaName = "고창고인돌휴게소(목포)";
                            } else if(serviceAreaName.equals("고창휴게소(시흥)")) {
                                serviceAreaName = "고창고인돌휴게소(시흥)";
                            } else if(serviceAreaName.equals("곡성휴게소(논산)")) {
                                serviceAreaName = "곡성기차마을휴게소(논산)";
                            } else if(serviceAreaName.equals("곡성휴게소(순천)")) {
                                serviceAreaName = "곡성기차마을휴게소(순천)";
                            } else if(serviceAreaName.equals("공주휴게소(상주)")) {
                                serviceAreaName = "공주휴게소(대전)";
                            } else if(serviceAreaName.equals("공주휴게소(창원)")) {
                                serviceAreaName = "공주휴게소(당진)";
                            } else if(serviceAreaName.equals("구리휴게소(퇴계원)")) {
                                serviceAreaName = "구리휴게소(일산)";
                            } else if(serviceAreaName.equals("군산휴게소(시흥)")) {
                                serviceAreaName = "군산휴게소(서울)";
                            } else if(serviceAreaName.equals("김해금관가야휴게소(진영)")) {
                                serviceAreaName = "김해금관가야휴게소";
                            } else if(serviceAreaName.equals("남성주휴게소(마산)")) {
                                serviceAreaName = "남성주휴게소(창원)";
                            } else if(serviceAreaName.equals("논공휴게소(무안)")) {
                                serviceAreaName = "논공휴게소(광주)";
                            } else if(serviceAreaName.equals("마장복합휴게소(통영)")) {
                                serviceAreaName = "마장프리미엄휴게소(통영)";
                            } else if(serviceAreaName.equals("마장휴게소(하남)")) {
                                serviceAreaName = "마장프리미엄휴게소(하남)";
                            } else if(serviceAreaName.equals("매송휴게소(시흥)")) {
                                serviceAreaName = "매송휴게소(서울)";
                            } else if(serviceAreaName.equals("목감휴게소(시흥)")) {
                                serviceAreaName = "목감휴게소(서울)";
                            } else if(serviceAreaName.equals("문경휴게소(마산)")) {
                                serviceAreaName = "문경휴게소(창원)";
                            } else if(serviceAreaName.equals("보성녹차휴게소(부산)")) {
                                serviceAreaName = "보성녹차휴게소(광양)";
                            } else if(serviceAreaName.equals("보성녹차휴게소(순천)")) {
                                serviceAreaName = "보성녹차휴게소(목포)";
                            } else if(serviceAreaName.equals("서산휴게소(시흥)")) {
                                serviceAreaName = "서산휴게소(서울)";
                            } else if(serviceAreaName.equals("서울만남휴게소(부산)")) {
                                serviceAreaName = "서울만남의광장휴게소(부산)";
                            } else if(serviceAreaName.equals("성주휴게소(마산)")) {
                                serviceAreaName = "성주휴게소(창원)";
                            } else if(serviceAreaName.equals("시흥하늘휴게소(퇴계원)")) {
                                serviceAreaName = "시흥하늘휴게소(브릿지스퀘어 일산)시흥하늘휴게소(브릿지스퀘어 판교)";
                            } else if(serviceAreaName.equals("안성맞춤휴게소(제천)")) {
                                serviceAreaName = "안성맞춤휴게소(충주)";
                            } else if(serviceAreaName.equals("여산휴게소(논산)")) {
                                serviceAreaName = "여산휴게소(천안)";
                            } else if(serviceAreaName.equals("영천휴게소(익산)")) {
                                serviceAreaName = "영천휴게소(대구)";
                            } else if(serviceAreaName.equals("예산휴게소(상주)")) {
                                serviceAreaName = "예산휴게소(대전)";
                            } else if(serviceAreaName.equals("예산휴게소(청원)")) {
                                serviceAreaName = "예산휴게소(당진)";
                            } else if(serviceAreaName.equals("외동휴게소(동해)")) {
                                serviceAreaName = "외동휴게소(울산)";
                            } else if(serviceAreaName.equals("외동휴게소(속초)")) {
                                serviceAreaName = "외동휴게소(포항)";
                            } else if(serviceAreaName.equals("의성휴게소(상주)")) {
                                serviceAreaName = "의성휴게소(당진)";
                            } else if(serviceAreaName.equals("의성휴게소(청원)")) {
                                serviceAreaName = "의성휴게소(영덕)";
                            } else if(serviceAreaName.equals("장유휴게소(냉정)")) {
                                serviceAreaName = "장유휴게소(부산)";
                            } else if(serviceAreaName.equals("정읍휴게소(논산)")) {
                                serviceAreaName = "정읍녹두장군휴게소(논산)";
                            } else if(serviceAreaName.equals("정읍휴게소(순천)")) {
                                serviceAreaName = "정읍녹두장군휴게소(순천)";
                            } else if(serviceAreaName.equals("주암휴게소(논산)")) {
                                serviceAreaName = "주암휴게소(천안)";
                            } else if(serviceAreaName.equals("지리산휴게소(무안)")) {
                                serviceAreaName = "지리산휴게소(광주)";
                            } else if(serviceAreaName.equals("진안휴게소(익산)")) {
                                serviceAreaName = "진안마이산휴게소(익산)";
                            } else if(serviceAreaName.equals("진안휴게소(포항)")) {
                                serviceAreaName = "진안마이산휴게소(포항)";
                            } else if(serviceAreaName.equals("천안휴게소(서울)")) {
                                serviceAreaName = "천안삼거리휴게소(서울)";
                            } else if(serviceAreaName.equals("청송휴게소(청원)")) {
                                serviceAreaName = "청송휴게소(영덕)";
                            } else if(serviceAreaName.equals("청통휴게소(익산)")) {
                                serviceAreaName = "청통휴게소(대구)";
                            } else if(serviceAreaName.equals("함평나비휴게소(대구)")) {
                                serviceAreaName = "함평나비휴게소(광주)";
                            } else if(serviceAreaName.equals("행담도휴게소(목포)")) {
                                serviceAreaName = "행담도휴게소";
                            } else if(serviceAreaName.equals("행담도휴게소(시흥)")) {
                                serviceAreaName = "행담도휴게소";
                            }
                            String routeCode = temp.getString("routeCode");
                            String routeName = temp.getString("routeName");
                            String batchMenu = null;
                            if(temp.get("batchMenu") != null) {
                                batchMenu = "" + temp.get("batchMenu");
                            }
                            String salePrice = null;
                            if(temp.get("salePrice") != null) {
                                salePrice = ("" + temp.get("salePrice")).trim().replace(" ", "").toLowerCase();
                                if(!salePrice.equals("null")) {
                                    if(salePrice.indexOf("￦") == 0) {
                                        salePrice = salePrice.substring(1);
                                    }
                                    
                                    if(salePrice.indexOf("원") < 0) {
                                        salePrice += "원";
                                    }
                                    
                                    if(salePrice.length() >= 4 && salePrice.indexOf(",") < 0) {
                                        String start = salePrice.substring(0, salePrice.length() - 4);
                                        String end = salePrice.substring(salePrice.length() - 4);
                                        salePrice = start + "," + end;
                                    }
                                } else {
                                    continue;
                                }
                            }
                            
                            // 추출한 데이터를 휴게소 대표메뉴 Beans에 주입
                            ServiceareaFood serviceareaFood = new ServiceareaFood();
                            serviceareaFood.setServiceAreaCode(serviceAreaCode);
                            serviceareaFood.setServiceAreaName(serviceAreaName);
                            serviceareaFood.setRouteCode(routeCode);
                            serviceareaFood.setRouteName(routeName);
                            serviceareaFood.setDirection(direction);
                            serviceareaFood.setBatchMenu(batchMenu.equals("null") ? null : batchMenu);
                            serviceareaFood.setSalePrice(salePrice == null ? null : salePrice);
                            
                            try {
                                // 저장하기 위한 Service를 호출
                                serviceareaFoodService.insertServiceareaFood(serviceareaFood);
                            } catch (Exception e) {
                                return web.redirect(null, e.getLocalizedMessage());
                            }
                        } // End for
                    } // End if
                    
                    // 연동해온 json을 로그에 기록
                    logger.debug("휴게소 대표메뉴 API json >> " + json);
                } // End while
            } // End if
        } // End for
        
        /** (5) 저장이 완료되었으므로 휴게소 메인페이지로 이동 */
        return web.redirect(web.getRootPath() + "/servicearea/servicearea_index.do", null);
    } // End serviceareaFoodApiInsert Method
    
    /**
     * 휴게소 유가정보 API 정보 추가를 위한 액션 컨트롤러
     */
    @RequestMapping(value = "/servicearea/servicearea_oil_api_insert.do", method = RequestMethod.GET)
    public ModelAndView serviceareaOilApiInsert(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "servicearea_index.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
        }
        
        /** (3) 로그인 여부 검사 */
        // 관리자가 아니라면 이 페이지를 동작시켜서는 안된다.
        Member loginInfo = (Member) web.getSession("loginInfo");
        if(!loginInfo.getGrade().equals("Master")) {
            return web.redirect(web.getRootPath() + "/index.do", "관리자 계정만 접근 가능합니다.");
        }
        
        /** (4) 휴게소 API를 연동하기 + Service를 통한 휴게소 정보 저장 */
        String pageSize = "1";
        
        // 휴게소 유가정보 API 불안정으로 JSON을 얻지 못하기에 얻을 때까지 반복
        for(int i = 1; i <= Integer.parseInt(pageSize); i++) {
            String code = "ERROR";
            
            if(code.equals("ERROR")) {
                boolean bool = true;
                
                while(bool) {
                    String url = "http://data.ex.co.kr/openapi/business/curStateStation?key=2017172116&type=json&numOfRows=10000&pageNo=" + i;
                    InputStream is = httpHelper.getWebData(url, "utf-8");
                    JSONObject json = jsonHelper.getJSONObject(is, "utf-8");
                    pageSize = "" + json.get("pageSize");
                    code = "" + json.get("code");
                    
                    if(code.equals("SUCCESS")) {
                        bool = false;
                        
                        // json 배열까지 접근한다.
                        JSONArray list = json.getJSONArray("list");
                        
                        // 배열 데이터이므로 반복문 안에서 처리해야 한다.
                        // 배열의 길이만큼 반복한다.
                        for(int j = 0; j < list.length(); j++) {
                            // 배열의 j번째 JSON을 꺼낸다.
                            JSONObject temp = list.getJSONObject(j);
                            
                            // 데이터를 추출
                            String routeCode = temp.getString("routeCode");
                            String routeName = temp.getString("routeName");
                            String direction = temp.getString("direction");
                            String serviceAreaCode = temp.getString("serviceAreaCode");
                            String serviceAreaName = temp.getString("serviceAreaName");
                            if(serviceAreaName.indexOf("(외측)") > -1) {
                                serviceAreaName = serviceAreaName.replace("(외측)", "");
                            } else if(serviceAreaName.indexOf("(내측)") > -1) {
                                serviceAreaName = serviceAreaName.replace("(내측)", "");
                            }
                            if(serviceAreaName.indexOf("휴게소") < 0) {
                                serviceAreaName += "휴게소";
                            }
                            serviceAreaName += ("(" + direction + ")");
                            // 값을 최신화 작업
                            if(serviceAreaName.equals("강릉휴게소(강릉)")) {
                                serviceAreaName = "강릉대관령휴게소(강릉)";
                            } else if(serviceAreaName.equals("강릉휴게소(인천)")) {
                                serviceAreaName = "강릉대관령휴게소(서창)";
                            } else if(serviceAreaName.equals("강천산휴게소(무안)")) {
                                serviceAreaName = "강천산휴게소(광주)";
                            } else if(serviceAreaName.equals("거창휴게소(무안)")) {
                                serviceAreaName = "거창한휴게소(광주)";
                            } else if(serviceAreaName.equals("곡성휴게소(논산)")) {
                                serviceAreaName = "곡성기차마을휴게소(논산)";
                            } else if(serviceAreaName.equals("곡성휴게소(순천)")) {
                                serviceAreaName = "곡성기차마을휴게소(순천)";
                            } else if(serviceAreaName.equals("공주휴게소(상주)")) {
                                serviceAreaName = "공주휴게소(대전)";
                            } else if(serviceAreaName.equals("공주휴게소(창원)")) {
                                serviceAreaName = "공주휴게소(당진)";
                            } else if(serviceAreaName.equals("구리남양주휴게소(판교)")) {
                                serviceAreaName = "구리휴게소(일산)";
                            } else if(serviceAreaName.equals("군산휴게소(시흥)")) {
                                serviceAreaName = "군산휴게소(서울)";
                            } else if(serviceAreaName.equals("김해휴게소(기장)")) {
                                serviceAreaName = "김해금관가야휴게소";
                            } else if(serviceAreaName.equals("김해휴게소(진영)")) {
                                serviceAreaName = "김해금관가야휴게소";
                            } else if(serviceAreaName.equals("남성주휴게소(마산)")) {
                                serviceAreaName = "남성주휴게소(창원)";
                            } else if(serviceAreaName.equals("논공휴게소(무안)")) {
                                serviceAreaName = "논공휴게소(광주)";
                            } else if(serviceAreaName.equals("마장복합휴게소(통영)")) {
                                serviceAreaName = "마장프리미엄휴게소(통영)";
                            } else if(serviceAreaName.equals("마장복합휴게소(하남)")) {
                                serviceAreaName = "마장프리미엄휴게소(하남)";
                            } else if(serviceAreaName.equals("매송휴게소(시흥)")) {
                                serviceAreaName = "매송휴게소(서울)";
                            } else if(serviceAreaName.equals("목감휴게소(시흥)")) {
                                serviceAreaName = "목감휴게소(서울)";
                            } else if(serviceAreaName.equals("문경휴게소(마산)")) {
                                serviceAreaName = "문경휴게소(창원)";
                            } else if(serviceAreaName.equals("보성녹차휴게소(부산)")) {
                                serviceAreaName = "보성녹차휴게소(광양)";
                            } else if(serviceAreaName.equals("보성녹차휴게소(순천)")) {
                                serviceAreaName = "보성녹차휴게소(목포)";
                            } else if(serviceAreaName.equals("서산휴게소(시흥)")) {
                                serviceAreaName = "서산휴게소(서울)";
                            } else if(serviceAreaName.equals("서울만남휴게소(부산)")) {
                                serviceAreaName = "서울만남의광장휴게소(부산)";
                            } else if(serviceAreaName.equals("성주휴게소(마산)")) {
                                serviceAreaName = "성주휴게소(창원)";
                            } else if(serviceAreaName.equals("속리산휴게소(청원)")) {
                                serviceAreaName = "속리산휴게소(청주)";
                            } else if(serviceAreaName.equals("시흥휴게소(퇴계원)")) {
                                serviceAreaName = "시흥하늘휴게소(브릿지스퀘어 일산)";
                            } else if(serviceAreaName.equals("시흥휴게소(판교)")) {
                                serviceAreaName = "시흥하늘휴게소(브릿지스퀘어 판교)";
                            } else if(serviceAreaName.equals("안성맞춤휴게소(제천)")) {
                                serviceAreaName = "안성맞춤휴게소(충주)";
                            } else if(serviceAreaName.equals("여산휴게소(논산)")) {
                                serviceAreaName = "여산휴게소(천안)";
                            } else if(serviceAreaName.equals("영천휴게소(익산)")) {
                                serviceAreaName = "영천휴게소(대구)";
                            } else if(serviceAreaName.equals("예산휴게소(상주)")) {
                                serviceAreaName = "예산휴게소(대전)";
                            } else if(serviceAreaName.equals("예산휴게소(청원)")) {
                                serviceAreaName = "예산휴게소(당진)";
                            } else if(serviceAreaName.equals("외동휴게소(동해)")) {
                                serviceAreaName = "외동휴게소(울산)";
                            } else if(serviceAreaName.equals("외동휴게소(속초)")) {
                                serviceAreaName = "외동휴게소(포항)";
                            } else if(serviceAreaName.equals("의성휴게소(상주)")) {
                                serviceAreaName = "의성휴게소(당진)";
                            } else if(serviceAreaName.equals("의성휴게소(청원)")) {
                                serviceAreaName = "의성휴게소(영덕)";
                            } else if(serviceAreaName.equals("의왕휴게소(판교)")) {
                                serviceAreaName = "의왕휴게소(청계)";
                            } else if(serviceAreaName.equals("장유휴게소(냉정)")) {
                                serviceAreaName = "장유휴게소(부산)";
                            } else if(serviceAreaName.equals("정읍휴게소(논산)")) {
                                serviceAreaName = "정읍녹두장군휴게소(논산)";
                            } else if(serviceAreaName.equals("정읍휴게소(순천)")) {
                                serviceAreaName = "정읍녹두장군휴게소(순천)";
                            } else if(serviceAreaName.equals("주암휴게소(논산)")) {
                                serviceAreaName = "주암휴게소(천안)";
                            } else if(serviceAreaName.equals("지리산휴게소(무안)")) {
                                serviceAreaName = "지리산휴게소(광주)";
                            } else if(serviceAreaName.equals("진안휴게소(익산)")) {
                                serviceAreaName = "진안마이산휴게소(익산)";
                            } else if(serviceAreaName.equals("진안휴게소(포항)")) {
                                serviceAreaName = "진안마이산휴게소(포항)";
                            } else if(serviceAreaName.equals("천안휴게소(서울)")) {
                                serviceAreaName = "천안삼거리휴게소(서울)";
                            } else if(serviceAreaName.equals("청송휴게소(청원)")) {
                                serviceAreaName = "청송휴게소(영덕)";
                            } else if(serviceAreaName.equals("청통휴게소(익산)")) {
                                serviceAreaName = "청통휴게소(대구)";
                            } else if(serviceAreaName.equals("함평나비휴게소(대구)")) {
                                serviceAreaName = "함평나비휴게소(광주)";
                            } else if(serviceAreaName.equals("행담도휴게소(목포)")) {
                                serviceAreaName = "행담도휴게소";
                            } else if(serviceAreaName.equals("행담도휴게소(시흥)")) {
                                continue;
                            }
                            String oilCompany = temp.getString("oilCompany");
                            String telNo = "" + temp.get("telNo");
                            String gasolinePrice = temp.getString("gasolinePrice");
                            String diselPrice = temp.getString("diselPrice");
                            String lpgYn = temp.getString("lpgYn");
                            String lpgPrice = temp.getString("lpgPrice");
                            
                            // 추출한 데이터를 휴게소 유가정보 Beans에 주입
                            ServiceareaOil serviceareaOil = new ServiceareaOil();
                            serviceareaOil.setRouteCode(routeCode);
                            serviceareaOil.setRouteName(routeName);
                            serviceareaOil.setDirection(direction);
                            serviceareaOil.setServiceAreaCode(serviceAreaCode);
                            serviceareaOil.setServiceAreaName(serviceAreaName);
                            serviceareaOil.setOilCompany(oilCompany);
                            serviceareaOil.setTelNo(telNo);
                            serviceareaOil.setGasolinePrice(gasolinePrice);
                            serviceareaOil.setDiselPrice(diselPrice);
                            serviceareaOil.setLpgYn(lpgYn);
                            serviceareaOil.setLpgPrice(lpgPrice);
                            
                            try {
                                // 저장하기 위한 Service를 호출
                                serviceareaOilService.insertServiceareaOil(serviceareaOil);
                            } catch (Exception e) {
                                return web.redirect(null, e.getLocalizedMessage());
                            }
                        } // End for
                    } // End if
                    
                    // 연동해온 json을 로그에 기록
                    logger.debug("휴게소 유가정보 API json >> " + json);
                } // End while
            } // End if
        } // End for
        
        /** (5) 저장이 완료되었으므로 휴게소 메인페이지로 이동 */
        return web.redirect(web.getRootPath() + "/servicearea/servicearea_index.do", null);
    } // End serviceareaOilApiInsert Method
    
    /**
     * 휴게소 편의시설 API 정보 추가를 위한 액션 컨트롤러
     */
    @RequestMapping(value = "/servicearea/servicearea_ps_api_insert.do", method = RequestMethod.GET)
    public ModelAndView serviceareaPsApiInsert(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "servicearea_index.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
        }
        
        /** (3) 로그인 여부 검사 */
        // 관리자가 아니라면 이 페이지를 동작시켜서는 안된다.
        Member loginInfo = (Member) web.getSession("loginInfo");
        if(!loginInfo.getGrade().equals("Master")) {
            return web.redirect(web.getRootPath() + "/index.do", "관리자 계정만 접근 가능합니다.");
        }
        
        /** (4) 휴게소 API를 연동하기 + Service를 통한 휴게소 정보 저장 */
        String pageSize = "1";
        
        // 휴게소 편의시설 API 불안정으로 JSON을 얻지 못하기에 얻을 때까지 반복
        for(int i = 1; i <= Integer.parseInt(pageSize); i++) {
            String code = "ERROR";
            
            if(code.equals("ERROR")) {
                boolean bool = true;
                
                while(bool) {
                    String url = "http://data.ex.co.kr/openapi/restinfo/restConvList?key=2017172116&type=json&numOfRows=10000&pageNo=" + i;
                    InputStream is = httpHelper.getWebData(url, "utf-8");
                    JSONObject json = jsonHelper.getJSONObject(is, "utf-8");
                    pageSize = "" + json.get("pageSize");
                    code = "" + json.get("code");
                    
                    if(code.equals("SUCCESS")) {
                        bool = false;
                        
                        // json 배열까지 접근한다.
                        JSONArray list = json.getJSONArray("list");
                        
                        // 배열 데이터이므로 반복문 안에서 처리해야 한다.
                        // 배열의 길이만큼 반복한다.
                        for(int j = 0; j < list.length(); j++) {
                            // 배열의 j번째 JSON을 꺼낸다.
                            JSONObject temp = list.getJSONObject(j);
                            
                            // 데이터를 추출
                            String stdRestCd = temp.getString("stdRestCd");
                            String stdRestNm = null;
                            if(temp.get("stdRestNm") != null) {
                                stdRestNm = "" + temp.get("stdRestNm");
                                // "null"로 저장된 값이 있기에 제외시킨다.
                                if(stdRestNm.equals("null")) {
                                    continue;
                                } else if(stdRestNm.indexOf("졸음쉼터") > -1) {
                                    // 졸음쉼터로 저장되는 이름값을 제외
                                    continue;
                                } else if(stdRestNm.indexOf("주유소") > -1) {
                                    // 주유소로 저장되는 이름값을 휴게소로 변경
                                    stdRestNm = stdRestNm.replace("주유소", "휴게소");
                                }
                                // "민자" 값이 들어간 경우 제거하고 유지
                                if(stdRestNm.indexOf("(민자)") > -1) {
                                    stdRestNm = stdRestNm.replace("(민자)", "");
                                } else if(stdRestNm.indexOf("_민자") > -1) {
                                    stdRestNm = stdRestNm.replace("_민자", "");
                                } else if(stdRestNm.indexOf("-") > -1) {
                                    // "-" 값이 들어간 경우 제거
                                    continue;
                                }
                                // 괄호의 위치를 일관시키기 위해 뒤로 이동
                                int start = stdRestNm.indexOf("(");
                                int end = stdRestNm.indexOf(")");
                                int length = stdRestNm.length();
                                if(length > end && start > -1 && end > -1) {
                                    String direction = stdRestNm.substring(start, end + 1);
                                    String first = stdRestNm.substring(0, start);
                                    String last = stdRestNm.substring(end + 1);
                                    stdRestNm = first + last + direction;
                                }
                                
                                // 정보가 최신화가 안된 값을 바꾸어 저장
                                if(stdRestNm.equals("강릉휴게소(강릉)")) {
                                    stdRestNm = "강릉대관령휴게소(강릉)";
                                } else if(stdRestNm.equals("강릉휴게소(서창)")) {
                                    stdRestNm = "강릉대관령휴게소(서창)";
                                } else if(stdRestNm.equals("거창휴게소(고서)")) {
                                    stdRestNm = "거창한휴게소(광주)";
                                } else if(stdRestNm.equals("거창휴게소(옥포)")) {
                                    stdRestNm = "거창휴게소(대구)";
                                } else if(stdRestNm.equals("고창휴게소(목포)")) {
                                    stdRestNm = "고창고인돌휴게소(목포)";
                                } else if(stdRestNm.equals("고창휴게소(시흥)")) {
                                    stdRestNm = "고창고인돌휴게소(시흥)";
                                } else if(stdRestNm.equals("곡성휴게소(논산)")) {
                                    stdRestNm = "곡성기차마을휴게소(논산)";
                                } else if(stdRestNm.equals("곡성휴게소(순천)")) {
                                    stdRestNm = "곡성기차마을휴게소(순천)";
                                } else if(stdRestNm.indexOf("구리남양주휴게소") > -1) {
                                    continue;
                                } else if(stdRestNm.equals("구리휴게소")) {
                                    stdRestNm = "구리휴게소(일산)";
                                } else if(stdRestNm.equals("군산휴게소(시흥)")) {
                                    stdRestNm = "군산휴게소(서울)";
                                } else if(stdRestNm.equals("금강휴게소")) {
                                    continue;
                                } else if(stdRestNm.equals("김포휴게소(일산)")) {
                                    continue;
                                } else if(stdRestNm.equals("김해휴게소(창원)")) {
                                    stdRestNm = "김해금관가야휴게소";
                                } else if(stdRestNm.equals("남성주휴게소(김천)")) {
                                    stdRestNm = "남성주휴게소(양평)";
                                } else if(stdRestNm.equals("남성주휴게소(현풍)")) {
                                    stdRestNm = "남성주휴게소(창원)";
                                } else if(stdRestNm.equals("논공휴게소(옥포)")) {
                                    continue;
                                } else if(stdRestNm.equals("덕평휴게소")) {
                                    stdRestNm = "덕평휴게소(강릉)덕평휴게소(인천)";
                                } else if(stdRestNm.equals("마장복합휴게소")) {
                                    stdRestNm = "마장프리미엄휴게소(통영)마장프리미엄휴게소(하남)";
                                } else if(stdRestNm.equals("목감휴게소(시흥)")) {
                                    stdRestNm = "목감휴게소(서울)";
                                } else if(stdRestNm.equals("문경휴게소(마산)")) {
                                    stdRestNm = "문경휴게소(창원)";
                                } else if(stdRestNm.equals("서산휴게소(시흥)")) {
                                    stdRestNm = "서산휴게소(서울)";
                                } else if(stdRestNm.equals("서울만남휴게소(부산)")) {
                                    stdRestNm = "서울만남의광장휴게소(부산)";
                                } else if(stdRestNm.equals("서하남휴게소(일산)")) {
                                    stdRestNm = "서하남휴게소(판교)";
                                } else if(stdRestNm.equals("성주휴게소(마산)")) {
                                    continue;
                                } else if(stdRestNm.equals("안성맞춤휴게소(음성)")) {
                                    continue;
                                } else if(stdRestNm.equals("여산휴게소(논산)")) {
                                    stdRestNm = "여산휴게소(천안)";
                                } else if(stdRestNm.equals("여주휴게소(서창)")) {
                                    stdRestNm = "여주휴게소(인천)";
                                } else if(stdRestNm.equals("용인휴게소(서창)")) {
                                    stdRestNm = "용인휴게소(인천)";
                                } else if(stdRestNm.equals("이서휴게소(천안)")) {
                                    stdRestNm = "이서휴게소(논산)";
                                } else if(stdRestNm.equals("이인휴게소(상)")) {
                                    stdRestNm = "이인휴게소(천안)";
                                } else if(stdRestNm.equals("장유휴게소(김해)")) {
                                    continue;
                                } else if(stdRestNm.equals("정안휴게소(상)")) {
                                    stdRestNm = "정안휴게소(천안)";
                                } else if(stdRestNm.equals("정안휴게소(하)")) {
                                    stdRestNm = "정안휴게소(순천)";
                                } else if(stdRestNm.equals("정읍휴게소(논산)")) {
                                    stdRestNm = "정읍녹두장군휴게소(논산)";
                                } else if(stdRestNm.equals("정읍휴게소(순천)")) {
                                    stdRestNm = "정읍녹두장군휴게소(순천)";
                                } else if(stdRestNm.equals("정읍휴게소(천안)")) {
                                    continue;
                                } else if(stdRestNm.equals("주암휴게소(논산)")) {
                                    continue;
                                } else if(stdRestNm.equals("지리산휴게소(고서)")) {
                                    continue;
                                } else if(stdRestNm.equals("지리산휴게소(옥포)")) {
                                    continue;
                                } else if(stdRestNm.equals("진안휴게소(익산)")) {
                                    stdRestNm = "진안마이산휴게소(익산)";
                                } else if(stdRestNm.equals("진안휴게소(장수)")) {
                                    continue;
                                } else if(stdRestNm.equals("진안휴게소(포항)")) {
                                    stdRestNm = "진안마이산휴게소(포항)";
                                } else if(stdRestNm.equals("천안휴게소(서울)")) {
                                    stdRestNm = "천안삼거리휴게소(서울)";
                                } else if(stdRestNm.equals("탄천휴게소(하)")) {
                                    stdRestNm = "탄천휴게소(광주)";
                                } else if(stdRestNm.equals("평창휴게소(서창)")) {
                                    stdRestNm = "평창휴게소(인천)";
                                } else if(stdRestNm.equals("함평나비휴게소(담양)")) {
                                    continue;
                                } else if(stdRestNm.equals("함평나비휴게소(대구)")) {
                                    continue;
                                } else if(stdRestNm.equals("함평휴게소(목포)")) {
                                    continue;
                                } else if(stdRestNm.equals("함평휴게소(서울)")) {
                                    continue;
                                } else if(stdRestNm.equals("화서휴게소(영덕)")) {
                                    stdRestNm = "화서휴게소(상주)";
                                } else if(stdRestNm.equals("황전휴게소(순천)")) {
                                    stdRestNm = "황전휴게소(광양)";
                                } else if(stdRestNm.equals("횡성휴게소(서창)")) {
                                    stdRestNm = "횡성휴게소(인천)";
                                }
                            } else {
                                // 값이 없다면 저장하지 않는다.
                                continue;
                            }
                            String psCode = temp.getString("psCode");
                            String psName = temp.getString("psName");
                            String psDesc = null;
                            if(temp.get("psDesc") != null) {
                                psDesc = "" + temp.get("psDesc");
                            }
                            String stime = temp.getString("stime");
                            String etime = temp.getString("etime");
                            String redDtime = temp.getString("redDtime");
                            String lsttmAltrDttm = temp.getString("lsttmAltrDttm");
                            
                            // 추출한 데이터를 휴게소 편의시설 Beans에 주입
                            ServiceareaPs serviceareaPs = new ServiceareaPs();
                            serviceareaPs.setStdRestCd(stdRestCd);
                            serviceareaPs.setStdRestNm(stdRestNm);
                            serviceareaPs.setPsCode(psCode);
                            serviceareaPs.setPsName(psName);
                            serviceareaPs.setPsDesc(psDesc.equals("null") ? null : psDesc);
                            serviceareaPs.setStime(stime);
                            serviceareaPs.setEtime(etime);
                            serviceareaPs.setRedDtime(redDtime);
                            serviceareaPs.setLsttmAltrDttm(lsttmAltrDttm);
                            
                            try {
                                // 저장하기 위한 Service를 호출
                                serviceareaPsService.insertServiceareaPs(serviceareaPs);
                            } catch (Exception e) {
                                return web.redirect(null, e.getLocalizedMessage());
                            }
                        } // End for
                    } // End if
                    
                    // 연동해온 json을 로그에 기록
                    logger.debug("휴게소 편의시설 API json >> " + json);
                } // End while
            } // End if
        } // End for
        
        /** (5) 저장이 완료되었으므로 휴게소 메인페이지로 이동 */
        return web.redirect(web.getRootPath() + "/servicearea/servicearea_index.do", null);
    } // End serviceareaPsApiInsert Method
    
    /**
     * 휴게소 테마 API 정보 추가를 위한 액션 컨트롤러
     */
    @RequestMapping(value = "/servicearea/servicearea_theme_api_insert.do", method = RequestMethod.GET)
    public ModelAndView serviceareaThemeApiInsert(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "servicearea_index.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
        }
        
        /** (3) 로그인 여부 검사 */
        // 관리자가 아니라면 이 페이지를 동작시켜서는 안된다.
        Member loginInfo = (Member) web.getSession("loginInfo");
        if(!loginInfo.getGrade().equals("Master")) {
            return web.redirect(web.getRootPath() + "/index.do", "관리자 계정만 접근 가능합니다.");
        }
        
        /** (4) 휴게소 API를 연동하기 + Service를 통한 휴게소 정보 저장 */
        String pageSize = "1";
        
        // 휴게소 테마 API 불안정으로 JSON을 얻지 못하기에 얻을 때까지 반복
        for(int i = 1; i <= Integer.parseInt(pageSize); i++) {
            String code = "ERROR";
            
            if(code.equals("ERROR")) {
                boolean bool = true;
                
                while(bool) {
                    String url = "http://data.ex.co.kr/openapi/restinfo/restThemeList?key=2017172116&type=json&numOfRows=10000&pageNo=" + i;
                    InputStream is = httpHelper.getWebData(url, "utf-8");
                    JSONObject json = jsonHelper.getJSONObject(is, "utf-8");
                    pageSize = "" + json.get("pageSize");
                    code = "" + json.get("code");
                    
                    if(code.equals("SUCCESS")) {
                        bool = false;
                        
                        // json 배열까지 접근한다.
                        JSONArray list = json.getJSONArray("list");
                        
                        // 배열 데이터이므로 반복문 안에서 처리해야 한다.
                        // 배열의 길이만큼 반복한다.
                        for(int j = 0; j < list.length(); j++) {
                            // 배열의 j번째 JSON을 꺼낸다.
                            JSONObject temp = list.getJSONObject(j);
                            
                            // 데이터를 추출
                            String stdRestCd = temp.getString("stdRestCd");
                            String stdRestNm = null;
                            if(temp.getString("stdRestNm") != null) {
                                stdRestNm = temp.getString("stdRestNm");
                                // "null"로 저장된 값이 있기에 제외시킨다.
                                if(stdRestNm.equals("null")) {
                                    continue;
                                } else if(stdRestNm.indexOf("주유소") > -1) {
                                    // 주유소로 저장되는 이름값을 휴게소로 변경
                                    stdRestNm = stdRestNm.replace("주유소", "휴게소");
                                }
                                // "민자" 값이 들어간 경우 제거하고 유지
                                if(stdRestNm.indexOf("(민자)") > -1) {
                                    stdRestNm = stdRestNm.replace("(민자)", "");
                                } else if(stdRestNm.indexOf("_민자") > -1) {
                                    stdRestNm = stdRestNm.replace("_민자", "");
                                } else if(stdRestNm.indexOf("-") > -1) {
                                    // "-" 값이 들어간 경우 제거
                                    continue;
                                }
                                // 괄호의 위치를 일관시키기 위해 뒤로 이동
                                int start = stdRestNm.indexOf("(");
                                int end = stdRestNm.indexOf(")");
                                int length = stdRestNm.length();
                                if(length > end && start > -1 && end > -1) {
                                    String direction = stdRestNm.substring(start, end + 1);
                                    String first = stdRestNm.substring(0, start);
                                    String last = stdRestNm.substring(end + 1);
                                    stdRestNm = first + last + direction;
                                }
                                // 값을 최신화 작업
                                if(stdRestNm.equals("강릉휴게소(강릉)")) {
                                    stdRestNm = "강릉대관령휴게소(강릉)";
                                } else if(stdRestNm.equals("")) {
                                    stdRestNm = "";
                                } else if(stdRestNm.equals("거창휴게소(고서)")) {
                                    stdRestNm = "거창한휴게소(광주)";
                                } else if(stdRestNm.equals("거창휴게소(옥포)")) {
                                    stdRestNm = "거창휴게소(대구)";
                                } else if(stdRestNm.equals("고창휴게소(목포)")) {
                                    stdRestNm = "고창고인돌휴게소(목포)";
                                } else if(stdRestNm.equals("고창휴게소(시흥)")) {
                                    stdRestNm = "고창고인돌휴게소(시흥)";
                                } else if(stdRestNm.equals("곡성휴게소(논산)")) {
                                    stdRestNm = "곡성기차마을휴게소(논산)";
                                } else if(stdRestNm.equals("곡성휴게소(순천)")) {
                                    stdRestNm = "곡성기차마을휴게소(순천)";
                                } else if(stdRestNm.equals("구리남양주휴게소(내측)")) {
                                    stdRestNm = "구리휴게소(일산)";
                                } else if(stdRestNm.equals("금강휴게소")) {
                                    stdRestNm = "금강휴게소(부산)";
                                } else if(stdRestNm.equals("남성주휴게소(김천)")) {
                                    continue;
                                } else if(stdRestNm.equals("남성주휴게소(현풍)")) {
                                    continue;
                                } else if(stdRestNm.equals("덕평휴게소")) {
                                    stdRestNm = "덕평휴게소(강릉)덕평휴게소(인천)";
                                } else if(stdRestNm.equals("문경휴게소(마산)")) {
                                    stdRestNm = "문경휴게소(창원)";
                                } else if(stdRestNm.equals("서울만남휴게소(부산)")) {
                                    stdRestNm = "서울만남의광장휴게소(부산)";
                                } else if(stdRestNm.equals("서하남휴게소(일산)")) {
                                    stdRestNm = "서하남휴게소(판교)";
                                } else if(stdRestNm.equals("성주휴게소(마산)")) {
                                    stdRestNm = "성주휴게소(창원)";
                                } else if(stdRestNm.equals("안성맞춤휴게소(음성)")) {
                                    stdRestNm = "안성맞춤휴게소(평택)";
                                } else if(stdRestNm.equals("양평휴게소(원주)")) {
                                    stdRestNm = "양평휴게소";
                                } else if(stdRestNm.equals("여산휴게소(논산)")) {
                                    stdRestNm = "여산휴게소(천안)";
                                } else if(stdRestNm.equals("여주휴게소(서창)")) {
                                    stdRestNm = "여주휴게소(인천)";
                                } else if(stdRestNm.equals("용인휴게소(서창)")) {
                                    stdRestNm = "용인휴게소(인천)";
                                } else if(stdRestNm.equals("이인휴게소(상)")) {
                                    stdRestNm = "이인휴게소(천안)";
                                } else if(stdRestNm.equals("정안휴게소(상)")) {
                                    stdRestNm = "정안휴게소(천안)";
                                } else if(stdRestNm.equals("정안휴게소(하)")) {
                                    stdRestNm = "정안휴게소(순천)";
                                } else if(stdRestNm.equals("정읍휴게소(논산)")) {
                                    stdRestNm = "정읍녹두장군휴게소(논산)";
                                } else if(stdRestNm.equals("정읍휴게소(순천)")) {
                                    stdRestNm = "정읍녹두장군휴게소(순천)";
                                } else if(stdRestNm.equals("정읍휴게소(천안)")) {
                                    continue;
                                } else if(stdRestNm.equals("주암휴게소(논산)")) {
                                    continue;
                                } else if(stdRestNm.equals("지리산휴게소(고서)")) {
                                    continue;
                                } else if(stdRestNm.equals("지리산휴게소(옥포)")) {
                                    continue;
                                } else if(stdRestNm.equals("진안휴게소(익산)")) {
                                    stdRestNm = "진안마이산휴게소(익산)";
                                } else if(stdRestNm.equals("진안휴게소(포항)")) {
                                    stdRestNm = "진안마이산휴게소(포항)";
                                } else if(stdRestNm.equals("진안휴게소(장수)")) {
                                    continue;
                                } else if(stdRestNm.equals("천안휴게소(서울)")) {
                                    stdRestNm = "천안삼거리휴게소(서울)";
                                } else if(stdRestNm.equals("평창휴게소(서창)")) {
                                    stdRestNm = "평창휴게소(인천)";
                                } else if(stdRestNm.equals("함평나비휴게소(담양)")) {
                                    continue;
                                } else if(stdRestNm.equals("함평나비휴게소(대구)")) {
                                    continue;
                                } else if(stdRestNm.equals("함평휴게소(목포)")) {
                                    continue;
                                } else if(stdRestNm.equals("함평휴게소(서울)")) {
                                    continue;
                                } else if(stdRestNm.equals("화서휴게소(영덕)")) {
                                    stdRestNm = "화서휴게소(상주)";
                                } else if(stdRestNm.equals("황전휴게소(순천)")) {
                                    stdRestNm = "황전휴게소(광양)";
                                } else if(stdRestNm.equals("횡성휴게소(서창)")) {
                                    stdRestNm = "횡성휴게소(인천)";
                                }
                            } else {
                                // 값이 없다면 저장하지 않는다.
                                continue;
                            }
                            String itemNm = temp.getString("itemNm");
                            String detail = temp.getString("detail");
                            String regDtime = temp.getString("regDtime");
                            String lsttmAltrDttm = temp.getString("lsttmAltrDttm");
                            
                            // 추출한 데이터를 휴게소 테마 Beans에 주입
                            ServiceareaTheme serviceareaTheme = new ServiceareaTheme();
                            serviceareaTheme.setStdRestCd(stdRestCd);
                            serviceareaTheme.setStdRestNm(stdRestNm);
                            serviceareaTheme.setItemNm(itemNm);
                            serviceareaTheme.setDetail(detail);
                            serviceareaTheme.setRegDtime(regDtime);
                            serviceareaTheme.setLsttmAltrDttm(lsttmAltrDttm);
                            
                            try {
                                // 저장하기 위한 Service를 호출
                                serviceareaThemeService.insertServiceareaTheme(serviceareaTheme);
                            } catch (Exception e) {
                                return web.redirect(null, e.getLocalizedMessage());
                            }
                        } // End for
                    } // End if
                    
                    // 연동해온 json을 로그에 기록
                    logger.debug("휴게소 테마 API json >> " + json);
                } // End while
            } // End if
        } // End for
        
        /** (5) 저장이 완료되었으므로 휴게소 메인페이지로 이동 */
        return web.redirect(web.getRootPath() + "/servicearea/servicearea_index.do", null);
    } // End serviceareaThemeApiInsert Method
    
    /**
     * 휴게소 위치 API 정보 추가를 위한 액션 컨트롤러
     */
    @RequestMapping(value = "/servicearea/servicearea_place_api_insert.do", method = RequestMethod.GET)
    public ModelAndView serviceareaPlaceApiInsert(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "servicearea_index.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
        }
        
        /** (3) 로그인 여부 검사 */
        // 관리자가 아니라면 이 페이지를 동작시켜서는 안된다.
        Member loginInfo = (Member) web.getSession("loginInfo");
        if(!loginInfo.getGrade().equals("Master")) {
            return web.redirect(web.getRootPath() + "/index.do", "관리자 계정만 접근 가능합니다.");
        }
        
        /** (4) 휴게소 API를 연동하기 + Service를 통한 휴게소 정보 저장 */
        List<Servicearea> list = null;
        try {
            list = serviceareaService.selectServiceareaList(null);
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        
        // 휴게소 정보를 한개씩 추출
        for(int i = 0; i < list.size(); i++) {
            Servicearea item = list.get(i);
            
            String query = item.getUnitName().trim().replace(" ", "");
            String queryOrigin = query;
            String queryX = null;
            String queryY = null;
            if(!item.getxValue().equals("null") && !item.getyValue().equals("null")) {
                queryX = item.getxValue();
                queryY = item.getyValue();
            } // End if
            
            Map<String, String> header = new HashMap<String, String>();
            header.put("Authorization", "KakaoAK 917780780abfbb0009a7f44cfb307f98");
            int j = 1;
            Boolean is_end = false;
            
            // 위치 검색을 위해 검색어 최적화
            int queryIdx = query.indexOf("(");
            if(queryIdx > -1) {
                query = query.substring(0, queryIdx) + query.substring(queryIdx + 1, queryIdx + 3);
            }
            if(query.indexOf("시흥하늘휴게소") > -1) {
                query = "시흥하늘휴게소브릿지스퀘어";
            }
            
            while(!is_end) {
                String url = "";
                if(queryX != null && queryY != null) {
                    url = "https://dapi.kakao.com/v2/local/search/keyword.json?page=" + j + "&query=" + query + "&x=" + queryX + "&y=" + queryY + "&radius=20000";
                } else {
                    url = "https://dapi.kakao.com/v2/local/search/keyword.json?page=" + j + "&query=" + query;
                }
                InputStream is = httpHelper.getWebData(url, "utf-8", header);
                JSONObject json = jsonHelper.getJSONObject(is, "utf-8");
                JSONObject meta = json.getJSONObject("meta");
                is_end = meta.getBoolean("is_end");
                
                // json 배열까지 접근한다.
                JSONArray documents = json.getJSONArray("documents");
                
                // 배열 데이터이므로 반복문 안에서 처리해야 한다.
                // 배열의 길이만큼 반복한다.
                for(int k = 0; k < documents.length(); k++) {
                    // 배열의 j번째 JSON을 꺼낸다.
                    JSONObject temp = documents.getJSONObject(k);
                    // 데이터를 추출   
                    String id = temp.getString("id");
                    String category_name = temp.getString("category_name");
                    String place_name = temp.getString("place_name");
                    if(place_name.equals("계룡관광휴게소")) {
                        // 저장하지 않아야 할 값
                        continue;
                    }
                    String phone = temp.getString("phone");
                    String address_name = temp.getString("address_name");
                    String road_address_name = temp.getString("road_address_name");
                    String place_url = temp.getString("place_url");
                    String distance = temp.getString("distance");
                    String x = temp.getString("x");
                    String y = temp.getString("y");
                    
                    // 추출한 데이터를 휴게소 위치 Beans에 주입
                    ServiceareaPlace serviceareaPlace = new ServiceareaPlace();
                    if(queryOrigin.indexOf("시흥하늘휴게소") > -1) {
                        serviceareaPlace.setQuery("시흥하늘휴게소(브릿지스퀘어)");
                    } else {
                        serviceareaPlace.setQuery(queryOrigin);
                    }
                    serviceareaPlace.setQueryX(queryX != null ? queryX : null);
                    serviceareaPlace.setQueryY(queryY != null ? queryY : null);
                    serviceareaPlace.setId(id);
                    serviceareaPlace.setCategory_name(category_name);
                    serviceareaPlace.setPlace_name(place_name);
                    serviceareaPlace.setPhone(phone);
                    serviceareaPlace.setAddress_name(address_name);
                    serviceareaPlace.setRoad_address_name(road_address_name);
                    serviceareaPlace.setPlace_url(place_url);
                    serviceareaPlace.setDistance(distance);
                    serviceareaPlace.setX(x);
                    serviceareaPlace.setY(y);
                    
                    // 휴게소 정보만 저장
                    int queryS = place_name.indexOf(query.substring(0, 2));
                    int queryLength = query.length();
                    String queryLast = query.substring(queryLength - 2);
                    int queryE = place_name.lastIndexOf(queryLast);
                    
                    if(queryS != queryE && queryS > -1 && queryE > -1) {
                        if(category_name.equals("교통,수송 > 휴게소 > 고속도로휴게소") || category_name.equals("교통,수송 > 휴게소")) {
                            try {
                                // 저장하기 위한 Service를 호출
                                serviceareaPlaceService.insertServiceareaPlace(serviceareaPlace);
                                
                                // x가 경도 lng도 경도
                                item.setxValue(serviceareaPlace.getX());
                                item.setyValue(serviceareaPlace.getY());
                                
                                // 새롭게 얻어온 위치 정보로 기본 휴게소 정보를 수정
                                serviceareaService.updateServiceareaByServiceareaCsOrServiceareaPlace(item);
                            } catch (Exception e) {
                                return web.redirect(null, e.getLocalizedMessage());
                            }
                            
                        } // End if
                        
                        // 휴게소 정보는 존재하나 휴게소로 조회된 값이 없을 경우 주유소로 대체하여 조회
                        if(category_name.indexOf("교통,수송 > 자동차 > 주유,가스 > 주유소") > -1 && (query.equals("구정휴게소속초") || query.equals("대천휴게소목포"))) {
                            try {
                                // 저장하기 위한 Service를 호출
                                serviceareaPlaceService.insertServiceareaPlace(serviceareaPlace);
                                
                                // x가 경도 lng도 경도
                                item.setxValue(serviceareaPlace.getX());
                                item.setyValue(serviceareaPlace.getY());
                                
                                // 새롭게 얻어온 위치 정보로 기본 휴게소 정보를 수정
                                serviceareaService.updateServiceareaByServiceareaCsOrServiceareaPlace(item);
                            } catch (Exception e) {
                                return web.redirect(null, e.getLocalizedMessage());
                            }
                        } // End if
                        
                        // 휴게소 정보는 존재하나 휴게소로 조회된 값이 없을 경우 주차장으로 대체하여 조회
                        if(category_name.equals("교통,수송 > 교통시설 > 주차장") && query.equals("관촌휴게소순천")) {
                            try {
                                // 저장하기 위한 Service를 호출
                                serviceareaPlaceService.insertServiceareaPlace(serviceareaPlace);
                                
                                // x가 경도 lng도 경도
                                item.setxValue(serviceareaPlace.getX());
                                item.setyValue(serviceareaPlace.getY());
                                
                                // 새롭게 얻어온 위치 정보로 기본 휴게소 정보를 수정
                                serviceareaService.updateServiceareaByServiceareaCsOrServiceareaPlace(item);
                            } catch (Exception e) {
                                return web.redirect(null, e.getLocalizedMessage());
                            }
                        } // End if
                    } // End if
                    
                } // End for
                
                j++;
                // 연동해온 json을 로그에 기록
                logger.debug("휴게소 위치 API json >> " + json);
            } // End while
        } // End for
                
        /** (5) 저장이 완료되었으므로 휴게소 메인페이지로 이동 */
        return web.redirect(web.getRootPath() + "/servicearea/servicearea_index.do", null);
    } // End serviceareaPlaceApiInsert Method
    
    /**
     * 휴게소 이미지 API 정보 추가를 위한 액션 컨트롤러
     */
    @RequestMapping(value = "/servicearea/servicearea_image_api_insert.do", method = RequestMethod.GET)
    public ModelAndView serviceareaImageApiInsert(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "servicearea_index.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
        }
        
        /** (3) 로그인 여부 검사 */
        // 관리자가 아니라면 이 페이지를 동작시켜서는 안된다.
        Member loginInfo = (Member) web.getSession("loginInfo");
        if(!loginInfo.getGrade().equals("Master")) {
            return web.redirect(web.getRootPath() + "/index.do", "관리자 계정만 접근 가능합니다.");
        }
        
        /** (4) 휴게소 API를 연동하기 + Service를 통한 휴게소 정보 저장 */
        List<ServiceareaFood> list = null;
        try {
            list = serviceareaFoodService.selectServiceareaFoodList(null);
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        
        // 휴게소 정보를 한개씩 추출
        for(int i = 0; i < list.size(); i++) {
            ServiceareaFood item = list.get(i);
            
            if(item.getBatchMenu() != null) {
                String query = item.getBatchMenu().trim().replace(" ", "");
                
                /** 같은 질의어 값이 저장되어 있는지 사전 조회한다. */
                ServiceareaImage serviceareaImageByQuery = new ServiceareaImage();
                serviceareaImageByQuery.setQuery(query);
                
                ServiceareaImage queryResult = null;
                try {
                    // 조회하기 위한 Service를 호출
                    queryResult = serviceareaImageService.selectServiceareaImageByQuery(serviceareaImageByQuery);
                } catch (Exception e) {
                    return web.redirect(null, e.getLocalizedMessage());
                }
                
                if(queryResult == null) {
                    /** Google Custom Image Search API */
                    // Google API는 하루 한도 100개로 제한되니 필요에 따라 사용한다.
                    String url = "";
                    if(i < 100) {
                        // MasterHong API Key
                        url = "https://www.googleapis.com/customsearch/v1?key=AIzaSyDLYHNwq4WPNyata4DZVJ5EiXub8Pr2XwI&cx=013708405310549574036:f3hr2q6zgvu&alt=json&searchType=image&num=1&q=" + query;
                    } else if(i >= 100 && i < 200) {
                        // MasterOh API Key
                        url = "https://www.googleapis.com/customsearch/v1?key=AIzaSyCBPEF7kzf9FhvQ-4MyAT8Iw0e4nbgEBog&cx=015813133634998525449:6aq1yp02vd8&alt=json&searchType=image&num=1&q=" + query;
                    } else {
                        // google API Key와 검색엔진 ID Key가 생길 때 위의 url 부분을 증가
                        continue;
                    }
                    InputStream is = httpHelper.getWebData(url, "utf-8");
                    JSONObject json = jsonHelper.getJSONObject(is, "utf-8");
                    JSONObject queries = json.getJSONObject("queries");
                    JSONArray requestBody = queries.getJSONArray("request");
                    int count = requestBody.getJSONObject(0).getInt("count");
                    
                    if(count == 0) {
                        System.out.println("검색 결과가 없습니다.");
                    } else {
                        // json 배열까지 접근한다.
                        JSONArray items = json.getJSONArray("items");
                        
                        // 배열 데이터이므로 반복문 안에서 처리해야 한다.
                        // 배열의 길이만큼 반복한다.
                        for(int j = 0; j < items.length(); j++) {
                            // 배열의 j번째 JSON을 꺼낸다.
                            JSONObject itemTemp = items.getJSONObject(j);
                            JSONObject temp = itemTemp.getJSONObject("image");
                            
                            // 데이터를 추출
                            String image_url = itemTemp.getString("link");
                            String thumbnail_url = temp.getString("thumbnailLink");
                            int width = temp.getInt("width");
                            int height = temp.getInt("height");
                            
                            // 추출한 데이터를 휴게소 이미지 Beans에 주입
                            ServiceareaImage serviceareaImage = new ServiceareaImage();
                            serviceareaImage.setQuery(query);
                            serviceareaImage.setImage_url(image_url);
                            serviceareaImage.setThumbnail_url(thumbnail_url);
                            serviceareaImage.setWidth(width);
                            serviceareaImage.setHeight(height);
                            
                            try {
                                // 저장하기 위한 Service를 호출
                                serviceareaImageService.insertServiceareaImage(serviceareaImage);
                            } catch (Exception e) {
                                return web.redirect(null, e.getLocalizedMessage());
                            }
                        } // End for
                        
                        // 연동해온 json을 로그에 기록
                        logger.debug("휴게소 이미지 Google API json >> " + json);
                    } // End if~else
                } // End if
                
            } // End if
            
        } // End for
        
        /** (5) 저장이 완료되었으므로 휴게소 메인페이지로 이동 */
        return web.redirect(web.getRootPath() + "/servicearea/servicearea_index.do", null);
    } // End serviceareaImageApiInsert Method
    
    /**
     * 휴게소 완성 정보 추가를 위한 액션 컨트롤러
     */
    @RequestMapping(value = "/servicearea/servicearea_group_insert.do", method = RequestMethod.GET)
    public ModelAndView serviceareaGroupInsert(Locale locale, Model model, 
            HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.
        
        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "servicearea_index.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
        }
        
        /** (3) 로그인 여부 검사 */
        // 관리자가 아니라면 이 페이지를 동작시켜서는 안된다.
        Member loginInfo = (Member) web.getSession("loginInfo");
        if(!loginInfo.getGrade().equals("Master")) {
            return web.redirect(web.getRootPath() + "/index.do", "관리자 계정만 접근 가능합니다.");
        }
        
        /** (4) 휴게소 API를 연동하기 + Service를 통한 휴게소 정보 저장 */
        List<Servicearea> list = null;
        try {
            list = serviceareaService.selectServiceareaList(null);
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        
        // 휴게소 정보를 한개씩 추출
        for(int i = 0; i < list.size(); i++) {
            Servicearea item = list.get(i);
            
            String serviceareaName = item.getUnitName();
            // 괄호의 위치를 일관시키기 위해 뒤로 이동
            int start = serviceareaName.indexOf("(");
            int end = serviceareaName.indexOf(")");
            String direction = null;
            if(start > -1 && end > -1) {
                direction = serviceareaName.substring(start + 1, end);
            }
            String routeName = item.getRouteName();
            String placeX = item.getxValue();
            String placeY = item.getyValue();
            
            /** (5) 휴게소이름으로 DB 조회 */
            // 조회하기 위한 Beans 구성
            ServiceareaCs serviceareaCs = new ServiceareaCs();
            if(serviceareaName.indexOf("마장프리미엄휴게소") > -1) {
                serviceareaCs.setStatNm("마장프리미엄휴게소");
            } else {
                serviceareaCs.setStatNm(serviceareaName);
            }
            ServiceareaFood serviceareaFood = new ServiceareaFood();
            serviceareaFood.setServiceAreaName(serviceareaName);
            ServiceareaImage serviceareaImage = new ServiceareaImage();
            ServiceareaOil serviceareaOil = new ServiceareaOil();
            serviceareaOil.setServiceAreaName(serviceareaName);
            ServiceareaPlace serviceareaPlace = new ServiceareaPlace();
            if(serviceareaName.indexOf("시흥하늘휴게소") > -1) {
                serviceareaPlace.setQuery("시흥하늘휴게소(브릿지스퀘어)");
            } else {
                serviceareaPlace.setQuery(serviceareaName);
            }
            ServiceareaPs serviceareaPs = new ServiceareaPs();
            serviceareaPs.setStdRestNm(serviceareaName);
            ServiceareaTheme serviceareaTheme = new ServiceareaTheme();
            serviceareaTheme.setStdRestNm(serviceareaName);
            
            // 조회된 결과값을 저장하기 위한 Beans
            ServiceareaCs csResult = null;
            ServiceareaFood foodResult = null;
            ServiceareaImage imageResult = null;
            ServiceareaOil oilResult = null;
            ServiceareaPlace placeResult = null;
            List<ServiceareaPs> psResult = null;
            ServiceareaTheme themeResult = null;
            
            try {
                // Service를 통한 조회
                csResult = serviceareaCsService.selectServiceareaCsByStatNm(serviceareaCs);
                foodResult = serviceareaFoodService.selectServiceareaFoodByServiceAreaName(serviceareaFood);
                if(foodResult != null) {
                    if(foodResult.getBatchMenu() != null) {
                        // 해당 휴게소에 대표메뉴가 있을 경우에만 이미지정보를 조회할 수 있다.
                        serviceareaImage.setQuery(foodResult.getBatchMenu().trim().replace(" ", ""));
                    }
                    imageResult = serviceareaImageService.selectServiceareaImageByQuery(serviceareaImage);
                }
                oilResult = serviceareaOilService.selectServiceareaOilByServiceAreaName(serviceareaOil);
                placeResult = serviceareaPlaceService.selectServiceareaPlaceByQuery(serviceareaPlace);
                psResult = serviceareaPsService.selectServiceareaPsListByStdRestNm(serviceareaPs);
                themeResult = serviceareaThemeService.selectServiceareaThemeByStdRestNm(serviceareaTheme);
            } catch (Exception e) {
                return web.redirect(null, e.getLocalizedMessage());
            }
            
            // 조회된 정보들을 하나로 취합하기 위해 Beans로 묶음
            ServiceareaGroup serviceareaGroup = new ServiceareaGroup();
            serviceareaGroup.setServiceareaName(serviceareaName);
            serviceareaGroup.setDirection(direction);
            serviceareaGroup.setRouteName(routeName);
            serviceareaGroup.setPlaceX(placeX);
            serviceareaGroup.setPlaceY(placeY);
            if(csResult != null) {
                serviceareaGroup.setCsStat(csResult.getStat());
            }
            if(foodResult != null) {
                serviceareaGroup.setFoodBatchMenu(foodResult.getBatchMenu());
                serviceareaGroup.setFoodSalePrice(foodResult.getSalePrice());
            }
            if(imageResult != null) {
                serviceareaGroup.setImageUrl(imageResult.getImage_url());
                serviceareaGroup.setImageThumbnailUrl(imageResult.getThumbnail_url());
                serviceareaGroup.setImageWidth(imageResult.getWidth());
                serviceareaGroup.setImageHeight(imageResult.getHeight());
            }
            if(oilResult != null) {
                serviceareaGroup.setOilCompany(oilResult.getOilCompany());
                serviceareaGroup.setOilGasolinePrice(oilResult.getGasolinePrice());
                serviceareaGroup.setOilDiselPrice(oilResult.getDiselPrice());
                serviceareaGroup.setOilLpgYn(oilResult.getLpgYn());
                serviceareaGroup.setOilLpgPrice(oilResult.getLpgPrice());
            }
            if(placeResult != null) {
                serviceareaGroup.setPlaceId(placeResult.getId());
                serviceareaGroup.setPlacePhone(placeResult.getPhone());
                serviceareaGroup.setPlaceAddress(placeResult.getAddress_name());
                serviceareaGroup.setPlaceRoadAddress(placeResult.getRoad_address_name());
                serviceareaGroup.setPlaceUrl(placeResult.getPlace_url());
            }
            if(psResult.size() > 0) {
                String psName = "";
                for(int j = 0; j < psResult.size(); j++) {
                    ServiceareaPs temp = psResult.get(j);
                    if(psName.indexOf(temp.getPsName()) == -1) {
                        if(psResult.size() > j + 1) {
                            psName += temp.getPsName() + ",";
                        } else {
                            psName += temp.getPsName();
                        }
                    }
                }
                serviceareaGroup.setPsName(psName);
            }
            if(themeResult != null) {
                serviceareaGroup.setThemeItemName(themeResult.getItemNm());
                serviceareaGroup.setThemeDetail(themeResult.getDetail());
            }
            
            try {
                // 취합된 정보의 Beans를 DB에 저장
                serviceareaGroupService.insertServiceareaGroup(serviceareaGroup);
            } catch (Exception e) {
                return web.redirect(null, e.getLocalizedMessage());
            }
            
        } // End for
        
        /** (6) 저장이 완료되었으므로 휴게소 메인페이지로 이동 */
        return web.redirect(web.getRootPath() + "/servicearea/servicearea_index.do", null);
    } // End serviceareaGroupInsert Method
    
}
