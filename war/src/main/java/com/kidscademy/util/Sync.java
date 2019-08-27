package com.kidscademy.util;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import js.core.Factory;
import js.io.FilesIterator;
import js.json.Json;
import js.log.Log;
import js.log.LogFactory;
import js.util.Files;

import com.kidscademy.model.DirtyFiles;
import com.kidscademy.model.Excludes;

public class Sync
{
  /** Class logger. */
  private static final Log log = LogFactory.getLog(Sync.class);

  /** Build number path, relative to repository root. */
  public static final String PATH_BUILD_NUMBER = "build-number.json";

  /** Path relative to repository root to repository files list. */
  public static final String PATH_FILES_LIST = "files-list.json";

  /** Prevent default constructor synthesis but allow sub-classing. */
  protected Sync()
  {
  }

  public static DirtyFiles getDirtyFiles(File targetDir, Excludes excludes, SortedMap<String, byte[]> sourceFiles) throws IOException
  {
    log.debug("getDirtyFiles(File, Excludes, SortedMap<String, byte[]>)");

    DirtyFiles dirtyFiles = new DirtyFiles();
    SortedMap<String, byte[]> targetFiles = createDigestFilesList(targetDir);

    Iterator<Map.Entry<String, byte[]>> sourceFilesIterator = sourceFiles.entrySet().iterator();
    Iterator<Map.Entry<String, byte[]>> targetFilesIterator = targetFiles.entrySet().iterator();

    SOURCE_FILES_LOOP: while(sourceFilesIterator.hasNext()) {
      if(!targetFilesIterator.hasNext()) {
        break;
      }
      Map.Entry<String, byte[]> sourceFileEntry = sourceFilesIterator.next();
      if(excludes.reject(sourceFileEntry.getKey())) {
        continue;
      }
      Map.Entry<String, byte[]> targetFileEntry = targetFilesIterator.next();
      while(excludes.reject(targetFileEntry.getKey())) {
        if(targetFilesIterator.hasNext()) {
          targetFileEntry = targetFilesIterator.next();
        }
        else {
          break SOURCE_FILES_LOOP;
        }
      }

      if(sourceFileEntry.getKey().equals(targetFileEntry.getKey())) {
        // here files are equals; check file digest to see if changed
        if(!Arrays.equals(sourceFileEntry.getValue(), targetFileEntry.getValue())) {
          dirtyFiles.addFreshFile(sourceFileEntry.getKey());
        }
        continue;
      }

      // source and target iterators synchronization is lost; attempt to regain it

      // remove target file by file till found one existing into source files or end of target files
      while(!sourceFiles.containsKey(targetFileEntry.getKey())) {
        // target file is not present on source and need to be erased
        dirtyFiles.addStaleFile(targetFileEntry.getKey());
        if(!targetFilesIterator.hasNext()) {
          break SOURCE_FILES_LOOP;
        }
        targetFileEntry = targetFilesIterator.next();
      }

      // here we know the target file is present into sources
      // add sources file by file until reach the target
      for(;;) {
        if(sourceFileEntry.getKey().equals(targetFileEntry.getKey())) {
          // here source and target files are equals; check file digest to see if changed
          if(!Arrays.equals(sourceFileEntry.getValue(), targetFileEntry.getValue())) {
            dirtyFiles.addFreshFile(sourceFileEntry.getKey());
          }
          continue SOURCE_FILES_LOOP;
        }
        dirtyFiles.addFreshFile(sourceFileEntry.getKey());
        if(!sourceFilesIterator.hasNext()) {
          break SOURCE_FILES_LOOP;
        }
        sourceFileEntry = sourceFilesIterator.next();
      }
    }

    // if there are more unprocessed source files just add to dirty files list
    while(sourceFilesIterator.hasNext()) {
      dirtyFiles.addFreshFile(sourceFilesIterator.next().getKey());
    }

    // if there are more unprocessed target files consider them as stale files scheduled for remove
    while(targetFilesIterator.hasNext()) {
      dirtyFiles.addStaleFile(targetFilesIterator.next().getKey());
    }

    return dirtyFiles;
  }

  public static SortedMap<String, byte[]> createDigestFilesList(File repositoryDir) throws IOException
  {
    log.debug("Create digest repository files list for |%s|.", repositoryDir);

    SortedMap<String, byte[]> filesMap = new TreeMap<String, byte[]>();
    for(String file : FilesIterator.getRelativeNamesIterator(repositoryDir)) {
      filesMap.put(Files.path2unix(file), Files.getFileDigest(new File(repositoryDir, file)));
    }
    return filesMap;
  }

  public static SortedMap<String, Long> createTimestampFilesList(File repositoryDir) throws IOException
  {
    log.debug("Create timestamp files list for repository |%s|.", repositoryDir);

    SortedMap<String, Long> filesList = new TreeMap<String, Long>();
    for(String path : FilesIterator.getRelativeNamesIterator(repositoryDir)) {
      path = Files.path2unix(path);
      final File file = new File(repositoryDir, path);
      filesList.put(path, file.lastModified());
    }
    return filesList;
  }

  public static SortedMap<String, Long> createTimestampFilesList(File repositoryDir, Excludes excludes) throws IOException
  {
    log.debug("Create timestamp files list for repository |%s|.", repositoryDir);

    SortedMap<String, Long> filesList = new TreeMap<String, Long>();
    for(String path : FilesIterator.getRelativeNamesIterator(repositoryDir)) {
      path = Files.path2unix(path);
      if(excludes.reject(path)) {
        continue;
      }
      final File file = new File(repositoryDir, path);
      filesList.put(path, file.lastModified());
    }
    return filesList;
  }

  public static void incrementBuildNumber(File repositoryDir) throws IOException
  {
    log.debug("Increment build number for repository |%s|.", repositoryDir);

    Json json = Factory.getInstance(Json.class);
    File buildNumberFile = new File(repositoryDir, PATH_BUILD_NUMBER);
    int buildNumber = 0;
    if(buildNumberFile.exists()) {
      buildNumber = json.parse(new FileReader(buildNumberFile), Integer.class);
    }
    buildNumber++;
    json.stringify(new FileWriter(buildNumberFile), buildNumber);
  }

  public static void generateTimestampFilesList(File repositoryDir, Excludes excludes) throws IOException
  {
    log.debug("Generate timestamp files list for repository |%s|.", repositoryDir);

    SortedMap<String, Long> files = createTimestampFilesList(repositoryDir, excludes);
    files.put(Sync.PATH_FILES_LIST, System.currentTimeMillis());

    FileWriter writer = new FileWriter(new File(repositoryDir, Sync.PATH_FILES_LIST));
    Json json = Factory.getInstance(Json.class);
    json.stringify(writer, files);
    writer.close();
  }
}
