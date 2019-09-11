$(function() {
	/** 정책안내 추가를 위한 폼 처리 */
	$("#policy_insert_form").submit(function(e) {
		// 우선 동작 멈춤
		e.preventDefault();
		
        // 입력정보 값 검사
        if(!regex.value("#agreementDoc", "이용약관 내용을 입력해 주세요.")) {return false;}
        if(!regex.value("#infoCollectionDoc", "개인정보처리방침 내용을 입력해 주세요.")) {return false;}
        if(!regex.value("#communityDoc", "게시글관리규정 내용을 입력해 주세요.")) {return false;}
        if(!regex.value("#emailCollectionDoc", "이메일무단수집거부 내용을 입력해 주세요.")) {return false;}
        
        var memF = $(this);
        memF.unbind("submit").submit();
	}); // End 정책안내 추가 submit Event
});