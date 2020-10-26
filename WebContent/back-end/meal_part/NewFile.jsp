<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.meal_part.model.*"%>
<%@ page import="com.food.model.*"%>
<%@ page import="com.meal.model.*"%>
<%@ page import="java.util.*"%>
<%
	MealService mealSvc = new MealService();
	List<MealVO> list = mealSvc.getAll();
	pageContext.setAttribute("list", list);
%>
<jsp:useBean id="meal_partSvt" scope="page" class="com.meal_part.model.Meal_partService" />
<jsp:useBean id="foodSvt" scope="page" class="com.food.model.FoodService" />

              
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>Insert title here</title>
</head>
<body>
<c:forEach var="mealVO" items="${list}" >
	<p>${meal_partSvt.getNut(mealVO.meal_no).get("fat")}</p>
</c:forEach> 

</body>
</html>