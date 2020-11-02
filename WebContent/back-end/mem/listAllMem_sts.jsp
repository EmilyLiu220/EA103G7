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
<title>所有會員狀態</title>

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
	<div id="loc"  style="margin-left:100px">
	<a class="btn btn-primary" href="select_page_mem.jsp" role="button"><i class="fas fa-home" style="font-size:25px"></i></a>
	<p><p>
	<label class="btn btn-success" style="cursor:pointer"><i class="fas fa-reply" style="font-size:25px" onclick="history.back()"></i></label>
	<p><p>
	<h3>所有會員狀態</h3>
	
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
	
	<table style="border:3px #cccccc solid; text-align:center; font-size: 80%;" cellpadding="10" border='1'>
		<tr>
			<th>會員編號</th>
			<th>姓名</th>
			<th>紅利點數</th>
			<th>是否可訂餐</th>
			<th>是否可訂位</th>
			<th>是否可評價</th>
			<th>是否可檢舉</th>
			<th>停權狀態</th>
			<th>狀態修改</th>
			
		</tr>
		<%@ include file="page1.file" %> 
		<c:forEach var="memVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
			
			<tr>
				<td>${memVO.mem_no}</td>
				<td>${memVO.mem_name}</td>
				<td>${memVO.mem_bns}</td>
				<td>${(memVO.mem_od_m == 1) ? "可" : "不可"}</td>
				<td>${(memVO.mem_od_r == 1) ? "可" : "不可"}</td>
				<td>${(memVO.mem_review == 1) ? "可" : "不可"}</td>
				<td>${(memVO.mem_repo == 1) ? "可" : "不可"}</td>
				<td>${(memVO.mem_sts == 1) ? "未停權" : "停權"}</td>
					
				<td>
				  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/mem/mem.do" style="margin-bottom: 0px;">
				     <label style="cursor:pointer"><i class="fas fa-wrench" style="font-size:25px"></i><input type="submit" value="狀態修改" style="display:none"></label>
				     <input type="hidden" name="mem_no" value="${memVO.mem_no}">
				     <input type="hidden" name="action"	value="Update_sts">
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