<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.news.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	NewsVO newsVO = (NewsVO) request.getAttribute("newsVO");
%>


<html>
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>員工查看通知</title>

<!-- Bootstrap CSS CDN -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css"
	integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<!-- Our Custom CSS -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/back-end/css/style2.css">
<!-- Scrollbar Custom CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.min.css">
<!-- Font Awesome JS -->
<script defer
	src="https://use.fontawesome.com/releases/v5.0.13/js/solid.js"
	integrity="sha384-tzzSw1/Vo+0N5UhStP3bvwWPq+uvzCMfrN1fEFe+xBmv1C/AtVX5K0uZtmcHitFZ"
	crossorigin="anonymous"></script>
<script defer
	src="https://use.fontawesome.com/releases/v5.0.13/js/fontawesome.js"
	integrity="sha384-6OIrr52G08NpOFSZdxxz1xdNSndlD4vdcf/q2myIUVO0VsqaGHJsB0RaBE01VTOY"
	crossorigin="anonymous"></script>


<style>
#table-1, #table-1 td {
	background: #7386D5;
	color: #fff;
	border: 0;
	width: 100%;
	height: 70;
	border-radius: 5px;
	text-align: center;
	box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);
}
</style>

</head>
<body>

	<div class="wrapper">

		<!-- Sidebar  -->
		<nav id="sidebar">
			<div class="sidebar-header" style="cursor: default;">
				<h3>
					<span>EMP0009</span><br>詹詠祺，您好！
				</h3>
				<!-- 員工編號 ${empVO.emp_no}  員工姓名 ${empVO.emp_name} -->
			</div>

			<ul class="list-unstyled components">
				<li><a href="#">現場點餐</a></li>
				<li><a href="#">現場劃位</a></li>
				<li><a href="#">訂單結帳</a></li>
				<li><a href="#">候位管理</a></li>
				<li class="active"><a href="#pageSubmenu"
					data-toggle="collapse" aria-expanded="false"
					class="dropdown-toggle">主管員工專區</a>
					<ul class="collapse list-unstyled" id="pageSubmenu">
						<li><a href="#">員工管理</a></li>
						<li><a href="#">會員管理</a></li>
						<li><a
							href="<%=request.getContextPath()%>/back-end/ad/select_ad.jsp">廣告管理</a></li>
						<li><a
							href="<%=request.getContextPath()%>/back-end/news/select_news.jsp">最新消息管理</a></li>
						<li><a
							href="<%=request.getContextPath()%>/back-end/inform_set/select_is.jsp">通知設定管理</a></li>
						<li><a href="#">評價管理</a></li>
						<li><a href="#">用餐時段管理</a></li>
						<li><a href="#">桌位管理</a></li>
						<li><a href="#">菜單管理</a></li>
						<li><a href="#">食材管理</a></li>
						<li><a href="#">餐點組成管理</a></li>
						<li><a href="#">食材消耗統計</a></li>
						<li><a href="#">紅利商品管理</a></li>
					</ul></li>
				<li><a href="#homeSubmenu" data-toggle="collapse"
					aria-expanded="false" class="dropdown-toggle">一般員工專區</a>
					<ul class="collapse list-unstyled" id="homeSubmenu">
						<li><a
							href="<%=request.getContextPath()%>/back-end/front_inform/empCheckInform_New.jsp">查看通知</a></li>
						<li><a href="#">訂單派工</a></li>
						<li><a href="#">出餐管理</a></li>
						<li><a href="#">訂餐訂單處理</a></li>
						<li><a href="#">訂餐管理</a></li>
						<li><a href="#">訂單管理</a></li>
						<li><a href="#">訂位管理</a></li>
					</ul></li>
			</ul>

			<ul class="list-unstyled CTAs">
				<li><a href="#" id="logIn">Log in</a></li>
				<li><a href="#" id="logOut">Log out</a></li>
			</ul>
		</nav>

		<!-- Page Content  -->
		<div id="content">

			<nav class="navbar navbar-expand-lg navbar-light bg-light">
				<div class="container-fluid">

					<button type="button" id="sidebarCollapse" class="btn btn-info">
						<i class="fas fa-align-justify"></i>
					</button>
					<div id="titleBig"
						style="margin: 0 auto; font-size: 30px; font-weight: 800;">
						<a
							href="<%=request.getContextPath()%>/back-end/back-index_New.jsp">吃
							Pot 吧！員工專區</a>
					</div>
					<div id="rwdShow">
						<button type="button" id="topbarCollapse" class="btn btn-info"
							data-toggle="collapse" data-target="#navbarSupportedContent"
							aria-controls="navbarSupportedContent" aria-expanded="false"
							aria-label="Toggle navigation">
							<i class="fas fa-align-justify"></i>
						</button>
						<div id="titleSmall"
							style="padding-left: 10px; font-size: 30px; font-weight: 800;">
							<a
								href="<%=request.getContextPath()%>/back-end/back-index_New.jsp">吃
								Pot 吧！主管員工專區</a>
						</div>
						<div class="collapse navbar-collapse" id="navbarSupportedContent">
							<ul class="nav navbar-nav ml-auto">
								<!-- 員工編號 ${empVO.emp_no}  員工姓名 ${empVO.emp_name} -->
								<li class="nav-item active"><a class="nav-link" href="#"
									id="empId" style="cursor: default;"><span>EMP0009
											詹詠祺</span>，您好！</a></li>
								<li class="nav-item active"><a class="nav-link" href="#">現場點餐</a></li>
								<li class="nav-item active"><a class="nav-link" href="#">現場劃位</a></li>
								<li class="nav-item active"><a class="nav-link" href="#">訂單結帳</a></li>
								<li class="nav-item active"><a class="nav-link" href="#">候位管理</a></li>
								<li class="nav-item active"><a class="nav-link"
									href="<%=request.getContextPath()%>/back-end/back-index_m.jsp">主管員工專區</a></li>
								<li class="nav-item active"><a class="nav-link"
									href="<%=request.getContextPath()%>/back-end/back-index_e.jsp">一般員工專區</a></li>
								<li class="nav-item active"><div id="topLogIn"
										style="display: inline-block; width: 90px; text-align: center; border-radius: 5px; background: #6d7fcc; color: #fff; cursor: pointer;">Log
										in</div>
									<div id="topLogOut"
										style="display: inline-block; width: 90px; text-align: center; margin-left: 10px; border-radius: 5px; background: #6d7fcc; color: #fff; cursor: pointer;">Log
										out</div></li>
							</ul>
						</div>
					</div>
				</div>
			</nav>

			<h5 style="font-weight: 900; display: inline-block;">主管員工專區</h5>
			<span> - 新增最新消息</span> <a
				href="<%=request.getContextPath()%>/back-end/back-index_New.jsp"
				style="display: inline-block; font-size: 8px; font-weight: 900; color: #6d7fcc; text-decoration: none; margin-left: 20px;">返回首頁</a>
			<p>
			<table id="table-1">
				<tr>
					<td>
						<h3 style="margin-bottom: 0;">新增最新消息</h3>
					</td>
				</tr>
			</table>
			<br>
			<%-- 錯誤表列 --%>
			<c:if test="${not empty errorMsgs}">
				<font style="color: red">請修正以下錯誤:</font>
				<ul>
					<c:forEach var="message" items="${errorMsgs}">
						<li style="color: red">${message}</li>
					</c:forEach>
				</ul>
			</c:if>
			<FORM METHOD="post"
				ACTION="<%=request.getContextPath()%>/news/news.do" name="form1">
				<table>
					<!-- 	<tr> -->
					<!-- 		<td>news_no:</td> -->
					<!-- 		<td><input type="TEXT" name="news_no" size="45"  -->
					<%-- 			 value="<%= (newsVO==null)? "SEQ_NEWS_NO" : newsVO.getNews_no()%>" /></td> --%>
					<!-- 	</tr> -->


					<!-- 					----------------------------------------------- -->

					<%-- <jsp:useBean id="empSvc" scope="page" class="com.ad.model.EmpService" /> --%>
<%-- 					<c:forEach var="empVO" items="${empSvc.all}"> --%>
<%-- 						<c:if test="${empVO.emp_no==newsVO.emp_no}"> --%>
<%-- 					${empVO.emp_name} --%>
<%-- 					</c:if> --%>
<%-- 					</c:forEach> --%>

					<!-- 					-------------------------------------------- -->
					<tr>
						<td>員工編號:</td>
						<td><input type="TEXT" name="emp_no" size="45"
							value="<%=(newsVO == null) ? "" : newsVO.getEmp_no()%>" /></td>
					</tr>
					<tr>
						<td>消息內容:</td>
						<td><input type="TEXT" name="news_cont" size="45"
							value="<%=(newsVO == null) ? "" : newsVO.getNews_cont()%>" /></td>
					</tr>
					<tr>
						<td>新增日期:</td>
						<td><input name="news_date" id="n_date" type="text"></td>
					</tr>
				</table>
				<br> <input type="hidden" name="action" value="insert">
				<input type="submit" value="送出新增">
			</FORM>
</body>

<%
	java.sql.Date getNews_date = null;
	try {
		getNews_date = newsVO.getNews_date();
	} catch (Exception e) {
		getNews_date = new java.sql.Date(System.currentTimeMillis());
	}
%>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script
	src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
.xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}
</style>

<script>
        $.datetimepicker.setLocale('zh');
        $('#n_date').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:false,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
		   value: '<%=getNews_date%>
	', // value:   new Date(),
	//disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
	//startDate:	            '2017/07/10',  // 起始日
	//minDate:               '-1970-01-01', // 去除今日(不含)之前
	//maxDate:               '+1970-01-01'  // 去除今日(不含)之後
	});
</script>
<!-- jQuery CDN - Slim version (=without AJAX) -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<!-- Popper.JS -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"
	integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ"
	crossorigin="anonymous"></script>
<!-- Bootstrap JS -->
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"
	integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm"
	crossorigin="anonymous"></script>
<!-- jQuery Custom Scroller CDN -->
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.concat.min.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$("#sidebar").mCustomScrollbar({
			theme : "minimal"
		});

		$('#sidebarCollapse').on('click', function() {
			$('#sidebar, #content').toggleClass('active');
			$('.collapse.in').toggleClass('in');
			$('a[aria-expanded=true]').attr('aria-expanded', 'false');
		});
	});
</script>
</html>