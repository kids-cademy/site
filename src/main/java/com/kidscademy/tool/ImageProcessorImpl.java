package com.kidscademy.tool;

import static com.kidscademy.tool.AbstractToolProcess.format;

import java.io.File;
import java.io.IOException;

public class ImageProcessorImpl implements ImageProcessor {
    private static final int PICTURE_WIDTH = 920;
    private static final int PICTURE_HEIGHT = 560;
    private static final double PICTURE_QUALITY = 40;

    private static final int ICON_SIZE = 96;
    private static final double ICON_QUALITY = 80;

    // thumbnail has restriction only on width, height is allowed to scale
    private static final int THUMBNAIL_WIDTH = 560;

    private final ImageMagickProcess convert;
    private final ImageMagickProcess identify;

    public ImageProcessorImpl() {
	this.convert = new ConvertProcess();
	this.identify = new IdentifyProcess();
    }

    @Override
    public ImageInfo getImageInfo(File imageFile) throws IOException {
	ImageInfoResult result = identify.exec(ImageInfoResult.class, imageFile.getAbsolutePath());
	return result.getImageInfo();
    }

    @Override
    public void saveObjectPicture(File uploadFile, File pictureFile) throws IOException {
	exec("${uploadFile} -resize ${width}x${height} -quality ${quality} ${pictureFile}", uploadFile, PICTURE_WIDTH,
		PICTURE_HEIGHT, PICTURE_QUALITY, pictureFile);
    }

    @Override
    public void saveObjectIcon(File uploadFile, File iconFile) throws IOException {
	exec("${uploadFile} -resize ${width}x${height} -quality ${quality} ${iconFile}", uploadFile, ICON_SIZE,
		ICON_SIZE, ICON_QUALITY, iconFile);
    }

    @Override
    public void saveObjectThumbnail(File uploadFile, File thumbnailFile) throws IOException {
	exec("${uploadFile} -resize ${width} ${thumbnailFile}", uploadFile, THUMBNAIL_WIDTH, thumbnailFile);
    }

    @Override
    public void createObjectIcon(File pictureFile, File iconFile) throws IOException {
	int width = PICTURE_HEIGHT;
	int height = PICTURE_HEIGHT;
	int xoffset = (PICTURE_WIDTH - PICTURE_HEIGHT) / 2;
	int yoffset = 0;

	exec("${pictureFile} -crop ${width}x${height}+${xoffset}+${yoffset} -resize ${size}x${size} -quality ${quality} ${iconFile}",
		pictureFile, width, height, xoffset, yoffset, ICON_SIZE, ICON_SIZE, ICON_QUALITY, iconFile);
    }

    @Override
    public void generateXAxis(File imageFile, int canvasWidth, int canvasHeight, String xaxisColor) throws IOException {
	float y = (canvasHeight - 1) / 2.0F;
	exec("-size ${canvasWidth}x${canvasHeight} xc:transparent -fill white -stroke ${xaxisColor} -draw \"line 0,${y},${canvasWidth},${y}\" ${imageFile}",
		canvasWidth, canvasHeight, xaxisColor, y, canvasWidth, y, imageFile);
    }

    @Override
    public void generateRainbowGradient(File imageFile, int canvasWidth, int canvasHeight) throws IOException {
	// reverse order of width and height because of -rotate 90
	exec("-size ${canvasHeight}x${canvasWidth} xc:red -colorspace HSB gradient: -compose CopyRed -composite -colorspace RGB -rotate 90.0 ${imageFile}",
		canvasHeight, canvasWidth, imageFile);
    }

    @Override
    public void crop(File imageFile, File targetFile, int width, int height, int xoffset, int yoffset)
	    throws IOException {
	exec("${imageFile} -crop ${width}x${height}+${xoffset}+${yoffset} ${targetFile}", imageFile, width, height,
		xoffset, yoffset, targetFile);
    }

    @Override
    public void compose(File imageFile, File maskFile, ImageCompose compose) throws IOException {
	exec("-composite -compose srcin ${imageFile} ${maskFile} ${imageFile}", imageFile, maskFile, imageFile);
    }

    @Override
    public void overlap(File imageFile, File... overlapImageFiles) throws IOException {
	for (File overlapImageFile : overlapImageFiles) {
	    exec("-composite ${imageFile} ${overlapImageFile} ${imageFile}", imageFile, overlapImageFile, imageFile);
	}
    }

    @Override
    public String perceptualHash(File imageFile) throws IOException {
	PerceptualHashResult result = identify.exec(PerceptualHashResult.class, format("-verbose -define identify:moments ${imageFile}", imageFile));
	return result.getHash();
    }

    // --------------------------------------------------------------------------------------------

    private void exec(String format, Object... args) throws IOException {
	convert.exec(format(format, args));
    }
}
