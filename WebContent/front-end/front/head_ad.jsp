<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.news.model.*"%>



<!DOCTYPE html>
<html lang="en">

<head>
<link
	href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Great+Vibes&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/front/css/open-iconic-bootstrap.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/front/css/animate.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/front/css/owl.carousel.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/front/css/owl.theme.default.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/front/css/magnific-popup.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/front/css/aos.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/front/css/ionicons.min.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/front/css/bootstrap-datepicker.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/front/css/jquery.timepicker.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/front/css/flaticon.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/front/css/icomoon.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/front-end/front/css/style.css">

</head>
<style>
.hero-wrap .overlay {
	position: absolute;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	content: '';
	opacity: 0;
	background: #000000;
}
</style>
<body
	style="background-image: url('<%=request.getContextPath()%>/front-end/front/images/pageBg.jpg');">

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
								<span class="mybb"> <a href=""><svg width="1em"
											height="1em" viewBox="0 0 16 16" class="bi bi-bell-fill"
											fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                            <path
												d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2zm.995-14.901a1 1 0 1 0-1.99 0A5.002 5.002 0 0 0 3 6c0 1.098-.5 6-2 7h14c-1.5-1-2-5.902-2-7 0-2.42-1.72-4.44-4.005-4.901z"></path></svg></a></span>
								<span class="mybb"><a href="menu.html" class="myaa">會員中心</a></span>
								<span class="mybb"><a href="blog.html" class="myaa">註冊</a></span>
								<span class="mybb"><a href="contact.html" class="myaa">登入</a></span>
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!--     <div class="py-1 bg-black top"> -->
	<!-- 		<div class="container"> -->
	<!-- 			<div -->
	<!-- 				class="row no-gutters d-flex align-items-start align-items-center px-md-0"> -->
	<!-- 				<div class="col-lg-12 d-block"> -->
	<!-- 					<div class="row d-flex"> -->
	<!-- 						<div class="col-md pr-4 d-flex topper align-items-center"> -->
	<!-- 							<div -->
	<!-- 								class="icon mr-2 d-flex justify-content-center align-items-center"> -->
	<!-- 								<span class=""></span> -->
	<!-- 							</div> -->
	<!-- 							<span class="text"></span> -->
	<!-- 						</div> -->
	<!-- 						<div class="col-md pr-4 d-flex topper align-items-center"></div> -->
	<!-- 						<div class=""> -->
	<!-- 							<p class="mb-0 register-link"> -->
	<!-- 								<span>Open hours:</span> <span>Monday - Sunday</span><span>8:00AM -->
	<!-- 									- 9:00PM</span> -->
	<!-- 							</p> -->
	<!-- 						</div> -->
	<!-- 					</div> -->
	<!-- 				</div> -->
	<!-- 			</div> -->
	<!-- 		</div> -->
	<!-- 	</div> -->
	<nav
		class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light"
		id="ftco-navbar">
		<div class="container">
			<a class="navbar-brand"
				href="<%=request.getContextPath()%>/front-end/front/index.jsp">PoT</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#ftco-nav" aria-controls="ftco-nav"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="oi oi-menu"></span> Menu
			</button>
			<div class="collapse navbar-collapse" id="ftco-nav">
				<ul class="navbar-nav ml-auto">
					<li class="nav-item"><a
						href="<%=request.getContextPath()%>/front-end/front/index.jsp"
						class="nav-link">Home</a></li>
					<li class="nav-item"><a href="" class="nav-link">Menu</a></li>
					<li class="nav-item"><a
						href="<%=request.getContextPath()%>/front-end/front/front_news-11.jsp"
						class="nav-link">最新消息</a></li>
					<%-- 					<li class="nav-item"><a href="<%=request.getContextPath()%>/front-end/front/front_ad2.jsp" class="nav-link">最新活動</a></li> --%>
					<li class="nav-item"><a href="" class="nav-link">評價總覽</a></li>
					<li class="nav-item"><a href="" class="nav-link">餐廳資訊</a></li>
					<li class="nav-item"><a href="" class="nav-link">候位狀況</a></li>
					<li class="nav-item"><a href="" class="nav-link">我要訂餐</a></li>
					<li class="nav-item"><a href="" class="nav-link">我要定位</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<!-- END nav -->
	<section class="hero-wrap hero-wrap-2"
		style="background-image: url('<%=request.getContextPath()%>/front-end/front/images/33333.jpg');"
		data-stellar-background-ratio="0.5">
		<div class="overlay"></div>
		<div class="container">
			<div
				class="row no-gutters slider-text align-items-end justify-content-center">
				<div class="col-md-9 ftco-animate text-center mb-4">
					<h1 class="mb-2 bread">最新活動</h1>
					<p class="breadcrumbs">
						<span class="mr-2"><a
							href="<%=request.getContextPath()%>/front-end/front/index.jsp">Home
								<i class="ion-ios-arrow-forward"></i>
						</a></span>
				</div>
			</div>
		</div>
	</section>
	<!-- loader -->
	<div id="ftco-loader" class="show fullscreen">
		<svg class="circular" width="48px" height="48px">
                <circle class="path-bg" cx="24" cy="24" r="22"
				fill="none" stroke-width="4" stroke="#eeeeee" />
                <circle class="path" cx="24" cy="24" r="22" fill="none"
				stroke-width="4" stroke-miterlimit="10" stroke="#F96D00" /></svg>
	</div>
	<script
		src="<%=request.getContextPath()%>/front-end/front/js/jquery.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/front-end/front/js/jquery-migrate-3.0.1.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/front-end/front/js/popper.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/front-end/front/js/bootstrap.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/front-end/front/js/jquery.easing.1.3.js"></script>
	<script
		src="<%=request.getContextPath()%>/front-end/front/js/jquery.waypoints.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/front-end/front/js/jquery.stellar.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/front-end/front/js/owl.carousel.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/front-end/front/js/jquery.magnific-popup.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/front/js/aos.js"></script>
	<script
		src="<%=request.getContextPath()%>/front-end/front/js/jquery.animateNumber.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/front-end/front/js/bootstrap-datepicker.js"></script>
	<script
		src="<%=request.getContextPath()%>/front-end/front/js/jquery.timepicker.min.js"></script>
	<script
		src="<%=request.getContextPath()%>/front-end/front/js/scrollax.min.js"></script>
	<script
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBVWaKrjvy3MaE7SQ74_uJiULgl1JY0H2s&sensor=false"></script>
	<script
		src="<%=request.getContextPath()%>/front-end/front/js/google-map.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/front/js/main.js"></script>
</body>

</html>