package project.spring.travel.test.controller.community;
//package project.jsp.travel.controller.community;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.ibatis.session.SqlSession;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.json.JSONObject;
//
//import project.java.travel.dao.MyBatisConnectionFactory;
//import project.java.travel.model.BoardReply;
//import project.java.travel.model.BoardReplyLike;
//import project.java.travel.model.Member;
//import project.java.travel.service.ReplyLikeService;
//import project.java.travel.service.ReplyService;
//import project.java.travel.service.impl.ReplyLikeServiceImpl;
//import project.java.travel.service.impl.ReplyServiceImpl;
//import project.jsp.helper.BaseController;
//import project.jsp.helper.RegexHelper;
//import project.jsp.helper.WebHelper;
//
///**
// * Servlet implementation class CommunityLike
// */
//@WebServlet("/community/CommunityReplyLike.do")
//public class CommunityReplyLike extends BaseController {
//	private static final long serialVersionUID = -2366045713217556343L;
//	
//	WebHelper web;
//	Logger logger;
//	SqlSession sqlSession;
//	RegexHelper regex;
//	ReplyService replyService;
//	ReplyLikeService replyLikeService;
//
//	@Override
//	public String doRun(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		web = WebHelper.getInstance(request, response);
//		logger = LogManager.getFormatterLogger("CommunityLike.do"); // CommunityLike.jsp 로그 설정
//		regex = RegexHelper.getInstance();
//		sqlSession = MyBatisConnectionFactory.getSqlSession(); // MyBatis 설정
//		replyService = new ReplyServiceImpl(logger, sqlSession); // Service  설정
//		replyLikeService = new ReplyLikeServiceImpl(logger, sqlSession);
//		
//		/** 컨텐츠 타입 명시 */
//		response.setContentType("application/json");
//		
//		Member loginInfo = (Member) web.getSession("loginInfo");
//		if (loginInfo == null) {
//			sqlSession.close();
//			web.printJsonRt("권한이 없습니다.");
//			return null;
//		}
//		
//		logger.debug("memberId >> " + loginInfo.getMemberId());
//		
//		int replyId = web.getInt("replyId"); // 댓글 일련번호 구분을 위한 파라미터
//		logger.debug("replyId = " + replyId);
//		
//		String chk = web.getString("chk");
//		logger.debug("chk = " + chk);
//		
//		int replyLikeCnt = Integer.parseInt(web.getString("replyLike"));
//		logger.debug("replyLikeCnt = " + replyLikeCnt);
//		
//		BoardReply params = new BoardReply();
//		params.setReplyId(replyId);
//		params.setReplyLike(replyLikeCnt);
//		
//		BoardReply list = null; // 업데이트 후 받을 객체
//		
//		BoardReplyLike temp = new BoardReplyLike(); // 선택된 게시물을 조회하기 위한 BoardReplyLike 객체 생성
//		temp.setReplyId(replyId); // replyId 파라미터 값을 객체에 replyId에 저장
//		temp.setMemberId(loginInfo.getMemberId());
//		
//		try {
//			// 타입에 따라 분류하여 증가 및 감소 한 값으로 키 재설정
//			if (chk.equals("Y")) {
//				if (replyLikeService.replyCheck(temp) == 0) {
//					replyLikeService.addBoardReplyLike(temp);
//					replyService.editBoardReplyByLikeUp(params);
//					list = replyService.selectComment(params);
//				}
//			} else if (chk.equals("N")) {
//				if (replyLikeService.replyCheck(temp) == 1) {
//					replyLikeService.deleteBoardReplyLike(temp);
//					replyService.editBoardReplyByLikeDown(params);
//					list = replyService.selectComment(params);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			web.printJsonRt(e.getLocalizedMessage());
//			return null;
//		}
//		
//		sqlSession.close();
//		
//		JSONObject rss = new JSONObject();
//		JSONObject item = new JSONObject();
//		
//		rss.put("rt", "OK");
//		item.put("replyLike", list.getReplyLike());
//		rss.put("item", item);
//		 
//		response.getWriter().print(rss);
//		 
//		return null;
//	}
//
//}
