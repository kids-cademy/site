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
		this._listView.on("click", this._onClick, this);

		const actionsBar = this.getByCssClass("actions-bar");
		actionsBar.on(this, {
			"&new": this._onNew
		});
		AdminService.getInstruments((instruments) => {
			instruments.forEach((instrument) => instrument.src = "/repository/" + instrument.iconPath);
			this._listView.setObject(instruments);
		});
	}

	_onClick(ev) {
		const li = ev.target.getParentByTag("li");
		WinMain.assign("form.htm", {
			id: li.getAttr("id")
		});
	}

	_onNew() {
		WinMain.assign("form.htm", {
			id: 0
		});
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

WinMain.setPage(com.kidscademy.admin.SearchPage);
