$(function() {
	// AOS 플러그인 초기화
	AOS.init();
	
	$("#culture-slider").owlCarousel({
		items : 4,
		loop : false,
		rewind : true,
		margin : 10,
		autoplay : true,
		autoplayHoverPause : true,
		autoplayTimeout : 4000,
		smartSpeed : 450,
		dots : true
	});

	$("#restarea-slider").owlCarousel({
		items : 3,
		loop : false,
		rewind : true,
		margin : 10,
		autoplay : true,
		autoplayHoverPause : true,
		autoplayTimeout : 4000,
		smartSpeed : 450,
		dots : true
	});

	$("#reserve").flip({
		trigger : "hover",
		front : $("#reserve-front"),
		back : $("#reserve-back"),
		speed : "300",
	});

	/** ===== Daum Map Api ===== */
    var mapContainer = document.getElementById('map'); // 지도를 표시할 div
    var mapOption = {
        center: new daum.maps.LatLng(lat, lng), // 지도의 중심좌표
        level: 3 // 지도의 확대 레벨
    };

    var map = new daum.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

    // 마커를 표시할 위치와 title 객체 배열입니다
    var position = {
        title: serviceareaName,
        latlng: new daum.maps.LatLng(lat, lng)
    };

    // 마커 이미지의 이미지 주소입니다
    var imageSrc = "http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

    // 마커 이미지의 이미지 크기 입니다
    var imageSize = new daum.maps.Size(24, 35);

    // 마커 이미지를 생성합니다
    var markerImage = new daum.maps.MarkerImage(imageSrc, imageSize);

    // 마커를 생성합니다
    var marker = new daum.maps.Marker({
        map: map, // 마커를 표시할 지도
        position: position.latlng, // 마커를 표시할 위치
        title : position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
        image : markerImage // 마커 이미지
    });

    // 마커에 클릭이벤트를 등록합니다
    daum.maps.event.addListener(marker, 'click', function() {
        window.open("http://map.daum.net/link/map/" + placeId);
    });
    
    // 휴게소 커튼 슬라이드 업 처리
    $(window).scroll(function() {
    	var scrollTop = $(window).scrollTop();
    	if (scrollTop > 1300) {
    		$(".servicearea_curtain").slideUp(500);
    	} else {
    		$(".servicearea_curtain").slideDown(500);
    	}
    });
    
    // 문화 애니메이션 처리
    $(window).scroll(function() {
    	var scrollTop = $(window).scrollTop();
    	if(scrollTop >= 3600 ) {
    		// 보이는 곳
    		$(".culture-layout .culture-slider, .culture-layout .c-pic").css({
    			"-webkit-animation": "showc 1.75s 1 ease forwards"
    		});
    		
    		$(".culture-layout .cont-box").css({
    			"-webkit-animation": "moveL 1.25s 1 ease forwards"
    		});
    		
    		$(".culture-layout .cont-box-rotate").css({
    			"-webkit-animation": "moveR 1.25s 1 ease forwards"
    		});
    	} else {
    		// 숨기는 곳
    		$(".culture-layout .cont-box").css({
    			"-webkit-animation": "RmoveL 1.25s 1 ease forwards"
    		});
    		
    		$(".culture-layout .cont-box-rotate").css({
    			"-webkit-animation": "RmoveR 1.25s 1 ease forwards"
    		});
    		
    		$(".culture-layout .culture-slider, .culture-layout .c-pic").css({
    			"-webkit-animation": "Rshowc 0.25s 1 ease forwards"
    		});
    	}
    });
});