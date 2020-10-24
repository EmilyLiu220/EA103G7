<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<title>員工查看通知</title>

<!-- Bootstrap CSS CDN -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css"
	integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
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
#table-1, #table-1 td {
	background: #555;
	color: #fff;
	border: 0;
	width: 100%;
	border-radius: 5px;
	text-align: center;
	box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);
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
				<li><a href="#">現場點餐</a></li>
				<li><a href="#">現場劃位</a></li>
				<li><a href="#">訂單結帳</a></li>
				<li><a href="#">候位管理</a></li>
				<li class="active"><a href="#pageSubmenu"
					data-toggle="collapse" aria-expanded="false"
					class="dropdown-toggle">主管員工專區</a>
					<ul class="collapse list-unstyled" id="pageSubmenu">
						<li><a href="#">員工管理</a></li>
						<li><a href="#">會員管理</a></li>
						<li><a href="#">廣告管理</a></li>
						<li><a href="#">最新消息管理</a></li>
						<li><a
							href="<%=request.getContextPath()%>/back-end/inform_set/select_is.jsp">通知設定管理</a></li>
						<li><a href="#">評價管理</a></li>
						<li><a href="#">用餐時段管理</a></li>
						<li><a
							href="<%=request.getContextPath()%>/back-end/seat/editSeat.jsp">桌位管理</a></li>
						<li><a href="#">菜單管理</a></li>
						<li><a href="#">食材管理</a></li>
						<li><a href="#">餐點組成管理</a></li>
						<li><a href="#">食材消耗統計</a></li>
						<li><a href="#">紅利商品管理</a></li>
					</ul></li>
				<li><a href="#homeSubmenu" data-toggle="collapse"
					aria-expanded="false" class="dropdown-toggle">一般員工專區</a>
					<ul class="collapse list-unstyled" id="homeSubmenu">
						<li><a
							href="<%=request.getContextPath()%>/back-end/front_inform/empCheckInform_New.jsp">查看通知</a></li>
						<li><a href="#">訂單派工</a></li>
						<li><a href="#">出餐管理</a></li>
						<li><a href="#">訂餐訂單處理</a></li>
						<li><a href="#">訂餐管理</a></li>
						<li><a href="#">訂單管理</a></li>
						<li><a href="#">訂位管理</a></li>
					</ul></li>
			</ul>

			<ul class="list-unstyled CTAs">
				<c:choose>
					<c:when test="${empVO2.emp_no==null}">
						<li><a
							href="<%=request.getContextPath()%>/back-end/emp/login.jsp"
							id="logIn">Log in</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="#" id="logOut">Log out</a></li>
					</c:otherwise>
				</c:choose>
			</ul>
		</nav>
	</div>
	<!-- Page Content  -->
	<div id="content">

		<nav class="navbar navbar-expand-lg navbar-light bg-light">
			<div class="container-fluid">

				<button type="button" id="sidebarCollapse" class="btn btn-dark">
					<svg class="svg-inline--fa fa-align-justify fa-w-14"
						aria-hidden="true" data-prefix="fas" data-icon="align-justify"
						role="img" xmlns="http://www.w3.org/2000/svg"
						viewBox="0 0 448 512" data-fa-i2svg="">
						<path fill="currentColor"
							d="M0 84V44c0-8.837 7.163-16 16-16h416c8.837 0 16 7.163 16 16v40c0 8.837-7.163 16-16 16H16c-8.837 0-16-7.163-16-16zm16 144h416c8.837 0 16-7.163 16-16v-40c0-8.837-7.163-16-16-16H16c-8.837 0-16 7.163-16 16v40c0 8.837 7.163 16 16 16zm0 256h416c8.837 0 16-7.163 16-16v-40c0-8.837-7.163-16-16-16H16c-8.837 0-16 7.163-16 16v40c0 8.837 7.163 16 16 16zm0-128h416c8.837 0 16-7.163 16-16v-40c0-8.837-7.163-16-16-16H16c-8.837 0-16 7.163-16 16v40c0 8.837 7.163 16 16 16z"></path></svg>
					<!-- <i class="fas fa-align-justify"></i> -->
				</button>
				<div id="titleBig"
					style="margin: 0 auto; font-size: 30px; font-weight: 800;">
					<a href="/EA103G7/back-end/back-index_New.jsp">吃 Pot 吧！員工專區</a>
				</div>
				<div id="rwdShow">
					<button type="button" id="topbarCollapse" class="btn btn-dark"
						data-toggle="collapse" data-target="#navbarSupportedContent"
						aria-controls="navbarSupportedContent" aria-expanded="false"
						aria-label="Toggle navigation">
						<svg class="svg-inline--fa fa-align-justify fa-w-14"
							aria-hidden="true" data-prefix="fas" data-icon="align-justify"
							role="img" xmlns="http://www.w3.org/2000/svg"
							viewBox="0 0 448 512" data-fa-i2svg="">
							<path fill="currentColor"
								d="M0 84V44c0-8.837 7.163-16 16-16h416c8.837 0 16 7.163 16 16v40c0 8.837-7.163 16-16 16H16c-8.837 0-16-7.163-16-16zm16 144h416c8.837 0 16-7.163 16-16v-40c0-8.837-7.163-16-16-16H16c-8.837 0-16 7.163-16 16v40c0 8.837 7.163 16 16 16zm0 256h416c8.837 0 16-7.163 16-16v-40c0-8.837-7.163-16-16-16H16c-8.837 0-16 7.163-16 16v40c0 8.837 7.163 16 16 16zm0-128h416c8.837 0 16-7.163 16-16v-40c0-8.837-7.163-16-16-16H16c-8.837 0-16 7.163-16 16v40c0 8.837 7.163 16 16 16z"></path></svg>
						<!-- <i class="fas fa-align-justify"></i> -->
					</button>
					<div id="titleSmall"
						style="padding-left: 10px; font-size: 30px; font-weight: 800;">
						<a href="/EA103G7/back-end/back-index_New.jsp">吃 Pot 吧！員工專區</a>
					</div>
					<div class="collapse navbar-collapse" id="navbarSupportedContent">
						<ul class="nav navbar-nav ml-auto">
							<li class="nav-item active"><a class="nav-link" href="#"
								id="empId" style="cursor: default;"> <span
									style="color: red; margin-top: 1rem;">嗨，您好！請記得登入喔！</span>



							</a></li>
							<li class="nav-item active"><a class="nav-link" href="#">現場點餐</a></li>
							<li class="nav-item active"><a class="nav-link" href="#">現場劃位</a></li>
							<li class="nav-item active"><a class="nav-link" href="#">訂單結帳</a></li>
							<li class="nav-item active"><a class="nav-link" href="#">候位管理</a></li>
							<li class="nav-item active"><a class="nav-link"
								href="/EA103G7/back-end/back-index_m.jsp">主管員工專區</a></li>
							<li class="nav-item active"><a class="nav-link"
								href="/EA103G7/back-end/back-index_e.jsp">一般員工專區</a></li>
							<li class="nav-item active"
								style="display: block; padding-top: 0.5rem; padding-bottom: 0.5rem;">


								<div id="topLogIn"
									style="display: inline-block; width: 90px; text-align: center; margin-left: 10px; border-radius: 5px; background: #424242; color: #ccc; cursor: pointer;"
									onmouseover="this.style.color='#fff'; this.style.background='#000';"
									onmouseout="this.style.color='#ccc'; this.style.background='#424242';">
									<a href="/EA103G7/back-end/emp/login.jsp">Log in</a>
								</div>



							</li>
						</ul>
					</div>
				</div>
			</div>
		</nav>

		<h5 style="font-weight: 900; display: inline-block;">主管員工專區</h5>
		<span> - 通知設定管理</span> <a href="/EA103G7/back-end/back-index_New.jsp"
			style="display: inline-block; font-size: 8px; font-weight: 900; color: #dea554; text-decoration: none; margin-left: 20px;"
			onmouseover="this.style.color='#ffbc5e';"
			onmouseout="this.style.color='#dea554';">返回首頁</a>
		<p></p>
		<table id="table-1">
			<tbody>
				<tr>
					<td>
						<h3 style="margin-bottom: 0;">查詢所有廣告</h3>
					</td>
				</tr>
			</tbody>
		</table>
		<br>
		<ul>

			<li><a href='<%=request.getContextPath() %>/back-end/ad/listAllAd.jsp'
				style="color: #dea554; font-weight: 600;"
				onmouseover="this.style.color='#ffbc5e';"
				onmouseout="this.style.color='#dea554';">顯示所有廣告</a><br>
			<br></li>
		</ul>


		<table id="table-1">
			<tbody>
				<tr>
					<td><h3 style="margin-bottom: 0;">動態查詢廣告</h3></td>
				</tr>
			</tbody>
		</table>
		<br>


		
			<span style="position: relative; left: 4%; font-weight: 600;">可自由輸入欲查詢之條件</span><br>
			<br>
			<ul>
				<li>
			<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/ad/ad.do">
				<b>廣告編號 (AD0001):</b> <input type="text" name="ad_no">
				<input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出1">
			</FORM>
		</li>
				<jsp:useBean id="adSvc" scope="page" class="com.ad.model.AdService" />
		
		<li>
			<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/ad/ad.do">
				<b>廣告編號:</b> <select size="1" name="ad_no">
					<c:forEach var="adVO" items="${adSvc.all}">
						<option value="${adVO.ad_no}">${adVO.ad_no}
					</c:forEach>
				</select> <input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出2">
			</FORM>
		</li>
				<li>
			<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/ad/ad.do">
				<b>選擇員工編號:</b> <select size="1" name="ad_no">
					<c:forEach var="adVO" items="${adSvc.all}">
						<option value="${adVO.ad_no}">${adVO.emp_no}
					</c:forEach>
				</select> <input type="hidden" name="action" value="getOne_For_Display">
				<input type="submit" value="送出3">
			</FORM>
		</li>
				
			</ul>
			
		
		<br>


		<table id="table-1">
			<tbody>
				<tr>
					<td>
						<h3 style="margin-bottom: 0;">新增廣告通知</h3>
					</td>
				</tr>
			</tbody>
		</table>
		<br>

		<ul>
			<li><a href='<%=request.getContextPath() %>/back-end/ad/addAd.jsp'
				style="color: #dea554; font-weight: 600;"
				onmouseover="this.style.color='#ffbc5e';"
				onmouseout="this.style.color='#dea554';">新增廣告設定</a></li>
		</ul>
		<p></p>
	</div>


	<!-- 		<h5 style="font-weight: 900; display: inline-block;">主管員工專區</h5> -->
	<!-- 		<span> - 廣告管理</span> <a -->
	<%-- 			href="<%=request.getContextPath()%>/back-end/back-index_New.jsp" --%>
	<!-- 			style="display: inline-block; font-size: 8px; font-weight: 900; color: #6d7fcc; text-decoration: none; margin-left: 20px;">返回首頁</a> -->
	<!-- 		<p> -->
	<!-- 		<table id="table-1"> -->
	<!-- 			<tr> -->
	<!-- 				<td> -->
	<!-- 					<h3 style="margin-bottom: 0;">廣告管理</h3> -->
	<!-- 				</td> -->
	<!-- 			</tr> -->
	<!-- 		</table> -->
	<!-- 		<br> -->
	<%-- 		<%-- 錯誤表列 --%>
	--%>
	<%-- 		<c:if test="${not empty errorMsgs}"> --%>
	<!-- 			<font style="color: red">請修正以下錯誤:</font> -->
	<!-- 			<ul> -->
	<%-- 				<c:forEach var="message" items="${errorMsgs}"> --%>
	<%-- 					<li style="color: red">${message}</li> --%>
	<%-- 				</c:forEach> --%>
	<!-- 			</ul> -->
	<%-- 		</c:if> --%>
	<!-- 	<ul> -->
	<%-- 		<li><a href='<%=request.getContextPath() %>/back-end/ad/listAllAd.jsp'>List</a> all Ad. <br>  --%>
	<!-- 		<br></li> -->

	<!-- 		<li> -->
	<%-- 			<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/ad/ad.do"> --%>
	<!-- 				<b>廣告編號 (AD0001):</b> <input type="text" name="ad_no"> -->
	<!-- 				<input type="hidden" name="action" value="getOne_For_Display"> -->
	<!-- 				<input type="submit" value="送出1"> -->
	<!-- 			</FORM> -->
	<!-- 		</li> -->
	<%-- 		<jsp:useBean id="adSvc" scope="page" class="com.ad.model.AdService" /> --%>

	<!-- 		<li> -->
	<%-- 			<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/ad/ad.do"> --%>
	<!-- 				<b>廣告編號:</b> <select size="1" name="ad_no"> -->
	<%-- 					<c:forEach var="adVO" items="${adSvc.all}"> --%>
	<%-- 						<option value="${adVO.ad_no}">${adVO.ad_no} --%>
	<%-- 					</c:forEach> --%>
	<!-- 				</select> <input type="hidden" name="action" value="getOne_For_Display"> -->
	<!-- 				<input type="submit" value="送出2"> -->
	<!-- 			</FORM> -->
	<!-- 		</li> -->
	<!-- 		<li> -->
	<%-- 			<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/ad/ad.do"> --%>
	<!-- 				<b>選擇員工編號:</b> <select size="1" name="ad_no"> -->
	<%-- 					<c:forEach var="adVO" items="${adSvc.all}"> --%>
	<%-- 						<option value="${adVO.ad_no}">${adVO.emp_no} --%>
	<%-- 					</c:forEach> --%>
	<!-- 				</select> <input type="hidden" name="action" value="getOne_For_Display"> -->
	<!-- 				<input type="submit" value="送出3"> -->
	<!-- 			</FORM> -->
	<!-- 		</li> -->

	<%-- 		<li><a href='<%=request.getContextPath() %>/back-end/ad/addAd.jsp'>Add</a> a new Ad.</li> --%>
	<!-- 	</ul> -->
</body>
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
</html>


