<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.member_review.model.*"%>
<%@ page import="com.report_appraise.model.*"%>

<%-- <jsp:useBean id="member_reviewSvc" scope="page" --%>
<%-- 	class="com.member_review.model.Member_ReviewService" /> --%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>會員評價功能</title>

<style>
table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
	border: 3px ridge Gray;
	height: 80px;
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
<div align="center">
<br>
	<h3>新增評價</h3>
		<li><a href='addMember_Review.jsp' id="review">Add</a> a new Member Review</li>
	<br>
	<h3>查詢所有評價</h3>
		<li><a href='listAllMember_Review.jsp'>Search</a> all MemberReview</li>
</div>
	<%@ include file="/front-end/footer.jsp"%>
	
<script>
	var mem_repo = `${memVO2.mem_repo}`;
	var review = document.getElementById("review");
	if (mem_no !== '' && mem_review == 0) {
		review.addEventListener("click", function() {
			alert("Sorry！您沒有評價權限！" + "\n" + "有任何疑問請洽客服。");
		});
	}
	if (mem_no === '') {
		review.addEventListener("click", function() {
			alert("請先登入會員喔！");
		});
	}
</script>
</body>
</html>