<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.inform_set.model.*"%>
<%@ page import="com.meal.model.*"%>
<%@ page import="com.meal_set.model.*"%>

<% 
MealSetVO mealSetVO = (MealSetVO) request.getAttribute("mealSetVO"); 
Map<String, String> errormsgs = (LinkedHashMap) request.getAttribute("errormsgs");

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>訂單管理-listAll</title>
<jsp:useBean id="mealSrv" class="com.meal.model.MealService" />
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
text-decoration: underline;
}
#tbody p{
color:red;
font-weight: bolder;
font-size: 14px;
}
img{
width: 512px;
height: 410px;
}
#add,#remove,#submit{
    font-weight: bolder;
    background: #dea554;
    color: #fff;
    border-radius: 15px;
}
#add:hover,#remove:hover,#submit:hover{
background-color: #ffbc5e;
border: 2px solid darkgray;
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
							<h3 style="margin-bottom:0;">修改套餐內容</h3>
						</td>
					</tr>
				</table>
				<br>
				
<jsp:useBean id="mealCatSrv" class="com.meal_category.model.MealCatService"/>
				<FORM METHOD="post" ACTION="<%= request.getContextPath() %>/meal_set/mealSet.do" enctype="multipart/form-data">
				<table class="table table-hover" style="width: 95%; font-size: 90%;">
					<thead style="text-align: center;">
						<tr>
							<th style="width: 35%;">詳細內容</th>
							<th style="width: 30%;">餐點目前圖片</th>
							<th style="width: 30%;">預覽更新圖片</th>
						</tr>
					</thead>
					<tbody id="tbody">
						<tr>
							<td id="meal-content">
							<b>餐點編號：</b><%= mealSetVO.getMeal_set_no()%><br><br>
							餐點名稱：<input type="TEXT" name="meal_set_name" size="15" value="<%= mealSetVO.getMeal_set_name()%>"/><p>${not empty errormsgs.get("mealSetName")!=null?errormsgs.get("mealSetName"):''}</p>
							
							餐點描述：<textarea  cols="45" rows="5"  maxlength="220" name="meal_set_info" ><%= mealSetVO.getMeal_set_info() %></textarea><p>${not empty errormsgs.get("mealSetInfo")!=null?errormsgs.get("mealSetInfo"):''}</p>
							餐點價格：<input type="TEXT" name="meal_set_price" size="15" value="<%= mealSetVO.getMeal_set_price()%>"/><p>${not empty errormsgs.get("mealSetPrice")!=null?errormsgs.get("mealSetPrice"):''}</p>
							餐點種類：<select name="cat_no">
			 					  <c:forEach var="mealCatVO" items="${mealCatSrv.all}">
								  <option value="${mealCatVO.cat_no}" ${(mealSetVO.cat_no == mealCatVO.cat_no)?"selected":"" }>${mealCatVO.cat_name}
			 					  </c:forEach>
			 					  </select><br><br>
			 				上下架狀態：<select size="1" name="meal_set_sts">
									 <option value="0"  >下架
									 <option value="1"  >上架
									 </select><p></p>
							套餐組成：<button id="add">+</button>&nbsp;&nbsp;<button id="remove">-</button><p></p>
							<p>${not empty errormsgs.get("mealNo")!=null?errormsgs.get("mealNo"):''}</p>
							餐點：<select name="meal_no">
								<c:forEach var="mealVO" items="${mealSrv.all}">
								<option value="${mealVO.meal_no}">${mealVO.meal_name}
								</c:forEach>
								</select>&nbsp;
								<select name="meal_qty">
										<% for(int i =1;i<10;i++){ %>
										<option value="<%= i%>"><%= i%>
										<%} %>
						
								</select>
								<p></p>
								餐點：<select name="meal_no">
								<c:forEach var="mealVO" items="${mealSrv.all}">
								<option value="${mealVO.meal_no}">${mealVO.meal_name}
								</c:forEach>
								</select>&nbsp;
								<select name="meal_qty">
										<% for(int i =1;i<10;i++){ %>
										<option value="<%= i%>"><%= i%>
										<%} %>
						
								</select>
								<p></p>
									
							</td>
							<br>
							<td style="text-align: center;"><img name="meal_set_img" src="<%= request.getContextPath() %>/meal_set/mealSet.showPic?meal_set_img=${mealSetVO.meal_set_no}"/>
															<br><br><input id="upload" type="file" name="meal_set_img" size="45"/></td>
							<td id="preview" style="text-align: center; width:536px;">
															</td>
						</tr>
					</tbody>
				</table>
				<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="meal_set_no" value="<%= mealSetVO.getMeal_set_no()%>">
<input id="submit" type="submit" value="送出修改"></FORM>
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
	
	function init() {
		var upload = document.getElementById("upload");
		upload.addEventListener("change", function(e) {

			var files = e.target.files;
			console.log(files[0])
			if (files && files[0]) {
				var file = files[0];
				if (file.type.indexOf("image") > -1) {
					var reader = new FileReader();
					reader.onload = function(e) {

						var img = document.createElement("img");
						var preview = document.getElementById("preview");
						img.setAttribute("src", e.target.result);
						img.style.width = "512px";
						img.style.height = "410px";
						
						img.classList.add("check");
						preview.appendChild(img);
					}
					reader.readAsDataURL(file);
				} else {
					alert("只能上傳圖片檔");
				}
			}
			var check = document.getElementsByClassName("check");
					if(check.length>0){
						check[0].remove();
					}
		});
		
		var mealContent = document.getElementById("meal-content");
		var remove = document.getElementById("remove");
		remove.addEventListener("click",function(e){
			e.preventDefault();
			$(".newtr:last-child").remove();
			
		});
		var add = document.getElementById("add");
		add.addEventListener("click", function (e) {
		    e.preventDefault();
			
			var tr = document.createElement("tr");
			tr.classList.add("newtr");

		    tr.innerHTML = `
		                       餐點：<select name="meal_no">
			 <c:forEach var="mealVO" items="${mealSrv.all}">
			 <option value="${mealVO.meal_no}">${mealVO.meal_name}
			 </c:forEach>
			 </select>&nbsp;
		    	<select name="meal_qty">
		    	<% for(int i =1;i<10;i++){ %>
		    	 <option value="<%=i %>"><%=i%>
		    	 <%}%>
		    	 </select>
		    	 <p></p>
		`;
		    mealContent.append(tr);
		});

		
		
		
	}
	window.onload = init;
		
	
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