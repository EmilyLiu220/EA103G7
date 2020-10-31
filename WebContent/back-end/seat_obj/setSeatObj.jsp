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
.seatSeatObjDiv {
	position: relative;
	z-index: 999;
	top: 70px;
}
</style>

</head>
<body>
	<div class="wrapper">
		<!-- Page Content  -->
		<div id="content">

			<div class="seatSeatObjDiv">

			<p>
				<%-- 查詢通知 --%>
				<table id="table-1">
					<tr>
						<td><h3 style="margin-bottom:0;">上傳桌位物件</h3></td>
					</tr>
				</table>
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
				</ul>
				<form action="<%=request.getContextPath()%>/seat/Seat_ObjServlet.do" method=post enctype="multipart/form-data">
					桌為物件上傳：
					<input type="file" id="myFile" name="seat_obj" multiple /> 
					
					<input class="update" type="SUBMIT" name="submit" value="上傳" />
					<br>
					<div class="row">
						<div id="preview"></div>
					</div>
					<input type="hidden" name="action" value="upload_seat_obj">
				</form>
				
				<%-- 查詢通知 --%>
				<table id="table-1">
					<tr>
						<td><h3 style="margin-bottom:0;">桌位物件設定</h3></td>
					</tr>
				</table>
				<br>
				<input type="button" value="回桌位設定" onclick="location.href='<%=request.getContextPath()%>/back-end/seat/editSeat.jsp'">
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
	</div>
	<jsp:include page="/back-end/siderbar/siderbar.jsp" />
	<script src="<%=request.getContextPath()%>/back-end/js/addSeatObjUpload.js"></script>
	<script src="<%=request.getContextPath()%>/back-end/js/setSeatObj.js"></script>
</body>
</html>