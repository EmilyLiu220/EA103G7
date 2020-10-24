<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ad.model.*"%>
<%
	AdVO adVO = (AdVO) request.getAttribute("adVO"); //AdServlet.java(Concroller), 存入req的adVO物件
%>


<head>
    <title>Feliciano - Free Bootstrap 4 Template by Colorlib</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Great+Vibes&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/open-iconic-bootstrap.min.css">
    <link rel="stylesheet" href="css/animate.css">
    <link rel="stylesheet" href="css/owl.carousel.min.css">
    <link rel="stylesheet" href="css/owl.theme.default.min.css">
    <link rel="stylesheet" href="css/magnific-popup.css">
    <link rel="stylesheet" href="css/aos.css">
    <link rel="stylesheet" href="css/ionicons.min.css">
    <link rel="stylesheet" href="css/bootstrap-datepicker.css">
    <link rel="stylesheet" href="css/jquery.timepicker.css">
    <link rel="stylesheet" href="css/flaticon.css">
    <link rel="stylesheet" href="css/icomoon.css">
    <link rel="stylesheet" href="css/style.css">
</head>

<%@ include file="head.jsp"%>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

 <nav class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light" id="ftco-navbar">
        <div class="container">
            <a class="navbar-brand" href="<%=request.getContextPath()%>/front-end/front/index.jsp">PoT</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#ftco-nav" aria-controls="ftco-nav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="oi oi-menu"></span> Menu
            </button>
            <div class="collapse navbar-collapse" id="ftco-nav">
                <ul class="navbar-nav ml-auto">
                   <li class="nav-item"><a href="<%=request.getContextPath()%>/front-end/front/index.jsp" class="nav-link">Home</a></li>
					<li class="nav-item"><a href="" class="nav-link">Menu</a></li>
<%-- 					<li class="nav-item"><a href="<%=request.getContextPath()%>/front-end/front/front_news-11.jsp" class="nav-link">News</a></li> --%>
					<li class="nav-item"><a href="<%=request.getContextPath()%>/front-end/front/front_ad2.jsp" class="nav-link">最新活動</a></li>
					<li class="nav-item"><a href="" class="nav-link">評價總覽</a></li>
					<li class="nav-item"><a href="" class="nav-link">餐廳資訊</a></li>
					<li class="nav-item"><a href="" class="nav-link">候位狀況</a></li>
					<li class="nav-item"><a href="" class="nav-link">我要訂餐</a></li>
					<li class="nav-item"><a href="" class="nav-link">我要定位</a></li>
                </ul>
            </div>
        </div>
    </nav>




</head>
<body>
	<%@ include file="footer.jsp"%>
</body>


<!-- loader -->
<!-- <div id="ftco-loader" class="show fullscreen"> -->
<!-- 	<svg class="circular" width="48px" height="48px"> -->
<%--                 <circle class="path-bg" cx="24" cy="24" r="22" --%>
<%-- 			fill="none" stroke-width="4" stroke="#eeeeee" /> --%>
<%--                 <circle class="path" cx="24" cy="24" r="22" fill="none" --%>
<%-- 			stroke-width="4" stroke-miterlimit="10" stroke="#F96D00" /></svg> --%>
<!-- </div> -->
      <script src="js/jquery.min.js"></script>
        <script src="js/jquery-migrate-3.0.1.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery.easing.1.3.js"></script>
        <script src="js/jquery.waypoints.min.js"></script>
        <script src="js/jquery.stellar.min.js"></script>
        <script src="js/owl.carousel.min.js"></script>
        <script src="js/jquery.magnific-popup.min.js"></script>
        <script src="js/aos.js"></script>
        <script src="js/jquery.animateNumber.min.js"></script>
        <script src="js/bootstrap-datepicker.js"></script>
        <script src="js/jquery.timepicker.min.js"></script>
        <script src="js/scrollax.min.js"></script>
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBVWaKrjvy3MaE7SQ74_uJiULgl1JY0H2s&sensor=false"></script>
        <script src="js/google-map.js"></script>
        <script src="js/main.js"></script>
   


</html>