$package("com.kidscademy.page");

/**
 * CommunityPage class.
 * 
 * @author Iulian Rotaru
 * @since 1.0
 * 
 * @constructor Construct an instance of CommunityPage class.
 */
com.kidscademy.page.CommunityPage = function() {
	this.$super();

	this._forgotPasswordSection = this.getByCss("section.forgot-password");

	this._loginForm = this.getByCss(".login form");
	this._joinForm = this._forgotPasswordSection.getByTag("form");
	this._forgotPasswordForm = this.getByCss(".forgot-password form");

	this.getByCss(".login button").on("click", this._onLogin, this);
	this.getByCss(".join button").on("click", this._onJoin, this);
	this._forgotPasswordSection.getByTag("button").on("click", this._onForgotPassword, this);
};

com.kidscademy.page.CommunityPage.prototype = {
	_onLogin : function(ev) {
		alert('login')
	},

	_onJoin : function(ev) {
		alert('join')
	},

	_onForgotPassword : function(ev) {
		if (this._forgotPasswordForm.isValid()) {
			com.kidscademy.SiteController.forgetPassword(this._forgotPasswordForm.getObject().emailAddress, function() {
				this._forgotPasswordSection.collapse();
			}, this);
		}
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "com.kidscademy.page.CommunityPage";
	}
};
$extends(com.kidscademy.page.CommunityPage, com.kidscademy.page.Page);
