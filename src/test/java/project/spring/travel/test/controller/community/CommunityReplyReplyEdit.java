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
//
//import project.java.travel.dao.MyBatisConnectionFactory;
//import project.java.travel.model.BoardReplyReply;
//import project.java.travel.service.ReplyService;
//import project.java.travel.service.impl.ReplyServiceImpl;
//import project.jsp.helper.BaseController;
//import project.jsp.helper.WebHelper;
//
///**
// * Servlet implementation class CommunityReplyReplyEdit
// */
//@WebServlet("/community/CommunityReplyReplyEdit.do")
//public class CommunityReplyReplyEdit extends BaseController {
//	private static final long serialVersionUID = 2661940540423876867L;
//	
//	/** (1) 사용하고자 하는 Helper 객체 선언 */
//	Logger logger;
//	SqlSession sqlSession;
//	WebHelper web;
//	ReplyService replyService;
//
//	@Override
//	public String doRun(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		/** (2) 사용하고자 하는 Helper + Service 객체 생성 */
//		logger = LogManager.getFormatterLogger(request.getRequestURI());
//		sqlSession = MyBatisConnectionFactory.getSqlSession();
//		web = WebHelper.getInstance(request, response);
//		replyService = new ReplyServiceImpl(logger, sqlSession);
//		
//		/** (3) 글 번호 파라미터 받기 */
//		int replyReplyId = web.getInt("replyReplyId");
//		if (replyReplyId == 0) {
//			sqlSession.close();
//			web.redirect(null, "댓글 번호가 지정되지 않았습니다.");
//			return null;
//		}
//		
//		// 파라미터를 Beans로 묶기
//		BoardReplyReply replyReply = new BoardReplyReply();
//		replyReply.setReplyReplyId(replyReplyId);
//		
//		/** (4) 댓글 일련번호를 사용한 데이터 조회 */
//		// 지금 읽고 있는 댓글이 저장될 객체
//		BoardReplyReply readReplyReply = null;
//		
//		try {
//			readReplyReply = replyService.selectReComment(replyReply);
//		} catch (Exception e) {
//			web.redirect(null, e.getLocalizedMessage());
//			return null;
//		} finally {
//			sqlSession.close();
//		}
//		
//		/** (5) 읽은 데이터를 View에게 전달한다. */
//		request.setAttribute("readReplyReply", readReplyReply);
//		
//		return "community/CommunityReplyReplyEditModal";
//	}
//
//}
