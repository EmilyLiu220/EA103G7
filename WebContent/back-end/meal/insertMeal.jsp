<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.meal.model.*"%>
<%@ page import="java.util.*"%>
<% MealVO mealVO = (MealVO) request.getAttribute("mealVO"); 
	Map<String, String> errormsgs = (LinkedHashMap) request.getAttribute("errormsgs");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>mealUpdate</title>
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
  img{
  	width:250px;
  	height:180px;
  }
  textarea{
  resize:none;
 
  }
  #index{
  	width:100;
  	height:32;
  }
</style>

</head>
<body>
<table id="table-1">
	<tr><td>
		 <h3>新增餐點</h3>
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

<jsp:useBean id="mealCatSrv" class="com.meal_category.model.MealCatService"/>
<jsp:useBean id="foodSrv" class="com.food.model.FoodService"/>
<FORM METHOD="post" ACTION="<%= request.getContextPath() %>/meal/meal.do" enctype="multipart/form-data">
<table id="table">
<!-- 	<tr> -->
<!-- 		<td>餐點編號:</td> -->
<%-- 		<td><%= mealVO.getMeal_no()%></td> --%>
		
<!-- 	</tr> -->
	<tr>
		<td>餐點名稱:</td>
		<td><input type="TEXT" name="meal_name" size="45" /></td>
<%-- 		<td><%= errormsgs.get("mealName")%></td> --%>
	</tr>
		
<%-- 		<c:out value="${errormsgs['mealName']}"/> --%>
	
	<tr>
		<td>餐點描述:</td>
		<td><textarea  cols="45" rows="5"  maxlength="220" name="meal_info" ></textarea></td>
<%-- 		<td><%= errormsgs.get("mealInfo")%></td> --%>
	</tr>
	
<%-- 		<c:out value="${errormsgs['mealInfo']}"/> --%>
	<tr>
		<td>圖片:</td>
		<td>
<%-- 		<img name="meal_img" src="<%= request.getContextPath() %>/meal/meal.showPic?meal_img=${mealVO.meal_no}"/> --%>
		<input type="file" name="meal_img" size="45"/></td>
<%-- 			 <td><%= errormsgs.get("mealImg")%></td> --%>
	</tr>
<%-- 			 <c:out value="${errormsgs['mealImg']}"/> --%>
	<tr>
		<td>價格:</td>
		<td><input type="TEXT" name="meal_price" size="45"/></td>
<%-- 			 <td><%= errormsgs.get("mealPrice")%></td> --%>
	</tr>
<%-- 			 <c:out value="${errormsgs['mealPrice']}"/> --%>
	<tr>
		<td>種類:</td>
		<td> <select name="cat_no">
			 <c:forEach var="mealCatVO" items="${mealCatSrv.all}">
			 <option value="${mealCatVO.cat_no}">${mealCatVO.cat_name}
			 </c:forEach>
			 </select>
		</td>
			
			 
<%-- 			 <td><%= errormsgs.get("mealCat")%></td> --%>
	</tr>
<%-- 			 <c:out value="${errormsgs['mealCat']}"/> --%>
	<tr>
				<td>食材組成:</td>
				<td><select name="fd_no">
						<c:forEach var="foodVO" items="${foodSrv.all}">
							<option value="${foodVO.fd_no}">${foodVO.fd_name}
						</c:forEach>
				</select>
				<input type="text" name="fd_gw" size="15"/> 公克
				
				<td><button id="add">+</button><br><button id="remove">-</button></td>
			</tr>
<!-- 	<tr> -->
<!-- 		<td>上下架狀態:<font color=red><b>*</b></font></td> -->
<!-- 		<td><select size="1" name="meal_sts"> -->
<!-- 				<option value="0"  >未上架 -->
<!-- 				<option value="1"  >已上架 -->
<!-- 		</select></td> -->
<!-- 	</tr> -->

</table>
<br>
<input type="hidden" name="action" value="insert">
<!-- <input type="hidden" name="meal_no"> -->
<input type="submit" value="新增餐點"></FORM>

<script>
var table = document.getElementById("table");
var remove = document.getElementById("remove");
remove.addEventListener("click",function(e){
	e.preventDefault();
	$(".newtr:last-child").remove();
	
});
var add = document.getElementById("add");
add.addEventListener("click", function (e) {
	
	var tr = document.createElement("tr");
	tr.classList.add("newtr");

    tr.innerHTML = `
    	<td>食材組成:</td>
		<td><select name="fd_no">
				<c:forEach var="foodVO" items="${foodSrv.all}">
					<option value="${foodVO.fd_no}">${foodVO.fd_name}
				</c:forEach>
		</select>
		<input type="text" name="fd_gw" size="15"/> 公克
		</td>`;
    table.append(tr);
    e.preventDefault();
});

</script>


 <script src="<%= request.getContextPath() %>/front-end/js/jquery-3.4.1.min.js"></script>
  <script src="<%= request.getContextPath() %>/front-end/js/popper.min.js"></script>
  <script src="<%= request.getContextPath() %>/front-end/js/bootstrap.min.js"></script>

</body>
</html>