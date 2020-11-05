<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
	MemVO memVO = (MemVO) request.getAttribute("memVO");
%>

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
	
	<ul>
		<li>會員姓名:<font color=red><b>*</b></font><input type="text" id="mem_name2" name="mem_name" size="45" value="${memVO.mem_name}" required/></li><p><p>
		
		<li>帳號:<font color=red><b>*</b></font>&emsp;&emsp;<input type="text" id="mem_act2" name="mem_act" size="45" value="${memVO.mem_act}" required/></li><p><p>
		<li>密碼:<font color=red><b>*</b></font>&emsp;&emsp;<input type="password" id="mem_psw1" name="mem_psw1" size="45" value="" required/></li><p><p>
		<li>密碼確認:<font color=red><b>*</b></font><input type="password" id="mem_psw2"  name="mem_psw2" size="45" value="" required/></li><p><p>
		<li>性別:&emsp;&emsp;&emsp;<label><input type="radio" name="mem_gen2" size="45" value="男" />男</label>
 				<label><input type="radio" name="mem_gen2" size="45" value="女" />女</label>
 				<label><input type="radio" name="mem_gen2" size="45" value="其他" checked/>其他</label></li><p><p>
		<li>生日:<font color=red><b>*</b></font>&emsp;&emsp;&ensp;<input type="date" id="mem_bir2" name="mem_bir" size="45" value="${memVO.mem_bir}" required/></li><p><p>
		<li>手機:<font color=red><b>*</b></font>&emsp;&emsp;<input type="text" id="mem_tel2" name="mem_tel" size="45" value="${memVO.mem_tel}" required/></li><p><p>
		<li>e-mail:<font color=red><b>*</b></font>&ensp;<input type="email" id="mem_mail2" name="mem_mail" size="45" value="${memVO.mem_mail}" required/></li><p><p>
		<li>地址:
			<div id="zipcode3" style="width: 800px">
 			<div class="f4" data-role="county" name="city"></div>
			<div class="f5" data-role="district" name="town"></div>
			</div>
			<br><br>
			<input id="address2" name="address" type="text" size="50px" value="${memVO.mem_adrs}">
		</li>
	</ul>
	
	<input type="hidden" name="action" value="insert">
	<input type="submit" id="ok" value="註冊會員" style="margin-left:250px">
	</FORM>
	
	<input type="button" id="button2" value="按我" style="margin-left:265px">
	
	</div>
	
<script>

	<!--小按鈕-->
	var button2 = document.getElementById("button2");
	var mem_name2 = document.getElementById("mem_name2");
	var mem_act2 = document.getElementById("mem_act2");
	var mem_psw1_2 = document.getElementById("mem_psw1");
	var mem_psw2_2 = document.getElementById("mem_psw2");
	var mem_gen2 = document.getElementsByName("mem_gen2");
	var mem_bir2 = document.getElementById("mem_bir2");
	var mem_tel2 = document.getElementById("mem_tel2");
	var mem_mail2 = document.getElementById("mem_mail2");
	var address2 = document.getElementById("address2");
	
	button2.addEventListener("click", function(e) {	
		mem_name2.value = "郭大俠";
		mem_act2.value = "zzz111";
		mem_psw1_2.value = "zzz111";
		mem_psw2_2.value = "zzz111";
		mem_gen2[0].checked = true;
		mem_bir2.value = "1990-08-08";
		mem_tel2.value = "0988168168";
		mem_mail2.value = "ea103g7@gmail.com";
		address2.value = "中大路300號";
	});
	
	<!--註冊成功訊息-->
// 	var mem_psw1 = document.getElementsByName("mem_psw1");
// 	var mem_psw2 = document.getElementsByName("mem_psw2");
// 	var ok = document.getElementById("ok");
	
// 	ok.addEventListener("click", function(e) {
// 		if (mem_psw1[0].value !== "" && mem_psw2[0].value !== "" && mem_psw1[0].value === mem_psw2[0].value) {
// 			alert("註冊成功");
// 		}
// 	});
	
</script>
	
	
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
<!-- 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
-->
<script src="<%=request.getContextPath()%>/front-end/js/jquery.twzipcode.min.js"></script>

	<jsp:include page="/front-end/footer.jsp" />
	
</body>
</html>