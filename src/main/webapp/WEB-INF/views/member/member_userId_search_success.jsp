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
    <h3 class="color3">ID/PW 찾기</h3>
</article>
<!-- //page header -->

<div class="container-fluid member_userId_search_success">
    <!-- Nav tabs -->
    <ul class="nav nav-pills nav-justified" role="tablist">
        <li role="presentation" class="active">
            <a href="${pageContext.request.contextPath}/member/member_userId_search.do">아이디 찾기</a>
        </li>
        <li role="presentation">
            <a href="${pageContext.request.contextPath}/member/member_userPw_search.do">비밀번호 찾기</a>
        </li>
    </ul>

    <div class="text-center margin_top50">
        <p class="txt1">아이디 조회 결과</p>
        <p class="txt2 margin_top20 color1">"${userId}"</p>
        <p class="txt3 margin_top10 color9">(${year}년 ${month}월 ${day}일 가입)</p>
    </div>

    <!-- 버튼 영역 -->
    <div class="form-group margin_top50">
        <div class="text-center">
            <form action="${pageContext.request.contextPath}/member/member_userPw_update.do" method="POST" class="form-inline" role="form">
                <input type="hidden"  name="memberId" value="${memberId}" />
                <input type="hidden"  name="userId" value="${userId}" />

                <a href="${pageContext.request.contextPath}/member/member_login.do" class="btn btn_color1 btn-lg margin3" title="로그인하기">로그인하기</a>
                <button type="submit" class="btn btn_color2 btn-lg margin_left10">비밀번호 재설정</button>
            </form>
        </div>
    </div>
    <!-- // 버튼 영역 -->
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
    <script src="${pageContext.request.contextPath}/assets/js/searchbar.js"></script><!-- searchbar를 사용하는 경우 장착 -->
</body>
</html>
