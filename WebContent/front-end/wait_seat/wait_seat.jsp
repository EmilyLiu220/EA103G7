<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.wait_seat.model.*"%>
<% 
	Wait_seatService wait_seatSvc = new Wait_seatService();
	List<Wait_seatVO> list = wait_seatSvc.getAllForUser();
	pageContext.setAttribute("list", list);
%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<style>
	#show{
		border-collapse:collapse;
	}
	#show,#show th,#show td{
		border:1px black solid;
	}
</style>
</head>
<body>
	<%="下一位編號:"+wait_seatSvc.getFirst().getWait_seat_no()%>
	<br>
	<%="等待人數:"+wait_seatSvc.getCount()+"人"%>
	<table id="show">
		<tr>
			<th class="text-center">候位編號</td>
			<th class="text-center">手機號碼</td>
		</tr>
	<c:forEach var="wait_seatVO" items="${list}" >
		<tr class="fd_tr">                    	
			<td>${wait_seatVO.wait_seat_no}</td>
			<td>${wait_seatVO.phone_m}</td>
		</tr>						
	</c:forEach>
	</table>
</body>
</html>