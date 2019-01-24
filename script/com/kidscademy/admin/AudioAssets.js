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

		/**
		 * Input control for sample title.
		 * @type js.dom.Control
		 */
		this._sampleTitleInput = this.getByName("sample-title");
		/**
		 * Hidden input that stores root relative URL for audio sample. This input is entangled with waveform graph and its validity
		 * state is reflected on waveform displayed state.
		 * @type com.kidscademy.HiddenControl
		 */
		this._sampleSrcValue = this.getByName("sample-src");
		/**
		 * Hidden input that stores root relative URL for waveform image.
		 * @type js.dom.Control
		 */
		this._waveformSrcValue = this.getByName("waveform-src");

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
		data.append("collection-name", object.dtype);
		data.append("object-name", object.name);
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
		AdminService.convertToMono(object.dtype, object.name, this._onProcessingComplete, this);
	}

	_onNormalize() {
		this._waveformImage.reset();
		this._sampleInfo.resetObject();

		const object = this._formPage.getObject();
		AdminService.normalizeSample(object.dtype, object.name, this._onProcessingComplete, this);
	}

	_onTrim() {
		this._waveformImage.reset();
		this._sampleInfo.resetObject();

		const object = this._formPage.getObject();
		AdminService.trimSilence(object.dtype, object.name, this._onProcessingComplete, this);
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
		AdminService.undoMediaProcessing(object.dtype, object.name, this._onProcessingComplete, this);
	}

	_onDone() {
		this._waveformImage.reset();
		this._sampleInfo.resetObject();

		const object = this._formPage.getObject();
		AdminService.commitMediaProcessing(object.dtype, object.name, this._onProcessingComplete, this);
	}

	_onRemove() {
		const object = this._formPage.getObject();
		if (!object.name) {
			return;
		}
		AdminService.removeObjectSample(object.dtype, object.name, () => {
			this._sampleSrcValue.reset();
			this._waveformSrcValue.reset();
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
		AdminService.generateWaveform(object.dtype, object.name, waveformPath => this._updateWaveformPath(waveformPath));
	}

	_updateSamplePath(samplePath) {
		this._sampleSrcValue.reset();
		if (samplePath) {
			this._sampleSrcValue.setValue(samplePath);
			this._audioPlayer.src = samplePath + '?' + Date.now();
		}
	}

	_updateWaveformPath(waveformPath) {
		this._waveformSrcValue.reset();
		this._waveformImage.reset();
		if (waveformPath) {
			this._waveformSrcValue.setValue(waveformPath);
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
