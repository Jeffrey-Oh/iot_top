$(function() {
    function getPolicy() {
        var regDate_val = $("#regDate").val();

        $.get(ROOT_URL + "/member/member_policy_select.do", {
            policyId: regDate_val
        }, function(json) {
            console.log(json);
            /**
             * ===== 정책안내 출력하기 =====
             * - 데이터를 받아온 경우 JSON 안에 포함된 배열의 항목 수 만큼 줄바꿈 문자를 처리
             * - 템플릿과 결합하여 덧글 목록을 위한 HTML 요소에 출력한다.
             */
            if(json.rt != "OK") {
                alert("" + json.rt, undefined, undefined, "warning");
                return false;
            }
            // 초기화
            $(".tab-content").empty();
            // 내용에 추가하기 전 활성화 초기화
            $("#loadActive_ul li").not("#loadActive").removeClass("active");

            // JSON에 포함된 "&lt;br/&gt;"을 검색해서 <br/>로 변경함.
            // -> 정규표현식 /~~~/g는 문자열 전체의 의미.
            json.item.agreementDoc = json.item.agreementDoc.replace(/&lt;br\/&gt;/g, "<br />");
            json.item.infoCollectionDoc = json.item.infoCollectionDoc.replace(/&lt;br\/&gt;/g, "<br />");
            json.item.communityDoc = json.item.communityDoc.replace(/&lt;br\/&gt;/g, "<br />");
            json.item.emailCollectionDoc = json.item.emailCollectionDoc.replace(/&lt;br\/&gt;/g, "<br />");

            // 템플릿 HTML을 로드한다.
            var template = Handlebars.compile($("#tmpl_policy_item").html());
            // JSON에 포함된 작성 결과 데이터를 템플릿에 결합한다.
            var html = template(json.item);
            // 결합된 결과를 내용에 추가한다.
            $(".tab-content").append(html);
            // 새롭게 추가된 내용을 위해 active 생성
            $("#loadActive").addClass("active");
            $("#agreementDoc").addClass("active");

            /**
             * 수정, 삭제시에는 상세 조회와 마찬가지로 대상을 식별해야 하기 때문에,
             * 정책안내의 일련번호(Primary Key)를 GET 파라미터로 전달한다.
             */
            $("#policy_update_btn").attr("href", ROOT_URL + "/member/member_policy_update.do?policyId=" + json.item.policyId);
            $("#policy_delete_btn").attr("href", ROOT_URL + "/member/member_policy_delete.do?policyId=" + json.item.policyId);
        }); // End $.get Ajax
    } // End getPolicy Function

    // 페이지 로딩 시 바로 내용 출력
    getPolicy();

    // 정책 안내 날짜를 선택하여 바뀌었을 경우
    $(document).on("change", "#regDate", function(e) {
        // 내용 출력
        getPolicy();
    }); // End #agreementDate change Event

});
