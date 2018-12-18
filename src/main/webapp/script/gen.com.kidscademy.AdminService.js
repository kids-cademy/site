$package("com.kidscademy");

/**
 * Admin service.
 */
com.kidscademy.AdminService = {
	/**
	 * Get instruments.
	 *
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.util.List<com.kidscademy.atlas.Instrument>
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 getInstruments: function() {
		var __callback__ = arguments[0];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AdminService#getInstruments", "Callback is not a function.");
		var __scope__ = arguments[1];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AdminService#getInstruments", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AdminService", "getInstruments");
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Get instrument.
	 *
	 * @param int instrumentId,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return com.kidscademy.atlas.Instrument
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 getInstrument: function(instrumentId) {
		$assert(typeof instrumentId !== "undefined", "com.kidscademy.AdminService#getInstrument", "Instrument id argument is undefined.");
		$assert(js.lang.Types.isNumber(instrumentId), "com.kidscademy.AdminService#getInstrument", "Instrument id argument is not a number.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AdminService#getInstrument", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AdminService#getInstrument", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AdminService", "getInstrument");
		rmi.setParameters(instrumentId);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Get instrument by name.
	 *
	 * @param java.lang.String name,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return com.kidscademy.atlas.Instrument
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 getInstrumentByName: function(name) {
		$assert(typeof name !== "undefined", "com.kidscademy.AdminService#getInstrumentByName", "Name argument is undefined.");
		$assert(name === null || js.lang.Types.isString(name), "com.kidscademy.AdminService#getInstrumentByName", "Name argument is not a string.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AdminService#getInstrumentByName", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AdminService#getInstrumentByName", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AdminService", "getInstrumentByName");
		rmi.setParameters(name);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Save instrument.
	 *
	 * @param com.kidscademy.atlas.Instrument instrument,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return void
	 * @assert callback is a {@link Function} and scope is an {@link Object}, if they are defined.
	 * @note since method return type is void, callback, and hence scope too, is optional.
	 */
	 saveInstrument: function(instrument) {
		$assert(typeof instrument !== "undefined", "com.kidscademy.AdminService#saveInstrument", "Instrument argument is undefined.");

		var __callback__ = arguments[1];
		$assert(typeof __callback__ === "undefined" || js.lang.Types.isFunction(__callback__), "com.kidscademy.AdminService#saveInstrument", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AdminService#saveInstrument", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AdminService", "saveInstrument");
		rmi.setParameters(instrument);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Get related instruments.
	 *
	 * @param java.util.List<java.lang.String> names,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.util.List<com.kidscademy.atlas.GraphicObject>
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 getRelatedInstruments: function(names) {
		$assert(typeof names !== "undefined", "com.kidscademy.AdminService#getRelatedInstruments", "Names argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AdminService#getRelatedInstruments", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AdminService#getRelatedInstruments", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AdminService", "getRelatedInstruments");
		rmi.setParameters(names);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Get available instruments.
	 *
	 * @param com.kidscademy.atlas.Instrument.Category category,
	 * @param java.util.List<com.kidscademy.atlas.GraphicObject> related,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.util.List<com.kidscademy.atlas.GraphicObject>
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 getAvailableInstruments: function(category, related) {
		$assert(typeof category !== "undefined", "com.kidscademy.AdminService#getAvailableInstruments", "Category argument is undefined.");
		$assert(typeof related !== "undefined", "com.kidscademy.AdminService#getAvailableInstruments", "Related argument is undefined.");

		var __callback__ = arguments[2];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AdminService#getAvailableInstruments", "Callback is not a function.");
		var __scope__ = arguments[3];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AdminService#getAvailableInstruments", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AdminService", "getAvailableInstruments");
		rmi.setParameters(category, related);
		rmi.exec(__callback__, __scope__);
	}
};

if(typeof AdminService === 'undefined') {
	AdminService = com.kidscademy.AdminService;
}
