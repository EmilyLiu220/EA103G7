<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.front_inform.model.*"%>
<%@ page import="com.message_record.model.*"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="com.ad.model.*"%>
<%
	MemVO memVO2 = (MemVO) session.getAttribute("memVO2");
%>
<%
	AdService adSvc = new AdService(); 
     List<AdVO> list = adSvc.find_adsts(1); 
     request.setAttribute("list",list);
%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>前台首頁</title>

<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link
	href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Great+Vibes&display=swap"
	rel="stylesheet">

<link rel="icon" type="image/png" sizes="32x32"
	href="<%=request.getContextPath()%>/images/pot.png">

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/open-iconic-bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/animate.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/owl.carousel.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/owl.theme.default.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/magnific-popup.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/aos.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/ionicons.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/bootstrap-datepicker.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/jquery.timepicker.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/flaticon.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/icomoon.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/style.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/fiNmsg.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/css/toastr.min.css">

<style>
.unshow {
	display: none;
}

.adimg {
	max-height: 100%;
	max-width: 100%;
}

.ad2 {
	width: 50%;
	height: 750px;
/* 	margin: 0px auto; */ 
left:750px;
	
}

.ad3 {
	height:850px;
	background-image:
		url('<%=request.getContextPath()%>/front-end/images/newsBg.jpg');
	
}

.ad {
	margin-top: 100px;
	left: 500px;
}

.adimg {
	position: relative;
	top: 300px;
	left: 400px;
}

</style>

</head>

<body>
	<%-- 小鈴鐺 table 開始 --%>
	<table id="fi_cont" style="border: 0; display: none;">
	</table>
	<%-- 小鈴鐺 table 結束 --%>


	<%-- 客服聊天室開始 --%>
	<div class="msg">
		<div id="addClass">
			<img id="custSvc"
				src="<%=request.getContextPath()%>/front-end/images/msg.png">
		</div>
		<div class="container">
			<div>
				<aside id="sidebar_secondary"
					class="tabbed_sidebar ng-scope chat_sidebar">
					<div class="popup-head">
						<div class="popup-head-right pull-right row justify-content-end">
							<button data-widget="remove" id="removeClass"
								class="chat-header-button pull-right" type="button">
								<img id="chatClose"
									src="<%=request.getContextPath()%>/front-end/images/x.png">
							</button>
						</div>
					</div>
					<div id="chat"
						class="chat_box_wrapper chat_box_small chat_box_active"
						style="opacity: 1; display: block; transform: translateX(0px); background: #d6fdff;">
					</div>
					<div class="chat_submit_box">
						<div class="uk-input-group">
							<div class="gurdeep-chat-box">
								<input type="text" placeholder="Type a message"
									id="submit_message" name="submit_message" class="md-input">
							</div>
							<span class="uk-input-group-addon"> <img id="sendMsg"
								src="<%=request.getContextPath()%>/front-end/images/send.png">
							</span>
						</div>
					</div>
				</aside>
			</div>
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
						<div
							class="col-md-5 pr-4 d-flex topper align-items-center text-lg-right justify-content-end">
							<p class="mb-0 register-link">
								<%-- 小鈴鐺圖示開始 --%>
								<span class="mybb"> <span class="badge"> </span>
									<button type="button" class="btn"
										style="color: #c8a97e; padding: 0px;"
										onclick="popFrontInform()">
										<svg width="1em" height="1em" viewBox="0 0 16 16"
											class="bi bi-bell-fill" fill="currentColor"
											xmlns="http://www.w3.org/2000/svg">
										<path
												d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2zm.995-14.901a1 1 0 1 0-1.99 0A5.002 5.002 0 0 0 3 6c0 1.098-.5 6-2 7h14c-1.5-1-2-5.902-2-7 0-2.42-1.72-4.44-4.005-4.901z" /></svg>
									</button>
								</span>
								<%-- 小鈴鐺圖示結束 --%>
								<span class="mybb"><a
									href="<%=request.getContextPath()%>/front-end/mem/login_success_mem.jsp"
									class="myaa"><span id="member">會員中心</span></a></span> <span
									class="mybb"><a
									href="<%=request.getContextPath()%>/front-end/mem/addMem.jsp"
									class="myaa"><span id="sign">加入會員</span></a></span> <span class="mybb"><a
									href="<%=request.getContextPath()%>/front-end/mem/login_mem.jsp"
									class="myaa"><span id="login">登入</span></a></span> <span class="mybb"><span
									id="mem_name" class="unshow">${memVO2.mem_name}</span></span> <span
									class="mybb"><span id="hello" class="unshow">您好！</span></span>
								<span class="mybb"><a
									href="<%=request.getContextPath()%>/front-end/mem/mem.do?action=logout"
									class="myaa"><span id="logout" class="unshow">登出</span></a></span>
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
			<a class="navbar-brand"
				href="<%=request.getContextPath()%>/front-end/front_home.jsp">PoT</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#ftco-nav" aria-controls="ftco-nav"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="oi oi-menu"></span> Menu
			</button>
			<div class="collapse navbar-collapse" id="ftco-nav">
				<ul class="navbar-nav ml-auto">
					<li class="nav-item"><a
						href="<%=request.getContextPath()%>/front-end/front_home.jsp"
						class="nav-link">Home</a></li>
					<li class="nav-item"><a href="" class="nav-link">Menu</a></li>
					<li class="nav-item"><a
						href="<%=request.getContextPath()%>/front-end/front/front_news-11.jsp"
						class="nav-link">店訊新訊</a></li>
					<li class="nav-item"><a
						href="<%=request.getContextPath()%>/front-end/front/front_ad_new.jsp"
						class="nav-link">最新活動</a></li>
					<li class="nav-item"><a href="" class="nav-link">評價總覽</a></li>
					<li class="nav-item"><a href="" class="nav-link">餐廳資訊</a></li>
					<li class="nav-item"><a href="" class="nav-link">候位狀況</a></li>
					<li class="nav-item"><a href="" class="nav-link" id="meal">我要訂餐</a></li>
					<li class="nav-item"><a
						href="<%=request.getContextPath()%>/front-end/res_order/orderSeat.jsp"
						class="nav-link" id="res">我要訂位</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<!-- END nav -->

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
						<span class="subheading">吃胖吧</span>
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
						<span class="subheading">吃胖吧</span>
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
					class="row slider-text js-fullheight justify-content-center align-items-center"
					data-scrollax-parent="true">
					<div class="col-md-12 col-sm-12 text-center ftco-animate">
						<span class="subheading">吃胖吧</span>
						<h1 class="mb-4">Delicious Specialties</h1>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section class="ftco-section ftco-no-pt ftco-no-pb">
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-12">
					<div class="featured">
						<div class="row">
							<div class="col-md-3">
								<div class="featured-menus ftco-animate">
									<div class="menu-img img"
										style="background-image: url(<%=request.getContextPath()%>/front-end/front/images/wait.gif);"></div>
									<div class="text text-center">
										<h3>套餐A</h3>
										<p>
											<span>Meat</span>, <span>Potatoes</span>, <span>Rice</span>,
											<span>Tomatoe</span>
										</p>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="featured-menus ftco-animate">
									<div class="menu-img img"
										style="background-image: url(<%=request.getContextPath()%>/front-end/front/images/wait.gif);"></div>
									<div class="text text-center">
										<h3>套餐B</h3>
										<p>
											<span>Meat</span>, <span>Potatoes</span>, <span>Rice</span>,
											<span>Tomatoe</span>
										</p>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="featured-menus ftco-animate">
									<div class="menu-img img"
										style="background-image: url(<%=request.getContextPath()%>/front-end/front/images/wait.gif);"></div>
									<div class="text text-center">
										<h3>套餐C</h3>
										<p>
											<span>Meat</span>, <span>Potatoes</span>, <span>Rice</span>,
											<span>Tomatoe</span>
										</p>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="featured-menus ftco-animate">
									<div class="menu-img img"
										style="background-image: url(<%=request.getContextPath()%>/front-end/front/images/wait.gif);"></div>
									<div class="text text-center">
										<h3>套餐D</h3>
										<p>
											<span>Meat</span>, <span>Potatoes</span>, <span>Rice</span>,
											<span>Tomatoe</span>
										</p>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<section class="ftco-section img"
		style="background-image: url(<%=request.getContextPath()%>/front-end/images/storeBg.jpg)"
		data-stellar-background-ratio="0.5">
		<div class="container">
			<div class="row">
				<div class="col-md-7 d-flex">
					<div class="img img-1 mr-md-2"
						style="background-image: url(<%=request.getContextPath()%>/front-end/images/HA.gif);"></div>
					<div class="img img-2 ml-md-2"
						style="background-image: url(<%=request.getContextPath()%>/front-end/images/HA.gif);"></div>
				</div>
				<div class="col-md-5 wrap-about pt-5 pt-md-5 pb-md-3 ftco-animate">
					<div class="heading-section mb-4 my-5 my-md-0">
						<span class="subheading">About</span>
						<h2 class="mb-4">吃胖吧PoT</h2>
					</div>
					<p>這裡可以放我們餐廳的介紹ㄎㄎ</p>
					<p class="time">
						<span>Mon - Fri <strong>8AM - 11 PM</strong></span> <span><a
							href="#">+ 1-978-123-4567</a></span>
					</p>
				</div>
			</div>
		</div>
	</section>
	<!-- 		------------------------------ -->
	<div id="carouselExampleIndicators" class="carousel slide "
		data-ride="carousel">

		<ol class="carousel-indicators">
			<li data-target="#carouselExampleIndicators" data-slide-to="0"
				class="active"></li>
			<li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
			<li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
		</ol>
		<%-- 		<img src="<%=request.getContextPath()%>/front-end/images/null2.jpg" class="adimg"> --%>
		<div class="ad3 ">
			<img src="<%=request.getContextPath()%>/front-end/images/adadt.jpg"
				class="adimg">
			<div class="carousel-inner ad2">
				<%@ include file="/front-end/front/page1-1.file"%>
				<div class="row text-center text-lg-left" >
					<c:forEach var="adVO" items="${list}">
						<div class="carousel-item ad">

							<div class="col-lg-3 col-md-4 col-6">
								<label for="${adVO.ad_no}" class="d-block mb-4 h-100"> <img
									class="img-fluid img-thumbnail"
									src="<%=request.getContextPath() %>/ad/ad.do?add_no=${adVO.ad_no}"
									alt="">
								</label>

								<div>${adVO.ad_add_date}</div>

								<FORM METHOD="post"
									ACTION="<%=request.getContextPath()%>/ad/ad.do">
									<label for="${adVO.ad_no}" class="d-block mb-4 h-100 "
										style="font-size: 20px" id="ad_title">${adVO.ad_title}<br>
										<button type="submit" id="${adVO.ad_no}"
											class="btn btn-outline-dark" style="border-radius: 100%"
											name="action" value="getFrontOne_For_Display">詳</button>
									</label> <input type="hidden" name="ad_no" value="${adVO.ad_no}">
								</FORM>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>

		</div>


		<!-- 		------------------------------------------- -->
		<a class="carousel-control-prev" href="#carouselExampleIndicators"
			role="button" data-slide="prev"> <span
			class="carousel-control-prev-icon" aria-hidden="true"></span> <span
			class="sr-only">Previous</span>
		</a> <a class="carousel-control-next" href="#carouselExampleIndicators"
			role="button" data-slide="next"> <span
			class="carousel-control-next-icon" aria-hidden="true"></span> <span
			class="sr-only">Next</span>
		</a>
	</div>

	<!-- 		------------------------------ -->


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
								style="background-image: url(<%=request.getContextPath()%>/front-end/images/insta-1.jpg);">
							</a> <a href="#" class="thumb-menu img"
								style="background-image: url(<%=request.getContextPath()%>/front-end/images/insta-2.jpg);">
							</a> <a href="#" class="thumb-menu img"
								style="background-image: url(<%=request.getContextPath()%>/front-end/images/insta-3.jpg);">
							</a>
						</div>
						<div class="thumb d-flex">
							<a href="#" class="thumb-menu img"
								style="background-image: url(<%=request.getContextPath()%>/front-end/images/insta-4.jpg);">
							</a> <a href="#" class="thumb-menu img"
								style="background-image: url(<%=request.getContextPath()%>/front-end/images/insta-5.jpg);">
							</a> <a href="#" class="thumb-menu img"
								style="background-image: url(<%=request.getContextPath()%>/front-end/images/insta-6.jpg);">
							</a>
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
							href="https://colorlib.com" target="_blank">Colorlib</a>
						<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
					</p>
				</div>
			</div>
		</div>
	</footer>

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
	<div class="modal fade" id="loginModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">您尚未登入</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">請先登入或註冊會員</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-dismiss="modal">關閉</button>
					<button type="button" class="btn btn-primary"
						onclick="location.href='<%=request.getContextPath()%>/front-end/mem/login_mem.jsp'">我要登入</button>
				</div>
			</div>
		</div>
	</div>
	<%-- Modal (擋住未登入的會員點選已登入會員才可看到的畫面) 結束 --%>
</body>
<%-- script 開始 --%>
<script src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
<script src="<%=request.getContextPath()%>/front-end/js/toastr.min.js"></script>
<script type="text/javascript">
	<%-- 聊天室  webSocket --%>
	var MyPoint = "/Message_RecordWS/${memVO2.mem_no}"; 
	var host = window.location.host;
	var path = window.location.pathname;
	var webCtx = path.substring(0, path.indexOf('/', 1));
	var endPointURL = "ws://" + host + webCtx + MyPoint;
	
	var mem_no = "${memVO2.mem_no}"; // 宣告自己(用來分辨訊息要套用的 CSS)
	var messagesArea = document.getElementById("chat");
	
	var webSocket = new WebSocket(endPointURL);
		
	webSocket.onopen = function(event) {
		console.log("Connect Success!");
	};
	
	function callToast(){
		// 當有新訊息時，在客服小姐旁邊跳出 toast
		toastr.options = {
			"positionClass": "toast-bottom-right",
			"newestOnTop": true,
			"closeButton": true,
			"progressBar": true,
			"onclick": null,
			"showDuration": "500",
			"hideDuration": "1000",
			"timeOut": "10000",
			"extendedTimeOut": "1000",
			"showEasing": "swing",
			"hideEasing": "linear",
			"showMethod": "fadeIn",
			"hideMethod": "fadeOut"
		}
		toastr.info("您有新訊息！");
		
		$("#toast-container").click(function(){
			document.getElementById("addClass").click();
		})
		
	}
	
	webSocket.onmessage = function(event) {
		var jsonObj = JSON.parse(event.data); // 把發送來的字串資料轉成 json 物件
		if ("history" === jsonObj.type) { // 這次來的是歷史訊息內容
			console.log("收到 history 訊息");
			messagesArea.innerHTML = '';
			var chat_box = document.createElement('div');
			chat_box.setAttribute("class", "chat_box touchscroll chat_box_colors_a");
			messagesArea.appendChild(chat_box); // 將新增的歷史訊息區塊加進 chat 區塊
			// 這行的jsonObj.message是從redis撈出跟好友的歷史訊息，再parse成JSON格式處理
			var messages = JSON.parse(jsonObj.msgJson);
			for (var i = 0; i < messages.length; i++) {
				var historyData = JSON.parse(messages[i]);
				var chat_message_wrapper = document.createElement('div');
				chat_message_wrapper.classList.add("chat_message_wrapper");
				
				var chat_user_avatar = document.createElement('div');
				chat_user_avatar.classList.add("chat_user_avatar");
						
				var img = document.createElement('img');
				img.classList.add("md-user-image");
				if( historyData.sender === mem_no ){
					img.setAttribute("src","<%=request.getContextPath()%>/front-end/images/smallWu.jpg");
				}else{
					img.setAttribute("src","<%=request.getContextPath()%>/front-end/images/bigWu.png");
				}
				chat_user_avatar.appendChild(img);
				
				var ul = document.createElement('ul');
				ul.classList.add("chat_message");
				var li = document.createElement('li');
				var p = document.createElement('p');
				var span = document.createElement('span');
				span.classList.add("chat_message_time");
				var spanReadSts = document.createElement('span');
						
				var showMsg = historyData.message;
				var timestamp = historyData.timestamp;
				var readSts = historyData.readSts;
				p.innerHTML = showMsg;
				var dayTime = timestamp.split(" ")[0];
				p.setAttribute("title",dayTime);
				var shortTime =timestamp.split(" ")[1];
				shortTime = shortTime.substring(0, shortTime.length-3);
				span.innerHTML = shortTime;
				// 根據發送者是自己還是對方來給予不同的class名, 以達到訊息左右區分
				if( historyData.sender === mem_no ){
					li.className += 'mem';
					chat_message_wrapper.classList.add("chat_message_right");
					var readStsText = " 已讀";
					if(readSts==0){
						readStsText = " 未讀";
						spanReadSts.classList.add("chat_unread");
					}
					spanReadSts.innerHTML = readStsText;
					span.appendChild(spanReadSts);
				}else{
					li.className += 'emp';
				}
				p.appendChild(span);
				li.appendChild(p);
				ul.appendChild(li);
				chat_message_wrapper.appendChild(chat_user_avatar);
				chat_message_wrapper.appendChild(ul);
				chat_box.appendChild(chat_message_wrapper);
			}
			messagesArea.scrollTop = messagesArea.scrollHeight;
		} else if ("chat" === jsonObj.type) {
			console.log("收到 "+jsonObj.sender+" chat 訊息");
			
			// 若聊天室為開啟狀態
			if(document.querySelector('#sidebar_secondary').classList.contains('popup-box-on')){
				var chat_message_wrapper = document.createElement('div');
				chat_message_wrapper.classList.add("chat_message_wrapper");
				
				var chat_user_avatar = document.createElement('div');
				chat_user_avatar.classList.add("chat_user_avatar");
				
				var img = document.createElement('img');
				img.classList.add("md-user-image");
				if( jsonObj.sender === mem_no ){
					img.setAttribute("src","<%=request.getContextPath()%>/front-end/images/smallWu.jpg");
				}else{
					img.setAttribute("src","<%=request.getContextPath()%>/front-end/images/bigWu.png");
				}
				chat_user_avatar.appendChild(img);
				
				var ul = document.createElement('ul');
				ul.classList.add("chat_message");
				var li = document.createElement('li');
				var p = document.createElement('p');
				var span = document.createElement('span');
				span.classList.add("chat_message_time");
				var spanReadSts = document.createElement('span');
						
				var showMsg = jsonObj.message;
				var timestamp = jsonObj.timestamp;
				var readSts = jsonObj.readSts;
				p.innerHTML = showMsg;
				var dayTime = timestamp.split(" ")[0];
				p.setAttribute("title",dayTime);
				var shortTime =timestamp.split(" ")[1];
				shortTime = shortTime.substring(0, shortTime.length-3);
				span.innerHTML = shortTime;
				// 根據發送者是自己還是對方來給予不同的class名, 以達到訊息左右區分
				if( jsonObj.sender === mem_no ){
					li.className += 'mem';
					chat_message_wrapper.classList.add("chat_message_right");
					var readStsText = " 已讀";
					if(readSts==0){
						readStsText = " 未讀";
						spanReadSts.classList.add("chat_unread");
					}
					spanReadSts.innerHTML = readStsText;
					span.appendChild(spanReadSts);
				}else{
					li.className += 'emp';
				}
				p.appendChild(span);
				li.appendChild(p);
				ul.appendChild(li);
				chat_message_wrapper.appendChild(chat_user_avatar);
				chat_message_wrapper.appendChild(ul);
				document.getElementsByClassName("chat_box")[0].appendChild(chat_message_wrapper);
				messagesArea.scrollTop = messagesArea.scrollHeight;
				// 收到員工訊息，發送給員工已讀訊息
				if(jsonObj.sender == "emp"){
					var jsonObj = {
						"type" : "read",
						"sender" : mem_no,
						"receiver" : "emp"
					};
					webSocket.send(JSON.stringify(jsonObj));
				}
			} else { // 聊天室沒開啟，跳出新訊息通知
				callToast();
			} 
		} else if ("read" === jsonObj.type){
			console.log("收到 "+jsonObj.sender+" read 訊息");
			document.querySelectorAll('.chat_unread').forEach( (e)=>{
				e.innerText = " 已讀";
				e.classList.remove("chat_unread");
			});
		}
	};
				
	webSocket.onclose = function(event) {
		console.log("Disconnected!");
	};
	
	// 發送訊息
	var inputMsg = document.getElementById("submit_message");
	inputMsg.addEventListener("keyup", function(event) {
		if (event.keyCode === 13) {
			event.preventDefault();
			document.getElementById("sendMsg").click();
		}
	});
	$("#sendMsg").click(function() {
		var inputMessage = document.getElementById("submit_message");
		var message = inputMessage.value.trim();
	
		if (message === "") {
			alert("Input a message");
			inputMessage.focus();
		} else {
			var jsonObj = {
				"type" : "chat",
				"sender" : mem_no,
				"receiver" : "emp",
				"message" : message,
				"timestamp" : new Date().toLocaleString(),
				"readSts" : 0
			};
			webSocket.send(JSON.stringify(jsonObj));
			inputMessage.value = "";
			inputMessage.focus();
		}
	});
	
	// 開啟聊天室 display
	var memVO_js = '${memVO2}';
	$("#addClass").click(function() {
		// 讓未登入者無法使用客服聊天室
		if(memVO_js==''){
			$('#loginModal').modal('show');
		} else {
			$('#sidebar_secondary').addClass('popup-box-on');
		}
		// 抓出聊天紀錄
		var jsonObj = { // 這裡要對應原本的 VO 內容
			"type" : "history", // 等同於一個 "action" 傳進去，去取得歷史訊息
			"sender" : mem_no,
			"receiver" : "emp",
			"message" : "",
			"timestamp" : "",
			"readSts" : 0
		};
		webSocket.send(JSON.stringify(jsonObj));
	});
			
	// 關閉聊天室 
	$("#removeClass").click(function() {
		$('#sidebar_secondary').removeClass('popup-box-on');
	});
	
	<%-- 通知  webSocket --%>
	var MyPoint_Inform = "/Front_InformWS/${memVO2.mem_no}"; 
	var host_Inform = window.location.host;
	var path_Inform = window.location.pathname;
	var webCtx_Inform = path_Inform.substring(0, path_Inform.indexOf('/', 1));
	var endPointURL_Inform = "ws://" + host_Inform + webCtx_Inform + MyPoint_Inform;
	
	// var mem_no = "${memVO2.mem_no}"; // 宣告自己，上方聊天室已宣告過
	var informArea = document.getElementById("fi_cont"); // 通知 table
	
	var webSocket_Inform = new WebSocket(endPointURL_Inform);
	
	webSocket_Inform.onopen = function(event) {
		console.log("Inform Connect Success!");
	};
	
	webSocket_Inform.onmessage = function(event) {
		var originalJsonObj = JSON.parse(event.data); // 把發送來的字串資料轉成 json 物件
		
		// 若從後端傳來的 originalJsonObj 是陣列 → onOpen 才會出現這種事，不需要判斷是否重複 id
		if(Array.isArray(originalJsonObj) && originalJsonObj !== null){
			for(let i=0 ; i<originalJsonObj.length ; i++){
				let jsonObj = originalJsonObj[i];

				let btnResNo = (jsonObj.res_no!==null)? jsonObj.res_no : 0 ; // 取得 res_no
				let trFiNo = jsonObj.info_no; // 取得 info_no
				
				// 已讀訊息 → 不需要小紅點
				if ( jsonObj.read_sts === 1 ) {
					var informTr = document.createElement('tr');
					informTr.setAttribute("name","read");
					informTr.setAttribute("id",trFiNo); // 將該 tr 綁上 id
				
				// 未打開鈴鐺 table，需顯示小紅點
				} else {
					if(document.getElementById("fi_cont").style.display == 'none'){
						document.getElementsByClassName("badge")[0].style.display = "inline-block";
					}
					var informTr = document.createElement('tr');
					informTr.setAttribute("name","unread");
					informTr.setAttribute("id",trFiNo);
					informTr.style.cssText = "background-color: rgb(230, 249, 255);";
				}
				
				// 需要回應的通知未確認
				if ( jsonObj.info_sts === 2 ) {
					// 第一個 td 要放到 tr 中
					var informTdCont = document.createElement('td');
					informTdCont.style.cssText = "height: 46px; width: 300px; word-break: break-all;"; // 此 td 寬度 300px
					// 下方 span 要放到上方的 td 中
					var informTdContSpan = document.createElement('span');
					informTdContSpan.innerHTML = jsonObj.info_cont;
					// 下方的 div 要放到上方的 td 中
					var informTdDiv = document.createElement('div');
					informTdDiv.setAttribute("class","d-flex justify-content-end")
					// 下方兩個 button 要放到上方的 div 中
					var informTdContBtnYes = document.createElement('button');
					informTdContBtnYes.setAttribute("id",trFiNo+"yes");
					informTdContBtnYes.innerHTML = "確認";
					informTdContBtnYes.addEventListener('click', function(){
						confirm(trFiNo, btnResNo);
					});
					informTdContBtnYes.style.cssText = "margin-right:3px;"; // 兩個 button 間的間距
					var informTdContBtnNo = document.createElement('button');
					informTdContBtnNo.setAttribute("id",trFiNo+"no");
					informTdContBtnNo.innerHTML = "取消";
					informTdContBtnNo.addEventListener('click', function(){
						cancel(trFiNo, btnResNo);
					});
					informTdDiv.appendChild(informTdContBtnYes); // <div> 內放 button
					informTdDiv.appendChild(informTdContBtnNo); // <div> 內放 button
					informTdCont.appendChild(informTdContSpan); // td 放通知文字 <span>
					informTdCont.appendChild(document.createElement('br')); // td 放 <br>
					informTdCont.appendChild(informTdDiv); // td 再放含有兩個 button 的 <div>
					
				// 不需要回應的通知
				} else if ( jsonObj.info_sts === 0 ) {
					// td 裡面包 <a>
					var informTdC_A = document.createElement('a');
					if( jsonObj.info_cont == "訂位成功，點選查看訂位訂單" || jsonObj.info_cont == "訂位訂單修改成功，點選查看訂位訂單" || jsonObj.info_cont == "您的訂位已取消"){
						informTdC_A.setAttribute("href","<%=request.getContextPath()%>/front-end/res_order/getMemberResSeat.jsp");
					}else if( jsonObj.info_cont == "訂餐成功，點選查看訂餐訂單" || jsonObj.info_cont == "餐點已完成，請至本餐廳取餐(點選可查看訂單)" || jsonObj.info_cont == "您的訂餐已取消" ){
						informTdC_A.setAttribute("href","<%=request.getContextPath()%>/front-end/shopping/mealOrder.jsp");
					}
					// tr 中的第一個 td
					var informTdCont = document.createElement('td');
					informTdCont.style.cssText = "width: 300px; word-break: break-all;"; // 此 td 寬度 300px
					// 把 info_cont 填入 a 中
					informTdC_A.style.cssText = "word-break: break-all;";
					informTdC_A.innerHTML = jsonObj.info_cont;
					// 先在第一個 td 中放 a
					informTdCont.appendChild(informTdC_A);
					
					
				// 需要回應的通知已確認
				} else if ( jsonObj.info_sts === 1 ) {
					// 第一個 td 要放到 tr 中
					var informTdCont = document.createElement('td');
					informTdCont.style.cssText = "height: 46px; width: 300px;"; // 此 td 寬度 300px
					// 下方 span 要放到上方的 td 中
					var informTdContSpan = document.createElement('span');
					informTdContSpan.innerHTML = jsonObj.info_cont;
					// 下方的 div 要放到上方的 td 中
					var informTdDiv = document.createElement('div');
					informTdDiv.setAttribute("class","d-flex justify-content-end")
					var informTdContYesSpan = document.createElement('span');
					informTdContYesSpan.innerHTML = "已確認";
					informTdDiv.appendChild(informTdContYesSpan); // <div> 內放 checked yes
					informTdCont.appendChild(informTdContSpan); // td 放通知文字 <span>
					informTdCont.appendChild(document.createElement('br')); // td 放 <br>
					informTdCont.appendChild(informTdDiv); // td 再放含有兩個 button 的 <div>
					
				// 需要回應的通知已取消
				} else if ( jsonObj.info_sts === 3 ) {
					// 第一個 td 要放到 tr 中
					var informTdCont = document.createElement('td');
					informTdCont.style.cssText = "height: 46px; width:300px;"; // 此 td 寬度 300px
					// 下方 span 要放到上方的 td 中
					var informTdContSpan = document.createElement('span');
					informTdContSpan.innerHTML = jsonObj.info_cont;
					// 下方的 div 要放到上方的 td 中
					var informTdDiv = document.createElement('div');
					informTdDiv.setAttribute("class","d-flex justify-content-end")
					var informTdContNoSpan = document.createElement('span');
					informTdContNoSpan.innerHTML = "已取消";
					informTdDiv.appendChild(informTdContNoSpan); // <div> 內放 checked no
					informTdCont.appendChild(informTdContSpan); // td 放通知文字 <span>
					informTdCont.appendChild(document.createElement('br')); // td 放 <br>
					informTdCont.appendChild(informTdDiv); // td 再放含有兩個 button 的 <div>
				}
				
				// 第二個 td 也要放到 tr 中
				var informTdDate = document.createElement('td');
				informTdDate.style.cssText = "width:100px;"; // 此 td 寬度 100px
				var infoDate = jsonObj.info_date;
				informTdDate.innerHTML = infoDate;
				// 把兩個 td 都放進 tr 中
				informTr.appendChild(informTdCont);
				informTr.appendChild(informTdDate);
				// 這條 tr 要放進 table 裡
				informArea.appendChild(informTr);
				informArea.scrollTop = 0;
				
			}
			
			
			
		// 若從後端傳來的 originalJsonObj 不是陣列 ← onMessage 傳來的，此時才需要判斷是否有出現過相同的 id
		} else if(!Array.isArray(originalJsonObj) && originalJsonObj !== null) {
			let jsonObj = originalJsonObj;
			
			let btnResNo = (jsonObj.res_no!==null)? jsonObj.res_no : 0 ; // 取得 res_no
			let trFiNo = jsonObj.info_no; // 取得 info_no
			
			// 若有抓到東西，修改其 innerHTML
			if(document.getElementById(trFiNo) !== null) {
				let alreadyMade = document.getElementById(trFiNo);
				// 不必再 insertRow(0)，而是去取得元素並更改其 innerHTML (從 td 開始就可以了)
				alreadyMade.innerHTML = '';
				// 第一個 td 要放到 tr 中
				var informTdCont = document.createElement('td');
				informTdCont.style.cssText = "width:300px;"; // 此 td 寬度 300px
				// 下方 span 要放到上方的 td 中
				var informTdContSpan = document.createElement('span');
				informTdContSpan.innerHTML = jsonObj.info_cont;
				// 下方的 div 要放到上方的 td 中
				var informTdDiv = document.createElement('div');
				informTdDiv.setAttribute("class","d-flex justify-content-end");
				
				// info_sts 必為 1 或 3
				if ( jsonObj.info_sts === 1 ) {
					var informTdContYesSpan = document.createElement('span');
					informTdContYesSpan.innerHTML = "已確認";
					informTdDiv.appendChild(informTdContYesSpan); // <div> 內放 checked yes
					
				// 需要回應的通知已取消
				} else if ( jsonObj.info_sts === 3 ) {
					var informTdContNoSpan = document.createElement('span');
					informTdContNoSpan.innerHTML = "已取消";
					informTdDiv.appendChild(informTdContNoSpan); // <div> 內放 checked no
				}
				
				informTdCont.appendChild(informTdContSpan); // td 放通知文字 <span>
				informTdCont.appendChild(document.createElement('br')); // td 放 <br>
				informTdCont.appendChild(informTdDiv); // td 再放含有兩個 button 的 <div>
				// 第二個 td 也要放到 tr 中
				var informTdDate = document.createElement('td');
				informTdDate.style.cssText = "width:100px;"; // 此 td 寬度 100px
				var infoDate = jsonObj.info_date;
				informTdDate.innerHTML = infoDate;
				// 把兩個 td 都放進 tr 中
				alreadyMade.appendChild(informTdCont);
				alreadyMade.appendChild(informTdDate);
				informArea.scrollTop = 0;
				
				
			// 沒有抓到東西 → 直接新增一筆通知
			} else {
				// 已讀訊息 → 不需要小紅點
				if ( jsonObj.read_sts === 1 ) {
					var informTr = informArea.insertRow(0);
					informTr.setAttribute("name","read");
					informTr.setAttribute("id",trFiNo);
				
				// 未讀訊息 → 需要小紅點
				} else {
					// 未打開鈴鐺 table，需顯示小紅點
					if(document.getElementById("fi_cont").style.display == 'none'){
						document.getElementsByClassName("badge")[0].style.display = "inline-block";
					}
					var informTr = informArea.insertRow(0);
					informTr.setAttribute("name","unread");
					informTr.setAttribute("id",trFiNo);
					informTr.style.cssText = "background-color: rgb(230, 249, 255);";
				}
				
				// 需要回應的通知未確認
				if ( jsonObj.info_sts === 2 ) {
					// 第一個 td 要放到 tr 中
					var informTdCont = document.createElement('td');
					informTdCont.style.cssText = "height: 46px; width:300px; word-break: break-all;"; // 此 td 寬度 300px
					// 下方 span 要放到上方的 td 中
					var informTdContSpan = document.createElement('span');
					informTdContSpan.innerHTML = jsonObj.info_cont;
					// 下方的 div 要放到上方的 td 中
					var informTdDiv = document.createElement('div');
					informTdDiv.setAttribute("class","d-flex justify-content-end")
					// 下方兩個 button 要放到上方的 div 中
					var informTdContBtnYes = document.createElement('button');
					informTdContBtnYes.setAttribute("id",trFiNo+"yes");
					informTdContBtnYes.innerHTML = "確認";
					informTdContBtnYes.addEventListener('click', function(){
						confirm(trFiNo, btnResNo);
					});
					informTdContBtnYes.style.cssText = "margin-right:3px;"; // 兩個 button 間的間距
					var informTdContBtnNo = document.createElement('button');
					informTdContBtnNo.setAttribute("id",trFiNo+"no");
					informTdContBtnNo.innerHTML = "取消";
					informTdContBtnNo.addEventListener('click', function(){
						cancel(trFiNo, btnResNo);
					});
					informTdDiv.appendChild(informTdContBtnYes); // <div> 內放 button
					informTdDiv.appendChild(informTdContBtnNo); // <div> 內放 button
					informTdCont.appendChild(informTdContSpan); // td 放通知文字 <span>
					informTdCont.appendChild(document.createElement('br')); // td 放 <br>
					informTdCont.appendChild(informTdDiv); // td 再放含有兩個 button 的 <div>
					
				// 不需要回應的通知
				} else if ( jsonObj.info_sts === 0 ) {
					// td 裡面包 <a>
					var informTdC_A = document.createElement('a');
					if( jsonObj.info_cont == "訂位成功，點選查看訂位訂單" || jsonObj.info_cont == "訂位訂單修改成功，點選查看訂位訂單" || jsonObj.info_cont == "您的訂位已取消"){
						informTdC_A.setAttribute("href","<%=request.getContextPath()%>/front-end/res_order/getMemberResSeat.jsp");
					}else if( jsonObj.info_cont == "訂餐成功，點選查看訂餐訂單" || jsonObj.info_cont == "餐點已完成，請至本餐廳取餐(點選可查看訂單)" || jsonObj.info_cont == "您的訂餐已取消" ){ 
						informTdC_A.setAttribute("href","<%=request.getContextPath()%>/front-end/shopping/mealOrder.jsp");
					}
					// tr 中的第一個 td
					var informTdCont = document.createElement('td');
					informTdCont.style.cssText = "width: 300px; word-break: break-all;"; // 此 td 寬度 300px
					// 把 info_cont 填入 a 中
					informTdC_A.style.cssText = "word-break: break-all;";
					informTdC_A.innerHTML = jsonObj.info_cont;
					// 先在第一個 td 中放 a
					informTdCont.appendChild(informTdC_A);
					
				// 需要回應的通知已確認
				} else if ( jsonObj.info_sts === 1 ) {
					// 第一個 td 要放到 tr 中
					var informTdCont = document.createElement('td');
					informTdCont.style.cssText = "width:300px;"; // 此 td 寬度 300px
					// 下方 span 要放到上方的 td 中
					var informTdContSpan = document.createElement('span');
					informTdContSpan.innerHTML = jsonObj.info_cont;
					// 下方的 div 要放到上方的 td 中
					var informTdDiv = document.createElement('div');
					informTdDiv.setAttribute("class","d-flex justify-content-end")
					var informTdContYesSpan = document.createElement('span');
					informTdContYesSpan.innerHTML = "已確認";
					informTdDiv.appendChild(informTdContYesSpan); // <div> 內放 checked yes
					informTdCont.appendChild(informTdContSpan); // td 放通知文字 <span>
					informTdCont.appendChild(document.createElement('br')); // td 放 <br>
					informTdCont.appendChild(informTdDiv); // td 再放含有兩個 button 的 <div>
					
				// 需要回應的通知已取消
				} else if ( jsonObj.info_sts === 3 ) {
					// 第一個 td 要放到 tr 中
					var informTdCont = document.createElement('td');
					informTdCont.style.cssText = "width:300px;"; // 此 td 寬度 300px
					// 下方 span 要放到上方的 td 中
					var informTdContSpan = document.createElement('span');
					informTdContSpan.innerHTML = jsonObj.info_cont;
					// 下方的 div 要放到上方的 td 中
					var informTdDiv = document.createElement('div');
					informTdDiv.setAttribute("class","d-flex justify-content-end")
					var informTdContNoSpan = document.createElement('span');
					informTdContNoSpan.innerHTML = "已取消";
					informTdDiv.appendChild(informTdContNoSpan); // <div> 內放 checked no
					informTdCont.appendChild(informTdContSpan); // td 放通知文字 <span>
					informTdCont.appendChild(document.createElement('br')); // td 放 <br>
					informTdCont.appendChild(informTdDiv); // td 再放含有兩個 button 的 <div>
				}
				
				// 第二個 td 也要放到 tr 中
				var informTdDate = document.createElement('td');
				informTdDate.style.cssText = "width:100px;"; // 此 td 寬度 100px
				var infoDate = jsonObj.info_date;
				informTdDate.innerHTML = infoDate;
				// 把兩個 td 都放進 tr 中
				informTr.appendChild(informTdCont);
				informTr.appendChild(informTdDate);
				informArea.scrollTop = 0;
				
			}
			
		}
		
	};
				
	webSocket_Inform.onclose = function(event) {
		console.log("Inform Disconnected!");
	};
	
</script>
<script src="<%=request.getContextPath()%>/front-end/js/jquery.min.js"></script>
<script
	src="<%=request.getContextPath()%>/front-end/js/bootstrap.min.js"></script>
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
	if(document.getElementsByName("unread").length > 0){
		document.getElementsByClassName("badge")[0].style.display = "inline-block";
	}else{
		document.getElementsByClassName("badge")[0].style.display = 'none';
	}
	function popFrontInform(){
		let fi_cont = document.getElementById("fi_cont");
	
		// 已讀未讀顯示顏色
		let readColor = document.querySelectorAll('[name="read"]');
		for (let i = 0; i < readColor.length; i++) {
			readColor[i].style.backgroundColor = "#fff";
		}
		let unreadColor = document.querySelectorAll('[name="unread"]');
		for (let i = 0; i < unreadColor.length; i++) {
			unreadColor[i].style.backgroundColor = "#e6f9ff";
			unreadColor[i].setAttribute('name', 'read');
		}
	
		// 紅點已讀消失
		document.getElementsByClassName("badge")[0].style.display = "none";
	
		// 已讀未讀狀態修改
		$.ajax({
			url:'<%=request.getContextPath()%>/front_inform/fi.do',
			method:"POST",
			dataType:"text",
			data:{
				action: 'updateReadSts',
				mem_no: mem_no,
			},
			success:function(res){
				if(res == "error"){
					fi_cont.style.display = "none";
					$('#loginModal').modal('show');
				} else {
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
				}
				console.log("popFrontInform ajax回應");
			 },
			 error:function(err){
				console.log(err);
				fi_cont.style.display = "none";
				$('#loginModal').modal('show');
			 },	
		});
			
	};
	
	//  須回覆的通知被按下確認鍵
	function confirm(info_no, res_no){
		$.ajax({
			 url:'<%=request.getContextPath()%>/front_inform/fi.do',
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
	};
	
	// 須回覆的通知被按下取消鍵
	function cancel(info_no, res_no){
		$.ajax({
			 url:'<%=request.getContextPath()%>/front_inform/fi.do',
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
	};
</script>

<script>
	<!--判斷現在是登入或登出的狀態 -->
	var sign = document.getElementById("sign");
	var login = document.getElementById("login");
	var mem_name = document.getElementById("mem_name");
	var hello = document.getElementById("hello");
	var logout = document.getElementById("logout");

	if (mem_name.innerText !== "") {
		sign.classList.add('unshow');
		login.classList.add('unshow');
		mem_name.classList.remove('unshow');
		hello.classList.remove('unshow');
		logout.classList.remove('unshow');
	} else {
		sign.classList.remove('unshow');
		login.classList.remove('unshow');
		mem_name.classList.add('unshow');
		hello.classList.add('unshow');
		logout.classList.add('unshow');
	}
	
	<!--判斷現在是否在會員中心頁面 -->
	var member = document.getElementById("member");
	var title = document.getElementById("title");
	
	var path = location.pathname;
	var path_f = '/EA103G7/front-end/mem';
	if (path === path_f + '/login_mem.jsp' || 
		path === path_f + '/login_success_mem.jsp' || 
		path === path_f + '/addMem.jsp' || 
		path === path_f + '/forgetPsw.jsp' || 
		path === path_f + '/showMemInfo.jsp' || 
		path === path_f + '/update_mem_info.jsp' || 
		path === path_f + '/mem.do') {
		title.innerHTML = '會員中心';
	}
	
	<!--判斷會員是否有權限 -->
	var mem_od_r = `${memVO2.mem_od_r}`;
	var mem_od_m = `${memVO2.mem_od_m}`;
	var mem_review = `${memVO2.mem_review}`;
	var mem_repo = `${memVO2.mem_repo}`;
	var mem_no = `${memVO2.mem_no}`;
	
	var res = document.getElementById("res");
	var meal = document.getElementById("meal");
// 	var review = document.getElementById("review");
// 	var repo = document.getElementById("repo");
	
	if (mem_no !== '' && mem_od_r == 0) {
		res.addEventListener("click", function() {
			alert("Sorry！您沒有訂位權限！" + "\n" + "有任何疑問請洽客服。");
		});
	}
	
	if (mem_no !== '' && mem_od_m == 0) {
		meal.addEventListener("click", function() {
			alert("Sorry！您沒有訂餐權限！" + "\n" + "有任何疑問請洽客服。");
		});
	}
// 	if (mem_no !== '' && mem_review == 0) {
// 		review.addEventListener("click", function() {
// 			alert("Sorry！您沒有評價權限！" + "\n" + "有任何疑問請洽客服。");
// 		});
// 	}
// 	if (mem_no !== '' && mem_repo == 0) {
// 		repo.addEventListener("click", function() {
// 			alert("Sorry！您沒有檢舉權限！" + "\n" + "有任何疑問請洽客服。");
// 		});
// 	}
	
	if (mem_no === '') {
		res.addEventListener("click", function() {
			alert("請先登入會員喔！");
		});
		meal.addEventListener("click", function() {
			alert("請先登入會員喔！");
		});
// 		review.addEventListener("click", function() {
// 			alert("請先登入會員喔！");
// 		});
// 		repo.addEventListener("click", function() {
// 			alert("請先登入會員喔！");
// 		});
	}
</script>
<script>
var ad=$(".ad").get(0);
ad.classList.add("active");
var ada=$(".ada").get(0);
ada.classList.add("active");
</script>

<script
	src="<%=request.getContextPath()%>/front-end/js/jquery-migrate-3.0.1.min.js"></script>
<script src="<%=request.getContextPath()%>/front-end/js/popper.min.js"></script>
<script
	src="<%=request.getContextPath()%>/front-end/js/jquery.easing.1.3.js"></script>
<script
	src="<%=request.getContextPath()%>/front-end/js/jquery.waypoints.min.js"></script>
<script
	src="<%=request.getContextPath()%>/front-end/js/jquery.stellar.min.js"></script>
<script
	src="<%=request.getContextPath()%>/front-end/js/owl.carousel.min.js"></script>
<script
	src="<%=request.getContextPath()%>/front-end/js/jquery.magnific-popup.min.js"></script>
<script src="<%=request.getContextPath()%>/front-end/js/aos.js"></script>
<script
	src="<%=request.getContextPath()%>/front-end/js/jquery.animateNumber.min.js"></script>
<script
	src="<%=request.getContextPath()%>/front-end/js/bootstrap-datepicker.js"></script>
<script
	src="<%=request.getContextPath()%>/front-end/js/jquery.timepicker.min.js"></script>
<script src="<%=request.getContextPath()%>/front-end/js/scrollax.min.js"></script>
<%-- <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBVWaKrjvy3MaE7SQ74_uJiULgl1JY0H2s&sensor=false"></script> --%>
<%-- <script src="<%=request.getContextPath()%>/front-end/js/google-map.js"></script> --%>
<script src="<%=request.getContextPath()%>/front-end/js/main.js"></script>

<!--為了顯示地址選單用 -->
<script
	src="<%=request.getContextPath()%>/front-end/js/jquery.twzipcode.min.js"></script>
<%-- script 結束 --%>
</html>