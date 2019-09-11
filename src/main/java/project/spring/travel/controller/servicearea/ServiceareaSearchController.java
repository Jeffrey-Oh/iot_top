package project.spring.travel.controller.servicearea;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import project.spring.helper.PageHelper;
import project.spring.helper.RegexHelper;
import project.spring.helper.WebHelper;
import project.spring.travel.model.HotKeyword;
import project.spring.travel.model.ServiceareaGroup;
import project.spring.travel.service.HotKeywordService;
import project.spring.travel.service.ServiceareaGroupService;

/**
 * @fileName    : ServiceareaSearchController.java
 * @author      : 홍준성
 * @description : 휴게소 검색 관련 컨트롤러 모음
 * @lastUpdate  : 2019. 5. 06.
 */
/** 컨트롤러 선언 */
@Controller
public class ServiceareaSearchController {
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
    // -> import project.spring.helper.PageHelper;
    @Autowired
    PageHelper pageHelper;
    // -> import project.java.travel.service.ServiceareaGroupService;
    @Autowired
    ServiceareaGroupService serviceareaGroupService;
    // -> import project.spring.travel.service.HotKeywordService;
    @Autowired
    HotKeywordService hotKeywordService;

    /**
     * 휴게소 검색 View를 위한 컨트롤러
     */
    @RequestMapping(value = "/servicearea/servicearea_search.do", method = RequestMethod.GET)
    public ModelAndView serviceareaSearch(Locale locale, Model model,
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
         * ===== 검색어 파라미터 전달받기 =====
         * - 사용자가 입력한 검색어는 최종적으로
         *     SQL의 Where 조건에 사용되어야 한다.
         * - SQL을 수행하는 Mapper에게 검색어를 전달하기 위하여
         *     JavaBeans 객체에 사용자의 입력값을 저장한다.
         */
        /** (3) 검색어 파라미터 받기 + Beans 설정 */
        String search_input = web.getString("search_input", "");
        // 로그에 기록
        logger.debug("search_input = " + search_input);

        /** 인기검색어에 등록 시작  */
        try {
            if(search_input != null && !search_input.trim().replace(" ", "").equals("")) {
                HotKeyword hotKeywordInsert = new HotKeyword(); // 검색어를 Beans에 묶는다.
                hotKeywordInsert.setKeyword(search_input);
                hotKeywordService.insertHotKeyword(hotKeywordInsert); // 검색어를 저장
            }
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        /** 인기검색어에 등록 끝 */

        // View의 검색영역에 사용하기 위해 등록
        model.addAttribute("search_input", search_input);
        ServiceareaGroup serviceareaGroup = new ServiceareaGroup();
        serviceareaGroup.setServiceareaName(search_input);

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
            totalCount = serviceareaGroupService.selectServiceareaGroupCount(serviceareaGroup);
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }

        // 페이지 번호에 대한 연산 수행 후, 조회조건값 지정을 위한 Beans에 추가하기
        pageHelper.pageProcess(nowPage, totalCount, 10, 5);
        serviceareaGroup.setLimitStart(pageHelper.getLimitStart());
        serviceareaGroup.setListCount(pageHelper.getListCount());
        // View의 페이지 구현에 사용하기 위해 등록
        model.addAttribute("pageHelper", pageHelper);

        // 휴게소 조회 결과를 저장하기 위한 객체
        List<ServiceareaGroup> groupList = null;

        try {
            /**
             * ===== 검색어를 저장하고 있는 Beans를 Service 객체에게 주입하기 =====
             * - GET 파라미터로 전달받은 검색어를 JavaBeans에 담은 상태로
             *     Service 객체에 전달한다.
             * - 이렇게 전달된 객체는 MyBatis의 Mapper에게 전달될 것이다.
             */
            groupList = serviceareaGroupService.selectServiceareaGroupList(serviceareaGroup);
        } catch (Exception e) {
            // 예외 발생 시, Service Layer에서 전달하는 메시지 내용을
            // 사용자에게 제시하고 이전페이지로 이동한다.
            return web.redirect(null, e.getLocalizedMessage());
        }

        // View에서 사용하기 위해 등록
        model.addAttribute("groupList", groupList);

        return new ModelAndView("servicearea/servicearea_search");
    } // End serviceareaSearch Method

    /**
     * 휴게소 노선별 안내 View를 위한 컨트롤러
     */
    @RequestMapping(value = "/servicearea/servicearea_line.do", method = RequestMethod.GET)
    public ModelAndView serviceareaLine(Locale locale, Model model,
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
         * ===== 검색어 파라미터 전달받기 =====
         * - 사용자가 입력한 검색어는 최종적으로
         *     SQL의 Where 조건에 사용되어야 한다.
         * - SQL을 수행하는 Mapper에게 검색어를 전달하기 위하여
         *     JavaBeans 객체에 사용자의 입력값을 저장한다.
         */
        /** (3) 검색어 파라미터 받기 + Beans 설정 */
        String lineName = web.getString("lineName", "");
        // 로그에 기록
        logger.debug("lineName = " + lineName);

        /** 인기검색어에 등록 시작  */
        try {
            if(lineName != null && !lineName.trim().replace(" ", "").equals("")) {
                HotKeyword hotKeywordInsert = new HotKeyword(); // 검색어를 Beans에 묶는다.
                hotKeywordInsert.setKeyword(lineName);
                hotKeywordService.insertHotKeyword(hotKeywordInsert); // 검색어를 저장
            }
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        /** 인기검색어에 등록 끝 */

        // View의 검색영역에 사용하기 위해 등록
        model.addAttribute("lineName", lineName);
        ServiceareaGroup serviceareaGroup = new ServiceareaGroup();
        serviceareaGroup.setRouteName(lineName);

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
            totalCount = serviceareaGroupService.selectRouteNameCount(serviceareaGroup);
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }

        // 페이지 번호에 대한 연산 수행 후, 조회조건값 지정을 위한 Beans에 추가하기
        pageHelper.pageProcess(nowPage, totalCount, 12, 5);
        serviceareaGroup.setLimitStart(pageHelper.getLimitStart());
        serviceareaGroup.setListCount(pageHelper.getListCount());
        // View의 페이지 구현에 사용하기 위해 등록
        model.addAttribute("pageHelper", pageHelper);

        // 휴게소 조회 결과를 저장하기 위한 객체
        List<ServiceareaGroup> lineList = null;

        try {
            /**
             * ===== 검색어를 저장하고 있는 Beans를 Service 객체에게 주입하기 =====
             * - GET 파라미터로 전달받은 검색어를 JavaBeans에 담은 상태로
             *     Service 객체에 전달한다.
             * - 이렇게 전달된 객체는 MyBatis의 Mapper에게 전달될 것이다.
             */
            lineList = serviceareaGroupService.selectRouteNameList(serviceareaGroup);
        } catch (Exception e) {
            // 예외 발생 시, Service Layer에서 전달하는 메시지 내용을
            // 사용자에게 제시하고 이전페이지로 이동한다.
            return web.redirect(null, e.getLocalizedMessage());
        }

        // View에서 사용하기 위해 등록
        model.addAttribute("lineList", lineList);

        return new ModelAndView("servicearea/servicearea_line");
    } // End serviceareaLine Method

    /**
     * 휴게소 유가정보 View를 위한 컨트롤러
     */
    @RequestMapping(value = "/servicearea/servicearea_oil.do", method = RequestMethod.GET)
    public ModelAndView serviceareaOil(Locale locale, Model model,
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
         * ===== 검색어 파라미터 전달받기 =====
         * - 사용자가 입력한 검색어는 최종적으로
         *     SQL의 Where 조건에 사용되어야 한다.
         * - SQL을 수행하는 Mapper에게 검색어를 전달하기 위하여
         *     JavaBeans 객체에 사용자의 입력값을 저장한다.
         */
        /** (3) 검색어 파라미터 받기 + Beans 설정 */
        String lineName = web.getString("lineName", "");
        // 로그에 기록
        logger.debug("lineName = " + lineName);

        /** 인기검색어에 등록 시작  */
        try {
            if(lineName != null && !lineName.trim().replace(" ", "").equals("")) {
                HotKeyword hotKeywordInsert = new HotKeyword(); // 검색어를 Beans에 묶는다.
                hotKeywordInsert.setKeyword(lineName);
                hotKeywordService.insertHotKeyword(hotKeywordInsert); // 검색어를 저장
            }
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        /** 인기검색어에 등록 끝 */

        // View의 검색영역에 사용하기 위해 등록
        model.addAttribute("lineName", lineName);
        ServiceareaGroup serviceareaGroup = new ServiceareaGroup();
        serviceareaGroup.setRouteName(lineName);

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
            totalCount = serviceareaGroupService.selectRouteNameCount(serviceareaGroup);
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }

        // 페이지 번호에 대한 연산 수행 후, 조회조건값 지정을 위한 Beans에 추가하기
        pageHelper.pageProcess(nowPage, totalCount, 12, 5);
        serviceareaGroup.setLimitStart(pageHelper.getLimitStart());
        serviceareaGroup.setListCount(pageHelper.getListCount());
        // View의 페이지 구현에 사용하기 위해 등록
        model.addAttribute("pageHelper", pageHelper);

        // 휴게소 조회 결과를 저장하기 위한 객체
        List<ServiceareaGroup> lineList = null;

        try {
            /**
             * ===== 검색어를 저장하고 있는 Beans를 Service 객체에게 주입하기 =====
             * - GET 파라미터로 전달받은 검색어를 JavaBeans에 담은 상태로
             *     Service 객체에 전달한다.
             * - 이렇게 전달된 객체는 MyBatis의 Mapper에게 전달될 것이다.
             */
            lineList = serviceareaGroupService.selectRouteNameList(serviceareaGroup);
        } catch (Exception e) {
            // 예외 발생 시, Service Layer에서 전달하는 메시지 내용을
            // 사용자에게 제시하고 이전페이지로 이동한다.
            return web.redirect(null, e.getLocalizedMessage());
        }

        // View에서 사용하기 위해 등록
        model.addAttribute("lineList", lineList);

        return new ModelAndView("servicearea/servicearea_oil");
    } // End serviceareaOil Method

    /**
     * 휴게소 대표음식 View를 위한 컨트롤러
     */
    @RequestMapping(value = "/servicearea/servicearea_food.do", method = RequestMethod.GET)
    public ModelAndView serviceareaFood(Locale locale, Model model,
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
         * ===== 검색어 파라미터 전달받기 =====
         * - 사용자가 입력한 검색어는 최종적으로
         *     SQL의 Where 조건에 사용되어야 한다.
         * - SQL을 수행하는 Mapper에게 검색어를 전달하기 위하여
         *     JavaBeans 객체에 사용자의 입력값을 저장한다.
         */
        /** (3) 검색어 파라미터 받기 + Beans 설정 */
        String lineName = web.getString("lineName", "");
        // 로그에 기록
        logger.debug("lineName = " + lineName);

        /** 인기검색어에 등록 시작  */
        try {
            if(lineName != null && !lineName.trim().replace(" ", "").equals("")) {
                HotKeyword hotKeywordInsert = new HotKeyword(); // 검색어를 Beans에 묶는다.
                hotKeywordInsert.setKeyword(lineName);
                hotKeywordService.insertHotKeyword(hotKeywordInsert); // 검색어를 저장
            }
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        /** 인기검색어에 등록 끝 */

        // View의 검색영역에 사용하기 위해 등록
        model.addAttribute("lineName", lineName);
        ServiceareaGroup serviceareaGroup = new ServiceareaGroup();
        serviceareaGroup.setRouteName(lineName);

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
            totalCount = serviceareaGroupService.selectRouteNameCount(serviceareaGroup);
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }

        // 페이지 번호에 대한 연산 수행 후, 조회조건값 지정을 위한 Beans에 추가하기
        pageHelper.pageProcess(nowPage, totalCount, 12, 5);
        serviceareaGroup.setLimitStart(pageHelper.getLimitStart());
        serviceareaGroup.setListCount(pageHelper.getListCount());
        // View의 페이지 구현에 사용하기 위해 등록
        model.addAttribute("pageHelper", pageHelper);

        // 휴게소 조회 결과를 저장하기 위한 객체
        List<ServiceareaGroup> lineList = null;

        try {
            /**
             * ===== 검색어를 저장하고 있는 Beans를 Service 객체에게 주입하기 =====
             * - GET 파라미터로 전달받은 검색어를 JavaBeans에 담은 상태로
             *     Service 객체에 전달한다.
             * - 이렇게 전달된 객체는 MyBatis의 Mapper에게 전달될 것이다.
             */
            lineList = serviceareaGroupService.selectRouteNameList(serviceareaGroup);
        } catch (Exception e) {
            // 예외 발생 시, Service Layer에서 전달하는 메시지 내용을
            // 사용자에게 제시하고 이전페이지로 이동한다.
            return web.redirect(null, e.getLocalizedMessage());
        }

        // View에서 사용하기 위해 등록
        model.addAttribute("lineList", lineList);

        return new ModelAndView("servicearea/servicearea_food");
    } // End serviceareaFood Method

    /**
     * 테마휴게소 View를 위한 컨트롤러
     */
    @RequestMapping(value = "/servicearea/servicearea_theme.do", method = RequestMethod.GET)
    public ModelAndView serviceareaTheme(Locale locale, Model model,
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
         * ===== 검색어 파라미터 전달받기 =====
         * - 사용자가 입력한 검색어는 최종적으로
         *     SQL의 Where 조건에 사용되어야 한다.
         * - SQL을 수행하는 Mapper에게 검색어를 전달하기 위하여
         *     JavaBeans 객체에 사용자의 입력값을 저장한다.
         */
        /** (3) 검색어 파라미터 받기 + Beans 설정 */
        String lineName = web.getString("lineName", "");
        // 로그에 기록
        logger.debug("lineName = " + lineName);

        /** 인기검색어에 등록 시작  */
        try {
            if(lineName != null && !lineName.trim().replace(" ", "").equals("")) {
                HotKeyword hotKeywordInsert = new HotKeyword(); // 검색어를 Beans에 묶는다.
                hotKeywordInsert.setKeyword(lineName);
                hotKeywordService.insertHotKeyword(hotKeywordInsert); // 검색어를 저장
            }
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        /** 인기검색어에 등록 끝 */

        // View의 검색영역에 사용하기 위해 등록
        model.addAttribute("lineName", lineName);
        ServiceareaGroup serviceareaGroup = new ServiceareaGroup();
        serviceareaGroup.setRouteName(lineName);

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
            totalCount = serviceareaGroupService.selectRouteNameCount(serviceareaGroup);
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }

        // 페이지 번호에 대한 연산 수행 후, 조회조건값 지정을 위한 Beans에 추가하기
        pageHelper.pageProcess(nowPage, totalCount, 12, 5);
        serviceareaGroup.setLimitStart(pageHelper.getLimitStart());
        serviceareaGroup.setListCount(pageHelper.getListCount());
        // View의 페이지 구현에 사용하기 위해 등록
        model.addAttribute("pageHelper", pageHelper);

        // 휴게소 조회 결과를 저장하기 위한 객체
        List<ServiceareaGroup> lineList = null;

        try {
            /**
             * ===== 검색어를 저장하고 있는 Beans를 Service 객체에게 주입하기 =====
             * - GET 파라미터로 전달받은 검색어를 JavaBeans에 담은 상태로
             *     Service 객체에 전달한다.
             * - 이렇게 전달된 객체는 MyBatis의 Mapper에게 전달될 것이다.
             */
            lineList = serviceareaGroupService.selectRouteNameList(serviceareaGroup);
        } catch (Exception e) {
            // 예외 발생 시, Service Layer에서 전달하는 메시지 내용을
            // 사용자에게 제시하고 이전페이지로 이동한다.
            return web.redirect(null, e.getLocalizedMessage());
        }

        // View에서 사용하기 위해 등록
        model.addAttribute("lineList", lineList);

        return new ModelAndView("servicearea/servicearea_theme");
    } // End serviceareaTheme Method

    /**
     * 휴게소 경로탐색 View를 위한 컨트롤러
     */
    @RequestMapping(value = "/servicearea/servicearea_route.do", method = RequestMethod.GET)
    public ModelAndView serviceareaRoute(Locale locale, Model model,
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

        // 휴게소 조회 결과를 저장하기 위한 객체
        List<ServiceareaGroup> groupList = null;

        try {
            groupList = serviceareaGroupService.selectServiceareaGroupList(null);
        } catch (Exception e) {
            // 예외 발생 시, Service Layer에서 전달하는 메시지 내용을
            // 사용자에게 제시하고 이전페이지로 이동한다.
            return web.redirect(null, e.getLocalizedMessage());
        }

        // View에서 사용하기 위해 등록
        model.addAttribute("groupList", groupList);
        // routeResult에서 사용하기 위해 cookie에 저장
        web.setSession("groupList", groupList);

        return new ModelAndView("servicearea/servicearea_route");
    } // End serviceareaRoute Method

}
