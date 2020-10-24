$(document).ready(function() {
	// 獲得文字框物件
	var t = $(".seat_people");
	//	避免超出範圍所設計的檢查
	if (parseInt(t.val()) < 12) {
		t.next("input").prop("disabled", false);
	}

	if (parseInt(t.val()) > 1) {
		t.prev("input").prop("disabled", false);
	}

	//數量增加操作
	$(".add").click(function() {
		// 給獲取的val加上絕對值，避免出現負數
		$(this).prev(".seat_people").val(parseInt($(this).prev(".seat_people").val()) + 1);
		if (parseInt($(this).prev(".seat_people").val()) != 1) {
			$(this).prev("input").prev("input").prop("disabled", false);
		}
		if (parseInt($(this).prev(".seat_people").val()) >= 12) {
			$(this).prop("disabled", true);
		}
	});
	//數量減少操作
	$(".cut").click(function() {
		$(this).next(".seat_people").val(parseInt($(this).next(".seat_people").val()) - 1);
		if (parseInt($(this).next(".seat_people").val()) == 1) {
			$(this).prop("disabled", true);
		}
		if (parseInt($(this).next(".seat_people").val()) < 12) {
			$(this).next("input").next("input").prop("disabled", false);
		}
	});
});