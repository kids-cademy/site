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

		this.CSS_INVALID = js.dom.Control.prototype.CSS_INVALID;

		this._form = this.getByClass(com.kidscademy.Form);

		this._graphicAssets = this.getByClass(com.kidscademy.admin.GraphicAssets);
		this._audioAssets = this.getByClass(com.kidscademy.admin.AudioAssets);
		this._relatedControl = this._form.getByClass(com.kidscademy.admin.RelatedControl);
		this._linksControl = this._form.getByClass(com.kidscademy.admin.LinksControl);

		const actions = this.getByCssClass("buttons-bar");
		actions.on(this, {
			"&save": this._onSave,
			"&reset": this._onReset,
			"&cancel": this._onCancel
		});

		this._graphicAssets.onCreate(this);
		this._audioAssets.onCreate(this);
		this._relatedControl.onCreate(this);
		this._linksControl.onCreate(this);

		const quickLinks = this.getByCssClass("quick-links");
		quickLinks.on("click", this._onQuickLinks, this);

		this._loadObject();
	}

	getObject() {
		this._form.getObject(this._object);
		return this._object;
	}

	_loadObject() {
		if (typeof WinMain.url.parameters.id != "undefined") {
			var id = Number(WinMain.url.parameters.id);
			AdminService.getInstrument(id, this._onObjectLoaded, this);
		}
		else {
			// for development preview
			AdminService.getInstrumentByName("cajon", this._onObjectLoaded, this);
		}
	}

	_onObjectLoaded(object) {
		this.findByCss(".quick-links li").removeCssClass(this.CSS_INVALID);
		this._form.reset();

		this._object = object;
		this._form.setObject(object);

		this._graphicAssets.onStart();
		this._audioAssets.onStart();
		this._relatedControl.onStart();
		this._linksControl.onStart();
	}

	_onSave() {
		this.findByCss(".quick-links li").removeCssClass(this.CSS_INVALID);
		const updateQuickLink = control => {
			const fieldset = control.getParentByTag("fieldset");
			// by convention quick link class is the fieldset ID
			this.getByCss(`.quick-links [data-name=${fieldset.getAttr("id")}]`).addCssClass(this.CSS_INVALID);
		};

		if (this._form.isValid(updateQuickLink)) {
			AdminService.saveInstrument(this.getObject(), id => this._object.id = id);
		}
	}

	_onReset() {
		this._loadObject();
	}

	_onCancel() {
		WinMain.back();
	}

	_onQuickLinks(ev) {
		const quickLink = ev.target.getParentByTag("li");
		if (quickLink != null) {
			// by convention quick link name is fieldset ID
			this.getById(quickLink.getAttr("data-name")).scrollIntoView();
		}
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

WinMain.createPage(com.kidscademy.admin.FormPage);
