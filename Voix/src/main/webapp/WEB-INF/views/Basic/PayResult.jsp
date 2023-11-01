<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<script type="text/javascript">
		window.close();
		window.onunload = function(){
			let payResult = '${payResult}';
			if(payResult == 'Y'){
				/* INSERT 성공, 결제 성공 */
				/* 부모창에 예매가 되었습니다! */
				window.opener.alert('예매되었습니다!');
				window.opener.location.href="/";
				//window.opener.reserveResult_success();
			} else {
				/* INSERT 성공, 결제 실패 */
				window.opener.alert('결제 실패!');
				window.opener.location.href="/";
			}
			
		}
		
	</script>
</head>
<body>
</body>
</html>












