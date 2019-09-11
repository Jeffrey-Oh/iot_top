$(function() {
	// search input tags autocomplete
	var arrivalCitys = [ "제주", "서울/김포", "부산/김해", "여수", "대구", "울산", "울산", "광주", "포항", "진주", "무안" ];

	var airlineKoreans = [ "아시아나항공", "이스타항공", "티웨이항공", "진에어", "제주항공",
			"대한항공", "에어부산" ];

	$("#arrivalCity").autocomplete({
		source : arrivalCitys
	});

	$("#airlineKorean").autocomplete({
		source : airlineKoreans
	});

	$("#datepicker").datepicker({
		autoHide : true,
		format : "yyyy-mm-dd",
		language : "ko-KR",
		weekStart : 0,
		trigger : "#show-cal"
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

	var tap = $(".home_search_title input[name='tap']:checked").val();

	$("#domestic-label").parent().css({
		"background-color" : "lightseagreen",
		"color" : "#fff"
	});

	$.validator.addMethod("kor", function(value, element) {
		return this.optional(element) || /^[ㄱ-ㅎ가-힣\/]*$/i.test(value);
	});

	$.validator.addMethod("eng-num", function(value, element) {
		return this.optional(element) || /^[a-zA-Z0-9]*$/i.test(value);
	});

	$("#home_search_form").validate({
		rules : {
			"arrivalCity" : {
				"kor" : true,
				"minlength" : 2
			},
			"airlineKorean" : {
				"kor" : true,
				"minlength" : 2
			},
			"domesticNum" : {
				"eng-num" : true,
				"minlength" : 2
			}
		},

		messages : {
			"arrivalCity" : {
				"kor" : "목적지는 한글만 입력 가능합니다.",
				"minlength" : "2글자 이상 입력하세요."
			},
			"airlineKorean" : {
				"kor" : "항공사명은 한글만 입력 가능합니다.",
				"minlength" : "2글자 이상 입력하세요."
			},
			"domesticNum" : {
				"eng-num" : "편명은 영어와 숫자만 입력 가능합니다.",
				"minlength" : "2글자 이상 입력하세요."
			}
		},
		submitHandler : function(form) {
			form.submit();
		}
	});
});