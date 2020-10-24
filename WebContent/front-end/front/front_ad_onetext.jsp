<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ad.model.*"%>

<%
	AdVO adVO = (AdVO) request.getAttribute("adVO"); //AdServlet.java(Concroller), 存入req的adVO物件
%>
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<!-- Custom fonts for this template -->
<link href="<%=request.getContextPath() %>/front-end/front/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">
<!-- Custom styles for this template -->
<link href="<%=request.getContextPath() %>/front-end/front/css/sb-admin-2.min.css" rel="stylesheet">
<!-- Custom styles for this page -->
<link href="<%=request.getContextPath() %>/front-end/front/vendor/datatables/dataTables.bootstrap4.min.css"
	rel="stylesheet">
	
	
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Great+Vibes&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/front-end/front/css/open-iconic-bootstrap.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/front-end/front/css/animate.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/front-end/front/css/owl.carousel.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/front-end/front/css/owl.theme.default.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/front-end/front/css/magnific-popup.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/front-end/front/css/aos.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/front-end/front/css/ionicons.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/front-end/front/css/bootstrap-datepicker.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/front-end/front/css/jquery.timepicker.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/front-end/front/css/flaticon.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/front-end/front/css/icomoon.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/front-end/front/css/style.css">

	
</head>
<style>
body {
	font-family: "Poppins", Arial, sans-serif;
	background: #e0d8ce;
	font-size: 17px;
	line-height: 2;
	font-weight: 700;
	color: #1f1e1e;
}

element.style {
	width: 1250px;
}

.container-fluid {
	width: 70%;
	padding-right: 15px;
	padding-left: 15px;
	margin-right: auto;
	margin-left: auto;
}

a {
	-webkit-transition: .3s all ease;
	-o-transition: .3s all ease;
	transition: .3s all ease;
	color: #3e2605;
	margin-left: 20px;
}

table#table-1 {
	border: 2px solid black;
	text-align: center;
}

table#table-1 h4 {
	display: block;
	margin-bottom: 1px;
}

h4 {
	color: blue;
	display: inline;
}

table {
	width: 1000px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
}

table, th, td {
	border: 1px solid #CCCCFF;
}

th, td {
	padding: 5px;
	text-align: center;
}

.card.shadow.mb-4 {
	top: 30px;
	bottom: 30px;
}

/* .ul { */
/* 	border-top: 1px solid #cacaca; */
/* 	border-bottom: 1px solid #cacaca; */
/* } */
hr {
	border-top: 0px solid rgba(0, 0, 0, 0.1);
}

hd {
	margin-bottom: 40px;
}

.hd a, .hd span {
	display: inline-block;
	font-size: 14px;
	letter-spacing: .5px;
	color: #767676;
	text-transform: uppercase;
	height: 10px;
	border-top-width: 50px;
	margin-top: 80px;
	margin-bottom: -30px;
}

container {
	height: 100%;
	width: 100%;
}
</style>
<%@ include file="head_adtext.jsp"%>
<div class="container">
	<div class="hd" style="margin-bottom: -30px;">
		<a href="">首頁</a> <span>最新消息</span>
	</div>
	<div class="font-weight-light text-center text-lg-left mt-4 mb-0">
		<ul class="classLink"
			style="border-top: 1px solid #cacaca; border-bottom: 1px solid #cacaca;">cccc
		</ul>
	</div>

	<hr class="mt-2 mb-5">

	<div class="row text-center text-lg-left">

		<table>
			<tr>
				<td style="width: 100px;">${adVO.ad_title}</td>
				<td style="width: 800px;">${adVO.ad_cont}</td>
				<td style="width: 100px;">${adVO.ad_add_date}</td>
				<td style="width: 100px;">${adVO.ad_re_date}</td>
				<td><img src="<%=request.getContextPath() %>/ad/ad.do?add_no=${adVO.ad_no}"></td>
			</tr>
		</table>
	</div>
</div>
<%@ include file="footer.jsp"%>

<!-- loader -->
        <div id="ftco-loader" class="show fullscreen"><svg class="circular" width="48px" height="48px">
                <circle class="path-bg" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke="#eeeeee" />
                <circle class="path" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke-miterlimit="10" stroke="#F96D00" /></svg></div>
        <script src="<%=request.getContextPath() %>/front-end/front/js/jquery.min.js"></script>
        <script src="<%=request.getContextPath() %>/front-end/front/js/jquery-migrate-3.0.1.min.js"></script>
        <script src="<%=request.getContextPath() %>/front-end/front/js/popper.min.js"></script>
        <script src="<%=request.getContextPath() %>/front-end/front/js/bootstrap.min.js"></script>
        <script src="<%=request.getContextPath() %>/front-end/front/js/jquery.easing.1.3.js"></script>
        <script src="<%=request.getContextPath() %>/front-end/front/js/jquery.waypoints.min.js"></script>
        <script src="<%=request.getContextPath() %>/front-end/front/js/jquery.stellar.min.js"></script>
        <script src="<%=request.getContextPath() %>/front-end/front/js/owl.carousel.min.js"></script>
        <script src="<%=request.getContextPath() %>/front-end/front/js/jquery.magnific-popup.min.js"></script>
        <script src="<%=request.getContextPath() %>/front-end/front/js/aos.js"></script>
        <script src="<%=request.getContextPath() %>/front-end/front/js/jquery.animateNumber.min.js"></script>
        <script src="<%=request.getContextPath() %>/front-end/front/js/bootstrap-datepicker.js"></script>
        <script src="<%=request.getContextPath() %>/front-end/front/js/jquery.timepicker.min.js"></script>
        <script src="<%=request.getContextPath() %>/front-end/front/js/scrollax.min.js"></script>
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBVWaKrjvy3MaE7SQ74_uJiULgl1JY0H2s&sensor=false"></script>
        <script src="<%=request.getContextPath() %>/front-end/front/js/google-map.js"></script>
        <script src="<%=request.getContextPath() %>/front-end/front/js/main.js"></script>

</html>
