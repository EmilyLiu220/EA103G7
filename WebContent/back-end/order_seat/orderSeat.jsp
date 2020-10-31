<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.time_peri.model.*"%>
<%@ page import="com.seat.model.*"%>
<%@ page import="javax.servlet.*,java.text.*"%>
<%
	session.setAttribute("insert", "insert");
%>
<jsp:useBean id="seatSvc" scope="page" class="com.seat.model.SeatService" />
<%
	List<SeatVO> list = seatSvc.getAll();
	TreeSet<Integer> ts = new TreeSet<Integer>();
	for (int i = 0; i < list.size(); i++) {
		ts.add(list.get(i).getSeat_f());
	}
	pageContext.setAttribute("ts", ts);
%>
<!DOCTYPE html>
<html>
<head>
<title>吃胖吧～訂位</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/back-end/css/orderSeat.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<style type="text/css">
input, select {
    padding: 5px 0px;
    border: 0 none;
    cursor: pointer;
    border-radius: 5px;
}
</style>
</head>
<body>
	<div class="wrapper">
		<!-- Page Content  -->
		<div id="content">
			<div class="orderSeatDiv">
				<table id="table-1">
					<tr>
						<td><h3 style="margin-bottom:0;">訂位查詢</h3></td>
					</tr>
				</table>
				<div>
					條件一：<input type="text" >　條件二：<input type="text" >　條件三：<input type="text" ><br>
					條件四：<input type="text" >　條件五：<input type="text" >　條件五：<input type="text" ><br>
					條件六：<input type="text" >　條件七：<input type="text" >　條件八：<input type="text" ><br>
				</div>
				<table id="table-1">
					<tr>
						<td><h3 style="margin-bottom:0;">訂位狀況</h3></td>
					</tr>
				</table>
				<form method="post" action="<%=request.getContextPath()%>/res_order/ResOrderServlet.do">
					<div class="container" id="orderSeatCondition">
						<select id="floor_list" name="floor_list">
							<c:forEach var="seat_f" items="${ts}">
								<option class="lt" value="${seat_f}">${seat_f}樓座位區</option>
							</c:forEach>
						</select>
							預定日期: 
							<input name="res_date" id="res_date" type="text" value="--請選擇日期--" onfocus="this.blur()"> 
						<label class="labelOne"> 
							選擇時段: 
							<jsp:useBean id="timePeriSvc" scope="page" class="com.time_peri.model.TimePeriService" /> 
							<select id="time_peri_no" name="time_peri_no" >
									<option class="lt" value="-1">--請先選擇日期--</option>
							</select> 
							<input type="hidden" name="mem_no" value="${memVO2.mem_no}"> <input type="hidden" name="emp_no" value="${empVO2.emp_no}">
						</label>
						<label class="labelTwo"> 
							用餐人數: 
							<input id="people" type="number" min="1" max="60" name="people" placeholder="請輸入用餐人數">人
						</label>
						<input type="hidden" name="action" value="order_seat">
						<button id="orderSeat" name="action" value="order_seat" class="btn btn-warning" onclick='return false;'>訂位</button>
					</div>
					<div id="container" class="container">
						<c:forEach var="seatVO" items="${seatSvc.all}">
							<c:if test="${seatVO.seat_f == 1 }">
								<div class="drag" style="position: absolute; left: ${seatVO.seat_x}px; top: ${seatVO.seat_y}px;" id="drag">
									<label> 
										<input type="checkbox" class="myCheckbox" name="seat_checked" value="${seatVO.seat_no}"> 
										<img src="<%=request.getContextPath()%>/seat/Seat_ObjServlet.do?seat_obj_no=${seatVO.seat_obj_no}">
									</label> 
									<label class="seatLabel">
										<input type="text" class="seatName" name="seatName" disabled value="${seatVO.seat_name}">
									</label>
								</div>
							</c:if>
						</c:forEach>
					</div>
				</form>
			</div>
		</div>
	</div>
<script src="<%=request.getContextPath()%>/back-end/js/jquery-1.12.4.js"></script>
<jsp:include page="/back-end/siderbar/siderbar_for_editSeat.jsp" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>
<script src="<%=request.getContextPath()%>/front-end/js/sweetalert.min.js"></script>
<script src="<%=request.getContextPath()%>/back-end/js/orderSeat.js"></script>
</body>
</html>