$package("com.kidscademy.admin");

$include("com.kidscademy.AdminService");

com.kidscademy.admin.LinksControl = function(ownerDoc, node) {
	this.$super(ownerDoc, node);

	this._listView = this.getByCssClass("list-view");
	this._listView.on("click", this._onListViewClick, this);

	this._editor = this.getByCssClass("editor");
	this._editIndex = -1;

	this._editor.getObject = function(link) {
		link.url = this.getByName("url").getValue();
		link.name = this.getByName("name").getValue();
		link.description = this.getByName("description").getValue();
		return link;
	};

	var actions = this.getByCssClass("actions");
	this._addAction = actions.getByName("add");
	this._browseAction = actions.getByName("browse");
	this._saveAction = actions.getByName("save");
	this._removeAction = actions.getByName("remove");
	this._closeAction = actions.getByName("close");

	this._addAction.on("click", this._onAdd, this);
	this._browseAction.on("click", this._onBrowse, this);
	this._saveAction.on("click", this._onSave, this);
	this._removeAction.on("click", this._onRemove, this);
	this._closeAction.on("click", this._onClose, this);

	this._showEditor(false);
};

com.kidscademy.admin.LinksControl.prototype = {
	bindEvents : function(events) {
		events.addListener("object-update", function(object) {
			this._parentObjectId = object.id;
		}, this);
	},

	setValue : function(links) {
		this._links = links;
		this._updateView();
		// ensure editor is closed
		this._showEditor(false);
	},

	getValue : function() {
		return typeof this._links === "undefined" ? [] : this._links;
	},

	isValid : function() {
		return true;
	},

	_onAdd : function() {
		this._editIndex = -1;
		this._showEditor(true);

		this._selectedLink = {
			url : null,
			name : null,
			description : null
		};
		this._editor.setObject(this._selectedLink);
	},

	_onListViewClick : function(ev) {
		var item = ev.target.getParentByTag("li");
		this._editIndex = item.getChildIndex();
		this._showEditor(true);

		this._selectedLink = item.getUserData();
		this._editor.setObject(this._selectedLink);
	},

	_onBrowse : function() {
		var urlInput = this.getByName("url");
		WinMain.open(urlInput.getValue());
	},

	_onSave : function() {
		function basedomain(url) {
			var matches = /http[s]?\:\/\/(?:[^.]+\.)*([^.]+)\.[^/]+.*/g.exec(url);
			return matches[1];
		}

		var link = this._editor.getObject(this._selectedLink);
		link.iconPath = "links/" + basedomain(link.url) + ".png";

		if (this._editIndex === -1) {
			// edit index is not set therefore we are in append mode
			link.id = 0;
			link.objectId = this._parentObjectId;
			this._links.push(link);
		}
		else {
			// edit index is set therefore we are in edit mode
			this._links[this._editIndex] = link;
		}

		this._updateView();
		this._showEditor(false);
	},

	_onRemove : function() {
		this._links.splice(this._editIndex, 1);
		this._updateView();
		this._showEditor(false);
	},

	_onClose : function() {
		this._showEditor(false);
	},

	_updateView : function() {
		this._links.forEach(function(link) {
			link.src = "/repository/" + link.iconPath;
		});
		this._listView.setObject(this._links);
	},

	_showEditor : function(show) {
		this._browseAction.show(show);
		this._saveAction.show(show);
		this._removeAction.show(show);
		this._closeAction.show(show);
		this._editor.show(show);
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
