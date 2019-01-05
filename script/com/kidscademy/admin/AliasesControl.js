$package("com.kidscademy.admin");

com.kidscademy.admin.AliasesControl = class extends js.dom.Control {
	constructor(ownerDoc, node) {
		super(ownerDoc, node);
	}

	setValue(aliases) {
		super.setValue(aliases.join(", "));
	}

	getValue() {
		var value = super.getValue();
		if (value == null) {
			return [];
		}
		return value.trim().split(/\s*,\s*/);
	}

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString() {
		return "com.kidscademy.admin.AliasesControl";
	}
};

$preload(com.kidscademy.admin.AliasesControl);
