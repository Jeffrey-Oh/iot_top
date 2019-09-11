$(function() {
    var pathXsplit = pathX.split(",");
    var pathYsplit = pathY.split(",");
    var centerIdx = parseInt(pathLength / 2); // 중심좌표에 넣을 위치

    var mapContainer = document.getElementById('map'); // 지도를 표시할 div
    var mapOption = {
        center: new daum.maps.LatLng(pathYsplit[centerIdx], pathXsplit[centerIdx]), // 지도의 중심좌표
        level: 13 // 지도의 확대 레벨
    };

    var map = new daum.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

    var startSrc = 'http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/red_b.png', // 출발 마커이미지의 주소입니다
        startSize = new daum.maps.Size(50, 45), // 출발 마커이미지의 크기입니다
        startOption = {
            offset: new daum.maps.Point(15, 43) // 출발 마커이미지에서 마커의 좌표에 일치시킬 좌표를 설정합니다 (기본값은 이미지의 가운데 아래입니다)
        };

    // 출발 마커 이미지를 생성합니다
    var startImage = new daum.maps.MarkerImage(startSrc, startSize, startOption);

    var startDragSrc = 'http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/red_drag.png', // 출발 마커의 드래그 이미지 주소입니다
        startDragSize = new daum.maps.Size(50, 64), // 출발 마커의 드래그 이미지 크기입니다
        startDragOption = {
            offset: new daum.maps.Point(15, 54) // 출발 마커의 드래그 이미지에서 마커의 좌표에 일치시킬 좌표를 설정합니다 (기본값은 이미지의 가운데 아래입니다)
        };

    // 출발 마커의 드래그 이미지를 생성합니다
    var startDragImage = new daum.maps.MarkerImage(startDragSrc, startDragSize, startDragOption);

    // 출발 마커가 표시될 위치입니다
    var startPosition = new daum.maps.LatLng(start2, start1);

    // 출발 마커를 생성합니다
    var startMarker = new daum.maps.Marker({
        map: map, // 출발 마커가 지도 위에 표시되도록 설정합니다
        position: startPosition,
        draggable: false, // 출발 마커가 드래그 가능하도록 설정합니다
        image: startImage // 출발 마커이미지를 설정합니다
    });

    var arriveSrc = 'http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/blue_b.png', // 도착 마커이미지 주소입니다
    arriveSize = new daum.maps.Size(50, 45), // 도착 마커이미지의 크기입니다
    arriveOption = {
        offset: new daum.maps.Point(15, 43) // 도착 마커이미지에서 마커의 좌표에 일치시킬 좌표를 설정합니다 (기본값은 이미지의 가운데 아래입니다)
    };

    // 도착 마커 이미지를 생성합니다
    var arriveImage = new daum.maps.MarkerImage(arriveSrc, arriveSize, arriveOption);

    var arriveDragSrc = 'http://t1.daumcdn.net/localimg/localimages/07/mapapidoc/blue_drag.png', // 도착 마커의 드래그 이미지 주소입니다
        arriveDragSize = new daum.maps.Size(50, 64), // 도착 마커의 드래그 이미지 크기입니다
        arriveDragOption = {
            offset: new daum.maps.Point(15, 54) // 도착 마커의 드래그 이미지에서 마커의 좌표에 일치시킬 좌표를 설정합니다 (기본값은 이미지의 가운데 아래입니다)
        };

    // 도착 마커의 드래그 이미지를 생성합니다
    var arriveDragImage = new daum.maps.MarkerImage(arriveDragSrc, arriveDragSize, arriveDragOption);

    // 도착 마커가 표시될 위치입니다
    var arrivePosition = new daum.maps.LatLng(goal2, goal1);

    // 도착 마커를 생성합니다
    var arriveMarker = new daum.maps.Marker({
        map: map, // 도착 마커가 지도 위에 표시되도록 설정합니다
        position: arrivePosition,
        draggable: false, // 도착 마커가 드래그 가능하도록 설정합니다
        image: arriveImage // 도착 마커이미지를 설정합니다
    });

    // 선을 구성하는 좌표 배열입니다. 이 좌표들을 이어서 선을 표시합니다.
    var linePath = [];
    linePath.push(startPosition);
    for(var i = 0; i < pathLength; i++) {
        // 위치가 담길 값 위치입니다
        var item = new daum.maps.LatLng(pathYsplit[i], pathXsplit[i]);
        console.dir(item);
        if(isNaN(item.ib) || isNaN(item.jb)) {
            continue;
        }
        linePath.push(item);
    } // End for

    // 지도에 표시할 선을 생성합니다
    var polyline = new daum.maps.Polyline({
        path: linePath, // 선을 구성하는 좌표배열 입니다
        strokeWeight: 5, // 선의 두께입니다
        strokeColor: 'rgb(51, 150, 255);', // 선의 색깔입니다
        strokeOpacity: 1, // 선의 불투명도입니다 0에서 1 사이값이며 0에 가까울수록 투명합니다
        strokeStyle: 'solid' // 선의 스타일입니다
    });

    // 지도에 선을 표시합니다
    polyline.setMap(map);

}); // End $(function() {});
