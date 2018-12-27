$package("com.kidscademy.admin");

$include("com.kidscademy.AdminService");

com.kidscademy.admin.GraphicAssets = function(ownerDoc, node) {
	this.$super(ownerDoc, node);

	/**
	 * Parent object name published by <code>object-name-change</code> event.
	 * 
	 * @type String
	 */
	this._objectName = null;

	this._pictureControl = this.getByName("picture-path");
	this._iconControl = this.getByName("icon-path");
	this._thumbnailControl = this.getByName("thumbnail-path");

	this._pictureImage = ownerDoc.getById("picture-path");
	this._iconImage = ownerDoc.getById("icon-path");
	this._thumbnailImage = ownerDoc.getById("thumbnail-path");

	this.getByCssClass("picture-file").on("change", this._onPictureFileSelected, this);
	this.getByCssClass("icon-file").on("change", this._onIconFileSelected, this);
	this.getByCssClass("thumbnail-file").on("change", this._onThumbnailFileSelected, this);

	var actions = this.getByCssClass("actions");
	actions.on(this, {
		"&create-icon" : this._onCreateIcon
	});
};

com.kidscademy.admin.GraphicAssets.prototype = {
	bindEvents : function(events) {
		events.addListener("object-update", function(object) {
			this._objectName = object.name;

			this._pictureControl.setValue(object.picturePath);
			this._iconControl.setValue(object.iconPath);
			this._thumbnailControl.setValue(object.thumbnailPath);

			this._pictureImage.setSrc("/repository/" + object.picturePath);
			this._iconImage.setSrc("/repository/" + object.iconPath);
			this._thumbnailImage.setSrc("/repository/" + object.thumbnailPath);
		}, this);

		events.addListener("object-name-change", function(objectName) {
			this._objectName = objectName;
		}, this);
	},

	_onPictureFileSelected : function(ev) {
		this._pictureImage.removeCssClass("invalid");
		this._upload(ev.target, "upload-picture-file", function(picturePath) {
			this._pictureControl.setValue(picturePath);
			this._pictureImage.reload("/repository/" + picturePath);
		});
	},

	_onIconFileSelected : function(ev) {
		this._iconImage.removeCssClass("invalid");
		this._upload(ev.target, "upload-icon-file", function(iconPath) {
			this._iconControl.setValue(iconPath);
			this._iconImage.reload("/repository/" + iconPath);
		});
	},

	_onThumbnailFileSelected : function(ev) {
		this._thumbnailImage.removeCssClass("invalid");
		this._upload(ev.target, "upload-thumbnail-file", function(thumbnailPath) {
			this._thumbnailControl.setValue(thumbnailPath);
			this._thumbnailImage.reload("/repository/" + thumbnailPath);
		});
	},

	_upload : function(input, method, callback) {
		if (this._objectName == null) {
			js.ua.System.alert("Missing object name.");
			return;
		}

		var data = new FormData();
		data.append("name", this._objectName);
		data.append("file", input._node.files[0]);

		var xhr = new js.net.XHR();
		xhr.on("load", callback, this);
		xhr.open("POST", "rest/" + method);
		xhr.send(data);
	},

	_onCreateIcon : function() {
		this._iconImage.removeCssClass("invalid");
		if (this._objectName == null) {
			js.ua.System.alert("Missing object name.");
			return;
		}
		AdminService.createObjectIcon(this._objectName, function(iconPath) {
			this._iconControl.setValue(iconPath);
			this._iconImage.setSrc("/repository/" + iconPath);
		}, this);
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
