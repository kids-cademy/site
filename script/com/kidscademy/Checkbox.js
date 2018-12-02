$package("com.kidscademy");

/**
 * Checkbox class.
 * 
 * @author Iulian Rotaru
 * @since 1.0
 * 
 * @constructor Construct an instance of Checkbox class.
 * @param js.dom.Document ownerDoc element owner document,
 * @param Node node native {@link Node} instance.
 * @assert assertions imposed by {@link js.dom.Element#Element(js.dom.Document, Node)}.
 */
com.kidscademy.Checkbox = function(ownerDoc, node) {
	this.$super(ownerDoc, node);
	this._checkbox = this.getByCssClass("control").on("click", this._onClick, this);
};

com.kidscademy.Checkbox.prototype = {
	/**
	 * Check this checkbox. After this method execution {@link #checked} returns true.
	 * 
	 * @return com.kidscademy.Checkbox this object.
	 */
	check : function() {
		this._checkbox.addCssClass("checked");
		return this;
	},

	/**
	 * Uncheck this checkbox. After this method execution {@link #checked} returns false.
	 * 
	 * @return com.kidscademy.Checkbox this object.
	 */
	uncheck : function() {
		this._checkbox.removeCssClass("checked");
		return this;
	},

	reset : function() {
		return this.uncheck();
	},

	/**
	 * Test if this checkbox is selected. Returns true if this checkbox is selected or false otherwise.
	 * 
	 * @return Boolean this checkbox state.
	 */
	checked : function() {
		return this._checkbox.hasCssClass("checked");
	},

	getName: function() {
		return this.getAttr("data-name");
	},
	
	getValue : function() {
		return this.getAttr("data-value");
	},


	/**
	 * Override control validation. Simply returns true since a checkbox is always valid.
	 * 
	 * @return Boolean always returns true.
	 */
	isValid : function() {
		return true;
	},

	/**
	 * Click event handler takes care to toggle checked state.
	 * 
	 * @param js.event.Event ev mouse click event.
	 */
	_onClick : function(ev) {
		this._checkbox.toggleCssClass("checked");
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "com.kidscademy.Checkbox";
	}
};
$extends(com.kidscademy.Checkbox, js.dom.Element);
$preload(com.kidscademy.Checkbox);
