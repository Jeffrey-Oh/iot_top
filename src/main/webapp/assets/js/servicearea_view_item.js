$(function() {
    //게시글 좋아요
    $(document).on("click", ".btn-like", function() {
        var target = $(this);

        if (target.find(".far").length == 1) {
            // ajax 통신 해서 결과 값 + 1
            $.ajax({
                method: "post",
                url: ROOT_URL + "/servicearea/servicearea_like.do",
                data: {
                    servicearea_groupId: servicearea_groupId,
                    serviceaeraLike: $("#likeCnt").html(),
                    chk: "Y"
                },
                dataType: "json",
                success: function(json) {
                    if (json.rt == "OK"){
                        console.log($("#likeCnt"));
                        $("#likeCnt").html(json.serviceareaLike);
                        target.find(".far").toggleClass("far fas");
                        target.find(".fas").css({
                            "transform": "scale(1.2, 1.2)",
                            "transition": "all .2s ease-in-out"
                        });
                    } else if(json.rt == "X-LOGIN") {
                        swal({
                            title: "<font color='lightskyblue'>알림</font>",
                            html: "로그인이 필요한 서비스 입니다.<br/>로그인하시려면 로그인 버튼을 누르세요.",
                            showCloseButton: true,
                            confirmButtonText: "로그인",
                            confirmButtonColor: "#a00",
                            showCancelButton: true,
                            cancelButtonText: "취소",
                            cancelButtonColor: "#f60",
                            imageUrl: ROOT_URL + "/images/common/login_need.jpg",
                            imageWidth: 400,
                            imageHeight: 350
                        }).then(function(result) {
                            if (result.value) {
                                location.href = ROOT_URL + "/member/member_login.do";
                            } else if (result.dismiss === "cancel") {

                            }
                        });
                    } else {
                        alert("" + json.rt, undefined, undefined, "warning");
                        return false;
                    }
                },
                error: function(request, status, error) {
                     alert("ResponseCode >> "+request.status+"\n"+"Message >> "+request.responseText+"\n"+"Error >> "+error);
                }
            }); // End POST $.ajax

        } else if (target.find(".fas").length == 1) {
            // ajax 통신 해서 결과 값 - 1

            $.ajax({
                method: "post",
                url: ROOT_URL + "/servicearea/servicearea_like.do",
                data: {
                    servicearea_groupId: servicearea_groupId,
                    serviceaeraLike: $("#likeCnt").html(),
                    chk: "N"
                },
                dataType: "json",
                success: function(json) {
                    if (json.rt == "OK"){
                        console.log($("#likeCnt"));
                        $("#likeCnt").html(json.serviceareaLike);
                        target.find(".fas").toggleClass("fas far");
                        target.find(".far").css({
                            "transform": "scale(1.0, 1.0)",
                            "transition": "all .2s ease-in-out"
                        });
                    } else if(json.rt == "X-LOGIN") {
                        swal({
                            title: "<font color='lightskyblue'>알림</font>",
                            html: "로그인이 필요한 서비스 입니다.<br/>로그인하시려면 로그인 버튼을 누르세요.",
                            showCloseButton: true,
                            confirmButtonText: "로그인",
                            confirmButtonColor: "#a00",
                            showCancelButton: true,
                            cancelButtonText: "취소",
                            cancelButtonColor: "#f60",
                            imageUrl: ROOT_URL + "/images/common/login_need.jpg",
                            imageWidth: 400,
                            imageHeight: 350
                        }).then(function(result) {
                            if (result.value) {
                                location.href = ROOT_URL + "/member/member_login.do";
                            } else if (result.dismiss === "cancel") {

                            }
                        });
                    } else {
                        alert("" + json.rt, undefined, undefined, "warning");
                        return false;
                    }
                },
                error: function(request, status, error) {
                     alert("ResponseCode >> "+request.status+"\n"+"Message >> "+request.responseText+"\n"+"Error >> "+error);
                }
            }); // End POST $.ajax
        } // End if~else if
    }); // End 좋아요 click Event

    // 즐겨찾기
    $("#btn-favorite").click(function() {
        if ($(this).css("color") == "rgb(51, 51, 51)") {
            // ajax 통신해서 DB 업데이트
            // 콜백함수 결과 성공 시 이벤트로 아래 1줄 함수안에 넣을 것
            $(this).css({
                "color": "#FA0",
                "transform": "scale(1.2, 1.2)",
                "transition": "all .2s ease-in-out"
            });
        } else {
            // ajax 통신해서 DB 업데이트
            // 콜백함수 결과 성공 시 이벤트로 아래 1줄 함수안에 넣을 것
            $(this).css({
                "color": "rgb(51, 51, 51)",
                "transform": "scale(1.0, 1.0)",
                "transition": "all .2s ease-in-out"
            });
        }
    });

    // 주소복사 버튼 클릭 시
    $("#link-copy").click(function(e) {
        e.preventDefault();
        $("#link-area").attr("type", "text");
        $("#link-area").select();
        var success = document.execCommand("copy");
        $("#link-area").attr("type", "hidden");
        if (success) {
            alert("링크가 복사되었습니다.");
        };
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

});
