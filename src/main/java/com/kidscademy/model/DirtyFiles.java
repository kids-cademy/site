package com.kidscademy.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import js.log.Log;
import js.log.LogFactory;

/**
 * Dirty files is used by {@link SyncService} to hold lists of fresh and stale files. A fresh file is one updated on
 * server repository whereas a stale file is one removed from server repository but present on application storage.
 * 
 * @author Iulian Rotaru
 */
public class DirtyFiles
{
  private static final Log log = LogFactory.getLog(DirtyFiles.class);

  /** Files that updated or newly created on server repository. */
  private List<String> freshFiles = new ArrayList<String>();

  /** Files existing on device storage but removed from server repository. */
  private List<String> staleFiles = new ArrayList<String>();

  /** Cache for instance string representation. */
  private String string;

  public void addFreshFile(String freshFile)
  {
    log.debug("Add fresh file |%s|.", freshFile);
    freshFiles.add(freshFile);
  }

  public void addStaleFile(File staleFile)
  {
    addStaleFile(staleFile.getPath());
  }

  public void addStaleFile(String staleFile)
  {
    log.debug("Add stale file |%s|.", staleFile);
    staleFiles.add(staleFile);
  }

  public List<String> freshFiles()
  {
    return freshFiles;
  }

  public List<String> staleFiles()
  {
    return staleFiles;
  }

  public boolean isEmpty()
  {
    return staleFiles.isEmpty() && freshFiles.isEmpty();
  }

  @Override
  public String toString()
  {
    if(string == null) {
      StringBuilder builder = new StringBuilder();
      builder.append("stale files\r\n");
      for(String remove : staleFiles) {
        builder.append(remove);
        builder.append("\r\n");
      }
      builder.append("fresh files\r\n");
      for(String update : freshFiles) {
        builder.append(update);
        builder.append("\r\n");
      }
      string = builder.toString();
    }
    return string;
  }

  public void dump()
  {
    System.out.println(toString());
  }
}
