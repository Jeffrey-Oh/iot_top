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
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/mailForm.css" />
</head>
<body>
<!-- 전체를 감싸는 div -->
<div class="container-fluid">
<%@ include file="/WEB-INF/inc/Gnb.jsp"%>
<%@ include file="/WEB-INF/inc/Visual.jsp"%>
<!-- contents -->
<section id="contents">
    <h2 class="blind">본문</h2>
    <div class="container">
        <!-- 메인 컨텐츠 삽입 -->

<!-- 여기에 contents 삽입 시작 -->
<!-- ===== 개인 작업 영역 ===== -->

<div>
	<div id="test">
		<!-- 제목 -->
		<div class="page-header">
			<h1>문의 메일</h1>
		</div>
		<!--// 제목 -->
		
		<div class="test1">
			<!-- 메일 폼 영역 시작 -->
			<form class="form-horizontal" method="post" action="${pageContext.request.contextPath}/customer/sendmail.do">
				<input type="hidden" name="category" value="customer" />
	
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="sender">보내는주소</label>
	                <div class="col-sm-10">
	                    <input type="text" name="sender" id="sender" class="form-control" 
	                        placeholder="보내는 분의 이메일 주소를 입력하세요."/>
	                </div>
	            </div>
	            
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="receiver">받는주소</label>
	                <div class="col-sm-10">
	                    <input type="text" name="receiver" id="receiver" class="form-control" 
	                        placeholder="받는 분의 이메일 주소를 입력하세요."/>
	                </div>
	            </div>
	
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="subject">메일 제목</label>
	                <div class="col-sm-10">
	                    <input type="text" name="subject" id="subject" class="form-control" 
	                        placeholder="이메일의 제목을 입력하세요." />
	                </div>
	            </div>
	
	            <div class="form-group">
	                <label class="control-label col-sm-2" for="content">내용입력</label>
	                <div class="col-sm-10">
	                    <textarea name="content" id="content" class="ckeditor" ></textarea>
	                </div>
	            </div>
	                
	            <div class="col-sm-10 col-sm-offset-2 text-right">
	                <button type='submit' class="btn btn-primary">메일보내기</button>
	               	<button type="reset" class="btn btn-default">다시작성</button>
	               	<a href="${pageContext.request.contextPath}/customer/CustomerBoardList.do"><button type="button" class="btn btn-danger">목록</button></a>
	            </div>
			</form>
		</div>
	</div>
</div>
<!--// 메일 폼 영역 끝 -->

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
    <!-- CKEditor 참조 -->
    <script src="http://cdn.ckeditor.com/4.5.8/standard/ckeditor.js"></script>
</body>
</html>