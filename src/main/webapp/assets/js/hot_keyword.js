$(function() {
	var owl = $('.owl-hot_keyword');
	
	owl.owlCarousel({
	     autoplay: true,
	     items: 1,
	     nav: true,
	     loop: false,
	     rewind: true,
	     mouseDrag: false,
	     animateOut: 'slideOutUp',
	     animateIn: 'slideInUp',
	     autoplayTimeout: 5000,
	     autoplayHoverPause: false
	});
})