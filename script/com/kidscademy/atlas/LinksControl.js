$package("com.kidscademy.atlas");

com.kidscademy.atlas.LinksControl = class extends js.dom.Control {
	constructor(ownerDoc, node) {
		super(ownerDoc, node);

		/**
		 * Parent form page.
		 * @type {com.kidscademy.atlas.FormPage}
		 */
		this._formPage = null;

		/**
		 * Link objects collection.
		 * @type {Array}
		 */
		this._links = null;

		this._linksView = this.getByCssClass("list-view");
		this._linksView.on("click", this._onLinksViewClick, this);

		this._editor = this.getByCssClass("editor");
		this._urlInput = this.getByName("url");
		this._editIndex = -1;

		/**
		 * Actions manager.
		 * @type {com.kidscademy.Actions}
		 */
		this._actions = this.getByClass(com.kidscademy.Actions).bind(this, this._urlInput);

		this._showEditor(false);
	}

	onCreate(formPage) {
		this._formPage = formPage;
	}

	onStart() {
	}

	// --------------------------------------------------------------------------------------------
	// CONTROL INTERFACE
	
	setValue(links) {
		this._links = links;
		this._updateView();
		// ensure editor is closed
		this._showEditor(false);
	}

	getValue() {
		return typeof this._links === "undefined" ? [] : this._links;
	}

	isValid() {
		return true;
	}

	// --------------------------------------------------------------------------------------------
	// ACTION HANDLERS
	
	_onAdd() {
		this._editIndex = -1;
		this._showEditor(true);
		this._urlInput.reset();
	}

	_onBrowse() {
		if(this._urlInput.isValid()) {
			WinMain.open(this._urlInput.getValue());
		}
	}

	_onDone() {
		const url = this._urlInput.getValue();
		if (url == null) {
			return;
		}

		if (this._editIndex === -1) {
			// edit index is not set therefore we are in append mode
			AtlasService.createLink(url, link => {
				this._links.push(link);
				this._updateView();
			});
		}
		else {
			// edit index is set therefore we are in edit mode
			const editLink = this._links[this._editIndex];
			AtlasService.createLink(url, link => {
				editLink.url = link.url;
				editLink.name = link.name;
				editLink.iconPath = link.iconPath;
				this._updateView();
			});
		}

		this._showEditor(false);
	}

	_onRemove() {
		if(this._urlInput.isValid() && this._editIndex !== -1) {
			this._links.splice(this._editIndex, 1);
			this._updateView();
			this._showEditor(false);
		}
	}

	_onClose() {
		this._showEditor(false);
	}

	// --------------------------------------------------------------------------------------------
	
	_onLinksViewClick(ev) {
		const item = ev.target.getParentByTag("li");
		if (item != null) {
			this._editIndex = item.getChildIndex();
			this._showEditor(true);
			this._urlInput.setValue(item.getUserData().url);
		}
	}

	_updateView() {
		this._linksView.setObject(this._links);
	}

	_showEditor(show) {
		this._actions.show(show, "browse", "done", "remove", "close");
		this._editor.show(show);
		if (show) {
			this._urlInput.scrollIntoView();
			this._urlInput.focus();
		}
	}

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString() {
		return "com.kidscademy.atlas.LinksControl";
	}
};

$preload(com.kidscademy.atlas.LinksControl);
