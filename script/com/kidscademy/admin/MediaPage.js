$package("com.kidscademy.admin");

/**
 * MediaPage class.
 * 
 * @author Iulian Rotaru
 */
com.kidscademy.admin.MediaPage = class extends com.kidscademy.page.Page {
	/**
	 * Construct an instance of MediaPage class.
	 */
	constructor() {
		super();
	}

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString() {
		return "com.kidscademy.admin.MediaPage";
	}
};

WinMain.createPage(com.kidscademy.admin.MediaPage);
