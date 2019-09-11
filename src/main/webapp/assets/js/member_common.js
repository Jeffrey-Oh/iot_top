$(function() {
    /* 우편번호 및 주소에 포커스가 들어 갔을 경우 */
    $("#postcode, #addr1").focus(function(e) {
        $("#addrSearch").click();
        $("#addr2").focus();
    });

    /** ===== 생년월일 ===== */
    var $year = $("#year"),
        $month = $("#month"),
        $day = $("#day");
    /** 생년월일 - 년 */
    $year.empty();

    var date = new Date(),
        y = date.getFullYear(),

        firstOption = $("<option>--- 년 ---</option>");
    $year.append(firstOption.val(""));
    
    for(var i = y; i >= y - 100; i--){                
        $year.append($('<option />').val(i).html(i));
    }

    /** 생년월일 - 월 */
    $month.empty();

    var firstOption = $("<option>--- 월 ---</option>");
    $month.append(firstOption.val(""));

    for(var i = 1; i <= 12; i++){                
        $month.append($('<option />').val(i).html(i));
    }

    /** 생년월일 - 일 */
    $.fn.updateDay = function(e) {
        $day.empty();

        var firstOption = $("<option>--- 일 ---</option>");
        $day.append(firstOption.val(""));

        month = $month.val();
        year = $year.val();
        days = new Date(year, month, 0).getDate();

        if(month > 0) {
            for(i=1; i < days + 1; i++){
                $day.append($('<option />').val(i).html(i));
            }    
        }
    };

    $day.updateDay();

    $('#year, #month').change(function(e){
        $day.updateDay();
    });
    /** End ===== 생년월일 ===== */
}); // End $function() {};