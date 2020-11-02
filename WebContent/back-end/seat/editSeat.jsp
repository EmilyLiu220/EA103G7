<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.inform_set.model.*"%>
<%@ page import="com.seat_obj.model.*"%>
<jsp:useBean id="isSvc" scope="page" class="com.inform_set.model.Inform_SetService"></jsp:useBean>
<jsp:useBean id="empSvc" scope="page" class="com.emp.model.EmpService"></jsp:useBean>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>桌位物件管理-select_is.jsp</title>
<link rel=stylesheet type="text/css" href="<%=request.getContextPath()%>/back-end/css/editSeat.css">

</head>
<body>
	<div class="wrapper">
		<!-- Page Content  -->
		<div id="content">
		<div class="editSeatDiv">
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
				<table id="table-1">
					<tr>
						<td><h3 style="margin-bottom:0;">桌位編輯</h3></td>
					</tr>
				</table>
				<jsp:include page="/back-end/seat/editSeat_include.jsp"></jsp:include>
			</div>
		</div>
	</div>
	<script src="<%=request.getContextPath()%>/back-end/js/jquery-1.12.4.js"></script>
	<jsp:include page="/back-end/siderbar/siderbar_for_editSeat.jsp" />
	<script src="<%=request.getContextPath()%>/back-end/js/jquery-ui-1.12.1.js"></script>
	<script src="<%=request.getContextPath()%>/back-end/js/jquery.ui.core.js"></script>
	<script src="<%=request.getContextPath()%>/back-end/js/jquery.ui.widget.js"></script>
	<script src="<%=request.getContextPath()%>/back-end/js/jquery.ui.mouse.js"></script>
	<script src="<%=request.getContextPath()%>/back-end/js/jquery.ui.draggable.js"></script>
	<script src="<%=request.getContextPath()%>/back-end/js/jquery-collision.min.js"></script>
	<!-- 碰撞 -->
	<script src="<%=request.getContextPath()%>/back-end/js/jquery-ui-draggable-collision.min.js"></script>
	<script src="<%=request.getContextPath()%>/back-end/js/editSeat.js"></script>
</body>
</html>