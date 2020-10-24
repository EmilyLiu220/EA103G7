<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="com.meal.model.*"%>

<%
     MealService mealSrv = new MealService(); 
     List<MealVO> list = mealSrv.getAll(); 
     request.setAttribute("list",list);
%>

<html>
<head>
<link rel="stylesheet" href="<%= request.getContextPath() %>/front-end/css/bootstrap.min.css">
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <title>瀏覽菜單</title>
 <style>
  table {
	width: 1100px;
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
  img{
  	width:250px;
  	height:180px;
  }
  #index{
  	width:100;
  	height:32;
  }
</style>
</head>
<body bgcolor="#FFFFFF">
<font size="+3">瀏覽菜單</font>
<hr><p>

<table border="1" width="740">  
 <tr>
		<th>餐點名稱</th>
		<th>餐點描述</th>
		<th>圖片</th>
		<th>價格</th>
		<th>數量</th>
		
		
	</tr>
	
		
 	<%@ include file="/front-end/shopping/page1.file" %> 
	<c:forEach var="mealVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr>
			  
			<td>${mealVO.meal_name}</td>
			<td>${mealVO.meal_info}</td>
			<td><img name="meal_img" src="<%= request.getContextPath() %>/meal/meal.showPic?meal_img=${mealVO.meal_no}"/></td>
			<td>${mealVO.meal_price}</td>
			
			<td>
			<form METHOD="post" ACTION="<%= request.getContextPath() %>/Shopping.do" style="margin-bottom: 0px;">
				 <input type="text" name="meal_qty" size="3" value=1>
			     <input type="hidden" name="meal_no"  value="${mealVO.meal_no}">
			     <input type="hidden" name="meal_name"  value="${mealVO.meal_name}">
			     <input type="hidden" name="meal_price"  value="${mealVO.meal_price}">
			     <input type="hidden" name="action"	value="addMeal">
			     <input type="submit" value="加入購物車">
			</form>
			</td>
			
			
		</tr>
	</c:forEach>

</table>
<%@ include file="/front-end/shopping/page2.file" %> 
<p> 
  <jsp:include page="/front-end/shopping/cart.jsp" flush="true" />
  
  
  <script src="<%=request.getContextPath() %>/front-end/js/jquery-3.4.1.min.js"></script>
  <script src="<%=request.getContextPath() %>/front-end/js/popper.min.js"></script>
  <script src="<%=request.getContextPath() %>/front-end/js/bootstrap.min.js"></script>
</body>
</html>