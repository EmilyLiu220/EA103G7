<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="com.meal.model.*"%>

<%
     MealService mealSrv = new MealService(); 
     List<MealVO> list = mealSrv.searchByMealSts(1); 
     request.setAttribute("list",list);
     String mem_no = "MEM0001";				//模擬假資料
     session.setAttribute("mem_no",mem_no);	//模擬假資料
     String emp_no = "EMP0002";				//模擬假資料
     session.setAttribute("emp_no",emp_no);	//模擬假資料
//      String res_no = "RESO0001";
//      request.setAttribute("res_no", res_no);
%>

<html>
<head>


    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Great+Vibes&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/open-iconic-bootstrap.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/animate.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/owl.carousel.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/owl.theme.default.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/magnific-popup.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/aos.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/ionicons.min.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/bootstrap-datepicker.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/jquery.timepicker.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/flaticon.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/icomoon.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/front-end/css/style.css">


<link rel="stylesheet" href="<%= request.getContextPath() %>/front-end/css/bootstrap.min.css">
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <title>會員所有訂餐訂單</title>
 <style>
 body{
/*  background-color:black; */
 }
 
  div.container {
  
/*       border: 1px solid red; */
/*       width: 100%; */
/*             height: 100%; */
/*              background-color: rgba(255, 255, 255, 0.9); */
    }

    div.container div.col {
/*       border: 1px solid blue; */
    }

    div.row {
      /* height: 100px; */
/*       border: 1px solid green; */
      margin-bottom: 10px;
    }
    #menu p{
	text-align: right;
    }
    #card-text h4{
    color: #dea554;
    font-weight: bolder;
    font-size: 30px;
    }
    #card-text p{
    color: black;
    font-weight: bolder;
    font-style: italic;
    font-size: 14px;
    }

    #top {
      height: 100px;
/*       border: 3px solid green; */
	margin-bottom: 50px;
    }
    #top div.col-3{
      text-align: center;
      
    }
    #top div.col-3 a{
      text-align: center;
      text-decoration:none;
  width:96px; 
  height:48px; 
 background-color:#dea554;
  color:#fff;
  border-radius:15px;
  border:3px solid white;
  cursor:pointer;
 	font-size: 24px;
 	font-weight: bolder;
 	padding:1px;
    }
    #top div.col-3 a:hover{
      text-align: center;
      text-decoration:none;
  width:96px; 
  height:48px; 
 background-color:#ffbc5e;
  color:#fff;
  border-radius:15px;
  border:3px solid black;
  cursor:pointer;
 	font-size: 24px;
 	font-weight: bolder;
 	padding:1px;
 	
    }
    #menu button{
      text-align: center;
  width:96px; 
  height:30px; 
 background-color:#dea554;
  color:#fff;
  border-radius:10px;
  border:3px solid white;
  cursor:pointer;
 	font-size: 16px;
 	font-weight: bolder;
 	padding:1px;
    }
    
    #menu button:hover{
 background-color:#ffbc5e;
  color:#fff;
  cursor:pointer;
    }
 #card-img img{
 	width:285px;
	height:191px; 
/*   	width:200px; */
/*   	height:110px; */
  }
  .increaseBtn ,.decreaseBtn,.decreaseBtn2,.increaseBtn2,.increaseRsvbtn,.increaseRsvbtn2,.decreaseRsvbtn,.decreaseRsvbtn2{
 text-align:center;
  width:24px; 
  height:24px; 
 background-color:#dea554;
  color:#fff;
  border-radius:50%;
  border:1px solid black;
  cursor:pointer;
 	font-size: 14px;
 	font-weight: bolder;
 	padding:1px;
 	
 }
 
#cartJSP button:hover{
 background-color:#ffbc5e;
 	
 }
 
 #qty{
 width:100px;
 }
 #cartJSP th{
 text-align: center;
 }
 #meal-row,#set-row{
 height: 30px;
 background-color:brown;
 color:white;
 font-weight:bold;
 }
 .errormsgs{
 font-weight: bolder;
 color:red;
 font-size:16px;
 }
 .card-margin{
   margin: 25px auto;  
 }
 
  </style>
 </head>

<body>

<jsp:include page="/front-end/headfinish.jsp" flush="true"/>
  <div class="container">
  
    <div id="top" class="row align-items-center" >
      <div class="col-3"><a href="<%= request.getContextPath() %>/front-end/shopping/mealMenu2.jsp">單點</a></div>
      <div class="col-3"><a href="<%= request.getContextPath() %>/front-end/shopping/mealSetMenu.jsp">套餐</a></div>
      <div class="col-3"><a>甜點</a></div>
      <div class="col-3"><a>飲料</a></div>
    </div>
    
    <%@ include file="/front-end/shopping/page1.file" %> 
    <div id="menu" class="row justify-content-start">
    
    <c:forEach var="mealVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
      
      <div class="col-6 card-margin">
      <div class="row align-items-center">
      <div id="card-text"class="col-4">
      <h4>${mealVO.meal_name}</h4>
      <p>單價:${mealVO.meal_price}元</p>
	<c:if test="${empty res_no}">
				 <button class="addCart">加入購物車</button>
				  <select name="meal_qty">
						<% for(int i =1;i<10;i++){ %>
						<option value="<%= i%>"><%= i%>
							<%} %>
						
				</select>
			     <input type="hidden" name="meal_no"  value="${mealVO.meal_no}">
			     </c:if>
			     
	<c:if test="${not empty res_no}">
				 <button class="addRsvCart">加入購物車</button>
				  <select name="meal_qty">
						<% for(int i =1;i<10;i++){ %>
						<option value="<%= i%>"><%= i%>
							<%} %>
						
				</select>
			     <input type="hidden" name="meal_no"  value="${mealVO.meal_no}">
			     </c:if>
      </div>
      <div id="card-img" class="col-8"><img name="meal_img" src="<%= request.getContextPath() %>/meal/meal.showPic?meal_img=${mealVO.meal_no}"/></div>
      </div>
      </div>
        </c:forEach>
      </div>
        <%@ include file="/front-end/shopping/page2.file" %> 
	<br> 
     <div id="cartJSP">
     <div id="cart-header" class="row">
    <div class="col">餐點名稱</div>
    <div class="col">單價</div>
    <div class="col">數量</div>
    <div class="col">小計</div>
  </div>
     <c:if test="${empty res_no}">
  <div id="meal-row" class="row">單點項目</div>
     <c:if test="${not empty mealList}">
   <c:forEach var="mealVO" items="${mealList}">
   <div class="row content ${mealVO.meal_no}">
    <div class="col">${mealVO.meal_name}</div>
    <div class="col">${mealVO.meal_price}&nbsp;元</div>
    <div class="col">
    <button class="decreaseBtn">-</button>
    <input type="hidden" value="${mealVO.meal_no}"/>
    <span>${mealVO.meal_qty}</span>
    <button class="increaseBtn">+</button>
    <input type="hidden" value="${mealVO.meal_no}"/>
    </div>
    <div class="col">${mealVO.meal_price * mealVO.meal_qty}&nbsp;元</div>
  </div>
  </c:forEach>
</c:if>
<div id="set-row" class="row">套餐項目</div>
<c:if test="${not empty setList}">
   <c:forEach var="mealSetVO" items="${setList}">
   <div class="row content ${mealSetVO.meal_set_no}">
    <div class="col">${mealSetVO.meal_set_name}</div>
    <div class="col">${mealSetVO.meal_set_price}&nbsp;元</div>
    <div class="col">
    <button class="decreaseBtn2">-</button>
    <input type="hidden" value="${mealSetVO.meal_set_no}"/>
    <span>${mealSetVO.meal_set_qty}</span>
    <button class="increaseBtn2">+</button>
    <input type="hidden" value="${mealSetVO.meal_set_no}"/>
    </div>
    <div class="col">${mealSetVO.meal_set_price * mealSetVO.meal_set_qty}&nbsp;元</div>
  </div>
  </c:forEach>
</c:if>
</c:if>

<c:if test="${not empty res_no}">
<div id="meal-row" class="row">單點項目</div>
     <c:if test="${not empty rsvMealList}">
   <c:forEach var="mealVO" items="${rsvMealList}">
   <div class="row content ${mealVO.meal_no}">
    <div class="col">${mealVO.meal_name}</div>
    <div class="col">${mealVO.meal_price}&nbsp;元</div>
    <div class="col">
    <button class="decreaseRsvBtn">-</button>
    <input type="hidden" value="${mealVO.meal_no}"/>
    <span>${mealVO.meal_qty}</span>
    <button class="increaseRsvBtn">+</button>
    <input type="hidden" value="${mealVO.meal_no}"/>
    </div>
    <div class="col">${mealVO.meal_price * mealVO.meal_qty}&nbsp;元</div>
  </div>
  </c:forEach>
</c:if>
<div id="set-row" class="row">套餐項目</div>
<c:if test="${not empty rsvSetList}">
   <c:forEach var="mealSetVO" items="${rsvSetList}">
   <div class="row content ${mealSetVO.meal_set_no}">
    <div class="col">${mealSetVO.meal_set_name}</div>
    <div class="col">${mealSetVO.meal_set_price}&nbsp;元</div>
    <div class="col">
    <button class="decreaseRsvBtn2">-</button>
    <input type="hidden" value="${mealSetVO.meal_set_no}"/>
    <span>${mealSetVO.meal_set_qty}</span>
    <button class="increaseRsvBtn2">+</button>
    <input type="hidden" value="${mealSetVO.meal_set_no}"/>
    </div>
    <div class="col">${mealSetVO.meal_set_price * mealSetVO.meal_set_qty}&nbsp;元</div>
  </div>
  </c:forEach>
</c:if>
</c:if>
	<c:if test="${empty res_no}">
     <div id="checkout" class="row">
     <div class="col errormsgs">${errormsgs.get("cartEmpty")}</div>
     <div class="col">	
     <form action="<%= request.getContextPath() %>/Shopping.do" method="POST">
     <input type="submit" value="結帳"/>
     <input type="hidden" name="action" value="checkout"/>
     </form></div>
     </div>
     </c:if>
     
     <c:if test="${not empty res_no}">
     <div id="checkout" class="row">
     <div class="col errormsgs">${errormsgs.get("rsvCartEmpty")}</div>
     <div class="col">	
     <form action="<%= request.getContextPath() %>/Shopping.do" method="POST">
     <input type="submit" value="確認訂餐"/>
     <input type="hidden" name="res_no" value="${res_no}"/>
     <input type="hidden" name="action" value="rsvCheckout"/>
     </form></div>
     </div>
     </c:if>
     </div> <!-- cartJSP -->
    </div>  <!-- container -->
    
    <jsp:include page="/front-end/footer.jsp" flush="true"/>

  

<script>
 	function init(){
 		
 		 var increasebtn = document.getElementsByClassName("increaseBtn");
         for (let i = 0; i < increasebtn.length; i++) {
             increasebtn[i].addEventListener("click", increase);
         }

         var decreasebtn = document.getElementsByClassName("decreaseBtn");
         for (let i = 0; i < decreasebtn.length; i++) {
             decreasebtn[i].addEventListener("click", decrease);
         }
         
         var increasebtn2 = document.getElementsByClassName("increaseBtn2");
         for (let i = 0; i < increasebtn2.length; i++) {
             increasebtn2[i].addEventListener("click", increase2);
         }

         var decreasebtn2 = document.getElementsByClassName("decreaseBtn2");
         for (let i = 0; i < decreasebtn2.length; i++) {
             decreasebtn2[i].addEventListener("click", decrease2);
         }
         
         var increaseRsvbtn = document.getElementsByClassName("increaseRsvBtn");
         for (let i = 0; i < increaseRsvbtn.length; i++) {
             increaseRsvbtn[i].addEventListener("click", increaseRsv);
         }

         var decreaseRsvbtn = document.getElementsByClassName("decreaseRsvBtn");
         for (let i = 0; i < decreaseRsvbtn.length; i++) {
             decreaseRsvbtn[i].addEventListener("click", decreaseRsv);
         }
         
         var increaseRsvbtn2 = document.getElementsByClassName("increaseRsvBtn2");
         for (let i = 0; i < increaseRsvbtn2.length; i++) {
             increaseRsvbtn2[i].addEventListener("click", increaseRsv2);
         }

         var decreaseRsvbtn2 = document.getElementsByClassName("decreaseRsvBtn2");
         for (let i = 0; i < decreaseRsvbtn2.length; i++) {
             decreaseRsvbtn2[i].addEventListener("click", decreaseRsv2);
         }

         $(".addCart").click(function (e) {
             e.preventDefault();
             $.ajax({
                 url: "${pageContext.request.contextPath}/Shopping.do",
                 type: "POST",
                 data: {
                     action: "addMeal",
                     meal_no: $(this).next().next().val(),
                     meal_qty: $(this).next().val()
                 },
                 dataType:"JSON",
                 success: function (data) {
					console.log(data.mealName);
					console.log(data.mealPrice);
					console.log(data.mealNo);
					console.log(data.mealQty);
					console.log(data.amount);
					console.log(data.match);
					console.log(data.eleindex);
					
					var eleindex = parseInt(data.eleindex);
					var match = parseInt(data.match);
					
                	 if (match === 0) {
                         var row = document.createElement("div");
                         row.classList.add("row", "content",data.mealNo);
                         row.innerHTML =
                             '<div class="col">' + data.mealName + '</div>' +
                             '<div class="col">' + data.mealPrice + '&nbsp;元</div>' +
                             '<div class="col">'+
                             '<button class="decreaseBtn">-</button>' +
                             '<input type="hidden" value="' + data.mealNo + '"/>' +
                             '<span>&nbsp;'+ data.mealQty  +'&nbsp;</span>' +
                             '<button class="increaseBtn">+</button>' +
                             '<input type="hidden" value="' + data.mealNo + '"/>' +
                             '</div>'+
                             '<div class="col">' + data.amount + '</div>'
                             ;
                         $("#set-row").before(row);

                         var increasebtn = document.getElementsByClassName("increaseBtn");
                         for (let i = 0; i < increasebtn.length; i++) {
                             increasebtn[i].addEventListener("click", increase);
                         }

                         var decreasebtn = document.getElementsByClassName("decreaseBtn");
                         for (let i = 0; i < decreasebtn.length; i++) {
                             decreasebtn[i].addEventListener("click", decrease);
                         }
                         
                     }else{
                         let mealNo = data.mealNo;
                         let mealQty = parseInt(data.mealQty);
                         let mealPrice = parseInt(data.mealPrice);
                    	 $("."+mealNo).find("span").text(" "+data.mealQty+" ");
                    	 $("."+mealNo).find("div:last-child").text(mealQty*mealPrice+" 元");
                     }
                 }
             })
         });
         
         $(".addRsvCart").click(function (e) {
             e.preventDefault();
             $.ajax({
                 url: "${pageContext.request.contextPath}/Shopping.do",
                 type: "POST",
                 data: {
                     action: "addRsvMeal",
                     meal_no: $(this).next().next().val(),
                     meal_qty: $(this).next().val()
                 },
                 dataType:"JSON",
                 success: function (data) {
					console.log(data.mealName);
					console.log(data.mealPrice);
					console.log(data.mealNo);
					console.log(data.mealQty);
					console.log(data.amount);
					console.log(data.match);
					console.log(data.eleindex);
					
					var eleindex = parseInt(data.eleindex);
					var match = parseInt(data.match);
					
                	 if (match === 0) {
                         var row = document.createElement("div");
                         row.classList.add("row", "content",data.mealNo);
                         row.innerHTML =
                             '<div class="col">' + data.mealName + '</div>' +
                             '<div class="col">' + data.mealPrice + '&nbsp;元</div>' +
                             '<div class="col">'+
                             '<button class="decreaseRsvBtn">-</button>' +
                             '<input type="hidden" value="' + data.mealNo + '"/>' +
                             '<span>&nbsp;'+ data.mealQty  +'&nbsp;</span>' +
                             '<button class="increaseRsvBtn">+</button>' +
                             '<input type="hidden" value="' + data.mealNo + '"/>' +
                             '</div>'+
                             '<div class="col">' + data.amount + '</div>'
                             ;
                         $("#set-row").before(row);

                         var increaseRsvbtn = document.getElementsByClassName("increaseRsvBtn");
                         for (let i = 0; i < increaseRsvbtn.length; i++) {
                             increaseRsvbtn[i].addEventListener("click", increaseRsv);
                         }

                         var decreaseRsvbtn = document.getElementsByClassName("decreaseRsvBtn");
                         for (let i = 0; i < decreaseRsvbtn.length; i++) {
                             decreaseRsvbtn[i].addEventListener("click", decreaseRsv);
                         }
                         
                     }else{
                         let mealNo = data.mealNo;
                         let mealQty = parseInt(data.mealQty);
                         let mealPrice = parseInt(data.mealPrice);
                    	 $("."+mealNo).find("span").text(" "+data.mealQty+" ");
                    	 $("."+mealNo).find("div:last-child").text(mealQty*mealPrice+" 元");
                     }
                 }
             })
         });



         function increase(e) {

             e.preventDefault();
            var btn = event.currentTarget;
             console.log($(this).next().val());
             $.ajax({
                 url: "${pageContext.request.contextPath}/Shopping.do",
                 type: "POST",
                 data: {
                     action: "addMeal",
                     meal_no: $(this).next().val(),
                     meal_qty: 1

                 },
                 dataType: "JSON",
                 success: function (data) {
					var eleindex = parseInt(data.eleindex);
					let mealNo = data.mealNo;
					let mealQty = parseInt(data.mealQty);
                    let mealPrice = parseInt(data.mealPrice);
                     $("."+mealNo).find("span").text(" "+data.mealQty+" ");
                     $("."+mealNo).find("div:last-child").text(mealQty*mealPrice+" 元");

                 }
             });
         };

         function decrease(e) {

             e.preventDefault();
             var btn = event.currentTarget;
             console.log($(this).next().val());
             $.ajax({
                 url: "${pageContext.request.contextPath}/Shopping.do",
                 type: "POST",
                 data: {
                     action: "removeMeal",
                     meal_no: $(this).next().val(),
                 },
                 dataType: "JSON",
                 success: function (data) {
					 let mealNo = data.mealNo;
					 let mealQty = parseInt(data.mealQty);
                     let mealPrice = parseInt(data.mealPrice);
                	 var eleindex = parseInt(data.eleindex);
                	 if (eleindex === 0) {
                         $("."+mealNo).remove();
                     }else{
                     $("."+mealNo).find("span").text(" "+data.mealQty+" ");
                     $("."+mealNo).find("div:last-child").text(mealQty*mealPrice+" 元");
                     }
                 }
             });
         };
         
         function increase2(e) {

             e.preventDefault();
             console.log($(this).next().val());
             $.ajax({
                 url: "${pageContext.request.contextPath}/Shopping.do",
                 type: "POST",
                 data: {
                     action: "addSet",
                     meal_set_no: $(this).next().val(),
                     meal_set_qty: 1

                 },
                 dataType: "JSON",
                 success: function (data) {
                	 let mealSetQty = parseInt(data.mealSetQty);
                     let mealSetPrice = parseInt(data.mealSetPrice);
					var eleindex = parseInt(data.eleindex);
					let mealSetNo = data.mealSetNo;
                     $("."+mealSetNo).find("span").text(" "+data.mealSetQty+" ");
                     $("."+mealSetNo).find("div:last-child").text(mealSetQty*mealSetPrice+" 元");

                 }
             });
         };

         function decrease2(e) {

             e.preventDefault();
             console.log($(this).next().val());
             $.ajax({
                 url: "${pageContext.request.contextPath}/Shopping.do",
                 type: "POST",
                 data: {
                     action: "removeSet",
                     meal_set_no: $(this).next().val(),
                 },
                 dataType: "JSON",
                 success: function (data) {
					 let mealSetNo = data.mealSetNo;
					 let mealSetQty = parseInt(data.mealSetQty);
                     let mealSetPrice = parseInt(data.mealSetPrice);
                	 var eleindex = parseInt(data.eleindex);
                	 if (eleindex === 0) {
                         $("."+mealSetNo).remove();
                     }else{
                     $("."+mealSetNo).find("span").text(" "+data.mealSetQty+" ");
                     $("."+mealSetNo).find("div:last-child").text(mealSetQty*mealSetPrice+" 元");
                     }
                 }
             });
         };
         
         function increaseRsv(e) {

             e.preventDefault();
             console.log($(this).next().val());
             $.ajax({
                 url: "${pageContext.request.contextPath}/Shopping.do",
                 type: "POST",
                 data: {
                     action: "addRsvMeal",
                     meal_no: $(this).next().val(),
                     meal_qty: 1

                 },
                 dataType: "JSON",
                 success: function (data) {
					var eleindex = parseInt(data.eleindex);
					let mealNo = data.mealNo;
					let mealQty = parseInt(data.mealQty);
                    let mealPrice = parseInt(data.mealPrice);
                     $("."+mealNo).find("span").text(" "+data.mealQty+" ");
                     $("."+mealNo).find("div:last-child").text(mealQty*mealPrice+" 元");

                 }
             });
         };

         function decreaseRsv(e) {

             e.preventDefault();
             console.log($(this).next().val());
             $.ajax({
                 url: "${pageContext.request.contextPath}/Shopping.do",
                 type: "POST",
                 data: {
                     action: "removeRsvMeal",
                     meal_no: $(this).next().val(),
                 },
                 dataType: "JSON",
                 success: function (data) {
					 let mealNo = data.mealNo;
					 let mealQty = parseInt(data.mealQty);
                     let mealPrice = parseInt(data.mealPrice);
                	 var eleindex = parseInt(data.eleindex);
                	 if (eleindex === 0) {
                         $("."+mealNo).remove();
                     }else{
                     $("."+mealNo).find("span").text(" "+data.mealQty+" ");
                     $("."+mealNo).find("div:last-child").text(mealQty*mealPrice+" 元");
                     }
                 }
             });
         };
         
         function increaseRsv2(e) {

             e.preventDefault();
             console.log($(this).next().val());
             $.ajax({
                 url: "${pageContext.request.contextPath}/Shopping.do",
                 type: "POST",
                 data: {
                     action: "addRsvSet",
                     meal_set_no: $(this).next().val(),
                     meal_set_qty: 1

                 },
                 dataType: "JSON",
                 success: function (data) {
                	 let mealSetQty = parseInt(data.mealSetQty);
                     let mealSetPrice = parseInt(data.mealSetPrice);
					var eleindex = parseInt(data.eleindex);
					let mealSetNo = data.mealSetNo;
                     $("."+mealSetNo).find("span").text(" "+data.mealSetQty+" ");
                     $("."+mealSetNo).find("div:last-child").text(mealSetQty*mealSetPrice+" 元");

                 }
             });
         };

         function decreaseRsv2(e) {

             e.preventDefault();
             console.log($(this).next().val());
             $.ajax({
                 url: "${pageContext.request.contextPath}/Shopping.do",
                 type: "POST",
                 data: {
                     action: "removeRsvSet",
                     meal_set_no: $(this).next().val(),
                 },
                 dataType: "JSON",
                 success: function (data) {
                	 let mealSetQty = parseInt(data.mealSetQty);
                     let mealSetPrice = parseInt(data.mealSetPrice);
					 let mealSetNo = data.mealSetNo;
                	 var eleindex = parseInt(data.eleindex);
                	 if (eleindex === 0) {
                         $("."+mealSetNo).remove();
                     }else{
                     $("."+mealSetNo).find("span").text(" "+data.mealSetQty+" ");
                     $("."+mealSetNo).find("div:last-child").text(mealSetQty*mealSetPrice+" 元");
                     }
                 }
             });
         };
         
         
         
         
 	};
	
 	window.onload = init;
		
		
		
    </script>
  <!-- body 結束標籤之前，載入Bootstrap 的 JS 及其相依性安裝(jQuery、Popper) -->
  <script src="<%= request.getContextPath() %>/front-end/js/jquery-3.4.1.min.js"></script>
  <script src="<%= request.getContextPath() %>/front-end/js/popper.min.js"></script>
  <script src="<%= request.getContextPath() %>/front-end/js/bootstrap.min.js"></script>
  
  <!-- loader -->
<!--         <div id="ftco-loader" class="show fullscreen"><svg class="circular" width="48px" height="48px"> -->
<%--                 <circle class="path-bg" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke="#eeeeee" /> --%>
<%--                 <circle class="path" cx="24" cy="24" r="22" fill="none" stroke-width="4" stroke-miterlimit="10" stroke="#F96D00" /></svg></div> --%>
        <script src="<%=request.getContextPath()%>/front-end/js/jquery.min.js"></script>
        <script src="<%=request.getContextPath()%>/front-end/js/jquery-migrate-3.0.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/front-end/js/popper.min.js"></script>
        <script src="<%=request.getContextPath()%>/front-end/js/bootstrap.min.js"></script>
        <script src="<%=request.getContextPath()%>/front-end/js/jquery.easing.1.3.js"></script>
        <script src="<%=request.getContextPath()%>/front-end/js/jquery.waypoints.min.js"></script>
        <script src="<%=request.getContextPath()%>/front-end/js/jquery.stellar.min.js"></script>
        <script src="<%=request.getContextPath()%>/front-end/js/owl.carousel.min.js"></script>
        <script src="<%=request.getContextPath()%>/front-end/js/jquery.magnific-popup.min.js"></script>
        <script src="<%=request.getContextPath()%>/front-end/js/aos.js"></script>
        <script src="<%=request.getContextPath()%>/front-end/js/jquery.animateNumber.min.js"></script>
        <script src="<%=request.getContextPath()%>/front-end/js/bootstrap-datepicker.js"></script>
        <script src="<%=request.getContextPath()%>/front-end/js/jquery.timepicker.min.js"></script>
        <script src="<%=request.getContextPath()%>/front-end/js/scrollax.min.js"></script>
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBVWaKrjvy3MaE7SQ74_uJiULgl1JY0H2s&sensor=false"></script>
        <script src="<%=request.getContextPath()%>/front-end/js/google-map.js"></script>
        <script src="<%=request.getContextPath()%>/front-end/js/main.js"></script>
  
</body>
 