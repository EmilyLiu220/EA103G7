<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.emp_auth.model.*"%>
<%@ page import="com.fun_auth.model.*"%>
<%@ page import="java.util.*"%>

<%
  EmpVO empVO = (EmpVO) request.getAttribute("empVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
%>

<% 
  List<Emp_authVO> emp_authVO = (List<Emp_authVO>) request.getAttribute("emp_authVO"); //Emp_authServlet.java(Concroller), 存入req的emp_authVO物件
%>

<%
  List<Fun_authVO> fun_authVO = (List<Fun_authVO>) request.getAttribute("fun_authVO");
%>

<html>
<head>
<script src="https://kit.fontawesome.com/d6c1e36c40.js" crossorigin="anonymous"></script>
<title>員工資料</title>

<style>
	#loc{
 		position: relative;
		top: 150px;
		z-index: 10;
		font-size: 150%;
	}
	#container{
	  	margin:0 auto;
	  	width: 1000px;
	}
	
</style>

</head>
<body>
	
<div class="wrapper">
	
	<div id="content">
	
	<div id="container">
	<div id="loc" style="margin-left:100px">
	<a class="btn btn-primary" href="select_page.jsp" role="button"><i class="fas fa-home" style="font-size:25px"></i></a>
	<p><p>
	<label class="btn btn-success" style="cursor:pointer"><i class="fas fa-reply" style="font-size:25px" onclick="history.back()"></i></label>
	<p><p>
	<h3>員工資料</h3>

	<table style="border:3px #FFD382 dashed; text-align:center; font-size: 120%;" cellpadding="10" border='1'>
		<tr>
			<th>員工編號</th>
			<th>員工姓名</th>
			<th>在職狀態</th>
			<th>狀態修改</th>
			<th>權限修改</th>
			<th>權限刪除</th>
		</tr>
		<tr>
			<td>${empVO.emp_no}</td>
			<td>${empVO.emp_name}</td>
			<td>${(empVO.emp_sts == 1) ? "在職" : "離職"}</td>
			
			<td>
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/emp/emp.do" style="margin-bottom: 0px;">
					<label style="cursor:pointer"><i class="fas fa-wrench" style="font-size:25px"></i><input type="submit" value="狀態修改" style="display:none"></label>
					<input type="hidden" name="emp_no" value="${empVO.emp_no}">
					<input type="hidden" name="action"	value="Update_sts">
				</FORM>
			</td>
			
			<td>
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/emp/emp.do" style="margin-bottom: 0px;">
					<label style="cursor:pointer"><i class="fas fa-id-badge" style="font-size:25px"></i><input type="submit" value="權限修改" style="display:none"></label>
					<input type="hidden" name="emp_no" value="${empVO.emp_no}">
					<input type="hidden" name="action" value="Update_auth">
				</FORM>
			</td>
			
			<td>
				<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/emp/emp.do" style="margin-bottom: 0px;">
					<label style="cursor:pointer"><i class="fas fa-eraser" style="font-size:25px"></i><input type="submit" value="權限刪除" style="display:none"></label>
					<input type="hidden" name="emp_no" value="${empVO.emp_no}">
					<input type="hidden" name="emp_name" value="${empVO.emp_name}">
					<input type="hidden" name="emp_sts" value="${empVO.emp_sts}">
					<input type="hidden" name="action" value="delete_emp_auth">
				</FORM>
			</td>
		</tr>
		
	</table>
	<p><p>
	
	<span>權限內容:</span>
	
	<table style="font-size: 80%;">
		<tr>
			<c:forEach var="fun_authVO" items="${fun_authVO}" begin="0" end="3">
				<td><i class="far fa-check-circle"></i>${fun_authVO.fun_name}&emsp;</td>
			</c:forEach>
		</tr>
		<tr>
			<c:forEach var="fun_authVO" items="${fun_authVO}" begin="4" end="7">
				<td><i class="far fa-check-circle"></i>${fun_authVO.fun_name}&emsp;</td>
			</c:forEach>
		</tr>
		<tr>
			<c:forEach var="fun_authVO" items="${fun_authVO}" begin="8" end="11">
				<td><i class="far fa-check-circle"></i>${fun_authVO.fun_name}&emsp;</td>
			</c:forEach>
		</tr>
		<tr>
			<c:forEach var="fun_authVO" items="${fun_authVO}" begin="12" end="15">
				<td><i class="far fa-check-circle"></i>${fun_authVO.fun_name}&emsp;</td>
			</c:forEach>
		</tr>
		<tr>
			<c:forEach var="fun_authVO" items="${fun_authVO}" begin="16" end="19">
				<td><i class="far fa-check-circle"></i>${fun_authVO.fun_name}&emsp;</td>
			</c:forEach>
		</tr>
		<tr>
			<c:forEach var="fun_authVO" items="${fun_authVO}" begin="20" end="23">
				<td><i class="far fa-check-circle"></i>${fun_authVO.fun_name}&emsp;</td>
			</c:forEach>
		</tr>
	</table>
	
	</div>
	</div>
	</div>
</div>

	<jsp:include page="/back-end/siderbar/siderbar.jsp" />

</body>
</html>