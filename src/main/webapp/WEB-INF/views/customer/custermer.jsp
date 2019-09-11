<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/inc/Head.jsp"%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/searchbar.css"><!-- searchbar를 사용하는 경우 장착 -->
    <!-- mainpage.css || subpage.css 중 1개는 꼭 참조 해야 함. -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/subpage.css" />
    <!-- 개인 css 링크 참조 영역 -->
    <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/daterangepicker/daterangepicker.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/customer.css">
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
    <div class="container clearfix marginTop20">
        <!-- 메인 컨텐츠 삽입 -->
        <!-- 여기에 contents 삽입 시작 -->
            <div class="notice_aside pull-left">
                <h2>
                    자주 찾는 질문 TOP 10
                </h2>
                <hr>
                <table class="table table-striped">
                    <tbody class="text-center">
                        <tr>
                            <th class="text-center">번호</th>
                            <th class="text-center">분류</th>
                            <th class="text-center">제목</th>
                            <th class="text-center">등록일</th>
                            <th class="text-center">조회수</th>
                        </tr>
                        <tr>
                            <td>9</td>
                            <td>[상품예약.결제]</td>
                            <td>Q.무이자 할부는 가능하나요?</td>
                            <td>2019-04-12</td>
                            <td>785</td>
                        </tr>
                        <tr>
                            <td>8</td>
                            <td>[해외항공권]</td>
                            <td>Q.항공권 결제는 어떻게 하나요?</td>
                            <td>2019-04-10</td>
                            <td>489</td>
                        </tr>
                        <tr>
                            <td>7</td>
                            <td>[해외항공권]</td>
                            <td>Q.결제한 항공권 취소하면 환불 수수료가 발생하나요?</td>
                            <td>2019-04-01</td>
                            <td>123</td>
                        </tr>
                        <tr>
                            <td>6</td>
                            <td>[해외항공권]</td>
                            <td>Q.티켓은 어떻게 받나요?</td>
                            <td>2019-03-24</td>
                            <td>5673</td>
                        </tr>
                        <tr>
                            <td>5</td>
                            <td>[상품예약.결제]</td>
                            <td>Q.결제수단에는 어떤 것들이 있나요?</td>
                            <td>2019-03-23</td>
                            <td>486</td>
                        </tr>
                        <tr>
                            <td>4</td>
                            <td>[해외 패키지]</td>
                            <td>Q.인솔자와 가이드의 차이점은 무엇인가요?</td>
                            <td>2019-02-16</td>
                            <td>7899</td>
                        </tr>
                        <tr>
                            <td>3</td>
                            <td>[해외 패키지]</td>
                            <td>Q.대기예약이라고 하면 예약이 어떻게 된 건가요?</td>
                            <td>2019-01-21</td>
                            <td>45345</td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>[해외호텔]</td>
                            <td>Q.예약을 하면 바로 확정이 되는건가요?</td>
                            <td>2018-12-08</td>
                            <td>15615</td>
                        </tr>
                        <tr>
                            <td>1</td>
                            <td>[회원혜택안내]</td>
                            <td>Q.[적립] 하나투어 마일리지는 어떻게 적립 받나요?</td>
                            <td>2018-11-19</td>
                            <td>783789</td>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="qna-box pull-right">
                <div class="qna-title clearfix">
                    <h2 class="pull-left">공지사항</h2>
                    <button type="button" class="btn btn-default pull-right">more</button>
                </div>
                <hr>
                <ul class="list-group">
                    <li class="list-group-item"><a href="">&lt;2019 완벽한 여행 여행박람회&gt; 전시/운영 대행사 모집</a></li>
                    <li class="list-group-item"><a href="">2019년 완벽한 여행 상품홍보자료 디자인업체 선정 입찰 공고</a></li>
                    <li class="list-group-item"><a href="">2019년 완벽한 여행 온라인 광고 대행사 선정</a></li>
                    <li class="list-group-item"><a href="">국내 여행지 꽃가루 관련 주의 안내</a></li>
                    <li class="list-group-item"><a href="">제 99회 완벽한 여행 결산 공고 [재무상태표]</a></li>
                </ul>
            </div>
            <div class="quick-call pull-right">
                <div class="fl pull-left">
                    <span class="title"><font><strong>고객센터 상담안내</strong></font></span><br/>
                    <span class="call-number"><font><strong><font size="6" face="Noto sans">1588</font><span class="item-circle"></span><font color="#05a" size="6" face="Noto sans">2486</font></strong></font></span><br />
                    <span class="open-time"><font><strong>평일 > 09:00 ~ 12:00, 13:00 ~ 18:00</strong></font></span><br />
                    <span class="ps-box"><small>업무 외 시간에는 서비스 문의하기를 이용해 주세요.</small></span>
                </div>
                <div class="fr pull-right">
                    <img src="${pageContext.request.contextPath}/images/customer/headset.png" height="148" width="172">
                </div>
            </div>
            <!-- 여기에 contents 삽입 끝 -->
            <!-- 사이드 퀵바 -->
            <div class="side-quickbar">
                <a href="${pageContext.request.contextPath}/MailForm.do"><img src="${pageContext.request.contextPath}/images/customer/que1.jpg" width="150" height="50"></a>
                <a href="${pageContext.request.contextPath}/customer/CustomerBoardList.do"><img src="${pageContext.request.contextPath}/images/customer/due1.jpg" width="150" height="50"></a>
            </div>
            <!-- //사이드 퀵바 -->
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
<script src="${pageContext.request.contextPath}/assets/js/regex.js"></script><!-- searchbar를 사용하는 경우 장착 -->
<script src="${pageContext.request.contextPath}/assets/js/searchbar.js"></script><!-- searchbar를 사용하는 경우 장착 -->
</body>
</html>