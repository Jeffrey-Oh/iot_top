<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- search bar -->
<section id="searchbar" class="container radius5">
	<h2 class="title">빠른 검색</h2>
	<article class="searchform radius5">
		<form method="get" role="form" id="fast_search_from">
			<fieldset>
				<legend class="blind">Search Form</legend>

				<div class="row">
                    <div class="col-md-3" id="category_div">
						<select name="category" id="category" class="form-control" required="required">
							<option value="">카테고리</option>
							<option value="tour">관광</option>
							<option value="air">항공</option>
							<option value="servicearea">휴게소</option>
							<option value="culture">문화</option>
							<option value="community">커뮤니티</option>
						</select>
					</div>
					<div class="col-md-7" id="search_input_div">
						<input type="text" class="form-control col-md-7" title="검색어 입력" name="search_input" placeholder="카테고리를 선택해주세요." readonly />
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
