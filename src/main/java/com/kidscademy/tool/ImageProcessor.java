package com.kidscademy.tool;

import java.io.File;
import java.io.IOException;

public interface ImageProcessor {
    /**
     * Convert an image file type into another. Image file type is identified by
     * file extension.
     * 
     * @param imageFile
     * @param targetFile
     * @param quality
     *            optional quality, in range 0 ... 100.
     * @throws IOException
     */
    void convert(File imageFile, File targetFile, int... quality) throws IOException;

    /**
     * Resize image.
     * 
     * @param imageFile
     * @param targetFile
     * @param width
     *            width, 0 for auto,
     * @param height
     *            height, 0 for auto.
     * @throws IOException
     */
    void resize(File imageFile, File targetFile, int width, int height) throws IOException;

    ImageInfo getImageInfo(File source) throws IOException;

    void generateXAxis(File imageFile, int canvasWidth, int canvasHeight, String xaxisColor) throws IOException;

    void generateRainbowGradient(File imageFile, int canvasWidth, int canvasHeight) throws IOException;

    /**
     * Remove unused space around image. This option removes any edges that are
     * exactly the same color as the corner pixels.
     * 
     * @param imageFile
     * @param targetFile
     * @throws IOException
     */
    void trim(File imageFile, File targetFile) throws IOException;

    void flop(File imageFile, File targetFile) throws IOException;

    void flip(File imageFile, File targetFile) throws IOException;

    void crop(File imageFile, File targetFile, int width, int height, int xoffset, int yoffset) throws IOException;

    void compose(File imageFile, File maskFile, ImageCompose compose) throws IOException;

    void overlap(File imageFile, File... overlapImageFiles) throws IOException;

    /**
     * Get MD5 digest of image content. Returned value is useful only to identify
     * content that is strictly similar, actually the same image. At least in theory
     * this value is not dependent on image codec, that is, JPEG or PNG, only on
     * content. Anyway, different image qualities can generate different hash
     * values.
     * <p>
     * This value cannot be used to asses at what degree two images are
     * (dis)similar.
     * 
     * @param imageFile
     *            image file.
     * @return image perceptual hash.
     * @throws IOException
     *             if image file reading or process fail.
     */
    String perceptualHash(File imageFile) throws IOException;

    <T> T info(File imageFile, String attribute, Class<T> type) throws IOException;

    boolean isOpaque(File imageFile) throws IOException;
}
