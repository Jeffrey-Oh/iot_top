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
	href="${pageContext.request.contextPath}/assets/css/airschedule.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/datepicker/datepicker.min.css" />
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
								<form action="AirSchedule.do" class="home_search_form"
									id="home_search_form">
									<div class="home_search_title">
										<label for="domestic-label"><span id="domestic-label">국내선</span>
										</label>
									</div>
									<div class="home_search_content">
										<div class="col-md-10 col-md-push-1">
											<input type="text" id="arrivalCity" name="arrivalCity"
												class="search_input search_input_1" placeholder="목적지">
											<input type="text" id="airlineKorean" name="airlineKorean"
												class="search_input search_input_1" placeholder="항공사">
											<input type="text" id="domesticNum" name="domesticNum"
												class="search_input search_input_1" placeholder="운항편명">
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
					<div class="container">
						<div id="airLS-contents" class="airLS-contents">
							<div class="row">
								<div class="col text-center">
									<div class="section_subtitle color3">Check the schedule
										of travel</div>
									<div class="section_title color3">
										<h2>Air Schedule</h2>
									</div>
								</div>
							</div>
						</div>
						<blockquote class="bq-text pull-right color2">※ 이전
								기록에 대한 자료 분석 결과 운항스케줄이 항상 일치하지 않을 수 있습니다.<br/>2019-09-12 기준 데이터입니다.</blockquote>
						<div class="schedule-table air-s">
							<table>
								<tr>
									<th class="table-header tg-c3ow color8 bg_color3" rowspan="2">운항편명</th>
									<th class="table-header tg-0pky color8 bg_color3" rowspan="2">항공사</th>
									<th class="table-header tg-0pky color8 bg_color3" rowspan="2">출발시간</th>
									<th class="table-header tg-0pky color8 bg_color3" rowspan="2">목적지</th>
									<th class="table-header tg-0pky color8 bg_color3" colspan="7">운항요일</th>
									<th class="table-header tg-0pky color8 bg_color3" rowspan="2">운항기간</th>
								</tr>
								<tr>
									<th class="flight-day w_color color8">월</th>
									<th class="flight-day w_color color8">화</th>
									<th class="flight-day w_color color8">수</th>
									<th class="flight-day w_color color8">목</th>
									<th class="flight-day w_color color8">금</th>
									<th class="flight-day h_color color8">토</th>
									<th class="flight-day h_color color8">일</th>
								</tr>


								<c:choose>
									<c:when test="${list != null}">
										<c:forEach var="list" items="${list}">
											<tr>
												<td>${list.domesticNum}</td>
												<td><span class="${list.logo}"></span>${list.airlineKorean}</td>
												<td>${list.domesticStartTime}</td>
												<td>${list.arrivalCityCode}(${list.arrivalCity})</td>


												<c:choose>
													<c:when test="${list.domesticMon.equals('Y')}">
														<td><span class="w_check"></span></td>
													</c:when>
													<c:otherwise>
														<td></td>
													</c:otherwise>
												</c:choose>

												<c:choose>
													<c:when test="${list.domesticTue.equals('Y')}">
														<td><span class="w_check"></span></td>
													</c:when>
													<c:otherwise>
														<td></td>
													</c:otherwise>
												</c:choose>

												<c:choose>
													<c:when test="${list.domesticWed.equals('Y')}">
														<td><span class="w_check"></span></td>
													</c:when>
													<c:otherwise>
														<td></td>
													</c:otherwise>
												</c:choose>

												<c:choose>
													<c:when test="${list.domesticThu.equals('Y')}">
														<td><span class="w_check"></span></td>
													</c:when>
													<c:otherwise>
														<td></td>
													</c:otherwise>
												</c:choose>

												<c:choose>
													<c:when test="${list.domesticFri.equals('Y')}">
														<td><span class="w_check"></span></td>
													</c:when>
													<c:otherwise>
														<td></td>
													</c:otherwise>
												</c:choose>

												<c:choose>
													<c:when test="${list.domesticSat.equals('Y')}">
														<td><span class="h_check"></span></td>
													</c:when>
													<c:otherwise>
														<td></td>
													</c:otherwise>
												</c:choose>

												<c:choose>
													<c:when test="${list.domesticSun.equals('Y')}">
														<td><span class="h_check"></span></td>
													</c:when>
													<c:otherwise>
														<td></td>
													</c:otherwise>
												</c:choose>

												<td>${list.domesticStdate.substring(0, 11)}~${list.domesticEddate.substring(0, 11)}</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td class="tg-0pky" colspan="6" rowspan="2">조회된 데이터가
												없습니다.</td>
										</tr>
									</c:otherwise>
								</c:choose>

							</table>

							<!-- 페이징 시작 -->
							<div class="list-number">
								<nav class="text-center">
									<ul class="list-unstyled pagination">
										<c:choose>
											<c:when test="${pageHelper.prevPage > 0}">
												<c:url var="firstUrl" value="/AirSchedule.do">
													<c:param name="arrivalCity" value="${arrivalCity}" />
													<c:param name="airlineKorean" value="${airlineKorean}" />
													<c:param name="domesticNum" value="${domesticNum}" />
													<c:param name="list" value="1" />
												</c:url>
												<c:url var="prevUrl" value="/AirSchedule.do">
													<c:param name="arrivalCity" value="${arrivalCity}" />
													<c:param name="airlineKorean" value="${airlineKorean}" />
													<c:param name="domesticNum" value="${domesticNum}" />
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
											<c:url var="pageUrl" value="/AirSchedule.do">
												<c:param name="arrivalCity" value="${arrivalCity}" />
												<c:param name="airlineKorean" value="${airlineKorean}" />
												<c:param name="domesticNum" value="${domesticNum}" />
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
												<c:url var="lastUrl" value="/AirSchedule.do">
													<c:param name="arrivalCity" value="${arrivalCity}" />
													<c:param name="airlineKorean" value="${airlineKorean}" />
													<c:param name="domesticNum" value="${domesticNum}" />
													<c:param name="list" value="${pageHelper.totalPage}" />
												</c:url>
												<c:url var="nextUrl" value="/AirSchedule.do">
													<c:param name="arrivalCity" value="${arrivalCity}" />
													<c:param name="airlineKorean" value="${airlineKorean}" />
													<c:param name="domesticNum" value="${domesticNum}" />
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
		src="${pageContext.request.contextPath}/plugins/datepicker/datepicker.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/datepicker/i18n/datepicker.ko-KR.js"></script>
	<script
		src="${pageContext.request.contextPath}/plugins/validate/jquery.validate.min.js"></script>
	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/air_schedule.js"></script>
</body>
</html>