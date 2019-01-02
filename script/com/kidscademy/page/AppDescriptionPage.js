$package("com.kidscademy.page");

/**
 * AppDescriptionPage class.
 * 
 * @author Iulian Rotaru
 */
com.kidscademy.page.AppDescriptionPage = class extends com.kidscademy.page.Page {
	/**
	 * Construct an instance of AppDescriptionPage class.
	 */
	constructor() {
		super();

		this._APPS_MAP = {
			"animals-atlas.htm": "com.kidscademy.animals",
			"arduino-primer": "com.kidscademy.primer.arduino",
			"birds-atlas": "com.kidscademy.birds",
			"car-brands-quiz": "com.kidscademy.cars",
			"fables": "com.kidscademy.fables",
			"learn-colors": "com.kidscademy.colors",
			"musical-instruments-atlas": "com.kidscademy.instruments",
			"sound-quiz": "com.kidscademy.sounds"
		};

		const appFeedbackView = this.getByClass(com.kidscademy.AppFeedback);
		const appID = this._getAppID();
		if (appID !== null) {
			appFeedbackView.setAppID(appID);
		}
	}

	_getAppID() {
		const requestPath = WinMain.url.path;
		for (let p in this._APPS_MAP) {
			if (requestPath.endsWith(p)) {
				return this._APPS_MAP[p];
			}
		}
		$error("com.kidscademy.page.AppDescriptionPage#_getAppID", `Bug exception. Request URL |${requestPath}| not mapped to app.`);
		return null;
	}

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString() {
		return "com.kidscademy.page.AppDescriptionPage";
	}
};

WinMain.setPage(com.kidscademy.page.AppDescriptionPage);
