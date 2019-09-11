$(function() {
	// 본인 확인 후  댓글에 대해 댓글 수정, 삭제 기능 부여 
	Handlebars.registerHelper("x-memberId", function(m, options) {
		if (m == "Y") {
			return options.fn(this);
		} else {
			return options.inverse(this);
		}
	});
	
	// 좋아요 현황 유지
	Handlebars.registerHelper("x-chk", function(chk, chkVal, options) {
		if (chk == chkVal) {
			return options.fn(this);
		} else {
			return options.inverse(this);
		}
	});
	
	// 댓글의 댓글 이전 내용 불러오기 생성
	Handlebars.registerHelper("x-tc", function(chk, options) {
		if (chk > 0) {
			return options.fn(this);
		} else {
			return options.inverse(this);
		}
	});
});