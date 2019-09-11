$(function() {
	// 주소복사 버튼 클릭 시
	$("#link-copy").click(function() {
		$("#link-area").attr("type", "text");
		$("#link-area").select();
		var success = document.execCommand("copy");
		$("#link-area").attr("type", "hidden");
		if (success) {
			alert("링크가 복사되었습니다.");
		};
	});
	
	//게시물 삭제
	$("#btn-del").click(function(e) {
		e.preventDefault();
		
		confirm("<font style='color: #f60;'>확인</font>", "정말 선택하신 항목을 삭제하시겠습니까?", function(result) {
			if (result) {
				$.ajax({
					method: "post",
					url: ROOT_URL + "/CommDeleteOk.do",
					data: {
						boardId: boardId,
						korCtg: korCtg
					},
					dataType: "json",
					success: function(json) {
						if (json.rt == "OK") {
							alert("삭제", "성공적으로 삭제되었습니다.", function() {
								location.href = ROOT_URL + "/CommIndex.do";
							}, "success");
						} else if (json.rt == "AuthFail") {
							alert("경고", "삭제 권한이 없습니다.");
						} else {
							alert("<font color='#05a'>Error</font>", "<strong>글 삭제에 실패했습니다. <br />관리자에게 문의하세요.</strong>", function() {
								location.href = ROOT_URL + "/CommIndex.do";
				        	}, "error");
						}
					},
					error: function(request, status, error) {
						 alert("ResponseCode >> "+request.status+"\n"+"Message >> "+request.responseText+"\n"+"Error >> "+error);
					}
				})
			} else {
				alert("취소", "삭제가 취소되었습니다.", undefined, "error");
			}
		}, "warning");
	});
	
	//게시글 좋아요
	$(document).on("click", ".btn-like", function() {
		var target = $(this);
		
		if (target.find(".far").length == 1) {
			// ajax 통신 해서 결과 값 + 1
			$.ajax({
				method: "post",
				url: ROOT_URL + "/CommLike.do",
				data: {
					boardId: boardId,
					boardLike: $("#likeCnt").html(),
					chk: "Y"
				},
				dataType: "json",
				success: function(json) {
					if (json.rt == "OK"){
						$("#likeCnt").html(json.item.boardLike);
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
				url: ROOT_URL + "/CommLike.do",
				data: {
					boardId: boardId,
					boardLike: $("#likeCnt").html(),
					chk: "N"
				},
				dataType: "json",
				success: function(json) {
					if (json.rt == "OK"){
						$("#likeCnt").html(json.item.boardLike);
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
	
	var category = korCtg;
	var switch1 = "#ctg li:nth-child(1) a";
	var switch2 = "#ctg li:nth-child(2) a";
	var switch3 = "#ctg li:nth-child(3) a";
	var switch4 = "#ctg li:nth-child(4) a";
	var switch5 = "#ctg li:nth-child(5) a";
	var switch6 = "#ctg li:nth-child(6) a";
	
	if (category == "") {
		$(switch1).addClass("ctg-a color1");
		$(switch1).not(switch1).removeClass("ctg-a");
	} else if (category == "공지사항") {
		$(switch2).addClass("ctg-a color1");
		$(switch2).not(switch2).removeClass("ctg-a");
	} else if (category == "관광") {
		$(switch3).addClass("ctg-a color1");
		$(switch3).not(switch3).removeClass("ctg-a");
	} else if (category == "항공") {
		$(switch4).addClass("ctg-a color1");
		$(switch4).not(switch4).removeClass("ctg-a");
	} else if (category == "휴게소") {
		$(switch5).addClass("ctg-a color1");
		$(switch5).not(switch5).removeClass("ctg-a");
	} else if (category == "문화") {
		$(switch6).addClass("ctg-a color1");
		$(switch6).not(switch6).removeClass("ctg-a");
	};
	
	// View 하단 테이블에 현재 게시글 위치로 active한 효과주기
	for (var i=2; i<=11; i++) {
		if ($(".bd-table tr:nth-child(" + i + ") td:nth-child(1)").html() == boardNum) {
			$(".bd-table tr:nth-child(" + i + ")").css({
			    "background-color": "aliceblue"
			});
		}
	};
	
	$(".list-query-add a").click(function(e) {
		e.preventDefault();
		
		var curThis = $(this);
		var href = curThis.attr("href");
		var tempUrl = href.substring(0, href.indexOf("&category="));
		var ctgParams = href.substring(href.indexOf("&category="));
		tempUrl += "&list=" + nowPage + ctgParams;
		curThis.attr("href", tempUrl);
		var url = curThis.attr("href");
		location.href = url;

	});
});