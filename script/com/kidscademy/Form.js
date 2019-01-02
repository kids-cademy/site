$package("com.kidscademy");

/**
 * Form class.
 * 
 * @author Iulian Rotaru
 */
com.kidscademy.Form = class extends js.dom.Form {
	/**
	 * Construct an instance of Form class.
	 * 
	 * @param js.dom.Document ownerDoc element owner document,
	 * @param Node node native {@link Node} instance.
	 * @assert assertions imposed by {@link js.dom.Element#Element(js.dom.Document, Node)}.
	 */
	constructor(ownerDoc, node) {
		super(ownerDoc, node);
		this._textAreaControls = this.findByTag("textarea");
	}

	show() {
		super.show();
		this._textAreaControls.forEach(control => control.show());
	}

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString() {
		return "com.kidscademy.Form";
	}
};
