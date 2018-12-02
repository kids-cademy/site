package com.kidscademy;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.SortedMap;

import js.annotation.Public;
import js.annotation.Remote;
import js.io.FilesInputStream;

import com.kidscademy.model.DirtyFiles;
import com.kidscademy.model.Excludes;

@Remote
@Public
public interface BuildManager
{
  boolean incrementBuildNumber(File projectDir) throws IOException;

  /**
   * Get files to be updated on target directory, compared with given source files, and remove obsolete (staled) target
   * files. Create a hash map for all files from target directory, similar to given source files hash map then compare
   * these hash maps using next rules:
   * <ul>
   * <li>If file name is present on both hash maps consider it dirty if source and target files have not the same
   * message digest.
   * <li>If file exists only on source files consider it dirty.
   * <li>If file exists only on target files consider it stale and remove it.
   * </ul>
   * Files hash map, both sources and targets have the same structure: relative file path is used as hash key and file
   * content digest is the value. Note that this method expect file path to be Unix like, i.e. uses slash as separator.
   * <h5>Remove State File</h5>
   * <p>
   * If parameter <code>removeStaleFiles</code> is true, all descendant files from target directory that are not present
   * into <code>sourceFiles</code> map are permanently removed. Depending on usage pattern, this may be potentially
   * harmful for which reason removing stale files is optional.
   * 
   * @param projectDir project root directory path, relative to web server document root,
   * @param excludes files excluded from generated dirty files,
   * @param sourceFiles source files hash map,
   * @param removeStaleFiles remove state files, potential harmful, see description.
   * @return dirty files list.
   * @throws IOException if target directory scanning fails.
   */
  DirtyFiles getDirtyFiles(File projectDir, Excludes excludes, SortedMap<String, byte[]> sourceFiles) throws IOException;

  /**
   * Synchronize files archive for application identified by given project path.
   * 
   * @param projectDir project root directory path, relative to web server document root,
   * @param mobileExcludes files excluded from generated mobile files list,
   * @param filesArchive files archive.
   * @throws IOException if read from socket stream or writing on target directory fails.
   */
  boolean synchronize(File projectDir, List<String> staleFiles, FilesInputStream freshFiles) throws IOException;

  boolean generateTimestampFilesList(File projectDir, Excludes excludes) throws IOException;

  /**
   * Deploy development repository of the named project to production server. At the moment this operation is performed
   * development repository should contain verified data. Be aware that production server deploy cannot be undone.
   * 
   * @param productionServer production server URL,
   * @param projectName project name,
   * @param dirtyExcludes files excluded from dirty files,
   * @param mobileExcludes files excluded from mobile files list.
   * @throws IOException on IO operation fail.
   */
  void deploy(String productionServer, String projectName, Excludes dirtyExcludes, Excludes mobileExcludes) throws IOException;
}