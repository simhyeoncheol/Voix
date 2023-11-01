<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<style>
/* 컨테이너 스타일 */
.container {
	padding: 20px;
}

/* 카드 스타일 */
.card {
	max-width: 500px;
	margin: 0 auto;
	background-color: #f5f5f5;
}

/* 카드 본문 스타일 */
.card-body {
	padding: 20px;
	background-color: #fff;
}

/* 제목 스타일 */
.card-body>div {
	font-size: 24px;
	font-weight: bold;
	margin-bottom: 20px;
}

/* 입력 필드 스타일 */
.formInput {
	width: 100%;
	padding: 10px;
	margin-bottom: 10px;
	border: 1px solid #ccc;
	border-radius: 4px;
}

/* 중복확인 및 주소찾기 버튼 스타일 */
input[type="button"] {
	padding: 10px 15px;
	background-color: #007bff;
	color: #fff;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

.checkMsg {
	font-size: 12px;
	margin-left: 4px;
	margin-top: 1px;
	margin-bottom: 1px;
}

.IdCheck {
	background-color: green;
	width: 100%;
	border-radius: 10px;
	font-size: 15px;
	cursor: pointer;
}

/* 이메일 입력 필드 스타일 */
input[name="memailId"], input[name="memailDomain"] {
	width: 45%;
	padding: 10px;
	margin-bottom: 10px;
	border: 1px solid #ccc;
	border-radius: 4px;
}

/* 이메일 도메인 선택 스타일 */
select {
	width: 45%;
	padding: 10px;
	margin-bottom: 10px;
	border: 1px solid #ccc;
	border-radius: 4px;
}

/* 보내기 및 확인 버튼 스타일 */
button {
	padding: 10px 20px;
	background-color: #007bff;
	color: #fff;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}

/* 인증번호 입력 필드 스타일 */
input[name="Code"] {
	width: 100%;
	padding: 10px;
	margin-bottom: 10px;
	border: 1px solid #ccc;
	border-radius: 4px;
}

input[type=file]::file-selector-button{
	background-color: #5e504e;
	border: 1px solid #5e504e;
	border-radius: 10px;
	color: #ede9e7;
	padding: 10px;
}
</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/Includes/Menu.jsp"%>
	<div class="container">

		<!-- 컨텐츠 시작 -->
		<div class="card mb-4 mx-auto" style="width: 500px;">
			<div class="card-body VOIXBODERLINE">
				<div style="font-size: 24px; font-weight: bold; color: #5e504e; justify-content: center; text-align: center;">회원가입</div>
				<form action="${pageContext.request.contextPath}/Join" method="post" enctype="multipart/form-data" onsubmit="return formCheck(this)">
					<div>
						<input placeholder="아이디" class="formInput p-1" type="text" name="Id" id="inputId" style="width: 75%; margin-bottom: 0px;">
						<button type="button" class="IdCheck" onclick="checkId(this)" style="width: 20% ;background-color: #5e504e ; font-size: 13px;" >중복확인</button>
						<p class="checkMsg mb-2" id="idMsg" >중복확인</p>
						<input placeholder="비밀번호" class="formInput p-1" type="text" name="RePw">
						<input placeholder="비밀번호 확인" class="formInput p-1" type="text" name="CkPw">
					</div>

					<div>
						<input placeholder="이름" class="formInput p-1" type="text" name="Name">
						<img id="preview" style="width: 70px; height: 70px;" alt="">
						<input id="fileInput" class="formInput p-1" type="file" name="mfile" placeholder="프로필 입력" style="border: none; width: 82%;">
					</div>


					<div>
						<input placeholder="주소" class="formInput p-1" type="text" id="Address" name="Address" style="width: 75%;">
						<button type="button" class="mb-2 VOIXBODERLINE" onclick="PostCode()" style="background-color: #5e504e; border-radius: 10px;" >주소찾기</button>
						<input placeholder="상세주소" class="formInput p-1" type="text" id="DetailAddress" name="DetailAddress">
					</div>
					<div>

						<div class="row m-1">
							<input type="text" name="memailId" id="inputEmailId" placeholder="이메일아이디">
							@
							<input type="text" name="memailDomain" id="inputDomain" placeholder="이메일도메인">
							<select onchange="domainSelect(this)" style="width: 75%;">
								<option value="">직접입력</option>
								<option value="naver.com">naver.com</option>
								<option value="gmail.com">gmail.com</option>
								<option value="daum.net">daum.net</option>

							</select>
							<script type="text/javascript">
								function domainSelect(selObj) {
									document.getElementById("inputDomain").value = selObj.value;
									/*document.querySelector("#inputDomain").value = selObj.value;*/
								}
							</script>
							<button type="button" class="VOIXBODERLINE" onclick="gomailCheck()" style="background-color: #5e504e; border-radius: 10px; width: 23%; margin-left: 5px;">보내기</button>
						</div>
						<div>
							<input type="text" name="Code" id="mail-check-input" placeholder="인증번호" style="width: 75%;">
							<button type="button" class="mb-2 VOIXBODERLINE" onclick="mailCheck()" style="background-color: #5e504e; border-radius: 10px; width: 22%;">확인</button>
							<span id="mail-check-warn"></span>
						</div>
							<button type="submit" class="VOIXBODERLINE" style="background-color: #5e504e; border-radius: 10px; width: 99% !important;">회원가입하기</button>

					</div>
				</form>
			</div>
		</div>
		<!-- 컨텐츠 끝 -->

	</div>



	<!-- Footer-->
	<footer class="py-5 bg-dark">
		<div class="container">
			<p class="m-0 text-center text-white">위 페이지의 출력되는 정보는 우측 상단에 있는 데이터 클롤링 및 페이지 양식을 인용하여 제작되었습니다.</p>
		</div>
	</footer>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<!-- Bootstrap core JS-->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<!-- Core theme JS-->
<script src="resources/js/scripts.js"></script>

<script type="text/javascript">
	function PostCode() {
		document.getElementById("Address").value = "";
		new daum.Postcode({
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
					if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
						extraAddr += data.bname;
					}
					// 건물명이 있고, 공동주택일 경우 추가한다.
					if (data.buildingName !== '' && data.apartment === 'Y') {
						extraAddr += (extraAddr !== '' ? ', '
								+ data.buildingName : data.buildingName);
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
	let buttonClicked = false;
	let button = document.getElementById('#IdCheck');
	function domainSelect(obj) {
		document.getElementById("inputDomain").value = obj.value;
	}
	function checkId(obj) {
		let idEl = document.querySelector('#inputId');
		let idMsg = $("#idMsg");
		console.log(idEl.value);
		$.ajax({
			type : "get",
			url : "memberIdCheck",
			data : {
				"inputId" : idEl.value
			},

			success : function(result) {
				if (result == "Y") {
					console.log("사용가능한 아이디");
					idMsg.css("color", "green").text('사용가능한아이디');
					$(obj).parent().removeClass('formInputErr');
					buttonClicked = true;
				} else {
					console.log("중복된 아이디");
					idMsg.css("color", "red").text('이미가입된아이디');
					$(obj).parent().addClass('formInputErr');
					buttonClicked = false;
				}
			}

		});

	}

	function formCheck(obj) {
		let idEl = obj.Id;
		let pwEl = obj.RePw;
		let cpEl = obj.CkPw;
		let emIdEl = obj.memailId;
		let emDoEl = obj.memailDomain;
		let naEl = obj.Name;
		let AdEl = obj.Address;
		if (idEl.value.length == 0) {
			alert('아이디를 입력해주세요');
			idEl.focus();
			return false;
		}
		if (!buttonClicked) {
			alert('중복체크를 해주세요');
			return false;
		}
		if (pwEl.value.length == 0) {
			alert('비밀번호를 입력해주세요');
			pwEl.focus();
			return false;
		}
		if (pwEl.value != cpEl.value) {
			alert('2차 비밀번호 확인을 해주세요');
			cpEl.focus();
			return false;
		}
		if (emIdEl.value.length == 0) {
			alert('이메일을 입력해주세요');
			emIdEl.focus();
			return false;
		}
		if (emDoEl.value.length == 0) {
			alert('이메일을 입력해주세요');
			emDoEl.focus();
			return false;
		}
		if (naEl.value.length == 0) {
			alert('이름 입력해주세요');
			naEl.focus();
			return false;
		}
		if (AdEl.value.length == 0) {
			alert('주소를 입력해주세요');
			AdEl.focus();
			return false;
		}

		else {
		}
		return;
	}
</script>

<script>
	document.querySelector('#fileInput').addEventListener(
			'change',
			function(e) {
				var reader = new FileReader();

				reader.onload = function(event) {
					document.querySelector('#preview').setAttribute('src',
							event.target.result);
				};

				reader.readAsDataURL(e.target.files[0]);
			});
</script>
</html>
