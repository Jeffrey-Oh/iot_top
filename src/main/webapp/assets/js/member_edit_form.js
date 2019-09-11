$(function() {
    /** onload 시 변경할 비밀번호 입력 항목에 자동커서 */
    $("#userPw").focus();
    /** 이전단계 버튼 */
    $("#backBtn").click(function(e) {
        history.back();
    });


    /** ===== 인증번호 발송 처리 및 인증 확인 ===== */
    var certificationNum = false;
    /** ===== 이메일 중복확인 ===== */
    var email_check_ok = false;

    /** 이메일 중복확인 버튼 */
    $("#email_check").click(function(e) {
        e.preventDefault();

        var email_val = $("#email").val();
        if(!regex.value("#email", "이메일을 입력해 주세요.")) {return false;}
        if(!regex.email("#email", "이메일 주소의 형식이 잘못되었습니다.")) {return false;}
        /** JSTL로 전역 변수로 쓰인 ROOT_URL을 사용한다. */
        $.post(ROOT_URL + "/member/email_check_ok.do", {email: email_val}, function(json) {
            // console.log(json);
            if(json.rt == "OK") {
                alert("사용 가능한 이메일 입니다.\n 이메일로 인증번호를 보내드렸으니 확인해주세요.", undefined, function() {
                    certificationNum = !certificationNum;
                    email_check_ok = !email_check_ok;
                    $("#email_check").addClass("blind");

                    // 템플릿 HTML을 로드한다.
                    var template = Handlebars.compile($("#tmpl_cert_num").html());
                    // 동적으로 인증번호 입력 영역을 form에 추가한다.
                    $("#cert_num_div").empty();
                    $("#cert_num_div").append(template);
                }, "success");
            } else if(json.rt == "X-LOGIN") {
                alert("로그인 후에 이용 가능합니다.", undefined, function() {
                    location.href = ROOT_URL + "/index.do";
                }, "error");
                return false;
            } else {
                alert("" + json.rt, undefined, undefined, "warning");
                return false;
            }

        }); // End $.post Ajax
    }); // End email_check click Event

    /** 인증번호의 결과를 저장한다. */
    var cert_ok = false;

    $(document).on("click", "#certOkBtn", function(e) {
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
                } else if(json.rt == "X-LOGIN") {
                    alert("로그인 후에 이용 가능합니다.", undefined, function() {
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
    }); // End 동적으로 생성된 certOkBtn click Event

    /** 이메일이 내용이 변경 될 경우 동적으로 생성되는 버튼 및 초기화 처리 */
    $(document).on("change keyup", "#email", function(e) {
        if($("#email").val() != userEmail) {
            $("#email_check").removeClass("blind");
        } else {
            $("#email_check").addClass("blind");
        }

        // 초기화
        email_check_ok = false;
        cert_ok = false;
        certificationNum = false;
        $("#cert_num_div").empty();
        $("#email_check_ok").val("");
        $("#cert_ok").val("");
    }); // End change & keyup Event

    // 파일첨부 체크 리스트
	$("input[id=profileImg]").change(function(){

	    if($(this).val() != ""){
	    	// 확장자 체크
            var ext = $(this).val().split(".").pop().toLowerCase();
            if($.inArray(ext, ["gif","jpg","jpeg","png"]) == -1){
                alert("gif, jpg, jpeg, png 파일만 업로드 해주세요.");
                $(this).val("");
                return;
            }

            // 용량 체크
            for (var i=0; i<this.files.length; i++) {
            	var fileSize = this.files[i].size;
            	var fSMB = (fileSize / (1024 * 1024)).toFixed(2);
            	var maxSize = 1024 * 1024 * 5;
            	var mSMB = (maxSize / (1024 * 1024));
            	if(fileSize > maxSize){
            		alert(this.files[i].name + "(이)가 용량 5MB을 초과했습니다.\n\n<font color='red'>" + fSMB + "MB</font> / " + mSMB + "MB");
            		$(this).val("");
            	}
            }
	    }
	});

    /** 회원정보 수정 폼의 정보입력 Submit Event */
    $("#member_edit_form").submit(function(e) {
        // jQuery에서의 기본 동작 중단 처리
        e.preventDefault();

        // 변경할 비밀번호의 값이 있을 경우에만
        if($("#userPw").val() != "" || $("#userPwCheck").val() != "") {
            // 변경할 비밀번호 값 검사
            if(!regex.value("#userPw", "변경할 비밀번호를 입력해 주세요.")) {return false;}
            if(!regex.min_length("#userPw", 6, "비밀번호는 최소 6자 이상 입력하셔야 합니다.")) {return false;}
            if(!regex.max_length("#userPw", 20, "비밀번호는 최대 20자 까지만 입력 가능합니다.")) {return false;}
            if(!regex.password("#userPw", "비밀번호는 적어도 1개 이상의 숫자와 1개 이상의 특수문자가 포함되어야 합니다.")) {return false;}
            if(!regex.index_check("#userPw", "#userId", "비밀번호에는 아이디가 포함되면 안됩니다.")) {return false;}
            // 변경할 비밀번호 확인 값 검사
            if(!regex.value("#userPwCheck", "변경할 비밀번호 확인을 입력해 주세요.")) {return false;}
            if(!regex.compare_to("#userPw", "#userPwCheck", "비밀번호 확인이 잘못되었습니다.")) {return false;}
        } else {
            $("#userPw").val("");
            $("#userPwCheck").val("");
        }

        // 휴대전화 값 검사
        if(!regex.value("#phone", "연락처를 입력해 주세요.")) {return false;}
        if(!regex.phone("#phone", "연락처의 형식이 잘못되었습니다.")) {return false;}

        // 우편번호, 상세주소 값 검사
        if(!regex.value("#postcode", "우편번호를 입력해 주세요.")) {return false;}
        if(!regex.value("#addr1", "주소를 입력해 주세요.")) {return false;}
        if(!regex.value("#addr2", "상세주소를 입력해 주세요.")) {return false;}

        // 이메일 값이 바뀌었는지에 따라 유효성 검사를 한다.
        if($("#email").val() != userEmail) {
            // 이메일 값 검사
            if(!regex.value("#email", "이메일을 입력해 주세요.")) {return false;}
            if(!regex.email("#email", "이메일 주소의 형식이 잘못되었습니다.")) {return false;}

            // 이메일 중복확인 검사
            if(!email_check_ok) {
                alert("이메일 중복확인을 먼저 하셔야 합니다.", undefined, function() {
                    setTimeout(function() {
                        // 대상요소에게 포커스 강제 지정
                        $("#email_check").focus();
                    }, 100)
                }, "warning");
                return false;
            }

            // hidden으로 보낼 값을 저장한다.
            $("#email_check_ok").val(email_check_ok);

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
            // hidden으로 보낼 값을 저장한다.
            $("#cert_ok").val(cert_ok);
        } // End email 유효성 검사

        var memF = $(this);
        memF.unbind("submit").submit();
    }); // End 회원정보 수정 폼의 정보입력 Submit Event
}); // End $function() {};
