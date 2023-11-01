

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<style>
.img-profile{
	width: 60%;
    border-radius: 100%;
}
.fontcolor{
	color: #5e504e;
}
</style>


<!-- Side widgets-->
<div class="col-lg-3">

	<c:choose>
		<c:when test="${sessionScope.loginId == null }">
			<!-- Search widget-->
			<div class="card mb-4">
				<div class="card-header" style="text-align: center;">로그인 후 이용
					해주세요!</div>
				<div class="card-body">
					<div class="input-group">
						<a href="/LoginPage" class="btn btn-primary" style="width: 100%;">로그인</a>
					</div>
				</div>
			</div>
		</c:when>

		<c:otherwise>
			<!-- Search widget-->
			<div class="card mb-4">
				<div class="card-header fontcolor" style="text-align: center;">${sessionScope.loginId} ${sessionScope.loginName }</div>
				<div class="card-body">
					<div class="loginInfo">
						<div class="d-flex justify-content-between">

							<c:choose>
								<c:when test="${sessionScope.loginState == 'YC' }">
									<c:choose>
										<c:when test="${sessionScope.loginProfile == null}">
											<%-- 등록된 프로필이 없는 경우 sessionScope.loginProfile --%>
											<img class="img-profile"
												src="${pageContext.request.contextPath }/resources/users/memberProfile/기본프로필.jpg">
										</c:when>
										<c:otherwise>
											<%-- 등록된 프로필이 있는 경우 --%>
											<img class="img-profile" 
												src="${pageContext.request.contextPath }/resources/users/memberProfile/${sessionScope.loginProfile }">
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<img class="img-profile "
										src="${sessionScope.loginProfile }">
								</c:otherwise>
							</c:choose>
							<div class="" style="text-align: center;align-self: center;width: 40%;">
							<div class="mb-3">
								<a class="nav-link fontcolor" href="/memberLogout">로그아웃</a>
							</div>
							<div class="mb-3">
								<a class="nav-link fontcolor" href="/MyInfoPage">내정보</a>
							</div>
							<div class="mb-3">
								<a class="nav-link fontcolor" href="/CartPage">장바구니</a>
							</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<!-- Categories widget-->
			<div class="card mb-4">
				<div class="card-header fontcolor">플레이리스트</div>
				
				<div class="">
					<div class="row">
						<div class="">
							<div id="playlist">

								<c:choose>
									<c:when test="${sessionScope.sideState == 'M'}">
										<!-- 랭킹비교, 메인페이지일때 -->
										<c:forEach items="${playlist}" var="pl">
											<div class="card-body d-flex">
												<div class="card-img">
													<img alt="" class="me-5" style="width: 35%;"
														src="${pl.SGIMG }">
													<i onclick="searchKey('${pl.SGTITLE}')"
														class="bi bi-play-circle me-3" style="font-size: 35px;"></i>
													<i onclick=""
														class="bi bi-arrow-through-heart-fill"
														style="font-size: 35px;"></i>
												</div>
											</div>
											<div class="card-text card-body text-lg-center">
												<a>${pl.SGTITLE }</a> <br> <a>${pl.SGARTIST } </a>
											</div>
											<hr>
										</c:forEach>
									</c:when>

									<c:when test="${sessionScope.sideState == 'N'}">
										<!-- 뉴스,블로그,티켓 일때 -->
										<c:forEach var="pl" items="${sessionScope.playlist}" end="1">
											<div class="card-body d-flex">
												<div class="card-img">
													<div id="player"></div>
													<img alt="" class="me-5" style="width: 35%;"
														src="${pageContext.request.contextPath }/resources/users/album/${pl.sgimg }">
													<a href="" class="me-3" id="playbtn"> <i
														class="bi bi-play-circle" style="font-size: 35px;"></i></a> <a
														href="찜"> <i class="bi bi-arrow-through-heart-fill"
														style="font-size: 35px;"></i>
													</a>
												</div>
											</div>
											<div class="card-text card-body text-lg-center">
												<a>${pl.sgtitle }</a> <br> <a>${pl.sgartist } </a>
											</div>
										</c:forEach>
									</c:when>
								</c:choose>
							</div>
						</div>
					</div>
				</div>
			</div>

			<c:choose>
				<c:when test="${sessionScope.sideState == 'N'}">
					<!-- 조회수 뉴스,블로그,티켓,앨범페이지 -->
					<c:choose>
					<c:when test="${sessionScope.rankState == 'NW'}" >
					<div class="card mb-4">
						<div class="card-header fontcolor">높은 조회수</div>
						<div class="card-body">
							<div class="" style="border-bottom: 2px solid black;" id = "NewsHit">
							
							</div>
						</div>
					</div>
					</c:when>
					<c:when test="${sessionScope.rankState == 'BL'}" >
					<div class="card mb-4">
						<div class="card-header fontcolor">높은 조회수</div>
						<div class="card-body">
							<div class="" style="border-bottom: 2px solid black;" id = "BlogHit">
							
							</div>
						</div>
					</div>
					</c:when>
					</c:choose>

				</c:when>
				<c:when test="${sessionScope.sideState == 'P'}">
					<!-- 가격비교 페이지일때 -->
					<div class="card mb-4">
						<div class="card-header">가격비교</div>
						<div class="card-body">
							<div id="carouselExampleInterval" class="carousel slide" data-bs-ride="carousel">
								<div class="carousel-inner">
								<div class="carousel-item active" data-bs-interval="2000">
									<a href="https://pay.genie.co.kr/buy/geniePack">
										<img src="https://corp.kt.com/images/kt/kt-ci.png?ver=2022120801" class="d-block w-70" alt="...">
									</a>
									</div>
									<div class="carousel-item" data-bs-interval="2000">
										<a href="https://www.music-flo.com/purchase/skt">
										<img src="data:image/png;base64,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" class="d-block w-70" alt="...">
									</a>
									</div>
									<div class="carousel-item" data-bs-interval="2000">
										<a href="https://www.lguplus.com/pogg/main/prod-detail?dispProdNo=1052">
										<img src="https://www.lg.co.kr/images/common/default_og_image_new.jpg" class="d-block w-70" alt="...">
									</a>
									</div>
								</div>
								<button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleInterval" data-bs-slide="prev">
									<span class="carousel-control-prev-icon" aria-hidden="true"></span> <span class="visually-hidden">Previous</span>
								</button>
								<button class="carousel-control-next" type="button" data-bs-target="#carouselExampleInterval" data-bs-slide="next">
									<span class="carousel-control-next-icon" aria-hidden="true"></span> <span class="visually-hidden">Next</span>
								</button>
							</div>
						</div>
					</div>
				</c:when>

			</c:choose>


		</c:otherwise>

	</c:choose>

</div>
<div class="playerDiv d-none" id="playerDiv" >

</div>
<script type="text/javascript">
var tag = document.createElement('script');
tag.src = "https://www.youtube.com/iframe_api";
var firstScriptTag = document.getElementsByTagName('script')[0];
firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

	
	function searchKey(searchKey){
	let playerDiv = document.querySelector('#playerDiv');
		playerDiv.innerHTML="";
	let ytdiv = document.createElement('div');
	ytdiv.setAttribute('id','player');
	playerDiv.appendChild(ytdiv);
	
	var player = document.querySelector("#test01 > div");
	console.log(playerDiv)
	var searchKeyWord = searchKey;
	console.log(searchKey);
	$.ajax({
		type : 'get',		
		url : 'https://www.googleapis.com/youtube/v3/search',
		data : ({
			'part' : 'snippet',
			'maxResult' : '1',
			'q' : searchKeyWord,
			'type' : 'video',
			'key' : 'AIzaSyDwUScxFiKbqnG1VbtM33itvJtDmaIdKI8'
		}),
		dataType : 'json',
		async : false,
		success : function onYouTubeIframeAPIReady(result){
			console.log(result);
			console.log(result.items[0].id.videoId);
			
			let videoId = result.items[0].id.videoId;
			
			
			player = new YT.Player('player', {
				videoId : videoId,
				playerVars : {
					'controls' : 0,
				},
				events : {
					'onReady' : onPlayerReady,
					'onStateChange' : onPlayerStateChange,
					'onError' : onPlayerError
				}
			});
		console.log(playerDiv)
			setTimeout(() => player.playVideo(), 1000);
			}
		});

	}
	
	function onPlayerReady(event) {
		event.target.setVolume(100);
	}
	
	function onPlayerStateChange(event) {
		if (event.data == YT.PlayerState.ENDED) {
			// 동영상이 종료되면 필요한 작업을 수행할 수 있습니다.
		}
	}

	function onPlayerError(event) {
		// 동영상 재생 중에 오류가 발생한 경우 처리할 수 있습니다.
	}
	
	function playVideo() {
		if (player) {
			player.playVideo();
		}
	}
    function stopVideo() {
        player.stopVideo();
      }
	
</script>
<script type="text/javascript">
if (${sessionScope.rankState == 'NW'}) {
    $.ajax({
        type: "GET",
        url: "NewsHitList",
        dataType: 'json',
        success: function (result) {
            var newsHitDiv = document.getElementById("NewsHit");

            for (let i = 0; i < result.length; i++) {
                var hitNews = result[i];

                // Create a new div element
                var newsHitItemDiv = document.createElement("div");
                newsHitItemDiv.className = "news-hit-item";

                // Create a new anchor link
                var newsHitItem = document.createElement("a");
                newsHitItem.href = "javascript:void(0);"; // Set a dummy href

                // Set data-nwcode as a custom data attribute
                newsHitItem.setAttribute("data-nwcode", hitNews.NWCODE);

                newsHitItem.textContent = (i + 1) + ". " + hitNews.NWTITLE; // Adding the number
                newsHitItem.onclick = function () {
                    // Define an onclick function
                    // Get the NWCODE from the data attribute
                    var nwcode = this.getAttribute("data-nwcode");
                    // Redirect to the NewsInfoPage with the NWCODE
                    window.location.href = "NewsInfoPage?nwcode=" + nwcode;
                };

                // Append the anchor link to the div
                newsHitItemDiv.appendChild(newsHitItem);
                // Append the div to the NewsHit div
                newsHitDiv.appendChild(newsHitItemDiv);
            }
        },
    });
}
if (${sessionScope.rankState == 'BL'}) {
    $.ajax({
        type: "GET",
        url: "BlogHitList",
        dataType: 'json',
        success: function (result) {
            var BlogHitDiv = document.getElementById("BlogHit");

            for (let i = 0; i < result.length; i++) {
                var hitBlog = result[i];

                // Create a new div element
                var BlogHitItemDiv = document.createElement("div");
                BlogHitItemDiv.className = "Blog-hit-item";

                // Create a new anchor link
                var BlogHitItem = document.createElement("a");
                BlogHitItem.href = "javascript:void(0);"; // Set a dummy href

                // Set data-nwcode as a custom data attribute
                BlogHitItem.setAttribute("data-bgcode", hitBlog.BGCODE);

                BlogHitItem.textContent = (i + 1) + ". " + hitBlog.BGTITLE; // Adding the number
                BlogHitItem.onclick = function () {
                    // Define an onclick function
                    // Get the NWCODE from the data attribute
                    var bgcode = this.getAttribute("data-bgcode");
                    // Redirect to the NewsInfoPage with the NWCODE
                    window.location.href = "BlogInfoPage?bgcode=" + bgcode;
                };

                // Append the anchor link to the div
                BlogHitItemDiv.appendChild(BlogHitItem);
                // Append the div to the NewsHit div
                BlogHitDiv.appendChild(BlogHitItemDiv);
            }

        },
    });
}
</script>

<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
