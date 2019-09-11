<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- footer -->
<footer id="footer" class="bg_color3">
    <div class="container text-center">
        <ul class="fnb list-unstyled clearfix">
            <li><a href="${pageContext.request.contextPath}/member/member_policy_view.do">이용약관</a></li>
            <li><a href="${pageContext.request.contextPath}/member/member_policy_view.do">개인정보처리방침</a></li>
            <li><a href="#" data-toggle="modal" data-target="#allmenu">사이트맵</a></li>
            <li><a href="${pageContext.request.contextPath}/customer.do">고객센터</a></li>
            <li><a href="#" id="thanks">광고제휴문의</a></li>
        </ul>
        <address class="color4">
            COPYRIGHT &copy; ALL RIGHTS RESERVED.
        </address>
    </div>
</footer>
<!-- //footer -->