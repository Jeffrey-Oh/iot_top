<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- search bar -->
<section id="searchbar" class="container radius5">
    <h2 class="title">휴게소 검색</h2>
    <article class="searchform radius5">
        <form action="${pageContext.request.contextPath}/servicearea/servicearea_search.do" name="servicearea_search_form" id="servicearea_search_form" method="get" role="form">
            <fieldset>
                <legend class="blind">Search Form</legend>
            
                <div class="row">
                    <div class="col-md-10">
                        <input type="text" class="form-control col-md-7" name="search_input" id="search_input" title="검색어 입력" placeholder="휴게소명을 입력해주세요" value="${search_input}" />
                    </div>
                    <div class="col-md-2">
                        <button type="submit" class="btn width100 bg_color1 color8 radius3">Search</button>
                    </div>
                </div>
            </fieldset>
        </form>
    </article>
</section>
<!-- //search bar -->
