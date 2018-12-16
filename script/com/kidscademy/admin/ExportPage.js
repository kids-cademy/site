$package("com.kidscademy.admin");

$include("com.kidscademy.AdminService");

/**
 * ExportPage class.
 * 
 * @author Iulian Rotaru
 * @since 1.0
 * 
 * @constructor Construct an instance of ExportPage class.
 */
com.kidscademy.admin.ExportPage = function() {
	this.$super();
};

com.kidscademy.admin.ExportPage.prototype = {
	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "com.kidscademy.admin.ExportPage";
	}
};
$extends(com.kidscademy.admin.ExportPage, com.kidscademy.page.Page);
