<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.emp.model.*"%>

<%
  EmpVO empVO = (EmpVO) request.getAttribute("empVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>

<title>員工資料新增</title>

<style>
	
	#loc{
 		position: relative;
		top: 150px;
		z-index: 10;
		font-size: 150%;
	}
	
	#container{
	  	margin:0 auto;
	  	width: 1200px;
	}
	
	td {
		width: 300px;
	}
</style>

</head>
<body>
	
<div class="wrapper">
	
	<div id="content">
	
	<div id="container">
	<div id="loc" style="margin-left:135px">
	<a class="btn btn-primary" href="select_page.jsp" role="button"><i class="fas fa-home" style="font-size:25px"></i></a>
	<p><p>
	<label class="btn btn-success" style="cursor:pointer"><i class="fas fa-reply" style="font-size:25px" onclick="history.back()"></i></label>
	<p><p>
	<h3 style="font-size: 120%;">員工資料新增</h3>
	
	<h5 style="font-size: 100%;">員工新增:</h5>
	
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color:red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color:red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	
	<FORM METHOD="post" ACTION="emp.do" name="form1">
	<table  style="font-size: 120%;">
		<tr>
			<td>員工姓名:<font color=red><b>*</b></font></td>
			<td><input type="TEXT" name="emp_name" size="20" value="" placeholder="請輸入員工姓名" required/></td>
		</tr>
	</table>
	<br>
	
	<jsp:useBean id="fun_authSvc" scope="page" class="com.fun_auth.model.Fun_authService" />
		
		權限新增:<font color=red><b>*</b></font><p><p>
	<table style="font-size: 80%;">
		<tr>
			<c:forEach var="fun_authVO" items="${fun_authSvc.all}" begin="0" end="4">
				<td><span class="textcolor"><label><input class="check1" name="fun_no[]" type="checkbox" value="${fun_authVO.fun_no}">${fun_authVO.fun_name}&emsp;</label></span></td>
			</c:forEach>
		</tr>
		<tr>
			<c:forEach var="fun_authVO" items="${fun_authSvc.all}" begin="5" end="9">
				<td><span class="textcolor"><label><input class="check1" name="fun_no[]" type="checkbox" value="${fun_authVO.fun_no}">${fun_authVO.fun_name}&emsp;</label></span></td>
			</c:forEach>
		</tr>
		<tr>
			<c:forEach var="fun_authVO" items="${fun_authSvc.all}" begin="10" end="14">
				<td><span class="textcolor"><label><input class="check1" name="fun_no[]" type="checkbox" value="${fun_authVO.fun_no}">${fun_authVO.fun_name}&emsp;</label></span></td>
			</c:forEach>
		</tr>
		<tr>
			<c:forEach var="fun_authVO" items="${fun_authSvc.all}" begin="15" end="19">
				<td><span class="textcolor"><label><input class="check1" name="fun_no[]" type="checkbox" value="${fun_authVO.fun_no}">${fun_authVO.fun_name}&emsp;</label></span></td>
			</c:forEach>
		</tr>
		<tr>
			<c:forEach var="fun_authVO" items="${fun_authSvc.all}" begin="20" end="24">
				<td><span class="textcolor"><label><input class="check1" name="fun_no[]" type="checkbox" value="${fun_authVO.fun_no}">${fun_authVO.fun_name}&emsp;</label></span></td>
			</c:forEach>
		</tr>
	</table>
	
	
	<input type="hidden" name="action" value="insert">
	<input type="submit" id="ok" class="btn btn-outline-primary" value="送出新增">
	</FORM>
	</div>
	</div>
	</div>
</div>
	
	<jsp:include page="/back-end/siderbar/siderbar.jsp" />
	
<script>

	//在沒勾選任何權限的情況下無法送出
	var btn = document.getElementById("ok");
	
	btn.onclick = function(e){
	    var obj = document.getElementsByName("fun_no[]");
	    var selected = [];
	    for (var i = 0; i < obj.length; i++) {
	        if (obj[i].checked) {
	            selected.push(obj[i].value);
	        }
	    }
	    if(selected.length === 0) {
	      	alert('請至少勾選一項權限！');
	      	e.preventDefault();
	    }
	};

</script>
	

	
</body>
</html>