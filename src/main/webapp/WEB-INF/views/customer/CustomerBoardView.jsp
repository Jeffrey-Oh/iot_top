<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/inc/Head.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/subpage.css" />
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/searchbar.css"><!-- searchbar를 사용하는 경우 장착 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/customer-view.css">
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
		<!-- 본문 section 시작 -->
		<section class="section">
			<!-- 게시물 시작 -->
			<div class="bd-table">
				<!-- 타이틀 박스 시작 -->
				<table class="table table-bordered">
					<tbody>
						<tr>
							<th class="text-center">카테고리</th>
							<td>${item.korCtg}</td>
						</tr>
						<tr>
							<th class="text-center">작성자</th>
							<td>${item.userName}</td>
						</tr>
						<tr>
							<th class="text-center">제목</th>
							<td>${item.title}</td>
						</tr>
						<tr>
							<th class="text-center">작성일</th>
							<td>${item.editDate}</td>
						</tr>
						<tr>
							<th class="text-center">내용</th>
							<td class="view-content">${item.content}</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- 게시물 끝 -->
			<div class="button-group">
				<a href="${pageContext.request.contextPath}/customer/CustomerBoardList.do"><button type="button" class="btn btn-primary">목록</button></a>
			</div>
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
<script src="${pageContext.request.contextPath}/assets/js/regex.js"></script><!-- searchbar를 사용하는 경우 장착 -->
<script src="${pageContext.request.contextPath}/assets/js/searchbar.js"></script><!-- searchbar를 사용하는 경우 장착 -->
</body>
</html>