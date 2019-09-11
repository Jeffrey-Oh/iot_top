$(function() {
	// search input tags autocomplete
	var arrivalCitys = [ "제주", "서울/김포", "부산/김해", "여수", "대구", "울산", "울산", "광주", "포항", "진주", "무안" ];

	var airlineKoreans = [ "아시아나항공", "이스타항공", "티웨이항공", "진에어", "제주항공",
			"대한항공", "에어부산" ];

	$("#boardingKor, #arrivedKor").autocomplete({
		source : arrivalCitys
	});

	$("#airlineKorean").autocomplete({
		source : airlineKoreans
	});

	$("#domestic-label").parent().css({
		"background-color" : "lightseagreen",
		"color" : "#fff"
	});

	// 페이징 번호만 글꼴(?)로 인해 약간 올라가 있어서 css로 수정
	for (var i = 1; i <= 9; i++) {
		if (!isNaN($(".list-number li:nth-child(" + i + ") a font")
				.html())) {
			$(".list-number li:nth-child(" + i + ")").css({
				"position" : "relative",
				"top" : "1px"
			});
		};
	};

	// Air Live 현황 badge css 동적 표현
	for (var i = 2; i <= 11; i++) {
		var html = $(
				".live-table tr:nth-child(" + i
						+ ") td:last-child span").html();
		var css = $(".live-table tr:nth-child(" + i
				+ ") td:last-child span");

		if (html == "출발") {
			css.addClass("badge_1");
			continue;
		} else if (html == "도착") {
			css.addClass("badge_2");
			continue;
		} else if (html == "지연") {
			css.addClass("badge_3");
			continue;
		} else if (html == "탑승장 입장") {
			css.addClass("badge_4 color3");
			continue;
		} else if (html == "수속중") {
			css.addClass("badge_5 color3");
			continue;
		} else if (html == "탑승최종") {
			css.addClass("badge_6");
			continue;
		} else if (html == "탑승구 변경") {
			css.addClass("badge_7");
			continue;
		};
	};

	$.validator.addMethod("kor", function(value, element) {
		return this.optional(element) || /^[ㄱ-ㅎ가-힣\/]*$/i.test(value);
	});

	$.validator.addMethod("eng-num", function(value, element) {
		return this.optional(element) || /^[a-zA-Z0-9]*$/i.test(value);
	});

	$("#home_search_form").validate({
		rules : {
			"boardingKor" : {
				"required" : true,
				"kor" : true,
				"minlength" : 2
			},
			"arrivedKor" : {
				"required" : true,
				"kor" : true,
				"minlength" : 2
			},
			"airlineKorean" : {
				"kor" : true,
				"minlength" : 2
			},
			"airFln" : {
				"eng-num" : true,
				"minlength" : 2
			}
		},

		messages : {
			"boardingKor" : {
				"required" : "출발지를 입력하세요.",
				"kor" : "출발지는 한글만 입력 가능합니다.",
				"minlength" : "2글자 이상 입력하세요."
			},
			"arrivedKor" : {
				"required" : "도착지를 입력하세요.",
				"kor" : "도착지는 한글만 입력 가능합니다.",
				"minlength" : "2글자 이상 입력하세요."
			},
			"airlineKorean" : {
				"kor" : "항공사명은 한글만 입력 가능합니다.",
				"minlength" : "2글자 이상 입력하세요."
			},
			"airFln" : {
				"eng-num" : "편명은 영어와 숫자만 입력 가능합니다.",
				"minlength" : "2글자 이상 입력하세요."
			}
		},
		submitHandler : function(form) {
			form.submit();
		}
	});
});