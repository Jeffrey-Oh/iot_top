<%@page import="org.json.JSONArray"%>
<%@page import="org.json.JSONObject"%>
<%@page import="project.spring.helper.FileHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/inc/Head.jsp"%>
<!-- 개인 css 링크 참조 영역 -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/css/mypage-favorite.css" />
<!-- mainpage.css || subpage.css 중 1개는 꼭 참조 해야 함. -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/css/subpage.css" />
<!-- 개인 css 링크 참조 영역 -->
</head>
<body>
<!-- 전체를 감싸는 div -->
<div class="container-fluid">
<%@ include file="/WEB-INF/inc/Gnb.jsp"%>
<%@ include file="/WEB-INF/inc/Visual.jsp"%>


<section id="contents">
    <h2 class="blind">본문</h2>
    <div class="container">
<!-- 여기에 contents 삽입 시작 -->
<div class="container-fluid">
	<!-- Nav tabs -->
	<ul class="nav nav-tabs nav-stacked nav-pills pull-left">
		<li class="active" id="favorite-tab">
			<a href="#home" class="tab-btn-item-link" data-toggle="tab" id="mypage1">찜<br />리<br />스<br />트
		</a>
		</li>
		<li id="favorite-tab2">
			<a href="#tab1" class="tab-btn-item-link" data-toggle="tab" id="mypage2">내<br />게<br />시<br />글
		</a>
		</li>
		<li id="favorite-tab3">
			<a href="#tab2" class="tab-btn-item-link" data-toggle="tab" id="mypage3">문<br />의<br />내<br />역
		</a>
		</li>
	</ul>

	<!-- Tab panes -->
	<div class="content-box tab-content">
		<div class="content-header tab-pane active" id="home">
			<!-- 찜리스트 영역  -->
			<h1>찜리스트</h1>
			<form action="${pageContext.request.contextPath}/mypage/mypage_favorite_delete_ok_via_mypage.do" method="post" id="favorite_form">
				<button type="submit" class="pull-right btn btn-primary func" id="deleteAll">일괄삭제</button>
			
				<button class="pull-right btn btn-primary func" id="checkAll">전체선택</button>
			
			<table class="table table-hover" id="favorite-items">
				<thead>
					<tr>
						
						<th style="width: 150px;">카테고리</th>
						<th style="min-width: 340px;">제목</th>
						<th>등록일</th>
						<th>선택</th>
					</tr>
				</thead>
				
				<c:choose>
				<c:when test="${fn:length(getFavoriteAll) == 0}">
								
								<tr>
									<td></td>
									<td class="noResult"> 조회된 결과가 없습니다. </td>
									<td></td>
									<td></td>
								</tr>
				</c:when>
				
				
				<c:otherwise>
				<c:forEach var="k" items="${getFavoriteAll}" varStatus="s">
					<tr>
						<c:if test="${k.boardId != null}">
							<td>커뮤니티</td>
						</c:if>
						<c:if test="${k.servicearea_groupId != null}">
							<td>휴게소</td>
						</c:if>
						<c:if test="${k.cultureId != null}">
							<td>문화</td>
						</c:if>
						<c:if test="${k.tourId != null}">
							<td>관광</td>
						</c:if>								
					
						<c:if test="${k.boardId != null}">
							<td><a href="${k.link}" target="_blank" class="favoriteTitle">${k.title}</a></td>
						</c:if>
						<c:if test="${k.servicearea_groupId != null}">
							<td><a href="${k.link}" target="_blank" class="favoriteTitle">${k.serviceareaName}</a></td>
						</c:if>
						<c:if test="${k.ctitle != null}">
							<td><a href="${k.link}" target="_blank" class="favoriteTitle">${k.ctitle}</a></td>
						</c:if>
						<c:if test="${k.cftitle != null}">
							<td><a href="${k.link}" target="_blank" class="favoriteTitle">${k.cftitle}</a></td>
						</c:if>
													
						
						<td>
							${k.regDate}
						</td>
						<td>
							&nbsp;&nbsp;<input type="checkbox" name="checkItem" id="checkItem${s.index}" class="checkItemFav" value="${k.favoriteId}">
						</td>
					</tr>
				
				</c:forEach>
				</c:otherwise>
			</c:choose>
			</table>
			</form>				
		</div>
		
		<c:if test="${type == 1}">
			<!-- 페이징 시작 -->
			<div class="list-number">
				<nav class="text-center">
					<ul class="list-unstyled pagination">
						<c:choose>
							<c:when test="${pageHelper.prevPage > 0}">
								<c:url var="firstUrl" value="/mypage/mypage_favorite.do">
								  <c:param name="page" value="1" />									
								<c:param name="type" value="${type}" />	
								</c:url>
								<c:url var="prevUrl" value="/mypage/mypage_favorite.do">
									<c:param name="page" value="${pageHelper.prevPage}" />
									<c:param name="type" value="${type}" />								  	
								</c:url>
								<li><a href="${firstUrl}">&laquo;</a></li>
								<li><a href="${prevUrl}">&lt;</a></li>
							</c:when>
	
							<c:otherwise>
								<li class="disabled"><a href="#">&laquo;</a></li>
								<li class="disabled"><a href="#">&lt;</a></li>
							</c:otherwise>
						</c:choose>
	
						<c:forEach var="i" begin="${pageHelper.startPage}" end="${pageHelper.endPage}">
							<c:url var="pageUrl" value="/mypage/mypage_favorite.do">
								<c:param name="page" value="${i}" />
								<c:param name="type" value="${type}" />
							</c:url>
	
							<c:choose>
								<c:when test="${pageHelper.page == i }">
									<li class="active"><a href="#">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="${pageUrl}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
	
						<c:choose>
							<c:when test="${pageHelper.nextPage > 0}">
								<c:url var="lastUrl" value="/mypage/mypage_favorite.do">
									<c:param name="page" value="${pageHelper.totalPage}" />
									<c:param name="type" value="${type}" />
									
								</c:url>
								<c:url var="nextUrl" value="/mypage/mypage_favorite.do">
									<c:param name="page" value="${pageHelper.nextPage}" />								
									<c:param name="type" value="${type}" />								
								</c:url>
								<li><a href="${nextUrl}">&gt;</a></li>
								<li><a href="${lastUrl}">&raquo;</a></li>
							</c:when>
	
							<c:otherwise>
								<li class="disabled"><a href="#">&gt;</a></li>
								<li class="disabled"><a href="#">&raquo;</a></li>
							</c:otherwise>
						</c:choose>
					</ul>
				</nav>
			</div>
			<!--  페이징 끝 -->
		</c:if>
		<div class="content-header head2 tab-pane" id="tab1">
			<h1 class="myWriting">내 게시글</h1>
			<%-- <form action="${pageContext.request.contextPath}/mypage//mypage/mypage_favorite_delete_ok_via_mypage.do" method="post" id="favorite_form">
				<button class="pull-right btn btn-primary func" id="deleteAll2">일괄삭제</button>
				<button class="pull-right btn btn-primary func" id="checkAll2">전체선택</button> --%>
				
			
			<table class="table table-hover writing-items">
				<thead>
					<tr>
						
						<th style="width: 150px;">카테고리</th>
						<th style="min-width: 340px;">제목</th>
						<th>등록일</th>
						<th>조회 수</th>
						<th>좋아요</th>
					</tr>
				</thead>
				<c:choose>
				<c:when test="${fn:length(getMyBoardList) == 0}">
								
								<tr>
									<td></td>
									<td class="noResult"> 조회된 결과가 없습니다. </td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
				</c:when>
				<c:otherwise>
				<c:forEach var="k" items="${getMyBoardList}" varStatus="s">
					<tr>
						
							<td>제목</td>
							<td>
								<a href="${pageContext.request.contextPath}/CommView.do?boardId=${k.boardId}&category=${k.category}">
									<span>${k.title}</span>							
								</a>
							</td>
										
						
						<td>
							${k.regDate}
						</td>
						<td>
							${k.boardCount}
						</td>
						<td>
							${k.boardLike}
						</td>
					</tr>
				
				</c:forEach>
				</c:otherwise>
				</c:choose>	
			</table>
			
					
			<!-- </form> -->			
		</div>
		<c:if test="${type == 2 }">
		<!-- 페이징 시작 -->
		
			<div class="list-number">
				<nav class="text-center">
					<ul class="list-unstyled pagination">
						<c:choose>
							<c:when test="${pageHelper.prevPage > 0}">
								<c:url var="firstUrl" value="/mypage/mypage_favorite.do">
								  <c:param name="page" value="1" />									
								<c:param name="type" value="${type}" />	
								</c:url>
								<c:url var="prevUrl" value="/mypage/mypage_favorite.do">
									<c:param name="page" value="${pageHelper.prevPage}" />
									<c:param name="type" value="${type}" />								  	
								</c:url>
								<li><a href="${firstUrl}">&laquo;</a></li>
								<li><a href="${prevUrl}">&lt;</a></li>
							</c:when>
	
							<c:otherwise>
								<li class="disabled"><a href="#">&laquo;</a></li>
								<li class="disabled"><a href="#">&lt;</a></li>
							</c:otherwise>
						</c:choose>
	
						<c:forEach var="i" begin="${pageHelper.startPage}" end="${pageHelper.endPage}">
							<c:url var="pageUrl" value="/mypage/mypage_favorite.do">
								<c:param name="page" value="${i}" />
								<c:param name="type" value="${type}" />
							</c:url>
	
							<c:choose>
								<c:when test="${pageHelper.page == i }">
									<li class="active"><a href="#">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="${pageUrl}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
	
						<c:choose>
							<c:when test="${pageHelper.nextPage > 0}">
								<c:url var="lastUrl" value="/mypage/mypage_favorite.do">
									<c:param name="page" value="${pageHelper.totalPage}" />
									<c:param name="type" value="${type}" />
									
								</c:url>
								<c:url var="nextUrl" value="/mypage/mypage_favorite.do">
									<c:param name="page" value="${pageHelper.nextPage}" />								
									<c:param name="type" value="${type}" />								
								</c:url>
								<li><a href="${nextUrl}">&gt;</a></li>
								<li><a href="${lastUrl}">&raquo;</a></li>
							</c:when>
	
							<c:otherwise>
								<li class="disabled"><a href="#">&gt;</a></li>
								<li class="disabled"><a href="#">&raquo;</a></li>
							</c:otherwise>
						</c:choose>
					</ul>
				</nav>
			</div>
		</c:if>
			<!--  페이징 끝 -->
		
		<div class="content-header head3 tab-pane" id="tab2">
			<h1 class="myWriting">문의내역</h1>
			<!-- <button class="pull-right btn btn-primary func" id="deleteAll3">일괄삭제</button>
			<button class="pull-right btn btn-primary func" id="checkAll3">전체선택</button> -->
			
			<table class="table table-hover Qna-items">
				<thead>
					<tr>
						
						<th style="width: 150px;">카테고리</th>
						<th style="min-width: 340px;">제목</th>
						<th>등록일</th>
						<th>조회 수</th>						
					</tr>
				</thead>
				<c:choose>
				<c:when test="${fn:length(getMyQnaList) == 0}">
								
								<tr>
									<td></td>
									<td class="noResult"> 조회된 결과가 없습니다. </td>
									<td></td>
									<td></td>
								</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="k" items="${getMyQnaList}" varStatus="s">
					
						
					
					
					
					<tr>
						
							<td>제목</td>
							<td>
								<a href="${pageContext.request.contextPath}/CommView.do?boardId=${k.boardId}&category=${k.category}">
									<span>${k.title}</span>							
								</a>
							</td>
										
						
						<td>
							${k.regDate}
						</td>
						<td>
							${k.boardCount}
						</td>						
					</tr>
					</c:forEach>
				</c:otherwise>
						
				
				</c:choose>	
			</table>
				
		</div>
		
		<c:if test="${type == 3}">
			<!-- 페이징 시작 -->
			<div class="list-number">
				<nav class="text-center">
					<ul class="list-unstyled pagination">
						<c:choose>
							<c:when test="${pageHelper.prevPage > 0}">
								<c:url var="firstUrl" value="/mypage/mypage_favorite.do">
								  <c:param name="page" value="1" />									
								<c:param name="type" value="${type}" />	
								</c:url>
								<c:url var="prevUrl" value="/mypage/mypage_favorite.do">
									<c:param name="page" value="${pageHelper.prevPage}" />
									<c:param name="type" value="${type}" />								  	
								</c:url>
								<li><a href="${firstUrl}">&laquo;</a></li>
								<li><a href="${prevUrl}">&lt;</a></li>
							</c:when>
	
							<c:otherwise>
								<li class="disabled"><a href="#">&laquo;</a></li>
								<li class="disabled"><a href="#">&lt;</a></li>
							</c:otherwise>
						</c:choose>
	
						<c:forEach var="i" begin="${pageHelper.startPage}" end="${pageHelper.endPage}">
							<c:url var="pageUrl" value="/mypage/mypage_favorite.do">
								<c:param name="page" value="${i}" />
								<c:param name="type" value="${type}" />
							</c:url>
	
							<c:choose>
								<c:when test="${pageHelper.page == i }">
									<li class="active"><a href="#">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li><a href="${pageUrl}">${i}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
	
						<c:choose>
							<c:when test="${pageHelper.nextPage > 0}">
								<c:url var="lastUrl1" value="/mypage/mypage_favorite.do">
									<c:param name="page" value="${pageHelper.totalPage}" />
									<c:param name="type" value="${type}" />
									
								</c:url>
								<c:url var="nextUrl" value="/mypage/mypage_favorite.do">
									<c:param name="page" value="${pageHelper.nextPage}" />								
									<c:param name="type" value="${type}" />								
								</c:url>
								<li><a href="${nextUrl}">&gt;</a></li>
								<li><a href="${lastUrl}">&raquo;</a></li>
							</c:when>
	
							<c:otherwise>
								<li class="disabled"><a href="#">&gt;</a></li>
								<li class="disabled"><a href="#">&raquo;</a></li>
							</c:otherwise>
						</c:choose>
					</ul>
				</nav>
			</div>
			<!--  페이징 끝 -->
		</c:if>
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

<%@ include file="/WEB-INF/inc/Footer.jsp"%>
</div>
<!-- // 전체를 감싸는 div -->
<!-- 개인 js 참조 영역 -->
<script src="${pageContext.request.contextPath}/plugins/handlebars/handlebars-v4.1.0.js"></script>

<%@ include file="/WEB-INF/inc/AllmenuModal.jsp" %>
<%@ include file="/WEB-INF/inc/CommonScript.jsp" %>
<script>

var type = "${type}";
console.log(type);
$(function() {
    
	
    $("#mypage1").click(function(e) {
    	e.preventDefault();
    	location.href = ROOT_URL+"/mypage/mypage_favorite.do?type=1";
    	
    });
    
    $("#mypage2").click(function(e) {
    	e.preventDefault();
    	location.href = ROOT_URL+"/mypage/mypage_favorite.do?type=2";
    	
    });
    
    $("#mypage3").click(function(e) {
    	e.preventDefault();
    	location.href = ROOT_URL+"/mypage/mypage_favorite.do?type=3";
    	
    });
    
    // 선택 상자의 체크 클릭 이벤트 정의
    $("#checkAll").click(function(e) {
    	e.preventDefault();
    	if ($(".checkItemFav").prop("checked") == false) {
    		$(".checkItemFav").prop("checked", true);
    	} else {
    		$(".checkItemFav").prop("checked", false);
    	}
    	
    });
    
    $("#checkAll2").click(function(e) {
    	e.preventDefault();
    	if ($(".checkItemWrt").prop("checked") == false) {
    		$(".checkItemWrt").prop("checked", true);
    	} else {
    		$(".checkItemWrt").prop("checked", false);
    	}
    	
    });
    
    $("#checkAll3").click(function(e) {
    	e.preventDefault();
    	if ($(".checkItemQna").prop("checked") == false) {
    		$(".checkItemQna").prop("checked", true);
    	} else {
    		$(".checkItemQna").prop("checked", false);
    	}
    	
    });
    
    // 선택된 아이템을 삭제하는 클릭 이벤트
    $("#deleteAll").submit(function(e) {
    	e.preventDefault();
    	$(".checkItemFav:checked").parent().parent().remove();   	
    	$(this).unbind("submit").submit();   	
    });
    
    $("#deleteAll2").submit(function(e) {
    	e.preventDefault();
    	$(".checkItemWrt:checked").parent().parent().remove();   	
    	$(this).unbind("submit").submit();  
    });
    
    $("#deleteAll3").submit(function(e) {
    	e.preventDefault();
    	$(".checkItemQna:checked").parent().parent().remove();   	
    	$(this).unbind("submit").submit();  
    });
    
    
    if (type==2) {
    	console.log(type);
    	$("#tab1").addClass("active");
    	$("#home").removeClass("active");
    	$("#tab2").removeClass("active");
    	$("#favorite-tab2").addClass("active");
    	$("#favorite-tab").removeClass("active");
    	$("#favorite-tab3").removeClass("active");
    }
    
    if (type==3) {
    	console.log(type);
    	$("#tab2").addClass("active");
    	$("#home").removeClass("active");
    	$("#tab1").removeClass("active");
    	$("#favorite-tab3").addClass("active");
    	$("#favorite-tab").removeClass("active");
    	$("#favorite-tab2").removeClass("active");
    }
});
</script>
</body>
</html>