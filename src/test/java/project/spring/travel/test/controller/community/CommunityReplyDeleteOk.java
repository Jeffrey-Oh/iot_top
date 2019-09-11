package project.spring.travel.test.controller.community;
/*
 * package project.jsp.travel.controller.community;
 * 
 * import java.io.IOException; import java.util.HashMap; import java.util.Map;
 * 
 * import javax.servlet.ServletException; import
 * javax.servlet.annotation.WebServlet; import
 * javax.servlet.http.HttpServletRequest; import
 * javax.servlet.http.HttpServletResponse;
 * 
 * import org.apache.ibatis.session.SqlSession; import
 * org.apache.logging.log4j.LogManager; import org.apache.logging.log4j.Logger;
 * 
 * import com.fasterxml.jackson.databind.ObjectMapper;
 * 
 * import project.java.travel.dao.MyBatisConnectionFactory; import
 * project.java.travel.model.BoardReply; import
 * project.java.travel.model.Member; import
 * project.java.travel.service.ReplyService; import
 * project.java.travel.service.impl.ReplyServiceImpl; import
 * project.jsp.helper.BaseController; import project.jsp.helper.WebHelper;
 * 
 * 
 *//**
	 * Servlet implementation class CommentDeleteOk
	 */
/*
 * @WebServlet("/community/CommunityReplyDeleteOk.do") public class
 * CommunityReplyDeleteOk extends BaseController { private static final long
 * serialVersionUID = -1295916696132526990L;
 * 
 *//** (1) 사용하고자 하는 Helper 객체 선언 */
/*
 * Logger logger; SqlSession sqlSession; WebHelper web; ReplyService
 * replyService;
 * 
 * @Override public String doRun(HttpServletRequest request, HttpServletResponse
 * response) throws ServletException, IOException {
 *//** (2) 페이지 형식 지정 + 사용하고자 하는 Helper + Service 객체 생성 */
/*
 * response.setContentType("application/json");
 * 
 * logger = LogManager.getFormatterLogger(request.getRequestURI()); sqlSession =
 * MyBatisConnectionFactory.getSqlSession(); web =
 * WebHelper.getInstance(request, response); replyService = new
 * ReplyServiceImpl(logger, sqlSession);
 * 
 *//** (3) 댓글번호와 비밀번호 받기 */
/*
 * int replyId = web.getInt("replyId"); logger.debug("replyId = " + replyId);
 * 
 * if (replyId == 0) { sqlSession.close(); web.printJsonRt("댓글 번호가 없습니다.");
 * return null; }
 * 
 *//** (4) 파라미터를 Beans로 묶기 */
/*
 * BoardReply reply = new BoardReply(); reply.setReplyId(replyId);
 * 
 *//** (5) 데이터 삭제 처리 */
/*
 * // 로그인 중이라면 회원일련번호를 Beans에 추가한다. Member loginInfo = (Member)
 * web.getSession("loginInfo"); if (loginInfo != null) {
 * reply.setMemberId(loginInfo.getMemberId()); }
 * 
 * try { // 댓글 좋아요 참조관계 해제 및 삭제 replyService.deleteReplyLike(reply); // 댓글 삭제
 * replyService.deleteComment(reply); } catch (Exception e) {
 * web.printJsonRt(e.getLocalizedMessage()); return null; } finally {
 * sqlSession.close(); }
 * 
 *//** (6) 처리 결과를 JSON으로 출력하기 *//*
								 * Map<String, Object> data = new HashMap<String, Object>(); data.put("rt",
								 * "OK"); data.put("replyId", replyId);
								 * 
								 * ObjectMapper mapper = new ObjectMapper();
								 * mapper.writeValue(response.getWriter(), data);
								 * 
								 * return null; }
								 * 
								 * }
								 */