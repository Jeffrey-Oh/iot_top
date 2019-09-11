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
 * project.java.travel.model.BoardList; import project.java.travel.model.File;
 * import project.java.travel.model.Member; import
 * project.java.travel.service.BoardService; import
 * project.java.travel.service.FileService; import
 * project.java.travel.service.impl.BoardServiceImpl; import
 * project.java.travel.service.impl.FileServiceImpl; import
 * project.jsp.helper.BaseController; import project.jsp.helper.FileInfo; import
 * project.jsp.helper.RegexHelper; import project.jsp.helper.UploadHelper;
 * import project.jsp.helper.WebHelper;
 * 
 *//**
	 * Servlet implementation class CommunityAddItemOk
	 */
/*
 * @WebServlet("/community/CommunityAddItemOk.do") public class
 * CommunityAddItemOk extends BaseController {
 * 
 * private static final long serialVersionUID = -5561828221801516615L;
 * 
 * Logger logger; SqlSession sqlSession; WebHelper web; UploadHelper upload;
 * RegexHelper regex; BoardService boardService; FileService fileService;
 * BBSCommon bbs;
 * 
 * @Override public String doRun(HttpServletRequest request, HttpServletResponse
 * response) throws ServletException, IOException { logger =
 * LogManager.getFormatterLogger("CommunityAddItemOk.do"); //
 * CommunityAddItemOk.jsp 로그 설정 sqlSession =
 * MyBatisConnectionFactory.getSqlSession(); // MyBatis 설정 web =
 * WebHelper.getInstance(request, response); upload =
 * UploadHelper.getInstance(); regex = RegexHelper.getInstance(); bbs =
 * BBSCommon.getInstance(); boardService = new BoardServiceImpl(logger,
 * sqlSession); // Service 설정 fileService = new FileServiceImpl(logger,
 * sqlSession);
 * 
 *//** 컨텐츠 타입 명시 */
/*
 * response.setContentType("application/json");
 * 
 *//** ===== 악의적인 접근에 대한 방어 코드 *//*
									 * String referer = request.getHeader("referer"); if(referer == null) { // 정상적인
									 * 접근 방법이 아닐 경우 sqlSession.close(); logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " +
									 * web.getClientIP()); web.redirect(web.getRootPath() + "/error/error_page.do",
									 * null); return null; } else { if(regex.isIndexCheck(referer,
									 * "CommunityAddItem.do")) { // 거쳐야할 절차를 건너뛴 경우 sqlSession.close();
									 * logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
									 * web.redirect(web.getRootPath() + "/error/error_page.do", null); return null;
									 * } }
									 * 
									 * Member loginInfo = (Member) web.getSession("loginInfo"); if (loginInfo ==
									 * null) { sqlSession.close(); web.redirect(web.getRootPath() +
									 * "community/CommunityIndex.do", "글 추가 권한이 없습니다."); return null; }
									 * 
									 * try { upload.multipartRequest(request); } catch (Exception e) {
									 * sqlSession.close(); web.printJsonRt("multipart 데이터가 아닙니다."); return null; }
									 * 
									 * int memberId = loginInfo.getMemberId(); // 조건값인 유저 일련번호 파라미터 String userName
									 * = loginInfo.getUserName(); // 조건값인 작성자 닉네임 파라미터
									 * 
									 * Map<String, String> paramMap = upload.getParamMap(); String title =
									 * paramMap.get("title").trim(); // 추가될 제목 파라미터 String content =
									 * paramMap.get("textarea"); // 추가될 내용 파라미터 String category =
									 * paramMap.get("category"); // 추가될 카테고리 파라미터 logger.debug(paramMap.toString());
									 * 
									 * String korCtg = ""; try { korCtg = bbs.getBbsName(category); if
									 * (korCtg.equals("null")) { korCtg = null; } logger.debug("korCtg = " +
									 * korCtg); request.setAttribute("korCtg", korCtg); } catch (Exception e) {
									 * web.printJsonRt(e.getLocalizedMessage()); return null; }
									 * 
									 * if (!regex.isValue(userName)) { upload.removeTempFile(); sqlSession.close();
									 * web.printJsonRt("로그인 후에 이용 가능합니다."); return null; }
									 * 
									 * if (!regex.isValue(title)) { upload.removeTempFile(); sqlSession.close();
									 * web.printJsonRt("제목을 입력하세요"); return null; }
									 * 
									 * if (title.length() < 2) { upload.removeTempFile(); sqlSession.close();
									 * web.printJsonRt("제목을 2글자 이상 입력하세요."); return null; }
									 * 
									 * if (!regex.isValue(content)) { upload.removeTempFile(); sqlSession.close();
									 * web.printJsonRt("내용을 입력하세요."); return null; }
									 * 
									 * if (content.length() < 10) { upload.removeTempFile(); sqlSession.close();
									 * web.printJsonRt("내용을 10자 이상 입력하세요."); return null; }
									 * 
									 * BoardList item = new BoardList(); // 추가될 게시물 아이템들을 설정하기 위한 BoardList 객체 생성
									 * item.setCategory(category); // category 파라미터 값을 객체 category에 저장
									 * item.setKorCtg(korCtg); item.setMemberId(memberId); // memberId 파라미터 값을 객체
									 * memberId에 저장 item.setUserName(userName); // userName 파라미터 값을 객체 userName에 저장
									 * item.setTitle(title); // title 파라미터 값을 객체 title에 저장 item.setContent(content);
									 * // content 파라미터 값을 객체 content에 저장
									 * 
									 * try { // for (int i=0; i<60; i++) { // item.setTitle(title + "_" + i);
									 * boardService.addItem(item); // } } catch (Exception e) { sqlSession.close();
									 * web.printJsonRt(e.getLocalizedMessage()); return null; }
									 * 
									 * List<FileInfo> fileList = upload.getFileList();
									 * 
									 * try { // 업로드 된 파일의 수 만큼 반복 처리 한다. for (int i=0; i<fileList.size(); i++) { //
									 * 업로드 된 정보 하나 추출하여 데이터베이스에 저장하기 위한 형태로 가공해야 하낟. FileInfo info =
									 * fileList.get(i);
									 * 
									 * // DB에 저장하기 위한 항목 생성 File file = new File();
									 * 
									 * // 몇 번 게시물에 속한 파일인지 지정한다. file.setBoardId(item.getBoardId());
									 * 
									 * // 데이터 복사 file.setOrginName(info.getOrginName());
									 * file.setFileDir(info.getFileDir()); file.setFileName(info.getFileName());
									 * file.setContentType(info.getContentType());
									 * file.setFileSize(info.getFileSize());
									 * 
									 * // 저장처리 fileService.insertFile(file); } } catch (Exception e) {
									 * web.printJsonRt(e.getLocalizedMessage()); return null; } finally {
									 * sqlSession.close(); }
									 * 
									 * //제출 전에 convert 필요
									 * 
									 * String url = "%s/community/CommunityView.do?category=%s&boardId=%d"; url =
									 * String.format(url, web.getRootPath(), item.getCategory(), item.getBoardId());
									 * 
									 * Map<String, Object> data = new HashMap<String, Object>(); data.put("rt",
									 * "OK"); data.put("item", item); data.put("url", url);
									 * 
									 * ObjectMapper mapper = new ObjectMapper();
									 * mapper.writeValue(response.getWriter(), data);
									 * 
									 * logger.debug("data >> " + data.toString());
									 * 
									 * return null; }
									 * 
									 * }
									 */