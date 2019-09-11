$(function() {
    /** onload 시 이름 입력 항목에 자동커서 */
    $("#userPw").focus();

    /** 회원수정 전 비밀번호 입력 폼의 Submit Event */
    $("#member_edit_door").submit(function(e) {
        // jQuery에서의 기본 동작 중단 처리
        e.preventDefault();

        // 비밀번호 값 검사
        if(!regex.value("#userPw", "비밀번호를 입력해 주세요.")) {return false;}
        if(!regex.min_length("#userPw", 6, "비밀번호는 최소 6자 이상 입력하셔야 합니다.")) {return false;}
        if(!regex.max_length("#userPw", 20, "비밀번호는 최대 20자 까지만 입력 가능합니다.")) {return false;}
        if(!regex.password("#userPw", "비밀번호는 적어도 1개 이상의 숫자와 1개 이상의 특수문자가 포함되어야 합니다.")) {return false;}

        var memF = $(this);
        memF.unbind("submit").submit();
    }); // End 회원수정 전 비밀번호 입력 폼의 Submit Event
}); // End $function() {};