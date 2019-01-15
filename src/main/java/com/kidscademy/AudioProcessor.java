package com.kidscademy;

import java.io.File;
import java.io.IOException;

import com.kidscademy.atlas.AudioFileInfo;

public interface AudioProcessor
{
  AudioFileInfo getAudioFileInfo(File audioFile) throws IOException;

  void generateWaveform(File audioFile, File waveformFile) throws IOException;

  /**
   * Trim silence from both start and end of given audio file. Inside silence is not processed. Trimmed audio is saved on optional target file; if target file
   * is not provided store trimmed audio in source file.
   * <p>
   * Silence trim is performed till found at least half seconds of signal. This allows for eliminating bursts of noises.
   * 
   * @param audioFile source audio file,
   * @param optionalTargetFile optional target file.
   * @throws IOException if processing fails.
   */
  void trimSilence(File audioFile, File... optionalTargetFile) throws IOException;

  void convertToMono(File audioFile, File... optionalTargetFile) throws IOException;

  /**
   * Normalize audio level peak to 0dB. Normalized audio is saved on optional target file; if target file is not provided store normalized audio in source file.
   * 
   * @param audioFile source audio file,
   * @param optionalTargetFile optional target file.
   * @throws IOException if processing fails.
   */
  void normalizeLevel(File audioFile, File... optionalTargetFile) throws IOException;
}
