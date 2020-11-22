$package("com.kidscademy");

/**
 * Hub service.
 */
com.kidscademy.HubService = {
	/**
	 * Record audit event.
	 *
	 * @param java.lang.String packageName,
	 * @param java.lang.String macAddress,
	 * @param java.lang.String event,
	 * @param java.lang.String parameter1,
	 * @param java.lang.String parameter2,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return void
	 * @assert callback is a {@link Function} and scope is an {@link Object}, if they are defined.
	 * @note since method return type is void, callback, and hence scope too, is optional.
	 */
	 recordAuditEvent: function(packageName, macAddress, event, parameter1, parameter2) {
		$assert(typeof packageName !== "undefined", "com.kidscademy.HubService#recordAuditEvent", "Package name argument is undefined.");
		$assert(packageName === null || js.lang.Types.isString(packageName), "com.kidscademy.HubService#recordAuditEvent", "Package name argument is not a string.");
		$assert(typeof macAddress !== "undefined", "com.kidscademy.HubService#recordAuditEvent", "Mac address argument is undefined.");
		$assert(macAddress === null || js.lang.Types.isString(macAddress), "com.kidscademy.HubService#recordAuditEvent", "Mac address argument is not a string.");
		$assert(typeof event !== "undefined", "com.kidscademy.HubService#recordAuditEvent", "Event argument is undefined.");
		$assert(event === null || js.lang.Types.isString(event), "com.kidscademy.HubService#recordAuditEvent", "Event argument is not a string.");
		$assert(typeof parameter1 !== "undefined", "com.kidscademy.HubService#recordAuditEvent", "Parameter1 argument is undefined.");
		$assert(parameter1 === null || js.lang.Types.isString(parameter1), "com.kidscademy.HubService#recordAuditEvent", "Parameter1 argument is not a string.");
		$assert(typeof parameter2 !== "undefined", "com.kidscademy.HubService#recordAuditEvent", "Parameter2 argument is undefined.");
		$assert(parameter2 === null || js.lang.Types.isString(parameter2), "com.kidscademy.HubService#recordAuditEvent", "Parameter2 argument is not a string.");

		var __callback__ = arguments[5];
		$assert(typeof __callback__ === "undefined" || js.lang.Types.isFunction(__callback__), "com.kidscademy.HubService#recordAuditEvent", "Callback is not a function.");
		var __scope__ = arguments[6];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.HubService#recordAuditEvent", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.HubService", "recordAuditEvent");
		rmi.setParameters(packageName, macAddress, event, parameter1, parameter2);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Dump stack trace.
	 *
	 * @param java.lang.String packageName,
	 * @param java.lang.String macAddress,
	 * @param java.lang.String stackTrace,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return void
	 * @assert callback is a {@link Function} and scope is an {@link Object}, if they are defined.
	 * @note since method return type is void, callback, and hence scope too, is optional.
	 */
	 dumpStackTrace: function(packageName, macAddress, stackTrace) {
		$assert(typeof packageName !== "undefined", "com.kidscademy.HubService#dumpStackTrace", "Package name argument is undefined.");
		$assert(packageName === null || js.lang.Types.isString(packageName), "com.kidscademy.HubService#dumpStackTrace", "Package name argument is not a string.");
		$assert(typeof macAddress !== "undefined", "com.kidscademy.HubService#dumpStackTrace", "Mac address argument is undefined.");
		$assert(macAddress === null || js.lang.Types.isString(macAddress), "com.kidscademy.HubService#dumpStackTrace", "Mac address argument is not a string.");
		$assert(typeof stackTrace !== "undefined", "com.kidscademy.HubService#dumpStackTrace", "Stack trace argument is undefined.");
		$assert(stackTrace === null || js.lang.Types.isString(stackTrace), "com.kidscademy.HubService#dumpStackTrace", "Stack trace argument is not a string.");

		var __callback__ = arguments[3];
		$assert(typeof __callback__ === "undefined" || js.lang.Types.isFunction(__callback__), "com.kidscademy.HubService#dumpStackTrace", "Callback is not a function.");
		var __scope__ = arguments[4];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.HubService#dumpStackTrace", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.HubService", "dumpStackTrace");
		rmi.setParameters(packageName, macAddress, stackTrace);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Vote project.
	 *
	 * @param java.lang.String packageName,
	 * @param java.lang.String macAddress,
	 * @param java.lang.String projectName,
	 * @param int vote,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return void
	 * @assert callback is a {@link Function} and scope is an {@link Object}, if they are defined.
	 * @note since method return type is void, callback, and hence scope too, is optional.
	 */
	 voteProject: function(packageName, macAddress, projectName, vote) {
		$assert(typeof packageName !== "undefined", "com.kidscademy.HubService#voteProject", "Package name argument is undefined.");
		$assert(packageName === null || js.lang.Types.isString(packageName), "com.kidscademy.HubService#voteProject", "Package name argument is not a string.");
		$assert(typeof macAddress !== "undefined", "com.kidscademy.HubService#voteProject", "Mac address argument is undefined.");
		$assert(macAddress === null || js.lang.Types.isString(macAddress), "com.kidscademy.HubService#voteProject", "Mac address argument is not a string.");
		$assert(typeof projectName !== "undefined", "com.kidscademy.HubService#voteProject", "Project name argument is undefined.");
		$assert(projectName === null || js.lang.Types.isString(projectName), "com.kidscademy.HubService#voteProject", "Project name argument is not a string.");
		$assert(typeof vote !== "undefined", "com.kidscademy.HubService#voteProject", "Vote argument is undefined.");
		$assert(js.lang.Types.isNumber(vote), "com.kidscademy.HubService#voteProject", "Vote argument is not a number.");

		var __callback__ = arguments[4];
		$assert(typeof __callback__ === "undefined" || js.lang.Types.isFunction(__callback__), "com.kidscademy.HubService#voteProject", "Callback is not a function.");
		var __scope__ = arguments[5];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.HubService#voteProject", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.HubService", "voteProject");
		rmi.setParameters(packageName, macAddress, projectName, vote);
		rmi.exec(__callback__, __scope__);
	}
};

if(typeof HubService === 'undefined') {
	HubService = com.kidscademy.HubService;
}
