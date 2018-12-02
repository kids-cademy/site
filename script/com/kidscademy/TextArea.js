$package("com.kidscademy");

/**
 * TextArea class.
 * 
 * @author Iulian Rotaru
 * @since 1.0
 * 
 * @constructor Construct an instance of TextArea class.
 * @param js.dom.Document ownerDoc element owner document,
 * @param Node node native {@link Node} instance.
 * @assert assertions imposed by {@link js.dom.Element#Element(js.dom.Document, Node)}.
 */
com.kidscademy.TextArea = function(ownerDoc, node) {
	this.$super(ownerDoc, node);
	this.on("keyup", this._onKeyUp, this);
	if (this.style.isVisible()) {
		this._onKeyUp(null);
	}
};

com.kidscademy.TextArea.prototype = {
	show : function() {
		this.$super("show");
		this._onKeyUp(null);
	},

	reset: function() {
		this.$super("reset");
		this._onKeyUp(null);
	},
	
	_onKeyUp : function(ev) {
		$assert(this.style.isVisible(), "com.kidscademy.TextArea#_onKeyUp", "Attempt to auto-resize on invisible text area.");
		this.style.setHeight(1);
		this.style.setHeight(this._node.scrollHeight);
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "com.kidscademy.TextArea";
	}
};
$extends(com.kidscademy.TextArea, js.dom.Control);
$preload(com.kidscademy.TextArea);