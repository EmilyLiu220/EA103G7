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
	
<!-- CSS的部分  -->
<!-- Bootstrap CSS CDN -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.0/css/bootstrap.min.css"
	integrity="sha384-9gVQ4dYFwwWSjIDZnLEWnxCjeSWFphJiwGPXr1jddIhOegiu1FwO5qRGvFXOdJZ4"
	crossorigin="anonymous">
<!-- Our Custom CSS -->
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/back-end/css/style2.css">
<!-- Scrollbar Custom CSS -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.min.css">
<!-- Font Awesome JS -->
<script src="https://kit.fontawesome.com/d6c1e36c40.js" crossorigin="anonymous"></script>
<!-- Font family -->
<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">
  
  
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
   	body{
   		font-family: 'Nunito', sans-serif;
   	}
   	.fd_td_fdname_input,.fd_td_fdstk_input,.fd_td_fdstkll_input{
/*    		width:90%; */
   	}

</style>
      
</head>
<body>

	<div class="wrapper">

		<!-- Sidebar  -->
		<nav id="sidebar">
			<div class="sidebar-header" style="cursor: default;">
				<h3><span>EMP0009</span><br>詹詠祺，您好！</h3>
				<!-- 員工編號 ${empVO.emp_no}  員工姓名 ${empVO.emp_name} -->
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
						<li><a href="#">通知管理</a></li>
						<li><a href="#">評價管理</a></li>
						<li><a href="#">用餐時段管理</a></li>
						<li><a href="#">桌位管理</a></li>
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
				<li><a href="#" id="logIn">Log in</a></li>
				<li><a href="#" id="logOut">Log out</a></li>
			</ul>
		</nav>

		<!-- Page Content  -->
		<div id="content" class="mb-1">
			<nav class="navbar navbar-expand-lg navbar-light bg-light">
				<div class="container-fluid">
					<button type="button" id="sidebarCollapse" class="btn btn-dark">
						<i class="fas fa-align-justify"></i>
					</button>
					
					<div id="titleBig" style="margin: 0 auto; font-size: 30px; font-weight: 800; cursor: default;">
						吃Pot 吧！員工專區
					</div>
					<div id="rwdShow">
						<button type="button" id="topbarCollapse" class="btn btn-info"
							data-toggle="collapse" data-target="#navbarSupportedContent"
							aria-controls="navbarSupportedContent" aria-expanded="false"
							aria-label="Toggle navigation">
							<i class="fas fa-align-justify"></i>
						</button>
						
						<div id="titleSmall"
							style="padding-left: 10px; font-size: 30px; font-weight: 800; cursor: default;">吃
							Pot 吧！員工專區</div>
						<div class="collapse navbar-collapse" id="navbarSupportedContent">
							<ul class="nav navbar-nav ml-auto">
								<!-- 員工編號 ${empVO.emp_no}  員工姓名 ${empVO.emp_name} -->
								<li class="nav-item active"><a class="nav-link" href="#"
									id="empId" style="cursor: default;"><span>EMP0009
											詹詠祺</span>，您好！</a></li>
								<li class="nav-item active"><a class="nav-link" href="#">現場點餐</a></li>
								<li class="nav-item active"><a class="nav-link" href="#">現場劃位</a></li>
								<li class="nav-item active"><a class="nav-link" href="#">訂單結帳</a></li>
								<li class="nav-item active"><a class="nav-link" href="#">候位管理</a></li>
								<li class="nav-item active"><a class="nav-link" href="<%=request.getContextPath()%>/back-end/back-index_m.jsp">主管員工專區</a></li>
								<li class="nav-item active"><a class="nav-link" href="<%=request.getContextPath()%>/back-end/back-index_e.jsp">一般員工專區</a></li>
								<li class="nav-item active">
								<div id="topLogIn" style="display: inline-block; width: 90px; text-align: center; border-radius: 5px; background: #6d7fcc; color: #fff; cursor: pointer;">Log in</div>
								<div id="topLogOut" style="display: inline-block; width: 90px; text-align: center; margin-left: 10px; border-radius: 5px; background: #6d7fcc; color: #fff; cursor: pointer;">Log out</div></li>
							</ul>
						</div>
					</div>
				</div>
			</nav>
				
        	<div class="card shadow mb-4"> 
        		<div class="card-header py-3 "> 
        			<h6 style='display:inline' class="m-0 font-weight-bold text-primary">新增食材</h6>
        			<button id="fd_btn_addinput" type="button" class="btn btn-sm btn-dark float-right mx-1"><i class="far fa-plus-square"></i></button>
					<button id="fd_btn_subinput" type="button" class="btn btn-sm btn-dark float-right mx-1"><i class="fas fa-minus"></i></button>
        		</div>
        						
				<div class="card-body">
					<c:if test="${not empty errorMsgs}">
					<font style="color:red">無法新增，請修正以下錯誤:</font>
						<ul>
							<c:forEach var="message" items="${errorMsgs}">
								<li style="color:red">${message}</li>
							</c:forEach>
						</ul>
					</c:if>
				
				    <FORM id="fd_form_add" METHOD="post" ACTION="<%=request.getContextPath()%>/meal_part/meal_part.do" name="form1">				    
						<input type="hidden" name="action" value="insert">	
						
						<div class="container-fluid">
  							<div class="row">
								<div class="col-sm text-center">餐點編號</div>
		    					<div class="col-sm text-center">食材編號</div>
		    					<div class="col-sm text-center">食材重量</div>
							</div>
							<div class="row adddiv_original">
								<div class="col-sm text-center">
									<input class="input-group mb-1 text-center" type="hidden" name="meal_no_original" placeholder="請填入餐點編號" >
								</div>
		    					<div class="col-sm text-center">
		    						<input class="input-group mb-1 text-center" type="hidden" name="fd_no_original" placeholder="請填入食材編號" >
								</div>
		    					<div class="col-sm text-center">
		    						<input class="input-group mb-1 text-center" type="hidden" name="fd_gw_original" placeholder="請填入食材重量" >
								</div>
							</div>
							
							<div class="row adddiv">
								<div class="col-sm text-center">
									<input class="input-group mb-1 text-center" type="text" name="meal_no" placeholder="請填入餐點編號" >
								</div>
		    					<div class="col-sm text-center">
		    						<input class="input-group mb-1 text-center" type="text" name="fd_no" placeholder="請填入食材編號" >
								</div>
		    					<div class="col-sm text-center">
		    						<input class="input-group mb-1 text-center" type="text" name="fd_gw" placeholder="請填入食材重量" >
								</div>
							</div>
							<button class="btn btn-sm btn-dark float-right mx-1 fd_btn_send_addfd">送出新增</button>
						
						</div>	
					</FORM>
				</div>
        	</div>
	        <!-- DataTales Example -->
	        <div id="div-table" class="card shadow mb-4"> 
	        	<div class="card-header py-3"> 
	        		<h6 class="m-0 font-weight-bold text-primary">餐點組成管理</h6>
	        	</div>          	
				<div class="card-body">
	            	<c:if test="${not empty updateErrorMsgs}">
<%-- 						<%String updatefd_no=(String)request.getAttribute("fd_no");%> --%>
<%-- 						<font style="color:red">營養編號<%=updatefd_no%>修改失敗，請修正以下錯誤:</font> --%>
						<ul>
							<c:forEach var="message" items="${updateErrorMsgs}">
								<li style="color:red">${message}</li>
							</c:forEach>
						</ul>
					</c:if>
				<div class="table-responsive">
					<FORM id="tabledata" METHOD="post"	ACTION="<%=request.getContextPath()%>/meal_part/meal_part.do">
		                <table class="table table-sm table-striped" id="dataTable" width="100%" cellspacing="0">
							<thead>                  	
			                	<%  int rowsPerPage = list.size();%>
			                  	<tr class="text-right">
			                    	<th><input type="checkbox" name="chkAll" /></th>
									<th>餐點編號</th>
									<th>食材編號</th>
									<th>食材重量</th>
									<th></th>
									<th></th>
								</tr>
							</thead>                  
		            		<tbody>                  							
	                  			<c:forEach var="meal_partVO" items="${list}" >			
	                    			<tr class="fd_tr text-right">                    	
										<td style="width: 7%"><input type="checkbox" name="fd_no" value=${foodVO.fd_no} /></td>
										<td class="meal_part_td_mealno" style="width: 20%">${mealSvt.searchByNo(meal_partVO.meal_no).meal_name}</td>
										<td class="meal_part_td_fdno" style="width: 20%">${foodSvt.getOneFood(meal_partVO.fd_no).fd_name}</td>
										<td class="meal_part_td_fdgw" style="width: 19%">${meal_partVO.fd_gw}</td>
										<td class ="modify" style="width: 12%">
											<div class="fd_table_div">
												<button class="btn btn-info btn-sm fd_btn_input_modify" type="button" style="width: 100%">
													<i class="fas fa-edit"></i>Edit
												</button>
											</div>	
											<div class="fd_table_div_modify_hidden">
												<button class="btn btn-sm btn-light fd_btn_modify_send" type="submit" style="width: 100%">Enter</button>
											</div>				
										</td>
										<td style="width: 12%">
											<div class="fd_table_div">
												<button class="btn btn-danger btn-sm fd_btn_delete" type="button" style="width: 100%">
													<i class="fas fa-trash"></i>Delete
												</button>
											</div>
											<div class="fd_table_div_modify_hidden">
												<button class="btn btn-sm btn-light fd_btn_modify_cancel" type="button" style="width: 100%">Cancel</button>
											</div>  											
											<input class="action" type="hidden" name="action" value="delete">
										</td>
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
	
	

  <!-- Bootstrap core JavaScript-->
  <script src="<%=request.getContextPath()%>/back-end/js/jquery.min.js"></script>
  <script src="<%=request.getContextPath()%>/back-end/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="<%=request.getContextPath()%>/back-end/js/jquery.easing.min.js"></script>
  
  <!-- Page level plugins -->
  <script src="<%=request.getContextPath()%>/back-end/js/jquery.dataTables.min.js"></script>
  <script src="<%=request.getContextPath()%>/back-end/js/dataTables.bootstrap4.min.js"></script>

  <link href="<%=request.getContextPath()%>/back-end/css/dataTables.bootstrap4.min.css" rel="stylesheet">

  <!-- Popper.JS -->
  <script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"
		integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ"
		crossorigin="anonymous"></script>
  <!-- jQuery Custom Scroller CDN -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/malihu-custom-scrollbar-plugin/3.1.5/jquery.mCustomScrollbar.concat.min.js"></script>
  <script type="text/javascript">
	$(document).ready(function() {
		$("#sidebar").mCustomScrollbar({
			theme : "minimal"
		});
		
		$('#sidebarCollapse').on('click',function() {
			$('#sidebar, #content').toggleClass('active');
			$('.collapse.in').toggleClass('in');
			$('a[aria-expanded=true]').attr('aria-expanded', 'false');
		});
		
		$("#fd_btn_addinput").click(function(){
			//新增input
			$("#fd_form_add").find(".fd_btn_send_addfd").before( $("#fd_form_add").find(".adddiv_original").clone() );
			//複製一個div放在button前
			$("#fd_form_add").find(".adddiv_original:last-of-type").find("input[name='meal_no_original']").attr({
				"type":"text","name":"meal_no"
			});
			$("#fd_form_add").find(".adddiv_original:last-of-type").find("input[name='fd_no_original']").attr({
				"type":"text","name":"fd_no"
			});
			$("#fd_form_add").find(".adddiv_original:last-of-type").find("input[name='fd_gw_original']").attr({
				"type":"text","name":"fd_gw"
			});
			//更改複製div內的input的display和name，原本的name故意取別的，這樣取值的時候才不會把隱藏的input也當資料取出
			$("#fd_form_add").find(".adddiv_original:last-of-type").attr("class","row adddiv");
			//更改div，div故意取別的，這樣刪的時候才不會把隱藏的div都刪掉
			
		});
		
		$("#fd_btn_subinput").click(function(){
			//刪除input
			$("#fd_form_add").find(".adddiv:last-of-type").remove();
		});

		$("#dataTable").on("click",".fd_btn_delete",function() {
			var fdno=$(this).parents(".fd_tr").children(".fd_td_fdno").text();
			$(this).parents(".fd_tr").children(".fd_td_fdno").append("<input type='hidden' name='fd_no' value='"+fdno+"'>");
			$("#tabledata").submit();
			//按下delete按鈕後，取得該食材的食材編號，在食材編號的欄位中增加一個tpye="hidden",value為食材的編號的input標籤，submit
		});
		
		$("#dataTable").on("click",".fd_btn_input_modify",function() {
// 			var fdno=$(this).parents(".fd_tr").children(".fd_td_fdno").text();
// 			$(this).parents(".fd_tr").children(".fd_td_fdno").append("<input type='hidden' name='fd_no' value='"+fdno+"'>");
			//在no的表格增加一個隱藏的input值為fd_no
			$(this).parents(".fd_tr").find(".fd_table_div_modify_hidden").show();
			$(this).parents(".fd_tr").find(".fd_table_div").hide(); 
			//隱藏包含的Edit和Delete兩個button的div,顯示包含Enter和Cancel兩個button的div
			var mealno=$(this).parents(".fd_tr").children(".meal_part_td_mealno").text();
 			$(this).parents(".fd_tr").children(".meal_part_td_mealno").text("");
			$(this).parents(".fd_tr").children(".meal_part_td_mealno").append("<input class='meal_part_td_mealno text-right' type='text' name='meal_no' value='"+mealno+"'>");
			var fdno=$(this).parents(".fd_tr").children(".meal_part_td_fdno").text();
			$(this).parents(".fd_tr").children(".meal_part_td_fdno").text("");
			$(this).parents(".fd_tr").children(".meal_part_td_fdno").append("<input class='meal_part_td_fdno text-right' type='text' name='fd_no' value='"+fdno+"'>");
			var fdgw=$(this).parents(".fd_tr").children(".meal_part_td_fdgw").text();
			$(this).parents(".fd_tr").children(".meal_part_td_fdgw").text("");
			$(this).parents(".fd_tr").children(".meal_part_td_fdgw").append("<input class='meal_part_td_fdgw text-right' type='text' name='fd_gw' value='"+fdgw+"'>");
			//將fd_name,fd_stk,stk_ll表格的值紀錄，刪除表格內容，再新增一個input值為各表格原本的值
			$(".action").attr("value","update");
			//將action從delete改為update
			
			$("#dataTable").on("click",".fd_btn_modify_cancel",function() {								
				$(this).parents(".fd_tr").find(".fd_table_div_modify_hidden").hide();
				$(this).parents(".fd_tr").find(".fd_table_div").show();
				$(this).parents(".fd_tr").find(".meal_part_td_mealno").text(mealno);
				$(this).parents(".fd_tr").find(".fd_table_div_modify_hidden").hide();
				$(this).parents(".fd_tr").find(".fd_table_div").show();
				$(this).parents(".fd_tr").find(".meal_part_td_fdno").text(fdno);
				$(this).parents(".fd_tr").find(".fd_table_div_modify_hidden").hide();
				$(this).parents(".fd_tr").find(".fd_table_div").show();
				$(this).parents(".fd_tr").find(".meal_part_td_fdgw").text(fdgw);
// 				$(this).parents(".fd_tr").find(".fd_td_fdno").text(fdno);
				$(".action").attr("value","delete");
				//將原本的動作全部變回原本的樣子
			});
		});
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
			"autoWidth:" : false, //禁用自動列寬的計算
			"order" : [],
			"order" : [ [ 1, 'asc' ] ],
			"columnDefs" : [ {
				"orderable" : false,
				"targets" : [ 0, 4, 5 ]
			}]
				//第targets不排序,須先把原設第一欄(0)的排序清掉，設成別欄
		});
	});
</script>
</body>
</html>