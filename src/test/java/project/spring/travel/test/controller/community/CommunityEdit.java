package project.spring.travel.test.controller.community;
/*
 * package project.jsp.travel.controller.community;
 * 
 * import java.io.IOException; import java.util.List;
 * 
 * import javax.servlet.ServletException; import
 * javax.servlet.annotation.WebServlet; import
 * javax.servlet.http.HttpServletRequest; import
 * javax.servlet.http.HttpServletResponse;
 * 
 * import org.apache.ibatis.session.SqlSession; import
 * org.apache.logging.log4j.LogManager; import org.apache.logging.log4j.Logger;
 * 
 * import project.java.travel.dao.MyBatisConnectionFactory; import
 * project.java.travel.model.BoardList; import project.java.travel.model.File;
 * import project.java.travel.model.Member; import
 * project.java.travel.service.BoardService; import
 * project.java.travel.service.FileService; import
 * project.java.travel.service.impl.BoardServiceImpl; import
 * project.java.travel.service.impl.FileServiceImpl; import
 * project.jsp.helper.BaseController; import project.jsp.helper.RegexHelper;
 * import project.jsp.helper.WebHelper;
 * 
 *//**
	 * Servlet implementation class CommunityEdit
	 */
/*
 * @WebServlet("/community/CommunityEdit.do") public class CommunityEdit extends
 * BaseController { private static final long serialVersionUID =
 * 8993480920585376097L;
 * 
 * WebHelper web; Logger logger; BBSCommon bbs; SqlSession sqlSession;
 * RegexHelper regex; FileService fileService;
 * 
 * @Override public String doRun(HttpServletRequest request, HttpServletResponse
 * response) throws ServletException, IOException { web =
 * WebHelper.getInstance(request, response);
 * 
 * logger = LogManager.getFormatterLogger("CommunityEdit.do"); //
 * CommunityEdit.jsp 로그 설정
 * 
 * sqlSession = MyBatisConnectionFactory.getSqlSession();
 * 
 * bbs = BBSCommon.getInstance();
 * 
 * regex = RegexHelper.getInstance();
 * 
 * fileService = new FileServiceImpl(logger, sqlSession);
 * 
 *//** ===== 악의적인 접근에 대한 방어 코드 *//*
									 * String referer = request.getHeader("referer"); if(referer == null) { // 정상적인
									 * 접근 방법이 아닐 경우 sqlSession.close(); logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " +
									 * web.getClientIP()); web.redirect(web.getRootPath() + "/error/error_page.do",
									 * null); return null; } else { if(regex.isIndexCheck(referer,
									 * "CommunityView.do") && regex.isIndexCheck(referer, "member_logout.do")) { //
									 * 거쳐야할 절차를 건너뛴 경우 sqlSession.close(); logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = "
									 * + web.getClientIP()); web.redirect(web.getRootPath() +
									 * "/error/error_page.do", null); return null; } }
									 * 
									 * Member loginInfo = (Member) web.getSession("loginInfo"); if (loginInfo ==
									 * null) { sqlSession.close(); web.redirect(web.getRootPath() +
									 * "/community/CommunityIndex.do", "수정 권한이 없습니다."); return null; }
									 * 
									 * int boardId = Integer.parseInt(web.getString("boardId")); // 게시물 일련번호 구분을 위한
									 * 파라미터 logger.debug("boardId = " + boardId); request.setAttribute("boardId",
									 * boardId);
									 * 
									 * String category = web.getString("category"); // 현재 페이지의 카테고리 구분을 위한 파라미터
									 * logger.debug("category = " + category); request.setAttribute("category",
									 * category);
									 * 
									 * 
									 * String korCtg = ""; try { korCtg = bbs.getBbsName(category); if
									 * (korCtg.equals("null")) { korCtg = null; } logger.debug("korCtg = " +
									 * korCtg); request.setAttribute("korCtg", korCtg); } catch (Exception e) {
									 * web.redirect(null, e.getLocalizedMessage()); return null; }
									 * 
									 * SqlSession sqlSession = MyBatisConnectionFactory.getSqlSession(); // MyBatis
									 * 설정
									 * 
									 * BoardService boardService = new BoardServiceImpl(logger, sqlSession); //
									 * Service 설정
									 * 
									 * int totalCount = sqlSession.selectOne("BoardMapper.getCount"); // 전체 게시물 개수
									 * request.setAttribute("totalCount", totalCount);
									 * 
									 * BoardList item = new BoardList(); // 선택된 게시물을 조회하기 위한 BoardList 객체 생성
									 * item.setBoardId(boardId); // boardId 파라미터 값을 객체에 boardId에 저장
									 * 
									 * File file = new File(); file.setBoardId(boardId);
									 * 
									 * BoardList obj = null; // 조회된 게시물 내용을 담을 BoardList 객체 생성 // 첨부파일 정보가 저장될 객체
									 * List<File> fileList = null;
									 * 
									 * try { // 조회된 게시물 내용을 obj에 담는다 obj = boardService.getItem(item);
									 * request.setAttribute("obj", obj); fileList =
									 * fileService.selectFileList(file); request.setAttribute("fileList", fileList);
									 * } catch (Exception e) { web.redirect(null, e.getLocalizedMessage()); return
									 * null; } finally { sqlSession.close(); }
									 * 
									 * return "community/CommunityEdit"; }
									 * 
									 * }
									 */