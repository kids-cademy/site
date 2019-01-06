$package("com.kidscademy.admin");

/**
 * AccountPage class.
 * 
 * @author Iulian Rotaru
 */
com.kidscademy.admin.AccountPage = class extends com.kidscademy.page.Page {
	/**
	 * Construct an instance of AccountPage class.
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
		return "com.kidscademy.admin.AccountPage";
	}
};

WinMain.createPage(com.kidscademy.admin.AccountPage);
