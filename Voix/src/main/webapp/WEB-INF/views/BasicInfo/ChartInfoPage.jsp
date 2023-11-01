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
<title>랭킹상세페이지 - ChartInfoPage</title>
<!-- Favicon-->
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="resources/css/styles.css" rel="stylesheet" />

<style type="text/css">
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
	<%@ include file="/WEB-INF/views/Includes/Menu.jsp"%>


	<!-- Responsive navbar-->
	<!-- Page content-->
	<div class="container">



	<c:forEach items="${ChartInfoList}" var="ChartInfoList">
			<div class="row">
				<div class="card col-md-4 mb-4 ">
					<img alt="랭킹 포스터" src="${ChartInfoList.sgimg}">
				</div>

				<div class="card col-md-8 mb-4">
					<p>${ChartInfoList.sgartitle}</p>
					<p>타이틀:${ChartInfoList.sgtitle}</p>
					<p>${ChartInfoList.sginfo}</p>
					<p>아티스트:${ChartInfoList.sgartist}</p>

					<div class="like_article" onclick="like('${ChartInfoList.sgcode}')"
						style="position: absolute; right: 20px; bottom: 20px;">
						<a href='' class="prdLike"> <img alt=""
							src="/resources/assets/blankheart.png" style="width: 30px;">
						</a>
					</div>
					<c:choose>
						<c:when test="${ChartInfoList.SGLIKED eq 'true'}">
							<div class="like_article"style="position: absolute; right: 20px; bottom: 20px;"
								onclick="like('${ChartInfoList.sgcode}', this)">
								<a class="prdLike" style="cursor: pointer;"> <img alt=""
									src="/resources/assets/heart.png" style="width: 30px;">
								</a>
							</div>
						</c:when>
						<c:otherwise>
							<div class="like_article"style="position: absolute; right: 20px; bottom: 20px;"
								onclick="like('${ChartInfoList.sgcode}', this)">
								<a class="prdLike" style="cursor: pointer;"> <img alt=""
									src="/resources/assets/blankheart.png" style="width: 30px;">
								</a>
							</div>

						</c:otherwise>
					</c:choose>
				</div>
			</div>


			<div class="row">
		
			<div class="col mb-4" style="margin-right: -10px;">
    			<iframe id="scroller" src="${ChartInfoList.sgmvurl}" width="1000" height="515"></iframe>		
			</div>
			
			<div class="card col mb-4" style="height:515px;">
				<div style="text-align: center; background-color: darkgrey;">가사</div>
				<div style="height: 515px; overflow: scroll; margin-left: 10px;">
					${ChartInfoList.sglyric}
				</div>
			</div>
			
		</div>
	</c:forEach>
		
		<c:if test="${sessionScope.loginId != null }">
				<div class="reviewWrite">
					<form action="ChartRegistReview" class="my-3" method="post">
						<input type="text" name="restate" value="${SgInfo.sgcode }" style="display: none">
						<textarea class="w-100 reviewComment" name="recontent" placeholder="댓글을 작성해보세요."></textarea>
						<input class="btn btn-success w-100" type="submit" value="댓글 등록">
					</form>
				</div>
				<hr>
			</c:if>
		<div class="borderline" style="overflow: scroll; height: 500px; width: 100%;">
				<div class="replyArea">
					<div class="row my-3 scroll" style="width: 100%; margin-left: 5px; padding: 0px; display: inline-block; height: auto; max-height: 450px;">
						<c:forEach items="${reviewList}" var="re">
							<div class="meminfo">
								<span>작성자: ${re.REWRITER} </span>
								<div style="margin-top: 5px; margin-bottom: 5px;">
									<!--
									<textarea rows="" cols="" class="rvcomm scroll" disabled="disabled">${re.RECONTENT}</textarea>
									-->
									<div class="textdiv w-100" style="font-size: large; border:1px solid #cccc;">${re.RECONTENT}</div>
								</div>
								<c:if test="${sessionScope.loginId == re.REWRITER}">
									<button type="button" onclick="location.href='/deleteChartReview?recode=${re.RECODE}&sgcode=${SgInfo.sgcode}'" class="btn btn-danger" style="font-size: 14px; margin-bottom: 4px; width: 88px; height: 33px; float: right;">댓글 삭제</button>
								</c:if>
								<div class="small text-muted">작성시간: ${re.REDATE}</div>
							</div>
							<hr>
						</c:forEach>
					</div>
				</div>
			</div>


		<!-- 				<div class="card mb-4">
					<a href="#!"><img class="card-img-top"
						src="https://dummyimage.com/850x350/dee2e6/6c757d.jpg" alt="..." /></a>
					<div class="card-body">
						<div class="small text-muted">January 1, 2023</div>
						<h2 class="card-title">Featured Post Title</h2>
						<p class="card-text">Lorem ipsum dolor sit amet, consectetur
							adipisicing elit. Reiciendis aliquid atque, nulla? Quos cum ex
							quis soluta, a laboriosam. Dicta expedita corporis animi vero
							voluptate voluptatibus possimus, veniam magni quis!</p>
						<a class="btn btn-primary" href="#!">Read more →</a>
					</div>
				</div>  -->

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
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<script type="text/javascript">
    let loginId = '${sessionScope.loginId}';
    function like(SongCode) {
        console.log(loginId);
        console.log(SongCode);
        if (loginId.length == 0) {
            alert("로그인을 먼저 해주세요.");
            location.href = "/LoginPage";
        } else {

        	$.ajax({
        	    type: "GET",
        	    url: "likeSong",
        	    data: {
        	        "like": SongCode
        	    },
        	    //async: false,
        	    success: function(response) {
        	        if (response == 1) {
        	            // '찜' 성공
        	            alert("찜하기가 되었습니다.");
        	            // 이미지 업데이트
        	            element.querySelector('img').src = '/resources/assets/heart.png';
        	        } else if (response == 0) {
        	            // '찜' 취소
        	            alert("찜하기가 취소되었습니다.");
        	            // 이미지 업데이트
        	            element.querySelector('img').src = '/resources/assets/blankheart.png';
        	        } else {
        	            // 이미 '찜'한 경우
        	            alert("이미 찜이 되어있습니다.");
        	            // 이미지 업데이트
        	            element.querySelector('img').src = '/resources/assets/blankheart.png'; // 이 부분을 추가
        	        }
        	    },
        	    error: function() {
        	        console.error("찜하기 요청 중 오류 발생");
        	        alert("찜하기에 실패했습니다.");
        	    }
        	});
        }
    }
</script>	
	
	<!-- 스크롤바 설정 못함
	 // 데이터 전송
		     // data: 전달할 메시지나 데이터
		     // ports: 메시지 포트(생략가능)
		     // targetOrigin: 타겟 도메인, 특정 도메인이 아니면 * 사용 가능
		     window.postMessage(data, [ports], targerOrigin)
		
		     // 데이터 수신
		     window.onmessage = function(e){
		     	if(e.origin === "https://보낸곳의도메인주소"){
		     		// 처리
		     		console.log(e.data);
		     	}
		     }
		     // 그외 데이터 수신
		     // window.addEventListener("message", 컨트롤함수, true);
		     // window.attachEvent("onmessage", 컨트롤함수);
	
	 -->
	<!--  
	<script>
        var iframe = document.getElementById('scroller'); 
        var iframeContentWindow = iframe.contentWindow;

        //스크롤 위치 지정
        var targetScrollX = 200; 
        var targetScrollY = 300; 

        iframe.onload = function() {
            // 로드될 때 위치 설정
            console.log(iframe)
            iframe.scrollTo(targetScrollX, targetScrollY);
        };

    </script>
	-->
	
</body>

</html>
