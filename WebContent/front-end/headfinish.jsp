<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.front_inform.model.*"%>
<%@ page import="com.message_record.model.*"%>
<%@ page import="com.mem.model.*"%>

<%
	MemVO memVO2 = (MemVO) session.getAttribute("memVO2");	
%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>Front_Inform.jsp</title>

<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900" rel="stylesheet">
<link href="https://fonts.googleapis.com/css?family=Great+Vibes&display=swap" rel="stylesheet">

<link rel="icon" type="image/png" sizes="32x32" href="<%=request.getContextPath() %>/images/pot.png">

<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/open-iconic-bootstrap.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/animate.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/owl.carousel.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/owl.theme.default.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/magnific-popup.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/aos.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/ionicons.min.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/bootstrap-datepicker.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/jquery.timepicker.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/flaticon.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/icomoon.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/style.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/fiNmsg.css">

<style>

.modal-open .top,
.modal-open .msg {
	padding-right: 17px;
}

.smoke {
	top: 360px;
	position: relative;
	background-image:
		url('<%=request.getContextPath()%>/front-end/front/images/inPageTopBar.png');
	height: 100px;
	border-top-width: 100px;
}

.hero-wrap .overlay {
	position: absolute;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	content: '';
	opacity: 0;
	background: #000000;
}
.top {
    background: transparent !important;
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    z-index: 3;
    background: rgba(0, 0, 0, 5) !important;
    color: rgba(255, 255, 255, 0.5);
    font-size: 14px;
}
.unshow {
	display: none;
}

</style>

</head>



<body style="background-image: url('<%=request.getContextPath()%>/front-end/front/images/pageBg.jpg');">>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	
	
	
	<%-- 小鈴鐺 table 開始 --%>
	<table id="fi_cont" style="border:0;display:none;">
		<c:forEach var="front_informVO" items="${front_informVOs}">
			<tr name="${(front_informVO.read_sts == 0) ? 'unread':'read'}" >
				<c:choose>
				   <c:when test="${front_informVO.info_sts == 2}">
						<td style="width:300px;">${front_informVO.info_cont}
						<br>
						<div class="d-flex justify-content-end">
							<button id="${front_informVO.info_no}yes" style="margin-right:3px" onclick="confirm('${front_informVO.info_no}', '${front_informVO.res_no}')">確認</button>
							<button id="${front_informVO.info_no}no" onclick="cancel('${front_informVO.info_no}', '${front_informVO.res_no}')">取消</button>
						</div>
						</td>
				   </c:when>
				   <c:when test="${front_informVO.info_sts == 1}">
						<td style="width:300px;">${front_informVO.info_cont}
						<br>
						<div class="d-flex justify-content-end">
							<span>已確認</span>
						</div>
						</td>
				   </c:when>
				   <c:when test="${front_informVO.info_sts == 3}">
						<td style="width:300px;">${front_informVO.info_cont}
						<br>
						<div class="d-flex justify-content-end">
							<span>已取消</span>
						</div>
						</td>
				   </c:when>
				   <c:otherwise>
						<td style="width:300px;">${front_informVO.info_cont}</td>
				   </c:otherwise>
				</c:choose>
				
				<td style="width:100px;"><fmt:formatDate value="${front_informVO.info_date}"
						pattern="yyyy-MM-dd" /></td>
			</tr>
		</c:forEach>
	</table>
	<%-- 小鈴鐺 table 結束 --%>
	
	
	
	<%-- 客服聊天室開始 --%>
	<div class="msg">
		<a href="#" class="open-btn" id="addClass">
			<button type="button" class="btn" style="box-shadow: 0 0 0; padding:0px" onclick="message()">
				<img id="custSvc" src="<%=request.getContextPath()%>/front-end/images/msg.png">
			</button>
		</a>
		<div class="container">
        	<div>
            	<aside id="sidebar_secondary" class="tabbed_sidebar ng-scope chat_sidebar">
                	<div class="popup-head">
                    	<div class="popup-head-right pull-right">
                        	<button data-widget="remove" id="removeClass" class="chat-header-button pull-right" type="button"><img id="chatClose" src="<%=request.getContextPath()%>/front-end/images/x.png"></button>
              	      </div>
              	  </div>
              	  <div id="chat" class="chat_box_wrapper chat_box_small chat_box_active" style="opacity: 1; display: block; transform: translateX(0px); background: #d6fdff;">
                 	   <div class="chat_box touchscroll chat_box_colors_a">
                     	   <div class="chat_message_wrapper">
                        	    <div class="chat_user_avatar">
                            	    <img alt="Gurdeep Osahan (Web Designer)" title="Gurdeep Osahan (Web Designer)" src="https://bootdey.com/img/Content/avatar/avatar1.png" class="md-user-image">
                           		</div>
                            	<ul class="chat_message">
                                	<li>
                                    	<p> 您好，請問有什麼能為您服務的嗎？ </p>
                                	</li>
                            	</ul>
                        	</div>
                        	<div class="chat_message_wrapper chat_message_right">
                            	<div class="chat_user_avatar">
                               		<img alt="Gurdeep Osahan (Web Designer)" title="Gurdeep Osahan (Web Designer)" src="https://bootdey.com/img/Content/avatar/avatar1.png" class="md-user-image">
                            	</div>
                            	<ul class="chat_message">
                                	<li>
                                    	<p>
                                        	Lorem ipsum dolor sit amet, consectetur adipisicing elit. Autem delectus distinctio dolor earum est hic id impedit ipsum minima mollitia natus nulla perspiciatis quae quasi, quis recusandae, saepe, sunt totam.
                                        	<span class="chat_message_time">13:34</span>
                                    	</p>
                                	</li>
                            	</ul>
                        	</div>
                        	<div class="chat_message_wrapper">
                            	<div class="chat_user_avatar">
                               		<img alt="Gurdeep Osahan (Web Designer)" title="Gurdeep Osahan (Web Designer)" src="https://bootdey.com/img/Content/avatar/avatar1.png" class="md-user-image">
                            	</div>
                            	<ul class="chat_message">
                                	<li>
                                    	<p>
                                        	Lorem ipsum dolor sit amet, consectetur adipisicing elit. Atque ea mollitia pariatur porro quae sed sequi sint tenetur ut veritatis.https://www.facebook.com/iamgurdeeposahan
                                        	<span class="chat_message_time">23 Jun 1:10am</span>
                                    	</p>
                                	</li>
                            	</ul>
                        	</div>
                        	<div class="chat_message_wrapper chat_message_right">
                            	<div class="chat_user_avatar">
                                	<img alt="Gurdeep Osahan (Web Designer)" title="Gurdeep Osahan (Web Designer)" src="https://bootdey.com/img/Content/avatar/avatar1.png" class="md-user-image">
                            	</div>
                            	<ul class="chat_message">
                                	<li>
                                   	 <p> Lorem ipsum dolor sit amet, consectetur. </p>
                                	</li>
                               		<li>
                                    	<p>
                                        	Lorem ipsum dolor sit amet, consectetur adipisicing elit.
                                        	<span class="chat_message_time">Friday 13:34</span>
                                    	</p>
                                	</li>
                            	</ul>
                        	</div>
                    	</div>
                	</div>
                	<div class="chat_submit_box">
                    	<div class="uk-input-group">
                        	<div class="gurdeep-chat-box">
                            	<input type="text" placeholder="Type a message" id="submit_message" name="submit_message" class="md-input">
                        	</div>
                        	<span class="uk-input-group-addon">
                            	<a href="#"><img id="sendMsg" src="<%=request.getContextPath()%>/front-end/images/send.png"></a>
                        	</span>
                    	</div>
                	</div>
            	</aside>
        	</div>
    	</div>
	</div>
	<%-- 客服聊天室結束 --%>



	<%-- 以下為 navbar 內容 --%>
	<div class="py-1 bg-black top">
		<div class="container">
			<div
				class="row no-gutters d-flex align-items-start align-items-center px-md-0">
				<div class="col-lg-12 d-block">
					<div class="row d-flex">
						<div class="col-md pr-4 d-flex topper align-items-center">
							<div
								class="icon mr-2 d-flex justify-content-center align-items-center">
								<span class=""></span>
							</div>
							<span class="text"></span>
						</div>
						<div class="col-md-5 pr-4 d-flex topper align-items-center text-lg-right justify-content-end">
							<p class="mb-0 register-link">
								<%-- 小鈴鐺圖示開始 --%>
								<span class="mybb">
									<span class="badge"> </span>
									<button type="button" class="btn" style="color: #c8a97e; padding: 0px;" onclick="popFrontInform()">
										<svg width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-bell-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
										<path d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2zm.995-14.901a1 1 0 1 0-1.99 0A5.002 5.002 0 0 0 3 6c0 1.098-.5 6-2 7h14c-1.5-1-2-5.902-2-7 0-2.42-1.72-4.44-4.005-4.901z" /></svg>
									</button>
								</span>
								<%-- 小鈴鐺圖示結束 --%>
								<span class="mybb"><a href="<%=request.getContextPath() %>/front-end/mem/login_success_mem.jsp" class="myaa"><span id="member">會員中心</span></a></span>
								<span class="mybb"><a href="<%=request.getContextPath() %>/front-end/mem/addMem.jsp" class="myaa"><span id="sign">註冊</span></a></span>
								<span class="mybb"><a href="<%=request.getContextPath() %>/front-end/mem/login_mem.jsp" class="myaa"><span id="login">登入</span></a></span>
								
								<span class="mybb"><span id="mem_name" class="unshow">${memVO2.mem_name}</span></span>
								<span class="mybb"><span id="hello" class="unshow">您好！</span></span>
								<span class="mybb"><a href="<%=request.getContextPath() %>/front-end/mem/mem.do?action=logout" class="myaa"><span id="logout" class="unshow">登出</span></a></span>
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<nav
		class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light navbar-fixed-top"
		id="ftco-navbar" style="border-bottom:none">
		<div class="container">
				<a class="navbar-brand"
				href="<%=request.getContextPath()%>/front-end/front_home.jsp">PoT</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#ftco-nav" aria-controls="ftco-nav"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="oi oi-menu"></span> Menu
			</button>
			<div class="collapse navbar-collapse" id="ftco-nav">
				<ul class="navbar-nav ml-auto">
					<li class="nav-item"><a href="<%=request.getContextPath()%>/front-end/front_home.jsp" class="nav-link">Home</a></li>
					<li class="nav-item"><a href="" class="nav-link">Menu</a></li>
					<li class="nav-item"><a href="<%=request.getContextPath()%>/front-end/front/front_news-11.jsp" class="nav-link">News</a></li>
					<li class="nav-item"><a href="<%=request.getContextPath()%>/front-end/front/front_ad_new.jsp" class="nav-link">最新活動</a></li>
					<li class="nav-item"><a href="" class="nav-link">評價總覽</a></li>
					<li class="nav-item"><a href="" class="nav-link">餐廳資訊</a></li>
					<li class="nav-item"><a href="" class="nav-link">候位狀況</a></li>
					<li class="nav-item"><a href="" class="nav-link">我要訂餐</a></li>
					<li class="nav-item"><a href="<%=request.getContextPath()%>/front-end/res_order/orderSeat.jsp" class="nav-link">我要訂位</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<%-- navbar 內容結束 --%>



	<%-- 以下為輪播的地方 --%>
	<section class="hero-wrap hero-wrap-2"
		style="background-image: url('<%=request.getContextPath()%>/front-end/images/carousel_3.jpg');"
		data-stellar-background-ratio="0.5">
		<div class="smoke"></div>
		<div class="overlay"></div>
		<div class="container">
			<div
				class="row no-gutters slider-text align-items-end justify-content-center">
				<div class="col-md-9 ftco-animate text-center mb-4"
					style="height: 230px;">
					<h1 class="mb-2 bread"><span id="title">最新活動</span></h1>
					<p class="breadcrumbs">
						<span class="mr-2"><a
							href="<%=request.getContextPath()%>/front-end/front_home.jsp">Home
								<i class="ion-ios-arrow-forward"></i>
						</a></span>
					</p>
				</div>
			</div>
		</div>
	</section>
	<%-- 輪播的地方結束 --%>
	
	<%-- loader ( 網頁尚在 loading 中時會出現的一個小圈圈 ) 開始 --%>
	<div id="ftco-loader" class="show fullscreen">
		<svg class="circular" width="48px" height="48px">
            <circle class="path-bg" cx="24" cy="24" r="22" fill="none"
				stroke-width="4" stroke="#eeeeee" />
            <circle class="path" cx="24" cy="24" r="22" fill="none"
				stroke-width="4" stroke-miterlimit="10" stroke="#F96D00" /></svg>
	</div>
	<%-- loader 結束 --%>
	
	
	
	<%-- Modal (擋住未登入的會員點選已登入會員才可看到的畫面) 開始 --%>
	<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">您尚未登入</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      <div class="modal-body">
	        請先登入或註冊會員
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-dismiss="modal">關閉</button>
	        <button type="button" class="btn btn-primary" onclick="location.href='<%=request.getContextPath() %>/front-end/mem/login_mem.jsp'">我要登入</button>
	      </div>
	    </div>
	  </div>
	</div>
	<%-- Modal (擋住未登入的會員點選已登入會員才可看到的畫面) 結束 --%>
	
	
	
	
	<%-- script 開始 --%>
	<script src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
	<script type="text/javascript">
		$(function() {
			$("#addClass").click(function() {
				$('#sidebar_secondary').addClass('popup-box-on');
			});

			$("#removeClass").click(function() {
				$('#sidebar_secondary').removeClass('popup-box-on');
			});
		})
	</script>
	<script src="<%=request.getContextPath()%>/front-end/js/jquery.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/js/bootstrap.min.js"></script>
	<script>
		<%-- 可在 modal 處自由加入想要擋住的內容 --%>
		var nb = $('nav.navbar-fixed-top');
		$('.modal')
	    .on('show.bs.modal', function () {
	        nb.width(nb.width());
	    })
	    .on('hidden.bs.modal', function () {
	        nb.width(nb.width('auto'));
	    });
		
		
		
		<%-- 小鈴鐺點擊後會產生的動作 --%>
		var mem_no="<%=request.getParameter("mem_no")%>"
		if(document.getElementsByName("unread").length > 0){
			document.getElementsByClassName("badge")[0].style.display = "inline-block";
		}else{
			document.getElementsByClassName("badge")[0].style.display = 'none';
		}
		function popFrontInform(){
			let fi_cont = document.getElementById("fi_cont");
			
			// 已讀未讀顯示顏色
			
			let readColor = document.querySelectorAll('[name="	read"]');
			for(let i=0; i<readColor.length ;i++){
				readColor[i].style.backgroundColor = "#fff"; 
			}
			let unreadColor = document.querySelectorAll('[name="unread"]');
			for(let i=0; i<unreadColor.length ;i++){
				unreadColor[i].style.backgroundColor = "#e6f9ff"; 
				unreadColor[i].setAttribute('name','read');
			}
			
			// 紅點已讀消失
			document.getElementsByClassName("badge")[0].style.display = "none";			
			
			// 已讀未讀狀態修改
			$.ajax({
				 url:'fi.do',
				 method:"POST",
				 dataType:"text",
				 data:{
					 action: 'updateReadSts',
					 mem_no: mem_no,
				 },
				 success:function(res){
					if(fi_cont.style.display == "none"){
						fi_cont.style.display = "block";
						fi_cont.style.position = "fixed";
						fi_cont.style.zIndex = '550';
						fi_cont.style.top = '40px';
					    fi_cont.style.right = '20%';
					    fi_cont.style.fontSize = '10px';
					    fi_cont.style.width = '400px';
					    fi_cont.style.height = '300px';
					    fi_cont.style.borderRadius = '10px';
					    fi_cont.style.overflow = 'auto';
					}else{
						fi_cont.style.display = "none";
					}
				 },
				 error:function(err){
					console.log(err);
					fi_cont.style.display = "none";
					$('#loginModal').modal('show');
				 },	
			});
			
		}		
		function confirm(info_no, res_no){
			$.ajax({
				 url:'fi.do',
				 method:"POST",
				 dataType:"text",
				 data:{
					 action: 'updateSts',
					 info_no: info_no,
					 mem_no: mem_no,
					 res_no: res_no,
					 checkYes: 'checkYes'
				 },
				 success:function(res){
				 },
				 error:function(err){console.log(err)},	
			});
			document.getElementById(info_no+"no").disabled="disabled";
		}
		function cancel(info_no, res_no){
			$.ajax({
				 url:'fi.do',
				 method:"POST",
				 dataType:"text",
				 data:{
					 action: 'updateSts',
					 info_no: info_no,
					 mem_no: mem_no,
					 res_no: res_no,
					 checkNo: 'checkNo'
				 },
				 success:function(res){
				 },
				 error:function(err){console.log(err)},	
			});			
			document.getElementById(info_no+"yes").disabled="disabled";
		}
	</script>
	
	<script>
	
		<!--判斷現在是登入或登出的狀態 -->
		var sign = document.getElementById("sign");
		var login = document.getElementById("login");
		var mem_name = document.getElementById("mem_name");
		var hello = document.getElementById("hello");
		var logout = document.getElementById("logout");
		
		if (mem_name.innerText !== "") {
			sign.classList.add('unshow');
			login.classList.add('unshow');
			mem_name.classList.remove('unshow');
			hello.classList.remove('unshow');
			logout.classList.remove('unshow');
		} else {
			sign.classList.remove('unshow');
			login.classList.remove('unshow');
			mem_name.classList.add('unshow');
			hello.classList.add('unshow');
			logout.classList.add('unshow');
		}
		
		<!--判斷現在是否在會員中心頁面 -->
		var member = document.getElementById("member");
		var title = document.getElementById("title");
		
		var path = location.pathname;
		if (path === '/EA103G7/front-end/mem/login_mem.jsp') {
			title.innerHTML = '會員中心';
		}
		if (path === '/EA103G7/front-end/mem/login_success_mem.jsp') {
			title.innerHTML = '會員中心';
		}
		if (path === '/EA103G7/front-end/mem/addMem.jsp') {
			title.innerHTML = '會員中心';
		}
		if (path === '/EA103G7/front-end/mem/forgetPsw.jsp') {
			title.innerHTML = '會員中心';
		}
		if (path === '/EA103G7/front-end/mem/showMemInfo.jsp') {
			title.innerHTML = '會員中心';
		}
		if (path === '/EA103G7/front-end/mem/update_mem_info.jsp') {
			title.innerHTML = '會員中心';
		}
		if (path === '/EA103G7/front-end/mem/mem.do') {
			title.innerHTML = '會員中心';
		}
		
	</script>
	
	<script src="<%=request.getContextPath()%>/front-end/js/jquery-migrate-3.0.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/js/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/js/jquery.easing.1.3.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/js/jquery.waypoints.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/js/jquery.stellar.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/js/owl.carousel.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/js/jquery.magnific-popup.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/js/aos.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/js/jquery.animateNumber.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/js/bootstrap-datepicker.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/js/jquery.timepicker.min.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/js/scrollax.min.js"></script>
	<script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBVWaKrjvy3MaE7SQ74_uJiULgl1JY0H2s&sensor=false"></script>
	<script src="<%=request.getContextPath()%>/front-end/js/google-map.js"></script>
	<script src="<%=request.getContextPath()%>/front-end/js/main.js"></script>
	
	<!--為了顯示地址選單用 -->
	<script src="<%=request.getContextPath()%>/front-end/js/jquery.twzipcode.min.js"></script>
	<%-- script 結束 --%>
</body>
</html>