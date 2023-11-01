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

<!-- 내정보변경 테이블 스타일 -->
<style type="text/css">
.tbl_model {
	position: relative;
	width: 100%;
	table-layout: fixed;
	border-spacing: 0;
	border-collapse: collapse;
	word-wrap: break-word;
	word-break: keep-all;
	border: 0;
	border-bottom: 1px solid #e5e5e5;
	border-top: 1px solid #e5e5e5;
}

.tbl_model .thcell {
	padding: 32px 29px 32px;
	font-size: 15px;
}

.tbl_model .tdcell {
	padding: 32px 0 3px 30px;
}

.tbl_model th {
	color: #333;
	border-top: 1px solid #e5e5e5;
	border-right: 1px solid #e5e5e5;
	background: #f9f9f9;
}

.tbl_model td {
	line-height: 14px;
	text-align: left;
	vertical-align: top;
	letter-spacing: -1px;
	border: 0;
	border-top: 1px solid #e5e5e5;
}
</style>

</head>
<body>
	<%@ include file="/WEB-INF/views/Includes/Menu.jsp"%>
	<div class="container">

		<!-- 컨텐츠 시작 -->
		<div class="card mb-4 mx-auto" style="width: 700px;">
			<div class="card-body">

				<form action="${pageContext.request.contextPath}/memberModify" method="post" enctype="multipart/form-data">

					<h4>[내정보 변경]</h4>
					<div class="mb-2">
						내ID:&nbsp
						<input type="text" name="mid" value="${mInfo.mid}" disabled="disabled" style="border: none; background-color: white;">
						<input type="text" style="display: none;" name="mid" value="${mInfo.mid}" readonly="readonly">
					</div>

					<table border="1" class="tbl_model mb-2">
						<colgroup>
							<col style="width: 22%;">
							<col>
						</colgroup>
						<tbody>

							<tr>
								<th scope="row">
									<div class="thcell">프로필 사진</div>
								</th>
								<td>
									<div class="tdcell mb-3">
										<div>
											<%-- <img id="preview" style="width: 70px; height: 70px;" alt="" src="${mInfo.mimg}"> --%>

																<c:choose>
												<c:when test="${sessionScope.loginState == 'YC'}">
													<c:choose>
														<c:when test="${sessionScope.loginProfile == null}">
															<%-- 등록된 프로필이 없는 경우 --%>
															<img id="preview" style="width: 70px; height: 70px;" class="img-profile" src="${pageContext.request.contextPath}/resources/users/memberProfile/기본프로필.jpg" alt="일반 프로필1">
														</c:when>
														<c:otherwise>
															<%-- 등록된 프로필이 있는 경우 --%>
															<img id="preview" style="width: 70px; height: 70px;" class="img-profile" src="${pageContext.request.contextPath}/resources/users/memberProfile/${sessionScope.loginProfile}" alt="일반 프로필2">
														</c:otherwise>
													</c:choose>
												</c:when>

												<c:otherwise>
													<img id="preview" style="width: 70px; height: 70px;" class="img-profile" src="${sessionScope.loginProfile}" alt="카카오 프로필">
												</c:otherwise>
											</c:choose>

											<input id="fileInput" class="formInput p-1" type="file" name="mfile">
										</div>

									</div>
								</td>
							</tr>

							<tr>
								<th scope="row">
									<div class="thcell">
										<label>이름</label>
									</div>
								</th>
								<td>
									<div class="tdcell">
										<p>
											<input type="text" style="display: none;" name="mpw" value="${mInfo.mpw}" readonly="readonly">
											<input type="text" style="display: none;" name="mstate" value="${mInfo.mstate}" readonly="readonly">
											<input value="${mInfo.mname}" class="formInput p-1" type="text" name="mname">
										</p>
									</div>
								</td>
							</tr>


							<tr>
								<th scope="row">
									<div class="thcell">
										<label>이메일</label>
									</div>
								</th>
								<td>
									<div class="tdcell">
										<p>
											<input value="${mInfo.memail}" class="formInput p-1" type="text" name="memail" style="width: 363px;">
										</p>
									</div>
								</td>
							</tr>


							<tr>
								<th scope="row">
									<div class="thcell">
										<label>주소</label>
									</div>
								</th>

								<td>
									<div class="tdcell">
										<p>
											<button type="button" class="mb-2" onclick="PostCode()" style="padding: 6px; border-radius: 5px; margin-right: 3px;">주소찾기</button>
											<input type="text" name="address" id="Address" placeholder="주소">
											<input type="text" name="detailAddress" id="DetailAddress" placeholder="상세주소">
										</p>
									</div>
								</td>
							</tr>

						</tbody>

					</table>

					<button class="btn btn-danger" type="submit">수정하기</button>

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
	<!-- Bootstrap core JS-->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
	<!-- Core theme JS-->
	<script src="js/scripts.js"></script>

	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>

	<script type="text/javascript">
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

</body>
</html>
