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
		 * Suggestions form.
		 * 
		 * @type com.kidscademy.page.MessageForm
		 */
		this._form = this.getByClass(com.kidscademy.MessageForm);
		this._form.on("submitted", this._loadData, this);

		this._loadData();
	}

	_loadData() {
		com.kidscademy.ServiceController.getFeedbackData(this._onDataLoaded, this);
	}

	_onDataLoaded(data) {
		this.getByCss(".feedback").setObject(data);
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

WinMain.setPage(com.kidscademy.page.FeedbackPage);
