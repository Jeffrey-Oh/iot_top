 $("#btn-favorite").click(function(){
	var link = document.location.href;
	var target = $(this).find(".fa-star");
	if (target.hasClass("far")) {
		$.post(ROOT_URL + '/mypage/mypage_favorite_ok.do', {link: link}, function(json) {			
			if (json.rt == "OK") {
				alert("즐겨찾기에 저장되었습니다.");
				target.toggleClass("far fas");
				target.css({
					"color": "#FA0",
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
                       imageUrl: ROOT_URL + "/images/common/banner.jpg",
                       imageWidth: 480,
                       imageHeight: 240,
                       animation: false
				}).then(function(result) {
                    if (result.value) {
                        location.href = ROOT_URL + "/member/member_login.do";
                    } else if (result.dismiss === "cancel") {
                    	
                    }
                });
			}
		});	
	} else {
		$.post(ROOT_URL + '/mypage/mypage_favorite_delete_ok.do', {link: link}, function(json){
			if (json.rt == "OK"){
				alert("즐겨찾기가 삭제되었습니다.");
				target.toggleClass("fas far");
				target.css({
					"color": "#333",
					"transform": "scale(1.0, 1.0)",
					"transition": "all .2s ease-in-out"
				});
			} else {
				swal({
					title: "<font color='lightskyblue'>알림</font>",
                    html: "여기야 여기~<br/>로그인하시려면 로그인 버튼을 누르세요.",
                    showCloseButton: true,
                    confirmButtonText: "로그인",
                    confirmButtonColor: "#a00",
                    showCancelButton: true,
                    cancelButtonText: "취소",
                    cancelButtonColor: "#f60",
                       imageUrl: ROOT_URL + "/images/common/banner.jpg",
                       imageWidth: 480,
                       imageHeight: 240,
                       animation: false
				}).then(function(result) {
                    if (result.value) {
                        location.href = ROOT_URL + "/member/member_login.do";
                    } else if (result.dismiss === "cancel") {
                    	
                    }
                });
			}
		});	
	}
});