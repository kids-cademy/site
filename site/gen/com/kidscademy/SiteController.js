$package("com.kidscademy");

/**
 * Site controller.
 */
com.kidscademy.SiteController = {
	/**
	 * Forget password.
	 *
	 * @param javax.mail.internet.InternetAddress emailAddress,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return void
	 * @assert callback is a {@link Function} and scope is an {@link Object}, if they are defined.
	 * @note since method return type is void, callback, and hence scope too, is optional.
	 */
	 forgetPassword: function(emailAddress) {
		$assert(typeof emailAddress !== "undefined", "com.kidscademy.SiteController#forgetPassword", "Email address argument is undefined.");

		var __callback__ = arguments[1];
		$assert(typeof __callback__ === "undefined" || js.lang.Types.isFunction(__callback__), "com.kidscademy.SiteController#forgetPassword", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.SiteController#forgetPassword", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.SiteController", "forgetPassword");
		rmi.setParameters(emailAddress);
		rmi.exec(__callback__, __scope__);
	}
};

if(typeof SiteController === 'undefined') {
	SiteController = com.kidscademy.SiteController;
}
