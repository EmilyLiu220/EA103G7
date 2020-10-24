<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.* , com.meal.model.*,com.meal_set.model.*" %>
<html>
<head>
<link rel="stylesheet" href="<%= request.getContextPath() %>/front-end/css/bootstrap.min.css">
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <title>購物車</title>
 <style>
 table th,td{
 text-align: center;
 }
 #qty{
 width:100px;
 }
 form{
 display: inline;
 margin: 2px;
 }
 .removebtn ,.incrsbtn,.removebtn2,.incrsbtn2{
 text-align:center;
  width:24px; 
  height:24px; 
 background-color:orange;
  color:#fff;
  border-radius:50%;
  border:1px solid black;
  cursor:pointer;
 	font-size: 14px;
 	font-weight: bolder;
 	padding:1px;
 	
 }
 
 
 </style>
</head>
<body bgcolor="#FFFFFF">
<%  
	Vector<MealVO> rsvMealList = (Vector<MealVO>) session.getAttribute("rsvMealCart");
	Vector<MealSetVO> rsvSetList = (Vector<MealSetVO>) session.getAttribute("rsvSetCart");

%>
 <div class="row">
    <div class="col">餐點名稱</div>
    <div class="col">單價</div>
    <div class="col">數量</div>
    <div class="col">小計</div>
  </div>
  <c:if test="${not empty rsvMealList}">
   <c:forEach var="mealVO" items="${rsvMealList}">
   <div class="row">
    <div class="col">${mealVO.meal_name}</div>
    <div class="col">${mealVO.meal_price}</div>
    <div class="col">
    <button class="increaseBtn">-</button>
    <input type="hidden" value="${mealVO.meal_no}"/>
    ${mealVO.meal_qty}
    <button class="decreaseBtn">+</button>
    <input type="hidden" value="${mealVO.meal_no}"/>
    </div>
    <div class="col">${mealVO.meal_price * mealVO.meal_qty}</div>
  </div>
  </c:forEach>
</c:if>
  <c:if test="${not empty rsvSetList}">
   <c:forEach var="mealSetVO" items="${rsvSetList}">
   <div class="row">
    <div class="col">${mealSetVO.meal_set_name}</div>
    <div class="col">${mealSetVO.meal_set_price}</div>
    <div class="col">${mealSetVO.meal_set_qty}</div>
    <div class="col">${mealSetVO.meal_set_price * mealSetVO.meal_set_qty}</div>
  </div>
  </c:forEach>
</c:if>

  <div class="row">
    <div class="col">checkbtn</div>
  </div>
<script>



</script>

<script src="<%= request.getContextPath() %>/front-end/js/jquery-3.4.1.min.js"></script>
<script src="<%= request.getContextPath() %>/front-end/js/popper.min.js"></script>
<script src="<%= request.getContextPath() %>/front-end/js/bootstrap.min.js"></script>

</body>
</html>