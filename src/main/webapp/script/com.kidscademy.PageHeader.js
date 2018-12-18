$package("com.kidscademy");

/**
 * PageHeader class.
 * 
 * @author Iulian Rotaru
 * @since 1.0
 * 
 * @constructor Construct an instance of PageHeader class.
 * @param js.dom.Document ownerDoc element owner document,
 * @param Node node native {@link Node} instance.
 * @assert assertions imposed by {@link js.dom.Element#Element(js.dom.Document, Node)}.
 */
com.kidscademy.PageHeader = function(ownerDoc, node) {
	this.$super(ownerDoc, node);

	this._topMenu = this.getByCssClass("top-menu");

	this._topMenuButton = this.getByCssClass("top-menu-button");
	this._topMenuButton.on("click", this._onTopMenuButton, this);

	this._collapsed = true;
};

com.kidscademy.PageHeader.prototype = {
	_onTopMenuButton : function(ev) {
		this._collapsed = !this._collapsed;
		if (!this._collapsed) {
			var topMenuHeight = this._topMenu.getFirstChild().style.getHeight();
			this._topMenu.style.set("height", topMenuHeight + "px");
		}
		else {
			this._topMenu.style.set("height", "0");
		}
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "com.kidscademy.PageHeader";
	}
};
$extends(com.kidscademy.PageHeader, js.dom.Element);
$preload(com.kidscademy.PageHeader);