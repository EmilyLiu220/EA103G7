<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.food.model.*"%>
<%
	FoodService foodSvc = new FoodService();
	List<List<String>> list = foodSvc.Statistics();
	pageContext.setAttribute("list", list);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="BIG5">
<title>食材統計</title>
<table>
	<tr>
		<th>食材編號</th>
		<th>食材名稱</th>
		<th>年份</th>
		<th>月份</th>
		<th>使用量</th>
	</tr>
<%-- 	<%! int index=0;%> --%>
	<c:forEach var="data" items="${list}" >			
	    <tr class="fd_tr">
	    	<td>${data.get(0)}</td>
	    	<td>${data.get(1)}</td>
	    	<td>${data.get(2)}</td>
	    	<td>${data.get(3)}</td>
	    	<td>${data.get(4)}</td>
	    </tr>    
	</c:forEach>
</table>
</head>
<body>
	
</body>
</html>