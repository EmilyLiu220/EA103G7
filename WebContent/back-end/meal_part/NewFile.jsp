<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

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

<c:forEach var="mealVO" items="${list}" >
	${foodSvt.get_cal_by_VO(meal_partSvt.getNut(mealVO.meal_no))}
</c:forEach> 
              
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>Insert title here</title>
</head>
<body>
</body>
</html>