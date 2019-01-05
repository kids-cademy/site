$package("com.kidscademy");

com.kidscademy.HiddenControl = class extends js.dom.Control {
	constructor(ownerDoc, node) {
		super(ownerDoc, node);

		/**
		 * Hidden control keep form value for a visible element that should be notified about validity state. 
		 * By convention bound element ID is this hidden control name.
		 * 
		 * @type js.dom.Element
		 */
		this._boundElement = ownerDoc.getById(this.getName());
	}

	isValid() {
		const value = this._getValue();
		this._boundElement.addCssClass(this.CSS_INVALID, !value);
		return value;
	}

	reset() {
		super.reset();
		this._boundElement.removeCssClass(this.CSS_INVALID);
	}

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString() {
		return "com.kidscademy.HiddenControl";
	}
};
