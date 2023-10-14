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
<title>Ticket Page</title>
<!-- Favicon-->
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
</style>

</head>
<body>
	<%@ include file="/WEB-INF/views/Includes/Menu.jsp"%>
	<!-- Page content-->
	<div class="container">
		<div class="row">
			<!-- Ticket entries-->
			<div class="col-lg-8">
				<div class="w-100">
					<div class="list-group">
						<ul class="list-group list-group-horizontal"
							style="list-style: none;">
							<li class="w-25"><a class="list-group-item">멜론</a></li>
							<li class="w-25"><a class="list-group-item">인터파크</a></li>
							<li class="w-25"><a class="list-group-item">예스24</a></li>
							<li class="w-25"><a class="list-group-item">벅스</a></li>
						</ul>
					</div>
				</div>



				<!-- Featured Ticket post-->
				<c:forEach items="${TkListMap}" var="TkMap">
					<div class="card mb-4">
						<div class="TicketDiv" style="display: flex;">
							<c:if test="${TkMap.TKIMG != null }">
								<div class="TicketImg">
									<a href="/TicketInfoPage?tkcode=${TkMap.TKCODE}"><img class=""
										src="${TkMap.TKIMG}" alt="..." /></a>
								</div>
							</c:if>
							<c:if test="${TkMap.TKIMG == null }">
								<div class="TicketImg">
									<a href="/TicketInfoPage?tkcode=${TkMap.TKCODE}"><img class=""
										src="https://dummyimage.com/150x150/c1e3cd/ffffff.jpg"
										alt="..." /></a>
								</div>
							</c:if>
							<div class="TicketContents w-100">
								<div class="TicketTitle">
									<h2 class="card-title m-2">${TkMap.TKTITLE}</h2>
								</div>
								<div class="TicketContentsWrapper">
									<div class="TicketText p-2 d-flex"
										style="justify-content: space-between;">
										<p class="card-text">${TkMap.TKINFO}</p>
										
										<div>
										<p class="card-text small">${TkMap.TKDATE}</p>
										<br>
										<p class="card-text small">${TkMap.TKPLACE}</p>
										<br>
										<p class="card-text small">${TkMap.TKTIME}</p>
										<br>
										<p class="card-text small">${TkMap.TKARTIST}</p>
										</div>
									</div>
								</div>
								<div class="small text-mute m-2" style="text-align: end;">
									<a href="찜" class=""><img alt=""
										src="/resources/assets/heart.png"></a>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>



			</div>
			<!-- end Page content-->
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
