<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="/WEB-INF/inc/Head.jsp"%>
    <!-- mainpage.css || subpage.css 중 1개는 꼭 참조 해야 함. -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/subpage.css"><!-- searchbar를 사용하는 경우 장착 -->
    <!-- 개인 css 링크 참조 영역 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/searchbar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/member.css" />
</head>
<body>
<!-- 전체를 감싸는 div -->
<div class="container-fluid">
<%@ include file="/WEB-INF/inc/Gnb.jsp" %>
<%@ include file="/WEB-INF/inc/Visual.jsp" %>
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
        <li class="pull-left active">
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

<div class="container-fluid member_join_agreement">

    <div class="text-center">
        <p class="title margin_top20">약관동의</p>
        <p class="txt1 margin_top20">가입을 위해 아래의 이용약관 개인정보 처리방침을 확인하신 후 동의를 해주세요.</p>
    </div>

    <form action="${pageContext.request.contextPath}/member/member_join_agreement_ok.do" method="POST" id="member_join_agreement" class="form-horizontal margin_top40" role="form">
        <legend class="blind">약관동의 폼</legend>

        <!-- 서비스 이용약관 -->
        <div class="form-group">
            <label for="serviceText" class="col-md-2 control-label">서비스 이용약관</label>
            <div class="col-md-10 margin_top10">
                <textarea name="serviceText" id="serviceText" class="form-control" rows="10" readonly>
                    ${policyItem.agreementDoc}
                </textarea>
            </div>
            <div class="col-md-10 col-md-offset-2 form-inline">
                <div class="checkbox">
                    <label>
                        <input type="checkbox" id="serviceCheck" class="agreement" name="serviceCheck">
                        동의(필수)
                    </label>
                </div>
            </div>
        </div>
        <!-- // 서비스 이용약관 -->

        <!-- 개인정보 수집 및 이용동의 -->
        <div class="form-group">
            <label for="InfoCollectionText" class="col-md-2 control-label">개인정보 수집 및 이용동의</label>
            <div class="col-md-10 margin_top10">
                <textarea name="infoCollectionText" id="infoCollectionText" class="form-control" rows="10" readonly>
                    ${policyItem.infoCollectionDoc}
                </textarea>
            </div>
            <div class="col-md-10 col-md-offset-2 form-inline">
                <div class="checkbox">
                    <label>
                        <input type="checkbox" id="InfoCollectionCheck" class="agreement" name="infoCollectionCheck">
                        동의(필수)
                    </label>
                </div>
            </div>
            <!-- <label for="InfoCollectionText" class="col-md-2 control-label"></label> -->
        </div>
        <!-- // 개인정보 수집 및 이용동의 -->

        <!-- 개인정보 처리 업무 위탁 동의 -->
        <div class="form-group">
            <label for="consignmentText" class="col-md-2 control-label">개인정보 처리 업무 위탁 동의</label>
            <div class="col-md-10 margin_top10">
                <textarea name="consignmentText" id="consignmentText" class="form-control" rows="10" readonly>
                    ${policyItem.communityDoc}
                </textarea>
            </div>
            <div class="col-md-10 col-md-offset-2 form-inline">
                <div class="checkbox">
                    <label>
                        <input type="checkbox" id="consignmentCheck" class="agreement" name="consignmentCheck">
                        동의(필수)
                    </label>
                </div>
            </div>
        </div>
        <!-- // 개인정보 처리 업무 위탁 동의 -->

        <!-- 개인정보의 파기에 관한 동의 -->
        <div class="form-group">
            <label for="marketingText" class="col-md-2 control-label">마케팅 목적 이용에 관한 동의</label>
            <div class="col-md-10 margin_top10">
                <textarea name="marketingText" id="marketingText" class="form-control" rows="10" readonly>
                    ${policyItem.emailCollectionDoc}
                </textarea>
            </div>
            <div class="col-md-10 col-md-offset-2 form-inline">
                <div class="checkbox">
                    <label>
                        <input type="checkbox" id="marketingCheck" class="agreement" name="marketingCheck">
                        동의(선택)
                    </label>
                </div>
            </div>
        </div>
        <!-- // 개인정보의 파기에 관한 동의 -->

        <!-- 모두 선택 체크박스 -->
        <div class="form-group">
            <div class="col-md-offset-2 col-md-10 form-inline">
                <div class="checkbox">
                    <label>
                        <input type="checkbox" name="allCheck" id="allCheck" data-all-check=".agreement" value="allCheck">
                        모든 방침에 동의합니다.
                    </label>
                </div>
            </div>
        </div>
        <!-- // 모두 선택 체크박스 -->

        <div class="form-group margin_top50">
            <div class="text-center">
                <button type="button" id="backBtn" class="btn btn_color4 btn-lg radius3">동의안함</button>
                <button type="submit" class="btn btn_color1 btn-lg margin_left10">동의</button>
            </div>
        </div>
    </form>
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
    <script src="${pageContext.request.contextPath}/assets/js/member_join_agreement.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/searchbar.js"></script><!-- searchbar를 사용하는 경우 장착 -->
</body>
</html>
