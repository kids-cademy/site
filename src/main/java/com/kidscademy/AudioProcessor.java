package com.kidscademy;

import java.io.File;
import java.io.IOException;

import com.kidscademy.atlas.AudioFileInfo;

public interface AudioProcessor
{
  AudioFileInfo getAudioFileInfo(File audioFile) throws IOException;
  
  void generateWaveform(File audioFile, File waveformFile) throws IOException;

  void removeSilence(File audioFile, File... optionalTargetFile) throws IOException;

  void convertToMono(File audioFile, File... optionalTargetFile) throws IOException;
}
