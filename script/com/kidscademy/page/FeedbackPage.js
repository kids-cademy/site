$package("com.kidscademy.page");

/**
 * FeedbackPage class.
 * 
 * @author Iulian Rotaru
 */
com.kidscademy.page.FeedbackPage = class extends com.kidscademy.page.Page {
	/**
	 * Construct an instance of FeedbackPage class.
	 */
	constructor() {
		super();

		/**
		 * Feedback data view. 
		 * @type js.dom.Element
		 */
		this._feedbackView = this.getByCss(".feedback");

		/**
		 * Suggestions form.
		 * @type com.kidscademy.MessageForm
		 */
		this._form = this.getByClass(com.kidscademy.MessageForm);
		this._form.on("submitted", this._loadData, this);

		this._loadData();
	}

	_loadData() {
		ServiceController.getFeedbackData((data) => this._feedbackView.setObject(data));
	}

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString() {
		return "com.kidscademy.page.FeedbackPage";
	}
};

WinMain.createPage(com.kidscademy.page.FeedbackPage);
