<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.front_inform.model.*"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>Front_Inform.jsp</title>

<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link
	href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Great+Vibes&display=swap"
	rel="stylesheet">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/open-iconic-bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/animate.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/owl.carousel.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/owl.theme.default.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/magnific-popup.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/aos.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/ionicons.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/bootstrap-datepicker.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/jquery.timepicker.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/flaticon.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/icomoon.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/style.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/fiNmsg.css">

<style>

.blank {
	width: 500px;
	height: 1000px;
}

.modal-open .top,
.modal-open .msg {
padding-right: 17px;
}

</style>
</head>



<body>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	
	
	
	<%-- 小鈴鐺 table 開始 --%>
	<table id="fi_cont" style="border:0;display:none;">
		<c:forEach var="front_informVO" items="${front_informVOs}">
			<tr name="${(front_informVO.read_sts == 0) ? 'unread':'read'}" >
				<c:choose>
				   <c:when test="${front_informVO.info_sts == 2}">
						<td style="width:300px;">${front_informVO.info_cont}
						<br>
						<div class="d-flex justify-content-end">
							<button id="${front_informVO.info_no}yes" style="margin-right:3px" onclick="confirm('${front_informVO.info_no}', '${front_informVO.res_no}')">確認</button>
							<button id="${front_informVO.info_no}no" onclick="cancel('${front_informVO.info_no}', '${front_informVO.res_no}')">取消</button>
						</div>
						</td>
				   </c:when>
				   <c:when test="${front_informVO.info_sts == 1}">
						<td style="width:300px;">${front_informVO.info_cont}
						<br>
						<div class="d-flex justify-content-end">
							<span>已確認</span>
						</div>
						</td>
				   </c:when>
				   <c:when test="${front_informVO.info_sts == 3}">
						<td style="width:300px;">${front_informVO.info_cont}
						<br>
						<div class="d-flex justify-content-end">
							<span>已取消</span>
						</div>
						</td>
				   </c:when>
				   <c:otherwise>
						<td style="width:300px;">${front_informVO.info_cont}</td>
				   </c:otherwise>
				</c:choose>
				
				<td style="width:100px;"><fmt:formatDate value="${front_informVO.info_date}"
						pattern="yyyy-MM-dd" /></td>
			</tr>
		</c:forEach>
	</table>
	<%-- 小鈴鐺 table 結束 --%>
	
	
	
	<%-- 客服聊天室開始 --%>
	<div class="msg">
		<button type="button" class="btn" style="box-shadow: 0 0 0; padding:0px" onclick="popMsg()">
			<img id="custSvc" src="<%=request.getContextPath()%>/front-end/images/msg.png">
		</button>
		<div>
		</div>
	</div>
	<%-- 客服聊天室結束 --%>



	<%-- 以下為 navbar 內容 --%>
	<div class="py-1 bg-black top">
		<div class="container">
			<div
				class="row no-gutters d-flex align-items-start align-items-center px-md-0">
				<div class="col-lg-12 d-block">
					<div class="row d-flex">
						<div class="col-md pr-4 d-flex topper align-items-center">
							<div
								class="icon mr-2 d-flex justify-content-center align-items-center">
								<span class=""></span>
							</div>
							<span class="text"></span>
						</div>
						<div class="col-md-5 pr-4 d-flex topper align-items-center text-lg-right justify-content-end">
							<p class="mb-0 register-link">
								<%-- 小鈴鐺圖示開始 --%>
								<span class="mybb">
									<span class="badge"> </span>
									<button type="button" class="btn" style="color: #c8a97e; padding: 0px;" onclick="popFrontInform()">
										<svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-bell-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
										<path d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2zm.995-14.901a1 1 0 1 0-1.99 0A5.002 5.002 0 0 0 3 6c0 1.098-.5 6-2 7h14c-1.5-1-2-5.902-2-7 0-2.42-1.72-4.44-4.005-4.901z" /></svg>
									</button>
								</span>
								<%-- 小鈴鐺圖示結束 --%>
								<span class="mybb"><a href="menu.html" class="myaa">會員中心</a></span>
								<span class="mybb"><a href="blog.html" class="myaa">註冊</a></span>
								<span class="mybb"><a href="contact.html" class="myaa">登入</a></span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<nav
		class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light navbar-fixed-top"
		id="ftco-navbar">
		<div class="container">
			<a class="navbar-brand" href="index.html">吃 Pot 吧</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#ftco-nav" aria-controls="ftco-nav"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="oi oi-menu"></span> Menu
			</button>
			<div class="collapse navbar-collapse" id="ftco-nav">
				<ul class="navbar-nav ml-auto">
					<li class="nav-item"><a href="index.html" class="nav-link">推薦菜色</a></li>
					<li class="nav-item"><a href="about.html" class="nav-link">最新消息</a></li>
					<li class="nav-item"><a href="menu.html" class="nav-link">評價總覽</a></li>
					<li class="nav-item"><a href="blog.html" class="nav-link">餐廳資訊</a></li>
					<li class="nav-item"><a href="contact.html" class="nav-link">候位狀況</a></li>
					<li class="nav-item cta"><a href="reservation.html"
						class="nav-link">我要訂餐</a></li>
					<li class="nav-item cta"><a href="reservation.html"
						class="nav-link">我要訂位</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<%-- navbar 內容結束 --%>



	<%-- 以下為輪播的地方 --%>
	<section class="home-slider owl-carousel js-fullheight">
		<div class="slider-item js-fullheight"
			style="background-image: url(<%=request.getContextPath()%>/front-end/images/carousel_1.jpg);">
			<div class="overlay"></div>
			<div class="container">
				<div
					class="row slider-text js-fullheight justify-content-center align-items-center"
					data-scrollax-parent="true">
					<div class="col-md-12 col-sm-12 text-center ftco-animate">
						<span class="subheading">吃 Pot 吧</span>
						<h1 class="mb-4">Best Restaurant</h1>
					</div>
				</div>
			</div>
		</div>
		<div class="slider-item js-fullheight"
			style="background-image: url(<%=request.getContextPath()%>/front-end/images/carousel_2.jpg);">
			<div class="overlay"></div>
			<div class="container">
				<div
					class="row slider-text js-fullheight justify-content-center align-items-center"
					data-scrollax-parent="true">
					<div class="col-md-12 col-sm-12 text-center ftco-animate">
						<span class="subheading">吃 Pot 吧</span>
						<h1 class="mb-4">Nutritious &amp; Tasty</h1>
					</div>
				</div>
			</div>
		</div>
		<div class="slider-item js-fullheight"
			style="background-image: url(<%=request.getContextPath()%>/front-end/images/carousel_3.jpg);">
			<div class="overlay"></div>
			<div class="container">
				<div
					class="row slider-text justify-content-center align-items-center"
					data-scrollax-parent="true">
					<div class="col-md-12 col-sm-12 text-center ftco-animate">
						<span class="subheading">吃 Pot 吧</span>
						<h1 class="mb-4">Delicious Specialties</h1>
					</div>
				</div>
			</div>
		</div>
	</section>
	<%-- 輪播的地方結束 --%>
	
	
	
	<%-- 內容放置區域開始 --%>
	<section>
		<div class="blank"></div>
	</section>
	<%-- 內容放置區域結束 --%>
	
	
	
	<%-- footer 開始 --%>
	<footer class="ftco-footer ftco-bg-dark ftco-section">
		<div class="container">
			<div class="row mb-5">
				<div class="col-md-6 col-lg-3">
					<div class="ftco-footer-widget mb-4">
						<h2 class="ftco-heading-2">吃 Pot 吧</h2>
						<p>Far far away, behind the word mountains, far from the
							countries Vokalia and Consonantia, there live the blind texts.</p>
						<ul
							class="ftco-footer-social list-unstyled float-md-left float-lft mt-3">
							<li class="ftco-animate"><a href="#"><span
									class="icon-twitter"></span></a></li>
							<li class="ftco-animate"><a href="#"><span
									class="icon-facebook"></span></a></li>
							<li class="ftco-animate"><a href="#"><span
									class="icon-instagram"></span></a></li>
						</ul>
					</div>
				</div>
				<div class="col-md-6 col-lg-3">
					<div class="ftco-footer-widget mb-4">
						<h2 class="ftco-heading-2">營業時間</h2>
						<ul class="list-unstyled open-hours">
							<li class="d-flex"><span>週一</span><span>9:00 - 24:00</span></li>
							<li class="d-flex"><span>週二</span><span>9:00 - 24:00</span></li>
							<li class="d-flex"><span>週三</span><span>公休時間</span></li>
							<li class="d-flex"><span>週四</span><span>9:00 - 24:00</span></li>
							<li class="d-flex"><span>週五</span><span>9:00 - 02:00</span></li>
							<li class="d-flex"><span>週六</span><span>9:00 - 02:00</span></li>
							<li class="d-flex"><span>週七</span><span>9:00 - 02:00</span></li>
						</ul>
					</div>
				</div>
				<div class="col-md-6 col-lg-3">
					<div class="ftco-footer-widget mb-4">
						<h2 class="ftco-heading-2">Instagram</h2>
						<div class="thumb d-sm-flex">
							<a href="#" class="thumb-menu img"
								style="background-image: url(<%=request.getContextPath()%>/front-end/images/insta-1.jpg);"> </a> <a
								href="#" class="thumb-menu img"
								style="background-image: url(<%=request.getContextPath()%>/front-end/images/insta-2.jpg);"> </a> <a
								href="#" class="thumb-menu img"
								style="background-image: url(<%=request.getContextPath()%>/front-end/images/insta-3.jpg);"> </a>
						</div>
						<div class="thumb d-flex">
							<a href="#" class="thumb-menu img"
								style="background-image: url(<%=request.getContextPath()%>/front-end/images/insta-4.jpg);"> </a> <a
								href="#" class="thumb-menu img"
								style="background-image: url(<%=request.getContextPath()%>/front-end/images/insta-5.jpg);"> </a> <a
								href="#" class="thumb-menu img"
								style="background-image: url(<%=request.getContextPath()%>/front-end/images/insta-6.jpg);"> </a>
						</div>
					</div>
				</div>
				<div class="col-md-6 col-lg-3">
					<div class="ftco-footer-widget mb-4">
						<h2 class="ftco-heading-2">Newsletter</h2>
						<p>Far far away, behind the word mountains, far from the
							countries.</p>
						<form action="#" class="subscribe-form">
							<div class="form-group">
								<input type="text" class="form-control mb-2 text-center"
									placeholder="Enter email address"> <input type="submit"
									value="Subscribe" class="form-control submit px-3">
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 text-center">
					<p>
						<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
						Copyright &copy;
						<script>
							document.write(new Date().getFullYear());
						</script>
						All rights reserved | This template is made with <i
							class="icon-heart" aria-hidden="true"></i> by <a
							href="https://colorlib.com" target="_blank">EA103G7</a>
						<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
					</p>
				</div>
			</div>
		</div>
	</footer>
	<%-- footer 結束 --%>
	
	
	
	<%-- loader ( 網頁尚在 loading 中時會出現的一個小圈圈 ) 開始 --%>
	<div id="ftco-loader" class="show fullscreen">
		<svg class="circular" width="48px" height="48px">
            <circle class="path-bg" cx="24" cy="24" r="22" fill="none"
				stroke-width="4" stroke="#eeeeee" />
            <circle class="path" cx="24" cy="24" r="22" fill="none"
				stroke-width="4" stroke-miterlimit="10" stroke="#F96D00" /></svg>
	</div>
	<%-- loader 結束 --%>
	
	
	
	<%-- Modal (擋住未登入的會員點選已登入會員才可看到的畫面) 開始 --%>
	<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">您尚未登入</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        請先登入或註冊會員
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">關閉</button>
	        <button type="button" class="btn btn-primary" onclick="location.href='contact.html'">我要登入</button>
	      </div>
	    </div>
	  </div>
	</div>
	<%-- Modal (擋住未登入的會員點選已登入會員才可看到的畫面) 結束 --%>
	
	
	
	<%-- script 開始 --%>
	<script src="<%=request.getContextPath()%>/front-end/js/jquery.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/js/bootstrap.min.js"></script>
	<script>
		<%-- 可在 modal 處自由加入想要擋住的內容 --%>
		var nb = $('nav.navbar-fixed-top');
		$('.modal')
	    .on('show.bs.modal', function () {
	        nb.width(nb.width());
	    })
	    .on('hidden.bs.modal', function () {
	        nb.width(nb.width('auto'));
	    });
		
		
		
		<%-- 小鈴鐺點擊後會產生的動作 --%>
		var mem_no="<%=request.getParameter("mem_no")%>"
		if(document.getElementsByName("unread").length > 0){
			document.getElementsByClassName("badge")[0].style.display = "inline-block";
		}else{
			document.getElementsByClassName("badge")[0].style.display = 'none';
		}
		function popFrontInform(){
			let fi_cont = document.getElementById("fi_cont");
			
			// 已讀未讀顯示顏色
			
			let readColor = document.querySelectorAll('[name="	read"]');
			for(let i=0; i<readColor.length ;i++){
				readColor[i].style.backgroundColor = "#fff"; 
			}
			let unreadColor = document.querySelectorAll('[name="unread"]');
			for(let i=0; i<unreadColor.length ;i++){
				unreadColor[i].style.backgroundColor = "#e6f9ff"; 
				unreadColor[i].setAttribute('name','read');
			}
			
			// 紅點已讀消失
			document.getElementsByClassName("badge")[0].style.display = "none";			
			
			// 已讀未讀狀態修改
			$.ajax({
				 url:'fi.do',
				 method:"POST",
				 dataType:"text",
				 data:{
					 action: 'updateReadSts',
					 mem_no: mem_no,
				 },
				 success:function(res){
					if(fi_cont.style.display == "none"){
						fi_cont.style.display = "block";
						fi_cont.style.position = "fixed";
						fi_cont.style.zIndex = '550';
						fi_cont.style.top = '40px';
					    fi_cont.style.right = '20%';
					    fi_cont.style.fontSize = '10px';
					    fi_cont.style.width = '400px';
					    fi_cont.style.height = '300px';
					    fi_cont.style.borderRadius = '10px';
					    fi_cont.style.overflow = 'auto';
					}else{
						fi_cont.style.display = "none";
					}
				 },
				 error:function(err){
					console.log(err);
					fi_cont.style.display = "none";
					$('#loginModal').modal('show');
				 },	
			});
			
		}		
		function confirm(info_no, res_no){
			$.ajax({
				 url:'fi.do',
				 method:"POST",
				 dataType:"text",
				 data:{
					 action: 'updateSts',
					 info_no: info_no,
					 mem_no: mem_no,
					 res_no: res_no,
					 checkYes: 'checkYes'
				 },
				 success:function(res){
				 },
				 error:function(err){console.log(err)},	
			});
			document.getElementById(info_no+"no").disabled="disabled";
		}
		function cancel(info_no, res_no){
			$.ajax({
				 url:'fi.do',
				 method:"POST",
				 dataType:"text",
				 data:{
					 action: 'updateSts',
					 info_no: info_no,
					 mem_no: mem_no,
					 res_no: res_no,
					 checkNo: 'checkNo'
				 },
				 success:function(res){
				 },
				 error:function(err){console.log(err)},	
			});			
			document.getElementById(info_no+"yes").disabled="disabled";
		}
		
		
		<%-- 客服對話框點擊後會產生的動作...尚未完成...QQ... --%>
		function popMsg(){
			console.log("QQ")
		}
	</script>
	<script src="<%=request.getContextPath()%>/front-end/js/jquery-migrate-3.0.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/js/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/js/jquery.easing.1.3.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/js/jquery.waypoints.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/js/jquery.stellar.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/js/owl.carousel.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/js/jquery.magnific-popup.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/js/aos.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/js/jquery.animateNumber.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/js/bootstrap-datepicker.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/js/jquery.timepicker.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/js/scrollax.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/js/main.js"></script>
	<%-- script 結束 --%>
</body>
</html>