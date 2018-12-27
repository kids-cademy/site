$package("com.kidscademy.admin");

com.kidscademy.admin.SpreadingControl = function(ownerDoc, node) {
	this.$super(ownerDoc, node);

	this._regions = [];

	this._regionsView = this.getByCssClass("regions-view");
	this._regionsView.on("click", this._onRegionsViewClick, this);

	this._editor = this.getByCssClass("editor");
	this._regionNameInput = this._editor.getByName("region-name");
	this._areaSelect = this._editor.getByName("area");
	this._editingRegionIndex = -1;

	var actions = this.getByCssClass("actions");
	this._addAction = actions.getByName("add");
	this._importAction = actions.getByName("import");
	this._saveAction = actions.getByName("save");
	this._removeAction = actions.getByName("remove");
	this._closeAction = actions.getByName("close");

	this._addAction.on("click", this._onAdd, this);
	this._saveAction.on("click", this._onSave, this);
	this._removeAction.on("click", this._onRemove, this);
	this._closeAction.on("click", this._onClose, this);

	this._showEditor(false);
};

com.kidscademy.admin.SpreadingControl.prototype = {
	AREA_DISPLAY : {
		"WHOLE" : "",
		"CENTRAL" : "Central",
		"NORTH" : "North",
		"NORTH_EAST" : "North-East",
		"EAST" : "East",
		"SOUTH_EAST" : "South-East",
		"SOUTH" : "South",
		"SOUTH_WEST" : "South-West",
		"WEST" : "West",
		"NORTH_WEST" : "North-West"
	},

	setValue : function(regions) {
		this._regions = regions;
		this._updateRegionsView();
	},

	getValue : function() {
		return this._regions;
	},

	isValid : function() {
		return true;
	},

	_onAdd : function(ev) {
		this._editingRegionIndex = -1;
		this._showEditor(true);
		this._regionNameInput.reset();
		this._areaSelect.reset();
	},

	_onSave : function(ev) {
		if (this._editingRegionIndex === -1) {
			// not editing mode, that is, add a new region
			this._regions.push({
				id : 0,
				name : this._regionNameInput.getValue(),
				area : this._areaSelect.getValue()
			});
		}
		else {
			// editing mode; this._editingRegionIndex points to item that is editing
			this._regions[this._editingRegionIndex].name = this._regionNameInput.getValue();
			this._regions[this._editingRegionIndex].area = this._areaSelect.getValue();
		}

		this._updateRegionsView();
		this._showEditor(false);
	},

	_onRemove : function(ev) {
		this._regions.splice(this._editingRegionIndex, 1);
		this._updateRegionsView();
		this._showEditor(false);
	},

	_onClose : function(ev) {
		this._showEditor(false);
	},

	_onRegionsViewClick : function(ev) {
		var li = ev.target.getParentByTag("li");
		if (li) {
			this._editingRegionIndex = li.getChildIndex();
			this._showEditor(true);

			var region = this._regions[this._editingRegionIndex];
			this._regionNameInput.setValue(region.name);
			this._areaSelect.setValue(region.area);
		}
	},

	_updateRegionsView : function() {
		this._regionsView.setObject(this._regions);
	},

	_showEditor : function(show) {
		this._saveAction.show(show);
		this._removeAction.show(show);
		this._closeAction.show(show);
		this._editor.show(show);

		if (show) {
			this._regionNameInput.focus();
		}
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "com.kidscademy.admin.SpreadingControl";
	}
};
$extends(com.kidscademy.admin.SpreadingControl, js.dom.Control);
$preload(com.kidscademy.admin.SpreadingControl);
