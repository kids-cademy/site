$package("com.kidscademy");

/**
 * Service controller.
 */
com.kidscademy.ServiceController = {
	/**
	 * Record audit event.
	 *
	 * @param java.lang.String appName,
	 * @param com.kidscademy.model.Device device,
	 * @param java.lang.String event,
	 * @param java.lang.String[] parameters,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return void
	 * @assert callback is a {@link Function} and scope is an {@link Object}, if they are defined.
	 * @note since method return type is void, callback, and hence scope too, is optional.
	 */
	 recordAuditEvent: function(appName, device, event, parameters) {
		$assert(typeof appName !== "undefined", "com.kidscademy.ServiceController#recordAuditEvent", "App name argument is undefined.");
		$assert(appName === null || js.lang.Types.isString(appName), "com.kidscademy.ServiceController#recordAuditEvent", "App name argument is not a string.");
		$assert(typeof device !== "undefined", "com.kidscademy.ServiceController#recordAuditEvent", "Device argument is undefined.");
		$assert(typeof event !== "undefined", "com.kidscademy.ServiceController#recordAuditEvent", "Event argument is undefined.");
		$assert(event === null || js.lang.Types.isString(event), "com.kidscademy.ServiceController#recordAuditEvent", "Event argument is not a string.");
		$assert(typeof parameters !== "undefined", "com.kidscademy.ServiceController#recordAuditEvent", "Parameters argument is undefined.");
		$assert(parameters === null || js.lang.Types.isArray(parameters), "com.kidscademy.ServiceController#recordAuditEvent", "Parameters argument is not an array.");

		var __callback__ = arguments[4];
		$assert(typeof __callback__ === "undefined" || js.lang.Types.isFunction(__callback__), "com.kidscademy.ServiceController#recordAuditEvent", "Callback is not a function.");
		var __scope__ = arguments[5];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.ServiceController#recordAuditEvent", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.ServiceController", "recordAuditEvent");
		rmi.setParameters(appName, device, event, parameters);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Dump stack trace.
	 *
	 * @param java.lang.String appName,
	 * @param com.kidscademy.model.Device device,
	 * @param java.lang.String stackTrace,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return void
	 * @assert callback is a {@link Function} and scope is an {@link Object}, if they are defined.
	 * @note since method return type is void, callback, and hence scope too, is optional.
	 */
	 dumpStackTrace: function(appName, device, stackTrace) {
		$assert(typeof appName !== "undefined", "com.kidscademy.ServiceController#dumpStackTrace", "App name argument is undefined.");
		$assert(appName === null || js.lang.Types.isString(appName), "com.kidscademy.ServiceController#dumpStackTrace", "App name argument is not a string.");
		$assert(typeof device !== "undefined", "com.kidscademy.ServiceController#dumpStackTrace", "Device argument is undefined.");
		$assert(typeof stackTrace !== "undefined", "com.kidscademy.ServiceController#dumpStackTrace", "Stack trace argument is undefined.");
		$assert(stackTrace === null || js.lang.Types.isString(stackTrace), "com.kidscademy.ServiceController#dumpStackTrace", "Stack trace argument is not a string.");

		var __callback__ = arguments[3];
		$assert(typeof __callback__ === "undefined" || js.lang.Types.isFunction(__callback__), "com.kidscademy.ServiceController#dumpStackTrace", "Callback is not a function.");
		var __scope__ = arguments[4];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.ServiceController#dumpStackTrace", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.ServiceController", "dumpStackTrace");
		rmi.setParameters(appName, device, stackTrace);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Send developer message.
	 *
	 * @param java.lang.String appName,
	 * @param com.kidscademy.model.Device device,
	 * @param java.lang.String developerName,
	 * @param java.lang.String message,
	 * @param java.lang.String senderEmail,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return void
	 * @assert callback is a {@link Function} and scope is an {@link Object}, if they are defined.
	 * @note since method return type is void, callback, and hence scope too, is optional.
	 */
	 sendDeveloperMessage: function(appName, device, developerName, message, senderEmail) {
		$assert(typeof appName !== "undefined", "com.kidscademy.ServiceController#sendDeveloperMessage", "App name argument is undefined.");
		$assert(appName === null || js.lang.Types.isString(appName), "com.kidscademy.ServiceController#sendDeveloperMessage", "App name argument is not a string.");
		$assert(typeof device !== "undefined", "com.kidscademy.ServiceController#sendDeveloperMessage", "Device argument is undefined.");
		$assert(typeof developerName !== "undefined", "com.kidscademy.ServiceController#sendDeveloperMessage", "Developer name argument is undefined.");
		$assert(developerName === null || js.lang.Types.isString(developerName), "com.kidscademy.ServiceController#sendDeveloperMessage", "Developer name argument is not a string.");
		$assert(typeof message !== "undefined", "com.kidscademy.ServiceController#sendDeveloperMessage", "Message argument is undefined.");
		$assert(message === null || js.lang.Types.isString(message), "com.kidscademy.ServiceController#sendDeveloperMessage", "Message argument is not a string.");
		$assert(typeof senderEmail !== "undefined", "com.kidscademy.ServiceController#sendDeveloperMessage", "Sender email argument is undefined.");
		$assert(senderEmail === null || js.lang.Types.isString(senderEmail), "com.kidscademy.ServiceController#sendDeveloperMessage", "Sender email argument is not a string.");

		var __callback__ = arguments[5];
		$assert(typeof __callback__ === "undefined" || js.lang.Types.isFunction(__callback__), "com.kidscademy.ServiceController#sendDeveloperMessage", "Callback is not a function.");
		var __scope__ = arguments[6];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.ServiceController#sendDeveloperMessage", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.ServiceController", "sendDeveloperMessage");
		rmi.setParameters(appName, device, developerName, message, senderEmail);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Agree no ads manifest.
	 *
	 * @param com.kidscademy.model.Device device,
	 * @param boolean agree,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return void
	 * @assert callback is a {@link Function} and scope is an {@link Object}, if they are defined.
	 * @note since method return type is void, callback, and hence scope too, is optional.
	 */
	 agreeNoAdsManifest: function(device, agree) {
		$assert(typeof device !== "undefined", "com.kidscademy.ServiceController#agreeNoAdsManifest", "Device argument is undefined.");
		$assert(typeof agree !== "undefined", "com.kidscademy.ServiceController#agreeNoAdsManifest", "Agree argument is undefined.");
		$assert(js.lang.Types.isBoolean(agree), "com.kidscademy.ServiceController#agreeNoAdsManifest", "Agree argument is not a boolean.");

		var __callback__ = arguments[2];
		$assert(typeof __callback__ === "undefined" || js.lang.Types.isFunction(__callback__), "com.kidscademy.ServiceController#agreeNoAdsManifest", "Callback is not a function.");
		var __scope__ = arguments[3];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.ServiceController#agreeNoAdsManifest", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.ServiceController", "agreeNoAdsManifest");
		rmi.setParameters(device, agree);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Get feedback data.
	 *
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return com.kidscademy.model.Feedback
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 getFeedbackData: function() {
		var __callback__ = arguments[0];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.ServiceController#getFeedbackData", "Callback is not a function.");
		var __scope__ = arguments[1];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.ServiceController#getFeedbackData", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.ServiceController", "getFeedbackData");
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Increment like counter.
	 *
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return com.kidscademy.model.Counters
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 incrementLikeCounter: function() {
		var __callback__ = arguments[0];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.ServiceController#incrementLikeCounter", "Callback is not a function.");
		var __scope__ = arguments[1];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.ServiceController#incrementLikeCounter", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.ServiceController", "incrementLikeCounter");
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Increment dislike counter.
	 *
	 * @param java.util.List<com.kidscademy.model.DislikeReason> reasons,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return com.kidscademy.model.Counters
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 incrementDislikeCounter: function(reasons) {
		$assert(typeof reasons !== "undefined", "com.kidscademy.ServiceController#incrementDislikeCounter", "Reasons argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.ServiceController#incrementDislikeCounter", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.ServiceController#incrementDislikeCounter", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.ServiceController", "incrementDislikeCounter");
		rmi.setParameters(reasons);
		rmi.exec(__callback__, __scope__);
	}
};

if(typeof ServiceController === 'undefined') {
	ServiceController = com.kidscademy.ServiceController;
}
