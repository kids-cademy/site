$package("com.kidscademy.page");

/**
 * BlogPage class.
 * 
 * @author Iulian Rotaru
 */
com.kidscademy.page.BlogPage = class extends com.kidscademy.page.Page {
	/**
	 * Construct an instance of BlogPage class.
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
		return "com.kidscademy.page.BlogPage";
	}
};

WinMain.createPage(com.kidscademy.page.BlogPage);
