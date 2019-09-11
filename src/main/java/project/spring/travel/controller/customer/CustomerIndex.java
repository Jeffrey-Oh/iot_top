package project.spring.travel.controller.customer;

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

import project.spring.helper.MailHelper;
import project.spring.helper.RegexHelper;
import project.spring.helper.WebHelper;
import project.spring.travel.controller.community.BBSCommon;
import project.spring.travel.model.HotKeyword;
import project.spring.travel.service.BoardService;
import project.spring.travel.service.HotKeywordService;


@Controller
public class CustomerIndex {

	/** (1) 사용하고자 하는 Helper 객체 선언 */
	// --> import study.spring.helper.WebHelper;
	Logger logger = LoggerFactory.getLogger(CustomerIndex.class);
	@Autowired
	WebHelper web;
	@Autowired
	BBSCommon bbs;
	@Autowired
	RegexHelper regex;
	@Autowired
	SqlSession sqlSession;
	@Autowired
	BoardService boardService;
	@Autowired
	MailHelper mailHelper;
	// -> import project.spring.travel.service.HotKeywordService;
	@Autowired
	HotKeywordService hotKeywordService;

	@RequestMapping(value="/customer.do", method=RequestMethod.GET)
	public ModelAndView Customer(Locale locale, Model model,HttpServletRequest request, HttpServletResponse response) 
			 {
		
		/** (2) 사용하고자 하는 Helper+Service 객체 생성 */
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
		
		return new ModelAndView("customer/custermer");
	}
	
}
