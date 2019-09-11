$(function() {
    // 이미지가 등록된 상태이므로, 파일의 신규 등록을 방지
    $("#profileImg").prop("disabled", true);
    $("#img_del").change(function(e) {
        $("#profileImg").prop("disabled", !$(this).is(":checked"));
    }); // End change Event
});