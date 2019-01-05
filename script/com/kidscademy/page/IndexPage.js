$package("com.kidscademy.page");

/**
 * IndexPage class.
 * 
 * @author Iulian Rotaru
 */
com.kidscademy.page.IndexPage = class extends com.kidscademy.page.Page {
	/**
	 * Construct an instance of IndexPage class.
	 */
	constructor() {
		super();
		// $E("button.fx").on("click", this._onFX, this);
		// js.ua.System.alert("@string/message");
		// js.ua.System.alert("@image/kids-cademy-logo");

		//	js.ua.System.confirm("bla bla bla", function(ok) {
		//		$E("#sys-message-id").success("@string/lorem");
		//	});

		this.findByCss("#success, #info, #warning, #danger").on("click", (ev) => {
			this.getByCss("#sys-message-id")[ev.target.getAttr("id")]("@string/lorem");
		});
	}

	_onFX(ev) {
		this.getByCss("h2").addCssClass("go");
	}

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString() {
		return "com.kidscademy.page.IndexPage";
	}
};

WinMain.createPage(com.kidscademy.page.IndexPage);
