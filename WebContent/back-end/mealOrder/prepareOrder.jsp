<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.inform_set.model.*"%>
<%@ page import="com.meal_order.model.*"%>
<%@ page import="com.meal_order_detail.model.*"%>

<% 
	MealOrderService mealOrderSrv = new MealOrderService();
	List<MealOrderVO> list = mealOrderSrv.searchByOrderSts(2);
	MealOrderDetailService detailSrv = new MealOrderDetailService();
	List<MealOrderDetailVO> detailList = new ArrayList<>();
	for(MealOrderVO mealOrderVO :list){
		for(MealOrderDetailVO detailVO :detailSrv.searchByOrderNo(mealOrderVO.getMeal_order_no())){
			detailList.add(detailVO);
		}
	}
	pageContext.setAttribute("list", list);
	pageContext.setAttribute("detailList", detailList);

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>訂單管理-出餐</title>
<jsp:useBean id="mealSrv" class="com.meal.model.MealService"/>
<jsp:useBean id="empSvc" scope="page" class="com.emp.model.EmpService"/>
<jsp:useBean id="mealSetSrv" scope="page" class="com.meal_set.model.MealSetService"/>
<%-- <jsp:useBean id="detailSrv" scope="page" class="com.meal_order_detail.model.MealOrderDetailService"/> --%>
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
#table-1, #table-1 td{
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
.table {
	margin-left: auto;
	margin-right: auto;
}
.hightlight {
	animation: blink 0.3s linear;
	background-color:darkgray;
	color: black;
        }
         @keyframes blink {
            0% {
                background-color: #abc;
            }
            60% {
                background-color: yellow;
            }
            80% {
                background-color: white;
            }
            100% {
                background-color: darkgray;
                color: black;
            }
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

			<h5 style="font-weight: 900; display: inline-block;">一般員工專區</h5><span> - 出餐管理</span>
			<a href="<%=request.getContextPath()%>/back-end/backindex.jsp" style="display: inline-block; font-size: 8px; font-weight: 900; color: #dea554; text-decoration: none; margin-left: 20px;" onMouseOver="this.style.color='#ffbc5e';" onMouseOut="this.style.color='#dea554';">返回首頁</a>			
			<p>
				<table id="table-1">
					<tr>
						<td>
							<h3 style="margin-bottom:0;">待製作餐點列表</h3>
						</td>
					</tr>
					<tr><td><span></span></td></tr>
				</table>
				<br>
<%-- 					<%@ include file="page1.file"%> --%>
				<c:forEach var="mealOrderVO" items="${list}">
				 <table id="table-1" class="${mealOrderVO.meal_order_no}">
					<tr>
						<td>
							<h3 style="margin-bottom:0;">訂餐編號：${mealOrderVO.meal_order_no}</h3>
						</td>
					</tr>
				</table>
				
				<table id="${mealOrderVO.meal_order_no}" class="table table-hover ${mealOrderVO.meal_order_no}" style="width: 60%; font-size: 90%;">
					<input type="hidden" name="pay_sts" value="${mealOrderVO.pay_sts}"/>
					<input type="hidden" name="noti_sts" value="${mealOrderVO.noti_sts}"/>
					<thead style="text-align: center;">
						<tr>
							<th style="width: 10%;">check</th>
							<th style="width: 25%;">餐點名稱</th>
							<th style="width: 25%;">餐點數量</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach var="detailVO" items="${detailList}">
			<c:if test="${mealOrderVO.meal_order_no == detailVO.meal_order_no and not empty detailVO.meal_no}">
			<tr>
				<td style="text-align: center;"><input class="checkbox" type="checkbox"/>
				<input type="hidden" value="${mealOrderVO.meal_order_no}"/></td>
				<td style="text-align: center;">${mealSrv.searchByNo(detailVO.meal_no).meal_name}</td>
				<td style="text-align: center;">${detailVO.qty}</td>
			</tr>
			</c:if>
     		 </c:forEach>
     		 
     		  <c:forEach var="detailVO" items="${detailList}">
     		 <c:if test="${mealOrderVO.meal_order_no == detailVO.meal_order_no and not empty detailVO.meal_set_no}">
			<tr>
				<td style="text-align: center;"><input class="checkbox" type="checkbox"/>
				<input type="hidden" value="${mealOrderVO.meal_order_no}"/></td>
				<td style="text-align: center;">${mealSetSrv.searchByNo(detailVO.meal_set_no).meal_set_name}</td>
				<td style="text-align: center;">${detailVO.qty}</td>
				
			</tr>
			 </c:if>
     		 </c:forEach>
					</tbody>
				</table>
				</c:forEach>
			</p>
		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.5.1.min.js" integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0=" crossorigin="anonymous"></script>
	<!-- jQuery CDN - Slim version (=without AJAX) -->
<!-- 	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
	<!-- Popper.JS -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
	<!-- Bootstrap JS -->
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
	<!-- jQuery Custom Scroller CDN -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.concat.min.js"></script>
<%-- <script src="<%=request.getContextPath()%>/front-end/datetimepicker/jquery.js"></script> --%>
<%-- 	<script src="<%= request.getContextPath() %>/front-end/js/jquery-3.4.1.min.js"></script> --%>
	<script src="<%=request.getContextPath()%>/front-end/datetimepicker/jquery.datetimepicker.full.js"></script>
	
	
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
	
	var count = document.getElementsByClassName("table").length;
	$("#table-1").find("span").html('目 前 共 有  <font color="red" size="5">' + count  + ' 筆   </font>未 完 成 餐 點 的 訂 單');
	
	$(".checkbox").change(sendOrder);
	
	function sendOrder(){
		var mealOrderNo = $(this).next().val();
		var notiSts = $("#"+mealOrderNo).find('input[name="noti_sts"]').val();
		var paySts = $("#"+mealOrderNo).find('input[name="pay_sts"]').val();
		var len = $("#"+mealOrderNo).find('input[class="checkbox"]').length;
		var checklen = $("#"+mealOrderNo).find('input[class="checkbox"]:checked').length;
		$(this).parent().parent().toggleClass("hightlight");
		if(len === checklen){
			$.ajax({
                url: "${pageContext.request.contextPath}/MealOrderServlet.do",
                type: "POST",
                data: {
                    action: "prepared",
                    meal_order_no: mealOrderNo,
                    noti_sts:notiSts,
                    pay_sts:paySts
                },
//                 dataType: "JSON",
                success: function () {
					 $("."+mealOrderNo).remove();
					 $(".checkbox").change(sendOrder);
					 count = document.getElementsByClassName("table").length;
						$("#table-1").find("span").html('目 前 共 有  <font color="red" size="5">' + count  + ' 筆   </font>未 完 成 餐 點 的 訂 單');
                }
            });
		}
		
	};
	
	var webSocket =null;
	var MyPoint = "/MealOrderWebSocket";
	var host = window.location.host;
	var path = window.location.pathname;
	var webCtx = path.substring(0, path.indexOf('/', 1));
	var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;
	if('WebSocket' in window){
		webSocket = new WebSocket(endPointURL);
	}else{
		alert('browser not support websocket');
	}
	webSocket.onopen = function (e){
		console.log('websocket onopen');
	}
	
	webSocket.onmessage = function (e){
		var jsonObj = JSON.parse(e.data);
		if(jsonObj.action === 'prepared'){
				var table = document.createElement("table");
				table.setAttribute('id','table-1');
				table.classList.add(jsonObj.mealOrderVO.meal_order_no);
				table.innerHTML = '<tr><td><h3 style="margin-bottom:0;">訂餐編號：'+ jsonObj.mealOrderVO.meal_order_no+'</h3></td></tr>';
				
				var table2 = document.createElement("table");
				table2.setAttribute('id',jsonObj.mealOrderVO.meal_order_no);
				table2.setAttribute('style','width: 60%;font-size: 90%;');
				table2.classList.add(jsonObj.mealOrderVO.meal_order_no+"body",'table','table-hover',jsonObj.mealOrderVO.meal_order_no);
				table2.innerHTML = 
				 '<input type="hidden" name="pay_sts" value="'+jsonObj.mealOrderVO.pay_sts +'"/>'
				+ '<input type="hidden" name="noti_sts" value="'+jsonObj.mealOrderVO.noti_sts +'"/>'
				+ '<thead style="text-align: center;"><tr><th style="width: 10%;">check</th><th style="width: 25%;">餐點名稱</th>	<th style="width: 25%;">餐點數量</th></tr></thead>'
				+ '<tbody class="'+jsonObj.mealOrderVO.meal_order_no+'body2">';
				
				$("#content").append(table);
				table.after(table2);
				
				jsonObj.detailList.forEach(function(detailVO){
					var row ='<tr><td style="text-align: center;"><input class="checkbox" type="checkbox"/><input type="hidden" value="'+jsonObj.mealOrderVO.meal_order_no+'"/></td>'
					+	'<td style="text-align: center;">'+ detailVO.meal_name +'</td>'
					+ '	<td style="text-align: center;">'+detailVO.qty+'</td></tr>';
					$("."+jsonObj.mealOrderVO.meal_order_no+"body2").append(row);
			});
				$(".checkbox").change(sendOrder);
				count = document.getElementsByClassName("table").length;
				$("#table-1").find("span").html('目 前 共 有 <font color="red" size="5">' + count  + ' 筆   </font>未 完 成 餐 點 的 訂 單');
		}
// 		$(".checkbox").change(sendOrder);
	}
	
	window.onbeforeunload = function (e) {
		webSocket.close();
	}
	
	webSocket.onclose = function(e){
		console.log('websocket closed');
	}
	
	
	
	
	
	
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