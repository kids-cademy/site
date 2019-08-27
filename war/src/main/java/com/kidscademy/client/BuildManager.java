package com.kidscademy.client;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.SortedMap;

import js.io.FilesOutputStream;
import js.io.StreamHandler;

import com.kidscademy.model.DirtyFiles;
import com.kidscademy.model.Excludes;

public interface BuildManager
{
  boolean incrementBuildNumber(File projectDir) throws IOException;

  DirtyFiles getDirtyFiles(File projectDir, Excludes excludes, SortedMap<String, byte[]> sourceFiles) throws IOException;

  boolean synchronize(File projectDir, List<String> staleFiles, StreamHandler<FilesOutputStream> freshFiles) throws IOException;

  boolean generateTimestampFilesList(File projectDir, Excludes excludes) throws IOException;

  void deploy(URL productionServer, String projectName, Excludes dirtyExcludes, Excludes mobileExcludes) throws IOException;
}
