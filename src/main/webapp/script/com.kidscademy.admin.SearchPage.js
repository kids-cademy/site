$package("com.kidscademy.admin");

$include("com.kidscademy.AdminService");

/**
 * SearchPage class.
 * 
 * @author Iulian Rotaru
 * @since 1.0
 * 
 * @constructor Construct an instance of SearchPage class.
 */
com.kidscademy.admin.SearchPage = function() {
	this.$super();

	this._listView = this.getByCssClass("list-view");
	this._listView.on("click", this._onClick, this);

	AdminService.getInstruments(function(instruments) {
		instruments.forEach(function(instrument) {
			instrument.src = "/repository/" + instrument.iconPath;
		});
		this._listView.setObject(instruments);
	}, this);
};

com.kidscademy.admin.SearchPage.prototype = {
	_onClick : function(ev) {
		var li = ev.target.getParentByTag("li");
		WinMain.assign("form.htm", {
			id : li.getAttr("id")
		});
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "com.kidscademy.admin.SearchPage";
	}
};
$extends(com.kidscademy.admin.SearchPage, com.kidscademy.page.Page);
