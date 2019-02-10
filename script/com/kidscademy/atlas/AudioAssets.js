$package("com.kidscademy.atlas");

com.kidscademy.atlas.AudioAssets = class extends js.dom.Element {
	constructor(ownerDoc, node) {
		super(ownerDoc, node);

		/**
		 * Parent form page.
		 * 
		 * @type com.kidscademy.atlas.FormPage
		 */
		this._formPage = null;

		/**
		 * Input control for sample title.
		 * @type js.dom.Control
		 */
		this._sampleTitleInput = this.getByName("sample-title");

		this._audioPlayer = this.getByClass(com.kidscademy.atlas.AudioPlayer);
		this._audioPlayer.on("stop", this._onPlayerStop, this);
		this._playAction = this.getByName("play");

		this._sampleInfo = this.getByCssClass("sample-info");

		const actions = this.getByCssClass("actions");
		actions.on(this, {
			"&cut": this._onCut,
			"&mono": this._onMono,
			"&normalize": this._onNormalize,
			"&trim": this._onTrim,
			"&fade-in": this._onFadeIn,
			"&fade-out": this._onFadeOut,
			"&play": this._onPlay,
			"&undo": this._onUndo,
			"&clear": this._onClear,
			"&done": this._onDone,
			"&remove": this._onRemove
		});

		this.getByCssClass("sample-file").on("change", this._onSampleFileSelected, this);
	}

	onCreate(formPage) {
		this._formPage = formPage;
		this._audioPlayer.onCreate(formPage);
	}

	onStart() {
		const object = this._formPage.getObject();
		this._audioPlayer.setObject(object);
		this._sampleInfo.setObject(object.sampleInfo);
	}

	_onSampleFileSelected(ev) {
		this._audioPlayer.resetObject();
		const object = this._formPage.getObject();
		if (!object.name) {
			js.ua.System.alert("Missing object name.");
			return;
		}
		this._sampleInfo.resetObject();

		const data = new FormData();
		data.append("dtype", object.dtype);
		data.append("name", object.name);
		data.append("file", ev.target._node.files[0]);

		const xhr = new js.net.XHR();
		xhr.on("load", this._onProcessingComplete, this);
		xhr.open("POST", "rest/upload-audio-sample");
		xhr.send(data);
	}

	_onProcessingComplete(info) {
		this._audioPlayer.setObject(info);
		this._sampleInfo.setObject(info);
	}

	_onCut() {
		const object = this._getObject();
		const start = this._audioPlayer.getSelectionStart();

		this._audioPlayer.resetObject();
		this._sampleInfo.resetObject();

		AtlasService.cutAudioSample(object, start, this._onProcessingComplete, this);
	}

	_onMono() {
		this._audioPlayer.resetObject();
		this._sampleInfo.resetObject();
		AtlasService.convertAudioSampleToMono(this._getObject(), this._onProcessingComplete, this);
	}

	_onNormalize() {
		this._audioPlayer.resetObject();
		this._sampleInfo.resetObject();
		AtlasService.normalizeAudioSample(this._getObject(), this._onProcessingComplete, this);
	}

	_onTrim() {
		this._audioPlayer.resetObject();
		this._sampleInfo.resetObject();
		AtlasService.trimAudioSampleSilence(this._getObject(), this._onProcessingComplete, this);
	}

	_onFadeIn() {
		this._audioPlayer.resetObject();
		this._sampleInfo.resetObject();
		AtlasService.fadeInAudioSample(this._getObject(), this._onProcessingComplete, this);
	}

	_onFadeOut() {
		this._audioPlayer.resetObject();
		this._sampleInfo.resetObject();
		AtlasService.fadeOutAudioSample(this._getObject(), this._onProcessingComplete, this);
	}

	_onPlay() {
		if (!this._audioPlayer.isPlaying()) {
			this._audioPlayer.play();
			this._playAction.setSrc("@image/action/stop");
		}
		else {
			this._audioPlayer.stop();
			this._playAction.setSrc("@image/action/play");
		}
	}

	_onPlayerStop() {
		this._playAction.setSrc("@image/action/play");
	}

	_onUndo() {
		this._audioPlayer.resetObject();
		this._sampleInfo.resetObject();
		AtlasService.undoAudioSampleProcessing(this._getObject(), this._onProcessingComplete, this);
	}

	_onClear() {
		if (this._audioPlayer.resetSelection()) {
			return;
		}

		this._audioPlayer.resetObject();
		this._sampleInfo.resetObject();
		AtlasService.roolbackAudioSampleProcessing(this._getObject(), this._onProcessingComplete, this);
		
	}
	
	_onDone() {
		this._audioPlayer.resetObject();
		this._sampleInfo.resetObject();
		AtlasService.commitAudioSampleProcessing(this._getObject(), this._onProcessingComplete, this);
	}

	_onRemove() {
		const object = this._getObject();
		if (!object.name) {
			return;
		}
		AtlasService.removeAudioSample(object, () => {
			this._audioPlayer.resetObject(false);
			this._sampleInfo.resetObject();
		});
	}

	_getObject() {
		const object = this._formPage.getObject();
		return {
			dtype: object.dtype,
			name: object.name
		}
	}

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString() {
		return "com.kidscademy.atlas.AudioAssets";
	}
};
