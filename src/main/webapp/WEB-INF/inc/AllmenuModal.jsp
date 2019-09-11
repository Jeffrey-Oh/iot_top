<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- allmenu Modal -->
<div class="modal fade" id="allmenu">
    <div class="modal-wrapper">
        <div class="modal-content">
            <div class="container">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body text-center">
                    <ul class="list_allmenu list-unstyled clearfix">
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
                                <li><a href="#">행사&amp;축제</a></li>
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
                        <li>
                            <a href="${pageContext.request.contextPath}/weather.do">날씨</a>
                            <ul class="subnav list-unstyled">
                                <li><a href="${pageContext.request.contextPath}/weather.do">지역별 날씨</a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/customer.do">고객센터</a>
                            <ul class="subnav list-unstyled">
                                <li><a href="${pageContext.request.contextPath}/MailForm.do">1:1 문의</a></li>
                                <li><a href="${pageContext.request.contextPath}/customer/CustomerBoardList.do">1:1 문의 내역</a></li>
                            </ul>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/member/member_login.do">멤버십</a>
                            <ul class="subnav list-unstyled">
<%-- 로그인 시와 로그아웃 상태 시의 UI를 분기한다. --%>
<c:choose>
    <c:when test="${loginInfo == null}">
                                <li><a href="${pageContext.request.contextPath}/member/member_login.do">로그인</a></li>
                                <li><a href="${pageContext.request.contextPath}/member/member_join.do">회원가입</a></li>
                                <li><a href="${pageContext.request.contextPath}/member/member_userId_search.do">ID/PW찾기</a></li>
    </c:when>
    <c:otherwise>
                                <li><a href="${pageContext.request.contextPath}/member/member_logout.do">로그아웃</a></li>
                                <li><a href="${pageContext.request.contextPath}/mypage/member_edit_door.do">회원정보 수정</a></li>
    </c:otherwise>
</c:choose>
<%-- // 로그인 시와 로그아웃 상태 시의 UI를 분기한다. --%>
                                <li><a href="${pageContext.request.contextPath}/member/member_policy_view.do">정책안내</a></li>
<%-- 관리자 계정으로 접속했을 때에만 보이는 UI --%>
<c:if test="${loginInfo.grade == 'Master'}">
                                <li><a href="${pageContext.request.contextPath}/member/member_management.do">회원관리</a></li>
</c:if>
<%-- // 관리자 계정으로 접속했을 때에만 보이는 UI --%>
                            </ul>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/mypage/mypage_home.do">마이페이지</a>
                            <ul class="subnav list-unstyled">
                                <li><a href="${pageContext.request.contextPath}/mypage/mypage_home.do">마이홈</a></li>
                                <li><a href="${pageContext.request.contextPath}/mypage/mypage_favorite.do?type=1">찜 리스트</a></li>
                                <li><a href="${pageContext.request.contextPath}/mypage/mypage_favorite.do?type=2">내 게시글</a></li>
                                <li><a href="${pageContext.request.contextPath}/mypage/mypage_favorite.do?type=3">문의내역</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- //allmenu Modal -->