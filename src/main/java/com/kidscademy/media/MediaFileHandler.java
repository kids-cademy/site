package com.kidscademy.media;

import java.io.File;
import java.io.IOException;

import com.kidscademy.CT;

import js.util.Files;
import js.util.Strings;

public class MediaFileHandler
{
  private String path;
  private File file;
  private File source;
  private File target;

  private File dir;
  private String basename;
  private String extension;
  private int version;

  public MediaFileHandler(String collection, String objectName, String mediaFile)
  {
    path = Strings.concat(collection, '/', objectName, '/', mediaFile);
    file = new File(CT.repositoryDir(), path);
    dir = file.getParentFile();
    basename = Files.basename(file);
    extension = Files.getExtension(file);

    version = 0;
    while(file(dir, basename, extension, version).exists()) {
      ++version;
    }
    --version;

    source = file(dir, basename, extension, version);
  }

  public String path()
  {
    return path;
  }

  public File file()
  {
    return source;
  }

  public File target()
  {
    if(target == null) {
      target = file(dir, basename, extension, version + 1);
    }
    return target;
  }

  public void rollback()
  {
    if(version < 1) {
      return;
    }

    file(dir, basename, extension, version).delete();

    --version;
    source = file(dir, basename, extension, version);
    target = null;
  }

  public void commit() throws IOException
  {
    source = file(dir, basename, extension, 0);
    target = null;

    File mostUpdatedWorkingFile = file(dir, basename, extension, version);
    Files.copy(mostUpdatedWorkingFile, source);

    int version = 1;
    while(file(dir, basename, extension, version).delete()) {
      ++version;
    }
  }

  public void reset()
  {
    source = file(dir, basename, extension, 0);
    target = null;

    int version = 1;
    while(file(dir, basename, extension, version).delete()) {
      ++version;
    }
  }

  public void delete()
  {
    source = null;
    target = null;

    int version = 0;
    while(file(dir, basename, extension, version).delete()) {
      ++version;
    }
  }

  public void mkdirs()
  {
    source.getParentFile().mkdirs();
  }

  private static File file(File dir, String basename, String extension, int version)
  {
    StringBuilder fileName = new StringBuilder();
    fileName.append(basename);
    if(version > 0) {
      fileName.append('_');
      fileName.append(version);
    }
    fileName.append('.');
    fileName.append(extension);
    return new File(dir, fileName.toString());
  }
}
