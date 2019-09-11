<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<%@ include file="/WEB-INF/inc/Head.jsp"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/subpage.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/community.css" />
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.2/css/all.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/community-view.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/searchbar.css"><!-- searchbar를 사용하는 경우 장착 -->
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
        <!-- 메인 컨텐츠 삽입 -->
<!-- content 감싸는 영역 -->
<div class="content-parent">
	<!-- content 시작 -->
	<section class="content clearfix">
		<!-- content-header 시작 -->
		<div id="content-header" class="content-header">
			<!-- 본문 주제 -->
			<h1 id="page-header" class="page-header">커뮤니티</h1>
		</div>
		<!-- content-header 끝 -->
		<!-- side-menu 시작 -->
		<aside class="aside">
			<nav class="side-menu">
				<!-- li 태그에 span 비워둠 i 태그로 새로운 글 생성 시 New 표시할 껀지.... -->
				<ul class="list-unstyled ctg" id="ctg">
					<li><a href="${pageContext.request.contextPath}/CommIndex.do">전체 글 보기<span>(${totalCountAll})
						</span></a></li>
					<li><a href="${pageContext.request.contextPath}/CommIndex.do?category=notice">공지사항<span></span></a></li>
					<li><a href="${pageContext.request.contextPath}/CommIndex.do?category=tour">관광<span></span></a></li>
					<li><a href="${pageContext.request.contextPath}/CommIndex.do?category=airport">항공<span></span></a></li>
					<li><a href="${pageContext.request.contextPath}/CommIndex.do?category=servicearea">휴게소<span></span></a></li>
					<li><a href="${pageContext.request.contextPath}/CommIndex.do?category=culture">문화<span></span></a></li>
				</ul>
			</nav>
		</aside>
		<!-- side-menu 끝 -->
		<!-- 본문 section 시작 -->
		<section class="section">
			<!-- 게시물 시작 -->
			<div class="bd-table">
				<!-- 타이틀 박스 시작 -->
				<div class="tit-box clearfix">
					<div class="container top-box">
						<div class="fl">
							<span>[${item.korCtg}] ${item.title}</span>
						</div>
						<div class="fr">
							<table>
								<tr>
									<td>${item.userName}</td>
									<td>${item.editDate}</td>
									<td>조회수 ${item.boardCount}</td>
									<td>
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
									<small class="disinb"><i id="likeCnt" class="likeCnt">${item.boardLike}</i></small>
									</td>
								</tr>
							</table>
						</div>
						<div id="link-box" class="link-box pull-right clearfix">
							<ul class="list-unstyled">
								<li id="link-copy" class="pull-right"><img src="${pageContext.request.contextPath}/images/common/btn-copy-add.gif" alt="주소복사"></li>
								<li><input type="hidden" id="link-area"
									value="${curUrl}" /></li>
							</ul>
						</div>
					</div>
					<!-- hr 역할 -->
					<div class="etc-box-hl clearfix"></div>
					<!-- 내용 박스 시작 -->
					<div class="cnt-box clearfix">
						<p>${item.content}</p>
					</div>
					<div class="cnt-box-hl clearfix"></div>
					<!-- 내용 박스 끝 -->
				</div>
				<!-- 첨부파일 목록 표시하기 -->
				<c:if test="${fileList != null}">
					<!-- 첨부파일 목록 -->
					<table class="table table-bordered">
						<tbody>
							<tr>
								<th class="warning" style="width: 100px">첨부파일</th>
								<td>
									<c:forEach var="file" items="${fileList}">
										<!-- 다운로드를 위한 URL만들기 -->
										<c:url var="downloadUrl" value="/download.do">
											<c:param name="file" value="${file.fileDir}/${file.fileName}" />
											<c:param name="orgin" value="${file.orginName}" />
										</c:url>
										<!-- 다운로드 링크 -->
										<a href="${downloadUrl}" class="btn btn-link btn-xs">${file.orginName}</a>
									</c:forEach>
								</td>
							</tr>
						</tbody>
					</table>

					<!-- 이미지만 별도로 화면에 출력하기 -->
					<c:forEach var="file" items="${fileList}">
						<c:if test="${fn:substringBefore(file.contentType, '/') == 'image'}">
							<c:url var="downloadUrl" value="/download.do">
								<c:param name="file" value="${file.fileDir}/${file.fileName}" />
							</c:url>
							<p>
								<img src="${downloadUrl}"  class="img-responsive" style="margin: auto"/>
							</p>
						</c:if>
					</c:forEach>
				</c:if>
				<!-- 타이틀 박스 끝 -->
				<!-- 수정, 목록, 삭제 버튼 -->
				<div class="container btn-eld clearfix">
					<c:choose>
					<c:when test="${loginInfo.memberId == item.memberId}">
						<div class="btn-group" style="margin-left: 40% !important;">
							<a href="${pageContext.request.contextPath}/CommEdit.do?boardId=${boardId}&category=${item.category}" class="btn btn_color1">수정</a>
							<a href="${pageContext.request.contextPath}/CommIndex.do?category=${item.category}" class="btn btn-list btn_color2">목록</a>
							<button id="btn-del" class="btn btn-del btn_color3">삭제</button>
						</div>
					</c:when>
					<c:when test="${loginInfo.grade.equals('Master')}">
						<div class="btn-group" style="margin-left: 44% !important;">
							<a href="${pageContext.request.contextPath}/CommIndex.do?category=${item.category}" class="btn btn-list btn_color2">목록</a>
							<button id="btn-del" class="btn btn-del btn_color3">삭제</button>
						</div>
					</c:when>
					<c:otherwise>
						<div class="btn-group" style="margin-left: 45% !important;">
							<a href="${pageContext.request.contextPath}/CommIndex.do?category=${item.category}" class="btn btn-list btn_color2">목록</a>
						</div>
					</c:otherwise>
					</c:choose>
				</div>

				<!-- 댓글 박스 시작 -->
				<div id="reply-area" class="reply-area clearfix">
					<div class="reply-box clearfix">
						<div class="fl">
							<p>
								댓글 <i id="replyCnt" class="replyCnt">0</i>
							</p>
						</div>
					</div>
					<div id="reply-all" class="reply-all">
						<div class="comment-box-on">
							<div class="fr">
								<form action="${pageContext.request.contextPath}/CommReplyInsert.do" method="post" id="reply_form">
									<c:choose>
										<c:when test="${loginInfo != null}">
											<textarea id="comment-on" name="content" class="comment-on" placeholder="내용을 입력하세요." /></textarea>
										</c:when>

										<c:otherwise>
											<textarea id="comment-off" name="content" class="comment-off comment-on" placeholder="로그인이 필요한 서비스 입니다." /></textarea>
										</c:otherwise>
									</c:choose>
									<button type="submit" id="btn-ori" class="btn-ori btn btn_color3 btn-comment">등록</button>
								</form>
							</div>
						</div>
					</div>
				</div>
				<!-- 댓글 박스 끝 -->

				<div class="prevReply" style="width: 160px; margin: 0 388px 17px auto;">
					<button class="btn btn-info form-control" id="prevReply">이전 댓글 불러오기 <span class="reCnt">0</span></button>
					<input type="hidden" name="limitStart" id="limitStart" value="${limitStart}" />
					<input type="hidden" name="limitLast" id="limitLast" value="N" />
				</div>

				<!-- 댓글 목록 시작 -->
				<ul class="media-list" id="reply_list">

				</ul>
				<!-- 댓글 목록 끝 -->
			</div>
			<!-- 게시물 끝 -->
			<!-- 하단 section 시작 -->
			<div class="view-bottom-table">
				<!-- 검색 BOX 시작 -->
				<div class="search-box clearfix">
					<!-- 검색 종류 시작 -->
					<div class="search-dropbox">
						<select name="search-select" id="search-title">
							<option value="제목%2B내용">제목+내용</option>
							<option value="제목">제목</option>
							<option value="작성자">작성자</option>
						</select>
					</div>
					<!-- 검색 종류 끝 -->
					<!-- 검색어 form 시작 -->
					<form action="CommIndex.do?category=${category}" method="get" name="form1" id="form1" class="form1">
						<!-- 검색어 입력할 input 태그 -->
						<div class="search">
							<input type="text" name="search-word" id="search-text"
								class="search-text" placeholder="입력하세요.">
							<button type="submit" id="btn-search-i" class="btn-search-i"></button>
						</div>
					</form>
					<!-- 검색어 form 끝 -->
					<!-- 로그인+카테고리 내+공지사항이 아닌경우 글쓰기 버튼 활성화 -->
					<c:if test="${loginInfo != null && korCtg != null && !korCtg.equals('공지사항') && !loginInfo.grade.equals('Master')}">
						<div class="btn-add btn_color2">
							<a href="${pageContext.request.contextPath}/CommAdd.do?category=${category}">글쓰기</a>
						</div>
					</c:if>

					<!-- 로그인(마스터) -->
					<c:if test="${loginInfo != null && loginInfo.grade.equals('Master') && korCtg == '공지사항'}">
					<div class="btn-add btn_color2">
						<a href="${pageContext.request.contextPath}/CommAdd.do?category=${category}">글쓰기</a>
					</div>
					</c:if>
				</div>
				<!-- 검색 BOX 끝 -->
				<!-- 게시글 조회 결과 시작 -->
				<div class="table-responsive">
					<table id="bd-table" class="bd-table">
						<tr class="bg_color5 color3">
							<th>번호</th>
							<th>카테고리</th>
							<th>제목</th>
							<th>작성자</th>
							<th>조회수</th>
							<th>작성일</th>
						</tr>
						<!-- 공지사항 목록 -->
						<c:choose>
							<c:when test="${fn:length(noticeList) != 0}">
								<c:forEach var="k" items="${noticeList}" varStatus="s">
									<tr style="background-color: #fbfcd3">
										<!--
											게시글 번호 역순으로 표시
											여기서 게시글 번호는 DB에 있는 게시물 일련번호와 다름
										-->
										<td><span><i class="fas fa-thumbtack"></i></span></td>
										<td>${k.korCtg}</td>
										<!-- 게시글 조회할 경우 게시글 일련번호를 파라미터로 넘김 -->
										<td>
										<c:choose>
											<c:when test="${fn:length(k.title) <= 20}">
												<span class="list-query-add"> <a
													href="${pageContext.request.contextPath}/CommView.do?boardId=${k.boardId}&category=${k.category}">${k.title}</a>
												</span>
											</c:when>
											<c:otherwise>
												<span id="title-flow" class="title-flow list-query-add">
													<a
													href="${pageContext.request.contextPath}/CommView.do?boardId=${k.boardId}&category=${k.category}">${k.title}</a>
												</span>
											</c:otherwise>
										</c:choose>
											<!--
												// Date를 기준으로 작성 및 수정날짜가 00:00일 때
												// 이미지 N 표시
											-->
					 						<c:if test="${year == k.editDate}">
												<img
													src="${pageContext.request.contextPath}/images/common/new.png">
											</c:if></td>
										<td>${k.userName}</td>
										<td>${k.boardCount}</td>
										<td>${k.editDate}</td>
									</tr>
								</c:forEach>
							</c:when>
						</c:choose>

						<c:choose>
						<c:when test="${fn:length(list) != 0}">
								<c:forEach var="k" items="${list}" varStatus="s">
									<tr>
										<!--
					게시글 번호 역순으로 표시
					여기서 게시글 번호는 DB에 있는 게시물 일련번호와 다름
					-->
										<td>${totalCount - s.index - pageHelper.limitStart + countNotice + 1}</td>
										<td>${k.korCtg}</td>
										<!-- 게시글 조회할 경우 게시글 일련번호를 파라미터로 넘김 -->
										<td>
										<c:choose>
											<c:when test="${fn:length(k.title) <= 20}">
												<span class="list-query-add"> <a
													href="${pageContext.request.contextPath}/CommView.do?boardId=${k.boardId}&category=${category}">${k.title}</a>
												</span>
											</c:when>
											<c:otherwise>
												<span id="title-flow" class="title-flow list-query-add">
													<a
													href="${pageContext.request.contextPath}/CommView.do?boardId=${k.boardId}&category=${category}">${k.title}</a>
												</span>
											</c:otherwise>
										</c:choose>
											<!--
												// Date를 기준으로 작성 및 수정날짜가 00:00일 때
												// 이미지 N 표시
											-->
					 						<c:if test="${year == k.editDate}">
												<img
													src="${pageContext.request.contextPath}/images/common/new.png">
											</c:if></td>
										<td>${k.userName}</td>
										<td>${k.boardCount}</td>
										<td>${k.editDate}</td>
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
						<!-- 조회된 게시물이 없는 경우 -->
						<tr>
							<td colspan="6">게시물이 없습니다.</td>
						</tr>
							</c:otherwise>
							</c:choose>
					</table>
				</div>
				<!-- 게시글 조회 결과 끝 -->
				<!-- 게시판 페이징 시작 -->
				<div class="list-number">
					<nav class="text-center">
						<ul class="list-unstyled pagination">
							<c:choose>
								<c:when test="${pageHelper.prevPage > 0}">
									<c:url var="firstUrl" value="/CommIndex.do">
										<c:param name="category" value="${bbsName}" />
										<c:param name="type" value="${type}" />
										<c:param name="search-word" value="${searchWord}" />
										<c:param name="list" value="1" />
									</c:url>
									<c:url var="prevUrl" value="/CommIndex.do">
										<c:param name="category" value="${bbsName}" />
										<c:param name="type" value="${type}" />
										<c:param name="search-word" value="${searchWord}" />
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
								<c:url var="pageUrl" value="/CommIndex.do">
									<c:param name="category" value="${bbsName}" />
									<c:param name="type" value="${type}" />
									<c:param name="search-word" value="${searchWord}" />
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
									<c:url var="lastUrl" value="/CommIndex.do">
										<c:param name="category" value="${bbsName}" />
										<c:param name="type" value="${type}" />
										<c:param name="search-word" value="${searchWord}" />
										<c:param name="list" value="${pageHelper.totalPage}" />
									</c:url>
									<c:url var="nextUrl" value="/CommIndex.do">
										<c:param name="category" value="${bbsName}" />
										<c:param name="type" value="${type}" />
										<c:param name="search-word" value="${searchWord}" />
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
				<!-- 게시판 페이징 끝 -->
			</div>
			<!-- 하단 div 끝 -->
		</section>
		<!-- 본문 section 끝 -->
	</section>
	<!-- content 끝 -->
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
<script src="${pageContext.request.contextPath}/plugins/handlebars/handlebars-v4.0.11.js"></script>
<script src="${pageContext.request.contextPath}/plugins/ajax-form/jquery.form.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/favorite.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/handlebar_register.js"></script>
<script>
	var boardId = "${boardId}";
	var category = "${category}";
	var korCtg = "${korCtg}";
	var nowPage = "${nowPage}";
	var boardNum = "${boardNum}";
	var loginChk = "${loginChk}";
</script>
<script src="${pageContext.request.contextPath}/assets/js/community_reply.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/community_reply_reply.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/community_view.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/regex.js"></script><!-- searchbar를 사용하는 경우 장착 -->
<script src="${pageContext.request.contextPath}/assets/js/searchbar.js"></script><!-- searchbar를 사용하는 경우 장착 -->

<!-- 수정 modal 시작 -->
<div class="modal fade" id="editModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<!-- 이 위치에 다른 HTML이 연결된다 -->
		</div>
	</div>
</div>
<!-- 수정 modal 끝 -->

<!-- 삭제 modal 시작 -->
<div class="modal fade" id="deleteModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<!-- 이 위치에 다른 HTML이 연결된다 -->
		</div>
	</div>
</div>
<!-- 삭제 modal 끝 -->

<!-- 수정 modal 시작 -->
<div class="modal fade" id="replyEditModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<!-- 이 위치에 다른 HTML이 연결된다 -->
		</div>
	</div>
</div>
<!-- 수정 modal 끝 -->

<!-- 삭제 modal 시작 -->
<div class="modal fade" id="replyDeleteModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<!-- 이 위치에 다른 HTML이 연결된다 -->
		</div>
	</div>
</div>
<!-- 삭제 modal 끝 -->

<!-- 댓글의 댓글 등록 영역 -->
<script type="text/x-handlebars-template" id="re-comment-box-tmpl">
<form action="${pageContext.request.contextPath}/CommReplyReplyInsert.do" method="post" id="reply_reply_form">
    <div id="re-comment-box-top" class="re-comment-box-on re-comment-box-top">
        <img src="${pageContext.request.contextPath}/images/common/reply.png" width="30" height="30">
        <div class="fr">
            <textarea name="content" id="re-comment-on" class="re-comment-on" placeholder="내용을 입력하세요." /></textarea>
            <button type="submit" id="btn-re-reply" class="btn-re-reply btn btn_color3 btn-comment">등록</button>
        </div>
    </div>
</form>
</script>

<!-- 댓글 원본 영역 -->
<script type="text/x-handlebars-template" id="reply-box-tmpl">
	<li class="reply-box-content container-fluid clearfix" id="reply_{{replyId}}">
        <div class="fl">
            <div class="media-list">
                <div class="media">
                    <div class="media-body">
                        <div class="clearfix">
                            <div class="pull-left">
                                <h4 class="media-heading">
                                    <span class="userName">{{userName}}</span>
									<span class="lv {{grade}}"></span>
                                    <small> {{regDate}}</small>
								<c:choose>
									<c:when test="${loginInfo.grade.equals('Master')}">
									{{#x-memberId chkMemberId}}
                                    	<a href="${pageContext.request.contextPath}/CommReplyEdit.do?replyId={{replyId}}" data-toggle="modal" data-target="#editModal">
											<button class="re-btn-edit btn btn-lg"><i class="far fa-edit color4"></i></button>
										</a>
									{{/x-memberId}}
										<a href="${pageContext.request.contextPath}/CommReplyDelete.do?replyId={{replyId}}" data-toggle="modal" data-target="#deleteModal">
											<button class="re-btn-del btn btn-lg"><i class="fas fa-times color1"></i></button>
										</a>
									</c:when>
									<c:otherwise>
									{{#x-memberId chkMemberId}}
                                    	<a href="${pageContext.request.contextPath}/CommReplyEdit.do?replyId={{replyId}}" data-toggle="modal" data-target="#editModal">
											<button class="re-btn-edit btn btn-lg"><i class="far fa-edit color4"></i></button>
										</a>
										<a href="${pageContext.request.contextPath}/CommReplyDelete.do?replyId={{replyId}}" data-toggle="modal" data-target="#deleteModal">
											<button class="re-btn-del btn btn-lg"><i class="fas fa-times color1"></i></button>
										</a>
									{{/x-memberId}}
									</c:otherwise>
								</c:choose>
                                    <button type="button" class="re-btn-like btn btn-lg">
										{{#x-chk chk "N"}}
											<i class="far fa-heart color2"></i>
										{{else}}
											<i class="fas fa-heart color2"></i>
										{{/x-chk}}
									</button>
                                    <small class="fs14"><i id="re-likeCnt" class="re-likeCnt">{{replyLike}}</i></small>
								<a href="#re-reply_list_{{replyId}}" class="collapse">
									<button type="button" class="re-btn-comment btn btn-lg">
										<i class="far fa-comment-dots"></i>
									</button>
								</a>
									<small class="fs14"><i id="re-commentCnt" class="re-commentCnt">0</i></small>
                                </h4>
                            </div>
							<c:if test="${loginInfo != null}">
                            	<div class="pull-right">
                                	<button type="button" id="btn-reply" class="btn-reply btn btn-sm">댓글</button>
                            	</div>
							</c:if>
                        </div>
                        <p>
                            {{{content}}}
                        </p>
                    </div>
                </div>
            </div>
        </div>

		<ul class="media-list re-reply_list_{{replyId}} re-reply_chk" id="re-reply_list_{{replyId}}">
			<div class="prevReplyReply prevChk">
				<input type="hidden" name="tc" id="tc" class="tc" value="0" />
				<button class="btn btn-info form-control hide" id="prevReplyReply">이전 댓글 보기 <span class="reCnt">0</span></button>
				<input type="hidden" name="reLimitStart" id="reLimitStart" value="0" />
				<input type="hidden" name="reLimitLast" id="reLimitLast" value="N" />
			</div>
		</ul>
	</li>
</script>

<!-- 댓글 원본에 댓글 영역 -->
<script type="text/x-handlebars-template" id="re-reply-box-tmpl">
    <li class="re-reply-box-content container-fluid clearfix" id="re-reply_{{replyReplyId}}">
        <div class="fl">
            <div class="media-list">
                <div class="media">
                    <img src="${pageContext.request.contextPath}/images/common/reply.png">
                    <div class="media-body">
                        <div class="clearfix">
                            <div class="pull-left">
                                <h4 class="media-heading">
                                    <span class="userName">{{userName}}</span>
									<span class="lv {{grade}}"></span>
                                    <small> {{regDate}}</small>
								<c:choose>
									<c:when test="${loginInfo.grade.equals('Master')}">
									{{#x-memberId chkMemberId}}
                                    	<a href="${pageContext.request.contextPath}/CommReplyReplyEdit.do?replyReplyId={{replyReplyId}}" data-toggle="modal" data-target="#replyEditModal">
											<button class="re-btn-edit btn btn-lg"><i class="far fa-edit color4"></i></button>
										</a>
									{{/x-memberId}}
										<a href="${pageContext.request.contextPath}/CommReplyReplyDelete.do?replyReplyId={{replyReplyId}}" data-toggle="modal" data-target="#replyDeleteModal">
											<button class="re-btn-del btn btn-lg"><i class="fas fa-times color1"></i></button>
										</a>
									</c:when>
									<c:otherwise>
									{{#x-memberId chkMemberId}}
                                    	<a href="${pageContext.request.contextPath}/CommReplyReplyEdit.do?replyReplyId={{replyReplyId}}" data-toggle="modal" data-target="#replyEditModal">
											<button class="re-btn-edit btn btn-lg"><i class="far fa-edit color4"></i></button>
										</a>
										<a href="${pageContext.request.contextPath}/CommReplyReplyDelete.do?replyReplyId={{replyReplyId}}" data-toggle="modal" data-target="#replyDeleteModal">
											<button class="re-btn-del btn btn-lg"><i class="fas fa-times color1"></i></button>
										</a>
									{{/x-memberId}}
									</c:otherwise>
								</c:choose>
									<button type="button" class="re-re-btn-like btn btn-lg">
										{{#x-chk chk "N"}}
											<i class="far fa-heart color2"></i>
										{{else}}
											<i class="fas fa-heart color2"></i>
										{{/x-chk}}
									</button>
                                    <small class="fs14"><i id="re-re-likeCnt" class="re-re-likeCnt">{{replyReplyLike}}</i></small>
                                </h4>
                            </div>
                        </div>
                        <p>
                            {{{content}}}
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </li>
</script>

</body>
</html>
