<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.seat_obj.model.*"%>
<%@ page import="com.seat.model.*"%>
<!DOCTYPE html>
<html>
<head>
<title>桌位編輯頁面</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Bootstrap CSS CDN -->
<!-- <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous"> -->
<%-- <link rel=stylesheet type="text/css" href="<%=request.getContextPath()%>/back-end/css/editSeat.css"> --%>

</head>
<body>
	<div class="editSeatDiv_include">
		<form method="post" action="<%=request.getContextPath()%>/seat/SeatServlet.do">
			<div id="container" class="container ui-widget-header" data-role="drag-drop-container">
				<jsp:useBean id="seatSvc" scope="page" class="com.seat.model.SeatService" />
				<%
					List<SeatVO> list = seatSvc.getAll();
					TreeSet<Integer> ts = new TreeSet<Integer>();
					for(int i = 0; i< list.size(); i++){
						ts.add(list.get(i).getSeat_f());
					}
					pageContext.setAttribute("ts", ts);
				%>
				<c:set var="floor" value="${floor}"></c:set>
				<c:forEach var="seatVO" items="${seatSvc.all}">
					<c:if test="${seatVO.seat_f == (empty floor ? 1 : floor) }">
						<div class="drag ui-draggable" style="position: absolute; left: ${seatVO.seat_x}px; top: ${seatVO.seat_y}px;" id="drag">
							<label> 
								<input type="checkbox" class="myCheckbox" id="myCheckbox" name="${seatVO.seat_name}" value="${seatVO.seat_no}"> 
								<img src="<%=request.getContextPath()%>/seat/Seat_ObjServlet.do?seat_obj_no=${seatVO.seat_obj_no}">
							</label>
							<label class="seatLabel">桌名：<input  type="text" class="seatName" name="seatName" value="${seatVO.seat_name}"></label>
						</div>
					</c:if>
				</c:forEach>
			</div>
			<div class="obj_bar" style="overflow:auto;">
				<p>桌位物件列表</p>
				<div class="select_div">
					<select id="floor_list" name="floor_list">
						<c:forEach var="seat_f" items="${ts}">
							<option class="lt" value="${seat_f}" ${seat_f==floor ? "selected":"" }>設定${seat_f}樓座位</option>
						</c:forEach>
					</select>
				</div>
				<jsp:useBean id="seatObjSvc" scope="page" class="com.seat_obj.model.SeatObjService" />
				<c:forEach var="seatObjVO" items="${seatObjSvc.all}">
				<div id="draggable" class="draggable ui-widget-content">
					<label> 
							<input type="checkbox" class="myCheckbox" id="myCheckbox" name="${seatObjVO.seat_obj_no}" disabled /> 
						<c:if test="${not empty seatObjVO.seat_obj}">
							<img src="<%=request.getContextPath()%>/seat/Seat_ObjServlet.do?seat_obj_no=${seatObjVO.seat_obj_no}" />
						</c:if> 
						<c:if test="${empty seatObjVO.seat_obj}">
							<img src="<%=request.getContextPath()%>/images/null2.jpg" />
						</c:if>
					</label>
					<label class="seatLabel">桌名：<input  type="text" class="seatName" name="seatName" disabled></label>
				</div>
				</c:forEach>
				<span><button id="delete" name="action" value="delete_seat" onclick='return false;'>刪除選中</button>
					<button id="save" name="action" value="archive_seat" onclick=' return false;'>儲存編輯</button></span>
				<div class="button">
					<div class="text-left">
						<input type="button" value="桌位物件設定" onclick="location.href='<%=request.getContextPath()%>/back-end/seat_obj/setSeatObj.jsp'">
					</div>
				</div>
			</div>
		</form>
	</div>
</body>
		<script src="<%=request.getContextPath()%>/back-end/js/jquery-1.12.4.js"></script>
		<script src="<%=request.getContextPath()%>/back-end/js/jquery-ui-1.12.1.js"></script>
		<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
		<script src="<%=request.getContextPath()%>/back-end/js/jquery.ui.core.js"></script>
		<script src="<%=request.getContextPath()%>/back-end/js/jquery.ui.widget.js"></script>
		<script src="<%=request.getContextPath()%>/back-end/js/jquery.ui.mouse.js"></script>
		<script src="<%=request.getContextPath()%>/back-end/js/jquery.ui.draggable.js"></script>
		<script src="<%=request.getContextPath()%>/back-end/js/jquery-collision.min.js"></script>
		<!-- 碰撞 -->
		<script src="<%=request.getContextPath()%>/back-end/js/jquery-ui-draggable-collision.min.js"></script>
		<script src="<%=request.getContextPath()%>/back-end/js/editSeat.js"></script>
		<!-- 獲得座位物件資訊，使用ajax傳送給servlet  -->

</html>