<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>Blog Home - Start Bootstrap Template</title>
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
<!-- Favicon-->
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="resources/css/styles.css" rel="stylesheet" />
</head>
<body>
	<%@ include file="/WEB-INF/views/Includes/Menu.jsp"%>
	<div class="container">

		<!-- 컨텐츠 시작 -->
		<div class="card mb-4 mx-auto" style="width: 1000px;">
			<div class="card-body">
				<div>

					<div class="row">

						<c:choose>
							<c:when test="${sessionScope.loginState == 'YC'}">
								<c:choose>
									<c:when test="${sessionScope.loginProfile == null}">
										<%-- 등록된 프로필이 없는 경우 --%>
										<img style="width: 170px; height: 150px;" class="img-profile" src="${pageContext.request.contextPath}/resources/users/memberProfile/기본프로필.jpg" alt="일반 프로필1">
									</c:when>
									<c:otherwise>
										<%-- 등록된 프로필이 있는 경우 --%>
										<img style="width: 170px; height: 150px;" class="img-profile" src="${pageContext.request.contextPath}/resources/users/memberProfile/${sessionScope.loginProfile}" alt="일반 프로필2">
									</c:otherwise>
								</c:choose>
							</c:when>

							<c:otherwise>
								<img style="width: 170px; height: 150px;" class="img-profile" src="${sessionScope.loginProfile}" alt="카카오 프로필">
							</c:otherwise>
						</c:choose>


						<div class="col mb-2">
							<p class="mb-1">이름: ${mInfo.mname}</p>
							<p class="mb-1">이메일: ${mInfo.memail}</p>
							<p class="mb-3">주소: ${mInfo.maddr}</p>
							<a class="btn btn-danger" href="#" onclick="PwCheck()">내정보변경하기</a>
						</div>


					</div>

					<hr>
					<br>

					<div class="row">
						<div class="col" style="text-align: center;">
							<a class="btn" onclick="OrberClick()">구매내역</a>
						</div>
						<div class="col" style="text-align: center;">
							<a class="btn" onclick="LickClick()">찜목록</a>
						</div>
						<div class="col" style="text-align: center;">
							<a class="btn" onclick="CommentClick()">내가쓴 댓글 목록</a>
						</div>
					</div>

					<br>
					<hr>
					<div id="LickList" style="display: none;">
						<div>뉴스</div>
						<div class="card mb-4">
							<c:forEach items="${newsLikeList}" var="news">
								<div class="NewsDiv" style="display: flex;">
									<div class="NewsImg">
										<a href="#뉴스코드">
											<img style="width: 350px; height: 200px;" src="${news.NWIMG}" alt="..." />
										</a>
									</div>
									<div class="NewsText" style="flex: 1;">
										<div class="NewsTitle">
											<h4>${news.NWTITLE}</h4>
										</div>
										<div class="NewsContents p-2">
											<p class="card-text">${news.NWCONTENT}</p>
										</div>
										<div class="small text-mute m-2" style="display: flex; justify-content: space-between; align-items: flex-end;">
											<a href="찜">
												<img alt="" src="/resources/assets/heart.png">
											</a>
											<a class="Views" style="text-decoration-line: none; color: gray;">조회수:${news.NWBIGHIT }</a>
										</div>
									</div>
								</div>
								<hr>
							</c:forEach>
						</div>

						<div>블로그</div>
						<div class="card mb-4">
							<c:forEach items="${blogLikeList}" var="blog">
								<div class="blogDiv" style="display: flex;">
									<div class="blogImg">
										<a href="#블로그코드">
											<img style="width: 350px; height: 200px;" src="${blog.BGIMG}" alt="..." />
										</a>
									</div>
									<div class="BlogText" style="flex: 1;">
										<div class="Blogtitle">
											<h4>${blog.BGTITLE}</h4>
										</div>
										<div class="BlogContents p-2">
											<p class="card-text">${blog.BGCONTENT}</p>
										</div>
										<div class="small text-mute m-2" style="display: flex; justify-content: space-between; align-items: flex-end;">
											<a href="찜">
												<img alt="" src="/resources/assets/heart.png">
											</a>
											<a class="Views" style="text-decoration-line: none; color: gray;">조회수:${blog.BGBIGHIT }</a>
										</div>
									</div>
								</div>
								<hr>
							</c:forEach>
						</div>

						<div>앨범</div>
						<div>
							<div class="card mb-4">
								<div class="AlbumDiv" style="display: flex;">
									<c:forEach items="${albumsLikeList}" var="albums">
										<div class="AlbumImg">
											<a href="#앨범코드">
												<img class="" style="width: 350px; height: 200px;" src="${albums.ALIMG}" alt="..." />
											</a>
										</div>
										<div class="NewsText" style="flex: 1;">
											<div class="AlbumTitle">
												<h4 class="card-title m-2">${albums.ALTITLE}</h4>
											</div>
											<div class="AlbumContentsWrapper">
												<div class="AlbumText p-2 d-flex" style="justify-content: space-between;">
													<p class="card-text">${albums.ALARTIST}</p>
													<p class="card-text">${albums.ALINFO}</p>
												</div>
											</div>
											<div class="small m-2 d-flex" style="justify-content: space-between;">
												<p class="text-mute">${albums.ALPRICE}원</p>
												<a href="찜" class="">
													<img alt="" src="/resources/assets/heart.png">
												</a>
											</div>
										</div>
										<hr>
									</c:forEach>
								</div>
							</div>
						</div>

						<div>랭킹</div>
						<div>
							<div class="card mb-4">
								<div class="NewsDiv" style="display: flex;">

									<c:forEach items="${songsLikeList}" var="songs">
										<div class="NewsImg">
											<a href="#뉴스코드">
												<img style="width: 350px; height: 200px;" src="${songs.SGIMG}" alt="..." />
											</a>
										</div>
										<div class="NewsText" style="flex: 1;">
											<div class="NewsTitle">

												<h4>${songs.SGARTIST}</h4>
											</div>
											<div class="NewsContents p-2">
												<p class="card-text">${songs.SGINFO}</p>
											</div>
											<div class="small text-mute m-2" style="display: flex; justify-content: space-between; align-items: flex-end;">
												<a href="찜">
													<img alt="" src="/resources/assets/heart.png">
												</a>
												<a class="Views" style="text-decoration-line: none; color: gray;"></a>
											</div>
										</div>
										<hr>
									</c:forEach>


								</div>
							</div>
						</div>

						<div>티켓</div>
						<div>
							<div class="card mb-4">
								<div class="TicketDiv" style="display: flex;">
									<c:forEach items="${ticketsLikeList}" var="tickets">
										<div class="TicketImg">
											<a href="#티켓코드">
												<img class="" src="${tickets.TKIMG}" alt="..." />
											</a>
										</div>
										<div class="TicketContents w-100">
											<div class="TicketTitle">
												<h2 class="card-title m-2">${tickets.TKARTIST}</h2>
											</div>
											<div class="TicketContentsWrapper">
												<div class="TicketText p-2 d-flex" style="justify-content: space-between;">
													<p class="card-text">${tickets.TKINFO}</p>
													<p class="card-text small"></p>
												</div>
											</div>
											<div class="small text-mute m-2" style="text-align: end;">
												<a href="찜" class="">
													<img alt="" src="/resources/assets/heart.png">
												</a>
											</div>
										</div>
										<hr>
									</c:forEach>
								</div>
							</div>
						</div>

					</div>

					<div id="CommentList" style="display: none;">
						<div>뉴스</div>
						<div class="card mb-4">
							<c:forEach items="${newsReviewList}" var="news">
								<div class="NewsImg">
									<a href="#뉴스코드">
										<img class="" style="width: 350px; height: 200px;" src="${news.NWIMG}" alt="..." />
									</a>
								</div>
								<div class="NewsDiv" style="display: flex;">
									<div class="NewsText" style="flex: 1;">
										<h4>${news.NWTITLE}</h4>
										<div class="NewsTitle">
											<h4>${news.REWRITER}</h4>
										</div>
										<div class="NewsContents p-2">
											<p class="card-text">${news.RECONTENT}</p>
										</div>
										<div class="small text-mute m-2" style="display: flex; justify-content: space-between; align-items: flex-end;">
											<a class="Views" style="text-decoration-line: none; color: gray;">${news.REDATE }</a>
										</div>
									</div>
								</div>
								<hr>
							</c:forEach>
						</div>

						<div>블로그</div>
						<div class="card mb-4">
							<c:forEach items="${blogReviewList}" var="blog">
								<div class="BlogImg">
									<a href="#뉴스코드">
										<img class="" style="width: 350px; height: 200px;" src="${blog.BGIMG}" alt="..." />
									</a>
								</div>
								<div class="NewsDiv" style="display: flex;">
									<div class="NewsText" style="flex: 1;">
										<h4>${blog.BGTITLE }</h4>
										<div class="NewsTitle">
											<h4>${blog.REWRITER}</h4>
										</div>
										<div class="NewsContents p-2">
											<p class="card-text">${blog.RECONTENT}</p>
										</div>
										<div class="small text-mute m-2" style="display: flex; justify-content: space-between; align-items: flex-end;">
											<a class="Views" style="text-decoration-line: none; color: gray;">${blog.REDATE }</a>
										</div>
									</div>
								</div>
								<hr>
							</c:forEach>
						</div>

						<div>앨범</div>
						<div class="card mb-4">
							<c:forEach items="${albumsReviewList}" var="album">
								<div class="AlbumImg">
									<a href="#뉴스코드">
										<img class="" style="width: 350px; height: 200px;" src="${album.ALIMG}" alt="..." />
									</a>
								</div>
								<div class="NewsDiv" style="display: flex;">
									<div class="NewsText" style="flex: 1;">
										<h4>${album.ALTITLE}</h4>
										<div class="NewsTitle">
											<h4>${album.REWRITER}</h4>
										</div>
										<div class="NewsContents p-2">
											<p class="card-text">${album.RECONTENT}</p>
										</div>
										<div class="small text-mute m-2" style="display: flex; justify-content: space-between; align-items: flex-end;">
											<a class="Views" style="text-decoration-line: none; color: gray;">${album.REDATE }</a>
										</div>
									</div>
								</div>
								<hr>
							</c:forEach>
						</div>

						<div>티켓</div>
						<div class="card mb-4">
							<c:forEach items="${ticketReviewList}" var="ticket">
								<div class="TicketImg">
									<a href="#뉴스코드">
										<img class="" style="width: 350px; height: 200px;" src="${ticket.TKIMG}" alt="..." />
									</a>
								</div>
								<div class="NewsDiv" style="display: flex;">
									<div class="NewsText" style="flex: 1;">
										<h4>${ticket.TKTITLE}</h4>
										<div class="NewsTitle">
											<h4>${ticket.REWRITER}</h4>
										</div>
										<div class="NewsContents p-2">
											<p class="card-text">${ticket.RECONTENT}</p>
										</div>
										<div class="small text-mute m-2" style="display: flex; justify-content: space-between; align-items: flex-end;">
											<a class="Views" style="text-decoration-line: none; color: gray;">${ticket.REDATE }</a>
										</div>
									</div>
								</div>
								<hr>
							</c:forEach>
						</div>

						<div>랭킹</div>
						<div class="card mb-4">
							<c:forEach items="${songsReviewList}" var="song">
								<div class="SongsImg">
									<a href="#뉴스코드">
										<img class="" style="width: 350px; height: 200px;" src="${song.SGIMG}" alt="..." />
									</a>
								</div>
								<div class="NewsDiv" style="display: flex;">
									<div class="NewsText" style="flex: 1;">
										<h4>${song.SGTITLE}</h4>
										<div class="NewsTitle">
											<h4>${song.REWRITER}</h4>
										</div>
										<div class="NewsContents p-2">
											<p class="card-text">${song.RECONTENT}</p>
										</div>
										<div class="small text-mute m-2" style="display: flex; justify-content: space-between; align-items: flex-end;">
											<a class="Views" style="text-decoration-line: none; color: gray;">${song.REDATE }</a>
										</div>
									</div>
								</div>
								<hr>
							</c:forEach>
						</div>

					</div>

					<div id="OrderList" style="display: none;">
						<div>구매내역</div>
						<div class="card mb-4">
							<c:forEach items="${AlbumOrderList}" var="order">
								<div class="NewsDiv" style="display: flex; height: 200px; overflow: hidden;">
									<div class="NewsImg">
										<a href="#뉴스코드">
											<img style="width: 350px; height: 200px;" src="${order.ALIMG}" alt="..." />
										</a>
									</div>
									<div class="NewsText" style="flex: 1;">
										<div class="NewsTitle">
											<h4>${order.ALTITLE}</h4>
										</div>
										<div class="NewsContents p-2">
											<p class="card-text">${order.ODQTY}</p>
											<p class="card-text">${order.ODPRICE}</p>
											<p class="card-text">${order.ODADDR}</p>
											<p class="card-text">${order.ODDATE}</p>
										</div>
									</div>
								</div>
								<hr>
							</c:forEach>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>
	<!-- 컨텐츠 끝 -->




	<!-- Footer-->
	<footer class="py-5 bg-dark">
		<div class="container">
			<p class="m-0 text-center text-white">위 페이지의 출력되는 정보는 우측 상단에 있는 데이터 클롤링 및 페이지 양식을 인용하여 제작되었습니다.</p>
		</div>
	</footer>
	<!-- Bootstrap core JS-->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
	<!-- Core theme JS-->
	<script src="js/scripts.js"></script>
	<script>
		let loginPw = '<c:out value="${sessionScope.loginPw}" />'; // 세션에서 비밀번호 가져오기
		function PwCheck() {
			console.log('호출성공');
			let pw = prompt('비밀번호를 입력해주세요.'); // 변수 이름을 'pw'로 수정
			if (pw === loginPw) {
				window.location.href = "/MyInfoUpdate";
			} else {
				alert('비밀번호가 일치하지 않습니다. 다시 시도해주세요');
			}
		}
	</script>
	<script>
		function OrberClick() {
			// '구매내역' 클릭 시 실행할 동작
			document.getElementById('OrderList').style.display = 'block';
			document.getElementById('LickList').style.display = 'none';
			document.getElementById('CommentList').style.display = 'none';
		}

		function LickClick() {
			// '찜목록' 클릭 시 실행할 동작
			document.getElementById('OrderList').style.display = 'none';
			document.getElementById('LickList').style.display = 'block';
			document.getElementById('CommentList').style.display = 'none';
		}

		function CommentClick() {
			// '내가쓴 댓글 목록' 클릭 시 실행할 동작
			document.getElementById('OrderList').style.display = 'none';
			document.getElementById('LickList').style.display = 'none';
			document.getElementById('CommentList').style.display = 'block';
		}
	</script>
</body>
</html>
