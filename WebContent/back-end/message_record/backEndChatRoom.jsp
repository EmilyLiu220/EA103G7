<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.emp_auth.model.*"%>
<%@ page import="com.fun_auth.model.*"%>

<%
	EmpVO empVO2 = (EmpVO) session.getAttribute("empVO2");
	List<Emp_authVO> emp_authVO2 = (List<Emp_authVO>) session.getAttribute("emp_authVO2");
	List<Fun_authVO> fun_authVO2 = (List<Fun_authVO>) session.getAttribute("fun_authVO2");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>後台首頁</title>

<!-- Bootstrap CSS CDN -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css" integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4" crossorigin="anonymous">
<!-- Our Custom CSS -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/back-end/css/style2.css">
<!-- Scrollbar Custom CSS -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.min.css">
<!-- Font Awesome JS -->
<script defer src="https://use.fontawesome.com/releases/v5.0.13/js/solid.js" integrity="sha384-tzzSw1/Vo+0N5UhStP3bvwWPq+uvzCMfrN1fEFe+xBmv1C/AtVX5K0uZtmcHitFZ" crossorigin="anonymous"></script>
<script defer src="https://use.fontawesome.com/releases/v5.0.13/js/fontawesome.js" integrity="sha384-6OIrr52G08NpOFSZdxxz1xdNSndlD4vdcf/q2myIUVO0VsqaGHJsB0RaBE01VTOY" crossorigin="anonymous"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" href="<%=request.getContextPath()%>/back-end/css/bChat.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

</head>
<body>

	<div class="wrapper">

		<!-- Sidebar  -->
		<nav id="sidebar">
			<div class="sidebar-header" style="cursor: default;">
				<h3>
					<c:choose>
						<c:when test="${empVO2.emp_no==null}">
							嗨
						</c:when>
						<c:otherwise>
							 ${empVO2.emp_no}<br>${empVO2.emp_name}
						</c:otherwise>					
					</c:choose>
					，您好！
				</h3>
			</div>

			<ul class="list-unstyled components">
				<li><a href="#">現場點餐</a></li>
				<li><a href="#">現場劃位</a></li>
				<li><a href="#">訂單結帳</a></li>
				<li><a href="#">候位管理</a></li>
				<li class="active"><a href="#pageSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">主管員工專區</a>
					<ul class="collapse list-unstyled" id="pageSubmenu">
						<li><a href="#">員工管理</a></li>
						<li><a href="#">會員管理</a></li>
						<li><a href="#">廣告管理</a></li>
						<li><a href="#">最新消息管理</a></li>
						<li><a href="<%=request.getContextPath()%>/back-end/inform_set/select_is.jsp">通知設定管理</a></li>
						<li><a href="#">評價管理</a></li>
						<li><a href="#">用餐時段管理</a></li>
						<li><a href="<%=request.getContextPath()%>/back-end/seat/editSeat.jsp">桌位管理</a></li>
						<li><a href="#">菜單管理</a></li>
						<li><a href="#">食材管理</a></li>
						<li><a href="#">餐點組成管理</a></li>
						<li><a href="#">食材消耗統計</a></li>
						<li><a href="#">紅利商品管理</a></li>
					</ul>
				</li>
				<li><a href="#homeSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">一般員工專區</a>
					<ul class="collapse list-unstyled" id="homeSubmenu">
						<li><a href="<%=request.getContextPath()%>/back-end/front_inform/empCheckInform_New.jsp">查看通知</a></li>
						<li><a href="<%=request.getContextPath()%>/back-end/message_record/backEndChatRoom.jsp">查看聊天室訊息</a></li>
						<li><a href="#">訂單派工</a></li>
						<li><a href="#">出餐管理</a></li>
						<li><a href="#">訂餐訂單處理</a></li>
						<li><a href="#">訂餐管理</a></li>
						<li><a href="#">訂單管理</a></li>
						<li><a href="#">訂位管理</a></li>
					</ul>
				</li>
			</ul>

			<ul class="list-unstyled CTAs">
				<c:choose>
					<c:when test="${empVO2.emp_no==null}">
						<li><a href="<%=request.getContextPath()%>/back-end/emp/login.jsp" id="logIn">Log in</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="#" id="logOut">Log out</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</nav>

		<!-- Page Content  -->
		<div id="content">

			<nav class="navbar navbar-expand-lg navbar-light bg-light">
				<div class="container-fluid">

					<button type="button" id="sidebarCollapse" class="btn btn-dark">
						<i class="fas fa-align-justify"></i>
					</button>
					<div id="titleBig" style="margin: 0 auto; font-size: 30px; font-weight: 800;"><a href="<%=request.getContextPath()%>/back-end/back-index_New.jsp">吃 Pot 吧！員工專區</a></div>
					<div id="rwdShow">
						<button type="button" id="topbarCollapse" class="btn btn-dark"
							data-toggle="collapse" data-target="#navbarSupportedContent"
							aria-controls="navbarSupportedContent" aria-expanded="false"
							aria-label="Toggle navigation">
							<i class="fas fa-align-justify"></i>
						</button>
						<div id="titleSmall" style="padding-left: 10px; font-size: 30px; font-weight: 800;"><a href="<%=request.getContextPath()%>/back-end/back-index_New.jsp">吃 Pot 吧！員工專區</a></div>
						<div class="collapse navbar-collapse" id="navbarSupportedContent">
							<ul class="nav navbar-nav ml-auto">
								<li class="nav-item active"><a class="nav-link" href="#"
									id="empId" style="cursor: default;">
									<c:choose>
										<c:when test="${empVO2.emp_no==null}">
											<span style="color: red; margin-top: 1rem;">嗨，您好！請記得登入喔！</span>
										</c:when>
										<c:otherwise>
											<span>${empVO2.emp_no}&nbsp;&nbsp;&nbsp;${empVO2.emp_name}，您好！</span>
										</c:otherwise>
									</c:choose>
								</a></li>
								<li class="nav-item active"><a class="nav-link" href="#">現場點餐</a></li>
								<li class="nav-item active"><a class="nav-link" href="#">現場劃位</a></li>
								<li class="nav-item active"><a class="nav-link" href="#">訂單結帳</a></li>
								<li class="nav-item active"><a class="nav-link" href="#">候位管理</a></li>
								<li class="nav-item active"><a class="nav-link" href="<%=request.getContextPath()%>/back-end/back-index_m.jsp">主管員工專區</a></li>
								<li class="nav-item active"><a class="nav-link" href="<%=request.getContextPath()%>/back-end/back-index_e.jsp">一般員工專區</a></li>
								<li class="nav-item active" style="display: block; padding-top: 0.5rem; padding-bottom: 0.5rem;">
									<c:choose>
										<c:when test="${empVO2.emp_no==null}">
											<div id="topLogIn" style="display: inline-block; width: 90px; text-align: center; margin-left: 10px; border-radius: 5px; background: #424242; color: #ccc; cursor: pointer;" onMouseOver="this.style.color='#fff'; this.style.background='#000';" onMouseOut="this.style.color='#ccc'; this.style.background='#424242';"><a href="<%=request.getContextPath()%>/back-end/emp/login.jsp">Log in</a></div>
										</c:when>
										<c:otherwise>
											<div id="topLogOut" style="display: inline-block; width: 90px; text-align: center; margin-left: 10px; border-radius: 5px; background: #424242; color: #ccc; cursor: pointer;" onMouseOver="this.style.color='#fff'; this.style.background='#000';" onMouseOut="this.style.color='#ccc'; this.style.background='#424242';"><a href="">Log out</a></div>
										</c:otherwise>
									</c:choose>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</nav>

			<%-- 後台聊天室開始，暫時先放假資料喔~不好意思QQ --%>
			<div class="container">
				<div class="messaging">
					<div class="inbox_msg">
						<div class="inbox_people">
							<div class="headind_srch">
								<div class="recent_heading">
									<h4>Recent</h4>
								</div>
								<div class="srch_bar">
									<div class="stylish-input-group">
										<input type="text" class="search-bar" placeholder="Search">
										<span class="input-group-addon">
	   										<button type="button"><i class="fa fa-search" aria-hidden="true"></i></button>
										</span>
									</div>
								</div>
							</div>
							<div class="inbox_chat">
								<div class="chat_list active_chat">
									<div class="chat_people">
										<div class="chat_img">
											<img src="https://ptetutorials.com/images/user-profile.png" alt="sunil">
										</div>
										<div class="chat_ib">
											<h5>Sunil Rajput <span class="chat_date">Dec 25</span></h5>
											<p>Test, which is a new approach to have all solutions astrology under one roof.</p>
										</div>
									</div>
								</div>
								<div class="chat_list">
									<div class="chat_people">
										<div class="chat_img">
											<img src="https://ptetutorials.com/images/user-profile.png" alt="sunil">
										</div>
										<div class="chat_ib">
											<h5>Sunil Rajput <span class="chat_date">Dec 25</span></h5>
											<p>Test, which is a new approach to have all solutions astrology under one roof.</p>
										</div>
									</div>
								</div>
	                        	<div class="chat_list">
	                            	<div class="chat_people">
										<div class="chat_img">
											<img src="https://ptetutorials.com/images/user-profile.png" alt="sunil">
										</div>
	                                	<div class="chat_ib">
	                                   		<h5>Sunil Rajput <span class="chat_date">Dec 25</span></h5>
	                                    	<p>Test, which is a new approach to have all solutions astrology under one roof.</p>
	                                	</div>
	                            	</div>
								</div>
								<div class="chat_list">
									<div class="chat_people">
										<div class="chat_img">
											<img src="https://ptetutorials.com/images/user-profile.png" alt="sunil">
										</div>
										<div class="chat_ib">
											<h5>Sunil Rajput <span class="chat_date">Dec 25</span></h5>
											<p>Test, which is a new approach to have all solutions astrology under one roof.</p>
										</div>
									</div>
								</div>
								<div class="chat_list">
									<div class="chat_people">
										<div class="chat_img">
											<img src="https://ptetutorials.com/images/user-profile.png" alt="sunil">
										</div>
										<div class="chat_ib">
											<h5>Sunil Rajput <span class="chat_date">Dec 25</span></h5>
											<p>Test, which is a new approach to have all solutions astrology under one roof.</p>
										</div>
									</div>
								</div>
								<div class="chat_list">
									<div class="chat_people">
										<div class="chat_img">
											<img src="https://ptetutorials.com/images/user-profile.png" alt="sunil">
										</div>
										<div class="chat_ib">
											<h5>Sunil Rajput <span class="chat_date">Dec 25</span></h5>
											<p>Test, which is a new approach to have all solutions astrology under one roof.</p>
										</div>
									</div>
								</div>
								<div class="chat_list">
									<div class="chat_people">
										<div class="chat_img">
											<img src="https://ptetutorials.com/images/user-profile.png" alt="sunil">
										</div>
										<div class="chat_ib">
											<h5>Sunil Rajput <span class="chat_date">Dec 25</span></h5>
											<p>Test, which is a new approach to have all solutions astrology under one roof.</p>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="mesgs">
							<div class="msg_history">
								<div class="incoming_msg">
									<div class="incoming_msg_img">
										<img src="https://ptetutorials.com/images/user-profile.png" alt="sunil">
									</div>
	                            	<div class="received_msg">
										<div class="received_withd_msg">
											<p>Test which is a new approach to have all solutions</p>
											<span class="time_date"> 11:01 AM | June 9</span>
										</div>
									</div>
								</div>
								<div class="outgoing_msg">
									<div class="sent_msg">
										<p>Test which is a new approach to have all solutions</p>
										<span class="time_date"> 11:01 AM | June 9</span>
									</div>
								</div>
								<div class="incoming_msg">
									<div class="incoming_msg_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> </div>
			                            <div class="received_msg">
			                                <div class="received_withd_msg">
			                                    <p>Test, which is a new approach to have</p>
			                                    <span class="time_date"> 11:01 AM | Yesterday</span>
			                                </div>
			                            </div>
								</div>
		                        <div class="outgoing_msg">
		                            <div class="sent_msg">
		                                <p>Apollo University, Delhi, India Test</p>
		                                <span class="time_date"> 11:01 AM | Today</span>
		                            </div>
		                        </div>
		                        <div class="incoming_msg">
		                            <div class="incoming_msg_img"> <img src="https://ptetutorials.com/images/user-profile.png" alt="sunil"> </div>
		                            <div class="received_msg">
		                                <div class="received_withd_msg">
		                                    <p>We work directly with our designers and suppliers,
		                                        and sell direct to you, which means quality, exclusive
		                                        products, at a price anyone can afford.</p>
		                                    <span class="time_date"> 11:01 AM | Today</span>
		                                </div>
		                            </div>
		                        </div>
	                    	</div>
		                    <div class="type_msg">
		                        <div class="input_msg_write">
		                            <input type="text" class="write_msg" placeholder="Type a message" />
		                            <button class="msg_send_btn" type="button"><img id="sendMsg" src="<%=request.getContextPath()%>/front-end/images/send.png"></button>
		                        </div>
		                    </div>
						</div>
					</div>
				</div>
			</div>
			<%-- 後台聊天室結束 --%>
		</div>
	</div>

	<!-- jQuery CDN - Slim version (=without AJAX) -->
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<!-- Popper.JS -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js" integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ" crossorigin="anonymous"></script>
	<!-- Bootstrap JS -->
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/js/bootstrap.min.js" integrity="sha384-uefMccjFJAIv6A+rW+L4AHf99KvxDjWSu1z9VI8SKNVmz4sk7buKt/6v9KI65qnm" crossorigin="anonymous"></script>
	<!-- jQuery Custom Scroller CDN -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.concat.min.js"></script>
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
</body>
</html>