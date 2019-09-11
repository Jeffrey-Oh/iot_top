$(function() {
    $(".radarBtn").click(function(e) {
        e.preventDefault();

        /** ===== geolocation api ===== */
        navigator.geolocation.watchPosition(function(position) {
            do_something(position.coords.latitude, position.coords.longitude);
        }, function() {
            do_something(37.4923615, 127.02928809999999);
        }, {
            enableHighAccuracy: true,
            maximumAge        : 30000,
            timeout           : 27000
        });

        function do_something(lat, lng) {
            $.ajaxSetup({async: false});
            $.get(ROOT_URL + "/servicearea/servicearea_radar_ok.do", {lng: lng, lat: lat}, function(json) {
                //console.log(json);
                if(json.rt == "OK") {
                    location.href = ROOT_URL + "/servicearea/servicearea_radar.do?placeId=" + json.placeId;
                } else {
                    alert("" + json.rt, undefined, undefined, "warning");
                    return false;
                } // End if~else
            }); // End $.get() Ajax
        } // End function do_someting();

    }); // End click Event

}); // End $(function() {});
