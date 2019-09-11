package project.spring.travel.controller.community;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import project.spring.helper.FileInfo;
import project.spring.helper.PageHelper;
import project.spring.helper.RegexHelper;
import project.spring.helper.UploadHelper;
import project.spring.helper.WebHelper;
import project.spring.travel.model.BoardList;
import project.spring.travel.model.BoardReply;
import project.spring.travel.model.BoardReplyReply;
import project.spring.travel.model.CategoryLike;
import project.spring.travel.model.Favorite;
import project.spring.travel.model.File;
import project.spring.travel.model.HotKeyword;
import project.spring.travel.model.Member;
import project.spring.travel.service.BoardService;
import project.spring.travel.service.CategoryLikeService;
import project.spring.travel.service.FavoriteService;
import project.spring.travel.service.FileService;
import project.spring.travel.service.HotKeywordService;
import project.spring.travel.service.ReplyService;

/**
 * Servlet implementation class CommunityIndex
 */
@Controller
public class CommunityIndex {

	Logger logger = LoggerFactory.getLogger(CommunityIndex.class);

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

	@RequestMapping(value = "/CommIndex.do")
	public ModelAndView CommIndex(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		web.init();
		
		web.removeSession("cateogry");
		web.removeSession("korCtg");
		
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

		// 카테고리 세션 삭제
		web.removeSession("category");

		String category = web.getString("category"); // 현재 페이지의 카테고리 구분을 위한 파라미터
		logger.debug("category = " + category);
		model.addAttribute("category", category);

		// 국문 카테고리 값 저장하기위한 빈 문자열
		String korCtg = "";
		try {
			// bbsCommon에서 존재하는 게시판 카테고리인지 확인 후 결과값 담기
			korCtg = bbsCommon.getBbsName(category);
			logger.debug("korCtg = " + korCtg);
			model.addAttribute("korCtg", korCtg);
		} catch (Exception e) {
			web.redirect(null, e.getLocalizedMessage());
			return null;
		}

		String searchWord = web.getString("search-word"); // 검색어 저장
		logger.debug("searchWord = " + searchWord);
		model.addAttribute("searchWord", searchWord);

		/** 인기검색어에 등록 시작  */
		try {
		    if(searchWord != null && !searchWord.trim().replace(" ", "").equals("")) {
		        HotKeyword hotKeywordInsert = new HotKeyword(); // 검색어를 Beans에 묶는다.
		        hotKeywordInsert.setKeyword(searchWord);
		        hotKeywordService.insertHotKeyword(hotKeywordInsert); // 검색어를 저장
		    }
		} catch (Exception e) {
		    return web.redirect(null, e.getLocalizedMessage());
		}
		/** 인기검색어에 등록 끝 */

		String type = web.getString("type", "제목%2B내용"); // 검색 종류
		logger.debug("type = " + type);

		int nowPage = web.getInt("list", 1); // 현재 페이지
		logger.debug("list = " + nowPage);
		model.addAttribute("nowPage", nowPage);

		BoardList params = new BoardList(); // SQL 파라미터로 넘길 BoardList 객체 생성

		model.addAttribute("type", type);
		params.setKorCtg(korCtg); // 카테고리를 카테고리에 저장
		if (type.equals("제목%2B내용")){
			params.setTitle(searchWord); // 검색어를 제목에 저장
			params.setContent(searchWord); // 검색어를 내용에 저장
		} else if (type.equals("제목")) {
			params.setTitle(searchWord); // 검색어를 제목에 저장
		} else if (type.equals("작성자")) {
			params.setUserName(searchWord);
		}

		int totalCount = 0; // 게시물 전체 개수 (공지사항 포함)
		int totalCountAll = 0; // 사이드(전체 글 보기)메뉴에 표시할 게시물 전체 개수

		try {
			totalCount = boardService.getCount(params);
			totalCountAll = boardService.getCount(null);
		} catch (Exception e) {
			web.redirect(null, e.getLocalizedMessage());
			return null;
		}

		int countNotice = 0; // 고정된 공지사항 전체 개수
		int noticeCountAll = 0; // 공지사항 전체 개수

		try {
			countNotice = boardService.getBoardNoticeCount();
			noticeCountAll = boardService.getBoardNoticeCountAll();
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		totalCount -= noticeCountAll; // 공지사항을 제외한 게시물 전체 개수

		model.addAttribute("totalCount", totalCount);
		model.addAttribute("totalCountAll", totalCountAll);
		model.addAttribute("countNotice", countNotice);

		pageHelper.pageProcess(nowPage, totalCount, 10, 5); // 페이징 10개씩 5그룹
		params.setLimitStart(pageHelper.getLimitStart());
		params.setListCount(pageHelper.getListCount());

		model.addAttribute("pageHelper", pageHelper);

		List<BoardList> list = null; // 조회된 게시물들을 담을 객체
		List<BoardList> noticeList = null; // 조회된 공지사항들을 담을 객체

		try {
			list = boardService.getItemAll(params);
			noticeList = boardService.getBoardNotice();
		} catch (Exception e) {
			web.redirect(null, e.getLocalizedMessage());
			return null;
		}

		// xss 처리 (게시물)
		for (int i=0; i<list.size(); i++) {
			BoardList temp = list.get(i);

			// 공격 코드 있으면 추가...
			if (temp.getTitle().indexOf("<script>") > -1) {
				temp.setTitle(web.getString(null, temp.getTitle(), true));
			} else if (temp.getContent().indexOf("<script>") > -1) {
				temp.setContent(web.getString(null, temp.getContent(), true));
			}
		}

		// xss 처리 (공지사항)
		for (int i=0; i<noticeList.size(); i++) {
			BoardList temp = noticeList.get(i);

			// 공격 코드 있으면 추가...
			if (temp.getTitle().indexOf("<script>") > -1) {
				temp.setTitle(web.getString(null, temp.getTitle(), true));
			} else if (temp.getContent().indexOf("<script>") > -1) {
				temp.setContent(web.getString(null, temp.getContent(), true));
			}
		}

		model.addAttribute("list", list);
		model.addAttribute("noticeList", noticeList);

		// 현재시간을 계산하여 값을 내보냄
		// 기준 시간을 지나면 N 이라는 이미지 추가 됨 (새 글 표시)
		Date date = new Date();
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
		String year = (String) simpleDate.format(date);
		model.addAttribute("year", year);
		
		logger.warn("Index category >>>> " + web.getSession("category"));
		logger.warn("Index korCtg >>>> " + web.getSession("korCtg"));

		return new ModelAndView("community/CommunityIndex");
	}

	@RequestMapping(value = "/CommView.do", method = RequestMethod.GET)
	public ModelAndView CommView(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		web.init();
		
        /** 인기검색어 영역 시작 */
        try {
            List<HotKeyword> hotKeywordList = null; // 조회된 검색어 리스트를 담을 리스트
            hotKeywordList = hotKeywordService.selectHotKeywordList(); // 최근 1달 검색어 조회
            // xss 처리
            for (int i=0; i<hotKeywordList.size(); i++) {
                HotKeyword hotKeywordtemp = hotKeywordList.get(i);
                // 공격 코드를 막기 위한 처리
                hotKeywordtemp.setKeyword(web.convertHtmlTag(hotKeywordtemp.getKeyword()));
                if (hotKeywordtemp.getKeyword().indexOf("<script>") > -1) {
                    hotKeywordtemp.setKeyword(web.getString(null, hotKeywordtemp.getKeyword(), true));
                }
            }
            model.addAttribute("hotKeywordList", hotKeywordList); // View에서 사용하기 위해 등록
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        /** 인기검색어 영역 끝 */

		int loginChk = 0; // 비 로그인시 0

		int memberId = 0; // 비 로그인시 0

		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo != null) {
			loginChk = 1; // 비 로그인시 댓글 등록 창 CSS 회색배경으로 변경하기 위한 변수
			memberId = loginInfo.getMemberId(); // 로그인 되어있다면 멤버일련번호 저장
		}

		model.addAttribute("loginChk", loginChk);

		int boardId = Integer.parseInt(web.getString("boardId")); // 게시물 일련번호 구분을 위한 파라미터
		logger.debug("boardId = " + boardId);
		model.addAttribute("boardId", boardId);
		web.setSession("boardId", boardId);

		if (boardId == 0) {
			return web.redirect(null, "글 번호가 지정되지 않았습니다.");
		}

		String category = web.getString("category"); // 현재 페이지의 카테고리 구분을 위한 파라미터
		logger.debug("category = " + category);
		model.addAttribute("category", category);
		web.setSession("category", category);

		// 국문 카테고리 값 저장하기위한 빈 문자열
		String korCtg = "";
		try {
			// bbsCommon에서 존재하는 게시판 카테고리인지 확인 후 결과값 담기
			korCtg = bbsCommon.getBbsName(category);
			if (korCtg.equals("null")) {
				return web.redirect(null, "존재하지 않는 게시판 입니다.");
			}
			logger.debug("korCtg = " + korCtg);
			model.addAttribute("korCtg", korCtg);
			web.setSession("korCtg", korCtg);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		String searchWord = web.getString("search-word"); // 검색어 저장
		logger.debug("search-word = " + searchWord);
		model.addAttribute("searchWord", searchWord);

        /** 인기검색어에 등록 시작  */
        try {
            if(searchWord != null && !searchWord.trim().replace(" ", "").equals("")) {
                HotKeyword hotKeywordInsert = new HotKeyword(); // 검색어를 Beans에 묶는다.
                hotKeywordInsert.setKeyword(searchWord);
                hotKeywordService.insertHotKeyword(hotKeywordInsert); // 검색어를 저장
            }
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        /** 인기검색어에 등록 끝 */

		String type = web.getString("type", "제목%2B내용"); // 검색 종류 빠른 검색때문에 디폴트 제목+내용
		logger.debug("type = " + type);
		model.addAttribute("type", type);

		int nowPage = web.getInt("list", 1); // 현재 페이징 번호
		logger.debug("list = " + nowPage);
		model.addAttribute("nowPage", nowPage);

		BoardList obj = new BoardList(); // 선택된 게시물을 조회하기 위한 객체 생성
		obj.setBoardId(boardId); // boardId 파라미터 값을 객체에 boardId에 저장

		File file = new File(); // 첨부파일이 있는지 확인하기 위한 객체 생성
		file.setBoardId(boardId);

		BoardList item = null; // 조회된 게시물을 담기 위한 빈 객체 선언

		CategoryLike like = new CategoryLike(); // 게시물 좋아요 상태 유지를 위한 객체 선언 및 생성
		like.setBoardId(boardId);
		like.setMemberId(memberId);

		List<File> fileList = null; // 게시물에 등록된 첨부파일 목록 조회 결과값을 담기 위한 객체 생성

		/** 조회수 중복 갱신 방지 처리 */
		// 카테고리와 게시물 일련번호를 조합한 문자열을 생성
		// ex) document_notice_15
		String cookieKey = "board_" + category + "_" + boardId + memberId;
		// 준비한 문자열에 대응되는 쿠키값 조회
		String cookieVar = web.getCookie(cookieKey);
		try {
			// 쿠키값이 없다면 조회수 갱신
			if (cookieVar == null && memberId != 0) {
				boardService.updateBoard(obj);
				// 준비한 문자열에 대한 쿠키를 24시간동안 저장
				web.setCookie(cookieKey, "Y", 60*60*24);
			}

			item = boardService.getItem(obj); // 조회된 게시물 내용을 obj에 담는다

			// xss 처리
			// 추가 소스가 있다면 추가
			if (item.getTitle().indexOf("<script>") > -1) {
				item.setTitle(web.getString(null, item.getTitle(), true));
			} else if (item.getContent().indexOf("<script>") > -1) {
				item.setContent(web.getString(null, item.getContent(), true));
			}

			// 첨부파일 목록 조회
			fileList = fileService.selectFileList(file);
			model.addAttribute("item", item);

			// 즐겨찾기 상태 유지를 위한 객체 생성 및 선언
			Favorite favorite = new Favorite();
			favorite.setBoardId(boardId);
			favorite.setMemberId(memberId);

			// 즐겨찾기 상태 유지를 위한 boolean 값
			boolean favoriteTarget = false;
			if (favoriteService.favoriteExist(favorite) == 1) {
				favoriteTarget = true;
			}
			model.addAttribute("favoriteTarget", favoriteTarget);

			// 좋아요 상태 유지를 위한 boolean 값
			boolean likeTarget = false;
			if (categoryLikeService.selectCategoryLike(like) == 1) {
				likeTarget = true;
			}
			model.addAttribute("likeTarget", likeTarget);
			model.addAttribute("fileList", fileList);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		// View 하단 글 목록 시작

		// 게시물 목록 조회 시 글번호를 DB 일련번호가 아닌 시작값 1로 시작하게 만들기 위한 변수
		int boardNum = 0;

		BoardList params = new BoardList(); // SQL 파라미터로 넘길 BoardList 객체 생성

		params.setType(type); // 검색 조건
		params.setKorCtg(korCtg); // 카테고리를 카테고리에 저장
		if (type.equals("제목%2B내용")){
			params.setTitle(searchWord); // 검색어를 제목에 저장
			params.setContent(searchWord); // 검색어를 내용에 저장
		} else if (type.equals("제목")) {
			params.setTitle(searchWord); // 검색어를 제목에 저장
		} else if (type.equals("작성자")) {
			params.setUserName(searchWord);
		}

		int totalCount = 0; // 게시물 총 개수
		int totalCountAll = 0; // 사이드에 표시하기 위한 게시물 총 개수
		int totalReply = 0; // 댓글 총 개수

		// 댓글 총개수 조회를 위한 객체 생성 및 선언
		BoardReply reply = new BoardReply();
		reply.setBoardId(boardId);
		reply.setMemberId(memberId);

		try {
			totalCount = boardService.getCount(params); // 게시물 전체 개수
			totalCountAll = boardService.getCount(null); // 사이드(전체 글 보기)메뉴에 표시할 게시물 전체 개수
			totalReply = replyService.selectCount(reply); // 댓글 전체 개수
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		int countNotice = 0; // 고정된 공지사항 총 개수
		int noticeCountAll = 0; // 공지사항 총 개수

		try {
			countNotice = boardService.getBoardNoticeCount();
			noticeCountAll = boardService.getBoardNoticeCountAll();
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}

		totalCount -= noticeCountAll; // 게시물 목록 개수에서 공지사항 총 개수 제외

		model.addAttribute("totalCount", totalCount);
		model.addAttribute("totalCountAll", totalCountAll);
		model.addAttribute("totalReply", totalReply);
		model.addAttribute("countNotice", countNotice);

		pageHelper.pageProcess(nowPage, totalCount, 10, 5);
		params.setLimitStart(pageHelper.getLimitStart());
		params.setListCount(pageHelper.getListCount());

		model.addAttribute("pageHelper", pageHelper);

		List<BoardList> list = null; // 조회된 게시물들을 담을 객체
		List<BoardList> noticeList = null; // 조회된 공지사항들을 담을 객체


		try {
			list = boardService.getItemAll(params);
			noticeList = boardService.getBoardNotice();
		} catch (Exception e) {
			web.redirect(null, e.getLocalizedMessage());
			return null;
		}

		// xss 처리
		for (int i=0; i<list.size(); i++) {
			BoardList temp = list.get(i);

			// 공격 코드 있으면 추가...
			if (temp.getTitle().indexOf("<script>") > -1) {
				temp.setTitle(web.getString(null, temp.getTitle(), true));
			} else if (temp.getContent().indexOf("<script>") > -1) {
				temp.setContent(web.getString(null, temp.getContent(), true));
			}
		}

		// xss 처리
		for (int i=0; i<noticeList.size(); i++) {
			BoardList temp = noticeList.get(i);

			// 공격 코드 있으면 추가...
			if (temp.getTitle().indexOf("<script>") > -1) {
				temp.setTitle(web.getString(null, temp.getTitle(), true));
			} else if (temp.getContent().indexOf("<script>") > -1) {
				temp.setContent(web.getString(null, temp.getContent(), true));
			}
		}

		model.addAttribute("list", list);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("userName", list.get(0).getUserName());

		Date date = new Date();
		SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
		String year = (String) simpleDate.format(date);
		model.addAttribute("year", year);

		for (int q = 0; q<list.size(); q++) {
			BoardList item1 = list.get(q);
			if(item1.getBoardId() == boardId){
				// 현재 게시물 임의 번호
				boardNum = totalCount-q-pageHelper.getLimitStart() + countNotice + 1;
				model.addAttribute("boardNum", boardNum);
			}

		}

		// View 하단 글 목록 끝

		// 현재 주소
		String curUrl = request.getRequestURL() + "?" + request.getQueryString();
		logger.debug("curUrl = " + curUrl);
		model.addAttribute("curUrl", curUrl);
		
		logger.warn("View category >>>> " + web.getSession("category"));
		logger.warn("View korCtg >>>> " + web.getSession("korCtg"));


		return new ModelAndView("community/CommunityView");
	}

	@RequestMapping(value = "/CommAdd.do", method = RequestMethod.GET)
	public ModelAndView CommAdd(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		web.init();

		/** ===== 악의적인 접근에 대한 방어 코드 */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "CommIndex.do") && regex.isIndexCheck(referer, "CommView.do") && regex.isIndexCheck(referer, "member_logout.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
        }

        // 로그인 체크
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo == null) {
			return web.redirect(web.getRootPath() + "/CommIndex.do", "글 추가 권한이 없습니다.");
		}

        /** 인기검색어 영역 시작 */
        try {
            List<HotKeyword> hotKeywordList = null; // 조회된 검색어 리스트를 담을 리스트
            hotKeywordList = hotKeywordService.selectHotKeywordList(); // 최근 1달 검색어 조회
            // xss 처리
            for (int i=0; i<hotKeywordList.size(); i++) {
                HotKeyword hotKeywordtemp = hotKeywordList.get(i);
                // 공격 코드를 막기 위한 처리
                hotKeywordtemp.setKeyword(web.convertHtmlTag(hotKeywordtemp.getKeyword()));
                if (hotKeywordtemp.getKeyword().indexOf("<script>") > -1) {
                    hotKeywordtemp.setKeyword(web.getString(null, hotKeywordtemp.getKeyword(), true));
                }
            }
            model.addAttribute("hotKeywordList", hotKeywordList); // View에서 사용하기 위해 등록
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        /** 인기검색어 영역 끝 */

		// 영문 카테고리
		String category = web.getString("category");

		// 국문 카테고리
		String korCtg = "";
		try {
			// 존재하는 게시판 체크
			korCtg = bbsCommon.getBbsName(category);
			if (korCtg.equals("null")) {
				return web.redirect(null, "존재하지 않는 게시판 입니다.");
			}
		} catch (Exception e) {
			web.redirect(null, e.getLocalizedMessage());
			return null;
		}

		// 관리자 인지 체크
		if (category.equals("notice") && !loginInfo.getGrade().equals("Master")) {
			return web.redirect(web.getRootPath() + "/CommIndex.do", "글 추가 권한이 없습니다.");
		}

		// 잘못된 접근 제어
		if (category.equals("notice") && regex.isIndexCheck(referer, "CommIndex.do?category=notice")) {
			return web.redirect(web.getRootPath() + "/error/error_page.do", null);
		}

		logger.debug("category = " + category);
		logger.debug("korCtg = " + korCtg);
		model.addAttribute("category", category);
		model.addAttribute("korCtg", korCtg);

		// 카테고리 세션 등록 (hidden 제외하기위한 처리)
		web.setSession("category", category);

		int totalCount = sqlSession.selectOne("BoardMapper.getCount"); // 전체 개시물 개수
		model.addAttribute("totalCount", totalCount);

		return new ModelAndView("community/CommunityAddItem");
	}

	@RequestMapping(value = "/CommAddOk.do", method = RequestMethod.POST)
	public String CommAddOk(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		web.init();

		/** 컨텐츠 타입 명시 */
		response.setContentType("application/json");
		/** ===== 악의적인 접근에 대한 방어 코드 */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "CommAdd.do"))) {
        	// 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            web.printJsonRt(web.getRootPath() + "/error/error_page.do");
            return null;
        }

        // 로그인 체크
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo == null) {
			web.printJsonRt(web.getRootPath() + "/CommIndex.do");
			return null;
		}

		// multipart 타입 form 처리
		try {
			upload.multipartRequest();
		} catch (Exception e) {
			web.printJsonRt("multipart 데이터가 아닙니다.");
			return null;
		}

		int memberId = loginInfo.getMemberId(); // 조건값인 유저 일련번호 파라미터
		String userName = loginInfo.getUserName(); // 조건값인 작성자 닉네임 파라미터

		// 파라미터 꺼내기
		Map<String, String> paramMap = upload.getParamMap();
		String title = paramMap.get("title").trim(); // 추가될 제목 파라미터
		String content = paramMap.get("textarea"); // 추가될 내용 파라미터

		// xss 처리
		// 공격 코드 있으면 추가...
		if (title.indexOf("<script>") > -1) {
			title = web.getString(null, title, true);
		} else if (content.indexOf("<script>") > -1) {
			content = web.getString(null, content, true);
		}

		// 고정 할 것인지 체크
		String noticeChk = "";
		if(paramMap.get("noticeChk") != null) {
			noticeChk = paramMap.get("noticeChk");
		}
		logger.debug(paramMap.toString());

		// 카테고리 체크
		String category = (String) web.getSession("category");
		if (category == null) {
			web.printJsonRt(web.getRootPath() + "/CommIndex.do");
			return null;
		}

		// 국문 카테고리
		String korCtg = "";
		try {
			// 존재하는 게시판 체크
			korCtg = bbsCommon.getBbsName(category);
			if (korCtg.equals("null")) {
				web.printJsonRt("존재하지 않는 게시판 입니다.");
				return null;
			}
			logger.debug("korCtg = " + korCtg);
			model.addAttribute("korCtg", korCtg);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
			return null;
		}

		// 유효성 검사 시작
		if (!regex.isValue(userName)) {
			upload.removeTempFile();
			web.printJsonRt("로그인 후에 이용 가능합니다.");
			return null;
		}

		if (!regex.isValue(title)) {
			upload.removeTempFile();
			web.printJsonRt("제목을 입력하세요");
			return null;
		}

		if (title.length() < 2) {
			upload.removeTempFile();
			web.printJsonRt("제목을 2글자 이상 입력하세요.");
			return null;
		}

		if (!regex.isValue(content)) {
			upload.removeTempFile();
			web.printJsonRt("내용을 입력하세요.");
			return null;
		}

		if (content.length() < 10) {
			upload.removeTempFile();
			web.printJsonRt("내용을 10자 이상 입력하세요.");
			return null;
		}
		// 유효성 검사 끝

		BoardList item = new BoardList(); // 추가될 게시물 아이템들을 설정하기 위한 BoardList 객체 생성
		item.setCategory(category); // category 파라미터 값을 객체 category에 저장
		item.setKorCtg(korCtg);
		item.setMemberId(memberId); // memberId 파라미터 값을 객체 memberId에 저장
		item.setUserName(userName); // userName 파라미터 값을 객체 userName에 저장
		item.setTitle(title); // title 파라미터 값을 객체 title에 저장
		item.setContent(content); // content 파라미터 값을 객체 content에 저장

		// 공지사항 고정 값 있을 시 세팅
		if (!noticeChk.equals("")) {
			item.setNoticeChk(noticeChk);
		}

		// 게시물 등록
		try {
			boardService.addItem(item);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
			return null;
		}

		// 첨부파일 받아오기
		List<FileInfo> fileList = upload.getFileList();

		try {
			// 업로드 된 파일의 수 만큼 반복 처리 한다.
			for (int i=0; i<fileList.size(); i++) {
				// 업로드 된 정보 하나 추출하여 데이터베이스에 저장하기 위한 형태로 가공해야 한다.
				FileInfo info = fileList.get(i);

				// DB에 저장하기 위한 항목 생성
				File file = new File();

				// 몇 번 게시물에 속한 파일인지 지정한다.
				file.setBoardId(item.getBoardId());

				// 데이터 복사
				file.setOrginName(info.getOrginName());
				file.setFileDir(info.getFileDir());
				file.setFileName(info.getFileName());
				file.setContentType(info.getContentType());
				file.setFileSize(info.getFileSize());

				// 파일 확장자 체크
				String ext = file.getOrginName().substring(file.getOrginName().indexOf(".") + 1).toLowerCase();
				if (!ext.equals("gif") && !ext.equals("jpg") && !ext.equals("jpeg") && !ext.equals("png")) {
					throw new FileUploadException();
				}

				// 파일 용량 체크
				int maxSize = 1024 * 1024 * 5;
				if (file.getFileSize() > maxSize) {
					throw new FileUploadException();
				}

				// 저장처리
				fileService.insertFile(file);
			}
		} catch (FileUploadException e) {
			web.printJsonRt("FileFail");
			return null;
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
			return null;
		}

		// 세션 제거
		web.removeSession("category");

		// 글 등록 후 페이지 이동시키기 위한 URL
		String url = "%s/CommView.do?boardId=%d&category=%s";
		url = String.format(url, web.getRootPath(), item.getBoardId(), item.getCategory());

		// JSON 출력
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "OK");
		data.put("item", item);
		data.put("url", url);

		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		logger.warn("View category >>>> " + web.getSession("category"));
		logger.warn("View korCtg >>>> " + web.getSession("korCtg"));

		return null;
	}

	@RequestMapping(value = "/CommEdit.do", method = RequestMethod.GET)
	public ModelAndView CommEdit(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		web.init();
		
		/** ===== 악의적인 접근에 대한 방어 코드 */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && (regex.isIndexCheck(referer, "CommView.do") && regex.isIndexCheck(referer, "member_logout.do")))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            return web.redirect(web.getRootPath() + "/error/error_page.do", null);
        }

        // 로그인 체크
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo == null) {
			return web.redirect(web.getRootPath() + "/CommIndex.do", "수정 권한이 없습니다.");
		}

        /** 인기검색어 영역 시작 */
        try {
            List<HotKeyword> hotKeywordList = null; // 조회된 검색어 리스트를 담을 리스트
            hotKeywordList = hotKeywordService.selectHotKeywordList(); // 최근 1달 검색어 조회
            // xss 처리
            for (int i=0; i<hotKeywordList.size(); i++) {
                HotKeyword hotKeywordtemp = hotKeywordList.get(i);
                // 공격 코드를 막기 위한 처리
                hotKeywordtemp.setKeyword(web.convertHtmlTag(hotKeywordtemp.getKeyword()));
                if (hotKeywordtemp.getKeyword().indexOf("<script>") > -1) {
                    hotKeywordtemp.setKeyword(web.getString(null, hotKeywordtemp.getKeyword(), true));
                }
            }
            model.addAttribute("hotKeywordList", hotKeywordList); // View에서 사용하기 위해 등록
        } catch (Exception e) {
            return web.redirect(null, e.getLocalizedMessage());
        }
        /** 인기검색어 영역 끝 */

		int boardId = Integer.parseInt(web.getString("boardId")); // 게시물 일련번호 구분을 위한 파라미터
		logger.debug("boardId = " + boardId);
		model.addAttribute("boardId", boardId);
		web.setSession("boardId", String.valueOf(boardId));

		// 영문 카테고리 저장
		String category = web.getString("category");
		logger.debug("category = " + category);
		model.addAttribute("category", category);
		web.setSession("category", category);

		// 국문 카테고리
		String korCtg = "";
		try {
			// 존재하는 게시판 체크
			korCtg = bbsCommon.getBbsName(category);
			if (korCtg.equals("null")) {
				return web.redirect(null, "존재하지 않는 게시판 입니다.");
			}
			logger.debug("korCtg = " + korCtg);
			model.addAttribute("korCtg", korCtg);
			web.setSession("korCtg", korCtg);
		} catch (Exception e) {
			web.redirect(null, e.getLocalizedMessage());
			return null;
		}

		// 소유권 검사 정보
		boolean myDocument = false;

		if (loginInfo != null) {
			try {
				// 소유권 판정을 위하여 사용하는 임시 객체
				BoardList temp = new BoardList();
				temp.setKorCtg(korCtg);
				temp.setBoardId(boardId);
				temp.setMemberId(loginInfo.getMemberId());

				if (boardService.selectDocumentCountByMemberId(temp) > 0) {
					// 소유권을 의미하는 변수 변경
					myDocument = true;
				}
			} catch (Exception e) {
				web.printJsonRt(e.getLocalizedMessage());
				return null;
			}
		}

		if (!myDocument) {
			return web.redirect(null, "자신의 게시물도 아니면서 왜 건드립니까 ?");
		}

		int totalCount = sqlSession.selectOne("BoardMapper.getCount"); // 전체 게시물 개수
		model.addAttribute("totalCount", totalCount);

		BoardList item = new BoardList(); // 선택된 게시물을 조회하기 위한 BoardList 객체 생성
		item.setBoardId(boardId); // boardId 파라미터 값을 객체에 boardId에 저장

		// 첨부파일이 있는지 체크
		File file = new File();
		file.setBoardId(boardId);

		BoardList obj = null; // 조회된 게시물 내용을 담을 BoardList 객체 생성

		// 첨부파일 정보가 저장될 객체
		List<File> fileList = null;

		try {
			// 조회된 게시물 내용을 obj에 담는다
			if (!korCtg.equals("공지사항")) {
				obj = boardService.getItem(item);
			} else {
				obj = boardService.getBoardNoticeItem(item);
			}

			// xss 처리
			obj.setTitle(web.getString(null, obj.getTitle(), true));
			obj.setContent(web.getString(null, obj.getContent(), true));

			model.addAttribute("obj", obj);
			fileList = fileService.selectFileList(file);
			model.addAttribute("fileList", fileList);
		} catch (Exception e) {
			web.redirect(null, e.getLocalizedMessage());
		}
		
		logger.warn("Edit category >>>> " + web.getSession("category"));
		logger.warn("Edit korCtg >>>> " + web.getSession("korCtg"));

		return new ModelAndView("community/CommunityEdit");
	}

	@RequestMapping(value = "/CommEditOk.do", method = RequestMethod.POST)
	public String CommEditOk(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		web.init();

		/** 컨텐츠 타입 명시 */
		response.setContentType("application/json");

		/** ===== 악의적인 접근에 대한 방어 코드 */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "CommEdit.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            web.printJsonRt(web.getRootPath() + "/error/error_page.do");
            return null;
        }

        // 로그인 체크
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo == null) {
			web.printJsonRt("수정 권한이 없습니다.");
			return null;
		}

		/** (3) 파일이 포함된 POST 파라미터 받기 */
		try {
			upload.multipartRequest();
		} catch (Exception e) {
			web.printJsonRt("multipart 데이터가 아닙니다.");
			return null;
		}

		/** (4) UploadHelper에서 텍스트 형식의 값을 추출 */
		int memberId = loginInfo.getMemberId(); // 조건값인 유저 일련번호 파라미터
		String userName = loginInfo.getUserName(); // 조건값인 작성자 닉네임 파라미터

		Map<String, String> paramMap = upload.getParamMap();
		String title = paramMap.get("title").trim(); // 추가될 제목 파라미터
		String content = paramMap.get("textarea"); // 추가될 내용 파라미터

		// xss 처리
		// 공격 코드 있으면 추가...
		if (title.indexOf("<script>") > -1) {
			title = web.getString(null, title, true);
		} else if (content.indexOf("<script>") > -1) {
			content = web.getString(null, content, true);
		}

		// 게시물 번호 세션 꺼내오기
		String boardIdStr = (String) web.getSession("boardId");
		int boardId = Integer.parseInt(boardIdStr);
		// 공지유무 체크 (공지사항 인 경우)
		String noticeChk = "";
		if(paramMap.get("noticeChk") != null) {
			noticeChk = paramMap.get("noticeChk");
		}
		logger.debug(paramMap.toString());

		// 카테고리 세션 꺼내오기
		String category = (String) web.getSession("category");
		if (category == null) {
			web.printJsonRt(web.getRootPath() + "/CommIndex.do");
			return null;
		}

		logger.debug("category = " + category);
		model.addAttribute("category", category);

		// 국문 카테고리
		String korCtg = "";
		try {
			// 존재하는 게시판 체크
			korCtg = bbsCommon.getBbsName(category);
			if (korCtg.equals("null")) {
				web.printJsonRt("존재하지 않는 게시판 입니다.");
				return null;
			}
			logger.debug("korCtg = " + korCtg);
			model.addAttribute("korCtg", korCtg);
		} catch (Exception e) {
			web.redirect(null, e.getLocalizedMessage());
			return null;
		}

		/** (5) 게시판 카테고리 값을 받아서 View에 전달 */
		// 파일이 첨부된 경우 WebHelper를 사용할 수 없다.
		// String category = web.getString("category");
		model.addAttribute("category", category);

		/** (6) 존재하는 게시판인지 판별하기 */
		try {
			String bbsName = bbsCommon.getBbsName(category);
			model.addAttribute("bbsName", bbsName);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
			return null;
		}

		/** (7) 로그인 한 경우 자신의 글이라면 입력하지 않은 정보를 세션 데이터로 대체한다. */
		// 소유권 검사 정보
		boolean myDocument = false;

		if (loginInfo != null) {
			try {
				// 소유권 판정을 위하여 사용하는 임시 객체
				BoardList temp = new BoardList();
				temp.setKorCtg(korCtg);
				temp.setBoardId(boardId);
				temp.setMemberId(loginInfo.getMemberId());


				if (boardService.selectDocumentCountByMemberId(temp) > 0) {
					// 소유권을 의미하는 변수 변경
					myDocument = true;
					// 입력되지 않은 정보들 갱신
					memberId = loginInfo.getMemberId();
				}
			} catch (Exception e) {
				web.printJsonRt(e.getLocalizedMessage());
				return null;
			}
		}

		// 전달된 파라미터는 로그로 확인한다.
		logger.debug("boardId=" + boardId);
		logger.debug("category=" + category);
		logger.debug("content=" + content);
		logger.debug("memberId=" + memberId);

		/** (8) 입력 받은 파라미터에 대한 유효성 검사 */
		// 이름 + 비밀번호
		if (!regex.isValue(userName)) {
			upload.removeTempFile();
			web.printJsonRt("로그인 후에 이용 가능합니다.");
			return null;
		}

		if (!regex.isValue(title)) {
			upload.removeTempFile();
			web.printJsonRt("제목을 입력하세요");
			return null;
		}

		if (title.length() < 2) {
			upload.removeTempFile();
			web.printJsonRt("제목을 2글자 이상 입력하세요.");
			return null;
		}

		if (!regex.isValue(content)) {
			upload.removeTempFile();
			web.printJsonRt("내용을 입력하세요.");
			return null;
		}

		if (content.length() < 10) {
			upload.removeTempFile();
			web.printJsonRt("내용을 10자 이상 입력하세요.");
			return null;
		}

		BoardList item = new BoardList(); // 추가될 게시물 아이템들을 설정하기 위한 BoardList 객체 생성
		item.setMemberId(memberId); // memberId 파라미터 값을 객체 memberId에 저장
		item.setTitle(title); // title 파라미터 값을 객체 title에 저장
		item.setContent(content); // content 파라미터 값을 객체 content에 저장
		item.setBoardId(boardId);

		if (!noticeChk.equals("")) {
			item.setNoticeChk(noticeChk);
		}

		/** (10) 게시물 변경을 위한 Service 기능을 호출 */
		try {
			if (!myDocument) {
				throw new Exception("건들지마세요 남의 자료 ㅋ");
			}

			if (!korCtg.equals("공지사항")) {
				boardService.editItem(item);
			} else {
				boardService.editNotice(item);
			}
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
			return null;
		}

		/** (11) 삭제를 선택한 첨부파일에 대한 처리 */
		// 삭제할 파일 목록에 대한 체크결과 --> 체크박스의 선택값을 paramMap에서 추출
		String delFile = paramMap.get("del_file");

		if (delFile != null) {
			// 콤마 단위로 잘라서 배열로 변환
			String[] delFileList = delFile.split(",");

			for (int i = 0; i < delFileList.length; i++) {
				try {
					// 체크박스에 의해서 전달된 id값으로 개별 파일에 대한 Beans 생성
					File file = new File();
					file.setFileId(Integer.parseInt(delFileList[i]));

					// 개별 파일에 대한 정보를 조회하여 실제 파일을 삭제한다.
					File fileItem = fileService.selectFile(file);
					upload.removeFile(fileItem.getFileDir() + "/" + fileItem.getFileName());

					// DB에서 파일정보 삭제처리
					fileService.deleteFile(file);
				} catch (Exception e) {
					web.printJsonRt(e.getLocalizedMessage());
					return null;
				}
			}
		}

		/** (12) 추가적으로 업로드 된 파일 정보 처리 */
		// 업로드 된 파일 목록
		List<FileInfo> fileInfoList = upload.getFileList();

		try {
			// 업로드 된 파일의 수 만큼 반복 처리 한다.
			for (int i = 0; i < fileInfoList.size(); i++) {
				// 업로드 된 정보 하나 추출
				// --> 업로드 된 정보를 데이터베이스에 저장하기 위한 형태로 가공해야 한다.
				FileInfo info = fileInfoList.get(i);

				// DB에 저장하기 위한 항목 하나 생성
				File file = new File();

				// 데이터 복사
				file.setOrginName(info.getOrginName());
				file.setFileDir(info.getFileDir());
				file.setFileName(info.getFileName());
				file.setContentType(info.getContentType());
				file.setFileSize(info.getFileSize());

				// 어느 게시물에 속한 파일인지 인식해야 하므로 글 번호 추가
				file.setBoardId(boardId);

				// 파일 확장자 체크
				String ext = file.getOrginName().substring(file.getOrginName().indexOf(".") + 1).toLowerCase();
				if (!ext.equals("gif") && !ext.equals("jpg") && !ext.equals("jpeg") && !ext.equals("png")) {
					throw new FileUploadException();
				}

				// 파일 용량 체크
				int maxSize = 1024 * 1024 * 5;
				if (file.getFileSize() > maxSize) {
					throw new FileUploadException();
				}

				// 복사된 데이터를 DB에 저장
				fileService.insertFile(file);
			}
		} catch (FileUploadException e) {
			web.printJsonRt("FileFail");
			return null;
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
			return null;
		}

		/** (13) 모든 절차가 종료되었으므로 DB접속 해제 후 페이지 이동 */

		JSONObject json = new JSONObject();

		json.put("rt", "OK");
		json.put("boardId", item.getBoardId());
		json.put("category", category);
		json.put("korCtg", korCtg);
		json.put("userName", item.getUserName());
		json.put("title", item.getTitle());
		json.put("content", item.getContent());
		String url = "%s/CommView.do?boardId=%d&category=%s";
		url = String.format(url, web.getRootPath(), item.getBoardId(), category);
		json.put("url", url);
		// 파일 업로드 개수만큼 json 만들어놓기

		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 세션 삭제
		web.removeSession("category");
		web.removeSession("boardId");

		return null;
	}

	@RequestMapping(value = "/CommDeleteOk.do", method = RequestMethod.POST)
	public String CommDeleteOk(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		web.init();
		
		/** 컨텐츠 타입 명시 */
		response.setContentType("application/json");

		/** ===== 악의적인 접근에 대한 방어 코드 */
        String referer = request.getHeader("referer");
        if(referer == null || (referer != null && regex.isIndexCheck(referer, "CommView.do"))) {
            // 정상적인 접근 방법이 아닐 경우
            logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
            web.printJsonRt(web.getRootPath() + "/error/error_page.do");
            return null;
        }

        // 로그인 체크
        Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo == null) {
			web.printJsonRt("삭제 권한이 없습니다.");
			return null;
		}

		int boardId = Integer.parseInt(web.getString("boardId")); // 조건값인 게시물 일련번호 파라미터
		logger.debug("boardId = " + boardId);

		// 국문 카테고리 받기
		String korCtg = web.getString("korCtg");
		logger.debug("korCtg = " + korCtg);

		if (boardId == 0) {
			web.printJsonRt("글 번호가 없습니다.");
			return null;
		}

		// 소유권 검사 정보
		boolean myDocument = false;

		if (loginInfo != null) {
			try {
				// 소유권 판정을 위하여 사용하는 임시 객체
				BoardList temp = new BoardList();
				temp.setKorCtg(korCtg);
				temp.setBoardId(boardId);
				temp.setMemberId(loginInfo.getMemberId());


				if (boardService.selectDocumentCountByMemberId(temp) > 0) {
					// 소유권을 의미하는 변수 변경
					myDocument = true;
				}
			} catch (Exception e) {
				web.printJsonRt(e.getLocalizedMessage());
				return null;
			}
		}

		// 관리자 인 경우 삭제 권한 부여
		if (loginInfo.getGrade().equals("Master")) {
			myDocument = true;
		}

		if (!myDocument) {
			web.printJsonRt("AuthFail");
			return null;
		}

		BoardList item = new BoardList(); // 삭제될 게시물 일련번호를 설정하기 위한 BoardList 객체 생성
		item.setBoardId(boardId); // boardId 파라미터 값을 객체 boardId에 저장
		item.setKorCtg(korCtg);
		item.setMemberId(loginInfo.getMemberId());

		// 첨부파일 조회
		File file = new File();
		file.setBoardId(boardId);

		// 게시물에 속한 댓글 삭제를 위해서 생성
		BoardReply reply = new BoardReply();
		reply.setBoardId(boardId);

		// 게시물 좋아요 멤버 참조관계 해제
		CategoryLike boardLike = new CategoryLike();
		boardLike.setBoardId(boardId);


		List<File> fileList = null; // 게시물에 속한 파일 목록에 대한 조회결과

		try {
			if (!loginInfo.getGrade().equals("Master")) {
				if (boardService.selectDocumentCountByMemberId(item) < 1) {
					web.printJsonRt("삭제 권한이 없습니다.");
					return null;
				}
			}

			fileList = fileService.selectFileList(file); // 게시글에 포함된 파일목록을 조회
			fileService.deleteFileAll(file);				// 게시글에 속한 파일목록 모두 삭제

			List<BoardReply> list = null;

			// 댓글의 댓글이 댓글을 참조하므로 먼저 삭제
			list = replyService.selectCommentListByReplyId(reply);

			for (int i=0; i<list.size(); i++) {
				BoardReply temp = list.get(i);

				BoardReplyReply replyReply = new BoardReplyReply();
				replyReply.setReplyId(temp.getReplyId());

				// 대댓글 좋아요 참조관계 해제 및 삭제
				replyService.deleteReplyReplyLike(replyReply);
				// 대댓글 삭제
				replyService.deleteReComment(replyReply);
				// 댓글 좋아요 참조관계 해제 및 삭제
				replyService.deleteReplyLike(temp);
				// 댓글 삭제
				replyService.deleteComment(temp);
			}

			// 댓글이 게시물을 참조하므로, 댓글이 먼저 삭제되어야 한다.
			replyService.deleteCommentAll(reply);

			// 게시물 좋아요 참조관계 삭제
			categoryLikeService.deleteCategoryLikeMemberOut(boardLike);

			boardService.removeItem(item);	// 게시글 삭제

		} catch (Exception e) {
			e.printStackTrace();
			web.printJsonRt(e.getLocalizedMessage());
			return null;
		}

		/** (8) 실제 파일을 삭제한다. */
		// DB에서 파일 정보가 삭제되더라도 실제 저장되어 있는 파일 자체가 삭제되는 것은 아니다.
		// 실제 파일도 함께 삭제하기 위해서 (7)번 절차에서 파일정보를 삭제하기 전에 미리
		// 조회해 둔 것이다.
		// 조회한 파일 목록만큼 반복하면서 저장되어 있는 파일을 삭제한다.
		if (fileList != null) {
			for (int i=0; i<fileList.size(); i++) {
				File f = fileList.get(i);
				String filePath = f.getFileDir() + "/" + f.getFileName();
				logger.debug("filePath = " + filePath);
				upload.removeFile(filePath);
			}
		}

		/** (6) 처리 결과를 JSON으로 출력하기 */
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "OK");
		data.put("boardId", boardId);

		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		logger.warn("Delete category >>>> " + web.getSession("category"));
		logger.warn("Delete korCtg >>>> " + web.getSession("korCtg"));

		return null;
	}

	@RequestMapping(value = "/CommLike.do", method = RequestMethod.POST)
	public String CommLike(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		web.init();

		/** 컨텐츠 타입 명시 */
		response.setContentType("application/json");

		// 로그인 체크
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo == null) {
			web.printJsonRt("권한이 없습니다.");
			return null;
		}

		logger.debug("memberId >> " + loginInfo.getMemberId());

		int boardId = web.getInt("boardId"); // 게시물 일련번호 구분을 위한 파라미터
		logger.debug("boardId = " + boardId);

		String chk = web.getString("chk"); // 좋아요 상태 유지
		logger.debug("chk = " + chk);

		int boardLikeCnt = Integer.parseInt(web.getString("boardLike")); // JSON에 넣기 전 String으로 파라미터를 받는다
		logger.debug("boardLikeCnt = " + boardLikeCnt);

		BoardList params = new BoardList(); // 좋아요 업데이트 할 정보 저장
		params.setBoardId(boardId);
		params.setBoardLike(boardLikeCnt);

		BoardList list = null; // 업데이트 후 받을 객체

		CategoryLike temp = new CategoryLike(); // 선택된 게시물을 조회하기 위한 BoardList 객체 생성
		temp.setBoardId(boardId); // boardId 파라미터 값을 객체에 boardId에 저장
		temp.setMemberId(loginInfo.getMemberId());

		try {
			// 타입에 따라 분류하여 증가 및 감소 한 값으로 키 재설정
			if (chk.equals("Y")) {
				if (categoryLikeService.selectCategoryLike(temp) == 0) {
					categoryLikeService.addCategoryLike(temp);
					boardService.editBoardByLikeUp(params);
					list = boardService.getItem(params);
				}
			} else if (chk.equals("N")) {
				if (categoryLikeService.selectCategoryLike(temp) == 1) {
					categoryLikeService.deleteCategoryLike(temp);
					boardService.editBoardByLikeDown(params);
					list = boardService.getItem(params);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			web.printJsonRt(e.getLocalizedMessage());
			return null;
		}

		/** 인기검색어에 등록 시작  */
		try {
		    if(list != null) {
		        if(list.getTitle() != null && !list.getTitle().equals("")) {
		            HotKeyword hotKeywordInsert = new HotKeyword(); // 검색어를 Beans에 묶는다.
		            hotKeywordInsert.setKeyword(list.getTitle());
		            hotKeywordService.insertHotKeyword(hotKeywordInsert); // 검색어를 저장
		        }
		    }
		} catch (Exception e) {
		    web.printJsonRt(e.getLocalizedMessage());
            return null;
		}
		/** 인기검색어에 등록 끝 */

		JSONObject rss = new JSONObject();
		JSONObject item = new JSONObject();

		rss.put("rt", "OK");
		item.put("boardLike", list.getBoardLike());
		rss.put("item", item);


		try {
			response.getWriter().print(rss);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "/imageUpload.do", method=RequestMethod.POST)
	public String imageUpload(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {

		web.init();

		/** 컨텐츠 타입 명시 */
		response.setContentType("application/json");

		// CKEDITOR 이미지 업로드 서버 전송 시 받을 수 있는 multipart 요청 처리
		try {
			upload.multipartRequest();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		// 첨부파일 읽기
		List<FileInfo> fileList = upload.getFileList();

		// 서버 URL 만들기
		String url = "%s/downloadImage.do?file=%s/%s";

		// 파일 이름 담기
		String fileName = "";

		if (fileList.size() > 0) {
			FileInfo temp = fileList.get(0);

			url = String.format(url, web.getRootPath(), temp.getFileDir(), temp.getFileName());
			fileName = temp.getFileName();

			model.addAttribute("url", url);

		}

		/** (6) 처리 결과를 JSON으로 출력하기 */
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("uploaded", 1);
		data.put("fileName", fileName);
		data.put("url", url);

		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}

	@RequestMapping(value = "/downloadImage.do")
	public String DownloadImage(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		web.init();

		// 파일 경로 읽어오기
		String fileDir = web.getString("file");

		if (fileDir != null) {
			try {
				// 미리보기를 위한 이미지 읽어오기
				upload.printFileStream(fileDir, null);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return null;
	}
}
