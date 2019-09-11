$(function() {
    /** 이전단계 버튼 */
    $("#backBtn").click(function(e) {
        history.back();
    });

    /** 전체 동의 체크박스 change Event */
    $(document).on("change", "*[data-all-check]", function() {
        var value = $(this).prop("checked");
        var target = $(this).data("all-check");
        $(target).prop("checked", value);
    }); // End 전체 동의 체크박스 change Event

    /** 체크박스 change Envent로 인한 전체 동의 체크박스점검 */
    $("input[type=checkbox].agreement").change(function() {
        // 전체 동의 체크박스를 했음에도 체크박스 요소들 중 하나라도 풀리면 해제시킨다.
        var isbooleanVal = $(this).prop("checked");

        if(!isbooleanVal) {
            $("#allCheck").prop("checked", isbooleanVal);
        }
    }); // End 체크박스 change Envent로 인한 전체 동의 체크박스점검

    /** 약관동의 정보입력 Submit Event */
    $("#member_join_agreement").submit(function(e) {
        // jQuery에서의 기본 동작 중단 처리
        e.preventDefault();

        // 필수 동의 값 검사
        if(!regex.check("#serviceCheck", "필수 항목을 체크해 주세요.")) {return false;}
        if(!regex.check("#InfoCollectionCheck", "필수 항목을 체크해 주세요.")) {return false;}
        if(!regex.check("#consignmentCheck", "필수 항목을 체크해 주세요.")) {return false;}

        var memF = $(this);
        memF.unbind("submit").submit();
    }); // End 약관동의 정보입력 폼의 Submit Event
}); // End $function() {};