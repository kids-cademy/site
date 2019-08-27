package com.kidscademy.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.SortedMap;

import js.annotation.Inject;
import js.core.AppContext;
import js.core.Factory;
import js.io.FilesInputStream;
import js.io.FilesOutputStream;
import js.io.StreamHandler;
import js.log.Log;
import js.log.LogFactory;
import js.util.Files;

import com.kidscademy.BuildManager;
import com.kidscademy.model.BuildException;
import com.kidscademy.model.DirtyFiles;
import com.kidscademy.model.Excludes;
import com.kidscademy.util.Sync;

class BuildManagerImpl implements BuildManager
{
  /** Class logger. */
  private static final Log log = LogFactory.getLog(BuildManagerImpl.class);

  /** Directory of kids (a)cademy development documents root. */
  public static File DOC_ROOT = new File("/var/www/vhosts/");

  @Inject
  private AppContext context;
  
  @Override
  public boolean incrementBuildNumber(File projectDir) throws IOException
  {
    Sync.incrementBuildNumber(docRoot(projectDir));
    return true;
  }

  @Override
  public DirtyFiles getDirtyFiles(File projectDir, Excludes excludes, SortedMap<String, byte[]> sourceFiles) throws IOException
  {
    excludes.add(Sync.PATH_BUILD_NUMBER);
    excludes.add(Sync.PATH_FILES_LIST);
    return Sync.getDirtyFiles(docRoot(projectDir), excludes, sourceFiles);
  }

  @Override
  public boolean synchronize(File projectDir, List<String> staleFiles, FilesInputStream freshFiles) throws IOException
  {
    File projectDocRoot = docRoot(projectDir);
    log.debug("Synchronize files archive on target directory |%s|.", projectDocRoot);

    for(File sourceFile : freshFiles) {
      File targetFile = new File(projectDocRoot, sourceFile.getPath());
      freshFiles.copy(targetFile);
      log.trace("Synchronize file |%s|.", targetFile);
    }

    for(String staleFile : staleFiles) {
      File targetFile = new File(projectDocRoot, staleFile);
      Files.delete(targetFile);
      log.trace("Remove target file |%s|.", targetFile);
    }

    return true;
  }

  @Override
  public boolean generateTimestampFilesList(File projectDir, Excludes excludes) throws IOException
  {
    excludes.add(Sync.PATH_BUILD_NUMBER);
    excludes.add(Sync.PATH_FILES_LIST);
    Sync.generateTimestampFilesList(docRoot(projectDir), excludes);
    return true;
  }

  @Override
  public void deploy(String productionServer, String projectName, Excludes dirtyExcludes, Excludes mobileExcludes) throws IOException
  {
    // project name is the same on both development and production server
    log.debug("Deploy |%s| to production server |%s|.", projectName, productionServer);

    com.kidscademy.client.BuildManager buildManager = Factory.getRemoteInstance(productionServer, com.kidscademy.client.BuildManager.class);

    final File developmentDocRoot = docRoot("dev.kids-cademy.com/" + projectName);
    SortedMap<String, byte[]> developmentFiles = Sync.createDigestFilesList(developmentDocRoot);

    final File productionDir = new File("data.kids-cademy.com/" + projectName);

    // a synchronization transaction has 4 steps:
    // 1. increment target build number to signal target repository dirty to mobile devices
    // 2. generate dirty files comparing source digest files list with target
    // 3. invoke the actual synchronization that does the actual changes on target repository
    // 4. generate timestamp files list used for mobile devices updates

    buildManager.incrementBuildNumber(productionDir);

    final DirtyFiles dirtyFiles = buildManager.getDirtyFiles(productionDir, dirtyExcludes, developmentFiles);

    buildManager.synchronize(productionDir, dirtyFiles.staleFiles(), new StreamHandler<FilesOutputStream>(FilesOutputStream.class)
    {
      @Override
      protected void handle(FilesOutputStream files) throws IOException
      {
        files.addFiles(developmentDocRoot, dirtyFiles.freshFiles());
      }
    });

    buildManager.generateTimestampFilesList(productionDir, mobileExcludes);
  }

  private static File docRoot(File projectDir) throws BuildException
  {
    return docRoot(projectDir.getPath());
  }

  private static File docRoot(String projectPath) throws BuildException
  {
    File projectDocRoot = new File(DOC_ROOT, projectPath);
    if(!projectDocRoot.exists()) {
      throw new BuildException("Project directory |%s| does not exist.", projectDocRoot);
    }
    if(!projectDocRoot.isDirectory()) {
      throw new BuildException("Project directory |%s| is a regular file.", projectDocRoot);
    }
    return projectDocRoot;
  }
}
