/**
 * HTML 특수문자를 원래의 구문으로 되돌리는 함수
 * ex) &lt;br/&gt; --> <br/>
 *
 * @param {string} str
 * @returns string
 */
function htmlspecialchar_decode(str) {
    // 임의의 HTML 태그를 생성하여 내용을 출력시킨다.
    var div = $("<div>").html(str);
    // 브라우저에 표시될 내용을 리턴한다.
    return div.text();
}

/**
 * 자바스크립트의 alert 함수를 SweetAlert 팝업으로 Override 한다.
 * @param {string} arg1             제목 (필수)
 * @param {string} arg2             내용 (내용이 생략된 경우 제목이 내용으로 사용됨)
 * @param {function} callback       콜백함수
 * @param {string} type             아이콘 타입
 */
window.alert = function (arg1, arg2, callback, type) {
    var title = "알림";
    var message = arg1;

    if (arg2 !== undefined) {
        if (typeof arg2 === "function") {
            type = callback;
            callback = arg2;
        } else {
            title = arg1;
            message = arg2;
        }
    }

    // 아이콘이 지정되지 않은 경우 기본은 info로 설정
    if (type === undefined) {
        type = "info";
    }

    // 줄바꿈 문자를 <br/>태그로 변환.
    message = message.replace(/\\n/gi, "<br/>");
    message = message.replace(/\n/gi, "<br/>");

    var win = swal({
        type: type,
        title: title,
        html: message
    });

    if (callback !== undefined) {
        win.then(result => {
            if (result.value) {
                callback();
            }
        });
    }
};

/**
 * 자바스크립트의 confirm 함수를 SweetAlert 팝업으로 Override 한다.
 * @param {string} arg1             제목 (필수)
 * @param {string} arg2             내용 (내용이 생략된 경우 제목이 내용으로 사용됨)
 * @param {function} callback       콜백함수
 * @param {string} type             아이콘 타입
 */
window.confirm = function (arg1, arg2, callback, type) {
    var title = "확인";
    var message = arg1;

    if (arg2 !== undefined) {
        if (typeof arg2 === "function") {
            type = callback;
            callback = arg2;
        } else {
            title = arg1;
            message = arg2;
        }
    }

    if (type === undefined) {
        type = "question";
    }

    message = message.replace(/\\n/gi, "<br/>");
    message = message.replace(/\n/gi, "<br/>");

    var win = swal({
        type: type,
        title: title,
        html: message,
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33"
    });

    if (callback !== undefined) {
        win.then(result => {
            callback(result.value);
        });
    }
};

/**
 * 팝업창 열기 함수.
 * 합업창을 화면의 중앙에 표시한다.
 *
 * @param {string} page_url  팝업창 표시 URL
 * @param {string} name      팝업창 객체 이름
 * @param {int} w            팝업창 가로 넓이
 * @param {int} h            팝업창 세로 높이
 * @returns 팝업창 객체
 */
function open_popup(page_url, name, w, h) {
    var left = screen.width / 2 - w / 2;
    var top = screen.height / 2 - h / 2 - 50;
    var targetWin = window.open(page_url, name, "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width=" + w + ", height=" + h + ", top=" + top + ", left=" + left);
    return targetWin;
}

/**
 * 지정된 범위 안의 렌덤값을 추출하여 리턴한다.
 *
 * @param {int} n1 최소값
 * @param {int} n2 최대값
 * @returns 렌덤값
 */
function random(n1, n2) {
    return parseInt(Math.random() * (n2 - n1 + 1)) + n1;
}

/**
 * 체크박스의 선택값들을 콤마로 연결된 문자열로 리턴받는다.
 *
 * @param {string} selector 체크박스들에 대한 셀렉터
 * @param {string} delimter 문자열 구분자
 * @returns string
 */
function get_checked_string(selector, delimter) {
    var cnt = $(selector + ":checked").length;

    if (cnt < 1) {
        return "";
    }

    if (delimter == undefined) {
        delimter = ",";
    }

    var checked_value = "";
    $(selector + ":checked").each(function (i) {
        checked_value += $(this).val();

        if (i + 1 < cnt) {
            checked_value += delimter;
        }
    });

    return checked_value;
}

/**
 * 체크박스의 선택된 항목들에 대한 값을 배열로 리턴받는다.
 *
 * @param {string} selector 체크박스들에 대한 셀렉터
 * @returns array
 */
function get_checked_array(selector) {
    var checked_value = [];
    var target = $(selector + ":checked");

    for (var i = 0; i < target.length; i++) {
        checked_value.push(
            $(target)
            .eq(i)
            .val()
        );
    }

    return checked_value;
}

/**
 * 문자열에서 마지막 글자가 쉼표인 경우 제거한다.
 *
 * @param {string} str 원본 문자열
 * @returns
 */
function remove_last_comma(str) {
    var result = str.trim();

    var last_one = result.substring(result.length - 1);
    console.log(last_one);

    if (last_one == ",") {
        result = result.substring(0, result.length - 1);
    }

    return result;
}

/**
 * 다음 우편번호 검색 팝업창을 호출한다.
 * 아래의 스크립트 참조가 선행되어야 한다.
 * http://dmaps.daum.net/map_js_init/postcode.v2.js
 *
 * @param {string} post
 * @param {string} addr1
 * @param {string} addr2
 */
function postcode(post, addr1, addr2) {
    new daum.Postcode({
        oncomplete: function (data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var fullAddr = ""; // 최종 주소 변수
            var extraAddr = ""; // 조합형 주소 변수

            // 사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === "R") {
                // 사용자가 도로명 주소를 선택했을 경우
                fullAddr = data.roadAddress;
            } else {
                // 사용자가 지번 주소를 선택했을 경우(J)
                fullAddr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 조합한다.
            if (data.userSelectedType === "R") {
                //법정동명이 있을 경우 추가한다.
                if (data.bname !== "") {
                    extraAddr += data.bname;
                }
                // 건물명이 있을 경우 추가한다.
                if (data.buildingName !== "") {
                    extraAddr += extraAddr !== "" ? ", " + data.buildingName : data.buildingName;
                }
                // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                fullAddr += extraAddr !== "" ? " (" + extraAddr + ")" : "";
            }

            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            $(post).val(data.zonecode);
            $(addr1).val(fullAddr);
            $(addr2).focus();
        }
    }).open();
}

/* ----------------- Start Document ----------------- */
// jQuery안에 브라우저 정보를 저장할 객체를 삽입한다.
$.browser = {};

$(function () {
    // IE9 이하 버전일 경우 jQuery에게 브라우저 버전을 알려줌.
    jQuery.browser.msie = false;
    $.browser.version = 0;
    if (navigator.userAgent.match(/MSIE ([0-9]+)\./)) {
        $.browser.msie = true;
        jQuery.browser.version = RegExp.$1;
    }

    /**
     * data-postcode 속성을 갖는 요소를 클릭한 경우 우편번호 검색 팝업을 호출한다.
     */
    $(document).on("click", '*[data-postcode]', function () {
        var current = $(this);
        var p = current.data('postcode');
        var a1 = current.data('addr1');
        var a2 = current.data('addr2');

        postcode(p, a1, a2);
    });

    /**
     * data-type="integer" 혹은 data-type="number" 속성을 갖는 input 태그들에 대해 숫자만 입력하도록 제한한다.
     */
    $(document).on("keyup", '*[data-type="integer"],*[data-type="number"]', function () {
        var v = $(this).val();
        var k = v.replace(/[^0-9]/g, "");
        console.log(k);
        $(this).val(k);
    });

    /**
     * data-type="float" 속성을 갖는 input 태그들에 대해 실수 형태의 값만 입력하도록 제한한다.
     */
    $(document).on("keyup", '*[data-type="float"]', function () {
        var v = $(this).val();
        var k = v.replace(/[^.0-9]/g, "");
        console.log(k);
        $(this).val(k);
    });

    /**
     * data-show="셀렉터" 형식의 속성을 갖는 체크박스에 대해 체크박스가 선택되었을 때 대상을 표시하도록 한다.
     */
    $(document).on("change", "*[data-show]", function () {
        var current = $(this);
        var target = current.data("show");

        if (current.is(":checked")) {
            $(target).show();
        }
    });

    /**
     * data-hide="셀렉터" 형식의 속성을 갖는 체크박스에 대해 체크박스가 해제되었을 때 대상을 숨기도록 한다.
     */
    $(document).on("change", "*[data-hide]", function () {
        var current = $(this);
        var target = current.data("hide");

        if (current.is(":checked")) {
            $(target).hide();
        }
    });

    /**
     * data-show-hide="셀렉터" 형식의 속성을 갖는 체크박스에 대해
     * 체크박스가 선택되면 표시, 해제되면 숨기기를 수행한다.
     */
    $(document).on("change", "*[data-show-hide]", function () {
        var current = $(this);
        var target = current.data("show-hide");

        if (current.is(":checked")) {
            $(target).show();
        } else {
            $(target).hide();
        }
    });

    /**
     * 특정 요소를 클릭했을 때 팝업창을 표시하도록 한다.
     * ex) <a href="#" data-popup-url="팝업창 URL"
     *                 data-popup-width="가로넓이" data-popup-height="세로높이"
     *                 data-popup-name="팝업창이름">open</a>
     */
    $(document).on("click", "*[data-popup-url]", function (e) {
        e.preventDefault();
        var url = $(this).data("popup-url");
        var width = $(this).data("popup-width");
        var height = $(this).data("popup-height");
        var name = $(this).data("popup-name");

        if ((name = undefined)) {
            name = "";
        }

        if (width == undefined) {
            width = 800;
        }

        if (height == undefined) {
            height = 600;
        }

        open_popup(url, name, width, height);
    });

    /**
     * data-role="close" 속성을 갖는 요소를 클릭했을 때 현재 창을 닫도록 한다.
     */
    $("*[data-role='close']").click(function (e) {
        e.preventDefault();
        self.close();
    });

    /**
     * data-role="back"이라는 속성을 갖는 모든 요소에 대해서 뒤로가기 기능 부여
     */
    $("*[data-role='back']").click(function (e) {
        e.preventDefault();
        history.back();
    });

    /**
     * data-clipboard="셀렉터" 속성을 갖는 요소 내의 텍스트들을 클립보드에 복사한다.
     * 만약 HTML 태그가 섞여 있다면 태그는 제외하고 문자열만 복사한다.
     */
    $(document).on("click", "*[data-clipboard]", function () {
        var target = $($(this).data("clipboard"));
        target.select();
        document.execCommand("Copy");
    });

    /**
     * data-role='print' 라는 속성을 갖는 요소를 클릭했을 때, 현재 웹 페이지를 프린트한다.
     */
    $("*[data-role='print']").click(function (e) {
        e.preventDefault();
        window.print();
    });

    /**
     * <input type="file" accept="image/*"  data-preview="img태그에 대한 selector" />
     * 형식으로 지정할 경우 첨부된 이미지 파일을 지정된 <img>요소에서 미리 볼 수 있도록 한다.
     */
    $(document).on("change", "input[type='file'][data-preview]", function (e) {
        var input = $(this);

        // accept 속성이 없다면 강제로 부여.
        var accept = input.attr("accept");
        if (accept == undefined || accept.indexOf("image") == -1) {
            input.attr("accept", "image/*");
        }

        var target_id = input.data("preview");

        if (e.target != undefined && e.target.files[0]) {
            var src = URL.createObjectURL(e.target.files[0]);
            $(target_id).attr("src", src).show();
        } else {
            $(target_id).attr("src", "").hide();
        }
    });

    /**
     * Ajax 기본 설정
     */
    $.ajaxSetup({
        /** ajax 기본 옵션 */
        cache: false, // 캐쉬 사용 금지 처리
        dataType: "json", // 읽어올 내용 형식 (html,xml,json)
        timeout: 30000, // 타임아웃 (30초)
        crossDomain: true,

        // 통신 시작전 실행할 기능 (ex: 로딩바 표시)
        beforeSend: function () {
            // 현재 통신중인 대상 페이지를 로그로 출력함
            // console.log(">> Ajax 통신 시작 >> " + this.url);
            loader.show();
        },
        // 통신 실패시 호출될 함수 (파라미터는 에러내용)
        error: function (error) {
            console.error("[" + error.status + "] " + error.statusText);
            var text = error.responseText;

            // 백엔드에서 전달하는 내용을 JSONObject로 변환한다.
            var json = null;
            var p = text.indexOf("{");

            if (p > -1) {
                try {
                    json = JSON.parse(text.substring(p));
                } catch (e) {}
            }

            var error_msg = false;
            var trace = false;

            // json데이터에 state값이 있는지 검사
            if (json !== null && json.rtmsg !== undefined) {
                error_msg = json.rtmsg;
                if (json.trace !== undefined) {
                    trace = json.trace;
                }
            } else {
                var code = parseInt(error.status / 100);
                if (code == 4) {
                    // 400번대의 에러인 경우
                    error_msg = "잘못된 요청입니다.\n" + error_msg;
                } else if (code == 5) {
                    error_msg = "서버의 응답이 없습니다.\n" + error_msg;
                } else if (code == 2 || code == 0) {
                    error_msg = "서버의 응답이 잘못되었습니다.\n" + error_msg;
                }

                trace = this.url;
            }

            alert(error_msg, function () {}, "error");

            console.error(error_msg);
            console.error(trace);
        },
        // 성공,실패에 상관 없이 맨 마지막에 무조건 호출됨 ex) 로딩바 닫기
        complete: function () {
            // console.log(">> Ajax 통신 종료!!!!");
            loader.hide();
        }
    });

    // jQuery Validation 플러그인을 사용 중이라면?
    if ($.validator !== undefined) {
        /**
         * jQuery Validation Plugin 기본 설정 --> Alert메시지 창을 사용하도록 구성함
         */
        $.validator.setDefaults({
            onkeyup: false,
            onclick: false,
            onfocusout: false,
            focusInvalid: false,
            onblur: false,
            showErrors: function (errorMap, errorList) {
                console.log(errorMap);
                if (this.numberOfInvalids()) {
                    if (errorList.length > 0) {
                        var caption = $(errorList[0].element).attr("caption") || $(errorList[0].element).attr("name");
                        console.log("[form-helper] >> " + caption + " >> " + errorList[0].message);
                        alert("알림", errorList[0].message, function() {
                        	setTimeout(function() {
                        		$(errorList[0].element).focus();
                        	}, 100);
                        }, 'error');
                    }
                }
            },
            ignore: "*:not([name]),:hidden:not(.post-content),.note-codable"
        });

        /**
         * 파일크기가 일정 용량 이상될 경우 업로드 못하게 하는 규칙 추가
         */
        $.validator.addMethod("filesize", function (value, element, param) {
            console.log(value);
            return this.optional(element) || element.files[0].size <= param;
        });
    }

    /**
     * AjaxHelper를 위한 Loading Div 추가하기
     */
    var loader = $("<div>").css({
        display: "block",
        width: "20px",
        height: "20px",
        position: "fixed",
        left: "50%",
        top: "50%",
        "margin-top": "-10px",
        "margin-left": "-10px",
        "z-index": "9999999",
        "background-size": "20px 20px",
        "background-repeat": "no-repeat",
        // 이미지는 base64 인코딩으로 처리했으므로 별도의 css나 이미지 파일 필요 없음.
        "background-image": "url('data:image/gif;base64,R0lGODlhIAAgAPMAAP///wAAAMbGxoSEhLa2tpqamjY2NlZWVtjY2OTk5Ly8vB4eHgQEBAAAAAAAAAAAACH/C05FVFNDQVBFMi4wAwEAAAAh/hpDcmVhdGVkIHdpdGggYWpheGxvYWQuaW5mbwAh+QQJCgAAACwAAAAAIAAgAAAE5xDISWlhperN52JLhSSdRgwVo1ICQZRUsiwHpTJT4iowNS8vyW2icCF6k8HMMBkCEDskxTBDAZwuAkkqIfxIQyhBQBFvAQSDITM5VDW6XNE4KagNh6Bgwe60smQUB3d4Rz1ZBApnFASDd0hihh12BkE9kjAJVlycXIg7CQIFA6SlnJ87paqbSKiKoqusnbMdmDC2tXQlkUhziYtyWTxIfy6BE8WJt5YJvpJivxNaGmLHT0VnOgSYf0dZXS7APdpB309RnHOG5gDqXGLDaC457D1zZ/V/nmOM82XiHRLYKhKP1oZmADdEAAAh+QQJCgAAACwAAAAAIAAgAAAE6hDISWlZpOrNp1lGNRSdRpDUolIGw5RUYhhHukqFu8DsrEyqnWThGvAmhVlteBvojpTDDBUEIFwMFBRAmBkSgOrBFZogCASwBDEY/CZSg7GSE0gSCjQBMVG023xWBhklAnoEdhQEfyNqMIcKjhRsjEdnezB+A4k8gTwJhFuiW4dokXiloUepBAp5qaKpp6+Ho7aWW54wl7obvEe0kRuoplCGepwSx2jJvqHEmGt6whJpGpfJCHmOoNHKaHx61WiSR92E4lbFoq+B6QDtuetcaBPnW6+O7wDHpIiK9SaVK5GgV543tzjgGcghAgAh+QQJCgAAACwAAAAAIAAgAAAE7hDISSkxpOrN5zFHNWRdhSiVoVLHspRUMoyUakyEe8PTPCATW9A14E0UvuAKMNAZKYUZCiBMuBakSQKG8G2FzUWox2AUtAQFcBKlVQoLgQReZhQlCIJesQXI5B0CBnUMOxMCenoCfTCEWBsJColTMANldx15BGs8B5wlCZ9Po6OJkwmRpnqkqnuSrayqfKmqpLajoiW5HJq7FL1Gr2mMMcKUMIiJgIemy7xZtJsTmsM4xHiKv5KMCXqfyUCJEonXPN2rAOIAmsfB3uPoAK++G+w48edZPK+M6hLJpQg484enXIdQFSS1u6UhksENEQAAIfkECQoAAAAsAAAAACAAIAAABOcQyEmpGKLqzWcZRVUQnZYg1aBSh2GUVEIQ2aQOE+G+cD4ntpWkZQj1JIiZIogDFFyHI0UxQwFugMSOFIPJftfVAEoZLBbcLEFhlQiqGp1Vd140AUklUN3eCA51C1EWMzMCezCBBmkxVIVHBWd3HHl9JQOIJSdSnJ0TDKChCwUJjoWMPaGqDKannasMo6WnM562R5YluZRwur0wpgqZE7NKUm+FNRPIhjBJxKZteWuIBMN4zRMIVIhffcgojwCF117i4nlLnY5ztRLsnOk+aV+oJY7V7m76PdkS4trKcdg0Zc0tTcKkRAAAIfkECQoAAAAsAAAAACAAIAAABO4QyEkpKqjqzScpRaVkXZWQEximw1BSCUEIlDohrft6cpKCk5xid5MNJTaAIkekKGQkWyKHkvhKsR7ARmitkAYDYRIbUQRQjWBwJRzChi9CRlBcY1UN4g0/VNB0AlcvcAYHRyZPdEQFYV8ccwR5HWxEJ02YmRMLnJ1xCYp0Y5idpQuhopmmC2KgojKasUQDk5BNAwwMOh2RtRq5uQuPZKGIJQIGwAwGf6I0JXMpC8C7kXWDBINFMxS4DKMAWVWAGYsAdNqW5uaRxkSKJOZKaU3tPOBZ4DuK2LATgJhkPJMgTwKCdFjyPHEnKxFCDhEAACH5BAkKAAAALAAAAAAgACAAAATzEMhJaVKp6s2nIkolIJ2WkBShpkVRWqqQrhLSEu9MZJKK9y1ZrqYK9WiClmvoUaF8gIQSNeF1Er4MNFn4SRSDARWroAIETg1iVwuHjYB1kYc1mwruwXKC9gmsJXliGxc+XiUCby9ydh1sOSdMkpMTBpaXBzsfhoc5l58Gm5yToAaZhaOUqjkDgCWNHAULCwOLaTmzswadEqggQwgHuQsHIoZCHQMMQgQGubVEcxOPFAcMDAYUA85eWARmfSRQCdcMe0zeP1AAygwLlJtPNAAL19DARdPzBOWSm1brJBi45soRAWQAAkrQIykShQ9wVhHCwCQCACH5BAkKAAAALAAAAAAgACAAAATrEMhJaVKp6s2nIkqFZF2VIBWhUsJaTokqUCoBq+E71SRQeyqUToLA7VxF0JDyIQh/MVVPMt1ECZlfcjZJ9mIKoaTl1MRIl5o4CUKXOwmyrCInCKqcWtvadL2SYhyASyNDJ0uIiRMDjI0Fd30/iI2UA5GSS5UDj2l6NoqgOgN4gksEBgYFf0FDqKgHnyZ9OX8HrgYHdHpcHQULXAS2qKpENRg7eAMLC7kTBaixUYFkKAzWAAnLC7FLVxLWDBLKCwaKTULgEwbLA4hJtOkSBNqITT3xEgfLpBtzE/jiuL04RGEBgwWhShRgQExHBAAh+QQJCgAAACwAAAAAIAAgAAAE7xDISWlSqerNpyJKhWRdlSAVoVLCWk6JKlAqAavhO9UkUHsqlE6CwO1cRdCQ8iEIfzFVTzLdRAmZX3I2SfZiCqGk5dTESJeaOAlClzsJsqwiJwiqnFrb2nS9kmIcgEsjQydLiIlHehhpejaIjzh9eomSjZR+ipslWIRLAgMDOR2DOqKogTB9pCUJBagDBXR6XB0EBkIIsaRsGGMMAxoDBgYHTKJiUYEGDAzHC9EACcUGkIgFzgwZ0QsSBcXHiQvOwgDdEwfFs0sDzt4S6BK4xYjkDOzn0unFeBzOBijIm1Dgmg5YFQwsCMjp1oJ8LyIAACH5BAkKAAAALAAAAAAgACAAAATwEMhJaVKp6s2nIkqFZF2VIBWhUsJaTokqUCoBq+E71SRQeyqUToLA7VxF0JDyIQh/MVVPMt1ECZlfcjZJ9mIKoaTl1MRIl5o4CUKXOwmyrCInCKqcWtvadL2SYhyASyNDJ0uIiUd6GGl6NoiPOH16iZKNlH6KmyWFOggHhEEvAwwMA0N9GBsEC6amhnVcEwavDAazGwIDaH1ipaYLBUTCGgQDA8NdHz0FpqgTBwsLqAbWAAnIA4FWKdMLGdYGEgraigbT0OITBcg5QwPT4xLrROZL6AuQAPUS7bxLpoWidY0JtxLHKhwwMJBTHgPKdEQAACH5BAkKAAAALAAAAAAgACAAAATrEMhJaVKp6s2nIkqFZF2VIBWhUsJaTokqUCoBq+E71SRQeyqUToLA7VxF0JDyIQh/MVVPMt1ECZlfcjZJ9mIKoaTl1MRIl5o4CUKXOwmyrCInCKqcWtvadL2SYhyASyNDJ0uIiUd6GAULDJCRiXo1CpGXDJOUjY+Yip9DhToJA4RBLwMLCwVDfRgbBAaqqoZ1XBMHswsHtxtFaH1iqaoGNgAIxRpbFAgfPQSqpbgGBqUD1wBXeCYp1AYZ19JJOYgH1KwA4UBvQwXUBxPqVD9L3sbp2BNk2xvvFPJd+MFCN6HAAIKgNggY0KtEBAAh+QQJCgAAACwAAAAAIAAgAAAE6BDISWlSqerNpyJKhWRdlSAVoVLCWk6JKlAqAavhO9UkUHsqlE6CwO1cRdCQ8iEIfzFVTzLdRAmZX3I2SfYIDMaAFdTESJeaEDAIMxYFqrOUaNW4E4ObYcCXaiBVEgULe0NJaxxtYksjh2NLkZISgDgJhHthkpU4mW6blRiYmZOlh4JWkDqILwUGBnE6TYEbCgevr0N1gH4At7gHiRpFaLNrrq8HNgAJA70AWxQIH1+vsYMDAzZQPC9VCNkDWUhGkuE5PxJNwiUK4UfLzOlD4WvzAHaoG9nxPi5d+jYUqfAhhykOFwJWiAAAIfkECQoAAAAsAAAAACAAIAAABPAQyElpUqnqzaciSoVkXVUMFaFSwlpOCcMYlErAavhOMnNLNo8KsZsMZItJEIDIFSkLGQoQTNhIsFehRww2CQLKF0tYGKYSg+ygsZIuNqJksKgbfgIGepNo2cIUB3V1B3IvNiBYNQaDSTtfhhx0CwVPI0UJe0+bm4g5VgcGoqOcnjmjqDSdnhgEoamcsZuXO1aWQy8KAwOAuTYYGwi7w5h+Kr0SJ8MFihpNbx+4Erq7BYBuzsdiH1jCAzoSfl0rVirNbRXlBBlLX+BP0XJLAPGzTkAuAOqb0WT5AH7OcdCm5B8TgRwSRKIHQtaLCwg1RAAAOwAAAAAAAAAAADxiciAvPgo8Yj5XYXJuaW5nPC9iPjogIG15c3FsX3F1ZXJ5KCkgWzxhIGhyZWY9J2Z1bmN0aW9uLm15c3FsLXF1ZXJ5Jz5mdW5jdGlvbi5teXNxbC1xdWVyeTwvYT5dOiBDYW4ndCBjb25uZWN0IHRvIGxvY2FsIE15U1FMIHNlcnZlciB0aHJvdWdoIHNvY2tldCAnL3Zhci9ydW4vbXlzcWxkL215c3FsZC5zb2NrJyAoMikgaW4gPGI+L2hvbWUvYWpheGxvYWQvd3d3L2xpYnJhaXJpZXMvY2xhc3MubXlzcWwucGhwPC9iPiBvbiBsaW5lIDxiPjY4PC9iPjxiciAvPgo8YnIgLz4KPGI+V2FybmluZzwvYj46ICBteXNxbF9xdWVyeSgpIFs8YSBocmVmPSdmdW5jdGlvbi5teXNxbC1xdWVyeSc+ZnVuY3Rpb24ubXlzcWwtcXVlcnk8L2E+XTogQSBsaW5rIHRvIHRoZSBzZXJ2ZXIgY291bGQgbm90IGJlIGVzdGFibGlzaGVkIGluIDxiPi9ob21lL2FqYXhsb2FkL3d3dy9saWJyYWlyaWVzL2NsYXNzLm15c3FsLnBocDwvYj4gb24gbGluZSA8Yj42ODwvYj48YnIgLz4KPGJyIC8+CjxiPldhcm5pbmc8L2I+OiAgbXlzcWxfcXVlcnkoKSBbPGEgaHJlZj0nZnVuY3Rpb24ubXlzcWwtcXVlcnknPmZ1bmN0aW9uLm15c3FsLXF1ZXJ5PC9hPl06IENhbid0IGNvbm5lY3QgdG8gbG9jYWwgTXlTUUwgc2VydmVyIHRocm91Z2ggc29ja2V0ICcvdmFyL3J1bi9teXNxbGQvbXlzcWxkLnNvY2snICgyKSBpbiA8Yj4vaG9tZS9hamF4bG9hZC93d3cvbGlicmFpcmllcy9jbGFzcy5teXNxbC5waHA8L2I+IG9uIGxpbmUgPGI+Njg8L2I+PGJyIC8+CjxiciAvPgo8Yj5XYXJuaW5nPC9iPjogIG15c3FsX3F1ZXJ5KCkgWzxhIGhyZWY9J2Z1bmN0aW9uLm15c3FsLXF1ZXJ5Jz5mdW5jdGlvbi5teXNxbC1xdWVyeTwvYT5dOiBBIGxpbmsgdG8gdGhlIHNlcnZlciBjb3VsZCBub3QgYmUgZXN0YWJsaXNoZWQgaW4gPGI+L2hvbWUvYWpheGxvYWQvd3d3L2xpYnJhaXJpZXMvY2xhc3MubXlzcWwucGhwPC9iPiBvbiBsaW5lIDxiPjY4PC9iPjxiciAvPgo8YnIgLz4KPGI+V2FybmluZzwvYj46ICBteXNxbF9xdWVyeSgpIFs8YSBocmVmPSdmdW5jdGlvbi5teXNxbC1xdWVyeSc+ZnVuY3Rpb24ubXlzcWwtcXVlcnk8L2E+XTogQ2FuJ3QgY29ubmVjdCB0byBsb2NhbCBNeVNRTCBzZXJ2ZXIgdGhyb3VnaCBzb2NrZXQgJy92YXIvcnVuL215c3FsZC9teXNxbGQuc29jaycgKDIpIGluIDxiPi9ob21lL2FqYXhsb2FkL3d3dy9saWJyYWlyaWVzL2NsYXNzLm15c3FsLnBocDwvYj4gb24gbGluZSA8Yj42ODwvYj48YnIgLz4KPGJyIC8+CjxiPldhcm5pbmc8L2I+OiAgbXlzcWxfcXVlcnkoKSBbPGEgaHJlZj0nZnVuY3Rpb24ubXlzcWwtcXVlcnknPmZ1bmN0aW9uLm15c3FsLXF1ZXJ5PC9hPl06IEEgbGluayB0byB0aGUgc2VydmVyIGNvdWxkIG5vdCBiZSBlc3RhYmxpc2hlZCBpbiA8Yj4vaG9tZS9hamF4bG9hZC93d3cvbGlicmFpcmllcy9jbGFzcy5teXNxbC5waHA8L2I+IG9uIGxpbmUgPGI+Njg8L2I+PGJyIC8+Cg==')"
    });
    loader.hide();
    $("body").prepend(loader);
});

/**
 * 폼 헬퍼 (ajaxForm + jQuery.Validation)
 */
$.fn.form_helper = function (option) {
    var validate_option = {};
    var is_validate = false;
    var is_submit = true;
    var method = $(this).attr("method");

    if (method === undefined) {
        method = "get";
    } else {
        method = method.toLowerCase();
    }

    if (method == "put" || method == "delete") {
        is_submit = false;
        validate_option.submitHandler = function (form) {
            $.ajax({
                method: method,
                url: $(form).attr("action"),
                data: $(form).serialize(),
                success: option.success
            });
        };
    }

    if (option.debug != undefined) {
        validate_option.debug = option.debug;
        is_submit = false;
    }

    if (option.messages != undefined) {
        validate_option.messages = option.messages;
    }

    if (option.rules != undefined) {
        validate_option.rules = option.rules;
        this.validate(validate_option);
        is_validate = true;
    }

    var form_option = {};
    var current = this;

    form_option.beforeSubmit = function (arr, $form, options) {
        if (is_validate == true) {
            //console.log("helper >> before submit called");
            var check = current.valid();
            //console.log('check=' + check);
            if (check == true && option.beforeSubmit != undefined) {
                if (option.beforeSubmit(arr, $form, options) === false) {
                    return false;
                }
            }

            return check;
        } else {
            if (option.beforeSubmit != undefined) {
                return option.beforeSubmit(arr, $form, options);
            }
        }

        return true;
    };

    form_option.beforeSerialize = function (form, options) {
        /* Before serialize */
        try {
            for (var instance in CKEDITOR.instances) {
                CKEDITOR.instances[instance].updateElement();
            }
        } catch (e) {}
        return true;
    };

    if (option.success != undefined && is_submit == true) {
        form_option.success = option.success;
        this.ajaxForm(form_option);
    }
};