$package("com.kidscademy.admin");

com.kidscademy.admin.TextControl = class extends js.dom.Control {
	constructor(ownerDoc, node) {
		super(ownerDoc, node);
	}

	setValue(text) {
		if (text == null) {
			this.reset();
			return;
		}
		this._setValue(text.replace(/<p>/g, "").replace(/<\/p>/g, "\n\n"));
	}

	getValue() {
		const text = this._getValue();
		if (!text) {
			return null;
		}
		return "<p>" + text.trim().replace(/\n\n/g, "</p><p>") + "</p>";
	}

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString() {
		return "com.kidscademy.admin.TextControl";
	}
};

$preload(com.kidscademy.admin.TextControl);
