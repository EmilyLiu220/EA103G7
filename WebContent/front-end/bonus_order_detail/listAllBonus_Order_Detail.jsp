<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.member_review.model.*"%>
<%@ page import="com.bonus.model.*"%>
<%@ page import="com.bonus_order.model.*"%>
<%@ page import="com.bonus_order_detail.model.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>紅利商品訂單明細-listAllBonus_Order.jsp</title>

<%
	Bonus_Order_DetailService bonus_order_detailSvc = new Bonus_Order_DetailService();
	List<Bonus_Order_DetailVO> list = bonus_order_detailSvc.getAll();
	pageContext.setAttribute("list", list);
%>

<html>
<head>
<title>所有評價內容 - listAllBonus_Order_Detail.jsp</title>

<style>
table#table-1 {
	background-color: #CCCCFF;
	border: 2px solid black;
	text-align: center;
}

table#table-1 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

h4 {
	color: blue;
	display: inline;
}
</style>

<style>
table {
	width: 800px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
}

table, th, td {
	border: 1px solid #CCCCFF;
}

th, td {
	padding: 5px;
	text-align: center;
}
</style>
<%@ include file="/front-end/headfinish.jsp"%>
</head>
<body bgcolor='white'>
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<table align="center">
		<tr>
			<th>紅利商品訂單編號</th>
			<th>紅利商品編號</th>
			<th>數量</th>
		</tr>
		<%@ include file="page1.file"%>
		<c:forEach var="bonus_order_detailVO" items="${list}"
			begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">

			<tr>
				<td>${bonus_order_detailVO.bo_no}</td>
				<td>${bonus_order_detailVO.bns_no}</td>
				<td>${bonus_order_detailVO.quantity}</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="page2.file"%>
	<%@ include file="/front-end/footer.jsp"%>
</body>
</html>