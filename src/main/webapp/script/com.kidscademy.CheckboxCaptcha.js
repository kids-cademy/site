$package("com.kidscademy");

/**
 * Checkbox CAPTCHA has a hidden standard challenge sentence and image set and a checkbox that reveal them.
 * 
 * @author Iulian Rotaru
 * 
 * @constructor Construct an instance of CAPTCHA class.
 * @param js.dom.Document ownerDoc element owner document,
 * @param Node node native {@link Node} instance.
 * @assert assertions imposed by {@link js.dom.Element#Element(js.dom.Document, Node)}.
 */
com.kidscademy.CheckboxCaptcha = function(ownerDoc, node) {

	this.$super(ownerDoc, node);

	this._checkbox = this.getByClass(com.kidscademy.Checkbox);
	this._checkbox.on("click", this._onCheckboxClick, this);

	this._bodyView = this.getByCssClass("body");

	this.getByCssClass("help").on("click", this._onHelp, this);
};

com.kidscademy.CheckboxCaptcha.prototype = {
	isValid : function() {
		if (!this._checkbox.checked()) {
			js.ua.System.alert("Please check you are a humman.");
			return false;
		}
		return true;
	},

	reset : function() {
		this.$super("reset");
		this._checkbox.reset();
		this._bodyView.hide();
	},

	_onCheckboxClick : function(ev) {
		ev.halt();
		this._loadChallenge(true);
		this._checkbox.uncheck();
		this._bodyView.show();
	},

	_onResponseVerified : function(challenge) {
		this.$super("_onResponseVerified", challenge);
		if (this._valid) {
			this._checkbox.check();
			this._bodyView.hide();
		}
	},

	_onHelp : function(ev) {
		js.ua.System.alert("Help not yet implemented.");
	},

	_loadChallenge : function(enabled) {
		if (enabled === true) {
			this.$super("_loadChallenge");
		}
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "com.kidscademy.CheckboxCaptcha";
	}
};
$extends(com.kidscademy.CheckboxCaptcha, js.widget.Captcha);
$preload(com.kidscademy.CheckboxCaptcha);
