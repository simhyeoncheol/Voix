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
		<div class="col-md-12 mb-4 text-center">
			<h2>주문내역</h2>
		</div>
		<c:forEach items="${OrderInfo}" var="OrderInfo" varStatus="loop">
			<div class="card m-2 CartList" id="CartList${loop.index}">
				<div class="row">
					<div class="col-md-12 m-2">
						<table class="border-0">
							<tr>
								<td rowspan="2" style="width: 150px;height:150px;">
								<a href="/AlbumInfoPage?alcode=${OrderInfo.ALCODE}"><img alt="앨범포스터" src="${OrderInfo.ALIMG}"
								style="width: 100%;height: 100%;object-fit: cover;" class="card-img-top"></a>
								</td>
								<td class="w-50"><h3>${OrderInfo.ALTITLE }</h3></td>
								<td><h5>${OrderInfo.ODPRICE }원</h5></td>
								<td id="odqty" rowspan="2"><h5>${OrderInfo.ODQTY}개</h5></td>
								<td> <h5>${OrderInfo.ODDATE }</h5> </td>
								<td style="width:110px;overflow:hidden;"> <h5>${OrderInfo.ODADDR }</h5> </td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
	<!-- Footer-->
	<footer class="py-5 bg-dark" id="footer">
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
