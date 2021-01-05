$package("com.kidscademy");

/**
 * Checkbox class.
 * 
 * @author Iulian Rotaru
 */
com.kidscademy.Checkbox = class extends HTMLElement {
	/**
	 * Construct an instance of Checkbox class.
	 */
	constructor() {
		super();
		this._control = this.getElementsByClassName("control")[0];
		this._control.addEventListener("click", this._onControlClick.bind(this));
	}

	set checked(checked) {
		this._control.classList.toggle("checked", checked);
	}

	get checked() {
		return this._control.classList.contains("checked");
	}

	/**
	 * Click event handler takes care to toggle checked state.
	 * 
	 * @param {Event} ev mouse click event.
	 */
	_onControlClick(ev) {
		this._control.classList.toggle("checked");
		this._fireChange();
	}

	_fireChange() {
		const event = document.createEvent("HTMLEvents");
		event.initEvent("change", false, true);
		this.dispatchEvent(event);
	}

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString() {
		return "com.kidscademy.Checkbox";
	}
};

customElements.define("ka-checkbox", com.kidscademy.Checkbox);
