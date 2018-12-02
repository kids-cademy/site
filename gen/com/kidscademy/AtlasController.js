$package("com.kidscademy");

/**
 * Atlas controller.
 */
com.kidscademy.AtlasController = {
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
		$assert(typeof instrumentId !== "undefined", "com.kidscademy.AtlasController#getInstrument", "Instrument id argument is undefined.");
		$assert(js.lang.Types.isNumber(instrumentId), "com.kidscademy.AtlasController#getInstrument", "Instrument id argument is not a number.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasController#getInstrument", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasController#getInstrument", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasController", "getInstrument");
		rmi.setParameters(instrumentId);
		rmi.exec(__callback__, __scope__);
	}
};

if(typeof AtlasController === 'undefined') {
	AtlasController = com.kidscademy.AtlasController;
}
