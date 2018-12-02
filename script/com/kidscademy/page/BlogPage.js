$package("com.kidscademy.page");

/**
 * BlogPage class.
 * 
 * @author Iulian Rotaru
 * @since 1.0
 * 
 * @constructor Construct an instance of BlogPage class.
 */
com.kidscademy.page.BlogPage = function() {
	this.$super();
};

com.kidscademy.page.BlogPage.prototype = {
	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString: function() {
		return "com.kidscademy.page.BlogPage";
	}
};
$extends(com.kidscademy.page.BlogPage, com.kidscademy.page.Page);
