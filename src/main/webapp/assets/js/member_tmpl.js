$(function() {
    /** onload 시 이름 입력 항목에 자동커서 */
    $("#userName").focus();

    /** 회원가입 폼의 정보입력 Submit Event */
    $("#member_form").submit(function(e) {
        // jQuery에서의 기본 동작 중단 처리
        e.preventDefault();

        // 이름 값 검사
        if(!regex.value("#userName", "이름을 입력해 주세요.")) {return false;}
        if(!regex.kor("#userName", "이름은 한글만 입력 가능합니다.")) {return false;}
        if(!regex.min_length("#userName", 2, "이름은 최소 2자 이상 입력해야 합니다.")) {return false;}
        if(!regex.max_length("#userName", 20, "이름은 최대 20자 까지만 입력 가능합니다.")) {return false;}

        // 성별 값 검사
        if(!regex.select("#gender", "성별을 선택해 주세요.")) {return false;}

        // 생년월일 값 검사
        if(!regex.select("#year", "생년월일을 선택해 주세요.")) {return false;}
        if(!regex.select("#month", "생년월일을 선택해 주세요.")) {return false;}
        if(!regex.select("#day", "생년월일을 선택해 주세요.")) {return false;}

        // 아이디 값 검사
        if(!regex.value("#userId", "아이디를 입력해 주세요.")) {return false;}
        if(!regex.eng_num("#userId", "아이디는 영어와 숫자 조합만 입력 가능합니다.")) {return false;}
        if(!regex.min_length("#userId", 4, "아이디는 최소 4자 이상 입력하셔야 합니다.")) {return false;}
        if(!regex.max_length("#userId", 20, "아이디는 최대 20자 까지만 입력 가능합니다.")) {return false;}

        // 비밀번호 값 검사
        if(!regex.value("#password", "비밀번호를 입력해 주세요.")) {return false;}
        if(!regex.min_length("#password", 6, "비밀번호는 최소 6자 이상 입력하셔야 합니다.")) {return false;}
        if(!regex.max_length("#password", 20, "비밀번호는 최대 20자 까지만 입력 가능합니다.")) {return false;}
        if(!regex.password("#password", "비밀번호는 적어도 1개 이상의 숫자와 1개 이상의 특수문자가 포함되어야 합니다.")) {return false;}
        if(!regex.index_check("#password", "#userId", "비밀번호에는 아이디가 포함되면 안됩니다.")) {return false;}
        // 비밀번호 확인 값 검사
        if(!regex.value("#passwordCheck", "비밀번호 확인을 입력해 주세요.")) {return false;}
        if(!regex.compare_to("#password", "#passwordCheck", "비밀번호 확인이 잘못되었습니다.")) {return false;}

        // 휴대전화 값 검사
        if(!regex.value("#phone", "연락처를 입력해 주세요.")) {return false;}
        if(!regex.phone("#phone", "연락처의 형식이 잘못되었습니다.")) {return false;}

        // 이메일 값 검사
        if(!regex.value("#email", "이메일을 입력해 주세요.")) {return false;}
        if(!regex.email("#email", "이메일 주소의 형식이 잘못되었습니다.")) {return false;}

        // 우편번호, 상세주소 값 검사
        if(!regex.value("#postcode", "우편번호를 입력해 주세요.")) {return false;}
        if(!regex.value("#addr1", "주소를 입력해 주세요.")) {return false;}
        if(!regex.value("#addr2", "상세주소를 입력해 주세요.")) {return false;}

        var memF = $(this);
        // 입력확인하기
        confirm("알림", "입력하신 내용이 맞습니까?", function(ok) {
            memF.unbind("submit").submit();
        });
    }); // End 회원가입 폼의 정보입력 Submit Event
}); // End $function() {};