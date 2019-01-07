$package("com.kidscademy.admin");

com.kidscademy.admin.GraphicAssets = class extends js.dom.Element {
	constructor(ownerDoc, node) {
		super(ownerDoc, node);

    	/**
    	 * Parent form page.
    	 * 
    	 * @type com.kidscademy.admin.FormPage
    	 */
		this._formPage = null;

		this._pictureControl = this.getByName("picture-path");
		this._iconControl = this.getByName("icon-path");
		this._thumbnailControl = this.getByName("thumbnail-path");

		this._pictureImage = ownerDoc.getById("picture-path");
		this._iconImage = ownerDoc.getById("icon-path");
		this._thumbnailImage = ownerDoc.getById("thumbnail-path");

		this.getByCssClass("picture-file").on("change", this._onPictureFileSelected, this);
		this.getByCssClass("icon-file").on("change", this._onIconFileSelected, this);
		this.getByCssClass("thumbnail-file").on("change", this._onThumbnailFileSelected, this);

		this._fileView = this.getByCssClass("file-view");
		this._cropView = this.getByCssClass("crop-view");
		this._previewImage = this.getByCss(".preview img");
		this._previewImage.on("load", this._onPreviewImageLoad, this);

		this._imageEditor = this.getByCssClass("editor");
		this._cropEditor = this.getByCssClass("crop-mask");

		this._aspectRatio = 0;

		const actions = this.getByCssClass("actions");
		actions.on(this, {
			"&crop": this._onCrop,
			"&create-icon": this._onCreateIcon,
			"&done": this._onDone,
			"&save": this._onSave,
			"&close": this._onClose
		});
	}

	onCreate(formPage) {
		this._formPage = formPage;
	}

	onStart() {
		const object = this._formPage.getObject();
		this._pictureControl.setValue(object.picturePath);
		this._iconControl.setValue(object.iconPath);
		this._thumbnailControl.setValue(object.thumbnailPath);

		if (object.picturePath) {
			this._pictureImage.setSrc(object.picturePath);
		}
		if (object.iconPath) {
			this._iconImage.setSrc(object.iconPath);
		}
		if (object.thumbnailPath) {
			this._thumbnailImage.setSrc(object.thumbnailPath);
		}
	}

	_onPictureFileSelected(ev) {
		this._pictureImage.removeCssClass("invalid");
		this._aspectRatio = 16 / 9;
		this._onImageFileSelected(ev);
	}

	_onIconFileSelected(ev) {
		this._iconImage.removeCssClass("invalid");
		this._aspectRatio = 1;
		this._onImageFileSelected(ev);
	}

	_onThumbnailFileSelected(ev) {
		this._thumbnailImage.removeCssClass("invalid");
		this._aspectRatio = 0;
		this._onImageFileSelected(ev);
	}

	_upload(method, callback) {
		const object = this._formPage.getObject();
		if (!object.name) {
			js.ua.System.alert("Missing object name.");
			return;
		}

		const xhr = new js.net.XHR();
		xhr.on("load", callback, this);
		xhr.open("POST", "rest/" + method);

		const canvas = this.getByTag("canvas")._node;
		canvas.toBlob((file) => {
			const data = new FormData();
			data.append("name", object.name);
			data.append("file", file);

			xhr.send(data);
		}, this._imageFile.type);
	}

	_onImageFileSelected(ev) {
		this._canvasUpdated = false;

		this._imageFile = ev.target._node.files[0];
		const reader = new FileReader();
		reader.onload = () => this._previewImage.setSrc(reader.result);
		reader.readAsDataURL(this._imageFile);
	}

	_onPreviewImageLoad(ev) {
		this._cropEditor.hide();
		this._imageEditor.show();
		this._imageFile.width = this._previewImage._node.naturalWidth;
		this._imageFile.height = this._previewImage._node.naturalHeight;
		this._fileView.setObject(this._imageFile);
	}

	_onCrop() {
		this._cropEditor.open({
			width: this._previewImage._node.width,
			height: this._previewImage._node.height,
			naturalWidth: this._previewImage._node.naturalWidth,
			naturalHeight: this._previewImage._node.naturalHeight,
			aspectRatio: this._aspectRatio
		}, this._onCropUpdate, this);
	}

	_onCropUpdate(cropInfo) {
		this._cropView.setObject(cropInfo)
	}

	_onCreateIcon() {
		this._iconImage.removeCssClass("invalid");
		const object = this._formPage.getObject();
		if (!object.name) {
			js.ua.System.alert("Missing object name.");
			return;
		}
		AdminService.createObjectIcon(object.name, (iconPath) => {
			this._iconControl.setValue(iconPath);
			this._iconImage.setSrc(iconPath);
		}, this);
	}

	_onDone() {
		const crop = this._cropEditor.getCropArea();
		this._cropEditor.hide();

		const canvas = this.getByTag("canvas")._node;
		canvas.width = crop.cx;
		canvas.height = crop.cy;

		const context = canvas.getContext("2d");
		context.drawImage(this._previewImage._node, crop.x, crop.y, crop.cx, crop.cy, 0, 0, crop.cx, crop.cy);

		this._previewImage.setSrc(canvas.toDataURL());
		this._canvasUpdated = true;
	}

	_onSave() {
		if (!this._canvasUpdated) {
			const canvas = this.getByTag("canvas")._node;
			canvas.width = this._previewImage._node.naturalWidth;
			canvas.height = this._previewImage._node.naturalHeight;

			const context = canvas.getContext("2d");
			context.drawImage(this._previewImage._node, 0, 0);
		}

		switch (this._aspectRatio) {
			case 0:
				this._upload("upload-thumbnail-file", thumbnailPath => {
					this._thumbnailControl.setValue(thumbnailPath);
					this._thumbnailImage.reload(thumbnailPath);
				});
				break;

			case 1:
				this._upload("upload-icon-file", iconPath => {
					this._iconControl.setValue(iconPath);
					this._iconImage.reload(iconPath);
				});
				break;

			default:
				this._upload("upload-picture-file", picturePath => {
					this._pictureControl.setValue(picturePath);
					this._pictureImage.reload(picturePath);
				});
		}
	}

	_onClose() {
		this._imageEditor.hide();
	}

    /**
     * Class string representation.
     * 
     * @return this class string representation.
     */
	toString() {
		return "com.kidscademy.admin.GraphicAssets";
	}
}

$preload(com.kidscademy.admin.GraphicAssets);
