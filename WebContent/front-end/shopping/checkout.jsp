<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.meal.model.*" %>
<%@ page import="com.meal_set.model.*" %>
<%@ page import="com.meal_order.model.*" %>
<%@ page import="java.util.*" %>

<%-- <% MealOrderVO mealOrderVO = (MealOrderVO)request.getAttribute("mealOrderVO"); %> --%>

<!DOCTYPE html>
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
<title>Checkout</title>
<style>
 *{
  text-align:center;  
   box-sizing: border-box; 
   margin:0; 
 } 
.mycontainer{
/* text-align:center;  */
/*   border:2px solid red; */
background-color: rgba(0, 0, 0, 0.9);
}
.title {
/* background-color: lightgray; */
color:#fff;
font-size: 26px;
font-weight: bolder;
text-align: center;
height: 100px;
}
.title span{
/* background-color: lightgray; */
text-align: center;
}
.content{
color:black;
background-color: rgba(255, 255, 255, 0.8);
/*   border:1px solid green; */
/*   height:100px;  */
  
}
#cate,#amount{
color:black;
font-weight: bolder;
text-align: center;
}

.col{
   border:1px solid black; 
  	height: 50px;
}
.submit{
width: 108px;
height: 72px;
background-color: rgba(0, 0, 0, 0.9);
color:#fff;
font-weight: bolder;
}
.submit-row{
background-color: rgba(209, 209, 209, 1);
}


</style>
</head>
<body>

<jsp:include page="/front-end/headfinish.jsp" flush="true"/>

<div class="container mycontainer">
	
  <div id="title" class="row title justify-content-center">
   <span>${mem_no} 您的訂單內容：</span>
    
    </div>
  <div id="cate" class="row content">
    <div class="col">餐點名稱</div>
    <div class="col">餐點價格</div>
    <div class="col">數量</div>
    <div class="col">小計</div>
  </div>
  <c:if test="${empty res_no}">
  <c:forEach var="mealVO" items="${mealList}">
  <div id="cartcontent" class="row content">
    <div class="col">${mealVO.meal_name}</div>
    <div class="col">${mealVO.meal_price}</div>
    <div class="col">${mealVO.meal_qty}</div>
    <div class="col">${mealVO.meal_price * mealVO.meal_qty}</div>
  	</div>
  	</c:forEach>
  	
  	 <c:forEach var="mealSetVO" items="${setList}">
  <div id="cartcontent" class="row content">
    <div class="col">${mealSetVO.meal_set_name}</div>
    <div class="col">${mealSetVO.meal_set_price}</div>
    <div class="col">${mealSetVO.meal_set_qty}</div>
    <div class="col">${mealSetVO.meal_set_price * mealSetVO.meal_set_qty}</div>
  	</div>
  	</c:forEach>
  	</c:if>
  <c:if test="${not empty res_no}">
  <c:forEach var="mealVO" items="${rsvMealList}">
  <div id="cartcontent" class="row content">
    <div class="col">${mealVO.meal_name}</div>
    <div class="col">${mealVO.meal_price}</div>
    <div class="col">${mealVO.meal_qty}</div>
    <div class="col">${mealVO.meal_price * mealVO.meal_qty}</div>
  	</div>
  	</c:forEach>
  	
  	 <c:forEach var="mealSetVO" items="${rsvSetList}">
  <div id="cartcontent" class="row content">
    <div class="col">${mealSetVO.meal_set_name}</div>
    <div class="col">${mealSetVO.meal_set_price}</div>
    <div class="col">${mealSetVO.meal_set_qty}</div>
    <div class="col">${mealSetVO.meal_set_price * mealSetVO.meal_set_qty}</div>
  	</div>
  	</c:forEach>
  </c:if>
  
  
  <c:if test="${empty res_no}">
  <form method="POST" action="<%= request.getContextPath()%>/MealOrderServlet.do">
  <div id="amount" class="row content">
 	<div class="col">選擇取餐時間：<input name="pickup_time" id="f_date1"></div>
	<div class="col">總金額：${amount} 元</div>
  </div>
  <div class="row submit-row justify-content-end">
  <div class="col-2">
  <input type="hidden" name="action" value="checkout">
  <input type="hidden" name="mem_no" value="${mem_no}">
  <input type="hidden" name="amount" value="${amount}">
  <input class="submit" type="submit" value="完成結帳">
  </div>
  </div>
 </form>
  </c:if>
  <c:if test="${not empty res_no}">
  <form method="POST" action="<%= request.getContextPath()%>/MealOrderServlet.do">
  <div class="row">
  <input type="hidden" name="action" value="rsvCheckout">
  <input type="hidden" name="res_no" value="${res_no}">
  <input type="hidden" name="mem_no" value="${mem_no}">
  <input type="hidden" name="amount" value="${amount}">
  <input type="submit" value="完成訂餐">
  </div>
 </form>
 </c:if>
 
  </div>
  
  <jsp:include page="/front-end/footer.jsp" flush="true"/>




<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-end/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/front-end/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/front-end/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>

<script>
		var now = new Date();
// 		var expiry = new Date(now.getFullYear(),(now.getMonth()+1),now.getDay());
        $.datetimepicker.setLocale('zh');
        $('#f_date1').datetimepicker({
           todayHighlight: true,
	       theme: 'dark',              //theme: 'dark',
	       timepicker:true,       //timepicker:true,
	       step: 30,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d H:i',         //format:'Y-m-d H:i:s',
		   value: 0,  // value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           roundTime: 'round',
           minTime:new Date(now.getFullYear(),(now.getMonth()+1),now.getDate(),now.getHours,(now.getMinutes()+30)),
           minDate:               new Date(), // 去除今日(不含)之前
           maxDate:               new Date(now.getFullYear(),(now.getMonth()+1),now.getDate())  // 去除今日(不含)之後
        });
        
        
   
        // ----------------------------------------------------------以下用來排定無法選擇的日期-----------------------------------------------------------

        //      1.以下為某一天之前的日期無法選擇
//              var somedate1 = new Date();
//              $('#f_date1').datetimepicker({
//                  beforeShowDay: function(date) {
//                	  if (  date.getYear() <  somedate1.getYear() || 
//         		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
//         		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
//                      ) {
//                           return [false, ""]
//                      }
//                      return [true, ""];
//              }});

        
        //      2.以下為某一天之後的日期無法選擇
//              var somedate2 = new Date();
//              $('#f_date1').datetimepicker({
//                  beforeShowDay: function(date) {
//                	  if (  date.getYear() >  somedate2.getYear() || 
//         		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
//         		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
//                      ) {
//                           return [false, ""]
//                      }
//                      return [true, ""];
//              }});


        //      3.以下為兩個日期之外的日期無法選擇 (也可按需要換成其他日期)
        //      var somedate1 = new Date('2017-06-15');
        //      var somedate2 = new Date('2017-06-25');
        //      $('#f_date1').datetimepicker({
        //          beforeShowDay: function(date) {
        //        	  if (  date.getYear() <  somedate1.getYear() || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
        //		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
        //		             ||
        //		            date.getYear() >  somedate2.getYear() || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
        //		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
        //              ) {
        //                   return [false, ""]
        //              }
        //              return [true, ""];
        //      }});
        
</script>

<!-- loader -->
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
</html>