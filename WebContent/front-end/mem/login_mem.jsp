<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<title>會員登入</title>

    <title>Feliciano - Free Bootstrap 4 Template by Colorlib</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Great+Vibes&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/front-end/front/css/open-iconic-bootstrap.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/front-end/front/css/animate.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/front-end/front/css/owl.carousel.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/front-end/front/css/owl.theme.default.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/front-end/front/css/magnific-popup.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/front-end/front/css/aos.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/front-end/front/css/ionicons.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/front-end/front/css/bootstrap-datepicker.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/front-end/front/css/jquery.timepicker.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/front-end/front/css/flaticon.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/front-end/front/css/icomoon.css">
    <link rel="stylesheet" href="<%=request.getContextPath() %>/front-end/front/css/style.css">
    
<style>

#container{
  padding-top: 50px;
  margin:0 auto;
  width: 800px;
}

td {
	width: 100px;
}

</style>
    
</head>
<body>
	
	<%@ include file="/front-end/headfinish.jsp"%>
	
	<div id="container">
	<div style="margin-left: 200px;">
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color:red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color:red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	</div>
	
	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front-end/mem/mem.do" style="margin-bottom: 0px;">
		<table style="width: 400px; margin-left: 200px; background-color:#eee;">
			<tr>
				<td colspan=2 align=center>
					<h2>會員登入</h2>
				</td>
			</tr>
			<tr>
				<td>
					<p align=right>
						<b style="font-size:30px;">帳號:</b>
				</td>
				<td>
					<p align=left>
						<input type=text name="account" value="" size=20 required>
				</td>
			</tr>
			<tr>
				<td>
					<p align=right>
						<b style="font-size:30px;">密碼:</b>
				</td>
				<td>
					<p align=left>
						<input type=password name="password" value="" size=20 required>
				</td>
			</tr>
			<tr>
				<td colspan=2 align=center>
					
					<input type=submit value="登入">
					<input type="hidden" name="action" value="login">
					
				</td>
			</tr>
		</table>
			
		<h4 style="margin-left:350px"><a href="<%=request.getContextPath()%>/front-end/mem/forgetPsw.jsp">忘記密碼?</a></h4>
			
	</FORM>
	</div>
		
		<jsp:include page="/front-end/front/footer.jsp" />
        
</body>
</html>