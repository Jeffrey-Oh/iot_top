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
    <h3 class="color3">테마휴게소</h3>
</article>
<!-- //page header -->

<div class="row clearfix">

    <!-- 서브 컨텐츠 -->
    <article class="col-md-10 pull-left">
        <!-- 내용 삽입 -->

        <!-- 노선 검색 폼 -->
        <form action="${pageContext.request.contextPath}/servicearea/servicearea_theme.do" method="GET" id="line_search" class="form-horizontal" role="form">
            <fieldset>
                <legend class="blind">노선 검색</legend>

                <!--입력 요소 및 로그인 버튼 -->
                <div class="row clearfix">
                    <div class="col-md-6 col-md-offset-2 pull-left">
                        <!-- 노선명 -->
                        <div class="form-group">
                            <label for="lineName" class="col-md-3 control-label blind">노선명</label>
                            <div class="col-md-9 col-md-offset-3">
                                <input type="text" name="lineName" id="lineName" class="form-control input-lg" placeholder="노선명을 입력해 주세요." value="${lineName}" />
                            </div>
                        </div>
                        <!-- // 노선명 -->
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
        <!-- // 노선 검색 폼 -->

        <!-- 검색 결과 -->
        <div class="servicearea_theme">
            <ul class="line_list list-unstyled clearfix">
<%--
    ===== 조회 결과가 존재하는 경우와 그렇지 않은 경우 구분하기 =====
    - 조회 결과를 갖는 컬렉션의 size()가 0보다 큰 경우와
        그렇지 않은 경우를 분기한다.
--%>
<c:choose>
    <c:when test="${fn: length(lineList) > 0}">
        <%--
            ===== 조회결과가 존재하는 경우 =====
            - 컬렉션에 저장된 내용을 반복문 안으로 조회결과를 출력한다.
        --%>
        <c:forEach var="item" items="${lineList}">
            <li>
                <a href="${pageContext.request.contextPath}/servicearea/servicearea_theme_result.do?routeName=${item.routeName}">
                    <span class="icon">
                        <img src="${pageContext.request.contextPath}/images/servicearea/line/${item.routeName}.gif" alt="노선" />
                    </span>
                    <span class="name">${item.routeName}</span>
                </a>
            </li>
        </c:forEach>
    </c:when>

    <c:otherwise>
                <%-- 조회결과가 존재하지 않는 경우 --%>
                <li>
                    <a href="#">
                        <span class="name">조회된 노선이 없습니다.</span>
                    </a>
                </li>
    </c:otherwise>
</c:choose>
            </ul>
        </div>
        <!-- // 검색 결과 -->

        <!--
            ===== 페이지 번호 출력을 위한 처리 =====
            - JSP에서 PageHelper로부터 얻은 값을 Service를 통해
                Mapper에 전달하면 Mapper에서는 해당 페이지에 맞는
                결과만을 조회한다.
            - 반면 JSP에서는 PageHelper가 가지고 있는 값을 활용하여
                페이지 번호를 출력해야 한다.
        -->
        <!-- pagination -->
        <nav class="text-center margin_top40">
            <ul class="pagination">
<%--
    ===== 이전 그룹으로 이동하기 위한 링크 =====
    - 페이지 번호를 출력하는 과정에서
        검색결과가 반영된 상태라면
        검색어에 대한 상태유지가 유지된다.
    - 그러므로 모든 페이지 번호에는 검색어 파라미터가
        함께 전달되어야 한다.
    - 첫 번째 그룹에서는 이전 그룹으로 이동할 수 없기 때문에 분기처리 한다.
--%>
        <!-- 이전 그룹 -->
<c:choose>
    <c:when test="${pageHelper.prevPage > 0}">
        <%-- 이전 그룹에 대한 페이지 번호가 존재한다면? --%>
        <%-- 이전 그룹으로 이동하기 위한 URL을 생성해서 "prevUrl"에 저장 --%>
        <c:url var="prevUrl" value="/servicearea/servicearea_theme.do">
            <c:param name="lineName" value="${lineName}" />
            <c:param name="page" value="${pageHelper.prevPage}" />
        </c:url>

                <li>
                    <a href="${prevUrl}">
                        <span aria-hideden="true">&laquo;</span>
                    </a>
                </li>
    </c:when>
    <c:otherwise>
                <!-- 이전 그룹에 대한 페이지 번호가 존재하지 않는다면? -->
                <li class="disabled">
                    <a href="#">
                        <span aria-hideden="true">&laquo;</span>
                    </a>
                </li>
    </c:otherwise>
</c:choose>

<%--
    ===== 반복문을 통한 페이지 번호 출력하기 =====
    - 반복문의 인덱스와 현재 페이지 번호가 동일하다면
        링크가 아닌 강조 형태로 표시하고,
        그 밖의 경우에는 정상적인 링크로 처리한다.
--%>
                <!-- 페이지 번호 -->
<%-- 현재 그룹의 시작페이지~끝페이지 사이를 1씩 증가하면서 반복 --%>
<c:forEach var="i" begin="${pageHelper.startPage}" end="${pageHelper.endPage}" varStatus="status">
    <%-- 각 페이지 번호로 이동할 수 있는 URL을 생성하여 pageUrl에 저장 --%>
    <c:url var="pageUrl" value="/servicearea/servicearea_theme.do">
        <c:param name="lineName" value="${lineName}" />
        <c:param name="page" value="${i}" />
    </c:url>

    <%-- 반복중인 페이지 번호와 현재 위치한 페이지 번호가 같은 경우에 대한 분기 --%>
    <c:choose>
        <c:when test="${pageHelper.page == i}">
                <li class="active"><a href="#">${i}</a></li>
        </c:when>
        <c:otherwise>
                <li>
                    <a href="${pageUrl}">${i}</a>
                </li>
        </c:otherwise>
    </c:choose>
</c:forEach>

<%--
    ===== 다음 그룹으로의 이동 버튼 표시하기 =====
    - 이전 그룹 표시하기와 동일한 원리
    - 맨 마지막 그룹에 위치하고 있다면 링크를 적용하지 않는다.
--%>
                <!-- 다음 그룹 -->
<c:choose>
    <c:when test="${pageHelper.nextPage > 0}">
        <%-- 다음 그룹에 대한 페이지 번호가 존재한다면? --%>
        <%-- 다음 그룹으로 이동하기 위한 URL을 생성하서 "nextUrl"에 저장 --%>
        <c:url var="nextUrl" value="/servicearea/servicearea_theme.do">
            <c:param name="lineName" value="${lineName}" />
            <c:param name="page" value="${pageHelper.nextPage}" />
        </c:url>

                <li>
                    <a href="${nextUrl}">
                        <span aria-hideden="true">&raquo;</span>
                    </a>
                </li>
    </c:when>
    <c:otherwise>
                <li class="disabled">
                    <a href="#">
                        <span aria-hideden="true">&raquo;</span>
                    </a>
                </li>
    </c:otherwise>
</c:choose>
            </ul>
            <!--
                End
                ### 검색어가 있는 경우 페이지 번호도 검색 결과 수에 따라 재구성 된다.
            -->
        </nav>
        <!-- // pagination -->
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
    <script src="${pageContext.request.contextPath}/assets/js/servicearea_line.js"></script>
</body>
</html>
