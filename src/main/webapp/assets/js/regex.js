/**
 * 입력값 검사를 수행하기 위한 객체 (라이브러리)
 */
var regex = {
    /**
     * 입력 값의 존재 여부를 검사한다.
     * @param  {string} selector 입력요소의 css셀렉터
     * @param  {string} msg      값이 없을 경우 표시될 메시지
     * @return {boolean} 입력된 경우 true / 입력되지 않은 경우 false
     */
    value: function(selector, msg) {
        // 대상 요소를 가져온다.
        var target = $(selector);

        // 값이 없다면?
        if(!target.val()) {
            // 메시지 표시
            alert(msg, undefined, function() {
                setTimeout(function() {
                    // 대상요소에게 포커스 강제 지정
                    target.focus();
                }, 100)
            }, "warning");

            // 실패했음을 리턴
            return false;
        }

        // 성공했음을 리턴
        return true;
    },

    /**
     * 입력 값이 지정된 글자수를 초과했는지 검사한다.
     * @param  {string} selector 입력요소의 css셀렉터
     * @param  {int}    len      최대 글자 수
     * @param  {string} msg      값이 클 경우 표시될 메시지
     * @return {boolean} 지정된 글자수 초과하지 않은 경우 true / 초과한 경우 false
     */
    max_length: function(selector, len, msg) {
        // 대상 요소를 가져온다.
        var target = $(selector);

        // 입력값이 주어진 길이보다 크다면?
        if(target.val().length > len) {
            // 메시지 표시
            alert(msg, undefined, function() {
                setTimeout(function() {
                    // 입력값을 강제로 지운다.
                    target.val("");
                    // 대상요소에게 포커스 강제 지정
                    target.focus();
                }, 100)
            }, "warning");

            // 실패했음을 리턴
            return false;
        }

        // 성공했음을 리턴
        return true;
    },

    /**
     * 입력 값이 지정된 글자수 미만인지 검사한다.
     * @param  {string} selector 입력요소의 css셀렉터
     * @param  {int}    len      최소 글자 수
     * @param  {string} msg      값이 작을 경우 표시될 메시지
     * @return {boolean} 지정된 글자수 이상인 경우 true / 미만인 경우 false
     */
    min_length: function(selector, len, msg) {
        // 대상 요소를 가져온다.
        var target = $(selector);

        // 입력값이 주어진 길이보다 작다면?
        if(target.val().length < len) {
            // 메시지 표시
            alert(msg, undefined, function() {
                setTimeout(function() {
                    // 입력값을 강제로 지운다.
                    target.val("");
                    // 대상요소에게 포커스 강제 지정
                    target.focus();
                }, 100)
            }, "warning");

            // 실패했음을 리턴
            return false;
        }

        // 성공했음을 리턴
        return true;
    },

    /**
     * 셀렉트(드롭다운)이 선택되어 있는지 여부를 검사한다.
     * @param  {string} selector 선택요소의 css셀렉터
     * @param  {string} msg      값이 없을 경우 표시될 메시지
     * @return {boolean} 체크된 경우 true / 체크되지 않은 경우 false
     */
    select: function(selector, msg) {
        // 대상 요소를 가져온다.
        var target = $(selector + " > option:selected").index();

        // 선택값이 없다면?
        if(target < 1) {
            // 메시지 표시
            alert(msg, undefined, function() {
                setTimeout(function() {
                    // 첫 번째 요소에게 포커스 강제 지정
                    $(selector).eq(0).focus();
                }, 100)
            }, "warning");

            // 실패했음을 리턴
            return false;
        }

        // 성공했음을 리턴
        return true;
    },

    /**
     * 체크박스나 라디오가 선택되어 있는지 여부를 검사한다.
     * @param  {string} selector 입력요소의 css셀렉터
     * @param  {string} msg      값이 없을 경우 표시될 메시지
     * @return {boolean} 체크된 경우 true / 체크되지 않은 경우 false
     */
    check: function(selector, msg) {
        // 대상 요소 중에서 선택된 항목들만 가져온다.
        var checked = $(selector + ":checked");

        // 선택된 항목의 길이가 1 미만이라면?
        if(checked.length < 1) {
            // 메시지 표시
            alert(msg, undefined, function() {
                setTimeout(function() {
                    // 첫 번째 요소에게 포커스 강제 지정
                    $(selector).eq(0).focus();
                }, 100)
            }, "warning");

            // 실패했음을 리턴
            return false;
        }

        // 성공했음을 리턴
        return true;
    },

    /**
     * 체크박스의 최소 선택 갯수를 검사한다.
     * @param  {string} selector 입력요소의 css셀렉터
     * @param  {int}    min      최소 선택 갯수
     * @param  {string} msg      검사에 실패한 경우 표시될 메시지
     * @return {boolean} 최소 수량 이상 체크된 경우 true / 그렇지 않은 경우 false
     */
    check_min: function(selector, min, msg) {
        // 대상요소의 선택된 항목수가 주어진 min값 보다 작다면?
        if($(selector + ":checked").length < min) {
            // 메시지 표시
            alert(msg, undefined, function() {
                setTimeout(function() {
                    // 첫 번째 요소에게 포커스 강제 지정
                    $(selector).eq(0).focus();
                }, 100)
            }, "warning");

            // 실패했음을 리턴
            return false;
        }

        // 성공했음을 리턴
        return true;
    },

    /**
     * 체크박스의 최대 선택 갯수를 검사한다.
     * @param  {string} selector 입력요소의 css셀렉터
     * @param  {int}    max      최대 선택 갯수
     * @param  {string} msg      검사에 실패한 경우 표시될 메시지
     * @return {boolean} 최대 수량 이하 체크된 경우 true / 그렇지 않은 경우 false
     */
    check_max: function(selector, max, msg) {
        // 대상요소의 선택된 항목수가 주어진 max값 보다 크다면?
        if($(selector + ":checked").length > max) {
            // 메시지 표시
            alert(msg, undefined, function() {
                setTimeout(function() {
                    // 첫 번째 요소에게 포커스 강제 지정
                    $(selector).eq(0).focus();
                }, 100)
            }, "warning");

            // 실패했음을 리턴
            return false;
        }

        // 성공했음을 리턴
        return true;
    },

    /**
     * 두 요소의 입력값이 동일한지 여부를 검사한다.
     * @param  {string} origin 원본 요소의 selector
     * @param  {string} compare  검사 대상 요소의 selector
     * @param  {string} msg      검사에 실패한 경우 표시될 메시지
     * @return {boolean} 동일한 경우 true / 다른 경우 false
     */
    compare_to: function(origin, compare, msg) {
        // 원본요소를 가져온다.
        var src = $(origin);
        // 검사 대상 요소를 가져온다.
        var dsc = $(compare);

        // 두 요소의 입력값이 다르다면?
        if(src.val() != dsc.val()) {
            // 메시지 표시
            alert(msg, undefined, function() {
                setTimeout(function() {
                    // 원본요소의 입력값 지움
                    src.val("");
                    // 검사 대상의 입력값 지움
                    dsc.val("");
                    // 원본 요소에게 포커스 강제 지정
                    src.focus();
                }, 100)
            }, "warning");

            // 실패했음을 리턴
            return false;
        }

        // 성공했음을 리턴
        return true;
    },

    /**
     * 입력값이 정규표현식에 부합되는지(충족하는지) 여부를 검사한다.
     * @param  {string} selector   검사 대상의 selector
     * @param  {string} msg        검사에 실패한 경우 표시될 메시지
     * @param  {object} regex_expr 검사할 정규표현식
     * @return {boolean} 표현식을 충족할 경우 true / 그렇지 않을 경우 false
     */
    field: function(selector, msg, regex_expr) {
        // 검사 대상 요소를 가져온다.
        var target = $(selector);

        // 입력값이 없거나 입력값에 대한 정규표현식 검사가 실패라면?
        if(!target.val() || !regex_expr.test(target.val())) {
            // 메시지 표시
            alert(msg, undefined, function() {
                setTimeout(function() {
                    // 검사 대상의 입력값 지움
                    target.val("");
                    // 포커스 강제 지정
                    target.focus();
                }, 100)
            }, "warning");

            // 실패했음을 리턴
            return false;
        }

        // 성공했음을 리턴
        return true;
    },

    /** ----- field() 함수에 정규표현식 전달하기 ----- */
    /** 
     * 아래의 함수들은 정규표현식 검사 함수에게
     * 검사대상과 검사할 표현식을 전달한다.
     */

    // 숫자로만 이루어 졌는지 검사
    num: function(selector, msg) {
        return this.field(selector, msg, /^[0-9]*$/);
    },

    // 영문으로만 이루어 졌는지 검사
    eng: function(selector, msg) {
        return this.field(selector, msg, /^[a-zA-Z]*$/);
    },
    
    // 한글로만 이루어 졌는지 검사
    kor: function(selector, msg) {
        return this.field(selector, msg, /^[ㄱ-ㅎ가-힣]*$/);
    },
    
    // 영문과 숫자로만 이루어 졌는지 검사
    eng_num: function(selector, msg) {
        return this.field(selector, msg, /^[a-zA-Z0-9]*$/);
    },

    // 한글과 숫자로만 이루어 졌는지 검사
    kor_num: function(selector, msg) {
        return this.field(selector, msg, /^[ㄱ-ㅎ가-힣0-9]*$/);
    },
    
    // 이메일주소 형식인지 검사
    email: function(selector, msg) {
        // "아이디@도메인"의 형식을 충족해야 한다.
        // /[0-9a-zA-Z]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$/
        return this.field(selector, msg, /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i);
    },
    
    // 핸드폰 번호 형식인지 검사
    cellphone: function(selector, msg) {
        return this.field(selector, msg, /^01(?:0|1|[6-9])(?:\d{3}|\d{4})\d{4}$/);
    },
    
    // 집전화 형식인지 검사
    telphone: function(selector, msg) {
        return this.field(selector, msg, /^\d{2,3}\d{3,4}\d{4}$/);
    },
    
    // 주민번호 형식인지 검사
    jumin: function(selector, msg) {
        // "-"없이 주민번호에 대한 글자수 및 뒷자리 첫글자가 1~4의 범위에 있는지에 대한 검사
        return this.field(selector, msg, /^\d{6}[1-4]\d{6}/);
    },

    // 비밀번호 형식인지 검사
    password: function(selector, msg) {
        // 적어도 1개 이상의 숫자와 1개 이상의 특수문자가 포함된 형식을 충족해야 한다.
        return this.field(selector, msg, /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]*$/);
    },
    
    /** ----- // field() 함수에 정규표현식 전달하기 ----- */

    /**
     * 핸드폰번호 형식과 집전화 번호 형식 둘 중 하나를 충족하는지 검사한다.
     * @param  {string} selector   검사 대상의 selector
     * @param  {string} msg        검사에 실패한 경우 표시될 메시지
     * @return {boolean} 표현식을 충족할 경우 true / 그렇지 않을 경우 false
     */
    phone: function(selector, msg) {
        // 핸드폰 형식
        var check1 = /^01(?:0|1|[6-9])(?:\d{3}|\d{4})\d{4}$/;
        // 집전화 형식
        var check2 = /^\d{2,3}\d{3,4}\d{4}$/;
        // 검사 대상 요소를 가져온다.
        var target = $(selector);

        // 입력값이 없거나,   핸드폰 형식도 아니고            집전화 형식도 아니라면?
        if(!target.val() || (!check1.test(target.val()) && !check2.test(target.val()))) {
            // 메시지 표시
            alert(msg, undefined, function() {
                setTimeout(function() {
                    // 검사 대상의 입력값 지움
                    target.val("");
                    // 포커스 강제 지정
                    target.focus();
                }, 100)
            }, "warning");

            // 실패했음을 리턴
            return false;
        }

        // 성공했음을 리턴
        return true;
    },

    /**
     * 비밀번호의 입력값에 아이디가 포함되어 있는지 여부를 검사한다.
     * @param  {string} origin 원본 요소의 selector
     * @param  {string} compare  검사 대상 요소의 selector
     * @param  {string} msg      검사에 실패한 경우 표시될 메시지
     * @return {boolean} 값이 포함되지 않은 경우 true / 포함된 경우 false
     */
    index_check: function(origin, compare, msg) {
        // 원본요소를 가져온다.
        var src = $(origin);
        // 검사 대상 요소를 가져온다.
        var dsc = $(compare).val();

        console.log(src.val());
        console.log(dsc);

        // 두 요소의 입력값이 다르다면?
        if(src.val().indexOf(dsc) > -1) {
            // 메시지 표시
            alert(msg, undefined, function() {
                setTimeout(function() {
                    // 원본요소의 입력값 지움
                    src.val("");
                    // 원본 요소에게 포커스 강제 지정
                    src.focus();
                }, 100)
            }, "warning");

            // 실패했음을 리턴
            return false;
        }

        // 성공했음을 리턴
        return true;
    },

    /**
     * 값이 바뀌었는지 여부를 검사한다.
     * @param {string} selector 검사 대상 요소의 selector
     * @param {string} next     검사에 성공할 경우 다음 selector
     */
    enter: function(selector, next) {
        // 원본 요소를 가져온다.
        var src = $(selector);
        // 다음 요소를 가져온다.
        var dsc = $(next);

        // 원본 요소의 값이 바뀌었을 경우
        src.keyup(function(e) {
            if(e.keyCode == 13) {
                if(src.val() != "") {
                    // 값이 있을 경우 다음 요소에 포커스 강제 지정
                    dsc.focus();
                } else {
                    // 값이 없을 경우 현재 요소에 포커스 강제 지정
                    src.focus();
                }
            }
        });
    }
};
