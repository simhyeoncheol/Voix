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
<style type="text/css">
table, tr, td {
	border: 1px solid black;
	border-collapse: collapse;
	padding: 5px;
	text-align: center;
}
</style>
</head>
<body>
	<!-- Responsive navbar-->
	<%@ include file="/WEB-INF/views/Includes/Menu.jsp"%>
	<!-- Page content-->
	<div class="container">
				<div class="newTitle card col-md-12 mb-2">
					<h3>제목</h3>
				</div>
	
		<div class="card m-2">
			<div class="row">
				<div class="col-md-1 m-1">
					<button class="mt-3">asdf</button>
				</div>
				<div class="col-md-10 m-2">
					<table>
						<tr>
							<td rowspan="2" style="width: 10%"><img alt="앨범포스터" src="http://t1.daumcdn.net/friends/prod/editor/dc8b3d02-a15a-4afa-a88b-989cf2a50476.jpg" style="width: 100%;" class="card-img-top"></td>
							<td>상품이름</td>
							<td>가격</td>
							<td rowspan="2">수량</td>
						</tr>
						<tr>
							<td>어디사이트</td>
							<td>할인가격</td>
						</tr>
					</table>
				</div>
			</div>
		</div>

		<div class="card m-2">
			<div class="row" style="justify-content: center; align-items: center;">
				<div class="card col-md-4 m-2">선택상품 + 총 배송비</div>
				<div class="card col-md-4 m-2">주문금액</div>
				<div class="card col-md-2 m-2">
					<button>결제</button>
				</div>
			</div>
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
