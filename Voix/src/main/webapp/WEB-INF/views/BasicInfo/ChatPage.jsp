<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>채팅 페이지</title>
<style>
#chatArea {
	box-sizing: border-box;
	border: 3px solid black;
	border-radius: 10px;
	width: 400px;
	padding: 10px;
	background-color: #9bbbd4;
	height: 470px;
	margin-bottom: 10px;

}

.sendMsg {

}

.msgComment {
	width: 700px;
	margin-bottom: 25px;
	display: inline-block;
	padding: 7px;
	border-radius: 7px;
	max-width: 95%;
	height: 180px;
}

.receiveMsg>.msgComment {
	background-color: #ffffff;
}

.sendMsg>.msgComment {
	background-color: #fef01b;
}

.receiveMsg, .sendMsg {
	margin-bottom: 5px;
}

.connMsg {
	min-width: 200px;
	max-width: 300px;
	margin: 5px auto;
	text-align: center;
	background-color: #556677;
	color: white;
	border-radius: 10px;
	padding: 5px;
}

.msgId {
	font-weight: bold;
	font-size: 13px;
	margin-bottom: 2px;
}

#inputMsg {
	display: flex;
	box-sizing: border-box;
	border: 3px solid black;
	border-radius: 10px;
	width: 400px;
	padding: 10px;
}

#inputMsg>input {
	width: 100%;
	padding: 5px;
}

#inputMsg>button {
	width: 100px;
	padding: 5px;
}
</style>
</head>

<body>
	<h1>${sessionScope.loginId}</h1>

	<div id="chatArea">

		<div class="sendMsg">
			<div class="msgComment">보낸메세지</div>
		</div>
		<div class="receiveMsg">
			<div class="msgId">AI챗봇</div>
			<div id="resultDiv" class="msgComment">답변은 여기에 작성됩니다.</div>
		</div>


	</div>

	<div id="inputMsg">
		<input type="text" id="sendMsg"> &nbsp
		<button onclick="chatbotSend()">전송</button>
	</div>


	<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
	<script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
	<script type="text/javascript">
		
		let mid = '${sessionScope.loginId}';
			// submit 했을 때 처리
		function chatbotSend(){
				let inputText = document.querySelector('#sendMsg').value;
				$('.msgComment').text(inputText);
			$.ajax({
				type : "get",
				url : "chatbotSend",
				data : {
					"inputText":inputText,
					"mid":mid
				},
// 				processData : false, // 필수
// 				contentType : false, // 필수
				success : function(result) {
					$('#resultDiv').text(result);
				},
				error : function(e) {
					alert("오류 발생" + e);
				}
			});
			}
		
	</script>
</body>

</html>
