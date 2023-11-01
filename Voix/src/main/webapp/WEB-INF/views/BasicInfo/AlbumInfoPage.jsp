<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>AlbumInfoPage</title>
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

.disNone {
	display: none;
}

.AlbumImg {
	width: 100%;
}

.selectbox {
	border: 1px solid #ccc;
	border-radius: 20px;
	text-align: center;
	width: 100px;
}

.infoBox {
	height: 600px;
	overflow: overlay;
}

.infoBox::-webkit-scrollbar {
	width: 10px;
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

				<c:if test="${ALInfo.alimg != null }">
					<div class="col-md-4 mb-4" style="width: 490px; height: 490px; margin-right: 7px;">
						<img style="width: 480px; height: 480px;" class="AlbumImg" src="${ALInfo.alimg}" alt="..." />
					</div>
				</c:if>
				<c:if test="${ALInfo.alimg == null }">
					<div class="col">
						<img class="AlbumImg" src="https://dummyimage.com/200x200/c1e3cd/ffffff.jpg" alt="..." />
					</div>
				</c:if>
				<div class="card col-md-7 mb-4" style="font-size: x-large; height: 480px; width: 800px;">
					<div class="card-title text-lg-center" style="height: 65px;">
						<p style="font-size: xx-large; margin-top: 15px;">${ALInfo.alartist}</p>
					</div>
					<div class="card-body infoBox">
						<p style="font-size:">${ALInfo.alinfo}</p>
					</div>
					<div class="card-footer text-lg-center" style="background-color: unset;">
						<p style="font-size: 15px; margin-top: 10px;">${ALInfo.aldate}</p>
					</div>
				</div>


			</div>
		</div>

		<div class="col-md-12 mb-2" style="font-size: x-large;">${ALInfo.allist.replace("A2", "<br>A2").replace("A3", "<br>A3").replace("A4", "<br>A4").replace("A5", "<br>A5").replace("A6", "<br>A6").replace("A7", "<br>A7").replace("B1", "<br><br>B1").replace("B2", "<br>B2").replace("B3", "<br>B3").replace("B4", "<br>B4").replace("B5", "<br>B5").replace("B6", "<br>B6").replace("B7", "<br>B7").replace("C1", "<br><br>C1").replace("C2", "<br>C2").replace("C3", "<br>C3").replace("C4", "<br>C4").replace("C5", "<br>C5").replace("C6", "<br>C6").replace("C7", "<br>C7").replace("D1", "<br><br>D1").replace("D2", "<br>D2").replace("D3", "<br>D3").replace("D4", "<br>D4").replace("D5", "<br>D5").replace("D6", "<br>D6").replace("D7", "<br>D7").replace(" 01", "<br><br>01").replace("02", "<br>02").replace(" 03", "<br>03").replace("04", "<br>04").replace("05", "<br>05").replace("06", "<br>06").replace("07", "<br>07").replace(" 08", "<br>08").replace(" 09", "<br>09").replace("10", "<br>10").replace(" 11", "<br>11").replace("12", "<br>12").replace("13", "<br>13").replace("14", "<br>14").replace("15", "<br>15").replace(" 16", "<br>16").replace(" 17", "<br>17").replace("18", "<br>18").replace("19 ", "<br>19 ").replace("20 ", "<br>20 ").replace("21", "<br>21").replace(" 22", "<br>22").replace("23", "<br>23").replace("24 ", "<br>24 ").replace(" 25", "<br>25").replace(" 26", "<br>26").replace("27", "<br>27").replace("28", "<br>28").replace("29", "<br>29").replace(" 30", "<br>30").replace("31", "<br>31").replace(" 32", "<br>32").replace("33", "<br>33").replace("34", "<br>34").replace(" 35", "<br>35").replace(" 36", "<br>36").replace(" 37", "<br>37").replace(" 38", "<br>38").replace(" 39", "<br>39").replace(" 40", "<br>40").replace(" 41", "<br>41").replace("42", "<br>42").replace("43", "<br>43").replace("44", "<br>44").replace("45", "<br>45").replace("46", "<br>46").replace("47", "<br>47").replace("48", "<br>48").replace(" 49", "<br>49").replace("50", "<br>50").replace(" 51", "<br>51").replace("52", "<br>52").replace("53", "<br>53").replace(" 54", "<br>54").replace("55", "<br>55").replace("56", "<br>56").replace(" 57", "<br>57").replace(" 58", "<br>58").replace(" 59", "<br>59").replace("<br>59th", " 59th").replace("60", "<br>60").replace("61", "<br>61").replace("62", "<br>62").replace("63", "<br>63").replace("선택듣기", "")}</div>

		<div>
			<div class="row">
				<div class="card col-md-8 mb-2 p-3" style="font-size: x-large;">
					<div class="mb-2 m-3">정가 : ${ALInfo.alprice}원</div>
					<c:forEach items="${AlbumInfoList}" var="AlbumInfoList">
						<div class="mb-2 m-3">
							<input type="radio" name="al" id="${AlbumInfoList.alcode}" oninput="is_checked(this)" value="${AlbumInfoList.alcode}">
							할인가 : ${AlbumInfoList.alsaleprice}원
							<!--내일 이거 해야함 체크 표시 해야 나오게  -->
							<input id="selectQty_${AlbumInfoList.alcode}" type="number" value="1" min="1" placeholder="수량" class="disNone selectbox">
							<c:choose>
									<c:when test="${AlbumInfoList1.ALLIKED eq 'true'}">
										<div class="like_article"
											onclick="like('${AlbumInfoList.alcode}', this)">
											<a class="prdLike" style="cursor: pointer;"> <img alt=""
												src="/resources/assets/heart.png" style="width: 30px;">
											</a>
										</div>
									</c:when>
									<c:otherwise>
										<div class="like_article"
											onclick="like('${AlbumInfoList.alcode}', this)">
											<a class="prdLike" style="cursor: pointer;"> <img alt=""
												src="/resources/assets/blankheart.png" style="width: 30px;">
											</a>
										</div>

									</c:otherwise>
								</c:choose>
						</div>
					</c:forEach>
				</div>
				<div class=" col-md-4 mb-2 " style="display: inline-block; align-self: center;">
					<div>
						<button type="submit" class="btn btn-success mb-4" style="font-size: x-large;" onclick="formsubmit('InsertCartPage')">장바구니 바로가기</button>
					</div>
					<div>
						<button class="btn btn-success" style="font-size: x-large;" onclick="formsubmit('PayPage')">결제 바로가기</button>
					</div>
				</div>

			</div>
		</div>




		<c:if test="${sessionScope.loginId != null }">
			<div class="replyWrite">
				<form action="albumRegistReview" class="my-3" method="post">
					<input type="text" name="restate" value="${ALInfo.alcode }" style="display: none">
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
								<div class="textdiv w-100" style="font-size: large; border:1px solid #cccc;">${re.RECONTENT}</div>
								<!--
								<textarea rows="" cols="" class="rvcomm scroll" disabled="disabled">${re.RECONTENT}</textarea>
								-->
							</div>
							<c:if test="${sessionScope.loginId == re.REWRITER}">
								<button type="button" onclick="location.href='/albumDeleteReview?recode=${re.RECODE}&alcode=${ALInfo.alcode}'" class="btn btn-danger" style="font-size: 14px; margin-bottom: 4px; width: 88px; height: 33px; float: right;">댓글 삭제</button>
							</c:if>
							<div class="small text-muted">작성시간: ${re.REDATE}</div>
						</div>
						<hr>
					</c:forEach>
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
	<script type="text/javascript">
      function is_checked(radio) {

         let radioEls = document.querySelectorAll('input[type="radio"]');
         for(let radioEl of radioEls){
            
            if(radioEl.checked){
               radioEl.nextElementSibling.classList.remove('disNone');
            } else {
               radioEl.nextElementSibling.classList.add('disNone');
            }
            
            
         }
      }
      function formsubmit(url){ //   /장바구니, /결제
        console.log(url);
        
        //let Qty = document.getElementById('').value;
        let checkEl = document.querySelector('input[type="radio"]:checked');
        console.log(checkEl.value);
        console.log(checkEl.nextElementSibling.value);
        if(url == "InsertCartPage"){
      	  location.href= "/"+url+"?caalcode="+checkEl.value+'&caqty='+ checkEl.nextElementSibling.value;
        	
        }else if (url == "PayPage"){
          location.href= "/"+url+"?selAl="+checkEl.value+'_1';
        }
          
          
   
      }
   </script>
	<script type="text/javascript">
    let loginId = '${sessionScope.loginId}';
    function like(alcode, element) {
        console.log(loginId);
        console.log(alcode);
        if (loginId.length === 0) {
            alert("로그인을 먼저 해주세요.");
            location.href = "/LoginPage";
        } else {

        	$.ajax({
        	    type: "GET",
        	    url: "likeAlbum",
        	    data: {
        	        "like": alcode
        	    },
        	    //async: false,
        	    success: function(response) {
        	        if (response === 1) {
        	            // '찜' 성공
        	            alert("찜하기가 되었습니다.");
        	            // 이미지 업데이트
        	            element.querySelector('img').src = '/resources/assets/heart.png';
        	        } else if (response === 0) {
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

</body>
</html>
