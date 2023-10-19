<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">


<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>뉴스상세페이지 - NewsInfoPage</title>
<!-- Favicon-->
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="resources/css/styles.css" rel="stylesheet" />

<style type="text/css">
.newsTitle {
	font-size: 32px;
	font-weight: bold;
	letter-spacing: -0.08em;
}
</style>

</head>



<body>
	<%@ include file="/WEB-INF/views/Includes/Menu.jsp"%>


	<!-- Responsive navbar-->
	<!-- Page content-->
	<div class="container">

		<div class="row">

			<div class="newsTitle card col-md-12 mb-2">
				<h3>뉴스제목</h3>
			</div>
			<div class="card col-md-12 mb-2">
				<div class="row">
					<div class="card col-md-2 my-md-2 mx-md-3">
						<img alt="뉴스사진" src="${nw.nwimg }">
					</div>
					<div class="card col-md-8 my-md-2 mx-md-3 ">
						<p>${nw.nwcontent }</p>
					</div>
				</div>
				<span>작성일: ${nw.nwdate }, // 조회수: ${nw.nwbighit }</span>
			</div>





				<c:if test="${sessionScope.loginId != null }">
				<div class="reviewWrite">
					<form action="newsRegistReview" class="my-3" method="post">
							<input type="text" name="restate" value="${nw.nwcode }" style="display:none">
							<textarea class="w-100 reviewComment" name="recontent"></textarea>
							<input class="btn btn-success w-100" type="submit" value="댓글 등록">
						</form>
				</div>
				<hr>
			</c:if>
			<c:forEach items="${reviewList}" var="re">
					<div class="meminfo">
						<input value="${re.REWRITER}" style="margin: 0px;" disabled="disabled">
						<c:if test="${sessionScope.loginId == re.REWRITER }">
						<button type="button" onclick="location.href='/deleteNewsReview?recode=${re.RECODE}&nwcode=${nw.nwcode}'" class="btn btn-danger" style="font-size: 10px; margin-bottom: 4px; height: 30px;">삭제</button>
						</c:if>
					</div>
					<div style="margin-top: 5px; margin-bottom: 5px;">
						<textarea rows="" cols="" class="rvcomm scroll" disabled="disabled">${re.RECONTENT}</textarea>
					</div>
					<div class="small text-muted">${re.REDATE}</div>
				</c:forEach>
		</div>


	</div>




	<!-- Footer-->
	<footer class="py-5 bg-dark">
		<div class="container">
			<p class="m-0 text-center text-white">위 페이지의 출력되는 정보는 우측 상단에 있는
				데이터 클롤링 및 페이지 양식을 인용하여 제작되었습니다.</p>
		</div>
	</footer>


	<!-- Bootstrap core JS-->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
	<!-- Core theme JS-->
	<script src="/resources/js/scripts.js"></script>

</body>

</html>