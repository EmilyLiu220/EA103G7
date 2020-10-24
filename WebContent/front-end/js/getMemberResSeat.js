$(document).ready(function() {
	$("td").addClass("align-middle");
	// 取消訂位按鈕
	$("button#cancel_Seat_Res_Order.btn.btn-danger").click(function(){
		var form = $(this).parents('form');
		swal({
			title: "您現在要取消此筆訂位嗎?",
			text: "取消後，將無法復原，需重新訂位",
			icon: "warning",
			buttons: ["不要，我點錯了！", "是的！我要取消"],
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
			buttons: ["不要，我點錯了！", "是的！我要點餐"],
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
			title: "您想要變更座位嗎?",
			text: "更改座位！點選馬上改～",
			icon: "warning",
			buttons: ["不要，我點錯了！", "是的！我要更改"],
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
				}).then(function() {
				});
			}
		});
	});
	
});