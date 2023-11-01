let code = null;
	let mid = null;
	let email = null;
	function gomailCheck() {
		console.log('클릭');

		let email = $('#inputEmailId').val() + "@" + $('#inputDomain').val(); // 이메일 주소값 얻어오기!
		console.log('완성된 이메일 : ' + email); // 이메일 오는지 확인
		let checkInput = $('#mail-check-input'); // 인증번호 입력하는곳 

		$.ajax({
			type : 'get',
			url : 'mailCheck', // GET방식이라 Url 뒤에 email을 뭍힐수있다.
			data : {
				"email" : email
			},
			success : function(data) {
				console.log("data : " + data);
				checkInput.attr('disabled', false);
				code = data;
				alert('인증번호가 전송되었습니다.');
			}
		}); // end ajax
	}

	function mailCheck() {
		let inputCode = $('#mail-check-input').val();
		let $resultMsg = $('#mail-check-warn');
		let email = $('#inputEmailId').val() + "@" + $('#inputDomain').val();
		if (inputCode == code) {
			$resultMsg.html('인증번호가 일치합니다.');
			$resultMsg.css('color', 'green');
			$('#inputEmailId').attr('readonly', true);
			$('#inputEmailId').attr('readonly', true);
			$('#inputDomain').attr('onFocus',
					'this.initialSelect = this.selectedIndex');
			$('#inputDomain').attr('onChange',
					'this.selectedIndex = this.initialSelect');
		selectId(email);
		} else {
			$resultMsg.html('인증번호가 불일치 합니다. 다시 확인해주세요!.');
			$resultMsg.css('color', 'red');
		}
	}

	// ajax 이메일 매개변수를 이용해서 아이디 select 하는 구절추가
	function selectId(email) {
		$.ajax({
			type : 'get',
			url : 'FindId', // GET방식이라 Url 뒤에 email을 뭍힐수있다.
			data : {
				"email" : email
			},
			async: false,
			success : function(data) {
				console.log("data : " + data);
				mid = data;
				document.querySelector( '.IdFind' ).innerText = mid;
				
			}
		}); // end ajax
	}