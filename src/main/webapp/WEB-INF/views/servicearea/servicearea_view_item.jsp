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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/servicearea.css" />
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css">
</head>
<body>
<!-- 전체를 감싸는 div -->
<div class="container-fluid">
<%@ include file="/WEB-INF/inc/Gnb.jsp"%>
<%@ include file="/WEB-INF/inc/Visual.jsp"%>
<%@ include file="/WEB-INF/inc/servicearea_Searchbar.jsp"%>

<!-- contents -->
<section id="contents">
    <h2 class="blind">본문</h2>
    <div class="container">
        <!-- 메인 컨텐츠 삽입 -->

<!-- 여기에 contents 삽입 시작 -->
<!-- ===== 개인 작업 영역 ===== -->

<!-- page header -->
<article class="page-header view_title clearfix">
    <div class="pull-left">
        <h3 class="color3">${item.serviceareaName}</h3>

        <!-- 찜, 좋아요 기능 -->
<%-- 찜리스트 선택여부에 따른 UI 분기 --%>
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

<%-- 좋아요 선택여부에 따른 UI 분기 --%>
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
        <small class="disinb"><i id="likeCnt" class="likeCnt">${item.serviceareaLike}</i></small>
    </div>

    <!-- 주소복사 기능 -->
    <div id="link-box" class="link-box pull-right clearfix">
        <ul class="list-unstyled">
            <li id="link-copy" class="link-copy pull-right"><img src="${pageContext.request.contextPath}/images/common/btn-copy-add.gif" alt="주소복사"></li>
            <li>
                <input type="hidden" id="link-area" value="${curUrl}" />
            </li>
        </ul>
    </div>
</article>
<!-- //page header -->

<div class="row clearfix">

    <!-- 서브 컨텐츠 -->
    <article class="col-md-10 pull-left">
        <!-- 내용 삽입 -->

        <!-- ===== 휴게소 상세 ===== -->
        <!-- 노선, 전화번호 -->
        <table class="table table-bordered">
            <colgroup>
                <col style="width:18%" />
                <col style="width:32%" />
                <col style="width:18%" />
                <col />
            </colgroup>
            <tbody>
                <tr>
                    <th>노선</th>
                    <td><img src="${pageContext.request.contextPath}/images/servicearea/line/${item.routeName}.gif" alt="노선" width="20" /> ${item.routeName}</td>
                    <th>전화번호</th>
<c:choose>
    <c:when test="${item.placePhone != null || item.placePhone != ''}">
                    <td>${item.placePhone}</td>
    </c:when>
    <c:otherwise>
                    <td>-</td>
    </c:otherwise>
</c:choose>
                </tr>
            </tbody>
        </table>
        <!-- // 노선, 전화번호 -->

        <!-- 지도 -->
        <div id="map" style="width:100%;height:350px;background-color:#eee"></div>
        <!-- // 지도 -->

<%-- 대표음식 값에 따른 UI 출력 --%>
<c:if test="${item.foodBatchMenu != null}">
        <!-- 대표음식 -->
        <h5 class="font26 margin_top50">대표음식</h5>

        <div class="foodinfo margin_top20">
            <div class="image">
    <c:choose>
        <c:when test="${item.imageUrl != null}">
                <img src="${item.imageUrl}" alt="대표음식" />
        </c:when>
        <c:otherwise>
                <img src="${pageContext.request.contextPath}/images/servicearea/no_image.jpg" alt="no_image" />
        </c:otherwise>
    </c:choose>
            </div>
            <div class="text">
                <p class="name">${item.foodBatchMenu}</p>
                <p class="price">${item.foodSalePrice}</p>
            </div>
        </div>
        <!-- // 대표음식 -->
</c:if>

<%-- 전체메뉴 유무에 따른 UI 출력 --%>
<c:if test="${fn: length(foodAllList) > 0}">
        <!-- 전체메뉴 -->
        <h5 class="font26 margin_top50">전체메뉴</h5>

        <table class="table table-bordered margin_top20">
            <colgroup>
                <col style="width:25%" />
                <col style="width:15%" />
                <col style="width:30%" />
                <col style="width:30%" />
            </colgroup>
            <thead>
            	<tr>
            		<th class="text-center">메뉴명</th>
                    <th class="text-center">가격</th>
                    <th class="text-center">재료</th>
                    <th class="text-center">상세설명</th>
            	</tr>
            </thead>
            <tbody>
    <c:forEach var="foodAllItem" items="${foodAllList}">
            	<tr>
            		<td class="text-center">
        <c:if test="${foodAllItem.recommendyn == 'Y'}">
                        <span class="food_badge recommend">추천</span>
        </c:if>
        <c:if test="${foodAllItem.bestfoodyn == 'Y'}">
                        <span class="food_badge best">Best</span>
        </c:if>
        <c:if test="${foodAllItem.premiumyn == 'Y'}">
                        <span class="food_badge premium">프리미엄</span>
        </c:if>
        <c:if test="${foodAllItem.seasonMenu == 's'}">
                        <span class="food_badge season_s">여름</span>
        </c:if>
        <c:if test="${foodAllItem.seasonMenu == 'w'}">
                        <span class="food_badge season_w">겨울</span>
        </c:if>
                        <strong class="menu">${foodAllItem.foodNm}</strong>
                    </td>
                    <td class="text-center">${foodAllItem.foodCost}</td>
                    <td>${foodAllItem.foodMaterial}</td>
                    <td>${foodAllItem.etc}</td>
            	</tr>
    </c:forEach>
            </tbody>
        </table>
        <!-- // 전체메뉴 -->
</c:if>

<%-- 주유소 유무에 따른 UI 출력 --%>
<c:if test="${item.oilCompany != null || item.csStat != null}">
        <!-- 주유소 -->
        <h5 class="font26 margin_top50">주유소</h5>

        <table class="table table-bordered margin_top20">
            <colgroup>
                <col style="width:18%" />
                <col style="width:32%" />
                <col style="width:18%" />
                <col />
            </colgroup>
            <tbody>
                <tr>
                    <th>정유사</th>
    <c:choose>
        <c:when test="${item.oilCompany != null}">
            <c:if test="${item.oilCompany == 'AD'}">
                    <td colspan="3">알뜰주유소</td>
            </c:if>
            <c:if test="${item.oilCompany == 'SK'}">
                    <td colspan="3">SK주유소</td>
            </c:if>
            <c:if test="${item.oilCompany == 'GS'}">
                    <td colspan="3">GS칼텍스</td>
            </c:if>
            <c:if test="${item.oilCompany == 'S'}">
                    <td colspan="3">S-Oil</td>
            </c:if>
            <c:if test="${item.oilCompany == 'HD'}">
                    <td colspan="3">현대오일뱅크</td>
            </c:if>
        </c:when>
        <c:otherwise>
                    <td colspan="3"><img src="${pageContext.request.contextPath}/images/servicearea/x_mark.png" alt="X" width="15" /></td>
        </c:otherwise>
    </c:choose>
                </tr>
                <tr>
                    <th>휘발유</th>
    <c:choose>
        <c:when test="${item.oilGasolinePrice != null && item.oilGasolinePrice != 'X'}">
                    <td>${item.oilGasolinePrice}</td>
        </c:when>
        <c:otherwise>
                    <td><img src="${pageContext.request.contextPath}/images/servicearea/x_mark.png" alt="X" width="15" /></td>
        </c:otherwise>
    </c:choose>
                    <th>경유</th>
    <c:choose>
        <c:when test="${item.oilDiselPrice != null && item.oilDiselPrice != 'X'}">
                    <td>${item.oilDiselPrice}</td>
        </c:when>
        <c:otherwise>
                    <td><img src="${pageContext.request.contextPath}/images/servicearea/x_mark.png" alt="X" width="15" /></td>
        </c:otherwise>
    </c:choose>
                </tr>
                <tr>
                    <th>LPG</th>
    <c:choose>
        <c:when test="${item.oilLpgYn != null && item.oilLpgYn != 'N' && item.oilLpgPrice != null && item.oilLpgPrice != 'X'}">
                    <td>${item.oilLpgPrice}</td>
        </c:when>
        <c:otherwise>
                    <td><img src="${pageContext.request.contextPath}/images/servicearea/x_mark.png" alt="X" width="15" /></td>
        </c:otherwise>
    </c:choose>
                    <th>전기차</th>
    <c:choose>
        <c:when test="${item.csStat == 1}">
                    <td>상태이상</td>
        </c:when>
        <c:when test="${item.csStat == 4}">
                    <td>운영중지</td>
        </c:when>
        <c:when test="${item.csStat == 5}">
                    <td>점검중</td>
        </c:when>
        <c:when test="${item.csStat == 2 || item.csStat == 3}">
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

<%-- 편의시설 유무에 따른 UI 출력 --%>
<c:if test="${atm != null || carrepair != null || fruit != null || food != null || bustransfer != null || shower != null || carwash != null || laundry != null || sleep != null || feeding != null || bench != null || pharmacy != null || hotel != null || barber != null}">
        <!-- 편의시설 -->
        <h5 class="font26 margin_top50">편의시설</h5>

        <table class="table table-bordered table-fixed text-center margin_top20">
            <tbody>
                <tr>
                    <th><img src="${pageContext.request.contextPath}/images/servicearea/atm.png" alt="ATM" width="25"><p class="margin_top5">ATM</p></th>
                    <th><img src="${pageContext.request.contextPath}/images/servicearea/carrepair.png" alt="경정비" width="25"><p class="margin_top5">경정비</p></th>
                    <th><img src="${pageContext.request.contextPath}/images/servicearea/fruit.png" alt="지역특산물" width="25"><p class="margin_top5">지역특산물</p></th>
                    <th><img src="${pageContext.request.contextPath}/images/servicearea/food.png" alt="매점" width="25"><p class="margin_top5">매점</p></th>
                    <th><img src="${pageContext.request.contextPath}/images/servicearea/bustransfer.png" alt="버스환승" width="25"><p class="margin_top5">버스환승</p></th>
                    <th><img src="${pageContext.request.contextPath}/images/servicearea/shower.png" alt="샤워실" width="25"><p class="margin_top5">샤워실</p></th>
                    <th><img src="${pageContext.request.contextPath}/images/servicearea/carwash.png" alt="세차장" width="25"><p class="margin_top5">세차장</p></th>
                    <th><img src="${pageContext.request.contextPath}/images/servicearea/laundry.png" alt="세탁실" width="25"><p class="margin_top5">세탁실</p></th>
                    <th><img src="${pageContext.request.contextPath}/images/servicearea/sleep.png" alt="수면실" width="25"><p class="margin_top5">수면실</p></th>
                    <th><img src="${pageContext.request.contextPath}/images/servicearea/feeding.png" alt="수유실" width="25"><p class="margin_top5">수유실</p></th>
                    <th><img src="${pageContext.request.contextPath}/images/servicearea/bench.png" alt="쉼터" width="25"><p class="margin_top5">쉼터</p></th>
                    <th><img src="${pageContext.request.contextPath}/images/servicearea/pharmacy.png" alt="약국" width="25"><p class="margin_top5">약국</p></th>
                    <th><img src="${pageContext.request.contextPath}/images/servicearea/hotel.png" alt="휴게텔" width="25"><p class="margin_top5">휴게텔</p></th>
                    <th><img src="${pageContext.request.contextPath}/images/servicearea/barber.png" alt="이발소" width="25"><p class="margin_top5">이발소</p></th>
                </tr>
                <tr>
                    <td>
    <c:choose>
        <c:when test="${atm != null}">
                    <img src="${pageContext.request.contextPath}/images/servicearea/o_mark.png" alt="O" width="15" />
        </c:when>
        <c:otherwise>
                    <img src="${pageContext.request.contextPath}/images/servicearea/x_mark.png" alt="X" width="15" />
        </c:otherwise>
    </c:choose>
                    </td>
                    <td>
    <c:choose>
        <c:when test="${carrepair != null}">
                    <img src="${pageContext.request.contextPath}/images/servicearea/o_mark.png" alt="O" width="15" />
        </c:when>
        <c:otherwise>
                    <img src="${pageContext.request.contextPath}/images/servicearea/x_mark.png" alt="X" width="15" />
        </c:otherwise>
    </c:choose>
                    </td>
                    <td>
    <c:choose>
        <c:when test="${fruit != null}">
                    <img src="${pageContext.request.contextPath}/images/servicearea/o_mark.png" alt="O" width="15" />
        </c:when>
        <c:otherwise>
                    <img src="${pageContext.request.contextPath}/images/servicearea/x_mark.png" alt="X" width="15" />
        </c:otherwise>
    </c:choose>
                    </td>
                    <td>
    <c:choose>
        <c:when test="${food != null}">
                    <img src="${pageContext.request.contextPath}/images/servicearea/o_mark.png" alt="O" width="15" />
        </c:when>
        <c:otherwise>
                    <img src="${pageContext.request.contextPath}/images/servicearea/x_mark.png" alt="X" width="15" />
        </c:otherwise>
    </c:choose>
                    </td>
                    <td>
    <c:choose>
        <c:when test="${bustransfer != null}">
                    <img src="${pageContext.request.contextPath}/images/servicearea/o_mark.png" alt="O" width="15" />
        </c:when>
        <c:otherwise>
                    <img src="${pageContext.request.contextPath}/images/servicearea/x_mark.png" alt="X" width="15" />
        </c:otherwise>
    </c:choose>
                    </td>
                    <td>
    <c:choose>
        <c:when test="${shower != null}">
                    <img src="${pageContext.request.contextPath}/images/servicearea/o_mark.png" alt="O" width="15" />
        </c:when>
        <c:otherwise>
                    <img src="${pageContext.request.contextPath}/images/servicearea/x_mark.png" alt="X" width="15" />
        </c:otherwise>
    </c:choose>
                    </td>
                    <td>
    <c:choose>
        <c:when test="${carwash != null}">
                    <img src="${pageContext.request.contextPath}/images/servicearea/o_mark.png" alt="O" width="15" />
        </c:when>
        <c:otherwise>
                    <img src="${pageContext.request.contextPath}/images/servicearea/x_mark.png" alt="X" width="15" />
        </c:otherwise>
    </c:choose>
                    </td>
                    <td>
    <c:choose>
        <c:when test="${laundry != null}">
                    <img src="${pageContext.request.contextPath}/images/servicearea/o_mark.png" alt="O" width="15" />
        </c:when>
        <c:otherwise>
                    <img src="${pageContext.request.contextPath}/images/servicearea/x_mark.png" alt="X" width="15" />
        </c:otherwise>
    </c:choose>
                    </td>
                    <td>
    <c:choose>
        <c:when test="${sleep != null}">
                    <img src="${pageContext.request.contextPath}/images/servicearea/o_mark.png" alt="O" width="15" />
        </c:when>
        <c:otherwise>
                    <img src="${pageContext.request.contextPath}/images/servicearea/x_mark.png" alt="X" width="15" />
        </c:otherwise>
    </c:choose>
                    </td>
                    <td>
    <c:choose>
        <c:when test="${feeding != null}">
                    <img src="${pageContext.request.contextPath}/images/servicearea/o_mark.png" alt="O" width="15" />
        </c:when>
        <c:otherwise>
                    <img src="${pageContext.request.contextPath}/images/servicearea/x_mark.png" alt="X" width="15" />
        </c:otherwise>
    </c:choose>
                    </td>
                    <td>
    <c:choose>
        <c:when test="${bench != null}">
                    <img src="${pageContext.request.contextPath}/images/servicearea/o_mark.png" alt="O" width="15" />
        </c:when>
        <c:otherwise>
                    <img src="${pageContext.request.contextPath}/images/servicearea/x_mark.png" alt="X" width="15" />
        </c:otherwise>
    </c:choose>
                    </td>
                    <td>
    <c:choose>
        <c:when test="${pharmacy != null}">
                    <img src="${pageContext.request.contextPath}/images/servicearea/o_mark.png" alt="O" width="15" />
        </c:when>
        <c:otherwise>
                    <img src="${pageContext.request.contextPath}/images/servicearea/x_mark.png" alt="X" width="15" />
        </c:otherwise>
    </c:choose>
                    </td>
                    <td>
    <c:choose>
        <c:when test="${hotel != null}">
                    <img src="${pageContext.request.contextPath}/images/servicearea/o_mark.png" alt="O" width="15" />
        </c:when>
        <c:otherwise>
                    <img src="${pageContext.request.contextPath}/images/servicearea/x_mark.png" alt="X" width="15" />
        </c:otherwise>
    </c:choose>
                    </td>
                    <td>
    <c:choose>
        <c:when test="${barber != null}">
                    <img src="${pageContext.request.contextPath}/images/servicearea/o_mark.png" alt="O" width="15" />
        </c:when>
        <c:otherwise>
                    <img src="${pageContext.request.contextPath}/images/servicearea/x_mark.png" alt="X" width="15" />
        </c:otherwise>
    </c:choose>
                    </td>
                </tr>
                <tr>
                    <td><c:if test="${atm != null}">${atmStime} ~ ${atmEtime}</c:if></td>
                    <td><c:if test="${carrepair != null}">${carrepairStime} ~ ${carrepairEtime}</c:if></td>
                    <td><c:if test="${fruit != null}">${fruitStime} ~ ${fruitEtime}</c:if></td>
                    <td><c:if test="${food != null}">${foodStime} ~ ${foodEtime}</c:if></td>
                    <td><c:if test="${bustransfer != null}">${bustransferStime} ~ ${bustransferEtime}</c:if></td>
                    <td><c:if test="${shower != null}">${showerStime} ~ ${showerEtime}</c:if></td>
                    <td><c:if test="${carwash != null}">${carwashStime} ~ ${carwashEtime}</c:if></td>
                    <td><c:if test="${laundry != null}">${laundryStime} ~ ${laundryEtime}</c:if></td>
                    <td><c:if test="${sleep != null}">${sleepStime} ~ ${sleepEtime}</c:if></td>
                    <td><c:if test="${feeding != null}">${feedingStime} ~ ${feedingEtime}</c:if></td>
                    <td><c:if test="${bench != null}">${benchStime} ~ ${benchEtime}</c:if></td>
                    <td><c:if test="${pharmacy != null}">${pharmacyStime} ~ ${pharmacyEtime}</c:if></td>
                    <td><c:if test="${hotel != null}">${hotelStime} ~ ${hotelEtime}</c:if></td>
                    <td><c:if test="${barber != null}">${barberStime} ~ ${barberEtime}</c:if></td>
                </tr>
            </tbody>
        </table>
        <!-- // 편의시설 -->
</c:if>

<%-- 휴게소 테마 정보가 있을 경우에만 UI에 노출 --%>
<c:if test="${item.themeItemName != null}">
        <!-- 휴게소 테마 -->
        <h5 class="font26 margin_top50">휴게소 테마</h5>

        <table class="table table-bordered margin_top20">
            <colgroup>
                <col style="width:18%" />
                <col />
            </colgroup>
            <thead>
                <tr>
                    <th class="text-center" colspan="2">${item.themeItemName}</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <th class="text-center">테마 소개</th>
                    <td>
                        ${item.themeDetail}
                    </td>
                </tr>
            </tbody>
        </table>
        <!-- // 휴게소 테마 -->
</c:if>

        <!-- // ===== 휴게소 상세 ===== -->

    </article>
    <!-- //서브 컨텐츠 -->

    <!-- aside -->
    <aside class="aside col-md-2 pull-right">
        <a href="${pageContext.request.contextPath}/index.do">
            <img src="${pageContext.request.contextPath}/images/common/banner.jpg" alt="" />
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
    <script>
        var servicearea_groupId = "${servicearea_groupId}";
        var lng = "${item.placeX}";
        var lat = "${item.placeY}";
        var serviceareaName = "${item.serviceareaName}";
        var placeId = "${item.placeId}";
    </script>
    <script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=a44d9158e7468f7aff1f8ed8fb064834"></script>
    <script src="${pageContext.request.contextPath}/assets/js/regex.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/servicearea_search.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/servicearea_view_item.js"></script>
    <script src="${pageContext.request.contextPath}/assets/js/favorite.js"></script>
</body>
</html>
