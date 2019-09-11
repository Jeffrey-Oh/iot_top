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

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/plugins/ajax/ajax_helper.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/plugins/multi-column/multi-columns-row.css" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/assets/css/cultureView.css" />
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/plugins/datepicker/datepicker.min.css" />
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/searchbar.css"><!-- searchbar를 사용하는 경우 장착 -->
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
		


<!-- 행사 영역 시작 -->
<div class="container">
	<div class="page-header">
		<!-- 제목 -->
		<div class="pull-left">
			<h1>행사·축제</h1>
		</div>

	</div>
	<div class="pull-right">
		<!-- 문화의 다른 탭으로 이동하는 버튼  -->
		<button class="btn btn-primary" type="button"
			onclick="location.href='culturePerformance.do'">공연, 전시
		</button>
	<!-- 즐겨찾기, 좋아요 버튼  -->
	<c:choose>
		<c:when test="${favoriteTarget == false}">
		<button id="btn-favorite" class="btn btn-favorite">
				<i class="far fa-star"></i>
			</button>
		</c:when>
		<c:otherwise>
			<button id="btn-favorite" class="btn btn-favorite">
				<i class="fas fa-star" style="color: #FA0;"></i>
			</button>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${likeTarget == false}">
		    <button id="btn-like" class="btn btn-like btn-lg">
				<i class="far fa-heart color2"></i>
			</button>
		</c:when>
		<c:otherwise>
			<button id="btn-like" class="btn btn-like btn-lg">
				<i class="fas fa-heart color2"></i>
			</button>
		</c:otherwise>

	</c:choose>
		<small class="disinb"><i id="likeCnt" class="likeCnt">${perforItem.festLike}</i></small>		
	</div>
</div>


	<table class="tbl_info table">
                    <!-- 행사기간,행사시간,행사가격,행사장소,주최,관람시간,연령 정보 제공 -->
                    <colgroup>
                        <col style="width:84px">
                        <col style="width:41%">
                        <col style="width:58px">
                        <col>
                    </colgroup>
                    <tbody>
                    <tr>
                        <th scope="row">행사기간</th>
                        <td colspan="3">

                                 <c:choose>
                                  <c:when test="${perforItem.eventStartDate <= perforItem.eventEndDate}">
                                    ${perforItem.eventStartDate}~${perforItem.eventEndDate}
                                  </c:when>
                                  <c:otherwise>
                                  	담당자 문의 요망
                                  </c:otherwise>
								</c:choose>

                        </td>
                    </tr>
                    <tr>
                        <th scope="row">행사시간</th>
                        <td colspan="3">
                        	<c:if test="${perforItem.playTime != null}">
                        		${perforItem.playTime}
                        	</c:if>
                        </td>
                    </tr>
                    <tr>
                        <th scope="row">행사장소</th>
                        <td>
                        <c:if test="${perforItem.addr1 != null}">
                            	${perforItem.addr1}
                        </c:if>                            
                        </td>
                        <th scope="row" class="rightLine">공연시간</th>

                        <td>
                        	<c:if test="${perforItem.spendTimeFestival != null}">
                        		${perforItem.spendTimeFestival}
                        	</c:if>
                        </td>

                    </tr>
                    <tr>
                        <th scope="row">연락처</th>
                        <td>
                        	<c:if test="${perforItem.tel != null}">
                        		${perforItem.tel} <br />
                        	</c:if>
                        	<c:if test="${perforItem.sponsor1Tel != null}">
                        		${perforItem.sponsor1Tel} <br />
                        	</c:if>
                        	<c:if test="${perforItem.sponsor2Tel != null && perforItem.sponsor1Tel != perforItem.sponsor2Tel}">
                        		${perforItem.sponsor2Tel} <br />
                        	</c:if>
	                        
	                        
	                       
                        </td>
                        <th scope="row">주관</th>
                        <td>
	                        <c:if test="${perforItem.sponsor1 != null}">
                            	<span>${perforItem.sponsor1}</span>                     	
	                        </c:if>	                        
                        </td>
                    </tr>
                    </tbody>
    </table>
    <div class="contents1">
      <c:if test="${perforItem.firstImage != null}">
    	<div class="imageBox">
    		<img src="${perforItem.firstImage}" />
    	</div>
      </c:if>
      <c:if test="">
      	<div>교통정보 : ${perforItem.placeInfo}</div>
      </c:if>
      
      	<c:if test="${perforItem.program != null}">
                ${perforItem.program} <br />
        </c:if>
    	<c:if test="${perforItem.infoText != null}">
                ${perforItem.infoText} <br />
        </c:if>
    	
	</div>

	<!-- 지도 -->
        <div id="map" style="width:100%;height:350px;background-color:#eee"></div>
    <!-- // 지도 -->

	<div class="listBtn">
		<a class="btn btn-primary goList text-center" href="${pageContext.request.contextPath}/culture/cultureFestival.do">
		목록보기
		</a>
	</div>

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
<%@ include file="/WEB-INF/inc/AllmenuModal.jsp" %>
<%@ include file="/WEB-INF/inc/CommonScript.jsp" %>
<!-- 개인 js 참조 영역 -->
<script>
	var cultureId = "${perforItem.contentId}";
    var lng = "${perforItem.mapX}";
    var lat = "${perforItem.mapY}";
    var place = "${perforItem.addr1}";
</script>
<script src="${pageContext.request.contextPath}/assets/js/regex.js"></script><!-- searchbar를 사용하는 경우 장착 -->
<script src="${pageContext.request.contextPath}/assets/js/searchbar.js"></script><!-- searchbar를 사용하는 경우 장착 -->
<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=a44d9158e7468f7aff1f8ed8fb064834"></script>
<script src="<%=request.getContextPath()%>/plugins/ajax/ajax_helper.js"></script>
<script src="<%=request.getContextPath()%>/plugins/handlebars/handlebars-v4.0.5.js"></script>
<script src="<%=request.getContextPath()%>/plugins/multi-column/ie-row-fix.js"></script>
<script src="<%=request.getContextPath()%>/plugins/datepicker/datepicker.min.js"></script>
<script src="<%=request.getContextPath()%>/plugins/datepicker/i18n/datepicker.ko-KR.js"></script>
<script src="<%=request.getContextPath()%>/plugins/datepicker/i18n/datepicker.ko-KR.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/favorite.js"></script>
<script>
    $(function() {





        /** ===== Daum Map Api ===== */
        var mapContainer = document.getElementById('map'); // 지도를 표시할 div
        var mapOption = {
            center: new daum.maps.LatLng(lat, lng), // 지도의 중심좌표
            level: 3 // 지도의 확대 레벨
        };

        var map = new daum.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

        // 마커를 표시할 위치와 title 객체 배열입니다
        var position = {
            title: place,
            latlng: new daum.maps.LatLng(lat, lng)
        };

        // 마커 이미지의 이미지 주소입니다
        var imageSrc = "http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

        // 마커 이미지의 이미지 크기 입니다
        var imageSize = new daum.maps.Size(24, 35);

        // 마커 이미지를 생성합니다
        var markerImage = new daum.maps.MarkerImage(imageSrc, imageSize);

        // 마커를 생성합니다
        var marker = new daum.maps.Marker({
            map: map, // 마커를 표시할 지도
            position: position.latlng, // 마커를 표시할 위치
            title : position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
            image : markerImage // 마커 이미지
        });

        // 마커에 클릭이벤트를 등록합니다
        /* daum.maps.event.addListener(marker, 'click', function() {
            window.open("http://map.daum.net/link/map/" + placeId);
        }); */



    });

  //게시글 좋아요
    $(document).on("click", ".btn-like", function() {
        var target = $(this);

        if (target.find(".far").length == 1) {
            // ajax 통신 해서 결과 값 + 1
            $.ajax({
                method: "post",
                url: ROOT_URL + "/culture/culture_fest_like.do",
                data: {
                    cultureId: cultureId,
                    cultureLike: $("#likeCnt").html(),
                    chk: "Y"
                },
                dataType: "json",
                success: function(json) {
                    if (json.rt == "OK"){
                        console.log($("#likeCnt"));
                        $("#likeCnt").html(json.cultureLike);
                        target.find(".far").toggleClass("far fas");
                        target.find(".fas").css({
                            "transform": "scale(1.2, 1.2)",
                            "transition": "all .2s ease-in-out"
                        });
                    } else if(json.rt == "X-LOGIN") {
                        swal({
                            title: "<font color='lightskyblue'>알림</font>",
                            html: "로그인이 필요한 서비스 입니다.<br/>로그인하시려면 로그인 버튼을 누르세요.",
                            showCloseButton: true,
                            confirmButtonText: "로그인",
                            confirmButtonColor: "#a00",
                            showCancelButton: true,
                            cancelButtonText: "취소",
                            cancelButtonColor: "#f60",
                            imageUrl: ROOT_URL + "/images/common/login_need.jpg",
                            imageWidth: 400,
                            imageHeight: 350
                        }).then(function(result) {
                            if (result.value) {
                                location.href = ROOT_URL + "/member/member_login.do";
                            } else if (result.dismiss === "cancel") {

                            }
                        });
                    } else {
                        alert("" + json.rt, undefined, undefined, "warning");
                        return false;
                    }
                },
                error: function(request, status, error) {
                     alert("ResponseCode >> "+request.status+"\n"+"Message >> "+request.responseText+"\n"+"Error >> "+error);
                }
            }); // End POST $.ajax

        } else if (target.find(".fas").length == 1) {
            // ajax 통신 해서 결과 값 - 1

            $.ajax({
                method: "post",
                url: ROOT_URL + "/culture/culture_fest_like.do",
                data: {
                    cultureId: cultureId,
                    cultureLike: $("#likeCnt").html(),
                    chk: "N"
                },
                dataType: "json",
                success: function(json) {
                    if (json.rt == "OK"){
                        console.log($("#likeCnt"));
                        $("#likeCnt").html(json.cultureLike);
                        target.find(".fas").toggleClass("fas far");
                        target.find(".far").css({
                            "transform": "scale(1.0, 1.0)",
                            "transition": "all .2s ease-in-out"
                        });
                    } else if(json.rt == "X-LOGIN") {
                        swal({
                            title: "<font color='lightskyblue'>알림</font>",
                            html: "로그인이 필요한 서비스 입니다.<br/>로그인하시려면 로그인 버튼을 누르세요.",
                            showCloseButton: true,
                            confirmButtonText: "로그인",
                            confirmButtonColor: "#a00",
                            showCancelButton: true,
                            cancelButtonText: "취소",
                            cancelButtonColor: "#f60",
                            imageUrl: ROOT_URL + "/images/common/login_need.jpg",
                            imageWidth: 400,
                            imageHeight: 350
                        }).then(function(result) {
                            if (result.value) {
                                location.href = ROOT_URL + "/member/member_login.do";
                            } else if (result.dismiss === "cancel") {

                            }
                        });
                    } else {
                        alert("" + json.rt, undefined, undefined, "warning");
                        return false;
                    }
                },
                error: function(request, status, error) {
                     alert("ResponseCode >> "+request.status+"\n"+"Message >> "+request.responseText+"\n"+"Error >> "+error);
                }
            }); // End POST $.ajax
        } // End if~else if
    }); // End 좋아요 click Event


</script>
</body>
</html>
