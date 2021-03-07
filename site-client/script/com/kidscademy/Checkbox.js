$package("com.kidscademy");

/**
 * Checkbox class.
 * 
 * @author Iulian Rotaru
 */
com.kidscademy.Checkbox = class extends HTMLElement {
	constructor() {
		super();
		this.classList.add("checkbox");
		this.innerHTML = '<span class="control"></span><span class="label"></span>';

		this._control = this.getElementsByClassName("control")[0];
		this._control.addEventListener("click", this._onControlClick.bind(this));

		this._label = this.getElementsByClassName("label")[0];
		this._label.textContent = this.getAttribute("label");
	}

	set name(name) {
		this.setAttribute("name", name);
	}

	get name() {
		return this.getAttribute("name");
	}

	set label(label) {
		this._label.textContent = label;
	}

	get label() {
		return this._label.textContent;
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
};

customElements.define("ka-checkbox", com.kidscademy.Checkbox);
