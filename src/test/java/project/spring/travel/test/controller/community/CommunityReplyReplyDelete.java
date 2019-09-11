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
//import project.java.travel.model.Member;
//import project.java.travel.service.ReplyService;
//import project.java.travel.service.impl.ReplyServiceImpl;
//import project.jsp.helper.BaseController;
//import project.jsp.helper.WebHelper;
//
///**
// * Servlet implementation class CommunityReplyReplyDelete
// */
//@WebServlet("/community/CommunityReplyReplyDelete.do")
//public class CommunityReplyReplyDelete extends BaseController {
//	private static final long serialVersionUID = 4435431052910152756L;
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
//		/** (3) 댓글 번호 받기 */
//		int replyReplyId = web.getInt("replyReplyId");
//		logger.debug("replyReplyId = " + replyReplyId);
//		if (replyReplyId == 0) {
//			sqlSession.close();
//			web.redirect(null, "댓글 번호가 없습니다.");
//			return null;
//		}
//		
//		// 파라미터를 Beans로 묶기
//		BoardReplyReply replyReply = new BoardReplyReply();
//		replyReply.setReplyReplyId(replyReplyId);
//		
//		// 로그인 한 경우 현재 회원의 일련번호를 추가한다. ( 비로그인시 0으로 설정됨 )
//		Member loginInfo = (Member) web.getSession("loginInfo");
//		if (loginInfo != null) {
//			replyReply.setMemberId(loginInfo.getMemberId());
//		}
//		
//		/** (4) 게시물 일련번호를 사용한 데이터 조회 */
//		// 회원번호가 일치하는 게시물 수 조회하기
//		int replyReplyCount = 0;
//		try {
//			replyReplyCount = replyService.selectReCommentCountByMemberId(replyReply);
//		} catch (Exception e) {
//			web.redirect(null, e.getLocalizedMessage());
//			return null;
//		} finally {
//			sqlSession.close();
//		}
//		
//		/** (5) 자신의 글에 대한 요청인지에 대한 여부를 view에 전달 */
//		boolean myComment = replyReplyCount > 0;
//		logger.debug("myComment = " + myComment);
//		request.setAttribute("myComment", myComment);
//		
//		// 상태유지를 위하여 게시글 일련번호를 View에 전달한다.
//		request.setAttribute("replyReplyId", replyReplyId);
//		
//		return "community/CommunityReplyReplyDeleteModal";
//	}
//
//}
