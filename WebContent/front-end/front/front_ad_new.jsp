<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.ad.model.*"%>

<%
	AdService adSvc = new AdService(); 
     List<AdVO> list = adSvc.find_adsts(1); 
     request.setAttribute("list",list);
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
#ad_title{
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

</style>
<%@ include  file="/front-end/headfinish.jsp"%>
<div class="container">
	<div class="hd" style="margin-bottom: -20px;">
		<a href="<%=request.getContextPath()%>/front-end/front_home.jsp">首頁</a> <span>最新消息</span>
	</div>
	<div class="font-weight-light text-center text-lg-left mt-4 mb-0">
		<a id="classLink" class="classLink" href="<%=request.getContextPath()%>/front-end/front/front_ad_new.jsp"
			style="border-top: 1px solid #cacaca; border-bottom: 1px solid #cacaca;">所有新訊
		</a>
	</div>

	<hr class="mt-2 mb-5" style="border-top:none">

	<div class="row text-center text-lg-left" >
		<%@ include file="page1-1.file"%>


		<c:forEach var="adVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
<%-- 			<label for="${adVO.ad_no}"> --%>
				<div class="col-lg-3 col-md-4 col-6">
					<label for="${adVO.ad_no}" class="d-block mb-4 h-100"> <img
						class="img-fluid img-thumbnail"
						src="<%=request.getContextPath() %>/ad/ad.do?add_no=${adVO.ad_no}"
						alt="">
					</label>
				</div>
<!-- 			</label> -->
			<div class="col-lg-3 col-md-4 col-6" >
				<div>${adVO.ad_add_date}</div>
				
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/ad/ad.do">
					<label for="${adVO.ad_no}" class="d-block mb-4 h-100 " style="font-size: 20px" 
					id="ad_title">${adVO.ad_title}<br>
<!-- 					*************************************************-->
<!-- 					<label for="ad_one" class="d-block mb-4 h-100 " style="font-size: 20px" 
					id="ad_title">${adVO.ad_title}<br>
<input type="submit" id="ad_one"> -->
<!-- 						<input type="hidden" name="action" value="getFrontOne_For_Display"> -->
<!-- *************************************************** -->

						<button type="submit" id="${adVO.ad_no}" class="btn btn-outline-dark"
							style="border-radius: 100%" name="action"
							value="getFrontOne_For_Display">詳</button>
					</label>
					<input type="hidden" name="ad_no" value="${adVO.ad_no}">
					</FORM>
			</div>
			
			
		</c:forEach>
	
	</div>
	<%@ include file="page2.file"%>	
</div>

<!-- /.container -->
<%@ include file="/front-end/footer.jsp"%>

</html>