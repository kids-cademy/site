$package("com.kidscademy");

/**
 * Atlas service.
 */
com.kidscademy.AtlasService = {
	/**
	 * Get instruments.
	 *
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.util.List<com.kidscademy.atlas.UIObject>
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 getInstruments: function() {
		var __callback__ = arguments[0];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#getInstruments", "Callback is not a function.");
		var __scope__ = arguments[1];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#getInstruments", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "getInstruments");
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
		$assert(typeof instrumentId !== "undefined", "com.kidscademy.AtlasService#getInstrument", "Instrument id argument is undefined.");
		$assert(js.lang.Types.isNumber(instrumentId), "com.kidscademy.AtlasService#getInstrument", "Instrument id argument is not a number.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#getInstrument", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#getInstrument", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "getInstrument");
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
		$assert(typeof name !== "undefined", "com.kidscademy.AtlasService#getInstrumentByName", "Name argument is undefined.");
		$assert(name === null || js.lang.Types.isString(name), "com.kidscademy.AtlasService#getInstrumentByName", "Name argument is not a string.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#getInstrumentByName", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#getInstrumentByName", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "getInstrumentByName");
		rmi.setParameters(name);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Save instrument.
	 *
	 * @param com.kidscademy.atlas.Instrument instrument,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return com.kidscademy.atlas.Instrument
	 * @throws java.io.IOException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 saveInstrument: function(instrument) {
		$assert(typeof instrument !== "undefined", "com.kidscademy.AtlasService#saveInstrument", "Instrument argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#saveInstrument", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#saveInstrument", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "saveInstrument");
		rmi.setParameters(instrument);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Get related instruments.
	 *
	 * @param java.util.List<java.lang.String> names,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.util.List<com.kidscademy.atlas.UIObject>
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 getRelatedInstruments: function(names) {
		$assert(typeof names !== "undefined", "com.kidscademy.AtlasService#getRelatedInstruments", "Names argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#getRelatedInstruments", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#getRelatedInstruments", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "getRelatedInstruments");
		rmi.setParameters(names);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Get available instruments.
	 *
	 * @param com.kidscademy.atlas.Instrument.Category category,
	 * @param java.util.List<com.kidscademy.atlas.UIObject> related,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.util.List<com.kidscademy.atlas.UIObject>
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 getAvailableInstruments: function(category, related) {
		$assert(typeof category !== "undefined", "com.kidscademy.AtlasService#getAvailableInstruments", "Category argument is undefined.");
		$assert(typeof related !== "undefined", "com.kidscademy.AtlasService#getAvailableInstruments", "Related argument is undefined.");

		var __callback__ = arguments[2];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#getAvailableInstruments", "Callback is not a function.");
		var __scope__ = arguments[3];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#getAvailableInstruments", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "getAvailableInstruments");
		rmi.setParameters(category, related);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Create link.
	 *
	 * @param com.kidscademy.atlas.Link link,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return com.kidscademy.atlas.Link
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 createLink: function(link) {
		$assert(typeof link !== "undefined", "com.kidscademy.AtlasService#createLink", "Link argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#createLink", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#createLink", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "createLink");
		rmi.setParameters(link);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Import object definition.
	 *
	 * @param com.kidscademy.atlas.Link link,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.lang.String
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 importObjectDefinition: function(link) {
		$assert(typeof link !== "undefined", "com.kidscademy.AtlasService#importObjectDefinition", "Link argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#importObjectDefinition", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#importObjectDefinition", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "importObjectDefinition");
		rmi.setParameters(link);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Import object description.
	 *
	 * @param com.kidscademy.atlas.Link link,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.lang.String
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 importObjectDescription: function(link) {
		$assert(typeof link !== "undefined", "com.kidscademy.AtlasService#importObjectDescription", "Link argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#importObjectDescription", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#importObjectDescription", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "importObjectDescription");
		rmi.setParameters(link);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Import objects facts.
	 *
	 * @param com.kidscademy.atlas.Link link,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return java.util.Map<java.lang.String,java.lang.String>
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 importObjectsFacts: function(link) {
		$assert(typeof link !== "undefined", "com.kidscademy.AtlasService#importObjectsFacts", "Link argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#importObjectsFacts", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#importObjectsFacts", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "importObjectsFacts");
		rmi.setParameters(link);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Upload picture.
	 *
	 * @param js.http.form.Form form,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return com.kidscademy.atlas.Picture
	 * @throws java.io.IOException
	 * @throws js.rmi.BusinessException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 uploadPicture: function(form) {
		$assert(typeof form !== "undefined", "com.kidscademy.AtlasService#uploadPicture", "Form argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#uploadPicture", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#uploadPicture", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "uploadPicture");
		rmi.setParameters(form);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Upload picture by source.
	 *
	 * @param js.http.form.Form form,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return com.kidscademy.atlas.Picture
	 * @throws java.io.IOException
	 * @throws js.rmi.BusinessException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 uploadPictureBySource: function(form) {
		$assert(typeof form !== "undefined", "com.kidscademy.AtlasService#uploadPictureBySource", "Form argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#uploadPictureBySource", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#uploadPictureBySource", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "uploadPictureBySource");
		rmi.setParameters(form);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Duplicate picture.
	 *
	 * @param com.kidscademy.atlas.UIObject object,
	 * @param com.kidscademy.atlas.Picture picture,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return com.kidscademy.atlas.Picture
	 * @throws java.io.IOException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 duplicatePicture: function(object, picture) {
		$assert(typeof object !== "undefined", "com.kidscademy.AtlasService#duplicatePicture", "Object argument is undefined.");
		$assert(typeof picture !== "undefined", "com.kidscademy.AtlasService#duplicatePicture", "Picture argument is undefined.");

		var __callback__ = arguments[2];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#duplicatePicture", "Callback is not a function.");
		var __scope__ = arguments[3];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#duplicatePicture", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "duplicatePicture");
		rmi.setParameters(object, picture);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Trim picture.
	 *
	 * @param com.kidscademy.atlas.UIObject object,
	 * @param com.kidscademy.atlas.Picture picture,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return com.kidscademy.atlas.Picture
	 * @throws java.io.IOException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 trimPicture: function(object, picture) {
		$assert(typeof object !== "undefined", "com.kidscademy.AtlasService#trimPicture", "Object argument is undefined.");
		$assert(typeof picture !== "undefined", "com.kidscademy.AtlasService#trimPicture", "Picture argument is undefined.");

		var __callback__ = arguments[2];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#trimPicture", "Callback is not a function.");
		var __scope__ = arguments[3];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#trimPicture", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "trimPicture");
		rmi.setParameters(object, picture);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Flop picture.
	 *
	 * @param com.kidscademy.atlas.UIObject object,
	 * @param com.kidscademy.atlas.Picture picture,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return com.kidscademy.atlas.Picture
	 * @throws java.io.IOException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 flopPicture: function(object, picture) {
		$assert(typeof object !== "undefined", "com.kidscademy.AtlasService#flopPicture", "Object argument is undefined.");
		$assert(typeof picture !== "undefined", "com.kidscademy.AtlasService#flopPicture", "Picture argument is undefined.");

		var __callback__ = arguments[2];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#flopPicture", "Callback is not a function.");
		var __scope__ = arguments[3];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#flopPicture", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "flopPicture");
		rmi.setParameters(object, picture);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Flip picture.
	 *
	 * @param com.kidscademy.atlas.UIObject object,
	 * @param com.kidscademy.atlas.Picture picture,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return com.kidscademy.atlas.Picture
	 * @throws java.io.IOException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 flipPicture: function(object, picture) {
		$assert(typeof object !== "undefined", "com.kidscademy.AtlasService#flipPicture", "Object argument is undefined.");
		$assert(typeof picture !== "undefined", "com.kidscademy.AtlasService#flipPicture", "Picture argument is undefined.");

		var __callback__ = arguments[2];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#flipPicture", "Callback is not a function.");
		var __scope__ = arguments[3];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#flipPicture", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "flipPicture");
		rmi.setParameters(object, picture);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Crop picture.
	 *
	 * @param com.kidscademy.atlas.UIObject object,
	 * @param com.kidscademy.atlas.Picture picture,
	 * @param int width,
	 * @param int height,
	 * @param int xoffset,
	 * @param int yoffset,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return com.kidscademy.atlas.Picture
	 * @throws java.io.IOException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 cropPicture: function(object, picture, width, height, xoffset, yoffset) {
		$assert(typeof object !== "undefined", "com.kidscademy.AtlasService#cropPicture", "Object argument is undefined.");
		$assert(typeof picture !== "undefined", "com.kidscademy.AtlasService#cropPicture", "Picture argument is undefined.");
		$assert(typeof width !== "undefined", "com.kidscademy.AtlasService#cropPicture", "Width argument is undefined.");
		$assert(js.lang.Types.isNumber(width), "com.kidscademy.AtlasService#cropPicture", "Width argument is not a number.");
		$assert(typeof height !== "undefined", "com.kidscademy.AtlasService#cropPicture", "Height argument is undefined.");
		$assert(js.lang.Types.isNumber(height), "com.kidscademy.AtlasService#cropPicture", "Height argument is not a number.");
		$assert(typeof xoffset !== "undefined", "com.kidscademy.AtlasService#cropPicture", "Xoffset argument is undefined.");
		$assert(js.lang.Types.isNumber(xoffset), "com.kidscademy.AtlasService#cropPicture", "Xoffset argument is not a number.");
		$assert(typeof yoffset !== "undefined", "com.kidscademy.AtlasService#cropPicture", "Yoffset argument is undefined.");
		$assert(js.lang.Types.isNumber(yoffset), "com.kidscademy.AtlasService#cropPicture", "Yoffset argument is not a number.");

		var __callback__ = arguments[6];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#cropPicture", "Callback is not a function.");
		var __scope__ = arguments[7];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#cropPicture", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "cropPicture");
		rmi.setParameters(object, picture, width, height, xoffset, yoffset);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Remove picture.
	 *
	 * @param com.kidscademy.atlas.UIObject object,
	 * @param com.kidscademy.atlas.Picture picture,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return void
	 * @throws java.io.IOException
	 * @assert callback is a {@link Function} and scope is an {@link Object}, if they are defined.
	 * @note since method return type is void, callback, and hence scope too, is optional.
	 */
	 removePicture: function(object, picture) {
		$assert(typeof object !== "undefined", "com.kidscademy.AtlasService#removePicture", "Object argument is undefined.");
		$assert(typeof picture !== "undefined", "com.kidscademy.AtlasService#removePicture", "Picture argument is undefined.");

		var __callback__ = arguments[2];
		$assert(typeof __callback__ === "undefined" || js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#removePicture", "Callback is not a function.");
		var __scope__ = arguments[3];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#removePicture", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "removePicture");
		rmi.setParameters(object, picture);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Undo picture.
	 *
	 * @param com.kidscademy.atlas.UIObject object,
	 * @param com.kidscademy.atlas.Picture picture,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return com.kidscademy.atlas.Picture
	 * @throws java.io.IOException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 undoPicture: function(object, picture) {
		$assert(typeof object !== "undefined", "com.kidscademy.AtlasService#undoPicture", "Object argument is undefined.");
		$assert(typeof picture !== "undefined", "com.kidscademy.AtlasService#undoPicture", "Picture argument is undefined.");

		var __callback__ = arguments[2];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#undoPicture", "Callback is not a function.");
		var __scope__ = arguments[3];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#undoPicture", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "undoPicture");
		rmi.setParameters(object, picture);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Commit picture.
	 *
	 * @param com.kidscademy.atlas.UIObject object,
	 * @param com.kidscademy.atlas.Picture picture,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return com.kidscademy.atlas.Picture
	 * @throws java.io.IOException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 commitPicture: function(object, picture) {
		$assert(typeof object !== "undefined", "com.kidscademy.AtlasService#commitPicture", "Object argument is undefined.");
		$assert(typeof picture !== "undefined", "com.kidscademy.AtlasService#commitPicture", "Picture argument is undefined.");

		var __callback__ = arguments[2];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#commitPicture", "Callback is not a function.");
		var __scope__ = arguments[3];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#commitPicture", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "commitPicture");
		rmi.setParameters(object, picture);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Rollback picture.
	 *
	 * @param com.kidscademy.atlas.UIObject object,
	 * @param com.kidscademy.atlas.Picture picture,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return void
	 * @throws java.io.IOException
	 * @assert callback is a {@link Function} and scope is an {@link Object}, if they are defined.
	 * @note since method return type is void, callback, and hence scope too, is optional.
	 */
	 rollbackPicture: function(object, picture) {
		$assert(typeof object !== "undefined", "com.kidscademy.AtlasService#rollbackPicture", "Object argument is undefined.");
		$assert(typeof picture !== "undefined", "com.kidscademy.AtlasService#rollbackPicture", "Picture argument is undefined.");

		var __callback__ = arguments[2];
		$assert(typeof __callback__ === "undefined" || js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#rollbackPicture", "Callback is not a function.");
		var __scope__ = arguments[3];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#rollbackPicture", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "rollbackPicture");
		rmi.setParameters(object, picture);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Upload audio sample.
	 *
	 * @param js.http.form.Form form,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return com.kidscademy.tool.AudioSampleInfo
	 * @throws java.io.IOException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 uploadAudioSample: function(form) {
		$assert(typeof form !== "undefined", "com.kidscademy.AtlasService#uploadAudioSample", "Form argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#uploadAudioSample", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#uploadAudioSample", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "uploadAudioSample");
		rmi.setParameters(form);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Normalize audio sample.
	 *
	 * @param com.kidscademy.atlas.UIObject object,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return com.kidscademy.tool.AudioSampleInfo
	 * @throws java.io.IOException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 normalizeAudioSample: function(object) {
		$assert(typeof object !== "undefined", "com.kidscademy.AtlasService#normalizeAudioSample", "Object argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#normalizeAudioSample", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#normalizeAudioSample", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "normalizeAudioSample");
		rmi.setParameters(object);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Convert audio sample to mono.
	 *
	 * @param com.kidscademy.atlas.UIObject object,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return com.kidscademy.tool.AudioSampleInfo
	 * @throws java.io.IOException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 convertAudioSampleToMono: function(object) {
		$assert(typeof object !== "undefined", "com.kidscademy.AtlasService#convertAudioSampleToMono", "Object argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#convertAudioSampleToMono", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#convertAudioSampleToMono", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "convertAudioSampleToMono");
		rmi.setParameters(object);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Trim audio sample silence.
	 *
	 * @param com.kidscademy.atlas.UIObject object,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return com.kidscademy.tool.AudioSampleInfo
	 * @throws java.io.IOException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 trimAudioSampleSilence: function(object) {
		$assert(typeof object !== "undefined", "com.kidscademy.AtlasService#trimAudioSampleSilence", "Object argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#trimAudioSampleSilence", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#trimAudioSampleSilence", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "trimAudioSampleSilence");
		rmi.setParameters(object);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Cut audio sample.
	 *
	 * @param com.kidscademy.atlas.UIObject object,
	 * @param float start,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return com.kidscademy.tool.AudioSampleInfo
	 * @throws java.io.IOException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 cutAudioSample: function(object, start) {
		$assert(typeof object !== "undefined", "com.kidscademy.AtlasService#cutAudioSample", "Object argument is undefined.");
		$assert(typeof start !== "undefined", "com.kidscademy.AtlasService#cutAudioSample", "Start argument is undefined.");
		$assert(js.lang.Types.isNumber(start), "com.kidscademy.AtlasService#cutAudioSample", "Start argument is not a number.");

		var __callback__ = arguments[2];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#cutAudioSample", "Callback is not a function.");
		var __scope__ = arguments[3];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#cutAudioSample", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "cutAudioSample");
		rmi.setParameters(object, start);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Fade in audio sample.
	 *
	 * @param com.kidscademy.atlas.UIObject object,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return com.kidscademy.tool.AudioSampleInfo
	 * @throws java.io.IOException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 fadeInAudioSample: function(object) {
		$assert(typeof object !== "undefined", "com.kidscademy.AtlasService#fadeInAudioSample", "Object argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#fadeInAudioSample", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#fadeInAudioSample", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "fadeInAudioSample");
		rmi.setParameters(object);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Fade out audio sample.
	 *
	 * @param com.kidscademy.atlas.UIObject object,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return com.kidscademy.tool.AudioSampleInfo
	 * @throws java.io.IOException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 fadeOutAudioSample: function(object) {
		$assert(typeof object !== "undefined", "com.kidscademy.AtlasService#fadeOutAudioSample", "Object argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#fadeOutAudioSample", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#fadeOutAudioSample", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "fadeOutAudioSample");
		rmi.setParameters(object);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Generate waveform.
	 *
	 * @param com.kidscademy.atlas.UIObject object,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return com.kidscademy.atlas.MediaSRC
	 * @throws java.io.IOException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 generateWaveform: function(object) {
		$assert(typeof object !== "undefined", "com.kidscademy.AtlasService#generateWaveform", "Object argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#generateWaveform", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#generateWaveform", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "generateWaveform");
		rmi.setParameters(object);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Undo audio sample processing.
	 *
	 * @param com.kidscademy.atlas.UIObject object,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return com.kidscademy.tool.AudioSampleInfo
	 * @throws java.io.IOException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 undoAudioSampleProcessing: function(object) {
		$assert(typeof object !== "undefined", "com.kidscademy.AtlasService#undoAudioSampleProcessing", "Object argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#undoAudioSampleProcessing", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#undoAudioSampleProcessing", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "undoAudioSampleProcessing");
		rmi.setParameters(object);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Rollback audio sample processing.
	 *
	 * @param com.kidscademy.atlas.UIObject object,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return com.kidscademy.tool.AudioSampleInfo
	 * @throws java.io.IOException
	 * @assert callback is a {@link Function} and scope is an {@link Object}.
	 */
	 rollbackAudioSampleProcessing: function(object) {
		$assert(typeof object !== "undefined", "com.kidscademy.AtlasService#rollbackAudioSampleProcessing", "Object argument is undefined.");

		var __callback__ = arguments[1];
		$assert(js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#rollbackAudioSampleProcessing", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#rollbackAudioSampleProcessing", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "rollbackAudioSampleProcessing");
		rmi.setParameters(object);
		rmi.exec(__callback__, __scope__);
	},

	/**
	 * Remove audio sample.
	 *
	 * @param com.kidscademy.atlas.UIObject object,
	 * @param Function callback function to invoke on RMI completion,
	 * @param Object scope optional callback run-time scope, default to global scope.
	 * @return void
	 * @throws java.io.IOException
	 * @assert callback is a {@link Function} and scope is an {@link Object}, if they are defined.
	 * @note since method return type is void, callback, and hence scope too, is optional.
	 */
	 removeAudioSample: function(object) {
		$assert(typeof object !== "undefined", "com.kidscademy.AtlasService#removeAudioSample", "Object argument is undefined.");

		var __callback__ = arguments[1];
		$assert(typeof __callback__ === "undefined" || js.lang.Types.isFunction(__callback__), "com.kidscademy.AtlasService#removeAudioSample", "Callback is not a function.");
		var __scope__ = arguments[2];
		$assert(typeof __scope__ === "undefined" || js.lang.Types.isObject(__scope__), "com.kidscademy.AtlasService#removeAudioSample", "Scope is not an object.");
		if(!js.lang.Types.isObject(__scope__)) {
			__scope__ = window;
		}

		var rmi = new js.net.RMI();
		rmi.setMethod("com.kidscademy.AtlasService", "removeAudioSample");
		rmi.setParameters(object);
		rmi.exec(__callback__, __scope__);
	}
};

if(typeof AtlasService === 'undefined') {
	AtlasService = com.kidscademy.AtlasService;
}
