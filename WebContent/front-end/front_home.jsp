<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.front_inform.model.*"%>
<%@ page import="com.message_record.model.*"%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>front_home.jsp</title>

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

.modal-open .top,
.modal-open .msg {
padding-right: 17px;
}

.unshow {
	display: none;
}

</style>
</head>



<body>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	
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
	<!-- END nav -->
	
	<section class="home-slider owl-carousel js-fullheight">
		<div class="slider-item js-fullheight"
			style="background-image: url(<%=request.getContextPath()%>/front-end/images/carousel_1.jpg);">
			<div class="overlay"></div>
			<div class="container">
				<div
					class="row slider-text js-fullheight justify-content-center align-items-center"
					data-scrollax-parent="true">
					<div class="col-md-12 col-sm-12 text-center ftco-animate">
						<span class="subheading">吃胖吧</span>
						<h1 class="mb-4">Best Restaurant</h1>
					</div>
				</div>
			</div>
		</div>
		<div class="slider-item js-fullheight"
			style="background-image: url(<%=request.getContextPath()%>/front-end/images/carousel_2.jpg);">
			<div class="overlay"></div>
			<div class="container">
				<div
					class="row slider-text js-fullheight justify-content-center align-items-center"
					data-scrollax-parent="true">
					<div class="col-md-12 col-sm-12 text-center ftco-animate">
						<span class="subheading">吃胖吧</span>
						<h1 class="mb-4">Nutritious &amp; Tasty</h1>
					</div>
				</div>
			</div>
		</div>
		<div class="slider-item js-fullheight"
			style="background-image: url(<%=request.getContextPath()%>/front-end/images/carousel_3.jpg);">
			<div class="overlay"></div>
			<div class="container">
				<div
					class="row slider-text js-fullheight justify-content-center align-items-center"
					data-scrollax-parent="true">
					<div class="col-md-12 col-sm-12 text-center ftco-animate">
						<span class="subheading">吃胖吧</span>
						<h1 class="mb-4">Delicious Specialties</h1>
					</div>
				</div>
			</div>
		</div>
	</section>
	<section class="ftco-section ftco-no-pt ftco-no-pb">
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-12">
					<div class="featured">
						<div class="row">
							<div class="col-md-3">
								<div class="featured-menus ftco-animate">
									<div class="menu-img img"
										style="background-image: url(<%=request.getContextPath()%>/front-end/front/images/wait.gif);"></div>
									<div class="text text-center">
										<h3>套餐A</h3>
										<p>
											<span>Meat</span>, <span>Potatoes</span>, <span>Rice</span>,
											<span>Tomatoe</span>
										</p>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="featured-menus ftco-animate">
									<div class="menu-img img"
										style="background-image: url(<%=request.getContextPath()%>/front-end/front/images/wait.gif);"></div>
									<div class="text text-center">
										<h3>套餐B</h3>
										<p>
											<span>Meat</span>, <span>Potatoes</span>, <span>Rice</span>,
											<span>Tomatoe</span>
										</p>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="featured-menus ftco-animate">
									<div class="menu-img img"
										style="background-image: url(<%=request.getContextPath()%>/front-end/front/images/wait.gif);"></div>
									<div class="text text-center">
										<h3>套餐C</h3>
										<p>
											<span>Meat</span>, <span>Potatoes</span>, <span>Rice</span>,
											<span>Tomatoe</span>
										</p>
									</div>
								</div>
							</div>
							<div class="col-md-3">
								<div class="featured-menus ftco-animate">
									<div class="menu-img img"
										style="background-image: url(<%=request.getContextPath()%>/front-end/front/images/wait.gif);"></div>
									<div class="text text-center">
										<h3>套餐D</h3>
										<p>
											<span>Meat</span>, <span>Potatoes</span>, <span>Rice</span>,
											<span>Tomatoe</span>
										</p>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<section class="ftco-section img"
		style="background-image: url(<%=request.getContextPath()%>/front-end/images/storeBg.jpg)"
		data-stellar-background-ratio="0.5">
		<div class="container">
			<div class="row">
				<div class="col-md-7 d-flex">
					<div class="img img-1 mr-md-2"
						style="background-image: url(<%=request.getContextPath()%>/front-end/images/HA.gif);"></div>
					<div class="img img-2 ml-md-2"
						style="background-image: url(<%=request.getContextPath()%>/front-end/images/HA.gif);"></div>
				</div>
				<div class="col-md-5 wrap-about pt-5 pt-md-5 pb-md-3 ftco-animate">
					<div class="heading-section mb-4 my-5 my-md-0">
						<span class="subheading">About</span>
						<h2 class="mb-4">吃胖吧PoT</h2>
					</div>
					<p>這裡可以放我們餐廳的介紹ㄎㄎ</p>
					<p class="time">
						<span>Mon - Fri <strong>8AM - 11 PM</strong></span> <span><a
							href="#">+ 1-978-123-4567</a></span>
					</p>
				</div>
			</div>
		</div>
	</section>



	<!-- 		------------------------------ -->
	<div id="carouselExampleIndicators" class="carousel slide"
		data-ride="carousel">
		<div id="img1">
			<ol class="carousel-indicators">
				<li data-target="#carouselExampleIndicators" data-slide-to="0"
					class="active"></li>
				<li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
				<li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
			</ol>

			<div class="carousel-inner">
				<div class="carousel-item active ">
<%-- 					<%@ include file="/front-end/front/page1-1.file"%> --%>

<%-- 					<c:forEach var="newsVO" items="${list}" begin="<%=pageIndex%>" --%>
<%-- 						end="<%=pageIndex+rowsPerPage-1%>"> --%>
<%-- 						<h3>${newsVO.news_cont}</h3> --%>
<%-- 						<h4>${newsVO.news_date}</h4> --%>
<%-- 					</c:forEach> --%>

				</div>
				<!-- 				<div class="carousel-item active ">123</div> -->
				<!-- 				<div class="carousel-item ">456</div> -->
				<!-- 				<div class="carousel-item ">789</div> -->
<!-- 				<a class="carousel-control-prev" -->
<%-- 					href="<%=request.getRequestURI()%>?whichPage=<%=whichPage - 1%>" --%>
<!-- 					role="button" data-slide="prev"> <span -->
<!-- 					class="carousel-control-prev-icon" aria-hidden="true"></span> <span -->
<!-- 					class="sr-only">Previous</span> -->
<!-- 				</a> <a class="carousel-control-next" -->
<%-- 					href="<%=request.getRequestURI()%>?whichPage=<%=whichPage + 1%>" --%>
<!-- 					role="button" data-slide="next"> <span -->
<!-- 					class="carousel-control-next-icon" aria-hidden="true"></span> <span -->
<!-- 					class="sr-only">Next</span> -->
<!-- 				</a> -->
			</div>
		</div>
	</div>
	<!-- ----------------------------------------- -->
	<div id="ExampleIndicators" class="carousel slide" data-ride="carousel">


		<div class="carousel-inner">

			<div class="carousel-item active">
				<img class="d-block w-100" src="<%=request.getContextPath()%>/front-end/images/twL_ad_20B20_m7ie5yxyhs.jpg"
					alt="twL_ad_20B20_m7ie5yxyhs.jpg">
			</div>
			<div class="carousel-item">
				<img class="d-block w-100" src="<%=request.getContextPath()%>/front-end/images/twL_ad_20B20_8n3uh8mz6a.jpg"
					alt="twL_ad_20B20_8n3uh8mz6a.jpg">
			</div>
			<div class="carousel-item">
				<img class="d-block w-100" src="<%=request.getContextPath()%>/front-end/images/twL_ad_20B20_xym5ycjdaj.jpg"
					alt="twL_ad_20B20_xym5ycjdaj.jpg">
			</div>
		</div>
	</div>


	<section class="ftco-section img"
		style="background-image: url(<%=request.getContextPath()%>/front-end/images/carousel_3.jpg)"
		data-stellar-background-ratio="0.5">
		<div class="container">
			<div class="row d-flex">
				<div
					class="col-md-7 ftco-animate makereservation p-4 px-md-5 pb-md-5">
					<div class="heading-section ftco-animate mb-5 text-center">
						<span class="subheading">Book a table</span>
						<h2 class="mb-4">我要訂位</h2>
					</div>
					<form action="#">
						<div class="row">
							<div class="col-md-6">
								<div class="form-group">
									<label for="">Name</label> <input type="text"
										class="form-control" placeholder="Your Name">
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="">Email</label> <input type="text"
										class="form-control" placeholder="Your Email">
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="">Phone</label> <input type="text"
										class="form-control" placeholder="Phone">
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="">Phone</label> <input type="text"
										class="form-control" id="book_date" placeholder="Date">
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="">Time</label> <input type="text"
										class="form-control" id="book_time" placeholder="Time">
								</div>
							</div>
							<div class="col-md-6">
								<div class="form-group">
									<label for="">Person</label>
									<div class="select-wrap one-third">
										<div class="icon">
											<span class="ion-ios-arrow-down"></span>
										</div>
										<select name="" id="" class="form-control">
											<option value="">Person</option>
											<option value="">1</option>
											<option value="">2</option>
											<option value="">3</option>
											<option value="">4+</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-md-12 mt-3">
								<div class="form-group text-center">
									<input type="submit" value="Make a Reservation"
										class="btn btn-primary py-3 px-5">
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<footer class="ftco-footer ftco-bg-dark ftco-section">
		<div class="container">
			<div class="row mb-5">
				<div class="col-md-6 col-lg-3">
					<div class="ftco-footer-widget mb-4">
						<h2 class="ftco-heading-2">吃 Pot 吧</h2>
						<p>Far far away, behind the word mountains, far from the
							countries Vokalia and Consonantia, there live the blind texts.</p>
						<ul
							class="ftco-footer-social list-unstyled float-md-left float-lft mt-3">
							<li class="ftco-animate"><a href="#"><span
									class="icon-twitter"></span></a></li>
							<li class="ftco-animate"><a href="#"><span
									class="icon-facebook"></span></a></li>
							<li class="ftco-animate"><a href="#"><span
									class="icon-instagram"></span></a></li>
						</ul>
					</div>
				</div>
				<div class="col-md-6 col-lg-3">
					<div class="ftco-footer-widget mb-4">
						<h2 class="ftco-heading-2">營業時間</h2>
						<ul class="list-unstyled open-hours">
							<li class="d-flex"><span>週一</span><span>9:00 - 24:00</span></li>
							<li class="d-flex"><span>週二</span><span>9:00 - 24:00</span></li>
							<li class="d-flex"><span>週三</span><span>公休時間</span></li>
							<li class="d-flex"><span>週四</span><span>9:00 - 24:00</span></li>
							<li class="d-flex"><span>週五</span><span>9:00 - 02:00</span></li>
							<li class="d-flex"><span>週六</span><span>9:00 - 02:00</span></li>
							<li class="d-flex"><span>週七</span><span>9:00 - 02:00</span></li>
						</ul>
					</div>
				</div>
				<div class="col-md-6 col-lg-3">
					<div class="ftco-footer-widget mb-4">
						<h2 class="ftco-heading-2">Instagram</h2>
						<div class="thumb d-sm-flex">
							<a href="#" class="thumb-menu img"
								style="background-image: url(<%=request.getContextPath()%>/front-end/images/insta-1.jpg);"> </a> <a
								href="#" class="thumb-menu img"
								style="background-image: url(<%=request.getContextPath()%>/front-end/images/insta-2.jpg);"> </a> <a
								href="#" class="thumb-menu img"
								style="background-image: url(<%=request.getContextPath()%>/front-end/images/insta-3.jpg);"> </a>
						</div>
						<div class="thumb d-flex">
							<a href="#" class="thumb-menu img"
								style="background-image: url(<%=request.getContextPath()%>/front-end/images/insta-4.jpg);"> </a> <a
								href="#" class="thumb-menu img"
								style="background-image: url(<%=request.getContextPath()%>/front-end/images/insta-5.jpg);"> </a> <a
								href="#" class="thumb-menu img"
								style="background-image: url(<%=request.getContextPath()%>/front-end/images/insta-6.jpg);"> </a>
						</div>
					</div>
				</div>
				<div class="col-md-6 col-lg-3">
					<div class="ftco-footer-widget mb-4">
						<h2 class="ftco-heading-2">Newsletter</h2>
						<p>Far far away, behind the word mountains, far from the
							countries.</p>
						<form action="#" class="subscribe-form">
							<div class="form-group">
								<input type="text" class="form-control mb-2 text-center"
									placeholder="Enter email address"> <input type="submit"
									value="Subscribe" class="form-control submit px-3">
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-12 text-center">
					<p>
						<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
						Copyright &copy;
						<script>
							document.write(new Date().getFullYear());
						</script>
						All rights reserved | This template is made with <i
							class="icon-heart" aria-hidden="true"></i> by <a
							href="https://colorlib.com" target="_blank">Colorlib</a>
						<!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
					</p>
				</div>
			</div>
		</div>
	</footer>
	
	<!-- loader -->
	<div id="ftco-loader" class="show fullscreen">
		<svg class="circular" width="48px" height="48px">
            <circle class="path-bg" cx="24" cy="24" r="22" fill="none"
				stroke-width="4" stroke="#eeeeee" />
            <circle class="path" cx="24" cy="24" r="22" fill="none"
				stroke-width="4" stroke-miterlimit="10" stroke="#F96D00" /></svg>
	</div>
	
	<!-- Modal -->
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
		var nb = $('nav.navbar-fixed-top');
		$('.modal')
	    .on('show.bs.modal', function () {
	        nb.width(nb.width());
	    })
	    .on('hidden.bs.modal', function () {
	        nb.width(nb.width('auto'));
	    });
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
		function message(){
			console.log("QQ")
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
	
</body>
</html>