<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.emp.model.*"%>

<%
  EmpVO empVO = (EmpVO) request.getAttribute("empVO");
%>

<html>
<head>
<script src="https://kit.fontawesome.com/d6c1e36c40.js" crossorigin="anonymous"></script>

<title>員工個資修改</title>

<style>
	#loc{
 		position: relative;
		top: 150px;
		z-index: 10;
		font-size: 150%;
	}
	#container{
	  	margin:0 auto;
	  	width: 600px;
	}
</style>

</head>
<body>

<div class="wrapper">
	
	<div id="content">

	<div id="container">
	<div id="loc">

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color:red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color:red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	
	<label class="btn btn-success" style="cursor:pointer"><i class="fas fa-reply" style="font-size:25px" onclick="history.back()"></i></label>
	<p><p>
	
	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/emp/emp.do" style="margin-bottom: 0px;">
	<table style="font-size: 120%;">
		<tr>
			<th colspan=2 align=center>
				<h4 style="font-size: 120%; text-align:center;">&emsp;&emsp;員&ensp;工&ensp;個&ensp;資&ensp;修&ensp;改</h4>
			</th>
		</tr>
		<tr>
			<td><i class="fas fa-user"></i>&ensp;員工編號:</td><td>${empVO.emp_no}</td>
		</tr>
		<tr>
			<td><i class="fas fa-user"></i>&ensp;員工姓名:</td><td><input type="text" name="emp_name" value="<%=empVO.getEmp_name()%>" required></td>
		</tr>
		<tr>
			<td><i class="fas fa-user"></i>&ensp;密碼修改:</td><td><input type="password" name="emp_psw1" value="" required></td>
		</tr>
		<tr>
			<td><i class="fas fa-user"></i>&ensp;密碼確認:</td><td><input type="password" name="emp_psw2" value="" required></td>
		</tr>
		<tr>
			<td colspan=2 align=center>
						
				<input type="submit" id="ok" class="btn btn-outline-primary" value="確認送出">
				<input type="hidden" name="emp_no" value="${empVO.emp_no}">
				<input type="hidden" name="action" value="update_i">
						
			</td>
		</tr>
	</table>
	</FORM>
	</div>
	</div>
	</div>
</div>

<script>

	var emp_psw1 = document.getElementsByName("emp_psw1");
	var emp_psw2 = document.getElementsByName("emp_psw2");
	var ok = document.getElementById("ok");
	
	ok.addEventListener("click", function(e) {
		if (emp_psw1[0].value !== "" && emp_psw2[0].value !== "" && emp_psw1[0].value === emp_psw2[0].value) {
			alert("修改成功！");
		}
	});

</script>
	
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.6/umd/popper.min.js" integrity="sha384-wHAiFfRlMFy6i5SRaxvfOCifBUQy1xHdJ/yoi7FRNXMRBu5WHdZYu1hA6ZOblgut" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/js/bootstrap.min.js" integrity="sha384-B0UglyR+jN6CkvvICOB2joaf5I4l3gm9GU6Hc1og6Ls7i6U/mkkaduKaBhlAXv9k" crossorigin="anonymous"></script>
	
	<jsp:include page="/back-end/siderbar/siderbar.jsp" />

</body>
</html>