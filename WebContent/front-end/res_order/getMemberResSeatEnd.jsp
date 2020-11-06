<%@ page import="com.time_peri.model.TimePeriService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.res_order.model.*"%>
<%@page import="com.mem.model.MemVO"%>
<%
	MemVO memVO = (MemVO) session.getAttribute("memVO2");
	ResOrderService resOrderSvc = new ResOrderService();
	List<ResOrderVO> list = resOrderSvc.getOneMemberResOrder(memVO.getMem_no(), "end");
	pageContext.setAttribute("list", list);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>會員訂位歷史紀錄</title>
<link rel=stylesheet type="text/css" href="<%=request.getContextPath()%>/front-end/css/getMemberResSeat.css">
<jsp:include page="/front-end/headfinish.jsp"></jsp:include>

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
<div class="container get_member_res_seat" >
<table id="table-1">
	<tr>
		<td><h3 style="margin-bottom:0;">訂位歷史紀錄</h3></td>
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
		<th>訂餐編號</th>
		<th>預約訂位日期</th>
		<th>用餐時段</th>
		<th>人數</th>
		<th>訂位狀態</th>
	</tr>
	<%@ include file="pages/page1.file"%>
	<jsp:useBean id="map_info_sts" class="java.util.HashMap"/>
		<c:set target="${map_info_sts}" property="2" value="完成"/>
		<c:set target="${map_info_sts}" property="3" value="取消訂位"/>
	<jsp:useBean id="seat_sts" class="java.util.HashMap"/>
		<c:set target="${seat_sts}" property="0" value="未入座"/>
		<c:set target="${seat_sts}" property="1" value="已入座"/>
	<jsp:useBean id="timePeriSvc" scope="page" class="com.time_peri.model.TimePeriService" />
	<jsp:useBean id="resDetailSvc" scope="page" class="com.res_detail.model.ResDetailService" />
	<jsp:useBean id="seatSvc" scope="page" class="com.seat.model.SeatService" />
	<c:forEach var="resOrderVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
	<c:if test="${(resOrderVO.info_sts eq 3) or (resOrderVO.seat_sts eq 1)}">
		<tr>
			<td>
				<c:forEach var="resDetailVO" items="${resDetailSvc.getAllResNO(resOrderVO.res_no)}">
					${seatSvc.getOneSeat(resDetailVO.seat_no).seat_f}樓_${seatSvc.getOneSeat(resDetailVO.seat_no).seat_name}桌<br> 
				</c:forEach>
			</td>
			<td>
				<c:choose>    
					<c:when test="${not empty resOrderVO.meal_order_no}">  
							<a href="<%=request.getContextPath()%>/MealOrderServlet.do?action=memOrder&meal_order_no=${resOrderVO.meal_order_no}&reqURL=<%=request.getServletPath()%>&whichPage=<%=whichPage%>&queryString=<%=request.getAttribute("action")%>">這筆訂餐</a>
					</c:when>
					<c:otherwise>
						<font color="red">未訂餐</font><br>
					</c:otherwise>
				</c:choose>
			</td>
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
					<c:if test="${item.key eq resOrderVO.info_sts or resOrderVO.seat_sts eq 0}">
						<c:if test="${item.key eq 3}">
							<font style="color: red" >${item.value}</font>
						</c:if>
					</c:if>
				</c:forEach>
				<c:if test="${resOrderVO.seat_sts eq 1}">
					<font style="color: blue" >已入座</font>
				</c:if>
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
<input class="btn btn-secondary" type="button" value="回進行中訂單" onclick="location.href='<%=request.getContextPath()%>/front-end/res_order/getMemberResSeat.jsp'">
</div>
<footer>
<jsp:include page="/front-end/footer.jsp"></jsp:include>
</footer>
<script src="<%=request.getContextPath()%>/front-end/js/jquery-1.12.4.js"></script>
<script src="<%=request.getContextPath()%>/front-end/js/sweetalert.min.js"></script>
<script src="<%=request.getContextPath()%>/front-end/js/getMemberResSeatEnd.js"></script>
</body>
</html>