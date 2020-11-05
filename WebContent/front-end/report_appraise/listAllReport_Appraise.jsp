<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.member_review.model.*"%>
<%@ page import="com.report_appraise.model.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>評價檢舉清單</title>

<%
	Report_AppraiseService report_appraiseSvc = new Report_AppraiseService();
	List<Report_AppraiseVO> list = report_appraiseSvc.getAll();
	pageContext.setAttribute("list", list);
%>

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
			<th>評價編號</th>
			<th>會員編號</th>
			<th>員工編號</th>
			<th>檢舉內容</th>
			<th>檢舉日期</th>
		</tr>
		<%@ include file="page1.file"%>
		<c:forEach var="report_appraiseVO" items="${list}"
			begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">

			<tr>
				<td>${report_appraiseVO.report_no}</td>
				<td>${report_appraiseVO.review_no}</td>
				<td>${report_appraiseVO.mem_no}</td>
				<td>${report_appraiseVO.emp_no}</td>
				<td>${report_appraiseVO.report_con}</td>		
			</tr>
		</c:forEach>
	</table>
	<%@ include file="page2.file"%>
	<%@ include file="/front-end/footer.jsp"%>
</body>
</html>