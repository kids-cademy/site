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

	this._linksControl = this._form.getByClass(com.kidscademy.admin.LinksControl);
	this._relatedControl = this._form.getByClass(com.kidscademy.admin.RelatedControl);
	this._graphicAssets = this.getByClass(com.kidscademy.admin.GraphicAssets);
	this._audioAssets = this.getByClass(com.kidscademy.admin.AudioAssets);

	this._form.getByName("name").on("change", this._onObjectNameChange, this);

	if (!WinMain.url.parameters["new"]) {
		var id = Number(WinMain.url.parameters.id);
		if (id) {
			AdminService.getInstrument(id, this._onInstrumentLoaded, this);
		}
		else {
			AdminService.getInstrumentByName("castanets", this._onInstrumentLoaded, this);
		}
	}
	else {
		this._instrument = {
			id : 0
		};
	}

	var buttonsBar = this.getByCssClass("buttons-bar");
	buttonsBar.on(this, {
		"&submit" : this._onSubmit,
		"&cancel" : this._onCancel
	});

	this._events = new js.event.CustomEvents(this);
	this._events.register("object-update", "object-name-change");

	this._linksControl.bindEvents(this._events);
	this._relatedControl.bindEvents(this._events);
	this._graphicAssets.bindEvents(this._events);
	this._audioAssets.bindEvents(this._events);
};

com.kidscademy.admin.FormPage.prototype = {
	_onInstrumentLoaded : function(instrument) {
		this._instrument = instrument;
		this._form.setObject(instrument);

		this._events.fire("object-update", instrument);
		this._events.fire("object-name-change", instrument.name);
	},

	_onSubmit : function() {
		if (this._form.isValid()) {
			var instrument = this._form.getObject(this._instrument);
			AdminService.saveInstrument(instrument, function(id) {
				this._instrument.id = id;
				this._onInstrumentLoaded(this._instrument);
			}, this);
		}
	},

	_onCancel : function() {

	},

	_onObjectNameChange : function(ev) {
		this._events.fire("object-name-change", ev.target.getValue());
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
