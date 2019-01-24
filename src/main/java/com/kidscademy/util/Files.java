package com.kidscademy.util;

import com.kidscademy.atlas.AtlasObject;

import js.util.Params;
import js.util.Strings;

public final class Files extends js.util.Files
{
  private Files()
  {
  }

  public static String mediaSrc(AtlasObject object, String mediaFile)
  {
    return mediaSrc(object.getDtype(), object.getName(), mediaFile);
  }

  public static String mediaSrc(String collectionName, String objectName, String mediaFile)
  {
    Params.notNullOrEmpty(collectionName, "Collection name");
    Params.notNullOrEmpty(objectName, "Object name");
    if(mediaFile == null) {
      return null;
    }
    return Strings.concat("/media/atlas/", collectionName, "/", objectName, '/', mediaFile);
  }

  public static String mediaFile(String path)
  {
    return path != null ? path.substring(path.lastIndexOf('/') + 1) : null;
  }
}
