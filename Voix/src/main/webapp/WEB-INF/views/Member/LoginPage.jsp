<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>로그인 페이지 - LoginPage</title>

<!-- Favicon-->
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="resources/css/styles.css" rel="stylesheet" />
</head>
<body>
	<%@ include file="/WEB-INF/views/Includes/Menu.jsp"%>
	<div class="container">

		<!-- 컨텐츠 시작 -->
		<div class="card mb-4 mx-auto " style="width: 500px;">
			<div class="card-body VOIXBODERLINE">
				<div style="font-size: 30px; font-weight: bold; color: #5e504e; justify-content: center; text-align: center;">로그인</div>
				<form action="/memberLogin" method="post" onsubmit="return formCheck(this);">
					<div class="row m-1">
						<input placeholder="아이디" class="formInput p-1" type="text" name="mid" id="inputId">
					</div>
					<div class="row m-1">
						<input placeholder="비밀번호" class="formInput p-1" type="text" name="mpw" id="inputPw">
					</div>
					<div class="row m-1">
						<input class="formInput p-1 btn btn-primary" type="submit" value="로그인">
					</div>
					<p>
						<a href="IdFindPage">아이디</a> / <a href="PwFindPage">비밀번호</a> 찾기
					</p>
				</form>
				<p style="font-size: 20px; font-weight: bold; color: #5e504e; text-align: center; ">간편로그인</p>
				<div class="row m-1">
					<button onclick="memberLogin_kakao()" class="btn btn-warning">카카오 로그인</button>

					<div class="btn" id="naverIdLogin_loginButton" onclick="memberLogin_naver()">
						<img src="https://static.nid.naver.com/oauth/big_g.PNG?version=js-2.0.1" height="60">

					</div>
					
				</div>
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
	<!-- Bootstrap core JS-->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
	<!-- Core theme JS-->
	<script src="/resources/js/scripts.js"></script>
	<!-- JQUERY -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
	<!-- 카카오 로그인 JS -->
	<script src="https://t1.kakaocdn.net/kakao_js_sdk/2.3.0/kakao.min.js" integrity="sha384-70k0rrouSYPWJt7q9rSTKpiTfX6USlMYjZUtr1Du+9o4cGvhPAWxngdtVZDdErlh" crossorigin="anonymous"></script>
	<!-- 네이버 로그인 JS -->
	<script type="text/javascript" src="https://static.nid.naver.com/js/naverLogin_implicit-1.0.3.js" charset="utf-8"></script>
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.min.js"></script>
	<script src="/resources/js/naveridlogin_js_sdk_2.0.2.js"></script>
	<script type="text/javascript">
	function memberLogin_naver(){
	window.open("/naverPopup");
	window.addEventListener('load',function(){
        location.href('/');
    });
	}

</script>

	<script type="text/javascript">
        Kakao.init('d4cb31bb87534a761936eb7f0916ec2f');
		Kakao.isInitialized();
		
		function memberLogin_kakao(){
			console.log('카카오 로그인 호출()');
			 Kakao.Auth.authorize({
		      redirectUri: 'http://localhost:8080/LoginPage',
		    });
		}
			let authCode = '${param.code}';	
			console.log("authCode: " + authCode);
			if(authCode.length > 0){
				console.log("카카오 인가코드 있음");
				console.log("인증토큰 요청");
				$.ajax({
					type : 'post',
					url : 'https://kauth.kakao.com/oauth/token',
					contentType : 'application/x-www-form-urlencoded;charset=utf-8',
					data : {'grant_type' : 'authorization_code', 
							'client_id' : 'db72247804d6b569e5f4b3aab6bcaa83',
							'redirect_uri' : 'http://localhost:8080/LoginPage',
							'code' : authCode
							},
					success : function(response){
						console.log("인증토큰" + response.access_token);
						Kakao.Auth.setAccessToken(response.access_token);
						
						Kakao.API.request({
							  url: '/v2/user/me',
							})
							  .then(function(response) {
								console.log("카카오 계정 정보")
							    console.log("id : " + response.id);
								console.log("email : " + response.kakao_account.email);
								console.log("nickname : " + response.properties.nickname);
								console.log("profile_image : " + response.properties.profile_image);
								/*ajax 카카오계정 정보가 members 테이블에서 조회(select)*/
								$.ajax({
									type : 'get',
									url : 'memberLogin_kakao',
									data : {'id' : response.id},
									success : function(checkMember_kakao){
										if(checkMember_kakao == 'Y'){
											//alert('로그인 되었습니다');
											location.href="/";
										}else{
											let check = confirm('가입된 정보가 없습니다 \n 카카오 계정으로 가입하시겠습니까?')
											if(check){
												console.log('카카오 회원가입 기능 호출');
												memberJoin_kakao(response);
											}
										}
									}							
								});							
								
							  })
							  .catch(function(error) {
							    console.log(error);
							  });

					}
				});
			}
			
		
		</script>

	<script type="text/javascript">
			function memberJoin_kakao(res){
				console.log('memberJoin_kakao 호출');
				$.ajax({
					type : 'get',
					url : 'memberJoin_kakao',
					data : {'mid' : res.id,
							'mname' : res.properties.nickname,
							'memail' : res.kakao_account.email,
							'mimg' : res.properties.profile_image},
					success : function(joinResult){
						alert('카카오 계정으로 회원가입 되었습니다. \다시 로그인 해주세요');
						location.href="/LoginPage";
					}
				});
			}
		</script>

	<script type="text/javascript">
		function formCheck(formObj) {
			console.log('formCheck() 호출');

			// 아이디가 입력되지 않았으면 false
			let inputId = formObj.userId; // 아이디 입력 input 태그
			if (inputId.value.length == 0) {
				alert('아이디를 입력 해주세여');
				inputId.focus();
				return false;
			}

			// 비밀번호가 입력되지 않았으면 false
			let inputPw = formObj.userPw; // 비밀번호 입력 input 태그
			if (inputPw.value.length == 0) {
				alert('비밀번호를 입력 해주세여');
				inputPw.focus();
				return false;
			}
		}
	</script>
</body>
</html>
