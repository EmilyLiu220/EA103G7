<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ page import="com.report_appraise.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
  Report_AppraiseVO report_appraiseVO = (Report_AppraiseVO) request.getAttribute("report_appraiseVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
%>

<html>
<head>
<title>員工資料 - listOneReport_Appraise.jsp</title>

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
		 <h3>檢舉資料 - listOneReport_Appraise.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>檢舉編號</th>
		<th>訂餐編號</th>
		<th>會員編號</th>
		<th>員工編號</th>
		<th>檢舉日期</th>
		<th>檢舉內容</th>
		<th>檢舉狀態</th>
	</tr>
	<tr>
		<td><%=report_appraiseVO.getReport_no()%></td>
		<td><%=report_appraiseVO.getReview_no()%></td>
		<td><%=report_appraiseVO.getMem_no()%></td>
		<td><%=report_appraiseVO.getEmp_no()%></td>
		<td><%=report_appraiseVO.getReport_date()%></td>
		<td><%=report_appraiseVO.getReport_con()%></td>
		<td><%=report_appraiseVO.getReply_sts()%></td>
	</tr>
</table>

</body>
</html>