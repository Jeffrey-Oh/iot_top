$(function() {
	// 이전 댓글 불러오기
	$(document).on("click", "#prevReplyReply", function() {
		var buttonTarget = $(this);
		
		var reLimitStart = buttonTarget.next("#reLimitStart");
		var reLimitLast = buttonTarget.next().next("#reLimitLast");
		
		if (reLimitStart.val() < 10) {
			reLimitLast.val("Y");
		} else {
			reLimitLast.val("N");
		}
		
		var rTarget = $(this).parents(".re-reply_chk").attr("class");
		var newRTarget = rTarget.substring(25, rTarget.lastIndexOf(" "));
		
		$.get(ROOT_URL + "/CommReplyReply.do", {
			replyId: newRTarget,
			reLimitStart: reLimitStart.val(),
			reLimitLast: reLimitLast.val()
		}, function(json) {
			buttonTarget.find(".reCnt").html(json.reLimitStart);
			reLimitStart.val(json.reLimitStart);
			
			if (json.reLimitStart != 0) {
				buttonTarget.removeClass("hide");
				buttonTarget.parent().css("margin-bottom", "17px");
			} else {
				buttonTarget.addClass("hide");
			}
			
			// 템플릿 HTML을 로드
			var template = Handlebars.compile($("#re-reply-box-tmpl").html());
			// JSON에 포함된 '&lt;br/&gt;'을 검색해서 <br/>로 변경
			for (var i=0; i<json.item.length; i++) {
				json.item[i].content = json.item[i].content.replace(/&lt;br\/&gt;/g, "<br/>");
				
				// 댓글 아이템 항목 하나를 템플릿과 결합한다.
				var html = template(json.item[json.item.length-1-i]);
				
				// 결합된 결과를 댓글 목록에 추가한다.
				$(html).insertAfter($("#re-reply_list_" + json.replyId).children(".prevChk"));
			}
		})
	});
	
	/** 댓글 작성 폼의 submit 이벤트 Ajax구현 */
	// <form>요소의 method,action속성과 <input>태그를
	// Ajax요청으로 자종 구성한다.
	$(document).on("submit", "#reply_reply_form", function(e) {
		e.preventDefault();
		
		var targetThis = $(this);
		
		var replyId = $("#reply_reply_form").prev(".reply-box-content").attr("id").substring(6);
		
		$("#reply_reply_form").find("#replyId").val(replyId);
		
		// AjaxForm 플러그인 강제 호출
		$(this).ajaxSubmit(function(json) {
			// json은 API에서 표시하는 전체 데이터
			if (json.rt != "OK") {
				alert(json.rt);
				setTimeout(function() {
					if (json.rt == "내용을 입력하세요.") {
						$("#comment-on").focus();
					}
				}, 100);
				return false;
			}
			
			// 줄 바꿈에 대한 처리
			// --> 정규표현식 /~~~/g는 문자열 전체의 의미.
			// --> JSON에 포함된 '&lt;br/&gt;'을 검색해서 <br/>로 변경함.
			json.item.content = json.item.content.replace(/&lt;br\/&gt;/g, "<br/>")
			
			// 템플릿 HTML을 로드
			var template = Handlebars.compile($("#re-reply-box-tmpl").html());
			// JSON에 포함된 작성 결과 데이터를 템플릿에 결합.
			var html = template(json.item);
			
			var idVal = targetThis.prev().attr("id").substring(6);
			
			// 결합된 결과를 댓글 목록에 추가
			$(".re-reply_list_" + idVal).append(html);
			
			if ($("#re-reply_list_" + idVal).css("display") == "none") {
				$("#reply_" + idVal + " > .fl .collapse button").trigger("click");
			}
			
			var prevCnt = eval(Number($("#reply_" + idVal + " #re-commentCnt").html()) + 1);
			
			$("#reply_" + idVal + " #re-commentCnt").html(prevCnt);
			
			$("#reply_list #reply_reply_form").remove();
			
			$(".re-reply_list_" + idVal + " li").last().css({
				"background-color": "aliceblue",
			});
			
			$(".re-reply_list_" + idVal + " li").last().find($(".re-re-btn-like, .re-btn-edit, .re-btn-del")).css({
				"background-color": "aliceblue",
			});
			
			setTimeout(function() {
				$(".re-reply_list_" + idVal + " li").last().css({
					"background-color": "white",
				});
			}, 1500);
			
			setTimeout(function() {
				$(".re-reply_list_" + idVal + " li").last().find($(".re-re-btn-like, .re-btn-edit, .re-btn-del")).css({
					"background-color": "white",
				});
			}, 1500);
		});
	});
	
	$(document).on("submit", "#reply_reply_edit_form", function(e) {
		e.preventDefault();
		
		// AjaxForm 플러그인 강제 호출
		$(this).ajaxSubmit(function(json) {
				
			if (json.rt != "OK") {
				alert(json.rt);
				return false;
			}
			$("#reply_list #reply_reply_form").remove();
			alert("댓글이 수정되었습니다.");
			
			// 줄 바꿈에 대한 처리
			// --> 정규표현식 /~~~/g는 문자열 전체의 의미.
			// --> JSON에 포함된 '&lt;br/&gt;'을 검색에서 <br/>로 변경함.
			json.item.content = json.item.content.replace(/&lt;br\/&gt;/g, "<br/>");
			
			// 템플릿 HTML을 로드
			var template = Handlebars.compile($("#re-reply-box-tmpl").html());
			// JSON에 포함된 작성 결과 데이터를 템플릿에 결합.
			var html = template(json.item);
			// 폼 태그의 입력값을 초기화 하기 위해 reset 이벤트 강제 호출
			$("#re-reply_" + json.item.replyReplyId).replaceWith(html);
			
			// 댓글 수정 모달 강제로 닫기
			$("#replyEditModal").modal("hide");
		});
	});
	
	$(document).on("submit", "#reply_reply_delete_form", function(e) {
		e.preventDefault();
		
		// AjaxForm 플러그인 강제 호출
		$(this).ajaxSubmit(function(json) {
			if (json.rt != "OK") {
				alert(json.rt);
				return false;
			}
			$("#reply_list #reply_reply_form").remove();
			alert("댓글이 삭제되었습니다.");
	
			// modal 강제로 닫기
			$("#replyDeleteModal").modal("hide");
			
			// JSON 결과에 포함된 댓글일련번호를 사용하여 삭제할 <li>의 id값을 찾는다.
			var replyId = json.replyId;
			
			// 이거 값 잘 찾아서 원본 댓글에 대댓글 개수 조절하기
			// 그리고 전체 댓글 이전댓글 부르는 것도 화살표가 아닌 개수 표시 하는 걸로 바꾸기
			var recommentCnt = eval($("#re-reply_list_" + replyId).prev().find("#re-commentCnt").html() - 1);
			
			$("#re-reply_list_" + replyId).prev().find("#re-commentCnt").html(recommentCnt);
			
			$("#re-reply_" + json.replyReplyId).remove();
		});
	});
	
	/* 모든 모달창의 캐시 방지 처리 */
	$(".modal").on("hidden.bs.modal", function(e) {
		// 모달창 내의 내용을 강제로 지움.
		$(this).removeData("bs.modal");
	});
	
	$("#re-comment-on").click(function() {
		$(this).css("cursor", "auto");
	});
	
	//댓글의 댓글 좋아요
	// 클릭 시 배경 회색 생김 어떻게 없애야하지 ?
	$(document).on("click", ".re-re-btn-like", function() {
		var target = $(this);
		var targetLoc = target.parents("li").attr("id");
		var targetId = targetLoc.substring(9);
		var replyId = $(this).parents("li.reply-box-content").attr("id").substring(6);
		
		if (target.find(".far").length == 1) {
			// ajax 통신 해서 결과 값 + 1
			
			$.ajax({
				method: "post",
				url: ROOT_URL + "/CommReplyReplyLike.do",
				data: {
					replyId: replyId,
					replyReplyId: targetId,
					replyReplyLike: target.next().find(".re-re-likeCnt").html(),
					chk: "Y"
				},
				dataType: "json",
				success: function(json) {
					if (json.rt == "OK"){
						target.next().find(".re-re-likeCnt").html(json.item.replyReplyLike);
						target.find(".far").toggleClass("far fas");
						target.find(".fas").css({
							"transform": "scale(1.2, 1.2)",
							"transition": "all .2s ease-in-out"
						});
					} else {
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
					}
				},
				error: function(request, status, error) {
					 alert("ResponseCode >> "+request.status+"\n"+"Message >> "+request.responseText+"\n"+"Error >> "+error);
				}
			});
	
		} else if (target.find(".fas").length == 1) {
			// ajax 통신 해서 결과 값 - 1
	
			$.ajax({
				method: "post",
				url: ROOT_URL + "/CommReplyReplyLike.do",
				data: {
					replyReplyId: targetId,
					replyReplyLike: target.next().find(".re-re-likeCnt").html(),
					chk: "N"
				},
				dataType: "json",
				success: function(json) {
					if (json.rt == "OK"){
						target.next().find(".re-re-likeCnt").html(json.item.replyReplyLike);
						target.find(".fas").toggleClass("fas far");
						target.find(".far").css({
							"transform": "scale(1.0, 1.0)",
							"transition": "all .2s ease-in-out"
						});
					} else {
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
					}
				},
				error: function(request, status, error) {
					 alert("ResponseCode >> "+request.status+"\n"+"Message >> "+request.responseText+"\n"+"Error >> "+error);
				}
			});
		}
	});
	
	// 댓글의 댓글 collapse 기능
	$(document).on("click", ".re-btn-comment", function(e) {
		e.preventDefault();

        var target = $(this).parent(".collapse").attr("href");
        $(target).slideToggle(100);
        $(this).parents(".reply-box-content").find("ul").not($(target)).slideUp(100);
	});
});