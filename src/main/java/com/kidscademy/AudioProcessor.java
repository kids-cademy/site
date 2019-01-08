package com.kidscademy;

import java.io.File;
import java.io.IOException;

import org.im4java.core.IM4JavaException;

public interface AudioProcessor
{
  void generateWaveform(File audioFile, File waveformFile) throws IOException, InterruptedException, IM4JavaException;
}
