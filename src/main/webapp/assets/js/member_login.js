$(function() {
    /** onload 시 이름 입력 항목에 자동커서 */
    if($("#userId").val() != "") {
        $("#userPw").focus();
    } else {
        $("#userId").focus();
    }

    /** 로그인 폼의 정보입력 Submit Event */
    $("#member_login").submit(function(e) {
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

        var memF = $(this);
        memF.unbind("submit").submit();
    }); // End 로그인 폼의 정보입력 Submit Event
}); // End $function() {};
