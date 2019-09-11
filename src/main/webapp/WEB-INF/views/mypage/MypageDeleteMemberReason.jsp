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
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/mypage-delete-reason.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/searchbar.css"><!-- searchbar를 사용하는 경우 장착 -->
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

<!-- page header -->
<article class="page-header">
    <h3 class="color3">탈퇴 사유</h3>
</article>
<!-- //page header -->

<form action="${pageContext.request.contextPath}/member/member_out_ok.do" method="POST" class="form-horizontal" id="member_out_reason" role="form">
    <div class="form_box">
        <div class="form-group">
        	<h2 class="delete_top">탈퇴를 결심하게 된 이유를 선택해 주세요.</h2>

        	<div class="checkbox">
                <label for="reason1">
                   	<input type="checkbox" name="reason" class="reason" id="reason1" value="여행을_가지_않게_되어서" /> 여행을 가지 않게 되어서
                </label>
            </div>
            <div class="checkbox">
                <label for="reason2">
                   	<input type="checkbox" name="reason" class="reason" id="reason2" value="광고가_너무 많아서" /> 광고가 너무 많아서
                </label>
            </div>
            <div class="checkbox">
                <label for="reason3">
                   	<input type="checkbox" name="reason" class="reason" id="reason3" value="재가입을_위해서" /> 재가입을 위해서
                </label>
            </div>
            <div class="checkbox">
                <label for="reason4">
                   	<input type="checkbox" name="reason" class="reason" id="reason4" value="사이트_이용방법이_어려워서" /> 사이트 이용방법이 어려워서
                </label>
            </div>
            <div class="checkbox">
                <label for="reason5">
                   	<input type="checkbox" name="reason" class="reason" class="reason" id="reason5" value="개인정보_보호를_위해" /> 개인정보 보호를 위해
                </label>
            </div>
            <div class="checkbox">
                <label for="reason6">
                   	<input type="checkbox" name="reason" class="reason" id="reason6" value="혜택부족" /> 혜택부족
                </label>
            </div>

            <div class="checkbox">
                <label for="input_enable">
                	<input type="checkbox" name="reason" class="reason" id="input_enable" value="기타" /> 기타
                </label>
                <div>
               		<textarea name="reasonEtc" id="reasonEtc" rows="7" class="margin_top10" disabled style='width:100%; resize:none;'></textarea>
                </div>
            </div>
        </div>

        <div class="form-group">
        	<div class="text-center">
                <button type="submit" class="btn btn-danger">탈퇴하기</button>
        	</div>
    	</div>
    </div>
</form>
<!-- ===== 개인 작업 영역 ===== -->
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
    <script src="${pageContext.request.contextPath}/assets/js/mypage_delete_member_reason.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/searchbar.js"></script><!-- searchbar를 사용하는 경우 장착 -->
</body>
</html>
