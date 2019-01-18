package com.kidscademy.media;

import java.io.File;

public class MediaFile
{
  private File base;
  private File working;
  private int version;

  public MediaFile(String path)
  {
    base = new File(path);
    while(new File(path).exists()) {
      ++version;
    }
  }

  public File getBaseFile()
  {
    return base;
  }

  public File getWorkingFile()
  {
    return working;
  }

  public void undo()
  {

  }

  public void commit()
  {

  }

  public void delete()
  {

  }
}
