<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.food.model.*"%>

<%
  FoodVO foodVO = (FoodVO) request.getAttribute("foodVO"); 
%>

<html>
<head>
<title>修改食材</title>
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
					<h3 style="margin-bottom: 0;">食材管理</h3>
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
	<form method="post" action="<%=request.getContextPath()%>/food/food.do">
	<input type="hidden" name="action" value="update">
	<table class="table table-bordered table-hover table-striped text-center">
	<tr>
		<td>食材編號</td>
		<td><%=foodVO.getFd_no()%>
			<input type="hidden" name="fd_no" value=<%=foodVO.getFd_no()%>>
		</td>
	</tr>
	<tr>
		<td>食材名稱</td>
		<td><input class="input" type="text" placeholder="食材名稱" name="fd_name" value=
			<%=foodVO.getFd_name()%>>
		</td>
	</tr>
	<tr>
		<td>庫存數量</td>
		<td><input class="input" type="text" placeholder="庫存數量" name="fd_stk" value=
			<%=foodVO.getFd_stk()%>>
		</td>
	</tr>
	<tr>	
		<td>庫存底線</td>
		<td>
			<input class="input" type="text" placeholder="庫存底線" name="stk_ll" value=
			<%=foodVO.getStk_ll()%>>
		</td>
	</tr>
	<tr>
		<td>卡路里</td>
		<td>
			<input class="input" type="text" placeholder="卡路里" name="cal" value=
			<%=foodVO.getCal()%>>
		</td>
	</tr>
	<tr>	
		<td>蛋白質</td>
		<td>
			<input class="input" type="text" placeholder="蛋白質" name="prot" value=
			<%=foodVO.getProt()%>>
		</td>
	</tr>
	<tr>	
		<td>碳水</td>
		<td>
			<input class="input" type="text" placeholder="碳水" name="carb" value=
			<%=foodVO.getCarb()%>>
		</td>
	</tr>
	<tr>	
		<td>脂肪</td>
		<td>
			<input class="input" type="text" placeholder="脂肪" name="fat" value=
			<%=foodVO.getFat()%>>
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