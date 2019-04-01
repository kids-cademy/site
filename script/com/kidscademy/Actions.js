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

		/**
		 * Name of the action executed previous to current one, possible null if no operation was invoked yet. This value is updated
		 * after every action handler invocation.
		 * This value is returned by {@link this.getPreviousAction()} and can be used by container when current action handler depends
		 * on previous action.
		 * @type {String} 
		 */
		this._previousAction = null;
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

			const containerHandler = container[handlerName(name)];
			if (typeof containerHandler !== "function") {
				throw `Missing handler for action ${name}.`;
			}

			// create a closure to keep action handler state that include current action name
			// container handler can obtain current action via this._actions.getCurrentAction()
			const actions = this;
			const actionHandler = function () {
				containerHandler.apply(container, arguments);
				actions._previousAction = name;
			}.bind(container);

			child.on("click", actionHandler, container);

			const key = child.getAttr("data-key");
			if (key !== null) {
				this._keymap[js.event.Key[key]] = actionHandler;
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

	showOnly(...names) {
		this.getChildren().forEach(action => {
			action.show(names.includes(action.getName()));
		});
	}

	showAll() {
		this.getChildren().forEach(action => {
			action.show();
		});
	}

	hide(...names) {
		names.forEach(name => this.getByName(name).hide());
		return this;
	}

	getPreviousAction() {
		return this._previousAction;
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
