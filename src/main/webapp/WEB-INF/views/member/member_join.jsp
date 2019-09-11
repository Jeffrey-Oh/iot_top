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
    <h3 class="color">회원가입</h3>
</article>
<!-- //page header -->

<!-- join step -->
<article class="join_step text-center">
    <ol class="list-unstyled clearfix">
        <li class="pull-left active">
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
        <li class="pull-left">
            <span class="txt">4. 가입완료</span>
        </li>
    </ol>
</article>
<!-- //join step -->

<!-- 내용 삽입 -->
<div class="container-fluid member_join">
<!-- 서브 컨텐츠 -->
    <div class="text-center">
        <p class="title margin_top20">회원가입여부 및 본인확인</p>
        <p class="txt1 margin_top20">인증정보는 회원가입여부 확인 및 입력편의 제공 및 인증 목적으로만 이용됩니다.<br />
        안전한 회원가입을 위해 아래의 인증 절차를 진행해 주세요.</p>
    </div>
    <article class="col-md-6 col-md-offset-3">
        <form action="${pageContext.request.contextPath}/member/member_join_ok.do" method="POST" class="form-horizontal margin_top50" id="member_join_or_userId_userPw_search" role="form">
            <legend class="blind">이메일 인증 폼</legend>

            <!-- 상태유지를 위한 값의 처리 -->
            <input type="hidden" name="cert_ok" id="cert_ok" value="" /><!-- 이메일인증하기 Boolean 저장 -->

            <!-- 이름 -->
            <div class="form-group">
                <label for="userName" class="col-md-3 control-label">이름</label>
                <div class="col-md-9 form-inline">
                    <input type="text" name="userName" id="userName" class="form-control" placeholder="이름을 입력해 주세요." />
                </div>
            </div>
            <!-- // 이름 -->

            <!-- 성별 -->
            <div class="form-group">
                <label for="gender" class="col-md-3 control-label">성별</label>
                <div class="col-md-9 form-inline">
                    <select name="gender" id="gender" class="form-control">
                        <option value="">--- 성별 ---</option>
                        <option value="M">남자</option>
                        <option value="F">여자</option>
                    </select>
                </div>
            </div>
            <!-- // 성별 -->

            <!-- 생년월일 -->
            <div class="form-group">
                <label for="year" class="col-md-3 control-label">생년월일</label>
                <div class="col-md-9 form-inline">
                    <select name="year" id="year" class="form-control">
                        <option value="">--- 년 ---</option>
                    </select>
                    <select name="month" id="month" class="form-control">
                        <option value="">--- 월 ---</option>
                    </select>
                    <select name="day" id="day" class="form-control">
                        <option value="">--- 일 ---</option>
                    </select>
                </div>
            </div>
            <!-- // 생년월일 -->

            <!-- 이메일 -->
            <div class="form-group">
                <label for="email" class="col-md-3 control-label">이메일</label>
                <div class="col-md-9 form-inline">
                    <input type="email" name="email" id="email" class="form-control" placeholder="이메일을 입력해 주세요." />
                    <button type="button" class="btn btn_color3" id="certBtn">인증번호 받기</button>
                </div>
            </div>
            <!-- // 이메일 -->

            <!-- 이메일 인증번호 -->
            <div class="form-group">
                <label for="certNum" class="col-md-3 control-label">이메일 인증번호</label>
                <div class="col-md-9 form-inline">
                    <input type="text" name="certNum" id="certNum" class="form-control" placeholder="인증번호를 입력해 주세요." />
                    <button type="button" class="btn btn_color3" id="certOkBtn">인증하기</button>
                </div>
            </div>
            <!-- // 이메일 인증번호 -->

            <div class="form-group margin_top50">
                <div class="text-center">
                    <button type="submit" class="btn btn_color1 btn-lg">확인</button>
                </div>
            </div>
        </form>
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
    <script src="${pageContext.request.contextPath}/assets/js/member_join_or_userId_userPw_search.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/searchbar.js"></script><!-- searchbar를 사용하는 경우 장착 -->
</body>
</html>
