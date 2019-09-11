$(function() {
	// 검색 종류를 변경할 경우
	$("#search-title").change(function() {
		var type = $("#search-title > option:selected").val();
		$("#type").attr("value", type);
	});

	$("#bd-table").find("img").css({
		"position" : "relative",
		"top" : "-1px"
	});

	$("#title-flow").next().css({
		"position" : "relative",
		"top" : "-5px"
	});

	var category = korCtg;
	var switch1 = "#ctg li:nth-child(1) a";
	var switch2 = "#ctg li:nth-child(2) a";
	var switch3 = "#ctg li:nth-child(3) a";
	var switch4 = "#ctg li:nth-child(4) a";
	var switch5 = "#ctg li:nth-child(5) a";
	var switch6 = "#ctg li:nth-child(6) a";

	if (category == "") {
		$(switch1).addClass("ctg-a color1");
		$(switch1).not(switch1).removeClass("ctg-a");
	} else if (category == "공지사항") {
		$(switch2).addClass("ctg-a color1");
		$(switch2).not(switch2).removeClass("ctg-a");
	} else if (category == "관광") {
		$(switch3).addClass("ctg-a color1");
		$(switch3).not(switch3).removeClass("ctg-a");
	} else if (category == "항공") {
		$(switch4).addClass("ctg-a color1");
		$(switch4).not(switch4).removeClass("ctg-a");
	} else if (category == "휴게소") {
		$(switch5).addClass("ctg-a color1");
		$(switch5).not(switch5).removeClass("ctg-a");
	} else if (category == "문화") {
		$(switch6).addClass("ctg-a color1");
		$(switch6).not(switch6).removeClass("ctg-a");
	};

	var nowPageStr = eval(nowPage % 5) + "";
	var list1 = ".list-number li:nth-child(1) a";
	var list2 = ".list-number li:nth-child(2) a";
	var list3 = ".list-number li:nth-child(3) a";
	var list4 = ".list-number li:nth-child(4) a";
	var list5 = ".list-number li:nth-child(5) a";
	var list6 = ".list-number li:nth-child(6) a";
	var list7 = ".list-number li:nth-child(7) a";

	if (nowPageStr == "1") {
		if ($(list1).html() != "처음") {
			$(list1).addClass("list-a");
			$(list1).not(list1).removeClass("list-a");
		} else {
			$(list3).addClass("list-a");
			$(list3).not(list3).removeClass("list-a");
		}
	} else if (nowPageStr == "2") {
		if ($(list1).html() != "처음") {
			$(list2).addClass("list-a");
			$(list2).not(list2).removeClass("list-a");
		} else {
			$(list4).addClass("list-a");
			$(list4).not(list4).removeClass("list-a");
		}
	} else if (nowPageStr == "3") {
		if ($(list1).html() != "처음") {
			$(list3).addClass("list-a");
			$(list3).not(list3).removeClass("list-a");
		} else {
			$(list5).addClass("list-a");
			$(list5).not(list5).removeClass("list-a");
		}
	} else if (nowPageStr == "4") {
		if ($(list1).html() != "처음") {
			$(list4).addClass("list-a");
			$(list4).not(list4).removeClass("list-a");
		} else {
			$(list6).addClass("list-a");
			$(list6).not(list6).removeClass("list-a");
		}
	} else if (nowPageStr == "0") {
		if ($(list1).html() != "처음") {
			$(list5).addClass("list-a");
			$(list5).not(list5).removeClass("list-a");
		} else {
			$(list7).addClass("list-a");
			$(list7).not(list7).removeClass("list-a");
		}
	};

	$(".list-query-add a").click(function(e) {
		e.preventDefault();
		
		var curThis = $(this);
		var href = curThis.attr("href");
		var tempUrl = href.substring(0, href.indexOf("&category="));
		var ctgParams = href.substring(href.indexOf("&category="));
		tempUrl += "&list=" + nowPage + ctgParams;
		curThis.attr("href", tempUrl);
		var url = curThis.attr("href");
		location.href = url;

	});
});