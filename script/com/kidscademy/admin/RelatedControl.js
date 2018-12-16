$package("com.kidscademy.admin");

com.kidscademy.admin.RelatedControl = function(ownerDoc, node) {
	this.$super(ownerDoc, node);

	this._listView = this.getByCssClass("list-view");
	this._listView.on("click", this._onListViewClick, this);

	this._editor = this.getByCssClass("editor");

	var actions = this.getByCssClass("actions");
	actions.on(this, {
		"&add" : this._onAdd,
		"&browse" : this._onBrowse,
		"&close" : this._onClose
	});
};

com.kidscademy.admin.RelatedControl.prototype = {
	setValue : function(names) {
		this._names = names;
		com.kidscademy.AdminService.getRelatedInstruments(names, function(objects) {
			objects.forEach(function(object) {
				object.src = "/repository/" + object.iconPath;
			});
			this._listView.setObject(objects);
		}, this);
	},

	getValue : function() {
		return this._names;
	},

	_onAdd : function() {
		this.addCssClass("opened");
		this._editor.setObject({
			url : null,
			name : null,
			description : null
		});
	},

	_onListViewClick : function(ev) {
		var item = ev.target.getParentByTag("li");
		this.addCssClass("opened");
		this._editor.setObject(item.getUserData());
	},

	_onBrowse : function() {
		var urlInput = this.getByName("url");
		WinMain.open(urlInput.getValue());
	},

	_onClose : function() {
		this.removeCssClass("opened");
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "com.kidscademy.admin.RelatedControl";
	}
};
$extends(com.kidscademy.admin.RelatedControl, js.dom.Control);
$preload(com.kidscademy.admin.RelatedControl);
