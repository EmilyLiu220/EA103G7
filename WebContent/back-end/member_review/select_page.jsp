<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>IBM Member_Review: Home</title>

<style>
  table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
    border: 3px ridge Gray;
    height: 80px;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
   <tr><td><h3>IBM Member_Review: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for IBM Member_Review: Home</p>

<h3>資料查詢:</h3>
	
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
	    <c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<ul>
  <li><a href='listAllMember_Review.jsp'>List</a> all Emps.  <br><br></li>
  
  
  <li>
    <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/member_review/forwarded" >
        <b>請輸入評價編號:</b>
        <input type="text" name="review_no">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">
    </FORM>
  </li>

  <jsp:useBean id="member_reviewSvc" scope="page" class="com.member_review.model.Member_ReviewService" />
   
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/member_review/forwarded" >
       <b>請選擇評價編號:</b>
       <select size="1" name="review_no">
         <c:forEach var="member_reviewVO" items="${member_reviewSvc.all}" > 
          <option value="${member_reviewVO.review_no}">${member_reviewVO.review_no}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
    </FORM>
  </li>
  
  <li>
     <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/back-end/member_review/forwarded" >
       <b>請選擇會員評價內容:</b>
       <select size="1" name="review_no">
         <c:forEach var="member_reviewVO" items="${member_reviewSvc.all}" > 
          <option value="${member_reviewVO.review_no}">${member_reviewVO.mem_review_con}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
     </FORM>
  </li>
</ul>
 

<h3>員工管理</h3>

<ul>
  <li><a href='addMember_Review.jsp'>Add</a> a new Member_Review.</li>
</ul>

</body>
</html>