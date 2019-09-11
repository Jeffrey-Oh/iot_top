$(function() {
    $(".mapBtn").click(function(e) {
        e.preventDefault();

        var placeId = $(this).attr("href");
        window.open("http://map.daum.net/link/map/" + placeId);
    }); // End click Event
}); // End $function() {};
