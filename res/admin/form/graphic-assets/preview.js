WinMain.on("load", function() {
	var graphicAssets = WinMain.doc.getByClass(com.kidscademy.admin.GraphicAssets);

	graphicAssets.onCreate({
		getObject : function() {
			return {
				name : "test",
				picturePath : "instruments/test/picture.jpg",
				iconPath : "instruments/test/icon.jpg",
				thumbnailPath : "instruments/test/thumbnail.png"
			};
		}
	});

	graphicAssets.onStart();
});