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
    <h3 class="color3">회원가입</h3>
</article>
<!-- //page header -->

<!-- join step -->
<article class="join_step text-center">
    <ol class="list-unstyled clearfix">
        <li class="pull-left">
            <span class="txt">1. 회원가입여부 및 본인확인</span>
            <span class="glyphicon glyphicon-menu-right"></span>
        </li>
        <li class="pull-left">
            <span class="txt">2. 약관동의</span>
            <span class="glyphicon glyphicon-menu-right"></span>
        </li>
        <li class="pull-left">
            <span class="txt">3. 정보입력</span>
            <span class="glyphicon glyphicon-menu-right"></span>
        </li>
        <li class="pull-left active">
            <span class="txt">4. 가입완료</span>
        </li>
    </ol>
</article>
<!-- //join step -->

<!-- 서브 컨텐츠 -->
<article class="col-md-10 col-md-offset-1">
    <!-- 내용 삽입 -->
    <div class="container-fluid member_join_success margin_top50">
        <div class="text-center">
            <p class="title">가입완료</p>
            <p class="txt1">회원가입을 진심으로 축하드립니다.</p>
            <p class="txt2 margin_top30"><span class="color1">"${userName}"</span>님의 회원가입이 완료되었습니다.<br />
            등록하신 정보는 로그인 후 마이페이지 &gt; 회원정보수정에서 변경하실 수 있습니다.</p>
        </div>

        <!-- 버튼 영역 -->
        <div class="form-group margin_top50">
            <div class="text-center">
                <a href="${pageContext.request.contextPath}/member/member_login.do" class="btn btn_color1 btn-lg radius3" title="로그인하기">로그인하기</a>
                <a href="${pageContext.request.contextPath}/index.do" class="btn btn_color2 btn-lg margin_left10 radius3" title="메인으로 가기">메인으로 가기</a>
            </div>
        </div>
        <!-- // 버튼 영역 -->
    </div>
    <!-- // 내용 삽입 -->
</article>
<!-- //서브 컨텐츠 -->

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
