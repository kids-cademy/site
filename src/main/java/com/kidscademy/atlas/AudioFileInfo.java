package com.kidscademy.atlas;

import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;

public class AudioFileInfo
{
  /** Sample file name. */
  private String fileName;
  /** Sample file size, in bytes. */
  private int fileSize;
  /** Audio codec full name. */
  private String codec;
  /** Sample duration, in milliseconds. */
  private int duration;
  private int channels;
  private int sampleRate;
  private int bitRate;

  public AudioFileInfo()
  {
  }

  public AudioFileInfo(FFmpegProbeResult probe)
  {
    this.fileName = probe.format.filename;
    this.fileSize = (int)probe.format.size;

    FFmpegStream stream = probe.getStreams().get(0);
    this.codec = stream.codec_long_name;
    this.duration = (int)Math.round(stream.duration * 1000);
    this.channels = stream.channels;
    this.sampleRate = stream.sample_rate;
    this.bitRate = (int)stream.bit_rate;
  }

  public String getFileName()
  {
    return fileName;
  }

  public int getFileSize()
  {
    return fileSize;
  }

  public String getCodec()
  {
    return codec;
  }

  public int getDuration()
  {
    return duration;
  }

  public int getChannels()
  {
    return channels;
  }

  public int getSampleRate()
  {
    return sampleRate;
  }

  public int getBitRate()
  {
    return bitRate;
  }
}
