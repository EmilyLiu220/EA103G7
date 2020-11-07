<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.wait_seat.model.*"%>

<%
  Wait_seatVO wait_seatVO = (Wait_seatVO) request.getAttribute("wait_seatVO"); 
%>

<html>
<head>
<title><%=wait_seatVO.getWait_seat_no()%></title>
<style>
	.loc{
		z-index: 10;
	}
	#top{
		top:100px;
		position:absolute;
	}
	td{
		width:40%;
		border:1px block solid;
	}
	
	#table-1, #table-1 td {
		background: #555;
		color: #fff;
		border: 0;
		width: 100%;
		height: 70;
		border-radius: 5px;
		text-align: center;
		box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);
	}
	.input{
		text-align: center; 
		background-color
	}
</style>
</head>
<body>
<div class="wrapper" id="top">
		<div class=loc>
	<div id="content" class="mb-2 bt-4">
		<h5 style="font-weight: 900; display: inline-block;">主管員工專區</h5>
		<span> - 候位修改</span>
		<table id="table-1">
			<tr>
				<td>
					<h3 style="margin-bottom: 0;">候位管理</h3>
				</td>
			</tr>
		</table>
		<div>
			<c:if test="${not empty updateErrorMsgs}">
				<font style="color:red">請修正以下錯誤:</font>
				<ul>
				<c:forEach var="message" items="${updateErrorMsgs}">
					<li style="color:red">${message}</li>
				</c:forEach>
				</ul>
			</c:if>
		</div>
	<form method="post" action="<%=request.getContextPath()%>/wait_seat/wait_seat.do">
	<input type="hidden" name="action" value="update">
	<table class="table table-bordered table-hover table-striped text-center">
	<tr>
		<td>候位編號</td>
		<td><%=wait_seatVO.getWait_seat_no()%>
			<input type="hidden" name="wait_seat_no" value=<%=wait_seatVO.getWait_seat_no()%>>
		</td>
	</tr>
	<tr>
		<td>會員編號</td>
		<td><input class="input" type="text" placeholder="會員姓名" name="mem_no" value=
			<%=wait_seatVO.getMem_no()==null?"":wait_seatVO.getMem_no()%>>
		</td>
	</tr>
	<tr>
		<td>非會員姓名</td>
		<td><input class="input" type="text" placeholder="非會員姓名" name="n_mem_name" value=
			<%=wait_seatVO.getN_mem_name()==null?"":wait_seatVO.getN_mem_name()%>>
		</td>
	</tr>
	<tr>	
		<td>手機號碼</td>
		<td>
			<input class="input" type="text" placeholder="手機號碼" name="phone_m" value=
			<%=wait_seatVO.getPhone_m()%>>
		</td>
	</tr>
	<tr>
		<td>延遲次數</td>
		<td>
			<%=wait_seatVO.getDelay()%>
			<input type="hidden" name="delay" value=<%=wait_seatVO.getDelay()%>>	
		</td>
	</tr>
	<tr>	
		<td>次序</td>
		<td>
			<%=wait_seatVO.getWait_n()%>
			<input type="hidden" name="wait_n" value=<%=wait_seatVO.getWait_n()%>>
		</td>
	</tr>
	<tr>
		<td colspan="2" tes>
			<input class="btn btn-secondary" type="submit" value="確定">
			<input class="btn btn-secondary" type="reset" value="重設" >
		</td>
	</tr>
	</table>
</form>
</div></div></div>	
<jsp:include page="/back-end/siderbar/siderbar.jsp" />
<script src="<%=request.getContextPath()%>/back-end/js/bootstrap.bundle.min.js"></script>
<script src="<%=request.getContextPath()%>/back-end/js/jquery.dataTables.min.js"></script>
<script src="<%=request.getContextPath()%>/back-end/js/dataTables.bootstrap4.min.js"></script>
</body>
</html>