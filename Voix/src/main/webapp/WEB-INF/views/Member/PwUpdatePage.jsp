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
<title>비밀번호변경 페이지</title>
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
						<div id="div2">
							<input type="text" id="mid" name="mid" value="${mInfo.mid}" disabled="disabled" style="display:none">							
							<p>변경할 비밀번호</p>
							<input placeholder="변경할 비밀번호" class="formInput p-1"
								type="text" name="RePw" id="password1" onkeyup="checkPassword()">
							<input
								placeholder="변경할 비밀번호 확인" class="formInput p-1" type="password"
								name="CkPw" id="password2" onkeyup="checkPassword()">
							<div id="passwordMessage"></div>
							<button class="btn btn-info" onclick="updatePw()">변경하기</button>
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
			let mid = document.getElementById("mid").value;
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
							alert('비밀번호가 변경 되었습니다.!');
							location.href = "/MyInfoPage";
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