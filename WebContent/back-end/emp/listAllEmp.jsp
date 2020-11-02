<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>

<%
  EmpVO empVO = (EmpVO) request.getAttribute("empVO");
%>

<%
    EmpService empSvc = new EmpService();
    List<EmpVO> list = empSvc.getAll();
    pageContext.setAttribute("list",list);
%>

<html>
<head>
<script src="https://kit.fontawesome.com/d6c1e36c40.js" crossorigin="anonymous"></script>
<title>所有員工資料</title>

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
	<h3>所有員工資料</h3>
	
	<jsp:useBean id="empSvc2" scope="page" class="com.emp.model.EmpService" />
	
	<div>
		<FORM METHOD="post" ACTION="emp.do" >
	        <b>輸入員工姓名 :</b>
	        <input type="text" name="emp_name" placeholder="王小明" size="6px" required>
	        <input type="hidden" name="action" value="getOne_For_Display_ByName">
	        <input type="submit" value="送出">
	    </FORM>
	</div>
	
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color:red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color:red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	
	<table style="border:3px #FFD382 dashed; text-align:center; font-size: 120%;" cellpadding="10" border='1'>
		<tr>
			<th>員工編號</th>
			<th>員工姓名</th>
			<th>在職狀態</th>
			<th>狀態修改</th>
			<th>權限修改</th>
			<th>權限刪除</th>
		</tr>
		<%@ include file="page1.file" %> 
		<c:forEach var="empVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
			
			<tr>
				<td>${empVO.emp_no}</td>
				<td>${empVO.emp_name}</td>
				<td>${(empVO.emp_sts==1) ? "在職" : "離職"}</td>
				
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
		</c:forEach>
	</table>
	<%@ include file="page2.file" %>
	</div>
	</div>
	</div>
</div>
	
	<jsp:include page="/back-end/siderbar/siderbar.jsp" />
	
</body>
</html>