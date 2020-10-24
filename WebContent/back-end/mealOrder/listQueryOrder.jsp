<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.meal_order.model.*" %>
<%@ page import="com.meal_order_detail.model.*" %>
<%@ page import="java.util.*" %>

<% 
	List<MealOrderVO> orderList = (ArrayList)request.getAttribute("orderList");
// 	String mem_no = "MEM0001";				//模擬假資料
// 	request.setAttribute("mem_no",mem_no);	//模擬假資料
%>
<jsp:useBean id="mealOrderSrv" scope="page" class="com.meal_order.model.MealOrderService"/>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="<%= request.getContextPath() %>/front-end/css/bootstrap.min.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>訂餐訂單管理-所有訂單</title>
<style>
*{
font-family:Microsoft JhengHei;
  box-sizing: border-box;
  margin:0;
}
.container{
  border:2px solid red;
}
#cate{
text-align:center;
}
.row{
  border:1px solid green;
  height:100px; 
  
}
.col,.col-4,.col-2{
  border:1px dashed black;
}
#header,#content div,#content{
  height:50px;
}
#header div{
background-color: lightgray;
font-weight: bolder;
}
.query{
height:50px;}
</style>
</head>
<body>
<div class="container">
	<div class="row">
		所有訂餐訂單</div>
			<form action="<%= request.getContextPath()%>/MealOrderServlet.do" method="POST">
	<div class="row query">
				<div class="col">訂單編號：<input type="text" name="meal_order_no" size="10"/></div>
				<div class="col">員工編號：<input type="text" name="emp_no" size="10"/></div>
				<div class="col">會員編號：<input type="text" name="mem_no" size="10"/></div>
				</div>
	<div class="row query">
				<div class="col">通知狀態：<select name="noti_sts">
			 			<option value=0>未通知
			 			<option value=1>已通知
						</select></div>
				<div class="col">付款狀態：<select name="pay_sts">
			 			<option value=0>未付款
			 			<option value=1>已付款
						</select></div>
				<div class="col">訂單狀態：<select name="meal_order_sts">
			 			<option value=0>已取消
			 			<option value=1>未派工
			 			<option value=2>已派工
			 			<option value=3>出餐未取
			 			<option value=4>已完成
						</select></div>
						</div>
	<div class="row query">
				<div class="col">訂餐時間：<input type="text" name="order_time" class="f_date1"/></div>
				<div class="col-1"><span>至</span></div>
				<div class="col">訂餐時間：<input type="text" name="order_time" class="f_date1"/></div>
				<div class="col-1"><span>之間</span></div>
				</div>
	<div class="row query">			
				<div class="col">取餐時間：<input type="text" name="pickup_time" class="f_date1"/></div>
				<div class="col-1"><span>至</span></div>
				<div class="col">取餐時間：<input type="text" name="pickup_time" class="f_date1"/></div>
				<div class="col-1"><span>之間</span></div>
				<input type="submit" value="查詢結果"/>
				<input type="hidden" name="action" value="queryAll"/></div></form>

  <div id="header" class="row">
    <div class="col">訂餐編號：</div>
    <div class="col">員工編號：</div>
    <div class="col">會員編號：</div>
    <div class="col-2">訂餐時間：</div>
    <div class="col-2">預計取餐時間：</div>
    <div class="col">訂單金額：</div>
    <div class="col">通知狀態：</div>
    <div class="col">付款狀態：</div>
    <div class="col">訂單狀態：</div>
  </div>
<c:forEach var="mealOrderVO" items="${orderList}">
  
  <div id="content" class="row">
    <div class="col"><a href="<%= request.getContextPath() %>/MealOrderServlet.do?meal_order_no=${mealOrderVO.meal_order_no}&action=search">${mealOrderVO.meal_order_no}</a></div>
<%--     <div class="col-2">${mealOrderVO.mem_no}</div> --%>
	<div class="col">${mealOrderVO.emp_no}</div>
	<div class="col">${mealOrderVO.mem_no!=null ? mealOrderVO.mem_no :'非會員顧客'}</div>
	<div class="col-2">${mealOrderSrv.dateFormat(mealOrderVO.order_time)}</div>
    <div class="col-2">${mealOrderVO.pickup_time !=null ? mealOrderSrv.dateFormat(mealOrderVO.pickup_time) : '現場用餐'}</div>
    <div class="col">${mealOrderVO.amount}</div>
    <div class="col">${mealOrderVO.noti_sts == 0 ?'未通知':'已通知'}</div>
    <div class="col">${mealOrderVO.pay_sts == 0?'未付款':'已付款'}</div>
    <div class="col">
    <c:if test="${mealOrderVO.meal_order_sts == 0}">已取消</c:if>
    <c:if test="${mealOrderVO.meal_order_sts == 1}">未派工</c:if>
    <c:if test="${mealOrderVO.meal_order_sts == 2}">已派工</c:if>
    <c:if test="${mealOrderVO.meal_order_sts == 3}">出餐未取</c:if>
    <c:if test="${mealOrderVO.meal_order_sts == 4}">已完成</c:if>
    </div>
<%--   	<form action="<%= request.getContextPath() %>/MealOrderServlet.do" method="POST"> --%>
<!--   <div class="col"> -->
<%--   	<input type="hidden" name="meal_order_no" value="${mealOrderVO.meal_order_no}"/> --%>
<!--   	<input type="hidden" name="action" value="search"/> -->
<!--   	<input type="submit" value="訂單詳細"/> -->
<!--   </div> -->
<!--   	</form> -->
  </div>
  </c:forEach>
    
</div>

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
        $('.f_date1').datetimepicker({
           todayHighlight: true,
	       theme: 'dark',              //theme: 'dark',
	       timepicker:true,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d H:i',         //format:'Y-m-d H:i:s',
		   value: 0,  // value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
//            roundTime: 'round',
//            minTime:new Date(now.getFullYear(),(now.getMonth()+1),now.getDate(),now.getHours,(now.getMinutes()+30)),
//            minDate:               new Date(), // 去除今日(不含)之前
//            maxDate:               new Date(now.getFullYear(),(now.getMonth()+1),now.getDate())  // 去除今日(不含)之後
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



</body>
</html>