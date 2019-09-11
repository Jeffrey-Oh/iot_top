package project.spring.helper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractView;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhncorp.lucy.security.xss.XssPreventer;

/**
 * @fileName : WebHelper.java
 * @author : 홍준성
 * @description : 파라미터를 수신하고, 기본적인 값의 존재 여부 검사나 형변환 기능을 제공하기 위한 클래스
 * @lastUpdate : 2019-04-15
 */
/**
 * # GET, POST 파라미터를 처리하기 위한 Helper 클래스 작성
 *
 * ## 모든 형태의 GET, POST 파라미터값을 취득하기 위해
 *     공통적으로 처리해야 하는 사항
 * - request.setCharacterEncoding("utf-8")을 최초 1회 호출한다.
 * - request.getParameter("이름")을 통해 전달받은 파라미터의 값이 존재하는지 판별한다.
 *     - Null 여부에 대해 판별한다.
 *     - Null이 아니라면 값이 존재하는지를(공백이 아닌지를) 판별한다.
 *         이 때 불필요한 공백을 제거하는 처리를 추가할 수 있다.
 *     - 파라미터가 Null이거나 공백이라면
 *         프로그램의 정상적인 동작을 위해 다른 값으로 대체해야 한다.
 * - request.getParameter("이름")으로 전달받은 파라미터를
 *     숫자 형식으로 상요해야 한다면,
 *     Wrapper 클래스를 상요해 적절한 형 변환을 수행한다.
 *
 * ## 파라미터 처리를 모듈화 하기 위한 WebHelper 클래스
 * - 앞서 살펴본 처리 과정을 내부적으로 처리하는 클래스를 작성하여
 *     파라미터에 대한 처리과정을 모듈화 하면
 *     파라미터의 처리 과정이 더욱 효율적으로 개선된다.
 * - 기능 요구사항
 *     *
 *     리턴형 : String
 *     메서드 : getString
 *     파라미터 : String filedName
 *             String defaultValue
 *     설명 : filedName값을 사용하여 request.getParameter(fieldName)형식으로 파라미터를 받아 리턴한다.
 *          값이 Null이거나 공백 문자열일 경우 defaultValue를 대신 리턴한다.
 *     *
 *     리턴형 : int
 *     메서드 : getInt
 *     파라미터 : String fieldName
 *             int defaultValue
 *     설명 : 내부적으로 getString() 메서드를 호출하여 파라미터를 받은 후 int형태로 형변환을 시도한다.
 *          형변환에 실패한 경우 defaultValue를 대신 리턴한다.
 */
public class WebHelper {
    /**
     * ==== 쿠키 저장에 활용될 도메인 설정하기 =====
     * - 이 값은 실제 상용화 시 사이트에 맞게 변경해야 한다.
     *     - ex) 사이트 도메인이 itpaper.co.kr인 경우
     *         - ".itpaper.co.kr" (도메인 앞에 "." 주의) 라고 지정하면
     *             모든 서브 도메인간에 쿠키값이 공유된다.
     */
    /** 쿠키에서 사용할 도메인 */
    private static final String DOMAIN = "localhost";

    /**
     * ===== JSP 내장객체와 초기화 메서드 선언하기 =====
     * - HTTP GET/POST 파라미터를 수신하는 기능을 제공하는 request 객체는
     *     오직 JSP 페이지에서만 생성되기 때문에
     *     일반 Java 클래스에서 사용하기 위해서는
     *     JSP 페이지로부터 전달받아야 한다.
     *     - request : JSP에서 전달받은 객체를 참조시키기 위한 객체 선언
     *     - init : JSP에서 전달받은 객체를 request 객체에 참조시키기 위한 메서드
     */
    /** JSP의 request 내장 객체 */
    // -> import javax.servlet.http.HttpServletRequest;
    private HttpServletRequest request;

    /** ===== 전달받는 내장객체를 참조하기 위한 객체 선언 ===== */
    /** JSP의 response 내장 객체 */
    // -> import javax.servlet.http.HttpServletResponse;
    private HttpServletResponse response;

    /** JSP의 out 내장 객체 */
    // -> import java.io.PrintWriter;
    private PrintWriter out;

    /** ===== WebHelper에서 Session 객체 사용하기 ===== */
    /** JSP의 session 내장 객체 */
    // -> import javax.servlet.http.HttpSession;
    private HttpSession session;

    /**
     * ===== init() 메서드에 내장객체 전달하기 =====
     * - JSP 페이지에서 WebHelper 객체를 생성하기 위해서는
     *     WebHelper 객체의 init() 메서드에게 내장객체를 전달해야만 한다.
     * - 이 과정을 일괄적으로 처리하기 위해
     *     내장객체를 전달하고,
     *     이 안에서 init()를 호출하도록 구성한다.
     *
     * ===== 메시지 표시와 페이지 이동 처리를 위한 WebHelper 기능 개선 =====
     * - HTML 출력 기능을 갖기 위해서는 out 객체가 필요하다.
     * - out객체는 response객체를 통해 추출할 수 있기 때문에
     *     WebHelper에게 response 내장객체를 전달한다.
     */
    /**
     * WebHelper 기능을 초기화 한다.
     * Spring이 제공하는 ServletRequestAttributes 객체를 통해서 request, response 객체를
     *     직접 생성할 수 있다.
     *
     * ===== response 내장 객체 참조 처리 밑 out객체 생성하기 =====
     * =====JSP 내장객체인 request, response 객체를 WebHelper 스스로 생성하도록
     *     개선하기 위해파라미터 삭제 =====
     */
    public void init() {
        /**
         * ===== WebHelper에서 JSP 내장객체를 스스로 생성하기 =====
         * - JSP 내장객체를 담고 있는 Spring의 객체를 통해서 내장객체 획득하기
         */
        ServletRequestAttributes requestAttr =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        // request와 response 객체를 추출한다.
        this.request = requestAttr.getRequest();
        this.response = requestAttr.getResponse();

        /** ===== WebHelper의 초기화 과정에서 세션 객체 생성하기 ===== */
        // 세션객체 생성하기
        this.session = request.getSession();

        // 페이지 이동 없이 세션이 유지되는 시간 설정 (초)
        // -> 24시간
        this.session.setMaxInactiveInterval(60 * 60 * 24);

        /** 내장객체 초기화 -> ust-8 설정 */
        try {
            // 인코딩 설정하기
            this.request.setCharacterEncoding("utf-8");
            this.response.setCharacterEncoding("utf-8");
            // out객체 생성하기
            this.out = response.getWriter();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // End init Method

    /** ===== 파라미터 수신을 위한 getString() 메서드 구현하기 ===== */
    /**
     * 파라미터를 전달받아서 리턴한다.
     * @param filedName - 파라미터 이름
     * @param defaultValue - 값이 없을 경우 사용될 기본값
     * @return String
     */
    public String getString(String filedName, String defaultValue) {
        // 리턴을 위한 값을 두 번째 파라미터(기본값)로 설정해 둔다.
        String result = defaultValue;
        // GET, POST 파라미터를 받는다.
        String param = this.request.getParameter(filedName);

        // 값이 null이 아니라면?
        if (param != null) {
            // 앞뒤 공백을 제거한다.
            param = param.trim();
            // 공백제거 결과가 빈 문자열이 아니라면?
            if(!param.equals("")) {
                // 리턴을 위해서 준비한 변수에 수신한 값을 복사한다.
                result = param;
            }
        }

        // 값을 리턴. param값이 존재하지 않을 경우 미리 준비한 기본값이 그대로 리턴된다.
        return result;
    } // End getString Method

    /**
     * naver/lucy-xss-filter 메서드
     * - UploadHelper를 사용한(multipart 상황)인 경우에는 filendName과 defaultValue를 반대로 적용하면 된다.
     * @param fieldName - 필터링할 내용
     * @param defaultValue - 기본값
     * @param xss - 사용 기준점
     * @return String - 필터링된 내용
     */
    public String getString(String fieldName, String defaultValue, boolean xss) {
        return XssPreventer.escape(this.getString(fieldName, defaultValue));
    } // End getString XssPreventer Method

    /** ===== 수신한 파라미터를 숫자형으로 변환하여 리턴하는 메서드 ===== */
    /**
     * 파라미터를 전달받아서 int로 형변환 하여 리턴한다.
     * @param fieldName - 파라미터 이름
     * @param defaultValue - 값이 없을 경우 상요될 기본값
     * @return int
     */
    public int getInt(String fieldName, int defaultValue) {
        // 리턴을 위한 값을 두 번째 파라미터(기본값)로 설정해 둔다.
        int result = defaultValue;
        // getString()메서드를 통해서 파라미터를 문자열 형태로 받는다.
        // 파라미터가 존재하지 않는다면 두 번째로 전달한 값이 리턴된다.
        String param = this.getString(fieldName, null);

        // 파라미터로 전달된 값을 숫자로 형변환 한다.
        try {
            result = Integer.parseInt(param);
        } catch (NumberFormatException e) {
            // 형변환에 실패한 경우 catch블록으로 제어가 이동하고, result값은 미리 설정해 둔
            // defaultValue인 상태를 유지한다.
            //e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    } // End getInt Method

    /**
     * ===== 체크박스 처리를 위한 WebHelper 클래스 개선 =====
     * # 추가할 기능들
     *      *
     *      리턴형 : String[]
     *      메서드 : getStirng
     *             Array
     *      파라미터 : String fieldName
     *              String defaultValue
     *      설명 : fieldName 값을 사용하여
     *           request.getParameterValues(fieldName)로 파라미터를 받아 배열을 리턴한다.
     *           값이 Null이거나 배열의 길이가 0인 경우 defaultValue를 대신 리턴한다.
     *           체크박스와 같이 다중 선택이 가능한 요소의 값을 수신할 때 사용한다.
     */
    /** ===== 체크박스와 같이 다중선택이 가능한 요소를 처리하기 위한 메서드 추가하기 ===== */
    /**
     * 배열 형태의 파리미터를 리턴한다. 체크박스 전용 기능
     * @param fieldName - 파라미터 이름
     * @param defaultValue - 값이 없거나 배열의 길이가 0인 경우 사용될 기본값
     * @return String[]
     */
    public String[] getStringArray(String fieldName, String[] defaultValue) {
        // 리턴을 위한 값을 두 번째 파라미터(기본값)로 설정해 둔다.
        String[] result = defaultValue;
        // 배열 형태의 GET, POST 파라미터를 받는다.
        String[] param = this.request.getParameterValues(fieldName);

        // 수신된 파라미터가 존재한다면?
        if(param != null) {
            // 배열의 길이가 0보다 크다면?
            if(param.length > 0) {
                // 리턴을 위해서 준비한 변수에 수신한 값을 복사한다.
                result = param;
            }
        }

        // 값을 리턴. param 값이 존재하지 않을 경우 미리 준비한 기본값이 그대로 리턴된다.
        return result;
    } // End getStringArray Method

    /** ===== 메시지 표시와 페이지 강제 이동 기능을 수행하기 위한 메서드 ===== */
    /**
     * ===== redirect 메서드의 개선 =====
     * - Spring에서는 Controller에서 out.print() 기능을 사용할 경우
     *     브라우저에게 데이터가 전달되지 않는다.
     * - 브라우저에게 전달되는 모든 데이터는
     *     ModelAndView 라는 가상의 View 객체로 구성되어야 하기 때문에,
     *     페이지 이동을 위한 기능의 리턴형을 개선한다.
     */
    /**
     * 메시지 표시 후, 페이지를 지정된 곳으로 이동한다.
     * @param url - 이동할 페이지의 URL. Null일 경우 이전페이지로 이동
     * @param msg - 화면에 표시할 메시지. Null일 경우 표시 안함
     */
    // -> import org.springframework.web.servlet.ModelAndView;
    public ModelAndView redirect(String url, String msg) {
        // 가상의 View로 만들기 위한 HTML 태그 구성
        String html = "<!doctype html>";
        html += "<html>";
        html += "<head>";
        html += "<meta charset='utf-8'>";

        // 메시지 표시
        if(msg != null) {
            html += "<script type='text/javascript'>alert('" + msg + "');</script>";
        }

        // 페이지 이동
        if(url != null) {
            html += "<meta http-equiv='refresh' content='0; url=" + url + "' />";
        } else {
            html += "<script type='text/javascript'>history.back();</script>";
        }

        html += "</head>";
        html += "<body></body>";
        html += "</html>";

        // 구성된 HTML을 출력한다.
        // out.print(html);

        /**
         * ===== 출력문 대신 ModelAndView의 객체 생성 후 리턴 =====
         */
        // 익명클래스 방식은 상수만 인식할 수 있으므로, HTML태그를 상수에 복사
        final String page_content = html;

        /** 가상의 View를 익명클래스 방식으로 생성하여 리턴 */
        // -> import org.springframework.web.servlet.View;
        // -> import org.springframework.web.servlet.view.AbstractView;
        View view = new AbstractView() {

            @Override
            protected void renderMergedOutputModel(
                    Map<String, Object> model, HttpServletRequest request,
                    HttpServletResponse response
                    ) throws Exception {
                out.println(page_content);
                out.flush();
            }
        };

        // 가상의 뷰를 리턴한다.
        return new ModelAndView(view);

        /**
         * ===== reDirect() 메서드를 사용할 수 있는 4가지 경우 =====
         * # 메시지 표시 후, 특정 페이지로 이동하기
         *     * web.redirect("이동할 URL", "메시지 내용");
         * # 페이지 표시 없이 특정 페이지로 이동하기
         *     * nweb.redirect("이동할 URL", null);
         * # 메시지 표시 후, 이전 페이지로 이동하기
         *     * web.redirect(null, "메시지 내용");
         * # 메시지 표시 없이 이전 페이지로 이동하기
         *     * web.redirect(null, null);
         */
    } // End redirect Method

    /**
     * ===== 파라미터 수신 처리를 간략하게 활용하기 위한 메서드 재정의 =====
     * - 필수 값으로 사용하게 될 두 번째 파라미터를 생략할 경우
     *     메서드 내부에서 null, 혹은 0으로 강제 설정할 수 있도록
     *     메서드를 오버로드 처리 하면
     *     필요한 기본값만 설정할 수 있게 된다.
     */
    /** getString()의 기본값을 null로 처리하도록 메서드 재정의 */
    public String getString(String fieldName) {
        return this.getString(fieldName, null);
    } // End getString Method Overload

    /** getInt()의 기본값을 0으로 처리하도록 메서드 재정의 */
    public int getInt(String fieldName) {
        return this.getInt(fieldName, 0);
    } // End getInt Method Overload

    /** getStringArray()의 기본값을 null로 처리하도록 메서드 재정의 */
    public String[] getStringArray(String fieldName) {
        return this.getStringArray(fieldName, null);
    } // End getStringArray Method Overload
    /**
     * ===== 파라미터를 받기 위한 2가지 경우 =====
     * # 값이 존재하지 않을 경우 대체할 기본값이 필요없는 경우
     * - 값이 없다면 null이 리턴된다.
     *     * String foo = web.getString("파라미터 이름");
     * # 값이 존재하지 않을 경우 대체할 기본값이 필요한 경우
     * - 예를 들어 "직업"을 의미하는 값이 없다면
     *     강제로 "프로그래머"로 설정할 수 있다.
     *     * String gender = web.getString("직업 파라미터 이름", "프로그래머");
     * - 또 다른 예로 "메일링"에 대한 입력값이 없다면
     *     강제로 "수신"으로 처리하는 것도 가능하다.
     *     * String mailling = web.getString("메일링 가입 여부", "수신");
     */

    /**
     * ===== 쿠키값 저장 기능을 위한 메서드 정의 =====
     * - 도메인이나 디렉토리 경로 정보는 메서드 안에서 고정값으로 설정하고,
     *     그 밖의 정보들은 파라미터에 의해서 설정할 수 있도록 할 것이다.
     */
    /**
     * 쿠키값을 저장한다.
     * @param key - 쿠키이름
     * @param value - 값
     * @param timeout - 설정시간. 브라우저를 닫으면 즉시 삭제할 경우 -1
     */
    public void setCookie(String key, String value, int timeout) {
        /**
         * ===== timeout 값의 판별 =====
         * - "-1"인 경우: if문을 수행하지 않기 때문에
         *     setMaxAge()는 호출되지 않는다.
         *     -> 브라우저를 닫으면 쿠키 삭제
         * - "0"인 경우: setMaxAge(0)으로 호출된다.
         *     -> 쿠키값 즉시 삭제
         * - "1이상"인 경우: 지정된 시간만큼
         *     유지된 후에 쿠키 삭제
         */
        /** 전달된 값을 URLEncoding 처리 한다. */
        if(value != null) {
            try {
                //import java.net.URLEncoder
                value = URLEncoder.encode(value, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } // End try~catch
        } // End if

        /** 쿠키 객체 생성 및 기본 설정 */
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");        

        // cookie.setDomain(DOMAIN);


        /** 유효시간 설정 */
        // 시간값이 0보다 작은 경우는 이 메서드를 설정하지 않도록 한다. (브라우저를 닫으면 삭제)
        // 0으로 설정할 경우 setMaxAge(0)이라고 설정되므로 즉시 삭제된다.
        if(timeout > -1) {
            cookie.setMaxAge(timeout);
        }

        /** 쿠키 저장하기 */
        this.response.addCookie(cookie);
    } // End setCookie Method

    /** ===== 저장된 쿠키값을 읽어서 리턴하는 메서드 정의 ===== */
    /**
     * 쿠키값을 조회한다.
     * @param key - 쿠키이름
     * @param defaultValue - 값이 없을 경우 사용될 기본값
     * @return String
     */
    public String getCookie(String key, String defaultValue) {
        /** 리턴할 값을 설정 */
        String result = defaultValue;

        /** ===== 쿠키값 읽기 ===== */
        /** 쿠키 배열 가져오기 */
        // import javax.servlet.http.Cookie
        Cookie[] cookies = this.request.getCookies();

        /** 쿠키가 있다면? 추출된 배열의 항목 수 만큼 반복하면서 원하는 이름의 값을 검색 */
        if(cookies != null) {
            for(int i = 0; i < cookies.length; i++) {
                // 쿠키의 이름 얻기
                String cookieName = cookies[i].getName();
                // 원하는 이름이라면?
                if(cookieName.equals(key)) {
                    // 값을 추출 -> 이 값이 리턴된다.
                    result = cookies[i].getValue();
                    try {
                        // -> import java.net.URLDecoder
                        result = URLDecoder.decode(result, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } // End try~catch
                    /**
                     * ### 원하는 값을 찾았다면 배열의 나머지 항목들은
                     *     검사할 필요가 없으므로 반복을 중단한다.
                     */
                    break;
                } // End if
            } // End for
        } // End if

        return result;
    } // End getCookie Method

    /**
     * ===== 쿠키값 읽기 기능에 대한 메서드 오버로드 + 쿠키 삭제 기능 =====
     * - 기본값을 설정하지 않을 경우 값이 없다면 null이 리턴될 수 있도록 기능을
     *     재정의 한다.
     */
    /**
     * 쿠키값을 조회한다. 값이 없을 경우 null을 리턴한다.
     * @param key - 쿠키이름
     * @return String
     */
    public String getCookie(String key) {
        return this.getCookie(key, null);
    } // End getCookie Method Overload

    /**
     * 지정된 키에 대한 쿠키를 삭제한다.
     * @param key - 쿠키이름
     */
    public void removeCookie(String key) {
        this.setCookie(key, null, 0);
    } // End removeCookie Method Overload

    /** ===== 세션 데이터 저장 + 저장된 데이터 추출 기능 구현 ===== */
    /**
     * 세션값을 저장한다.
     * @param key - 세션이름
     * @param value - 저장할 데이터
     */
    public void setSession(String key, Object value) {
        this.session.setAttribute(key, value);
    } // End setSession Method

    /**
     * 세션값을 조회한다.
     * @param key - 조회할 세션의 이름
     * @param defaultValue - 값이 없을 경우 대체할 기본값
     * @return Object 이므로 명시적 형변환 필요함
     */
    public Object getSession(String key, Object defaultValue) {
        Object value = this.session.getAttribute(key);

        if(value == null) {
            value = defaultValue;
        }

        return value;
    } // End getSession Method

    /** ===== 데이터 추출 기능의 재정의 및 세션 삭제 기능 구현 ===== */
    /**
     * 세션값을 조회한다.
     * 값이 없을 경우에 대한 기본값을 null로 설정
     * @param key - 세션 이름
     * @return Object 이므로 명시적 형변환 필요함
     */
    public Object getSession(String key) {
        return this.getSession(key, null);
    } // End getSession Method Overload

    /**
     * 특정 세션값을 삭제한다.
     * @param key - 세션 이름
     */
    public void removeSession(String key) {
        this.session.removeAttribute(key);
    } // End removeSession Method

    /**
     * 현재 사용자에 대한 모든 세션값을 일괄 삭제한다.
     */
    public void removeAllSession() {
        this.session.invalidate();
    } // End removeAllSession Method

    /**
     * ===== ContextPath를 리턴하기 위한 메서드 추가 =====
     * - 서블릿 클래스에서의 활용을 위해
     *     WebHelper안에 내장된 request 객체를 사용하여
     *     ContextPath값을 리턴한다.
     */
    public String getRootPath() {
        return this.request.getContextPath();
    } // End getRootPath Method

    /**
     * ===== 접속자의 아이피 주소를 획득하기 위한 기능 추가 =====
     * - 게시글이 저장될 때 작성자의 아이피 주소를 함께 저장하기 위하여
     *     WebHelper를 개선한다.
     *     - 원래 JSP에서 사용자의 IP주소를 획득하는 방법은 request.getRemoteAddr()이지만,
     *         다양한 네트워크 환경에 대응하기 위해 상황에 따라 HTTP Header를 참조하였다.
     */
    /**
     * 현재 접속자에 대한 IP주소를 조회하여 리턴한다.
     * @return String
     */
    public String getClientIP() {
        String ip = request.getHeader("X-FORWARDED-FOR");
        if(ip == null || ip.length() == 0) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0) {
            ip = request.getRemoteAddr();
        }
        return ip;
    } // End getClientIP Method

    /**
     * ===== 결과 메시지를 표현하기 위한 기능 추가 =====
     * - JSON 데이터를 표현하는 컨트롤러는 웹 브라우저가 직접 호출하는 형태가 아닌,
     *     Ajax나 Moblie App등에 의해 간접적으로 호출되는 형태로 실행되기 때문에
     *     web.redirect() 메서드의 페이지 이동 기능을 처리하지 못한다.
     * - 예외가 발생한 경우
     *     자신을 호출한 위치에 처리 결과를 전달하는 것으로
     *     역할 수행을 완료했다고 볼 수 있으므로,
     *     결과 메시지를 JSON으로 출력하는 기능을 구성한다.
     */
    /**
     * 결과 메시지를 JSON으로 출력한다.
     * JSON Api에서 web.redirect() 기능을 대체할 용도.
     * @param rt - JSON에 포함할 메시지 내용
     */
    public void printJsonRt(String rt) {
        Map<String, String> data = new HashMap<String, String>();
        data.put("rt", rt);

        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(response.getWriter(), data);
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // End printJsonRt Method

    /**
     * ===== HTML 태그에 대한 특수문자 처리 기능 정의 =====
     * - JSON 형식의 데이터에는 HTML 태그와 줄바꿈 문자를 표현할 수 없기 때문에
     *     모든 HTMl 태그를 특수문자 형태로 변경해야 한다.
     *     - <html> -> &lt;html&gt;
     *     - 줄바꿈 문자 -> &lt;br/&gt;
     */
    /**
     * 문자열에 포함된 HTMl 태그와 줄바꿈 문자를 HTMl특수문자 형태로 변환
     * @param content
     * @return String
     */
    public String convertHtmlTag(String content) {
        // 변경 결과를 저장할 객체
        StringBuilder builder = new StringBuilder();

        /**
         * ===== HTML 특수문자 처리 기능 구현 =====
         * - 문자열을 한 글자씩 추출하여
         *     특수문자 형식인 경우 변환한다.
         * - 그렇지 않은 경우
         *     원본을 StringBuilder에 추가 한다.
         * - 최종적으로
         *     builder의 내용을 문자열로 변환하여 리턴한다.
         */
        // 문자열에 포함된 한 글자
        char chrBuff;

        // 글자 수 만큼 반복한다.
        for(int i = 0; i < content.length(); i++) {
            // 한 글자를 추출
            chrBuff = (char) content.charAt(i);

            // 특수문자 형태에 부합할 경우 변환하여 builder에 추가
            // 그렇지 않을 경우 원본 그대로 builder에 추가
            switch(chrBuff) {
                case '<':
                    builder.append("&lt;");
                    break;
                case '>':
                    builder.append("&gt;");
                    break;
                case '&':
                    builder.append("&amp;");
                    break;
                case '\n':
                    builder.append("&lt;br/&gt;");
                    break;
                case ' ':
                    builder.append("&nbsp;");
                    break;
                case '"':
                    builder.append("&quot;");
                    break;
                case '?':
                    builder.append("&reg;");
                    break;
                default:
                    builder.append(chrBuff);
            }
        }

        // 조립된 결과를 문자열로 변환해서 리턴한다.
        return builder.toString();
    } // End convertHtmlTag Method

}
