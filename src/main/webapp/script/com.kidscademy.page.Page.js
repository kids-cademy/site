$package("com.kidscademy.page");

/**
 * Page class.
 * 
 * @author Iulian Rotaru
 * @since 1.0
 * 
 * @constructor Construct an instance of Page class.
 */
com.kidscademy.page.Page = function() {
	this.$super();

	window.onscroll = function() {
		WinMain.doc.getByTag("body").addCssClass("scroll", window.pageYOffset > 40);
	}
};

com.kidscademy.page.Page.prototype = {
	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "com.kidscademy.page.Page";
	}
};
$extends(com.kidscademy.page.Page, js.ua.Page);
