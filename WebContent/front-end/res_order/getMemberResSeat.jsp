<%@page import="com.time_peri.model.TimePeriService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.res_order.model.*"%>
<%
	ResOrderService resOrderSvc = new ResOrderService();
	List<ResOrderVO> list = resOrderSvc.getOneMemberResOrder("MEM0010");
	pageContext.setAttribute("list", list);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Order Seat</title>
<link rel=stylesheet type="text/css" href="<%=request.getContextPath()%>/front-end/css/getMemberResSeat.css">
<jsp:include page="/front-end/headfinish.jsp"></jsp:include>
</head>
<body>
<div class="container">
<table class="table table-striped table-hover mx-auto w-auto">
	<tr>
		<th>樓層</th>
		<th>桌位</th>
		<th>訂餐編號</th>
<!-- 		<th>會員編號</th> -->
<!-- 		<th>員工編號</th> -->
<!-- 		<th>訂單成立時間</th> -->
		<th>預約訂位日期</th>
		<th>用餐時段</th>
		<th>人數</th>
		<th>訊息狀態</th>
		<th>入座狀態</th>
		<th>修改座位</th>
		<th>取消訂位</th>
	</tr>
	<%@ include file="pages/page1.file"%>
	<jsp:useBean id="map" class="java.util.HashMap"/>
		<c:set target="${map}" property="0" value="未發送"/>
		<c:set target="${map}" property="1" value="已發送未確認"/>
		<c:set target="${map}" property="2" value="已發送已確認"/>
		<c:set target="${map}" property="3" value="取消訂位"/>
	<jsp:useBean id="map1" class="java.util.HashMap"/>
		<c:set target="${map1}" property="0" value="未入座"/>
		<c:set target="${map1}" property="1" value="已入座"/>
	<jsp:useBean id="timePeriSvc" scope="page" class="com.time_peri.model.TimePeriService" />
	<jsp:useBean id="resDetailSvc" scope="page" class="com.res_detail.model.ResDetailService" />
	<jsp:useBean id="seatSvc" scope="page" class="com.seat.model.SeatService" />
	<c:forEach var="resOrderVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr>
			<td>
				<c:forEach var="resDetailVO" items="${resDetailSvc.getAllResNO(resOrderVO.res_no)}">
					${seatSvc.getOneSeat(resDetailVO.seat_no).seat_f}樓
				</c:forEach>
			</td>
			<td>
				<c:forEach var="resDetailVO" items="${resDetailSvc.getAllResNO(resOrderVO.res_no)}">
					${seatSvc.getOneSeat(resDetailVO.seat_no).seat_name}桌 
				</c:forEach>
			</td>
			<td>
				<c:if test="${not empty resOrderVO.meal_order_no}">
					${resOrderVO.meal_order_no}
				</c:if> 
				<c:if test="${empty resOrderVO.meal_order_no}">
					<form method="post" action="<%=request.getContextPath()%>/res_order/ResOrderServlet.do">
						<input type="hidden" name="action" value="go_res_meal">
						<font color="red">未訂餐</font><br>
						<button type="submit" class="btn btn-primary">我要訂餐</button>
					</form>
				</c:if>
			</td>
<!-- 			<td> -->
<%-- 				${resOrderVO.mem_no} --%>
<!-- 			</td> -->
<!-- 			<td> -->
<%-- 				<c:if test="${not empty resOrderVO.emp_no}"> --%>
<%-- 					${resOrderVO.emp_no} --%>
<%-- 				</c:if>  --%>
<%-- 				<c:if test="${empty resOrderVO.emp_no}"> --%>
<!-- 					無 -->
<%-- 				</c:if> --%>
<!-- 			</td> -->
<!-- 			<td> -->
<%-- 				<fmt:formatDate value="${resOrderVO.res_time}" pattern="yyyy-MM-dd HH:mm:ss"/> --%>
<!-- 			</td> -->
			<td>
				${resOrderVO.res_date}
			</td>
			<td>
				${timePeriSvc.getOneTimePeri(resOrderVO.time_peri_no).time_start}
			</td>
			<td>
				${resOrderVO.people}
			</td>
			<td>
				<c:forEach  var="item" items="${map}">
					<c:if test="${item.key eq resOrderVO.info_sts}">
						<c:if test="${item.key eq 3}">
							<font style="color: red" >${item.value}</font>
						</c:if>
						<c:if test="${item.key ne 3}">
							<font style="color: blue" >${item.value}</font>
						</c:if>
					</c:if>
				</c:forEach>
			</td>
			<td>
				<c:forEach  var="item" items="${map1}">
					<c:if test="${item.key eq resOrderVO.seat_sts}">
						<c:choose>
							<c:when  test= "${item.key eq 0}" >${item.value}</c:when>
							<c:otherwise>${item.value}</c:otherwise>
						</c:choose>
					</c:if>
				</c:forEach>
			</td>
			<td>
				<form method="post" action="<%=request.getContextPath()%>/res_order/ResOrderServlet.do">
					<input type="hidden" name="res_order_no" value="${resOrderVO.res_no}">
					<input type="hidden" name="action" value="modifySeatPosition">
					<button type="submit" class="btn btn-warning">修改座位</button>
				</form>
			</td>
			<td>
				<form method="post" action="<%=request.getContextPath()%>/res_order/ResOrderServlet.do">
					<input type="hidden" name="res_order_no" value="${resOrderVO.res_no}">
					<input type="hidden" name="action" value="cancelSeatResOrder">
					<button type="submit" class="btn btn-danger" >取消訂位</button>
				</form>
			</td>
		</tr>
	</c:forEach>
</table>

<%@ include file="pages/page2.file"%>
<input type="button" value="回首頁" onclick="location.href='<%=request.getContextPath()%>/back-end/seat_obj/addSeatObj.jsp'">
<input type="button" value="回桌訂位畫面" onclick="location.href='<%=request.getContextPath()%>/front-end/res_order/orderSeat.jsp'">
</div>
<footer>
<jsp:include page="/front-end/footer.jsp"></jsp:include>
</footer>
<script src="<%=request.getContextPath()%>/front-end/js/jquery-1.12.4.js"></script>
<script src="<%=request.getContextPath()%>/front-end/js/getMemberResSeat.js"></script>
</body>
</html>