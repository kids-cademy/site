package com.kidscademy.media;

import java.io.File;
import java.io.IOException;

public interface ImageProcessor
{
  void saveObjectPicture(File uploadFile, File pictureFile) throws IOException;

  void saveObjectIcon(File uploadFile, File iconFile) throws IOException;

  void saveObjectThumbnail(File uploadFile, File thumbnailFile) throws IOException;

  void createObjectIcon(File uploadFile, File iconFile) throws IOException;

  void generateXAxis(File imageFile, int canvasWidth, int canvasHeight, String xaxisColor) throws IOException;

  void generateRainbowGradient(File imageFile, int canvasWidth, int canvasHeight) throws IOException;

  void crop(File imageFile, int width, int height, int xoffset, int yoffset) throws IOException;

  void compose(File imageFile, File maskFile, Compose compose) throws IOException;

  void overlap(File imageFile, File... overlapImageFiles) throws IOException;

  public static enum Compose
  {
    SRCIN("srcin");

    private String value;

    private Compose(String value)
    {
      this.value = value;
    }

    public String value()
    {
      return value;
    }
  }
}
