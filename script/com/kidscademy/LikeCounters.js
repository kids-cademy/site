$package("com.kidscademy");

$include("com.kidscademy.ServiceController");

/**
 * Counters for user like / dislike with like percent value and bar graph.
 * 
 * @author Iulian Rotaru
 * 
 * @constructor Construct an instance of LikeCounters class.
 * @param js.dom.Document ownerDoc element owner document,
 * @param Node node native {@link Node} instance.
 * @assert assertions imposed by {@link js.dom.Element#Element(js.dom.Document, Node)}.
 */
com.kidscademy.LikeCounters = function(ownerDoc, node) {
	this.$super(ownerDoc, node);

	/**
	 * Counters view.
	 * 
	 * @type js.dom.Element
	 */
	this._countersView = this.getByCss(".counters");

	/**
	 * Bar graph for score percent. On mobile devices this bar graph is not displayed.
	 * 
	 * @type js.dom.Element
	 */
	this._percentGraphView = this.getByCss(".percent-graph");

	if (this._percentGraphView !== null) {
		/**
		 * Percent bar graph width, in pixels.
		 * 
		 * @type Number
		 */
		this._percentGraphWidth = this.getByCss(".percent-graph").style.getWidth();

		/**
		 * Pointer view for percent bar graph.
		 * 
		 * @type js.dom.Element
		 */
		this._percentGraphPointer = this.getByCss(".percent-graph .pointer");

		/**
		 * Empty percent label view with background color animated by bar graph.
		 * 
		 * @type js.dom.Element
		 */
		this._percentLabel = this.getByCss(".percent .label");

		/**
		 * Value view for percent integer part.
		 * 
		 * @type js.dom.Element
		 */
		this._percentIntegerView = this.getByCss(".percent .integer-part");

		/**
		 * Value view for percent fractional part.
		 * 
		 * @type js.dom.Element
		 */
		this._percentFractionalView = this.getByCss(".percent .fractional-part");

		/**
		 * Animation for percent value and bar graph.
		 * 
		 * @type js.fx.Anim
		 */
		this._percentAnim = null;
	}

	/**
	 * Dislike reasons group contains the form and buttons.
	 * 
	 * @type js.dom.Element
	 */
	this._reasonsViewGroup = this.getByCss(".reasons");

	/**
	 * Dislike reasons form.
	 * 
	 * @type js.dom.Form
	 */
	this._reasonsForm = this.getByCss(".reasons form");

	/**
	 * Captcha used to verify 'do not like' submit.
	 * 
	 * @type com.kidscademy.CheckboxCaptcha
	 */
	this._captcha = this.getByClass(com.kidscademy.CheckboxCaptcha);

	this.getByCss(".icon.plus").on("click", this._onLike, this);
	this.getByCss(".icon.minus").on("click", this._onDislike, this);
	this.getByCss(".reasons .submit").on("click", this._onReasonsSubmit, this);
	this.getByCss(".reasons .cancel").on("click", this._onReasonsCancel, this);
};

com.kidscademy.LikeCounters.prototype = {
	SPEED_FACTOR : 20,

	/**
	 * Set like / dislike counters value, compute like percent value and start percent bar graph animation.
	 * <p>
	 * Here is description for counters object.
	 * 
	 * <pre>
	 * Counters = {
	 *  likeCount: 0,
	 *  dislikeCount: 0
	 * }
	 * </pre>
	 * 
	 * @param Object counters counters value.
	 */
	setObject : function(counters) {
		this._countersView.setObject(counters);

		// on mobile there is not percent bra graph
		if (this._percentGraphView === null) {
			return;
		}

		var percent = counters.dislikeCount !== 0 ? 100 * counters.likeCount / (counters.likeCount + counters.dislikeCount) : 100;
		var percentIntegerPart = Math.floor(percent);
		var percentFractionalPart = Math.ceil(((percent < 1.0) ? percent : (percent % Math.floor(percent))) * 100);

		var anim = new js.fx.Anim({
			el : this._percentGraphPointer,
			duration : this.SPEED_FACTOR * percent,
			style : "left",
			from : 0,
			to : this._percentGraphWidth * percent / 100,
			ttf : js.fx.TTF.Logarithmic
		});

		this._percentIntegerView.setText("00");
		this._percentFractionalView.setText("00");

		anim.on("anim-render", function(timestamp) {
			// animation duration and pointer position are both proportional with percent value
			// so time stamp can be used for both position and percent value inferring
			this._percentIntegerView.setText(Math.round(timestamp / this.SPEED_FACTOR));
			this._percentLabel.style.set("background-color", this._getColorByPosition(10 * timestamp / this.SPEED_FACTOR));
		}, this);

		anim.on("anim-stop", function(timestamp) {
			this._percentIntegerView.setText(percentIntegerPart);
			this._percentFractionalView.setText(percentFractionalPart);
		}, this);
		anim.start();
	},

	_onLike : function(ev) {
		ServiceController.incrementLikeCounter(this.setObject, this);
	},

	_onDislike : function(ev) {
		this.findByCss(".checkbox").call("uncheck");
		this._reasonsViewGroup.show();
		// WinMain.scrollTo(this._reasonsViewGroup, parseInt("@dimen/page-header-height"));
		var messageView = this.getByCss(".feedback .message");
		if (messageView !== null) {
			WinMain.scrollTo(this._reasonsViewGroup, messageView.style.getPageTop());
		}
	},

	_onReasonsSubmit : function(ev) {
		if (!this._reasonsForm.isValid()) {
			return;
		}
		this._reasonsViewGroup.hide();

		var reasons = [];
		this.findByCss("form>.checkbox").forEach(function(checkbox) {
			if (checkbox.checked()) {
				reasons.push(checkbox.getValue());
			}
		}, this);

		WinMain.scrollTo(WinMain.doc.getByTag("body"), 0, function() {
			if (reasons.length > 0) {
				ServiceController.incrementDislikeCounter(reasons, this.setObject, this);
			}
		}, this);
	},

	_onReasonsCancel : function(ev) {
		this._captcha.reset();
		this._reasonsViewGroup.hide();
		WinMain.scrollTo(WinMain.doc.getByTag("body"));
	},

	/**
	 * Get color of the percent bar graph at given position. This color is used to animate percent label. This method
	 * tries to indirectly deduce color value based on linear gradient color stops. If gradient style is changed this
	 * method logic should be updated.
	 * <p>
	 * Current logic is valid for <code>linear-gradient(to right, #FF0000, #FFFF00, #008000)</code>. There are three
	 * color stops at 0%, 50% and 100% bar graph width for red, yellow and green colors. Red decreases from maximum to 0
	 * in interval 0% to 50%, yellow increases from 0 to maximum in the same interval then decreases back to 0 in 50% to
	 * 100% interval. Green is 0 for first interval then increases to maximum in the second. This method logic assume
	 * gradient easing function is linear.
	 * <p>
	 * This method computed R, G and B color components for given position and return CSS color. Computation is based on
	 * RGB evolution described below ASCII diagram. Note that channel B is always 0.
	 * 
	 * <pre>
	 * R channel                    G channel                     B channel
	 * 
	 * ==========\ ......... 255             /\ ........ 255
	 *             \                       /     \
	 *               \                   /          \ .. 128
	 *                 \               / 
	 *                   \ .. 0      / ................. 0        ================= .. 0
	 * 0%       50%     100%         0%      50%     100%    	 0%      50%      100%		
	 * </pre>
	 * 
	 * @param Number position, value between 0 and bar graph width.
	 * @return String color string in CSS format.
	 */
	_getColorByPosition : function(position) {
		var halfWidth = this._percentGraphWidth / 2;
		var offset = position % halfWidth;
		var R, G, B = 0;

		if (position < halfWidth) {
			R = 255;
			G = 255 * offset / halfWidth;
		}
		else {
			R = 255 * (halfWidth - offset) / halfWidth;
			G = 128 + 128 * (halfWidth - offset) / halfWidth;
		}

		return $format("#%02X%02X%02X", R, G, B);
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "com.kidscademy.LikeCounters";
	}
};
$extends(com.kidscademy.LikeCounters, js.dom.Element);
