$package("com.kidscademy");

/**
 * AppFeedback class.
 * 
 * @author Iulian Rotaru
 * 
 * @constructor Construct an instance of AppFeedback class.
 * @param js.dom.Document ownerDoc element owner document,
 * @param Node node native {@link Node} instance.
 * @assert assertions imposed by {@link js.dom.Element#Element(js.dom.Document, Node)}.
 */
com.kidscademy.AppFeedback = function(ownerDoc, node) {
	this.$super(ownerDoc, node);

	/**
	 * Feedback form.
	 * 
	 * @type js.dom.Form
	 */
	this._form = this.getByTag("form");

	/**
	 * Application ID as registered into kinds (a)cademy servers.
	 * 
	 * @type String
	 */
	this._appID = null;

	this.getByCss("button.submit").on("click", this._onSubmit, this);
	this.getByCss("button.cancel").on("click", this._onCancel, this);
};

com.kidscademy.AppFeedback.prototype = {
	setAppID : function(appID) {
		this._appID = appID;
	},

	_onSubmit : function(ev) {
		if(!this._form.isValid()) {
			return;
		}
		
		var message = this._form.getObject();
		var text = com.kidscademy.util.Text.text2html(message.text);
		if (text) {
			com.kidscademy.ServiceController.sendDeveloperMessage(this._appID, null, null, text, message.senderEmail, function() {
				this._form.reset();
			}, this);
		}
	},

	_onCancel : function(ev) {
		this._form.reset();
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "com.kidscademy.AppFeedback";
	}
};
$extends(com.kidscademy.AppFeedback, js.dom.Element);
