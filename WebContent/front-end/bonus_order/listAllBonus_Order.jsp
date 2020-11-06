<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.member_review.model.*"%>
<%@ page import="com.bonus.model.*"%>
<%@ page import="com.bonus_order.model.*"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>紅利商品訂單清單-listAllBonus_Order.jsp</title>

<%
	Bonus_OrderService bonus_orderSvc = new Bonus_OrderService();
	List<Bonus_OrderVO> list = bonus_orderSvc.getAll();
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
			<th style="width: 33%;">紅利商品訂單編號</th>
			<th style="width: 33%;">會員編號</th>
			<th style="width: 33%;">訂單日期</th>
			<th style="width: 1%;"></th>
		</tr>
		<%@ include file="page1.file"%>
		<tbody>
			<c:forEach var="bonus_orderVO" items="${list}" begin="<%=pageIndex%>"
				end="<%=pageIndex+rowsPerPage-1%>">
				<tr>
					<td style="text-align: center;">${bonus_orderVO.bo_no}</td>
					<td style="text-align: center;">${bonus_orderVO.mem_no}</td>
					<td style="text-align: center;">${bonus_orderVO.bo_date}</td>
					<td style="text-align: center;">
						<FORM METHOD="post"
							ACTION="<%=request.getContextPath()%>/front-end/bonus_order/forwarded"
							style="margin-bottom: 0px;">
							<input type="submit" value="明細" id="details"
								style="border: 1px solid #c8a97e; border-radius: 5px; color: #fff; background: #6b2822; cursor: pointer; box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);"
								onMouseOver="this.style.background='#ba2214'"
								onMouseOut="this.style.background='#6b2822'"> <input
								type="hidden" name="bo_no" value="${bonus_orderVO.bo_no}">
							<input type="hidden" name="action" value="bonusOrderDetailsFront">
						</FORM>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<%@ include file="page2.file"%>
	</p>
	</div>
	</div>

	<!-- jQuery CDN - Slim version (=without AJAX) -->
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	<!-- Popper.JS -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"
		integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ"
		crossorigin="anonymous"></script>
	<!-- Bootstrap JS -->
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"
		integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm"
		crossorigin="anonymous"></script>
	<!-- jQuery Custom Scroller CDN -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.concat.min.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#sidebar").mCustomScrollbar({
				theme : "minimal"
			});

			$('#sidebarCollapse').on('click', function() {
				$('#sidebar, #content').toggleClass('active');
				$('.collapse.in').toggleClass('in');
				$('a[aria-expanded=true]').attr('aria-expanded', 'false');
			});
		});
	</script>
	<%@ include file="/front-end/footer.jsp"%>
</body>
</html>