$package("com.kidscademy");

/**
 * SysMessage class.
 * 
 * @author Iulian Rotaru
 * @since 1.0
 * 
 * @constructor Construct an instance of SysMessage class.
 * @param js.dom.Document ownerDoc element owner document,
 * @param Node node native {@link Node} instance.
 * @assert assertions imposed by {@link js.dom.Element#Element(js.dom.Document, Node)}.
 */
com.kidscademy.SysMessage = function(ownerDoc, node) {
	this.$super(ownerDoc, node);
	this._messageView = this.getByCssClass("message");

	/**
	 * Track current message level in order to remove it when switch to another level.
	 * 
	 * @type String
	 */
	this._currentLevel = null;

	this.getByCssClass("close").on("click", this.close, this);
};

com.kidscademy.SysMessage.prototype = {
	success : function(message) {
		this.open(message, "success");
	},

	info : function(message) {
		this.open(message, "info");
	},

	warning : function(message) {
		this.open(message, "warning");
	},

	danger : function(message) {
		this.open(message, "danger");
	},

	open : function(message, level) {
		if (this._currentLevel != null) {
			this.removeCssClass(this._currentLevel);
		}
		this._currentLevel = level;
		this.addCssClass(level);
		this.addCssClass("visible");
		this._messageView.setText(message);
	},

	close : function() {
		this.removeCssClass("visible");
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "com.kidscademy.SysMessage";
	}
};
$extends(com.kidscademy.SysMessage, js.dom.Element);
