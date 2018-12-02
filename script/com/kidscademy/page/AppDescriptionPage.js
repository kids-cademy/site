$package("com.kidscademy.page");

/**
 * AppDescriptionPage class.
 * 
 * @author Iulian Rotaru
 * @since 1.0
 * 
 * @constructor Construct an instance of AppDescriptionPage class.
 */
com.kidscademy.page.AppDescriptionPage = function() {
	this.$super();

	var appFeedbackView = this.getByClass(com.kidscademy.AppFeedback);
	var appID = this._getAppID();
	if (appID !== null) {
		appFeedbackView.setAppID(appID);
	}

};

com.kidscademy.page.AppDescriptionPage.prototype = {
	_APPS_MAP : {
		"animals-atlas.htm" : "com.kidscademy.animals",
		"arduino-primer" : "com.kidscademy.primer.arduino",
		"birds-atlas" : "com.kidscademy.birds",
		"car-brands-quiz" : "com.kidscademy.cars",
		"fables" : "com.kidscademy.fables",
		"learn-colors" : "com.kidscademy.colors",
		"musical-instruments-atlas" : "com.kidscademy.instruments",
		"sound-quiz" : "com.kidscademy.sounds"
	},

	_getAppID : function() {
		var requestPath = WinMain.url.path;
		for ( var p in this._APPS_MAP) {
			if (requestPath.endsWith(p)) {
				return this._APPS_MAP[p];
			}
		}
		$error("com.kidscademy.page.AppDescriptionPage#_getAppID", "Bug exception. Request URL |%s| not mapped to app.", requestPath);
		return null;
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "com.kidscademy.page.AppDescriptionPage";
	}
};
$extends(com.kidscademy.page.AppDescriptionPage, com.kidscademy.page.Page);
