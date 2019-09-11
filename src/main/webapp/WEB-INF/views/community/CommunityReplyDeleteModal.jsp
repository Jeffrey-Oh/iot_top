<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<form action="${pageContext.request.contextPath}/CommReplyDeleteOk.do" id="reply_delete_form" method="post">
	<input type="hidden" name="replyId" value="${replyId}" />
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title">댓글 삭제</h4>
	</div>
	<div class="modal-body">
		<c:choose>
        	<c:when test="${myComment == true}">
        		<!-- 자신의 글에 대한 삭제 -->
        		<p>정말 이 댓글을 삭제하시겠습니까?</p>
        	</c:when>
        	<c:otherwise>
        		<p>왜자꾸 ㅋ 저리가요 ^^</p>
        	</c:otherwise>
        </c:choose>
	</div>
	<div class="modal-footer">
		<c:choose>
        	<c:when test="${myComment == true}">
        		<!-- 자신의 글에 대한 삭제 -->
        		<button type="button" class="btn btn-success" data-dismiss="modal">취소</button>
        		<!-- 삭제 버튼 클래스랑 아이디 왜edit..? 확인할 것 -->
				<button type="submit" id="btn-comment-edit" class="btn-comment-edit btn btn-primary">삭제</button>
        	</c:when>
        	<c:otherwise>
        		<button type="button" class="btn btn-success" data-dismiss="modal">취소</button>
        	</c:otherwise>
        </c:choose>
	</div>
</form>