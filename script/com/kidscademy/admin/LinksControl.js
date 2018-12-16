$package("com.kidscademy.admin");

$include("com.kidscademy.AdminService");

com.kidscademy.admin.LinksControl = function(ownerDoc, node) {
	this.$super(ownerDoc, node);

	this._listView = this.getByCssClass("list-view");
	this._listView.on("click", this._onListViewClick, this);

	this._editor = this.getByCssClass("editor");

	this._editor.getObject = function(link) {
		link.url = this.getByName("url").getValue();
		link.name = this.getByName("name").getValue();
		link.description = this.getByName("description").getValue();
		return link;
	};

	var actions = this.getByCssClass("actions");
	actions.on(this, {
		"&add" : this._onAdd,
		"&browse" : this._onBrowse,
		"&save" : this._onSave,
		"&remove" : this._onRemove,
		"&close" : this._onClose
	});
};

com.kidscademy.admin.LinksControl.prototype = {
	setValue : function(links) {
		this._links = links;
		links.forEach(function(link) {
			link.src = "/repository/" + link.iconPath;
		});
		this._listView.setObject(links);

		// ensure editor is closed
		this._onClose();
	},

	getValue : function() {
		return this._links;
	},

	setParentObject : function(parentObjectId) {
		this._parentObjectId = parentObjectId;
	},

	_onAdd : function() {
		this.addCssClass("opened");
		this._selectedLink = {
			url : null,
			name : null,
			description : null
		};
		this._createLink = true;
		this._editor.setObject(this._selectedLink);
	},

	_onListViewClick : function(ev) {
		var item = ev.target.getParentByTag("li");
		this.addCssClass("opened");
		this._selectedLink = item.getUserData();
		this._editor.setObject(this._selectedLink);
	},

	_onBrowse : function() {
		var urlInput = this.getByName("url");
		WinMain.open(urlInput.getValue());
	},

	_onSave : function() {
		if (this._createLink) {
			AdminService.createLink(this._parentObjectId, this._editor.getObject(this._selectedLink), function(links) {
				this.setValue(links);
			}, this);
		}
		else {
			AdminService.updateLink(this._editor.getObject(this._selectedLink), function(links) {
				this.setValue(links);
			}, this);
		}
		this._createLink = false;
	},

	_onRemove : function() {
		AdminService.removeLink(this._editor.getObject(this._selectedLink), function(links) {
			this.setValue(links);
		}, this);
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
		return "com.kidscademy.admin.LinksControl";
	}
};
$extends(com.kidscademy.admin.LinksControl, js.dom.Control);
$preload(com.kidscademy.admin.LinksControl);
