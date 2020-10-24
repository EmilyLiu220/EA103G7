<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.emp_auth.model.*"%>
<%@ page import="com.fun_auth.model.*"%>

<%
  EmpVO empVO2 = (EmpVO) session.getAttribute("empVO2");
%>

<% 
  List<Emp_authVO> emp_authVO2 = (List<Emp_authVO>) session.getAttribute("emp_authVO2");
%>

<%
  List<Fun_authVO> fun_authVO2 = (List<Fun_authVO>) session.getAttribute("fun_authVO2");
%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>siderbar</title>

<!-- Bootstrap CSS CDN -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css"
	integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4"
	crossorigin="anonymous">
<!-- Our Custom CSS -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/back-end/css/style2.css">
<!-- Scrollbar Custom CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.min.css">
<!-- Font Awesome JS -->
<script defer
	src="https://use.fontawesome.com/releases/v5.0.13/js/solid.js"
	integrity="sha384-tzzSw1/Vo+0N5UhStP3bvwWPq+uvzCMfrN1fEFe+xBmv1C/AtVX5K0uZtmcHitFZ"
	crossorigin="anonymous"></script>
<script defer
	src="https://use.fontawesome.com/releases/v5.0.13/js/fontawesome.js"
	integrity="sha384-6OIrr52G08NpOFSZdxxz1xdNSndlD4vdcf/q2myIUVO0VsqaGHJsB0RaBE01VTOY"
	crossorigin="anonymous"></script>

<style>

.unshow{
 	display: none;
}

</style>

</head>
<body>
		<!-- Sidebar  -->
		<nav id="sidebar">
			<div class="sidebar-header" style="cursor: default;">
				<h3><span id="no"> ${empVO2.emp_no}</span><br> <span>${empVO2.emp_name}&emsp;您好！</span></h3>
				<!-- 員工編號 ${empVO.emp_no}  員工姓名 ${empVO.emp_name} -->
				<hr>
				
				<ul class="list-unstyled CTAs">
					<li style="text-align:center; font-size:30px" id="in">
						<form method="post" action="<%=request.getContextPath()%>/back-end/emp/login.jsp">
					        <label style="cursor:pointer"><a id="logIn">Log in</a>
					        <input type="submit" style="display:none"></label>  
					    </form>
					</li>
	
					<li style="text-align:center; font-size:30px" id="out" class="unshow">
						<form method="post" action="<%=request.getContextPath()%>/back-end/emp/emp.do">
					        <input type="hidden" name="action" value="logout"> 
					        <label style="cursor:pointer"><a id="logOut">Log out</a>
					        <input type="submit" style="display:none"></label>  
					    </form>
					</li>
					
				</ul>
				
			</div>

			<ul class="list-unstyled components">
				<li class="fun2"><a href="#">現場點餐</a></li>
				<li class="fun2"><a href="#">現場劃位</a></li>
				<li class="fun2"><a href="#">訂單結帳</a></li>
				<li class="fun2"><a href="#">候位管理</a></li>
				<li class="active"><a href="#pageSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">主管員工專區</a>
					<ul class="collapse list-unstyled" id="pageSubmenu">
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/emp/select_page.jsp">員工管理</a></li>
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/mem/select_page_mem.jsp">會員管理</a></li>
						<li class="fun2"><a href="#">廣告管理</a></li>
						<li class="fun2"><a href="#">最新消息管理</a></li>
						<li class="fun2"><a href="#">通知管理</a></li>
						<li class="fun2"><a href="#">評價管理</a></li>
						<li class="fun2"><a href="#">用餐時段管理</a></li>
						<li class="fun2"><a href="#">桌位管理</a></li>
						<li class="fun2"><a href="#">菜單管理</a></li>
						<li class="fun2"><a href="#">食材管理</a></li>
						<li class="fun2"><a href="#">餐點組成管理</a></li>
						<li class="fun2"><a href="#">食材消耗統計</a></li>
						<li class="fun2"><a href="#">紅利商品管理</a></li>
					</ul>
				</li>
				<li><a href="#homeSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">一般員工專區</a>
					<ul class="collapse list-unstyled" id="homeSubmenu">
						<li class="fun2"><a href="#">查看通知</a></li>
						<li class="fun2"><a href="#">查看聊天室訊息</a></li>
						<li class="fun2"><a href="#">訂單派工</a></li>
						<li class="fun2"><a href="#">出餐管理</a></li>
						<li class="fun2"><a href="#">訂餐訂單處理</a></li>
						<li class="fun2"><a href="#">訂餐管理</a></li>
						<li class="fun2"><a href="#">訂單管理</a></li>
						<li class="fun2"><a href="#">訂位管理</a></li>
					</ul>
				</li>
			</ul>
			
			<div style="text-align:center; font-size:30px" id="info2" class="unshow">
				<form method="post" action="<%=request.getContextPath()%>/back-end/emp/emp.do">
					<input type="hidden" name="emp_no" value="${empVO2.emp_no}">
					<input type="hidden" name="action" value="Update_info"> 
					<label style="cursor:pointer"><a id="info">員工個資修改</a>
					<input type="submit" style="display:none"></label>  
				</form>
			</div>
			
		</nav>
		
		<div class="wrapper">

		<!-- Page Content  -->
		<div id="content">

			<nav class="navbar navbar-expand-lg navbar-light bg-light">
				<div class="container-fluid">
					
					<button type="button" id="sidebarCollapse" class="btn btn-info">
						<i class="fas fa-align-justify"></i>
					</button>
					<div id="titleBig"
						style="margin: 0 auto; font-size: 30px; font-weight: 800; cursor: default;">吃
						Pot 吧！員工專區</div>
					<div id="rwdShow">
						<button type="button" id="topbarCollapse" class="btn btn-info"
							data-toggle="collapse" data-target="#navbarSupportedContent"
							aria-controls="navbarSupportedContent" aria-expanded="false"
							aria-label="Toggle navigation">
							<i class="fas fa-align-justify"></i>
						</button>
						<div id="titleSmall"
							style="padding-left: 10px; font-size: 30px; font-weight: 800; cursor: default;">吃
							Pot 吧！員工專區</div>	
					
					<div class="collapse navbar-collapse" id="navbarSupportedContent">
						
					</div>
					
				</div>
			</nav>
		</div>
		</div>
		
		<!-- jQuery CDN - Slim version (=without AJAX) -->
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	<!-- Popper.JS -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"
		integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ"
		crossorigin="anonymous"></script>
	<!-- Bootstrap JS -->
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js"
		integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm"
		crossorigin="anonymous"></script>
	<!-- jQuery Custom Scroller CDN -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.concat.min.js"></script>
		
	<script type="text/javascript">
		$(document).ready(function() {
			$("#sidebar").mCustomScrollbar({
				theme : "minimal"
			});

			$('#sidebarCollapse').on('click', function() {
				$('#sidebar, #content').toggleClass('active');
				$('.collapse.in').toggleClass('in');
				$('a[aria-expanded=true]').attr('aria-expanded', 'false');
			});
		});
	</script>
	
		<div id="fun" style="display:none">
			<c:forEach var="fun_authVO2" items="${fun_authVO2}">
				<span class="fun">${fun_authVO2.fun_name}</span><br>
			</c:forEach>
		</div>
	
	<script>
		// 判斷員工擁有哪些權限可以點選
		var fun = document.getElementsByClassName("fun");
		var arr1 = [];
		for (let i = 0; i < fun.length; i++) {
			var x = fun[i].innerText;
			arr1.push(x);
		}
		
		var fun2 = document.getElementsByClassName("fun2");
		var arr2 = [];
		for (let i = 0; i < fun2.length; i++) {
			var y = fun2[i].innerText;
			arr2.push(y);
		}
		
		for (let i = 0; i < arr2.length; i++) {
			var allow = true;
			for (let j = 0; j < arr1.length; j++) {
				if (arr2[i] === arr1[j]) {
					allow = false;
					break;
				}
			}
			if (allow) {
				fun2[i].classList.add('unshow');
			}
		}
		
		// 登入後顯示登出按鈕，登出後顯示登入按鈕
		var login = document.getElementById("in");
		var logout = document.getElementById("out");
		var info = document.getElementById("info2");
		
		var no = document.getElementById("no");
		
		if (no.innerText !== "") {
			login.classList.add("unshow");
			logout.classList.remove("unshow");
			info.classList.remove("unshow");
		} else {
			info.classList.add("unshow");
			logout.classList.add("unshow");
			login.classList.remove("unshow");
		}
		
	</script>
	
</body>
</html>