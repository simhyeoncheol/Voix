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
</style>
</head>
<body>
	<!-- Responsive navbar-->
	<%@ include file="/WEB-INF/views/Includes/Menu.jsp"%>
	<!-- Page content-->
	<div class="container">
		<div>
			<div class="row">

				<div class="card col-md-4 mb-4">
					<img alt="공연포스터" src="${tk.tkimg}" style="width: 100%;" class="card-img-top">
				</div>
				<div class="card col-md-8 mb-4">
					<p>${tk.tktitle}</p>
					<p>${tk.tkartist}</p>
					<p id="tkplace">${tk.tkplace}</p>
					<p>${tk.tktime}</p>
					<p>${tk.tkdate}</p>
					<p>${tk.tkinfo}</p>
				</div>


			</div>
		</div>

		<div>
			<div class="row">
				<div class="card col-md-8 mb-2" id="mapInfo">
					<div id="map" style="width: 100%; height: 350px; margin-left: 5px; margin-top: 5px; border: 5px solid bisque; border-radius: 10px;"></div>
				</div>
				<div class="card col-md-4 mb-2 " style="display: inline-block;">
					<div>
						<button class="button mb-4 mt-4" onclick="location.href='공연페이지'">바로가기</button>
					</div>
				</div>
			</div>
		</div>
		<div class="borderline" style="overflow: scroll; height: 500px; width: 80%;">
			<div>
				댓글 <input value="댓글모음">
			</div>
			<div>댓글</div>
			<div>댓글</div>
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
		    +'<div style="padding:5px; font-size:13px; display: flex;">  <span style="flex-grow: 1;">' + tkplace
				+' </span> '+"  "+'<i onclick="clickPlus(this)" id="icon" class="fa-solid fa-plus fa-bounce fa-2xl" style="flex-shrink: 0; width: 10px; color: blue;"></i> </div>'
				+' <div><div class="Disnone" id="consertview" style="background-color: white;"> ';
				for(let t of tktitle){
					iwContent += '<br><hr>'+t.tktitle;
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