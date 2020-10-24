<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ page import="com.bonus.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
  BonusVO bonusVO = (BonusVO) request.getAttribute("bonusVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
%>

<html>
<head>
<title>紅利商品資料 - listOneBonus.jsp</title>

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
		 <h3>紅利商品資料 - listOneBonus.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>紅利商品編號</th>
		<th>紅利商品名稱</th>
		<th>紅利商品價格</th>
		<th>庫存量</th>
		<th>有效日期</th>
		<th>紅利商品狀態</th>
	</tr>
	<tr>
		<td><%=bonusVO.getBns_no()%></td>
		<td><%=bonusVO.getBns_name()%></td>
		<td><%=bonusVO.getBns_price()%></td>
		<td><%=bonusVO.getBns_stks()%></td>
		<td><%=bonusVO.getBns_date()%></td>
		<td><%=bonusVO.getBns_sts()%></td>
	</tr>
</table>

</body>
</html>