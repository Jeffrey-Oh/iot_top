package project.spring.travel.test.controller.servicearea;
//package project.spring.travel.controller.servicearea;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.ibatis.session.SqlSession;
//import org.apache.logging.log4j.LogManager;
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//import project.java.helper.HttpHelper;
//import project.java.helper.JsonHelper;
//import project.java.travel.dao.MyBatisConnectionFactory;
//import project.java.travel.model.Member;
//import project.java.travel.model.Servicearea;
//import project.java.travel.service.ServiceareaService;
//import project.java.travel.service.impl.ServiceareaServiceImpl;
//import project.spring.helper.BaseController;
//import project.spring.helper.RegexHelper;
//import project.spring.helper.WebHelper;
//
///**
// * @fileName    : ServiceareaApiInsert.java
// * @author      : 홍준성
// * @description : 휴게소 API 정보 추가를 위한 액션 컨트롤러
// * @lastUpdate  : 2019. 4. 16.
// */
//@WebServlet("/servicearea/servicearea_api_insert.do")
//public class ServiceareaApiInsert extends BaseController {
//    private static final long serialVersionUID = 6730690602663297006L;
//    
//    /**
//     * ===== 관리자 외에는 접근할 수 없는 페이지 =====
//     * - 정책안내 관리 페이지는 관리계정 상태가 아니라면 접근할 수 없도록 처리해야 하기 때문에
//     *     세션이 존재한다면 (null이 아니라면) 관리자 계정인지 확인 한 후 접근할 수 있도록 제어한다.
//     */
//    /** (1) 사용하고자 하는 Helper + Service 객체 선언 */
//    // Logger logger 는 BaseController에서 이미 객체 선언이 되어 있음으로 여기서는 또 선언할 필요가 없다.
//    // -> import org.apache.ibatis.session.SqlSession;
//    SqlSession sqlSession;
//    // -> import project.spring.helper.WebHelper
//    WebHelper web;
//    // -> import project.spring.helper.RegexHelper
//    RegexHelper regex;
//    // -> import project.java.travel.service.ServiceareaService;
//    ServiceareaService serviceareaService;
//
//    @Override
//    public String doRun(HttpServletRequest request, HttpServletResponse response) 
//            throws ServletException, IOException {
//        /** (2) 사용하고자 하는 Helper + Service 객체 생성 */
//        // -> import org.apache.logging.log4j.LogManager;
//        logger = LogManager.getFormatterLogger(request.getRequestURI());
//        // -> import study.jsp.myschool.dao.MyBatisConnectionFactory;
//        sqlSession = MyBatisConnectionFactory.getSqlSession();
//        web = WebHelper.getInstance(request, response);
//        regex = RegexHelper.getInstance();
//        // 휴게소 API 저장 처리를 위한 Service객체
//        // -> import project.java.travel.service.impl.ServiceareaServiceImpl;
//        serviceareaService = new ServiceareaServiceImpl(sqlSession, logger);
//        
//        /** ===== 악의적인 접근에 대한 방어 코드 ===== */
//        String referer = request.getHeader("referer");
//        if(referer == null) {
//            // 정상적인 접근 방법이 아닐 경우
//            sqlSession.close();
//            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
//            web.redirect(web.getRootPath() + "/error/error_page.do", null);
//            return null;
//        } else {
//            if(regex.isIndexCheck(referer, "servicearea_index.do")) {
//                // 거쳐야할 절차를 건너뛴 경우
//                sqlSession.close();
//                logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
//                web.redirect(web.getRootPath() + "/error/error_page.do", null);
//                return null;
//            }
//        }
//        
//        /** (3) 로그인 여부 검사 */
//        // 관리자가 아니라면 이 페이지를 동작시켜서는 안된다.
//        Member loginInfo = (Member) web.getSession("loginInfo");
//        if(!loginInfo.getGrade().equals("Master")) {
//            sqlSession.close();
//            web.redirect(web.getRootPath() + "/index.do", "관리자 계정만 접근 가능합니다.");
//            return null;
//        }
//        
//        /** (4) 휴게소 API를 연동하기 + Service를 통한 휴게소 정보 저장 */
//        String now_page = "1"; // 첫 페이지부터 시작하기 위한 변수
//        String url = "http://data.ex.co.kr/openapi/locationinfo/locationinfoRest?key=2017172116&type=json&numOfRows=10000&pageNo=" + now_page; // 연동할 API
//        InputStream is = HttpHelper.getInstance().getWebData(url, "utf-8");
//        JSONObject json = JsonHelper.getInstance().getJSONObject(is, "utf-8");
//        String pageSize = "" + json.get("pageSize");
//        String code = "" + json.get("code");
//        
//        boolean bool = true;
//        // API 불안정으로 JSON을 얻지 못했을 경우
//        if(code.equals("ERROR")) {
//            while(bool) {
//                url = "http://data.ex.co.kr/openapi/locationinfo/locationinfoRest?key=2017172116&type=json&numOfRows=10000&pageNo=" + now_page;
//                is = HttpHelper.getInstance().getWebData(url, "utf-8");
//                json = JsonHelper.getInstance().getJSONObject(is, "utf-8");
//                pageSize = "" + json.get("pageSize");
//                code = "" + json.get("code");
//                
//                if(code.equals("SUCCESS")) {
//                    bool = false;
//                }
//            }
//        }
//        // 연동해온 json을 로그에 기록
//        logger.debug("json 1 >> " + json);
//        
//        if(json != null) {
//            // json 배열까지 접근한다.
//            JSONArray list = json.getJSONArray("list");
//            
//            // 배열 데이터이므로 반복문 안에서 처리해야 한다.
//            // 배열의 길이만큼 반복한다.
//            for(int j = 0; j < list.length(); j++) {
//                // 배열의 j번째 JSON을 꺼낸다.
//                JSONObject temp = list.getJSONObject(j);
//                
//                // 데이터를 추출
//                String unitName = temp.getString("unitName");
//                String unitCode = temp.getString("unitCode");
//                String routeNo = temp.getString("routeNo");
//                String routeName = temp.getString("routeName");
//                String xValue = null;
//                if(temp.get("xValue") != null) {
//                    xValue = "" + temp.get("xValue");
//                }
//                String yValue = null;
//                if(temp.get("yValue") != null) {
//                    yValue = "" + temp.get("yValue");
//                }
//                
//                // 유효성 검사
//                if(!regex.isValue(unitName)) {
//                    sqlSession.close();
//                    web.redirect(null, "휴게소명이 없습니다.");
//                    return null;
//                }
//                if(!regex.isValue(unitCode)) {
//                    sqlSession.close();
//                    web.redirect(null, "휴게소코드가 없습니다.");
//                    return null;
//                }
//                if(!regex.isValue(routeNo)) {
//                    sqlSession.close();
//                    web.redirect(null, "노선코드가 없습니다.");
//                    return null;
//                }
//                if(!regex.isValue(routeName)) {
//                    sqlSession.close();
//                    web.redirect(null, "노선명이 없습니다.");
//                    return null;
//                }
//                
//                // 추출한 데이터를 휴게소 Beans에 주입
//                Servicearea servicearea = new Servicearea();
//                servicearea.setUnitName(unitName);
//                servicearea.setUnitCode(unitCode);
//                servicearea.setRouteNo(routeNo);
//                servicearea.setRouteName(routeName);
//                servicearea.setxValue(xValue);
//                servicearea.setyValue(yValue);
//                
//                try {
//                    // 저장하기 위한 Service를 호출
//                    serviceareaService.insertServicearea(servicearea);
//                } catch (Exception e) {
//                    sqlSession.close();
//                    web.redirect(null, e.getLocalizedMessage());
//                    return null;
//                }
//            } // End for
//        } // End if
//        
//        // 남은 API 데이터도 모두 JSON 획득을 위한 반복문
//        for(int i = Integer.parseInt(now_page) + 1; i <= Integer.parseInt(pageSize); i++) {
//            url = "http://data.ex.co.kr/openapi/locationinfo/locationinfoRest?key=2017172116&type=json&numOfRows=10000&pageNo=" + i;
//            is = HttpHelper.getInstance().getWebData(url, "utf-8");
//            json = JsonHelper.getInstance().getJSONObject(is, "utf-8");
//            code = "" + json.get("code");
//            
//            // API 불안정으로 JSON을 얻지 못했을 경우
//            if(code.equals("ERROR")) {
//                bool = true;
//                
//                while(bool) {
//                    url = "http://data.ex.co.kr/openapi/locationinfo/locationinfoRest?key=2017172116&type=json&numOfRows=10000&pageNo=" + i;
//                    is = HttpHelper.getInstance().getWebData(url, "utf-8");
//                    json = JsonHelper.getInstance().getJSONObject(is, "utf-8");
//                    code = "" + json.get("code");
//                    
//                    if(code.equals("SUCCESS")) {
//                        bool = false;
//                    }
//                }
//            }
//            // 연동해온 json을 로그에 기록
//            logger.debug("json " + i + " >> " + json);
//            
//            if(json != null) {
//                // json 배열까지 접근한다.
//                JSONArray list = json.getJSONArray("list");
//                
//                // 배열 데이터이므로 반복문 안에서 처리해야 한다.
//                // 배열의 길이만큼 반복한다.
//                for(int j = 0; j < list.length(); j++) {
//                    // 배열의 j번째 JSON을 꺼낸다.
//                    JSONObject temp = list.getJSONObject(j);
//                    
//                    // 데이터를 추출
//                    String unitName = temp.getString("unitName");
//                    String unitCode = temp.getString("unitCode");
//                    String routeNo = temp.getString("routeNo");
//                    String routeName = temp.getString("routeName");
//                    String xValue = null;
//                    if(temp.get("xValue") != null) {
//                        xValue = "" + temp.get("xValue");
//                    }
//                    String yValue = null;
//                    if(temp.get("yValue") != null) {
//                        yValue = "" + temp.get("yValue");
//                    }
//                    
//                    // 유효성 검사
//                    if(!regex.isValue(unitName)) {
//                        sqlSession.close();
//                        web.redirect(null, "휴게소명이 없습니다.");
//                        return null;
//                    }
//                    if(!regex.isValue(unitCode)) {
//                        sqlSession.close();
//                        web.redirect(null, "휴게소코드가 없습니다.");
//                        return null;
//                    }
//                    if(!regex.isValue(routeNo)) {
//                        sqlSession.close();
//                        web.redirect(null, "노선코드가 없습니다.");
//                        return null;
//                    }
//                    if(!regex.isValue(routeName)) {
//                        sqlSession.close();
//                        web.redirect(null, "노선명이 없습니다.");
//                        return null;
//                    }
//                    
//                    // 추출한 데이터를 휴게소 Beans에 주입
//                    Servicearea servicearea = new Servicearea();
//                    servicearea.setUnitName(unitName);
//                    servicearea.setUnitCode(unitCode);
//                    servicearea.setRouteNo(routeNo);
//                    servicearea.setRouteName(routeName);
//                    servicearea.setxValue(xValue);
//                    servicearea.setyValue(yValue);
//                    
//                    try {
//                        // 저장하기 위한 Service를 호출
//                        serviceareaService.insertServicearea(servicearea);
//                    } catch (Exception e) {
//                        sqlSession.close();
//                        web.redirect(null, e.getLocalizedMessage());
//                        return null;
//                    }
//                } // End for
//            } // End if
//        } // End for
//        
//        /** (5) 저장이 완료되었으므로 휴게소 메인페이지로 이동 */
//        web.redirect(web.getRootPath() + "/servicearea/servicearea_index.do", null);
//        
//        return null;
//    }
//
//}
