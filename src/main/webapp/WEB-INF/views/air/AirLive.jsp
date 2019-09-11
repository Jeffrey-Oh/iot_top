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
	href="${pageContext.request.contextPath}/assets/css/subpage.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/air-common.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/airlive.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/airlive_responsive.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/datepicker.min.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/sweetalert/sweetalert2.min.css" />
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
</head>
<body>
	<!-- 전체를 감싸는 div -->
	<div class="container-fluid">
		<%@ include file="/WEB-INF/inc/Gnb.jsp"%>
		<!-- visual -->
		<section id="visual">
			<article class="bg"></article>
			<div class="bg-text">Let us take you away</div>
		</section>
		<!-- //visual -->

		<!-- search bar -->
		<section id="searchbar" class="container radius5"
			style="background-color: rgba(0, 0, 0, 0);">
			<div class="home_search">
				<div class="container">
					<div class="row">
						<div class="col">
							<div class="home_search_container">
								<form action="AirLive.do" class="home_search_form"
									id="home_search_form">
									<div class="home_search_title">
										<label for="domestic-label"><span id="domestic-label">국내선</span>
										</label>
									</div>
									<div class="home_search_content">
										<div>
											<input type="text" id="boardingKor" name="boardingKor" class="search_input search_input_2" placeholder="출발지" />
											<input type="text" id="arrivedKor" name="arrivedKor" class="search_input search_input_2" placeholder="도착지">
											<input type="text" id="airlineKorean" name="airlineKorean" class="search_input search_input_2" placeholder="항공사">
											<input type="text" id="airFln" name="airFln" class="search_input search_input_2" placeholder="편명">
											<button type="submit" class="home_search_button">search</button>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
		<!-- //search bar -->

		<!-- contents -->
		<section id="contents">
			<h2 class="blind">본문</h2>
			<div class="container">
				<!-- 메인 컨텐츠 삽입 -->
				<!-- content 감싸는 영역 -->
				<div class="content-parent">

					<!-- Destinations -->

					<div class="destinations" id="destinations">
						<div class="container">
							<div class="row">
								<div class="col text-center subtitle">
									<div class="section_subtitle">simply amazing places</div>
									<div class="section_title">
										<h2>Popular Destinations</h2>
									</div>
								</div>
							</div>
							<blockquote class="bq-text pull-right color2">※ 이전
								기록에 대한 자료 분석 결과 실시간 현황이 항상 일치하지 않을 수 있습니다.<br/>2019-09-12 기준 데이터입니다.</blockquote>
							<div class="live-table">
								<table>
									<tr>
										<th class="tg-c3ow">항공사</th>
										<th class="tg-0pky">편명</th>
										<th class="tg-0pky">출발지->도착지</th>
										<th class="tg-0pky">계획</th>
										<th class="tg-0pky">예상</th>
										<th class="tg-0pky">현황</th>
									</tr>
									<%-- <c:choose>
										<c:when test="${liveChk == false}"> --%>
											<c:forEach var="list" items="${list}">
												<tr>
													<td class="tg-0pky">${list.airlineKorean}</td>
													<td class="tg-0pky">${list.airFln}</td>
													<td class="tg-0pky">${list.airport}(${list.boardingKor})<br />->
														${list.city}(${list.arrivedKor})
													</td>
													<c:choose>
														<c:when test="${list.std != null}">
															<td class="tg-0pky">${list.std}</td>
														</c:when>
														<c:otherwise>
															<td class="tg-0pky">미정</td>
														</c:otherwise>
													</c:choose>
													<c:choose>
														<c:when test="${list.etd != null}">
															<td class="tg-0pky">${list.etd}</td>
														</c:when>
														<c:otherwise>
															<td class="tg-0pky">미정</td>
														</c:otherwise>
													</c:choose>
													<c:choose>
														<c:when test="${list.rmkKor == null}">
															<td class="tg-0pky"><span class="badge">-</span></td>
														</c:when>
														<c:otherwise>
															<td class="tg-0pky"><span class="badge">${list.rmkKor}</span></td>
														</c:otherwise>
													</c:choose>
												</tr>
											</c:forEach>
										<%-- </c:when> --%>
										<%-- <c:otherwise>
											<tr>
												<td class="tg-0pky" colspan="6">00:00 ~ 06:00 비행 준비 중</td>
											</tr>
										</c:otherwise>
									</c:choose> --%>
								</table>


								<!-- 페이징 시작 -->
								<div class="list-number">
									<nav class="text-center">
										<ul class="list-unstyled pagination">
											<c:choose>
												<c:when test="${pageHelper.prevPage > 0}">
													<c:url var="firstUrl" value="/AirLive.do">
														<c:param name="boardingKor" value="${boardingKor}" />
														<c:param name="arrivedKor" value="${arrivedKor}" />
														<c:param name="airlineKorean" value="${airlineKorean}" />
														<c:param name="airFln" value="${airFln}" />
														<c:param name="list" value="1" />
													</c:url>
													<c:url var="prevUrl" value="/AirLive.do">
														<c:param name="boardingKor" value="${boardingKor}" />
														<c:param name="arrivedKor" value="${arrivedKor}" />
														<c:param name="airlineKorean" value="${airlineKorean}" />
														<c:param name="airFln" value="${airFln}" />
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
												<c:url var="pageUrl" value="/AirLive.do">
													<c:param name="boardingKor" value="${boardingKor}" />
													<c:param name="arrivedKor" value="${arrivedKor}" />
													<c:param name="airlineKorean" value="${airlineKorean}" />
													<c:param name="airFln" value="${airFln}" />
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
													<c:url var="lastUrl" value="/AirLive.do">
														<c:param name="boardingKor" value="${boardingKor}" />
														<c:param name="arrivedKor" value="${arrivedKor}" />
														<c:param name="airlineKorean" value="${airlineKorean}" />
														<c:param name="airFln" value="${airFln}" />
														<c:param name="list" value="${pageHelper.totalPage}" />
													</c:url>
													<c:url var="nextUrl" value="/AirLive.do">
														<c:param name="boardingKor" value="${boardingKor}" />
														<c:param name="arrivedKor" value="${arrivedKor}" />
														<c:param name="airlineKorean" value="${airlineKorean}" />
														<c:param name="airFln" value="${airFln}" />
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
								<!-- 페이징 끝 -->
							</div>
						</div>
					</div>
				</div>
				<div class="banner">
					<img
						src="${pageContext.request.contextPath}/images/common/banner.jpg" />
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
	<script
		src="${pageContext.request.contextPath}/plugins/OwlCarousel2-2.2.1/owl.carousel.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/validate/jquery.validate.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/sweetalert/sweetalert2.all.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/assets/js/ezen-helper.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/air_live.js"></script>
</body>
</html>