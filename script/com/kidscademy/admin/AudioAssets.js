$package("com.kidscademy.admin");

com.kidscademy.admin.AudioAssets = class extends js.dom.Element {
	constructor(ownerDoc, node) {
		super(ownerDoc, node);

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

		this._sampleInfo = this.getByCssClass("sample-info");

		this.getByCssClass("sample-file").on("change", this._onSampleFileSelected, this);

		this._audioPlayer = new Audio();
		// using timeupdate event for progress has slow update rate, that is, not smooth animation
		// current implementation uses setPeriod; next commented line of code is just a reminder
		// this._audioPlayer.addEventListener("timeupdate", this._onAudioPlayerProgress.bind(this));

		this._playAction = this.getByName("play");

		var waveform = this.getByCssClass("waveform");
		this._waveformWidth = waveform.style.getWidth();
		this._audioPlayerProgress = waveform.getByCssClass("progress");

		const actions = this.getByCssClass("actions");
		actions.on(this, {
			"&audio-mono": this._onAudioMono,
			"&normalize": this._onNormalize,
			"&trim": this._onTrim,
			"&fade-in": this._onFadeIn,
			"&fade-out": this._onFadeOut,
			"&play": this._onPlay,
			"&undo": this._onUndo,
			"&done": this._onDone,
			"&remove": this._onRemove
		});
	}

	onCreate(formPage) {
		this._formPage = formPage;
	}

	onStart() {
		const object = this._formPage.getObject();
		this._updateSamplePath(object.sampleSrc);
		this._updateWaveformPath(object.waveformSrc);
		this._sampleInfo.setObject(object.sampleInfo);
	}

	_onSampleFileSelected(ev) {
		this._waveformGroup.removeCssClass("invalid");
		const object = this._formPage.getObject();
		if (!object.name) {
			js.ua.System.alert("Missing object name.");
			return;
		}
		this._waveformImage.reset();
		this._sampleInfo.resetObject();

		const data = new FormData();
		data.append("name", object.name);
		data.append("file", ev.target._node.files[0]);

		const xhr = new js.net.XHR();
		xhr.on("load", this._onProcessingComplete, this);
		xhr.open("POST", "rest/upload-audio-sample");
		xhr.send(data);
	}

	_onProcessingComplete(info) {
		this._updateSamplePath(info.sampleSrc);
		this._updateWaveformPath(info.waveformSrc);
		this._sampleInfo.setObject(info);
	}

	_onAudioMono() {
		this._waveformImage.reset();
		this._sampleInfo.resetObject();

		const object = this._formPage.getObject();
		AdminService.convertToMono(object.name, this._onProcessingComplete, this);
	}

	_onNormalize() {
		this._waveformImage.reset();
		this._sampleInfo.resetObject();

		const object = this._formPage.getObject();
		AdminService.normalizeSample(object.name, this._onProcessingComplete, this);
	}

	_onTrim() {
		this._waveformImage.reset();
		this._sampleInfo.resetObject();

		const object = this._formPage.getObject();
		AdminService.trimSilence(object.name, this._onProcessingComplete, this);
	}

	_onFadeIn() {

	}

	_onFadeOut() {

	}

	_onPlay() {
		const progress = () => {
			var percent = this._audioPlayer.currentTime / this._audioPlayer.duration;
			this._audioPlayerProgress.style.setWidth(this._waveformWidth * percent);
		};

		const stop = () => {
			clearInterval(this._timer);
			this._audioPlayer.pause();
			this._audioPlayer.currentTime = 0;
			this._playAction.setSrc("@image/action/play");
			this._audioPlayerProgress.style.setWidth(0);
		};

		if (this._audioPlayer.paused) {
			this._audioPlayer.play();
			this._playAction.setSrc("@image/action/stop");

			this._timer = setInterval(function () {
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
	}

	_onUndo() {
		this._waveformImage.reset();
		this._sampleInfo.resetObject();

		const object = this._formPage.getObject();
		AdminService.undoMediaProcessing(object.name, this._onProcessingComplete, this);
	}

	_onDone() {
		this._waveformImage.reset();
		this._sampleInfo.resetObject();

		const object = this._formPage.getObject();
		AdminService.commitMediaProcessing(object.name, this._onProcessingComplete, this);
	}

	_onRemove() {
		const object = this._formPage.getObject();
		if (!object.name) {
			return;
		}
		AdminService.removeInstrumentSample(object.name, () => {
			this._samplePathControl.reset();
			this._waveformPathControl.reset();
			this._waveformImage.reset();
			this._sampleInfo.resetObject();

			this._audioPlayer.pause();
			this._audioPlayer.src = 'data:audio/wav;base64,UklGRiQAAABXQVZFZm10IBAAAAABAAEAVFYAAFRWAAABAAgAZGF0YQAAAAA=';
		});
	}

	_onMissingWaveform(ev) {
		const object = this._formPage.getObject();
		if (!object.name) {
			return;
		}
		AdminService.generateWaveform(object.name, waveformPath => this._updateWaveformPath(waveformPath));
	}

	_updateSamplePath(samplePath) {
		this._samplePathControl.reset();
		if (samplePath) {
			this._samplePathControl.setValue(samplePath);
			this._audioPlayer.src = samplePath + '?' + Date.now();
		}
	}

	_updateWaveformPath(waveformPath) {
		this._waveformPathControl.reset();
		this._waveformImage.reset();
		if (waveformPath) {
			this._waveformPathControl.setValue(waveformPath);
			this._waveformImage.reload(waveformPath);
		}
	}

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString() {
		return "com.kidscademy.admin.AudioAssets";
	}
};

$preload(com.kidscademy.admin.AudioAssets);
