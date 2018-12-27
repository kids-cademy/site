$package("com.kidscademy.admin");

com.kidscademy.admin.HDateControl = function(ownerDoc, node) {
	this.$super(ownerDoc, node);

	this._valueInput = this.getByName("value");
	this._formatSelect = this.getByName("format");
	this._periodSelect = this.getByName("period");
	this._eraSelect = this.getByName("era");
};

com.kidscademy.admin.HDateControl.prototype = {
	setValue : function(hdate) {
		if (hdate == null) {
			this._valueInput.reset();
			this._formatSelect.reset();
			this._periodSelect.reset();
			this._eraSelect.reset();
			return;
		}

		this._valueInput.setValue(hdate.value);
		this._formatSelect.setValue(hdate.mask & 0x000000FF);
		this._periodSelect.setValue(hdate.mask & 0x0000FF00);
		this._eraSelect.setValue(hdate.mask & 0x00FF0000);
	},

	getValue : function() {
		var format = Number(this._formatSelect.getValue());
		var period = Number(this._periodSelect.getValue());
		var era = Number(this._eraSelect.getValue());

		return {
			value : Number(this._valueInput.getValue()),
			mask : format + period + era
		};
	},

	isValid : function() {
		var valid = this._valueInput.isValid();
		valid = this._formatSelect.isValid() && valid;
		valid = this._periodSelect.isValid() && valid;
		valid = this._eraSelect.isValid() && valid;
		return valid;
	},

	reset : function() {
		this._valueInput.reset();
		this._formatSelect.reset();
		this._periodSelect.reset();
		this._eraSelect.reset();
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "com.kidscademy.admin.HDateControl";
	}
};
$extends(com.kidscademy.admin.HDateControl, js.dom.Control);
$preload(com.kidscademy.admin.HDateControl);
