<%@page import="com.mem.model.MemVO"%>
<%@page import="com.time_peri.model.TimePeriService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.res_order.model.*"%>
<%
	MemVO memVO = (MemVO) session.getAttribute("memVO2");
 	ResOrderService resOrderSvc = new ResOrderService();
	List<ResOrderVO> list = resOrderSvc.getOneMemberResOrder(memVO.getMem_no(), "ing");
	pageContext.setAttribute("list", list);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>會員訂位紀錄</title>
<link rel=stylesheet type="text/css" href="<%=request.getContextPath()%>/front-end/css/getMemberResSeat.css">
<%@include file="/front-end/headfinish.jsp" %>
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
<div class="container get_member_res_seat">
<table id="table-1">
	<tr>
		<td><h3 style="margin-bottom:0;">進行中的訂位</h3></td>
	</tr>
</table>
<table class="table table-striped table-hover mx-auto w-auto">
<c:choose>
	 <c:when test="${list.size() eq 0}">
	 	<font style="font-size: 40px;" color="red">查無資料</font><br>
    </c:when>
    <c:otherwise>
	<tr>
		<th>桌位</th>
		<th>訂餐明細</th>
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

	<jsp:useBean id="map_info_sts" class="java.util.HashMap"/>
		<c:set target="${map_info_sts}" property="0" value="未發送"/>
		<c:set target="${map_info_sts}" property="1" value="已發送未確認"/>
		<c:set target="${map_info_sts}" property="2" value="已發送已確認"/>
		<c:set target="${map_info_sts}" property="3" value="取消訂位"/>
	<jsp:useBean id="map_seat_sts" class="java.util.HashMap"/>
		<c:set target="${map_seat_sts}" property="0" value="未入座"/>
		<c:set target="${map_seat_sts}" property="1" value="已入座"/>
	<jsp:useBean id="timePeriSvc" scope="page" class="com.time_peri.model.TimePeriService" />
	<jsp:useBean id="resDetailSvc" scope="page" class="com.res_detail.model.ResDetailService" />
	<jsp:useBean id="seatSvc" scope="page" class="com.seat.model.SeatService" />
	<c:forEach var="resOrderVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
	<c:if test="${resOrderVO.info_sts lt 3}">
		<tr>
			<td>
				<c:forEach var="resDetailVO" items="${resDetailSvc.getAllResNO(resOrderVO.res_no)}">
					${seatSvc.getOneSeat(resDetailVO.seat_no).seat_f}樓_${seatSvc.getOneSeat(resDetailVO.seat_no).seat_name}桌<br> 
				</c:forEach>
			</td>
			<td>
				<c:if test="${not empty resOrderVO.meal_order_no}">
					<a href="<%=request.getContextPath()%>/MealOrderServlet.do?action=memOrder&meal_order_no=${resOrderVO.meal_order_no}&reqURL=<%=request.getServletPath()%>&whichPage=<%=whichPage%>&queryString=<%=request.getAttribute("action")%>">這筆訂餐</a>
				</c:if> 
				<c:if test="${empty resOrderVO.meal_order_no}">
					<form method="post" action="<%=request.getContextPath()%>/res_order/ResOrderServlet.do">
						<c:choose>    
							<c:when test="${resOrderVO.info_sts ne 3}">  
									<input type="hidden" name="res_no" value="${resOrderVO.res_no}">
									<font color="red">未訂餐</font><br>
									<button type="submit" id="go_Order_Meal" class="btn btn-primary" onclick='return false;'>我要訂餐</button>
							</c:when>
							<c:otherwise>
								<font color="red">未訂餐</font><br>
								<button type="submit" class="btn btn-primary" disabled="disabled" style="cursor: not-allowed;">我要訂餐</button>
							</c:otherwise>
						</c:choose>
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
				<c:forEach  var="item" items="${map_info_sts}">
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
				<c:forEach  var="item" items="${map_seat_sts}">
					<c:if test="${item.key eq resOrderVO.seat_sts}">
						<c:choose>
							<c:when test= "${item.key eq 0}" >${item.value}</c:when>
							<c:otherwise>${item.value}</c:otherwise>
						</c:choose>
					</c:if>
				</c:forEach>
			</td>
			<td>
				<form method="post" action="<%=request.getContextPath()%>/res_order/ResOrderServlet.do">
					<c:choose>    
						<c:when test="${resOrderVO.info_sts lt 1}">  
								<input type="hidden" name="res_no" value="${resOrderVO.res_no}">
								<input type="hidden" name="res_people" value="${resOrderVO.people}">
								<input type="hidden" name="action" value="modify_Seat_Order">
								<button type="submit" id="modify_Seat_Order" class="btn btn-warning" onclick='return false;'>修改座位</button>
						</c:when>
						<c:otherwise>
							<button type="submit" class="btn btn-warning" disabled="disabled" style="cursor: not-allowed;">修改座位</button>
						</c:otherwise>
					</c:choose>
				</form>
			</td>
			<td>
				<form method="post" action="<%=request.getContextPath()%>/res_order/ResOrderServlet.do">
					<c:choose>    
						<c:when test="${resOrderVO.info_sts lt 1}">  
								<input type="hidden" name="res_no" value="${resOrderVO.res_no}">
								<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			     				<input type="hidden" name="whichPage"	value="<%=whichPage%>">               <!--送出當前是第幾頁給Controller-->
								<button type="submit" id="cancel_Seat_Res_Order" class="btn btn-danger" onclick='return false;' >取消訂位</button>
						</c:when>
						<c:otherwise>
							<button type="submit" class="btn btn-danger" disabled="disabled" style="cursor: not-allowed;">取消訂位</button>
						</c:otherwise>
					</c:choose>
				</form>
			</td>
		</tr>
		</c:if>
	</c:forEach>
</table>

<%@ include file="pages/page2.file"%>
	    </c:otherwise>
</c:choose>
<input class="btn btn-primary" type="button" value="回首頁" onclick="location.href='<%=request.getContextPath()%>/back-end/seat_obj/addSeatObj.jsp'">
<input class="btn btn-secondary" type="button" value="回桌訂位畫面" onclick="location.href='<%=request.getContextPath()%>/front-end/res_order/orderSeat.jsp'">

</div>

<jsp:include page="/front-end/footer.jsp"></jsp:include>

<script src="<%=request.getContextPath()%>/front-end/js/jquery-1.12.4.js"></script>
<script src="<%=request.getContextPath()%>/front-end/js/sweetalert.min.js"></script>
<script src="<%=request.getContextPath()%>/front-end/js/getMemberResSeatIng.js"></script>
</body>
</html>