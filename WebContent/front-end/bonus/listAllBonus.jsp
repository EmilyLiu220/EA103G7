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
<title>紅利商品兌換</title>

<%
	BonusService bonusSvc = new BonusService();
	List<BonusVO> list = bonusSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<jsp:useBean id="detailSvc" scope="page"
	class="com.bonus_order_detail.model.Bonus_Order_DetailService" />

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
	border: 1px solid red;
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
			<th style="width: 20%;">紅利商品編號</th>
			<th style="width: 20%;">紅利商品名稱</th>
			<th style="width: 20%;">紅利商品價格</th>
			<th style="width: 20%;">有效日期</th>
			<th style="width: 20%;">商品預覽</th>
			<th style="width: 0%;"></th>			
		</tr>
		<%@ include file="page1.file"%>
		<c:forEach var="bonusVO" items="${list}" begin="<%=pageIndex%>"
			end="<%=pageIndex+rowsPerPage-1%>">
			<tr>
				<td>${bonusVO.bns_no}</td>
				<td>${bonusVO.bns_name}</td>
				<td>${bonusVO.bns_price}</td>
				<td>${bonusVO.bns_date}</td>
				<td><img name="bns_img" src="<%=request.getContextPath() %>/bonus/bonus.showPic?bonus_img=${bonusVO.bns_no}"></td>
			<td>
				<FORM METHOD="post"
					ACTION="<%=request.getContextPath()%>/front-end/bonus_order/forwarded"
					style="margin-bottom: 0px;">
					<input type="submit" value="兌換" id="insert"
						style="border: 1px solid #c8a97e; border-radius: 5px; color: #fff; background: #8f801d; cursor: pointer; box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);"
						onMouseOver="this.style.background='#c4b029'"
						onMouseOut="this.style.background='#8f801d'"> 
						<input type="hidden" name="bns_no" value="${bonusVO.bns_no}"> 
						<input type="hidden" name="action" value="insert">
				</FORM>
			</td>
		</c:forEach>
	</table>
	<br>
	<%@ include file="page2.file"%>
	<%@ include file="/front-end/footer.jsp"%>
</body>
</html>