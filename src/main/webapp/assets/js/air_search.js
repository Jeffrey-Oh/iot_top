$(function() {
	$("#sdate").datepicker({
        autoHide: true,
        format: "yyyy-mm-dd",
        language: "ko-KR",
        weekStart: 0,
        startDate: new Date()
    });
	
	$("#sdate").on("change", function() {
		if (!$("#edate").prop("disabled")) {
            $("#edate").remove();
            $("<input type='text' id='edate' class='search_input search_input_2' name='edate' placeholder='오는 날' readonly style='margin-left: 3px;' readonly>").insertAfter($("#sdate"));
            if($("#edate").val() == "" && $("#sdate").val() != "") {
                $("#edate").datepicker({
                    autoHide: true,
                    format: "yyyy-mm-dd",
                    language: "ko-KR",
                    weekStart: 0,
                    startDate: $("#sdate").val()
                });
            }
		}
    });
	
	$(document).on("click", "#edate", function() {
        if($("#sdate").val() == "") {
            alert("알림", "가는 날을 먼저 선택하세요.", function() {
            	setTimeout(function(){
            		$("#sdate").val("");
                    $("#sdate").focus();
            	},100);
            });
        }
    });
	
    if (tab == "round") {
    	$(".home_search_title label[for='round']").css({
			"background" : "lightskyblue",
			"color" : "white"
		});
    } else {
    	$(".home_search_title label[for='oneway']").css({
			"background" : "lightseagreen",
			"color" : "white"
		});
    	$("#edate").prop("disabled", true);
		$("#edate").attr("placeholder", "");
		$("#edate").css("background-color", "#aaa");
    };

    $(".home_search_title label").click(function() {
    	if ($(this).html() == "왕복") {
    		$("#sdate").val("");
    		$("#edate").prop("disabled", false);
    		$("#edate").attr("placeholder", "오는 날");
    		$("#edate").css("background-color", "#F2F5F6");
    		$(this).css({
    			"background" : "lightskyblue",
    			"color" : "white"
    		});
    		$(".home_search_title label").not(this).css({
    			"background" : "white",
    			"color" : "#777"
    		});
    	} else {
    		$("#edate").prop("disabled", true);
    		$("#edate").attr("placeholder", "");
    		$("#edate").css("background-color", "#aaa");
    		$(this).css({
    			"background" : "lightseagreen",
    			"color" : "white"
    		});
    		$(".home_search_title label").not(this).css({
    			"background" : "white",
    			"color" : "#777"
    		});
    	}
    });

    $.validator.addMethod("kor", function(value, element) {
        return this.optional(element) || /^[ㄱ-ㅎ가-힣]*$/i.test(value);
    });

    $.validator.addMethod("date", function(value, element) {
        return this.optional(element) || /^(19|20)\d{2}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[0-1])$/i.test(value);
    });

    $("#home_search_form").validate({
		rules : {
			"sdate" : {
				"required" : true,
				"date" : true
			},
			"edate" : {
				"required" : true,
				"date" : true
			},
			"boardingKor" : {
				"required" : true,
				"kor" : true
			},
			"arrivedKor" : {
				"required" : true,
				"kor" : true
			}
		},

		messages : {
			"sdate" : {
				"required" : "가는 날을 선택하세요.",
				"date" : "날짜 형식이 올바르지 않습니다."
			},
			"edate" : {
				"required" : "오는 날을 선택하세요.",
				"date" : "날짜 형식이 올바르지 않습니다."
			},
			"boardingKor" : {
				"required" : "출발지를 입력하세요.",
				"kor" : "출발지는 한글만 입력 가능합니다."
			},
			"arrivedKor" : {
				"required" : "도착지를 입력하세요.",
				"kor" : "도착지는 한글만 입력 가능합니다."
			}
		},
		submitHandler : function(form) {
			form.submit();
		}
	});
});