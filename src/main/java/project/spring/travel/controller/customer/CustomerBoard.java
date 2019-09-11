package project.spring.travel.controller.customer;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import project.spring.helper.UploadHelper;
import project.spring.helper.WebHelper;
import project.spring.travel.controller.community.BBSCommon;
import project.spring.travel.model.BoardList;
import project.spring.travel.model.HotKeyword;
import project.spring.travel.model.Member;
import project.spring.travel.service.BoardService;
import project.spring.travel.service.CategoryLikeService;
import project.spring.travel.service.FavoriteService;
import project.spring.travel.service.FileService;
import project.spring.travel.service.HotKeywordService;
import project.spring.travel.service.ReplyService;

// 컨트롤 / 부분 주석!
/*
Servlet implementation class CommunityIndex
 */
@Controller
public class CustomerBoard {

	Logger logger = LoggerFactory.getLogger(CustomerBoard.class);

	@Autowired
	SqlSession sqlSession;

	@Autowired
	WebHelper web;

	@Autowired
	UploadHelper upload;

	@Autowired
	RegexHelper regex;

	@Autowired
	BoardService boardService;

	@Autowired
	FileService fileService;

	@Autowired
	CategoryLikeService categoryLikeService;

	@Autowired
	ReplyService replyService;

	@Autowired
	FavoriteService favoriteService;

	@Autowired
	BBSCommon bbsCommon;

	@Autowired
	PageHelper pageHelper;
	
	// -> import project.spring.travel.service.HotKeywordService;
	@Autowired
	HotKeywordService hotKeywordService;

	@RequestMapping(value = "/customer/CustomerBoardList.do", method=RequestMethod.GET)
	public ModelAndView CustomerboardList(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		web.init();
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo == null) {
			return web.redirect(web.getRootPath() + "/member/member_login.do", "로그인이 필요한 서비스 입니다.");
		}
		
		int memberId = 0; // 비 로그인 시 0
		memberId = loginInfo.getMemberId();
		
		if (memberId == 0) {
			return web.redirect(null, "로그인이 필요한 서비스 입니다.");
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

		int nowPage = web.getInt("list", 1);  //현재 페이지
		logger.debug("list = " + nowPage);
		model.addAttribute("nowPage", nowPage);

		BoardList params = new BoardList();//  SQL 파라미터로 넘길 BoardList 객체 생성
		params.setMemberId(memberId);
		
		int totalCount = 0;

		try {
			totalCount = boardService.getCountCenter(params);  //게시물 전체 개수

		} catch (Exception e) {
			web.redirect(null, e.getLocalizedMessage());
			return null;
		}

		model.addAttribute("totalCount", totalCount);


		pageHelper.pageProcess(nowPage, totalCount, 10, 5);
		params.setLimitStart(pageHelper.getLimitStart());
		params.setListCount(pageHelper.getListCount());

		model.addAttribute("pageHelper", pageHelper);

		List<BoardList> list = null;  //조회된 게시물들을 담을 List<BoardList> //객체

		try {
			list = boardService.getBoardListCenter(params);
		} catch (Exception e) {
			web.redirect(null, e.getLocalizedMessage());
			return null;
		}

		model.addAttribute("getItemAll", list);

		Date date = new Date();
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
		String year = (String) simpleDate.format(date);
		model.addAttribute("year", year);

		return new ModelAndView("customer/CustomerBoardList");
	}


	@RequestMapping(value = "/customer/CustomerBoardView.do", method = RequestMethod.GET)
	public ModelAndView CustomerBoardView(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		
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
		
		int loginChk = 0;  //비 로그인시 0
		
		model.addAttribute("loginChk", loginChk);
		
		int boardId = Integer.parseInt(web.getString("boardId")); // 게시물 일련번호 구분을 위한 파라미터
		logger.debug("boardId = " + boardId);
		model.addAttribute("boardId", boardId);
		
		if (boardId == 0) {
			return web.redirect(null, "글 번호가 지정되지 않았습니다.");
		}
		
		int nowPage = web.getInt("list", 1);  //현재 페이지
		logger.debug("list = " + nowPage);
		model.addAttribute("nowPage", nowPage);

		BoardList obj = new BoardList();  //선택된 게시물을 조회하기 위한 BoardList 객체 생성
		obj.setBoardId(boardId); // boardId 파라미터 값을 객체에 boardId에 저장
		
		BoardList item = null;
		
		try {
			
			item = boardService.getItem(obj);  //조회된 게시물 내용을 obj에 담는다
			
			if (item.getTitle().indexOf("<script>") > -1) {
				item.setTitle(web.getString(null, item.getTitle(), true));
			} else if (item.getContent().indexOf("<script>") > -1) {
				item.setContent(web.getString(null, item.getContent(), true));
			}
			
			model.addAttribute("item", item);
			
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		String curUrl = request.getRequestURL() + "?" + request.getQueryString();
		logger.debug("curUrl = " + curUrl);
		model.addAttribute("curUrl", curUrl);
		
		return new ModelAndView("customer/CustomerBoardView");
	}
	
}




