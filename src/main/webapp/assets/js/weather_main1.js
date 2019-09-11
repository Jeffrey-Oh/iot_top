$(function() {
	var locArray = [
        [
            "Seoul", "Hongsung", "Yangpyong", "Kwangju", "Kumi", "Gimcheon", "Seongnam", "Kosong",
            "Ulsan", "Incheon", "Daegu", "Busan", "Kimhae", "Daejeon", "Muan", "Yeoju", "Kangwŏn-do", "Jeju"
        ],
        [
            "서울", "홍성", "양평", "광주", "구미", "김천", "성남", "고성", "울산", "인천", "대구", "부산", "김해",
            "대전", "무안", "여주", "강원도", "제주도"
        ]
    ];
	
	for(var i = 0; i < locArray[0].length; i++) {
        $("#location").append(
            $("<option />").val(
        		locArray[0][i]
            ).html(
        		locArray[1][i]
            )
        );
    } // End for
	
	for(var i = 0; i < 18; i++) {
		var tempVal = $("#location").find("option:eq(" + i + ")").val();
		if(tempVal == loc) {
			$("#location").find("option:eq(" + i + ")").prop("selected", true);
		}
	}
	
	var query = $("#location").val();
	
	$("#location").change(function(e) {
		var locationVal = $(this).val();
		if(locationVal != "") {
			location.href = ROOT_URL + "/weather.do?location=" + locationVal;
		} else {
			alert("날짜를 선택해 주세요.");
		}
	}); // End change Event
	
	
	$.ajaxSetup({
		async: false
	});
	
	$.get(ROOT_URL + "/weather_ok.do", {query: query}, function(json) {
		if(json.rt == "OK") {
			$("#tem").html(json.temp +' C&deg');
		    $("#humidit").html(json.humidity +' %');
		    $("#tem_max").html(json.temp_max +' C&deg');
		    $("#spee").html(json.speed +' m/s');
		    $("#ico").html('<img src="http://openweathermap.org/img/w/' + json.icon + '.png"</img>');
		} else {
			alert("" + json.rt);
		}
	});
    
	var chart = bb.generate({
  	    data: {
	  		columns: [
		  		temp_max_array,
		  		humidity_array,
		  		speed_array
	  		]
  		},
  		size : {
  			"width" : 1400
  		},
  		axis: {
  		    x: {
  		        type: "category",
  		        categories: time_chart
  		  	}
  		},
  	    "legend": {
  	        "position": "right"
  	    },
  		bindto: "#categoryAxis"
	}); // End chart
});