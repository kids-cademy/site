package com.kidscademy.media;

import java.io.File;
import java.io.IOException;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;

import js.lang.BugError;

public class ImageProcessorImpl implements ImageProcessor
{
  private static final int PICTURE_WIDTH = 920;
  private static final int PICTURE_HEIGHT = 560;
  private static final double PICTURE_QUALITY = 40;

  private static final int ICON_WIDTH = 96;
  private static final int ICON_HEIGHT = 96;
  private static final double ICON_QUALITY = 80;

  // thumbnail has restriction only on width, height is allowed to scale
  private static final int THUMBNAIL_WIDTH = 560;

  @Override
  public void saveObjectPicture(File uploadFile, File pictureFile) throws IOException
  {
    IMOperation op = new IMOperation();
    op.addImage(uploadFile.getAbsolutePath());
    op.resize(PICTURE_WIDTH, PICTURE_HEIGHT);
    op.quality(PICTURE_QUALITY);
    op.addImage(pictureFile.getAbsolutePath());
    execute(op);
  }

  @Override
  public void saveObjectIcon(File uploadFile, File iconFile) throws IOException
  {
    IMOperation op = new IMOperation();
    op.addImage(uploadFile.getAbsolutePath());
    op.resize(ICON_WIDTH, ICON_HEIGHT);
    op.quality(ICON_QUALITY);
    op.addImage(iconFile.getAbsolutePath());
    execute(op);
  }

  @Override
  public void saveObjectThumbnail(File uploadFile, File thumbnailFile) throws IOException
  {
    IMOperation op = new IMOperation();
    op.addImage(uploadFile.getAbsolutePath());
    op.resize(THUMBNAIL_WIDTH);
    op.addImage(thumbnailFile.getAbsolutePath());
    execute(op);
  }

  @Override
  public void createObjectIcon(File uploadFile, File iconFile) throws IOException
  {
    int width = PICTURE_HEIGHT;
    int height = PICTURE_HEIGHT;
    int xoffset = (PICTURE_WIDTH - PICTURE_HEIGHT) / 2;
    int yoffset = 0;

    IMOperation op = new IMOperation();
    op.addImage(uploadFile.getAbsolutePath());
    op.crop(width, height, xoffset, yoffset);
    op.resize(ICON_WIDTH, ICON_HEIGHT);
    op.quality(ICON_QUALITY);
    op.addImage(iconFile.getAbsolutePath());
    execute(op);
  }

  @Override
  public void generateXAxis(File imageFile, int canvasWidth, int canvasHeight, String xaxisColor) throws IOException
  {
    // convert -size ${canvasWidth}x${canvasHeight} xc:transparent -fill white -stroke ${xaxisColor} -draw "line 0,69.5,960,69.5" ${imageFile}
    IMOperation op = new IMOperation();
    op.size(canvasWidth, canvasHeight);
    op.addRawArgs("xc:transparent");
    op.fill("white");
    op.stroke(xaxisColor);
    float y = (canvasHeight - 1) / 2.0F;
    op.draw(String.format("line 0,%f,%d,%f", y, canvasWidth, y));
    op.addImage(imageFile.getAbsolutePath());
    execute(op);
  }

  @Override
  public void generateRainbowGradient(File imageFile, int canvasWidth, int canvasHeight) throws IOException
  {
    // convert -size ${canvasHeight}x${canvasWidth} xc:red -colorspace HSB gradient: -compose CopyRed -composite -colorspace RGB -rotate 90 ${imageFile}
    IMOperation op = new IMOperation();
    // reverse order of width and height because of -rotate 90
    op.size(canvasHeight, canvasWidth);
    op.addRawArgs("xc:red");
    op.colorspace("HSB");
    op.addRawArgs("gradient:");
    op.compose("CopyRed");
    op.composite();
    op.colorspace("RGB");
    op.rotate(90.0);
    op.addImage(imageFile.getAbsolutePath());
    execute(op);
  }

  @Override
  public void crop(File imageFile, int width, int height, int xoffset, int yoffset) throws IOException
  {
    // convert ${imageFile} -crop ${width}x${height}+${xoffset}+${yoffset}
    IMOperation op = new IMOperation();
    op.addImage(imageFile.getAbsolutePath());
    op.crop(width, height, xoffset, yoffset);
    op.addImage(imageFile.getAbsolutePath());
    execute(op);
  }

  @Override
  public void compose(File imageFile, File maskFile, Compose compose) throws IOException
  {
    // convert -composite -compose ${compose} ${imageFile} ${maskFile} ${imageFile}
    IMOperation op = new IMOperation();
    op.composite();
    op.compose(compose.value());
    op.addImage(imageFile.getAbsolutePath());
    op.addImage(maskFile.getAbsolutePath());
    op.addImage(imageFile.getAbsolutePath());
    execute(op);
  }

  @Override
  public void overlap(File imageFile, File... overlapImageFiles) throws IOException
  {
    for(File overlapImageFile : overlapImageFiles) {
      // convert -composite ${imageFile} ${overlapImageFile} ${imageFile}
      IMOperation op = new IMOperation();
      op.addImage(imageFile.getAbsolutePath());
      op.addImage(overlapImageFile.getAbsolutePath());
      op.composite();
      op.addImage(imageFile.getAbsolutePath());
      execute(op);
    }
  }

  // ----------------------------------------------------------------------------------------------

  private static void execute(IMOperation imageOperation) throws IOException
  {
    try {
      ConvertCmd cmd = new ConvertCmd();
      cmd.run(imageOperation);
    }
    catch(InterruptedException | IM4JavaException e) {
      new BugError(e);
    }
  }
}
