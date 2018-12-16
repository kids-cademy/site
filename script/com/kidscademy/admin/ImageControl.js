$package("com.kidscademy.admin");

com.kidscademy.admin.ImageControl = function(ownerDoc, node) {
	this.$super(ownerDoc, node);
};

com.kidscademy.admin.ImageControl.prototype = {
	setValue : function(imagePath) {
		this._imagePath = imagePath;
		this.setAttr("src", "/repository/" + imagePath);
	},

	getValue : function() {
		return this._imagePath;
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "com.kidscademy.admin.ImageControl";
	}
};
$extends(com.kidscademy.admin.ImageControl, js.dom.Control);
$preload(com.kidscademy.admin.ImageControl);
