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
<title>Blog Page</title>
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
.BlogDiv{
	height:200px;
}
</style>

</head>
<body>
	<%@ include file="/WEB-INF/views/Includes/Menu.jsp"%>
	<!-- Page content-->
	<div class="container">
		<div class="row">
			<!-- Blog entries-->
			<div class="col-lg-8">
				<!-- Featured blog post-->

				<div class="card mb-4">
					<div class="BlogDiv" style="display: flex;">
						<div class="BlogImg">
							<a href="#블로그코드"><img class=""
								src="https://dummyimage.com/200x200/c1e3cd/ffffff.jpg" alt="..." /></a>
						</div>
						<div class="BlogText">
							<div class="small text-mute m-2"
								style="display: flex; justify-content: space-between; align-items: flex-end;">
								<a>작성자 1대1 채팅</a> <a>찜하기</a>
							</div>
							<div class="BlogTitle">
								<h2 class="card-title m-2">블로그제목</h2>
							</div>
							<!-- 새로운 div 추가 -->
							<div class="BlogContentsWrapper">
								<div class="BlogContents  p-2">
									<p class="card-text">블로그내용블라블라블라블라블라블라블라블라블라블라블라블라블라블라블라블라블라블라블라블라블라블라</p>
								</div>
							</div>
							<!-- 내용을 감싼 div에 스타일 적용 -->
							<div class="small text-mute mx-2"
								style="display: flex; justify-content: space-between; align-items: flex-end;">
								<a href="찜"><img alt="" src="/resources/assets/heart.png"></a>
								<a class="Views"
									style="text-decoration-line: none; color: gray;">조회수</a>
							</div>
						</div>
					</div>
				</div>
				<div class="card mb-4">
					<div class="BlogDiv" style="display: flex;">
						<div class="BlogImg">
							<a href="#블로그코드"><img class=""
								src="https://dummyimage.com/200x200/c1e3cd/ffffff.jpg" alt="..." /></a>
						</div>
						<div class="BlogText">
							<div class="small text-mute m-2"
								style="display: flex; justify-content: space-between; align-items: flex-end;">
								<a>작성자 1대1 채팅</a> <a>찜하기</a>
							</div>
							<div class="BlogTitle">
								<h2 class="card-title m-2">블로그제목</h2>
							</div>
							<!-- 새로운 div 추가 -->
							<div class="BlogContentsWrapper">
								<div class="BlogContents  p-2">
									<p class="card-text">블로그내용블라블라블라블라블라블라블라블라블라블라블라블라블라블라블라블라블라블라블라블라블라블라</p>
								</div>
							</div>
							<!-- 내용을 감싼 div에 스타일 적용 -->
							<div class="small text-mute mx-2"
								style="display: flex; justify-content: space-between; align-items: flex-end;">
								<a href="찜"><img alt="" src="/resources/assets/heart.png"></a>
								<a class="Views"
									style="text-decoration-line: none; color: gray;">조회수</a>
							</div>
						</div>
					</div>
				</div>
				<div class="card mb-4">
					<div class="BlogDiv" style="display: flex;">
						<div class="BlogImg">
							<a href="#블로그코드"><img class=""
								src="https://dummyimage.com/200x200/c1e3cd/ffffff.jpg" alt="..." /></a>
						</div>
						<div class="BlogText">
							<div class="small text-mute m-2"
								style="display: flex; justify-content: space-between; align-items: flex-end;">
								<a>작성자 1대1 채팅</a> <a>찜하기</a>
							</div>
							<div class="BlogTitle">
								<h2 class="card-title m-2">블로그제목</h2>
							</div>
							<!-- 새로운 div 추가 -->
							<div class="BlogContentsWrapper">
								<div class="BlogContents  p-2">
									<p class="card-text">블로그내용블라블라블라블라블라블라블라블라블라블라블라블라블라블라블라블라블라블라블라블라블라블라</p>
								</div>
							</div>
							<!-- 내용을 감싼 div에 스타일 적용 -->
							<div class="small text-mute m-2"
								style="display: flex; justify-content: space-between; align-items: flex-end;">
								<a href="찜"><img alt="" src="/resources/assets/heart.png"></a>
								<a class="Views"
									style="text-decoration-line: none; color: gray;">조회수</a>
							</div>
						</div>
					</div>
				</div>
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