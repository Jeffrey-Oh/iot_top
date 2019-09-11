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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/searchbar.css">
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

<div class="container-fluid member_userPw_update">
    <!-- Nav tabs -->
    <ul class="nav nav-pills nav-justified" role="tablist">
        <li role="presentation">
            <a href="${pageContext.request.contextPath}/member/member_userId_search.do">아이디 찾기</a>
        </li>
        <li role="presentation" class="active">
            <a href="${pageContext.request.contextPath}/member/member_userPw_search.do">비밀번호 찾기</a>
        </li>
    </ul>

    <div class="text-center margin_top50">
        <p class="txt1">새로운 비밀번호를 입력해 주세요.</p>
    </div>

    <!-- 서브 컨텐츠 -->
    <article class="col-md-6 col-md-offset-3 margin_top10">
        <!-- 아이디 찾기 폼 -->
        <form method="POST" class="form-horizontal margin_top40" id="member_userPw_update" role="form">
            <legend class="blind">이메일 인증 폼</legend>

            <!-- 상태유지를 위한 hidden타입 -->
            <input type="hidden" name="userId" value="${userId}" id="userId" /><!-- 아이디 -->

            <!-- 비밀번호 -->
            <div class="form-group">
                <label for="userPw" class="col-md-3 control-label">비밀번호</label>
                <div class="col-md-7">
                    <input type="password" name="userPw" id="userPw" class="form-control" placeholder="비밀번호를 입력해 주세요." />
                    <p class="color9 margin_top10">적어도 1개 이상의 숫자와 1개 이상의 특수문자가 포함되며<br /> 6자리 이상의 형식이어야 합니다.</p>
                </div>
            </div>
            <!-- // 비밀번호 -->

            <!-- 비밀번호 확인 -->
            <div class="form-group margin_top20">
                <label for="userPwCheck" class="col-md-3 control-label">비밀번호 확인</label>
                <div class="col-md-7">
                    <input type="password" name="userPwCheck" id="userPwCheck" class="form-control" />
                </div>
            </div>
            <!-- // 비밀번호 확인 -->

            <!-- 버튼 영역 -->
            <div class="form-group margin_top50">
                <div class="text-center">
                    <button type="button" class="btn btn_color1 btn-lg" id="userPw_update_btn">확인</button>
                </div>
            </div>
            <!-- // 버튼 영역 -->
        </form>
        <!-- // 아이디 찾기 폼 -->
    </article>
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
    <script src="${pageContext.request.contextPath}/assets/js/member_common.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/member_userPw_update.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/searchbar.js"></script><!-- searchbar를 사용하는 경우 장착 -->
</body>
</html>
