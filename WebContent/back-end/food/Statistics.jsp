<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.food.model.*"%>
<%
	FoodService foodSvc = new FoodService();
	List<List<String>> list = foodSvc.Statistics();
	pageContext.setAttribute("list", list);
	List<FoodVO> list2 = foodSvc.getAll();
	pageContext.setAttribute("list2", list2);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>食材統計</title>
<style>
	#loc{
		z-index: 10;
	}
   	#top{
		top:150px;
		position:absolute;
	}
</style>
</head>
	<jsp:include page="/back-end/siderbar/siderbar.jsp" />
<body>
<div class="wrapper" id="top">
	<div id="loc">
		<div id="content" class="mb-1">
			<div id="container" style="width: 750px; height: 400px; margin: 0 auto"><div>
		</div>
	</div>
</div>
<script src="<%=request.getContextPath()%>/back-end/js/highcharts.js"></script>
<!-- <script src="http://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js"></script> -->

<script language="JavaScript">
$(document).ready(function() {
	var title = {
		       text: '食材統計'   
		   };
		   var subtitle = {
		        text: '食材每月使用情況'
		   };
		   var xAxis = {
		       categories: ['一月', '二月', '三月', '四月', '五月', '六月'
		              ,'七月', '八月', '九月', '十月', '十一月', '十二月']
		   };
		   var yAxis = {
		      title: {
		         text: '公克(g)'
		      },
		      plotLines: [{
		         value: 0,
		         width: 1,
		         color: '#808080'
		      }]
		   };   

		   var tooltip = {
		      valueSuffix: '\xB0C'
		   }

		   var legend = {
		      layout: 'vertical',
		      align: 'right',
		      verticalAlign: 'middle',
		      borderWidth: 0
		   };

		   var series =  [
		      <c:forEach var="data" items="${list}" >
		      {
			     name: '${data.get(1)}',
			     data: [${data.get(3)}, ${data.get(4)}, ${data.get(5)}, ${data.get(6)}, 
			    	    ${data.get(7)}, ${data.get(8)}, ${data.get(9)}, ${data.get(10)}, 
			    	    ${data.get(11)}, ${data.get(12)}, ${data.get(13)}, ${data.get(14)}]
			  }, 
			  </c:forEach>
		   ];

		   var json = {};

		   json.title = title;
		   json.subtitle = subtitle;
		   json.xAxis = xAxis;
		   json.yAxis = yAxis;
		   json.tooltip = tooltip;
		   json.legend = legend;
		   json.series = series;

		   $('#container').highcharts(json);
});
</script>

</body>
</html>