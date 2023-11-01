<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">


<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>결제 페이지</title>
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

		<c:forEach items="${alList }" var="al">
			<div class="row">

				<div class="col-md-4 mb-4">
					<input id="alList1" type="hidden" value="${al.alcode }">
					<input type="hidden" value="${al.alqty}">
					<span class="d-none alcode_qty_price">${al.alcode }_${al.alqty}_${al.alsaleprice}</span> <img alt="" src="${al.alimg}" style="width: 100%; height: 315px;">
				</div>

				<div class="card col-md-8 mb-4" style="width: 65.666667% !important;">
					<div style="height: 315px; margin-left: 10px; overflow: scroll;">
						<h2>제목: ${al.altitle}</h2>
						<h5>아티스트: ${al.alartist}</h5>
						<p>장르: ${al.algenre}</p>
						<p>판매가: ${al.alprice}원</p>
						<p>할인가: ${al.alsaleprice}원</p>
						<p>발매일: ${al.aldate}</p>
						<p>${al.alinfo}</p>
						<!-- 
				 <p>${al.alqty}</p>
				 -->

					</div>
				</div>
			</div>
		</c:forEach>

		<div class="card col mb-8">


			<div class="m-2">
				<input placeholder="주소" class="formInput" type="text" id="Address" name="Address" style="width: 80%;">
				<button type="button" class="mb-2" onclick="PostCode()">주소찾기</button>
			</div>
			<input placeholder="상세주소" class="formInput m-2" type="text" id="DetailAddress" name="DetailAddress" style="width: 80%;">
			<input type="text" placeholder="받는사람" class="m-2" value="${mid}" style="width: 80%;">

		</div>

		<button type="button" style="align-items: center;" onclick="goPay()">결제하기</button>

	</div>




	<!-- Footer-->
	<footer class="py-5 bg-dark">
		<div class="container">
			<p class="m-0 text-center text-white">위 페이지의 출력되는 정보는 우측 상단에 있는 데이터 클롤링 및 페이지 양식을 인용하여 제작되었습니다.</p>
		</div>
	</footer>


	<!-- Bootstrap core JS-->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
	<!-- Core theme JS-->
	<script src="/resources/js/scripts.js"></script>
	<script>
		function PostCode() {
			document.getElementById("Address").value = "";
			new daum.Postcode(
					{
						oncomplete : function(data) {
							// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

							// 각 주소의 노출 규칙에 따라 주소를 조합한다.
							// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
							var addr = ''; // 주소 변수
							var extraAddr = ''; // 참고항목 변수

							//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
							if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
								addr = data.roadAddress;
							} else { // 사용자가 지번 주소를 선택했을 경우(J)
								addr = data.jibunAddress;
							}

							// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
							if (data.userSelectedType === 'R') {
								// 법정동명이 있을 경우 추가한다. (법정리는 제외)
								// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
								if (data.bname !== ''
										&& /[동|로|가]$/g.test(data.bname)) {
									extraAddr += data.bname;
								}
								// 건물명이 있고, 공동주택일 경우 추가한다.
								if (data.buildingName !== ''
										&& data.apartment === 'Y') {
									extraAddr += (extraAddr !== '' ? ', '
											+ data.buildingName
											: data.buildingName);
								}
								// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
								if (extraAddr !== '') {
									extraAddr = ' (' + extraAddr + ')';
								}
							}

							// 우편번호와 주소 정보를 해당 필드에 넣는다.

							document.getElementById("Address").value = addr;
							// 커서를 상세주소 필드로 이동한다.
							document.getElementById("DetailAddress").focus();
						}
					}).open();
		}
	</script>

	<script type="text/javascript">
	
		
		let alqtys = [];
	
		let popUpName = null;
		let addr = document.getElementById("Address");
		let dadr = document.getElementById("DetailAddress");
	//	let alcode = '${alcode}';	
	//	let alqty = '1';
	//	let alprice = ${alsaleprice};
		let odmid = '${sessionScope.loginId}';
		function goPay() {
			console.log(addr.value);
			if (odmid.length == 0) {
				alert('로그인 후 이용 가능합니다.');
				location.href = "LoginPage";
			}
			if (addr.value.length == 0) {
				alert('주소를 입력하시오');
				addr.focus();
			}
			if (dadr.value.length == 0) {
				alert('상세주소를 입력하시오');
				dadr.focus();
			} else if(odmid.length != 0 && addr.value.length != 0&& dadr.value.length != 0 ){
				kakaoPay_ready();
			}
		}
			
		
		function kakaoPay_ready() {
			console.log('카카오 페이 결제준비');
			let cartList = [];
			let payInfoList = document.querySelectorAll(".alcode_qty_price");
			for(let info of payInfoList){
				let od_alcode = info.innerText.split("_")[0];
				let od_qty = info.innerText.split("_")[1];
				let od_price = info.innerText.split("_")[2];
				let odDto_Json = { "odalcode" : od_alcode, "odqty" : od_qty, "odprice" :od_price };
				cartList.push(odDto_Json);
			}
			console.log(cartList);
			
			$.ajax({
				type : "post",
				url : "kakaoPay_ready",
				data : {
					'odmid' : odmid,
					'odaddr' : addr.value + " " + dadr.value,
					'orList' : JSON.stringify(cartList)
				},
				async : false,
				success : function(result) {
					console.log(result);
					popUpName = window.open(result, "pay",
							"width=400,height=600");
				}

			})
			
		}
	</script>

</body>

</html>
