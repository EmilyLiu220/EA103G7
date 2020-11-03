<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="com.mem.model.*"%>

<%
  MemVO memVO = (MemVO) request.getAttribute("memVO");
%>

<%
	MemService memSvc = new MemService();
    List<MemVO> list = memSvc.getAll();
    pageContext.setAttribute("list",list);
%>

<html>
<head>
<script src="https://kit.fontawesome.com/d6c1e36c40.js" crossorigin="anonymous"></script>
<title>所有會員資料</title>

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
	
</style>

</head>
<body>
	
<div class="wrapper">
	
	<div id="content">
	
	<div id="container">
	<div id="loc" style="margin-left:80px">
	<a class="btn btn-primary" href="select_page_mem.jsp" role="button"><i class="fas fa-home" style="font-size:25px"></i></a>
	<p><p>
	<label class="btn btn-success" style="cursor:pointer"><i class="fas fa-reply" style="font-size:25px" onclick="history.back()"></i></label>
	<p><p>
	<h3>所有會員資料</h3>
	
	<jsp:useBean id="memSvc2" scope="page" class="com.mem.model.MemService" />
	
	<div>
		<FORM METHOD="post" ACTION="mem.do" >
	        <b>輸入會員姓名 :</b>
	        <input type="text" name="mem_name" placeholder="王小明" size="6px" required>
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
	
	<table style="border:3px #cccccc solid;  text-align:center; font-size: 80%;" cellpadding="10" border='1'>
		<tr>
			<th>會員編號</th>
			<th>姓名</th>
			<th>帳號</th>
			<th>性別</th>
			<th>生日</th>
			<th>手機</th>
			<th>地址</th>
			<th>e-mail</th>
			
		</tr>
		<%@ include file="page1.file" %> 
		<c:forEach var="memVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
			
			<tr>
				<td>${memVO.mem_no}</td>
				<td>${memVO.mem_name}</td>
				<td>${memVO.mem_act}</td>
				<td>${memVO.mem_gen}</td>
				<td>${memVO.mem_bir}</td>
				<td>${memVO.mem_tel}</td>
				<td>${memVO.mem_adrs}</td>
				<td>${memVO.mem_mail}</td>
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