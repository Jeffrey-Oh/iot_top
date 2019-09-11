<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script>
    /** js확장자에서도 JSTL 경로를 사용하기 위한 전역변수 */
    var ROOT_URL = "${pageContext.request.contextPath}";
</script>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/OwlCarousel2/2.2.0/owl.carousel.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/plugins/ajax/ajax_helper.js"></script><!-- ajax helper -->
<script src="${pageContext.request.contextPath}/plugins/headroom/Debouncer.js"></script>
<script src="${pageContext.request.contextPath}/plugins/headroom/features.js"></script>
<script src="${pageContext.request.contextPath}/plugins/headroom/Headroom.js"></script>
<script src="${pageContext.request.contextPath}/plugins/headroom/jQuery.headroom.js"></script>
<script src="${pageContext.request.contextPath}/plugins/sweetalert/sweetalert2.all.min.js"></script>
<script src="${pageContext.request.contextPath}/plugins/animate/jquery.animatecss.min.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/common.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/hot_keyword.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/ezen-helper.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/servicearea_radar_ok.js"></script> 
<script src="${pageContext.request.contextPath}/assets/js/weather_main.js"></script>
