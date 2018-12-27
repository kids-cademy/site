$package("com.kidscademy.admin");

com.kidscademy.admin.HiddenControl = function(ownerDoc, node) {
	this.$super(ownerDoc, node);

	/**
	 * Hidden control keep form value for a visible element that should be notified about validity state.
	 * 
	 * @type js.dom.Element
	 */
	this._boundElement = ownerDoc.getById(this._config["for"]);
};

com.kidscademy.admin.HiddenControl.prototype = {
	isValid : function() {
		var value = this._getValue();
		this._boundElement.addCssClass(this.CSS_INVALID, !value);
		return value;
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "com.kidscademy.admin.HiddenControl";
	}
};
$extends(com.kidscademy.admin.HiddenControl, js.dom.Control);
$preload(com.kidscademy.admin.HiddenControl);
