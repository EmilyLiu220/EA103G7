<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.news.model.*"%>

<%
	NewsService newsSvc = new NewsService();
	List<NewsVO> list = newsSvc.frontNews_sts(1);
	request.setAttribute("list", list);
%>

<!DOCTYPE html>
<html lang="en">

<%@ include file="/front-end/headfinish.jsp"%>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<title>SB Admin 2 - Tables</title>
<!-- Custom fonts for this template -->
<link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet"
	type="text/css">
<link
	href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">
<!-- Custom styles for this template -->
<link href="css/sb-admin-2.min.css" rel="stylesheet">
<!-- Custom styles for this page -->
<link href="vendor/datatables/dataTables.bootstrap4.min.css"
	rel="stylesheet">
</head>

<style>
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

#ad_title {
	color: #3e2605
}

#classLink {
	display: block;
	list-style-type: disc;
	margin-block-start: 1em;
	margin-block-end: 1em;
	margin-inline-start: 0px;
	margin-inline-end: 0px;
	padding-inline-start: 40px;
	color: #3e2605
}

div {
	width: 100%;
	margin: 0px auto;
}

pageMainArea {
	position: relative;
	z-index: 1;
	padding-top: 100px;
}

.data {
	text-align: left;
}

#date {
	width: 120px;
	text-align: center;
}
</style>

<body id="page-top">
	<!-- Page Wrapper -->
	<div id="wrapper">
		<div id="content-wrapper" class="d-flex flex-column">
			<div class="pageMainArea">
				<div id="content" style="height: 900px;">
					<div class="container-fluid">
						<div class="hd" style="margin-bottom: -20px;">
							<a href="<%=request.getContextPath()%>/front-end/front_home.jsp">首頁</a> <span>最新消息</span>
						</div>
						<div class="font-weight-light text-center text-lg-left mt-4 mb-0">
							<a id="classLink" class="classLink"
								href="<%=request.getContextPath()%>/front-end/front/front_news-11.jsp"
								style="border-top: 1px solid #cacaca; border-bottom: 1px solid #cacaca;">所有店訊
							</a>
						</div>
						<div class="card shadow mb-4">
							<div class="card-header py-3"></div>
							<div class="card-body">
								<div class="table-responsive">
									<div id="dataTable_wrapper"
										class="dataTables_wrapper dt-bootstrap4">
										<div class="col-sm-12">
											<table class="table table-bordered dataTable" id="dataTable"
												role="grid" aria-describedby="dataTable_info"
												style="width: 100%;">
												<%@ include file="page1.file"%>
												<tbody>
													<c:forEach var="newsVO" items="${list}"
														begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-	1%>">
														<tr>
															<td class="data">${newsVO.news_cont}</td>
															<td class="data" id="date"
																style="padding-left: 0px; padding-right: 0px">${newsVO.news_date}</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
											<%@ include file="page2.file"%>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<a class="scroll-to-top rounded" href="#page-top"
		style="display: inline;"> <i class="fas fa-angle-up"></i>
	</a>
	<!-- Logout Modal-->
	<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Ready to Leave?</h5>
					<button class="close" type="button" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">×</span>
					</button>
				</div>
				<div class="modal-body">Select "Logout" below if you are ready
					to end your current session.</div>
				<div class="modal-footer">
					<button class="btn btn-secondary" type="button"
						data-dismiss="modal">Cancel</button>
					<a class="btn btn-primary" href="login.html">Logout</a>
				</div>
			</div>
		</div>
	</div>
	<!-- Bootstrap core JavaScript-->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
	<!-- Core plugin JavaScript-->
	<script src="vendor/jquery-easing/jquery.easing.min.js"></script>
	<!-- Custom scripts for all pages-->
	<script src="js/sb-admin-2.min.js"></script>
	<!-- Page level plugins -->
	<script src="vendor/datatables/jquery.dataTables.min.js"></script>
	<script src="vendor/datatables/dataTables.bootstrap4.min.js"></script>
	<!-- Page level custom scripts -->
	<script src="js/demo/datatables-demo.js"></script>
</body>


<%@ include file="footer.jsp"%>
</html>