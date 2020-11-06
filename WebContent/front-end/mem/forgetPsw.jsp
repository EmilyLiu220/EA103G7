<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>

<%
	String x = (String) request.getAttribute("x");
%>

<html>
<head>
<title>忘記密碼</title>

<style>

#container{
  padding-top: 50px;
  padding-bottom: 50px;
  margin:0 auto;
  width: 600px;
}

</style>

</head>
<body onload="hint()">
	
	<%@ include file="/front-end/headfinish.jsp"%>
	
	<div id="container">
	<h3 style="text-align:center">忘記密碼</h3>
	
	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color:red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color:red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	
	<form method="post" action="<%=request.getContextPath()%>/front-end/mem/mem.do">
		<span>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;請輸入email:</span>
		<input type="email" name="mem_mail" value="${memVO2.mem_mail}" required>
		<p><p>
		<input type="submit" id="ok" value="送出" style="margin-left:275px">
		<input type="hidden" name="action" value="forget_psw">
	</form>
	
	<span style="color: red;">&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;★按下送出將會寄出臨時密碼，請使用臨時密碼登入</span>
	</div>
	
	<jsp:include page="/front-end/front/footer.jsp" />

<!--sweet alert -->
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

<script>

// 	var mem_mail = document.getElementsByName("mem_mail");
// 	var ok = document.getElementById("ok");
	
// 	ok.addEventListener("click", function() {
// 		if (mem_mail[0].value !== "") {
// 			alert("臨時密碼已寄出，請至email信箱查收！");
// 		}
// 	});

	var x = `${x}`;
	console.log(x);
	function hint() {
		if (x === "notfound") {
// 			alert("查無此email！請重新輸入！");
			swal({
				  title: "查無此email！請重新輸入！",
				  text: "3秒後自動關閉。",
				  icon: "error",
				  timer: 3000,
				  showConfirmButton: true
				});
		}
	}

</script>

</body>
</html>