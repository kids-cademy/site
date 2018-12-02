$package("com.kidscademy.page");

/**
 * IndexPage class.
 * 
 * @author Iulian Rotaru
 * @since 1.0
 * 
 * @constructor Construct an instance of IndexPage class.
 */
com.kidscademy.page.IndexPage = function() {
	this.$super();
	// $E("button.fx").on("click", this._onFX, this);
	//js.ua.System.alert("@string/message");
	// js.ua.System.alert("@image/kids-cademy-logo");

//	js.ua.System.confirm("bla bla bla", function(ok) {
//		$E("#sys-message-id").success("@string/lorem");
//	});
	
	this.findByCss("#success, #info, #warning, #danger").on("click", function(ev) {
		this.getByCss("#sys-message-id")[ev.target.getAttr("id")]("@string/lorem");
	}, this);
};

com.kidscademy.page.IndexPage.prototype = {
	_onFX : function(ev) {
		this.getByCss("h2").addCssClass("go");
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "com.kidscademy.page.IndexPage";
	}
};
$extends(com.kidscademy.page.IndexPage, com.kidscademy.page.Page);
