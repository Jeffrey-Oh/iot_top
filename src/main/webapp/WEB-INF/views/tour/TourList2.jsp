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
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/Tour2page.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/searchbar.css"><!-- searchbar를 사용하는 경우 장착 -->
	<style type="text/css">
	article {
		display: block;
	}

	#visual {
	    height: 850px;
	}

	.bg {
   		background-position: center 47%;
	}

	.ImageShotReview {
		display: inline-block;
		width: 1050px;
	}

	hr {
		border: solid 2px #30cc7e;
	}
	</style>
</head>

<body>
	<!-- 전체를 감싸는 div -->
	<div class="container-fluid">
		<%@ include file="/WEB-INF/inc/Gnb.jsp"%>
		<!-- visual -->
		<section id="visual">
			<article class="bg">
				<img src="${pageContext.request.contextPath}/images/tour/Background3.jpg" alt="BackgroundImage" style="" />
			</article>
			<div class="bg-text"></div>
		</section>
		<!-- //visual -->
		<%@ include file="/WEB-INF/inc/Searchbar.jsp" %><!-- 빠른검색(css, js, regex.js 필요) -->
		<!-- contents -->
		<section id="contents">
			<h2 class="blind">본문</h2>
			<div class="container">
				<!-- 메인 컨텐츠 삽입 -->

				<!-- 여기에 contents 삽입 시작 -->
				<!-- ===== 개인 작업 영역 ===== -->
				<div class="MainTour">
					<div class="Tour">
						<br />
						<div class="ImageShot" style="">
							<br /> <br />
							<!-- 검색어 부분 -->
							<!-- 지역: 서울 인천 강원 부산 제주도 광주 충남 대전 전라 -->
							<form action="${pageContext.request.contextPath}/tour/TourList2.do">
								<div class="pull-right" style="margin-right: 30px;">
									<div class="input-group" style="width: 200px">
										<%-- 주소 용 검색 --%>
										<!-- <input style="width: 450px;" type="text" id="keyword" name="keyword" class="form-control" value="${keyword}" placeholder="관광 지역검색" /> -->
										<select name="keyword" id="" style="width: 200px; height: 30px; font-size: 15px;">
											<option selected value="1" style=" font-size: 20px;">서울</option>
											<option value="2" style=" font-size: 20px;">인천</option>
											<option value="32" style=" font-size: 20px;">강원</option>
											<option value="6" style=" font-size: 20px;">부산</option>
											<option value="5" style=" font-size: 20px;">광주</option>
											<option value="34" style=" font-size: 20px;">충남</option>
											<option value="33" style=" font-size: 20px;">충북</option>
											<option value="3" style=" font-size: 20px;">대전</option>
											<option value="36" style=" font-size: 20px;">경상남도</option>
											<option value="35" style=" font-size: 20px;">경상북도</option>
											<option value="38" style=" font-size: 20px;">전라</option>
											<option value="39" style=" font-size: 20px;">제주도</option>
										</select>
										<%-- 주소 용 검색 끝 --%>
										<span class="input-group-btn">
											<button type="submit" class="btn btn-primary" id="btn">여행지역 검색</button>
										</span>
									</div>
								</div>
							</form>
							<!-- 검색어 부분 끝 -->
							<h1>
								<img src="<%=request.getContextPath()%>/images/tour/20190128_122025.png" alt="" class="imgFor1" />▶ 국내 관광 정보 ◀
							</h1>
							<div class="ThumImage">
								<h2 class="W1">이미지 정보</h2>
								<div class="ImageShotReview">
									<c:forEach var="item" items="${list}">
									 	<div class="">
											<c:url var="readUrl" value="/tour/TourList3.do">
												<c:param name="tourId" value="${item.tourId}"></c:param>
												<c:param name="contentid" value="${item.contentid}"></c:param>
											</c:url>
										</div>

										<c:choose>
											<c:when test="${item.firstimage != Null}">
												<div class="col-xs-8 col-md-4" style="margin-bottom: 10px;">
													<a href="${readUrl}"  class="thumbnail"> <img data-src="holder.js/100%x180" alt="281x180" style="height: 180px; width: 100%; display: block; width: inherit;" src="${item.firstimage}" data-holder-rendered="true">
													</a>
													<hr />
													<div>
														<h4>${item.title}</h4>
														<div>${item.addr1}</div>
														<div>${item.tourId}</div>
														<div class="">수정 및 생성날짜 : ${item.modifiedtime}</div>
													</div>
												</div>
											</c:when>
											<c:otherwise>
												<div class="col-xs-8 col-md-4" style="margin: 10px;">
													<a href="#" class="thumbnail"> <img data-src="holder.js/100%x180" alt="100%x180" style="height: 180px; width: 100%; display: block;" src="data:image/svg+xml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGL
										TgiIHN0YW5kYWxvbmU9InllcyI/PjxzdmcgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMD
										Avc3ZnIiB3aWR0aD0iMjgxIiBoZWlnaHQ9IjE4MCIgdmlld0JveD0iMCAwIDI4MSAxODAiIHByZXNl
										cnZlQXNwZWN0UmF0aW89Im5vbmUiPjwhLS0KU291cmNlIFVSTDogaG9sZGVyLmpzLzEwMCV4MTgwCkNyZWF
										0ZWQgd2l0aCBIb2xkZXIuanMgMi42LjAuCkxlYXJuIG1vcmUgYXQgaHR0cDovL2hvbGRlcmpzLmNvbQooYykg
										MjAxMi0yMDE1IEl2YW4gTWFsb3BpbnNreSAtIGh0dHA6Ly9pbXNreS5jbwotLT48ZGVmcz48c3R5bGUgdHlwZT0id
										GV4dC9jc3MiPjwhW0NEQVRBWyNob2xkZXJfMTY5OTE3NGNhYjMgdGV4dCB7IGZpbGw6I0FBQUFBQTtmb250LXdlaWdo
										dDpib2xkO2ZvbnQtZmFtaWx5OkFyaWFsLCBIZWx2ZXRpY2EsIE9wZW4gU2Fucywgc2Fucy1zZXJpZiwgbW9ub3NwYWNlO2
										ZvbnQtc2l6ZToxNHB0IH0gXV0+PC9zdHlsZT48L2RlZnM+PGcgaWQ9ImhvbGRlcl8xNjk5MTc0Y2FiMyI+PHJlY3Qgd2lk
										dGg9IjI4MSIgaGVpZ2h0PSIxODAiIGZpbGw9IiNFRUVFRUUiLz48Zz48dGV4dCB4PSIxMDQuMTc5Njg3NSIgeT0iOTYuMyI+
										MjgxeDE4MDwvdGV4dD48L2c+PC9nPjwvc3ZnPg==" data-holder-rendered="true">
													</a>
													<div>
														<h4>${item.title}</h4>
														<div>${item.addr1}</div>
													</div>
												</div>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</div>
							</div>
						</div>
						<br />
						<div>
							<div class="class33"></div>
						</div>
						<!-- 버튼  -->
						<div class="clearfix" id="BACKk">
							<div class="pull-right">
								<a href="${pageContext.request.contextPath}/tour/TourList.do" class="btn btn-info" style="width: 400px;  ">뒤로가기</a>
							</div>
						</div>
						<!-- 여기에 contents 삽입 끝 -->
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
	<%@ include file="/WEB-INF/inc/AllmenuModal.jsp"%>
	<%@ include file="/WEB-INF/inc/CommonScript.jsp"%>
	<!-- 개인 js 참조 영역 -->
	<script src="${pageContext.request.contextPath}/assets/js/regex.js"></script><!-- searchbar를 사용하는 경우 장착 -->
	<script src="${pageContext.request.contextPath}/assets/js/searchbar.js"></script><!-- searchbar를 사용하는 경우 장착 -->
</body>

</html>
