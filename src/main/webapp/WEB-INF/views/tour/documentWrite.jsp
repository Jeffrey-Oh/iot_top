<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page trimDirectiveWhitespaces="true"%>
<!doctype html>
<html>

<head>
	<!-- Bootstrap + jQuery -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	<script src="http://code.jquery.com/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
	<!--// Bootstrap + jQuery -->

	<%-- 개인 CSS 참조  --%>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/TourDocument.css">
</head>

<body>
	<!--// 글 목록 끝 -->
	<div class="container">
		<h1 class="page-header"> <small> 관광 후기 게시판 </small></h1>

		<!-- 글 목록 시작 -->
		<div class="table-responsive">
			<table class="table table-hover">
				<thead>
					<tr>
						<th class="text-center" style="width: 100px">번호</th>
						<th class="text-center">제목</th>
						<th class="text-center" style="width: 120px">작성자</th>
						<th class="text-center" style="width: 100px">조회수</th>
						<th class="text-center" style="width: 120px">작성일</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${fn:length(documentList) > 0}">
							<c:forEach var="document" items="${documentList}">
								<tr>
									<td class="text-center">${document.id}</td>
									<td>
										<c:url var="readUrl" value="/bbs/document_read.do">
											<c:param name="category" value="${document.category}" />
											<c:param name="document_id" value="${document.id}" />
										</c:url>
										<a href="${readUrl}">${document.subject}</a>
									</td>
									<td class="text-center">${document.writerName}</td>
									<td class="text-center">${document.hit}</td>
									<td class="text-center">${document.regDate }</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="5" class="text-center" style="line-height: 100px;">
									조회된 글이 없습니다.</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
		</div>

		<br /><br /><br />
		<hr />
		<hr />

		<!-- 버튼들 -->
		<div class="form-group bottomSection">
			<div class="col-sm-offset-2 col-sm-10">
				<form class="" action="index.html" method="post">
					<input type="text" name="" value="" />
					<button type="submit" name="button" class="btn-sm btn-info">검색</button>
				</form>
				<a type="button" class="btn btn-primary" href="${pageContext.request.contextPath}/tour/documentWriter.do">게시글 작성하기</a>
				<a type="button" class="btn btn-danger">목록 보기</a>
			</div>
		</div>
</body>

</html>
