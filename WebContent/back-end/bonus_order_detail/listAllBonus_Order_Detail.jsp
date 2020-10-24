<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.bonus_order_detail.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
    Bonus_Order_DetailService bonus_order_detailSvc = new Bonus_Order_DetailService();
    List<Bonus_Order_DetailVO> list = bonus_order_detailSvc.getAll();
    pageContext.setAttribute("list",list);
%>


<html>
<head>
<title>所有評價內容 - listAllBonus_Order_Detail.jsp</title>

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

</head>
<body bgcolor='white'>

<h4>此頁練習採用 EL 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>所有評價內容 - listAllBonus_Order_Detail.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<table>
	<tr>
		<th>紅利商品訂單編號</th>
		<th>紅利商品編號</th>
		<th>數量</th>
		<th>修改</th>
		<th>刪除</th>
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="bonus_order_detailVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		
		<tr>
			<td>${bonus_order_detailVO.bo_no}</td>
			<td>${bonus_order_detailVO.bns_no}</td>
			<td>${bonus_order_detailVO.quantity}</td>

			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/bonus_order_detail/forwarded" style="margin-bottom: 0px;">
			     <input type="submit" value="修改">
			     <input type="hidden" name="bo_no"  value="${bonus_order_detailVO.bo_no}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/bonus_order_detail/forwarded" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除">
			     <input type="hidden" name="bo_no"  value="${bonus_order_detailVO.bo_no}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>

</body>
</html>