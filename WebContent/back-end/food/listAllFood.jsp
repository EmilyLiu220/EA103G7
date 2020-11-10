<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.food.model.*"%>
<%
	FoodService foodSvc = new FoodService();
	List<FoodVO> list = foodSvc.getAll();
	pageContext.setAttribute("list", list);
	
	List<FoodVO> warningList = new ArrayList<FoodVO>();
	for(FoodVO fd : foodSvc.getAll()){
		if(fd.getFd_stk()<fd.getStk_ll())
			warningList.add(fd);
	}
	pageContext.setAttribute("warningList", warningList);
%>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>食材管理</title>
	
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
 	#addBtn,#subBtn{
 	display:inline;
 	width:30px;
 	margin-left:5px;
 	}
	.adddiv_original{
	display:none;
	}
	#test{
	width:30px;
	height:30px;
	}
</style>
      
</head>

<body>
	<div class="wrapper" id="top">
		<div class=loc>
		<div id="content" class="mb-2 bt-4">
			<h5 style="font-weight: 900; display: inline-block;">主管員工專區</h5>
			<span> - 食材管理</span>
			<button id="addBtn" class="btn btn-secondary btn-sm float-right">+</button>
			<button id="subBtn" class="btn btn-secondary btn-sm float-right">-</button>
			<button id="test" class="btn btn-secondary btn-sm float-right" size="10"></button>
			<div id="addWaitSeat" class="my-2">
				<div class="title mb-2"><h3 style="margin-bottom: 0">新增食材</h3>
			</div>
				<form id="fd_form_add" method="post" action="<%=request.getContextPath()%>/food/food.do" name="form1">				    
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
  						<div class="adddiv_original">
	  							<div class="row">
									<div class="col-6 mx-0">
										<div class="input-title mr-1 p-0">食材名稱</div>
										<div class="input-text">
											<input style="width: 100%" class="input_fd_name my-0 p-0 text-center" type="text" name="fd_name_original" placeholder="請填入食材名稱" >
										</div>
									</div>
									<div class="col-6 mx-0">
										<div class="input-title-nut mr-1 p-0">熱量</div>
										<div class="input-text">
											<input style="width: 100%" class="input_fd_cal my-0 p-0 text-center" type="text" name="cal_original" placeholder="請填入100g食材所含的熱量" >
										</div>
									</div>																			
								</div>
								
								<div class="row">
									<div class="col-6">
										<div class="input-title mr-1 p-0">庫存量</div>
										<div class="input-text">
											<input style="width: 100%" class="input_fd_stk my-0 p-0 text-center" type="text" name="fd_stk_original" placeholder="請填入食材庫存量" >
										</div>
									</div>
									<div class="col-6">
										<div class="input-title-nut mr-1 p-0">蛋白質</div>
										<div class="input-text">
											<input style="width: 100%" class="input_fd_prot my-0 p-0 text-center" type="text" name="prot_original" placeholder="請填入100g食材所含的蛋白質" >
										</div>
									</div>																			
								</div>
								
								<div class="row">
									<div class="col-6">
										<div class="input-title mr-1 p-0">庫存底線</div>
										<div class="input-text">
											<input style="width: 100%" class="input_fd_stkll my-0 p-0 text-center" type="text" name="stk_ll_original" placeholder="請填入食材的庫存底線" >
										</div>
									</div>
									<div class="col-6">
										<div class="input-title-nut mr-1 p-0">碳水</div>
										<div class="input-text">
											<input style="width: 100%" class="input_fd_carb my-0 p-0 text-center" type="text" name="carb_original" placeholder="請填入100g食材所含的碳水" >
										</div>
									</div>																			
								</div>
								
								<div class="row">
									<div class="col-6">
										<div class="input-title mr-1 p-0"></div>
										<div class="input-text"></div>
									</div>
									<div class="col-6">
										<div class="input-title-nut mr-1 p-0">脂肪</div>
										<div class="input-text">
											<input style="width: 100%" class="input_fd_fat my-0 p-0 text-center" type="text" name="fat_original" placeholder="請填入100g食材所含的脂肪" >
										</div>
									</div>																			
								</div>
							</div>
							
							<div class="adddiv">
	  							<div class="row">
									<div class="col-6 mx-0">
										<div class="input-title mr-1 p-0">食材名稱</div>
										<div class="input-text">
											<input style="width: 100%" class="input_fd_name my-0 p-0 text-center" type="text" name="fd_name" placeholder="請填入食材名稱" >
										</div>
									</div>
									<div class="col-6 mx-0">
										<div class="input-title-nut mr-1 p-0">熱量</div>
										<div class="input-text">
											<input style="width: 100%" class="input_fd_cal my-0 p-0 text-center" type="text" name="cal" placeholder="請填入100g食材所含的熱量" >
										</div>
									</div>																			
								</div>
								
								<div class="row">
									<div class="col-6">
										<div class="input-title mr-1 p-0">庫存量</div>
										<div class="input-text">
											<input style="width: 100%" class="input_fd_stk my-0 p-0 text-center" type="text" name="fd_stk" placeholder="請填入食材庫存量" >
										</div>
									</div>
									<div class="col-6">
										<div class="input-title-nut mr-1 p-0">蛋白質</div>
										<div class="input-text">
											<input style="width: 100%" class="input_fd_prot my-0 p-0 text-center" type="text" name="prot" placeholder="請填入100g食材所含的蛋白質" >
										</div>
									</div>																			
								</div>
								
								<div class="row">
									<div class="col-6">
										<div class="input-title mr-1 p-0">庫存底線</div>
										<div class="input-text">
											<input style="width: 100%" class="input_fd_stkll my-0 p-0 text-center" type="text" name="stk_ll" placeholder="請填入食材的庫存底線" >
										</div>
									</div>
									<div class="col-6">
										<div class="input-title-nut mr-1 p-0">碳水</div>
										<div class="input-text">
											<input style="width: 100%" class="input_fd_carb my-0 p-0 text-center" type="text" name="carb" placeholder="請填入100g食材所含的碳水" >
										</div>
									</div>																			
								</div>
								
								<div class="row">
									<div class="col-6">
										<div class="input-title mr-1 p-0"></div>
										<div class="input-text"></div>
									</div>
									<div class="col-6">
										<div class="input-title-nut mr-1 p-0">脂肪</div>
										<div class="input-text">
											<input style="width: 100%" class="input_fd_fat my-0 p-0 text-center" type="text" name="fat" placeholder="請填入100g食材所含的脂肪" >
										</div>
									</div>																			
								</div>
							</div>
						<button class="btn btn-sm btn-dark float-right fd_btn_send_addfd mt-2">送出新增</button>
					</div>	
				</form>
			</div>
		<div class="title">
			<h3>食材管理</h3>
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
				<FORM id="tabledata" METHOD="post"	ACTION="<%=request.getContextPath()%>/food/food.do">
		        	<table class="table table-sm table-striped" id="dataTable" width="100%" cellspacing="0">
						<thead>                 
			                 <tr class="text-center">
			                 	<th>營養編號</th>
								<th>營養名稱</th>
								<th>庫存量</th>
								<th>庫存底線</th>
								<th>熱量</th>
								<th>蛋白質</th>
								<th>碳水</th>
								<th>脂肪</th>
								<th>刪除</th>
							</tr>
						</thead>                  
		           		<tbody>                  							
	                 		<c:forEach var="foodVO" items="${list}" >
								<tr class="text-center">
									<td class="data food_td_fd_no" style="width: 10%" onclick="location.href='<%=request.getContextPath()%>/food/food.do?action=getOne&fd_no=${foodVO.fd_no}';"><div>${foodVO.fd_no}</div></td>
									<td class="data food_td_fd_name" style="width: 20%" onclick="location.href='<%=request.getContextPath()%>/food/food.do?action=getOne&fd_no=${foodVO.fd_no}';"><div>${foodVO.fd_name}</div></td>
									<td class="data food_td_fd_stk" style="width: 20%" onclick="location.href='<%=request.getContextPath()%>/food/food.do?action=getOne&fd_no=${foodVO.fd_no}';"><div>${foodVO.fd_stk}</div></td>
									<td class="data food_td_stk_ll" style="width: 20%" onclick="location.href='<%=request.getContextPath()%>/food/food.do?action=getOne&fd_no=${foodVO.fd_no}';"><div>${foodVO.stk_ll}</div></td>
									<td class="data food_td_cal" style="width: 17%" onclick="location.href='<%=request.getContextPath()%>/food/food.do?action=getOne&fd_no=${foodVO.fd_no}';"><div>${foodVO.cal}</div></td>
									<td class="data food_td_prot" style="width: 8%" onclick="location.href='<%=request.getContextPath()%>/food/food.do?action=getOne&fd_no=${foodVO.fd_no}';"><div>${foodVO.prot}</div></td>
									<td class="data food_td_carb" style="width: 8%" onclick="location.href='<%=request.getContextPath()%>/food/food.do?action=getOne&fd_no=${foodVO.fd_no}';"><div>${foodVO.carb}</div></td>
									<td class="data food_td_fat" style="width: 8%" onclick="location.href='<%=request.getContextPath()%>/food/food.do?action=getOne&fd_no=${foodVO.fd_no}';"><div>${foodVO.fat}</div></td>
									<td style="width: 5%">
									<form method="post" action="<%=request.getContextPath()%>/food/food.do">
										<button class="del_btn btn btn-danger"><i class="fas fa-trash"></i></i></button>
										<input type="hidden" name="action" value="delete">
										<input type="hidden" name="fd_no" value=${foodVO.fd_no}>
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
  <script src="<%=request.getContextPath()%>/front-end/js/sweetalert.min.js"></script>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/back-end/css/dataTables.bootstrap4.min.css" rel="stylesheet">
  
<script type="text/javascript">
	$(document).ready(function() {
		tablestart();
	});
	
	$("#test").click(function(){
		add();
		let fd_name=document.getElementsByClassName("input_fd_name");
		fd_name[1].value="香菇1"
		fd_name[2].value="洋芋片1"
		let fd_stk=document.getElementsByClassName("input_fd_stk");
		fd_stk[1].value="1000"
		fd_stk[2].value="2000"
		let fd_stkll=document.getElementsByClassName("input_fd_stkll");
		fd_stkll[1].value="500"
		fd_stkll[2].value="1000"
		let fd_cal=document.getElementsByClassName("input_fd_cal");
		fd_cal[1].value="10000"
		fd_cal[2].value="10000"
		let fd_carb=document.getElementsByClassName("input_fd_carb");
		fd_carb[1].value="30"
		fd_carb[2].value="50"
		let fd_prot=document.getElementsByClassName("input_fd_prot");
		fd_prot[1].value="20"
		fd_prot[2].value="10"
		let fd_fat=document.getElementsByClassName("input_fd_fat");
		fd_fat[1].value="50"
		fd_fat[2].value="30"
	});
	
	function add(){
		$("#fd_form_add").find(".fd_btn_send_addfd").before( $("#fd_form_add").find(".adddiv_original").clone() );
		//複製一個div放在button前
		let adddiv_count=$("#fd_form_add").find(".adddiv");
		if(adddiv_count.length!=0){
			$("#fd_form_add").find(".adddiv_original:last-of-type").prepend("<hr>");
		}
		//如果adddiv都沒有，不加<hr>，有超過一個，要加
		$("#fd_form_add").find(".adddiv_original:last-of-type").find("input[name='fd_name_original']").attr({
			"type":"text","name":"fd_name"
		});
		$("#fd_form_add").find(".adddiv_original:last-of-type").find("input[name='fd_stk_original']").attr({
			"type":"text","name":"fd_stk"
		});
		$("#fd_form_add").find(".adddiv_original:last-of-type").find("input[name='stk_ll_original']").attr({
			"type":"text","name":"stk_ll"
		});
		$("#fd_form_add").find(".adddiv_original:last-of-type").find("input[name='cal_original']").attr({
			"type":"text","name":"cal"
		});
		$("#fd_form_add").find(".adddiv_original:last-of-type").find("input[name='prot_original']").attr({
			"type":"text","name":"prot"
		});
		$("#fd_form_add").find(".adddiv_original:last-of-type").find("input[name='carb_original']").attr({
			"type":"text","name":"carb"
		});
		$("#fd_form_add").find(".adddiv_original:last-of-type").find("input[name='fat_original']").attr({
			"type":"text","name":"fat"
		});
		//更改複製div內的input的display和name，原本的name故意取別的，這樣取值的時候才不會把隱藏的input也當資料取出
		$("#fd_form_add").find(".adddiv_original:last-of-type").attr("class","adddiv");
		//更改div，div故意取別的，這樣刪的時候才不會把隱藏的div都刪掉
	}
	var warningMsg="";
	<c:forEach var="warningVO" items="${warningList}">
		warningMsg+="食材編號"+'${warningVO.getFd_no()}'+" "+'${warningVO.getFd_name()}'+" 庫存量不足"+"\n";
	</c:forEach>
	//把錯誤訊息串起來
	if(warningMsg.length>0){
		swal({
			title: "注意食材底於底線!",
			icon: "warning",
			text: warningMsg
		});			
	}
	//sweetalert顯示訊息
	$("#fd_btn_addinput").click(function(){
		//新增input
		$("#fd_form_add").find(".fd_btn_send_addfd").before( $("#fd_form_add").find(".adddiv_original").clone() );
		//複製一個div放在button前
		let adddiv_count=$("#fd_form_add").find(".adddiv");
		if(adddiv_count.length!=0){
			$("#fd_form_add").find(".adddiv_original:last-of-type").prepend("<hr>");
		}
		//如果adddiv都沒有，不加<hr>，有超過一個，要加
		$("#fd_form_add").find(".adddiv_original:last-of-type").find("input[name='fd_name_original']").attr({
			"type":"text","name":"fd_name"
		});
		$("#fd_form_add").find(".adddiv_original:last-of-type").find("input[name='fd_stk_original']").attr({
			"type":"text","name":"fd_stk"
		});
		$("#fd_form_add").find(".adddiv_original:last-of-type").find("input[name='stk_ll_original']").attr({
			"type":"text","name":"stk_ll"
		});
		$("#fd_form_add").find(".adddiv_original:last-of-type").find("input[name='cal_original']").attr({
			"type":"text","name":"cal"
		});
		$("#fd_form_add").find(".adddiv_original:last-of-type").find("input[name='prot_original']").attr({
			"type":"text","name":"prot"
		});
		$("#fd_form_add").find(".adddiv_original:last-of-type").find("input[name='carb_original']").attr({
			"type":"text","name":"carb"
		});
		$("#fd_form_add").find(".adddiv_original:last-of-type").find("input[name='fat_original']").attr({
			"type":"text","name":"fat"
		});
		//更改複製div內的input的display和name，原本的name故意取別的，這樣取值的時候才不會把隱藏的input也當資料取出
		$("#fd_form_add").find(".adddiv_original:last-of-type").attr("class","adddiv");
		//更改div，div故意取別的，這樣刪的時候才不會把隱藏的div都刪掉
		
	});
	
	$('.data').hover(
		function(){$(this).parent().find('.data').addClass('select')},
		function(){$(this).parent().find('.data').removeClass('select')}
	);
	
	$("#addBtn").click(function(){
		//新增input
		$("#fd_form_add").find(".fd_btn_send_addfd").before( $("#fd_form_add").find(".adddiv_original").clone() );
		//複製一個div放在button前
		let adddiv_count=$("#fd_form_add").find(".adddiv");
		if(adddiv_count.length!=0){
			$("#fd_form_add").find(".adddiv_original:last-of-type").prepend("<hr>");
		}
		//如果adddiv都沒有，不加<hr>，有超過一個，要加
		$("#fd_form_add").find(".adddiv_original:last-of-type").find("input[name='fd_name_original']").attr({
			"type":"text","name":"fd_name"
		});
		$("#fd_form_add").find(".adddiv_original:last-of-type").find("input[name='fd_stk_original']").attr({
			"type":"text","name":"fd_stk"
		});
		$("#fd_form_add").find(".adddiv_original:last-of-type").find("input[name='stk_ll_original']").attr({
			"type":"text","name":"stk_ll"
		});
		$("#fd_form_add").find(".adddiv_original:last-of-type").find("input[name='cal_original']").attr({
			"type":"text","name":"cal"
		});
		$("#fd_form_add").find(".adddiv_original:last-of-type").find("input[name='prot_original']").attr({
			"type":"text","name":"prot"
		});
		$("#fd_form_add").find(".adddiv_original:last-of-type").find("input[name='carb_original']").attr({
			"type":"text","name":"carb"
		});
		$("#fd_form_add").find(".adddiv_original:last-of-type").find("input[name='fat_original']").attr({
			"type":"text","name":"fat"
		});
		//更改複製div內的input的display和name，原本的name故意取別的，這樣取值的時候才不會把隱藏的input也當資料取出
		$("#fd_form_add").find(".adddiv_original:last-of-type").attr("class","adddiv");
		//更改div，div故意取別的，這樣刪的時候才不會把隱藏的div都刪掉
		
	});
	
	$("#subBtn").click(function(){
		//刪除input
		$("#fd_form_add").find(".adddiv:last-of-type").remove();
	});
	
	
	
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
			"rowCallback": function( row, data, index ) {
		        if ( $(data[2]).text() - $(data[3]).text() <0) {
			          $(row).find('td:eq(2)').css('color', 'red');
			          $(row).find('td:eq(3)').css('color', 'red');
		          $(row).addClass("table-danger");
		        }
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