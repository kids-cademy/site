$package("com.kidscademy.admin");

com.kidscademy.admin.FactsControl = function(ownerDoc, node) {
	this.$super(ownerDoc, node);

	this._facts = null;

	this._factsView = this.getByCssClass("facts-view");
	this._factsView.on("click", this._onFactsClick, this);

	this._editor = this.getByCssClass("editor");
	this._termInput = this._editor.getByName("term");
	this._definitionInput = this._editor.getByName("definition");
	this._termOnEdit = null;

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

com.kidscademy.admin.FactsControl.prototype = {
	setValue : function(facts) {
		function empty(object) {
			for ( var property in object) {
				if (object.hasOwnProperty(property)) {
					return false;
				}
			}
			return true;
		}

		this._facts = facts;
		if (!empty(facts)) {
			this._factsView.setObject(facts);
		}
	},

	getValue : function() {
		return this._facts;
	},

	_onAdd : function(ev) {
		this._showEditor(true);
		this._termInput.reset();
		this._definitionInput.reset();
	},

	_onSave : function(ev) {
		if (this._termOnEdit) {
			delete this._facts[this._termOnEdit];
			this._termOnEdit = null;
		}
		this._facts[this._termInput.getValue()] = this._definitionInput.getValue();

		this._factsView.setObject(this._facts);
		this._showEditor(false);
	},

	_onRemove : function(ev) {
		delete this._facts[this._termInput.getValue()];
		this._termOnEdit = null;
		this._factsView.setObject(this._facts);
		this._showEditor(false);
	},

	_onClose : function(ev) {
		this._showEditor(false);
	},

	_onFactsClick : function(ev) {
		if (ev.target.getTag() === "dt") {
			this._showEditor(true);
			this._termOnEdit = ev.target.getText();
			this._termInput.setValue(ev.target.getText());
			this._definitionInput.setValue(ev.target.getNextSibling().getText());
		}
	},

	_showEditor : function(show) {
		this._saveAction.show(show);
		this._removeAction.show(show);
		this._closeAction.show(show);
		this._editor.show(show);

		if (show) {
			this._termInput.focus();
		}
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "com.kidscademy.admin.FactsControl";
	}
};
$extends(com.kidscademy.admin.FactsControl, js.dom.Control);
$preload(com.kidscademy.admin.FactsControl);
