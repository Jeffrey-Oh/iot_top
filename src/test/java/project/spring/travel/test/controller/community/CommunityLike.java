package project.spring.travel.test.controller.community;
/*
 * package project.jsp.travel.controller.community;
 * 
 * import java.io.IOException;
 * 
 * import javax.servlet.ServletException; import
 * javax.servlet.annotation.WebServlet; import
 * javax.servlet.http.HttpServletRequest; import
 * javax.servlet.http.HttpServletResponse;
 * 
 * import org.apache.ibatis.session.SqlSession; import
 * org.apache.logging.log4j.LogManager; import org.apache.logging.log4j.Logger;
 * import org.json.JSONObject;
 * 
 * import project.java.travel.dao.MyBatisConnectionFactory; import
 * project.java.travel.model.BoardLike; import
 * project.java.travel.model.BoardList; import project.java.travel.model.Member;
 * import project.java.travel.service.BoardLikeService; import
 * project.java.travel.service.BoardService; import
 * project.java.travel.service.impl.BoardLikeServiceImpl; import
 * project.java.travel.service.impl.BoardServiceImpl; import
 * project.jsp.helper.BaseController; import project.jsp.helper.RegexHelper;
 * import project.jsp.helper.WebHelper;
 * 
 *//**
	 * Servlet implementation class CommunityLike
	 */
/*
 * @WebServlet("/community/CommunityLike.do") public class CommunityLike extends
 * BaseController { private static final long serialVersionUID =
 * -1293161885482861894L;
 * 
 * WebHelper web; Logger logger; SqlSession sqlSession; RegexHelper regex;
 * BoardService boardService; BoardLikeService boardLikeService;
 * 
 * @Override public String doRun(HttpServletRequest request, HttpServletResponse
 * response) throws ServletException, IOException { web =
 * WebHelper.getInstance(request, response); logger =
 * LogManager.getFormatterLogger("CommunityLike.do"); // CommunityLike.jsp 로그 설정
 * regex = RegexHelper.getInstance(); sqlSession =
 * MyBatisConnectionFactory.getSqlSession(); // MyBatis 설정 boardService = new
 * BoardServiceImpl(logger, sqlSession); // Service 설정 boardLikeService = new
 * BoardLikeServiceImpl(logger, sqlSession);
 * 
 *//** 컨텐츠 타입 명시 *//*
					 * response.setContentType("application/json");
					 * 
					 * Member loginInfo = (Member) web.getSession("loginInfo"); if (loginInfo ==
					 * null) { sqlSession.close(); web.printJsonRt("권한이 없습니다."); return null; }
					 * 
					 * logger.debug("memberId >> " + loginInfo.getMemberId());
					 * 
					 * int boardId = web.getInt("boardId"); // 게시물 일련번호 구분을 위한 파라미터
					 * logger.debug("boardId = " + boardId);
					 * 
					 * String chk = web.getString("chk"); logger.debug("chk = " + chk);
					 * 
					 * int boardLikeCnt = Integer.parseInt(web.getString("boardLike")); // JSON에 넣기
					 * 전 String으로 파라미터를 받는다 logger.debug("boardLikeCnt = " + boardLikeCnt);
					 * 
					 * BoardList params = new BoardList(); params.setBoardId(boardId);
					 * params.setBoardLike(boardLikeCnt);
					 * 
					 * BoardList list = null; // 업데이트 후 받을 객체
					 * 
					 * BoardLike temp = new BoardLike(); // 선택된 게시물을 조회하기 위한 BoardList 객체 생성
					 * temp.setBoardId(boardId); // boardId 파라미터 값을 객체에 boardId에 저장
					 * temp.setMemberId(loginInfo.getMemberId());
					 * 
					 * try { // 타입에 따라 분류하여 증가 및 감소 한 값으로 키 재설정 if (chk.equals("Y")) { if
					 * (boardLikeService.selectBoardLike(temp) == 0) {
					 * boardLikeService.addBoardLike(temp); boardService.editBoardByLikeUp(params);
					 * list = boardService.getItem(params); } } else if (chk.equals("N")) { if
					 * (boardLikeService.selectBoardLike(temp) == 1) {
					 * boardLikeService.deleteBoardLike(temp);
					 * boardService.editBoardByLikeDown(params); list =
					 * boardService.getItem(params); } } } catch (Exception e) {
					 * e.printStackTrace(); web.printJsonRt(e.getLocalizedMessage()); return null; }
					 * finally { sqlSession.close(); }
					 * 
					 * 
					 * JSONObject rss = new JSONObject(); JSONObject item = new JSONObject();
					 * 
					 * rss.put("rt", "OK"); item.put("boardLike", list.getBoardLike());
					 * rss.put("item", item);
					 * 
					 * 
					 * response.getWriter().print(rss);
					 * 
					 * return null; }
					 * 
					 * }
					 */