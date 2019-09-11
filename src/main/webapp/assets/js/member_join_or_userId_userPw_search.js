$(function() {
    /** onload 시 이름 입력 항목에 자동커서 */
    $("#userName").focus();

    /** ===== 인증번호 발송 처리 및 인증 확인 ===== */
    var certificationNum = false;

    $("#certBtn").click(function(e) {
        e.preventDefault();

        var userName_val = $("#userName").val();
        var email_val = $("#email").val();
        if(!regex.value("#email", "이메일을 입력해 주세요.")) {return false;}
        if(!regex.email("#email", "이메일 주소의 형식이 잘못되었습니다.")) {return false;}
        /** JSTL로 전역 변수로 쓰인 ROOT_URL을 사용한다. */
        $.post(ROOT_URL + "/member/certification_ok.do", {email: email_val, userName: userName_val}, function(json) {
            // console.log(json);
            if(json.rt == "OK") {
                alert("인증번호 발송에 성공하였습니다.", undefined, function() {
                    certificationNum = !certificationNum;
                    $("#certBtn").addClass("blind");
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
    }); // End click Event

    var cert_ok = false;

    $("#certOkBtn").click(function(e) {
        e.preventDefault();

        // 인증번호 발송 처리가 되어 있는 경우
        if(certificationNum) {
            var email_val = $("#email").val();
            if(!regex.value("#email", "이메일을 입력해 주세요.")) {return false;}
            if(!regex.email("#email", "이메일 주소의 형식이 잘못되었습니다.")) {return false;}
            var certNum_val = $("#certNum").val();
            if(!regex.value("#certNum", "이메일 인증번호를 입력해 주세요.")) {return false;}
            /** JSTL로 전역 변수로 쓰인 ROOT_URL을 사용한다. */
            $.post(ROOT_URL + "/member/certification_check_ok.do", {email: email_val, certNum: certNum_val}, function(json) {
                // console.log(json);
                if(json.rt == "OK") {
                    alert("인증에 성공하였습니다.", undefined, function() {
                        cert_ok = !cert_ok;
                        $("#certOkBtn").addClass("blind");
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
        } else {
            alert("인증번호 받기를 먼저 하셔야 합니다.", undefined, undefined, "warning");
            return false;
        }
    }); // End click Event

    /** 이메일이 내용이 변경 될 경우 초기화 */
    $(document).on("change keyup", "#email", function(e) {
        // 초기화
        cert_ok = false;
        certificationNum = false;
        $("#cert_ok").val("");
        $("#certBtn").removeClass("blind");
        $("#certOkBtn").removeClass("blind");
    }); // End change & keyup Event

    /** 회원가입여부 및 본인확인 또는 아이디 찾기와 비밀번호 찾기 폼의 정보입력 Submit Event */
    $("#member_join_or_userId_userPw_search").submit(function(e) {
        // jQuery에서의 기본 동작 중단 처리
        e.preventDefault();

        // 이름 값 검사
        if(!regex.value("#userName", "이름을 입력해 주세요.")) {return false;}
        if(!regex.kor("#userName", "이름은 한글만 입력 가능합니다.")) {return false;}
        if(!regex.min_length("#userName", 2, "이름은 최소 2자 이상 입력해야 합니다.")) {return false;}
        if(!regex.max_length("#userName", 20, "이름은 최대 20자 까지만 입력 가능합니다.")) {return false;}

        // 성별 값 검사
        if(!regex.select("#gender", "성별을 선택해 주세요.")) {return false;}

        // 생년월일 값 검사
        if(!regex.select("#year", "생년월일을 선택해 주세요.")) {return false;}
        if(!regex.select("#month", "생년월일을 선택해 주세요.")) {return false;}
        if(!regex.select("#day", "생년월일을 선택해 주세요.")) {return false;}

        if($("#userId").length > 0) {
	        // 아이디 값 검사
            if(!regex.value("#userId", "아이디를 입력해 주세요.")) {return false;}
            if(!regex.eng_num("#userId", "아이디는 영어와 숫자 조합만 입력 가능합니다.")) {return false;}
            if(!regex.min_length("#userId", 4, "아이디는 최소 4자 이상 입력하셔야 합니다.")) {return false;}
            if(!regex.max_length("#userId", 20, "아이디는 최대 20자 까지만 입력 가능합니다.")) {return false;}
        }

        // 이메일 값 검사
        if(!regex.value("#email", "이메일을 입력해 주세요.")) {return false;}
        if(!regex.email("#email", "이메일 주소의 형식이 잘못되었습니다.")) {return false;}

        // 이메일 인증번호 값 검사
        if(!regex.value("#certNum", "이메일 인증번호를 입력해 주세요.")) {return false;}
        if(!cert_ok) {
            alert("이메일 인증을 먼저 하셔야 합니다.", undefined, function() {
            	setTimeout(function() {
                    // 대상요소에게 포커스 강제 지정
                    $("#certNum").focus();
                }, 100)
            }, "warning");
            return false;
        }

        $("#cert_ok").val(cert_ok);

        var memF = $(this);
        memF.unbind("submit").submit();
    }); // End 회원가입여부 및 본인확인 또는 아이디 찾기와 비밀번호 찾기 폼의 정보입력 Submit Event
}); // End $function() {};
