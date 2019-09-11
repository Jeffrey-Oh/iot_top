$(function() {
	// 취소 버튼 클릭 시 이전페이지로 이동
	$("#btn-cls").click(function() {
		history.back();
	});
	
	// edit-form을 ajaxForm plugin으로 submit
	$("#edit-form").form_helper({
		rules : {
			"title" : {
				"required" : true,
				"minlength" : 2
			}
		},

		messages : {
			"title" : {
				"required" : "제목을 입력하세요.",
				"minlength" : "제목은 최소 2글자 이상 입력하세요."
			}
		},
		beforeSubmit : function(arr, form, options) {
			var content = CKEDITOR.instances.textarea.getData();
			var content_len = CKEDITOR.instances.textarea.getData().length;
			
			// 내용이 없는 경우
			if (content == "") {
				alert("알림", "내용을 입력하세요.", function() {
					setTimeout(function() {
						CKEDITOR.instances.textarea.focus();
					}, 100);
				}, "warning");
				return false;
			}

			// 내용이 10글자 미만인 경우
			if (content_len < 10) {
				alert("알림", "내용을 10자 이상 입력하세요.", function() {
					setTimeout(function() {
						CKEDITOR.instances.textarea.setData("");
					}, 100);
					CKEDITOR.instances.textarea.focus();
				}, "warning");
				return false;
			}

			// edit-form validate plugin 다시 실행
			return $("#edit-form").valid();
		},
		success : function(json) {
			if (json.rt == "OK") {
				alert("<font color='#05a'>Success</font>", "<strong>수정되었습니다.</strong>", function() {
					location.href = json.url;
				}, "success");
			} else if (json.rt == "FileFail") {
				alert("용량이 <span style='color: red; font-weight: bold'>5MB</span>(이)가 넘는 파일이 있거나\n<span style='color: red; font-weight: bold'>확장자</span>가 올바르지 않습니다. 확인해 주세요");
        	} else {
        		alert("<font color='#05a'>Error</font>", "<strong>글 수정에 실패했습니다. <br />관리자에게 문의하세요.</strong>", function() {
					location.href = ROOT_URL + "/CommIndex.do";
	        	}, "error");
        	}
		}
	});
	
	var category = korCtg;
	var switch1 = "#ctg li:nth-child(1) a";
	var switch2 = "#ctg li:nth-child(2) a";
	var switch3 = "#ctg li:nth-child(3) a";
	var switch4 = "#ctg li:nth-child(4) a";
	var switch5 = "#ctg li:nth-child(5) a";
	var switch6 = "#ctg li:nth-child(6) a";

	if (category == "") {
		$(switch1).addClass("ctg-a color1");
		$(switch1).not(switch1).removeClass("ctg-a");
	} else if (category == "공지사항") {
		$(switch2).addClass("ctg-a color1");
		$(switch2).not(switch2).removeClass("ctg-a");
	} else if (category == "관광") {
		$(switch3).addClass("ctg-a color1");
		$(switch3).not(switch3).removeClass("ctg-a");
	} else if (category == "항공") {
		$(switch4).addClass("ctg-a color1");
		$(switch4).not(switch4).removeClass("ctg-a");
	} else if (category == "휴게소") {
		$(switch5).addClass("ctg-a color1");
		$(switch5).not(switch5).removeClass("ctg-a");
	} else if (category == "문화") {
		$(switch6).addClass("ctg-a color1");
		$(switch6).not(switch6).removeClass("ctg-a");
	};

	// 파일첨부 체크 리스트
	$("input[id=file]").change(function(){

	    if($(this).val() != ""){
	    	// 확장자 체크
            var ext = $(this).val().split(".").pop().toLowerCase();
            if($.inArray(ext, ["gif","jpg","jpeg","png"]) == -1){
                alert("gif, jpg, jpeg, png 파일만 업로드 해주세요.");
                $(this).val("");
                return;
            }
            
            // 용량 체크
            for (var i=0; i<this.files.length; i++) {
            	var fileSize = this.files[i].size;
            	var fSMB = (fileSize / (1024 * 1024)).toFixed(2);
            	var maxSize = 1024 * 1024 * 5;
            	var mSMB = (maxSize / (1024 * 1024));
            	if(fileSize > maxSize){
            		alert(this.files[i].name + "(이)가 용량 5MB을 초과했습니다.\n\n<font color='red'>" + fSMB + "MB</font> / " + mSMB + "MB");
            		$(this).val("");	
            	}
            }
	    }
	});
});
