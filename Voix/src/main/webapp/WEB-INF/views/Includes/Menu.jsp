<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
<link href="resources/css/styles.css" rel="stylesheet" />
<c:if test="${msg != null }">
	<script type="text/javascript">
    			alert('${msg}');
   			</script>
</c:if>
<style>
.homePageLink {
	font-size: 16px;
	margin-right: 12px;
	color: #ccc;
	text-decoration: none;
}

.navBarMain {
	margin-left: 60px;
	margin-right: 60px;
}

.VoixImg {
	width: 100%;
	height: 200px;
	overflow: hidden;
}

.navbar-brand.form-control2 {
	width: 100%;
	padding: 0.375rem 0.75rem;
	border-radius: 0.375rem;
	font-size: 1rem;
	font-weight: 400;
	line-height: 1.5;
	color: #212529;
	border: none;
	float: left;
}

.btn-primary {
	width: 100%;
	background-color: #5e504e;
	border: 1px solid #5e504e;
	color: ##ccc;
 	border-radius: 0.375rem;
	transition: background-color 0.15s ease-in-out, border-color 0.15s ease-in-out;
	float: right;
}

.navbar-brand {
	cursor: pointer;
	border-top: 1px solid #d9f0de;
}

.navbar-brand.click {
	border-left: 4px solid #5e504e;
	border-top: 4px solid #5e504e;
	border-right: 4px solid #5e504e;
	background: #ede9e7;
}

.navbar-brand.unclicked {
	border-bottom: 4px solid #5e504e;
}

.navbar-brand.form-control2.click {
	width: 100%;
	padding: 0.375rem 0.75rem;
	border-radius: 0.375rem;
	font-size: 1rem;
	font-weight: 400;
	line-height: 1.5;
	color: #212529;
	border: none; /* 테두리 삭제 */
	border-left: 4px solid #5e504e;
	border-right: 4px solid #5e504e;
	border-bottom: 4px solid #5e504e;
}

.navbar-brand.form-control2.unclicked {
	width: 100%;
	padding: 0.375rem 0.75rem;
	border-radius: 0.375rem;
	font-size: 1rem;
	font-weight: 400;
	line-height: 1.5;
	color: #212529;
	border: none;
	border-left: 4px solid #5e504e;
	border-right: 4px solid #5e504e;
	border-bottom: 4px solid #5e504e;
}

.navbar-brand.form-control2.unclicked.click {
	width: 100%;
	padding: 0.375rem 0.75rem;
	border-radius: 0.375rem;
	font-size: 1rem;
	font-weight: 400;
	line-height: 1.5;
	color: white;
	border: none; /* 테두리 삭제 */
	border-left: 4px solid #5e504e;
	border-right: 4px solid #5e504e;
	border-bottom: 4px solid #5e504e;
	outline: none;
}

table {
	width: 100%;
	border-collapse: collapse;
	border-spacing: 0;
	margin-left: 0;
}

th {
	text-align: center;
}

th {
	font-weight: bold;
	cursor: pointer;
	font-size: 80px;
}

.hover:hover {
	background: #5e504e;
	border-top: 1px solid #5e504e;
}
body{
	background: #ede9e7 !important;
}
</style>

<!-- white-space: nowrap; -->
<!-- Responsive navbar-->
<nav class="navbar navbar-expand-lg navbar-dark" style="background-color: #5e504e;">

	<div class="container">
		<a class="homePageLink" href="https://www.melon.com/">멜론</a> <a class="homePageLink" href="https://vibe.naver.com/today">바이브</a> <a class="homePageLink" href="https://music.bugs.co.kr/">벅스</a> <a class="homePageLink" href="https://www.genie.co.kr/">지니</a> <a class="homePageLink" href="https://www.billboard.com/charts/hot-100/">빌보드</a> <a class="homePageLink" href="http://ticket.yes24.com/">예스24</a> <a class="homePageLink" href="https://www.interpark.com/">인터파크</a> <a class="homePageLink" href="https://www.aladin.co.kr/m/main.aspx">알라딘</a> <a class="homePageLink" href="https://product.kyobobook.co.kr/detail/S000001913217">교보문고</a> <a class="homePageLink" href="https://www.youtube.com/">유튜브</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav ms-auto mb-2 mb-lg-0">
				<c:choose>
					<c:when test="${sessionScope.loginId == null}">
						<li class="nav-item"><a class="nav-link" href="/LoginPage">로그인</a></li>
						<li class="nav-item"><a class="nav-link" href="/JoinPage">회원가입</a></li>
					</c:when>

					<c:otherwise>
						<li class="nav-item"><a class="nav-link" href="/memberLogout">로그아웃</a></li>
						<li class="nav-item"><a class="nav-link" href="/MyInfoPage">내정보</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>
</nav>
<!-- Page header with logo and tagline-->
<header class="mb-4" style="background-color: #ede9e7;">
	<div class="container">

		<table class="navbar-dark">
			<tr>
				<th class="navbar-brand fs-4" style="width: 200px;">
					<a href="/" id="clickImg">
			 			<img class="w-100" alt="" src="/resources/assets/VoixImg5.png">
			 		</a>
			 	</th>
				<th class="navbar-brand p-3 hover  fs-4" id="news" onClick="location.href='NewsPage'" style="color:black;">뉴스</th>
				<th class="navbar-brand p-3 hover fs-4" id="blog" onClick="location.href='BlogPage'" style="color:black;">블로그</th>
				<th class="navbar-brand p-3 hover fs-4" id="ticket" onClick="location.href='TicketPage'" style="color:black;">티켓</th>
				<th class="navbar-brand p-3 hover fs-4" id="album" onClick="location.href='AlbumPage'" style="color:black;">앨범</th>
				<th class="navbar-brand p-3 hover fs-4" id="chart" onClick="location.href='ChartPage'" style="color:black;">랭킹비교</th>
				<th class="navbar-brand p-3 hover fs-4" id="price" onClick="location.href='PricePage'" style="color:black;">가격비교</th>
			</tr>
			<!--  
			<tr>
				<td colspan="6">
				<input class="navbar-brand form-control2" type="text"
				placeholder="Enter search term..." aria-label="Enter search term..."
				aria-describedby="button-search" />
	
			<button class="btn btn-primary" id="button-search" type="button">Go!</button>
				</td>			
			</tr>
			  -->
		</table>
		<c:if test="${sessionScope.SerchState == 'Y'}">
		<div class="searchbar" style="margin-bottom: 7rem !important;">
				<c:if test="${param.siteVal != null}">
				<form action="/getSearchRank">
				<input type="hidden" id=Sitevalue name="Sitevalue" value="">
				<input class="navbar-brand form-control2" id="searchKeyword" name="searchKeyword" type="text" placeholder="검색어 입력" aria-label="검색어 입력..." aria-describedby="button-search" 
				style="width:85%;"/>
				<button class="btn btn-primary" id="button-search" type="submit" style="width:15%">Go!</button>
				</form>
				</c:if>
				<c:if test="${param.siteVal == null}">
				<form action="/getSearch">
				<input type="hidden" id="pageType" name="pageType" value="">
				<input class="navbar-brand form-control2" id="searchKeyword" name="searchKeyword" type="text" placeholder="검색어 입력" aria-label="검색어 입력..." aria-describedby="button-search" 
				style="width:85%;"/>
				<button class="btn btn-primary" id="button-search" type="submit" style="width:15%">Go!</button>
				</form>
				</c:if>
		</div>
		</c:if>
		<!--  
				<a class="navbar-brand-click" id="news"  href="/NewsPage">뉴스</a> 
				<a class="navbar-brand" id="blog"  href="/BlogPage">블로그</a> 
				<a class="navbar-brand" id="ticket"href="/TicketPage">티켓</a> 
				<a class="navbar-brand" id="album" href="/AlbumPage">앨범</a> 
				<a class="navbar-brand" id="chart" href="/ChartPage">랭킹비교</a> 
				<a class="navbar-brand" id="price" href="/PricePage">가격비교</a>
			-->
	</div>




	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
	<script type="text/javascript">
    const clickImg = document.getElementById("clickImg");
    const colorChange = document.querySelectorAll(".navbar-brand");
    const formControl2 = document.querySelector(".navbar-brand.form-control2");

    // 메인 페이지 클릭하면 색상 초기화
    clickImg.addEventListener("click", function () {
        colorChange.forEach((navLink) => {
            navLink.classList.remove("click");
        });
        formControl2.classList.remove("unclicked");
        localStorage.removeItem("selectedMenu");
    });

    // 모든 "navbar-brand"에 클릭 이벤트 추가
    colorChange.forEach(function (menu) {
        menu.addEventListener("click", function () {
            // 클릭한 메뉴에 "clicked" 클래스 추가
            menu.classList.add("click");

            // 다른 메뉴에서 "clicked" 클래스 제거
            colorChange.forEach(function (otherMenu) {
                if (otherMenu !== menu) {
                    otherMenu.classList.remove("click");
                    otherMenu.classList.add("unclicked");
                }
            });

            // "unclicked" 클래스를 formControl2에 추가하여 해당 스타일을 적용
            formControl2.classList.add("unclicked");

            // 선택한 메뉴를 로컬 스토리지에 저장
            localStorage.setItem("selectedMenu", menu.id);
        });
    });

    // 페이지가 로드될 때 저장된 메뉴 클릭 상태 확인
    window.addEventListener("load", function () {
        const selectedMenuId = localStorage.getItem("selectedMenu");
        if (selectedMenuId) {
            // 저장된 메뉴에 "clicked" 클래스 추가하여 색상 복원
            const selectedMenu = document.getElementById(selectedMenuId);
            selectedMenu.classList.add("click");
            
            
            // "unclicked" 클래스도 추가
            colorChange.forEach(function (menu) {
                if (menu.id !== selectedMenuId) {
                    menu.classList.add("unclicked");
                }
            });
        }
    });
</script>

	<script type="text/javascript">
		let currentURL = window.location.href;
		let currentPath = currentURL.split("/")[3].split('?')[0];	//BlogPage?	
		pageId = document.querySelector('#pageType');
		pageId.value = currentPath;		
		console.log(pageId.value);
	</script>

	<script type="text/javascript">
		let currentURLFUCK = window.location.href;
		let currentPathFUCK = currentURLFUCK.split("/")[3];

		pageId = document.querySelector('#Sitevalue');
		pageId.value = currentPathFUCK;	
		console.log(pageId.value);
	</script>




</header>
