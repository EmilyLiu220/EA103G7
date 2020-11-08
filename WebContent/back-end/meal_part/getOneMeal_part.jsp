<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.meal_part.model.*"%>

<%
  Meal_partVO meal_partVO = (Meal_partVO) request.getAttribute("meal_partVO");
%>

<jsp:useBean id="foodSvt" scope="page" class="com.food.model.FoodService" />
<jsp:useBean id="mealSvt" scope="page" class="com.meal.model.MealService" />

<html>
<head>
<title>修改餐點組成</title>
<style>
	.loc{
		z-index: 10;
	}
	#top{
		top:100px;
		position:absolute;
	}
	td{
		width:40%;
		border:1px block solid;
	}
	
	#table-1, #table-1 td {
		background: #555;
		color: #fff;
		border: 0;
		width: 100%;
		height: 70;
		border-radius: 5px;
		text-align: center;
		box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);
	}
	.input{
		text-align: center; 
		background-color
	}
</style>
</head>
<body>
<div class="wrapper" id="top">
		<div class=loc>
	<div id="content" class="mb-2 bt-4">
		
		<table id="table-1">
			<tr>
				<td>
					<h3 style="margin-bottom: 0;">餐點組成管理</h3>
				</td>
			</tr>
		</table>
		<div>
			<c:if test="${not empty updateErrorMsgs}">
				<font style="color:red">請修正以下錯誤:</font>
				<ul>
				<c:forEach var="message" items="${updateErrorMsgs}">
					<li style="color:red">${message}</li>
				</c:forEach>
				</ul>
			</c:if>
		</div>
	<form method="post" action="<%=request.getContextPath()%>/meal_part/meal_part.do">
	<input type="hidden" name="action" value="update">
	<table class="table table-bordered table-hover table-striped text-center">
	<tr>
		<td>餐點名稱</td>
		<td><%=mealSvt.searchByNo(meal_partVO.getMeal_no()).getMeal_name()%>
			<input type="hidden" name="meal_no" value=<%=meal_partVO.getMeal_no()%>>
		</td>
	</tr>
	<tr>
		<td>食材名稱</td>
		<td><%=foodSvt.getOneFood(meal_partVO.getFd_no()).getFd_name()%>
			<input type="hidden" name="fd_no" value=<%=meal_partVO.getFd_no()%>>
		</td>
	</tr>
	<tr>
		<td>食材重量</td>
		<td><input class="input" type="text" placeholder="食材重量" name="fd_gw" value=
			<%=meal_partVO.getFd_gw()%>>
		</td>
	</tr>
	<tr>
		<td colspan="2" tes>
			<input class="btn btn-secondary" type="submit" value="確定">
			<input class="btn btn-secondary" type="reset" value="重設" >
		</td>
	</tr>
	</table>
</form>
</div></div></div>	
<jsp:include page="/back-end/siderbar/siderbar.jsp" />
<script src="<%=request.getContextPath()%>/back-end/js/bootstrap.bundle.min.js"></script>
<script src="<%=request.getContextPath()%>/back-end/js/jquery.dataTables.min.js"></script>
<script src="<%=request.getContextPath()%>/back-end/js/dataTables.bootstrap4.min.js"></script>
</body>
</html>