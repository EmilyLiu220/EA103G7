<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.inform_set.model.*"%>

<jsp:useBean id="isSvc" scope="page" class="com.inform_set.model.Inform_SetService"></jsp:useBean>
<jsp:useBean id="empSvc" scope="page" class="com.emp.model.EmpService"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>通知設定管理-select_is.jsp</title>

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
.updateSeatObjectDiv{
	position: relative;
	z-index: 999;
	top: 70px;
}
</style>

</head>
<body>
	<div class="wrapper">
		<div id="content">
		<div class="updateSeatObjectDiv">
			<p>
				<%-- 查詢通知 --%>
				<table id="table-1">
					<tr>
						<td><h3 style="margin-bottom:0;">變更桌位物件</h3></td>
					</tr>
				</table>
				<br>
				<%-- 錯誤表列 --%>
				<c:if test="${not empty errorMsgs}">
					<font style="color: red">請修正以下錯誤:</font>
					<ul>
						<c:forEach var="message" items="${errorMsgs}">
							<li style="color: red">${message}</li>
						</c:forEach>
					</ul>
					<br>
				</c:if>
				
				<form action="<%=request.getContextPath()%>/seat/Seat_ObjServlet.do" method=post enctype="multipart/form-data">
						<label>桌位物件編號：${seatObjVO.seat_obj_no}</label>
						<div class="div_img">
							<input type="file" id="myFile" name="seat_obj" />
							<br>
						</div>
						<div id="preview">
							<img src="<%=request.getContextPath()%>/seat/Seat_ObjServlet.do?seat_obj_no=${seatObjVO.seat_obj_no}" />
						</div>
						<div class="set_people">
							設定桌位人數：
							<input class="cut" name="" type="button" value="-" disabled /> 
							<input id="seat_people" class="seat_people"	name="seat_people" type="text" value="${seatObjVO.seat_people}" readonly="readonly"
									style="width: 30px; text-align: center" /> 
							<input class="add" name="" type="button" value="+" disabled />
						</div>
						<div class="select_div">
							<jsp:useBean id="map" class="java.util.HashMap"/>
							<c:set target="${map}" property="0" value="座位"/>
							<c:set target="${map}" property="1" value="障礙物"/>
							<c:set target="${map}" property="2" value="背景圖"/>
							物件用途：
							<select id="seat_use" name="seat_use">
								<c:forEach  var="item" items="${map}">
								  <option class="seat_use" value="${item.key}" ${item.key eq seatObjVO.seat_use ? "selected" : ""}>${item.value}</option>
								</c:forEach>
							</select>
						</div>
						<input type="hidden" name="seat_obj_no" value="${seatObjVO.seat_obj_no}"> 
						<input type="hidden" name="action" value="one_obj_update"> <input type="SUBMIT" name="submit"
								value="送出修改" />
						<input type="button" value="回桌位物件設定" onclick="location.href='<%=request.getContextPath()%>/back-end/seat_obj/setSeatObj.jsp'">
				</form>
				</div>
			</p>	
		</div>
	</div>
	<jsp:include page="/back-end/siderbar/siderbar.jsp" />
	<script src="<%=request.getContextPath()%>/back-end/js/setSeatObj.js"></script>
	<script src="<%=request.getContextPath()%>/back-end/js/updateSeatObjUpload.js"></script>
</body>
</html>