package com.kidscademy.poc;

import java.io.File;
import java.io.IOException;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.ProcessStarter;
import org.junit.BeforeClass;
import org.junit.Test;

public class ImageMagick
{
  @BeforeClass
  public static void beforeClass()
  {
    ProcessStarter.setGlobalSearchPath("C:\\Program Files\\ImageMagick-6.9.9-Q16-HDRI");
  }

  @Test
  public void resize() throws Exception
  {
    ConvertCmd cmd = new ConvertCmd();

    // create the operation, add images and operators/options
    IMOperation op = new IMOperation();
    op.addImage(new File("fixture/source.png").getAbsolutePath()); // source file
    // op.resize(800, 600);
    op.resize(96);
    // of op.resize(800); // and height calculate automatically
    op.addImage(new File("fixture/resized.jpg").getAbsolutePath()); // destination file file

    // execute the operation
    cmd.run(op);
  }

  @Test
  public void resizeBestFit() throws Exception
  {
    IMOperation op = new IMOperation();
    op.addImage(new File("fixture/picture_hq.jpg").getAbsolutePath());
    op.crop(560, 560, 180, 0);
    op.resize(96, 96);
    op.quality(80.0);
    op.addImage(new File("fixture/icon.jpg").getAbsolutePath());

    ConvertCmd cmd = new ConvertCmd();
    cmd.run(op);
  }

  @Test
  public void convertToPng8Alpha() throws Exception
  {
    ConvertCmd cmd = new ConvertCmd();

    IMOperation op = new IMOperation();
    op.addImage(new File("fixture/logo.png").getAbsolutePath());
    op.type("PaletteAlpha");
    op.addImage(new File("fixture/logo.8.png").getAbsolutePath());

    cmd.run(op);
  }

  @Test
  public void drawLine() throws IOException, InterruptedException, IM4JavaException
  {
    IMOperation op = new IMOperation();
    op.size(800, 160);
    op.addRawArgs("xc:transparent");
    op.fill("white");
    op.stroke("#00B359");
    float y = (160 - 1) / 2;
    op.draw(String.format("line 0,%f,%d,%f", y, 800, y));
    op.addImage(new File("fixture/line.png").getAbsolutePath());

    ConvertCmd cmd = new ConvertCmd();
    cmd.run(op);
  }

  @Test
  public void createRainbowGradient() throws IOException, InterruptedException, IM4JavaException
  {
    IMOperation op = new IMOperation();
    // reverse order of width and height because of -rotate 90
    op.size(160, 800);
    op.addRawArgs("xc:red");
    op.colorspace("HSB");
    op.addRawArgs("gradient:");
    op.compose("CopyRed");
    op.composite();
    op.colorspace("RGB");
    op.rotate(90.0);
    op.addImage(new File("fixture/rainbow.png").getAbsolutePath());

    ConvertCmd cmd = new ConvertCmd();
    cmd.run(op);
  }
}
