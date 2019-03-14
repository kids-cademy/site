$package("com.kidscademy");

/**
 * FormData class.
 * 
 * @author Iulian Rotaru
 */
com.kidscademy.FormData = class extends js.dom.Element {
	/**
	 * Construct an instance of FormData class.
	 * 
	 * @param {js.dom.Document} ownerDoc element owner document,
	 * @param {Node} node native {@link Node} instance.
	 * @assert assertions imposed by {@link js.dom.Element#Element(js.dom.Document, Node)}.
	 */
	constructor(ownerDoc, node) {
		super(ownerDoc, node);

		/**
		 * Iterable for controls owned by this form data.
		 * @type {js.dom.ControlsIterable}
		 */
		this._iterable = new js.dom.ControlsIterable(this);
	}

	isValid() {
		var valid = true;
		this._iterable.forEach(control => valid = control.isValid() && valid);
		return valid;
	}

	getObject() {
		const formData = new FormData();
		this._iterable.forEach(control => formData.append(control.getName(), control.getValue()));
		return formData;
	}

	setObject(object) {

	}

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString() {
		return "com.kidscademy.FormData";
	}
};

$preload(com.kidscademy.FormData);