$(function() {
    /** onload 시 이름 입력 항목에 자동커서 */
    $("#userId").focus();

    /** 비밀번호 재설정 폼의 정보입력 전송 버튼 Click Event */
    $("#userPw_update_btn").click(function(e) {
        // jQuery에서의 기본 동작 중단 처리
        e.preventDefault();

        // 비밀번호 값 검사
        if(!regex.value("#userPw", "비밀번호를 입력해 주세요.")) {return false;}
        if(!regex.min_length("#userPw", 6, "비밀번호는 최소 6자 이상 입력하셔야 합니다.")) {return false;}
        if(!regex.max_length("#userPw", 20, "비밀번호는 최대 20자 까지만 입력 가능합니다.")) {return false;}
        if(!regex.password("#userPw", "비밀번호는 적어도 1개 이상의 숫자와 1개 이상의 특수문자가 포함되어야 합니다.")) {return false;}
        if(!regex.index_check("#userPw", "#userId", "비밀번호에는 아이디가 포함되면 안됩니다.")) {return false;}
        // 비밀번호 확인 값 검사
        if(!regex.value("#userPwCheck", "비밀번호 확인을 입력해 주세요.")) {return false;}
        if(!regex.compare_to("#userPw", "#userPwCheck", "비밀번호 확인이 잘못되었습니다.")) {return false;}

        var userPw_val = $("#userPw").val();
        var userPwCheck_val = $("#userPwCheck").val();
        /** JSTL로 전역 변수로 쓰인 ROOT_URL을 사용한다. */
        $.post(ROOT_URL + "/member/member_userPw_update_ok.do", {userPw: userPw_val, userPwCheck: userPwCheck_val}, function(json) {
            console.log(json);
            if(json.rt == "OK") {
            	alert("비밀번호 재설정에 성공하였습니다.", undefined, function() {
            		// 로그인 페이지로 이동
            		location.href = ROOT_URL + "/member/member_login.do";
            	}, "success");
            } else if(json.rt == "LOGIN_OK") {
            	alert("이미 로그인 중입니다.", undefined, function() {
            		location.href = ROOT_URL + "/index.do";
            	}, "error");
            	return false;
            } else {
            	alert("" + json.rt, undefined, undefined, "warning");
            	return false;
            }

        }); // End $.post Ajax

    }); // End 비밀번호 재설정 폼의 정보입력 전송 버튼 Click Event
}); // End $function() {};
