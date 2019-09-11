package project.spring.travel.controller.customer;

import java.util.List;
import java.util.Locale;

import javax.mail.MessagingException;
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
import project.spring.travel.model.BoardList;
import project.spring.travel.model.HotKeyword;
import project.spring.travel.model.Member;
import project.spring.travel.service.BoardService;
import project.spring.travel.service.HotKeywordService;

@Controller
public class CustomerMail {
	
	/** 사용하고자 하는 Helper + Service 객체 선언 */
    // Logger logger 는 BaseController에서 이미 객체 선언이 되어 있음으로 여기서는 또 선언할 필요가 없다.
    // -> import org.apache.ibatis.session.SqlSession;
	Logger logger = LoggerFactory.getLogger(CustomerMail.class);
	
	@Autowired
	SqlSession sqlSession;
    // -> import project.spring.helper.WebHelper
    @Autowired
	WebHelper web;
    
    @Autowired
    MailHelper mailHelper;
    
    @Autowired
    RegexHelper regex;
    
    @Autowired
	HotKeywordService hotKeywordService;
    
    @Autowired
    BoardService boardService;
	
	@RequestMapping(value="/MailForm.do", method=RequestMethod.GET)
	public ModelAndView MailForm(Locale locale, Model model,HttpServletRequest request, HttpServletResponse response) 
			 {
		
		/** (2) 사용하고자 하는 Helper+Service 객체 생성 */
		web.init();
		
		// 로그인 체크
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo == null) {
			return web.redirect(web.getRootPath() + "/member/member_login.do", "로그인이 필요한 서비스 입니다.");
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
		
		return new ModelAndView("customer/MailForm");
	}
	
	@RequestMapping(value="/customer/sendmail.do", method = RequestMethod.POST)
	public ModelAndView Sendmail(Locale locale, Model model,HttpServletRequest request, HttpServletResponse response) 
			 {
		/** (2) 사용하고자 하는 Helper+Service 객체 생성 */
		web.init();
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo == null) {
			
			return web.redirect(web.getRootPath() + "/customer.do", "문의 권한이 없습니다.");
			
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

		/** 사용자의 입력값 받기 */
		String sender = web.getString("sender"); // 실제로는 root-context에 설정된 이메일로 발송됨
		String receiver = web.getString("receiver");
		String subject = web.getString("subject");
		String content = web.getString("content");
		String category = web.getString("category");
		
		/** 입력여부 검사후, 입력되지 않은 경우 이전 페이지로 보내기 */
		// 보내는 메일 주소 검사하기
		if (!regex.isValue(sender)) {
			return web.redirect(null, "보내는 사람의 이메일 주소를 입력하세요.");
			
		}

		if (!regex.isEmail(sender)) {
			return web.redirect(null, "보내는 사람의 이메일 주소가 잘못되었습니다.");
			
		}

		// 받는 메일 주소 검사하기
		if (!regex.isValue(receiver)) {
			return web.redirect(null, "받는 사람의 이메일 주소를 입력하세요.");
			
		}

		if (!regex.isEmail(receiver)) {
			return web.redirect(null, "받는 사람의 이메일 주소가 잘못되었습니다.");
			
		}

		// 메일 제목 --> null체크도 입력 여부를 확인할 수 있다.
		if (subject == null) {
			return web.redirect(null, "메일 제목을 입력하세요.");
			
		}

		// 메일 내용 --> null체크도 입력 여부를 확인할 수 있다.
		if (content == null) {
			return web.redirect(null, "메일의 내용을 입력하세요.");
			
		}
		
		/* 카테고리 검사 */
		if (!regex.isValue(category)) {
			return web.redirect(null, "카테고리가 없습니다.");
			
		}
		
		BoardList params = new BoardList();
		params.setUserName(sender);
		params.setTitle(subject);
		params.setContent(content);
		params.setCategory(category);
		params.setKorCtg("문의하기");
		params.setMemberId(loginInfo.getMemberId());
		
		
		logger.debug("sender >> " + sender);
		logger.debug("receiver >> " + receiver);
		logger.debug("subject >> " + subject);
		logger.debug("content >> " + content);
		logger.debug("category >> " + category);
		logger.debug("memberId >> " + loginInfo.getMemberId());
		
		/** 메일 발송 처리 */
		try {
			// sendMail() 메서드 선언시 throws를 정의했기 때문에 예외처리가 요구된다.
			mailHelper.sendMail(sender, receiver, subject, content);
			boardService.addItem(params);
		} catch (MessagingException e) {
			e.printStackTrace();
			return web.redirect(null, "메일 발송에 실패했습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}

		/** 결과처리 */
		return web.redirect(web.getRootPath() + "/customer.do", "메일 발송에 성공했습니다.");
	}
}
