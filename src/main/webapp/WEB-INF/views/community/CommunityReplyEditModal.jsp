<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<form action="${pageContext.request.contextPath}/CommReplyEditOk.do" id="reply_edit_form" method="post">
	<input type="hidden" name="replyId" value="${readReply.replyId}" />
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
		<h4 class="modal-title">댓글 수정</h4>
	</div>
	<div class="modal-body">
	<c:choose>
		 <c:when test="${readReply.memberId == loginInfo.memberId}">
			<textarea id="edit-comment-on" class="edit-comment-on" name="replyContent"
				placeholder="내용을 입력하세요.">${readReply.content}</textarea>
		</c:when>
		<c:otherwise>
			<div>저리가 ㅎ</div>
		</c:otherwise>
	</c:choose>
	</div>
	<div class="modal-footer">
		<c:choose>
			 <c:when test="${readReply.memberId == loginInfo.memberId}">
				<button type="button" class="btn btn-success" data-dismiss="modal">취소</button>
				<button type="submit" id="btn-comment-edit" class="btn-comment-edit btn btn-primary">수정</button>
			</c:when>
			<c:otherwise>
				<button type="button" class="btn btn-success" data-dismiss="modal">취소</button>
			</c:otherwise>
		</c:choose>
	</div>
</form>