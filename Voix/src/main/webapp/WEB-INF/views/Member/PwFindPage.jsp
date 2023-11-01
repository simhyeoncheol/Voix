<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>비밀번호찾기 페이지</title>
<!-- Favicon-->
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="resources/css/styles.css" rel="stylesheet" />
</head>
<body>
	<%@ include file="/WEB-INF/views/Includes/Menu.jsp"%>
	<div class="container">

		<!-- 컨텐츠 시작 -->
		<div class="card mb-4 mx-auto" style="width: 500px;">
			<div class="card-body">
			
						<!-- 이메일이랑 아이디 맞는지 체크하는폼 -->
						<div id="div1">
						<div>비밀번호 찾기</div>
						<form onsubmit="return emailIdCheck(this)">
							<!-- onsubmit="return formCheck(this);" -->
							<input placeholder="아이디" class="formInput p-1" type="text"
								name="inputId">
							<div class="row m-1">
								<input type="text" name="memailId" placeholder="이메일아이디" id="inputEmailId">
								@ <input type="text" name="memailDomain" id="inputDomain"
									placeholder="이메일도메인"> <select
									onchange="domainSelect(this)">
									<option value="">직접입력</option>
									<option value="naver.com">naver.com</option>
									<option value="gmail.com">gmail.com</option>
									<option value="daum.net">daum.net</option>
								</select>
								<button type="submit" class="btn btn-warning">보내기</button>
							</div>
						</form>
							<div>
								<input type="text" name="Code" id="mail-check-input"placeholder="인증번호">
								<button class="btn btn-info"onclick="mailCheck_t()">확인</button>
								<span id="mail-check-warn"></span>
							</div>
						</div>
						<!-- 인증번호가 맞을시 -->
						<div id="div2" style="display:none;">
							<p>변경할 비밀번호</p>
							<input placeholder="변경할 비밀번호" class="formInput p-1"
								type="text" name="RePw" id="password1" onkeyup="checkPassword()">
							<input
								placeholder="변경할 비밀번호 확인" class="formInput p-1" type="password"
								name="CkPw" id="password2" onkeyup="checkPassword()">
							<div id="passwordMessage"></div>
							<button class="btn btn-info" onclick="updatePw()">변경하기</button>
						</div>

				<div>
					<a type="button" href="LoginPage">로그인하기</a> <a type="button"
						href="IdFindPage">아이디 찾기</a>
				</div>
			</div>
		</div>
		<!-- 컨텐츠 끝 -->

	</div>

	<!-- Footer-->
	<footer class="py-5 bg-dark">
		<div class="container">
			<p class="m-0 text-center text-white">위 페이지의 출력되는 정보는 우측 상단에 있는
				데이터 클롤링 및 페이지 양식을 인용하여 제작되었습니다.</p>
		</div>
	</footer>
	<!-- Bootstrap core JS-->
	<script src="resources/js/scripts.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
	<!-- Core theme JS-->
		
	<script type="text/javascript">
		
		function mailCheck_t(){
			mailCheck();
			console.log(mid);
			if(mid != null){
				//div 2를 디스플레이 하고 div1을 display='none';으로 없애
				 $("#div1").hide();
			     $("#div2").show();
			}
			
		}
	
		function formCheck(formObj) {
			console.log('formCheck()호출')
			console.log(formObj);
			//아이디가 입력되지 않았을 경우 false
			let inputId = formObj.userId;
			if (inputId.value.length == 0) {
				alert('아이디를 입력 해주세요!');
				inputId.focus();
				return false;
			}
			let memailId = formObj.memailId;
			if (memailId.value.length == 0) {
				alert('이메일을 입력 해주세요!');
				memailId.focus();
				return false;
			}
			let memailDomain = formObj.memailDomain;
			if (memailDomain.value.length == 0) {
				alert('이메일을 입력 해주세요!');
				memailDomain.focus();
				return false;
			}
		}
		function domainSelect(selObj) {
			document.querySelector('#inputDomain').value = selObj.value;
		}
		
	</script>
	<script type="text/javascript">
		function emailIdCheck(formObj) {
			$.ajax({
				type : "get",/*전송 방식*/
				url : "emailIdCheck",/*전송 URL*/
				data : {
					"inputId" : formObj.inputId.value,
					"memailId" : formObj.memailId.value,
					"memailDomain" : formObj.memailDomain.value
				},/*전송 파라메터*/
				success : function(CheckResult) {/* 전송에 성공 했을 경우 */
					console.log("확인결과 : " + CheckResult);
					gomailCheck();
				}
			});
			return false;
		}
		let AuthPw = null;
		function checkPassword() {
			var password1 = document.getElementById("password1").value;
			var password2 = document.getElementById("password2").value;
			var message = document.getElementById("passwordMessage");

			if (password1 === password2) {
				// 비밀번호가 일치할 때
				message.textContent = "비밀번호가 일치합니다.";
				message.style.color = "green";
				AuthPw = 'Y';
			} else {
				// 비밀번호가 일치하지 않을 때
				message.textContent = "비밀번호가 일치하지 않습니다.";
				message.style.color = "red";
				AuthPw = 'N';
			}
		}

		function updatePw() {
			let selPw = document.getElementById("password2").value;
			var password2 = document.getElementById("password2").value;
			console.log(selPw);
			console.log(mid);
			if (AuthPw == 'Y') {
				$.ajax({
					type : "get",
					url : "updatePw",
					data : {
						"mid" : mid,
						"mpw" : selPw
					},
					success : function(result) {
						console.log("확인결과 : " + result);
						if (result != null) {
							alert('비밀번호가 변경 되었습니다. \로그인 해주세요!');
							location.href = "/LoginPage";
						}
					}
				});
			}else{
				alert('비밀번호가 일치한지 확인 해주세요!');
			}

		}
	</script>

</body>
</html>
