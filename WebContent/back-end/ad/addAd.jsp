<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ad.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	AdVO adVO = (AdVO) request.getAttribute("adVO");
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


<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />

<style>
#table-1, #table-1 td {
	background: #555;
	color: #fff;
	border: 0;
	width: 100%;
	height: 70;
	border-radius: 5px;
	text-align: center;
	box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);
}

img {
	vertical-align: middle;
	border-style: none;
	max-width: 100%;
	max-height: 100%;
}

.xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}

#logout {
	width: 212px;
}

.button {
	width: 100px;
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
						<li><a
							href="<%=request.getContextPath()%>/back-end/emp/login.jsp"
							id="logIn">Log in</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="<%=request.getContextPath()%>/back-end/emp/emp.do?action=logout" id="logOut">Log out</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</nav>

		<div id="content">

			<nav class="navbar navbar-expand-lg navbar-light bg-light">
				<div class="container-fluid">

					<button type="button" id="sidebarCollapse" class="btn btn-dark">
						<svg class="svg-inline--fa fa-align-justify fa-w-14"
							aria-hidden="true" data-prefix="fas" data-icon="align-justify"
							role="img" xmlns="http://www.w3.org/2000/svg"
							viewBox="0 0 448 512" data-fa-i2svg="">
							<path fill="currentColor"
								d="M0 84V44c0-8.837 7.163-16 16-16h416c8.837 0 16 7.163 16 16v40c0 8.837-7.163 16-16 16H16c-8.837 0-16-7.163-16-16zm16 144h416c8.837 0 16-7.163 16-16v-40c0-8.837-7.163-16-16-16H16c-8.837 0-16 7.163-16 16v40c0 8.837 7.163 16 16 16zm0 256h416c8.837 0 16-7.163 16-16v-40c0-8.837-7.163-16-16-16H16c-8.837 0-16 7.163-16 16v40c0 8.837 7.163 16 16 16zm0-128h416c8.837 0 16-7.163 16-16v-40c0-8.837-7.163-16-16-16H16c-8.837 0-16 7.163-16 16v40c0 8.837 7.163 16 16 16z"></path></svg>
						<!-- <i class="fas fa-align-justify"></i> -->
					</button>
					<div id="titleBig"
						style="margin: 0 auto; font-size: 30px; font-weight: 800;">
						<a href="<%=request.getContextPath()%>/back-end/backindex.jsp">吃
							Pot 吧！員工專區</a>
					</div>
					<div id="rwdShow">
						<button type="button" id="topbarCollapse" class="btn btn-dark"
							data-toggle="collapse" data-target="#navbarSupportedContent"
							aria-controls="navbarSupportedContent" aria-expanded="false"
							aria-label="Toggle navigation">
							<svg class="svg-inline--fa fa-align-justify fa-w-14"
								aria-hidden="true" data-prefix="fas" data-icon="align-justify"
								role="img" xmlns="http://www.w3.org/2000/svg"
								viewBox="0 0 448 512" data-fa-i2svg="">
								<path fill="currentColor"
									d="M0 84V44c0-8.837 7.163-16 16-16h416c8.837 0 16 7.163 16 16v40c0 8.837-7.163 16-16 16H16c-8.837 0-16-7.163-16-16zm16 144h416c8.837 0 16-7.163 16-16v-40c0-8.837-7.163-16-16-16H16c-8.837 0-16 7.163-16 16v40c0 8.837 7.163 16 16 16zm0 256h416c8.837 0 16-7.163 16-16v-40c0-8.837-7.163-16-16-16H16c-8.837 0-16 7.163-16 16v40c0 8.837 7.163 16 16 16zm0-128h416c8.837 0 16-7.163 16-16v-40c0-8.837-7.163-16-16-16H16c-8.837 0-16 7.163-16 16v40c0 8.837 7.163 16 16 16z"></path></svg>
							<!-- <i class="fas fa-align-justify"></i> -->
						</button>
						<div id="titleSmall"
							style="padding-left: 10px; font-size: 30px; font-weight: 800;">
							<a href="<%=request.getContextPath()%>/back-end/backindex.jsp">吃
								Pot 吧！員工專區</a>
						</div>
						<div class="collapse navbar-collapse" id="navbarSupportedContent">
							<ul class="nav navbar-nav ml-auto">
								<li class="nav-item active"><a class="nav-link" href="#"
									id="empId" style="cursor: default;"> <c:choose>
											<c:when test="${empVO2.emp_no==null}">
												<span style="color: red; margin-top: 1rem;">嗨，您好！請記得登入喔！</span>
											</c:when>
											<c:otherwise>
												<span>${empVO2.emp_no}&nbsp;&nbsp;&nbsp;${empVO2.emp_name}，您好！</span>
											</c:otherwise>
										</c:choose>
								</a></li>
								<li class="nav-item active"><a class="nav-link"
									href="<%=request.getContextPath()%>/back-end/res_order/orderSeat.jsp">現場劃位</a></li>
								<li class="nav-item active"><a class="nav-link"
									href="<%=request.getContextPath()%>/back-end/wait_seat/listAllWait_seat.jsp">候位管理</a></li>
								<li class="nav-item active"><a class="nav-link"
									href="<%=request.getContextPath()%>/back-end/back-index_m.jsp">主管員工專區</a></li>
								<li class="nav-item active"><a class="nav-link"
									href="<%=request.getContextPath()%>/back-end/back-index_e.jsp">一般員工專區</a></li>
								<li class="nav-item active"
									style="display: block; padding-top: 0.5rem; padding-bottom: 0.5rem;">
									<c:choose>
										<c:when test="${empVO2.emp_no==null}">
											<div id="topLogIn"
												style="display: inline-block; width: 90px; text-align: center; margin-left: 10px; border-radius: 5px; background: #424242; color: #ccc; cursor: pointer;"
												onMouseOver="this.style.color='#fff'; this.style.background='#000';"
												onMouseOut="this.style.color='#ccc'; this.style.background='#424242';">
												<a
													href="<%=request.getContextPath()%>/back-end/emp/login.jsp">Log
													in</a>
											</div>
										</c:when>
										<c:otherwise>
											<div id="topLogOut"
												style="display: inline-block; width: 90px; text-align: center; margin-left: 10px; border-radius: 5px; background: #424242; color: #ccc; cursor: pointer;"
												onMouseOver="this.style.color='#fff'; this.style.background='#000';"
												onMouseOut="this.style.color='#ccc'; this.style.background='#424242';">
												<a
													href="<%=request.getContextPath()%>/back-end/emp/emp.do?action=logout">Log
													out</a>
											</div>
										</c:otherwise>
									</c:choose>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</nav>

			<h5 style="font-weight: 900; display: inline-block;">主管員工專區</h5>
			<span> - 廣告新增</span> <a
				href="<%=request.getContextPath()%>/back-end/ad/select_ad.jsp"
				style="display: inline-block; font-size: 8px; font-weight: 900; color: #dea554; text-decoration: none; margin-left: 20px;"
				onmouseover="this.style.color='#ffbc5e';"
				onmouseout="this.style.color='#DEA554;';">返回上一頁</a>
			<p></p>
			<table id="table-1">
				<tbody>
					<tr>
						<td>
							<h3 style="margin-bottom: 0;">新增廣告設定</h3>
						</td>
					</tr>
				</tbody>
			</table>
			<br>

			<%-- 錯誤表列 --%>

			<c:if test="${not empty errorMsgs}">
				<font style="color: red"></font>
				<ul>
					<c:forEach var="message" items="${errorMsgs}">
						<li style="color: red">${message}</li>
					</c:forEach>
				</ul>
			</c:if>

			<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/ad/ad.do"
				name="form1" enctype="multipart/form-data">
				<br>
				<table class="table table-hover"
					style="width: 100%; font-size: 90%;">

					<!-- 			----------------------------------------------- -->
					<%-- 					<jsp:useBean id="empSvc" scope="page" --%>
					<%-- 						class="com.emp.model.EmpService" /> --%>
					<%-- 					<c:forEach var="empVO" items="${empSvc.all}"> --%>
					<%-- 						<c:if test="${empVO.emp_no==newsVO.emp_no}"> --%>
					<%-- 																	${empVO.emp_name} --%>
					<%-- 																	</c:if> --%>
					<%-- 					</c:forEach> --%>

					<!-- 		-------------------------------------------- -->
					<tbody>
						<!-- 						<tr> -->
						<!-- 							<td>員工編號:<font color="red"><b>*</b></font></td> -->
						<!-- 							<td><input type="TEXT" name="emp_no1" size="45" -->
						<%-- 								value="<%=(adVO == null) ? "" : adVO.getEmp_no()%>" /></td> --%>
						<!-- 						</tr> -->
						<tr>
							<td>廣告標題:<font color="red"><b>*</b></font></td>
							<td><textarea style="width: 410px; height: 50px;"
									id="ad_title" name="ad_title" /><%=(adVO == null) ? "" : adVO.getAd_title()%></textarea></td>
						</tr>
						<tr>
							<td>廣告內容:<font color="red"><b>*</b></font></td>
							<td><textarea style="width: 410px; height: 100px;"
									id="cont" name="ad_cont" /><%=(adVO == null) ? "" : adVO.getAd_cont()%></textarea></td>
						</tr>
						<tr>
							<td>上架日期:<font color="red"><b>*</b></font></td>
							<td><input name="ad_add_date" id="ad_date" type="text"
								 value="<%=(adVO == null) ? "" : adVO.getAd_add_date()%>" /></td>
						</tr>
						<tr>
							<td>下架日期:<font color="red"><b>*</b></font></td>
							<td><input name="ad_re_date" id="re_date" type="text"
								value="<%=(adVO == null) ? "" : ""%>" /></td>
						</tr>
					</tbody>
				</table>
				<!-- 				<div> -->
				<!-- 					廣告圖片:<font color="red"><b>*</b></font> -->
				<!-- 					<input type="file" name="ad_img" id="img"> -->
				<!-- 				</div> -->
				<!-- 				<div id="preview"></div> -->

				<!-- 				<br> <input type="hidden" name="action" value="insert"> -->
				<%-- 				<input type="hidden" name="emp_no" value="${empVO2.emp_no}"> --%>
				<!-- 				<input type="submit" value="送出新增" -->
				<!-- 					style="cursor: pointer; margin-left: 70%; background: rgb(200, 169, 126); color: rgb(255, 255, 255); border: 0px; border-radius: 5px; width: 100px; height: 40px; font-weight: 600;" -->
				<!-- 					onmouseover="this.style.background='#ffbc5e'" -->
				<!-- 					onmouseout="this.style.background='#c8a97e'"> -->


				<div class="form-group">
					<label class="btn btn-secondary info btn-icon-split"> <span
						class="icon text-white-50"><i class="fas fa-image"></i></span> <span
						class="text">請上傳圖片</span> <input type="file" id="img" name=ad_img
						accept="image/gif, image/jpeg, image/png" style="display: none" />
					</label>
					<div id="pic">
						<img id="preview" src="">
					</div>
				</div>

				<br>
				<button type="button" class="btn btn-secondary button"
					onclick="javascript:location.href='<%=request.getContextPath()%>/back-end/ad/select_ad.jsp'">取消</button>
				<button type="submit" class="btn btn-primary button" id="sendMsg"
					onclick="sendMsg();">儲存</button>
				<input type="hidden" name="action" value="insert"> <input
					type="hidden" name="emp_no" value="${empVO2.emp_no}">
				<!-- 						<input type="submit" value="送出新增"> -->
				
			</form>
			<button type="button" id="button" class=""></button>
		</div>
	</div>
	<%
		java.sql.Date getAd_add_date = null;
		try {
			getAd_add_date = adVO.getAd_add_date();
		} catch (Exception e) {
			getAd_add_date = new java.sql.Date(System.currentTimeMillis());
		}
	%>
	<%
		java.sql.Date getAd_re_date = null;
		try {
			getAd_re_date = adVO.getAd_re_date();
		} catch (Exception e) {
			getAd_re_date = new java.sql.Date(System.currentTimeMillis());
		}
	%>

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
	<!-- Font Awesome JS -->
	<script defer
		src="https://use.fontawesome.com/releases/v5.0.13/js/solid.js"
		integrity="sha384-tzzSw1/Vo+0N5UhStP3bvwWPq+uvzCMfrN1fEFe+xBmv1C/AtVX5K0uZtmcHitFZ"
		crossorigin="anonymous"></script>
	<script defer
		src="https://use.fontawesome.com/releases/v5.0.13/js/fontawesome.js"
		integrity="sha384-6OIrr52G08NpOFSZdxxz1xdNSndlD4vdcf/q2myIUVO0VsqaGHJsB0RaBE01VTOY"
		crossorigin="anonymous"></script>
	<script>
		$("#img").change(function() {
			readURL(this);
		});

		function readURL(input) {
			if (input.files && input.files[0]) {
				var reader = new FileReader();
				reader.onload = function(e) {
					$("#preview").attr('src', e.target.result);
				}
				reader.readAsDataURL(input.files[0]);
			}
		}
	</script>

	<link rel="stylesheet" type="text/css"
		href="<%=request.getContextPath()%>/back-end/datetimepicker/jquery.datetimepicker.css" />
	<script
		src="<%=request.getContextPath()%>/back-end/datetimepicker/jquery.js"></script>
	<script
		src="<%=request.getContextPath()%>/back-end/datetimepicker/jquery.datetimepicker.full.js"></script>
	<script>

$.datetimepicker.setLocale('zh'); // kr ko ja en
$(function(){
	 $('#ad_date').datetimepicker({
	  format:'Y-m-d',
	  onShow:function(){
	   this.setOptions({
	    maxDate:$('#re_date').val()?$('#re_date').val():false
	   })
	  },
	  timepicker:false
	 });
	 
	 $('#re_date').datetimepicker({
	  format:'Y-m-d',
	  onShow:function(){
	   this.setOptions({
	    minDate:$('#ad_date').val()?$('#ad_date').val():false
	   })
	  },
	  timepicker:false
	 });
});
</script>

	<div id="fun" style="display: none">
		<c:forEach var="fun_authVO2" items="${fun_authVO2}">
			<span class="fun">${fun_authVO2.fun_name}</span>
			<br>
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
	<script>
var button = document.getElementById("button");
var ad_title = document.getElementById("ad_title");
var ad_cont = document.getElementById("cont");
var ad_add_date = document.getElementById("ad_date");
var ad_re_date = document.getElementById("re_date");

button.addEventListener("click", function(e) {
    e.preventDefault();
    ad_title.value = "歡慶聖誕節";
    ad_cont.value = "🌲We wish you a Merry Christmas 🌲氣溫漸涼，越來越有過聖誕的feel了～❄️熱熱的火鍋湯底、頂級豪華的食材、暖暖的心這個聖誕節讓PoT陪伴你度過吧❤️12月起只要2位大人(含)以上同行，升級豪華套餐即可享✨9折優惠✨哦！(服務費另計)";
    ad_add_date.value = "2020-12-01";
    ad_re_date.value = "2020-12-31";
  
  
});
</script>
</body>
</html>


