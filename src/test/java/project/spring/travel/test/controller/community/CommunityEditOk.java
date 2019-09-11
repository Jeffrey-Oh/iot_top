package project.spring.travel.test.controller.community;
/*
 * package project.jsp.travel.controller.community;
 * 
 * import java.io.IOException; import java.util.List; import java.util.Map;
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
	 * Servlet implementation class CommunityEditOk
	 */
/*
 * @WebServlet("/community/CommunityEditOk.do") public class CommunityEditOk
 * extends BaseController { private static final long serialVersionUID =
 * -9005509512499487015L;
 * 
 * 
 * 
 *//** (1) 사용하고자 하는 Helper 객체 선언 */
/*
 * Logger logger; SqlSession sqlSession; WebHelper web; BBSCommon bbs;
 * UploadHelper upload; RegexHelper regex; BoardService boardService;
 * FileService fileService;
 * 
 * @Override public String doRun(HttpServletRequest request, HttpServletResponse
 * response) throws ServletException, IOException { logger =
 * LogManager.getFormatterLogger("CommunityEditOk.do"); // CommunityEditOk.jsp
 * 로그 설정 sqlSession = MyBatisConnectionFactory.getSqlSession(); // MyBatis 설정
 * web = WebHelper.getInstance(request, response); upload =
 * UploadHelper.getInstance(); regex = RegexHelper.getInstance(); bbs =
 * BBSCommon.getInstance(); boardService = new BoardServiceImpl(logger,
 * sqlSession); // Service 설정 fileService = new FileServiceImpl(logger,
 * sqlSession);
 * 
 *//** 컨텐츠 타입 명시 */
/*
 * response.setContentType("application/json");
 * 
 *//** ===== 악의적인 접근에 대한 방어 코드 */
/*
 * String referer = request.getHeader("referer"); if(referer == null) { // 정상적인
 * 접근 방법이 아닐 경우 sqlSession.close(); logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " +
 * web.getClientIP()); web.redirect(web.getRootPath() + "/error/error_page.do",
 * null); return null; } else { if(regex.isIndexCheck(referer,
 * "CommunityEdit.do")) { // 거쳐야할 절차를 건너뛴 경우 sqlSession.close();
 * logger.debug("정상적인 접근 방법이 아닌 사용자의 IP = " + web.getClientIP());
 * web.redirect(web.getRootPath() + "/error/error_page.do", null); return null;
 * } }
 * 
 * Member loginInfo = (Member) web.getSession("loginInfo"); if (loginInfo ==
 * null) { sqlSession.close(); web.redirect(web.getRootPath() +
 * "community/CommunityIndex.do", "수정 권한이 없습니다."); return null; }
 * 
 *//** (3) 파일이 포함된 POST 파라미터 받기 */
/*
 * try { upload.multipartRequest(request); } catch (Exception e) {
 * sqlSession.close(); web.redirect(null, "multipart 데이터가 아닙니다."); return null;
 * }
 * 
 *//** (4) UploadHelper에서 텍스트 형식의 값을 추출 */
/*
 * int memberId = loginInfo.getMemberId(); // 조건값인 유저 일련번호 파라미터 String userName
 * = loginInfo.getUserName(); // 조건값인 작성자 닉네임 파라미터
 * 
 * Map<String, String> paramMap = upload.getParamMap(); String title =
 * paramMap.get("title").trim(); // 추가될 제목 파라미터 String content =
 * paramMap.get("textarea"); // 추가될 내용 파라미터 String category =
 * paramMap.get("category"); // 추가될 카테고리 파라미터 int boardId =
 * Integer.parseInt(paramMap.get("boardId")); logger.debug(paramMap.toString());
 * 
 *//** (5) 게시판 카테고리 값을 받아서 View에 전달 */
/*
 * // 파일이 첨부된 경우 WebHelper를 사용할 수 없다. // String category =
 * web.getString("category"); request.setAttribute("category", category);
 * 
 *//** (6) 존재하는 게시판인지 판별하기 */
/*
 * try { String bbsName = bbs.getBbsName(category);
 * request.setAttribute("bbsName", bbsName); } catch (Exception e) {
 * sqlSession.close(); web.redirect(null, e.getLocalizedMessage()); return null;
 * }
 * 
 *//** (7) 로그인 한 경우 자신의 글이라면 입력하지 않은 정보를 세션 데이터로 대체한다. */
/*
 * // 소유권 검사 정보 boolean myDocument = false;
 * 
 * if (loginInfo != null) { try { // 소유권 판정을 위하여 사용하는 임시 객체 BoardList temp = new
 * BoardList(); temp.setCategory(category); temp.setBoardId(boardId);
 * temp.setMemberId(loginInfo.getMemberId());
 * 
 * if (boardService.selectDocumentCountByMemberId(temp) > 0) { // 소유권을 의미하는 변수
 * 변경 myDocument = true; // 입력되지 않은 정보들 갱신 memberId = loginInfo.getMemberId(); }
 * } catch (Exception e) { sqlSession.close(); web.redirect(null,
 * e.getLocalizedMessage()); return null; } }
 * 
 * // 전달된 파라미터는 로그로 확인한다. logger.debug("boardId=" + boardId);
 * logger.debug("category=" + category); logger.debug("content=" + content);
 * logger.debug("memberId=" + memberId);
 * 
 *//** (8) 입력 받은 파라미터에 대한 유효성 검사 */
/*
 * // 이름 + 비밀번호 String korCtg = ""; try { korCtg = bbs.getBbsName(category); if
 * (korCtg.equals("null")) { korCtg = null; } logger.debug("korCtg = " +
 * korCtg); request.setAttribute("korCtg", korCtg); } catch (Exception e) {
 * web.redirect(null, e.getLocalizedMessage()); return null; }
 * 
 * if (!regex.isValue(userName)) { upload.removeTempFile(); sqlSession.close();
 * web.redirect(null, "로그인 후에 이용 가능합니다."); return null; }
 * 
 * if (!regex.isValue(title)) { upload.removeTempFile(); sqlSession.close();
 * web.redirect(null, "제목을 입력하세요"); return null; }
 * 
 * if (title.length() < 2) { upload.removeTempFile(); sqlSession.close();
 * web.redirect(null, "제목을 2글자 이상 입력하세요."); return null; }
 * 
 * if (!regex.isValue(content)) { upload.removeTempFile(); sqlSession.close();
 * web.redirect(null, "내용을 입력하세요."); return null; }
 * 
 * if (content.length() < 10) { upload.removeTempFile(); sqlSession.close();
 * web.redirect(null, "내용을 10자 이상 입력하세요."); return null; }
 * 
 * BoardList item = new BoardList(); // 추가될 게시물 아이템들을 설정하기 위한 BoardList 객체 생성
 * item.setCategory(category); // category 파라미터 값을 객체 category에 저장
 * item.setKorCtg(korCtg); item.setMemberId(memberId); // memberId 파라미터 값을 객체
 * memberId에 저장 item.setUserName(userName); // userName 파라미터 값을 객체 userName에 저장
 * item.setTitle(title); // title 파라미터 값을 객체 title에 저장 item.setContent(content);
 * // content 파라미터 값을 객체 content에 저장 item.setBoardId(boardId);
 * 
 *//** (10) 게시물 변경을 위한 Service 기능을 호출 */
/*
 * try { if (!myDocument) { throw new Exception("건들지마세요 남의 자료 ㅋ"); }
 * boardService.editItem(item); } catch (Exception e) { sqlSession.close();
 * web.redirect(null, e.getLocalizedMessage()); return null; }
 * 
 *//** (11) 삭제를 선택한 첨부파일에 대한 처리 */
/*
 * // 삭제할 파일 목록에 대한 체크결과 --> 체크박스의 선택값을 paramMap에서 추출 String delFile =
 * paramMap.get("del_file");
 * 
 * if (delFile != null) { // 콤마 단위로 잘라서 배열로 변환 String[] delFileList =
 * delFile.split(",");
 * 
 * for (int i = 0; i < delFileList.length; i++) { try { // 체크박스에 의해서 전달된 id값으로
 * 개별 파일에 대한 Beans 생성 File file = new File();
 * file.setFileId(Integer.parseInt(delFileList[i]));
 * 
 * // 개별 파일에 대한 정보를 조회하여 실제 파일을 삭제한다. File fileItem =
 * fileService.selectFile(file); upload.removeFile(fileItem.getFileDir() + "/" +
 * fileItem.getFileName());
 * 
 * // DB에서 파일정보 삭제처리 fileService.deleteFile(file); } catch (Exception e) {
 * sqlSession.close(); web.redirect(null, e.getLocalizedMessage()); return null;
 * } } }
 * 
 *//** (12) 추가적으로 업로드 된 파일 정보 처리 */
/*
 * // 업로드 된 파일 목록 List<FileInfo> fileInfoList = upload.getFileList(); // 업로드 된
 * 파일의 수 만큼 반복 처리 한다. for (int i = 0; i < fileInfoList.size(); i++) { // 업로드 된
 * 정보 하나 추출 // --> 업로드 된 정보를 데이터베이스에 저장하기 위한 형태로 가공해야 한다. FileInfo info =
 * fileInfoList.get(i);
 * 
 * // DB에 저장하기 위한 항목 하나 생성 File file = new File();
 * 
 * // 데이터 복사 file.setOrginName(info.getOrginName());
 * file.setFileDir(info.getFileDir()); file.setFileName(info.getFileName());
 * file.setContentType(info.getContentType());
 * file.setFileSize(info.getFileSize());
 * 
 * // 어느 게시물에 속한 파일인지 인식해야 하므로 글 번호 추가 file.setBoardId(boardId);
 * 
 * // 복사된 데이터를 DB에 저장 try { fileService.insertFile(file); } catch (Exception e)
 * { sqlSession.close(); web.redirect(null, e.getLocalizedMessage()); return
 * null; } }
 * 
 *//** (13) 모든 절차가 종료되었으므로 DB접속 해제 후 페이지 이동 *//*
												 * sqlSession.close();
												 * 
												 * JSONObject json = new JSONObject();
												 * 
												 * json.put("boardId", item.getBoardId()); json.put("category",
												 * category); json.put("korCtg", korCtg); json.put("userName",
												 * userName); json.put("title", title); json.put("content", content);
												 * String url = "%s/community/CommunityView.do?category=%s&boardId=%d";
												 * url = String.format(url, web.getRootPath(), item.getCategory(),
												 * item.getBoardId()); json.put("url", url); // 파일 업로드 개수만큼 json 만들어놓기
												 * 
												 * response.getWriter().print(json); System.out.println("json >> " +
												 * json.toString());
												 * 
												 * return null; }
												 * 
												 * }
												 */