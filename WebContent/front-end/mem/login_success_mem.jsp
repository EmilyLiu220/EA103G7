<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="BIG5"%>
<%@ page import="com.mem.model.*"%>
    
<html>
<head>
<script src="https://kit.fontawesome.com/d6c1e36c40.js" crossorigin="anonymous"></script>
<title>�|���n�J���\</title>
    
<style>

#container{
  padding-top: 50px;
  padding-bottom: 50px;
  margin:0 auto;
  width: 400px;
}

</style>
    
</head>
<body onload="getInfo()">

	<%@ include file="/front-end/headfinish.jsp"%>

	<div id="container">
<!-- 	<h3 style="margin-left:240px">�\��C��</h3><br> -->
<!-- 	<FORM METHOD="post" ACTION="mem.do" name="form1"> -->
<!-- 		<input type="submit" value="�Ӹ�d��"  style="margin-left:250px"> -->
<%-- 		<input type="hidden" name="mem_no" value="${memVO2.mem_no}"> --%>
<!-- 		<input type="hidden" name="action" value="check_info"> -->
<!-- 	</FORM> -->
	
<!-- 	<FORM METHOD="post" ACTION="mem.do" name="form1"> -->
<!-- 		<input type="submit" value="�Ӹ�M�K�X�ק�"  style="margin-left:225px"> -->
<%-- 		<input type="hidden" name="mem_no" value="${memVO2.mem_no}"> --%>
<!-- 		<input type="hidden" name="action" value="Update_info"> -->
<!-- 	</FORM> -->
		
<!-- 	<FORM METHOD="post" ACTION="mem.do" name="form1"> -->
<!-- 		<input type="submit" value="�n�X"  style="margin-left:265px"> -->
<!-- 		<input type="hidden" name="action" value="logout"> -->
<!-- 	</FORM> -->

	<div class="list-group" style="text-align:center;">
	  <a class="list-group-item list-group-item-action"><i class="fas fa-users"></i>&ensp;�|���\��C��</a>
	
	  <a href="<%=request.getContextPath() %>/front-end/mem/mem.do?action=Update_info&mem_no=${memVO2.mem_no}" class="list-group-item list-group-item-action list-group-item-primary"><i class="fas fa-utensils"></i>&ensp;�K�X�M�Ӹ�ק�</a>
	  <a href="<%=request.getContextPath() %>/front-end/mem/mem.do?action=check_info&mem_no=${memVO2.mem_no}" class="list-group-item list-group-item-action list-group-item-secondary"><i class="fas fa-utensils"></i>&ensp;�Ӹ�d��</a>
	  <a href="#" class="list-group-item list-group-item-action list-group-item-danger"><i class="fas fa-utensils"></i>&ensp;���Q�M��</a>
	  <a href="#" class="list-group-item list-group-item-action list-group-item-success"></a>
	  <a href="#" class="list-group-item list-group-item-action list-group-item-warning"></a>
	  <a href="#" class="list-group-item list-group-item-action list-group-item-info"></a>
	  <a href="#" class="list-group-item list-group-item-action list-group-item-light"></a>
	  <a href="#" class="list-group-item list-group-item-action list-group-item-dark"></a>
	</div>

	</div>
	
<script>

	//getMyInform 
	function getInfo() {
		$.ajax({
		     url:'<%=request.getContextPath() %>/front_inform/fi.do',
		     method:"POST",
		     dataType:"text",
		     data:{
		         action: 'getMyInform',
		     },
		     success:function(res){ },
		     error:function(err){},
		});
	}
	
</script>
	
	<jsp:include page="/front-end/front/footer.jsp" />
	
</body>
</html>