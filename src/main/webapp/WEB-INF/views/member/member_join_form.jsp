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
        <li class="pull-left active">
            <span class="txt">3. 정보입력</span>
            <span class="glyphicon glyphicon-menu-right"></span>
        </li>
        <li class="pull-left">
            <span class="txt">4. 가입완료</span>
        </li>
    </ol>
</article>
<!-- //join step -->

<!-- 서브 컨텐츠 -->
<article class="col-md-10 col-md-offset-1">
    <!-- 내용 삽입 -->
    <div class="container-fluid member_join_form">
        <div class="text-center">
            <p class="title margin_top20">정보입력</p>
            <p class="txt1 margin_top20">회원님의 개인정보는 동의 없이 공개되지 않으며, 개인정보 처리방침 가이드에 맞춰 보호 받고 있습니다.</p>
        </div>

        <!-- 회원가입 폼 -->
        <form action="${pageContext.request.contextPath}/member/member_join_form_ok.do" method="POST" class="form-horizontal margin_top40" id="member_join_form" enctype="multipart/form-data">
            <fieldset>
                <legend class="blind">회원가입</legend>

                <!-- 아이디 중복 검사 확인 결과를 담은 hidden -->
                <input type="hidden" name="userId_check_ok" id="userId_check_ok" />

                <div class="form-group">
                    <p class="col-md-10 text-right"><span class="identify">*</span> 표시는 필수 입력입니다.</p>
                </div>

                <!-- 이름 -->
                <div class="form-group">
                    <label for="userName" class="col-md-2 control-label">이름 <span class="identify">*</span></label>
                    <div class="col-md-10 form-inline">
                        <!-- 회원가입 앞 단계에서 받아온 정보는 변경할 수 없도록 출력만 한다. -->
                        <p class="form-control-static">${userName}</p>
                    </div>
                </div>
                <!-- // 이름 -->

                <!-- 성별 -->
                <div class="form-group">
                    <label for="gender" class="col-md-2 control-label">성별 <span class="identify">*</span></label>
                    <div class="col-md-10 form-inline">
                        <p class="form-control-static">
<%--
    회원가입 앞 단계에서 받아온 정보는 변경할 수 없도록 출력만 한다.
    - 남자인지 여자인지 gender 값에 따른 분기 출력
--%>
<c:choose>
    <c:when test="${gender == 'M'}">
                            남자
    </c:when>
    <c:otherwise>
                            여자
    </c:otherwise>
</c:choose>
                        </p>
                    </div>
                </div>
                <!-- // 성별 -->

                <!-- 생년월일 -->
                <div class="form-group">
                    <label for="year" class="col-md-2 control-label">생년월일 <span class="identify">*</span></label>
                    <div class="col-md-10 form-inline">
                        <!-- 회원가입 앞 단계에서 받아온 정보는 변경할 수 없도록 출력만 한다. -->
                        <p class="form-control-static">${year}년 ${month}월 ${day}일</p>
                    </div>
                </div>
                <!-- // 생년월일 -->

                <!-- 아이디 -->
                <div class="form-group">
                    <label for="userId" class="col-md-2 control-label">아이디 <span class="identify">*</span></label>
                    <div class="col-md-10 form-inline">
                        <input type="text" name="userId" id="userId" class="form-control" placeholder="아이디를 입력해 주세요." />
                        <button type="button" name="userId_check" id="userId_check" class="btn btn_color3" title="중복확인">중복확인</button>
                        <p class="color9 margin_top5">아이디는 영문과 숫자의 조합으로 생성 가능합니다.</p>
                    </div>
                </div>
                <!-- // 아이디 -->

                <!-- 비밀번호 -->
                <div class="form-group">
                    <label for="userPw" class="col-md-2 control-label">비밀번호 <span class="identify">*</span></label>
                    <div class="col-md-10 form-inline">
                        <input type="password" name="userPw" id="userPw" class="form-control" placeholder="비밀번호를 입력해 주세요." />
                        <p class="color9 margin_top5">적어도 1개 이상의 숫자와 1개 이상의 특수문자가 포함되며 6자리 이상의 형식이어야 합니다.</p>
                    </div>
                </div>
                <!-- // 비밀번호 -->

                <!-- 비밀번호 확인 -->
                <div class="form-group">
                    <label for="userPwCheck" class="col-md-2 control-label">비밀번호 확인 <span class="identify">*</span></label>
                    <div class="col-md-10 form-inline">
                        <input type="password" name="userPwCheck" id="userPwCheck" class="form-control" />
                    </div>
                </div>
                <!-- // 비밀번호 확인 -->

                <!-- 휴대전화 -->
                <div class="form-group">
                    <label for="phone" class="col-md-2 control-label">휴대전화 <span class="identify">*</span></label>
                    <div class="col-md-10 form-inline">
                        <input type="tel" name="phone" id="phone" class="form-control" placeholder="'-' 없이 입력해 주세요." />
                    </div>
                </div>
                <!-- // 휴대전화 -->

                <!-- 이메일 -->
                <div class="form-group">
                    <label for="email" class="col-md-2 control-label">이메일 <span class="identify">*</span></label>
                    <div class="col-md-10 form-inline">
                        <!-- 회원가입 앞 단계에서 받아온 정보는 변경할 수 없도록 출력만 한다. -->
                        <p class="form-control-static">${email}</p>
                    </div>
                </div>
                <!-- // 이메일 -->

                <!-- 우편번호 -->
                <div class="form-group">
                    <label for="postcode" class="col-md-2 control-label">주소 <span class="identify">*</span></label>
                    <div class="col-md-10 form-inline">
                        <input type="text" name="postcode" id="postcode" class="form-control" placeholder="우편번호" />
                        <input type="button" id="addrSearch" data-postcode="#postcode" data-addr1="#addr1" data-addr2="#addr2" class="btn btn_color3 form-control" value="우편번호검색" />
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-8 col-md-offset-2">
                        <input type="text" name="addr1" id="addr1" class="form-control" placeholder="주소" />
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-8 col-md-offset-2">
                        <input type="text" name="addr2" id="addr2" class="form-control" placeholder="상세주소" />
                    </div>
                </div>
                <!-- // 우편번호 -->

                <!-- 프로필 사진 -->
                <div class="form-group">
                    <label for="profileImg" class="col-md-2 control-label">프로필 사진</label>
                    <div class="col-md-10 form-inline">
                        <input type="file" name="profileImg" id="profileImg" class="form-control"/>
                    </div>
                </div>
                <!-- // 프로필 사진 -->

                <!-- 이메일 수신여부 -->
                <div class="form-group">
                    <label for="emailCheck" class="col-md-2 control-label">이메일 수신여부</label>
                    <div class="col-md-10">
                        <label class="radio-inline">
                            <input type="radio" name="emailCheck" id="emailCheck1" value="agree" checked />
                            수신 동의
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="emailCheck" id="emailCheck2" value="disagree" />
                            수신 거부
                        </label>
                    </div>
                </div>
                <!-- // 이메일 수신여부 -->

                <!-- SMS 수신여부 -->
                <div class="form-group">
                    <label for="smsCheck" class="col-md-2 control-label">SMS 수신여부</label>
                    <div class="col-md-10">
                        <label class="radio-inline">
                            <input type="radio" name="smsCheck" id="smsCheck1" value="agree" checked />
                            수신 동의
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="smsCheck" id="smsCheck2" value="disagree" />
                            수신 거부
                        </label>
                    </div>
                </div>
                <!-- // SMS 수신여부 -->

                <!-- 버튼 영역 -->
                <div class="form-group margin_top50">
                    <div class="text-center">
                        <button type="button" id="backBtn" class="btn btn_color4 btn-lg" title="이전단계">이전단계</button>
                        <button type="submit" class="btn btn_color1 btn-lg margin_left10" title="가입완료">가입완료</button>
                    </div>
                </div>
                <!-- // 버튼 영역 -->
            </fieldset>
        </form>
        <!-- // 회원가입 폼 -->
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
    <!-- <script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script> -->
    <script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/regex.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/member_common.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/member_join_form.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/searchbar.js"></script><!-- searchbar를 사용하는 경우 장착 -->
</body>
</html>
