$(function() {
    /** onload 시 이름 입력 항목에 자동커서 */
    $("#userName").focus();
    /** 이전단계 버튼 */
    $("#backBtn").click(function(e) {
        history.back();
    });

    /** ===== 아이디 중복확인 ===== */
    var userId_check_ok = false;

    /** 아이디의 내용이 변경 될 경우 초기화 */
    $(document).on("change keyup", "#userId", function(e) {
        // 초기화
        userId_check_ok = false;
        $("#userId_check_ok").val("");
        $("#userId_check").removeClass("blind");
    }); // End change & keyup Event

    /** 아이디 중복확인 버튼 */
    $("#userId_check").click(function(e) {
        e.preventDefault();

        var userId_val = $("#userId").val();
        if(!regex.value("#userId", "아이디를 입력해 주세요.")) {return false;}
        if(!regex.eng_num("#userId", "아이디는 영어와 숫자 조합만 입력 가능합니다.")) {return false;}
        if(!regex.min_length("#userId", 4, "아이디는 최소 4자 이상 입력하셔야 합니다.")) {return false;}
        if(!regex.max_length("#userId", 20, "아이디는 최대 20자 까지만 입력 가능합니다.")) {return false;}
        /** JSTL로 전역 변수로 쓰인 ROOT_URL을 사용한다. */
        $.post(ROOT_URL + "/member/user_id_check_ok.do", {userId: userId_val}, function(json) {
            // console.log(json);
            if(json.rt == "OK") {
                alert("사용 가능한 아이디 입니다.", undefined, function(e) {
                    userId_check_ok = !userId_check_ok;
                    $("#userId_check").addClass("blind");
                    $("#userPw").focus();
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

    /** 회원가입 폼의 정보입력 Submit Event */
    $("#member_join_form").submit(function(e) {
        // jQuery에서의 기본 동작 중단 처리
        e.preventDefault();

        // 아이디 값 검사
        if(!regex.value("#userId", "아이디를 입력해 주세요.")) {return false;}
        if(!regex.eng_num("#userId", "아이디는 영어와 숫자 조합만 입력 가능합니다.")) {return false;}
        if(!regex.min_length("#userId", 4, "아이디는 최소 4자 이상 입력하셔야 합니다.")) {return false;}
        if(!regex.max_length("#userId", 20, "아이디는 최대 20자 까지만 입력 가능합니다.")) {return false;}

        // 비밀번호 값 검사
        if(!regex.value("#userPw", "비밀번호를 입력해 주세요.")) {return false;}
        if(!regex.min_length("#userPw", 6, "비밀번호는 최소 6자 이상 입력하셔야 합니다.")) {return false;}
        if(!regex.max_length("#userPw", 20, "비밀번호는 최대 20자 까지만 입력 가능합니다.")) {return false;}
        if(!regex.password("#userPw", "비밀번호는 적어도 1개 이상의 숫자와 1개 이상의 특수문자가 포함되어야 합니다.")) {return false;}
        if(!regex.index_check("#userPw", "#userId", "비밀번호에는 아이디가 포함되면 안됩니다.")) {return false;}
        // 비밀번호 확인 값 검사
        if(!regex.value("#userPwCheck", "비밀번호 확인을 입력해 주세요.")) {return false;}
        if(!regex.compare_to("#userPw", "#userPwCheck", "비밀번호 확인이 잘못되었습니다.")) {return false;}

        // 휴대전화 값 검사
        if(!regex.value("#phone", "연락처를 입력해 주세요.")) {return false;}
        if(!regex.phone("#phone", "연락처의 형식이 잘못되었습니다.")) {return false;}

        // 우편번호, 상세주소 값 검사
        if(!regex.value("#postcode", "우편번호를 입력해 주세요.")) {return false;}
        if(!regex.value("#addr1", "주소를 입력해 주세요.")) {return false;}
        if(!regex.value("#addr2", "상세주소를 입력해 주세요.")) {return false;}

        // 아이디 중복확인 검사
        if(!userId_check_ok) {
            alert("아이디 중복확인을 먼저 하셔야 합니다.", undefined, function() {
            	setTimeout(function() {
                    // 대상요소에게 포커스 강제 지정
                    $("#userId_check").focus();
                }, 100)
            }, "warning");
            return false;
        }

        $("#userId_check_ok").val(userId_check_ok);

        var memF = $(this);
        memF.unbind("submit").submit();
    }); // End 회원가입 폼의 정보입력 Submit Event
}); // End $function() {};
