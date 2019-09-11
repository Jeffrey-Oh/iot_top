package project.spring.travel.test.controller.community;
//package project.jsp.travel.controller.community;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.ibatis.session.SqlSession;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import project.java.travel.dao.MyBatisConnectionFactory;
//import project.java.travel.model.BoardReplyReply;
//import project.java.travel.model.Member;
//import project.java.travel.service.ReplyService;
//import project.java.travel.service.impl.ReplyServiceImpl;
//import project.jsp.helper.BaseController;
//import project.jsp.helper.RegexHelper;
//import project.jsp.helper.WebHelper;
//
///**
// * Servlet implementation class CommunityReplyReplyInsert
// */
//@WebServlet("/community/CommunityReplyReplyInsert.do")
//public class CommunityReplyReplyInsert extends BaseController {
//	private static final long serialVersionUID = 9046035368165171952L;
//	
//	/** (1) 사용하고자 하는 Helper 객체 선언 */
//	Logger logger;
//	SqlSession sqlSession;
//	WebHelper web;
//	RegexHelper regex;
//	ReplyService replyService;
//
//	@Override
//	public String doRun(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		/** (2) 페이지 형식 지정 + 사용하고자 하는 Helper + Service 객체 생성 */
//		response.setContentType("application/json");
//		
//		logger = LogManager.getFormatterLogger(request.getRequestURI());
//		sqlSession = MyBatisConnectionFactory.getSqlSession();
//		web = WebHelper.getInstance(request, response);
//		regex = RegexHelper.getInstance();
//		replyService = new ReplyServiceImpl(logger, sqlSession);
//		
//		/** (3) 파라미터 받기 */
//		int replyId = web.getInt("replyId");
//		String userName = web.getString("userName");
//		String content = web.getString("content");
//		int memberId = 0;
//		
//		Member loginInfo = (Member) web.getSession("loginInfo");
//		if (loginInfo != null) {
//			userName = loginInfo.getUserName();
//			memberId = loginInfo.getMemberId();
//		}
//
//		logger.debug("replyId = " + replyId);
//		logger.debug("userName = " + userName);
//		logger.debug("content = " + content);
//		logger.debug("memberId = " + memberId);
//		
//		/** (4) 입력 받은 파라미터에 대한 유효성 검사 */
//		if (replyId == 0) {
//			sqlSession.close();
//			web.printJsonRt("게시물 일련번호가 없습니다");
//			return null;
//		}
//		
//		if (memberId == 0) {
//			sqlSession.close();
//			web.printJsonRt("로그인 필요한 서비스 입니다.");
//			return null;
//		}
//		
//		if (!regex.isValue(userName)) {
//			sqlSession.close();
//			web.printJsonRt("로그인 필요한 서비스 입니다.");
//			return null;
//		}
//		
//		if (!regex.isValue(content)) {
//			sqlSession.close();
//			web.printJsonRt("내용을 입력하세요.");
//			return null;
//		}
//		
//		/** (5) 입력 받은 파라미터를 Beans로 묶기 */
//		BoardReplyReply replyReply = new BoardReplyReply();
//		replyReply.setReplyId(replyId);
//		replyReply.setUserName(userName);
//		replyReply.setContent(content);
//		replyReply.setMemberId(memberId);
//		logger.debug("replyReply >> " + replyReply.toString());
//		
//		/** (6) Service를 통한 댓글 저장 */
//		BoardReplyReply item = null;
//		
//		try {
//			replyService.insertReComment(replyReply);
//			item = replyService.selectReComment(replyReply);
//		} catch (Exception e) {
//			web.printJsonRt(e.getLocalizedMessage());
//			return null;
//		} finally {
//			sqlSession.close();
//		}
//		
//		/** (7) 처리 결과를 JSON으로 출력하기 */
//		// 줄바꿈이나 HTML특수문자에 대한 처리
//		item.setUserName(web.convertHtmlTag(item.getUserName()));
//		item.setContent(web.convertHtmlTag(item.getContent()));
//		
//		Map<String, Object> data = new HashMap<String, Object>();
//		data.put("rt", "OK");
//		data.put("item", item);
//		
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.writeValue(response.getWriter(), data);
//		
//		return null;
//	}
//}
