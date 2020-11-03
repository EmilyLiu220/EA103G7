<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.mem.model.*"%>

<% 
	MemVO memVO = (MemVO) request.getAttribute("memVO");
%>
<%
	String x = (String) request.getAttribute("x"); // 判斷修改成功用
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>會員資料</title>

<style>

#container{
  padding-top: 50px;
  padding-bottom: 50px;
  margin:0 auto;
  width: 800px;
}

.t{
	text-align: right;
}

</style>

</head>
<body onload="hint()">
	
	<%@ include file="/front-end/headfinish.jsp"%>
	
<!-- 	<h4><a href="select_page.jsp">回主頁</a></h4> -->

	<div id="container">
	<h3 style="text-align:center">會員資料</h3>
	
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color:red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color:red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	
	<FORM METHOD="post" ACTION="mem.do" name="form1">
	<table border="1" style="width:600px; margin-left:100px; background-color:#eee;" class="table table-striped">
		<tr>
			<td class="t">會員編號:&emsp;</td>
			<td>&emsp;${memVO.mem_no}</td>
		</tr>
		<tr>
			<td class="t">會員姓名:&emsp;</td>
			<td>&emsp;${memVO.mem_name}</td>
		</tr>
		<tr>
			<td class="t">帳號:&emsp;</td>
			<td>&emsp;${memVO.mem_act}</td>
		</tr>
		<tr>
			<td class="t">性別:&emsp;</td>
			<td>&emsp;${memVO.mem_gen}</td>
		</tr>
		<tr>
			<td class="t">生日:&emsp;</td>
			<td>&emsp;${memVO.mem_bir}</td>
		</tr>
		<tr>
			<td class="t">手機:&emsp;</td>
			<td>&emsp;${memVO.mem_tel}</td>
		</tr>
		<tr>
			<td class="t">地址:&emsp;</td>
			<td>&emsp;${memVO.mem_adrs}</td>
		</tr>
		<tr>
			<td class="t">e-mail:&emsp;</td>
			<td>&emsp;${memVO.mem_mail}</td>
		</tr>
		<tr>
			<td class="t">紅利點數:&emsp;</td>
			<td>&emsp;${(memVO.mem_bns == null) ? 0 : memVO.mem_bns}</td>
		</tr>
<!-- 		<tr> -->
<!-- 			<td class="t">是否可訂餐:&emsp;</td> -->
<%-- 			<td>&emsp;${(memVO.mem_od_m == 0) ? "不可" : "可"}</td> --%>
<!-- 		</tr> -->
<!-- 		<tr> -->
<!-- 			<td class="t">是否可訂位:&emsp;</td> -->
<%-- 			<td>&emsp;${(memVO.mem_od_r == 0) ? "不可" : "可"}</td> --%>
<!-- 		</tr> -->
<!-- 		<tr> -->
<!-- 			<td class="t">是否可評價:&emsp;</td> -->
<%-- 			<td>&emsp;${(memVO.mem_review == 0) ? "不可" : "可"}</td> --%>
<!-- 		</tr> -->
<!-- 		<tr> -->
<!-- 			<td class="t">是否可檢舉:&emsp;</td> -->
<%-- 			<td>&emsp;${(memVO.mem_repo == 0) ? "不可" : "可"}</td> --%>
<!-- 		</tr> -->
	</table>
		
		<p><p>
		
		<input type="submit" value="個資和密碼修改" style="margin-left:335px">
		<input type="hidden" name="mem_no" value="${memVO.mem_no}">
		<input type="hidden" name="action" value="Update_info">
	
	</FORM>
	
	<form action="<%=request.getContextPath() %>/front-end/mem/login_success_mem.jsp">
		<input type="submit" value="回功能列表" style="margin-left:350px">
	</form>
	
	</div>
	
	<jsp:include page="/front-end/front/footer.jsp" />
	
<!--sweet alert -->
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
	
<script>
	
	<!--判斷修改成功用-->
	var x = `${x}`;
	function hint() {
		if (x === "success") {
// 			alert("恭喜您！修改成功！");
			swal({
				  title: "恭喜您！修改成功！",
				  text: "3秒後自動關閉。",
				  icon: "success",
				  timer: 3000,
				  showConfirmButton: true
				});
		}
	}
	
</script>
	
</body>
</html>