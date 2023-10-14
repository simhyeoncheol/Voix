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
</head>



<body>
	<%@ include file="/WEB-INF/views/Includes/Menu.jsp"%>


	<!-- Responsive navbar-->
	<!-- Page content-->
	<div class="container">

		<!-- Blog entries-->

		<!-- Featured blog post-->

		<div class="newsTitle card col-md-12 mb-2">
			<h3>제목</h3>
		</div>


		<div class="row">

			<div class="col mb-4 m-2">
				<img alt="" src="https://i3.ruliweb.com/img/20/01/26/16fe1c27a9b49956a.png" style="width: 560px; height: 315px;">
			</div>

			<div class="card col mb-4 m-2">
				<div style="height: 315px; overflow: scroll; margin-left: 10px;">상품정보</div>
			</div>
		</div>


		<div class="card col mb-8">
		
		 <div class="row">
		 <div class="col-md-8 m-2">
			<input type="text" placeholder="주소" style="width: 760px;">
		 </div>
				<div class="col-md-2 m-2">
					<button>주소찾기</button>
				</div>
			</div>
			
			<input type="text" placeholder="상세주소" class="m-2"> 
			<input type="text" placeholder="요청사항" class="m-2"> 
			<input type="text" placeholder="받는사람" class="m-2">
		</div>

		<button style="align-items: center;">결제하기</button>

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
