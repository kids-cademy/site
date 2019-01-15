package com.kidscademy.atlas;

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

  public String getFileName()
  {
    return fileName;
  }

  public void setFileName(String fileName)
  {
    this.fileName = fileName;
  }

  public int getFileSize()
  {
    return fileSize;
  }

  public void setFileSize(int fileSize)
  {
    this.fileSize = fileSize;
  }

  public String getCodec()
  {
    return codec;
  }

  public void setCodec(String codec)
  {
    this.codec = codec;
  }

  public int getDuration()
  {
    return duration;
  }

  public void setDuration(int duration)
  {
    this.duration = duration;
  }

  public int getChannels()
  {
    return channels;
  }

  public void setChannels(int channels)
  {
    this.channels = channels;
  }

  public int getSampleRate()
  {
    return sampleRate;
  }

  public void setSampleRate(int sampleRate)
  {
    this.sampleRate = sampleRate;
  }

  public int getBitRate()
  {
    return bitRate;
  }

  public void setBitRate(int bitRate)
  {
    this.bitRate = bitRate;
  }
}
