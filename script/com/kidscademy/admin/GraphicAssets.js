$package("com.kidscademy.admin");

$include("com.kidscademy.AdminService");

com.kidscademy.admin.GraphicAssets = function(ownerDoc, node) {
	this.$super(ownerDoc, node);

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

	var actions = this.getByCssClass("actions");
	actions.on(this, {
		"&crop" : this._onCrop,
		"&create-icon" : this._onCreateIcon,
		"&done" : this._onDone,
		"&save" : this._onSave,
		"&close" : this._onClose
	});
};

com.kidscademy.admin.GraphicAssets.prototype = {
	onCreated : function(formPage) {
		this._formPage = formPage;
	},

	onStart : function() {
		var object = this._formPage.getObject();
		this._pictureControl.setValue(object.picturePath);
		this._iconControl.setValue(object.iconPath);
		this._thumbnailControl.setValue(object.thumbnailPath);

		if (object.picturePath) {
			this._pictureImage.setSrc("/repository/" + object.picturePath);
		}
		if (object.iconPath) {
			this._iconImage.setSrc("/repository/" + object.iconPath);
		}
		if (object.thumbnailPath) {
			this._thumbnailImage.setSrc("/repository/" + object.thumbnailPath);
		}
	},

	_onPictureFileSelected : function(ev) {
		this._pictureImage.removeCssClass("invalid");
		this._aspectRatio = 16 / 9;
		this._onImageFileSelected(ev);
	},

	_onIconFileSelected : function(ev) {
		this._iconImage.removeCssClass("invalid");
		this._aspectRatio = 1;
		this._onImageFileSelected(ev);
	},

	_onThumbnailFileSelected : function(ev) {
		this._thumbnailImage.removeCssClass("invalid");
		this._aspectRatio = 0;
		this._onImageFileSelected(ev);
	},

	_upload : function(method, callback) {
		var object = this._formPage.getObject();
		if (!object.name) {
			js.ua.System.alert("Missing object name.");
			return;
		}

		var xhr = new js.net.XHR();
		xhr.on("load", callback, this);
		xhr.open("POST", "rest/" + method);

		var canvas = this.getByTag("canvas")._node;
		canvas.toBlob(function(file) {
			var data = new FormData();
			data.append("name", object.name);
			data.append("file", file);

			xhr.send(data);
		}, this._imageFile.type);
	},

	_onImageFileSelected : function(ev) {
		this._canvasUpdated = false;
		
		this._imageFile = ev.target._node.files[0];
		var reader = new FileReader();
		reader.onload = function(e) {
			this._previewImage.setSrc(reader.result);
		}.bind(this);
		reader.readAsDataURL(this._imageFile);
	},

	_onPreviewImageLoad : function(ev) {
		this._cropEditor.hide();
		this._imageEditor.show();
		this._imageFile.width = this._previewImage._node.naturalWidth;
		this._imageFile.height = this._previewImage._node.naturalHeight;
		this._fileView.setObject(this._imageFile);
	},

	_onCrop : function() {
		this._cropEditor.open({
			width : this._previewImage._node.width,
			height : this._previewImage._node.height,
			naturalWidth : this._previewImage._node.naturalWidth,
			naturalHeight : this._previewImage._node.naturalHeight,
			aspectRatio : this._aspectRatio
		}, this._onCropUpdate, this);
	},

	_onCropUpdate : function(cropInfo) {
		this._cropView.setObject(cropInfo)
	},

	_onCreateIcon : function() {
		this._iconImage.removeCssClass("invalid");
		var object = this._formPage.getObject();
		if (!object.name) {
			js.ua.System.alert("Missing object name.");
			return;
		}
		AdminService.createObjectIcon(object.name, function(iconPath) {
			this._iconControl.setValue(iconPath);
			this._iconImage.setSrc("/repository/" + iconPath);
		}, this);
	},

	_onDone : function() {
		var crop = this._cropEditor.getCropArea();
		// alert(JSON.stringify(crop));
		this._cropEditor.hide();

		var canvas = this.getByTag("canvas")._node;
		canvas.width = crop.cx;
		canvas.height = crop.cy;

		var context = canvas.getContext("2d");

		context.drawImage(this._previewImage._node, crop.x, crop.y, crop.cx, crop.cy, 0, 0, crop.cx, crop.cy);

		this._previewImage.setSrc(canvas.toDataURL());
		this._canvasUpdated = true;
	},

	_onSave : function() {
		if(!this._canvasUpdated) {
			var canvas = this.getByTag("canvas")._node;
			canvas.width = this._previewImage._node.naturalWidth;
			canvas.height = this._previewImage._node.naturalHeight;

			var context = canvas.getContext("2d");
			context.drawImage(this._previewImage._node, 0, 0);
		}
		
		switch (this._aspectRatio) {
		case 0:
			this._upload("upload-thumbnail-file", function(thumbnailPath) {
				this._thumbnailControl.setValue(thumbnailPath);
				this._thumbnailImage.reload("/repository/" + thumbnailPath);
			});
			break;

		case 1:
			this._upload("upload-icon-file", function(iconPath) {
				this._iconControl.setValue(iconPath);
				this._iconImage.reload("/repository/" + iconPath);
			});
			break;

		default:
			this._upload("upload-picture-file", function(picturePath) {
				this._pictureControl.setValue(picturePath);
				this._pictureImage.reload("/repository/" + picturePath);
			});
		}
	},

	_onClose : function() {
		this._imageEditor.hide();
	},

	/**
	 * Class string representation.
	 * 
	 * @return this class string representation.
	 */
	toString : function() {
		return "com.kidscademy.admin.GraphicAssets";
	}
};
$extends(com.kidscademy.admin.GraphicAssets, js.dom.Element);
$preload(com.kidscademy.admin.GraphicAssets);
