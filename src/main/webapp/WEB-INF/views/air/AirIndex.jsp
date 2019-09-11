<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/inc/Head.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/subpage.css" />
<link href="${pageContext.request.contextPath}/plugins/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/air-common.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/airindex.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/airindex_responsive.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/datepicker.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/aos/aos.css" />
</head>
<body>
<!-- 전체를 감싸는 div -->
<div class="container-fluid">
<%@ include file="/WEB-INF/inc/Gnb.jsp"%>
<!-- visual -->
<section id="visual">
	<article class="bg"></article>
	<div class="bg-text color8" data-aos="fade-zoom-in" data-aos-offest="100" data-aos-easing="ease-in-sine" data-aos-duration="600">Let us take you away</div>
	<div class="move-text text-right">
		<ul class="list-unstyled">
			<li class="move_1 underline"><a href="#destinations">추천 특가 <span>01</span></a></li>
			<li class="move_2 underline"><a href="#air-l">실시간 항공 <span>02</span></a></li>
			<li class="move_3 underline"><a href="#air-s">운항 스케줄 <span>03</span></a></li>
		</ul>
	</div>
</section>
<!-- //visual -->

<!-- search bar -->
<section id="searchbar" class="container radius5"
	style="background-color: rgba(0, 0, 0, 0);">
	<!-- Search -->

	<div class="home_search">
		<div class="container">
			<div class="row">
				<div class="col">
					<div class="home_search_container">
						<div class="home_search_title">Search for your trip</div>
						<div class="home_search_content">
							<form action="AirSearch.do" class="home_search_form" id="home_search_form" method="get">
								<div>
									<input type="text" class="search_input search_input_1" name="boardingKor" placeholder="Origin" required="required">
									<input type="text" class="search_input search_input_1" name="arrivedKor" placeholder="Destination" required="required">
									<input type="text" id="deptDate" class="search_input search_input_2" name="sdate" placeholder="Departure" required="required" readonly>
									<input type="text" id="arriDate" class="search_input search_input_2" name="edate" placeholder="Arrival" required="required" readonly>
									<button class="home_search_button">search</button>
								</div>
							</form>
						</div>
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
		<!-- Intro -->

		<div class="intro">
			<div class="intro_background"></div> 
			<div class="container">
				<div class="row">
					<div class="col">
						<div class="intro_container border-bottom">
							<div class="row">

								<!-- Intro Item -->
								<div class="col-lg-4 intro_col">
									<div class="intro_item">
										<div class="intro_icon">
											<img src="${pageContext.request.contextPath}/images/air/beach.svg" alt="">
										</div>
										<div class="intro_content">
											<div class="intro_title color3">최고의 휴양지</div>
											<div class="intro_subtitle color1">
												<p>오직 완벽한 여행에서만 알려드리는 최고의 정보</p>
											</div>
										</div>
									</div>
								</div>

								<!-- Intro Item -->
								<div class="col-lg-4 intro_col">
									<div
										class="intro_item">
										<div class="intro_icon">
											<img src="${pageContext.request.contextPath}/images/air/wallet.svg" alt="">
										</div>
										<div class="intro_content">
											<div class="intro_title color3">확실한 적정가 추천</div>
											<div class="intro_subtitle color1">
												<p>여행갈 땐 저렴하게</p>
											</div>
										</div>
									</div>
								</div>

								<!-- Intro Item -->
								<div class="col-lg-4 intro_col">
									<div
										class="intro_item">
										<div class="intro_icon">
											<img src="${pageContext.request.contextPath}/images/air/suitcase.svg" alt="">
										</div>
										<div class="intro_content">
											<div class="intro_title color3">여행에 필요한 정보</div>
											<div class="intro_subtitle color1">
												<p>다양한 여행 정보를 완벽한 여행에서 한눈에</p>
											</div>
										</div>
									</div>
								</div>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="destinations" id="destinations">
			<div class="container border-bottom">
				<div class="row">
					<div class="col text-center">
						<div class="section_subtitle">simply amazing places</div>
						<div class="section_title">
							<h2>Popular Destinations</h2>
						</div>
					</div>
				</div>
				<div class="row destinations_row">
					<div class="col">
						<div class="destinations_container item_grid">

							<!-- Destination -->
							<div class="destination item col-md-4 col-sm-6 col-xs-12" data-aos="fade-up-right" data-aos-offest="100" data-aos-easing="ease-in-sine" data-aos-duration="200">
								<a href="${pageContext.request.contextPath}/AirSearch.do?tab=oneway&sdate=${date}&arrivedKor=${airHotList[0].arrivalCity}">
									<div class="destination_image">
										<img src="${pageContext.request.contextPath}${airHotList[0].imageUrl}" width="358" height="274">
										<div class="spec_offer text-center">
											<span>Special Offer</span>
										</div>
									</div>
									<div class="destination_content">
										<div class="destination_title">${airHotList[0].title}</div>
										<div class="destination_subtitle">
											<p>${airHotList[0].content}</p>
										</div>
										<div class="destination_price badge">${airHotList[0].arrivalCity} - 가격 알아보기</div>
									</div>
								</a>
							</div>

							<!-- Destination -->
							<div class="destination item col-md-4 col-sm-6 col-xs-12" data-aos="fade-up" data-aos-offest="100" data-aos-easing="ease-in-sine" data-aos-duration="200">
								<a href="${pageContext.request.contextPath}/AirSearch.do?tab=oneway&sdate=${date}&arrivedKor=${airHotList[1].arrivalCity}">
									<div class="destination_image">
										<img src="${pageContext.request.contextPath}${airHotList[1].imageUrl}" width="358" height="274">
									</div>
									<div class="destination_content">
										<div class="destination_title">${airHotList[1].title}</div>
										<div class="destination_subtitle">
											<p>${airHotList[1].content}</p>
										</div>
										<div class="destination_price badge">${airHotList[1].arrivalCity} - 가격 알아보기</div>
									</div>
								</a>
							</div>

							<!-- Destination -->
							<div class="destination item col-md-4 col-sm-6 col-xs-12" data-aos="fade-up-left" data-aos-offest="100" data-aos-easing="ease-in-sine" data-aos-duration="200">
								<a href="${pageContext.request.contextPath}/AirSearch.do?tab=oneway&sdate=${date}&arrivedKor=${airHotList[2].arrivalCity}">
									<div class="destination_image">
										<img src="${pageContext.request.contextPath}${airHotList[2].imageUrl}" width="358" height="274">
									</div>
									<div class="destination_content">
										<div class="destination_title">${airHotList[2].title}</div>
										<div class="destination_subtitle">
											<p>${airHotList[2].content}</p>
										</div>
										<div class="destination_price badge">${airHotList[2].arrivalCity} - 가격 알아보기</div>
									</div>
								</a>
							</div>

							<!-- Destination -->
							<div class="destination item col-md-4 col-sm-6 col-xs-12" data-aos="fade-up-right" data-aos-offest="100" data-aos-easing="ease-in-sine" data-aos-duration="200">
								<a href="${pageContext.request.contextPath}/AirSearch.do?tab=oneway&sdate=${date}&arrivedKor=${airHotList[3].arrivalCity}">
									<div class="destination_image">
										<img src="${pageContext.request.contextPath}${airHotList[3].imageUrl}" width="358" height="274">
									</div>
									<div class="destination_content">
										<div class="destination_title">${airHotList[3].title}</div>
										<div class="destination_subtitle">
											<p>${airHotList[3].content}</p>
										</div>
										<div class="destination_price badge">${airHotList[3].arrivalCity} - 가격 알아보기</div>
									</div>
								</a>
							</div>

							<!-- Destination -->
							<div class="destination item col-md-4 col-sm-6 col-xs-12" data-aos="fade-up" data-aos-offest="100" data-aos-easing="ease-in-sine" data-aos-duration="200">
								<a href="${pageContext.request.contextPath}/AirSearch.do?tab=oneway&sdate=${date}&arrivedKor=${airHotList[4].arrivalCity}">
									<div class="destination_image">
										<img src="${pageContext.request.contextPath}${airHotList[4].imageUrl}" width="358" height="274">
									</div>
									<div class="destination_content">
										<div class="destination_title">${airHotList[4].title}</div>
										<div class="destination_subtitle">
											<p>${airHotList[4].content}</p>
										</div>
										<div class="destination_price badge">${airHotList[4].arrivalCity} - 가격 알아보기</div>
									</div>
								</a>
							</div>

							<!-- Destination -->
							<div class="destination item col-md-4 col-sm-6 col-xs-12" data-aos="fade-up-left" data-aos-offest="100" data-aos-easing="ease-in-sine" data-aos-duration="200">
								<a href="${pageContext.request.contextPath}/AirSearch.do?tab=oneway&sdate=${date}&arrivedKor=${airHotList[5].arrivalCity}">
									<div class="destination_image">
										<img src="${pageContext.request.contextPath}${airHotList[5].imageUrl}" width="358" height="274">
									</div>
									<div class="destination_content">
										<div class="destination_title">${airHotList[5].title}</div>
										<div class="destination_subtitle">
											<p>${airHotList[5].content}</p>
										</div>
										<div class="destination_price badge">${airHotList[5].arrivalCity} - 가격 알아보기</div>
									</div>
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- Air Live & Schedule -->
        <div class="LSimg">
            <div class="airLS" id="airLS">
                <div class="row">
                    <div class="col text-center airLS-text">
                        <div class="section_subtitle color8" data-aos="fade-up" data-aos-offset="300" data-aos-easing="ease" data-aos-duration="500">simply amazing places</div>
                        <div class="section_title color8" data-aos="fade-up" data-aos-offset="300" data-aos-easing="ease" data-aos-duration="800">
                            <h2>Air Live &amp; Schedule</h2>
                        </div>
                    </div>
                </div>
                <div class="row testimonials_row">
                    <div class="col">
                        <!-- 아이디어 없나요 -->
                        <div class="tech-slideshow">
							<div class="mover-1"></div>
						</div>
                    </div>
                </div>
            </div>
        </div>
        <div class="container margin_top600">
            <div id="air-l" class="airLS-contents padding_100 border-bottom">
                <div class="row">
                    <div class="col text-center">
                        <div class="section_subtitle color3">find flight status in live</div>
                        <div class="section_title color3">
                            <h2 style="margin-bottom: 20px;">Air Live</h2>
                            <h5>2019-09-12 기준 데이터입니다.</h5>
                        </div>
                    </div>
                </div>
            </div>
            <div class="air-table air-l">
                <table>
					<tr>
						<th class="tg-c3ow color8 bg_color3">항공사</th>
						<th class="tg-0pky color8 bg_color3">편명</th>
						<th class="tg-0pky color8 bg_color3">출발지->도착지</th>
						<th class="tg-0pky color8 bg_color3">계획</th>
						<th class="tg-0pky color8 bg_color3">예상</th>
						<th class="tg-0pky color8 bg_color3">현황</th>
					</tr>
					<%-- <c:choose> --%>
						<%-- <c:when test="${liveChk == false}"> --%>
							<c:forEach var="liveList" items="${liveList}">
								<tr>
									<td class="tg-0pky">${liveList.airlineKorean}</td>
									<td class="tg-0pky">${liveList.airFln}</td>
									<td class="tg-0pky">${liveList.airport}(${liveList.boardingKor})<br />->
										${liveList.city}(${liveList.arrivedKor})
									</td>
									<c:choose>
										<c:when test="${liveList.std != null}">
											<td class="tg-0pky">${liveList.std}</td>
										</c:when>
										<c:otherwise>
											<td class="tg-0pky">미정</td>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${liveList.etd != null}">
											<td class="tg-0pky">${liveList.etd}</td>
										</c:when>
										<c:otherwise>
											<td class="tg-0pky">미정</td>
										</c:otherwise>
									</c:choose>
									<c:choose>
										<c:when test="${liveList.rmkKor == null}">
											<td class="tg-0pky"><span class="badge">-</span></td>
										</c:when>
										<c:otherwise>
											<td class="tg-0pky"><span class="badge">${liveList.rmkKor}</span></td>
										</c:otherwise>
									</c:choose>
								</tr>
							</c:forEach>
						<%-- </c:when> --%>
						<%-- <c:otherwise>
							<tr>
								<td class="tg-0pky" colspan="6">00:00 ~ 06:00 비행 준비 중</td>
							</tr>
						</c:otherwise> --%>
					<%-- </c:choose> --%>
				</table>
                <div class="air-more btn btn-primary btn-lg"><a href="AirLive.do">더 알아보기</a></div>
            </div>
        </div>
        <div class="container">
            <div id="air-s" class="airLS-contents padding_100 border-bottom">
                <div class="row">
                    <div class="col text-center">
                        <div class="section_subtitle color3">Check the schedule of travel</div>
                        <div class="section_title color3">
                            <h2 style="margin-bottom: 20px;">Air Schedule</h2>
                            <h5>2019-09-12 기준 데이터입니다.</h5>
                        </div>
                    </div>
                </div>
            </div>
            <div class="air-table air-s">
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
						<c:when test="${domesticList != null}">
							<c:forEach var="domesticList" items="${domesticList}">
								<tr>
									<td>${domesticList.domesticNum}</td>
									<td><span class="${domesticList.logo}"></span>${domesticList.airlineKorean}</td>
									<td>${domesticList.domesticStartTime}</td>
									<td>${domesticList.arrivalCityCode}(${domesticList.arrivalCity})</td>


									<c:choose>
										<c:when test="${domesticList.domesticMon.equals('Y')}">
											<td><span class="w_check"></span></td>
										</c:when>
										<c:otherwise>
											<td></td>
										</c:otherwise>
									</c:choose>

									<c:choose>
										<c:when test="${domesticList.domesticTue.equals('Y')}">
											<td><span class="w_check"></span></td>
										</c:when>
										<c:otherwise>
											<td></td>
										</c:otherwise>
									</c:choose>

									<c:choose>
										<c:when test="${domesticList.domesticWed.equals('Y')}">
											<td><span class="w_check"></span></td>
										</c:when>
										<c:otherwise>
											<td></td>
										</c:otherwise>
									</c:choose>

									<c:choose>
										<c:when test="${domesticList.domesticThu.equals('Y')}">
											<td><span class="w_check"></span></td>
										</c:when>
										<c:otherwise>
											<td></td>
										</c:otherwise>
									</c:choose>

									<c:choose>
										<c:when test="${domesticList.domesticFri.equals('Y')}">
											<td><span class="w_check"></span></td>
										</c:when>
										<c:otherwise>
											<td></td>
										</c:otherwise>
									</c:choose>

									<c:choose>
										<c:when test="${domesticList.domesticSat.equals('Y')}">
											<td><span class="h_check"></span></td>
										</c:when>
										<c:otherwise>
											<td></td>
										</c:otherwise>
									</c:choose>

									<c:choose>
										<c:when test="${domesticList.domesticSun.equals('Y')}">
											<td><span class="h_check"></span></td>
										</c:when>
										<c:otherwise>
											<td></td>
										</c:otherwise>
									</c:choose>

									<td>${domesticList.domesticStdate.substring(0, 11)}~${domesticList.domesticEddate.substring(0, 11)}</td>
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
                <div class="air-more btn btn-primary btn-lg"><a href="AirSchedule.do">더 알아보기</a></div>
            </div>
        </div>
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
<%@ include file="/WEB-INF/inc/AllmenuModal.jsp" %>
<%@ include file="/WEB-INF/inc/CommonScript.jsp" %>
<script src="${pageContext.request.contextPath}/plugins/OwlCarousel2-2.2.1/owl.carousel.js"></script>
<script src="${pageContext.request.contextPath}/plugins/validate/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/plugins/ajax-form/jquery.form.min.js"></script>
<script src="${pageContext.request.contextPath}/plugins/datepicker/datepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/plugins/datepicker/i18n/datepicker.ko-KR.js"></script>
<script src="${pageContext.request.contextPath}/plugins/aos/aos.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/air_index.js"></script>
</body>
</html>