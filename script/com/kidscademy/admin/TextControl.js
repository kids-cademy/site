$package("com.kidscademy.admin");

com.kidscademy.admin.TextControl = function(ownerDoc, node) {
	this.$super(ownerDoc, node);
};

com.kidscademy.admin.TextControl.prototype = {
	setValue : function(text) {
		if (text == null) {
			this.reset();
			return;
		}
		this._setValue(text.replace(/<p>/g, "").replace(/<\/p>/g, "\n\n"));
	},

	getValue : function() {
		const text = this._getValue();
		if (!text) {
			return null;
		}
		return "<p>" + text.trim().replace(/\n\n/g, "</p><p>") + "</p>";
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "com.kidscademy.admin.TextControl";
	}
};
$extends(com.kidscademy.admin.TextControl, js.dom.Control);
$preload(com.kidscademy.admin.TextControl);
