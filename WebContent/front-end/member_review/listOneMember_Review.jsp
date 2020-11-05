<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.member_review.model.*"%>
<%@ page import="com.bonus.model.*"%>

<%
   Member_ReviewVO member_reviewVO = (Member_ReviewVO) request.getAttribute("member_reviewVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
%>

<html>
<head>
<title>會員評價資料</title>

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
<br>
<table align="center">
	<tr>
<!-- 		<th>評價編號</th> -->
		<th>訂餐編號</th>
		<th>評價內容</th>
		<th>評價日期</th>
		<th>狀態</th>
	</tr>
	<tr>
<%-- 		<td><%=member_reviewVO.getReview_no()%></td> --%>
		<td><%=member_reviewVO.getMeal_order_no()%></td>
		<td><%=member_reviewVO.getMem_review_con()%></td>
		<td><%=member_reviewVO.getReview_date()%></td>
		<td><font color="red">評價新增成功</font></td>
</table>
<%@ include file="/front-end/footer.jsp"%>
</body>
</html>