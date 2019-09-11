<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/inc/Head.jsp"%>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/searchbar.css">
<!-- searchbar를 사용하는 경우 장착 -->
<!-- mainpage.css || subpage.css 중 1개는 꼭 참조 해야 함. -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/subpage.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/customer-list.css" />
</head>
<body>
	<!-- 전체를 감싸는 div -->
	<div class="container-fluid">
		<%@ include file="/WEB-INF/inc/Gnb.jsp"%>
		<%@ include file="/WEB-INF/inc/Visual.jsp"%>
		<%@ include file="/WEB-INF/inc/Searchbar.jsp"%><!-- 빠른검색(css, js, regex.js 필요) -->
		<!-- contents -->
		<section id="contents">
			<h2 class="blind">본문</h2>
			<div class="container">
				<!-- 메인 컨텐츠 삽입 -->
				<!-- 여기에 contents 삽입 시작 -->
				<h1 class="section_title">문의 내역 확인</h1>
				<c:if test="${loginInfo != null}">
					<div class="write-item"><a href="${pageContext.request.contextPath}/MailForm.do"><button type="button" class="btn btn-primary">글쓰기</button></a></div>
				</c:if>
				<!-- 글 목록 시작 -->
				<div class="mail-list">
					<table class="table table-hover text-center">
						<tbody>
							<tr>
								<th class="text-center" style="width: 100px">번호</th>
								<th class="text-center" style="width: 100px">분류</th>
								<th class="text-center" style="width: 370px">제목</th>
								<th class="text-center" style="width: 100px">등록일</th>
								<th class="text-center" style="width: 120px">조회수</th>
							</tr>
							<c:choose>
								<c:when test="${fn:length(getItemAll) != 0}">
									<c:forEach var="k" items="${getItemAll}" varStatus="s">
										<tr>
											<td>${totalCount - s.index - pageHelper.limitStart}</td>
											<td>${k.korCtg}</td>
											<!-- 게시글 조회할 경우 게시글 일련번호를 파라미터로 넘김 -->
											<td class="text-left"><c:choose>
													<c:when test="${fn:length(k.title) <= 20}">
														<span class="list-query-add"> <a
															href="${pageContext.request.contextPath}/customer/CustomerBoardView.do?boardId=${k.boardId}&category=${k.category}">${k.title}</a>
														</span>
													</c:when>
													<c:otherwise>
														<td class="text-left"><span id="title-flow"
															class="title-flow list-query-add"> <a
																href="${pageContext.request.contextPath}/customer/CustomerBoardView.do?boardId=${k.boardId}&category=${k.category}">${k.title}</a>
														</span>
													</c:otherwise>
												</c:choose> <c:if test="${year == k.regDate}">
													<img
														src="${pageContext.request.contextPath}/images/common/new.png">
												</c:if></td>
											<td>${k.regDate}</td>
											<td>${k.boardCount}</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="5" class="text-center"
											style="line-height: 100px;">조회된 글이 없습니다.</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>
				</div>
				<!-- // 글 목록 끝 -->
				<!-- 게시판 페이징 시작 -->
				<div class="list-number">
					<nav class="text-center">
						<ul class="list-unstyled pagination">
							<c:choose>
								<c:when test="${pageHelper.prevPage > 0}">
									<c:url var="firstUrl" value="/customer/CustomerBoardList.do">
										<c:param name="list" value="1" />
									</c:url>
									<c:url var="prevUrl" value="/customer/CustomerBoardList.do">
										<c:param name="list" value="${pageHelper.prevPage}" />
									</c:url>
									<li><a href="${firstUrl}">&laquo;</a></li>
									<li><a href="${prevUrl}">&lt;</a></li>
								</c:when>

								<c:otherwise>
									<li class="disabled"><a href="#">&laquo;</a></li>
									<li class="disabled"><a href="#">&lt;</a></li>
								</c:otherwise>
							</c:choose>

							<c:forEach var="i" begin="${pageHelper.startPage}"
								end="${pageHelper.endPage}">
								<c:url var="pageUrl" value="/customer/CustomerBoardList.do">
									<c:param name="list" value="${i}" />
								</c:url>

								<c:choose>
									<c:when test="${pageHelper.page == i}">
										<li class="active"><a href="#">${i}</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="${pageUrl}">${i}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>

							<c:choose>
								<c:when test="${pageHelper.nextPage > 0}">
									<c:url var="lastUrl" value="/customer/CustomerBoardList.do">
										<c:param name="list" value="${pageHelper.totalPage}" />
									</c:url>
									<c:url var="nextUrl" value="/customer/CustomerBoardList.do">
										<c:param name="list" value="${pageHelper.nextPage}" />
									</c:url>
									<li><a href="${nextUrl}">&gt;</a></li>
									<li><a href="${lastUrl}">&raquo;</a></li>
								</c:when>

								<c:otherwise>
									<li class="disabled"><a href="#">&gt;</a></li>
									<li class="disabled"><a href="#">&raquo;</a></li>
								</c:otherwise>
							</c:choose>
						</ul>
					</nav>
				</div>
				<!-- 게시판 페이징 끝 -->


				<!-- 여기에 contents 삽입 끝 -->
				<!-- top button -->
				<button type="button" class="btn_top bg_color1 color8">
					<span class="glyphicon glyphicon-menu-up"></span>
				</button>
				<!-- //top button -->
			</div>
		</section>
		<!-- //contents -->
		<%@ include file="/WEB-INF/inc/Footer.jsp"%>
	</div>
	<!-- // 전체를 감싸는 div -->
	<%@ include file="/WEB-INF/inc/AllmenuModal.jsp"%>
	<%@ include file="/WEB-INF/inc/CommonScript.jsp"%>
</body>

</html>