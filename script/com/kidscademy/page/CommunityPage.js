$package("com.kidscademy.page");

/**
 * CommunityPage class.
 * 
 * @author Iulian Rotaru
 */
com.kidscademy.page.CommunityPage = class extends com.kidscademy.page.Page {
	/**
	 * Construct an instance of CommunityPage class.
	 */
	constructor() {
		super();

		this._forgotPasswordSection = this.getByCss("section.forgot-password");

		this._loginForm = this.getByCss(".login form");
		this._joinForm = this._forgotPasswordSection.getByTag("form");
		this._forgotPasswordForm = this.getByCss(".forgot-password form");

		this.getByCss(".login button").on("click", this._onLogin, this);
		this.getByCss(".join button").on("click", this._onJoin, this);
		this._forgotPasswordSection.getByTag("button").on("click", this._onForgotPassword, this);
	}

	_onLogin(ev) {
		if (this._loginForm.isValid()) {
			AdminService.login(this._loginForm.getObject(), login => {
				if (login) {
					WinMain.assign("collection.htm");
				}
				else {
					this._loginForm.reset();
				}
			});
		}
	}

	_onJoin(ev) {
		alert('join')
	}

	_onForgotPassword(ev) {
		if (this._forgotPasswordForm.isValid()) {
			com.kidscademy.SiteController.forgetPassword(this._forgotPasswordForm.getObject().emailAddress, () => this._forgotPasswordSection.collapse());
		}
	}

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString() {
		return "com.kidscademy.page.CommunityPage";
	}
};

WinMain.createPage(com.kidscademy.page.CommunityPage);
