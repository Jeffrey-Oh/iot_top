package project.spring.travel.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @fileName    : ErrorPage.java
 * @author      : 홍준성
 * @description : 잘못된 접근으로 접속할 시 에러 View를 처리하기 위한 컨트롤러
 * @lastUpdate  : 2019. 4. 18.
 */
/** 컨트롤러 선언 */
@Controller
public class ErrorPage {

    @RequestMapping(value = "/error/error_page.do")
	public ModelAndView doRun(Locale locale, Model model, 
	        HttpServletRequest request, HttpServletResponse response) {
        
		return new ModelAndView("error/ErrorPage");
	}

}
