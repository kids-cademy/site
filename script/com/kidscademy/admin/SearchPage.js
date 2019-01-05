$package("com.kidscademy.admin");

/**
 * SearchPage class.
 * 
 * @author Iulian Rotaru
 */
com.kidscademy.admin.SearchPage = class extends com.kidscademy.page.Page {
	/**
	 * Construct an instance of SearchPage class.
	 */
	constructor() {
		super();

		this._listView = this.getByCssClass("list-view");
		this._listView.on("click", this._onListClick, this);

		const actions = this.getByCss(".side-menu .actions");
		actions.on(this, {
			"&new-object": this._onNewObject
		});

		AdminService.getInstruments(instruments => {
			instruments.forEach(instrument => instrument.src = "/repository/" + instrument.iconPath);
			this._listView.setObject(instruments);
		});
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
		return "com.kidscademy.admin.SearchPage";
	}
};

WinMain.createPage(com.kidscademy.admin.SearchPage);
