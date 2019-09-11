<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <%@ include file="/WEB-INF/inc/Head.jsp"%>
    <!-- mainpage.css || subpage.css 중 1개는 꼭 참조 해야 함. -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/mainpage.css" />
    <!-- 개인 css 링크 참조 영역 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/rest-area-3d.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/aos/aos.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/owl.carousel.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/owl.theme.default.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/searchbar.css"><!-- searchbar를 사용하는 경우 장착 -->
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
        <!-- 양 옆에 왼쪽이나 오른쪽에 여행 뱃지 같은 그림 삽입하면 안 심심할 듯 -->
        <div class="cities clearfix">
            <div class="container">
                <div class="row">
                    <div class="col">
                        <div class="section_title">Find properties in these cities</div>
                        <div class="section_subtitle">Search your dream home</div>
                    </div>
                </div>
            </div>
            <div class="cities_container">
                <c:forEach var="item" items="${tourItem}">
	                <!-- City -->
	                <div class="city pull-left" data-aos="fade-up">
	                    <img src="${item.firstimage}" width="257.25" height="171.61">
	                    <div class="city_overlay">
	                        <a href="${pageContext.request.contextPath}/tour/TourList3.do?tourId=${item.tourId}&contentid=${item.contentid}" class="tour-item-contents">
	                            <c:choose>
	                            	<c:when test="${fn:length(item.title) > 10}">
	                            		<div class="city_title" style="word-break: keep-all; padding-top: 40px;">${item.title}</div>
	                            	</c:when>
	                            	<c:otherwise>
	                            		<div class="city_title">${item.title}</div>
	                            	</c:otherwise>
	                            </c:choose>
	                            <div class="city_subtitle">${item.addr1}</div>
	                        </a>
	                    </div>
	                </div>
                </c:forEach>
            </div>
        </div>
        <!-- 관광 끝 -->

		<div class="servicearea_curtain"></div>
		<div class="servicearea_bg"></div>
        <div class="slider-box" data-aos="fade-zoom-in" data-aos-easing="ease-in-back">
            <div class="rest-text" data-aos="fade-right" data-aos-easing="ease-in-sine" data-aos-offset="300" data-aos-duration="800">
            	<%-- 대표음식 값에 따른 UI 출력 --%>
			        <div class="foodinfo">
			            <div class="image">
			                <img src="${serviceareaItem.imageUrl}" alt="대표음식" width="230" height="200" />
			            </div>
			            <div class="text-area">
			            	<div class="text">
				                ${serviceareaItem.foodBatchMenu}<br />
				                ${serviceareaItem.foodSalePrice}
			                </div>
			            </div>
			        </div>
			        <!-- // 대표음식 -->

            	<%-- 주유소 유무에 따른 UI 출력 --%>
				<c:if test="${serviceareaItem.oilCompany != null || serviceareaItem.csStat != null}">
			        <!-- 주유소 -->
			        <table class="table table-bordered table-oil">
			            <tbody>
			                <tr>
			                    <th>정유사</th>
							    <c:choose>
							        <c:when test="${serviceareaItem.oilCompany != null}">
							            <c:if test="${serviceareaItem.oilCompany == 'AD'}">
							                    <td>알뜰주유소</td>
							            </c:if>
							            <c:if test="${serviceareaItem.oilCompany == 'SK'}">
							                    <td>SK주유소</td>
							            </c:if>
							            <c:if test="${serviceareaItem.oilCompany == 'GS'}">
							                    <td>GS칼텍스</td>
							            </c:if>
							            <c:if test="${serviceareaItem.oilCompany == 'S'}">
							                    <td>S-Oil</td>
							            </c:if>
							            <c:if test="${serviceareaItem.oilCompany == 'HD'}">
							                    <td>현대오일뱅크</td>
							            </c:if>
							        </c:when>
							        <c:otherwise>
							                    <td><img src="${pageContext.request.contextPath}/images/servicearea/x_mark.png" alt="X" width="15" /></td>
							        </c:otherwise>
							    </c:choose>
							                </tr>
							                <tr>
							                    <th>휘발유</th>
							    <c:choose>
							        <c:when test="${serviceareaItem.oilGasolinePrice != null && serviceareaItem.oilGasolinePrice != 'X'}">
							                    <td>${serviceareaItem.oilGasolinePrice}</td>
							        </c:when>
							        <c:otherwise>
							                    <td><img src="${pageContext.request.contextPath}/images/servicearea/x_mark.png" alt="X" width="15" /></td>
							        </c:otherwise>
							    </c:choose>
							    			</tr>
							    			<tr>
							                    <th>경유</th>
							    <c:choose>
							        <c:when test="${serviceareaItem.oilDiselPrice != null && serviceareaItem.oilDiselPrice != 'X'}">
							                    <td>${serviceareaItem.oilDiselPrice}</td>
							        </c:when>
							        <c:otherwise>
							                    <td><img src="${pageContext.request.contextPath}/images/servicearea/x_mark.png" alt="X" width="15" /></td>
							        </c:otherwise>
							    </c:choose>
							                </tr>
							                <tr>
							                    <th>LPG</th>
							    <c:choose>
							        <c:when test="${serviceareaItem.oilLpgYn != null && serviceareaItem.oilLpgYn != 'N' && serviceareaItem.oilLpgPrice != null && serviceareaItem.oilLpgPrice != 'X'}">
							                    <td>${serviceareaItem.oilLpgPrice}</td>
							        </c:when>
							        <c:otherwise>
							                    <td><img src="${pageContext.request.contextPath}/images/servicearea/x_mark.png" alt="X" width="15" /></td>
							        </c:otherwise>
							    </c:choose>
							    			</tr>
							    			<tr>
							                    <th>전기차</th>
							    <c:choose>
							        <c:when test="${serviceareaItem.csStat == 1}">
							                    <td>상태이상</td>
							        </c:when>
							        <c:when test="${serviceareaItem.csStat == 4}">
							                    <td>운영중지</td>
							        </c:when>
							        <c:when test="${serviceareaItem.csStat == 5}">
							                    <td>점검중</td>
							        </c:when>
							        <c:when test="${serviceareaItem.csStat == 2 || serviceareaItem.csStat == 3}">
							                    <td><img src="${pageContext.request.contextPath}/images/servicearea/oil/thunder.png" alt="충전가능" width="27" /></td>
							        </c:when>
							        <c:otherwise>
							                    <td><img src="${pageContext.request.contextPath}/images/servicearea/x_mark.png" alt="X" width="15" /></td>
							        </c:otherwise>
							    </c:choose>
			                </tr>
			            </tbody>
					</table>
			        <!-- // 주유소 -->
				</c:if>
            </div>
            <div class="slider" id="slider">
                <div class="slider__content" id="slider-content">
                    <div class="slider__images">
                        <div class="slider__images-item slider__images-item--active" data-id="1">
                        	<img class="blind" src="images/main/bg.jpg" />
                        	<div id="map" style="width: 100%; height: 32.5vw;"></div>
                        </div>
                    </div>
                    <div class="slider__text">
                        <div class="slider__text-item slider__text-item--active" data-id="1">
                            <div class="slider__text-item-head">
                                <h3>오늘의 휴게소</h3>
                            </div>
                            <div class="slider__text-item-info">
                                <p>${serviceareaItem.serviceareaName} - ${serviceareaItem.foodBatchMenu}이(가) 정말 맛있는 휴게소 !!!</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="rest-detail">
                <div class="container">
                	<div class="c-pic"></div>
                    <div class="owl-carousel restarea-slider" id="restarea-slider">
                    	<c:forEach var="item" items="${serviceareaGroupList}">
	                        <div class="item restarea-slider-item">
	                        	<span class="food_image"><a href="${pageContext.request.contextPath}/servicearea/servicearea_view_item.do?servicearea_groupId=${item.servicearea_groupId}"><img src="${item.imageUrl}" /></a></span>
	                        	<span class="food_name badge bg_color4">${item.foodBatchMenu}</span>
	                        	<span class="food_price badge bg_color2">${item.foodSalePrice}</span>
	                        </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>
        <!-- 휴게소 끝 -->

        <section class="gallery-area">
            <div class="container">
                <div class="air-bg">
			        <div class="reserve-cell">
			            <div id="reserve">
			                <div id="reserve-front"></div>
			                <div id="reserve-back">
		                		<img src="${pageContext.request.contextPath}/images/common/logo.png" />
		                		<p>
		                			완벽한 여행에서 저렴하고<br />정확한 티켓을 구해보세요 . !
		                		</p>
		                    	<a href="${pageContext.request.contextPath}/AirSearch.do">항공권 검색</a>
			                </div>
			            </div>
			        </div>
                </div>
                <div class="row">
                    <div class="col-lg-8 h-267" data-aos="fade-up" data-aos-easing="ease-in-sine">
                        <div class="single-gallery">
                            <div class="content">
                                <a href="${pageContext.request.contextPath}/AirSearch.do?tab=oneway&sdate=${date}&arrivedKor=${airHotList[0].arrivalCity}">
                                    <div class="content-overlay"></div>
                                    <img class="content-image img-fluid" src="${pageContext.request.contextPath}${airHotList[0].imageUrl}">
                                    <div class="content-details fadeIn-bottom">
                                        <h4 class="content-title mx-auto">${airHotList[0].content}</h4>
                                        <a href="${pageContext.request.contextPath}/AirSearch.do?tab=oneway&sdate=${date}&arrivedKor=${airHotList[0].arrivalCity}" class="primary-btn text-uppercase mt-20">${airHotList[0].arrivalCity} - 비행기 표 알아보기</a>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4" data-aos="fade-up" data-aos-easing="ease-in-sine">
                        <div class="single-gallery">
                            <div class="content">
                                <a href="${pageContext.request.contextPath}/AirSearch.do?tab=oneway&sdate=${date}&arrivedKor=${airHotList[1].arrivalCity}">
                                    <div class="content-overlay"></div>
                                    <img class="content-image img-fluid" src="${pageContext.request.contextPath}${airHotList[1].imageUrl}" height="228">
                                    <div class="content-details fadeIn-bottom">
                                        <h4 class="content-title mx-auto">${airHotList[1].content}</h4>
                                        <a href="${pageContext.request.contextPath}/AirSearch.do?tab=oneway&sdate=${date}&arrivedKor=${airHotList[1].arrivalCity}" class="primary-btn text-uppercase mt-20">${airHotList[1].arrivalCity} - 비행기 표 알아보기</a>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-4" data-aos="fade-up" data-aos-easing="ease-in-sine">
                        <div class="single-gallery">
                            <div class="content">
                                <a href="${pageContext.request.contextPath}/AirSearch.do?tab=oneway&sdate=${date}&arrivedKor=${airHotList[2].arrivalCity}">
                                    <div class="content-overlay"></div>
                                    <img class="content-image img-fluid" src="${pageContext.request.contextPath}${airHotList[2].imageUrl}" height="228">
                                    <div class="content-details fadeIn-bottom">
                                        <h4 class="content-title mx-auto">${airHotList[2].content}</h4>
                                        <a href="${pageContext.request.contextPath}/AirSearch.do?tab=oneway&sdate=${date}&arrivedKor=${airHotList[2].arrivalCity}" class="primary-btn text-uppercase mt-20">${airHotList[2].arrivalCity} - 비행기 표 알아보기</a>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-8 h-267" data-aos="fade-up" data-aos-easing="ease-in-sine">
                        <div class="single-gallery">
                            <div class="content">
                                <a href="${pageContext.request.contextPath}/AirSearch.do?tab=oneway&sdate=${date}&arrivedKor=${airHotList[3].arrivalCity}">
                                    <div class="content-overlay"></div>
                                    <img class="content-image img-fluid" src="${pageContext.request.contextPath}${airHotList[3].imageUrl}" >
                                    <div class="content-details fadeIn-bottom">
                                        <h4 class="content-title mx-auto">${airHotList[3].content}</h4>
                                        <a href="${pageContext.request.contextPath}/AirSearch.do?tab=oneway&sdate=${date}&arrivedKor=${airHotList[3].arrivalCity}" class="primary-btn text-uppercase mt-20">${airHotList[3].arrivalCity} - 비행기 표 알아보기</a>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

    <!-- 항공 끝 -->
    
    <div class="culture-layout">
    	<div class="c-pic"></div>
        <div class="cont-box"></div>
        <div class="cont-box-rotate"></div>
        <div class="container">
            <div class="owl-carousel culture-slider" id="culture-slider">
            <c:forEach var="item" items="${cultureItem}">
                <div class="item culture-slider-item"><a href="${pageContext.request.contextPath}/culture/culturePerformanceView.do?seq=${item.seq}"><img src="${item.thumbnail}"></a></div>
            </c:forEach>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="culture-info clearfix">
            <div class="row culture-items clearfix" data-aos="fade-right" data-aos-duration="500">
                <div class="col-md-4 pull-left">
                	<a href="${pageContext.request.contextPath}/culture/culturePerformanceView.do?seq=${randomCultureItem.seq}">
                    	<img src="${randomCultureItem.thumbnail}" width="313" height="370">
                    </a>
                </div>
                <div class="col-md-8 subtitle-box">
                    <div class="section_title">
                        <h4 class="page-header">공연/전시</h4>
                    </div>
                    <div class="section_subtitle">
	                    <table class="table">
	                    	<tbody>
		                    	<tr class="text-center">
		                    		<th class="text-center" style="border-right: 1px solid#d5d5d5">공연명</th>
		                    		<td>${randomCultureItem.title}</td>
		                    	</tr>
		                    	<tr class="text-center">
		                    		<th class="text-center" style="border-right: 1px solid#d5d5d5">시작일</th>
		                    		<td>${randomCultureItem.startDate}</td>
		                    	</tr>
		                    	<tr class="text-center">
		                    		<th class="text-center" style="border-right: 1px solid#d5d5d5">종료일</th>
		                    		<td>${randomCultureItem.endDate}</td>
		                    	</tr>
		                    	<tr class="text-center" style="border-bottom: 1px solid #d5d5d5">
		                    		<th class="text-center" style="border-right: 1px solid#d5d5d5">분류명</th>
		                    		<td>${randomCultureItem.realmName}</td>
		                    	</tr>
	                    	</tbody>
	                    </table>
                    </div>
                </div>
            </div>
            <hr>
            <div class="row culture-items clearfix" data-aos="fade-left" data-aos-duration="500">
                <div class="col-md-8 col-md-push-1 subtitle-box mgl-36">
                    <div class="section_title">
                        <h4 class="page-header">행사/축제</h4>
                    </div>
                    <div class="section_subtitle">
                        <table class="table">
	                    	<tbody>
		                    	<tr class="text-center">
		                    		<th class="text-center" style="border-right: 1px solid#d5d5d5">행사명</th>
		                    		<td>${randomFestivalItem.title}</td>
		                    	</tr>
		                    	<tr class="text-center">
		                    		<th class="text-center" style="border-right: 1px solid#d5d5d5">시작일</th>
		                    		<td>${randomFestivalItem.eventStartDate}</td>
		                    	</tr>
		                    	<tr class="text-center">
		                    		<th class="text-center" style="border-right: 1px solid#d5d5d5">종료일</th>
		                    		<td>${randomFestivalItem.eventEndDate}</td>
		                    	</tr>
	                    	</tbody>
	                    </table>
                    </div>
                </div>
                <div class="col-md-4 pull-right">
                	<a href="${pageContext.request.contextPath}/culture/cultureFestivalView.do?contentId=${randomFestivalItem.contentId}">
                    	<img src="${randomFestivalItem.firstImage}" width="313" height="370">
                    </a>
                </div>
            </div>
        </div>
    </div>
    <!-- 문화 끝 -->
    <!-- <div class="simsim" style="width: 100%; height: 100px;">
        심심하면 여기에 뭔가 추가
    </div> -->
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

<!-- 이젠 팝업 공지 -->
<div id='ezen-notice-popup' style='position: absolute; left: 50px; top: 150px; padding: 30px 50px; background-color: rgb(43,43,43); color: #fff; font-size: 16px; width: 400px; z-index: 9999999;'>
    <h1 style='border-bottom: 1px solid #eee; font-size: 24px'>학생 포트폴리오 입니다.</h1>
    <p>이 사이트는 "<a href="http://it.ezenac.co.kr/" target="_blank" style='color: #fff; display: inline'>이젠IT아카데미</a>"에서 진행된 "사물인터넷(IoT) 시스템 개발자" 수료생들의 취업을 위한 포트폴리오 사이트 입니다.</p>
    <p>실제 운영을 위한 사이트가 아님에 착오 없으시기 바랍니다.</p>
    <p style='text-align: right'>
        <a href='#' style='color: white' onclick="$('#ezen-notice-popup').hide()">[닫기]</a>
    </p>
</div>

<%@ include file="/WEB-INF/inc/AllmenuModal.jsp" %>
<%@ include file="/WEB-INF/inc/CommonScript.jsp" %>
    <!-- 개인 js 참조 영역 -->
    <script>
	    var lng = "${serviceareaItem.placeX}";
	    var lat = "${serviceareaItem.placeY}";
	    var serviceareaName = "${serviceareaItem.serviceareaName}";
	    var placeId = "${serviceareaItem.placeId}";
    </script>
    <script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=a44d9158e7468f7aff1f8ed8fb064834"></script>
    <script src="${pageContext.request.contextPath}/assets/js/rest-area-3d.js"></script>
    <script src="${pageContext.request.contextPath}/plugins/aos/aos.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/owl.carousel.min.js"></script>
    <script src="${pageContext.request.contextPath}/plugins/flip/jquery.flip.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/owl.carousel.min.js"></script>
    <script src="${pageContext.request.contextPath}/plugins/superslides/jquery.easing.1.3.js"></script>
    <script src="${pageContext.request.contextPath}/plugins/superslides/jquery.superslides.min.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/mainPage.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/regex.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/searchbar.js"></script><!-- searchbar를 사용하는 경우 장착 -->
</body>
</html>
