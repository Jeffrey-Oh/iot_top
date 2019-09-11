$(function() {
        	
		

		
				$("#from").datepicker({
						autoHide : true,
						format : "yyyy-mm-dd",
						language : "ko-KR",
						weekStart : 0,

				});

				$("#from").on("change", function() {
					$("#to").remove();
					$("<input type='text' id='to' class='search_input search_input_3' name='to' placeholder='to' required='required' style='margin-left: 3px;' readonly>").insertAfter($("label[for='to']"));
					if ($("#to").val() == "" && $("#from").val() != "") {
						$("#to").datepicker({
							autoHide : true,
							format : "yyyy-mm-dd",
							language : "ko-KR",
							weekStart : 0,
							startDate : $("#from").val()
						});
					}
				});

				$(document).on("click", "#to", function() {
					if ($("#from").val() == "") {
						alert("알림", "시작일을 먼저 선택하세요.", function() {
							setTimeout(function() {
								$("#from").val("");
								$("#from").focus();
							}, 100);
						});
					}
				});
		/* }); */
        
        // 게시글 좋아요
        $(document).on("click", ".btn-like", function() {
            if ($(this).children(".far").length == 1) {
                // ajax 통신 해서 결과 값 + 1
                // 콜백함수 결과로 성공 시 이벤트로 아래 2줄을 함수안에 넣을 것

                $(this).children(".far").toggleClass("far fas");
                $(this).find(".fas").css({
                    "transform": "scale(1.2, 1.2)",
                    "transition": "all .2s ease-in-out"
                });
                
            } else if ($(this).children(".fas").length == 1) {
                // ajax 통신 해서 결과 값 - 1
                // // 콜백함수 결과로 성공 시 이벤트로 아래 2줄을 함수안에 넣을 것
                
                $(this).children(".fas").toggleClass("fas far");
                $(this).find(".far").css({
                    "transform": "scale(1.0, 1.0)",
                    "transition": "all .2s ease-in-out"
                });
                
            }
        });
        
        // 즐겨찾기
        $(document).on("click", ".btn-favorite", function() {
            if ($(this).css("color") == "rgb(51, 51, 51)") {
                // ajax 통신해서 DB 업데이트
                // 콜백함수 결과 성공 시 이벤트로 아래 1줄 함수안에 넣을 것
                $(this).css({
                    "color": "#FA0",
                    "transform": "scale(1.2, 1.2)",
                    "transition": "all .2s ease-in-out"
                });
            } else {
                // ajax 통신해서 DB 업데이트
                // 콜백함수 결과 성공 시 이벤트로 아래 1줄 함수안에 넣을 것
                $(this).css({
                    "color": "rgb(51, 51, 51)",
                    "transform": "scale(1.0, 1.0)",
                    "transition": "all .2s ease-in-out"
                });
            }
        });
        
    });