$package("com.kidscademy.admin");

/**
 * CollectionPage class.
 * 
 * @author Iulian Rotaru
 */
com.kidscademy.admin.CollectionPage = class extends com.kidscademy.page.Page {
	/**
	 * Construct an instance of CollectionPage class.
	 */
	constructor() {
		super();

		this._listView = this.getByCssClass("list-view");
		this._listView.on("click", this._onListClick, this);

		const actions = this.getByCss(".side-menu .actions");
		actions.on(this, {
			"&new-object": this._onNewObject
		});

		AdminService.getInstruments(instruments => this._listView.setObject(instruments));
	}

	_onListClick(ev) {
		const li = ev.target.getParentByTag("li");
		WinMain.assign("form.htm", { id: li.getAttr("id") });
	}

	_onNewObject() {
		WinMain.assign("form.htm", { id: 0 });
	}

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString() {
		return "com.kidscademy.admin.CollectionPage";
	}
};

WinMain.createPage(com.kidscademy.admin.CollectionPage);
