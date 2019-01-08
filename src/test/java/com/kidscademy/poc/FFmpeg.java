package com.kidscademy.poc;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.kidscademy.atlas.AudioSampleInfo;

import js.format.BitRate;
import js.format.Duration;
import js.format.FileSize;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.probe.FFmpegStream;

public class FFmpeg
{
  @Test
  public void printFFprobe() throws IOException
  {
    FFprobe ffprobe = new FFprobe("c://ffmpeg/ffprobe.exe");
    FFmpegProbeResult probeResult = ffprobe.probe("d://tmp/sample.mp3");

    FFmpegFormat format = probeResult.getFormat();
    System.out.format("%nFile: '%s' ; Format: '%s' ; Duration: %.3fs", format.filename, format.format_long_name, format.duration);

    FFmpegStream stream = probeResult.getStreams().get(0);
    System.out.format("%nCodec: '%s' ; Width: %dpx ; Height: %dpx", stream.codec_long_name, stream.width, stream.height);
  }

  @Test
  public void printAudioSampleInfo() throws IOException
  {
    FFprobe probe = new FFprobe();
    AudioSampleInfo info = new AudioSampleInfo(probe.probe("d://tmp/sample.mp3"));

    FileSize fileSizeFormat = new FileSize();
    Duration durationFormat = new Duration();
    BitRate bitRateFormat = new BitRate();

    System.out.printf("file name  : %s\n", info.fileName);
    System.out.printf("file size  : %s\n", fileSizeFormat.format(info.fileSize));
    System.out.printf("codec      : %s\n", info.codec);
    System.out.printf("duration   : %s\n", durationFormat.format(info.duration));
    System.out.printf("channels   : %s\n", info.channels);
    System.out.printf("sample rate: %s\n", info.sampleRate);
    System.out.printf("bit rate   : %s\n", bitRateFormat.format(info.bitRate));
  }
  
  @Test
  public void generateWaveform() throws IOException {
    File sampleFile = new File("fixture/sample.mp3");
    File waveformFile = new File("fixture/waveform.png");
    
    FFmpegBuilder builder = new FFmpegBuilder();
    builder.setVerbosity(FFmpegBuilder.Verbosity.DEBUG);
    builder.setInput(sampleFile.getAbsolutePath());
    builder.addOutput(waveformFile.getAbsolutePath());
    builder.setComplexFilter("aformat=channel_layouts=mono,showwavespic=size=960x140:colors=#0000FF");

    FFmpegExecutor executor = new FFmpegExecutor();
    executor.createJob(builder).run();
  }
}
