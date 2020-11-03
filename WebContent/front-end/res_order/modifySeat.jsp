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
<jsp:useBean id="timePeriSvc" scope="page" class="com.time_peri.model.TimePeriService" />
<jsp:useBean id="resOrderSvc" scope="page" class="com.res_order.model.ResOrderService" />
<jsp:useBean id="resDetailSvc" scope="page" class="com.res_detail.model.ResDetailService" />
<!DOCTYPE html>
<html>
<head>
<title>吃胖吧～修改訂位</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-end/css/modifySeat.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<style type="text/css">
input, select {
    padding: 5px 0px;
    border: 0 none;
    cursor: pointer;
    border-radius: 5px;
}
</style>
<jsp:include page="/front-end/headfinish.jsp"></jsp:include>
</head>
<body>
	<%
		List<SeatVO> list = seatSvc.getAll();
		TreeSet<Integer> ts = new TreeSet<Integer>();
		for (int i = 0; i < list.size(); i++) {
			ts.add(list.get(i).getSeat_f());
		}
		pageContext.setAttribute("ts", ts);
	%>
	<form method="post" action="<%=request.getContextPath()%>/res_order/ResOrderServlet.do">
		<input type="hidden" id="res_no" name="res_no" value="${param.res_no}">
		<input type="hidden" id="res_people" value="${param.res_people}">
		<div class="container" id="modifySeatCondition">
			<table class="table table-striped table-hover mx-auto w-auto">
				<tr>
					<th rowspan="2" style="color: red; font-weight:bold; vertical-align:middle;">原訂位資訊</th>
					<th>桌位</th>
					<th>預約訂位日期</th>
					<th>用餐時段</th>
					<th>人數</th>
					<th rowspan="2" style="font-weight:bold; vertical-align:middle;">
						<button type="submit" class="btn btn-primary" name="action" value="return_former_page" onclick="location.href='<%=request.getContextPath()%>/res_order/ResOrderServlet.do?requestURL=${param.requestURL}&whichPage=${param.whichPage}'">返回</button>
					</th>
				</tr>
				<tr>
					<td style="vertical-align:middle;">
						<c:forEach var="resDetailVO" items="${resDetailSvc.getAllResNO(param.res_no)}">
							${seatSvc.getOneSeat(resDetailVO.seat_no).seat_f}樓_${seatSvc.getOneSeat(resDetailVO.seat_no).seat_name}桌<br> 
						</c:forEach>
					</td>
					<td style="vertical-align:middle;">${resOrderSvc.getOneResOrder(param.res_no).res_date}</td>
					<td style="vertical-align:middle;">${timePeriSvc.getOneTimePeri(resOrderSvc.getOneResOrder(param.res_no).time_peri_no).time_start}</td>
					<td style="vertical-align:middle;">${param.res_people}</td>
				</tr>
			</table>
			<select id="floor_list" name="floor_list">
				<c:forEach var="seat_f" items="${ts}">
					<option class="lt" value="${seat_f}">${seat_f}樓座位區</option>
				</c:forEach>
			</select>
				預定日期: 
				<input name="res_date" id="res_date" type="text" value="--請選擇日期--" onfocus="this.blur()"> 
			<label class="labelOne"> 
				選擇時段: 
				<select id="time_peri_no" name="time_peri_no" >
						<option class="lt" value="-1">--請先選擇日期--</option>
				</select> 
				<input type="hidden" name="mem_no" value="${memVO2.mem_no}"> <input type="hidden" name="emp_no" value="${empVO2.emp_no}">
			</label>
			<label class="labelTwo"> 
				用餐人數: 
				<input id="people" type="number" min="1" max="60" name="people" placeholder="請輸入用餐人數">人
			</label>
			<input type="hidden" name="action" value="modify_seat">
			<input type="hidden" name="requestURL" value="${param.requestURL}">
			<input type="hidden" name="whichPage" value="${param.whichPage}">
			<button id="modifySeat" class="btn btn-primary" onclick='return false;'>修改訂位</button>
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
	<footer>
		<jsp:include page="/front-end/footer.jsp"></jsp:include>
	</footer>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>
<script src="<%=request.getContextPath()%>/front-end/js/sweetalert.min.js"></script>
<script src="<%=request.getContextPath()%>/front-end/js/modifySeat.js"></script>
</body>
</html>