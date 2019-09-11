<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/billboard.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/weather.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/searchbar.css"><!-- searchbar를 사용하는 경우 장착 -->
	<script src="https://d3js.org/d3.v4.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/billboard.js"></script>
</head>
<body>
<!-- 전체를 감싸는 div -->
<div class="container-fluid">
<%@ include file="/WEB-INF/inc/Gnb.jsp"%>
<%@ include file="/WEB-INF/inc/Visual.jsp"%>
<%@ include file="/WEB-INF/inc/Searchbar.jsp" %><!-- 빠른검색(css, js 필요) -->
<!-- contents -->
<section id="contents">
    <h2 class="blind">본문</h2>
    <div class="container weather-box">
        <!-- 메인 컨텐츠 삽입 -->

<!-- 여기에 contents 삽입 시작 -->
<!-- ===== 개인 작업 영역 ===== -->
<div class="section_title pull-left">
	<!-- page header -->
	<article class="page-header clearfix">
	    <h3 class="color3 pull-left">지역별 날씨</h3>
		<select id="location" class="form-control input-lg loc_select pull-left margin_left10"></select>
	</article>
	<!-- //page header -->
    
	<div align="left" class="margin_top20">
		<span style="font-size: 2em;">
			현재온도 :<span id="tem"></span><br/> 
			날씨 :<span id="ico"></span><br/>
		습도 :<span id="humidit"></span><br/>
		풍속 : <span id="spee"></span>
		</span>
		<div id="categoryAxis"></div>
	</div>
</div>
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

<%@ include file="/WEB-INF/inc/AllmenuModal.jsp"%>
<%@ include file="/WEB-INF/inc/CommonScript.jsp"%>

	<!-- 개인 js 참조 영역 -->
	<script src="${pageContext.request.contextPath}/assets/js/regex.js"></script><!-- searchbar를 사용하는 경우 장착 -->
	<script src="${pageContext.request.contextPath}/assets/js/searchbar.js"></script><!-- searchbar를 사용하는 경우 장착 -->
	<!--d3js CDN-->
	<script src="https://d3js.org/d3.v4.min.js"></script>
	<script src="${pageContext.request.contextPath}/assets/js/billboard.js"></script>
	<script>
	    var loc = "${location}";
	    var temp_max_array = [${temp_max_array}];
	    var humidity_array = [${humidity_array}];
	    var speed_array = [${speed_array}];
	    var time_chart = [${dt_txt_array}];
	</script>
	<script src="${pageContext.request.contextPath}/assets/js/weather_main1.js"></script>
</body>
</html>
