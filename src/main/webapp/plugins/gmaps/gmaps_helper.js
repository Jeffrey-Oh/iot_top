/**
 * 지도 객체를 생성 후 리턴한다.
 * @params 	elid 		- 지도가 표시될 div요소의 id
 * @params 	lat_value 	- 지도에 표시할 위치의 위도
 * @params 	lng_value 	- 지도에 표시할 위치의 경도
 */
function createGoogleMap(elid, lat_value, lng_value) {
	var map = new GMaps({
		// 지도를 표시할 div의 id값.
		el: elid,
		// 지도가 표시될 위/경도
		lat : lat_value,
		lng : lng_value,
		// 줌 컨트롤 사용 여부
		zoomControl : true
	});

	return map;
}

/**
 * 지도 객체에 마커를 추가한다.
 * @params 	map 		 - 마커를 표시할 지도 객체
 * @params 	lng_value 	 - 마커가 표시될 위치의 경도
 * @params 	lat_value 	 - 마커가 표시될 위치의 위도
 * @params 	title_string - 마커에 대한 타이틀
 * @params 	icon_url 	 - 마커 이미지의 URL
 * @params 	content_html - 마커 클릭시 표시될 말풍선에 구성할 HTML 태그
 */
function addMapMarker(map, lat_value, lng_value, title_string, icon_url, content_html) {
	// 표시중인 위치에 마커 추가
	map.addMarker({
		lat : lat_value,
		lng : lng_value,
		// 마우스 오버시 노란 박스
		title : title_string,
		// 사용자 정의 아이콘
		icon: {
			url: icon_url, // url
   			scaledSize: new google.maps.Size(50, 50)
		},
		// 클릭시 말풍선 <-- HTML 코딩 가능함.
		infoWindow : {
			content : content_html
		}
	});
}