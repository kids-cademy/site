package com.kidscademy.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class WaveStream extends InputStream
{
  private AudioInputStream stream;
  private byte[] frame;
  private int frameSize;
  private int frameIndex;
  private long samplesCount;
  private long samplesIndex;
  private Sampler sampler;
  private SamplesListener samplesListener;
  private ProgressListener progressListener;

  public WaveStream(File file) throws UnsupportedAudioFileException, IOException
  {
    this.stream = AudioSystem.getAudioInputStream(file);
    init();
  }

  public WaveStream(InputStream stream) throws UnsupportedAudioFileException, IOException
  {
    this.stream = AudioSystem.getAudioInputStream(stream);
    init();
  }

  private void init()
  {
    AudioFormat format = stream.getFormat();
    this.frameSize = format.getFrameSize();
    this.frameIndex = this.frameSize; // mark frame index for dirty frame, i.e. need to read from audio stream
    this.frame = new byte[this.frameSize];
    this.samplesCount = stream.getFrameLength();

    AudioFormat.Encoding encoding = format.getEncoding();
    boolean bigEndien = format.isBigEndian();
    int channels = format.getChannels();
    int sampleSize = format.getSampleSizeInBits();

    if(encoding == AudioFormat.Encoding.PCM_UNSIGNED) {
      if(channels == 1) {
        if(sampleSize == 8) {
          this.sampler = new UnsignedPcmMono8Sampler();
        }
        else if(sampleSize == 16) {
          if(bigEndien) {
            this.sampler = new UnsignedPcmMono16BigSampler();
          }
          else {
            this.sampler = new UnsignedPcmMono16LittleSampler();
          }
        }
      }
      else if(channels == 2) {
        if(sampleSize == 8) {
          this.sampler = new UnsignedPcmStereo8Sampler();
        }
        else if(sampleSize == 16) {
          if(bigEndien) {
            this.sampler = new UnsignedPcmStereo16BigSampler();
          }
          else {
            this.sampler = new UnsignedPcmStereo16LittleSampler();
          }
        }
      }
      else {
        throw new IllegalStateException();
      }
    }
    else if(encoding == AudioFormat.Encoding.PCM_SIGNED) {
      if(channels == 1) {
        if(sampleSize == 8) {
          this.sampler = new SignedPcmMono8Sampler();
        }
        else if(sampleSize == 16) {
          if(bigEndien) {
            this.sampler = new SignedPcmMono16BigSampler();
          }
          else {
            this.sampler = new SignedPcmMono16LittleSampler();
          }
        }
      }
      else if(channels == 2) {
        if(sampleSize == 8) {
          this.sampler = new SignedPcmStereo8Sampler();
        }
        else if(sampleSize == 16) {
          if(bigEndien) {
            this.sampler = new SignedPcmStereo16BigSampler();
          }
          else {
            this.sampler = new SignedPcmStereo16LittleSampler();
          }
        }
      }
      else {
        throw new IllegalStateException();
      }
    }
    else {
      throw new IllegalStateException();
    }
    if(this.sampler == null) {
      throw new IllegalStateException("Unsupported format: " + format);
    }
  }

  public long getSamplesCount()
  {
    return samplesCount;
  }

  public void setSamplesListener(SamplesListener samplesListener)
  {
    this.samplesListener = samplesListener;
  }

  public void setProgressListener(ProgressListener progressListener)
  {
    this.progressListener = progressListener;
  }

  public void process() throws IOException
  {
    while(read() != -1) {}
  }

  private int progress;

  @Override
  public int read() throws IOException
  {
    if(this.frameIndex == this.frameSize) {
      // frame is dirty, that is, all its content was processed; read another one
      int n = this.stream.read(this.frame);
      if(n == -1) return -1;

      if(this.samplesListener != null) {
        this.samplesListener.onWaveStreamSample(this.sampler.nextSample(this.frame));
      }
      if(this.progressListener != null) {
        this.samplesIndex++;
        int progress = (int)(100 * this.samplesIndex / this.samplesCount);
        if(this.progress != progress) {
          this.progress = progress;
          this.progressListener.onProgress(progress);
        }
      }
      this.frameIndex = 0;
    }
    return this.frame[this.frameIndex++] & 0xFF;
  }

  public static class Decimator implements SamplesListener
  {
    private long samplesCount;
    private SamplesListener samplesListener;
    private int decimationBlockSize;

    public Decimator(WaveStream waveStream)
    {
      this.samplesCount = waveStream.samplesCount;
      waveStream.setSamplesListener(this);
    }

    public void setSamplesListener(SamplesListener samplesListener)
    {
      this.samplesListener = samplesListener;
    }

    private int index;
    @SuppressWarnings("unused")
    private double totalSamples;
    private double maxSampleValue;

    @Override
    public void onWaveStreamSample(double sample)
    {
      if(this.index == this.decimationBlockSize) {
        // this.samplesListener.onWaveStreamSample(this.totalSamples / this.decimationBlockSize);
        this.samplesListener.onWaveStreamSample(this.maxSampleValue);
        this.index = 0;
        this.totalSamples = 0;
        this.maxSampleValue = 0;
      }
      this.index++;
      this.totalSamples += sample;
      if(sample > this.maxSampleValue) {
        this.maxSampleValue = sample;
      }
    }

    public void setDownSamplesCount(int downSamplesCount)
    {
      this.decimationBlockSize = (int)(this.samplesCount / downSamplesCount);
    }
  }

  public static interface SamplesListener
  {
    void onWaveStreamSample(double sample);
  }

  private static interface Sampler
  {
    double nextSample(byte[] frame);
  }

  private static class UnsignedPcmMono8Sampler implements Sampler
  {
    @Override
    public double nextSample(byte[] frame)
    {
      assert frame.length == 1;
      return ((int)frame[0] & 0xFF) / 256.0;
    }
  }

  private static class UnsignedPcmStereo8Sampler implements Sampler
  {
    @Override
    public double nextSample(byte[] frame)
    {
      assert frame.length == 2;
      return (((int)frame[0] & 0xFF) + ((int)frame[1] & 0xFF)) / 512.0;
    }
  }

  private static class UnsignedPcmMono16BigSampler implements Sampler
  {
    @Override
    public double nextSample(byte[] frame)
    {
      assert frame.length == 2;
      return (int)(frame[0] << 8 | (frame[1] & 0xFF)) / 65536.0;
    }
  }

  private static class UnsignedPcmStereo16BigSampler implements Sampler
  {
    @Override
    public double nextSample(byte[] frame)
    {
      assert frame.length == 4;
      int left = frame[0] << 8 | (frame[1] & 0xFF);
      int right = frame[2] << 8 | (frame[3] & 0xFF);
      return (left + right) / 131072.0;
    }
  }

  private static class UnsignedPcmMono16LittleSampler implements Sampler
  {
    @Override
    public double nextSample(byte[] frame)
    {
      assert frame.length == 2;
      return (int)(frame[1] << 8 | (frame[0] & 0xFF)) / 65536.0;
    }
  }

  private static class UnsignedPcmStereo16LittleSampler implements Sampler
  {
    @Override
    public double nextSample(byte[] frame)
    {
      assert frame.length == 4;
      int left = frame[1] << 8 | (frame[0] & 0xFF);
      int right = frame[3] << 8 | (frame[2] & 0xFF);
      return (left + right) / 131072.0;
    }
  }

  private static class SignedPcmMono8Sampler implements Sampler
  {
    @Override
    public double nextSample(byte[] frame)
    {
      assert frame.length == 1;
      return (frame[0] + 127) / 256.0;
    }
  }

  private static class SignedPcmStereo8Sampler implements Sampler
  {
    @Override
    public double nextSample(byte[] frame)
    {
      assert frame.length == 2;
      return ((int)(frame[0] + frame[1]) + 256) / 512.0;
    }
  }

  private static class SignedPcmMono16BigSampler implements Sampler
  {
    @Override
    public double nextSample(byte[] frame)
    {
      assert frame.length == 2;
      return ((int)(frame[0] << 8 | (frame[1] & 0xFF)) + 32767) / 65536.0;
    }
  }

  private static class SignedPcmStereo16BigSampler implements Sampler
  {
    @Override
    public double nextSample(byte[] frame)
    {
      assert frame.length == 4;
      int left = frame[0] << 8 | (frame[1] & 0xFF);
      int right = frame[2] << 8 | (frame[3] & 0xFF);
      return (left + right + 65534) / 131072.0;
    }
  }

  private static class SignedPcmMono16LittleSampler implements Sampler
  {
    @Override
    public double nextSample(byte[] frame)
    {
      assert frame.length == 2;
      return ((int)(frame[1] << 8 | (frame[0] & 0xFF)) + 32767) / 65536.0;
    }
  }

  private static class SignedPcmStereo16LittleSampler implements Sampler
  {
    @Override
    public double nextSample(byte[] frame)
    {
      assert frame.length == 4;
      int left = frame[1] << 8 | (frame[0] & 0xFF);
      int right = frame[3] << 8 | (frame[2] & 0xFF);
      return (left + right + 65534) / 131072.0;
    }
  }
}
