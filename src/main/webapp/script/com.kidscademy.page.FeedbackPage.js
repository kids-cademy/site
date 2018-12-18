$package("com.kidscademy.page");

/**
 * FeedbackPage class.
 * 
 * @author Iulian Rotaru
 * @since 1.0
 * 
 * @constructor Construct an instance of FeedbackPage class.
 */
com.kidscademy.page.FeedbackPage = function() {
	this.$super();

	/**
	 * Suggestions form.
	 * 
	 * @type com.kidscademy.page.MessageForm
	 */
	this._form = this.getByClass(com.kidscademy.MessageForm);
	this._form.on("submitted", this._loadData, this);

	this._loadData();
};

com.kidscademy.page.FeedbackPage.prototype = {
	_loadData : function() {
		com.kidscademy.ServiceController.getFeedbackData(this._onDataLoaded, this);
	},

	_onDataLoaded : function(data) {
		this.getByCss(".feedback").setObject(data);
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "com.kidscademy.page.FeedbackPage";
	}
};
$extends(com.kidscademy.page.FeedbackPage, com.kidscademy.page.Page);
