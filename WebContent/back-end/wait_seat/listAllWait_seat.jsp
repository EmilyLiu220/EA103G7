<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.wait_seat.model.*"%>
<jsp:useBean id="memSvc" scope="page" class="com.mem.model.MemService" />
<%
	Wait_seatService wait_seatSvc = new Wait_seatService();
	List<Wait_seatVO> list = wait_seatSvc.getAll();
	pageContext.setAttribute("list", list);
%>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>候位管理</title>
	
<!-- Font family -->
<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
<link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">

  
<style>
	#dataTable,#dataTable th{
  		line-height: 1.2;
   	} 
   	td{ 
   		height: 30px;           
   		line-height: 30px; 
   	} 
  	.div-insert-food{
  		display: flex; 
  		align-items: center;
  	}
  	.add_fd{
  		height: 30px;           
   		line-height: 30px; 
  	}
  	#div-table{
  		margin-top:5px;
  	}
  	.fd_btn_modify_cancel{
  		border:1px solid gray;
  	}  	
  	.fd_btn_modify_send{
  		border:1px solid gray;
  	}
   	.fd_table_div{ 
   		display:block; 
   	} 
  	.fd_table_div_modify_hidden{ 
   		display:none; 
   	} 
   	div{
   		font-family: 'Nunito', sans-serif;
   	}
   	.fd_td_fdname_input,.fd_td_fdstk_input,.fd_td_fdstkll_input{
/*    		width:90%; */
   	}
   	.loc{
		z-index: 10;
	}
   	#top{
		top:100px;
		position:absolute;
	}
	div.title h3{
	background: #555;
	color: #fff;
	border: 0;
	width: 100%;
	height: 70;
	border-radius: 5px;
	text-align: center;
	box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);
	}
 	.select{ 
 	color:blue; 
 	font-weight:bold; 
 	text-decoration: underline; 
 	} 

</style>
      
</head>

<body>
	<div class="wrapper" id="top">
		<div class=loc>
		<div id="content" class="mb-2 bt-4">
			<h5 style="font-weight: 900; display: inline-block;">主管員工專區</h5>
			<span> - 候位管理</span>
			<div id="addWaitSeat" class="my-2">
				<div class="title mb-2"><h3 style="margin-bottom: 0">新增候位</h3></div>
				<form id="fd_form_add" method="post" action="<%=request.getContextPath()%>/wait_seat/wait_seat.do" name="form1">				    
					<input type="hidden" name="action" value="insert">	
					<div>
						<c:if test="${not empty errorMsgs}">
							<font style="color:red">請修正以下錯誤:</font>
							<ul>
								<c:forEach var="message" items="${errorMsgs}">
									<li style="color:red">${message}</li>
								</c:forEach>
							</ul>
						</c:if>
					</div>
					<div class="mb-5">
  						<div class="row">
								<div class="col-sm text-center">會員編號</div>
		    					<div class="col-sm text-center">非會員候位姓名</div>
		    					<div class="col-sm text-center">行動電話</div>
							</div>
						<div class="row adddiv_original">
								<div class="col-sm text-center">
									<input class="input-group mb-1 text-center" type="hidden" name="mem_no_original" placeholder="請填入會員編號" >
								</div>
		    					<div class="col-sm text-center">
		    						<input class="input-group mb-1 text-center" type="hidden" name="n_mem_name_original" placeholder="請填入非會員候位姓名" >
								</div>
		    					<div class="col-sm text-center">
		    						<input class="input-group mb-1 text-center" type="hidden" name="phone_m_original" placeholder="請填入行動電話" >
								</div>
							</div>
						<div class="row adddiv">
								<div class="col-sm text-center">
									<input class="input-group mb-1 text-center" type="text" name="mem_no" placeholder="請填入會員編號" >
								</div>
		    					<div class="col-sm text-center">
		    						<input class="input-group mb-1 text-center" type="text" name="n_mem_name" placeholder="請填入非會員候位姓名" >
								</div>
		    					<div class="col-sm text-center">
		    						<input class="input-group mb-1 text-center" type="text" name="phone_m" placeholder="請填入行動電話" >
								</div>
							</div>
						<button class="btn btn-sm btn-dark float-right fd_btn_send_addfd ">送出新增</button>
					</div>	
				</form>
			</div>
		<div class="title">
			<h3>候位管理</h3> 
			<div><form method="post" action="<%=request.getContextPath()%>/wait_seat/wait_seat.do">
			<input type="hidden" name="action" value="delay_update">
			<input type="submit" value="未到" class="mb-2 btn btn-sm btn-dark float-right">
			</form></div>
		</div>
		<div class="card-body">
	    	<c:if test="${not empty updateErrorMsgs}">
	    		<ul>
					<c:forEach var="message" items="${updateErrorMsgs}">
						<li style="color:red">${message}</li>
					</c:forEach>
				</ul>
			</c:if>
		<div id="table" class="table-responsive">
				<FORM id="tabledata" METHOD="post"	ACTION="<%=request.getContextPath()%>/wait_seat/wait_seat.do">
		        	<table class="table table-sm table-striped" id="dataTable" width="100%" cellspacing="0">
						<thead>                 
			                 <tr class="text-center">
			                 	<th>次序</th>
			                   	<th>候位編號</th>
								<th>會員姓名</th>
								<th>非會員姓名</th>
								<th>行動電話</th>
								<th>未在場</th>
								<th>刪除</th>
							</tr>
						</thead>                  
		           		<tbody>                  							
	                 		<c:forEach var="wait_seatVO" items="${list}" >
<%-- 	                 			<tr class="fd_tr text-center" onclick="location.href='<%=request.getContextPath()%>/wait_seat/wait_seat.do?action=getOne&wait_seat_no=${wait_seatVO.wait_seat_no}';">	 --%>
								<tr class="text-center">
									<td class="data wait_seat_td_with_n" style="width: 10%" onclick="location.href='<%=request.getContextPath()%>/wait_seat/wait_seat.do?action=getOne&wait_seat_no=${wait_seatVO.wait_seat_no}';"><div>${wait_seatVO.wait_n}</div></td>
									<td class="data wait_seat_td_wait_seatno" style="width: 20%" onclick="location.href='<%=request.getContextPath()%>/wait_seat/wait_seat.do?action=getOne&wait_seat_no=${wait_seatVO.wait_seat_no}';"><div>${wait_seatVO.wait_seat_no}</div></td>
									<td class="data wait_seat_td_memno" style="width: 20%" onclick="location.href='<%=request.getContextPath()%>/wait_seat/wait_seat.do?action=getOne&wait_seat_no=${wait_seatVO.wait_seat_no}';"><div>${memSvc.getOneMem(wait_seatVO.mem_no).mem_name}</div></td>
									<td class="data wait_seat_td_n_mem_name" style="width: 20%" onclick="location.href='<%=request.getContextPath()%>/wait_seat/wait_seat.do?action=getOne&wait_seat_no=${wait_seatVO.wait_seat_no}';"><div>${wait_seatVO.n_mem_name}</div></td>
									<td class="data wait_seat_td_phone_m" style="width: 17%" onclick="location.href='<%=request.getContextPath()%>/wait_seat/wait_seat.do?action=getOne&wait_seat_no=${wait_seatVO.wait_seat_no}';"><div>${wait_seatVO.phone_m}</div></td>
									<td class="data wait_seat_td_delay" style="width: 8%" onclick="location.href='<%=request.getContextPath()%>/wait_seat/wait_seat.do?action=getOne&wait_seat_no=${wait_seatVO.wait_seat_no}';"><div>${wait_seatVO.delay}</div></td>
									<td style="width: 5%">
									<form method="post" action="<%=request.getContextPath()%>/wait_seat/wait_seat.do">
										<button class="del_btn btn btn-danger"><i class="fas fa-trash"></i></i></button>
										<input type="hidden" name="action" value="delete">
										<input type="hidden" name="wait_seat_no" value=${wait_seatVO.wait_seat_no}>
									</form></td>
								</tr>
					  		</c:forEach>
		            	</tbody>
		    		</table>	                
				</FORM>
	    	</div>
		</div>
	</div>
</div>
</div>

<jsp:include page="/back-end/siderbar/siderbar.jsp" />
  <script src="<%=request.getContextPath()%>/back-end/js/bootstrap.bundle.min.js"></script>
  <script src="<%=request.getContextPath()%>/back-end/js/jquery.dataTables.min.js"></script>
  <script src="<%=request.getContextPath()%>/back-end/js/dataTables.bootstrap4.min.js"></script>

  <link rel="stylesheet" href="<%=request.getContextPath()%>/back-end/css/dataTables.bootstrap4.min.css" rel="stylesheet">
  
<script type="text/javascript">
	$(document).ready(function() {
		tablestart();
	});
	
	$('.data').hover(
		function(){$(this).parent().find('.data').addClass('select')},
		function(){$(this).parent().find('.data').removeClass('select')}
	);
	
	function tablestart(){
		table = $("#dataTable").DataTable({
			language : {
				"sProcessing" : "處理中...",
				"sLengthMenu" : "顯示 _MENU_ 項結果",
				"sZeroRecords" : "沒有匹配結果",
				"sInfo" : "顯示第 _START_ 至 _END_ 項結果，共 _TOTAL_ 項",
				"sInfoEmpty" : "顯示第 0 至 0 項結果，共 0 項",
				"sInfoFiltered" : "(由 _MAX_ 項結果過濾)",
				"sInfoPostFix" : "",
				"sSearch" : "搜索:",
				"sUrl" : "",
				"sEmptyTable" : "表中數據為空",
				"sLoadingRecords" : "載入中...",
				"sInfoThousands" : ",",
				"oPaginate" : {
					"sFirst" : "首頁",
					"sPrevious" : "上頁",
					"sNext" : "下頁",
					"sLast" : "末頁"
				},
			},
			"autoWidth:" : true, //禁用自動列寬的計算
			"ordering":false,
// 			"searching": false,
			"order" : [],
			"order" : [ [ 1, 'asc' ] ],
			"columnDefs" : [ {
				"orderable" : false,
				"targets" : [ 0, 4, 5 ]
			}]
				//第targets不排序,須先把原設第一欄(0)的排序清掉，設成別欄
		});
	}
</script>
</body>
</html>