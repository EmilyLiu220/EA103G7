<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.inform_set.model.*"%>
<%@ page import="java.io.*"%>
<%@ page import="com.meal.model.*"%>

<% 
	MealService mealSrv = new MealService(); 
	List<MealVO> list = mealSrv.getAll();
	request.setAttribute("list",list);
%>
<jsp:useBean id="mealCatSrv" class="com.meal_category.model.MealCatService"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>訂單管理-listQuery</title>

<jsp:useBean id="empSvc" scope="page" class="com.emp.model.EmpService"></jsp:useBean>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-end/datetimepicker/jquery.datetimepicker.css" />
<!-- Bootstrap CSS CDN -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
<!-- Our Custom CSS -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/back-end/css/style2.css">
<!-- Scrollbar Custom CSS -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.min.css">
<!-- Font Awesome JS -->
<script defer src="https://use.fontawesome.com/releases/v5.0.13/js/solid.js" integrity="sha384-tzzSw1/Vo+0N5UhStP3bvwWPq+uvzCMfrN1fEFe+xBmv1C/AtVX5K0uZtmcHitFZ" crossorigin="anonymous"></script>
<script defer src="https://use.fontawesome.com/releases/v5.0.13/js/fontawesome.js" integrity="sha384-6OIrr52G08NpOFSZdxxz1xdNSndlD4vdcf/q2myIUVO0VsqaGHJsB0RaBE01VTOY" crossorigin="anonymous"></script>

<style>
#table-1, #table-1 td {
	background: #555;
    color: #fff;
	border: 0;
	width: 100%;
	border-radius: 5px;
	text-align: center;
	box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);
}
.table a{
color:blue;
text-decoration: underline;
}
</style>

</head>
<body>

	<div class="wrapper">

		<!-- Sidebar  -->
		<nav id="sidebar">
			<div class="sidebar-header" style="cursor: default;">
				<h3>
					<c:choose>
						<c:when test="${empVO2.emp_no==null}">
							嗨
						</c:when>
						<c:otherwise>
							 ${empVO2.emp_no}<br>${empVO2.emp_name}
						</c:otherwise>					
					</c:choose>
					，您好！
				</h3>
			</div>

			<ul class="list-unstyled components">
				<li><a href="#">現場點餐</a></li>
				<li><a href="#">現場劃位</a></li>
				<li><a href="#">訂單結帳</a></li>
				<li><a href="#">候位管理</a></li>
				<li class="active"><a href="#pageSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">主管員工專區</a>
					<ul class="collapse list-unstyled" id="pageSubmenu">
						<li><a href="#">員工管理</a></li>
						<li><a href="#">會員管理</a></li>
						<li><a href="#">廣告管理</a></li>
						<li><a href="#">最新消息管理</a></li>
						<li><a href="<%=request.getContextPath()%>/back-end/inform_set/select_is.jsp">通知管理</a></li>
						<li><a href="#">評價管理</a></li>
						<li><a href="#">用餐時段管理</a></li>
						<li><a href="#">桌位管理</a></li>
						<li><a href="#">菜單管理</a></li>
						<li><a href="#">食材管理</a></li>
						<li><a href="#">餐點組成管理</a></li>
						<li><a href="#">食材消耗統計</a></li>
						<li><a href="#">紅利商品管理</a></li>
					</ul>
				</li>
				<li><a href="#homeSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">一般員工專區</a>
					<ul class="collapse list-unstyled" id="homeSubmenu">
						<li><a href="<%=request.getContextPath()%>/back-end/front_inform/empCheckAllInform.jsp">查看通知</a></li>
						<li><a href="<%=request.getContextPath()%>/back-end/message_record/backEndChatRoom.jsp">後檯即時通訊</a></li>
						<li><a href="#">訂單派工</a></li>
						<li><a href="#">出餐管理</a></li>
						<li><a href="#">訂餐訂單處理</a></li>
						<li><a href="#">訂餐管理</a></li>
						<li><a href="#">訂單管理</a></li>
						<li><a href="#">訂位管理</a></li>
					</ul>
				</li>
			</ul>

			<ul class="list-unstyled CTAs">
				<c:choose>
					<c:when test="${empVO2.emp_no==null}">
						<li><a href="<%=request.getContextPath()%>/back-end/emp/login.jsp" id="logIn">Log in</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="#" id="logOut">Log out</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</nav>

		<!-- Page Content  -->
		<div id="content">

			<nav class="navbar navbar-expand-lg navbar-light bg-light">
				<div class="container-fluid">

					<button type="button" id="sidebarCollapse" class="btn btn-dark">
						<i class="fas fa-align-justify"></i>
					</button>
					<div id="titleBig" style="margin: 0 auto; font-size: 30px; font-weight: 800;"><a href="<%=request.getContextPath()%>/back-end/back-index_New.jsp">吃 Pot 吧！員工專區</a></div>
					<div id="rwdShow">
						<button type="button" id="topbarCollapse" class="btn btn-dark"
							data-toggle="collapse" data-target="#navbarSupportedContent"
							aria-controls="navbarSupportedContent" aria-expanded="false"
							aria-label="Toggle navigation">
							<i class="fas fa-align-justify"></i>
						</button>
						<div id="titleSmall" style="padding-left: 10px; font-size: 30px; font-weight: 800;"><a href="<%=request.getContextPath()%>/back-end/back-index_New.jsp">吃 Pot 吧！員工專區</a></div>
						<div class="collapse navbar-collapse" id="navbarSupportedContent">
							<ul class="nav navbar-nav ml-auto">
								<li class="nav-item active"><a class="nav-link" href="#"
									id="empId" style="cursor: default;">
									<c:choose>
										<c:when test="${empVO2.emp_no==null}">
											<span style="color: red; margin-top: 1rem;">嗨，您好！請記得登入喔！</span>
										</c:when>
										<c:otherwise>
											<span>${empVO2.emp_no}&nbsp;&nbsp;&nbsp;${empVO2.emp_name}，您好！</span>
										</c:otherwise>
									</c:choose>
								</a></li>
								<li class="nav-item active"><a class="nav-link" href="#">現場點餐</a></li>
								<li class="nav-item active"><a class="nav-link" href="#">現場劃位</a></li>
								<li class="nav-item active"><a class="nav-link" href="#">訂單結帳</a></li>
								<li class="nav-item active"><a class="nav-link" href="#">候位管理</a></li>
								<li class="nav-item active"><a class="nav-link" href="<%=request.getContextPath()%>/back-end/back-index_m.jsp">主管員工專區</a></li>
								<li class="nav-item active"><a class="nav-link" href="<%=request.getContextPath()%>/back-end/back-index_e.jsp">一般員工專區</a></li>
								<li class="nav-item active" style="display: block; padding-top: 0.5rem; padding-bottom: 0.5rem;">
									<c:choose>
										<c:when test="${empVO2.emp_no==null}">
											<div id="topLogIn" style="display: inline-block; width: 90px; text-align: center; margin-left: 10px; border-radius: 5px; background: #424242; color: #ccc; cursor: pointer;" onMouseOver="this.style.color='#fff'; this.style.background='#000';" onMouseOut="this.style.color='#ccc'; this.style.background='#424242';">Log in</div>
										</c:when>
										<c:otherwise>
											<div id="topLogOut" style="display: inline-block; width: 90px; text-align: center; margin-left: 10px; border-radius: 5px; background: #424242; color: #ccc; cursor: pointer;" onMouseOver="this.style.color='#fff'; this.style.background='#000';" onMouseOut="this.style.color='#ccc'; this.style.background='#424242';">Log out</div>
										</c:otherwise>
									</c:choose>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</nav>

			<h5 style="font-weight: 900; display: inline-block;">一般員工專區</h5><span> - 餐點管理</span>
			<a href="<%=request.getContextPath()%>/back-end/back-index_New.jsp" style="display: inline-block; font-size: 8px; font-weight: 900; color: #dea554; text-decoration: none; margin-left: 20px;" onMouseOver="this.style.color='#ffbc5e';" onMouseOut="this.style.color='#dea554';">返回首頁</a>			
			<p>
				<table id="table-1">
					<tr>
						<td>
							<h3 style="margin-bottom:0;">查看所有餐點內容</h3>
						</td>
					</tr>
				</table>
				<br>
<%-- 				<form action="<%= request.getContextPath()%>/MealOrderServlet.do" method="POST"> --%>
<!-- 				<table id="table-2"> -->
<!-- 				<tr class="row query"> -->
<!-- 					<td class="col">訂單編號：<input type="text" name="meal_order_no" size="10"/></td> -->
<!-- 					<td class="col">員工編號：<input type="text" name="emp_no" size="10"/></td> -->
<!-- 					<td class="col">會員編號：<input type="text" name="mem_no" size="10"/></td> -->
<!-- 				</tr> -->
<!-- 				<tr class="row query"> -->
<!-- 					<td class="col">通知狀態：<select name="noti_sts"> -->
<!-- 			 			<option value=''>請選擇 -->
<!-- 			 			<option value=0>未通知 -->
<!-- 			 			<option value=1>已通知 -->
<!-- 						</select></td> -->
<!-- 				<td class="col">付款狀態：<select name="pay_sts"> -->
<!-- 			 			<option value=''>請選擇 -->
<!-- 			 			<option value=0>未付款 -->
<!-- 			 			<option value=1>已付款 -->
<!-- 						</select></td> -->
<!-- 				<td class="col">訂單狀態：<select name="meal_order_sts"> -->
<!-- 			 			<option value=''>請選擇 -->
<!-- 			 			<option value=0>已取消 -->
<!-- 			 			<option value=1>未派工 -->
<!-- 			 			<option value=2>已派工 -->
<!-- 			 			<option value=3>出餐未取 -->
<!-- 			 			<option value=4>已完成 -->
<!-- 						</select></td> -->
<!-- 						</tr> -->
<!-- 	<tr class="row query"> -->
<!-- 				<td class="col"> -->
<!-- 				訂餐時間：<input type="text" name="order_time" class="f_date1"/> -->
<!-- 				至 <input type="text" name="order_time" class="f_date1"/> 之間</td> -->
<!-- 				</tr> -->
<!-- 	<tr class="row query">			 -->
<!-- 				<td class="col"> -->
<!-- 				取餐時間：<input type="text" name="pickup_time" class="f_date1"/> -->
<!-- 				至 <input type="text" name="pickup_time" class="f_date1"/> 之間</td> -->
<!-- 				<td> -->
<!-- 				<input type="submit" value="查詢結果"/> -->
<!-- 				<input type="hidden" name="action" value="queryAll"/></td> -->
<!-- 				</tr> -->
				
<!-- 				</table> -->
<!-- 				</form> -->
				<br>
				<%-- 錯誤表列 --%>

				<table class="table table-hover" style="width: 80%; font-size: 90%;">
					<thead style="text-align: center;">
						<tr>
							<th style="width: 10%;">餐點編號</th>
							<th style="width: 10%;">餐點名稱</th>
							<th style="width: 20%;">餐點描述</th>
							<th style="width: 10%;">餐點價格</th>
							<th style="width: 10%;">餐點種類</th>
							<th style="width: 10%;">上下架狀態</th>
							<th style="width: 10%;"></th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="mealVO" items="${list}">
						<tr>
							<td style="text-align: center;"><font color="#dea554">${mealVO.meal_no}</font></td>
							<td style="text-align: center;">${mealVO.meal_name}</td>
							<td style="text-align: center;">${mealVO.meal_info}</td>
							<td style="text-align: center;">${mealVO.meal_price} 元</td>
							<td style="text-align: center;"><c:forEach var="mealCatVO" items="${mealCatSrv.all}">
                    <c:if test="${mealVO.cat_no == mealCatVO.cat_no}">
	                    ${mealCatVO.cat_name}
                    </c:if>
                </c:forEach></td>
							<td style="text-align: center;">${mealVO.meal_sts == 0?'<font color="red">已下架</font>':'<font color="green">上架中</font>'}</td>
							<td style="text-align: center;"><FORM METHOD="post" ACTION="<%= request.getContextPath() %>/meal/meal.do" style="margin-bottom: 0px;">
			     											<input type="submit" value="修改餐點">
			     											<input type="hidden" name="meal_no"  value="${mealVO.meal_no}">
			     											<input type="hidden" name="action"	value="getOneupdate"></FORM></td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
			</p>
		</div>
	</div>

	<!-- jQuery CDN - Slim version (=without AJAX) -->
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<!-- Popper.JS -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
	<!-- Bootstrap JS -->
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
	<!-- jQuery Custom Scroller CDN -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.concat.min.js"></script>
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
</body>
</html>