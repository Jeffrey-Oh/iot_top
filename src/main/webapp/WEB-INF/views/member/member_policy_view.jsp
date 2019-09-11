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
                    <h3 class="color3">정책안내</h3>

                </article>
                <!-- //page header -->

<div class="container-fluid member_join_agreement margin_top50">
    <div role="tabpanel">
        <div class="clearfix">
            <label>
                <select name="regDate" id="regDate" class="form-control">
<c:forEach var="policyItem" items="${policyList}" varStatus="status">
                    <option value="${policyItem.policyId}">${policyItem.regDate}
    <c:if test="${status.index == 0}">
                        (현재)
    </c:if>
                    </option>
</c:forEach>
                </select>
            </label>

<%-- 관리자 계정만 볼 수 있는 버튼 모음 --%>
<c:if test="${loginInfo.grade == 'Master'}">
            <div class="policy_master pull-right">
                <a href="${pageContext.request.contextPath}/member/member_policy_insert.do" class="btn btn_color1 radius3 margin_left10 policy_master_btn">정책안내 추가</a>

                <a href="${pageContext.request.contextPath}/member/member_policy_update.do" class="btn btn_color2 radius3 margin_left10 policy_master_btn" id="policy_update_btn">정책안내 수정</a>

                <a href="${pageContext.request.contextPath}/member/member_policy_delete.do" class="btn btn_color3 radius3 margin_left10 policy_master_btn" id="policy_delete_btn">정책안내 삭제</a>
            </div>
</c:if>
<%-- // 관리자 계정만 볼 수 있는 버튼 모음 --%>
        </div>

        <!-- Nav tabs -->
        <ul class="nav nav-pills nav-justified margin_top5" role="tablist" id="loadActive_ul">
            <li role="presentation" class="active" id="loadActive">
                <a href="#agreementDoc" aria-controls="agreementDoc" role="tab" data-toggle="tab">이용약관</a>
            </li>
            <li role="presentation">
                <a href="#infoCollectionDoc" aria-controls="infoCollectionDoc" role="tab" data-toggle="tab">개인정보처리방침</a>
            </li>
            <li role="presentation">
                <a href="#communityDoc" aria-controls="communityDoc" role="tab" data-toggle="tab">게시글관리규정</a>
            </li>
            <li role="presentation">
                <a href="#emailCollectionDoc" aria-controls="emailCollectionDoc" role="tab" data-toggle="tab">이메일무단수집거부</a>
            </li>
        </ul>

        <!-- Tab panes -->
        <div class="tab-content margin_top30">
            <!-- handlebar로 동적으로 만들어 낼 영역-->
        </div>
    </div>
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

<!-- 정책안내를 동적으로 출력하기 위한 handlebar -->
<script type="text/x-handlebars-template" id="tmpl_policy_item">
    <div role="tabpanel" class="tab-pane active" id="agreementDoc">
        <textarea name="serviceText" class="form-control serviceText" rows="50" readonly>
            {{{agreementDoc}}}
        </textarea>
    </div>
    <div role="tabpanel" class="tab-pane" id="infoCollectionDoc">
        <textarea name="serviceText" class="form-control serviceText" rows="50" readonly>
            {{{infoCollectionDoc}}}
        </textarea>
    </div>
    <div role="tabpanel" class="tab-pane" id="communityDoc">
        <textarea name="serviceText" class="form-control serviceText" rows="50" readonly>
            {{{communityDoc}}}
        </textarea>
    </div>
    <div role="tabpanel" class="tab-pane" id="emailCollectionDoc">
        <div class="email_policy text-center">
            <img src="${pageContext.request.contextPath}/images/member/message.png" alt="" width="50" /><br /><br /><br />

            <p class="serviceText">
                {{{emailCollectionDoc}}}
            </p>
        </div>
    </div>
</script>

<%@ include file="/WEB-INF/inc/AllmenuModal.jsp" %>
<%@ include file="/WEB-INF/inc/CommonScript.jsp" %>
    <!-- 개인 js 참조 영역 -->
    <!-- handlebar plugin -->
    <script src="${pageContext.request.contextPath}/assets/js/regex.js"></script>
    <script src="${pageContext.request.contextPath}/plugins/handlebars/handlebars-v4.1.0.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/member_policy_view.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/searchbar.js"></script><!-- searchbar를 사용하는 경우 장착 -->
</body>
</html>
