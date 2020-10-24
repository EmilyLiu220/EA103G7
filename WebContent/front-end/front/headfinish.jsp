<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.front_inform.model.*"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>Front_Inform.jsp</title>

<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link
	href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Great+Vibes&display=swap"
	rel="stylesheet">
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

.blank {
	width: 500px;
	height: 1000px;
}

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
		<button type="button" class="btn" style="box-shadow: 0 0 0; padding:0px" onclick="popMsg()">
			<img id="custSvc" src="<%=request.getContextPath()%>/front-end/images/msg.png">
		</button>
		<div>
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
								<span class="mybb"><a href="menu.html" class="myaa">會員中心</a></span>
								<span class="mybb"><a href="blog.html" class="myaa">註冊</a></span>
								<span class="mybb"><a href="contact.html" class="myaa">登入</a></span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<nav
		class="navbar navbar-expand-lg navbar-dark ftco_navbar bg-dark ftco-navbar-light navbar-fixed-top"
		id="ftco-navbar">
		<div class="container">
				<a class="navbar-brand"
				href="<%=request.getContextPath()%>/front-end/front/index.jsp">PoT</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#ftco-nav" aria-controls="ftco-nav"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="oi oi-menu"></span> Menu
			</button>
			<div class="collapse navbar-collapse" id="ftco-nav">
				<ul class="navbar-nav ml-auto">
					<li class="nav-item"><a
						href="<%=request.getContextPath()%>/front-end/front/index.jsp"
						class="nav-link">Home</a></li>
					<li class="nav-item"><a href="" class="nav-link">Menu</a></li>
				<li class="nav-item"><a
					href="<%=request.getContextPath()%>/front-end/front/front_news.jsp"
					class="nav-link">News</a></li>
				<li class="nav-item"><a
						href="<%=request.getContextPath()%>/front-end/front/front_ad.jsp"
						class="nav-link">最新活動</a></li>
					<li class="nav-item"><a href="" class="nav-link">評價總覽</a></li>
					<li class="nav-item"><a href="" class="nav-link">餐廳資訊</a></li>
					<li class="nav-item"><a href="" class="nav-link">候位狀況</a></li>
					<li class="nav-item"><a href="" class="nav-link">我要訂餐</a></li>
					<li class="nav-item"><a href="" class="nav-link">我要定位</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<%-- navbar 內容結束 --%>



	<%-- 以下為輪播的地方 --%>

	
	<section class="hero-wrap hero-wrap-2"
		style="background-image: url('<%=request.getContextPath()%>/front-end/front/images/33333.jpg');"
		data-stellar-background-ratio="0.5">
		<div class="smoke"></div>
		<div class="overlay"></div>

		<div class="container">
			<div
				class="row no-gutters slider-text align-items-end justify-content-center">
				<div class="col-md-9 ftco-animate text-center mb-4"
					style="height: 230px;">
					<h1 class="mb-2 bread">最新活動</h1>
					<p class="breadcrumbs">
						<span class="mr-2"><a
							href="<%=request.getContextPath()%>/front-end/front/index.jsp">Home
								<i class="ion-ios-arrow-forward"></i>
						</a></span>
				</div>
			</div>
		</div>


	
	</section>
	<%-- 輪播的地方結束 --%>
	
	
	
	<%-- 內容放置區域開始 --%>
	<section>
		<div class="blank"></div>
	</section>
	<%-- 內容放置區域結束 --%>
	
	
	
	<%-- footer 開始 --%>

	<%-- footer 結束 --%>
	
	
	
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
	        <button type="button" class="btn btn-primary" onclick="location.href='contact.html'">我要登入</button>
	      </div>
	    </div>
	  </div>
	</div>
	<%-- Modal (擋住未登入的會員點選已登入會員才可看到的畫面) 結束 --%>
	
	
	
	<%-- script 開始 --%>
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
		
		
		<%-- 客服對話框點擊後會產生的動作...尚未完成...QQ... --%>
		function popMsg(){
			console.log("QQ")
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
	<script src="<%=request.getContextPath()%>/front-end/js/main.js"></script>
	<%-- script 結束 --%>
</body>
</html>