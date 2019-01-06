$package("com.kidscademy.admin");

com.kidscademy.admin.LinksControl = class extends js.dom.Control {
	constructor(ownerDoc, node) {
		super(ownerDoc, node);

		/**
		 * Parent form page.
		 * 
		 * @type com.kidscademy.admin.FormPage
		 */
		this._formPage = null;

		/**
		 * Links collection.
		 * 
		 * @type Array
		 */
		this._links = null;

		this._listView = this.getByCssClass("list-view");
		this._listView.on("click", this._onListViewClick, this);

		this._editor = this.getByCssClass("editor");
		this._urlInput = this.getByName("url");
		this._urlInput.on("keypress", this._onUrlInputKey, this);
		this._editIndex = -1;


		const actions = this.getByCssClass("actions");
		this._addAction = actions.getByName("add");
		this._browseAction = actions.getByName("browse");
		this._doneAction = actions.getByName("done");
		this._removeAction = actions.getByName("remove");
		this._closeAction = actions.getByName("close");

		this._addAction.on("click", this._onAdd, this);
		this._browseAction.on("click", this._onBrowse, this);
		this._doneAction.on("click", this._onDone, this);
		this._removeAction.on("click", this._onRemove, this);
		this._closeAction.on("click", this._onClose, this);

		this._showEditor(false);
	}

	onCreate(formPage) {
		this._formPage = formPage;
	}

	onStart() {
	}

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

	_onAdd() {
		this._editIndex = -1;
		this._showEditor(true);
		this._urlInput.reset();
	}

	_onListViewClick(ev) {
		const item = ev.target.getParentByTag("li");
		this._editIndex = item.getChildIndex();
		this._showEditor(true);
		this._urlInput.setValue(item.getUserData().url);
	}

	_onBrowse() {
		WinMain.open(this._urlInput.getValue());
	}

	_onDone() {
		const url = this._urlInput.getValue();
		if (url == null) {
			return;
		}

		if (this._editIndex === -1) {
			// edit index is not set therefore we are in append mode
			AdminService.createLink(url, link => {
				this._links.push(link);
				this._updateView();
			});
		}
		else {
			// edit index is set therefore we are in edit mode
			const editLink = this._links[this._editIndex];
			AdminService.createLink(url, link => {
				editLink.url = link.url;
				editLink.name = link.name;
				editLink.iconPath = link.iconPath;
				this._updateView();
			});
		}

		this._showEditor(false);
	}

	_onRemove() {
		this._links.splice(this._editIndex, 1);
		this._updateView();
		this._showEditor(false);
	}

	_onClose() {
		this._showEditor(false);
	}

	_updateView() {
		this._listView.setObject(this._links);
	}

	_showEditor(show) {
		this._browseAction.show(show);
		this._doneAction.show(show);
		this._removeAction.show(show);
		this._closeAction.show(show);
		this._editor.show(show);

		if (show) {
			this._urlInput.scrollIntoView();
			this._urlInput.focus();
		}
	}

	_onUrlInputKey(ev) {
		switch (ev.key) {
			case js.event.Key.ENTER:
				this._onDone();
				break;

			case js.event.Key.ESCAPE:
				this._onClose();
				break;

			case js.event.Key.DELETE:
				this._onRemove();
				break;
		}
	}

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString() {
		return "com.kidscademy.admin.LinksControl";
	}
};

$preload(com.kidscademy.admin.LinksControl);
