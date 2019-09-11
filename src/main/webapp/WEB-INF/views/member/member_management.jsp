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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/searchbar.css"><!-- searchbar를 사용하는 경우 장착 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/member.css" />
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
    <div class="container-fluid member_management">
        <!-- 메인 컨텐츠 삽입 -->

<!-- 여기에 contents 삽입 시작 -->
<!-- ===== 개인 작업 영역 ===== -->
<!-- page header -->
<article class="page-header clearfix">
    <h3 class="color3">회원관리</h3>

    <div class="pull-right management_btn">
        <!--
            ===== 목록 페이지의 검색 폼 구성 =====
            - 목록 페이지에서 입력한 검색어는
                목록의 결과 표시에 반영되어야 하기 때문에,
                검색폼에서는 사용자의 입력값을 목록페이지 스스로에게 전송하도록
                action을 지정해야 한다.
        -->
        <form action="${pageContext.request.contextPath}/member/member_management.do" method="GET" class="member_management_search" role="form">
            <legend class="blind">회원이름 검색</legend>

            <div class="input-group">
                <label for="keyword" class="control-label"></label>
                <input type="text" class="form-control" name="keyword" id="keyword" placeholder="회원이름 검색" value="${keyword}" />

                <span class="input-group-btn">
                    <!-- 회원이름 검색 버튼 -->
                    <button class="btn btn_color4" type="submit">
                        <i class="glyphicon glyphicon-search"></i>
                    </button>

                    <!-- 휴면계정 삭제 버튼 -->
                    <button type="button" id="inactive_delete_btn" class="btn btn_color1 margin_left10">휴면계정 삭제</button>
                </span>
            </div>
        </form>
    </div>
</article>
<!-- //page header -->

<!-- 조회결과를 출력하기 위한 표 -->
<table class="table member_management_list">
    <thead>
        <!-- 목록의 제목 표시하기 -->
        <tr>
            <th class="info text-center">회원번호</th>
            <th class="info text-center">이름</th>
            <th class="info text-center">성별</th>
            <th class="info text-center">생년월일</th>
            <th class="info text-center">아이디</th>
            <th class="info text-center">연락처</th>
            <th class="info text-center">이메일</th>
            <th class="info text-center">우편번호</th>
            <th class="info text-center">주소</th>
            <th class="info text-center">상세주소</th>
            <th class="info text-center">마케팅활용동의</th>
            <th class="info text-center">이메일수신동의</th>
            <th class="info text-center">SMS수신동의</th>
            <th class="info text-center">생성일</th>
            <th class="info text-center">수정일</th>
            <th class="info text-center">삭제예정일</th>
            <th class="info text-center">등급</th>
            <th class="info text-center">프로필사진</th>
        </tr>
    </thead>
    <tbody>
<%--
    ===== 조회 결과가 존재하는 경우와 그렇지 않은 경우 구분하기 =====
    - 조회 결과를 갖는 컬렉션의 size()가 0보다 큰 경우와
        그렇지 않은 경우를 분기한다.
--%>
<c:choose>
    <c:when test="${fn: length(list) > 0}">
        <%--
            ===== 조회결과가 존재하는 경우 =====
            - 컬렉션에 저장된 내용을 반복문 안으로 조회결과를 출력한다.
        --%>
        <c:forEach var="item" items="${list}" varStatus="status">
        <tr class="text-center">
            <td>${item.memberId}</td>
            <td>
                <a href="${pageContext.request.contextPath}/member/member_management_view.do?memberId=${item.memberId}">
                    ${item.userName}
                </a>
            </td>
            <td>
            <c:choose>
                <c:when test="${item.gender == 'M'}">
                남자
                </c:when>
                <c:otherwise>
                여자
                </c:otherwise>
            </c:choose>
            </td>
            <td>${item.birthDate}</td>
            <td>${item.userId}</td>
            <td>${item.phone}</td>
            <td>${item.email}</td>
            <td>${item.postcode}</td>
            <td>${item.address1}</td>
            <td>${item.address2}</td>
            <%-- 마케팅 활용 동의에 따라 값 표현 분기 --%>
            <c:choose>
                <c:when test="${item.marketingCheckedDate != null}">
            <td>${item.marketingCheckedDate}</td>
                </c:when>
                <c:otherwise>
            <td>동의 안함</td>
                </c:otherwise>
            </c:choose>
            <%-- 이메일 수신 동의에 따라 값 표현 분기 --%>
            <c:choose>
                <c:when test="${item.toEmailCheckedDate != null}">
            <td>${item.toEmailCheckedDate}</td>
                </c:when>
                <c:otherwise>
            <td>동의 안함</td>
                </c:otherwise>
            </c:choose>
            <%-- 문자 수신 동의에 따라 값 표현 분기 --%>
            <c:choose>
                <c:when test="${item.toSmsCheckedDate != null}">
            <td>${item.toSmsCheckedDate}</td>
                </c:when>
                <c:otherwise>
            <td>동의 안함</td>
                </c:otherwise>
            </c:choose>
            <td>${item.regDate}</td>
            <%-- 회원정보 수정 유무에 따라 값 표현 분기 --%>
            <c:choose>
                <c:when test="${item.editDate != null}">
            <td>${item.editDate}</td>
                </c:when>
                <c:otherwise>
            <td>-</td>
                </c:otherwise>
            </c:choose>
            <%-- 휴면계정 삭제일 유무에 따라 값 표현 분기 --%>
            <c:choose>
                <c:when test="${item.deleteDate != null}">
            <td>${item.deleteDate}</td>
                </c:when>
                <c:otherwise>
            <td>-</td>
                </c:otherwise>
            </c:choose>
            <td><span class="lv ${item.grade}">${item.grade}</span></td>
            <%-- 프로필 사진 유무에 따라 값 표현 분기 --%>
            <c:choose>
                <c:when test="${item.profileImg != null}">
            <td>
                <img src="${pageContext.request.contextPath}/download.do?file=${item.profileImg}&size=24x24&crop=true" class="profile img-rounded" alt="프로필 사진" />
            </td>
                </c:when>
                <c:otherwise>
            <td>-</td>
                </c:otherwise>
            </c:choose>
        </tr>
        </c:forEach>
    </c:when>
    <c:otherwise>
        <%-- 조회결과가 존재하지 않는 경우 --%>
        <tr class="text-center">
            <td colspan="18">조회된 회원 목록이 없습니다.</td>
        </tr>
    </c:otherwise>
</c:choose>
    </tbody>
</table>
<!-- // 조회결과를 출력하기 위한 표 -->

<!--
    ===== 페이지 번호 출력을 위한 처리 =====
    - JSP에서 PageHelper로부터 얻은 값을 Service를 통해
        Mapper에 전달하면 Mapper에서는 해당 페이지에 맞는
        결과만을 조회한다.
    - 반면 JSP에서는 PageHelper가 가지고 있는 값을 활용하여
        페이지 번호를 출력해야 한다.
-->
<!-- pagination -->
<nav class="text-center">
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
        <c:url var="prevUrl" value="/member/member_management.do">
            <c:param name="keyword" value="${keyword}" />
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
    <c:url var="pageUrl" value="/member/member_management.do">
        <c:param name="keyword" value="${keyword}" />
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
        <c:url var="nextUrl" value="/member/member_management.do">
            <c:param name="keyword" value="${keyword}" />
            <c:param name="page" value="${pageHelper}" />
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
    <script src="${pageContext.request.contextPath}/assets/js/member_management.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/searchbar.js"></script><!-- searchbar를 사용하는 경우 장착 -->
</body>
</html>
