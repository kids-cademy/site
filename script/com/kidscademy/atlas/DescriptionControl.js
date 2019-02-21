$package("com.kidscademy.atlas");

com.kidscademy.atlas.DescriptionControl = class extends js.dom.Control {
	constructor(ownerDoc, node) {
		super(ownerDoc, node);

		/**
		 * Parent form page.
		 * @type {com.kidscademy.atlas.FormPage}
		 */
		this._formPage = null;

		this._textarea = this.getByTag("textarea");
		
		/**
		 * Actions manager.
		 * @type {com.kidscademy.Actions}
		 */
		this._actions = this.getByClass(com.kidscademy.Actions).bind(this);
	}

	onCreate(formPage) {
		this._formPage = formPage;
	}

	onStart() {
	}

	// --------------------------------------------------------------------------------------------
	// CONTROL INTERFACE
	
	setValue(description) {
		if (description == null) {
			this._textarea.reset();
			return;
		}
		this._textarea.setValue(description.replace(/<p>/g, "").replace(/<\/p>/g, "\n\n"));
	}

	getValue() {
		const description = this._textarea.getValue();
		if (!description) {
			return null;
		}
		return "<p>" + description.trim().replace(/\n\n/g, "</p><p>") + "</p>";
	}

	isValid() {
		return true;
	}

	// --------------------------------------------------------------------------------------------
	// ACTION HANDLERS
	
	_onImport() {
		AtlasService.importObjectDescription(this._formPage.getUIObject(), description => this.setValue(description));
	}

	// --------------------------------------------------------------------------------------------

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString() {
		return "com.kidscademy.atlas.DescriptionControl";
	}
};
