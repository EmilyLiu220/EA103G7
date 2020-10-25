$(document).ready(function() {
// e.preventDefault();
// e.stopImmediatePropagation();
//	var info_sts = { "0" : "未發送", "1" : "已發送未確認", "2" : "已發送已確認", "3" : "取消訂位" };
//	var seat_sts = { "0" : "未入座", "1" : "已入座"};
//	var ajaxURL = (window.location.pathname).substr(0, (window.location.pathname).indexOf("/", (window.location.pathname).indexOf("/") + 1));
//	var index = 5;
//	$.ajax({
//		// url is servlet url, ?get_Member_Res_Seat_Ing is tell servlet execute
//		// which
//		// one judgment
//		url: ajaxURL + "/res_order/ResOrderServlet.do?action=get_Member_Res_Seat_End",
//		type: "post",
//		
//		// synchronize is true
//		async: false,
//		data:{},
//		dataType: "json",
//		success: function(jsonArray) {
//			$("div.container.get_member_res_seat").empty();
//			
//			$("<table>").attr({
//				class: "table table-striped table-hover mx-auto w-auto",
//			}).appendTo("div.container.get_member_res_seat");
//			
//			var row = $("<tr>").appendTo("table.table.table-striped");
//			$("<th>").text("桌位").appendTo(row);
//			$("<th>").text("訂餐編號").appendTo(row);
//			$("<th>").text("預約訂位日期").appendTo(row);
//			$("<th>").text("用餐時段").appendTo(row);
//			$("<th>").text("人數").appendTo(row);
//			$("<th>").text("訊息狀態").appendTo(row);
//			$("<th>").text("入座狀態").appendTo(row);
//			
//			$.each(jsonArray, function(i, item) {
//				if(i < index){
//				
//					const row = $("<tr>").appendTo("table.table.table-striped");
//					
//					$.ajax({
//						// url is servlet url, ?get_Member_Res_Seat_Ing is tell
//						// servlet execute
//						// which
//						// one judgment
//						url: ajaxURL + "/seat/SeatServlet.do?action=get_seatVO",
//						type: "post",
//						// synchronize is true
//						async: false,
//						data:{
//							"res_no" : item.res_no,
//						},
//						success: function(seatJSONArray) {
//							var fs = $("<td>");
//							$.each(JSON.parse(seatJSONArray), function(j, seat) {
//								fs.append(seat.seat_f+"樓_"+seat.seat_name+"桌<br>");
//							});
//							fs.appendTo(row);
//						},
//						error: function(xhr, ajaxOptions, thrownError) {
//							lock_floor_list = true;// 如果業務執行失敗，修改鎖狀態
//							swal("儲存失敗", errorText, "warning");
//						},
//					});
//					$("<td>").text("訂餐編號").appendTo(row);
//					$("<td>").text(item.res_date).appendTo(row);
//					
//					$.ajax({
//						// url is servlet url, ?get_Member_Res_Seat_Ing is tell
//						// servlet execute
//						// which
//						// one judgment
//						url: ajaxURL + "/time_peri/TimePeriServlet.do?action=get_TimePeriVO_List",
//						type: "post",
//						// synchronize is true
//						async: false,
//						data:{
//							"time_peri_no" : item.time_peri_no,
//						},
//						success: function(timePeriVO) {
//							$("<td>").text(JSON.parse(timePeriVO).time_start).appendTo(row);
//						},
//						error: function(xhr, ajaxOptions, thrownError) {
//							lock_floor_list = true;// 如果業務執行失敗，修改鎖狀態
//							swal("儲存失敗", errorText, "warning");
//						},
//					});
//					
//					$("<td>").text(item.people).appendTo(row);
//					$("<td>").text(info_sts[item.info_sts]).appendTo(row);
//					$("<td>").text(seat_sts[item.seat_sts]).appendTo(row);
//				}
//			});
//			for (let i = 0; i < jsonArray.length / 5 ; i++) {
//				$("<input>").attr({
//					class:"btn btn-primary changePage", 
//					type:"button",
//					name:Math.floor(( Math.floor(jsonArray.length / 5) + 1 ) * (i + 1)),
//					value:"　"+(i+1)+"　",
//				}).appendTo("div.container.get_member_res_seat");
//			}
//			
//			$("<br>").appendTo("div.container.get_member_res_seat");
//			$("<input>").attr({
//				class:"btn btn-primary", 
//				type:"button", 
//				value:"回首頁",
//				onclick:"location.href='"+ajaxURL+"/back-end/seat_obj/addSeatObj.jsp'",
//			}).appendTo("div.container.get_member_res_seat");
//			
//			$("<input>").attr({
//				class:"btn btn-secondary", 
//				type:"button", 
//				value:"回桌訂位畫面",
//				onclick:"location.href='"+ajaxURL+"/front-end/res_order/orderSeat.jsp'",
//			}).appendTo("div.container.get_member_res_seat");
//			
//			lock_floor_list = true;// 如果業務執行成功，修改鎖狀態
//		},
//		error: function(xhr, ajaxOptions, thrownError) {
//			lock_floor_list = true;// 如果業務執行失敗，修改鎖狀態
//			swal("儲存失敗", errorText, "warning");
//		},
//	});
	
	
	$("td").addClass("align-middle");
	// 取消訂位按鈕
	$("button#cancel_Seat_Res_Order.btn.btn-danger").click(function(){
		var form = $(this).parents('form');
		swal({
			title: "您現在要取消此筆訂位嗎?",
			text: "取消後，將無法復原，需重新訂位",
			icon: "warning",
			buttons: ["不要，我點錯了！", "是的！我要取消！"],
			dangerMode: true,
		}).then((willDelete) => {
			if (willDelete) {
				swal("即將取消訂位！", {
					icon: "success",
				}).then(function() {
					$("<input>").attr({
						type: "hidden",
						name: "action",
						value: "cancel_Seat_Res_Order",
					}).appendTo(form);
					form.submit();
				});
			} else {
				swal("您並沒有任何動作", {
					icon: "info",
				}).then(function() {
				});
			}
		});
	});
	
	// 我要訂餐按鈕
	$("button#go_Order_Meal.btn.btn-primary").click(function(){
		var form = $(this).parents('form');
		swal({
			title: "您需要先訂餐嗎?",
			text: "先選購餐點，到店省時間！",
			icon: "warning",
			buttons: ["不要，我點錯了！", "是的！我要點餐！"],
			dangerMode: true,
		}).then((willDelete) => {
			if (willDelete) {
				swal("即將出發點餐！", {
					icon: "success",
				}).then(function() {
					$("<input>").attr({
						type: "hidden",
						name: "action",
						value: "go_res_meal",
					}).appendTo(form);
					form.submit();
				});
			} else {
				swal("您並沒有任何動作", {
					icon: "info",
				}).then(function() {
				});
			}
		});
	});
	
	// 修改座位按鈕
	$("button#modify_Seat_Position.btn.btn-warning").click(function(){
		var form = $(this).parents('form');
		swal({
			title: "您想要變更訂位嗎?",
			icon: "warning",
			buttons: ["不要，我點錯了！", "是的！我要更改！"],
			dangerMode: true,
		}).then((willDelete) => {
			if (willDelete) {
				swal("即將出發更改座位！", {
					icon: "success",
				}).then(function() {
					$("<input>").attr({
						type: "hidden",
						name: "action",
						value: "modify_Seat_Position",
					}).appendTo(form);
					form.submit();
				});
			} else {
				swal("您並沒有任何動作", {
					icon: "info",
				});
			}
		});
	});
});
