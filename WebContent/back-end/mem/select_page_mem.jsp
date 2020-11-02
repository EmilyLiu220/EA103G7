<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>會員管理主頁</title>
<script src="https://kit.fontawesome.com/d6c1e36c40.js" crossorigin="anonymous"></script>
<style>

	#loc{
 		position: relative;
		top: 150px;
		z-index: 10;
		font-size: 150%;
	}
	#container{
	  	margin:0 auto;
	  	width: 600px;
	}
	
</style>

</head>
<body>
	
<div class="wrapper">
	
	<div id="content">
	
	<div id="container">
	<div id="loc" style="margin-left:100px">
	
	<h3 style="margin-left:135px">會員管理主頁</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color:red">請修正以下錯誤:</font>
		<ul>
		    <c:forEach var="message" items="${errorMsgs}">
				<li style="color:red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
	
	<h5>資料查詢:</h5>
	
	<ul>
	  
	  <li>
	    <FORM METHOD="post" ACTION="mem.do" >
	        <b>輸入會員編號 :</b>
	        <input type="text" name="mem_no" placeholder="MEM0001" size="6px" required>
	        <input type="hidden" name="action" value="getOne_For_Display">
	        <input type="submit" value="送出">
	    </FORM>
	  </li>
	  
	  <li>
	    <FORM METHOD="post" ACTION="mem.do" >
	        <b>輸入會員姓名 :</b>
	        <input type="text" name="mem_name" placeholder="王小明" size="6px" required>
	        <input type="hidden" name="action" value="getOne_For_Display_ByName">
	        <input type="submit" value="送出">
	    </FORM>
	  </li>
	
	  <jsp:useBean id="memSvc" scope="page" class="com.mem.model.MemService" />
	   
	  <li>
	     <FORM METHOD="post" ACTION="mem.do" >
	       <b>選擇會員編號:</b>
	       <select size="1" name="mem_no">
	         <c:forEach var="memVO" items="${memSvc.all}" > 
	          <option value="${memVO.mem_no}">${memVO.mem_no}
	         </c:forEach>   
	       </select>
	       <input type="hidden" name="action" value="getOne_For_Display">
	       <input type="submit" value="送出">
	    </FORM>
	  </li>
	  
	  <li>
	     <FORM METHOD="post" ACTION="mem.do" >
	       <b>選擇會員姓名:</b>
	       <select size="1" name="mem_no">
	         <c:forEach var="memVO" items="${memSvc.all}" > 
	          <option value="${memVO.mem_no}">${memVO.mem_name}
	         </c:forEach>   
	       </select>
	       <input type="hidden" name="action" value="getOne_For_Display">
	       <input type="submit" value="送出">
	     </FORM>
	  </li>
	</ul>
	
	&emsp;&emsp;&ensp;<a class="btn btn-outline-secondary" href="listAllMem.jsp" role="button"><i class="fas fa-file-alt" style="font-size:20px"></i>&ensp;所有會員資料</a>
	&emsp;&emsp;&ensp;<a class="btn btn-outline-secondary" href="listAllMem_sts.jsp" role="button"><i class="fas fa-file-alt" style="font-size:20px"></i>&ensp;所有會員權限</a>
	
	</div>
	</div>
	</div>
</div>
	
	<jsp:include page="/back-end/siderbar/siderbar.jsp" />

</body>
</html>