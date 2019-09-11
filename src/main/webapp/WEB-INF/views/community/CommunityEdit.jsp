<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/inc/Head.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/subpage.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/community.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/community-add-edit.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/searchbar.css"><!-- searchbar를 사용하는 경우 장착 -->
</head>
<body>
<!-- 전체를 감싸는 div -->
<div class="container-fluid">
<%@ include file="/WEB-INF/inc/Gnb.jsp"%>
<%@ include file="/WEB-INF/inc/Visual.jsp"%>
<%@ include file="/WEB-INF/inc/Searchbar.jsp" %><!-- 빠른검색(css, js, regex.js 필요) -->

<!-- contents -->
<section id="contents">
    <h2 class="blind">본문</h2>
    <div class="container">
        <!-- 메인 컨텐츠 삽입 -->
<!-- content 감싸는 영역 -->
<div class="content-parent">
	<!-- content 시작 -->
	<section class="content clearfix">
		<!-- content-header 시작 -->
		<div id="content-header" class="content-header">
			<!-- 본문 주제 -->
			<h1 id="page-header" class="page-header">커뮤니티</h1>
			<hr>
		</div>
		<!-- content-header 끝 -->
		<!-- side-menu 시작 -->
		<aside class="aside">
			<nav class="side-menu">
				<!-- li 태그에 span 비워둠 i 태그로 새로운 글 생성 시 New 표시할 껀지.... -->
				<ul class="list-unstyled ctg" id="ctg">
					<li><a href="${pageContext.request.contextPath}/CommIndex.do">전체 글 보기<span>(${totalCount})
						</span></a></li>
					<li><a href="${pageContext.request.contextPath}/CommIndex.do?category=notice">공지사항<span></span></a></li>
					<li><a href="${pageContext.request.contextPath}/CommIndex.do?category=tour">관광<span></span></a></li>
					<li><a href="${pageContext.request.contextPath}/CommIndex.do?category=airport">항공<span></span></a></li>
					<li><a href="${pageContext.request.contextPath}/CommIndex.do?category=servicearea">휴게소<span></span></a></li>
					<li><a href="${pageContext.request.contextPath}/CommIndex.do?category=culture">문화<span></span></a></li>
				</ul>
			</nav>
		</aside>
		<!-- side-menu 끝 -->
		<!-- 본문 section 시작 -->
		<section class="section clearfix">
			<!-- 게시물 수정 form 시작 -->
			<form name="form1" id="edit-form" action="${pageContext.request.contextPath}/CommEditOk.do" method="post" enctype="multipart/form-data" class="form-horizontal">
				<div class="board-box col-lg-12 col-md-6 col-sm-6 col-xs-3">
					<table class="table">
						<tbody>
							<tr>
								<td>카테고리</td>
								<td>${korCtg}</td>
							</tr>
							<tr>
								<td>작성자</td>
								<td>${loginInfo.userName}</td>
							</tr>
							<tr>
								<td>제목</td>
								<td><input type="text" name="title" id="title"
									class="title form-control" placeholder="제목을 입력하세요."
									value="${obj.title}"></td>
							</tr>
							<c:if test="${loginInfo.grade.equals('Master') && korCtg.equals('공지사항')}">
							<tr>
								<td>공지유무</td>
								<c:choose>
									<c:when test="${obj.noticeChk.equals('off')}">
										<td>
											<input type="checkbox" id="noticeChk" class="noticeChk" name="noticeChk">
										</td>
									</c:when>
									<c:otherwise>
										<td>
											<input type="checkbox" id="noticeChk" class="noticeChk" name="noticeChk" checked>
										</td>
									</c:otherwise>
								</c:choose>
							</tr>
							</c:if>
							<tr>
								<td align="center">내용</td>
								<td><textarea name="textarea" id="textarea"
										class="textarea">${obj.content}</textarea></td>
							</tr>
						</tbody>
					</table>
				</div>
				<!-- 파일업로드 -->
			<div class="form-group">
				<label for="file" class="col-sm-2 control-label">파일첨부</label>
				<div class="col-sm-10">
					<input type="file" class="form-control" id="file" name="file" multiple/>

					<!-- 첨부파일 리스트가 존재할 경우만 삭제할 항목 선택 가능 -->
					<c:if test="${fileList != null}">
						<c:forEach var="file" items="${fileList}">
							<!-- 이미지를 다운받기 위한 URL 구성 -->
							<c:url value="/download.do" var="downloadUrl">
								<c:param name="file" value="${file.fileDir}/${file.fileName}" />
							</c:url>

							<div class="checkbox">
								<label>
									<input type="checkbox" name="del_file" id="img_del" value="${file.fileId}"/>
									${file.orginName} 삭제하기
									<a href="${downloadUrl}">[다운받기]</a>
								</label>
							</div>
						</c:forEach>
					</c:if>
				</div>
			</div>
				<div class="btn-item">
					<button type="submit" class="btn btn-primary">등록</button>
					<button id="btn-cls" class="btn btn-default" type="button">취소</button>
				</div>
			</form>
			<!-- 게시물 수정 form 끝 -->
		</section>
		<!-- 본문 section 끝 -->
	</section>
	<!-- content 끝 -->
</div>
 <!-- top button -->
        <button type="button" class="btn_top bg_color1 color8">
            <span class="glyphicon glyphicon-menu-up"></span>
        </button>
        <!-- //top button -->
    </div>
</section>
<!-- //contents -->
<%@ include file="/WEB-INF/inc/Footer.jsp" %>
</div>
<!-- // 전체를 감싸는 div -->
<%@ include file="/WEB-INF/inc/AllmenuModal.jsp" %>
<%@ include file="/WEB-INF/inc/CommonScript.jsp" %>
<script src="${pageContext.request.contextPath}/plugins/validate/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/plugins/ajax-form/jquery.form.min.js"></script>
<script src="https://cdn.ckeditor.com/4.11.3/standard/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/community_edit.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/regex.js"></script><!-- searchbar를 사용하는 경우 장착 -->
<script src="${pageContext.request.contextPath}/assets/js/searchbar.js"></script><!-- searchbar를 사용하는 경우 장착 -->
<script>
	// CKEDITOR 속성 편집
	CKEDITOR.config.resize_enabled = false;
	CKEDITOR.config.enterMode = CKEDITOR.ENTER_BR;
	CKEDITOR.config.shiftEnterMode = CKEDITOR.ENTER_P;
	CKEDITOR.config.fillEmptyBlocks = false;
	CKEDITOR.config.allowedContent = true;
	CKEDITOR.config.uiColor = "#7ae8b8";
	// 툴바 소스보기 기능 삭제
	CKEDITOR.config.removePlugins = "sourcearea";

	// 이미지 업로드 기능 추가
	CKEDITOR.replace( "textarea", {
		filebrowserUploadUrl: "${pageContext.request.contextPath}/imageUpload.do"
    });

	window.parent.CKEDITOR.tools.callFunction('${CKEditorFuncNum}', '${url}', '파일 전송 완료.');

	$("#title").focus();

	var korCtg = "${korCtg}";
</script>
</body>
</html>
