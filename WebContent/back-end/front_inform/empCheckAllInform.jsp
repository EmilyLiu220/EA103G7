<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ page import="java.util.*"%>
<%@ page import="com.front_inform.model.*"%>

<%
	Front_InformService front_informSvc = new Front_InformService();
	List<Front_InformVO> list = front_informSvc.getAllInform();
	pageContext.setAttribute("list", list);
%>
<jsp:useBean id="memSvc" scope="page" class="com.mem.model.MemService"></jsp:useBean>

<html>
<head>

<title>查看通知</title>

<!-- Bootstrap CSS CDN -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css"
	integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4"
	crossorigin="anonymous">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">

<style>
#container {
	margin-top: 100px;
}
#table-1, #table-1 td {
	background: #555;
    color: #fff;
	border: 0;
	width: 100%;
	border-radius: 5px;
	text-align: center;
	box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);
}
</style>

</head>
<body>

<div class="wrapper">
	<div id="content">
		<div id="container">
			<h5 style="font-weight: 900; display: inline-block;">一般員工專區</h5><span> - 查看通知</span>
			<a href="<%=request.getContextPath()%>/back-end/back-index_New.jsp" style="display: inline-block; font-size: 8px; font-weight: 900; color: #dea554; text-decoration: none; margin-left: 20px;" onMouseOver="this.style.color='#ffbc5e';" onMouseOut="this.style.color='#dea554';">返回首頁</a>			
			<p>
				<table id="table-1">
					<tr>
						<td>
							<h3 style="margin-bottom:0;">查看所有會員通知</h3>
						</td>
					</tr>
				</table>
				<br>
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
							<th style="width: 10%;">編號</th>
							<th style="width: 20%;">會員</th>
							<th style="width: 10%;">訂位編號</th>
							<th style="width: 20%;">通知內容</th>
							<th style="width: 20%;">通知日期</th>
							<th style="width: 10%;">類別</th>
							<th style="width: 10%;">讀取狀態</th>
						</tr>
					</thead>
					<%@ include file="page1.file"%>
					<tbody>
					<c:forEach var="front_informVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
						<tr>
							<td style="text-align: center;">${front_informVO.info_no}</td>
							<td style="text-align: center;">${front_informVO.mem_no} ${pageScope.memSvc.getOneMem(front_informVO.mem_no).mem_name}</td>
							<td style="text-align: center;">${front_informVO.res_no}</td>
							<td style="text-align: center;">
								<c:choose>
									<c:when test="${front_informVO.info_cont eq '提醒您，因您多次訂位且多次點按確認當天用餐按鈕，但皆未至本餐廳用餐，您的訂位功能將於 3 天後恢復'}">暫停訂位功能通知</c:when>
									<c:when test="${front_informVO.info_cont eq '提醒您，因您多次訂餐付款但皆未至本餐廳取餐，您的訂餐功能將於 3 天後恢復'}">暫停訂餐功能通知</c:when>
									<c:when test="${front_informVO.info_cont eq '提醒您，因您檢舉多則評價，但評價內容多數未達不當言論之標準，您的檢舉功能將於 7 天後恢復'}">暫停檢舉功能通知</c:when>
									<c:when test="${front_informVO.info_cont eq '提醒您，因您有多則評價被檢舉成功，您的評價功能將於 14 天後恢復'}">暫停評價功能通知</c:when>
									<c:when test="${front_informVO.info_cont eq '提醒您，您將於 1 分鐘後被停權'}">會員停權通知</c:when>
									<c:when test="${front_informVO.info_cont eq '訂位成功，點選查看訂位明細'}">訂位成功通知</c:when>
									<c:when test="${front_informVO.info_cont eq '訂位訂單修改成功，點選查看訂位明細'}">訂位修改通知</c:when>
									<c:when test="${front_informVO.info_cont eq '訂餐成功！您尚未付款，點選前往結帳'}">訂餐成功尚未結帳</c:when>
									<c:when test="${front_informVO.info_cont eq '您已成功付款，點選查看訂單明細'}">訂單付款成功通知</c:when>
									<c:when test="${front_informVO.info_cont eq '您的訂單已取消'}">訂單取消通知</c:when>
									<c:when test="${front_informVO.info_cont eq '您的訂位已取消'}">訂位取消通知</c:when>
									<c:when test="${front_informVO.info_cont eq '您的餐點已完成，請至本餐廳取餐'}">取餐通知</c:when>
									<c:when test="${fn:contains(front_informVO.info_cont, '是否確認今日用餐')}">當日用餐通知</c:when>
									<c:otherwise>活動推播通知</c:otherwise>
								</c:choose>
							</td>
							<td style="text-align: center;"><fmt:formatDate value="${front_informVO.info_date}" pattern="yyyy-MM-dd" /></td>
							<td style="text-align: center;">
								<c:choose>
									<c:when test="${front_informVO.info_sts == 0}">一般通知</c:when>
									<c:when test="${front_informVO.info_sts == 1}">確認用餐</c:when>
									<c:when test="${front_informVO.info_sts == 2}">尚未回覆</c:when>
									<c:when test="${front_informVO.info_sts == 3}">取消訂位</c:when>
								</c:choose>
							</td>
							<td style="text-align: center;">
								<c:choose>
									<c:when test="${front_informVO.read_sts == 0}">未讀</c:when>
									<c:when test="${front_informVO.read_sts == 1}">已讀</c:when>
								</c:choose>
							</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
				<%@ include file="page2.file"%>
			</p>
		</div>
	</div>
</div>
<jsp:include page="/back-end/siderbar/siderbar.jsp" />
</body>
</html>