$package("com.kidscademy");

/**
 * Admin service.
 */
com.kidscademy.AdminService = {
	/**
	 * Login.
	 *
	 * @param com.kidscademy.atlas.Login login,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return boolean
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 login: function(login) {
		$assert(typeof login !== "undefined", "com.kidscademy.AdminService#login", "Login argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AdminService#login", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AdminService#login", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AdminService", "login");
		rmi.setParameters(login);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Is authenticated.
	 *
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return boolean
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 isAuthenticated: function() {
		var __callback__ = arguments[0];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AdminService#isAuthenticated", "Callback is not a function.");
		var __scope__ = arguments[1];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AdminService#isAuthenticated", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AdminService", "isAuthenticated");
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Logout.
	 *
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return void
	 * @assert callback is a {@link Function} and scope is an {@link Object}, if they are defined.
	 * @note since method return type is void, callback, and hence scope too, is optional.
	 */
	 logout: function() {
		var __callback__ = arguments[0];
		$assert(typeof __callback__ === "undefined" || js.lang.Types.isFunction(__callback__), "com.kidscademy.AdminService#logout", "Callback is not a function.");
		var __scope__ = arguments[1];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AdminService#logout", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AdminService", "logout");
		rmi.exec(__callback__, __scope__);
	}
};

if(typeof AdminService === 'undefined') {
	AdminService = com.kidscademy.AdminService;
}
