package com.kidscademy.media;

import java.io.File;
import java.io.IOException;

public interface AudioProcessor
{
  SampleFileInfo getAudioFileInfo(File audioFile) throws IOException;

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
  void trimSilence(MediaFileHandler object) throws IOException;

  void convertToMono(MediaFileHandler object) throws IOException;

  /**
   * Normalize audio level peak to 0dB. Normalized audio is saved on optional target file; if target file is not provided store normalized audio in source file.
   * 
   * @param audioFile source audio file,
   * @param optionalTargetFile optional target file.
   * @throws IOException if processing fails.
   */
  void normalizeLevel(MediaFileHandler object) throws IOException;
}
