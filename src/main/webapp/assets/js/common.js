$(document).ready(function() {
    var $gnb = $('#gnb'),
        $topBtn = $('.btn_top'),
        $footer = $('#footer'),
        headOffset = $('#contents').offset().top - $gnb.height();

    $(window).on('load scroll', function(){
        var scrollTop = $(this).scrollTop(),
            winHeight = $(this).height(),
            docHeight = $(document).height();

        /* gnb */
        if(scrollTop > 35){
            $gnb.addClass('fixed').css({'position': 'fixed', top: 0});
        } else {
            $gnb.removeClass('fixed').css({'position': 'absolute', top: 35});
        }
        /* End gnb */

        /* top button */
        if(scrollTop > 0){
            $topBtn.fadeIn(200);
        } else {
            $topBtn.fadeOut(200);
        }

        if(scrollTop > docHeight - $footer.height() - winHeight){
            $topBtn.css({'position': 'absolute', bottom: 0});
        } else {
            $topBtn.css({'position': 'fixed', bottom: 10});
        }
        /* End top button */
    });

    /* top button */
    $topBtn.click(function(){
        $('html, body').stop().animate({scrollTop: 0}, 500);
    });
    /* End top button */

    /* gnb Headroom */
    $gnb.headroom({
        tolerance: 10,// 스크롤 이동 거리(px) -> 이만큼의 스크롤이 이동되어야 동작함
        offset: headOffset,// 타이틀바 유효 위치(px) -> 세로로 350px까지는 정상표시됨
        classes: {// 타이틀바에 적용할 CSS 클래스
            initial: "animated",// 초기화 클래스(무조건 적용됨)
            pinned: "slideInDown",// 스크롤이 위로 이동할 때 적용될 클래스
            unpinned: "slideOutUp",// 스크롤이 아래로 이동할 때 적용될 클래스
            top: "headroom--top",// 타이틀바가 화면에 표시중인 상태에서 적용될 클래스
            notTop: "headroom--not-top"// 타이틀바가 위로 사라진 상태에서 적용될 클래스
        }
    });

    $('#allmenu').on('show.bs.modal', function(){
        $('html').addClass('modal-open allmenu-open');
    }).on('hidden.bs.modal', function(){
        $('html').removeClass('modal-open allmenu-open');
    });

    /** 광고제휴문의를 클릭할 경우 */
    $("#thanks").click(function(e) {
        alert("광고 제휴는\n이메일: masterTravel@travel.co.kr 로\n문의 바랍니다.");
    }); // End click Event
});
