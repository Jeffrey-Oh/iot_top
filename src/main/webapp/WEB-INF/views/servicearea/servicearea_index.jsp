<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="/WEB-INF/inc/Head.jsp"%>
    <!-- mainpage.css || subpage.css 중 1개는 꼭 참조 해야 함. -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/subpage.css" />
    <!-- 개인 css 링크 참조 영역 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/servicearea.css" />
</head>
<body>
<!-- 전체를 감싸는 div -->
<div class="container-fluid">
<%@ include file="/WEB-INF/inc/Gnb.jsp"%>
<%@ include file="/WEB-INF/inc/Visual.jsp"%>
<%@ include file="/WEB-INF/inc/servicearea_Searchbar.jsp"%>

<!-- contents -->
<section id="contents">
    <h2 class="blind">본문</h2>
    <div class="container">
        <!-- 메인 컨텐츠 삽입 -->

<!-- 여기에 contents 삽입 시작 -->
<!-- ===== 개인 작업 영역 ===== -->

<!-- page header -->
<article class="page-header">
    <h3 class="color3">휴게소</h3>

<%-- 관리자 계정만 볼 수 있는 버튼 모음 --%>
<c:if test="${loginInfo.grade == 'Master'}">
    <div class="row margin_top10 servicearea_api_management">
        <a href="${pageContext.request.contextPath}/servicearea/servicearea_api_insert.do" class="btn btn_color1 radius3 servicearea_api_btn">휴게소 API 추가</a>
        <a href="${pageContext.request.contextPath}/servicearea/servicearea_cs_api_insert.do" class="btn btn_color1 radius3 margin_left10 servicearea_api_btn">휴게소 전기차 충전소 API 추가</a>
        <a href="${pageContext.request.contextPath}/servicearea/servicearea_food_all_api_insert.do" class="btn btn_color1 radius3 margin_left10 servicearea_api_btn">휴게소 음식전체 API 추가</a>
        <a href="${pageContext.request.contextPath}/servicearea/servicearea_food_api_insert.do" class="btn btn_color1 radius3 margin_left10 servicearea_api_btn">휴게소 대표음식 API 추가</a>
        <a href="${pageContext.request.contextPath}/servicearea/servicearea_image_api_insert.do" class="btn btn_color1 radius3 margin_left10 servicearea_api_btn">휴게소 이미지 API 추가</a>
    </div>
    <div class="row margin_top10 servicearea_api_management">
        <a href="${pageContext.request.contextPath}/servicearea/servicearea_oil_api_insert.do" class="btn btn_color1 radius3 margin_left10 servicearea_api_btn">휴게소 유가정보 API 추가</a>
        <a href="${pageContext.request.contextPath}/servicearea/servicearea_place_api_insert.do" class="btn btn_color1 radius3 margin_left10 servicearea_api_btn">휴게소 위치 API 추가</a>
        <a href="${pageContext.request.contextPath}/servicearea/servicearea_ps_api_insert.do" class="btn btn_color1 radius3 margin_left10 servicearea_api_btn">휴게소 편의시설 API 추가</a>
        <a href="${pageContext.request.contextPath}/servicearea/servicearea_theme_api_insert.do" class="btn btn_color1 radius3 margin_left10 servicearea_api_btn">휴게소 테마 API 추가</a>
        <a href="${pageContext.request.contextPath}/servicearea/servicearea_group_insert.do" class="btn btn_color2 radius3 margin_left10 servicearea_api_btn">휴게소 완성 DB 추가</a>
    </div>
</c:if>
<%-- // 관리자 계정만 볼 수 있는 버튼 모음 --%>
</article>
<!-- //page header -->

<div class="row clearfix">

    <!-- 서브 컨텐츠 -->
    <article class="col-md-10 pull-left">
        <!-- 내용 삽입 -->
        <ul class="list-unstyled clearfix servicearea_nav text-center">
            <li class="pull-left line"><a href="${pageContext.request.contextPath}/servicearea/servicearea_line.do"><span class="text">노선별 안내</span></a></li>
            <li class="pull-left radar"><a href="${pageContext.request.contextPath}/servicearea/servicearea_radar.do" class="radarBtn"><span class="text">주변검색</span></a></li>
            <li class="pull-left route"><a href="${pageContext.request.contextPath}/servicearea/servicearea_route.do"><span class="text">경로탐색</span></a></li>
            <li class="pull-left oil"><a href="${pageContext.request.contextPath}/servicearea/servicearea_oil.do"><span class="text">유가정보</span></a></li>
            <li class="pull-left food"><a href="${pageContext.request.contextPath}/servicearea/servicearea_food.do"><span class="text">대표메뉴</span></a></li>
            <li class="pull-left theme"><a href="${pageContext.request.contextPath}/servicearea/servicearea_theme.do"><span class="text">테마휴게소</span></a></li>
        </ul>
    </article>
    <!-- //서브 컨텐츠 -->

    <!-- aside -->
    <aside class="aside col-md-2 pull-right">
        <a href="${pageContext.request.contextPath}/index.do">
            <img src="${pageContext.request.contextPath}/images/common/banner.jpg" alt="" />
        </a>
    </aside>
    <!-- //aside -->
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
<!-- // 전체를 감싸는 div -->
<%@ include file="/WEB-INF/inc/AllmenuModal.jsp" %>
<%@ include file="/WEB-INF/inc/CommonScript.jsp" %>
    <!-- 개인 js 참조 영역 -->
    <script src="${pageContext.request.contextPath}/assets/js/regex.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/servicearea_search.js"></script>
</body>
</html>
