<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<link href="resources/css/styles.css" rel="stylesheet" />
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>Album Page</title>

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

.thDiv {
	border: 3px solid #ccc;
	border-radius: 10px;
	padding: 10px;
	font-size: 30px;
	text-align: center;
	background-color: #5e504e;
	color: #ede9e7;
}

.likeImg {
	position: absolute;
	right: 5px;
	bottom: 5px;
}
</style>

</head>
<body>
	<%@ include file="/WEB-INF/views/Includes/Menu.jsp"%>
	<!-- Page content-->
	<div class="container">
		<div class="row">
			<!-- Ticket entries-->
			<div class="col-lg-9">
				<!-- Featured Ticket post-->

				<div class="AlGenre mb-2" style="display: flex;">
					<div class="thDiv" style="width: 700px;">가요</div>
					<div class="thDiv" style="width: 700px;">팝송</div>
				</div>

				<div style="display: flex;">

					<div style="width: 700px;">
						<c:forEach items="${AlbumListMap}" var="AlbumMap">
							<c:if test="${AlbumMap.ALGENRE == '가요'}">
								<table>
									<tr>
										<td>
											<div class="card mb-2 " style="height: 173px;">
												<div class="AlbumDiv VOIXBODERLINE" style="display: flex; overflow: hidden;">
													<div class="AlbumImg">
														<c:if test="${AlbumMap.ALIMG != null }">
															<a href="/AlbumInfoPage?alcode=${AlbumMap.ALCODE}"><img style="height: 170px; width: 170px;" src="${AlbumMap.ALIMG}" alt="..." /></a>
														</c:if>
														<c:if test="${AlbumMap.ALIMG == null }">
															<a href="/AlbumInfoPage?alcode=${AlbumMap.ALCODE}"><img class="" src="https://dummyimage.com/150x150/c1e3cd/ffffff.jpg" alt="..." /></a>
														</c:if>
													</div>
													<div class="AlbumContents" >
														<div class="AlbumTitle">
															<h5 class="card-title m-2">${AlbumMap.ALTITLE}</h5>
														</div>
														<div class="AlbumContentsWrapper">
															<div class="AlbumText px-2 d-flex" style="justify-content: space-between;">
																<p class="card-text">${AlbumMap.ALARTIST}</p>
															</div>
															<div class="small m-2 d-flex" style="justify-content: space-between; align-items: flex-end;">
																<p class="card-text" style="font-size: 12px;">${AlbumMap.ALDATE}</p>
															</div>
														</div>
														<div class="small m-2 d-flex" style="justify-content: space-between; position: absolute; bottom: 0; right: 0">
															
														</div>
													</div>
												</div>
											</div>
										</td>
									</tr>
								</table>
							</c:if>
						</c:forEach>
					</div>

					<div style="width: 700px;">
						<c:forEach items="${AlbumListMap}" var="AlbumMap">
							<c:if test="${AlbumMap.ALGENRE == '팝송'}">
								<table>
									<tr>
										<td>
											<div class="card mb-2" style="height: 173px;">
												<div class="AlbumDiv VOIXBODERLINE" style="display: flex; overflow: hidden;">
													<div class="AlbumImg">
														<c:if test="${AlbumMap.ALIMG != null }">
															<a href="/AlbumInfoPage?alcode=${AlbumMap.ALCODE}"><img style="height: 170px; width: 170px;" src="${AlbumMap.ALIMG}" alt="..." /></a>
														</c:if>
														<c:if test="${AlbumMap.ALIMG == null }">
															<a href="/AlbumInfoPage?alcode=${AlbumMap.ALCODE}"><img class="" src="https://dummyimage.com/150x150/c1e3cd/ffffff.jpg" alt="..." /></a>
														</c:if>
													</div>
													<div class="AlbumContents">
														<div class="AlbumTitle">
															<h5 class="card-title m-2">${AlbumMap.ALTITLE}</h5>
														</div>
														<div class="AlbumContentsWrapper">
															<div class="AlbumText px-2 d-flex" style="justify-content: space-between;">
																<p class="card-text">${AlbumMap.ALARTIST}</p>
															</div>
															<div class="small m-2 d-flex" style="justify-content: space-between; align-items: flex-end;">
																<p class="card-text" style="font-size: 12px;">${AlbumMap.ALDATE}</p>
															</div>
														</div>
														<div class="small m-2 d-flex" style="justify-content: space-between; position: absolute; bottom: 0; right: 0">
															
														</div>
													</div>
												</div>
											</div>
										</td>
									</tr>
								</table>
							</c:if>
						</c:forEach>
					</div>
				</div>

				<!-- <button id="myButtonPop" type="button">팝송크롤링</button> -->
				<!-- <button id="myButtonKpop" type="button">가요크롤링</button> -->

			</div>
			<!-- end Page content-->
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

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script>
		$(document).ready(function() {
			$("#myButtonPop").click(function() {
				$.ajax({
					url : "/getYes24Pop",
					type : 'GET',
					success : function(result) {
						console.log(result);
					}
				});
			});
		});
	</script>
	<script>
		$(document).ready(function() {
			$("#myButtonKpop").click(function() {
				$.ajax({
					url : "/getYes24Kpop",
					type : 'POST',
					success : function(result) {
						console.log(result);
					}
				});
			});
		});
	</script>

</body>
</html>
