<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member_review.model.*"%>
<%@ page import="com.report_appraise.model.*"%>

<%
	Member_ReviewVO member_reviewVO = (Member_ReviewVO) request.getAttribute("member_reviewVO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
%>
<jsp:useBean id="member_reviewSvc" scope="page"
	class="com.member_review.model.Member_ReviewService" />
	
<%
	Report_AppraiseVO report_appraiseVO = (Report_AppraiseVO) request.getAttribute("report_appraiseVO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
%>
<jsp:useBean id="report_appraiseSvc" scope="page"
	class="com.report_appraise.model.Report_AppraiseService" />

<html>
<head>
<title>評價檢舉新增</title>

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
<!-- 		<th>檢舉編號</th> -->
		<th>評價編號</th>
		<th>會員編號</th>
		<th>檢舉內容</th>
		<th>檢舉日期</th>
		<th>狀態</th>
	</tr>
	<tr>
<%-- 		<td><%=report_appraiseVO.getReport_no()%></td> --%>
		<td><%=report_appraiseVO.getReview_no()%></td>
		<td><%=report_appraiseVO.getMem_no()%></td>
		<td><%=report_appraiseVO.getReport_con()%></td>
		<td><%=report_appraiseVO.getReport_date()%></td>
		<td><font color="red">評價檢舉成功</font></td>
	</tr>
</table>
<%@ include file="/front-end/footer.jsp"%>
</body>
</html>