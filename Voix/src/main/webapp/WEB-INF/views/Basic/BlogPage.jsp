<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>Blog Page</title>
<!-- Favicon-->
<style type="text/css">
.pagination {
	list-style-type: none;
	display: inline-block;
}

.pagination li {
	display: inline;
	margin-right: 5px;
}

.pagination li a {
	text-decoration: none;
	padding: 5px 10px;
	border: 1px solid #ccc;
	background-color: #f7f7f7;
}

.blog-hit-item {
	white-space: nowrap; /* 줄 바꿈 방지 */
	overflow: hidden; /* 내용 숨기기 */
	text-overflow: ellipsis; /* 긴 내용에 대한 생략 부호 (...) */
	margin-bottom: 5px; /* 아래 여백 추가 (선택적) */
}

.blog-hit-item a {
	color: black; /* 링크 텍스트 색상을 검은색으로 설정 */
	text-decoration: none; /* 밑줄 제거 */
}
</style>

</head>
<body>
	<%@ include file="/WEB-INF/views/Includes/Menu.jsp"%>
	<!-- Page content-->
	<div class="container">
		<div class="row">
			<!-- Blog entries-->
			<div class="col-lg-9">
				<!-- Featured blog post-->
				<c:forEach items="${BlogList}" var="BlogListMap">
					<div class="card mb-4">
						<div class="BlogDiv VOIXBODERLINE" style="display: flex; border-radius: 10px;">
							<c:if test="${BlogListMap.BGIMG != null }">
								<div class="BlogImg">
									<a href="/BlogInfoPage?bgcode=${BlogListMap.BGCODE}">
										<img class="" src="${BlogListMap.BGIMG}" alt="..." style="width: 350px; height: 300p object-fit: cover;" />
									</a>
								</div>
							</c:if>
							<c:if test="${BlogListMap.BGIMG == null }">
								<div class="BlogImg">
									<a href="/BlogInfoPage?bgcode=${BlogListMap.BGCODE}">
										<img class="" src="https://dummyimage.com/200x200/c1e3cd/ffffff.jpg" style="width: 350px; height: 300px; object-fit: cover;" alt="..." />
									</a>
								</div>
							</c:if>
							<div class="BlogText" style="flex: 1;">
								<div class="BlogTitle">
									<h2 class="card-title m-2" style="overflow: hidden; height: 75px;">${BlogListMap.BGTITLE}</h2>
<button style="margin-left: 7px; border-radius: 7px; background-color: #ede9e7;" onclick="chatPage()">채팅페이지</button>

								</div>
								<div class="BlogContents p-2" style="height: 150px; overflow: hidden;">
									<p class="card-text">${BlogListMap.BGCONTENT}</p>
								</div>
								<div class="small text-mute m-2" style="display: flex; justify-content: space-between; align-items: flex-end;">
									<!-- ---------------------------------------------------------------------------- -->
									<a class="Views" style="text-decoration-line: none; color: gray;">작성자: ${BlogListMap.BGWRITER}</a>
									<a class="Views" style="text-decoration-line: none; color: gray;">조회수: ${BlogListMap.BGBIGHIT}</a>
									<a class="Views" style="text-decoration-line: none; color: gray;">${BlogListMap.BGDATE}</a>
									
									<c:choose>
									    <c:when test="${BlogListMap.BGLIKED eq 'true'}">
									        <div class="like_article" onclick="like('${BlogListMap.BGCODE}', this)">
									            <a href="#" class="prdLike">
									                <img alt="" src="/resources/assets/heart.png" style="width: 30px;">
									            </a>
									        </div>
									    </c:when>
									    <c:otherwise>
									        <div class="like_article" onclick="like('${BlogListMap.BGCODE}', this)">
									            <a href="#" class="prdLike">
									                <img alt="" src="/resources/assets/blankheart.png" style="width: 30px;">
									            </a>
									        </div>
									      
									    </c:otherwise>
									</c:choose>
									
								</div>
							</div>
						</div>
					</div>
				</c:forEach>


			</div>
			<!-- end Page content-->
			<%@ include file="/WEB-INF/views/Includes/Side.jsp"%>
		</div>
	</div>
	<ul class="pagination" style="place-content: center;">
		    <c:if test="${pageMaker.prev }">
		    <li>
		        <a href="/BlogPage?page=${pageMaker.startPage-1}" style="color: #5e504e">
		   			<i class="fa fa-chevron-left"></i>◀
		   		</a>
		    </li>
		    <!-- <a href='<c:url value="/NewsPage?page=${pageMaker.startPage-1 }"/>'><i class="fa fa-chevron-left"></i></a> -->
		    </c:if>
		    <c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="pageNum" >
		    <li>
		        <a href='<c:url value="/BlogPage?page=${pageNum }"/>' style="color: #5e504e"><i class="fa">${pageNum }</i></a>
		    </li>
		    </c:forEach>
		    <c:if test="${pageMaker.next && pageMaker.endPage >0 }">
		    <li>
		        <a href='<c:url value="/BlogPage?page=${pageMaker.endPage+1 }"/>' style="color: #5e504e">▶<i class="fa fa-chevron-right"></i></a>
		    </li>
		    </c:if>
	</ul>
	<!-- Footer-->
	<footer class="py-5 bg-dark">
		<div class="container">
			<p class="m-0 text-center text-white">위 페이지의 출력되는 정보는 우측 상단에 있는 데이터 클롤링 및 페이지 양식을 인용하여 제작되었습니다.</p>
		</div>
	</footer>
	<!-- Bootstrap core JS-->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
	<!-- Core theme JS-->

	<!-- if(loginId.length === 0){ -->
	<script type="text/javascript">
    let loginId = '${sessionScope.loginId}';
    function like(blogCode, element) {
        console.log(loginId);
        console.log(blogCode);
        if (loginId.length === 0) {
            alert("로그인을 먼저 해주세요.");
            location.href = "/LoginPage";
        } else {

        	$.ajax({
        	    type: "GET",
        	    url: "likeBlog",
        	    data: {
        	        "like": blogCode
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
<script type="text/javascript">
		function chatPage() {
			let chatId = '${sessionScope.loginId}';
			console.log(chatId);
			window.open("/chatPage?chatId="+chatId, "chatbot",
					"width=400, height=600");
		if (chatId == null) {
			window.close();
		}
		}
	</script>
</body>


</html>
