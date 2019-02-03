$package("com.kidscademy.atlas");

com.kidscademy.atlas.FactsControl = class extends js.dom.Control {
	constructor(ownerDoc, node) {
		super(ownerDoc, node);

		/**
		 * Facts dictionary.
		 * 
		 * @type {Object}
		 */
		this._facts = null;

		this._factsView = this.getByCssClass("facts-view");
		this._factsView.on("click", this._onFactsClick, this);

		this._editor = this.getByCssClass("editor");
		this._termInput = this._editor.getByName("term");
		this._definitionInput = this._editor.getByName("definition");
		this._termOnEdit = null;

		this._actions = this.getByClass(com.kidscademy.Actions).bind(this);

		this._showEditor(false);
	}

	// --------------------------------------------------------------------------------------------
	// CONTROL INTERFACE

	setValue(facts) {
		function empty(object) {
			for (let property in object) {
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
	}

	getValue() {
		return this._facts;
	}

	isValid() {
		return true;
	}

	// --------------------------------------------------------------------------------------------
	// ACTION HANDLERS

	_onAdd(ev) {
		this._showEditor(true);
		this._termInput.reset();
		this._definitionInput.reset();
	}

	_onImport() {
		js.ua.System.alert("Import not yet implemented.");
	}
	
	_onDone(ev) {
		if (this._termOnEdit) {
			delete this._facts[this._termOnEdit];
			this._termOnEdit = null;
		}
		this._facts[this._termInput.getValue()] = this._definitionInput.getValue();

		this._factsView.setObject(this._facts);
		this._showEditor(false);
	}

	_onRemove(ev) {
		delete this._facts[this._termInput.getValue()];
		this._termOnEdit = null;
		this._factsView.setObject(this._facts);
		this._showEditor(false);
	}

	_onClose(ev) {
		this._showEditor(false);
	}

	// --------------------------------------------------------------------------------------------

	_onFactsClick(ev) {
		if (ev.target.getTag() === "dt") {
			this._showEditor(true);
			this._termOnEdit = ev.target.getText();
			this._termInput.setValue(ev.target.getText());
			this._definitionInput.setValue(ev.target.getNextSibling().getText());
		}
	}

	_showEditor(show) {
		this._actions.show(show, "done", "remove", "close");
		this._editor.show(show);
		if (show) {
			this._termInput.focus();
		}
	}

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString() {
		return "com.kidscademy.atlas.FactsControl";
	}
};

$preload(com.kidscademy.atlas.FactsControl);
