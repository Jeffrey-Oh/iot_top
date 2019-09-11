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
 * 
 * import project.java.travel.dao.MyBatisConnectionFactory; import
 * project.java.travel.model.Member; import
 * project.java.travel.service.BoardService; import
 * project.java.travel.service.impl.BoardServiceImpl; import
 * project.jsp.helper.BaseController; import project.jsp.helper.RegexHelper;
 * import project.jsp.helper.UploadHelper; import project.jsp.helper.WebHelper;
 * 
 *//**
	 * Servlet implementation class CommunityAddItem
	 */
/*
 * @WebServlet("/community/CommunityAddItem.do") public class CommunityAddItem
 * extends BaseController { private static final long serialVersionUID =
 * -1993807566419864926L;
 * 
 * Logger logger; SqlSession sqlSession; WebHelper web; UploadHelper upload;
 * RegexHelper regex; BoardService boardService; BBSCommon bbs;
 * 
 * @Override public String doRun(HttpServletRequest request, HttpServletResponse
 * response) throws ServletException, IOException { logger =
 * LogManager.getFormatterLogger("CommunityAddItem.do"); // CommunityAddItem.jsp
 * 로그 설정
 * 
 * web = WebHelper.getInstance(request, response);
 * 
 * sqlSession = MyBatisConnectionFactory.getSqlSession(); // MyBatis 설정
 * 
 * boardService = new BoardServiceImpl(logger, sqlSession); // Service 설정
 * 
 * bbs = BBSCommon.getInstance();
 * 
 * regex = RegexHelper.getInstance();
 * 
 *//** ===== 악의적인 접근에 대한 방어 코드 *//*
									 * String referer = request.getHeader("referer"); if(referer == null) { // 정상적인
									 * 접근 방법이 아닐 경우 sqlSession.close(); logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " +
									 * web.getClientIP()); web.redirect(web.getRootPath() + "/error/error_page.do",
									 * null); return null; } else { if(regex.isIndexCheck(referer,
									 * "CommunityIndex.do") && regex.isIndexCheck(referer, "CommunityView.do") &&
									 * regex.isIndexCheck(referer, "member_logout.do")) { // 거쳐야할 절차를 건너뛴 경우
									 * sqlSession.close(); logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " +
									 * web.getClientIP()); web.redirect(web.getRootPath() + "/error/error_page.do",
									 * null); return null; } }
									 * 
									 * Member loginInfo = (Member) web.getSession("loginInfo"); if (loginInfo ==
									 * null) { sqlSession.close(); web.redirect(web.getRootPath() +
									 * "/community/CommunityIndex.do", "글 추가 권한이 없습니다."); return null; }
									 * 
									 * String category = web.getString("category"); // 현재 페이지의 카테고리 구분을 위한 파라미터
									 * logger.debug("category = " + category); request.setAttribute("category",
									 * category);
									 * 
									 * String korCtg = ""; try { korCtg = bbs.getBbsName(category); if
									 * (korCtg.equals("null")) { korCtg = null; } logger.debug("korCtg = " +
									 * korCtg); request.setAttribute("korCtg", korCtg); } catch (Exception e) {
									 * web.redirect(null, e.getLocalizedMessage()); return null; }
									 * 
									 * int totalCount = sqlSession.selectOne("BoardMapper.getCount"); // 전체 개시물 개수
									 * request.setAttribute("totalCount", totalCount);
									 * 
									 * String userName = loginInfo.getUserName(); request.setAttribute("userName",
									 * userName);
									 * 
									 * return "community/CommunityAddItem"; }
									 * 
									 * 
									 * }
									 */