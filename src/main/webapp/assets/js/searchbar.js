$(function() {
    $("#category").change(function(e) {
        var categoryVal = $(this).val();
        var $search_input_div = $("#search_input_div");

        if(categoryVal == "tour") {
            /** 선택 값이 관광일 경우 */
            // 초기화
            $search_input_div.empty();

            // 드롭박스 생성
            var tourSelect = $("<select name='keyword' id='tourSelect' class='form-control col-md-7' title='지역 선택' />");
            $search_input_div.append(tourSelect);
            var $tourSelect = $("#tourSelect");
            // 옵션 생성
            var firstOption = $("<option>지역을 선택해 주세요.</option>");
            $tourSelect.append(firstOption.val(""));
            var tourOptionArray = [
                [
                    1, 2, 32, 6, 5, 34, 33, 3,
                    36, 35, 38, 39
                ],
                [
                    "서울", "인천", "강원", "부산", "광주", "충남", "충북",
                    "대전", "경상남도", "경상북도", "전라도", "제주도"
                ]
            ];
            for(var i = 0; i < tourOptionArray[0].length; i++) {
                $tourSelect.append(
                    $("<option />").val(
                        tourOptionArray[0][i]
                    ).html(
                        tourOptionArray[1][i]
                    )
                );
            } // End for

            // action 경로 변경
            $("#fast_search_from").attr("action", ROOT_URL + "/tour/TourList2.do");
        } else if(categoryVal == "air") {
            /** 선택 값이 항공일 경우 */
            // 초기화
            $search_input_div.empty();
            
            var airHidden = $("<input type='hidden' name='tab' value='round' />");
            $search_input_div.append(airHidden);
            
            // 드롭박스 생성
            var boardingKorSelect = $("<select name='boardingKor' id='boardingKor' class='air_search_select form-control col-md-7' title='출발지 선택' />");
            $search_input_div.append(boardingKorSelect);
            var $boardingKor = $("#boardingKor");
            // 옵션 생성
            var firstOption = $("<option>출발지를 선택해 주세요.</option>");
            $boardingKor.append(firstOption.val(""));
            // 드롭박스 생성
            var arrivedKorSelect = $("<select name='arrivedKor' id='arrivedKor' class='air_search_select form-control col-md-7' title='도착지 선택'  />");
            $search_input_div.append(arrivedKorSelect);
            var $arrivedKor = $("#arrivedKor");
            // 옵션 생성
            var firstOption = $("<option>도착지를 선택해 주세요.</option>");
            $arrivedKor.append(firstOption);
            var airOptionArray = [
                "서울/김포", "부산/김해", "제주", "광주", "울산",
                "포항", "진주", "여수", "무안", "대구"
            ];
            for(var i = 0; i < airOptionArray.length; i++) {
                $boardingKor.append(
                    $("<option />").val(
                        airOptionArray[i]
                    ).html(
                        airOptionArray[i]
                    )
                );
                $arrivedKor.append(
                    $("<option />").val(
                        airOptionArray[i]
                    ).html(
                        airOptionArray[i]
                    )
                );
            } // End for
            
            // action 경로 변경
            $("#fast_search_from").attr("action", ROOT_URL + "/AirSearch.do");
        } else if(categoryVal == "servicearea") {
            /** 선택 값이 휴게소일 경우 */
            // 초기화
            $search_input_div.empty();

            // 입력창 생성
            var serviceareaInput = $("<input type='text' class='form-control col-md-7' title='검색어 입력' name='search_input' placeholder='검색어를 입력해주세요.' />");
            $search_input_div.append(serviceareaInput);

            // action 경로 변경
            $("#fast_search_from").attr("action", ROOT_URL + "/servicearea/servicearea_search.do");
        } else if(categoryVal == "culture") {
            /** 선택 값이 문화일 경우 */
            // 초기화
            $search_input_div.empty();

            // 입력창 생성
            var cultureInput = $("<input type='text' class='form-control col-md-7' title='검색어 입력' name='keyword' placeholder='검색어를 입력해주세요.' />");
            $search_input_div.append(cultureInput);

            var cultureHidden = $("<input type='hidden' name='condition' value='titleContent' />");
            $search_input_div.append(cultureHidden);

            // action 경로 변경
            $("#fast_search_from").attr("action", ROOT_URL + "/culture/culturePerformance.do");
        } else if(categoryVal == "community") {
            /** 선택 값이 커뮤니티일 경우 */
            // 초기화
            $search_input_div.empty();

            // 입력창 생성
            var communityInput = $("<input type='text' class='form-control col-md-7' title='검색어 입력' name='search-word' placeholder='검색어를 입력해주세요.' />");
            $search_input_div.append(communityInput);

            // action 경로 변경
            $("#fast_search_from").attr("action", ROOT_URL + "/CommIndex.do");
        } else {
            /** 선택 값이 카테고리일 경우 */
            // 초기화
            $search_input_div.empty();

            // 입력창 생성
            var categoryInput = $("<input type='text' class='form-control col-md-7' title='검색어 입력' name='search_input' placeholder='카테고리를 선택해주세요.' readonly />");
            $search_input_div.append(categoryInput);

            // action 경로 변경
            $("#fast_search_from").attr("action", "#");
        } // End if~else if~else
    }); // End change Event

    /** 빠른검색 폼의 정보입력 Submit Event */
    $("#fast_search_from").submit(function(e) {
        // jQuery에서의 기본 동작 중단 처리
        e.preventDefault();

        /** 유효성 검사 */
        // 카테고리 선택 검사
        if(!regex.select("#category", "카테고리를 선택해 주세요.")) {return false;}
        var categoryVal = $("#category").val();
        if(categoryVal == "tour") {
            /** 선택 값이 관광일 경우 */
            // 지역 선택 검사
            if(!regex.select("#tourSelect", "지역을 선택해 주세요.")) {return false;}
        } else if(categoryVal == "air") {
            /** 선택 값이 항공일 경우 */
            // 출발지와 도착지 선택 검사
            if(!regex.select("#boardingKor", "출발지를 선택해 주세요.")) {return false;}
            if(!regex.select("#arrivedKor", "도착지를 선택해 주세요.")) {return false;}
            var startIdx = $("#boardingKor>option:selected").index();
            var goalIdx = $("#arrivedKor>option:selected").index();
            if(startIdx == goalIdx) {
                alert("출발지와 도착지가 같아선 안됩니다.", undefined, undefined, "warning");
                return false;
            }
        } // End if~else if

        // category 값 삭제 후 전송
        $("#category_div").empty();

        var searchF = $(this);
        searchF.unbind("submit").submit();
    }); // End 빠른검색 폼의 정보입력 Submit Event
}); // End $(function() {})
