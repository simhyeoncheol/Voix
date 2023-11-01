<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>Blog Home - Start Bootstrap Template</title>
<!-- Favicon-->
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="resources/css/styles.css" rel="stylesheet" />
<script src="https://kit.fontawesome.com/acc1ccb443.js" crossorigin="anonymous"></script>

<style type="text/css">
.pagination {
	list-style-type: none;
	display: inline-block;
}

.pagination li {
	display: inline;
	margin-right: 5px;
}

.pagination li a {
	text-decoration: none;
	padding: 5px 10px;
	border: 1px solid #ccc;
	background-color: #f7f7f7;
}

.rankChart {
	background-color: #5e504e;
	margin-left: 7px;
	margin-top: 5px;
	margin-bottom: 5px;
	border-radius: 5px;
	text-align: center;
	color: #ede9e7;
	font-weight: bold;
	font-size: 18px;
	width: 170px;
}
</style>
</head>
<body>
	<!-- Responsive navbar-->
	<%@ include file="/WEB-INF/views/Includes/Menu.jsp"%>
	<!-- Page content-->
	<div class="container">
		<div class="row">
			<!-- Blog entries-->
			<div class="col-lg-9">
				<div class="row">
					


						<c:choose>
							<c:when test="${param.page != null }">
								<c:set value="${param.page }" var="pageNumber" />
							</c:when>
							<c:otherwise>
								<c:set value="1" var="pageNumber" />
							</c:otherwise>
						</c:choose>

						<c:forEach items="${ChartList}" var="ChartMap" varStatus="status">
							<div class="card col-md-6 m-2 VOIXBODERLINE" style="height: 215px; width: 48%; overflow: hidden;">

								<div class="rankChart">No.${(pageNumber-1)*10 + status.index + 1}</div>
								<div style="display: flex;">
									<c:if test="${ChartMap.SGIMG != null }">
										<div class="ChartImg align-items-center" style="margin-left: 7px; margin-right: 10px;">
											<a href="/ChartInfoPage?sgcode=${ChartMap.SGCODE}">
												<img style="width: 170px; height: 170px;" src="${ChartMap.SGIMG}" class="VOIXBODERLINE" />
											</a>
										</div>
									</c:if>
									<c:if test="${ChartMap.SGIMG == null }">
										<div class="ChartImg align-items-center" style="margin-left: 7px; margin-right: 10px;">
											<a href="/ChartInfoPage?sgcode=${ChartMap.SGCODE}">
												<img style="width: 170px; height: 170px;" src="http://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg" alt="..." />
											</a>
										</div>
									</c:if>


									<div class="">
										<div class="pt-2 ps-2" style="width: 550px; display: -webkit-inline-box;">
											<h2>${ChartMap.SGTITLE }</h2>
										</div>
										<div class="pt-2 ps-2" style="width: 70%; display: -webkit-inline-box;">
											<p>${ChartMap.SGINFO }</p>
										</div>
									</div>

								</div>
							</div>
					</c:forEach>
					
				</div>
				<ul class="pagination" style="place-content: center;">
					<c:if test="${pageMaker.prev }">
						<li><a href="/ChartPage?page=${pageMaker.startPage-1}" style="color: #5e504e">
								<i class="fa fa-chevron-left"></i>
							</a></li>
						<!-- <a href='<c:url value="/NewsPage?page=${pageMaker.startPage-1 }"/>'><i class="fa fa-chevron-left"></i></a> -->
					</c:if>
					<c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="pageNum">
						<li><a href='<c:url value="/ChartPage?page=${pageNum }"/>' style="color: #5e504e">
								<i class="fa">${pageNum }</i>
							</a></li>
					</c:forEach>
					<c:if test="${pageMaker.next && pageMaker.endPage >0 }">
						<li><a href='<c:url value="/ChartPage?page=${pageMaker.endPage+1 }"/>' style="color: #5e504e">
								<i class="fa fa-chevron-right"></i>
							</a></li>
					</c:if>
				</ul>

			</div>

			<!-- Side widgets-->
			<%@ include file="/WEB-INF/views/Includes/Side.jsp"%>

		</div>
	</div>
	<!-- Footer-->
	<footer class="py-5 bg-dark">
		<div class="container">
			<p class="m-0 text-center text-white">위 페이지의 출력되는 정보는 우측 상단에 있는 데이터 클롤링 및 페이지 양식을 인용하여 제작되었습니다.</p>
		</div>
	</footer>
	<!-- Bootstrap core JS-->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
	<!-- Core theme JS-->
	<script src="/resources/js/scripts.js"></script>
</body>
</html>
