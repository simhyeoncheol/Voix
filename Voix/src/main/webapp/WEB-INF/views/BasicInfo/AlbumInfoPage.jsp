<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<style type="text/css">
line {
   display: inline-block;
}

albuminfo {
   padding: 5px !important;
}

button {
   width: 100%;
   background-color: gray;
   border: none;
}
.disNone{
   display: none;
}

</style>
</head>
<body>
   <!-- Responsive navbar-->
   <%@ include file="/WEB-INF/views/Includes/Menu.jsp"%>
   <!-- Page content-->
   <div class="container">
      <div>
         <div class="row">

            <c:if test="${ALInfo.alimg != null }">
               <div class="AlbumImg">
                  <img class="" src="${ALInfo.alimg}" alt="..." />
               </div>
            </c:if>
            <c:if test="${ALInfo.alimg == null }">
               <div class="AlbumImg">
                  <img class="" src="https://dummyimage.com/200x200/c1e3cd/ffffff.jpg" alt="..." />
               </div>
            </c:if>
            <div class="card col-md-8 mb-4">
               <p>${ALInfo.alartist}</p>
               <p>${ALInfo.alinfo}</p>
               <p>${ALInfo.aldate}</p>
            </div>


         </div>
      </div>

      <div class="card col-md-12 mb-2">${ALInfo.allist}</div>

      <div>
         <div class="row">
            <div class="card col-md-8 mb-2">
               <div class="card mb-2 mt-2">${ALInfo.alprice}</div>
               <c:forEach items="${AlbumInfoList}" var="AlbumInfoList">
                  <div class="card mb-2">
                     <input type="radio" name="al" id="${AlbumInfoList.alcode}" oninput ="is_checked(this)" value="${AlbumInfoList.alcode}">
                     ${AlbumInfoList.alsaleprice}원
                     <!--내일 이거 해야함 체크 표시 해야 나오게  -->
                     <input id="selectQty_${AlbumInfoList.alcode}" type="number" min="0" placeholder="수량" class="disNone">
                  </div>
               </c:forEach>
            </div>
            <div class="card col-md-4 mb-2 " style="display: inline-block;">
               <div>
                  <button type="submit" class="button mb-4 mt-4" onclick="formsubmit('CartPage')">장바구니 바로가기</button>
               </div>
               <div>
                  <button class="submit" onclick="formsubmit('PayPage')">결제 바로가기</button>
               </div>
            </div>
            
         </div>
      </div>

   </div>
   <div>
      <div class="borderline" style="overflow: scroll; height: 500px; width: 80%;">
         <div>
            댓글
            <input value="댓글모음">
         </div>
         <div>댓글</div>
         <div>댓글</div>
      </div>
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
   <script type="text/javascript">
      function is_checked(radio) {

         let radioEls = document.querySelectorAll('input[type="radio"]');
         for(let radioEl of radioEls){
            
            if(radioEl.checked){
               radioEl.nextElementSibling.classList.remove('disNone');
            } else {
               radioEl.nextElementSibling.classList.add('disNone');
            }
            
            
         }
      }
      function formsubmit(url){ //   /장바구니, /결제
        console.log(url);
        
        //let Qty = document.getElementById('').value;
        let checkEl = document.querySelector('input[type="radio"]:checked');
        console.log(checkEl.value);
        console.log(checkEl.nextElementSibling.value);
        location.href= "/"+url+"?caalcode="+checkEl.value+'&caqty='+ checkEl.nextElementSibling.value;
          
          
   
      }
   </script>
</body>
</html>