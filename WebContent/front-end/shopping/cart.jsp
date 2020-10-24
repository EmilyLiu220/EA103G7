<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.* , com.meal.model.*,com.meal_set.model.*" %>
<html>
<head>
<link rel="stylesheet" href="<%= request.getContextPath() %>/front-end/css/bootstrap.min.css">
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <title>購物車</title>
 <style>
 table th,td{
 text-align: center;
 }
 #qty{
 width:100px;
 }
 form{
 display: inline;
 margin: 2px;
 }
 .removebtn ,.incrsbtn,.removebtn2,.incrsbtn2{
 text-align:center;
  width:24px; 
  height:24px; 
 background-color:orange;
  color:#fff;
  border-radius:50%;
  border:1px solid black;
  cursor:pointer;
 	font-size: 14px;
 	font-weight: bolder;
 	padding:1px;
 	
 }
 
 
 </style>
</head>
<body bgcolor="#FFFFFF">

<% Vector<MealVO> mealList = (Vector<MealVO>) session.getAttribute("mealList");%>
<% Vector<MealSetVO> setList = (Vector<MealSetVO>) session.getAttribute("setList");%>

<table id="carttable"border="1" width="350">
	<tr>
		<th>餐點名稱</th>
		<th>單價</th>
		<th>數量</th>
		<th>小計</th>
	</tr>
<% if (mealList != null && (mealList.size() > 0)) {%>

<!-- <font size="+3">已選購餐點：</font> -->

	<%
	
	 for (int index = 0; index < mealList.size(); index++) {
		MealVO mealVO = mealList.get(index);
		
	%>
	<tr>
		<td width="100"><div align="center"><b><%=mealVO.getMeal_name()%></b></div></td>
		<td width="100"><div align="center"><b><%=mealVO.getMeal_price()%> 元</b></div></td>
<%-- 		<td width="100"><div align="center"><b><%=mealVO.getMeal_qty()%></b></div></td> --%>
		
		<td width="100"><div align="center" id="qty">
<%-- 				<form name="deleteForm" action="<%= request.getContextPath() %>/Shopping.do" method="POST"> --%>
              <input type="hidden" name="action" value="removeMeal">
              <button class="removebtn">-</button>
              <input type="hidden" name="deleteMeal" value="<%= index %>">
              <input type="hidden" name="meal_no" value="<%= mealVO.getMeal_no() %>">
<!--               <input class="btn" type="submit" value="-"> </form> -->
              &nbsp;&nbsp;<b><%=mealVO.getMeal_qty()%></b>&nbsp;&nbsp;
<%-- 			<form  action="<%= request.getContextPath() %>/Shopping.do" method="POST"> --%>
              <button class="incrsbtn">+</button>
              <input type="hidden" name="meal_no" value="<%= mealVO.getMeal_no() %>">
              <input type="hidden" name="action" value="addMeal">
              <input type="hidden" name="meal_name" value="<%=mealVO.getMeal_name()%>">
              <input type="hidden" name="meal_price" value="<%=mealVO.getMeal_price()%>">
              <input type="hidden" name="meal_qty" value="1">
<!--               <input class="btn" type="submit" value="+"> -->
<!--               </form> -->
              </div>
        </td>
        <td><b><%= mealVO.getMeal_price()*mealVO.getMeal_qty()%>&nbsp;元</b></td>
	</tr>
	<%}%>
<%}%>	

	
	
<% if (setList != null && (setList.size() > 0)) {%>
	
	<%
	
	 for (int index = 0; index < setList.size(); index++) {
		MealSetVO mealSetVO = setList.get(index);
		
	%>
	<tr>
		<td width="100"><div align="center"><b><%=mealSetVO.getMeal_set_name()%></b></div></td>
		<td width="100"><div align="center"><b><%=mealSetVO.getMeal_set_price()%> 元</b></div></td>
<%-- 		<td width="100"><div align="center"><b><%=mealVO.getMeal_qty()%></b></div></td> --%>
		
		<td width="100"><div align="center" id="qty">
<%-- 				<form name="deleteForm" action="<%= request.getContextPath() %>/Shopping.do" method="POST"> --%>
              <input type="hidden" name="action" value="removeSet">
              <button class="removebtn2">-</button>
              <input type="hidden" name="deleteSet" value="<%= index %>">
              <input type="hidden" name="meal_set_no" value="<%= mealSetVO.getMeal_set_no() %>">
<!--               <input class="btn" type="submit" value="-"> </form> -->
              &nbsp;&nbsp;<b><%=mealSetVO.getMeal_set_qty()%></b>&nbsp;&nbsp;
<%-- 			<form  action="<%= request.getContextPath() %>/Shopping.do" method="POST"> --%>
              <button class="incrsbtn2">+</button>
              <input type="hidden" name="meal_set_no" value="<%= mealSetVO.getMeal_set_no() %>">
              <input type="hidden" name="action" value="addSet">
              <input type="hidden" name="meal_set_name" value="<%=mealSetVO.getMeal_set_name()%>">
              <input type="hidden" name="meal_set_price" value="<%=mealSetVO.getMeal_set_price()%>">
              <input type="hidden" name="meal_set_qty" value="1">
<!--               <input class="btn" type="submit" value="+"> -->
<!--               </form> -->
              </div>
        </td>
        <td><b><%= mealSetVO.getMeal_set_price()*mealSetVO.getMeal_set_qty()%>&nbsp;元</b></td>
	</tr>
	
	<%}%>
	<%}%>
<%-- 	<input id="amount" type="hidden" value="<%= amount%>"> --%>
<%-- 	<tr><b>總金額：<%= amount%>&nbsp;元</b> --%>
	

<%--           <form name="checkoutForm" action="<%= request.getContextPath() %>/Shopping.do" method="POST"> --%>
<!--               <input type="hidden" name="action"	value="checkout">  -->
<!--               <input type="submit" value="結帳"> -->
<!--           </form> -->

</table>



<script>
 	function init(){
 		
 		var remove = document.getElementsByClassName("removebtn");
 		for(let i=0;i<remove.length;i++){
 		    remove[i].addEventListener("click",removebtn);
 		};
 		var inc = document.getElementsByClassName("incrsbtn");
 		for(let i=0;i<inc.length;i++){
 		    inc[i].addEventListener("click",incbtn);
 		};
 		
 		var remove2 = document.getElementsByClassName("removebtn2");
 		for(let i=0;i<remove2.length;i++){
 		    remove2[i].addEventListener("click",removebtn2);
 		};
 		var inc2 = document.getElementsByClassName("incrsbtn2");
 		for(let i=0;i<inc2.length;i++){
 		    inc2[i].addEventListener("click",incbtn2);
 		};
 		
 		function removebtn(e){
		    e.preventDefault();
				        let index = $(this).next().val();
				        console.log(index);
				        let mealNo = $(this).next().next().val();
				       console.log(mealNo);
					$.ajax({
						url:"${pageContext.request.contextPath}/Shopping.do",
			            type:"POST",
			            data:{
			            	action:"removeMeal",
			                meal_no:mealNo,
			                deleteMeal:index
			            },
			           success: function(data){
						$("#carttable").empty();
	 	                $("#carttable").html(data);
						var remove = document.getElementsByClassName("removebtn");
				 		for(let i=0;i<remove.length;i++){
				 		    remove[i].addEventListener("click",removebtn);
				 		};
				 		var inc = document.getElementsByClassName("incrsbtn");
				 		for(let i=0;i<inc.length;i++){
				 		    inc[i].addEventListener("click",incbtn);
				 		};
				 		
				 		var remove2 = document.getElementsByClassName("removebtn2");
				 		for(let i=0;i<remove2.length;i++){
				 		    remove2[i].addEventListener("click",removebtn2);
				 		};
				 		var inc2 = document.getElementsByClassName("incrsbtn2");
				 		for(let i=0;i<inc2.length;i++){
				 		    inc2[i].addEventListener("click",incbtn2);
				 		};
	 	                }
					});
				};
				
				function incbtn(e){
					 e.preventDefault();
 		        let mealNo = $(this).next().val();
 		       console.log(mealNo);
 			$.ajax({
 				url:"${pageContext.request.contextPath}/Shopping.do",
 	            type:"POST",
 	            data:{
 	            	action:"addMeal",
 	                meal_no:mealNo,
 	                meal_qty:1
		 			            },
		 			          success: function(data){
		 						$("#carttable").empty();
		 	 	                $("#carttable").html(data);
		 						var remove = document.getElementsByClassName("removebtn");
		 				 		for(let i=0;i<remove.length;i++){
		 				 		    remove[i].addEventListener("click",removebtn);
		 				 		};
		 				 		var inc = document.getElementsByClassName("incrsbtn");
		 				 		for(let i=0;i<inc.length;i++){
		 				 		    inc[i].addEventListener("click",incbtn);
		 				 		};
		 				 		
		 				 		var remove2 = document.getElementsByClassName("removebtn2");
		 				 		for(let i=0;i<remove2.length;i++){
		 				 		    remove2[i].addEventListener("click",removebtn2);
		 				 		};
		 				 		var inc2 = document.getElementsByClassName("incrsbtn2");
		 				 		for(let i=0;i<inc2.length;i++){
		 				 		    inc2[i].addEventListener("click",incbtn2);
		 				 		};
		 	 	                }
		 					});
		 				};
		 				
		 				function removebtn2(e){
	 		 	  		    e.preventDefault();
	 		 	 				        let index = $(this).next().val();
	 		 	 				        console.log(index);
	 		 	 				        let mealSetNo = $(this).next().next().val();
	 		 	 				       console.log(mealSetNo);
	 		 	 					$.ajax({
	 		 	 						url:"${pageContext.request.contextPath}/Shopping.do",
	 		 	 			            type:"POST",
	 		 	 			            data:{
	 		 	 			            	action:"removeSet",
	 		 	 			                meal_set_no:mealSetNo,
	 		 	 			                deleteSet:index
	 		 	 			            },
	 		 	 			           success: function(data){
	 		 	 						$("#carttable").empty();
	 		 	 	 	                $("#carttable").html(data);
	 		 	 						var remove2 = document.getElementsByClassName("removebtn2");
	 		 	 				 		for(let i=0;i<remove2.length;i++){
	 		 	 				 		    remove2[i].addEventListener("click",removebtn2);
	 		 	 				 		};
	 		 	 				 		var inc2 = document.getElementsByClassName("incrsbtn2");
	 		 	 				 		for(let i=0;i<inc2.length;i++){
	 		 	 				 		    inc2[i].addEventListener("click",incbtn2);
	 		 	 				 		};
	 		 	 				 		
	 		 	 				 	var remove = document.getElementsByClassName("removebtn");
	 		 				 		for(let i=0;i<remove.length;i++){
	 		 				 		    remove[i].addEventListener("click",removebtn);
	 		 				 		};
	 		 				 		var inc = document.getElementsByClassName("incrsbtn");
	 		 				 		for(let i=0;i<inc.length;i++){
	 		 				 		    inc[i].addEventListener("click",incbtn);
	 		 				 		};
	 		 	 	 	                }
	 		 	 					});
	 		 	 				};
	 		 	 				
	 		 	 				function incbtn2(e){
	 		 	  					 e.preventDefault();
	 		 		 		        let mealSetNo = $(this).next().val();
	 		 		 		       console.log(mealSetNo);
	 		 		 			$.ajax({
	 		 		 				url:"${pageContext.request.contextPath}/Shopping.do",
	 		 		 	            type:"POST",
	 		 		 	            data:{
	 		 		 	            	action:"addSet",
	 		 		 	                meal_set_no:mealSetNo,
	 		 		 	                meal_set_qty:1
	 		 	 		 			            },
	 		 	 		 			          success: function(data){
	 		 	 		 						$("#carttable").empty();
	 		 	 		 	 	                $("#carttable").html(data);
	 		 	 		 						var remove2 = document.getElementsByClassName("removebtn2");
	 		 	 		 				 		for(let i=0;i<remove2.length;i++){
	 		 	 		 				 		    remove2[i].addEventListener("click",removebtn2);
	 		 	 		 				 		};
	 		 	 		 				 		var inc2 = document.getElementsByClassName("incrsbtn2");
	 		 	 		 				 		for(let i=0;i<inc2.length;i++){
	 		 	 		 				 		    inc2[i].addEventListener("click",incbtn2);
	 		 	 		 				 		};
	 		 	 		 				 		
	 		 	 		 				 	var remove = document.getElementsByClassName("removebtn");
	 		 		 				 		for(let i=0;i<remove.length;i++){
	 		 		 				 		    remove[i].addEventListener("click",removebtn);
	 		 		 				 		};
	 		 		 				 		var inc = document.getElementsByClassName("incrsbtn");
	 		 		 				 		for(let i=0;i<inc.length;i++){
	 		 		 				 		    inc[i].addEventListener("click",incbtn);
	 		 		 				 		};
	 		 	 		 	 	                }
	 		 	 		 					});
	 		 	 		 				};
 		
//  		$(".removebtn").click(function(e){
//  			  e.preventDefault();
//  		        let index = $(this).next().val();
//  		        console.log(index);
//  		        let mealNo = $(this).next().next().val();
//  		       console.log(mealNo);
//  			$.ajax({
//  				url:"${pageContext.request.contextPath}/Shopping.do",
//  	            type:"POST",
//  	            data:{
//  	            	action:"removeMeal",
//  	                meal_no:mealNo,
//  	                deleteMeal:index
//  	            }
//  			});
//  		});
 		
//  		$(".incrsbtn").click(function(e){
// 			  e.preventDefault();
// 		        let mealNo = $(this).next().val();
// 		       console.log(mealNo);
// 			$.ajax({
// 				url:"${pageContext.request.contextPath}/Shopping.do",
// 	            type:"POST",
// 	            data:{
// 	            	action:"addMeal",
// 	                meal_no:mealNo,
// 	                meal_qty:1
// 	            }
// 			});
// 		});
//  		 $("#amount").change(function(e){
//              window.location.reload();
//          });
 	};
	
 	window.onload = init;
		
		
		
    </script>
    
<script src="<%= request.getContextPath() %>/front-end/js/jquery-3.4.1.min.js"></script>
<script src="<%= request.getContextPath() %>/front-end/js/popper.min.js"></script>
<script src="<%= request.getContextPath() %>/front-end/js/bootstrap.min.js"></script>

</body>
</html>