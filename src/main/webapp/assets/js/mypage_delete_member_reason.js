$(function() {
    // 페이지가 열리자마자 초기화
    $(".reason").prop("checked", false);
    $("#reasonEtc").val("");

    // '#input_enable'의 선택 상태가 변경된 경우
    $("#input_enable").change(function () {
        // 선택이 안되었을 경우 안의 내용물을 지운다.
        if(!$("#input_enable").is(":checked")) {
            $("#reasonEtc").val("");
        }
        // '#reasonEtc'의 현재 disabled 값을 가져온다. --> true/false
        var now = $("#reasonEtc").prop('disabled');
        // 가져온 값을 역으로 변경하여 다시 적용한다.
        $("#reasonEtc").prop('disabled', !now);
        // 입력 가능 상태라면 포커스 지정
        if ($("#reasonEtc").prop('disabled') == false) {
            $("#reasonEtc").focus();
        }
    }); // End change Event

    /** 회원탈퇴 폼의 Submit Event */
    $("#member_out_reason").submit(function(e) {
        // jQuery에서의 기본 동작 중단 처리
        e.preventDefault();

        // 탈퇴 사유 체크박스 선택 검사
        if(!regex.check(".reason", "탈퇴하시는 이유를 선택해 주세요.")) {return false;}

        // 기타 사유 선택 시 내용 검사
        if($("#input_enable").is(":checked")) {
            if(!regex.value("#reasonEtc", "기타 사유를 입력해 주세요.")) {return false;}
        }

        var memF = $(this);
        memF.unbind("submit").submit();
    }); // End 회원탈퇴 폼의 Submit Event
}); // End $function() {};
