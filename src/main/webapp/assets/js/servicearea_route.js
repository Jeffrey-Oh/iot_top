$(function() {
    /** onload 시 이름 입력 항목에 자동커서 */
    $("#startLoc").focus();

    /** 경로탐색 폼의 정보입력 Submit Event */
    $("#servicearea_route_form").submit(function(e) {
        // jQuery에서의 기본 동작 중단 처리
        e.preventDefault();

        // 도착지 값 검사
        if(!regex.select("#goal", "도착지를 선택해 주세요.")) {return false;}

        // 출발지 값 검사
        var start = $("#start").val();
        var goal = $("#goal").val();
        var startIdx = $("#start>option:selected").index();
        var goalIdx = $("#goal>option:selected").index();
        if(startIdx == goalIdx) {
            alert("출발지와 도착지가 같아선 안됩니다.", undefined, undefined, "warning");
            return false;
        }
        if(startIdx == 0) {
            /** ===== geolocation api ===== */
            navigator.geolocation.watchPosition(function(position) {
                do_something(position.coords.latitude, position.coords.longitude);
            }, function() {
                do_something(37.4923615, 127.02928809999999);
            }, {
                enableHighAccuracy: true,
                maximumAge        : 30000,
                timeout           : 27000
            });

            function do_something(lat, lng) {
                location.href = ROOT_URL + "/servicearea/servicearea_route_result.do?start=" + lng + "," + lat + "&goal=" + goal;
            } // End function do_someting();
        } else {
            location.href = ROOT_URL + "/servicearea/servicearea_route_result.do?start=" + start + "&goal=" + goal;
        } // End if~else
    }); // End 경로탐색 폼의 정보입력 Submit Event
}); // End $function() {};

