$package("com.kidscademy.page");

/**
 * Page class.
 * 
 * @author Iulian Rotaru
 */
com.kidscademy.page.Page = class extends js.ua.Page {
    /**
     * Construct an instance of Page class.
     */
    constructor() {
    	super();

    	window.onscroll = function() {
    		WinMain.doc.getByTag("body").addCssClass("scroll", window.pageYOffset > 40);
    	}
    }

    /**
     * Class string representation.
     * 
     * @return this class string representation.
     */
    toString() {
    	return "com.kidscademy.page.Page";
    }
};
