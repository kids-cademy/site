$package("com.kidscademy.page");

/**
 * FormPage class.
 * 
 * @author Iulian Rotaru
 * @since 1.0
 * 
 * @constructor Construct an instance of FormPage class.
 */
com.kidscademy.page.FormPage = function() {
	this.$super();

	this._form = this.getByTag("form");

	com.kidscademy.AtlasController.getInstrument(1, function(instrument) {
		this._form.setObject(instrument);
	}, this);
};

com.kidscademy.page.FormPage.prototype = {
	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "com.kidscademy.page.FormPage";
	}
};
$extends(com.kidscademy.page.FormPage, com.kidscademy.page.Page);
