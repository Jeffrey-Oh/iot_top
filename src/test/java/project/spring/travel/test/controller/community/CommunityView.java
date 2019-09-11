package project.spring.travel.test.controller.community;
/*
 * package project.jsp.travel.controller.community;
 * 
 * import java.io.IOException; import java.text.SimpleDateFormat; import
 * java.util.Date; import java.util.List;
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
 * project.java.travel.model.BoardLike; import
 * project.java.travel.model.BoardList; import
 * project.java.travel.model.BoardReply; import project.java.travel.model.File;
 * import project.java.travel.model.Member; import
 * project.java.travel.service.BoardLikeService; import
 * project.java.travel.service.BoardService; import
 * project.java.travel.service.FileService; import
 * project.java.travel.service.ReplyService; import
 * project.java.travel.service.impl.BoardLikeServiceImpl; import
 * project.java.travel.service.impl.BoardServiceImpl; import
 * project.java.travel.service.impl.FileServiceImpl; import
 * project.java.travel.service.impl.ReplyServiceImpl; import
 * project.jsp.helper.BaseController; import project.jsp.helper.PageHelper;
 * import project.jsp.helper.WebHelper;
 * 
 *//**
	 * Servlet implementation class CommunityView
	 */
/*
 * @WebServlet("/community/CommunityView.do") public class CommunityView extends
 * BaseController { private static final long serialVersionUID =
 * 799772303772423619L;
 * 
 * Logger logger; SqlSession sqlSession; WebHelper web; BBSCommon bbs;
 * BoardService boardService; FileService fileService; BoardLikeService
 * boardLikeService; ReplyService replyService;
 * 
 * @Override public String doRun(HttpServletRequest request, HttpServletResponse
 * response) throws ServletException, IOException { web =
 * WebHelper.getInstance(request, response);
 * 
 * logger = LogManager.getFormatterLogger("CommunityView.do"); //
 * CommunityView.jsp 로그 설정
 * 
 * sqlSession = MyBatisConnectionFactory.getSqlSession(); // MyBatis 설정
 * 
 * BoardService boardService = new BoardServiceImpl(logger, sqlSession); //
 * Service 설정
 * 
 * fileService = new FileServiceImpl(logger, sqlSession);
 * 
 * bbs = BBSCommon.getInstance();
 * 
 * boardLikeService = new BoardLikeServiceImpl(logger, sqlSession);
 * 
 * replyService = new ReplyServiceImpl(logger, sqlSession);
 * 
 * int loginChk = 0; // 비 로그인시 0
 * 
 * int memberId = 0;
 * 
 * Member loginInfo = (Member) web.getSession("loginInfo"); if (loginInfo !=
 * null) { loginChk = 1; memberId = loginInfo.getMemberId(); }
 * 
 * request.setAttribute("loginChk", loginChk);
 * 
 * int boardId = Integer.parseInt(web.getString("boardId")); // 게시물 일련번호 구분을 위한
 * 파라미터 logger.debug("boardId = " + boardId); request.setAttribute("boardId",
 * boardId);
 * 
 * if (boardId == 0) { web.redirect(null, "글 번호가 지정되지 않았습니다.");
 * sqlSession.close(); return null; }
 * 
 * String category = web.getString("category"); // 현재 페이지의 카테고리 구분을 위한 파라미터
 * logger.debug("category = " + category); request.setAttribute("category",
 * category); // 파라미터 넘어올 때 String으로 넘어와서 다시한번 null로 변환해줘야함 String korCtg = "";
 * try { korCtg = bbs.getBbsName(category); logger.debug("korCtg = " + korCtg);
 * request.setAttribute("korCtg", korCtg); } catch (Exception e) {
 * web.redirect(null, e.getLocalizedMessage()); return null; }
 * 
 * String searchWord = web.getString("search-word"); // 검색어 저장
 * logger.debug("search-word = " + searchWord);
 * request.setAttribute("searchWord", searchWord);
 * 
 * String type = web.getString("type", ""); // 검색 종류 logger.debug("type = " +
 * type); request.setAttribute("type", type);
 * 
 * int nowPage = web.getInt("list", 1); // 현재 페이지 logger.debug("list = " +
 * nowPage); request.setAttribute("nowPage", nowPage);
 * 
 * BoardList obj = new BoardList(); // 선택된 게시물을 조회하기 위한 BoardList 객체 생성
 * obj.setBoardId(boardId); // boardId 파라미터 값을 객체에 boardId에 저장
 * 
 * File file = new File(); file.setBoardId(boardId);
 * 
 * BoardList item = null;
 * 
 * BoardLike like = new BoardLike(); like.setBoardId(boardId);
 * like.setMemberId(memberId);
 * 
 * List<File> fileList = null;
 *//** 조회수 중복 갱신 방지 처리 *//*
							 * // 카테고리와 게시물 일련번호를 조합한 문자열을 생성 // ex) document_notice_15 String cookieKey =
							 * "board_" + category + "_" + boardId + memberId; // 준비한 문자열에 대응되는 쿠키값 조회
							 * String cookieVar = web.getCookie(cookieKey); try { // 쿠키값이 없다면 조회수 갱신 if
							 * (cookieVar == null && memberId != 0) { boardService.updateBoard(obj); // 준비한
							 * 문자열에 대한 쿠키를 24시간동안 저장 web.setCookie(cookieKey, "Y", 60*60*24); } item =
							 * boardService.getItem(obj); // 조회된 게시물 내용을 obj에 담는다 // 게시물 좋아요 현황 db 조회 // 게시물
							 * 등록할 때 참조관계로 인해 게시물 좋아요 테이블에도 같이 추가해야함..... 변경 필요 fileList =
							 * fileService.selectFileList(file); request.setAttribute("item", item); boolean
							 * likeTarget = false; if (boardLikeService.selectBoardLike(like) == 1) {
							 * likeTarget = true; } request.setAttribute("likeTarget", likeTarget);
							 * request.setAttribute("fileList", fileList); } catch (Exception e) {
							 * web.redirect(null, e.getLocalizedMessage()); return null; }
							 * 
							 * int boardNum = 0;
							 * 
							 * BoardList params = new BoardList(); // SQL 파라미터로 넘길 BoardList 객체 생성
							 * 
							 * params.setType(type); params.setKorCtg(korCtg); // 카테고리를 카테고리에 저장 if
							 * (type.equals("제목%2B내용")){ params.setTitle(searchWord); // 검색어를 제목에 저장
							 * params.setContent(searchWord); // 검색어를 내용에 저장 } else if (type.equals("제목")) {
							 * params.setTitle(searchWord); // 검색어를 제목에 저장 } else if (type.equals("작성자")) {
							 * params.setUserName(searchWord); }
							 * 
							 * int totalCount = 0; int totalCountAll = 0; int totalReply = 0;
							 * 
							 * BoardReply reply = new BoardReply(); reply.setBoardId(boardId);
							 * reply.setMemberId(memberId);
							 * 
							 * try { totalCount = boardService.getCount(params); // 게시물 전체 개수 totalCountAll
							 * = boardService.getCount(null); // 사이드(전체 글 보기)메뉴에 표시할 게시물 전체 개수 totalReply =
							 * replyService.selectCount(reply); // 댓글 전체 개수 } catch (Exception e) {
							 * web.redirect(null, e.getLocalizedMessage()); return null; }
							 * 
							 * request.setAttribute("totalCount", totalCount);
							 * request.setAttribute("totalCountAll", totalCountAll);
							 * request.setAttribute("totalReply", totalReply);
							 * 
							 * PageHelper pageHelper = PageHelper.getInstance(nowPage, totalCount, 10, 5);
							 * params.setLimitStart(pageHelper.getLimitStart());
							 * params.setListCount(pageHelper.getListCount());
							 * 
							 * request.setAttribute("pageHelper", pageHelper);
							 * 
							 * List<BoardList> list = null; // 조회된 게시물들을 담을 List<BoardList> 객체
							 * 
							 * try { list = boardService.getItemAll(params); } catch (Exception e) {
							 * web.redirect(null, e.getLocalizedMessage()); return null; } finally {
							 * sqlSession.close(); }
							 * 
							 * request.setAttribute("getItemAll", list); request.setAttribute("userName",
							 * list.get(0).getUserName());
							 * 
							 * Date date = new Date(); SimpleDateFormat simpleDate = new
							 * SimpleDateFormat("yyyy-MM-dd"); String year = (String)
							 * simpleDate.format(date); request.setAttribute("year", year);
							 * 
							 * for (int q = 0; q<list.size(); q++) { BoardList item1 = list.get(q);
							 * if(item1.getBoardId() == boardId){ // 현재 게시물 임의 번호 boardNum =
							 * totalCount-q-pageHelper.getLimitStart(); request.setAttribute("boardNum",
							 * boardNum); } }
							 * 
							 * String curUrl = request.getRequestURL() + "?" + request.getQueryString();
							 * logger.debug("curUrl = " + curUrl); request.setAttribute("curUrl", curUrl);
							 * 
							 * return "community/CommunityView"; }
							 * 
							 * }
							 */