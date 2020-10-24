<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>新增桌位物件</title>
<style type="text/css">
.seat_people {
	width: 20px;
}
</style>
</head>
<!-- 因為是從servletController那邊forward過來，參考路徑需由servlet角度參考 -->
<link rel=stylesheet type="text/css"
		href="<%=request.getContextPath()%>/back-end/css/addSeatObjUpload.css">
<body>
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
		<br> 
		<input type="SUBMIT" name="submit" value="上傳" />
		<button id="delete">刪除勾選</button> <input type="button" value="回桌位物件設定" onclick="location.href='<%=request.getContextPath()%>/back-end/seat_obj/setSeatObj.jsp'">
		<br>
		<div class="row">
			<div id="preview"></div>
		</div>
		<input type="hidden" name="action" value="upload_seat_obj">
	</form>
<script src="<%=request.getContextPath()%>/back-end/js/jquery-1.12.4.js"></script>
<script src="<%=request.getContextPath()%>/back-end/js/jquery-ui-1.12.1.js"></script>
<!-- 因為是從servletController那邊forward過來，參考路徑需由servlet角度參考 -->
<script src="<%=request.getContextPath()%>/back-end/js/addSeatObjUpload.js"></script>
</body>
</html>