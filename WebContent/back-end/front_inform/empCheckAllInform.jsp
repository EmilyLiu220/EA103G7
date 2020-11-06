<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ page import="java.util.*"%>
<%@ page import="com.front_inform.model.*"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.emp_auth.model.*"%>
<%@ page import="com.fun_auth.model.*"%>

<%
	EmpVO empVO2 = (EmpVO) session.getAttribute("empVO2");
	List<Emp_authVO> emp_authVO2 = (List<Emp_authVO>) session.getAttribute("emp_authVO2");
	List<Fun_authVO> fun_authVO2 = (List<Fun_authVO>) session.getAttribute("fun_authVO2");	
	Front_InformService front_informSvc = new Front_InformService();
	List<Front_InformVO> list = front_informSvc.getAllInform();
	pageContext.setAttribute("list", list);
%>
<jsp:useBean id="memSvc" scope="page" class="com.mem.model.MemService"></jsp:useBean>

<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>查看通知</title>

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

.unshow{
 	display: none;
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
		<h5 style="font-weight: 900; display: inline-block;">一般員工專區</h5><span> - 查看通知</span>
		<a href="<%=request.getContextPath()%>/back-end/front_inform/select_fi.jsp" style="display: inline-block; font-size: 8px; font-weight: 900; color: #dea554; text-decoration: none; margin-left: 20px;" onMouseOver="this.style.color='#ffbc5e';" onMouseOut="this.style.color='#dea554';">返回查詢頁面</a>		
		<a href="<%=request.getContextPath()%>/back-end/backindex.jsp" style="display: inline-block; font-size: 8px; font-weight: 900; color: #dea554; text-decoration: none; margin-left: 20px;" onMouseOver="this.style.color='#ffbc5e';" onMouseOut="this.style.color='#dea554';">返回首頁</a>		
		<p>
			<table id="table-1">
				<tr>
					<td>
						<h3 style="margin-bottom:0;">查看所有前檯通知</h3>
					</td>
				</tr>
			</table>
			<br>
			
			<table class="table table-hover" style="width: 100%; font-size: 90%;">
				<thead style="text-align: center;">
					<tr>
						<th style="width: 10%;">編號</th>
						<th style="width: 20%;">會員</th>
						<th style="width: 10%;">訂位編號</th>
						<th style="width: 20%;">通知內容</th>
						<th style="width: 20%;">通知日期</th>
						<th style="width: 10%;">類別</th>
						<th style="width: 10%;">讀取狀態</th>
					</tr>
				</thead>
				<%@ include file="page1.file"%>
				<tbody>
				<c:forEach var="front_informVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
					<tr>
						<td style="text-align: center;">${front_informVO.info_no}</td>
						<td style="text-align: center;">${front_informVO.mem_no} ${pageScope.memSvc.getOneMem(front_informVO.mem_no).mem_name}</td>
						<td style="text-align: center;">${front_informVO.res_no}</td>
						<td style="text-align: center;">
							<c:choose>
								<c:when test="${front_informVO.info_cont eq '提醒您，因您多次訂位但皆未至本餐廳用餐，您的訂位權限將被暫停使用'}">暫停訂位功能通知</c:when>
								<c:when test="${front_informVO.info_cont eq '您的訂位權限已恢復喔~點選查看訂位頁面'}">訂位功能恢復通知</c:when>
								<c:when test="${front_informVO.info_cont eq '提醒您，因您多次訂餐付款但皆未至本餐廳取餐，您的訂餐權限將被暫停使用'}">暫停訂餐功能通知</c:when>
								<c:when test="${front_informVO.info_cont eq '您的訂餐權限已恢復喔~點選查看訂餐頁面'}">訂餐功能恢復通知</c:when>
								<c:when test="${front_informVO.info_cont eq '提醒您，因您檢舉多則評價，但評價內容多數未達不當言論之標準，您的檢舉權限將被暫停使用'}">暫停檢舉功能通知</c:when>
								<c:when test="${front_informVO.info_cont eq '您的檢舉權限已恢復喔~'}">檢舉功能恢復通知</c:when>
								<c:when test="${front_informVO.info_cont eq '提醒您，因您有多則評價被檢舉成功，您的評價權限將被暫停使用'}">暫停評價功能通知</c:when>
								<c:when test="${front_informVO.info_cont eq '您的評價權限已恢復喔~'}">評價功能恢復通知</c:when>
								<c:when test="${front_informVO.info_cont eq '提醒您，您已被停權'}">會員停權通知</c:when>
								<c:when test="${front_informVO.info_cont eq '歡迎您回來~'}">取消停權通知</c:when>
								<c:when test="${front_informVO.info_cont eq '訂位成功，點選查看訂位訂單'}">訂位成功通知</c:when>
								<c:when test="${front_informVO.info_cont eq '訂位訂單修改成功，點選查看訂位訂單'}">訂位修改通知</c:when>
								<c:when test="${front_informVO.info_cont eq '訂餐成功，點選查看訂餐訂單'}">訂餐成功通知</c:when>
								<c:when test="${front_informVO.info_cont eq '餐點已完成，請至本餐廳取餐(點選可查看訂單)'}">取餐通知</c:when>
								<c:when test="${front_informVO.info_cont eq '您的訂餐已取消'}">訂餐取消通知</c:when>
								<c:when test="${front_informVO.info_cont eq '您的訂位已取消'}">訂位取消通知</c:when>
								<c:when test="${fn:contains(front_informVO.info_cont, '是否確認今日用餐')}">當日用餐通知</c:when>
								<c:otherwise>活動推播通知</c:otherwise>
							</c:choose>
						</td>
						<td style="text-align: center;"><fmt:formatDate value="${front_informVO.info_date}" pattern="yyyy-MM-dd" /></td>
						<td style="text-align: center;">
							<c:choose>
								<c:when test="${front_informVO.info_sts == 0}">一般通知</c:when>
								<c:when test="${front_informVO.info_sts == 1}">確認用餐</c:when>
								<c:when test="${front_informVO.info_sts == 2}">尚未回覆</c:when>
								<c:when test="${front_informVO.info_sts == 3}">取消訂位</c:when>
							</c:choose>
						</td>
						<td style="text-align: center;">
							<c:choose>
								<c:when test="${front_informVO.read_sts == 0}">未讀</c:when>
								<c:when test="${front_informVO.read_sts == 1}">已讀</c:when>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<%@ include file="page2.file"%>
		</p>
	</div>
</div>
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