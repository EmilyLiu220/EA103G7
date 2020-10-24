<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="com.meal_order.model.*"%>
<%@ page import="com.meal_order_detail.model.*"%>

<%	
	 MealOrderVO mealOrderVO = (MealOrderVO)request.getAttribute("mealOrderVO");
	MealOrderDetailService detailSrv = new MealOrderDetailService();
	List<MealOrderDetailVO> detailList = detailSrv.searchByOrderNo(mealOrderVO.getMeal_order_no());
	request.setAttribute("detailList", detailList);
//      String mem_no = "MEM0001";				//模擬假資料
//      session.setAttribute("mem_no",mem_no);	//模擬假資料
//      String emp_no = "EMP0002";				//模擬假資料
//      session.setAttribute("emp_no",emp_no);	//模擬假資料
%>
<jsp:useBean id="mealOrderSrv" class="com.meal_order.model.MealOrderService"/>
<jsp:useBean id="mealSrv" class="com.meal.model.MealService"></jsp:useBean>
<jsp:useBean id="mealSetSrv" class="com.meal_set.model.MealSetService"></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="<%= request.getContextPath() %>/front-end/css/bootstrap.min.css">
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>會員訂餐訂單</title>
<style>
*{
  box-sizing: border-box;
  margin:0;
}
.container{
/*   border:2px solid red; */
}
#top,#content{
background-color: lightgray;
color:white;
font-weight: bolder;
}
.row{
/*   border:1px solid green; */
/*   height:50px; */
  text-align:center;
  
}
.col,.col-4,.col-2{
  border:1px dashed black;
}
#header,#content div,#content{
  height:50px;
  
}
</style>
</head>
<body>
<jsp:include page="/front-end/headfinish.jsp" flush="true"/>
<div class="container">
<div id="top" class="row">
  <div class="col">
  <span>我的訂單</span>
  <c:if test="${mealOrderVO.meal_order_sts != 0}">
  <form method="POST" action="<%= request.getContextPath()%>/MealOrderServlet.do">
  <input type="hidden" name="action" value="update"/>
  <input type="hidden" name="meal_order_no" value="${mealOrderVO.meal_order_no}"/>
  <input type="submit" value="取消此訂單"/>
  <c:if test="${not empty errormsgs}"><p>${errormsgs.get("orderUpdate")}</p></c:if>
  </form>
  </c:if>
  </div>
  </div>
  <div class="row">
    <div class="col-4">訂餐編號：</div>
    <div class="col">${mealOrderVO.meal_order_no}</div>
   
  </div>
  <div class="row">
    <div class="col-4">訂餐時間：</div>
    <div class="col">${mealOrderSrv.dateFormat(mealOrderVO.order_time)}</div>
   
  </div>
  <div class="row">	
    <div class="col-4">預計取餐時間：</div>
    <div class="col">
    ${mealOrderVO.pickup_time !=null ? mealOrderSrv.dateFormat(mealOrderVO.pickup_time):'現場用餐'}</div>
   
  </div>
  <div class="row">
    <div class="col-4">訂單金額：</div>
    <div class="col">${mealOrderVO.amount} 元</div>
   
  </div>
  <div class="row">
    <div class="col-4">通知狀態：</div>
    <div class="col">${mealOrderVO.noti_sts == 0 ?'未通知':'已通知'}</div>
   
  </div>
  <div class="row">
    <div class="col-4">付款狀態：</div>
    <div class="col">${mealOrderVO.pay_sts == 0?'未付款':'已付款'}</div>
   
  </div>
  <div class="row">
    <div class="col-4">訂單狀態：</div>
    <div class="col">
    <c:if test="${mealOrderVO.meal_order_sts == 0}">已取消</c:if>
    <c:if test="${mealOrderVO.meal_order_sts == 1}">未派工</c:if>
    <c:if test="${mealOrderVO.meal_order_sts == 2}">已派工</c:if>
    <c:if test="${mealOrderVO.meal_order_sts == 3}">出餐未取</c:if>
    <c:if test="${mealOrderVO.meal_order_sts == 4}">已完成</c:if></div>
   
  </div>
  <div id="content" class="row">
    <div class="col">訂單內容</div></div>
      <div class="row">
        <div class="col">餐點名稱</div>
        <div class="col">數量</div>
        <div class="col">價格</div>
      </div>
      <c:forEach var="mealOrderDetailVO" items="${detailList}">
      <c:if test="${not empty mealOrderDetailVO.meal_no}">
      <div class="row">
         <div class="col">${mealSrv.searchByNo(mealOrderDetailVO.meal_no).meal_name}</div>
        <div class="col">${mealOrderDetailVO.qty}</div>
        <div class="col">${mealOrderDetailVO.detail_amount} 元</div>
      </div>
      </c:if>
      </c:forEach>
      <c:forEach var="mealOrderDetailVO" items="${detailList}">
      <c:if test="${not empty mealOrderDetailVO.meal_set_no}">
      <div class="row">
         <div class="col">${mealSetSrv.searchByNo(mealOrderDetailVO.meal_set_no).meal_set_name}</div>
        <div class="col">${mealOrderDetailVO.qty}</div>
        <div class="col">${mealOrderDetailVO.detail_amount} 元</div>
      </div>
       </c:if>
      </c:forEach>
    </div>
    
    <jsp:include page="/front-end/footer.jsp" flush="true"/>



</body>
</html>