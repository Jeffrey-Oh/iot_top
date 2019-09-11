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
    <h3 class="color3">로그인</h3>
</article>
<!-- //page header -->


<div class="row clearfix">
    <!-- 서브 컨텐츠 -->
    <article class="col-md-10 pull-left">
        <!-- 내용 삽입 -->
        <div class="container-fluid member_login">
            <!-- 회원 로그인 폼 -->
            <form action="${pageContext.request.contextPath}/member/member_login_ok.do" method="POST" id="member_login" class="form-horizontal" role="form">
                <fieldset>
                    <legend class="blind">회원 로그인</legend>

                    <!--입력 요소 및 로그인 버튼 -->
                    <div class="row clearfix margin_top50">
                        <div class="col-md-6 col-md-offset-2 pull-left">
                            <!-- 아이디 -->
                            <div class="form-group">
                                <label for="userId" class="col-md-3 control-label">아이디</label>
                                <div class="col-md-9">
                                    <input type="text" name="userId" id="userId" class="form-control" value="${remember_userId}" placeholder="아이디를 입력해 주세요." />
                                </div>
                            </div>
                            <!-- // 아이디 -->

                            <!-- 비밀번호 -->
                            <div class="form-group">
                                <label for="userPw" class="col-md-3 control-label">비밀번호</label>
                                <div class="col-md-9">
                                    <input type="password" name="userPw" id="userPw" class="form-control" placeholder="비밀번호를 입력해 주세요." />
                                </div>
                            </div>
                            <!-- // 비밀번호 -->

                            <!-- 아이디 저장 -->

                            <div class="form-group">
                                <div class="checkbox col-md-9 col-md-offset-3">
                                    <label>
<c:choose>
    <c:when test="${remember_userId == '' || remember_userId == null}">
                                        <input type="checkbox" name="remember_userId" value="remember_userId" />
    </c:when>
    <c:otherwise>
                                        <input type="checkbox" name="remember_userId" value="remember_userId" checked />
    </c:otherwise>
</c:choose>
                                        아이디 저장
                                    </label>
                                </div>
                            </div>
                            <!-- // 아이디 저장 -->
                        </div>

                        <div class="col-md-2 pull-left">
                            <div class="form-group">
                                <button type="submit" class="btn btn_color1 btn-lg btn_login">로그인</button>
                            </div>
                        </div>
                    </div>
                    <!-- // 입력 요소 및 로그인 버튼 -->

                    <div class="form-group margin_top20">
                        <div class="text-center">
                            <a href="${pageContext.request.contextPath}/member/member_join.do" id="member_join_btn" class="btn btn_color2 radius3" title="회원가입">회원가입</a>
                            <a href="${pageContext.request.contextPath}/member/member_userId_search.do" id="userId_search_btn" class="btn btn_color3 radius3" title="아이디 찾기">아이디 찾기</a>
                            <a href="${pageContext.request.contextPath}/member/member_userPw_search.do" id="userPw_search_btn" class="btn btn_color3 radius3" title="패스워드 찾기">패스워드 찾기</a>
                        </div>
                    </div>
                </fieldset>
            </form>
            <!-- // 회원 로그인 폼 -->
        </div>
        <!-- // 내용 삽입 -->
    </article>
    <!-- // 서브 컨텐츠 -->

    <!-- aside -->
    <aside class="aside col-md-2 pull-right">
        <a href="#">
            <img src="${pageContext.request.contextPath}/images/common/banner.jpg" alt="배너광고" />
        </a>
    </aside>
    <!-- //aside -->
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
    <script src="${pageContext.request.contextPath}/assets/js/member_login.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/searchbar.js"></script><!-- searchbar를 사용하는 경우 장착 -->
</body>
</html>
