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
    <h3 class="color3">경로탐색</h3>
</article>
<!-- //page header -->

<div class="row clearfix">

    <!-- 서브 컨텐츠 -->
    <article class="col-md-10 pull-left">
        <!-- 내용 삽입 -->

        <!-- 출발지, 도착지 검색 폼 -->
        <form action="${pageContext.request.contextPath}/servicearea/servicearea_route_result.do" method="GET" id="servicearea_route_form" class="form-horizontal" role="form">
            <fieldset>
                <legend class="blind">출발지, 도착지 검색 폼</legend>

                <!--입력 요소 및 로그인 버튼 -->
                <div class="row clearfix margin_top50">
                    <div class="col-md-6 col-md-offset-2 pull-left">
                        <!-- 출발 -->
                        <div class="form-group">
                            <label for="startLoc" class="col-md-3 control-label">출발</label>
                            <div class="col-md-9">
                                <select name="start" id="start" class="form-control input-lg">
                                    <option value="nowLoc">현재 위치에서 출발합니다.</option>
<c:forEach var="item" items="${groupList}">
                                    <option value="${item.placeX},${item.placeY}">${item.routeName}&nbsp;&nbsp;&nbsp;&nbsp;${item.serviceareaName}</option>
</c:forEach>
                                </select>
                            </div>
                        </div>
                        <!-- // 출발 -->
                    </div>

                    <div class="col-md-6 col-md-offset-2 pull-left">
                        <!-- 출발 -->
                        <div class="form-group">
                            <label for="endLoc" class="col-md-3 control-label">도착</label>
                            <div class="col-md-9">
                                <select name="goal" id="goal" class="form-control input-lg">
                                    <option value="">도착지를 선택해 주세요.</option>
<c:forEach var="item" items="${groupList}">
                                    <option value="${item.placeX},${item.placeY}">${item.routeName}&nbsp;&nbsp;&nbsp;&nbsp;${item.serviceareaName}</option>
</c:forEach>
                                </select>
                            </div>
                        </div>
                        <!-- // 출발 -->
                    </div>

                    <div class="col-md-2 pull-left">
                        <div class="form-group">
                            <button type="submit" class="btn btn_color1 btn-lg btn_login glyphicon glyphicon-search"></button>
                        </div>
                    </div>
                </div>
                <!-- // 입력 요소 및 검색 버튼 -->
            </fieldset>
        </form>
        <!-- // 출발지, 도착지 검색 폼 -->

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
    <script src="${pageContext.request.contextPath}/assets/js/servicearea_route.js"></script>
</body>
</html>
