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
    <h3 class="color3">회원정보 수정</h3>
</article>
<!-- //page header -->

<!-- 서브 컨텐츠 -->
<article class="col-md-10 col-md-offset-1">
    <!-- 내용 삽입 -->
    <div class="container-fluid member_join_form">
        <div class="text-center">
            <p class="title margin_top20">정보입력</p>
            <p class="txt1 margin_top20">회원님의 개인정보는 동의 없이 공개되지 않으며, 개인정보 처리방침 가이드에 맞춰 보호 받고 있습니다.</p>
        </div>

        <!-- 회원정보 수정 폼 -->
        <form action="${pageContext.request.contextPath}/member/member_edit_ok.do" method="POST" class="form-horizontal margin_top40" id="member_edit_form" enctype="multipart/form-data">
            <fieldset>
                <legend class="blind">회원정보 수정</legend>

                <!-- 상태유지를 위한 hidden 영역 -->
                <input type="hidden" name="email_check_ok" id="email_check_ok" value="" /><!-- 이메일 중복 검사 확인 결과를 담기 -->
                <input type="hidden" name="cert_ok" id="cert_ok" value="" /><!-- 이메일인증하기 Boolean 저장 -->

                <div class="form-group">
                    <p class="col-md-10 text-right"><span class="identify">*</span> 표시는 필수 입력입니다.</p>
                </div>

                <!-- 이름 -->
                <div class="form-group">
                    <label for="userName" class="col-md-2 control-label">이름 <span class="identify">*</span></label>
                    <div class="col-md-10 form-inline">
                        <!-- 회원가입 시 입력한 정보 중 몇가지는 변경할 수 없도록 출력만 한다. -->
                        <p class="form-control-static">${loginInfo.userName}</p>
                    </div>
                </div>
                <!-- // 이름 -->

                <!-- 성별 -->
                <div class="form-group">
                    <label for="gender" class="col-md-2 control-label">성별 <span class="identify">*</span></label>
                    <div class="col-md-10 form-inline">
                        <p class="form-control-static">
<%--
    회원가입 시 입력한 정보 중 몇가지는 변경할 수 없도록 출력만 한다.
    - 남자인지 여자인지 gender 값에 따른 분기 출력
--%>
<c:choose>
    <c:when test="${loginInfo.gender == 'M'}">
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
                        <!-- 회원가입 시 입력한 정보 중 몇가지는 변경할 수 없도록 출력만 한다. -->
                        <p class="form-control-static">${tempBirthDate}</p>
                    </div>
                </div>
                <!-- // 생년월일 -->

                <!-- 아이디 -->
                <div class="form-group">
                    <label for="userId" class="col-md-2 control-label">아이디 <span class="identify">*</span></label>
                    <div class="col-md-10 form-inline">
                        <p class="form-control-static">${loginInfo.userId}</p>
                    </div>
                </div>
                <!-- // 아이디 -->

                <!-- 변경할 비밀번호 -->
                <div class="form-group">
                    <label for="userPw" class="col-md-2 control-label">변경할 비밀번호</label>
                    <div class="col-md-10 form-inline">
                        <input type="password" name="userPw" id="userPw" class="form-control" placeholder="변경할 경우만 입력하세요" />
                        <p class="color9 margin_top5">적어도 1개 이상의 숫자와 1개 이상의 특수문자가 포함되며 6자리 이상의 형식이어야 합니다.</p>
                    </div>
                </div>
                <!-- // 변경할 비밀번호 -->

                <!-- 변경할 비밀번호 확인 -->
                <div class="form-group">
                    <label for="userPwCheck" class="col-md-2 control-label">변경할 비밀번호 확인</label>
                    <div class="col-md-10 form-inline">
                        <input type="password" name="userPwCheck" id="userPwCheck" class="form-control" />
                    </div>
                </div>
                <!-- // 변경할 비밀번호 확인 -->

                <!-- 휴대전화 -->
                <div class="form-group">
                    <label for="phone" class="col-md-2 control-label">휴대전화 <span class="identify">*</span></label>
                    <div class="col-md-10 form-inline">
                        <input type="tel" name="phone" id="phone" class="form-control" placeholder="'-' 없이 입력해 주세요." value="${loginInfo.phone}" />
                    </div>
                </div>
                <!-- // 휴대전화 -->

                <!-- 이메일 -->
                <div class="form-group">
                    <label for="email" class="col-md-2 control-label">이메일 <span class="identify">*</span></label>
                    <div class="col-md-10 form-inline">
                        <!-- 이메일은 변경 가능하나 중복되는 이메일이 기존에 있는지 검사한다. -->
                        <input type="email" name="email" id="email" class="form-control" value="${loginInfo.email}" />
                        <!-- 이메일의 내용을 바꿀 시 동적으로 보여질 버튼 -->
                        <button type="button" name="email_check" id="email_check" class="btn btn_color3 blind" title="중복확인">중복확인</button>
                    </div>
                </div>
                <!-- // 이메일 -->

                <!-- 이메일을 변경하고자 할 때 인증번호 받는 입력칸이 생긴다. -->
                <div id="cert_num_div" class="form-group"></div>

                <!-- 우편번호 -->
                <div class="form-group">
                    <label for="postcode" class="col-md-2 control-label">주소 <span class="identify">*</span></label>
                    <div class="col-md-10 form-inline">
                        <input type="text" name="postcode" id="postcode" class="form-control" placeholder="우편번호" value="${loginInfo.postcode}" />
                        <input type="button" id="addrSearch" data-postcode="#postcode" data-addr1="#addr1" data-addr2="#addr2" class="btn btn_color3 form-control" value="우편번호검색" />
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-8 col-md-offset-2">
                        <input type="text" name="addr1" id="addr1" class="form-control" placeholder="주소" value="${loginInfo.address1}" />
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-md-8 col-md-offset-2">
                        <input type="text" name="addr2" id="addr2" class="form-control" placeholder="상세주소" value="${loginInfo.address2}" />
                    </div>
                </div>
                <!-- // 우편번호 -->

                <!-- 프로필 사진 -->
                <div class="form-group">
                    <label for="profileImg" class="col-md-2 control-label">프로필 사진</label>
                    <div class="col-md-10 form-inline">
                        <input type="file" name="profileImg" id="profileImg" class="form-control"/>
                        <p class="color9 margin_top5">기존 프로필 이미지를 삭제하셔야 새로운 사진 등록이 가능합니다.</p>
                    </div>
                </div>
                <!-- // 프로필 사진 -->
<%--
    ===== 프로필 사진의 처리 =====
    - <input type="file"> 요소는 value값을 미리 지정핼 줄 수 없다.
        (웹 브라우저의 보안정책 때문)
    - <c:if> : 기존에 업로드 된 사진의 정보를 별도로 표시하기 위해
        등록된 사진의 정보가 존재할 경우 if문을 준비한다.
--%>
<c:if test="${cookie.profileThumbnail != null}">
                <div class="form-group">
                    <!-- 등록된 프로필 이미지 표시하기 -->
                    <div class="col-md-10 col-md-offset-2 form-inline">
                        <!--
                            ===== 첨부파일 이미지의 변경 처리 =====
                            - 첨부파일의 경우 기존의 파일
                                삭제, 교체, 추가의 처리가 가능하다.
                            - 파일을 추가하는 경우 기존의 파일은
                                반드시 삭제되어야 하므로,
                                체크박스를 통해 삭제를 선택한 경우만
                                파일을 등록할 수 있도록
                                자바스크립트를 구성한다.
                        -->
                        <img src="${pageContext.request.contextPath}/download.do?file=${loginInfo.profileImg}&size=100x100&crop=true" class="profile img-rounded" alt="프로필 사진" />
                        <label class="checkbox-inline">
                            <input type="checkbox" name="img_del" id="img_del" value="Y" />
                            이미지 삭제
                        </label>
                        <!--
                            End
                            ### 기존의 이미지 삭제를 체크한 경우만
                                업로드 필드가 활성화 된다.
                        -->
                    </div>
                </div>
                <!--
                    ### 현재 등록된 이미지가 존재한다면 이미지를 표시하고,
                        삭제 여부를 선택할 수 있도록
                        체크박스를 표시한다.
                -->
</c:if>
<%-- // 프로필 사진의 처리 --%>

                <!-- 이메일 수신여부 -->
                <div class="form-group">
                    <label for="emailCheck" class="col-md-2 control-label">이메일 수신여부</label>
                    <div class="col-md-10">
                        <label class="radio-inline">
                            <input type="radio" name="emailCheck" id="emailCheck1" value="agree"
<c:if test="${loginInfo.toEmailCheckedDate != null}">checked </c:if>
                            />
                            수신 동의
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="emailCheck" id="emailCheck2" value="disagree"
<c:if test="${loginInfo.toEmailCheckedDate == null}">checked </c:if>
                            />
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
                            <input type="radio" name="smsCheck" id="smsCheck1" value="agree" checked
<c:if test="${loginInfo.toSmsCheckedDate != null}">checked </c:if>
                            />
                            수신 동의
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="smsCheck" id="smsCheck2" value="disagree"
<c:if test="${loginInfo.toSmsCheckedDate == null}">checked </c:if>
                            />
                            수신 거부
                        </label>
                    </div>
                </div>
                <!-- // SMS 수신여부 -->

                <!-- 버튼 영역 -->
                <div class="form-group margin_top50">
                    <div class="text-center">
                        <button type="button" id="backBtn" class="btn btn_color4 btn-lg margin_left10" title="이전단계">이전단계</button>
                        <button type="submit" class="btn btn_color1 btn-lg margin_left10" title="가입완료">수정완료</button>
                    </div>
                </div>
                <!-- // 버튼 영역 -->
            </fieldset>
        </form>
        <!-- // 회원정보 수정 폼 -->

        <!-- 회원탈퇴 버튼 영역 -->
        <hr class="margin_top50" />
        <div class="text-center">
            <h6>회원 탈퇴를 원하실 경우 아래의 버튼을 눌러주세요.</h6>
            <a href="${pageContext.request.contextPath}/mypage/mypage_delete_member_reason.do" class="btn btn_color3 btn-sm radius3 margin_top10" title="회원탈퇴">회원탈퇴</a>
        </div>
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

    <!-- 동적으로 생성될 인증번호 입력 받을 템플릿 -->
    <script type="text/x-handlebars-template" id="tmpl_cert_num">
        {{!-- 이메일 인증번호 --}}
        <label for="certNum" class="col-md-2 control-label">이메일 인증번호</label>
        <div class="col-md-10 form-inline">
            <input type="text" name="certNum" id="certNum" class="form-control" placeholder="인증번호를 입력해 주세요." />
            <button type="button" class="btn btn_color3" id="certOkBtn">인증하기</button>
        </div>
        {{!-- // 이메일 인증번호 --}}
    </script>

<%@ include file="/WEB-INF/inc/AllmenuModal.jsp" %>
<%@ include file="/WEB-INF/inc/CommonScript.jsp" %>
    <!-- 개인 js 참조 영역 -->
<c:if test="${cookie.profileThumbnail != null}">
    <script src="${pageContext.request.contextPath}/assets/js/member_edit_form_profileImg.js"></script>
</c:if>
    <script>
        // 이메일 입력칸이 변경 될 때 비교할 로그인 유저의 이메일 값을 Javascript 변수에 담아 놓는다.
        var userEmail = "${loginInfo.email}";
    </script>
    <script src="${pageContext.request.contextPath}/plugins/handlebars/handlebars-v4.1.0.js"></script>
    <!-- <script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script> -->
    <script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/regex.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/member_common.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/member_edit_form.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/searchbar.js"></script><!-- searchbar를 사용하는 경우 장착 -->
</body>
</html>
