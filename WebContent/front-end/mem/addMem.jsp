<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>會員註冊</title>

<style>
.city, .town{width: 100%;}
.f1{float:left;margin-left:5px;margin-right:5px;width:calc(5% - 10px)}
.f2{float:left;margin-left:5px;margin-right:5px;width:calc(10% - 10px)}
.f3{float:left;margin-left:5px;margin-right:5px;width:calc(15% - 10px)}
.f4{float:left;margin-left:5px;margin-right:5px;width:calc(20% - 10px)}
.f5{float:left;margin-left:5px;margin-right:5px;width:calc(25% - 10px)}
.f6{float:left;margin-left:5px;margin-right:5px;width:calc(30% - 10px)}
.f7{float:left;margin-left:5px;margin-right:5px;width:calc(35% - 10px)}
.f8{float:left;margin-left:5px;margin-right:5px;width:calc(40% - 10px)}
.f9{float:left;margin-left:5px;margin-right:5px;width:calc(45% - 10px)}
.f10{float:left;margin-left:5px;margin-right:5px;width:calc(50% - 10px)}
.f11{float:left;margin-left:5px;margin-right:5px;width:calc(55% - 10px)}
.f12{float:left;margin-left:5px;margin-right:5px;width:calc(60% - 10px)}
.f13{float:left;margin-left:5px;margin-right:5px;width:calc(65% - 10px)}
.f14{float:left;margin-left:5px;margin-right:5px;width:calc(70% - 10px)}
.f15{float:left;margin-left:5px;margin-right:5px;width:calc(75% - 10px)}
.f16{float:left;margin-left:5px;margin-right:5px;width:calc(80% - 10px)}
.f17{float:left;margin-left:5px;margin-right:5px;width:calc(85% - 10px)}
.f18{float:left;margin-left:5px;margin-right:5px;width:calc(90% - 10px)}
.f19{float:left;margin-left:5px;margin-right:5px;width:calc(95% - 10px)}
.f20{float:left;margin-left:5px;margin-right:5px;width:calc(100% - 10px)}
</style>


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
<!-- 	<h4><a href="select_page.jsp">回主頁</a></h4> -->
	
	<div id="container">
	<h3 style="text-align:center">會員註冊</h3>
	
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
<!-- 	<table> -->
<!-- 		<tr> -->
<!-- 			<td>會員姓名:<font color=red><b>*</b></font></td> -->
<!-- 			<td><input type="text" name="mem_name" size="45" value="" required/></td> -->
<!-- 		</tr> -->
<!-- 		<tr> -->
<!-- 			<td>帳號:<font color=red><b>*</b></font></td> -->
<!-- 			<td><input type="text" name="mem_act" size="45" value="" required/></td> -->
<!-- 		</tr> -->
<!-- 		<tr> -->
<!-- 			<td>密碼:<font color=red><b>*</b></font></td> -->
<!-- 			<td><input type="password" name="mem_psw1" size="45" value="" required/></td> -->
<!-- 		</tr> -->
<!-- 		<tr> -->
<!-- 			<td>密碼確認:<font color=red><b>*</b></font></td> -->
<!-- 			<td><input type="password" name="mem_psw2" size="45" value="" required/></td> -->
<!-- 		</tr> -->
<!-- 		<tr> -->
<!-- 			<td>性別:</td> -->
<!-- 			<td><label><input type="radio" name="mem_gen" size="45" value="男" />男</label> -->
<!-- 				<label><input type="radio" name="mem_gen" size="45" value="女" />女</label> -->
<!-- 				<label><input type="radio" name="mem_gen" size="45" value="其他" checked/>其他</label></td> -->
<!-- 		</tr> -->
<!-- 		<tr> -->
<!-- 			<td>生日:</td> -->
<!-- 			<td><input type="date" name="mem_bir" size="45" value="0001-01-01" /></td> -->
<!-- 		</tr> -->
<!-- 		<tr> -->
<!-- 			<td>手機:<font color=red><b>*</b></font></td> -->
<!-- 			<td><input type="text" name="mem_tel" size="45" value="" required/></td> -->
<!-- 		</tr> -->
<!-- 		<tr> -->
<!-- 			<td>地址:</td> -->
<!-- 			<td> -->
<!-- 				<div id="zipcode3"> -->
<!-- 				<div class="f3" data-role="county" name="city"> -->
<!-- 				</div> -->
<!-- 				<div class="f4" data-role="district" name="town"> -->
<!-- 				</div> -->
<!-- 				</div> -->
<!-- 				<input name="address" type="text" class="f13 address form-control"> -->
<!-- 			</td> -->
<!-- 		</tr> -->
<!-- 		<tr> -->
<!-- 			<td>e-mail:<font color=red><b>*</b></font></td> -->
<!-- 			<td><input type="email" name="mem_mail" size="45" value="" required/></td> -->
<!-- 		</tr> -->
<!-- 	</table> -->
	
	<ul>
		<li>會員姓名:<font color=red><b>*</b></font><input type="text" name="mem_name" size="45" value="" required/></li>
		
		<li>帳號:<font color=red><b>*</b></font>&emsp;&emsp;<input type="text" name="mem_act" size="45" value="" required/></li>
		<li>密碼:<font color=red><b>*</b></font>&emsp;&emsp;<input type="password" name="mem_psw1" size="45" value="" required/></li>
		<li>密碼確認:<font color=red><b>*</b></font><input type="password" name="mem_psw2" size="45" value="" required/></li>
		<li>性別:&emsp;&emsp;&emsp;<label><input type="radio" name="mem_gen" size="45" value="男" />男</label>
 				<label><input type="radio" name="mem_gen" size="45" value="女" />女</label>
 				<label><input type="radio" name="mem_gen" size="45" value="其他" checked/>其他</label></li>
		<li>生日:<font color=red><b>*</b></font>&emsp;&emsp;&ensp;<input type="date" name="mem_bir" size="45" value="" required/></li>
		<li>手機:<font color=red><b>*</b></font>&emsp;&emsp;<input type="text" name="mem_tel" size="45" value="" required/></li>
		<li>e-mail:<font color=red><b>*</b></font>&ensp;<input type="email" name="mem_mail" size="45" value="" required/></li>
		<li>地址:
			<div id="zipcode3" style="width: 800px">
 			<div class="f4" data-role="county" name="city"></div>
			<div class="f5" data-role="district" name="town"></div>
			</div>
			<br><br>
			<input name="address" type="text" size="50px">
		</li>
	</ul>
	
	<input type="hidden" name="action" value="insert">
	<input type="submit" id="ok" value="註冊會員" style="margin-left:250px">
	</FORM>
	</div>
	
<script>
	$("#zipcode3").twzipcode({
	"zipcodeIntoDistrict": true,
	"css": ["city form-control", "town form-control"],
	"countyName": "city", // 指定城市 select name
	"districtName": "town" // 指定地區 select name
	});
</script>

<script>
	$("#twzipcode").twzipcode();
</script>
	
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/front-end/js/jquery.twzipcode.min.js"></script>

	<jsp:include page="/front-end/footer.jsp" />

	
</body>
</html>