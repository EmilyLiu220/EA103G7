function previewImages() {
	var $preview = $("#preview").empty();
	if (this.files) $.each(this.files, readAndPreview);

	function readAndPreview(i, file) {

		if (!/\.(jpe?g|png|gif)$/i.test(file.name)) {
			return alert(file.name + " is not an image");
		}
		var reader = new FileReader();
		$(reader).on("load", function() {
			$("<div/>", {
				id: "div_img",
				class: "div_img",
				title: "now this div has a title!"
			}).appendTo("#preview");
			$("#preview div:last-child").append($("<img/>", { src: this.result })).append($("<label/>"));
		});

		reader.readAsDataURL(file);
	}
}

$("#myFile").on("change", previewImages);


