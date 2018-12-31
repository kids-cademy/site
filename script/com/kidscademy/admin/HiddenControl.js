$package("com.kidscademy.admin");

com.kidscademy.admin.HiddenControl = class extends js.dom.Control {
	constructor(ownerDoc, node) {
		super(ownerDoc, node);
	
		/**
		 * Hidden control keep form value for a visible element that should be notified about validity state.
		 * 
		 * @type js.dom.Element
		 */
		this._boundElement = ownerDoc.getById(this._config["for"]);
	}

	isValid() {
		const value = this._getValue();
		this._boundElement.addCssClass(this.CSS_INVALID, !value);
		return value;
	}

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString() {
		return "com.kidscademy.admin.HiddenControl";
	}
};
$preload(com.kidscademy.admin.HiddenControl);

//export var com.kidscademy.admin.HiddenControl = HiddenControl; 