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
	 * @throws java.io.IOException
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
	 * @return int
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 saveInstrument: function(instrument) {
		$assert(typeof instrument !== "undefined", "com.kidscademy.AdminService#saveInstrument", "Instrument argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AdminService#saveInstrument", "Callback is not a function.");
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
	},

	/**
	 * Upload audio sample.
	 *
	 * @param js.http.form.Form form,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.util.Map<java.lang.String,java.lang.Object>
	 * @throws java.io.IOException
	 * @throws javax.sound.sampled.UnsupportedAudioFileException
	 * @throws java.lang.InterruptedException
	 * @throws org.im4java.core.IM4JavaException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 uploadAudioSample: function(form) {
		$assert(typeof form !== "undefined", "com.kidscademy.AdminService#uploadAudioSample", "Form argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AdminService#uploadAudioSample", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AdminService#uploadAudioSample", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AdminService", "uploadAudioSample");
		rmi.setParameters(form);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Generate waveform.
	 *
	 * @param java.lang.String objectName,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.lang.String
	 * @throws java.io.IOException
	 * @throws javax.sound.sampled.UnsupportedAudioFileException
	 * @throws java.lang.InterruptedException
	 * @throws org.im4java.core.IM4JavaException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 generateWaveform: function(objectName) {
		$assert(typeof objectName !== "undefined", "com.kidscademy.AdminService#generateWaveform", "Object name argument is undefined.");
		$assert(objectName === null || js.lang.Types.isString(objectName), "com.kidscademy.AdminService#generateWaveform", "Object name argument is not a string.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AdminService#generateWaveform", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AdminService#generateWaveform", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AdminService", "generateWaveform");
		rmi.setParameters(objectName);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Upload picture file.
	 *
	 * @param js.http.form.Form form,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.lang.String
	 * @throws java.io.IOException
	 * @throws java.lang.InterruptedException
	 * @throws org.im4java.core.IM4JavaException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 uploadPictureFile: function(form) {
		$assert(typeof form !== "undefined", "com.kidscademy.AdminService#uploadPictureFile", "Form argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AdminService#uploadPictureFile", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AdminService#uploadPictureFile", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AdminService", "uploadPictureFile");
		rmi.setParameters(form);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Upload icon file.
	 *
	 * @param js.http.form.Form form,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.lang.String
	 * @throws java.io.IOException
	 * @throws java.lang.InterruptedException
	 * @throws org.im4java.core.IM4JavaException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 uploadIconFile: function(form) {
		$assert(typeof form !== "undefined", "com.kidscademy.AdminService#uploadIconFile", "Form argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AdminService#uploadIconFile", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AdminService#uploadIconFile", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AdminService", "uploadIconFile");
		rmi.setParameters(form);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Upload thumbnail file.
	 *
	 * @param js.http.form.Form form,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.lang.String
	 * @throws java.io.IOException
	 * @throws java.lang.InterruptedException
	 * @throws org.im4java.core.IM4JavaException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 uploadThumbnailFile: function(form) {
		$assert(typeof form !== "undefined", "com.kidscademy.AdminService#uploadThumbnailFile", "Form argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AdminService#uploadThumbnailFile", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AdminService#uploadThumbnailFile", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AdminService", "uploadThumbnailFile");
		rmi.setParameters(form);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Create object icon.
	 *
	 * @param java.lang.String objectName,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.lang.String
	 * @throws java.io.IOException
	 * @throws java.lang.InterruptedException
	 * @throws org.im4java.core.IM4JavaException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 createObjectIcon: function(objectName) {
		$assert(typeof objectName !== "undefined", "com.kidscademy.AdminService#createObjectIcon", "Object name argument is undefined.");
		$assert(objectName === null || js.lang.Types.isString(objectName), "com.kidscademy.AdminService#createObjectIcon", "Object name argument is not a string.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AdminService#createObjectIcon", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AdminService#createObjectIcon", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AdminService", "createObjectIcon");
		rmi.setParameters(objectName);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Create link.
	 *
	 * @param java.net.URL url,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return com.kidscademy.atlas.Link
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 createLink: function(url) {
		$assert(typeof url !== "undefined", "com.kidscademy.AdminService#createLink", "Url argument is undefined.");
		$assert(url === null || js.lang.Types.isString(url), "com.kidscademy.AdminService#createLink", "Url argument is not a string.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AdminService#createLink", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AdminService#createLink", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AdminService", "createLink");
		rmi.setParameters(url);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Remove instrument sample.
	 *
	 * @param java.lang.String instrumentName,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return void
	 * @assert callback is a {@link Function} and scope is an {@link Object}, if they are defined.
	 * @note since method return type is void, callback, and hence scope too, is optional.
	 */
	 removeInstrumentSample: function(instrumentName) {
		$assert(typeof instrumentName !== "undefined", "com.kidscademy.AdminService#removeInstrumentSample", "Instrument name argument is undefined.");
		$assert(instrumentName === null || js.lang.Types.isString(instrumentName), "com.kidscademy.AdminService#removeInstrumentSample", "Instrument name argument is not a string.");

		var __callback__ = arguments[1];
		$assert(typeof __callback__ === "undefined" || js.lang.Types.isFunction(__callback__), "com.kidscademy.AdminService#removeInstrumentSample", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AdminService#removeInstrumentSample", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AdminService", "removeInstrumentSample");
		rmi.setParameters(instrumentName);
		rmi.exec(__callback__, __scope__);
	}
};

if(typeof AdminService === 'undefined') {
	AdminService = com.kidscademy.AdminService;
}
