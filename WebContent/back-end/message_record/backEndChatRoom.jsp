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
<title>後檯即時通訊</title>

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/back-end/css/bootstrap-4.1.0.min.css">
<!-- Our Custom CSS -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/back-end/css/style2.css">
<!-- Scrollbar Custom CSS -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/back-end/css/jquery-3.1.4.mCustomScrollbar.min.css">
<!-- Font Awesome JS -->
<script defer src="<%=request.getContextPath()%>/back-end/js/solid.js"></script>
<script defer src="<%=request.getContextPath()%>/back-end/js/fontawesome.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.css" type="text/css" rel="stylesheet">
<link rel="stylesheet" href="<%=request.getContextPath()%>/back-end/css/bChat.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<style>

.unshow{
 	display: none;
}

</style>
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
				<c:choose>
					<c:when test="${empVO2.emp_no!=null}">
						<li style="font-size:20px;"><a href="<%=request.getContextPath()%>/back-end/emp/emp.do?action=Update_info&emp_no=${empVO2.emp_no}">員工個資修改</a></li>
					</c:when>
				</c:choose>
				<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/res_order/orderSeat.jsp">現場劃位</a></li>
				<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/wait_seat/listAllWait_seat.jsp">候位管理</a></li>
				<li class="active"><a href="#pageSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">主管員工專區</a>
					<ul class="collapse list-unstyled" id="pageSubmenu">
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/emp/select_page.jsp">員工管理</a></li>
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/mem/select_page_mem.jsp">會員管理</a></li>
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/ad/select_ad.jsp">廣告管理</a></li>
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/news/select_news.jsp">最新消息管理</a></li>
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/inform_set/select_is.jsp">通知管理</a></li>
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/member_review/select_page.jsp">評價管理</a></li>
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/seat/editSeat.jsp">桌位管理</a></li>
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/meal/menuManagement.jsp">菜單管理</a></li>
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/food/listAllFood.jsp">食材管理</a></li>
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/meal_part/listAllMeal_part.jsp">餐點組成管理</a></li>
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/food/Statistics.jsp">食材消耗統計</a></li>
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/bonus/select_page.jsp">紅利商品管理</a></li>
					</ul>
				</li>
				<li><a href="#homeSubmenu" data-toggle="collapse" aria-expanded="false" class="dropdown-toggle">一般員工專區</a>
					<ul class="collapse list-unstyled" id="homeSubmenu">
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/front_inform/select_fi.jsp">查看通知</a></li>
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/message_record/backEndChatRoom.jsp">後檯即時通訊</a></li>
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/mealOrder/mealOrderManagement.jsp">訂餐管理</a></li>
						<li class="fun2"><a href="<%=request.getContextPath()%>/back-end/res_order/resOrderManage.jsp">訂位管理</a></li>
					</ul>
				</li>
			</ul>

			<ul class="list-unstyled CTAs">
				<c:choose>
					<c:when test="${empVO2.emp_no==null}">
						<li><a href="<%=request.getContextPath()%>/back-end/emp/login.jsp" id="logIn">Log in</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="<%=request.getContextPath()%>/back-end/emp/emp.do?action=logout" id="logOut">Log out</a></li>
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
					<div id="titleBig" style="margin: 0 auto; font-size: 30px; font-weight: 800;"><a href="<%=request.getContextPath()%>/back-end/backindex.jsp">吃 Pot 吧！員工專區</a></div>
					<div id="rwdShow">
						<button type="button" id="topbarCollapse" class="btn btn-dark"
							data-toggle="collapse" data-target="#navbarSupportedContent"
							aria-controls="navbarSupportedContent" aria-expanded="false"
							aria-label="Toggle navigation">
							<i class="fas fa-align-justify"></i>
						</button>
						<div id="titleSmall" style="padding-left: 10px; font-size: 30px; font-weight: 800;"><a href="<%=request.getContextPath()%>/back-end/backindex.jsp">吃 Pot 吧！員工專區</a></div>
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
								<li class="nav-item active"><a class="nav-link" href="<%=request.getContextPath()%>/back-end/res_order/orderSeat.jsp">現場劃位</a></li>
								<li class="nav-item active"><a class="nav-link" href="<%=request.getContextPath()%>/back-end/wait_seat/listAllWait_seat.jsp">候位管理</a></li>
								<li class="nav-item active"><a class="nav-link" href="<%=request.getContextPath()%>/back-end/back-index_m.jsp">主管員工專區</a></li>
								<li class="nav-item active"><a class="nav-link" href="<%=request.getContextPath()%>/back-end/back-index_e.jsp">一般員工專區</a></li>
								<li class="nav-item active" style="display: block; padding-top: 0.5rem; padding-bottom: 0.5rem;">
									<c:choose>
										<c:when test="${empVO2.emp_no==null}">
											<div id="topLogIn" style="display: inline-block; width: 90px; text-align: center; margin-left: 10px; border-radius: 5px; background: #424242; color: #ccc; cursor: pointer;" onMouseOver="this.style.color='#fff'; this.style.background='#000';" onMouseOut="this.style.color='#ccc'; this.style.background='#424242';"><a href="<%=request.getContextPath()%>/back-end/emp/login.jsp">Log in</a></div>
										</c:when>
										<c:otherwise>
											<div id="topLogOut" style="display: inline-block; width: 90px; text-align: center; margin-left: 10px; border-radius: 5px; background: #424242; color: #ccc; cursor: pointer;" onMouseOver="this.style.color='#fff'; this.style.background='#000';" onMouseOut="this.style.color='#ccc'; this.style.background='#424242';"><a href="<%=request.getContextPath()%>/back-end/emp/emp.do?action=logout">Log out</a></div>
										</c:otherwise>
									</c:choose>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</nav>

			<%-- 後台聊天室開始 --%>
			<div class="container">
				<div class="messaging">
					<div class="inbox_msg">
						<div class="inbox_people">
							<div class="headind_srch">
								<div class="recent_heading">
									<h4>Recent</h4>
								</div>
								<%--<div class="srch_bar">
									<div class="stylish-input-group">
										<input type="text" class="search-bar" placeholder="Search">
										<span class="input-group-addon">
	   										<button type="button"><i class="fa fa-search" aria-hidden="true"></i></button>
										</span>
									</div>
								</div> --%>
							</div>
							<div class="inbox_chat">
							</div>
						</div>
						<div class="mesgs">
							<div class="msg_history"></div>
		                    <div class="type_msg">
		                        <div class="input_msg_write">
		                            <input type="text" id="submit_message" class="write_msg" placeholder="Type a message" />
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
	<script src="<%=request.getContextPath()%>/back-end/js/jquery-3.3.1.slim.min.js"></script>
	<script type="text/javascript">
		<%-- 聊天室 --%>
		var MyPoint = "/Message_RecordWS/emp"; // Java 會先執行 → 所以到 JS 這裡的時候就可以直接用 EL 取值
		var host = window.location.host;
		var path = window.location.pathname;
		var webCtx = path.substring(0, path.indexOf('/', 1));
		var endPointURL = "ws://" + host + webCtx + MyPoint;
		var chatPic = "<%=request.getContextPath()%>/back-end/images/kou.jpg";
	
		var messagesArea = document.getElementsByClassName("msg_history")[0];
		var emp_no = "${empVO2.emp_no}"; // 宣告自己(用來分辨訊息要套用的 CSS)
		
		var	webSocket = new WebSocket(endPointURL);
			
		webSocket.onopen = function(event) {
			console.log("Connect Success!");
		};
		
		webSocket.onmessage = function(event) {
			var jsonObj = JSON.parse(event.data); // 把發送來的字串資料轉成 json 物件
			
			// 連線後刷新左側會員清單
			if ("refresh" === jsonObj.type) {
				console.log("收到 refresh 訊息");
				refreshMemList(jsonObj);
			}else if ("history" === jsonObj.type) { // 這次來的是歷史訊息內容
				console.log("收到 history 訊息");
				messagesArea.innerHTML = '';
				let receiver = '';
				var messages = JSON.parse(jsonObj.msgJson);
				for (var i = 0; i < messages.length; i++) { // 將畫面渲染
					var from_msg = document.createElement('div');
					var historyData = JSON.parse(messages[i]);
					var content_msg = document.createElement('div');
					
					// 判斷發送者為何，渲染畫面
					if( historyData.sender !== "emp" ) { 
						from_msg.setAttribute("class", "incoming_msg");
						var incoming_msg_img = document.createElement('div');
						incoming_msg_img.classList.add("incoming_msg_img");
						var img = document.createElement('img');
						img.setAttribute("src",chatPic);
						img.setAttribute("class","chatImg");
						//img.setAttribute("alt","sunil");
						incoming_msg_img.appendChild(img);
						
						var received_msg = document.createElement('div');
						received_msg.classList.add("received_msg");
						content_msg.classList.add("received_withd_msg");
						
						
						var p = document.createElement('p');
						var span = document.createElement('span');
						span.classList.add("time_date");
						var showMsg = historyData.message;
						var timestamp = historyData.timestamp;
						var readSts = historyData.readSts;
						p.innerHTML = showMsg;
						var dayTime = timestamp.split(" ")[0];
						p.setAttribute("title",dayTime);
						var shortTime =timestamp.split(" ")[1];
						shortTime = shortTime.substring(0, shortTime.length-3);
						span.innerHTML = shortTime;
						
						content_msg.appendChild(p);
						content_msg.appendChild(span);
						received_msg.appendChild(content_msg);
						from_msg.appendChild(incoming_msg_img);
						from_msg.appendChild(received_msg);
						
						receiver = historyData.sender;
					}else{
						from_msg.setAttribute("class", "outgoing_msg");
						var sent_msg = document.createElement('div');
						content_msg.classList.add("sent_msg");
						
						var p = document.createElement('p');
						var span = document.createElement('span');
						var spanReadSts = document.createElement('span');
						span.classList.add("time_date");
						var showMsg = historyData.message;
						var timestamp = historyData.timestamp;
						var readStsText = " 已讀";
						if(historyData.readSts==0){
							readStsText = " 未讀";
							spanReadSts.classList.add("chat_unread");
						}
						spanReadSts.innerHTML = readStsText;
						p.innerHTML = showMsg;
						var dayTime = timestamp.split(" ")[0];
						p.setAttribute("title",dayTime + "--" + historyData.emp_no);
						var shortTime =timestamp.split(" ")[1];
						shortTime = shortTime.substring(0, shortTime.length-3);
						
						span.innerHTML = shortTime + " | ";
						span.appendChild(spanReadSts);
						
						content_msg.appendChild(p);
						content_msg.appendChild(span);
						from_msg.appendChild(content_msg);
						
					}
					
					messagesArea.appendChild(from_msg); // 將新增的歷史訊息區塊加進 chat 區塊
				}
				
				// 讀取訊息，刷新左側清單
				let chat_list_receiver = document.querySelector("#"+receiver);
				chat_list_receiver.removeAttribute("style");
				let unreadCount = chat_list_receiver.firstChild.lastChild;
				if( unreadCount.childElementCount == 2){
					unreadCount.removeChild(unreadCount.lastChild);
					chat_list_receiver.setAttribute("title","0");
				}
				Sort();
				chat_listListener();
				
				messagesArea.scrollTop = messagesArea.scrollHeight;
			} else if ("chat" === jsonObj.type) {
				console.log("收到 "+jsonObj.sender+" chat 訊息");
				var from_msg = document.createElement('div');
				var content_msg = document.createElement('div');

				var sender = document.querySelector('#'+jsonObj.sender);
				var receiver = document.querySelector('#'+jsonObj.receiver);
				var dom = sender==null ? receiver : sender;
				if( dom != null && dom.classList.contains("active_chat")){ // 員工正在該會員的聊天室
					if( jsonObj.sender !== "emp") { // 若訊息為會員發出，更新聊天室內容
						from_msg.setAttribute("class", "incoming_msg");
						
						var incoming_msg_img = document.createElement('div');
						incoming_msg_img.classList.add("incoming_msg_img");
						var img = document.createElement('img');
						img.setAttribute("src",chatPic);
						img.setAttribute("class","chatImg");
						//img.setAttribute("alt","sunil");
						incoming_msg_img.appendChild(img);
						
						var received_msg = document.createElement('div');
						received_msg.classList.add("received_msg");
						content_msg.classList.add("received_withd_msg");
						
						
						var p = document.createElement('p');
						var span = document.createElement('span');
						span.classList.add("time_date");
						var showMsg = jsonObj.message;
						var timestamp = jsonObj.timestamp;
						var readSts = jsonObj.readSts;
						
						p.innerHTML = showMsg;
						var dayTime = timestamp.split(" ")[0];
						p.setAttribute("title",dayTime);
						var shortTime =timestamp.split(" ")[1];
						shortTime = shortTime.substring(0, shortTime.length-3);
						span.innerHTML = shortTime;
						
						
						content_msg.appendChild(p);
						content_msg.appendChild(span);
						received_msg.appendChild(content_msg);
						from_msg.appendChild(incoming_msg_img);
						from_msg.appendChild(received_msg);
						
						// 發送給會員已讀訊息
						var jsonObj = {
							"type" : "read",
							"sender" : "emp",
							"receiver" : jsonObj.sender
						};
						webSocket.send(JSON.stringify(jsonObj));
					} else { // 訊息為員工發出，更新畫面
						from_msg.setAttribute("class", "outgoing_msg");
						var sent_msg = document.createElement('div');
						content_msg.classList.add("sent_msg");
						
						var p = document.createElement('p');
						var span = document.createElement('span');
						var spanReadSts = document.createElement('span');
						span.classList.add("time_date");
						var showMsg = jsonObj.message;
						var timestamp = jsonObj.timestamp;
						var readStsText = " 已讀";
						if(jsonObj.readSts == 0){
							readStsText = " 未讀";
							spanReadSts.classList.add("chat_unread");
						}
						spanReadSts.innerHTML = readStsText;
						p.innerHTML = showMsg;
						var dayTime = timestamp.split(" ")[0];
						p.setAttribute("title",dayTime + "--" + jsonObj.emp_no);
						var shortTime =timestamp.split(" ")[1];
						shortTime = shortTime.substring(0, shortTime.length-3);
						
						span.innerHTML = shortTime + " | ";
						span.appendChild(spanReadSts);
						
						content_msg.appendChild(p);
						content_msg.appendChild(span);
						from_msg.appendChild(content_msg);
					}
				} else { // 不在與該會員的聊天室中，收到來自會員的訊息
					// 更新左側清單
					var inbox_chat = document.getElementsByClassName("inbox_chat")[0];
					if(sender == null){ // 若該會員從未留言，新增左側清單
						var chat_list = document.createElement('div');
						chat_list.classList.add("chat_list");
						chat_list.setAttribute("id", jsonObj.sender);
						var chat_people = document.createElement('div');
						chat_people.classList.add("chat_people");
						var chat_img = document.createElement('img');
						chat_img.classList.add("chat_img");
						chat_img.setAttribute("src",chatPic);
						var chat_ib = document.createElement('div');
						chat_ib.classList.add("chat_ib");
						var span = document.createElement('span');
						span.innerHTML = jsonObj.sender;
						let spanRead = document.createElement("span");
						spanRead.innerHTML = 1;
						spanRead.classList.add("badge", "badge-primary", "badge-pill", "float-right");
						chat_ib.appendChild(span);
						chat_ib.appendChild(spanRead);
						chat_people.appendChild(chat_img);
						chat_people.appendChild(chat_ib);
						chat_list.setAttribute("title", new Date().toLocaleString());
						chat_list.style.background = "rgba(100, 100, 100, 0.2)";
						chat_list.appendChild(chat_people);
						
						chat_list.appendChild(chat_people);
						if( inbox_chat.childElementCount == 0){
							inbox_chat.appendChild(chat_list);
						} else {
							inbox_chat.insertBefore(chat_list, inbox_chat.firstChild);
						}
						
						chat_listListener();
					} else { // 會員曾留言，則更新左側列表
						let chat_ib = dom.firstChild.lastChild;
						if( chat_ib.childElementCount == 1){
							let spanRead = document.createElement("span");
							spanRead.innerHTML = 1;
							spanRead.classList.add("badge", "badge-primary", "badge-pill", "float-right");
							chat_ib.appendChild(spanRead);
							dom.style.background = "rgba(100, 100, 100, 0.2)";
						} else {
							chat_ib.lastChild.innerText = parseInt(chat_ib.lastChild.innerText) + parseInt(1);
						}
						dom.setAttribute("title", new Date().toLocaleString());
						inbox_chat.insertBefore(dom, inbox_chat.firstChild);
					}
				}
				messagesArea.appendChild(from_msg); // 將新增的訊息區塊加進 chat 區塊
				messagesArea.scrollTop = messagesArea.scrollHeight;
			} else if ("read" === jsonObj.type){
				console.log("收到 "+jsonObj.sender+" read 訊息");
				var sender = document.querySelector('#'+jsonObj.sender);
				var receiver = document.querySelector('#'+jsonObj.receiver);
				var dom = sender==null ? receiver : sender;
				if( dom != null && dom.classList.contains("active_chat")){
					document.querySelectorAll('.chat_unread').forEach( (e)=>{
						e.innerText = " 已讀";
						e.classList.remove("chat_unread");
					})
				}
			}
		};
		
		webSocket.onclose = function(event) {
			console.log("Disconnected!");
		};
		
		// 左側頁面刷新
		function refreshMemList(jsonObj) {
			var inbox_chat = document.getElementsByClassName("inbox_chat")[0];
			inbox_chat.innerHTML = ''; 
			for (var i = 0; i < jsonObj.msgJson.length; i++) {
				var msgJson = JSON.parse(jsonObj.msgJson[i]); // 取得會員詳細資料
				
				var chat_list = document.createElement('div');
				chat_list.classList.add("chat_list");
				chat_list.setAttribute("id", msgJson.mem);
				var chat_people = document.createElement('div');
				chat_people.classList.add("chat_people");
				var chat_img = document.createElement('img');
				chat_img.classList.add("chat_img");
				chat_img.setAttribute("src",chatPic);
				//chat_img.setAttribute("alt","sunil");
				var chat_ib = document.createElement('div');
				chat_ib.classList.add("chat_ib");
				var span = document.createElement('span');
				span.innerHTML = msgJson.mem;
				chat_ib.appendChild(span);
				chat_people.appendChild(chat_img);
				chat_people.appendChild(chat_ib);
				
				chat_list.setAttribute("title", "0");
				if(msgJson.unread != 0){
					var unread = document.createElement('span');
					unread.innerHTML = msgJson.unread;
					unread.classList.add("badge", "badge-primary", "badge-pill", "float-right");
					chat_ib.appendChild(unread);
					chat_list.setAttribute("title", msgJson.latestMsgTime);
					chat_list.style.background = "rgba(100, 100, 100, 0.2)";
				}
				chat_list.appendChild(chat_people);
				inbox_chat.appendChild(chat_list);
			}
			
	        Sort();
			chat_listListener();
			
			// 發送訊息
			var inputMsg = document.getElementById("submit_message");
			inputMsg.addEventListener("keyup", function(event) {
				if (event.keyCode === 13) {
					event.preventDefault();
					document.getElementById("sendMsg").click();
				}
			});
			$("#sendMsg").click(function() {
				var inputMessage = document.getElementById("submit_message");
				var message = inputMessage.value.trim();
				var mem_no = "";
				let chat_lists = document.querySelectorAll('.chat_list');
				chat_lists.forEach( (e)=>{
					if( e.classList.contains("active_chat")){
						mem_no = e.id;
						if (message === "") {
							alert("Input a message");
							inputMessage.focus();
						} else {
							var jsonObj = {
								"type" : "chat",
								"sender" : "emp",
								"receiver" : mem_no,
								"message" : message,
								"timestamp" : new Date().toLocaleString(),
								"emp_no" : emp_no,
								"readSts" : 0
							};
							webSocket.send(JSON.stringify(jsonObj));
							inputMessage.value = "";
							inputMessage.focus();
						}
					}
				})
			});
		}
		
		// 排序左側清單
		function Sort() {
            var $domArr = $(".inbox_chat .chat_list").get();
            $domArr.sort(function (a, b) {
                var $aTime = $(a).attr('title');
                var $bTime = $(b).attr('title');
                if ($aTime && $bTime) {
                	let aDate;
                	let bDate;
                	if($aTime != '0'){
                		aDate = new Date( $aTime.split(" ")[0] );
                		let aTime = $aTime.split(" ")[1];
                		let aTimeDetail = aTime.substring(2).split(":");
                		if( aTime.charAt(0) == "下" ) {
                			aTimeDetail[0] = parseInt(aTimeDetail[0]) + parseInt(12);
                		}
                		aDate.setHours(aTimeDetail[0]);
                		aDate.setMinutes(aTimeDetail[1]);
                		aDate.setSeconds(aTimeDetail[2]);
                	} else {
                        aDate = new Date($aTime);
                	}
                	
                	if($bTime != '0'){
                		bDate = new Date( $bTime.split(" ")[0] );
                		let bTime = $bTime.split(" ")[1];
                		let bTimeDetail = bTime.substring(2).split(":");
                		if( bTime.charAt(0) == "下" ) {
                			bTimeDetail[0] = parseInt(bTimeDetail[0]) + parseInt(12);
                		}
                		bDate.setHours(bTimeDetail[0]);
                		bDate.setMinutes(bTimeDetail[1]);
                		bDate.setSeconds(bTimeDetail[2]);
                	} else {
                        bDate = new Date($bTime);
                	}
                	
                    if (aDate > bDate)
                        return -1   
                    else
                        return 1
                }
            });
            $(".inbox_chat").html($domArr);
        }
		
		
		// 建立聊天清單監聽器
		function chat_listListener(){
			let chat_lists = document.querySelectorAll('.chat_list');
			$(".chat_list").click(function(e) {
				let target = e.currentTarget;
				
				chat_lists.forEach( (e)=>{
					if( e.classList.contains("active_chat")){
						e.classList.toggle("active_chat");
					}
				})
				target.classList.add('active_chat');
				// 抓出聊天紀錄
				var jsonObj = { // 這裡要對應原本的 VO 內容
					"type" : "history", // 等同於一個 "action" 傳進去，去取得歷史訊息
					"sender" : "emp",
					"receiver" : target.id,
					"message" : "",
					"timestamp" : "",
					"emp_no" : emp_no,
					"readSts" : 0
				};
				webSocket.send(JSON.stringify(jsonObj));
			});
		}
	</script>
	<!-- Popper.JS -->
	<script src="<%=request.getContextPath()%>/back-end/js/popper-2018.min.js"></script>
	<!-- Bootstrap JS -->
	<script src="<%=request.getContextPath()%>/back-end/js/bootstrap-4.1.0.min.js"></script>
	<!-- jQuery Custom Scroller CDN -->
	<script src="<%=request.getContextPath()%>/back-end/js/jquery-3.1.5.mCustomScrollbar.concat.min.js"></script>
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
	</script>
	
</body>
</html>