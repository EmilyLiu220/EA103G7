<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ page import="com.bonus_order.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
  Bonus_OrderVO bonus_orderVO = (Bonus_OrderVO) request.getAttribute("bonus_orderVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
%>

<html>
<head>
<title>紅利商品訂單編號資料 - listOneBonus_Order.jsp</title>

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

</head>
<body bgcolor='white'>

<h4>此頁暫練習採用 Script 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>紅利商品訂單編號資料 - listOneBonus_Order.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>紅利商品編號</th>
		<th>會員編號</th>
		<th>訂單日期</th>
		<th>優惠代碼</th>
	</tr>
	<tr>
		<td><%=bonus_orderVO.getBo_no()%></td>
		<td><%=bonus_orderVO.getMem_no()%></td>
		<td><%=bonus_orderVO.getBo_date()%></td>
		<td><%=bonus_orderVO.getPromo_code()%></td>
	</tr>
</table>

</body>
</html>