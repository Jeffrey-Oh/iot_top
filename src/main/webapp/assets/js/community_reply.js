$(function() {
	$.ajaxSetup({ async: false });
	
	// 전체 댓글 로딩
	$.get(ROOT_URL + "/CommReply.do", {
		boardId: boardId
	}, function(json) {
		if (json.rt != "OK") {
			alert(json.rt);
			return false;
		}
		
		$("#prevReply .reCnt").html(json.limitStart);

		$("#limitStart").val(json.limitStart);
		
		if ($("#limitStart").val() == 0) {
			$("#prevReply").addClass("hide");
		}
		
		// 템플릿 HTML을 로드
		var template = Handlebars.compile($("#reply-box-tmpl").html());
		// JSON에 포함된 '&lt;br/&gt;'을 검색해서 <br/>로 변경
		for (var i=0; i<json.item.length; i++) {
			
			json.item[i].content = json.item[i].content.replace(/&lt;br\/&gt;/g, "<br/>");
			
			// 댓글 아이템 항목 하나를 템플릿과 결합한다.
			var html = template(json.item[i]);
			
			// 결합된 결과를 댓글 목록에 추가한다.
			$("#reply_list").append(html);
		}
		
		for (var i=0; i<json.item.length; i++) {
			// 전체 댓글의 댓글 로딩
			$.get(ROOT_URL + "/CommReplyReply.do", {
				replyId: json.item[i].replyId
			}, function(json) {
				if (json.rt != "OK") {
					alert(json.rt);
					return false;
				}

				var rlVal = json.reLimitStart;
				
				$("#re-reply_list_" + json.replyId + " input:eq(1)").val(rlVal);
				
				$("#re-reply_list_" + json.replyId + " #tc").val(json.tc);
				
				var newTc = 0;
				
				if (json.tc > 0) {
					newTc = eval(json.tc - 10);
				}
				
				$("#re-reply_list_" + json.replyId + " .reCnt").html(newTc);
				
				if (rlVal != 0) {
					$("#re-reply_list_" + json.replyId + " button").removeClass("hide");
					$("#re-reply_list_" + json.replyId + " .prevReplyReply").css({
					    "width": "160px",
					    "margin": "auto",
					    "position": "absolute",
					    "left": "350px",
					    "z-index": "1",
					    "font-size": "14px !important"
					});
				}
				
				$("#reply_" + json.replyId).find("#re-commentCnt").html(json.tc);
				
				// 템플릿 HTML을 로드
				var template = Handlebars.compile($("#re-reply-box-tmpl").html());
				// JSON에 포함된 '&lt;br/&gt;'을 검색해서 <br/>로 변경
				for (var i=0; i<json.item.length; i++) {
					json.item[i].content = json.item[i].content.replace(/&lt;br\/&gt;/g, "<br/>");
					
					// 댓글 아이템 항목 하나를 템플릿과 결합한다.
					var html = template(json.item[i]);
					
					// 결합된 결과를 댓글 목록에 추가한다.
					$("#re-reply_list_" + json.item[i].replyId).append(html);
					$("#re-reply_list_" + json.item[i].replyId).css("display", "none");
				}
			});
		}
	});
	
	// 이전 댓글 불러오기
	$("#prevReply").click(function() {
		if ($("#limitStart").val() < 10) {
			$("#limitLast").val("Y");
		} else {
			$("#limitLast").val("N");
		}
		
		$.get(ROOT_URL + "/CommReply.do", {
			boardId: boardId,
			limitStart: $("#limitStart").val(),
			limitLast: $("#limitLast").val()
		}, function(json) {
			$("#limitStart").val(json.limitStart);
			if (json.limitStart == 0) {
				$("#prevReply").addClass("hide");
			}
			
			$("#prevReply .reCnt").html(json.limitStart);
			
			// 템플릿 HTML을 로드
			var template = Handlebars.compile($("#reply-box-tmpl").html());
			// JSON에 포함된 '&lt;br/&gt;'을 검색해서 <br/>로 변경
			for (var i=0; i<json.item.length; i++) {
				json.item[i].content = json.item[i].content.replace(/&lt;br\/&gt;/g, "<br/>");
				
				// 댓글 아이템 항목 하나를 템플릿과 결합한다.
				var html = template(json.item[json.item.length-1-i]);
				
				// 결합된 결과를 댓글 목록에 추가한다.
				$(html).insertBefore($("#reply_list li:nth-child(1)"));
				
				// 전체 댓글의 댓글 로딩
				$.get(ROOT_URL + "/CommReplyReply.do", {
					replyId: json.item[json.item.length-1-i].replyId
				}, function(json) {
					if (json.rt != "OK") {
						alert(json.rt);
						return false;
					}
					
					$("#reply_" + json.replyId).find("#re-commentCnt").html(json.tc);
					
					$("#reply_" + json.replyId + " #reLimitStart").val(json.reLimitStart);
					
					$("#re-reply_list_" + json.replyId + " .reCnt").html(json.reLimitStart);
					
					if (json.reLimitStart != 0) {
						$("#re-reply_list_" + json.replyId + " button").removeClass("hide");
						$("#re-reply_list_" + json.replyId + " .prevReplyReply").css({
						    "width": "160px",
						    "margin": "auto",
						    "position": "absolute",
						    "left": "350px",
						    "z-index": "1",
						    "font-size": "14px !important"
						});
					}
					
					// 템플릿 HTML을 로드
					var template = Handlebars.compile($("#re-reply-box-tmpl").html());
					// JSON에 포함된 '&lt;br/&gt;'을 검색해서 <br/>로 변경
					for (var i=0; i<json.item.length; i++) {
						json.item[i].content = json.item[i].content.replace(/&lt;br\/&gt;/g, "<br/>");
						
						// 댓글 아이템 항목 하나를 템플릿과 결합한다.
						var html = template(json.item[i]);
						
						// 결합된 결과를 댓글 목록에 추가한다.
						$("#re-reply_list_" + json.item[i].replyId).append(html);
						$("#re-reply_list_" + json.item[i].replyId).css("display", "none");
					}
				});
			}
		})
	});
	
	/** 댓글 작성 폼의 submit 이벤트 Ajax구현 */
	// <form>요소의 method,action속성과 <input>태그를
	// Ajax요청으로 자종 구성한다.
	$("#reply_form").ajaxForm(function(json) {
		// json은 API에서 표시하는 전체 데이터
		if (json.rt != "OK") {
			alert(json.rt, undefined, function() {
				setTimeout(function() {
					if (json.rt == "내용을 입력하세요.") {
						$("#comment-on").focus();
					}
				}, 100);
			});
			return false;
		}
		
		// 줄 바꿈에 대한 처리
		// --> 정규표현식 /~~~/g는 문자열 전체의 의미.
		// --> JSON에 포함된 '&lt;br/&gt;'을 검색해서 <br/>로 변경함.
		json.item.content = json.item.content.replace(/&lt;br\/&gt;/g, "<br/>")
		
		// 템플릿 HTML을 로드
		var template = Handlebars.compile($("#reply-box-tmpl").html());
		// JSON에 포함된 작성 결과 데이터를 템플릿에 결합.
		var html = template(json.item);
		// 결합된 결과를 댓글 목록에 추가
		$("#reply_list").append(html);
		// 폼 태그의 입력값을 초기화 하기 위해 reset 이벤트 강제 호출
		$("#reply_form").trigger("reset");
		
		// focus는 탭키가 먹히는 곳에만 지정할 수 있는 함수임
		$("#reply_list li").last().find(".re-btn-like").focus();
		
		$("#reply_list li").last().css({
			"background-color": "aliceblue",
		});
		
		$("#reply_list li").last().find(".re-btn-like, .re-btn-comment, .re-btn-edit, .re-btn-del").css({
			"background-color": "aliceblue",
		});
		
		setTimeout(function() {
			$("#reply_list li").last().css({
				"background-color": "white",
			});
		}, 1000);
		
		setTimeout(function() {
			$("#reply_list li").last().find(".re-btn-like, .re-btn-comment, .re-btn-edit, .re-btn-del").css({
				"background-color": "white",
			});
		}, 1000);
		
	});
	
	$(document).on("submit", "#reply_edit_form", function(e) {
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
			
//			// 템플릿 HTML을 로드
//			var template = Handlebars.compile($("#reply-box-tmpl").html());
//			// JSON에 포함된 작성 결과 데이터를 템플릿에 결합.
//			var html = template(json.item);
			// 폼 태그의 입력값을 초기화 하기 위해 reset 이벤트 강제 호출
			$("#reply_" + json.item.replyId + " >.fl .media-body p").html(json.item.content);
			
			// 댓글 수정 모달 강제로 닫기
			$("#editModal").modal("hide");
		});
	});
	
	$(document).on("submit", "#reply_delete_form", function(e) {
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
			$("#deleteModal").modal("hide");
			
			// JSON 결과에 포함된 댓글일련번호를 사용하여 삭제할 <li>의 id값을 찾는다.
			var replyId = json.replyId;
			$("#reply_" + replyId).remove();
		});
	});
	
	/* 모든 모달창의 캐시 방지 처리 */
	$(".modal").on("hidden.bs.modal", function(e) {
		// 모달창 내의 내용을 강제로 지움.
		$(this).removeData("bs.modal");
	});
	
	// div는 focus 이벤트가 먹히지 않기 때문에
	// input, textarea를 감싸는 경우 parent로 접근하여 css 처리해야한다.
	
	if (loginChk == "0") {
		$("#comment-off").parents(".fr").css("background-color", "#eee");
	};
	
	$("#comment-on").click(function() {
		$(this).css("cursor", "auto");
	});
	
	// 여전히... 클릭하고있는 상태에서의 입력을 막지 못함
	$(document).on("mouseleave", "#comment-off", function() {
		$(this).blur();
	});
	
	$(document).on("click", "#comment-off", function() {
		setTimeout(function() {
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
		}, 100);
		
		$(this).blur();
	});
	
	//댓글 좋아요
	// 클릭 시 배경 회색 생김 어떻게 없애야하지 ?
	$(document).on("click", ".re-btn-like", function() {
		var target = $(this);
		var targetLoc = target.parents("li").attr("id");
		var targetId = targetLoc.substring(6);
		
		if (target.find(".far").length == 1) {
			// ajax 통신 해서 결과 값 + 1
			
			$.ajax({
				method: "post",
				url: ROOT_URL + "/CommReplyLike.do",
				data: {
					replyId: targetId,
					replyLike: target.next().find(".re-likeCnt").html(),
					chk: "Y"
				},
				dataType: "json",
				success: function(json) {
					if (json.rt == "OK"){
						target.next().find(".re-likeCnt").html(json.item.replyLike);
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
				url: ROOT_URL + "/CommReplyLike.do",
				data: {
					replyId: targetId,
					replyLike: target.next().find(".re-likeCnt").html(),
					chk: "N"
				},
				dataType: "json",
				success: function(json) {
					if (json.rt == "OK"){
						target.next().find(".re-likeCnt").html(json.item.replyLike);
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
	
	// 댓글 버튼 클릭 시 하위 댓글 코멘트 입력 창 보이기
	$(document).on("click", ".btn-reply", function() {
		var target = $(this).parents(".reply-box-content").attr("id").substring(6);
		var cur = $(this).parents(".reply-box-content");
		
		$.ajax({
			method: "post",
			url: ROOT_URL + "/CommReplyReply2.do",
			data: {
				replyId: target
			},
			dataType: "json",
			success: function(json) {
				if (json.rt == "OK"){
					$("#reply_list #reply_reply_form").remove();
					
					var template = Handlebars.compile($("#re-comment-box-tmpl").html());

					var html = template();

			        $(html).insertAfter(cur);
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
	});
});