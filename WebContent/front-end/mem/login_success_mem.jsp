<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%@ page import="com.mem.model.*"%>
    
<html>
<head>
<title>會員登入成功</title>
    
<style>

#container{
  padding-top: 50px;
  padding-bottom: 50px;
  margin:0 auto;
  width: 600px;
}

</style>
    
</head>
<body>

	<%@ include file="/front-end/headfinish.jsp"%>

	<div id="container">
	<h3 style="margin-left:240px">功能列表</h3><br>
	<FORM METHOD="post" ACTION="mem.do" name="form1">
		<input type="submit" value="個資查詢"  style="margin-left:250px">
		<input type="hidden" name="mem_no" value="${memVO2.mem_no}">
		<input type="hidden" name="action" value="check_info">
	</FORM>
	
	<FORM METHOD="post" ACTION="mem.do" name="form1">
		<input type="submit" value="個資和密碼修改"  style="margin-left:225px">
		<input type="hidden" name="mem_no" value="${memVO2.mem_no}">
		<input type="hidden" name="action" value="Update_info">
	</FORM>
		
<!-- 	<FORM METHOD="post" ACTION="mem.do" name="form1"> -->
<!-- 		<input type="submit" value="登出"  style="margin-left:265px"> -->
<!-- 		<input type="hidden" name="action" value="logout"> -->
<!-- 	</FORM> -->
	</div>
	
	<jsp:include page="/front-end/front/footer.jsp" />
	
</body>
</html>