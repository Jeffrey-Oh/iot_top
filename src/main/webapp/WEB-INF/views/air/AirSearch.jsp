<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/inc/Head.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/subpage.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/air-common.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/airsearch.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/datepicker/datepicker.min.css" />
</head>
<body>
<!-- 전체를 감싸는 div -->
<div class="container-fluid">
<%@ include file="/WEB-INF/inc/Gnb.jsp"%>
<!-- visual -->
<section id="visual">
	<article class="bg"></article>
	<div class="bg-text">Let us take you away</div>
</section>
<!-- //visual -->

<!-- search bar -->
<section id="searchbar" class="container radius5"
	style="background-color: rgba(0, 0, 0, 0);">
	<div class="home_search">
		<div class="container">
			<div class="row">
				<div class="col">
					<div class="home_search_container">
						<form action="AirSearch.do" class="home_search_form" id="home_search_form">
                            <div class="home_search_title">
                                <label for="round">왕복</label>
                                <label for="oneway">편도</label>
                                <c:choose>
	                                <c:when test="${tab == 'round'}">
		                                <input type="radio" name="tab" id="round" value="round" checked="checked">
		                                <input type="radio" name="tab" id="oneway" value="oneway">
	                                </c:when>
	                                <c:otherwise>
										<input type="radio" name="tab" id="round" value="round">
		                                <input type="radio" name="tab" id="oneway" value="oneway" checked="checked">
	                                </c:otherwise>
                                </c:choose>
                            </div>
                            <div class="home_search_content">
                                <div>
                                    <input type="text" id="boardingKor" name="boardingKor" class="search_input search_input_2" placeholder="출발지" value="${boardingKor}">
                                    <input type="text" id="arrivedKor" name="arrivedKor" class="search_input search_input_2" placeholder="도착지" value="${arrivedKor}">
                                    <input type="text" id="sdate" name="sdate" class="search_input search_input_2" readonly placeholder="가는 날" value="${sdate}"/>
                                    <input type="text" id="edate" name="edate" class="search_input search_input_2" readonly placeholder="오는 날" value="${edate}" />
                                    <button type="submit" class="home_search_button">search</button>
                                </div>
                            </div>
                        </form>
					</div>
				</div>
			</div>
		</div>
	</div>
</section>
<!-- //search bar -->

<!-- contents -->
<section id="contents">
    <h2 class="blind">본문</h2>
    <div class="container">
        <!-- 메인 컨텐츠 삽입 -->
<!-- content 감싸는 영역 -->
<div class="content-parent">

	<!-- Destinations -->
	<div class="container">
		<div class="airLS-contents">
			<div class="row">
				<div class="col text-center">
					<div class="section_subtitle color3">let's search for your flight</div>
					<div class="section_title color3">
						<h2>Air Search</h2>
					</div>
				</div>
			</div>
		</div>
	</div>
	<c:choose>
		<c:when test="${fn:length(slist) == 0}">
		<!-- 검색 전에 캐러셀 표시 -->
			<div class="tech-slideshow">
				<div class="mover-1"></div>
			</div>
		</c:when>

	 	<c:otherwise>
	 	<!-- 검색 후 목록 표시 -->
	      	<ul class="list-group text-center ticket-list">
		      	<c:choose>
		      		<c:when test="${tab.equals('round')}">
			      		<c:choose>
			      			<c:when test="${fn:length(slist) > fn:length(blist)}">
			      			<!-- 왕복 조회 이며 가는날상품이 오는날상품보다 많은 경우 -->
			      				<c:forEach var="i" items="${slist}" varStatus="s">
									<li class="list-group-item ticket-list-item">
								        <div class="row">
								            <div class="col-md-9">
								                <div class="col-md-3 logo-img">
								                    <span class="${i.logo}"></span>
								                    <c:choose>
								                    	<c:when test="${fn:length(blist) > s.index }">
									                    	<span class="${blist[s.index].logo}"></span>
								                    	</c:when>
								                    	<c:otherwise>
								                    		<span></span>
								                    	</c:otherwise>
								                    </c:choose>
								                </div>
								                <div class="col-md-9 ticket-text-box">
								                	<c:choose>
								                		<c:when test="${fn:length(blist) > s.index }">
								                			<div class="col-md-12">
										                        <div class="col-md-4 status-left">
										                            <span class="text-right">${i.domesticStartTime}</span>
										                            <span class="text-right">${i.startCityCode}</span>
										                        </div>
										                        <div class="col-md-4 status-center">
										                            <span class="text-center">0시간 55분</span>
										                            <span class="text-center clearfix">
										                                <hr class="pull-left">
										                                <i class="glyphicon glyphicon-plane"></i>
										                            </span>
										                            <span class="text-center via-text badge">직항</span>
										                        </div>
										                        <div class="col-md-4 status-right">
										                            <span class="text-left">${i.domesticArrivalTime}</span>
										                            <span class="text-left">${i.arrivalCityCode}</span>
										                        </div>
										                    </div>
								                			<div class="col-md-12">
										                        <div class="col-md-4 status-left">
										                            <span class="text-right">${blist[s.index].domesticStartTime}</span>
										                            <span class="text-right">${blist[s.index].startCityCode}</span>
										                        </div>
										                        <div class="col-md-4 status-center">
										                            <span class="text-center">0시간 55분</span>
										                            <span class="text-center clearfix">
										                                <hr class="pull-left">
										                                <i class="glyphicon glyphicon-plane"></i>
										                            </span>
										                            <span class="text-center via-text badge">직항</span>
										                        </div>
										                        <div class="col-md-4 status-right">
										                            <span class="text-left">${blist[s.index].domesticArrivalTime}</span>
										                            <span class="text-left">${blist[s.index].arrivalCityCode}</span>
										                        </div>
										                    </div>
									                   </c:when>
								                		<c:otherwise>
								                			<div class="col-md-12">
										                        <div class="col-md-4 status-left">
										                            <span class="text-right">${i.domesticStartTime}</span>
										                            <span class="text-right">${i.startCityCode}</span>
										                        </div>
										                        <div class="col-md-4 status-center">
										                            <span class="text-center">0시간 55분</span>
										                            <span class="text-center clearfix">
										                                <hr class="pull-left">
										                                <i class="glyphicon glyphicon-plane"></i>
										                            </span>
										                            <span class="text-center via-text badge">직항</span>
										                        </div>
										                        <div class="col-md-4 status-right">
										                            <span class="text-left">${i.domesticArrivalTime}</span>
										                            <span class="text-left">${i.arrivalCityCode}</span>
										                        </div>
										                    </div>
								                			<div class="col-md-12" style="line-height: 80px;">
										                        조회된 상품이 없습니다.
										                    </div>
								                		</c:otherwise>
								                	</c:choose>
								                </div>
								            </div>
								            <c:choose>
								            	<c:when test="${fn:length(blist) > s.index}">
								            		<div class="col-md-3">
										                <div class="col-md-11">
										                    <div class="ticket-info-box">
										                        <span>총 ${listTotalCount}건 중 최저가</span>
										                        <span>${i.price} 원</span>
										                        <a href="#" class="btn btn-lg bg_color2 btn-ticket-info">선택 <i class="glyphicon glyphicon-arrow-right"></i></a>
										                    </div>
										                </div>
										            </div>
								            	</c:when>
								            	<c:otherwise>
								            		<div class="col-md-3">
										                <div class="col-md-11">
										                    <div class="ticket-info-box">
										                    	<span>총 ${listTotalCount}건 중 최저가</span>
										                        <span>${i.price} 원</span>
										                        <a href="#" class="btn btn-lg bg_color2 btn-ticket-info">선택 <i class="glyphicon glyphicon-arrow-right"></i></a>
										                    </div>
										                </div>
										            </div>
								            	</c:otherwise>
								            </c:choose>
								        </div>
								    </li>
							    </c:forEach>
			      			</c:when>
			      			<c:otherwise>
			      			<!-- 왕복이며 오는날 상품이 가는날 상품보다 많은 경우 -->
			      				<c:forEach var="i" items="${slist}" varStatus="s">
									<li class="list-group-item ticket-list-item">
								        <div class="row">
								            <div class="col-md-9">
								                <div class="col-md-3 logo-img">
								                    <c:choose>
								                    	<c:when test="${fn:length(blist) > s.index }">
									                    	<span class="${blist[s.index].logo}"></span>
								                    	</c:when>
								                    	<c:otherwise>
								                    		<span></span>
								                    	</c:otherwise>
								                    </c:choose>
								                    <span class="${i.logo}"></span>
								                </div>
								                <div class="col-md-9 ticket-text-box">
								                	<c:choose>
								                		<c:when test="${fn:length(blist) > s.index }">
								                			<div class="col-md-12">
										                        <div class="col-md-4 status-left">
										                            <span class="text-right">${i.domesticStartTime}</span>
										                            <span class="text-right">${i.startCityCode}</span>
										                        </div>
										                        <div class="col-md-4 status-center">
										                            <span class="text-center">0시간 55분</span>
										                            <span class="text-center clearfix">
										                                <hr class="pull-left">
										                                <i class="glyphicon glyphicon-plane"></i>
										                            </span>
										                            <span class="text-center via-text badge">직항</span>
										                        </div>
										                        <div class="col-md-4 status-right">
										                            <span class="text-left">${i.domesticArrivalTime}</span>
										                            <span class="text-left">${i.arrivalCityCode}</span>
										                        </div>
										                    </div>
								                			<div class="col-md-12">
										                        <div class="col-md-4 status-left">
										                            <span class="text-right">${blist[s.index].domesticStartTime}</span>
										                            <span class="text-right">${blist[s.index].startCityCode}</span>
										                        </div>
										                        <div class="col-md-4 status-center">
										                            <span class="text-center">0시간 55분</span>
										                            <span class="text-center clearfix">
										                                <hr class="pull-left">
										                                <i class="glyphicon glyphicon-plane"></i>
										                            </span>
										                            <span class="text-center via-text badge">직항</span>
										                        </div>
										                        <div class="col-md-4 status-right">
										                            <span class="text-left">${blist[s.index].domesticArrivalTime}</span>
										                            <span class="text-left">${blist[s.index].arrivalCityCode}</span>
										                        </div>
										                    </div>
									                   </c:when>
								                		<c:otherwise>
								                			조회된 상품이 없습니다.
								                		</c:otherwise>
								                	</c:choose>
								                </div>
								            </div>
								            <c:choose>
								            	<c:when test="${fn:length(blist) > s.index}">
								            		<div class="col-md-3">
										                <div class="col-md-11">
										                    <div class="ticket-info-box">
										                        <span>총 ${listTotalCount}건 중 최저가</span>
										                        <span>${i.price}</span>
										                        <a href="#" class="btn btn-lg bg_color2 btn-ticket-info">선택 <i class="glyphicon glyphicon-arrow-right"></i></a>
										                    </div>
										                </div>
										            </div>
								            	</c:when>
								            	<c:otherwise>
								            		<div class="col-md-3">
										                <div class="col-md-11">
										                    <div class="ticket-info-box">
										                        <span>조회결과 없음</span>
										                    </div>
										                </div>
										            </div>
								            	</c:otherwise>
								            </c:choose>
								        </div>
								    </li>
							    </c:forEach>
		      				</c:otherwise>
		      			</c:choose>
		   			</c:when>
		   			<c:otherwise>
		   			<!-- 편도 조회 -->
	      				<c:forEach var="i" items="${slist}" varStatus="s">
							<li class="list-group-item ticket-list-item">
						        <div class="row">
						            <div class="col-md-9">
						                <div class="col-md-3 logo-img">
						                    <span class="${i.logo}" style="top: 58px !important;"></span>
						                </div>
						                <div class="col-md-9 ticket-text-box">
				                			<div class="col-md-12" style="top: 35px !important;">
						                        <div class="col-md-4 status-left">
						                            <span class="text-right">${i.domesticStartTime}</span>
						                            <span class="text-right">${i.startCityCode}</span>
						                        </div>
						                        <div class="col-md-4 status-center">
						                            <span class="text-center">0시간 55분</span>
						                            <span class="text-center clearfix">
						                                <hr class="pull-left">
						                                <i class="glyphicon glyphicon-plane"></i>
						                            </span>
						                            <span class="text-center via-text badge">직항</span>
						                        </div>
						                        <div class="col-md-4 status-right">
						                            <span class="text-left">${i.domesticArrivalTime}</span>
						                            <span class="text-left">${i.arrivalCityCode}</span>
						                        </div>
						                    </div>
						                </div>
						            </div>
				            		<div class="col-md-3">
						                <div class="col-md-11">
						                    <div class="ticket-info-box">
						                        <span>총 ${listTotalCount}건 중 최저가</span>
						                        <span>${i.price} 원</span>
						                        <a href="#" class="btn btn-lg bg_color2 btn-ticket-info">선택 <i class="glyphicon glyphicon-arrow-right"></i></a>
						                    </div>
						                </div>
						            </div>
						        </div>
						    </li>
					    </c:forEach>
		   			</c:otherwise>
				</c:choose>
	      	</ul>
	      	
	      	<!-- 페이징 시작 -->
			<div class="list-number">
				<nav class="text-center">
					<ul class="list-unstyled pagination">
						<c:choose>
							<c:when test="${pageHelper.prevPage > 0}">
								<c:url var="firstUrl" value="/AirSearch.do">
									<c:param name="tab" value="${tab}"/>
									<c:param name="sdate" value="${sdate}" />
									<c:param name="edate" value="${edate}" />
									<c:param name="boardingKor" value="${boardingKor}" />
									<c:param name="arrivedKor" value="${arrivedKor}" />
									<c:param name="list" value="1" />
								</c:url>
								<c:url var="prevUrl" value="/AirSearch.do">
									<c:param name="tab" value="${tab}"/>
									<c:param name="sdate" value="${sdate}" />
									<c:param name="edate" value="${edate}" />
									<c:param name="boardingKor" value="${boardingKor}" />
									<c:param name="arrivedKor" value="${arrivedKor}" />
									<c:param name="list" value="${pageHelper.prevPage}" />
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
							<c:url var="pageUrl" value="/AirSearch.do">
								<c:param name="tab" value="${tab}"/>
								<c:param name="sdate" value="${sdate}" />
								<c:param name="edate" value="${edate}" />
								<c:param name="boardingKor" value="${boardingKor}" />
								<c:param name="arrivedKor" value="${arrivedKor}" />
								<c:param name="list" value="${i}" />
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
								<c:url var="lastUrl" value="/AirSearch.do">
									<c:param name="tab" value="${tab}"/>
									<c:param name="sdate" value="${sdate}" />
									<c:param name="edate" value="${edate}" />
									<c:param name="boardingKor" value="${boardingKor}" />
									<c:param name="arrivedKor" value="${arrivedKor}" />
									<c:param name="list" value="${pageHelper.totalPage}" />
								</c:url>
								<c:url var="nextUrl" value="/AirSearch.do">
									<c:param name="tab" value="${tab}"/>
									<c:param name="sdate" value="${sdate}" />
									<c:param name="edate" value="${edate}" />
									<c:param name="boardingKor" value="${boardingKor}" />
									<c:param name="arrivedKor" value="${arrivedKor}" />
									<c:param name="list" value="${pageHelper.nextPage}" />
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
			<!-- 페이징 끝 -->
      	</c:otherwise>
    </c:choose>
</div>
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
<script src="${pageContext.request.contextPath}/plugins/OwlCarousel2-2.2.1/owl.carousel.js"></script>
<script src="${pageContext.request.contextPath}/plugins/datepicker/datepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/plugins/datepicker/i18n/datepicker.ko-KR.js"></script>
<script src="${pageContext.request.contextPath}/plugins/validate/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/air_search.js"></script>
<script>
	var tab = "${tab}";
</script>
</body>
</html>