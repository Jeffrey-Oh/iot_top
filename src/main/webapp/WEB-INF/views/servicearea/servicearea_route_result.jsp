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
    <c:set var="placeXY" value="${item.placeX},${item.placeY}" />
    <c:choose>
        <c:when test="${placeXY == start}">
                                    <option value="${item.placeX},${item.placeY}" selected>${item.routeName}&nbsp;&nbsp;&nbsp;&nbsp;${item.serviceareaName}</option>
        </c:when>
        <c:otherwise>
                                    <option value="${item.placeX},${item.placeY}">${item.routeName}&nbsp;&nbsp;&nbsp;&nbsp;${item.serviceareaName}</option>
        </c:otherwise>
    </c:choose>
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
    <c:set var="placeXY" value="${item.placeX},${item.placeY}" />
    <c:choose>
        <c:when test="${placeXY == goal}">
                                    <option value="${item.placeX},${item.placeY}" selected>${item.routeName}&nbsp;&nbsp;&nbsp;&nbsp;${item.serviceareaName}</option>
        </c:when>
        <c:otherwise>
                                    <option value="${item.placeX},${item.placeY}">${item.routeName}&nbsp;&nbsp;&nbsp;&nbsp;${item.serviceareaName}</option>
        </c:otherwise>
    </c:choose>
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

        <!-- 검색 결과 -->
        <table class="table table-bordered table-hover margin_top30">
            <thead>
                <tr>
                    <th class="text-center">전체 경로 거리</th>
                    <th class="text-center">전체 경로 소요 시간</th>
                    <th class="text-center">전체 주행 도로</th>
                </tr>
            </thead>
            <tbody>
                <tr class="text-center">
                    <td>
                        <span class="name">${summaryDistanceForm} km</span>
                    </td>
                    <td>
                        <span class="name">
<c:if test="${hours != 0}">
                            ${hours}시간
</c:if>
                            ${minutes}분 ${seconds}초 <span>소요 예상</span>
                        </span>
                    </td>
                    <td>
                        <span class="name">${name}</span>
                    </td>
                </tr>
            </tbody>
        </table>

        <table class="table table-bordered table-hover margin_top30">
            <thead>
                <tr>
                    <th class="text-center">길 안내</th>
                    <th class="text-center">이전 경로로부터 거리</th>
                    <th class="text-center">이전 경로로부터 소요 시간</th>
                </tr>
            </thead>
            <tbody>
<c:forEach var="i" begin="0" end="${guideLength -1}">
                <tr class="text-center">
                    <td>
                        <span class="name">${instructions[i]}</span>
                    </td>
                    <td>
                        <span class="name">${guideDistance[i]} km</span>
                    </td>
                    <td>
                        <span class="name">
    <c:if test="${nextHours[i] != 0}">
                            ${nextHours[i]}시간
    </c:if>
    <c:if test="${nextMinutes[i] != 0}">
                            ${nextMinutes[i]}분
    </c:if>
                            ${nextSeconds[i]}초 <span>소요 예상</span>
                        </span>
                    </td>
                </tr>
</c:forEach>
            </tbody>
        </table>
        <!-- // 검색 결과 -->

        <!-- 지도 -->
        <!-- 경로 지도가 구현되어야 함 -->
        <div id="map" class="margin_top50" style="width:100%;height:350px;background-color:#eee"></div>
        <!-- // 지도 -->

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
    <script>
<c:if test="${pathLength > 0}">
    <c:forTokens var="startItem" items="${start}" delims="," varStatus="status">
        eval("var start" + ${status.count} + " = " + ${startItem});
    </c:forTokens>
    <c:forTokens var="goalItem" items="${goal}" delims="," varStatus="status">
        eval("var goal" + ${status.count} + " = " + ${goalItem});
    </c:forTokens>
        var pathLength = ${pathLength};
        var pathX = "${pathX}";
        var pathY = "${pathY}";
</c:if>
    </script>
    <script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=a44d9158e7468f7aff1f8ed8fb064834"></script>
    <script src="${pageContext.request.contextPath}/assets/js/regex.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/servicearea_search.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/servicearea_route.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/servicearea_route_map.js"></script>
</body>
</html>
