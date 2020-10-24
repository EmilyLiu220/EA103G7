<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.inform_set.model.*"%>
<%@ page import="com.seat_obj.model.*"%>
<%
	SeatObjService seatObjSvc = new SeatObjService();
	List<SeatObjVO> list = seatObjSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<jsp:useBean id="isSvc" scope="page" class="com.inform_set.model.Inform_SetService"></jsp:useBean>
<jsp:useBean id="empSvc" scope="page" class="com.emp.model.EmpService"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>桌位物件管理-select_is.jsp</title>
<link rel=stylesheet type="text/css" href="<%=request.getContextPath()%>/back-end/css/setSeatObj.css">
<link rel=stylesheet type="text/css" href="<%=request.getContextPath()%>/back-end/css/addSeatObjUpload.css">
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
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
						<li><a href="<%=request.getContextPath()%>/back-end/inform_set/select_is.jsp">通知設定管理</a></li>
						<li><a href="#">評價管理</a></li>
						<li><a href="#">用餐時段管理</a></li>
						<li><a href="<%=request.getContextPath()%>/back-end/seat/editSeat.jsp">桌位管理</a></li>
						<li><a href="#">菜單管理</a></li>
						<li><a href="#">食材管理</a></li>
						<li><a href="#">餐點組成管理</a></li>
						<li><a href="#">食材消耗統計</a></li>
						<li><a href="#">紅利商品管理</a></li>
					</ul>
				</li>
				<li><a href="#homeSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">一般員工專區</a>
					<ul class="collapse list-unstyled" id="homeSubmenu">
						<li><a href="<%=request.getContextPath()%>/back-end/front_inform/empCheckInform_New.jsp">查看通知</a></li>
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
											<div id="topLogIn" style="display: inline-block; width: 90px; text-align: center; margin-left: 10px; border-radius: 5px; background: #424242; color: #ccc; cursor: pointer;" onMouseOver="this.style.color='#fff'; this.style.background='#000';" onMouseOut="this.style.color='#ccc'; this.style.background='#424242';"><a href="<%=request.getContextPath()%>/back-end/emp/login.jsp">Log in</a></div>
										</c:when>
										<c:otherwise>
											<div id="topLogOut" style="display: inline-block; width: 90px; text-align: center; margin-left: 10px; border-radius: 5px; background: #424242; color: #ccc; cursor: pointer;" onMouseOver="this.style.color='#fff'; this.style.background='#000';" onMouseOut="this.style.color='#ccc'; this.style.background='#424242';"><a href="">Log out</a></div>
										</c:otherwise>
									</c:choose>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</nav>

			<h5 style="font-weight: 900; display: inline-block;">主管員工專區</h5><span> - 通知設定管理</span>
			<a href="<%=request.getContextPath()%>/back-end/back-index_New.jsp" style="display: inline-block; font-size: 8px; font-weight: 900; color: #dea554; text-decoration: none; margin-left: 20px;" onMouseOver="this.style.color='#ffbc5e';" onMouseOut="this.style.color='#dea554';">返回首頁</a>			
			<p>
				<%-- 查詢通知 --%>
				<table id="table-1">
					<tr>
						<td><h3 style="margin-bottom:0;">上傳桌位物件</h3></td>
					</tr>
				</table>
				<br>
				<ul>
					<%-- 錯誤表列 --%>
					<c:if test="${not empty errorMsgs}">
						<font style="color: red">請修正以下錯誤:</font>
						<ul>
							<c:forEach var="message" items="${errorMsgs}">
								<li style="color: red">${message}</li>
							</c:forEach>
						</ul>
					</c:if>
					<form action="<%=request.getContextPath()%>/seat/Seat_ObjServlet.do" method=post
							enctype="multipart/form-data">
						桌為物件上傳：
						<input type="file" id="myFile" name="seat_obj" multiple /> 
						
						<input class="update" type="SUBMIT" name="submit" value="上傳" />
						<br>
						<div class="row">
							<div id="preview"></div>
						</div>
						<input type="hidden" name="action" value="upload_seat_obj">
					</form>
				</ul>
		
				<%-- 查詢通知 --%>
				<table id="table-1">
					<tr>
						<td><h3 style="margin-bottom:0;">桌位物件設定</h3></td>
					</tr>
				</table>
				<br>

				<table>
					<tr>
						<th	style="text-align: center">桌位物件編號</th>
						<th style="text-align: center">桌位物件</th>
						<th style="text-align: center">桌位人數</th>
						<th style="text-align: center">用途</th>
						<th style="text-align: center">修改</th>
						<th style="text-align: center">刪除</th>
					</tr>
					<jsp:useBean id="map" class="java.util.HashMap"/>
						<c:set target="${map}" property="0" value="座位"/>
						<c:set target="${map}" property="1" value="障礙物"/>
						<c:set target="${map}" property="2" value="背景圖"/>
					<%@ include file="pages/page1.file"%><label>　　　</label>
					<input type="button" value="回桌位設定" onclick="location.href='<%=request.getContextPath()%>/back-end/seat/editSeat.jsp'">
						<c:forEach var="SeatObjVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
							<tr>
							<td>
								${SeatObjVO.seat_obj_no}
							</td>
							<td>
								<c:if test="${not empty SeatObjVO.seat_obj}">
									<img id="table_img" src="<%=request.getContextPath()%>/seat/Seat_ObjServlet.do?seat_obj_no=${SeatObjVO.seat_obj_no}" />
								</c:if> 
								<c:if test="${empty SeatObjVO.seat_obj}">
									<img src="<%=request.getContextPath()%>/images/null.jpg" />
								</c:if>
							</td>
							<td>
<!-- 								<input class="cut" name="" type="button" value="-" disabled />  -->
								<input id="seat_people" class="seat_people" name="seat_people"
											type="text" value="${SeatObjVO.seat_people}" readonly="readonly" style="width: 30px; text-align: center" /> 
<!-- 								<input class="add" name="" type="button" value="+" disabled /> -->
							</td>
							<td>
								<c:forEach  var="item" items="${map}">
									<c:if test="${item.key eq SeatObjVO.seat_use}">
										<c:if test="${item.key eq SeatObjVO.seat_use}">
											${item.value}
										</c:if>
									</c:if>
								</c:forEach>
							</td>
							<td>
								<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/seat/Seat_ObjServlet.do" style="margin-bottom: 0px;">
									<input type="submit" value="修改">
									<input type="hidden" name="action" value="go_update">
									<input type="hidden" name="seat_obj_no" value="${SeatObjVO.seat_obj_no}">
								</FORM>
							</td>
							<td>
								<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/seat/Seat_ObjServlet.do" style="margin-bottom: 0px;">
									<input type="submit" value="刪除">
									<input type="hidden" name="seat_obj_no" value="${SeatObjVO.seat_obj_no}">
									<input type="hidden" name="action" value="delete_use_update">
									<input type="hidden" id="Page" name="Page" value="@ViewBag.Page" />
								</FORM>
							</td>
						</tr>
					</c:forEach>
				</table>
				<%@ include file="pages/page2.file"%>
			</p>	
		</div>
	</div>
    <script src="<%=request.getContextPath()%>/back-end/js/jquery-1.12.4.js"></script>
	<script src="<%=request.getContextPath()%>/back-end/js/jquery-ui-1.12.1.js"></script>
	<script src="<%=request.getContextPath()%>/back-end/js/addSeatObjUpload.js"></script>
	<script src="<%=request.getContextPath()%>/back-end/js/setSeatObj.js"></script>
	<!-- jQuery CDN - Slim version (=without AJAX) -->
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
	<!-- Popper.JS -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"></script>
	<!-- Bootstrap JS -->
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"></script>
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
		$( function() {
			var dateFormat = "mm/dd/yy",
			from = $( "#is_date_startDate" ).datepicker({
				defaultDate: "+1w",
				changeMonth: true,
				numberOfMonths: 1
			}).on( "change", function() {
				to.datepicker( "option", "minDate", getDate( this ) );
			}),
			to = $( "#is_date_stopDate" ).datepicker({
				defaultDate: "+1w",
				changeMonth: true,
				numberOfMonths: 1
			}).on( "change", function() {
				from.datepicker( "option", "maxDate", getDate( this ) );
			});
		 
			function getDate( element ) {
				var date;
				try {
					date = $.datepicker.parseDate( dateFormat, element.value );
				} catch( error ) {
					date = null;
				}
				return date;
			}
		});
	</script>
</body>
</html>