package project.spring.travel.test.controller.community;
/*
 * package project.jsp.travel.controller.community;
 * 
 * import java.io.IOException; import java.util.HashMap; import java.util.List;
 * import java.util.Map;
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
 * project.java.travel.model.BoardList; import
 * project.java.travel.model.BoardReply; import project.java.travel.model.File;
 * import project.java.travel.model.Member; import
 * project.java.travel.service.BoardService; import
 * project.java.travel.service.FileService; import
 * project.java.travel.service.ReplyService; import
 * project.java.travel.service.impl.BoardServiceImpl; import
 * project.java.travel.service.impl.FileServiceImpl; import
 * project.java.travel.service.impl.ReplyServiceImpl; import
 * project.jsp.helper.BaseController; import project.jsp.helper.RegexHelper;
 * import project.jsp.helper.UploadHelper; import project.jsp.helper.WebHelper;
 * 
 *//**
	 * Servlet implementation class CommunityDeleteOk
	 */
/*
 * 
 * @WebServlet("/community/CommunityDeleteOk.do") public class CommunityDeleteOk
 * extends BaseController { private static final long serialVersionUID =
 * 9195762008770605975L;
 * 
 * WebHelper web; Logger logger; RegexHelper regex; UploadHelper upload;
 * SqlSession sqlSession; BoardService boardService; ReplyService replyService;
 * FileService fileService;
 * 
 * @Override public String doRun(HttpServletRequest request, HttpServletResponse
 * response) throws ServletException, IOException { web =
 * WebHelper.getInstance(request, response);
 * 
 * logger = LogManager.getFormatterLogger("CommunityDeleteOk.do"); //
 * CommunityDeleteOk.jsp 로그 설정
 * 
 * sqlSession = MyBatisConnectionFactory.getSqlSession(); // Mybatis 설정
 * 
 * boardService = new BoardServiceImpl(logger, sqlSession); // Service 설정
 * 
 * replyService = new ReplyServiceImpl(logger, sqlSession);
 * 
 * fileService = new FileServiceImpl(logger, sqlSession);
 * 
 * regex = RegexHelper.getInstance();
 * 
 * upload = UploadHelper.getInstance();
 * 
 *//** ===== 악의적인 접근에 대한 방어 코드 */
/*
 * 
 * String referer = request.getHeader("referer"); if(referer == null) { // 정상적인
 * 접근 방법이 아닐 경우 sqlSession.close(); logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " +
 * web.getClientIP()); web.redirect(web.getRootPath() + "/error/error_page.do",
 * null); return null; } else { if(regex.isIndexCheck(referer,
 * "CommunityView.do")) { // 거쳐야할 절차를 건너뛴 경우 sqlSession.close();
 * logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
 * web.redirect(web.getRootPath() + "/error/error_page.do", null); return null;
 * } }
 * 
 * Member loginInfo = (Member) web.getSession("loginInfo"); if (loginInfo ==
 * null) { sqlSession.close(); web.redirect(web.getRootPath() +
 * "community/CommunityIndex.do", "삭제 권한이 없습니다."); return null; }
 * 
 * int boardId = Integer.parseInt(web.getString("boardId")); // 조건값인 게시물 일련번호
 * 파라미터 logger.debug("boardId = " + boardId);
 * 
 * String category = web.getString("category"); logger.debug("category = " +
 * category);
 * 
 * if (boardId == 0) { sqlSession.close(); web.redirect(null, "글 번호가 없습니다.");
 * return null; }
 * 
 * String userName = web.getString("userName"); logger.debug("userName = " +
 * userName);
 * 
 * BoardList item = new BoardList(); // 삭제될 게시물 일련번호를 설정하기 위한 BoardList 객체 생성
 * item.setBoardId(boardId); // boardId 파라미터 값을 객체 boardId에 저장
 * item.setCategory(category); item.setMemberId(loginInfo.getMemberId());
 * 
 * File file = new File(); file.setBoardId(boardId);
 * 
 * // 게시물에 속한 댓글 삭제를 위해서 생성 BoardReply reply = new BoardReply();
 * reply.setBoardId(boardId); reply.setMemberId(loginInfo.getMemberId());
 * 
 * List<File> fileList = null; // 게시물에 속한 파일 목록에 대한 조회결과
 * 
 * try { if (boardService.selectDocumentCountByMemberId(item) < 1) {
 * web.redirect(null, "삭제 권한이 없습니다."); return null; }
 * 
 * fileList = fileService.selectFileList(file); // 게시글에 포함된 파일목록을 조회
 * fileService.deleteFileAll(file); // 게시글에 속한 파일목록 모두 삭제
 * 
 * // 댓글이 게시물을 참조하므로, 댓글이 먼저 삭제되어야 한다. replyService.deleteCommentAll(reply);
 * 
 * boardService.removeItem(item); // 게시글 삭제 } catch (Exception e) {
 * web.redirect(null, e.getLocalizedMessage()); return null; } finally {
 * sqlSession.close(); }
 * 
 *//** (8) 실제 파일을 삭제한다. */
/*
 * 
 * // DB에서 파일 정보가 삭제되더라도 실제 저장되어 있는 파일 자체가 삭제되는 것은 아니다. // 실제 파일도 함께 삭제하기 위해서
 * (7)번 절차에서 파일정보를 삭제하기 전에 미리 // 조회해 둔 것이다. // 조회한 파일 목록만큼 반복하면서 저장되어 있는 파일을
 * 삭제한다. if (fileList != null) { for (int i=0; i<fileList.size(); i++) { File f
 * = fileList.get(i); String filePath = f.getFileDir() + "/" + f.getFileName();
 * logger.debug("filePath = " + filePath); upload.removeFile(filePath); } }
 * 
 *//** (6) 처리 결과를 JSON으로 출력하기 *//*
								 * Map<String, Object> data = new HashMap<String, Object>(); data.put("rt",
								 * "OK"); data.put("boardId", boardId);
								 * 
								 * ObjectMapper mapper = new ObjectMapper();
								 * mapper.writeValue(response.getWriter(), data);
								 * 
								 * return null; }
								 * 
								 * }
								 */