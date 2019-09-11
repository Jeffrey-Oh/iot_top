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
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/airhot.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/datepicker.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/aos/aos.css" />
</head>
<body>
<!-- 전체를 감싸는 div -->
<div class="container-fluid">
<%@ include file="/WEB-INF/inc/Gnb.jsp"%>
<!-- visual -->
<section id="visual">
	<!-- id 값을 모두 맞춰야함 div, ol, a 태그 -->
    <div id="carousel-id" class="carousel slide" data-ride="carousel">
        <!-- 중앙 하단부 동그라미들 -->
        <ol class="carousel-indicators">
                <!--
                    data-slide-to 숫자는 0부터 시작하는 일련번호
                    active는 첫 화면 설정
                -->
                <li data-target="#carousel-id" data-slide-to="0" class="active"></li>
                <li data-target="#carousel-id" data-slide-to="1" class=""></li>
                <li data-target="#carousel-id" data-slide-to="2" class=""></li>
                <li data-target="#carousel-id" data-slide-to="3" class=""></li>
                <li data-target="#carousel-id" data-slide-to="4" class=""></li>
            </ol>

            <!-- 이미지 슬라이드 -->
            <div class="carousel-inner">
                <div class="item active" style="background-image: url(${pageContext.request.contextPath}/images/air/hot_bg_1.jpg)">
            		<div class="container">
            			<div class="carousel-caption">
            				<h1>Example headline.</h1>
            				<p>Note: If you're viewing this page via a <code>file://</code> URL, the "next" and "previous" Glyphicon buttons on the left and right might not load/display properly due to web browser security rules.</p>
            				<p><a class="btn btn-lg btn-primary" href="#" role="button">Sign up today</a></p>
            			</div>
            		</div>
            	</div>
            	<c:forEach var="i" begin="2" end="5">
	            	<div class="item" style="background-image: url(${pageContext.request.contextPath}/images/air/hot_bg_${i}.jpg)">
	            		<div class="container">
	            			<div class="carousel-caption">
	            				<h1>Another example headline.</h1>
	            				<p>Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
	            				<p><a class="btn btn-lg btn-primary" href="#" role="button">Learn more</a></p>
	            			</div>
	            		</div>
	            	</div>
            	</c:forEach>
            </div>

            <!-- 이전, 다음 버튼 -->
            <a class="left carousel-control" href="#carousel-id" data-slide="prev"><span class="glyphicon glyphicon-chevron-left"></span></a>
            <a id="click" class="right carousel-control" href="#carousel-id" data-slide="next"><span class="glyphicon glyphicon-chevron-right"></span></a>
        </div>
</section>
<!-- //visual -->

<!-- contents -->
<section id="contents">
    <h2 class="blind">본문</h2>
    <div class="container">
        <!-- 메인 컨텐츠 삽입 -->

	<!-- content 감싸는 영역 -->
	<div class="content-parent">
		<div class="destinations" id="destinations">
			<div class="container border-bottom">
				<div class="row section-text">
					<div class="col text-center">
						<div class="section_subtitle">simply amazing places</div>
						<div class="section_title">
							<h2>Popular Destinations</h2>
						</div>
					</div>
				</div>
				<div class="row destinations_row">
					<div class="col">
						<div class="destinations_container">
						<c:forEach var="item" items="${list}" varStatus="s">
							<div class="item-box item_grid_${s.count}">
								<a href="${pageContext.request.contextPath}/AirSearch.do?tab=oneway&sdate=${date}&arrivedKor=${item.arrivalCity}" class="item-link">
									<span class="item-bg" style="background-image: url(${pageContext.request.contextPath}${item.imageUrl});"></span>
									<span class="item-content">
										<span class="item-text">
											<h4>${item.title}</h4>
											<br />
											<p>${item.content}</p>
										</span>
									</span>
								</a>
							</div>
						</c:forEach>
						</div>
					</div>
				</div>
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
<script src="${pageContext.request.contextPath}/assets/js/air_hot.js"></script>
</body>
</html>