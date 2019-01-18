package com.kidscademy;

import java.io.File;

import js.annotation.ContextParam;

public class CT
{
  @ContextParam("objects.repository.path")
  private static File REPOSITORY_DIR;

  @ContextParam("image.magick.path")
  private static String IMAGE_MAGICK_PATH;

  public static File repositoryDir()
  {
    return REPOSITORY_DIR;
  }

  public static String imageMagickPath()
  {
    return IMAGE_MAGICK_PATH;
  }
}
