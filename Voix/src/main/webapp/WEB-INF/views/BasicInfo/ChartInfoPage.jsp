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
<title>랭킹상세페이지 - ChartInfoPage</title>
<!-- Favicon-->
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="resources/css/styles.css" rel="stylesheet" />

<style type="text/css">
</style>

</head>



<body>
	<%@ include file="/WEB-INF/views/Includes/Menu.jsp"%>


	<!-- Responsive navbar-->
	<!-- Page content-->
	<div class="container">



	<c:forEach items="${ChartInfoList}" var="ChartInfoList">
		<div class="row">
			<div class="card col-md-4 mb-4 ">
				<img alt="랭킹 포스터" src="${ChartInfoList.sgimg}">
			</div>

			<div class="card col-md-8 mb-4">
				<p>${ChartInfoList.sgaltitle}</p>
				<p>${ChartInfoList.sgtitle}</p>
				<p>${ChartInfoList.sginfo}</p>
			</div>
		</div>


		<div class="row">

			<div class="col mb-4">
				${ChartInfoList.sgmvurl}
			</div>

			<div class="card col mb-4">
				<div style="height: 415px; overflow: scroll; margin-left: 10px;">
					${ChartInfoList.sglyric}
				</div>
			</div>
			<div class="card col mb-4">
				<p>${ChartInfoList.sgartist}</p>
			</div>
		</div>
	</c:forEach>



		<div class="card col mb-4">
			<p>댓글 영역</p>
		</div>


		<!-- 				<div class="card mb-4">
					<a href="#!"><img class="card-img-top"
						src="https://dummyimage.com/850x350/dee2e6/6c757d.jpg" alt="..." /></a>
					<div class="card-body">
						<div class="small text-muted">January 1, 2023</div>
						<h2 class="card-title">Featured Post Title</h2>
						<p class="card-text">Lorem ipsum dolor sit amet, consectetur
							adipisicing elit. Reiciendis aliquid atque, nulla? Quos cum ex
							quis soluta, a laboriosam. Dicta expedita corporis animi vero
							voluptate voluptatibus possimus, veniam magni quis!</p>
						<a class="btn btn-primary" href="#!">Read more →</a>
					</div>
				</div>  -->

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