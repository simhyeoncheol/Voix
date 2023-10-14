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
<title>Blog Home - Start Bootstrap Template</title>
<!-- Favicon-->
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="resources/css/styles.css" rel="stylesheet" />
<script src="https://kit.fontawesome.com/acc1ccb443.js"
	crossorigin="anonymous"></script>
</head>
<body>
	<!-- Responsive navbar-->
	<%@ include file="/WEB-INF/views/Includes/Menu.jsp"%>
	<!-- Page content-->
	<div class="container">
		<div class="row">
			<!-- Blog entries-->
			<div class="col-lg-8">
				<div class="card m-2">예스, 알라딘, 교보</div>
				<c:forEach items="${ChartListMap}" var="ChartMap">
					<div class="card m-2">
						<div class="row">
							<div class="col-md-2">순위</div>
							<c:if test="${ChartMap.SGIMG != null }">
								<div class="ChartImg">
									<a href="/ChartInfoPage?sgcode=${ChartMap.SGCODE}"><img
										class="" src="${ChartMap.SGIMG}" alt="..." /></a>
								</div>
							</c:if>
							<c:if test="${ChartMap.SGIMG == null }">
								<div class="ChartImg">
									<a href="/ChartInfoPage?sgcode=${ChartMap.SGCODE}"><img
										class=""
										src="http://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg"
										alt="..." /></a>
								</div>
							</c:if>

							<div class="col-md-4">
								<div class="card m-2 pt-2 ps-2"
									style="width: 30%; display: -webkit-inline-box;">
									<p>${ChartMap.SGTITLE }</p>
								</div>
								<div class="card m-2 pt-2 ps-2"
									style="width: 30%; display: -webkit-inline-box;">
									<p>${ChartMap.SGINFO }</p>
								</div>

							</div>
							<div class="col-md-4">
								<div class="card m-2 pt-2 ps-2" style="width: 30%;">
									<i class="fa-solid fa-play"></i>
								</div>
								<div class="card m-2 pt-2 ps-2" style="width: 30%;">
									<i class="fa-solid fa-stop"></i>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>



				<ul class="pagination">
					<li class="page-item"><a class="page-link" href="#">Previous</a></li>
					<li class="page-item"><a class="page-link" href="#">1</a></li>
					<li class="page-item"><a class="page-link" href="#">2</a></li>
					<li class="page-item"><a class="page-link" href="#">3</a></li>
					<li class="page-item"><a class="page-link" href="#">4</a></li>
					<li class="page-item"><a class="page-link" href="#">5</a></li>
					<li class="page-item"><a class="page-link" href="#">Next</a></li>
				</ul>

			</div>

			<!-- Side widgets-->
			<%@ include file="/WEB-INF/views/Includes/Side.jsp"%>

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
