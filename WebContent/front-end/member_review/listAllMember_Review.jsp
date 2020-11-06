<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.member_review.model.*"%>
<%@ page import="com.bonus.model.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>會員評價檢舉</title>

<%
	Member_ReviewService member_reviewSvc = new Member_ReviewService();
	List<Member_ReviewVO> list = member_reviewSvc.getAll();
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
			<th style="width: 15%;">評價編號</th>
			<th style="width: 15%;">訂餐編號</th>
			<th style="width: 50%;">評價內容</th>
			<th style="width: 30%;">評價日期</th>
			<th style="width: 0%;"></th>
		</tr>		
		<%@ include file="page1.file"%>
		<c:forEach var="member_reviewVO" items="${list}"
			begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
			<tr>
				<td>${member_reviewVO.review_no}</td>
				<td>${member_reviewVO.meal_order_no}</td>
				<td>${member_reviewVO.mem_review_con}</td>
				<td>${member_reviewVO.review_date}</td>
			<td>
				<FORM METHOD="post"
					ACTION="<%=request.getContextPath()%>/front-end/report_appraise/forwarded"
					style="margin-bottom: 0px;">
					<input type="submit" value="檢舉" id="report" style="border: 1px solid #c8a97e; border-radius: 5px; color: #fff; background: #6b2822; cursor: pointer; box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);"
						onMouseOver="this.style.background='#ba2214'"
						onMouseOut="this.style.background='#6b2822'"> <input
						type="hidden" name="review_no"
						value="${member_reviewVO.review_no}"> 
						<input type="hidden" name="action" value="getOneForReportAppraise">
				</FORM>
			</td>
		</c:forEach>
	</table>	
	<%@ include file="page2.file"%>
	<%@ include file="/front-end/footer.jsp"%>
</body>
</html>