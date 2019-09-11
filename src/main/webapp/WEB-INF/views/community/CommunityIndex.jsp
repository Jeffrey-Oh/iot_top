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
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css">
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
														<blockquote class="bq-text pull-right color2">※ 욕설 및 커뮤니티에 어울리지 않는 게시물은 검토 후 삭제될 수 있습니다.</blockquote>
						</div>
						<!-- content-header 끝 -->
						<!-- side-menu 시작 -->
						<aside class="aside">
							<nav class="side-menu">
								<!-- li 태그에 span 비워둠 i 태그로 새로운 글 생성 시 New 표시할 껀지.... -->
								<ul class="list-unstyled ctg" id="ctg">
									<li><a href="${pageContext.request.contextPath}/CommIndex.do">전체 글 보기<span>(${totalCountAll})
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
						<section class="section">
							<c:choose>
								<c:when test="${korCtg == null}">
									<h1>전체 글 보기</h1>
								</c:when>
								<c:otherwise>
									<h1>${korCtg}</h1>
								</c:otherwise>
							</c:choose>
							<!-- 검색 BOX 시작 -->
							<div class="search-box clearfix">
								<!-- 검색 종류 시작 -->
								<div class="search-dropbox">
									<select name="search-select" id="search-title">
										<option value="제목%2B내용">제목+내용</option>
										<option value="제목">제목</option>
										<option value="작성자">작성자</option>
									</select>
								</div>
								<!-- 검색 종류 끝 -->
								<!-- 검색어 form 시작 -->
								<form action="CommIndex.do"
									method="get" name="form1" id="form1" class="form1">
									<!-- 검색어 입력할 input 태그 -->
									<div class="search">
										<input type="text" name="search-word" id="search-text"
											class="search-text" placeholder="입력하세요." value="${searchWord}">
										<button type="submit" id="btn-search-i" class="btn-search-i"></button>
									</div>
									<!-- 검색 종류 파라미터 -->
									<input type="hidden" name="type" id="type" value="제목%2B내용" />
								</form>
								<!-- 검색어 form 끝 -->
								<!-- 로그인+카테고리 내+공지사항이 아닌경우 글쓰기 버튼 활성화 -->
								<c:if test="${loginInfo != null && korCtg != null && !korCtg.equals('공지사항') && !loginInfo.grade.equals('Master')}">
									<div class="btn-add btn_color2">
										<a href="${pageContext.request.contextPath}/CommAdd.do?category=${category}">글쓰기</a>
									</div>
								</c:if>

								<!-- 로그인(마스터) -->
								<c:if test="${loginInfo != null && loginInfo.grade.equals('Master') && korCtg != null && korCtg == '공지사항'}">
								<div class="btn-add btn_color2">
									<a href="${pageContext.request.contextPath}/CommAdd.do?category=${category}">글쓰기</a>
								</div>
								</c:if>
							</div>
							<!-- 검색 BOX 끝 -->
							<!-- 게시글 조회 결과 시작 -->
							<div class="table-responsive">
								<table id="bd-table" class="bd-table">
									<tr class="bg_color5 color3">
										<th>번호</th>
										<th>카테고리</th>
										<th>제목</th>
										<th>작성자</th>
										<th>조회수</th>
										<th>작성일</th>
									</tr>

									<!-- 공지사항 목록 -->
									<c:choose>
										<c:when test="${fn:length(noticeList) != 0}">
											<c:forEach var="k" items="${noticeList}" varStatus="s">
												<tr style="background-color: #fbfcd3">
													<!--
														게시글 번호 역순으로 표시
														여기서 게시글 번호는 DB에 있는 게시물 일련번호와 다름
													-->
													<td><span><i class="fas fa-thumbtack"></i></span></td>
													<td>${k.korCtg}</td>
													<!-- 게시글 조회할 경우 게시글 일련번호를 파라미터로 넘김 -->
													<td>
													<c:choose>
														<c:when test="${fn:length(k.title) <= 20}">
															<span class="list-query-add"> <a
																href="${pageContext.request.contextPath}/CommView.do?boardId=${k.boardId}&category=${k.category}">${k.title}</a>
															</span>
														</c:when>
														<c:otherwise>
															<span id="title-flow" class="title-flow list-query-add">
																<a
																href="${pageContext.request.contextPath}/CommView.do?boardId=${k.boardId}&category=${k.category}">${k.title}</a>
															</span>
														</c:otherwise>
													</c:choose>
														<!--
															// Date를 기준으로 작성 및 수정날짜가 00:00일 때
															// 이미지 N 표시
														-->
								 						<c:if test="${year == k.editDate}">
															<img
																src="${pageContext.request.contextPath}/images/common/new.png">
														</c:if></td>
													<td>${k.userName}</td>
													<td>${k.boardCount}</td>
													<td>${k.editDate}</td>
												</tr>
											</c:forEach>
										</c:when>
									</c:choose>

									<!-- 일반 게시물 목록 -->
									<c:choose>
										<c:when test="${fn:length(list) != 0}">
											<c:forEach var="k" items="${list}" varStatus="s">
												<tr>
													<!--
														게시글 번호 역순으로 표시
														여기서 게시글 번호는 DB에 있는 게시물 일련번호와 다름
													-->
													<td>${totalCount - s.index - pageHelper.limitStart + countNotice + 1}</td>
													<td>${k.korCtg}</td>
													<!-- 게시글 조회할 경우 게시글 일련번호를 파라미터로 넘김 -->
													<td>
													<c:choose>
														<c:when test="${fn:length(k.title) <= 20}">
															<span class="list-query-add"> <a
																href="${pageContext.request.contextPath}/CommView.do?boardId=${k.boardId}&category=${k.category}">${k.title}</a>
															</span>
														</c:when>
														<c:otherwise>
															<span id="title-flow" class="title-flow list-query-add">
																<a
																href="${pageContext.request.contextPath}/CommView.do?boardId=${k.boardId}&category=${k.category}">${k.title}</a>
															</span>
														</c:otherwise>
													</c:choose>
														<!--
															// Date를 기준으로 작성 및 수정날짜가 00:00일 때
															// 이미지 N 표시
														-->
								 						<c:if test="${year == k.editDate}">
															<img
																src="${pageContext.request.contextPath}/images/common/new.png">
														</c:if></td>
													<td>${k.userName}</td>
													<td>${k.boardCount}</td>
													<td>${k.editDate}</td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
											<!-- 조회된 게시물이 없는 경우 -->
											<tr>
												<td colspan="6">게시물이 없습니다.</td>
											</tr>
										</c:otherwise>
									</c:choose>
								</table>
							</div>
							<!-- 게시글 조회 결과 끝 -->
							<!-- 게시판 페이징 시작 -->
							<div class="list-number">
								<nav class="text-center">
									<ul class="list-unstyled pagination">
										<c:choose>
											<c:when test="${pageHelper.prevPage > 0}">
												<c:url var="firstUrl" value="/CommIndex.do">
													<c:param name="category" value="${bbsName}" />
													<c:param name="type" value="${type}" />
													<c:param name="search-word" value="${searchWord}" />
													<c:param name="list" value="1" />
												</c:url>
												<c:url var="prevUrl" value="/CommIndex.do">
													<c:param name="category" value="${bbsName}" />
													<c:param name="type" value="${type}" />
													<c:param name="search-word" value="${searchWord}" />
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

										<c:forEach var="i" begin="${pageHelper.startPage}" end="${pageHelper.endPage}">
											<c:url var="pageUrl" value="/CommIndex.do">
												<c:param name="category" value="${bbsName}" />
												<c:param name="type" value="${type}" />
												<c:param name="search-word" value="${searchWord}" />
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
												<c:url var="lastUrl" value="/CommIndex.do">
													<c:param name="category" value="${bbsName}" />
													<c:param name="type" value="${type}" />
													<c:param name="search-word" value="${searchWord}" />
													<c:param name="list" value="${pageHelper.totalPage}" />
												</c:url>
												<c:url var="nextUrl" value="/CommIndex.do">
													<c:param name="category" value="${bbsName}" />
													<c:param name="type" value="${type}" />
													<c:param name="search-word" value="${searchWord}" />
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
		<%@ include file="/WEB-INF/inc/Footer.jsp"%>
	</div>
	<!-- // 전체를 감싸는 div -->
	<%@ include file="/WEB-INF/inc/AllmenuModal.jsp"%>
	<%@ include file="/WEB-INF/inc/CommonScript.jsp"%>
	<script src="${pageContext.request.contextPath}/assets/js/community_index.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/regex.js"></script><!-- searchbar를 사용하는 경우 장착 -->
	<script src="${pageContext.request.contextPath}/assets/js/searchbar.js"></script><!-- searchbar를 사용하는 경우 장착 -->

	<script>
		var korCtg = "${korCtg}";
		var nowPage = "${nowPage}";
	</script>
</body>
</html>
