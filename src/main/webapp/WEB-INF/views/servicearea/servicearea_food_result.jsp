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
    <h3 class="color3">휴게소 대표메뉴</h3>
</article>
<!-- //page header -->

<div class="row clearfix">

    <!-- 서브 컨텐츠 -->
    <article class="col-md-10 pull-left">

        <!-- 검색 결과 -->
        <div class="line_title clearfix">
            <h4 class="pull-left">
                <span class="icon">
                    <img src="${pageContext.request.contextPath}/images/servicearea/line/${routeName}.gif" alt="노선" width="27" />
                </span>
                <span class="name">${routeName}</span>
            </h4>
        </div>
        <table class="table table-bordered table-hover margin_top20">
            <colgroup>
                <col style="width:35%" />
                <col style="width:35%" />
                <col />
            </colgroup>
            <thead>
                <tr>
                    <th class="text-center">휴게소</th>
                    <th class="text-center">메뉴명</th>
                    <th class="text-center">가격</th>
                </tr>
            </thead>
            <tbody>
<%--
    ===== 조회 결과가 존재하는 경우와 그렇지 않은 경우 구분하기 =====
    - 조회 결과를 갖는 컬렉션의 size()가 0보다 큰 경우와
        그렇지 않은 경우를 분기한다.
--%>
<c:choose>
    <c:when test="${fn: length(groupList) > 0}">
        <%--
            ===== 조회결과가 존재하는 경우 =====
            - 컬렉션에 저장된 내용을 반복문 안으로 조회결과를 출력한다.
        --%>
        <c:forEach var="item" items="${groupList}">
                <tr class="text-center">
                    <td>
                        <a href="${pageContext.request.contextPath}/servicearea/servicearea_view_item.do?servicearea_groupId=${item.servicearea_groupId}">
                            <span class="name">${item.serviceareaName}</span>
                        </a>
                    </td>
            <c:choose>
                <c:when test="${item.foodBatchMenu != null}">
                    <td>${item.foodBatchMenu}</td>
                    <td>${item.foodSalePrice}</td>
                </c:when>
                <c:otherwise>
                    <td><img src="${pageContext.request.contextPath}/images/servicearea/x_mark.png" alt="X" width="15" /></td>
                    <td><img src="${pageContext.request.contextPath}/images/servicearea/x_mark.png" alt="X" width="15" /></td>
                </c:otherwise>
            </c:choose>
                </tr>
        </c:forEach>
    </c:when>

    <c:otherwise>
                <%-- 조회결과가 존재하지 않는 경우 --%>
                <tr class="text-center">
                    <td colspan="3">조회된 휴게소가 없습니다.</td>
                </tr>
    </c:otherwise>
</c:choose>
            </tbody>
        </table>
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
        <c:url var="prevUrl" value="/servicearea/servicearea_food_result.do">
            <c:param name="routeName" value="${routeName}" />
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
    <c:url var="pageUrl" value="/servicearea/servicearea_food_result.do">
        <c:param name="routeName" value="${routeName}" />
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
        <c:url var="nextUrl" value="/servicearea/servicearea_food_result.do">
            <c:param name="routeName" value="${routeName}" />
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
</body>
</html>
