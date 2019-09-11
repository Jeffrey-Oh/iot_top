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
    <div class="container">
        <!-- 메인 컨텐츠 삽입 -->

<!-- 여기에 contents 삽입 시작 -->
<!-- ===== 개인 작업 영역 ===== -->
<!-- page header -->
<article class="page-header">
    <h3 class="color3">회원 상세 보기</h3>
</article>
<!-- //page header -->

<!-- 조회결과를 출력하기 위한 표 -->
<table class="table table-bordered member_management_view">
    <tbody>
        <tr>
            <th class="info text-center" width="200">회원번호</th>
            <td>${item.memberId}</td>
        </tr>
        <tr>
            <th class="info text-center">이름</th>
            <td>${item.userName}</td>
        </tr>
        <tr>
            <th class="info text-center">성별</th>
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
        </tr>
        <tr>
            <th class="info text-center">생년월일</th>
            <td>${item.birthDate}</td>
        </tr>
        <tr>
            <th class="info text-center">아이디</th>
            <td>${item.userId}</td>
        </tr>
        <tr>
            <th class="info text-center">연락처</th>
            <td>${item.phone}</td>
        </tr>
        <tr>
            <th class="info text-center">이메일</th>
            <td>${item.email}</td>
        </tr>
        <tr>
            <th class="info text-center">우편번호</th>
            <td>${item.postcode}</td>
        </tr>
        <tr>
            <th class="info text-center">주소</th>
            <td>${item.address1}</td>
        </tr>
        <tr>
            <th class="info text-center">상세주소</th>
            <td>${item.address2}</td>
        </tr>
        <tr>
            <th class="info text-center">마케팅활용동의</th>
<%-- 마케팅 활용 동의에 따라 값 표현 분기 --%>
<c:choose>
    <c:when test="${item.marketingCheckedDate != null}">
            <td>${item.marketingCheckedDate}</td>
    </c:when>
    <c:otherwise>
            <td>동의 안함</td>
    </c:otherwise>
</c:choose>
        </tr>
        <tr>
            <th class="info text-center">이메일수신동의</th>
<%-- 이메일 수신 동의에 따라 값 표현 분기 --%>
<c:choose>
    <c:when test="${item.toEmailCheckedDate != null}">
            <td>${item.toEmailCheckedDate}</td>
    </c:when>
    <c:otherwise>
            <td>동의 안함</td>
    </c:otherwise>
</c:choose>
        </tr>
        <tr>
            <th class="info text-center">SMS수신동의</th>
<%-- 문자 수신 동의에 따라 값 표현 분기 --%>
<c:choose>
    <c:when test="${item.toSmsCheckedDate != null}">
            <td>${item.toSmsCheckedDate}</td>
    </c:when>
    <c:otherwise>
            <td>동의 안함</td>
    </c:otherwise>
</c:choose>
        </tr>
        <tr>
            <th class="info text-center">생성일</th>
            <td>${item.regDate}</td>
        </tr>
        <tr>
            <th class="info text-center">수정일</th>
<%-- 회원정보 수정 유무에 따라 값 표현 분기 --%>
<c:choose>
    <c:when test="${item.editDate != null}">
            <td>${item.editDate}</td>
    </c:when>
    <c:otherwise>
            <td>-</td>
    </c:otherwise>
</c:choose>
        </tr>
        <tr>
            <th class="info text-center">삭제예정일</th>
<%-- 휴면계정 삭제일 유무에 따라 값 표현 분기 --%>
<c:choose>
    <c:when test="${item.deleteDate != null}">
            <td>${item.deleteDate}</td>
    </c:when>
    <c:otherwise>
            <td>-</td>
    </c:otherwise>
</c:choose>
        </tr>
        <tr>
            <th class="info text-center">등급</th>
            <td><span class="lv ${item.grade}">${item.grade}</span></td>
        </tr>
        <tr>
            <th class="info text-center">프로필사진</th>
<%-- 프로필 사진 유무에 따라 값 표현 분기 --%>
<c:choose>
    <c:when test="${item.profileImg != null}">
            <td>
                <img src="${pageContext.request.contextPath}/download.do?file=${item.profileImg}&size=100x100&crop=true" class="profile img-rounded" alt="프로필 사진" />
                &nbsp;&nbsp;${item.profileImg}
            </td>
    </c:when>
    <c:otherwise>
            <td>-</td>
    </c:otherwise>
</c:choose>
        </tr>
    </tbody>
</table>
<!-- // 조회결과를 출력하기 위한 표 -->

<%--
    - 수정, 삭제시에는 상세 조회와 마찬가지로
        대상을 식별해야 하기 때문에,
        회원의 일련번호(Primary Key)를 GET 파라미터로 전달한다.
--%>
<!-- 버튼 시작 -->
<div class="text-center">
    <a href="${pageContext.request.contextPath}/member/member_management.do" class="btn btn_color1 radius3">목록</a>
</div>
<!-- // 버튼 시작 -->

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
    <script src="${pageContext.request.contextPath}/assets/js/searchbar.js"></script><!-- searchbar를 사용하는 경우 장착 -->
</body>
</html>
