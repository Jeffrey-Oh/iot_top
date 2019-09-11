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
 * project.java.travel.model.BoardReplyLike; import
 * project.java.travel.model.Member; import
 * project.java.travel.service.ReplyLikeService; import
 * project.java.travel.service.ReplyService; import
 * project.java.travel.service.impl.ReplyLikeServiceImpl; import
 * project.java.travel.service.impl.ReplyServiceImpl; import
 * project.jsp.helper.BaseController; import project.jsp.helper.RegexHelper;
 * import project.jsp.helper.WebHelper;
 * 
 *//**
	 * Servlet implementation class CommunityReplyEditOk
	 */
/*
 * @WebServlet("/community/CommunityReplyEditOk.do") public class
 * CommunityReplyEditOk extends BaseController { private static final long
 * serialVersionUID = -8586584906680668461L;
 * 
 *//** (1) 사용하고자 하는 Helper 객체 선언 */
/*
 * Logger logger; SqlSession sqlSession; WebHelper web; RegexHelper regex;
 * ReplyService replyService; ReplyLikeService boardReplyLikeService;
 * 
 * @Override public String doRun(HttpServletRequest request, HttpServletResponse
 * response) throws ServletException, IOException {
 *//** (2) 페이지 형식 지정 + 사용하고자 하는 Helper + Service 객체 생성 */
/*
 * response.setContentType("application/json");
 * 
 * logger = LogManager.getFormatterLogger(request.getRequestURI()); sqlSession =
 * MyBatisConnectionFactory.getSqlSession(); web =
 * WebHelper.getInstance(request, response); regex = RegexHelper.getInstance();
 * replyService = new ReplyServiceImpl(logger, sqlSession);
 * boardReplyLikeService = new ReplyLikeServiceImpl(logger, sqlSession);
 * 
 *//** (3) 파라미터 받기 */
/*
 * int replyId = web.getInt("replyId"); String replyContent =
 * web.getString("replyContent");
 * 
 * // 회원 일련번호 --> 비 로그인인 경우 0 int memberId = 0;
 * 
 * // 전달된 파라미터는 로그로 확인한다. logger.debug("replyId = " + replyId);
 * logger.debug("replyContent = " + replyContent);
 * 
 *//** (4) 로그인 한 경우 자신의 글이라면 입력하지 않은 정보를 세션 데이터로 대체 */
/*
 * // 소유권 검사 정보 boolean myComment = false;
 * 
 * Member loginInfo = (Member) web.getSession("loginInfo"); if (loginInfo !=
 * null) { try { // 소유권 판정을 위하여 사용하는 임시 객체 BoardReply temp = new BoardReply();
 * temp.setReplyId(replyId); temp.setMemberId(loginInfo.getMemberId());
 * 
 * if (replyService.selectCommentCountByMemberId(temp) > 0) { // 소유권을 의미하는 변수 변경
 * myComment = true; // 입력되지 않은 정보들 갱신 memberId = loginInfo.getMemberId(); } }
 * catch (Exception e) { sqlSession.close();
 * web.printJsonRt(e.getLocalizedMessage()); return null; } }
 * 
 * if (memberId == 0) { sqlSession.close(); web.printJsonRt("로그인이 필요한 서비스입니다.");
 * return null; }
 * 
 * // 전달된 파라미터는 로그로 확인한다. logger.debug("replyId = " + replyId);
 * logger.debug("replyContent = " + replyContent);
 * 
 *//** (5) 입력 받은 파라미터에 대한 유효성 검사 */
/*
 * if (replyId == 0) { sqlSession.close(); web.printJsonRt("댓글 번호가 없습니다.");
 * return null; }
 * 
 * if (!regex.isValue(replyContent)) { sqlSession.close();
 * web.printJsonRt("내용을 입력하세요."); return null; }
 * 
 *//** (6) 입력 받은 파라미터를 Beans로 묶기 */
/*
 * BoardReply reply = new BoardReply(); reply.setReplyId(replyId);
 * reply.setContent(replyContent); reply.setMemberId(memberId);
 * logger.debug("reply >> " + reply.toString());
 * 
 *//** (7) 게시물 변경을 위한 Service 기능을 호출 */
/*
 * BoardReply item = null;
 * 
 * int chk = 0; try { // 자신의 글이 아니라면 비밀번호 검사를 먼저 수행 if (!myComment) {
 * web.printJsonRt("댓글 수정 권한이 없습니다."); return null; }
 * replyService.updateComment(reply); // 변경된 결과를 조회 item =
 * replyService.selectComment(reply); BoardReplyLike boardReplyLike = new
 * BoardReplyLike(); boardReplyLike.setMemberId(memberId);
 * boardReplyLike.setReplyId(item.getReplyId()); chk =
 * boardReplyLikeService.replyCheck(boardReplyLike); } catch (Exception e) {
 * web.printJsonRt(e.getLocalizedMessage()); return null; } finally {
 * sqlSession.close(); }
 * 
 * if (chk != 0) { item.setChk("Y"); }
 * 
 *//** (8) 처리 결과를 JSON으로 출력하기 *//*
								 * // 줄바꿈이나 HTML특수문자에 대한 처리
								 * item.setContent(web.convertHtmlTag(item.getContent()));
								 * 
								 * Map<String, Object> data = new HashMap<String, Object>(); data.put("rt",
								 * "OK"); data.put("item", item);
								 * 
								 * ObjectMapper mapper = new ObjectMapper();
								 * mapper.writeValue(response.getWriter(), data);
								 * 
								 * return null; }
								 * 
								 * }
								 */