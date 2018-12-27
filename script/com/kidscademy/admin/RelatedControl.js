$package("com.kidscademy.admin");

$include("com.kidscademy.AdminService");

com.kidscademy.admin.RelatedControl = function(ownerDoc, node) {
	this.$super(ownerDoc, node);

	this._relatedView = this.getByCss(".list-view.related");
	this._relatedView.on("dragstart", this._onDragStart, this);
	this._relatedView.on("dragover", this._onDragOver, this);
	this._relatedView.on("drop", this._onRelatedViewDrop, this);

	this._collectionView = this.getByCss(".list-view.collection");
	this._collectionView.on("dragstart", this._onDragStart, this);
	this._collectionView.on("dragover", this._onDragOver, this);
	this._collectionView.on("drop", this._onCollectionViewDrop, this);

	var actions = this.getByCssClass("actions");
	this._editAction = actions.getByName("edit");
	this._closeAction = actions.getByName("close");

	this._editAction.on("click", this._onEdit, this);
	this._closeAction.on("click", this._onClose, this);
	this._closeAction.hide();
};

com.kidscademy.admin.RelatedControl.prototype = {
	bindEvents : function(events) {
		events.addListener("object-update", function(object) {
			this._instrument = object;
		}, this);
	},

	setValue : function(names) {
		com.kidscademy.AdminService.getRelatedInstruments(names, function(objects) {
			objects.forEach(function(object) {
				object.src = "/repository/" + object.iconPath;
			});
			this._relatedView.setObject(objects);
		}, this);
	},

	getValue : function() {
		var names = [];
		this._relatedView.getChildren().forEach(function(instrumentView) {
			// template item has no id attribute
			if (instrumentView.getAttr("id") !== null) {
				names.push(instrumentView.getUserData("value").name);
			}
		}, this);
		return names;
	},

	isValid : function() {
		return true;
	},

	_onEdit : function() {
		this._closeAction.show();

		var instruments = [];
		this._relatedView.getChildren().forEach(function(view) {
			instruments.push({
				id : view.getAttr("id")
			});
		}, this);
		instruments.push({
			id : this._instrument.id
		});

		AdminService.getAvailableInstruments(this._instrument.category, instruments, function(collection) {
			collection.forEach(function(object) {
				object.src = "/repository/" + object.iconPath;
			});
			this._collectionView.show();
			this._collectionView.setObject(collection);
		}, this);
	},

	_onClose : function() {
		this._collectionView.hide();
		this._closeAction.hide();
	},

	_onDragStart : function(ev) {
		var li = ev.target.getParentByTag("li");
		ev.setData("index", li.getChildIndex());
	},

	_onDragOver : function(ev) {
		ev.prevent();
	},

	_onRelatedViewDrop : function(ev) {
		ev.prevent();
		this._relatedView.addChild(this._collectionView.getByIndex(ev.getData("index")));
	},

	_onCollectionViewDrop : function(ev) {
		ev.prevent();
		this._collectionView.addChild(this._relatedView.getByIndex(ev.getData("index")));
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
