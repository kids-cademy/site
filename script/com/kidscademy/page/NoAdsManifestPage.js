$package("com.kidscademy.page");

/**
 * NoAdsManifestPage class.
 * 
 * @author Iulian Rotaru
 * 
 * @constructor Construct an instance of NoAdsManifestPage class.
 */
com.kidscademy.page.NoAdsManifestPage = function() {
	this.$super();

	this._captcha = this.getByClass(com.kidscademy.CheckboxCaptcha);

	this.getByCss("button.agree").on("click", this._onAgree, this);
	this.getByCss("button.not-agree").on("click", this._onNotAgree, this);
};

com.kidscademy.page.NoAdsManifestPage.prototype = {
	_onAgree : function(ev) {
		if (this._captcha.isValid()) {
			com.kidscademy.ServiceController.agreeNoAdsManifest(null, true, this._onSurveyDone, this);
		}
	},

	_onNotAgree : function(ev) {
		if (this._captcha.isValid()) {
			com.kidscademy.ServiceController.agreeNoAdsManifest(null, false, this._onSurveyDone, this);
		}
	},

	_onSurveyDone : function() {
		WinMain.assign("@link/index");
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "com.kidscademy.page.NoAdsManifestPage";
	}
};
$extends(com.kidscademy.page.NoAdsManifestPage, com.kidscademy.page.Page);
