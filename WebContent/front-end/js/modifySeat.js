$(document).ready(function() {
	// // 控制資料庫拉出物件的checkbox
	// $(".myCheckbox").click(function(e) {
	// e.preventDefault();
	// e.stopImmediatePropagation();
	// if ($(this).is(":checked"))
	// $(this).show();
	// else
	// $(this).hide();
	// });
	/** *************** 獲得座位物件資訊，使用ajax傳送給servlet **************** */
	// sweet alert messages

	/** ***************************** 日期選擇 ****************************** */
	var errorText;

	var ajaxURL = (window.location.pathname).substr(0, (window.location.pathname).indexOf("/", (window.location.pathname).indexOf("/") + 1));
	function ajaxSuccessFalse(xhr) {
		errorText = xhr.responseText.substr(xhr.responseText.indexOf("Message") + 12, xhr.responseText.indexOf("</p><p><b>Description") - (xhr.responseText.indexOf("Message") + 12));
	}
	// var lock_ = true;//防止重複提交定義鎖
	// $("#").change(function() {
	// if (!lock_) {// 2.判斷該鎖是否開啟，如果是關閉的，則直接返回
	// return false;
	// }
	// lock_ = false; //3.進來後，立馬把鎖鎖住
	// $.ajax({
	// // url is servlet url, ?archive_seat is tell servlet execute which one
	// judgment
	// url: ajaxURL + "/modifySeat/ResOrderServlet.do?",
	// type: "post",
	// // synchronize is false
	// async: false,
	// data: {
	// "action":"XXXXXXXXXXX",
	// "res_date": res_date,
	// "time_peri_no": time_peri_no,
	// },
	// success: function(messages) {
	// $.each($myCheckbox, function(_index, item) {
	// });
	// lock_ = true;//如果業務執行成功，修改鎖狀態
	// },
	// error: function(xhr, ajaxOptions, thrownError) {
	// lock_ = true;//如果業務執行失敗，修改鎖狀態
	// ajaxSuccessFalse(xhr);
	// swal("儲存失敗", errorText, "warning");
	// },
	// });
	// return false;
	// });
	
	/*********************************************
	 *********************************************
	 ******* 有空再改，修改訂位會直接顯示到 ******
	 **** 這筆訂單所訂位的畫面，優化使用者體驗 ***
	 *********************************************
	 ********************************************* */
//	$( window ).on( "load", function(e) { 
//		 e.preventDefault();
//		 e.stopImmediatePropagation();
//		 var res_date = $("#res_date").val();
//		 var time_peri_no = $("#time_peri_no").val();
//		 var res_no = $("#res_no").val();
//			$.ajax({
//				// url is servlet url, ?archive_seat is tell servlet execute
//				// which
//				// one judgment
//				url: ajaxURL + "/res_order/ResOrderServlet.do?",
//				type: "post",
//				// synchronize is false
//				async: false,
//				data: {
//					"action":"get_Modify_Seat_Order_Info",
//					"res_no": res_no,
//				},
//				success: function(messages) {
//					$("#modifySeat").css("display", "inline-block");
//					$("#floor_list").val(JSON.parse(messages).seat_f[0]);
//					$("#res_date").val(JSON.parse(messages).res_date);
//					$("#people").val($("#res_people").val());
//					var time_peri_no = JSON.parse(messages).time_peri_no;
//					var res_date = $("#res_date").val();
//					$.ajax({
//						// url is servlet url, ?archive_seat is tell servlet execute which
//						// one judgment
//						url: ajaxURL + "/time_peri/TimePeriServlet.do?",
//						type: "post",
//						// synchronize is false
//						async: false,
//						data: {
//							"action":"get_TimePeri",
//							"res_date": JSON.parse(messages).res_date,
//						},
//						success: function(messages) {
//							// console.log(messages);
//							var jsonArray = JSON.parse(messages);
//							$("#time_peri_no").empty();
//							$("#time_peri_no").append("<option class=\"lt\" value=\"-1\">--請選擇時段--</option>");
//							$.each(jsonArray, function(_index, item) {
//								if(time_peri_no == item.time_peri_no){
//									var option = $("<option/>");
//									option.attr({
//										value : item.time_peri_no,
//										selected : true,
//									}).text(item.time_start.replace("-", ":"));
//									$("#time_peri_no").append(option);
//								} else {
//									var option = $("<option/>");
//									option.attr({
//										value: item.time_peri_no,
//									}).text(item.time_start.replace("-", ":"));
//									$("#time_peri_no").append(option);
//								}
//							});
//							$(".labelOne").css("display", "inline-block");
//							$(".labelTwo").css("display", "inline-block");
//							$("div#container.container").css("display", "block");
//							lock_order_date = true;// 如果業務執行成功，修改鎖狀態
//						},
//						error: function(xhr, ajaxOptions, thrownError) {
//							lock_order_date = true;// 如果業務執行失敗，修改鎖狀態
//							ajaxSuccessFalse(xhr);
//							swal("儲存失敗", errorText, "warning");
//						},
//					});
//					return false;
//					console.log(JSON.parse(messages).time_peri_no);
//					console.log(messages);
//				},
//				error: function(xhr, ajaxOptions, thrownError) {
//					lock_time_peri_no = true;// 如果業務執行失敗，修改鎖狀態
//					ajaxSuccessFalse(xhr);
//					swal("儲存失敗", errorText, "warning");
//				},
//			});
//			return false;
//	});
	/** ***************************** 人數 ****************************** */
	var lock_people = true;// 防止重複提交定義鎖
	$("#people").change(function() {
		if (!lock_people) {// 2.判斷該鎖是否開啟，如果是關閉的，則直接返回
			return false;
		}
		lock_people = false; // 3.進來後，立馬把鎖鎖住
		var res_people = $("#res_people").val();
		var people = $("#people").val()
		var time_peri_no = $("#time_peri_no").val();
		var res_date = $("#res_date").val();
		if(parseInt(people) < chooseSeatPeople) {
			chooseSeatPeople = 0;
			swal("人數低於選擇座位人數！", "請重新確認人數", "warning");
			$.each($(".myCheckbox"), (_index, itemCheckbox) =>{
				if(_index == 0) {
					$.each($(".myCheckbox"), (ind, checkboxReset) =>{
						$(checkboxReset).closest(".drag").css({
							filter: "hue-rotate(0deg)",
						});
						$(checkboxReset).prop("checked", false);
						$(checkboxReset).prop("disabled", true);
					});
				}
				$.each(JSON.parse(JSONArray) , (i, item) =>{
					if($(itemCheckbox).val() == item) {
						$(itemCheckbox).closest(".drag").css({
							filter: "invert(23%) sepia(98%) saturate(6242%) hue-rotate(342deg) brightness(103%) contrast(118%)",
						});
						$(itemCheckbox).prop("checked", true);
						$(itemCheckbox).prop("disabled", true);
						return false;
					}
				});
				$.each(JSON.parse(thisResInfo).seat_no, (i, orderSeat) => {
					if ($(itemCheckbox).val() == orderSeat  && JSON.parse(thisResInfo).res_date == res_date && JSON.parse(thisResInfo).time_peri_no == time_peri_no) {
						$(itemCheckbox).closest(".drag").css({
							filter: "invert(23%) sepia(98%) saturate(6242%) hue-rotate(252deg) brightness(103%) contrast(118%)",
						});
						$(itemCheckbox).prop("disabled", false);
						$(itemCheckbox).prop("checked", true);
						$(itemCheckbox).css("display", "block");
						return false;
					}
				});
				if($(".myCheckbox").length == (_index + 1)){
					$("#people").val(res_people);
					if (JSON.parse(thisResInfo).res_date == res_date && JSON.parse(thisResInfo).time_peri_no == time_peri_no) {
						$.each(JSON.parse(thisResInfo).people, (i, resPeople) => {
							addChooseSeatPeople(parseInt(resPeople));
						});
					} else {
						chooseSeatPeople = 0;
						$("#people").val("");
					}
				}
			});
		}
		console.log($("#people").val());
		console.log("chooseSeatPeople："+parseInt(chooseSeatPeople));
		if(parseInt(people) > chooseSeatPeople ){
			console.log(parseInt(people));
			console.log(chooseSeatPeople);
			$.each($(".myCheckbox").not(":checked"), (i, item) =>{
				$(item).prop("disabled", false);
			});
//			$.each($(".myCheckbox"), (i, itemCheckbox) =>{
//				$.each(JSON.parse(thisResInfo).seat_no, (i, orderSeat) => {
//					if ($(itemCheckbox).val() == orderSeat  && JSON.parse(thisResInfo).res_date == res_date && JSON.parse(thisResInfo).time_peri_no == time_peri_no) {
//						$(itemCheckbox).closest(".drag").css({
//							filter: "invert(23%) sepia(98%) saturate(6242%) hue-rotate(252deg) brightness(103%) contrast(118%)",
//						});
//						$(itemCheckbox).prop("disabled", false);
//						$(itemCheckbox).prop("checked", true);
//						$(itemCheckbox).css("display", "block");
//						addChooseSeatPeople(parseInt(JSON.parse(thisResInfo).people));
//					}
//				});
//			});
		}
		if(parseInt(people) == chooseSeatPeople ){
			$.each($(".myCheckbox").not(":checked"), (i, item) =>{
				$(item).prop("disabled", true);
			});
		}
		if ($("#people").val() > 20 || $("#people").val() < 1) {
			swal("輸入的值超出範圍!", "請輸入1～20的數字!", "info");
			$("#people").val("");
		}
		lock_people = true;
		return false;
	});
	
	/****************************** 人數 *******************************/
	var lock_checked = true;
	$(".myCheckbox").change(function() {
//		console.log("parseInt(people) - chooseSeatPeople > 0");
		console.log("chooseSeatPeople"+chooseSeatPeople);
		console.log("people"+people);
		if($("#res_date").val() == "--請選擇日期--"){
			swal("請選擇日期！", "請先選擇日期在選擇時段！", "info");
			$(this).prop("checked", false);
			return false;
		} else if ($("#time_peri_no").val() == -1){
			swal("請選擇時段", "請選擇時段！", "info");
			$(this).prop("checked", false);
			return false;
		}
		$.each(JSON.parse(thisResInfo).seat_no , (i, item) =>{
			if ($(this).is(":checked")) {
				let time_peri_no = $("#time_peri_no").val();
				let res_date = $("#res_date").val();
				if($(this).val() == item && JSON.parse(thisResInfo).res_date == res_date && JSON.parse(thisResInfo).time_peri_no == time_peri_no) {
					$(this).closest(".drag").css({
						filter: "invert(23%) sepia(98%) saturate(6242%) hue-rotate(252deg) brightness(103%) contrast(118%)",
					});
					return false;
				} else {
					$(this).closest(".drag").css({
						filter: "invert(23%) sepia(98%) saturate(6242%) hue-rotate(90deg) brightness(103%) contrast(118%)",
					});
				}
			} else {
				$(this).closest(".drag").css({
					filter: "hue-rotate(0deg)",
				});
			}
		});
		var people = $("#people").val();
		if ($("#people").val().length === 0) {
			swal("請先輸入來店人數", "", "info");
			$(this).closest(".drag").css({
				filter: "hue-rotate(0deg)",
			});
			$(this).prop("disabled", false);
			$(this).prop("checked", false);
		}
		var thisCheckboxValue = $(this).val();
		var thisCheckbox = $(this);
		var allNotCheckbox= $(".myCheckbox").not(":checked");
		$.each(jsonArray_people, function(_index, item) {
			let key = Object.keys(item);
			let value = Object.values(item);
			if (key[0] === thisCheckboxValue) {
				if (thisCheckbox.is(":checked")) {
					console.log(1);
					addChooseSeatPeople(value[0]);
				} else if (thisCheckbox.not(":checked")) {
					lessChooseSeatPeople(value[0]);
				}
				// 來店人數大於選擇座位可容納人數
				if (parseInt(people) - chooseSeatPeople > 0) {
					let nowNotCheckbox= $(".myCheckbox").not(":checked");
					$.each(nowNotCheckbox, function(i, item){
						$(item).prop("disabled", false);
					});
//					console.log("parseInt(people) - chooseSeatPeople > 0");
//					console.log("chooseSeatPeople"+chooseSeatPeople);
//					console.log("people"+people);
				}
				// 來店人數等於選擇座位人數
				if (parseInt(chooseSeatPeople) - parseInt(people) == 0) {
					swal("已經選擇適當的桌位囉！", "", "info");
					$.each(allNotCheckbox, function(i, item){
						$(item).prop("disabled", true);
					});
//					console.log("parseInt(chooseSeatPeople) - parseInt(people) == 0");
//					console.log("chooseSeatPeople"+chooseSeatPeople);
//					console.log("people"+people);
				// 選擇座位人數超過來店人數太多
				} else if (parseInt(chooseSeatPeople) >= parseInt(people) + 3) {
					swal("選擇座位人數超過來店人數太多！！", "請重新選擇相符的人數座位～", "info");
					thisCheckbox.closest(".drag").css({
						filter: "hue-rotate(0deg)",
					});
					thisCheckbox.prop("disabled", false);
					thisCheckbox.prop("checked", false);
					lessChooseSeatPeople(value[0]);
//					console.log("parseInt(chooseSeatPeople) >= parseInt(people) + 3");
//					console.log("chooseSeatPeople"+chooseSeatPeople);
//					console.log("people"+people);
				// 符合來店人數低於選擇最為可容納人數之可接受範圍
				} else if(parseInt(people) - parseInt(chooseSeatPeople) < 0 ) {
					swal("已經選擇適當的桌位囉！", "", "info");
					$.each(allNotCheckbox, function(i, item){
						$(item).prop("disabled", true);
					});
//					console.log("parseInt(people) - parseInt(chooseSeatPeople) < 0");
//					console.log("chooseSeatPeople"+chooseSeatPeople);
//					console.log("people"+people);
				} 
//				else if (chooseSeatPeople > parseInt(people) + 5) {
//					swal("123！", "", "info");
//					thisCheckbox.closest(".drag").css({
//						filter: "hue-rotate(0deg)",
//					});
//					thisCheckbox.prop("disabled", false);
//					thisCheckbox.prop("checked", false);
//					lessChooseSeatPeople(value[0]);
//					return false;
//				}
				return false;
			}
		});
		return false;
	});

	/**
	 * ***************************** 換樓層選擇座位區更換成該樓層座位
	 * ******************************
	 */
	var lock_floor_list = true;// 防止重複提交定義鎖
	$("#floor_list").change(function() {
		if (!lock_floor_list) {// 2.判斷該鎖是否開啟，如果是關閉的，則直接返回
			return false;
		}
		lock_floor_list = false; // 3.進來後，立馬把鎖鎖住
		$.ajax({
			// url is servlet url, ?archive_seat is tell servlet execute which
			// one judgment
			url: ajaxURL + "/res_order/ResOrderServlet.do?",
			type: "post",
			// synchronize is false
			async: false,
			data: {
				"floor": $("#floor_list").val(),
				"action":"floor_load",
			},
			success: function(messages) {
//				$("body > div#container.container").load(ajaxURL + "/front-end/res_order/modifySeat.jsp div#container.container");
//				$.getScript(ajaxURL + "/js/jquery-1.12.4.js");
				$.getScript(ajaxURL + "/front-end/js/modifySeat.js");
//				$.getScript(ajaxURL + "/js/sweetalert.min.js");
				var jsonArray = JSON.parse(messages);
				$("div#container.container").empty();
//				$("#time_peri_no").empty();
//				$("#people").val("");
				$("#res_date").val("--請選擇日期--");
				$("#time_peri_no").append("<option class=\"lt\" value=\"-1\">--請先選擇日期--</option>");
				$.each(jsonArray, function(_index, item) {
					$("<div>").attr({
						class: "drag",
						id: "drag",
					}).css({
						"position": "absolute",
						"left": item.seat_x + "px",
						"top": item.seat_y + "px",
					}).appendTo("div#container.container");

					var $drag = $("div#container.container .drag").eq(_index);
					$("<label>").appendTo($drag);
					var $label = $("div#container.container .drag > label:first-child").eq(_index);
					$("<input>").attr({
						type: "checkbox",
						class: "myCheckbox",
						name: "seat_checked",
						value: item.seat_no,
					}).appendTo($label);
					$("<img>").attr({
						src: ajaxURL + "/seat/Seat_ObjServlet.do?seat_obj_no=" + item.seat_obj_no,
					}).appendTo($label);
					$("<label>").attr({
						class: "seatLabel",
					}).appendTo($drag);
					var $label2 = $("div#container.container .drag .seatLabel").eq(_index);
					$("<input>").attr({
						type: "text",
						class: "seatName",
						name: "seatName",
						value: item.seat_name,
					}).attr("disabled", true).appendTo($label2);
				});
//				$(".labelOne").css("display", "none");
//				$(".labelTwo").css("display", "none");
//				$("#container").css("display", "none");
			},
			error: function(xhr, ajaxOptions, thrownError) {
				lock_floor_list = true;// 如果業務執行失敗，修改鎖狀態
				ajaxSuccessFalse(xhr);
				swal("儲存失敗", errorText, "warning");
			},
		});
		return false;
	});

	/** ***************************** 日期選擇 ****************************** */
	$.datetimepicker.setLocale('zh');	// 設定語言
	var somedate = new Date();
	$("#res_date").datetimepicker({
		timepicker: false,
		format: 'Y-m-d',				// 時間格式
		scrollInput: false,				// 預防滾輪選取不可選取的日期
		validateOnBlur: false, 			// 失去焦點時才驗證輸入直
		minDate: 0,						// 開始日期
		maxDate: '+1970/01/14',			// 開始日期到結束日期
	})

	var lock_order_date = true;// 防止重複提交定義鎖
	$("#res_date").change(function() {
		if (!lock_order_date) {// 2.判斷該鎖是否開啟，如果是關閉的，則直接返回
			return false;
		}
		lock_order_date = false; // 3.進來後，立馬把鎖鎖住
		var res_date = $("#res_date").val();
		$.ajax({
			// url is servlet url, ?archive_seat is tell servlet execute which
			// one judgment
			url: ajaxURL + "/time_peri/TimePeriServlet.do?",
			type: "post",
			// synchronize is false
			async: false,
			data: {
				"action":"get_TimePeri",
				"res_date": res_date,
			},
			success: function(messages) {
				var jsonArray = JSON.parse(messages);
				$("#time_peri_no").empty();
				$("#time_peri_no").append("<option class=\"lt\" value=\"-1\">--請選擇時段--</option>");
				$.each(jsonArray, function(_index, item) {
					var option = $("<option/>");
					option.attr({
						value: item.time_peri_no,
					}).text(item.time_start.replace("-", ":"));
					$("#time_peri_no").append(option);
				});
				$(".labelOne").css("display", "inline-block");
				lock_order_date = true;// 如果業務執行成功，修改鎖狀態
			},
			error: function(xhr, ajaxOptions, thrownError) {
				lock_order_date = true;// 如果業務執行失敗，修改鎖狀態
				ajaxSuccessFalse(xhr);
				swal("儲存失敗", errorText, "warning");
			},
		});
		return false;
	})

	var lock_time_peri_no = true;// 防止重複提交定義鎖
	$("#time_peri_no").change(function(e) {
		e.preventDefault();
		e.stopImmediatePropagation();
		if (!lock_time_peri_no) {// 2.判斷該鎖是否開啟，如果是關閉的，則直接返回
			return false;
		}
		lock_time_peri_no = false; // 3.進來後，立馬把鎖鎖住
		var res_date = $("#res_date").val();
		var time_peri_no = $("#time_peri_no").val();
		var res_no = $("#res_no").val();
		var floor = $("#floor_list").val();
		// get all seat_no
		var $drag = $(".drag");
		var jsonDataStr = new Array();
		$.each($drag, function(_index, item) {
			var mySeat = new Object();
			mySeat.seat_no = $(this).find("input").attr("value");
			mySeat.seat_obj_no = $(item).find("img").attr("src").substr($(item).find("img").attr("src").lastIndexOf("=") + 1);
			// push data to JSONArray
			jsonDataStr.push(mySeat);
		});
		// 拿所有座位可容納人數
		$.ajax({
			// url is servlet url, ?archive_seat is tell servlet execute which
			// one judgment
			url: ajaxURL + "/res_order/ResOrderServlet.do",
			type: "post",
			// synchronize is false
			async: false,
			data: {
				"action":"get_All_Seat_People",
				"people": JSON.stringify(jsonDataStr),
			},
			success: function(messages) {
				jsonArray_people = JSON.parse(messages);
				setJSONArray_people(jsonArray_people);
				$("#container").css("display", "block");
				$("#modifySeat").css("display", "inline-block");
				
				lock_people = true;// 如果業務執行成功，修改鎖狀態
			},
			error: function(xhr, ajaxOptions, thrownError) {
				lock_people = true;// 如果業務執行失敗，修改鎖狀態
				ajaxSuccessFalse(xhr);
				swal("儲存失敗", errorText, "warning");
			},
		});
		// 拿今天這個時段被訂位的座位
		$.ajax({
			// url is servlet url, ?archive_seat is tell servlet execute which
			// one judgment
			url: ajaxURL + "/res_order/ResOrderServlet.do?",
			type: "post",
			// synchronize is false
			async: false,
			data: {
				"action":"get_Res_Order_Today",
				"res_date": res_date,
				"time_peri_no": time_peri_no,
				"floor": floor,
			},
			success: function(messages) {
				setJSONArray(messages);
				var jsonArray = JSON.parse(messages);
				var $myCheckbox = $(".myCheckbox");
				$.each($myCheckbox, function(_index, item) {
					$(item).closest(".drag").css({
						filter: "hue-rotate(0deg)",
					});
					$(item).prop("disabled", false);
					$(item).prop("checked", false);
				});
				// 拿要修改座位的資訊
				$.ajax({
					// url is servlet url, ?archive_seat is tell servlet execute
					// which
					// one judgment
					url: ajaxURL + "/res_order/ResOrderServlet.do?",
					type: "post",
					// synchronize is false
					async: false,
					data: {
						"action":"get_Modify_Seat_Order_Info",
						"res_no": res_no,
					},
					success: function(resInfo) {
						// 如果這個時段的座位編號出現在訂位訂單同時段同編號，代表已經被別人下訂，要顯示為無法訂位
						$.each($myCheckbox, function(_index, item) {
							$.each(jsonArray, function(_index, item1) {
								if ($(item).val() === item1) {
									$(item).closest(".drag").css({
										filter: "invert(23%) sepia(98%) saturate(6242%) hue-rotate(342deg) brightness(103%) contrast(118%)",
									});
									$(item).prop("disabled", true);
									$(item).prop("checked", true);
									$(item).css("display", "none");
								}
							});
						});
						// 設定訂單桌位
						getThisResInfo(resInfo);
//						console.log("getThisResInfo"+resInfo);
						chooseSeatPeople = 0;
//						console.log(resInfo);
						if(JSON.parse(resInfo).res_date == res_date && JSON.parse(resInfo).time_peri_no == time_peri_no){
//							// 設定選取桌位人數初始
//							$.each(JSON.parse(resInfo).people , (i,people) =>{
//								if(JSON.parse(resInfo).seat_f == $("#floor_list").val()) {
//									addChooseSeatPeople(parseInt(people));
//								}
//							});
							$.each($myCheckbox, function(_index, item) { // 所有座位
								if(chooseSeatPeople == $("#people").val()) { 
									if($(item).not("checked")) {
										$(item).prop("disabled", true);
									}
								}
								$.each(JSON.parse(resInfo).seat_no, function(_index, item2) { // 訂單的座位
									if($(item).val() == item2) {
										$(item).closest(".drag").css({
											filter: "invert(23%) sepia(98%) saturate(6242%) hue-rotate(252deg) brightness(103%) contrast(118%)",
										});
										$(item).prop("disabled", false);
										$(item).prop("checked", true);
										$(item).css("display", "inline-block");
									}
								});
							});
							// 設定選取桌位人數初始
							chooseSeatPeople = 0;
							$.each(JSON.parse(resInfo).people, function(_index, people) {
								addChooseSeatPeople(parseInt(people));
							});
							$("#people").val($("#res_people").val());
							let allNotCheckbox= $(".myCheckbox").not(":checked");
							$.each(allNotCheckbox, function(i, item){
								if(parseInt($("#people").val()) <= chooseSeatPeople){
									$(item).prop("disabled", true);
									$(item).css("display", "block");
								}
							});
						} 
					},
					error: function(xhr, ajaxOptions, thrownError) {
						lock_time_peri_no = true;// 如果業務執行失敗，修改鎖狀態
						ajaxSuccessFalse(xhr);
						swal("儲存失敗", errorText, "warning");
					},
				});
				lock_time_peri_no = true;// 如果業務執行成功，修改鎖狀態
				$(".labelTwo").css("display", "inline-block");
//				$("div#container.container").css("display", "block");
				$("#modifySeat").css("display", "inline-block");
				let allNotCheckbox= $(".myCheckbox").not(":checked");
				$.each(allNotCheckbox, function(i, item){
					$(item).css("display", "block");
				});
			},
			error: function(xhr, ajaxOptions, thrownError) {
				lock_time_peri_no = true;// 如果業務執行失敗，修改鎖狀態
				ajaxSuccessFalse(xhr);
			},
		});
//		console.log("chooseSeatPeople"+chooseSeatPeople);
//		console.log("people"+$("#people").val());
		return false;
	});
	
	function setJSONArray_people(value) {
		jsonArray_people = value;
	}
	
	function setJSONArray(jsonArray){
		JSONArray = jsonArray;
	}
	
	function addChooseSeatPeople(value) {
		chooseSeatPeople += value;
	}
	
	function lessChooseSeatPeople(value) {
		chooseSeatPeople -= value;
	}
	
	function getThisResInfo(resInfo){
		thisResInfo = resInfo;
	}
	
	$("#modifySeat").click(function() {
		if(chooseSeatPeople < parseInt($("#people").val())){
			swal("來店人數還大於選擇座位人數唷！", "請在選擇座位，直到座位足夠容納來店人數", "info");
			return false;
		}
		var form = $(this).parents('form');
		swal({
			title: "確定修改訂位嗎?",
			icon: "warning",
			buttons: ["取消", "確定"],
			dangerMode: true,
		}).then((willDelete) => {
			if (willDelete) {
				swal("即將完成修改！", {
					icon: "success",
				}).then(function() {
					form.submit();
				});
			} else {
				swal("您已經取消這次的修改！", {
					icon: "info",
				}).then(function() {
					form.attr('action', ajaxURL+"/front-end/res_order/getMemberResSeat.jsp").submit();
				});
			}
		});
	});
});