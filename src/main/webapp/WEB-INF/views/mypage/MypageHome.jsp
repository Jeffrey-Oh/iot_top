<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>

<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/inc/Head.jsp"%>
<!-- mainpage.css || subpage.css 중 1개는 꼭 참조 해야 함. -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/css/subpage.css" />
<!-- 개인 css 링크 참조 영역 -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/css/mypage-home.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/plugins/aos/aos.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/owl.carousel.min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/owl.theme.default.min.css">


<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/tooltip-classic.css" />

<!-- // 개인 css 링크 참조 영역 -->
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

				<!-- 여기에 contents 삽입 시작 -->
				<div class="page-header">
					<h1>마이페이지</h1>
				</div>

				<div class="container">
					<div class="info clearfix">
						<ul class="list-unstyled user_simple_info">
							<li class="pull-left">
								<h1>
									<%-- 프로필 사진 존재유무에 따른 프로필 이미지 표시 --%>
									<c:choose>
										<c:when test="${cookie.profileThumbnail != null}">
											<img
												src="${pageContext.request.contextPath}/download.do?file=${loginInfo.profileImg}&size=120x180"
												class="profile img-rounded" />
										</c:when>
										<c:otherwise>
											<img
												src="${pageContext.request.contextPath}/images/mypage/no_image.jpg"
												width="120" height="180" />
										</c:otherwise>
									</c:choose>
									<%-- // 프로필 사진 존재유무에 따른 프로필 이미지 표시 끝 --%>
									${loginInfo.userName}님 환영합니다
								</h1>
							</li>

							<li class="pull-right btn_update">경험치 : ${cookie.grade_exp.value} 
								<span class="tooltip tooltip-effect-5">
									<span class="tooltip-item">
										<span class="glyphicon glyphicon-question-sign tooltip-item">
										</span>
									</span>
									<span class="tooltip-content clearfix">
									<span class="tooltip-text">
										<span class="lv lv1"></span>&nbsp; level 1: 토트백(경험치 500미만)<br />
										<span class="lv lv2"></span>&nbsp; level 2: 쇼퍼백(경험치 500~999)<br />
										<span class="lv lv3"></span>&nbsp; level 3: 소형백팩(경험치 1000~3999)<br />
										<span class="lv lv4"></span>&nbsp; level 4: 비지니스백(경험치 4000~7999)<br />
										<span class="lv lv5"></span>&nbsp; level 5: 대형배낭(경험치 8000~19999)<br />
										<span class="lv lv6"></span>&nbsp; level 6: 캐리어(경험치 20000~)										
									</span>
									</span>
								</span>
(현재 : <span class="lv ${loginInfo.grade}">${loginInfo.grade}</span>)&nbsp;&nbsp;&nbsp;이메일:
								${loginInfo.email} <a
								href="${pageContext.request.contextPath}/mypage/member_edit_door.do"
								class="btn btn_color2 radius3" style="margin-left: 10px;">정보수정</a>
							</li>
						</ul>
					</div>
				</div>
				<div class="container box">
					<h1 class="jjim_header">
						찜리스트						
						<a href="${pageContext.request.contextPath}/mypage/mypage_favorite.do?type=1" class="glyphicon glyphicon-plus pull-right more"><span>더보기</span></a>
					</h1>
					<div class="culture-layout">

						<div class="owl-carousel culture-slider" id="culture-slider">
						<c:choose>
							<c:when test="${fn:length(getFavoriteAll) == 0}">
									<table class="table">
										<tr>
											<td></td>
											<td class="noResult"> 조회된 결과가 없습니다. </td>
											<td></td>
											<td></td>
										</tr>
									</table>
							</c:when>
							
							
							<c:otherwise>
									<c:forEach var="k" items="${getFavoriteAll}" varStatus="s">
										<c:choose>
											<c:when test="${k.thumbnail != null}">										
			                					<div class="item culture-slider-item">
			                						<a href="${pageContext.request.contextPath}/culture/culturePerformanceView.do?seq=${k.seq}">
			                							<img class="slideImg" src="${k.thumbnail}">
			                						</a>
			                					</div>            								
											</c:when>
											<c:when test="${k.imageUrl != null || k.imageUrl}">
												<div class="item culture-slider-item">
													<a href="${pageContext.request.contextPath}/servicearea/servicearea_view_item.do?servicearea_groupId=${k.servicearea_groupId}">
														<img class="slideImg" src="${k.imageUrl}">
													</a>
												</div>
											</c:when>
											<c:when test="${k.firstImage != null || k.firstImage}">
												<div class="item culture-slider-item">
													<a href="${pageContext.request.contextPath}/culture/cultureFestivalView.do?contentId=${k.contentId}">
														<img class="slideImg" src="${k.firstImage}">
													</a>
												</div>
											</c:when>
											<c:when test="${k.boardId != null}">
												<div class="item culture-slider-item">
													<a href="${pageContext.request.contextPath}/CommView.do?boardId=${k.boardId}&category=${k.category}">
														<img class="slideImg" src="${pageContext.request.contextPath}/images/mypage/no_image.jpg" />
													</a>	
												</div>
											</c:when>
											<c:otherwise>
												<div class="item culture-slider-item">
													<img class="slideImg" src="${pageContext.request.contextPath}/images/mypage/no_image.jpg" />
												</div>
											</c:otherwise>										
										</c:choose>
									</c:forEach>
							</c:otherwise>
						</c:choose>													
						</div>

					</div>

				</div>

				<div class="container myQna">
					<div class="col-md-6 col-md-push-6 box">
						<h1>
							문의내역
							<a href="${pageContext.request.contextPath}/mypage/mypage_favorite.do?type=3" class="glyphicon glyphicon-plus pull-right more"><span>더보기</span></a>
						</h1>
							<table class="table" id="myQna">
								<thead>
									<tr>										
										<th class="boardId">문의 번호</th>
										<th class="boardTitle">제목</th>										
										<th class="boardRegDate">작성일</th>																			
									</tr>
								</thead>
								
							<c:choose>
								<c:when test="${fn:length(getMyQnaList) == 0}">
									
										<tr>
											<td></td>
											<td class="noResult"> 조회된 결과가 없습니다. </td>
											<td></td>
										</tr>
									
								</c:when>
								<c:otherwise>
									<c:forEach var="k" items="${getMyQnaList}" varStatus="s">
										<tr>										
											<td class="boardId BBSNum">${k.boardId}</td>
											<td class="boardTitle">
												<a href="${pageContext.request.contextPath}/CommView.do?boardId=${k.boardId}&category=${k.category}" 
													target="_blank">
													<span class="BBSTitle">${k.title}</span>
												</a>
											</td>																				
											<td class="boardRegDate BBSRegDate">${k.regDate}</td>																			
										</tr>								
									</c:forEach>
								</c:otherwise>
							</c:choose>	
							</table>
						
					</div>
					<div class="col-md-6 col-md-pull-6 box">
						<div class="item myWriting">
							<h1>
								내 게시글
								<a href="${pageContext.request.contextPath}/mypage/mypage_favorite.do?type=2" class="glyphicon glyphicon-plus pull-right more"><span>더보기</span></a>							
							</h1>
							<table class="table" id="myBoard">
								<thead>
									<tr>										
										<th class="boardId">글 번호</th>
										<th class="boardTitle">제목</th>										
										<th class="boardRegDate">작성일</th>
										<th class="boardCount">조회 수</th>										
									</tr>
								</thead>
						<c:choose>
							<c:when test="${fn:length(getMyBoardList) == 0}">
									
										<tr>
											<td></td>
											<td class="noResult"> 조회된 결과가 없습니다. </td>
											<td></td>
											<td></td>
										</tr>
									
							</c:when>
						
							
							<c:otherwise>	
								<c:forEach var="k" items="${getMyBoardList}" varStatus="s">
									<tr>										
										<td class="boardId BBSNum">${k.boardId}</td>
										<td class="boardTitle">
											<a href="${pageContext.request.contextPath}/CommView.do?boardId=${k.boardId}&category=${k.category}" 
												target="_blank">
												<span class="BBSTitle">${k.title}</span>
											</a>
										</td>																				
										<td class="boardRegDate BBSRegDate">${k.regDate}</td>
										<td class="boardCount BBSCount">${k.boardCount}</td>									
									</tr>								
								</c:forEach>
							</c:otherwise>
						</c:choose>
							</table>
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
		<%@ include file="/WEB-INF/inc/Footer.jsp"%>
	</div>
	<!-- // 전체를 감싸는 div -->
	<%@ include file="/WEB-INF/inc/AllmenuModal.jsp"%>
	<%@ include file="/WEB-INF/inc/CommonScript.jsp"%>
	<!-- 개인 js 참조 영역 -->
	 <script src="${pageContext.request.contextPath}/assets/js/owl.carousel.min.js"></script>


	<script>
	
$(function() {
    
	
	
    $("#culture-slider").owlCarousel({
        items:4,
        loop:true,
        margin:10,
        autoplay:true,
        autoplayHoverPause:true,
        autoplayTimeout: 4000,
        smartSpeed: 450,
        dots: true
    });
    
    
    $('[data-toggle="popover"]').hover().click(function(e) {
        e.preventDefault();
        
    });

    

    
    
});

</script>
</body>
</html>