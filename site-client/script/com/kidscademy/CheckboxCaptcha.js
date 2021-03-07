$package("com.kidscademy");

/**
 * Checkbox CAPTCHA has a hidden standard challenge sentence and image set and a checkbox that reveal them.
 * 
 * @author Iulian Rotaru
 */
com.kidscademy.CheckboxCaptcha = class extends js.widget.Captcha {
	/**
	 * Construct an instance of CAPTCHA class.
	 */
	constructor() {
		super();

		this._checkbox = this.getElementsByTagName("ka-checkbox")[0];
		this._checkbox.addEventListener("change", this._onCheckboxChange.bind(this));

		this._bodyView = this.getElementsByClassName("body")[0];
		this.getElementsByClassName("help")[0].addEventListener("click", this._onHelp.bind(this));
	}

	isValid() {
		if (!this._checkbox.checked) {
			js.ua.System.alert("Please check you are a humman.");
			return false;
		}
		return true;
	}

	reset() {
		super.reset();
		this._checkbox.checked = false;
		this._bodyView.classList.add("hidden");
	}

	_onCheckboxChange(ev) {
		//ev.halt();
		this._loadChallenge();
		this._checkbox.checked = false;
		this._bodyView.classList.remove("hidden");
	}

	_onResponseVerified(challenge) {
		super._onResponseVerified(challenge);
		if (this._valid) {
			this._checkbox.checked = true;
			this._bodyView.classList.add("hidden");
		}
	}

	_onHelp(ev) {
		js.ua.System.alert("Help not yet implemented.");
	}
};

customElements.define("ka-checkbox-captcha", com.kidscademy.CheckboxCaptcha);
