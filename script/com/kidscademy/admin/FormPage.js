$package("com.kidscademy.admin");

$include("com.kidscademy.AdminService");

/**
 * FormPage class.
 * 
 * @author Iulian Rotaru
 * @since 1.0
 * 
 * @constructor Construct an instance of FormPage class.
 */
com.kidscademy.admin.FormPage = function() {
	this.$super();

	this._form = this.getByTag("form");
	this._linksControl = this._form.getByName("links");

	var id = Number(WinMain.url.parameters.id);
	if (id) {
		AdminService.getInstrument(id, this._onInstrumentLoaded, this);
	}
	else {
		AdminService.getInstrumentByName("castanets", this._onInstrumentLoaded, this);
	}

	var buttonsBar = this.getByCssClass("buttons-bar");
	buttonsBar.on(this, {
		"&submit" : this._onSubmit,
		"&cancel" : this._onCancel
	});
};

com.kidscademy.admin.FormPage.prototype = {
	_onInstrumentLoaded : function(instrument) {
		this._instrument = instrument;
		this._form.setObject(instrument);
		this._linksControl.setParentObject(instrument.id);
	},

	_onSubmit : function() {
		var instrument = this._form.getObject(this._instrument);
		AdminService.saveInstrument(instrument);
	},

	_onCancel : function() {

	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "com.kidscademy.admin.FormPage";
	}
};
$extends(com.kidscademy.admin.FormPage, com.kidscademy.page.Page);
