var selectArray = [
	{ val: 1, text: "1" },
	{ val: 2, text: "2" },
	{ val: 3, text: "3" },
	{ val: 4, text: "4" },
	{ val: 5, text: "5" },
	{ val: 6, text: "6" },
	{ val: 7, text: "7" },
	{ val: 8, text: "8" },
	{ val: 9, text: "9" },
	{ val: 10, text: "10" },
	{ val: 11, text: "11" },
	{ val: 12, text: "12" }
];
var selectUseArray = [
	{ val: 0, text: "座位" },
	{ val: 1, text: "障礙物" },
	{ val: 2, text: "背景圖" },
];

function previewImages() {
	var $preview = $("#preview").empty();
	if (this.files) $.each(this.files, readAndPreview);

	function readAndPreview(i, file) {

		if (!/\.(jpe?g|png|gif)$/i.test(file.name)) {
			return alert(file.name + " is not an image");
		}
		// else...
		var reader = new FileReader();
		$(reader).on("load", function() {
			
			$("<div/>", {
				id: "div_img",
				class: "div_img",
				title: "now this div has a title!"
			}).appendTo("#preview");
			$("#preview div:last-child").append($("<img/>", { src: this.result })).append($("<label/>").text("設定桌位人數："));
			$("<select>", {
				id: "seat_people",
				name: "seat_people",
			}).appendTo("#preview #div_img:last-child");
			$("#preview div:last-child").append($("<label/>").text(" 人"));
			$(selectArray).each(function() {
				$("#preview #div_img:last-child").find("#seat_people").append($("<option>").attr("value", this.val).text(this.text));
			});
			$("#preview div:last-child").append($("<label/>").text("物件用途："));
			$("<select>", {
				id: "seat_use",
				name: "seat_use",
			}).appendTo("#preview #div_img:last-child");
			$(selectUseArray).each(function() {
				$("#preview #div_img:last-child").find("#seat_use").append($("<option>").attr("value", this.val).text(this.text));
			});
		});

		reader.readAsDataURL(file);
	}
}

$("#myFile").on("change", previewImages);


