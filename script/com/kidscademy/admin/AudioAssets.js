$package("com.kidscademy.admin");

$include("com.kidscademy.AdminService");

com.kidscademy.admin.AudioAssets = function(ownerDoc, node) {
	this.$super(ownerDoc, node);

	/**
	 * Parent form page.
	 * 
	 * @type com.kidscademy.admin.FormPage
	 */
	this._formPage = null;

	this._sampleTitleControl = this.getByName("sample-title");
	this._samplePathControl = this.getByName("sample-path");
	this._waveformPathControl = this.getByName("waveform-path");

	this._waveformGroup = this.getByCssClass("waveform");
	this._waveformImage = this._waveformGroup.getByTag("img");
	this._waveformImage.on("error", this._onMissingWaveform, this);

	this.getByCssClass("sample-file").on("change", this._onSampleFileSelected, this);

	this._audioPlayer = new Audio();
	// this._audioPlayer.addEventListener("timeupdate", this._onAudioPlayerProgress.bind(this));

	this._playAction = this.getByName("play");

	var waveform = this.getByCssClass("waveform");
	this._waveformWidth = waveform.style.getWidth();
	this._audioPlayerProgress = waveform.getByCssClass("progress");

	var actions = this.getByCssClass("actions");
	actions.on(this, {
		"&fade-in" : this._onFadeIn,
		"&fade-out" : this._onFadeOut,
		"&play" : this._onPlay,
		"&remove" : this._onRemove
	});
};

com.kidscademy.admin.AudioAssets.prototype = {
	onCreated : function(formPage) {
		this._formPage = formPage;
	},

	onStart : function() {
		var object = this._formPage.getObject();
		this._updateSamplePath(object.samplePath);
		this._updateWaveformPath(object.waveformPath);
	},

	_onSampleFileSelected : function(ev) {
		this._waveformGroup.removeCssClass("invalid");
		var object = this._formPage.getObject();
		if (!object.name) {
			js.ua.System.alert("Missing object name.");
			return;
		}
		this._waveformImage.reset();

		var data = new FormData();
		data.append("name", object.name);
		data.append("file", ev.target._node.files[0]);

		var xhr = new js.net.XHR();
		xhr.on("load", this._onUploadComplete, this);
		xhr.open("POST", "rest/upload-audio-sample");
		xhr.send(data);
	},

	_onUploadComplete : function(object) {
		this._updateSamplePath(object.samplePath);
		this._updateWaveformPath(object.waveformPath);
	},

	_onFadeIn : function() {

	},

	_onFadeOut : function() {

	},

	_onPlay : function() {
		var progress = function() {
			var percent = this._audioPlayer.currentTime / this._audioPlayer.duration;
			this._audioPlayerProgress.style.setWidth(this._waveformWidth * percent);
		}.bind(this);

		var stop = function() {
			clearInterval(this._timer);
			this._audioPlayer.pause();
			this._audioPlayer.currentTime = 0;
			this._playAction.setSrc("@image/action/play");
			this._audioPlayerProgress.style.setWidth(0);
		}.bind(this);

		if (this._audioPlayer.paused) {
			this._audioPlayer.play();
			this._playAction.setSrc("@image/action/stop");

			this._timer = setInterval(function() {
				if (this._audioPlayer.paused) {
					stop();
				}
				else {
					requestAnimationFrame(progress);
				}
			}.bind(this), 20);
		}
		else {
			stop();
		}
	},

	_onRemove : function() {

	},

	_onMissingWaveform : function(ev) {
		var object = this._formPage.getObject();
		if (!object.name) {
			return;
		}
		AdminService.generateWaveform(object.name, function(waveformPath) {
			this._updateWaveformPath(waveformPath);
		}, this);
	},

	_updateSamplePath : function(samplePath) {
		if (samplePath) {
			this._samplePathControl.setValue(samplePath);
			this._audioPlayer.src = "/repository/" + samplePath;
		}
	},

	_updateWaveformPath : function(waveformPath) {
		if (waveformPath) {
			this._waveformPathControl.setValue(waveformPath);
			this._waveformImage.setSrc("/repository/" + waveformPath);
		}
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "com.kidscademy.admin.AudioAssets";
	}
};
$extends(com.kidscademy.admin.AudioAssets, js.dom.Element);
$preload(com.kidscademy.admin.AudioAssets);
