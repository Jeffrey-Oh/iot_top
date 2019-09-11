$(function() {
    /** 휴게소 목록이 존재할 경우에만 실행 */
    if(parseInt(groupList_length) > 0) {
        /** ===== Daum Map Api ===== */
        var mapContainer = document.getElementById('map'); // 지도를 표시할 div
        var mapOption = {
            center: new daum.maps.LatLng(lat[0], lng[0]), // 지도의 중심좌표
            level: 10 // 지도의 확대 레벨
        };

        var map = new daum.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

        // 마커를 표시할 위치와 title 객체 배열입니다
        var positions = [
            {
                title: serviceareaName[0],
                latlng: new daum.maps.LatLng(lat[0], lng[0])
            }
        ]; // End positions[]
        // 배열에 삽입
        for(var i = 1; i < parseInt(groupList_length); i++) {
            positions.push(
                {
                    title: serviceareaName[i],
                    latlng: new daum.maps.LatLng(lat[i], lng[i])
                }
            );
        }

        // 마커 이미지의 이미지 주소입니다
        var imageSrc = "http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

        for(var i = 0; i < positions.length; i++) {

            // 마커 이미지의 이미지 크기 입니다
            var imageSize = new daum.maps.Size(24, 35);

            // 마커 이미지를 생성합니다
            var markerImage = new daum.maps.MarkerImage(imageSrc, imageSize);

            // 마커를 생성합니다
            var marker = new daum.maps.Marker({
                map: map, // 마커를 표시할 지도
                position: positions[i].latlng, // 마커를 표시할 위치
                title : positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
                image : markerImage // 마커 이미지
            });

            // 마커에 클릭이벤트를 등록합니다
            daum.maps.event.addListener(marker, 'click', function() {
            	var tag = $(this)[0].hg.id.substring(22);
            	if (tag === "a") {
            		tag = 10;
            	}
                window.open("http://map.daum.net/link/map/" + placeId[tag-1]);
            });
        } // End for

    } // End if

}); // End $(function() {});
