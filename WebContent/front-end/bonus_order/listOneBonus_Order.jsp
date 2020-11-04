<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.member_review.model.*"%>
<%@ page import="com.report_appraise.model.*"%>
<%@ page import="com.bonus.model.*"%>
<%@ page import="com.bonus_order.model.*"%>
<%@ page import="com.bonus_order_detail.model.*"%>

<%
	BonusVO bonusVO = (BonusVO) request.getAttribute("bonusVO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
%>
<jsp:useBean id="bonusSvc" scope="page"
	class="com.bonus.model.BonusService" />
	
<%
	Bonus_OrderVO bonus_orderVO = (Bonus_OrderVO) request.getAttribute("bonus_orderVO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
%>
<jsp:useBean id="bonus_orderSvc" scope="page"
	class="com.bonus_order.model.Bonus_OrderService" />
	
<%
	Bonus_Order_DetailVO bonus_order_detailVO = (Bonus_Order_DetailVO) request.getAttribute("bonus_order_detailVO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
%>
<jsp:useBean id="bonus_order_detailSvc" scope="page"
	class="com.bonus_order_detail.model.Bonus_Order_DetailService" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>紅利商品訂單明細-listOneBonus_Order.jsp</title>

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
	width: 600px;
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
<table id="table-1">
	<tr><td>
		 <h3>紅利商品訂單編號資料 - listOneBonus_Order.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<%-- 錯誤表列 --%>
			<c:if test="${not empty errorMsgs}">
				<font style="color: red">請修正以下錯誤:</font>
				<ul>
					<c:forEach var="message" items="${errorMsgs}">
						<li style="color: red">${message}</li>
					</c:forEach>
				</ul>
			</c:if>
			
<table class="table table-hover" style="width: 100%; font-size: 90%;">
				<thead style="text-align: center;">
					<tr>
						<th style="width: 20%;">紅利商品編號</th>
						<th style="width: 20%;">紅利商品訂單編號</th>
						<th style="width: 20%;">會員編號</th>
						<th style="width: 20%;">訂單日期</th>
						<th style="width: 20%;">優惠代碼</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="text-align: center;"><%=bonusVO.getBns_no()%></td>
						<td style="text-align: center;"><%=bonus_orderVO.getBo_no()%></td>
						<td style="text-align: center;"><%=bonus_orderVO.getMem_no()%></td>
						<td style="text-align: center;"><%=bonus_orderVO.getBo_date()%></td>
						<td style="text-align: center;"><%=bonus_orderVO.getPromo_code()%></td>
					</tr>
				</tbody>
			</table>
<%@ include file="/front-end/footer.jsp"%>
</body>
</html>