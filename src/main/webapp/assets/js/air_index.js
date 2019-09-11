$(function() {
	AOS.init();
	
	$("#domestic-label").parent().css({
		"background-color" : "lightseagreen",
		"color" : "#fff"
	});
	
	$("#contents > .container").toggleClass("intro_bg");
	
	$(".move_1 a").click(function(){
        $('html, body').stop().animate({scrollTop: 1206}, 800);
    });
	
	$(".move_2 a").click(function(){
        $('html, body').stop().animate({scrollTop: 3013}, 1000);
    });
	
	$(".move_3 a").click(function(){
        $('html, body').stop().animate({scrollTop: 4089}, 1200);
    });
	
	var date = new Date();
    date.setDate(date.getDate()+1);
    var ndate = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();

    // 출발일, 도착일 설정
    $("#deptDate").datepicker({
        autoHide: true,
        format: "yyyy-mm-dd",
        language: "ko-KR",
        weekStart: 0,
        startDate: ndate,
        ignoreReadonly: true
    });

    $("#deptDate").on("change", function() {
        $("#arriDate").remove();
        $("<input type='text' id='arriDate' class='search_input search_input_3' name='edate' placeholder='Arrival' required='required' style='margin-left: 3px;' readonly>").insertAfter($("#deptDate"));
        if($("#arriDate").val() == "" && $("#deptDate").val() != "") {
            $("#arriDate").datepicker({
                autoHide: true,
                format: "yyyy-mm-dd",
                language: "ko-KR",
                weekStart: 0,
                startDate: $("#deptDate").val(),
                ignoreReadonly: true
            });
        }
    });

	$(document).on("click", "#arriDate", function() {
        if($("#deptDate").val() == "") {
            alert("알림", "출발일을 먼저 선택하세요.", function() {
            	setTimeout(function(){
            		$("#deptDate").val("");
                    $("#deptDate").focus();
            	},100);
            });
        }
    });
	
	$(document).on("click", ".home_search_button", function() {
		if ($("#deptDate").val() != "" && $("#arriDate").val() == "") {
			alert("알림", "도착일을 선택하세요.", function() {
	        	setTimeout(function(){
	        		$("#arriDate").val("");
	                $("#arriDate").focus();
	        	},100);
	        });
			return false;
		}
	});

	// Air Live 현황 badge css 동적 표현
	// i는 6까지 현재 임의로 8로 늘림 모든 상황 보려고
	for (var i=2; i<=8; i++) {
		var html = $(".air-l tr:nth-child(" + i + ") td:last-child span").html();
		var css = $(".air-l tr:nth-child(" + i + ") td:last-child span");
		
		if(html == "출발") {
			css.addClass("badge_1");
			continue;				
		} else if(html == "도착") {
			css.addClass("badge_2");
			continue;
		} else if(html == "지연") {
			css.addClass("badge_3");
			continue;
		} else if(html == "탑승장 입장") {
			css.addClass("badge_4 color3");
			continue;
		} else if(html == "수속중") {
			css.addClass("badge_5 color3");
			continue;
		} else if(html == "탑승최종") {
			css.addClass("badge_6");
			continue;
		} else if(html == "탑승구 변경") {
			css.addClass("badge_7");
			continue;
		};
	};
});