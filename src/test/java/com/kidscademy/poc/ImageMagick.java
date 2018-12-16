package com.kidscademy.poc;

import java.io.File;

import org.im4java.core.ConvertCmd;
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
  public void convertToPng8Alpha() throws Exception
  {
    ConvertCmd cmd = new ConvertCmd();

    IMOperation op = new IMOperation();
    op.addImage(new File("fixture/logo.png").getAbsolutePath());
    op.type("PaletteAlpha");
    op.addImage(new File("fixture/logo.8.png").getAbsolutePath());

    cmd.run(op);
  }
}
