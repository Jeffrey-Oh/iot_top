<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">

<head>
	<%@ include file="/WEB-INF/inc/Head.jsp"%>
	<!-- mainpage.css || subpage.css 중 1개는 꼭 참조 해야 함. -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/subpage.css" />
	<!-- 개인 css 링크 참조 영역 -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/Tour3page.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/searchbar.css"><!-- searchbar를 사용하는 경우 장착 -->
	<style type="text/css">
	#visual {
		height: 850px !important;
	}
	
	.MainTour {
		margin-top: 50x;
	}


	.wrapper.style1 .image {
		box-shadow: rgb(52, 152, 219) 0px 0px 0px 7px, rgba(255, 255, 255, 0.25) 0px 0px 0px 8px;
		border-radius: 100%;
		padding: 20px;
	}

	.wrapper.style1 {
		background-color: rgb(52, 152, 219);
		color: rgb(174, 214, 241);
		padding: 40px;
	}

	.col col1 {
		width: 600px;
		height: 600px;
	}

	.flex.flex-2 .col.col1 {
		width: 100%;
	}

	.wrapper>.inner {
		width: 60em;
		position: relative;
		z-index: 99;
		margin: 0px auto;

	}

	.flex.flex-2 {
		align-items: stretch;
		margin-top: 50px;
	}

	.image img {
		display: block;
		height: 600px;
	}

	.image.round img {
		border-radius: 100%;
	}

	.image.fit {
		display: block;
		/*width: 100%;*/
		margin: 0px 0px 2em;
	}

	.image {
		position: relative;
		border-width: 0px;
		border-style: initial;
		border-color: initial;
		border-image: initial;
	}
	</style>

</head>

<body>
	<!-- 전체를 감싸는 div -->
	<div class="container-fluid">
		<%@ include file="/WEB-INF/inc/Gnb.jsp"%>
		<!--visual-->
		<section id="visual">
			<article class="bg">
				<div class="bg-text">
					<img src="${pageContext.request.contextPath}/images/tour/Background3.jpg" alt="">
				</div>
			</article>
		</section>
		<%@ include file="/WEB-INF/inc/Searchbar.jsp" %><!-- 빠른검색(css, js, regex.js 필요) -->
		<!-- contents -->
		<section id="contents">
			<h2 class="blind">본문</h2>
			<div class="container">
				<!-- 메인 컨텐츠 삽입 -->
				<div class="MainTour">
					<div class="Tour">
						<br />
						<hr />
						<div class="ImageShot" style="">
							<h1>
								<img src="<%=request.getContextPath()%>/Seonjun/image2/20190128_122025.png" alt="" class="imgFor1" />★ 상세 정보 확인.
							</h1>
						</div>
						<br />
						<hr />
						<hr />

						<!-- Section -->
						<section class="wrapper style1">
							<div class="inner">
								<!-- 2 Columns -->
								<div class="flex flex-2">
									<div class="col col1">
										<div class="image round fit">
											<img src="${item.firstimage}" alt="" style="margin: auto;" />
										</div><br />
									</div>
									<div class="col col2"><h1>★ 내용</h1><br />
										<h3>${item.title}</h3>
										<hr />
										<p style="font-size: 15px; ">${itemInfo}</p>
									</div>
								</div>
							</div>
						</section>
					</div>


					<!-- 버튼  -->
					<div class="clearfix margin_top50">
						<div class="pull-right">
							<a href="${pageContext.request.contextPath}/tour/TourList2.do" class="btn btn-info">뒤로가기</a>
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
<%@ include file="/WEB-INF/inc/Footer.jsp" %>
</div>
<!-- // 전체를 감싸는 div -->
<%@ include file="/WEB-INF/inc/AllmenuModal.jsp" %>
<%@ include file="/WEB-INF/inc/CommonScript.jsp" %>
    <!-- 개인 js 참조 영역 -->
    <script src="${pageContext.request.contextPath}/assets/js/regex.js"></script><!-- searchbar를 사용하는 경우 장착 -->
    <script src="${pageContext.request.contextPath}/assets/js/searchbar.js"></script><!-- searchbar를 사용하는 경우 장착 -->
    <script></script>
</body>

</html>
