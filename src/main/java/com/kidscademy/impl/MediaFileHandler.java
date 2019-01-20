package com.kidscademy.impl;

import java.io.File;
import java.io.IOException;

import js.util.Files;
import js.util.Params;
import js.util.Strings;

/**
 * Media file transaction handler for chained processing. A media processing transaction can span multiple transforms: every transform reads media from a source
 * file and stores result in a target file. Current transform source is previous transform output so that transforms are chained. Transformed media files are
 * versioned incrementally. On commit latest version is copied into original / base file and all versions removed. On roolback version is decremented and file
 * removed.
 * <p>
 * There are two abstractions to identify source and target: absolute <code>file</code> and root relative URL named <code>path</code> in this context. Files are
 * used by media processors whereas paths are used by web views to render media content. Root relative URL is starts with path separator and media repository
 * context. Absolute file is concatenated from server document root directory and root relative URL.
 * <p>
 * Usage is straightforward: create handler instance and pass it to media processor. Processor uses {@link #source()} to read media content and store transform
 * result to {@link #target()}. Please note that target file is not created on file system at handler instance creation; it is created by media processor and
 * till processing complete it does not exists.
 * 
 * <pre>
 * MediaFileHandler handler = new MediaFileHandler(objectName, "sample.mp3");
 * // here handler.target() returns a valid file object but not existing on file system
 * audioProcessor.normalizeLevel(handler);
 * // after media processing completes target file is create and can be returned to user interface
 * return handler.targetPath();
 * </pre>
 * 
 * @author Iulian Rotaru
 */
public class MediaFileHandler
{
  /** Absolute file used as source for media transform. */
  private File source;
  /** Root relative URL for source used by web views to render media content. */
  private String sourcePath;
  /** Absolute file used as media transform target - where processing result is stored. It does not actually exists on file system till processor creates it. */
  private File target;
  /** Root relative URL for target used by web views to render media content. It does not actually exists on file system till processor creates it. */
  private String targetPath;

  /** Base directory stores all source / target files and is absolute file. */
  private File baseDir;
  /** Base path is the root relative URL without last segment, that is, without file name. */
  private String basePath;

  /** Base name is the media file name without extension. */
  private String basename;
  /** Media file extension. */
  private String extension;
  /**
   * Latest version denotes last media transform. This file version exist created on file system by previous transform. It is the source file; target file
   * version is this value incremented by one.
   */
  private int version;

  /**
   * Initialize internal state from given arguments and version value by scanning file system.
   * 
   * @param objectName object name,
   * @param mediaFile media file name, without any path components.
   * @throws IllegalArgumentException if arguments are null or empty or media file has no extension.
   */
  public MediaFileHandler(String objectName, String mediaFile) throws IllegalArgumentException
  {
    Params.notNullOrEmpty(objectName, "Object name");
    Params.notNullOrEmpty(objectName, "Media file");

    String path = path(objectName, mediaFile);
    // infer base path from path in order to avoid hard coded path dependency
    basePath = path.substring(0, path.lastIndexOf('/') + 1);

    File file = file(path);
    baseDir = file.getParentFile();
    basename = Files.basename(mediaFile);
    extension = Files.getExtension(mediaFile);
    if(extension.isEmpty()) {
      throw new IllegalArgumentException(Strings.format("Invalid media file |%s|. Missing extension.", mediaFile));
    }

    // scan files system in order to detect version
    version = 0;
    while(file(baseDir, basename, version, extension).exists()) {
      ++version;
    }
    if(version > 0) {
      --version;
    }
  }

  /**
   * Upload file to media repository and remove any existing version, if any. Takes care to create parent directories.
   * 
   * @param file source file.
   * @throws IllegalArgumentException if <code>file</code> argument is not an existing regular file.
   * @throws IOException if IO operation fails.
   */
  public void upload(File file) throws IOException
  {
    Params.isFile(file, "Source file");
    File source = source();
    File parentDir = source.getParentFile();

    if(!parentDir.exists() && !parentDir.mkdirs()) {
      throw new IOException(Strings.format("Fail to create parent directory |%s.|", parentDir));
    }
    if(source.exists()) {
      delete();
    }
    if(!file.renameTo(source)) {
      throw new IOException(Strings.format("Fail to move source file |%s| to |%s|", file, source));
    }
  }

  /**
   * Get absolute file for processing source. Source is the file from where processor reads media content.
   * 
   * @return source absolute file.
   * @see #source
   */
  public File source()
  {
    if(source == null) {
      source = file(baseDir, basename, version, extension);
    }
    return source;
  }

  /**
   * Get root relative URL for processing source. Source is the file from where processor reads media content. This path is used by web views to render media
   * content.
   * 
   * @return root relative URL for source file.
   * @see #sourcePath
   */
  public String sourcePath()
  {
    if(sourcePath == null) {
      sourcePath = path(basePath, basename, version, extension);
    }
    return sourcePath;
  }

  /**
   * Get absolute file for processing target. Target is the file on which processor writes transformed media content. It does not actually exists on file system
   * till processor creates it.
   * 
   * @return target absolute file.
   * @see #target
   */
  public File target()
  {
    if(target == null) {
      target = file(baseDir, basename, version + 1, extension);
    }
    return target;
  }

  /**
   * Get root relative URL for processing target. Target is the file on which processor writes transformed media content. This path is used by web views to
   * render media content.
   * 
   * @return root relative URL for target file.
   * @see #targetPath
   */
  public String targetPath()
  {
    if(targetPath == null) {
      targetPath = path(basePath, basename, version + 1, extension);
    }
    return targetPath;
  }

  /**
   * Not atomic commit media file transaction. This method copy latest version to base file - base file is that with version zero, then remove all versions.
   * Reset {@link #version} value to zero. After this method execution all chained transforms from transaction are stored into base file.
   * <p>
   * If there is no version files, that is, {@link #version} value is zero, this method does nothing. If IO operation fails commit state is undefined.
   * 
   * @throws IOException if IO operation fail.
   */
  public void commit() throws IOException
  {
    if(version < 1) {
      return;
    }

    File target = target();

    // latest version is target file, if exists on file system - created by media processor
    // otherwise latest version is current source file
    File latestVersionFile = target;
    if(!latestVersionFile.exists()) {
      latestVersionFile = source();
    }
    // copy latest version to base file, that is, with version 0
    Files.copy(latestVersionFile, file(baseDir, basename, 0, extension));

    if(target.exists() && !target.delete()) {
      throw new IOException(String.format("Unable to remove media target file with version |%d|.", version + 1));
    }

    for(; version > 0; --version) {
      if(!file(baseDir, basename, version, extension).delete()) {
        throw new IOException(String.format("Unable to remove media file with version |%d|.", version));
      }
    }

    this.version = 0;
    this.source = null;
    this.target = null;
  }

  /**
   * Remove latest version source and target files from file system and decrease this handler version by one. If there is no version files, that is,
   * {@link #version} value is zero, this method does nothing.
   * 
   * @throws IOException if file remove operation fails.
   */
  public void rollback() throws IOException
  {
    if(version < 1) {
      return;
    }

    File target = target();
    if(target.exists() && !target.delete()) {
      throw new IOException(String.format("Unable to remove media target file with version |%d|.", version + 1));
    }
    if(!source().delete()) {
      throw new IOException(String.format("Unable to remove media source file with version |%d|.", version));
    }

    --version;
    this.source = null;
    this.target = null;
  }

  /**
   * Remove <code>ALL</code> files, including base version zero from file system and reset version value. This method remove every file version, one by one in
   * sequence. If a file remove operation fails all lower versions are left on file system; {@link #version} value is guaranteed to reflect this incomplete
   * remove. If this method is invoked after media processor has been created target file, remove it too.
   * 
   * @throws IOException if a file remove operation fails.
   */
  public void delete() throws IOException
  {
    File target = target();
    if(target.exists() && !target.delete()) {
      throw new IOException(String.format("Unable to remove media target file with version |%d|.", version + 1));
    }

    for(; version >= 0; --version) {
      if(!file(baseDir, basename, version, extension).delete()) {
        throw new IOException(String.format("Unable to remove media file with version |%d|.", version));
      }
    }

    this.version = 0;
    this.source = null;
    this.target = null;
  }

  // ----------------------------------------------------------------------------------------------
  // UTILITY FUNCTIONS

  private static File file(File dir, String basename, int version, String extension)
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

  private static String path(String basePath, String basename, int version, String extension)
  {
    StringBuilder path = new StringBuilder();
    path.append(basePath);
    path.append(basename);
    if(version > 0) {
      path.append('_');
      path.append(version);
    }
    path.append('.');
    path.append(extension);
    return path.toString();
  }

  // ----------------------------------------------------------------------------------------------
  // FACTORY METHODS

  private static File REPOSIOTRY_DIR = new File(System.getProperty("catalina.base") + "/webapps");

  public static String path(String objectName, String file)
  {
    if(file == null) {
      return null;
    }
    return Strings.concat("/media/atlas/instruments/", objectName, '/', file);
  }

  public static File file(String path)
  {
    // repository dir := ${catalina.base}/webapps
    // path := /media/atlas/instruments/object/file
    return new File(REPOSIOTRY_DIR, path);
  }

  public static File file(String objectName, String file)
  {
    // repository dir := ${catalina.base}/webapps
    // path := /media/atlas/instruments/object/file
    return new File(REPOSIOTRY_DIR, path(objectName, file));
  }
}
