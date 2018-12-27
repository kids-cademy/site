package com.kidscademy.tool;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.UnsupportedAudioFileException;

import com.kidscademy.util.WaveForm;
import com.kidscademy.util.WaveStream;

import js.util.Files;

public class GenerateWaveForm
{
  public static void main(String... args) throws UnsupportedAudioFileException, IOException
  {
    File dir = new File("d:/tmp/hc");
    File trackFile = new File(dir, "sample.wav");
    File waveFormFile = new File(dir, Files.basename(trackFile) + ".png");
    WaveForm form = new WaveForm(1180, 140);
    WaveStream stream = new WaveStream(trackFile);
    form.setSamplesCount(stream.getSamplesCount());
    stream.setProgressListener(form);
    stream.setSamplesListener(form);
    Files.copy(stream);
    form.save(waveFormFile);
  }
}
