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

	this._graphicAssets = this.getByClass(com.kidscademy.admin.GraphicAssets);
	this._audioAssets = this.getByClass(com.kidscademy.admin.AudioAssets);
	this._relatedControl = this._form.getByClass(com.kidscademy.admin.RelatedControl);
	this._linksControl = this._form.getByClass(com.kidscademy.admin.LinksControl);

	if (typeof WinMain.url.parameters.id != "undefined") {
		var id = Number(WinMain.url.parameters.id);
		AdminService.getInstrument(id, this._onInstrumentLoaded, this);
	}
	else {
		// for preview
		AdminService.getInstrumentByName("cajon", this._onInstrumentLoaded, this);
	}

	var buttonsBar = this.getByCssClass("buttons-bar");
	buttonsBar.on(this, {
		"&submit" : this._onSubmit,
		"&cancel" : this._onCancel
	});

	this._graphicAssets.onCreated(this);
	this._audioAssets.onCreated(this);
	this._relatedControl.onCreated(this);
	this._linksControl.onCreated(this);
};

com.kidscademy.admin.FormPage.prototype = {
	getObject : function() {
		this._form.getObject(this._instrument);
		return this._instrument;
	},

	_onInstrumentLoaded : function(instrument) {
		this._instrument = instrument;
		this._form.setObject(instrument);

		this._graphicAssets.onStart();
		this._audioAssets.onStart();
		this._relatedControl.onStart();
		this._linksControl.onStart();
	},

	_onSubmit : function() {
		if (this._form.isValid()) {
			AdminService.saveInstrument(this.getObject(), function(id) {
				this._instrument.id = id;
			}, this);
		}
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
