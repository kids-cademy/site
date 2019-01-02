$package("com.kidscademy.admin");

/**
 * FormPage class.
 * 
 * @author Iulian Rotaru
 */
com.kidscademy.admin.FormPage = class extends com.kidscademy.page.Page {
	/**
	 * Construct an instance of FormPage class.
	 */
	constructor() {
		super();

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

		const actions = this.getByCssClass("buttons-bar");
		actions.on(this, {
			"&submit": this._onSubmit,
			"&cancel": this._onCancel
		});

		this._graphicAssets.onCreated(this);
		this._audioAssets.onCreated(this);
		this._relatedControl.onCreated(this);
		this._linksControl.onCreated(this);
	}

	getObject() {
		this._form.getObject(this._instrument);
		return this._instrument;
	}

	_onInstrumentLoaded(instrument) {
		this._instrument = instrument;
		this._form.setObject(instrument);

		this._graphicAssets.onStart();
		this._audioAssets.onStart();
		this._relatedControl.onStart();
		this._linksControl.onStart();
	}

	_onSubmit() {
		if (this._form.isValid()) {
			AdminService.saveInstrument(this.getObject(), (id) => this._instrument.id = id);
		}
	}

	_onCancel() {

	}

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString() {
		return "com.kidscademy.admin.FormPage";
	}
};

WinMain.setPage(com.kidscademy.admin.FormPage);
