<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- skip navigation -->
<nav id="accessibility">
    <h2 class="blind">컨텐츠 바로가기</h2>
    <ul class="list-unstyled">
        <li><a href="#gnb"><span>주메뉴 바로가기</span></a></li>
        <li><a href="#container"><span>본문 바로가기</span></a></li>
    </ul>
</nav>
<!-- //skip navigation -->

<!-- hot_keyword -->
<ol class="hot_keyword owl-hot_keyword list-unstyled owl-change">
<c:forEach var="item" items="${hotKeywordList}" varStatus="status">
    <li>${status.count}.&nbsp;${item.keyword}</li>
</c:forEach>
</ol>
<!-- // hot_keyword  -->

<!-- header -->
<header id="header">
    <!-- head top -->
    <div class="head_top">
        <div class="container clearfix">
            <ul class="util list-unstyled pull-right clearfix">
<%-- 세션값의 존재 여부에 따른 HTML UI 분기하기 --%>
<c:choose>
    <c:when test="${loginInfo == null}">
        <%-- 세션에서 획득한 객체가 없는 경우는 로그인 상태가 아님 --%>
                <li><a href="${pageContext.request.contextPath}/member/member_login.do">로그인</a></li>
                <li><a href="${pageContext.request.contextPath}/member/member_join.do">회원가입</a></li>
                <li><a href="${pageContext.request.contextPath}/customer.do">고객센터</a></li>
    </c:when>
    <c:otherwise>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
        <%-- 쿠키값에 따른 프로필 이미지 표시 --%>
        <c:if test="${cookie.profileThumbnail != null}">
                        <img src="${pageContext.request.contextPath}/download.do?file=${cookie.profileThumbnail.value}" class="profile img-rounded" />
        </c:if>                     
        <%-- // 쿠키값에 따른 프로필 이미지 표시 끝 --%>
                        ${loginInfo.userName}님 (Lv. <span class="lv ${loginInfo.grade}">${loginInfo.grade}</span>) <span class="caret"></span>
                    </a>
                    <!-- 로그인한 경우 표시될 메뉴 -->
                    <ul class="dropdown-menu">
                        <!--
                            ===== 로그인 중에 표시될 메뉴 구성하기 =====
                            - ContextPath를 사용하여 절대경로로 접근한다.
                        -->
                        <li>
                            <a href="${pageContext.request.contextPath}/member/member_logout.do">로그아웃</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/mypage/member_edit_door.do">회원정보 수정</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/mypage/mypage_home.do">마이페이지</a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/customer.do">고객센터</a>
                        </li>
        <c:if test="${loginInfo.grade == 'Master'}">
                        <li>
                            <a href="${pageContext.request.contextPath}/member/member_management.do">회원관리</a>
                        </li>
        </c:if>
                    </ul>
                </li>
    </c:otherwise>
</c:choose>
            </ul>
        </div>
    </div>
    <!-- //head top -->
    <!-- gnb -->
    <nav id="gnb">
        <h2 class="blind">주메뉴</h2>
        <div class="container text-center">
            <h1 class="logo"><a href="${pageContext.request.contextPath}/index.do">완벽한 여행</a></h1>
            <ul class="navigation list-unstyled clearfix">
                <li>
                    <a href="${pageContext.request.contextPath}/tour/TourList.do">관광</a>
                    <ul class="subnav list-unstyled">
                        <li><a href="${pageContext.request.contextPath}/tour/TourList2.do">여행 상품 정보</a></li>
                    </ul>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/AirMain.do">항공</a>
                    <ul class="subnav list-unstyled">
                        <li><a href="${pageContext.request.contextPath}/AirHot.do">HOT</a></li>
                        <li><a href="${pageContext.request.contextPath}/AirLive.do">Air Live</a></li>
                        <li><a href="${pageContext.request.contextPath}/AirSchedule.do">Air Schedule</a></li>
                        <li><a href="${pageContext.request.contextPath}/AirSearch.do">Air Search</a></li>
                    </ul>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/servicearea/servicearea_index.do">휴게소</a>
                    <ul class="subnav list-unstyled">
                        <li><a href="${pageContext.request.contextPath}/servicearea/servicearea_search.do">휴게소 검색</a></li>
                        <li><a href="${pageContext.request.contextPath}/servicearea/servicearea_line.do">노선별 안내</a></li>
                        <li><a href="${pageContext.request.contextPath}/servicearea/servicearea_radar.do" class="radarBtn">주변 검색</a></li>
                        <li><a href="${pageContext.request.contextPath}/servicearea/servicearea_route.do">경로 탐색</a></li>
                        <li><a href="${pageContext.request.contextPath}/servicearea/servicearea_oil.do">유가 정보</a></li>
                        <li><a href="${pageContext.request.contextPath}/servicearea/servicearea_food.do">대표 메뉴</a></li>
                        <li><a href="${pageContext.request.contextPath}/servicearea/servicearea_theme.do">테마 휴게소</a></li>
                    </ul>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/culture/culturePerformance.do">문화</a>
                    <ul class="subnav list-unstyled">
                        <li><a href="${pageContext.request.contextPath}/culture/culturePerformance.do">공연&amp;전시</a></li>
                        <li><a href="${pageContext.request.contextPath}/culture/cultureFestival.do">행사&amp;축제</a></li>
                    </ul>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/CommIndex.do">커뮤니티</a>
                    <ul class="subnav list-unstyled">
                        <li><a href="${pageContext.request.contextPath}/CommIndex.do">전체 글 보기</a></li>
                        <li><a href="${pageContext.request.contextPath}/CommIndex.do?category=tour">관광</a></li>
                        <li><a href="${pageContext.request.contextPath}/CommIndex.do?category=airport">항공</a></li>
                        <li><a href="${pageContext.request.contextPath}/CommIndex.do?category=servicearea">휴게소</a></li>
                        <li><a href="${pageContext.request.contextPath}/CommIndex.do?category=culture">문화</a></li>
                    </ul>
                </li>
                <li class="more"><a href="#" class="glyphicon glyphicon-menu-hamburger" data-toggle="modal" data-target="#allmenu"></a></li>
            </ul>
            
            <div class="weather" onclick="location.href='${pageContext.request.contextPath}/weather.do'" style="cursor:pointer;">
<div class="weather-carousel owl-theme">
    <div class="item">
    <span style="font-size:1.5em;">서울 <span id="temp0"></span>   
    <span id="icon0"></span>
    </span>
		<span style="display : inline-block;">
		<span id="temp_min0"></span>/<span id="temp_max0"></span>
		</span>
		습도 : <span id="humidity0"></span></div>
    
     <div class="item">
    <span style="font-size:1.5em;">대전 <span id="temp1"></span>
    <span id="icon1"></span>
    </span>
		<span style="display : inline-block;">
		<span id="temp_min1"></span>/<span id="temp_max1"></span>
		</span>
		습도 : <span id="humidity1"></span></div>
  
    <div class="item">
    <span style="font-size:1.5em;">전주 <span id="temp2"></span>
    <span id="icon2"></span>
    </span>
		<span style="display : inline-block;">
		<span id="temp_min2"></span>/<span id="temp_max2"></span>
		</span>
		습도 : <span id="humidity2"></span></div>
		
	<div class="item">
    <span style="font-size:1.5em;">춘천 <span id="temp3"></span>
    <span id="icon3"></span>
    </span>
		<span style="display : inline-block;">
		<span id="temp_min3"></span>/<span id="temp_max3"></span>
		</span>
		습도 : <span id="humidity3"></span></div>
		
	<div class="item">
    <span style="font-size:1.5em;">청주 <span id="temp4"></span>
    <span id="icon4"></span>
    </span>
		<span style="display : inline-block;">
		<span id="temp_min4"></span>/<span id="temp_max4"></span>
		</span>
		습도 : <span id="humidity4"></span></div>
		
	<div class="item">
    <span style="font-size:1.5em;">광주 <span id="temp5"></span>
    <span id="icon5"></span>
    </span>
		<span style="display : inline-block;">
		<span id="temp_min5"></span>/<span id="temp_max5"></span>
		</span>
		습도 : <span id="humidity5"></span></div>		
		
	<div class="item">
    <span style="font-size:1.5em;">대구 <span id="temp6"></span>
    <span id="icon6"></span>
    </span>
		<span style="display : inline-block;">
		<span id="temp_min6"></span>/<span id="temp_max6"></span>
		</span>
		습도 : <span id="humidity6"></span></div>	
	
	<div class="item">
    <span style="font-size:1.5em;">부산 <span id="temp7"></span>
    <span id="icon7"></span>
    </span>
		<span style="display : inline-block;">
		<span id="temp_min7"></span>/<span id="temp_max7"></span>
		</span>
		습도 : <span id="humidity7"></span></div>		
		
	<div class="item"> 
    <span style="font-size:1.5em;">제주 <span id="temp8"></span>
    <span id="icon8"></span>
    </span>
		<span style="display : inline-block;">
		<span id="temp_min8"></span>/<span id="temp_max8"></span>
		</span>
		습도 : <span id="humidity8"></span></div>	
				</div> 
            </div>
        </div>   
    </nav>
    <!-- //gnb -->  
</header>
<!-- //header -->

