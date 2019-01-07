package com.kidscademy.atlas;

import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;

public class AudioSampleInfo
{
  /** Sample file name. */
  public String fileName;
  /** Sample file size, in bytes. */
  public int fileSize;
  /** Audio codec full name. */
  public String codec;
  /** Sample duration, in milliseconds. */
  public int duration;
  public int channels;
  public int sampleRate;
  public int bitRate;

  public AudioSampleInfo()
  {
  }
  
  public AudioSampleInfo(FFmpegProbeResult probe)
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
}
