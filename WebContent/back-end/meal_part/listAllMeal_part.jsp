<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.meal_part.model.*"%>
<%
	Meal_partService meal_partSvc = new Meal_partService();
	List<Meal_partVO> list = meal_partSvc.getAll();
	pageContext.setAttribute("list", list);
%>

<jsp:useBean id="foodSvt" scope="page" class="com.food.model.FoodService" />
<jsp:useBean id="mealSvt" scope="page" class="com.meal.model.MealService" />

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>餐點組成管理</title>
	
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
</style>
      
</head>

<body>
	<div class="wrapper" id="top">
		<div class=loc>
		<div id="content" class="mb-2 bt-4">
			<h5 style="font-weight: 900; display: inline-block;">主管員工專區</h5>
			<span> - 餐點組成管理</span>
			<button id="addBtn" class="btn btn-secondary btn-sm float-right">+</button>
			<button id="subBtn" class="btn btn-secondary btn-sm float-right">-</button>
			<div id="addWaitSeat" class="my-2">
				<div class="title mb-2"><h3 style="margin-bottom: 0">新增餐點組成</h3></div>
				<form id="fd_form_add" method="post" action="<%=request.getContextPath()%>/meal_part/meal_part.do" name="form1">				    
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
<!--   						<div class="row"> -->
<!-- 								<div class="col-sm text-center">會員編號</div> -->
<!-- 		    					<div class="col-sm text-center">非會員候位姓名</div> -->
<!-- 		    					<div class="col-sm text-center">行動電話</div> -->
<!-- 						</div> -->
						<div class="adddiv_original">
							<div class="meal_name mb-1">
								餐點名稱
								<select size="1" name="meal_no_original">
		         					<c:forEach var="mealVO" items="${mealSvt.all}" > 
		         						<option value="${mealVO.meal_no}">${mealVO.meal_name}
		         					</c:forEach>   
	       						</select>
       						</div>
		    				食材名稱
		    				<select size="1" name="fd_no_original">
         						<c:forEach var="foodVO" items="${foodSvt.all}" > 
         							<option value="${foodVO.fd_no}">${foodVO.fd_name}
         						</c:forEach>   
       						</select>
		    				食材重量
	 		    			<input class="mb-1 text-center input_inline" type="hidden" name="fd_gw_original" placeholder="請填入食材重量" >
						</div>
						<div class="adddiv">
							<div class="meal_name mb-1">
								餐點名稱
								<select size="1" name="meal_no">
		         					<c:forEach var="mealVO" items="${mealSvt.all}" > 
		         						<option value="${mealVO.meal_no}">${mealVO.meal_name}
		         					</c:forEach>   
	       						</select>
	       					</div>
		    				食材名稱
		    				<select size="1" name="fd_no">
         						<c:forEach var="foodVO" items="${foodSvt.all}" > 
         							<option value="${foodVO.fd_no}">${foodVO.fd_name}
         						</c:forEach>   
       						</select>
		    				食材重量
	 		    			<input class="mb-1 text-center input_inline" type="text" name="fd_gw" placeholder="請填入食材重量" >
	 		    		</div>			
						<button class="btn btn-sm btn-dark float-right fd_btn_send_addfd ">送出新增</button>
					</div>	
				</form>
			</div>
		<div class="title">
			<h3>餐點組成管理</h3> 
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
				<FORM id="tabledata" METHOD="post"	ACTION="<%=request.getContextPath()%>/meal_part/meal_part.do">
		        	<table class="table table-sm table-striped" id="dataTable" width="100%" cellspacing="0">
						<thead>                 
			                 <tr class="text-center">
			                 	<th></th>
			                 	<th>餐點名稱</th>
			                   	<th>食材名稱</th>
								<th>食材重量</th>
								<th>刪除</th>
							</tr>
						</thead>                  
		           		<tbody>                  							
	                 		<c:forEach var="meal_partVO" items="${list}" >
 	                 			<tr class="text-center">
									<td style="width: 5%"><input type="checkbox" name="fdMeal_no" value=${meal_partVO.meal_no}+${meal_partVO.fd_no} /></td>
									<td class="data meal_part_td_meal_no" style="width: 30%" onclick="location.href='<%=request.getContextPath()%>/meal_part/meal_part.do?action=getOne&meal_no=${meal_partVO.meal_no}&fd_no=${meal_partVO.fd_no}';"><div>${mealSvt.searchByNo(meal_partVO.meal_no).meal_name}</div></td>
									<td class="data meal_part_td_fd_no" style="width: 30%" onclick="location.href='<%=request.getContextPath()%>/meal_part/meal_part.do?action=getOne&meal_no=${meal_partVO.meal_no}&fd_no=${meal_partVO.fd_no}';"><div>${foodSvt.getOneFood(meal_partVO.fd_no).fd_name}</div></td>
									<td class="data meal_part_td_fd_gw" style="width: 30%" onclick="location.href='<%=request.getContextPath()%>/meal_part/meal_part.do?action=getOne&meal_no=${meal_partVO.meal_no}&fd_no=${meal_partVO.fd_no}';"><div>${meal_partVO.fd_gw}</div></td>
									
									<td style="width: 5%">
									<form method="post" action="<%=request.getContextPath()%>/meal_part/meal_part.do">
										<button class="del_btn btn btn-danger"><i class="fas fa-trash"></i></i></button>
										<input type="hidden" name="fdMeal_no" value=${meal_partVO.meal_no}+${meal_partVO.fd_no} />
										<input type="hidden" name="action" value="delete">
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
	
	$("#addBtn").click(function(){
		//新增input
		$("#fd_form_add").find(".fd_btn_send_addfd").before( $("#fd_form_add").find(".adddiv_original").clone() );
		//複製一個div放在button前
//			$("#fd_form_add").find(".adddiv_original:last-of-type").find("input[name='meal_no_original']").attr({
//				"type":"text","name":"meal_no"
//			});
		$("#fd_form_add").find(".adddiv_original:last-of-type").find(".meal_name").remove();
		
		$("#fd_form_add").find(".adddiv_original:last-of-type").find("select[name='fd_no_original']").attr({
			"type":"text","name":"fd_no"
		});
		$("#fd_form_add").find(".adddiv_original:last-of-type").find("input[name='fd_gw_original']").attr({
			"type":"text","name":"fd_gw"
		});
		//更改複製div內的input的display和name，原本的name故意取別的，這樣取值的時候才不會把隱藏的input也當資料取出
		$("#fd_form_add").find(".adddiv_original:last-of-type").attr("class","adddiv");
		//更改div，div故意取別的，這樣刪的時候才不會把隱藏的div都刪掉
		
	});
	
	$("#subBtn").click(function(){
		//刪除input
		var adddiv=$("#fd_form_add").find(".adddiv");
		if(adddiv.length!==1){
			$("#fd_form_add").find(".adddiv:last-of-type").remove();
		}
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