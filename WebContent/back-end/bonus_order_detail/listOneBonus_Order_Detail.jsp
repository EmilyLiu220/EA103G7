<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ page import="com.bonus_order_detail.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
  Bonus_Order_DetailVO bonus_order_detailVO = (Bonus_Order_DetailVO) request.getAttribute("bonus_order_detailVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
%>

<html>
<head>
<title>紅利商品訂單編號資料 - listOneBonus_Order_Detail.jsp</title>

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
		 <h3>紅利商品訂單編號資料 - listOneBonus_Order_Detail.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>紅利商品訂單編號</th>
		<th>紅利商品編號</th>
		<th>數量</th>
	</tr>
	<tr>
		<td><%=bonus_order_detailVO.getBo_no()%></td>
		<td><%=bonus_order_detailVO.getBns_no()%></td>
		<td><%=bonus_order_detailVO.getQuantity()%></td>
	</tr>
</table>

</body>
</html>