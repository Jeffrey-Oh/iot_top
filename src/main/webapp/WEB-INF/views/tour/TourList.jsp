<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%-- Standard JSP/JSTL "goodness" --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="ko">

<head>
	<%@include file="/WEB-INF/inc/Head.jsp"%>
	<!-- mainpage.css || subpage.css 중 1개는 꼭 참조 해야 함. -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/subpage.css" />
	<!-- 개인 css 링크 참조 영역 -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/Tour.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/searchbar.css"><!-- searchbar를 사용하는 경우 장착 -->
	<style>
	hr {
		border: solid #0ad88c;
	}

	.class11 {
		margin-bottom: 20px;

	}

	.MainTour {
		background: #03a9f4;
	}

	.HotTourList {
		margin-left: 25px;
	}
	</style>

</head>

<body>


	<!-- 전체를 감싸는 div -->
	<div class="container-fluid">
		<%@include file="/WEB-INF/inc/Gnb.jsp"%>
		<!-- visual -->
		<section id="visual">
			<article class="bg">
				<img src="${pageContext.request.contextPath}/images/tour/Background3.jpg" alt="배경" />
			</article>
			<div class="bg-text"></div>
		</section>
		<!-- //visual -->
		<%@include file="/WEB-INF/inc/Searchbar.jsp"%>
		<!-- contents -->
		<section id="contents">
			<h2 class="blind" style="padding-bottom: 100px;">본문</h2>
			<div class="container">
				<!-- 메인 컨텐츠 삽입 -->

				<!-- 여기에 contents 삽입 시작 -->
				<!-- ===== 개인 작업 영역 ===== -->
				<div class="MainTour">
					<div class="Tour">
						<div id="carousel-id" class="carousel slide" data-ride="carousel">
							<!-- 중앙 하단부 동그라미들 -->
							<!-- active가 걸려 있으면 첫 화면. target이 slide박스 아이디 -->
							<ol class="carousel-indicators">
								<li data-target="#carousel-id" data-slide-to="0" class=""></li>
								<li data-target="#carousel-id" data-slide-to="1" class=""></li>
								<li data-target="#carousel-id" data-slide-to="2" class="active"></li>
								<li data-target="#carousel-id" data-slide-to="3" class=""></li>
								<li data-target="#carousel-id" data-slide-to="4" class=""></li>
							</ol>
							<!-- 이미지 슬라이드 -->
							<div class="carousel-inner">
								<div class="item">
									<img data-src="holder.js/900x500/auto/#777:#7a7a7a/text:First slide" alt="First slide" src="${pageContext.request.contextPath}/images/tour/6.jpg" class="imageT">
									<div class="container">
										<div class="carousel-caption">
											<h1>국내여행 관광지 확인하기.</h1>
											<p>
												Note: If you're viewing this page via a
												<code>file://</code>
												URL, the "next" and "previous" Glyphicon buttons on the left
												and right might not load/display properly due to web browser
												security rules.
											</p>
											<p>
												<a class="btn btn-lg btn-primary" href="#" role="button">Sign
													up today</a>
											</p>
										</div>
									</div>
								</div>
								<div class="item">
									<img data-src="holder.js/900x500/auto/#666:#6a6a6a/text:Second slide" alt="Second slide" src="${pageContext.request.contextPath}/images/tour/4.jpg" class="imageT">
									<div class="container">
										<div class="carousel-caption">
											<h1>국내여행 관광지 확인하기2.</h1>
											<p>Cras justo odio, dapibus ac facilisis in, egestas eget
												quam. Donec id elit non mi porta gravida at eget metus.
												Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
											<p>
												<a class="btn btn-lg btn-primary" href="#" role="button">Learn
													more</a>
											</p>
										</div>
									</div>
								</div>
								<div class="item active">
									<img data-src="holder.js/900x500/auto/#555:#5a5a5a/text:Third slide" alt="Third slide" src="${pageContext.request.contextPath}/images/tour/53.jpg" class="imageT">
									<div class="container">
										<div class="carousel-caption">
											<h1>국내여행 관광지 확인하기3.</h1>
											<p>Cras justo odio, dapibus ac facilisis in, egestas eget
												quam. Donec id elit non mi porta gravida at eget metus.
												Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
											<p>
												<a class="btn btn-lg btn-primary" href="#" role="button">Browse
													gallery</a>
											</p>
										</div>
									</div>
								</div>
								<div class="item">
									<img data-src="holder.js/900x500/auto/#666:#6a6a6a/text:내번째 slide" alt="Second slide" src="${pageContext.request.contextPath}/images/tour/imageview.jpg" class="imageT">
									<div class="container">
										<div class="carousel-caption">
											<h1>국내여행 관광지 확인하기4.</h1>
											<p>Cras justo odio, dapibus ac facilisis in, egestas eget
												quam. Donec id elit non mi porta gravida at eget metus.
												Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
											<p>
												<a class="btn btn-lg btn-primary" href="#" role="button">Learn
													more</a>
											</p>
										</div>
									</div>
								</div>
								<div class="item">
									<img data-src="holder.js/900x500/auto/#666:#6a6a6a/text:5번째 slide" alt="Second slide" src="${pageContext.request.contextPath}/images/tour/imageview2.jpg" class="imageT">
									<div class="container">
										<div class="carousel-caption">
											<h1>국내여행 관광지 확인하기5.</h1>
											<p>Cras justo odio, dapibus ac facilisis in, egestas eget
												quam. Donec id elit non mi porta gravida at eget metus.
												Nullam id dolor id nibh ultricies vehicula ut id elit.</p>
											<p>
												<a class="btn btn-lg btn-primary" href="#" role="button">Learn
													more</a>
											</p>
										</div>
									</div>
								</div>
							</div>
						</div>
						<!-- 이전, 다음 버튼. href가 slide박스 아이디 -->
						<div>
							<a class="left carousel-control" href="#carousel-id" data-slide="prev" style="height: 16%;"><span class="glyphicon glyphicon-chevron-left">
								</span></a>
							<a class="right carousel-control" href="#carousel-id" data-slide="next" style="height: 16%;"><span class="glyphicon glyphicon-chevron-right">
								</span></a>
						</div>
					</div>
				</div>
				<br /> <br /> <br />
				<hr />
				<div class="TourMainHotImage" style="width: 100%;">
					<div class="class22" style="display: inline; width: 100%; float: left;">
						<div class="ImageShot" style="">
							<div class="HotTourList">
								<a style="font-size: 25px;" href="${pageContext.request.contextPath}/tour/TourList2.do">
									인기 관광 정보 <img src="${pageContext.request.contextPath}/images/tour/HotTourList.png" alt="" style="width: 50px; height: 50px; "></a>
								<a style="margin-bottom: 20px; font-size: 25px; display: inline-block; padding-left: 20px;" href="${pageContext.request.contextPath}/CommIndex.do?category=tour">
									여행 후기 <img src="${pageContext.request.contextPath}/images/tour/picture.png" alt="" />
								</a>
							</div>
							<hr />
							<div class="ThumImage">
								<c:forEach var="item" items="${list}">
									<c:choose>
										<c:when test="${item.firstimage != Null && item.areacode == 1}">
											<div class="col-xs-6 col-md-3">
												<a href="${pageContext.request.contextPath}/tour/TourList2.do" class="thumbnail"> <img data-src="holder.js/100%x180" alt="DbImages" style="height: 180px; width: 100%;" src="${item.firstimage}" data-holder-rendered="true">
												</a>
											</div>
										</c:when>
										<c:otherwise>
											<div class="col-xs-6 col-md-3">
												<a href="${pageContext.request.contextPath}/tour/TourList2.do" class="thumbnail"> <img data-src="holder.js/100%x180" alt="NoImages" style="height: 180px; width: 100%;"
														src="${pageContext.request.contextPath}/images/tour/NoImage800x511.png" data-holder-rendered="true">
												</a>
											</div>
										</c:otherwise>
									</c:choose>
								</c:forEach>
							</div>
						</div>
					</div>
				</div>
				<br /> <br />
	<br />
	<br />
	<br />
	<div class="ENDD" style="margin-top: 40px;"></div>

	<!-- 여기에 contents 삽입 끝 -->

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
	<%@include file="/WEB-INF/inc/AllmenuModal.jsp"%>
	<%@include file="/WEB-INF/inc/CommonScript.jsp"%>
	<!-- 개인 js 참조 영역 -->
	<script src="${pageContext.request.contextPath}/assets/js/regex.js"></script><!-- searchbar를 사용하는 경우 장착 -->
    <script src="${pageContext.request.contextPath}/assets/js/searchbar.js"></script><!-- searchbar를 사용하는 경우 장착 -->
	

</body>

</html>
