$(function() {
    $("#inactive_delete_btn").click(function(e) {
        $.post(ROOT_URL + "/member/member_management_inactive_delete_ok.do", function(json) {
            // 관리자 계정이 아닐 경우
            if(json.rt == "X-MASTER") {
                alert("관리자 계정만 접근 가능합니다.", undefined, undefined, "error");
                return false;
            } else if(json.rt == "X-LOGIN") {
                alert("로그인 후에 이용 가능합니다.", undefined, undefined, "error");
                return false;
            } else if(json.rt == "OK") {
                alert("휴면계정 삭제에 성공했습니다.", undefined, function(e) {
                    location.reload();
                }, "success");
            } else {
                alert("" + json.rt, undefined, undefined, "warning");
                return false;
            }
        }); // End $.post Ajax
    }); // End click Event
});
