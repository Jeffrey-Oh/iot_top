<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page trimDirectiveWhitespaces="true"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/inc/Head.jsp"%>
    <!-- mainpage.css || subpage.css 중 1개는 꼭 참조 해야 함. -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/css/subpage.css" />
	    <!-- 개인 css 링크 참조 영역 -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/searchbar.css"><!-- searchbar를 사용하는 경우 장착 -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/plugins/ajax/ajax_helper.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/plugins/multi-column/multi-columns-row.css" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/css/cultureHome.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/plugins/datepicker/datepicker.min.css" />
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css">
<title>culture</title>
</head>
<body>
<!-- 전체를 감싸는 div -->
<div class="container-fluid">
<%@ include file="/WEB-INF/inc/Gnb.jsp"%>
<%@ include file="/WEB-INF/inc/Visual.jsp"%>
<%@ include file="/WEB-INF/inc/Searchbar.jsp" %><!-- 빠른검색(css, js, regex.js 필요) -->
<!-- contents -->
<section id="contents">
    <h2 class="blind">본문</h2>
    <div class="container">
		<c:choose>
	        <c:when test="${loginInfo.grade == 'Master'}">
	        	<a href="${pageContext.request.contextPath}/culture/culturePerf_API_insert.do">데이터 새로고침</a>
	        </c:when>
        </c:choose>


<!-- 공연 영역 시작 -->
<div class="container">
	<div class="page-header">
		<!-- 제목 -->
		<div class="pull-left">
			<h1>공연·전시</h1>
		</div>

	</div>
	<div class="pull-right">
		<!-- 문화의 다른 탭으로 이동하는 버튼  -->
		<button class="btn btn-primary" type="button"
			onclick="location.href='cultureFestival.do'">행사, 축제</button>

	</div>
</div>

<nav class="navbar navbar-inverse">
	<div class="container-fluid">
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<!-- navbar-right를 추가할 경우 메뉴가 오른쪽에 배치 -->
		<form action="${pageContext.request.contextPath}/culture/culturePerformance.do" method="get">
			<ul class="nav navbar-nav">
				<!-- 특정 메뉴 강조 -->
				<li>
					<div id="datepicker">
						<label for="from Label">시작일</label>
						<input type="text" name="from"
							id="from" readonly>~<label for="to">종료일</label> <input
							type="text" name="to" id="to" readonly>
					</div>
				</li>

				<li class="clearfix">
					<label for="performArea" id="performAreaLabel" class="pull-left LabelA">지역</label>
					<select name="performArea" id="performArea" class="pull-left selectListA">
					    <option value="">전체</option>
					    <option value="서울">서울</option>
					    <option value="부산">부산</option>
					    <option value="대구">대구</option>
					    <option value="인천">인천</option>
					    <option value="광주">광주</option>
					    <option value="대전">대전</option>
					    <option value="울산">울산</option>
					    <option value="세종">세종</option>
					    <option value="경기">경기</option>
					    <option value="강원">강원</option>
					    <option value="충북">충북</option>
					    <option value="충남">충남</option>
					    <option value="전북">전북</option>
					    <option value="전남">전남</option>
					    <option value="경북">경북</option>
					    <option value="경남">경남</option>
					    <option value="제주">제주</option>
					</select>
				</li>
				
				<li class="clearfix">
					<label for="realmName" id="realmNameLabel" class="pull-left LabelA">장르</label>
					<select name="realmName" id="realmName" class="pull-left selectListA">
					    <option value="">전체</option>
					    <option value="국악">국악</option>
					    <option value="무용">무용</option>
					    <option value="미술">미술</option>
					    <option value="연극">연극</option>
					    <option value="음악">음악</option>
					    <option value="기타">기타</option>					    
					</select>
				</li>
			</ul>
			<div class="navbar-form navbar-right">
				<div class="form-group dropdown">
					<label for="condition" id="conditionLabel" class="pull-left LabelB">검색조건</label>
					<select name="condition" id="condition" class="pull-left selectListB">
					    <option value="title">제목</option>
					    <option value="content">내용</option>
					    <option value="titleContent">제목+내용</option>
					    <option value="place">장소</option>

					</select>
				</div>
				<div class="form-group">
					<input type="text" class="form-control" name="keyword" placeholder="검색어를 입력하세요">
				</div>
				<button type="submit" class="btn btn-default clearfix">
					<span class="glyphicon glyphicon-search"></span>
				</button>
			</div>

			</div>
			<!-- /.navbar-collapse -->
		</div>
	</form>
	<!-- /.container-fluid -->
</nav>

<div class="container">
	<div class="row multi-columns-row" id="culture-list">
		<c:forEach var="k" items="${culturePerfList}" varStatus="s">
		<div class="col-md-6 col-sm-6 col-xs-12">
               	<div class="thumbnail clearfix">
                   	<a href="${pageContext.request.contextPath}/culture/culturePerformanceView.do?seq=${k.seq}"><img src="${k.thumbnail}" class="img-responsive pull-left thumbnail-box"/></a>
                   	<div style="padding: 7px 10px 0px 10px">
                      	 <h3>${k.title}</h3>
                       	<ul class='list-unstyled perforDate'>
                       		<li>${k.startDate}<span>~</span>${k.endDate}<c:if test="${k.startDate > dateInt}"><span>(예정)</span></c:if></li>
                       		<li>${k.realmName}</li>
                           	<li>${k.area}|${k.place}</li>
                       	</ul>
                   	</div>
					<!-- <div class="btn-box pull-right">
						<button id="btn-favorite" class="btn btn-favorite">
       						<i class="fas fa-star"></i>
   						</button>
   						<button id="btn-like" class="btn btn-like">
       						<i class="far fa-heart color2"></i>
   						</button><small><i id="likeCnt" class="likeCnt">0</i></small>
					</div> -->
               	</div>
       </div>
       </c:forEach>
	</div>
</div>
	<!-- top button -->
        <button type="button" class="btn_top bg_color1 color8">
            <span class="glyphicon glyphicon-menu-up"></span>
        </button>
        <!-- //top button -->
    </div>
    <!-- 페이징 시작 -->
		<div class="list-number">
			<nav class="text-center">
				<ul class="list-unstyled pagination">
					<c:choose>
						<c:when test="${pageHelper.prevPage > 0}">
							<c:url var="firstUrl" value="/culture/culturePerformance.do">
							  <c:param name="page" value="1" />
								<c:if test="${from != null && from != ''}">
								<c:param name="startDate" value="${from}" />
							  	</c:if>
							  	<c:if test="${to != null && to != ''}">
									<c:param name="endDate" value="${to}" />
							  	</c:if>
							  	<c:if test="${area != null && area != ''}">
									<c:param name="performArea" value="${performArea}" />
							  	</c:if>

							  	<c:if test="${condition != null && condition != ''}">
									<c:param name="condition" value="${condition}" />
							  	</c:if>

							  	
							  	<c:if test="${realmName != null && realmName != ''}">	
									<c:param name="realmName" value="${realmName}" />						  
							  	</c:if>						  


							  	<c:if test="${keyword != null && keyword != ''}">
									<c:param name="keyword" value="${keyword}" />
							  	</c:if>

							</c:url>
							<c:url var="prevUrl" value="/culture/culturePerformance.do">
								<c:param name="page" value="${pageHelper.prevPage}" />
								<c:if test="${from != null && from != ''}">
								<c:param name="from" value="${from}" />
							  	</c:if>
							  	<c:if test="${to != null && to != ''}">
									<c:param name="to" value="${to}" />
							  	</c:if>
							  	<c:if test="${area != null && area != ''}">
									<c:param name="performArea" value="${performArea}" />
							  	</c:if>

							  	<c:if test="${condition != null && condition != ''}">
									<c:param name="condition" value="${condition}" />
							  	</c:if>

							  	<c:if test="${keyword != null && keyword != ''}">
									<c:param name="keyword" value="${keyword}" />
							  	</c:if>
							  	
							  	<c:if test="${realmName != null && realmName != ''}">	
									<c:param name="realmName" value="${realmName}" />						  
							  	</c:if>
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
						<c:url var="pageUrl" value="/culture/culturePerformance.do">
							<c:param name="page" value="${i}" />
							<c:if test="${from != null && from != ''}">
							<c:param name="from" value="${from}" />
						  	</c:if>
						  	<c:if test="${to != null && to != ''}">
								<c:param name="to" value="${to}" />
						  	</c:if>
						  	<c:if test="${area != null && area != ''}">
								<c:param name="performArea" value="${performArea}" />
						  	</c:if>

						  	<c:if test="${condition != null && condition != ''}">
								<c:param name="condition" value="${condition}" />
						  	</c:if>


						  	
						  	<c:if test="${keyword != null && keyword != ''}">	
								<c:param name="keyword" value="${keyword}" />						  
						  	</c:if> 
						  	
						  	<c:if test="${realmName != null && realmName != ''}">	
								<c:param name="realmName" value="${realmName}" />						  
							</c:if>

						</c:url>

						<c:choose>
							<c:when test="${pageHelper.page == i}">
								<li class="active"><a href="#">${i}</a></li>
							</c:when>
							<c:otherwise>
								<li><a href="${pageUrl}">${i}</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>

					<c:choose>
						<c:when test="${pageHelper.nextPage > 0}">
							<c:url var="lastUrl" value="/culture/culturePerformance.do">
								<c:param name="page" value="${pageHelper.totalPage}" />
								<c:if test="${from != null && from != ''}">
								<c:param name="from" value="${from}" />
							  	</c:if>
							  	<c:if test="${to != null && to != ''}">
									<c:param name="to" value="${to}" />
							  	</c:if>
							  	<c:if test="${area != null && area != ''}">
									<c:param name="performArea" value="${performArea}" />
							  	</c:if>

							  	<c:if test="${condition != null && condition != ''}">
									<c:param name="condition" value="${condition}" />
							  	</c:if>

							  	<c:if test="${keyword != null && keyword != ''}">
									<c:param name="keyword" value="${keyword}" />
							  	</c:if>
							  	<c:if test="${realmName != null && realmName != ''}">	
									<c:param name="realmName" value="${realmName}" />						  
							  	</c:if>
							</c:url>
							<c:url var="nextUrl" value="/culture/culturePerformance.do">
								<c:param name="page" value="${pageHelper.nextPage}" />
								<c:if test="${from != null && from != ''}">
								<c:param name="from" value="${from}" />
							  	</c:if>
							  	<c:if test="${to != null && to != ''}">
									<c:param name="to" value="${to}" />
							  	</c:if>
							  	<c:if test="${area != null && area != ''}">
									<c:param name="performArea" value="${performArea}" />
							  	</c:if>

							  	<c:if test="${condition != null && condition != ''}">
									<c:param name="condition" value="${condition}" />
							  	</c:if>

							  	<c:if test="${keyword != null && keyword != ''}">
									<c:param name="keyword" value="${keyword}" />
							  	</c:if>
							  	<c:if test="${realmName != null && realmName != ''}">	
									<c:param name="realmName" value="${realmName}" />						  
							  	</c:if>
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
</section>
<!-- //contents -->
<%@ include file="/WEB-INF/inc/Footer.jsp"%>
</div>
<!-- // 전체를 감싸는 div -->
<%@ include file="/WEB-INF/inc/AllmenuModal.jsp" %>
<%@ include file="/WEB-INF/inc/CommonScript.jsp" %>
<!-- 개인 js 참조 영역 -->
<script src="${pageContext.request.contextPath}/assets/js/regex.js"></script><!-- searchbar를 사용하는 경우 장착 -->
<script src="${pageContext.request.contextPath}/assets/js/searchbar.js"></script><!-- searchbar를 사용하는 경우 장착 -->
<script src="<%=request.getContextPath()%>/plugins/ajax/ajax_helper.js"></script>
<script src="<%=request.getContextPath()%>/plugins/handlebars/handlebars-v4.0.5.js"></script>
<script src="<%=request.getContextPath()%>/plugins/multi-column/ie-row-fix.js"></script>
<script src="<%=request.getContextPath()%>/plugins/datepicker/datepicker.min.js"></script>
<script src="<%=request.getContextPath()%>/plugins/datepicker/i18n/datepicker.ko-KR.js"></script>
<script src="<%=request.getContextPath()%>/plugins/datepicker/i18n/datepicker.ko-KR.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/culturePerform.js"></script>
<script>

    $(function() {




				$("#from").datepicker({
						autoHide : true,
						format : "yyyy-mm-dd",
						language : "ko-KR",
						weekStart : 0,

				});

				$("#from").on("change", function() {
					$("#to").remove();
					$("<input type='text' id='to' class='search_input search_input_3' name='to' placeholder='to' required='required' style='margin-left: 3px;' readonly>").insertAfter($("label[for='to']"));
					if ($("#to").val() == "" && $("#from").val() != "") {
						$("#to").datepicker({
							autoHide : true,
							format : "yyyy-mm-dd",
							language : "ko-KR",
							weekStart : 0,
							startDate : $("#from").val()
						});
					}
				});

				$(document).on("click", "#to", function() {
					if ($("#from").val() == "") {
						alert("알림", "시작일을 먼저 선택하세요.", function() {
							setTimeout(function() {
								$("#from").val("");
								$("#from").focus();
							}, 100);
						});
					}
				});
		/* }); */

        // 게시글 좋아요
        $(document).on("click", ".btn-like", function() {
            if ($(this).children(".far").length == 1) {
                // ajax 통신 해서 결과 값 + 1
                // 콜백함수 결과로 성공 시 이벤트로 아래 2줄을 함수안에 넣을 것

                $(this).children(".far").toggleClass("far fas");
                $(this).find(".fas").css({
                    "transform": "scale(1.2, 1.2)",
                    "transition": "all .2s ease-in-out"
                });
            } else if ($(this).children(".fas").length == 1) {
                // ajax 통신 해서 결과 값 - 1
                // // 콜백함수 결과로 성공 시 이벤트로 아래 2줄을 함수안에 넣을 것

                $(this).children(".fas").toggleClass("fas far");
                $(this).find(".far").css({
                    "transform": "scale(1.0, 1.0)",
                    "transition": "all .2s ease-in-out"
                });
            }
        });

        // 즐겨찾기
        $(document).on("click", ".btn-favorite", function() {
            if ($(this).css("color") == "rgb(51, 51, 51)") {
                // ajax 통신해서 DB 업데이트
                // 콜백함수 결과 성공 시 이벤트로 아래 1줄 함수안에 넣을 것
                $(this).css({
                    "color": "#FA0",
                    "transform": "scale(1.2, 1.2)",
                    "transition": "all .2s ease-in-out"
                });
            } else {
                // ajax 통신해서 DB 업데이트
                // 콜백함수 결과 성공 시 이벤트로 아래 1줄 함수안에 넣을 것
                $(this).css({
                    "color": "rgb(51, 51, 51)",
                    "transform": "scale(1.0, 1.0)",
                    "transition": "all .2s ease-in-out"
                });
            }
        });

    });



</script>
</body>
</html>
