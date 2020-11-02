<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.wait_seat.model.*"%>
<% 
	Wait_seatService wait_seatSvc = new Wait_seatService();
	List<Wait_seatVO> list = wait_seatSvc.getAllForUser();
	pageContext.setAttribute("list", list);
%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
<style>
	#show{
		border-collapse:collapse;
	}
	#show,#show th,#show td{
		border:1px black solid;
	}
	#wrapper{
		width:700px; 
		margin:0 auto;
	}
	#content{
		height: 900px;
	}
	#WScount,#next{
		display: inline;
	}

</style>
</head>
<body>
	<jsp:include page="/front-end/headfinish.jsp" flush="true"/>
	<div id="wrapper">
		<div id="content-wrapper" class="d-flex flex-column">
			<div id="content">
				<div class="container-fluid">
					<div class="card shadow mb-4">
						<div class="card-header py-3">候位狀況</div>
						<div class="card-body">
							<div class="row-12">
								下一位編號:<div id="next"><%=wait_seatSvc.getFirst().getWait_seat_no()%></div>
								<br>
								等待人數:<div id="WScount"><%=wait_seatSvc.getCount()%></div>人
							</div>
							<table id="WStable" style="width: 100%;">
								<tbody>
									<tr>
										<th class="text-center">候位編號</th>
										<th class="text-center">手機號碼</th>
									</tr>
									<c:forEach var="wait_seatVO" items="${list}" >
										<tr class="fd_tr">                    	
											<td class="text-center">${wait_seatVO.wait_seat_no}</td>
											<td class="text-center">${wait_seatVO.phone_m}</td>
										</tr>						
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
		$(document).ready(function() {
			setInterval(update,5*1000);
			function update(){
				$.ajax({
					url: "${pageContext.request.contextPath}/wait_seat/wait_seat.do",
	                type: "POST",
	                data: {
	                    action: "getAll"
	                },
	                dataType: "JSON",
	                success: function (data) {
	                 	$("#WStable").html("");
	                 	let makeTable="<tr><th class='text-center'> 候位編號  </th>" +
	                 	              "<th class='text-center'> 手機號碼  </th></tr>";
	                 	$("#WStable").append(makeTable);
	                 	$.each(data,function(i,val){
	                 		makeTable="<tr><td class='text-center'>"+val.wait_seat_no+"</td>" +
	                 		          "<td class='text-center'>"+val.phone_m+"</td></tr>";
	                 		$("#WStable").append(makeTable);						
						});
						$("#next").text(data[0].wait_seat_no);
						$("#WScount").text(data.length);
	                }
				});
			}
		});
	</script>
	<jsp:include page="/front-end/footer.jsp" flush="true"/>
</body>
</html>