package project.spring.travel.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import project.spring.helper.HttpHelper;
import project.spring.helper.JsonHelper;
import project.spring.helper.WebHelper;

/**
 * @fileName    : Proxy.java
 * @author      : 홍준성
 * @description : 프록시 서버를 통해 Javascript에 원하는 값으로 통신한다.
 * @lastUpdate  : 2019. 4. 18.
 */
/** 컨트롤러 선언 */
@Controller
public class Proxy {
    
    /** (1) 사용하고자 하는 Helper + Service 객체 선언 */
    // -> import org.slf4j.Logger;
    // -> import org.slf4j.LoggerFactory;
    Logger logger = LoggerFactory.getLogger(Proxy.class); // Log4j 객체 직접 생성
    // -> import project.jsp.helper.WebHelper
    @Autowired
    WebHelper web;
    @Autowired
    JsonHelper jsonHelper;
    @Autowired
    HttpHelper httpHelper;

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/proxy.do")
	public String doRun(Locale locale, Model model, 
	        HttpServletRequest request, HttpServletResponse response) {
        /** (2) WebHelper 초기화 */
        web.init(); // Helper, Service 객체들의 할당은 자동화 되므로 삭제하고 WebHelper를 초기화 한다.

		String reqUrl = null;
		String targetParams = "";
		String contentType = "application/json";		
		String encType = "utf-8";

		try {
            request.setCharacterEncoding(encType);
        } catch (UnsupportedEncodingException e) {
            web.redirect(null, e.getLocalizedMessage());
            return null;
        }
		response.setCharacterEncoding(encType);
		response.setContentType(contentType);

		Map<String, String> header = new HashMap<String, String>();
        Enumeration<String> headerNames = request.getHeaderNames(); // 노란밑줄 처리 어떻게?

		if (headerNames != null) {
			String[] ignoreHeaders = {"host", "referer", "connection", "accept", "dnt", "x-requested-with", "accept-encoding", "accept-language", "cookie"};
			while (headerNames.hasMoreElements()) {
				String name = (String) headerNames.nextElement();
				String value = request.getHeader(name);

				if (Arrays.asList(ignoreHeaders).indexOf(name) < 0) {
					System.out.println("[HTTP HEADER] " + name + "=" + value);
					header.put(name, value);
				}
			}
		}
		
        Enumeration<String> params = request.getParameterNames(); // 노란밑줄 처리 어떻게?
		while (params.hasMoreElements()) {
			String name = (String) params.nextElement();
			if (name.equals("csurl")) {
				reqUrl = request.getParameter(name);
			} else {
				String tmpParam = request.getParameter(name);
				try {
                    targetParams += name + "=" + URLEncoder.encode(tmpParam, encType) + "&";
                } catch (UnsupportedEncodingException e) {
                    web.redirect(null, e.getLocalizedMessage());
                    return null;
                }
				
				System.out.println("[GET PARAMTER] " + name + "=" + tmpParam);
			}
		}
		
		System.out.println("[CSURL] " + reqUrl);
		
		if (targetParams.length() > 0) {
			reqUrl += "?" + targetParams;
		}
		

		System.out.println("[Request] " + reqUrl);
		
		InputStream is = httpHelper.getWebData(reqUrl, encType, header);
		JSONObject jsonObject = null;
		
		if (is != null) {
			jsonObject = jsonHelper.getJSONObject(is, encType);
			try {
                response.getWriter().print(jsonObject);
            } catch (IOException e) {
                web.redirect(null, e.getLocalizedMessage());
                return null;
            }
		} else {
			try {
                response.getWriter().print("{}");
            } catch (IOException e) {
                web.redirect(null, e.getLocalizedMessage());
                return null;
            }
		}
		
		return null;
	}
	
}
