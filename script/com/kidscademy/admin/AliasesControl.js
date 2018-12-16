$package("com.kidscademy.admin");

com.kidscademy.admin.AliasesControl = function(ownerDoc, node) {
	this.$super(ownerDoc, node);
};

com.kidscademy.admin.AliasesControl.prototype = {
	setValue : function(aliases) {
		this.$super("setValue", aliases.join(", "));
	},

	getValue : function() {
		var value = this.$super("getValue");
		if(value == null) {
			return [];
		}
		return value.trim().split(/\s*,\s*/);
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "com.kidscademy.admin.AliasesControl";
	}
};
$extends(com.kidscademy.admin.AliasesControl, js.dom.Control);
$preload(com.kidscademy.admin.AliasesControl);
