<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.inform_set.model.*"%>
<%@ page import="com.emp_auth.model.*"%>
<%@ page import="com.fun_auth.model.*"%>

<%
	EmpVO empVO2 = (EmpVO) session.getAttribute("empVO2");
	Inform_SetVO isVO = (Inform_SetVO) request.getAttribute("isVO");
	List<Emp_authVO> emp_authVO2 = (List<Emp_authVO>) session.getAttribute("emp_authVO2");
	List<Fun_authVO> fun_authVO2 = (List<Fun_authVO>) session.getAttribute("fun_authVO2");
%>
<jsp:useBean id="empSvc" scope="page" class="com.emp.model.EmpService" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>通知管理</title>

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/back-end/css/bootstrap-4.1.0.min.css">
<!-- Our Custom CSS -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/back-end/css/style2.css">
<!-- Scrollbar Custom CSS -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/back-end/css/jquery-3.1.4.mCustomScrollbar.min.css">
<!-- Font Awesome JS -->
<script defer src="<%=request.getContextPath()%>/back-end/js/solid.js"></script>
<script defer src="<%=request.getContextPath()%>/back-end/js/fontawesome.js"></script>

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
.unshow {
 	display: none;
}
#forward {
	display: block; 
		position: absolute; 
		right: 20%; 
		top: 80%;
}
#back {
	display: block; 
		position: absolute; 
		right: 35%; 
		top: 80%;
}
#revise {
	cursor: pointer; 
	margin-right: 10px; 
	background: #c8a97e; 
	color: #fff; 
	border: 0; 
	border-radius: 5px; 
	width: 100px; 
	height: 40px; 
	font-weight: 600;
}
#holdBack{
	cursor: pointer; 
	margin-right: 10px; 
	background: #c8a97e; 
	color: #fff; 
	border: 0; 
	border-radius: 5px; 
	width: 100px; 
	height: 40px; 
	font-weight: 600;
}
</style>
<style>
	.xdsoft_datetimepicker .xdsoft_datepicker {
		width:  300px;   /* width:  300px; */
	}
	.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
		height: 151px;   /* height:  151px; */
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
				<c:choose>
					<c:when test="${empVO2.emp_no!=null}">
						<li style="font-size:20px;"><a href="<%=request.getContextPath()%>/back-end/emp/emp.do?action=Update_info&emp_no=${empVO2.emp_no}">員工個資修改</a></li>
					</c:when>
				</c:choose>
				<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/res_order/orderSeat.jsp">現場劃位</a></li>
				<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/wait_seat/listAllWait_seat.jsp">候位管理</a></li>
				<li class="active"><a href="#pageSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">主管員工專區</a>
					<ul class="collapse list-unstyled" id="pageSubmenu">
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/emp/select_page.jsp">員工管理</a></li>
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/mem/select_page_mem.jsp">會員管理</a></li>
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/ad/select_ad.jsp">廣告管理</a></li>
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/news/select_news.jsp">最新消息管理</a></li>
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/inform_set/select_is.jsp">通知管理</a></li>
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/member_review/select_page.jsp">評價管理</a></li>
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/seat/editSeat.jsp">桌位管理</a></li>
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/meal/menuManagement.jsp">菜單管理</a></li>
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/food/listAllFood.jsp">食材管理</a></li>
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/meal_part/listAllMeal_part.jsp">餐點組成管理</a></li>
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/food/Statistics.jsp">食材消耗統計</a></li>
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/bonus/select_page.jsp">紅利商品管理</a></li>
					</ul>
				</li>
				<li><a href="#homeSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">一般員工專區</a>
					<ul class="collapse list-unstyled" id="homeSubmenu">
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/front_inform/select_fi.jsp">查看通知</a></li>
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/message_record/backEndChatRoom.jsp">後檯即時通訊</a></li>
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/mealOrder/mealOrderManagement.jsp">訂餐管理</a></li>
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/res_order/resOrderManage.jsp">訂位管理</a></li>
					</ul>
				</li>
			</ul>

			<ul class="list-unstyled CTAs">
				<c:choose>
					<c:when test="${empVO2.emp_no==null}">
						<li><a href="<%=request.getContextPath()%>/back-end/emp/login.jsp" id="logIn">Log in</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="<%=request.getContextPath()%>/back-end/emp/emp.do?action=logout" id="logOut">Log out</a></li>
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
					<div id="titleBig" style="margin: 0 auto; font-size: 30px; font-weight: 800;"><a href="<%=request.getContextPath()%>/back-end/backindex.jsp">吃 Pot 吧！員工專區</a></div>
					<div id="rwdShow">
						<button type="button" id="topbarCollapse" class="btn btn-dark"
							data-toggle="collapse" data-target="#navbarSupportedContent"
							aria-controls="navbarSupportedContent" aria-expanded="false"
							aria-label="Toggle navigation">
							<i class="fas fa-align-justify"></i>
						</button>
						<div id="titleSmall" style="padding-left: 10px; font-size: 30px; font-weight: 800;"><a href="<%=request.getContextPath()%>/back-end/backindex.jsp">吃 Pot 吧！員工專區</a></div>
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
								<li class="nav-item active"><a class="nav-link" href="<%=request.getContextPath()%>/back-end/res_order/orderSeat.jsp">現場劃位</a></li>
								<li class="nav-item active"><a class="nav-link" href="<%=request.getContextPath()%>/back-end/wait_seat/listAllWait_seat.jsp">候位管理</a></li>
								<li class="nav-item active"><a class="nav-link" href="<%=request.getContextPath()%>/back-end/back-index_m.jsp">主管員工專區</a></li>
								<li class="nav-item active"><a class="nav-link" href="<%=request.getContextPath()%>/back-end/back-index_e.jsp">一般員工專區</a></li>
								<li class="nav-item active" style="display: block; padding-top: 0.5rem; padding-bottom: 0.5rem;">
									<c:choose>
										<c:when test="${empVO2.emp_no==null}">
											<div id="topLogIn" style="display: inline-block; width: 90px; text-align: center; margin-left: 10px; border-radius: 5px; background: #424242; color: #ccc; cursor: pointer;" onMouseOver="this.style.color='#fff'; this.style.background='#000';" onMouseOut="this.style.color='#ccc'; this.style.background='#424242';"><a href="<%=request.getContextPath()%>/back-end/emp/login.jsp">Log in</a></div>
										</c:when>
										<c:otherwise>
											<div id="topLogOut" style="display: inline-block; width: 90px; text-align: center; margin-left: 10px; border-radius: 5px; background: #424242; color: #ccc; cursor: pointer;" onMouseOver="this.style.color='#fff'; this.style.background='#000';" onMouseOut="this.style.color='#ccc'; this.style.background='#424242';"><a href="<%=request.getContextPath()%>/back-end/emp/emp.do?action=logout">Log out</a></div>
										</c:otherwise>
									</c:choose>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</nav>

			<h5 style="font-weight: 900; display: inline-block;">主管員工專區</h5><span> - 通知管理</span>
			<a href="<%=request.getContextPath()%>/back-end/inform_set/select_is.jsp" style="display: inline-block; font-size: 8px; font-weight: 900; color: #dea554; text-decoration: none; margin-left: 20px;" onMouseOver="this.style.color='#ffbc5e';" onMouseOut="this.style.color='#dea554';">返回查詢頁面</a>		
			<a href="<%=request.getContextPath()%>/back-end/backindex.jsp" style="display: inline-block; font-size: 8px; font-weight: 900; color: #dea554; text-decoration: none; margin-left: 20px;" onMouseOver="this.style.color='#ffbc5e';" onMouseOut="this.style.color='#dea554';">返回首頁</a>			
			<p>
				<table id="table-1">
					<tr>
						<td>
							<h3 style="margin-bottom:0;">修改群發通知</h3>
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
				<FORM METHOD="post" ACTION="is.do" name="form1">
					<table class="table table-hover" style="width: 100%; font-size: 90%;">
						<tr>
							<td>通知編號:<font color=red><b>*</b></font></td>
							<td><%=isVO.getIs_no()%></td>
						</tr>
						<tr>
							<td>員工編號:<font color=red><b>*</b></font></td>
							<c:choose>
								<c:when test="${empVO2.emp_no==null}">
									<td><input type="TEXT" name="emp_no" size="45"	value="<%=isVO.getEmp_no()%>" /></td>
								</c:when>
								<c:otherwise>
									<td><input type="TEXT" name="emp_no" size="45" value="${empVO2.emp_no}"/></td>
								</c:otherwise>
							</c:choose>
						</tr>
						<tr>
							<td>通知內容:<font color=red><b>*</b></font></td><br>
							<td><textarea style="width:400px;height:100px;" name="is_cont" placeholder="<%=isVO.getIs_cont()%>"></textarea></td>
						</tr>
						<tr>
							<td>通知日期:<font color=red><b>*</b></font></td>
							<td><input  type="TEXT" name="is_date" id="f_date1"/></td>
						</tr>
					</table>
					<br>
					<div id="forward">
						<input type="hidden" name="is_no" value="<%=isVO.getIs_no()%>"/>
						<input type="hidden" name="action" value="updateIs"/>
						<input id="revise" type="submit" value="送出修改" onMouseOver="this.style.background='#ffbc5e'" onMouseOut="this.style.background='#c8a97e'"/>
					</div>
				</FORM>
				<div id="back">
					<FORM METHOD="post" ACTION="is.do" name="form1">
						<input type="hidden" name="is_no" value="<%=isVO.getIs_no()%>"/>
						<input type="hidden" name="action" value="getOneIsForDisplay"/>
						<input id="holdBack" type="submit" value="取消修改" onMouseOver="this.style.background='#ffbc5e'" onMouseOut="this.style.background='#c8a97e'"/>
					</FORM>
				</div>
			</p>	
		</div>
	</div>

	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/back-end/datetimepicker/jquery.datetimepicker.css"/>
	<script src="<%=request.getContextPath()%>/back-end/datetimepicker/jquery.js"></script>
	<script src="<%=request.getContextPath()%>/back-end/datetimepicker/jquery.datetimepicker.full.js"></script>
	
	<script>
	 	$.datetimepicker.setLocale('zh');
		$('#f_date1').datetimepicker({
			theme: 'dark',
			timepicker:false,
			step: 1,
			format:'Y-m-d',
			value: '<%=isVO.getIs_date()%>',
			minDate:new Date() // 去除今日(不含)之前
		});
	</script>
	
	<!-- jQuery CDN - Slim version (=without AJAX) -->
	<script src="<%=request.getContextPath()%>/back-end/js/jquery-3.3.1.slim.min.js"></script>
	<!-- Popper.JS -->
	<script src="<%=request.getContextPath()%>/back-end/js/popper-2018.min.js"></script>
	<!-- Bootstrap JS -->
	<script src="<%=request.getContextPath()%>/back-end/js/bootstrap-4.1.0.min.js"></script>
	<!-- jQuery Custom Scroller CDN -->
	<script src="<%=request.getContextPath()%>/back-end/js/jquery-3.1.5.mCustomScrollbar.concat.min.js"></script>
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
	<div id="fun" style="display:none">
		<c:forEach var="fun_authVO2" items="${fun_authVO2}">
			<span class="fun">${fun_authVO2.fun_name}</span><br>
		</c:forEach>
	</div>
	
	<script>
		// 判斷員工擁有哪些權限可以點選
		var fun = document.getElementsByClassName("fun");
		var arr1 = [];
		for (let i = 0; i < fun.length; i++) {
			var x = fun[i].innerText;
			arr1.push(x);
		}
		
		var fun2 = document.getElementsByClassName("fun2");
		var arr2 = [];
		for (let i = 0; i < fun2.length; i++) {
			var y = fun2[i].innerText;
			arr2.push(y);
		}
		
		for (let i = 0; i < arr2.length; i++) {
			var allow = true;
			for (let j = 0; j < arr1.length; j++) {
				if (arr2[i] === arr1[j]) {
					allow = false;
					break;
				}
			}
			if (allow) {
				fun2[i].classList.add('unshow');
			}
		}
	</script>
</body>
</html>