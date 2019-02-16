$package("com.kidscademy");

/**
 * Actions handler.
 * 
 * @author Iulian Rotaru
 */
com.kidscademy.Actions = class extends js.dom.Element {
	/**
	 * Construct an instance of Actions class.
	 * 
	 * @param js.dom.Document ownerDoc element owner document,
	 * @param Node node native {@link Node} instance.
	 * @assert assertions imposed by {@link js.dom.Element#Element(js.dom.Document, Node)}.
	 */
	constructor(ownerDoc, node) {
		super(ownerDoc, node);

		this._keymap = {};
	}

	bind(container, input) {
		function handlerName(name) {
			return "_on" + name.replace(/(?:^|\-)(\w)/g, (match, capture) => capture.toUpperCase());
		}

		this.getChildren().forEach(child => {
			const name = child.getName();
			if (name == null) {
				return;
			}

			const handler = container[handlerName(name)];
			if (typeof handler !== "function") {
				throw `Missing handler for action ${name}.`;
			}

			child.on("click", handler, container);

			const key = child.getAttr("data-key");
			if (key !== null) {
				this._keymap[js.event.Key[key]] = handler.bind(container);
			}
		});

		if (input) {
			input.on("keypress", this._onKey, this);
		}
		return this;
	}

	show(...args) {
		if (args.length === 0) {
			return;
		}

		var show, names;
		if (js.lang.Types.isBoolean(args[0])) {
			show = args[0];
			names = args.slice(1);
		}
		else {
			show = true;
			names = args;
		}

		names.forEach(name => this.getByName(name).show(show));
		return this;
	}

	hide(...names) {
		names.forEach(name => this.getByName(name).hide());
		return this;
	}

	_onKey(ev) {
		const handler = this._keymap[ev.key];
		if (handler) {
			ev.halt();
			handler();
		}
	}

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString() {
		return "com.kidscademy.Actions";
	}
};
