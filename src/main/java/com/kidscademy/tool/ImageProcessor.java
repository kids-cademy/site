package com.kidscademy.tool;

import java.io.File;
import java.io.IOException;

public interface ImageProcessor {
    ImageInfo getImageInfo(File source) throws IOException;

    void saveObjectPicture(File uploadFile, File pictureFile) throws IOException;

    void saveObjectIcon(File uploadFile, File iconFile) throws IOException;

    void saveObjectThumbnail(File uploadFile, File thumbnailFile) throws IOException;

    void createObjectIcon(File pictureFile, File iconFile) throws IOException;

    void generateXAxis(File imageFile, int canvasWidth, int canvasHeight, String xaxisColor) throws IOException;

    void generateRainbowGradient(File imageFile, int canvasWidth, int canvasHeight) throws IOException;

    void crop(File imageFile, File targetFile, int width, int height, int xoffset, int yoffset) throws IOException;

    void compose(File imageFile, File maskFile, ImageCompose compose) throws IOException;

    void overlap(File imageFile, File... overlapImageFiles) throws IOException;
    
    String perceptualHash(File imageFile) throws IOException;
}
