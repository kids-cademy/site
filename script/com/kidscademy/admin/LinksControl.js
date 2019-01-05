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
		this._editIndex = -1;

		this._editor.getObject = function (link) {
			link.url = this.getByName("url").getValue();
			link.name = this.getByName("name").getValue();
			link.description = this.getByName("description").getValue();
			return link;
		};

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

		this._selectedLink = {
			url: null,
			name: null,
			description: null
		};
		this._editor.setObject(this._selectedLink);
	}

	_onListViewClick(ev) {
		const item = ev.target.getParentByTag("li");
		this._editIndex = item.getChildIndex();
		this._showEditor(true);

		this._selectedLink = item.getUserData();
		this._editor.setObject(this._selectedLink);
	}

	_onBrowse() {
		const urlInput = this.getByName("url");
		WinMain.open(urlInput.getValue());
	}

	_onDone() {
		function basedomain(url) {
			var matches = /http[s]?\:\/\/(?:[^.]+\.)*([^.]+)\.[^/]+.*/g.exec(url);
			return matches[1];
		}

		const link = this._editor.getObject(this._selectedLink);
		link.iconPath = `links/${basedomain(link.url)}.png`;

		if (this._editIndex === -1) {
			// edit index is not set therefore we are in append mode
			link.id = 0;
			link.objectId = this._formPage.getObject().id;
			this._links.push(link);
		}
		else {
			// edit index is set therefore we are in edit mode
			this._links[this._editIndex] = link;
		}

		this._updateView();
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
		this._links.forEach(function (link) {
			link.src = "/repository/" + link.iconPath;
		});
		this._listView.setObject(this._links);
	}

	_showEditor(show) {
		this._browseAction.show(show);
		this._doneAction.show(show);
		this._removeAction.show(show);
		this._closeAction.show(show);
		this._editor.show(show);
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
