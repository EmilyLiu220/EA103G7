<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.bonus.model.*"%>
<%@ page import="com.bonus_order.model.*"%>
<%@ page import="com.bonus_order_detail.model.*"%>

<%
	BonusService bonusSvc = new BonusService();
	List<BonusVO> list = bonusSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<%
// 	BonusVO bonusVO = (BonusVO) request.getAttribute("bonusVO");
%>
<jsp:useBean id="bonuswSvc" scope="page" class="com.bonus.model.BonusService" /> 
	
<%
// 	Bonus_OrderVO bonus_orderVO = (Bonus_OrderVO) request.getAttribute("bonus_orderVO"); 
%>
<%-- <jsp:useBean id="bonus_orderSvc" scope="page" --%>
<%-- 	class="com.bonus_order.model.Bonus_OrderService" /> --%>

<%
// 	Bonus_Order_DetailVO bonus_order_detailVO = (Bonus_Order_DetailVO) request.getAttribute("bonus_order_detailVO");  
%>
<jsp:useBean id="bonus_order_detailSvc" scope="page" class="com.bonus_order_detail.model.Bonus_Order_DetailService" /> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>紅利商品訂單明細</title>

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
<%-- 錯誤表列 --%>
			<c:if test="${not empty errorMsgs}">
				<font style="color: red">請修正以下錯誤:</font>
				<ul>
					<c:forEach var="message" items="${errorMsgs}">
						<li style="color: red">${message}</li>
					</c:forEach>
				</ul>
			</c:if>
			<br>
			<table class="table table-hover" style="width: 100%; font-size: 90%;">
				<thead style="text-align: center;">
					<tr>
						<th style="width: 25%;">紅利商品訂單編號</th>
						<th style="width: 25%;">紅利商品</th>
						<th style="width: 25%;">圖片預覽</th>
						<th style="width: 25%;">狀態</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td style="text-align: center;">${bonus_orderVO.bo_no}</td>
						<td style="text-align: center;">${bonuswSvc.getOneBonus((bonus_order_detailSvc.getOneBonus_Order_Detail(bonus_orderVO.bo_no).bns_no)).bns_name}</td>
						<td><img src="<%=request.getContextPath() %>/bonus/bonus.showPic?bonus_img=${bonus_order_detailSvc.getOneBonus_Order_Detail(bonus_orderVO.bo_no).bns_no}"></td>
						<td style="text-align: center;"><span style="color:red;">兌換成功</span></td>
					</tr>
				</tbody>
			</table>
			<%@ include file="/front-end/footer.jsp"%>
</body>
</html>