$package("com.kidscademy.page");

/**
 * NoAdsManifestPage class.
 * 
 * @author Iulian Rotaru
 */
com.kidscademy.page.NoAdsManifestPage = class extends com.kidscademy.page.Page {
	/**
	 * Construct an instance of NoAdsManifestPage class.
	 */
	constructor() {
		super();

		this._captcha = this.getByClass(com.kidscademy.CheckboxCaptcha);

		this.getByCss("button.agree").on("click", this._onAgree, this);
		this.getByCss("button.not-agree").on("click", this._onNotAgree, this);
	}

	_onAgree(ev) {
		if (this._captcha.isValid()) {
			com.kidscademy.ServiceController.agreeNoAdsManifest(null, true, this._onSurveyDone, this);
		}
	}

	_onNotAgree(ev) {
		if (this._captcha.isValid()) {
			com.kidscademy.ServiceController.agreeNoAdsManifest(null, false, this._onSurveyDone, this);
		}
	}

	_onSurveyDone() {
		WinMain.assign("@link/index");
	}

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString() {
		return "com.kidscademy.page.NoAdsManifestPage";
	}
};
