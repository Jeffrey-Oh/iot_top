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

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import project.spring.helper.RegexHelper;
import project.spring.helper.WebHelper;
import project.spring.travel.model.BoardReply;
import project.spring.travel.model.BoardReplyLike;
import project.spring.travel.model.BoardReplyReply;
import project.spring.travel.model.HotKeyword;
import project.spring.travel.model.Member;
import project.spring.travel.service.HotKeywordService;
import project.spring.travel.service.ReplyLikeService;
import project.spring.travel.service.ReplyService;

/**
 * Servlet implementation class CommunityReply
 */
@Controller
public class CommunityReply {
	
	/** (1) 사용하고자 하는 Helper 객체 선언 */
	Logger logger = LoggerFactory.getLogger(CommunityReply.class);
	
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
	
	@RequestMapping(value = "/CommReply.do", method = RequestMethod.GET)
	public String CommReply(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		/** (2) 페이지 형식 지정 + 사용하고자 하는 Helper + Service 객체 생성 */
		
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
		int boardId = web.getInt("boardId");
		logger.debug("boardId = " + boardId);
		
		int limitStart = Integer.parseInt(web.getString("limitStart", "0")); // 현재 페이지
		logger.debug("limitStart = " + limitStart);
		
		String limitLast = web.getString("limitLast", "");
		logger.debug("limitLast = " + limitLast);
		
		/** (4) 입력 받은 파라미터에 대한 유효성 검사 */
		// 댓글이 소속될 게시물의 일련번호
		if (boardId == 0) {
			web.printJsonRt("게시물 일련번호가 없습니다.");
			return null;
		}
		
		/** (5) 입력 받은 파라미터를 Beans로 묶기 */
		BoardReply reply = new BoardReply();
		reply.setBoardId(boardId);
		
		int totalCount = 0;
		
		try {
			totalCount = replyService.selectCount(reply); // 댓글 전체 개수
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
			return null;
		}
		
		logger.debug("totalCount = " + totalCount);
		
		int firstRange = totalCount / 10; // 처음 10의자리 수
		logger.debug("firstRange = " + firstRange);
		int secondRange = totalCount % 10; // 처음 1의자리 수
		logger.debug("secondRange = " + secondRange);
		
		if (limitLast.equals("N")) {
			firstRange = limitStart / 10;
			secondRange = limitStart % 10;
			limitStart = (firstRange - 1) * 10 + secondRange;
			reply.setLimitStart(limitStart); // 아직 불러올 댓글이 10개 이상인 경우
			reply.setListCount(10);
		}
		
		if (limitStart == 0) {
			limitStart = (firstRange - 1) * 10 + secondRange;
			if (limitStart < 0) {
				limitStart = 0;
			}
			logger.debug("limitStartNew = " + limitStart);
			reply.setLimitStart(limitStart); // 처음 불러올 댓글이 10개 이상인 경우
			reply.setListCount(10);
		}
		
		if (limitLast.equals("Y")) {
			reply.setLimitStart(0); // 불러올 댓글이 10개 미만인 경우 나머지를 다 부름
			reply.setListCount(limitStart);
			limitStart = 0;
		}
		
		/** (6) Service를 통한 댓글 목록 조회 */
		List<BoardReply> item = null;
		try {
			item = replyService.selectCommentList(reply);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
			return null;
		}
		
		/** (7) 처리 결과를 JSON으로 출력하기 */
		// 줄바꿈이나 HTML특수문자에 대한 처리 및 좋아요 현황 설정
		int chk = 0;
		for (int i=0; i<item.size(); i++) {
			BoardReply temp = item.get(i);
			
			BoardReplyLike boardReplyLike = new BoardReplyLike();
			boardReplyLike.setReplyId(temp.getReplyId());
			boardReplyLike.setMemberId(memberId);
			
			temp.setContent(web.convertHtmlTag(temp.getContent()));
			try {
				chk = replyLikeService.replyCheck(boardReplyLike);
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
			
		}
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "OK");
		data.put("item", item);
		data.put("limitStart", limitStart);
		data.put("limitLast", limitLast);
		data.put("tc", totalCount);
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@RequestMapping(value = "/CommReplyInsert.do", method = RequestMethod.POST)
	public String CommReplyInsert(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		web.init();
		
		response.setContentType("application/json");
		
		/** (3) 파라미터 받기 */
		int boardId = (int) web.getSession("boardId");
		String content = web.getString("content");
		int memberId = 0;
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo != null) {
			memberId = loginInfo.getMemberId();
		}

		logger.debug("boardId = " + boardId);
		logger.debug("content = " + content);
		logger.debug("memberId = " + memberId);
		
		/** (4) 입력 받은 파라미터에 대한 유효성 검사 */
		if (boardId == 0) {
			web.printJsonRt("게시물 일련번호가 없습니다");
			return null;
		}
		
		if (memberId == 0) {
			web.printJsonRt("로그인 필요한 서비스 입니다.");
			return null;
		}
		
		if (!regex.isValue(content)) {
			web.printJsonRt("내용을 입력하세요.");
			return null;
		}
		
		/** (5) 입력 받은 파라미터를 Beans로 묶기 */
		BoardReply comment = new BoardReply();
		comment.setBoardId(boardId);
		comment.setUserName(loginInfo.getUserName());
		comment.setContent(content);
		comment.setMemberId(memberId);
		logger.debug("comment >> " + comment.toString());
		
		/** (6) Service를 통한 댓글 저장 */
		BoardReply item = null;
		
		try {
			replyService.insertComment(comment);
			item = replyService.selectComment(comment);
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
	
	@RequestMapping(value = "/CommReplyEdit.do", method = RequestMethod.GET)
	public ModelAndView CommReplyEdit(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		web.init();
		
		/** (3) 글 번호 파라미터 받기 */
		int replyId = web.getInt("replyId");
		if (replyId == 0) {
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
		BoardReply reply = new BoardReply();
		reply.setReplyId(replyId);
		
		/** (4) 댓글 일련번호를 사용한 데이터 조회 */
		// 지금 읽고 있는 댓글이 저장될 객체
		BoardReply readReply = null;
		
		try {
			readReply = replyService.selectComment(reply);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		/** (5) 읽은 데이터를 View에게 전달한다. */
		model.addAttribute("readReply", readReply);
		
		
		return new ModelAndView("community/CommunityReplyEditModal");
	}
	@RequestMapping(value = "/CommReplyEditOk.do", method = RequestMethod.POST)
	public String CommReplyEditOk(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		web.init();
		
		response.setContentType("application/json");
		
		/** (3) 파라미터 받기 */
		int replyId = web.getInt("replyId");
		String replyContent = web.getString("replyContent");
		
		// 회원 일련번호 --> 비 로그인인 경우 0
		int memberId = 0;
		
		// 전달된 파라미터는 로그로 확인한다.
		logger.debug("replyId = " + replyId);
		logger.debug("replyContent = " + replyContent);
		
		/** (4) 로그인 한 경우 자신의 글이라면 입력하지 않은 정보를 세션 데이터로 대체 */
		// 소유권 검사 정보
		boolean myComment = false;
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo != null) {
			try {
				// 소유권 판정을 위하여 사용하는 임시 객체
				BoardReply temp = new BoardReply();
				temp.setReplyId(replyId);
				temp.setMemberId(loginInfo.getMemberId());
				
				if (replyService.selectCommentCountByMemberId(temp) > 0) {
					// 소유권을 의미하는 변수 변경
					myComment = true;
					// 입력되지 않은 정보들 갱신
					memberId = loginInfo.getMemberId();
				}
			} catch (Exception e) {
				web.printJsonRt(e.getLocalizedMessage());
				return null;
			}
		}
		
		if (memberId == 0) {
			web.printJsonRt("로그인이 필요한 서비스입니다.");
			return null;
		}
		
		// 전달된 파라미터는 로그로 확인한다.
		logger.debug("replyId = " + replyId);
		logger.debug("replyContent = " + replyContent);
		
		/** (5) 입력 받은 파라미터에 대한 유효성 검사 */
		if (replyId == 0) {
			web.printJsonRt("댓글 번호가 없습니다.");
			return null;
		}
		
		if (!regex.isValue(replyContent)) {
			web.printJsonRt("내용을 입력하세요.");
			return null;
		}
		
		/** (6) 입력 받은 파라미터를 Beans로 묶기 */
		BoardReply reply = new BoardReply();
		reply.setReplyId(replyId);
		reply.setContent(replyContent);
		reply.setMemberId(memberId);
		logger.debug("reply >> " + reply.toString());
		
		/** (7) 게시물 변경을 위한 Service 기능을 호출 */
		BoardReply item = null;
		
		int chk = 0;
		try {
			// 자신의 글이 아니라면 비밀번호 검사를 먼저 수행
			if (!myComment) {
				web.printJsonRt("댓글 수정 권한이 없습니다.");
				return null;
			}
			replyService.updateComment(reply);
			// 변경된 결과를 조회
			item = replyService.selectComment(reply);
			BoardReplyLike boardReplyLike = new BoardReplyLike();
			boardReplyLike.setMemberId(memberId);
			boardReplyLike.setReplyId(item.getReplyId());
			chk = replyLikeService.replyCheck(boardReplyLike);
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
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	@RequestMapping(value = "/CommReplyDelete.do", method = RequestMethod.GET)
	public ModelAndView CommReplyDelete(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		web.init();
		
		/** (3) 댓글 번호 받기 */
		int replyId = web.getInt("replyId");
		logger.debug("replyId = " + replyId);
		if (replyId == 0) {
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
		BoardReply reply = new BoardReply();
		reply.setReplyId(replyId);
		
		// 로그인 한 경우 현재 회원의 일련번호를 추가한다. ( 비로그인시 0으로 설정됨 )
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo != null) {
			reply.setMemberId(loginInfo.getMemberId());
		}
		
		/** (4) 게시물 일련번호를 사용한 데이터 조회 */
		// 회원번호가 일치하는 게시물 수 조회하기
		int replyCount = 0;
		try {
			replyCount = replyService.selectCommentCountByMemberId(reply);
		} catch (Exception e) {
			return web.redirect(null, e.getLocalizedMessage());
		}
		
		if (loginInfo.getGrade().equals("Master")) {
			replyCount = 1;
		}
		
		/** (5) 자신의 글에 대한 요청인지에 대한 여부를 view에 전달 */
		boolean myComment = replyCount > 0;
		logger.debug("myComment = " + myComment);
		model.addAttribute("myComment", myComment);
		
		// 상태유지를 위하여 게시글 일련번호를 View에 전달한다.
		model.addAttribute("replyId", replyId);
		
		return new ModelAndView("community/CommunityReplyDeleteModal");
	}
	
	@RequestMapping(value = "/CommReplyDeleteOk.do", method = RequestMethod.POST)
	public String CommReplyDeleteOk(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		web.init();
		
		response.setContentType("application/json");
		
		/** (3) 댓글번호와 비밀번호 받기 */
		int replyId = web.getInt("replyId");
		logger.debug("replyId = " + replyId);
		
		if (replyId == 0) {
			web.printJsonRt("댓글 번호가 없습니다.");
			return null;
		}
		
		/** (4) 파라미터를 Beans로 묶기 */
		BoardReply reply = new BoardReply();
		reply.setReplyId(replyId);
		
		BoardReplyReply replyReply = new BoardReplyReply();
		replyReply.setReplyId(replyId);
		
		/** (5) 데이터 삭제 처리 */
		// 로그인 중이라면 회원일련번호를 Beans에 추가한다.
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo != null) {
			reply.setMemberId(loginInfo.getMemberId());
		}
		
		try {
			// 대댓글 좋아요 참조관계 해제 및 삭제
			replyService.deleteReplyReplyLike(replyReply);
			// 대댓글 삭제
			replyService.deleteReComment(replyReply);
			// 댓글 좋아요 참조관계 해제 및 삭제
			replyService.deleteReplyLike(reply);
			// 댓글 삭제
			replyService.deleteComment(reply);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
			return null;
		}
		
		/** (6) 처리 결과를 JSON으로 출력하기 */
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "OK");
		data.put("replyId", replyId);
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@RequestMapping(value = "/CommReplyLike.do", method = RequestMethod.POST)
	public String CommReplyLike(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		web.init();
		
		response.setContentType("application/json");
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo == null) {
			web.printJsonRt("권한이 없습니다.");
			return null;
		}
		
		logger.debug("memberId >> " + loginInfo.getMemberId());
		
		int replyId = web.getInt("replyId"); // 댓글 일련번호 구분을 위한 파라미터
		logger.debug("replyId = " + replyId);
		
		String chk = web.getString("chk");
		logger.debug("chk = " + chk);
		
		int replyLikeCnt = Integer.parseInt(web.getString("replyLike"));
		logger.debug("replyLikeCnt = " + replyLikeCnt);
		
		BoardReply params = new BoardReply();
		params.setReplyId(replyId);
		params.setReplyLike(replyLikeCnt);
		
		BoardReply list = null; // 업데이트 후 받을 객체
		
		BoardReplyLike temp = new BoardReplyLike(); // 선택된 게시물을 조회하기 위한 BoardReplyLike 객체 생성
		temp.setReplyId(replyId); // replyId 파라미터 값을 객체에 replyId에 저장
		temp.setMemberId(loginInfo.getMemberId());
		
		try {
			// 타입에 따라 분류하여 증가 및 감소 한 값으로 키 재설정
			if (chk.equals("Y")) {
				if (replyLikeService.replyCheck(temp) == 0) {
					replyLikeService.addBoardReplyLike(temp);
					replyService.editBoardReplyByLikeUp(params);
					list = replyService.selectComment(params);
				}
			} else if (chk.equals("N")) {
				if (replyLikeService.replyCheck(temp) == 1) {
					replyLikeService.deleteBoardReplyLike(temp);
					replyService.editBoardReplyByLikeDown(params);
					list = replyService.selectComment(params);
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
		item.put("replyLike", list.getReplyLike());
		rss.put("item", item);
		 
		try {
			response.getWriter().print(rss);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
