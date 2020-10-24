<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="com.meal_set.model.*"%>

<%
     MealSetService mealSetSrv = new MealSetService(); 
     List<MealSetVO> list = mealSetSrv.getAll(); 
     request.setAttribute("list",list);
%>

<jsp:useBean id="mealCatSrv" class="com.meal_category.model.MealCatService"/>


<html>
<head>


<style>
  table {
	width: 900px;
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
<meta charset="BIG5">
<title>餐點管理 - Meal_Manage</title>
</head>
<body>
<table id="table-1">
	<tr><td>
		 <h3>套餐管理</h3>
		 <h4><a  href="<%= request.getContextPath() %>/back-end/back-index.jsp"><img id="index" src="<%= request.getContextPath() %>/back-end/meal/meal_Img/back1.gif" width="100px" height="32px" border="0">回首頁</a></h4>
	</td></tr>
</table>
<c:if test="${not empty errormsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
	    <c:forEach var="message" items="${errormsgs}">
			<li style="color:red">${message.value}</li>
		</c:forEach>
	</ul>
</c:if>
<a href="<%= request.getContextPath() %>/back-end/meal_set/insertMealSet.jsp">新增套餐</a>
	<table>
	<tr>
		<th>餐點編號</th>
		<th>餐點名稱</th>
		<th>餐點描述</th>
		<th>圖片</th>
		<th>價格</th>
		<th>種類</th>
		<th>上下架狀態</th>
		
	</tr>
	<c:forEach var="mealSetVO" items="${list}">
		
		<tr>
			<td>${mealSetVO.meal_set_no}</td>
			<td>${mealSetVO.meal_set_name}</td>
			<td>${mealSetVO.meal_set_info}</td>
			<td><img name="meal_set_img" src="<%= request.getContextPath() %>/meal_set/mealSet.showPic?meal_set_img=${mealSetVO.meal_set_no}"/></td>
			<td>${mealSetVO.meal_set_price}</td>
			<td>
			<c:forEach var="mealCatVO" items="${mealCatSrv.all}">
                    <c:if test="${mealSetVO.cat_no == mealCatVO.cat_no}">
	                    ${mealCatVO.cat_name}
                    </c:if>
                </c:forEach>
			</td>
			<c:if test="${mealSetVO.meal_set_sts == 0}">
			<td>已下架</td></c:if>
			<c:if test="${mealSetVO.meal_set_sts == 1}">
			<td>上架中</td></c:if> 
			
			<td>
			  <FORM METHOD="post" ACTION="<%= request.getContextPath() %>/meal_set/mealSet.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改套餐">
			     <input type="hidden" name="meal_set_no"  value="${mealSetVO.meal_set_no}">
			     <input type="hidden" name="action"	value="getOneupdate"></FORM>
			</td>
			
		</tr>
	</c:forEach>
	</table>


</body>
</html>