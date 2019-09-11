$(function() {
	$('.weather-carousel').owlCarousel({
		autoplay : true,
		items : 1,
		nav : true,
		loop : false,
		rewind : true,
		mouseDrag : false,
		animateOut : 'slideOutLeft',
		animateIn : 'slideInRight',
		autoplayTimeout : 8000,
		autoplayHoverPause : false
	})

	var query = [
		"Seoul", "Daejeon", "Jeonju", "Chuncheon", "Cheongju", "Kwangju", "Daegu",
		"Busan", "Jeju"
	];
	
	$.each(query, function(i) {
		$.ajaxSetup({
			async: false
		});
		
		$.get(ROOT_URL + "/weather_ok.do", {query: query[i]}, function(json) {
			if(json.rt == "OK") {
				$("#temp" + i).html(json.temp + ' C&deg');
				$("#humidity" + i).html(json.humidity + ' %');
				$("#temp_min" + i).html(json.minTemp + ' C&deg');
				$("#temp_max" + i).html(json.maxTemp + ' C&deg');
				$("#icon" + i).html('<img src="http://openweathermap.org/img/w/'+ json.icon + '.png"</img>');	
			} else {
				alert("" + json.rt);
			}
		});
	});
});
