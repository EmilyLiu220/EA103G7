<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>Time Setting</title></head>
<body>
<br>
<div class="rest_day_setting">
	<!-- onfocus="this.blur()" input 不給輸入-->
	開始日期：<input id="start_date" name="start_date" type="text" onfocus="this.blur()">
	<br><br>
	結束日期：<input id="end_date" name="end_date" type="text" onfocus="this.blur()">
	<br><br>
	固定店休日(開始)：
	<select id="rest_day_fix_start" name="rest_day_fix_start">
		<option class="rest_day_week" value="-1">--請選擇--</option>
		<option class="rest_day_week" value="0">星期日</option>
		<option class="rest_day_week" value="1">星期一</option>
		<option class="rest_day_week" value="2">星期二</option>
		<option class="rest_day_week" value="3">星期三</option>
		<option class="rest_day_week" value="4">星期四</option>
		<option class="rest_day_week" value="5">星期五</option>
		<option class="rest_day_week" value="6">星期六</option>
	</select>
	<br><br>
	固定店休日(結束)：
	<select id="rest_day_fix_start" name="rest_day_fix_start">
		<option class="rest_day_week" value="-1">--請選擇--</option>
		<option class="rest_day_week" value="0">星期日</option>
		<option class="rest_day_week" value="1">星期一</option>
		<option class="rest_day_week" value="2">星期二</option>
		<option class="rest_day_week" value="3">星期三</option>
		<option class="rest_day_week" value="4">星期四</option>
		<option class="rest_day_week" value="5">星期五</option>
		<option class="rest_day_week" value="6">星期六</option>
	</select>
</div>

</body>
<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->
<!-- 參考網站: https://xdsoft.net/jqplugins/datetimepicker/ -->
<link   rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>

<script>
$.datetimepicker.setLocale('zh'); // kr ko ja en
$(function(){
	 $('#start_date').datetimepicker({
	  format:'Y-m-d',
	  onShow:function(){
	   this.setOptions({
	    maxDate:$('#end_date').val()?$('#end_date').val():false
	   })
	  },
	  timepicker:false
	 });
	 
	 $('#end_date').datetimepicker({
	  format:'Y-m-d',
	  onShow:function(){
	   this.setOptions({
	    minDate:$('#start_date').val()?$('#start_date').val():false
	   })
	  },
	  timepicker:false
	 });
});
</script>

</html>