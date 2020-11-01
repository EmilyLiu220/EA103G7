<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>
<%@ page import="java.util.*"%>

<%
  MemVO empVO = (MemVO) request.getAttribute("memVO");
%>

<html>
<head>
<script src="https://kit.fontawesome.com/d6c1e36c40.js" crossorigin="anonymous"></script>
<title>會員資料</title>

<style>

	#loc{
 		position: relative;
		top: 150px;
		z-index: 10;
		font-size: 150%;
	}
	#container{
	  	margin:0 auto;
	  	width: 1300px;
	}
	
</style>

</head>
<body>
	
<div class="wrapper">
	
	<div id="content">
	
	<div id="container">
	<div id="loc" style="margin-left:100px">
	<a class="btn btn-primary" href="select_page_mem.jsp" role="button"><i class="fas fa-home" style="font-size:25px"></i></a>
	<p><p>
	<label class="btn btn-success" style="cursor:pointer"><i class="fas fa-reply" style="font-size:25px" onclick="history.back()"></i></label>
	<p><p>
	<h3>會員資料</h3>

	<table style="border:3px #cccccc solid; text-align:center; font-size: 80%;" cellpadding="10" border='1'>
		<tr>
			<th>會員編號</th>
			<th>會員姓名</th>
			<th>帳號</th>
			<th>性別</th>
			<th>生日</th>
			<th>手機</th>
			<th>地址</th>
			<th>e-mail</th>
			
		</tr>
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
		
	</table>
	
	<p><p>
	
	<h3>會員狀態</h3>
	
	<table style="border:3px #cccccc solid; text-align:center; font-size: 80%;" cellpadding="10" border='1'>
		<tr>
			<th>紅利點數</th>
			<th>是否可訂餐</th>
			<th>是否可訂位</th>
			<th>是否可評價</th>
			<th>是否可檢舉</th>
			<th>停權狀態</th>
			<th>狀態修改</th>
			
		</tr>
		
		<tr>
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
	
	</table>
	</div>
	</div>
	</div>
</div>
	
	<jsp:include page="/back-end/siderbar/siderbar.jsp" />
	
</body>
</html>