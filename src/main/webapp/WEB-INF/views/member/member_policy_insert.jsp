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
    <h3 class="color3">정책안내 저장</h3>
</article>
<!-- //page header -->

<div class="container-fluid member_join_agreement">
    <!-- 저장을 위한 폼 시작 -->
    <form action="${pageContext.request.contextPath}/member/member_policy_insert_ok.do" method="POST" class="form-horizontal" role="form" id="policy_insert_form">
        <legend class="blind">정책안내 저장</legend>

        <div class="form-group">
            <label for="agreementDoc" class="col-md-2 control-label">이용약관</label>
            <div class="col-md-10">
                <textarea name="agreementDoc" id="agreementDoc" class="form-control serviceText" rows="30"></textarea>
            </div>
        </div>

        <div class="form-group">
            <label for="infoCollectionDoc" class="col-md-2 control-label">개인정보처리방침</label>
            <div class="col-md-10">
                <textarea name="infoCollectionDoc" id="infoCollectionDoc" class="form-control serviceText" rows="30"></textarea>
            </div>
        </div>

        <div class="form-group">
            <label for="communityDoc" class="col-md-2 control-label">게시글관리규정</label>
            <div class="col-md-10">
                <textarea name="communityDoc" id="communityDoc" class="form-control serviceText" rows="30"></textarea>
            </div>
        </div>

        <div class="form-group">
            <label for="emailCollectionDoc" class="col-md-2 control-label">이메일무단수집거부</label>
            <div class="col-md-10">
                <textarea name="emailCollectionDoc" id="emailCollectionDoc" class="form-control serviceText" rows="30"></textarea>
            </div>
        </div>

        <div class="form-group">
            <div class="col-md-10 col-md-offset-2">
                <button type="submit" class="btn btn_color2">저장</button>
            </div>
        </div>
    </form>
    <!-- // 저장을 위한 폼 시작 -->
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
    <script src="${pageContext.request.contextPath}/assets/js/member_policy_insert.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/searchbar.js"></script><!-- searchbar를 사용하는 경우 장착 -->
</body>
</html>
