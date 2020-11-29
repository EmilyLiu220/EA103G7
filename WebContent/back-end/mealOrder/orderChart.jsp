<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.stream.Collectors"%>
<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.res_order.model.*"%>
<%@ page import="com.inform_set.model.*"%>
<%@ page import="com.meal.model.*"%>
<%@ page import="com.meal_set.model.*"%>
<%@ page import="com.meal_order.model.*"%>
<%@ page import="com.meal_order_detail.model.*"%>

<% 
	MealOrderService mealOrderSrv = new MealOrderService();
	List<MealOrderVO> list = mealOrderSrv.searchToday(new Date());
	MealOrderDetailService detailSrv = new MealOrderDetailService();
	MealService mealSrv = new MealService();
	MealSetService mealSetSrv = new MealSetService();
	ResOrderService resOrderSrv = new ResOrderService();
	int nop = 0;
	
// 	for(ResOrderVO resOrderVO : resOrderSrv.getAll()){
// 		for(MealOrderVO mealOrderVO : list ){
// 			if(resOrderVO.getSeat_sts() == 1 && resOrderVO.getMeal_order_no().equals(mealOrderVO.getMeal_order_no())){
// 				nop += resOrderVO.getPeople();
// 			}else{
// 				nop += 1;
// 			}
// 		}
// 	}

	Map<String, Object> mealMap = mealSrv.getAll2().stream()
			.collect(Collectors.toMap(MealVO::getMeal_no, m -> m));
	Map<String, Object> mealSetMap = mealSetSrv.getAll().stream()
			.collect(Collectors.toMap(MealSetVO::getMeal_set_no, ms -> ms));
	Set<String> mealKeys = mealMap.keySet();
	Set<String> mealSetKeys = mealSetMap.keySet();
	int amount = 0;
	int qty = 0;
	
	for (String key : mealKeys) {
		((MealVO) mealMap.get(key)).setMeal_qty(0);
	}
	for (String key : mealSetKeys) {
		((MealSetVO) mealSetMap.get(key)).setMeal_set_qty(0);
	}

	for (MealOrderVO mealOrderVO : list) {
		amount += mealOrderVO.getAmount();
		for (MealOrderDetailVO detailVO : detailSrv.searchByOrderNo(mealOrderVO.getMeal_order_no())) {
			for (String key : mealKeys) {
				if (key.equals(detailVO.getMeal_no())) {
					qty += ((MealVO) mealMap.get(key)).getMeal_qty() + detailVO.getQty();
					((MealVO) mealMap.get(key))
							.setMeal_qty(((MealVO) mealMap.get(key)).getMeal_qty() + detailVO.getQty());
				}
			}
			for (String key : mealSetKeys) {
				if (key.equals(detailVO.getMeal_set_no())) {
					qty += ((MealSetVO) mealSetMap.get(detailVO.getMeal_set_no())).getMeal_set_qty()
							+ detailVO.getQty();
					((MealSetVO) mealSetMap.get(detailVO.getMeal_set_no())).setMeal_set_qty(
							((MealSetVO) mealSetMap.get(detailVO.getMeal_set_no())).getMeal_set_qty()
									+ detailVO.getQty());
				}
			}
		}
	}
	
	Map<String, Map<String, Object>> jsonMap = new HashMap<>();
	jsonMap.put("mealMap", mealMap);
	jsonMap.put("mealSetMap", mealSetMap);
	Gson gson = new Gson();
	String jsondata = gson.toJson(jsonMap);
	
	pageContext.setAttribute("list", list);
	pageContext.setAttribute("amount", amount);
	pageContext.setAttribute("qty", qty);
// 	pageContext.setAttribute("nop", nop);
	pageContext.setAttribute("jsondata",jsondata);
	
	
	boolean isPdf = request.getParameter("pdf") != null;
	String contextPath = request.getContextPath();
	String realPath = request.getServletContext().getRealPath("/");
  
	String imageRoot = isPdf ? realPath + "/image" : contextPath + "/image";
	String cssFile = isPdf ? realPath + "/css/pdf.css" : contextPath + "/css/web.css";

%>
<% if (!isPdf) {%>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>訂單管理-listAll</title>
<jsp:useBean id="empSvc" scope="page" class="com.emp.model.EmpService"></jsp:useBean>
<jsp:useBean id="mealOrderSrv2" scope="page" class="com.meal_order.model.MealOrderService"/>
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
}
.table a:hover{
color:lightblue;
text-decoration: underline;
}
.chart-container,.chart-container2{
height: 480px;
width: 100%;
display: block;
}
.chart-content,.chart-content2{
height:150px;
max-width:100%;
margin-left: 20px;
}
canvas{
display: block;
}
.chart-content .col-4,.chart-content2 .col-4{
background-color: rgba(0, 0, 0, 0.9);
height: 40px;
color:#dea554;
font-weight: bolder;
border: 1px solid #fff;
}
.chart-content span,.chart-content2 span{
color:white;
}
#submit,#download{
    font-weight: bolder;
    background: #dea554;
    color: #fff;
    border-radius: 15px;
}
#submit:hover,#download:hover{
background-color: #ffbc5e;
border: 2px solid darkgray;
}
.container{
height: auto;
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

			<h5 style="font-weight: 900; display: inline-block;">一般員工專區</h5><span> - 訂餐訂單管理</span>
			<a href="<%=request.getContextPath()%>/back-end/backindex.jsp" style="display: inline-block; font-size: 8px; font-weight: 900; color: #dea554; text-decoration: none; margin-left: 20px;" onMouseOver="this.style.color='#ffbc5e';" onMouseOut="this.style.color='#dea554';">返回首頁</a>			
			<p>
				<table id="table-1">
					<tr>
						<td>
							<h3 style="margin-bottom:0;">查看所有訂餐訂單</h3>
						</td>
					</tr>
				</table>
				<br>
				<form action="<%= request.getContextPath()%>/MealOrderServlet.do" method="POST">
				<table id="table-2">
				<tr class="row query">
					<td class="col">訂單編號：<input type="text" name="meal_order_no" size="10"/></td>
					<td class="col">員工編號：<input type="text" name="emp_no" size="10"/></td>
					<td class="col">會員編號：<input type="text" name="mem_no" size="10"/></td>
				</tr>
				<tr class="row query">
					<td class="col">通知狀態：<select name="noti_sts">
			 			<option value=''>請選擇
			 			<option value=0>未通知
			 			<option value=1>已通知
						</select></td>
				<td class="col">付款狀態：<select name="pay_sts">
			 			<option value=''>請選擇
			 			<option value=0>未付款
			 			<option value=1>已付款
						</select></td>
				<td class="col">訂單狀態：<select name="meal_order_sts">
			 			<option value=''>請選擇
			 			<option value=0>已取消
			 			<option value=1>未派工
			 			<option value=2>已派工
			 			<option value=3>已出餐
			 			<option value=4>已完成
						</select></td>
						</tr>
	<tr class="row query">
				<td class="col">
				訂餐時間：<input type="text" name="order_time" class="f_date1"/>
				至 <input type="text" name="order_time" class="f_date1"/> 之間</td>
				</tr>
	<tr class="row query">			
				<td class="col">
				取餐時間：<input type="text" name="pickup_time" class="f_date1"/>
				至 <input type="text" name="pickup_time" class="f_date1"/> 之間</td>
				<td>
				<input id="submit" type="button" value="查詢結果"/>
				&nbsp;&nbsp;&nbsp;
				<button id="download">匯出PDF</button>
<%-- 				<a href="<%= request.getContextPath()%>/MealOrderServlet.do?"><button id="pdf">匯出PDF</button></a> --%>
				<input type="hidden" name="action" value="orderChart"/></td>
				</tr>
				
				</table>
				</form>
				<br>
				<div id="container" class="container">
				<div class="chart-container">
				<canvas id="myChart"></canvas>
				</div>
				<br>
				<br>
				<br>
				<div class="row chart-content justify-content-around">
					<div class="col">
						<div class="row">
						<div class="col-4">總銷售額：<span>${amount}&nbsp;元</span></div>
						<div class="col-4">總銷售量：<span>${qty}&nbsp;個</span></div>
						<div class="col-4">訂單數量：<span>${list.size()}&nbsp;筆</span></div>
						</div>
						<div class="row">
						<fmt:parseNumber  parseLocale="#" integerOnly="true" value="${amount !=0 and list.size()!=0?amount/list.size():''}" var="result" />
						<div class="col-4">平均訂單金額：<span><c:out value="${result}"/>&nbsp;/&nbsp;筆</span></div>
						<div class="col-4">平均客單價：<span>&nbsp;/&nbsp;人</span></div>
						</div>
					</div>
				
				</div>
				
				<div class="chart-container2">
				<canvas id="myChart2"></canvas>
				</div>
				<br>
				<br>
				<br>
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
	<script src="https://cdn.jsdelivr.net/npm/chart.js@2.9.4/dist/Chart.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.3.3/jspdf.min.js"></script>
<%-- <script src="<%=request.getContextPath()%>/front-end/datetimepicker/jquery.js"></script> --%>
<script src="<%=request.getContextPath()%>/front-end/datetimepicker/jquery.datetimepicker.full.js"></script>
<script src="https://cdn.jsdelivr.net/gh/emn178/chartjs-plugin-labels/src/chartjs-plugin-labels.js"></script>
<script src="https://cdn.bootcss.com/html2canvas/0.5.0-beta4/html2canvas.js"></script>
<script src="https://cdn.bootcss.com/jspdf/1.3.4/jspdf.debug.js"></script>
	
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
// 		$('#pdf').click(function(e){
// 			e.preventDefault();
// 		});
		
		var now = new Date();
// 		var expiry = new Date(now.getFullYear(),(now.getMonth()+1),now.getDay());
        $.datetimepicker.setLocale('zh');
        $('.f_date1').datetimepicker({
           todayHighlight: true,
	       theme: 'dark',              //theme: 'dark',
	       timepicker:true,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d H:i',         //format:'Y-m-d H:i:s',
// 	       showApplyButton: true,
		   value: 0,  // value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
//            roundTime: 'round',
//            minTime:new Date(now.getFullYear(),(now.getMonth()+1),now.getDate(),now.getHours,(now.getMinutes()+30)),
//            minDate:               new Date(), // 去除今日(不含)之前
//            maxDate:               new Date(now.getFullYear(),(now.getMonth()+1),now.getDate())  // 去除今日(不含)之後
        });
        
//         function getRandomColor() {
//         	  var letters = '0123456789ABCDEF';
//         	  var color = '#';
//         	  for (var i = 0; i < 6; i++) {
//         	    color += letters[Math.floor(Math.random() * 16)];
//         	  }
        	  
//         	  return color;
//         	}
        
        function random_bg_color() {
            var x = Math.floor(Math.random() * 155+100);
            var y = Math.floor(Math.random() * 155+100);
            var z = Math.floor(Math.random() * 155+100);
            var bgColor = "rgb(" + x + "," + y + "," + z + ",0.5)";
            return bgColor;
            }
        

        Chart.defaults.global.defaultFontSize = 18;
        
        $("#submit").click(function(e){
        	e.preventDefault();
        	console.log($('#table-2').find('input[name="order_time"]').length);
        	console.log($($('#table-2').find('input[name="order_time"]')[0]).val());
        	console.log($($('#table-2').find('input[name="order_time"]')[1]).val());
        	var orderTime = [];
        	orderTime.push($($('#table-2').find('input[name="order_time"]')[0]).val());
        	orderTime.push($($('#table-2').find('input[name="order_time"]')[1]).val());
        	var pickupTime = [];
        	pickupTime.push($($('#table-2').find('input[name="pickup_time"]')[0]).val());
        	pickupTime.push($($('#table-2').find('input[name="pickup_time"]')[1]).val());
        	console.log(orderTime);
        	$.ajax({
                url: "${pageContext.request.contextPath}/MealOrderServlet.do",
                type: "POST",
                data: {
                    action: 'orderChart',
                    meal_order_no:$('#table-2').find('input[name="meal_order_no"]').val(),
                    mem_no:$('#table-2').find('input[name="mem_no"]').val(),
                    emp_no:$('#table-2').find('input[name="emp_no"]').val(),
                    noti_sts:$('#table-2').find('select[name="noti_sts"]').val(),
                    pay_sts:$('#table-2').find('select[name="pay_sts"]').val(),
                    meal_order_sts:$('#table-2').find('select[name="meal_order_sts"]').val(),
                    order_time:JSON.stringify(orderTime),
                    pickup_time:JSON.stringify(pickupTime)
                    
                },
                dataType: "JSON",
                success: function (e) {
                	console.log(typeof(e));
                	console.log(Object.keys(e.mealMap).length);
                	var mealLen = Object.keys(e.mealMap).length;
                	var setLen = Object.keys(e.mealSetMap).length;
                	console.log(mealLen);
                	console.log(setLen);
                	var mealKeys = Object.keys(e.mealMap);
                	var setKeys = Object.keys(e.mealSetMap);
                	console.log(mealKeys[0]);
                	var bgcolor = [];
                	var dataPrice = [];
                	var dataQty =[];
                	var dataset =[]; 
                	var amount = 0;
                	var qty = 0;
                		for(let i = 0;i < mealLen;i++){
                		let keys = mealKeys[i];
                		console.log(e.mealMap[mealKeys[i]].meal_name);
                		dataset.push(e.mealMap[mealKeys[i]].meal_name);
                		console.log(e.mealMap[mealKeys[i]].meal_qty);
                		dataQty.push(e.mealMap[mealKeys[i]].meal_qty);
                		qty += e.mealMap[mealKeys[i]].meal_qty;
                		console.log(e.mealMap[mealKeys[i]].meal_qty * e.mealMap[mealKeys[i]].meal_price);
                		dataPrice.push(e.mealMap[mealKeys[i]].meal_qty * e.mealMap[mealKeys[i]].meal_price);
                		amount += e.mealMap[mealKeys[i]].meal_qty * e.mealMap[mealKeys[i]].meal_price;
                		bgcolor.push(random_bg_color());
                		
                		}
                		console.log(dataset);
                		for(let i = 0;i < setLen;i++){
                			dataset.push(e.mealSetMap[setKeys[i]].meal_set_name);
                			dataQty.push(e.mealSetMap[setKeys[i]].meal_set_qty);
                			qty += e.mealMap[mealKeys[i]].meal_qty;
                			dataPrice.push(e.mealSetMap[setKeys[i]].meal_set_qty * e.mealSetMap[setKeys[i]].meal_set_price);
                			amount += e.mealMap[mealKeys[i]].meal_qty * e.mealMap[mealKeys[i]].meal_price;
                			bgcolor.push(random_bg_color());
                		}
                		console.log(dataset);
                		$(".chart-container").empty();
                		$(".chart-container2").empty();
                	var canvas = document.createElement("canvas");
                	var canvas2 = document.createElement("canvas");
                	$(canvas).attr('id','myChart');
                	$(canvas2).attr('id','myChart2');
                	$(".chart-container").append(canvas);
                	$(".chart-container2").append(canvas2);
                	
                	$($(".chart-content").find("span")[0]).text(amount + ' 元');
                	$($(".chart-content").find("span")[1]).text(qty + ' 份');
                	$($(".chart-content").find("span")[2]).text(e.orderList['orderList'] + ' 筆');
                	$($(".chart-content").find("span")[3]).text(Math.round((amount/e.orderList['orderList'])) + ' 元/筆');
                	$($(".chart-content").find("span")[4]).text(Math.round((amount/e.orderList['ranNumPeople'])) + ' 元/人');
                	
                	
                	var ctx = document.getElementById('myChart').getContext('2d');
                    var myChart = new Chart(ctx, {
                        type: 'pie',
                        data: {
                            labels: dataset,
                            datasets: [{
                                label: '銷售總量',
                                data: dataQty,
                                fill: false,
                                backgroundColor:bgcolor,
                                borderColor: bgcolor,
                                borderWidth: 3
                            }],
                           
                        },
                        options: {
                        	responsive: true,
                            maintainAspectRatio: false,
                        	plugins:{
                        		labels: [{
                        		render: 'label',
                        		fontColor:'black',
                        		position: 'outside',
                        		fontSize: 14,
                        		outsidePadding: 2,
                        		arc: true,
                        		},{
                        		render:'percentage',
                        		fontColor:'black',
                        		precision: 2,
                        		fontSize: 14,
                        		}]
                        		}
//                             scales: {
//                                 yAxes: [{
//                                     ticks: {
//                                     	stepSize: 30,
//                                         beginAtZero: true
//                                     }
//                                 }]
//                             }
                        }
                    });
                    
                    var ctx2 = document.getElementById('myChart2').getContext('2d');
                    var myChart2 = new Chart(ctx2, {
                        type: 'bar',
                        data: {
                            labels: dataset,
                            datasets: [{
                                label: '銷售總額',
                                data: dataPrice,
                                fill: false,
                                backgroundColor:bgcolor,
                                borderColor:bgcolor,
                                borderWidth: 3
                            }],
                           
                        },
                        options: {
//                         	responsive: true,
                            maintainAspectRatio: false,
                        	plugins:{
                        		labels: {
                        		render: 'value',
                        		fontColor:'black',
                        		arc: true,
                        		}
                        		},
                            scales: {
                                yAxes: [{
                                    ticks: {
                                    	stepSize: 500,
                                        beginAtZero: true
                                    }
                                }]
                            }
                        }
                    });	
                		
                		
                		
                		
                		
                }
            });
	        	
        	
        	
        })
        
        var jsonObj = ${jsondata};
        console.log(jsonObj);
        var todayAmount = 0;
        var todayQty = 0;
		var todaybgcolor =[];
        var todayLabels = [];
        var todayDataPrice = [];
    	var todayDataQty =[];
        var todayMealKeys = Object.keys(jsonObj.mealMap);
    	var todaySetKeys = Object.keys(jsonObj.mealSetMap);
        var todayMealLen = Object.keys(jsonObj.mealMap).length;
    	var todaySetLen = Object.keys(jsonObj.mealSetMap).length;
        
    	for(let i = 0;i < todayMealLen;i++){
    		let keys = todayMealKeys[i];
    		todayLabels.push(jsonObj.mealMap[todayMealKeys[i]].meal_name);
    		todayDataQty.push(jsonObj.mealMap[todayMealKeys[i]].meal_qty);
    		todayQty += jsonObj.mealMap[todayMealKeys[i]].meal_qty;
    		todayDataPrice.push(jsonObj.mealMap[todayMealKeys[i]].meal_qty * jsonObj.mealMap[todayMealKeys[i]].meal_price);
    		todayAmount += jsonObj.mealMap[todayMealKeys[i]].meal_qty * jsonObj.mealMap[todayMealKeys[i]].meal_price;
    		todaybgcolor.push(random_bg_color());
    		}
    		console.log(todayLabels);
    		for(let i = 0;i < todaySetLen;i++){
    			todayLabels.push(jsonObj.mealSetMap[todaySetKeys[i]].meal_set_name);
    			todayDataQty.push(jsonObj.mealSetMap[todaySetKeys[i]].meal_set_qty);
    			todayDataPrice.push(jsonObj.mealSetMap[todaySetKeys[i]].meal_set_qty * jsonObj.mealSetMap[todaySetKeys[i]].meal_set_price);
    			todaybgcolor.push(random_bg_color());
    		}
        
        var ctx = document.getElementById('myChart').getContext('2d');
        var myChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: todayLabels,
                datasets: [{
                    label: '今日營業額',
                    data: todayDataPrice,
                    fill: false,
                    backgroundColor:todaybgcolor,
                    borderColor:todaybgcolor,
                    borderWidth: 3
                }],
               
            },
            options: {
//             	responsive: true,
                maintainAspectRatio: false,
            	plugins:{
            		labels: {
            		render: 'value',
            		fontColor:'black',
            		arc: true,
            		}
            		},
                scales: {
                    yAxes: [{
                        ticks: {
                        	stepSize: 500,
                            beginAtZero: true
                        }
                    }]
                }
            }
        });
        
        var ctx2 = document.getElementById('myChart2').getContext('2d');
        var myChart2 = new Chart(ctx2, {
            type: 'pie',
            data: {
                labels: todayLabels,
                datasets: [{
                    label: '銷售總量',
                    data: todayDataQty,
                    fill: false,
                    backgroundColor:todaybgcolor,
                    borderColor: todaybgcolor,
                    borderWidth: 3
                }],
               
            },
            options: {
            	responsive: true,
                maintainAspectRatio: false,
            	plugins:{
            		labels: [{
            		render: 'label',
            		fontColor:'black',
            		position: 'outside',
            		fontSize: 14,
            		outsidePadding: 2,
            		arc: true,
            		},{
            		render:'percentage',
            		fontColor:'black',
            		precision: 2,
            		fontSize: 14,
            		}]
            		}
//                 scales: {
//                     yAxes: [{
//                         ticks: {
//                         	stepSize: 30,
//                             beginAtZero: true
//                         }
//                     }]
//                 }
            }
        });
        
//         download.addEventListener("click", function (e) {
//         	e.preventDefault();
//             var imgData = document.getElementById('container').toDataURL("image/jpeg", 1.0);
//             var pdf = new jsPDF();

//             pdf.addImage(imgData, 'JPEG', 0, 0);
//             pdf.save("download.pdf");
//         }, false);


download.addEventListener("click",function (e) {
	e.preventDefault();
  var target = document.getElementsByClassName("container")[0];
  target.style.background = "#FFFFFF";

  html2canvas(target, {
    onrendered:function(canvas) {
        var contentWidth = canvas.width;
        var contentHeight = canvas.height;

        //一頁pdf顯示html頁面生成的canvas高度;
        var pageHeight = contentWidth / 592.28 * 841.89;
        //未生成pdf的html頁面高度
        var leftHeight = contentHeight;
        //頁面偏移
        var position = 0;
        //a4紙的尺寸[595.28,841.89]，html頁面生成的canvas在pdf中圖片的寬高
        var imgWidth = 595.28;
        var imgHeight = 592.28/contentWidth * contentHeight;

        var pageData = canvas.toDataURL('image/jpeg', 1.0);

        var pdf = new jsPDF('', 'pt', 'a4');

        //有兩個高度需要區分，一個是html頁面的實際高度，和生成pdf的頁面高度(841.89)
        //當內容未超過pdf一頁顯示的范圍，無需分頁
        if (leftHeight < pageHeight) {
        pdf.addImage(pageData, 'JPEG', 0, 0, imgWidth, imgHeight );
        } else {
            while(leftHeight > 0) {
                pdf.addImage(pageData, 'JPEG', 0, position, imgWidth, imgHeight)
                leftHeight -= pageHeight;
                position -= 841.89;
                //避免添加空白頁
                if(leftHeight > 0) {
                  pdf.addPage();
                }
            }
        }

        pdf.save("content.pdf");
    }
  })
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
<%
	}else{
%>





<% }%>