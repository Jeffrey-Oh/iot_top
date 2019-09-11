package project.spring.travel.controller.community;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import project.spring.helper.RegexHelper;
import project.spring.helper.WebHelper;
import project.spring.travel.model.BoardReply;
import project.spring.travel.model.BoardReplyReply;
import project.spring.travel.model.BoardReplyReplyLike;
import project.spring.travel.model.HotKeyword;
import project.spring.travel.model.Member;
import project.spring.travel.service.HotKeywordService;
import project.spring.travel.service.ReplyLikeService;
import project.spring.travel.service.ReplyService;

/**
 * Servlet implementation class CommunityReplyReply
 */
@Controller
public class CommunityReplyReply {
	
	/** (1) 사용하고자 하는 Helper 객체 선언 */
	Logger logger = LoggerFactory.getLogger(CommunityReplyReply.class);
	
	@Autowired
	WebHelper web;
	
	@Autowired
	RegexHelper regex;
	
	@Autowired
	ReplyService replyService;
	
	@Autowired
	ReplyLikeService replyLikeService;
	
	// -> import project.spring.travel.service.HotKeywordService;
	@Autowired
	HotKeywordService hotKeywordService;
	
	@RequestMapping(value = "/CommReplyReply2.do", method = RequestMethod.POST)
	public String CommReplyReplySetSession(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		web.init();

		response.setContentType("application/json");
		
		int memberId = 0;
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo != null) {
			memberId = loginInfo.getMemberId();
		}
		
		if (memberId == 0) {
			web.printJsonRt("로그인 필요한 서비스 입니다.");
			return null;
		}
		
		int replyId = web.getInt("replyId");
		web.setSession("replyId", replyId);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "OK");
		data.put("s", web.getSession("replyId"));
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@RequestMapping(value = "/CommReplyReply.do", method = RequestMethod.GET)
	public String CommReplyReply(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		web.init();
		
		response.setContentType("application/json");
		
		int memberId = 0;
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo != null) {
			memberId = loginInfo.getMemberId();
			logger.debug("memberId = " + memberId);
		}
		
		/** (3) 파라미터 받기 */
		// 어떤 게시물에 속한 댓글들을 조회할 지 판별하기 위하여
		// 게시물 일련번호를 파라미터로 받는다.
		int replyId = web.getInt("replyId");
		logger.debug("replyId = " + replyId);
		
		int reLimitStart = Integer.parseInt(web.getString("reLimitStart", "0")); // 현재 페이지
		logger.debug("reLimitStart = " + reLimitStart);
		
		String reLimitLast = web.getString("reLimitLast", "");
		logger.debug("reLimitLast = " + reLimitLast);
		
		/** (4) 입력 받은 파라미터에 대한 유효성 검사 */
		// 댓글이 소속될 게시물의 일련번호
		if (replyId == 0) {
			web.printJsonRt("댓글 일련번호가 없습니다.");
			return null;
		}
		
		/** (5) 입력 받은 파라미터를 Beans로 묶기 */
		BoardReplyReply replyReply = new BoardReplyReply();
		replyReply.setReplyId(replyId);
		replyReply.setMemberId(memberId);
		
		int totalCount = 0;
		
		try {
			totalCount = replyService.selectReCount(replyReply); // 댓글 전체 개수
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
			return null;
		}
		
		logger.debug("totalCount = " + totalCount);
		
		int firstRange = totalCount / 10; // 처음 10의자리 수
		logger.debug("firstRange = " + firstRange);
		int secondRange = totalCount % 10; // 처음 1의자리 수
		logger.debug("secondRange = " + secondRange);
		
		if (reLimitLast.equals("N")) {
			firstRange = reLimitStart / 10;
			secondRange = reLimitStart % 10;
			reLimitStart = (firstRange - 1) * 10 + secondRange;
			replyReply.setLimitStart(reLimitStart); // 아직 불러올 댓글이 10개 이상인 경우
			replyReply.setListCount(10);
			logger.debug("LimitStart = " + reLimitStart);
		}
		
		if (reLimitStart == 0) {
			reLimitStart = (firstRange - 1) * 10 + secondRange;
			if (reLimitStart < 0) {
				reLimitStart = 0;
			}
			logger.debug("reLimitStartNew = " + reLimitStart);
			replyReply.setLimitStart(reLimitStart); // 처음 불러올 댓글이 10개 이상인 경우
			replyReply.setListCount(10);
		}
		
		if (reLimitLast.equals("Y")) {
			replyReply.setLimitStart(0); // 불러올 댓글이 10개 미만인 경우 나머지를 다 부름
			replyReply.setListCount(reLimitStart);
			logger.debug("LimitStart = " + reLimitStart);
			reLimitStart = 0;
		}
		
		/** (6) Service를 통한 댓글 목록 조회 */
		List<BoardReplyReply> item = null;
		try {
			item = replyService.selectReCommentList(replyReply);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
			return null;
		}
		
		/** (7) 처리 결과를 JSON으로 출력하기 */
		// 줄바꿈이나 HTML특수문자에 대한 처리 및 좋아요 현황 설정
		int chk = 0;
		for (int i=0; i<item.size(); i++) {
			
			BoardReplyReply temp = item.get(i);
			
			BoardReplyReplyLike boardReplyReplyLike = new BoardReplyReplyLike();
			boardReplyReplyLike.setReplyReplyId(temp.getReplyReplyId());
			boardReplyReplyLike.setMemberId(memberId);
			
			temp.setContent(web.convertHtmlTag(temp.getContent()));
			try {
				chk = replyLikeService.replyReplyCheck(boardReplyReplyLike);
			} catch (Exception e) {
				web.printJsonRt(e.getLocalizedMessage());
				return null;
			}
			
			// 좋아요 현황 체크
			if (chk != 0) {
				temp.setChk("Y");
			}
			
			// 본인 것인지 확인
			if (temp.getMemberId() == memberId && memberId != 0) {
				temp.setChkMemberId("Y");
			}
			
//			logger.debug("chk >> " + chk);
//			logger.debug("replyId >> " + temp.getReplyId());
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "OK");
		data.put("item", item);
		data.put("reLimitStart", reLimitStart);
		data.put("reLimitLast", reLimitLast);
		data.put("tc", totalCount);
		data.put("replyId", replyId);
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	@RequestMapping(value = "/CommReplyReplyInsert.do", method = RequestMethod.POST)
	public String CommReplyReplyInsert(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		web.init();
		
		response.setContentType("application/json");
		
		/** (3) 파라미터 받기 */
		int replyId = (int) web.getSession("replyId");
		String userName = web.getString("userName");
		String content = web.getString("content");
		int memberId = 0;
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo != null) {
			userName = loginInfo.getUserName();
			memberId = loginInfo.getMemberId();
		}

		logger.debug("replyId = " + replyId);
		logger.debug("userName = " + userName);
		logger.debug("content = " + content);
		logger.debug("memberId = " + memberId);
		
		/** (4) 입력 받은 파라미터에 대한 유효성 검사 */
		if (replyId == 0) {
			web.printJsonRt("게시물 일련번호가 없습니다");
			return null;
		}
		
		if (memberId == 0) {
			web.printJsonRt("로그인 필요한 서비스 입니다.");
			return null;
		}
		
		if (!regex.isValue(userName)) {
			web.printJsonRt("로그인 필요한 서비스 입니다.");
			return null;
		}
		
		if (!regex.isValue(content)) {
			web.printJsonRt("내용을 입력하세요.");
			return null;
		}
		
		/** 현재 게시물에 등록된 원본 댓글인지 확인 */
		BoardReply reply = new BoardReply();
		reply.setBoardId((int) web.getSession("boardId"));
		reply.setReplyId(replyId);
		try {
			if (replyService.selectCheckThisReplyIdByBoard(reply) == 0) {
				web.printJsonRt("해당 게시물에 있는 댓글이 아닙니다.");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/** (5) 입력 받은 파라미터를 Beans로 묶기 */
		BoardReplyReply replyReply = new BoardReplyReply();
		replyReply.setReplyId(replyId);
		replyReply.setUserName(userName);
		replyReply.setContent(content);
		replyReply.setMemberId(memberId);
		logger.debug("replyReply >> " + replyReply.toString());
		
		/** (6) Service를 통한 댓글 저장 */
		BoardReplyReply item = null;
		
		try {
			replyService.insertReComment(replyReply);
			item = replyService.selectReComment(replyReply);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
			return null;
		}
		
		/** (7) 처리 결과를 JSON으로 출력하기 */
		// 줄바꿈이나 HTML특수문자에 대한 처리
		item.setUserName(web.convertHtmlTag(item.getUserName()));
		item.setContent(web.convertHtmlTag(item.getContent()));
		
		// 본인 것인지 확인
		if (item.getMemberId() == memberId && memberId != 0) {
			item.setChkMemberId("Y");
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "OK");
		data.put("item", item);
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@RequestMapping(value = "/CommReplyReplyEdit.do", method = RequestMethod.GET)
	public ModelAndView CommReplyReplyEdit(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		web.init();
		
		/** (3) 글 번호 파라미터 받기 */
		int replyReplyId = web.getInt("replyReplyId");
		if (replyReplyId == 0) {
			return web.redirect(null, "댓글 번호가 지정되지 않았습니다.");
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
		
		// 파라미터를 Beans로 묶기
		BoardReplyReply replyReply = new BoardReplyReply();
		replyReply.setReplyReplyId(replyReplyId);
		
		/** (4) 댓글 일련번호를 사용한 데이터 조회 */
		// 지금 읽고 있는 댓글이 저장될 객체
		BoardReplyReply readReplyReply = null;
		
		try {
			readReplyReply = replyService.selectReComment(replyReply);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		/** (5) 읽은 데이터를 View에게 전달한다. */
		model.addAttribute("readReplyReply", readReplyReply);
		
		return new ModelAndView("community/CommunityReplyReplyEditModal");
	}
	
	@RequestMapping(value = "/CommReplyReplyEditOk.do", method = RequestMethod.POST)
	public String CommReplyReplyEditOk(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		web.init();
		
		response.setContentType("application/json");
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo == null) {
			web.printJsonRt("댓글 수정 권한이 없습니다.");
			return null;
		}
		
		/** (3) 파라미터 받기 */
		int replyReplyId = web.getInt("replyReplyId");
		String replyReplyContent = web.getString("replyReplyContent");
		
		// 회원 일련번호 --> 비 로그인인 경우 0
		int memberId = 0;
		
		// 전달된 파라미터는 로그로 확인한다.
		logger.debug("replyReplyId = " + replyReplyId);
		logger.debug("replyReplyContent = " + replyReplyContent);
		
		/** (4) 로그인 한 경우 자신의 글이라면 입력하지 않은 정보를 세션 데이터로 대체 */
		// 소유권 검사 정보
		boolean myComment = false;
		
		
		try {
			// 소유권 판정을 위하여 사용하는 임시 객체
			BoardReplyReply temp = new BoardReplyReply();
			temp.setReplyReplyId(replyReplyId);
			temp.setMemberId(loginInfo.getMemberId());
			
			if (replyService.selectReCommentCountByMemberId(temp) > 0) {
				// 소유권을 의미하는 변수 변경
				myComment = true;
				// 입력되지 않은 정보들 갱신
				memberId = loginInfo.getMemberId();
				
			}
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
			return null;
		}
		
		
		if (memberId == 0) {
			web.printJsonRt("로그인이 필요한 서비스입니다.");
			return null;
		}
		
		/** (5) 입력 받은 파라미터에 대한 유효성 검사 */
		if (replyReplyId == 0) {
			web.printJsonRt("댓글 번호가 없습니다.");
			return null;
		}
		
		if (!regex.isValue(replyReplyContent)) {
			web.printJsonRt("내용을 입력하세요.");
			return null;
		}
		
		/** (6) 입력 받은 파라미터를 Beans로 묶기 */
		BoardReplyReply replyReply = new BoardReplyReply();
		replyReply.setReplyReplyId(replyReplyId);
		replyReply.setContent(replyReplyContent);
		replyReply.setMemberId(memberId);
		logger.debug("replyReply >> " + replyReply.toString());
		
		/** (7) 게시물 변경을 위한 Service 기능을 호출 */
		BoardReplyReply item = null;
		
		int chk = 0;
		try {
			// 자신의 글이 아니라면 비밀번호 검사를 먼저 수행
			if (!myComment) {
				web.printJsonRt("댓글 수정 권한이 없습니다.");
				return null;
			}
			replyService.updateReComment(replyReply);
			// 변경된 결과를 조회
			item = replyService.selectReComment(replyReply);
			BoardReplyReplyLike boardReplyReplyLike = new BoardReplyReplyLike();
			boardReplyReplyLike.setMemberId(memberId);
			boardReplyReplyLike.setReplyReplyId(item.getReplyReplyId());
			chk = replyLikeService.replyReplyCheck(boardReplyReplyLike);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
			return null;
		}
		
		
		if (chk != 0) {
			item.setChk("Y");
		}
		
		// 본인 것인지 확인
		if (item.getMemberId() == memberId && memberId != 0) {
			item.setChkMemberId("Y");
		}
		
		/** (8) 처리 결과를 JSON으로 출력하기 */
		// 줄바꿈이나 HTML특수문자에 대한 처리
		item.setContent(web.convertHtmlTag(item.getContent()));
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "OK");
		data.put("item", item);
		
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	@RequestMapping(value = "/CommReplyReplyDelete.do", method = RequestMethod.GET)
	public ModelAndView CommReplyReplyDelete(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		web.init();
		
		/** (3) 댓글 번호 받기 */
		int replyReplyId = web.getInt("replyReplyId");
		logger.debug("replyReplyId = " + replyReplyId);
		if (replyReplyId == 0) {
			return web.redirect(null, "댓글 번호가 없습니다.");
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
		
		// 파라미터를 Beans로 묶기
		BoardReplyReply replyReply = new BoardReplyReply();
		replyReply.setReplyReplyId(replyReplyId);
		
		// replyId를 유지하기위한 객체 생성
		BoardReplyReply rI = new BoardReplyReply();
		
		// 로그인 한 경우 현재 회원의 일련번호를 추가한다. ( 비로그인시 0으로 설정됨 )
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo != null) {
			replyReply.setMemberId(loginInfo.getMemberId());
		}
		
		/** (4) 게시물 일련번호를 사용한 데이터 조회 */
		// 회원번호가 일치하는 게시물 수 조회하기
		int replyReplyCount = 0;
		try {
			replyReplyCount = replyService.selectReCommentCountByMemberId(replyReply);
			rI = replyService.selectReplyIdByReplyReply(replyReply);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		if (loginInfo.getGrade().equals("Master")) {
			replyReplyCount = 1;
		}
		
		/** (5) 자신의 글에 대한 요청인지에 대한 여부를 view에 전달 */
		boolean myComment = replyReplyCount > 0;
		logger.debug("myComment = " + myComment);
		model.addAttribute("myComment", myComment);
		
		// 상태유지를 위하여 게시글 일련번호를 View에 전달한다.
		model.addAttribute("replyReplyId", replyReplyId);
		model.addAttribute("replyId", rI.getReplyId());
		
		return new ModelAndView("community/CommunityReplyReplyDeleteModal");
	}
	
	@RequestMapping(value = "/CommReplyReplyDeleteOk.do", method = RequestMethod.POST)
	public String CommReplyReplyDeleteOk(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		web.init();
		
		response.setContentType("application/json");
		
		/** (3) 댓글번호와 비밀번호 받기 */
		int replyId = web.getInt("replyId");
		logger.debug("replyId = " + replyId);
		
		int replyReplyId = web.getInt("replyReplyId");
		logger.debug("replyReplyId = " + replyReplyId);
		
		if (replyReplyId == 0) {
			web.printJsonRt("댓글 번호가 없습니다.");
			return null;
		}
		
		/** (4) 파라미터를 Beans로 묶기 */
		BoardReplyReply replyReply = new BoardReplyReply();
		replyReply.setReplyReplyId(replyReplyId);
		
		/** (5) 데이터 삭제 처리 */
		// 로그인 중이라면 회원일련번호를 Beans에 추가한다.
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo != null) {
			replyReply.setMemberId(loginInfo.getMemberId());
		}
		
		try {
			// 댓글 좋아요 참조관계 해제 및 삭제
			replyService.deleteReplyReplyLike(replyReply);
			// 댓글 삭제
			replyService.deleteReComment(replyReply);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
			return null;
		}
		
		/** (6) 처리 결과를 JSON으로 출력하기 */
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "OK");
		data.put("replyReplyId", replyReplyId);
		data.put("replyId", replyId);
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@RequestMapping(value = "/CommReplyReplyLike.do", method = RequestMethod.POST)
	public String CommReplyReplyLike(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		web.init();
		
		response.setContentType("application/json");
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo == null) {
			web.printJsonRt("권한이 없습니다.");
			return null;
		}
		
		logger.debug("memberId >> " + loginInfo.getMemberId());
		
		int replyId = web.getInt("replyId");
		logger.debug("replyId = " + replyId);
		
		int replyReplyId = web.getInt("replyReplyId"); // 댓글 일련번호 구분을 위한 파라미터
		logger.debug("replyReplyId = " + replyReplyId);
		
		String chk = web.getString("chk");
		logger.debug("chk = " + chk);
		
		int replyReplyLike = web.getInt("replyReplyLike");
		logger.debug("replyReplyLike = " + replyReplyLike);
		
		BoardReplyReply params = new BoardReplyReply();
		params.setReplyReplyId(replyReplyId);
		params.setReplyReplyLike(replyReplyLike);
		
		BoardReplyReply list = null; // 업데이트 후 받을 객체
		
		BoardReplyReplyLike temp = new BoardReplyReplyLike(); // 선택된 게시물을 조회하기 위한 BoardReplyLike 객체 생성
		temp.setReplyReplyId(replyReplyId); // replyId 파라미터 값을 객체에 replyId에 저장
		temp.setMemberId(loginInfo.getMemberId());
		temp.setReplyId(replyId);
		
		try {
			// 타입에 따라 분류하여 증가 및 감소 한 값으로 키 재설정
			if (chk.equals("Y")) {
				if (replyLikeService.replyReplyCheck(temp) == 0) {
					replyLikeService.addBoardReplyReplyLike(temp);
					replyService.editBoardReplyReplyByLikeUp(params);
					list = replyService.selectReComment(params);
				}
			} else if (chk.equals("N")) {
				if (replyLikeService.replyReplyCheck(temp) == 1) {
					replyLikeService.deleteBoardReplyReplyLike(temp);
					replyService.editBoardReplyReplyByLikeDown(params);
					list = replyService.selectReComment(params);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			web.printJsonRt(e.getLocalizedMessage());
			return null;
		}
		
		JSONObject rss = new JSONObject();
		JSONObject item = new JSONObject();
		
		rss.put("rt", "OK");
		item.put("replyReplyLike", list.getReplyReplyLike());
		rss.put("item", item);
		 
		try {
			response.getWriter().print(rss);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
