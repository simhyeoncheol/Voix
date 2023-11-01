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
line {
	display: inline-block;
}

albuminfo {
	padding: 5px !important;
}

button {
	width: 100%;
	background-color: gray;
	border: none;
}

.Disnone {
	display: none;
}

.infoo {
	list-style-type: none;
	word-break: break-all;
	margin-left: -42px;
	font-size: larger;
    display: grid;
    height: 100%;
}

.textdiv {
	height: 100%;
	max-height:150px;
    background: #f8f9fa;
    overflow: scroll;
    overflow-x: hidden;
}
.textdiv::-webkit-scrollbar {
  width: 10px;
}

.textdiv::-webkit-scrollbar-track {
  background: #f8f9fa; /* Track color */
}

.textdiv::-webkit-scrollbar-thumb {
  background-color: #888; /* Thumb color */
  border-radius: 10px	; /* Rounded thumb */
}
</style>
</head>
<body>
	<!-- Responsive navbar-->
	<%@ include file="/WEB-INF/views/Includes/Menu.jsp"%>
	<!-- Page content-->
	<div class="container">
		<div>
			<div class="row">

				<div class="col-md-5 mb-4">
					<img alt="공연포스터" src="${tk.tkimg}" style="width: 100%;" class="card-img-top">
				</div>
				<div class="card col-md-7 mb-4">
					<ul class="infoo">
						<li class="infooItem"><strong class="infooLabel">공연제목</strong>
							<div class="infooDesc">
								<p class="infooText">${tk.tktitle}</p>
							</div></li>

						<li class="infooItem"><strong class="infooLabel">출연진</strong>
							<div class="infooDesc">
								<p class="infooText">${tk.tkartist}</p>
							</div></li>
						<li class="infooItem"><strong class="infooLabel">장소</strong>
							<div class="infooDesc">
								<p class="infooText" id="tkplace">${tk.tkplace}</p>
							</div></li>
						<li class="infooItem"><strong class="infooLabel">공연기간</strong>
							<div class="infooDesc">
								<p class="infooText">${tk.tkdate}</p>
							</div></li>
						<li class="infooItem"><strong class="infooLabel">공연시간</strong>
							<div class="infooDesc">
								<p class="infooText">${tk.tktime}</p>
							</div></li>
						<li class="infooItem"><strong class="infooLabel">공연정보</strong>
							<div class="infooDesc">
								<p class="infooText">${tk.tkinfo}</p>
							</div></li>

					</ul>
					<!--
					<p>${tk.tktitle}</p>
					<p>${tk.tkartist}</p>
					<p id="tkplace">${tk.tkplace}</p>
					<p>${tk.tktime}</p>
					<p>${tk.tkdate}</p>
					<p>${tk.tkinfo}</p>
					-->
				</div>


			</div>
		</div>

		<div>
			<div class="row">
				<div class="col-md-10 mb-2" id="mapInfo">
					<div id="map" style="width: 100%; height: 450px; border-radius: 10px;"></div>
				</div>
				<div class="col-md-2 mb-2 " style="display: inline-block;">
					<div style="height: 100%;display: grid;place-items: center;">
						<button class="btn btn-success" style="font-size: x-large;height: 100%;"onclick="location.href='${tk.tkurl}'">바로가기</button>
					</div>
				</div>
			</div>
		</div>

		<div class="borderline textdiv" style="overflow: scroll; height:100%; max-height: 500px; width: 100%; overflow-x: hidden; background: white;">
			<div class="replyArea">
				<div class="row my-3 scroll" style="width: 100%; margin-left: 5px; padding: 0px; display: inline-block; height: auto; max-height: 450px;">
					<c:forEach items="${reviewList}" var="re">
						<div class="meminfo">
							<span style="font-size: larger;">작성자: ${re.REWRITER} </span>
							<div style="margin-top: 5px; margin-bottom: 5px;">
								<div class="textdiv w-100" style="font-size: large; border:1px solid #cccc;">${re.RECONTENT}</div>
							</div>
							<c:if test="${sessionScope.loginId == re.REWRITER}">
								<button type="button" onclick="location.href='/deleteReview?recode=${re.RECODE}&tkcode=${tk.tkcode}'" class="btn btn-danger" style="font-size: 14px; margin-bottom: 4px; width: 88px; height: 33px; float: right;">댓글 삭제</button>
							</c:if>
							<div class="small text-muted">작성시간: ${re.REDATE}</div>
						</div>
						<hr>
					</c:forEach>
				</div>
			</div>
		</div>
		<c:if test="${sessionScope.loginId != null }">
			<div class="replyWrite">
				<form action="registReview" class="my-3 d-flex" method="post">
					<input type="text" name="restate" value="${tk.tkcode }" style="display: none">
					<textarea class="reviewComment" name="recontent" placeholder="댓글을 작성해보세요." style="width: 85%;font-size: large;"></textarea>
					<input class="btn btn-success " type="submit" value="댓글 등록" style="width:15%;">
				</form>
			</div>
			<hr>
		</c:if>
	</div>

	<!-- Footer-->
	<footer class="py-5 bg-dark">
		<div class="container">
			<p class="m-0 text-center text-white">위 페이지의 출력되는 정보는 우측 상단에 있는 데이터 클롤링 및 페이지 양식을 인용하여 제작되었습니다.</p>
		</div>
	</footer>

	<!-- Bootstrap core JS-->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
	<script src="https://kit.fontawesome.com/acc1ccb443.js" crossorigin="anonymous"></script>
	<!-- Core theme JS-->
	<script src="/resources/js/scripts.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=18a5f71bbe7bc58965ce6ce689f1e211"></script>
	<script type="text/javascript">
		let tkplace = document.getElementById('tkplace').innerHTML;
		let xMap = null;
		let yMap = null;
		$.ajax({
			type : "get",
			url : "getMapXY",
			data : {
				"tkplace" : tkplace
			},
			async : false,
			dataType : "json",
			success : function(result) {
				console.log('xy성공');
				yMap = result.x;
				xMap = result.y;
			}
		});
		let tktitle = null;
		$.ajax({
			type : "get",
			url : "getTkTitle",
			data : {
				"tkplace" : tkplace
			},
			async : false,
			success : function(result) {
				console.log(result);
				tktitle = result;
			}
			
		});
		var mapContainer = document.getElementById('map'), mapCenter = new kakao.maps.LatLng(
				xMap, yMap), mapOption = {
			center : mapCenter,
			level : 4
		};
		var map = new kakao.maps.Map(mapContainer, mapOption);

		kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
			var latlng = mouseEvent.latLng;
		});
		var mMarker = new kakao.maps.Marker({
			position : mapCenter,
			map : map
		});
		var iwContent = '<div style="max-width: 500px; width:auto; display: inline-block; white-space: nowrap;">'
		    +'<div style="padding:5px; font-size:13px; ">  <span style="flex-grow: 1;">' + tkplace
				+' </span> '+"  "+'<i onclick="clickPlus(this)" id="icon" class="fa-solid fa-plus fa-bounce fa-2xl" style="flex-shrink: 0; width: 10px; color: blue;"></i> </div>'
				+' <div><div class="Disnone" id="consertview" style="background-color: white;"> ';
				for(let t of tktitle){
					iwContent +='<hr>'+ t.tktitle;
 				}
				iwContent += ' </div> </div></div>';
		var mLabel = new kakao.maps.InfoWindow({
			position : mapCenter,
			content : iwContent
		});
		mLabel.open(map, mMarker);
	</script>

	<script type="text/javascript">
	
		function clickPlus(obj){
			console.log('클릭');
			let cvDiv = document.getElementById('consertview');
			let icDiv = document.getElementById('icon');
		  console.log(cvDiv.style.display);
			if(cvDiv.classList.contains('Disnone')){
				console.log('화면출력');
				cvDiv.classList.remove('Disnone')
				//cvDiv.style.display = 'block';
			icDiv.removeAttribute('class');
			icDiv.setAttribute('class','fa-solid fa-minus fa-beat fa-2xl');
			icDiv.setAttribute('style','color: blue');
		  }else{
			console.log('화면숨기기');
			//cvDiv.style.display = 'none';
			cvDiv.classList.add('Disnone');
			icDiv.removeAttribute('class');
			icDiv.setAttribute('class','fa-solid fa-plus fa-bounce fa-2xl');
			icDiv.setAttribute('style','color: blue');
			}
		}
	</script>
</body>
</html>
