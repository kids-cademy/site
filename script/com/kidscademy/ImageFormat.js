$package("com.kidscademy");

com.kidscademy.ImageFormat = class {
	constructor() {
		
	}
	
	format(imagePath) {
		return "/media/atlas/" + imagePath;
	}
};