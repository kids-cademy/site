$package("com.kidscademy.page");

/**
 * Page class for No-Ads Manifesto.
 * 
 * @author Iulian Rotaru
 */
com.kidscademy.page.NoAdsManifestoPage = class extends com.kidscademy.page.Page {
	/**
	 * Construct an instance of No-Ads Manifesto page.
	 */
	constructor() {
		super();

		this._captcha = this.getByClass(com.kidscademy.CheckboxCaptcha);

		this.getByCss("button.agree").on("click", this._onAgree, this);
		this.getByCss("button.not-agree").on("click", this._onNotAgree, this);
	}

	_onAgree(ev) {
		if (this._captcha.isValid()) {
			ServiceController.agreeNoAdsManifest(null, true, this._onSurveyDone, this);
		}
	}

	_onNotAgree(ev) {
		if (this._captcha.isValid()) {
			ServiceController.agreeNoAdsManifest(null, false, this._onSurveyDone, this);
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

WinMain.createPage(com.kidscademy.page.NoAdsManifestoPage);