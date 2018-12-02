$package("com.kidscademy");

/**
 * CounterFormat class.
 *
 * @author Iulian Rotaru
 * @since 1.0
 *
 * @constructor Construct an CounterFormat instance.
 */
com.kidscademy.CounterFormat = function() {
    /**
     * Number format used internally to render numeric values.
     * 
     * @type js.format.NumberFormat
     */
    this._numberFormat = new js.format.NumberFormat();
    this._numberFormat.setGroupingUsed(true);
    this._numberFormat.setMinimumFractionDigits(0);
    this._numberFormat.setMaximumFractionDigits(0);
};

com.kidscademy.CounterFormat.prototype = {
	/**
	 * Class string representation.
	 *
	 * @return this class string representation.
	 */
	toString: function() {
		return "com.kidscademy.CounterFormat";
	}
};
$extends(com.kidscademy.CounterFormat, js.format.Number);
