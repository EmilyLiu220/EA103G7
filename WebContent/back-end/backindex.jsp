<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>後台首頁</title>

<style>
	#loc {
		font-size: 150%;
	}
	#container{
	  	margin:0 auto;
	  	width: 600px;
	}
</style>

</head>
<body>

	<div class="wrapper">

		<!-- Page Content  -->
		<div id="content">

			<nav class="navbar navbar-expand-lg navbar-light bg-light">
				<div class="container-fluid">

					<div id="titleBig"
						style="margin: 0 auto; font-size: 30px; font-weight: 800; cursor: default;">吃
						Pot 吧！員工專區</div>
					<div id="rwdShow">
						
					</div>
				</div>
			</nav>
			
		<div id="container">
			<div id="loc" style="margin-left:200px">
			<h3>&emsp;</h3>
			<h3 style="font-weight: 900; display: inline-block;">吃 Pot 吧 ! 員工守則</h3>
			<ol>
				<li style="margin-top: 15px;">須守時盡責</li>
				<li style="margin-top: 15px;">遵顧客至上</li>
				<li style="margin-top: 15px;">常微笑待人</li>
				<li style="margin-top: 15px;">要不辭勞苦</li>
				<li style="margin-top: 15px;">懂臨機應變</li>
				<li style="margin-top: 15px; margin-bottom: 25px;">熟練鐵沙掌</li>
			</ol>
			<div class="line"></div>
			<h3 style="font-weight: 900; display: inline-block;">當日領班</h3>
			<div class="leader" style="cursor: default;">
				<h4>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>EMP0005</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>楊介安</span></h4>
			</div>
			</div>
		</div>
		</div>
	</div>

	<jsp:include page="/back-end/siderbar/siderbar.jsp" />

</body>
</html>