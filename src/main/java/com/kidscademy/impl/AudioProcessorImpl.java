package com.kidscademy.impl;

import java.io.File;
import java.io.IOException;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.ProcessStarter;

import com.kidscademy.AudioProcessor;

import js.annotation.ContextParam;
import js.core.AppContext;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

public class AudioProcessorImpl implements AudioProcessor
{
  private static final int WAVEFORM_WIDTH = 800;
  private static final int WAVEFORM_HEIGHT = 160;
  private static final String WAVEFORM_COLOR = "#0000FF";
  private static final String XAXIS_COLOR = "#00B359";

  @ContextParam("image.magick.path")
  private static String IMAGE_MAGICK_PATH;

  private final File waveformXAxisFile;
  private final File waveformGradientFile;

  public AudioProcessorImpl(AppContext context) throws IOException, InterruptedException, IM4JavaException
  {
    ProcessStarter.setGlobalSearchPath(IMAGE_MAGICK_PATH);

    File waveformXAxisFile = context.getAppFile("waveform-x-axis.png");
    if(!waveformXAxisFile.exists()) {
      // convert -size 960x140 xc:transparent -fill white -stroke black -draw "line 0,69.5,960,69.5" waveform-x-axis.png

      IMOperation op = new IMOperation();
      op.size(WAVEFORM_WIDTH, WAVEFORM_HEIGHT);
      op.addRawArgs("xc:transparent");
      op.fill("white");
      op.stroke(XAXIS_COLOR);
      float y = (WAVEFORM_HEIGHT - 1) / 2.0F;
      op.draw(String.format("line 0,%f,%d,%f", y, WAVEFORM_WIDTH, y));
      op.addImage(waveformXAxisFile.getAbsolutePath());

      ConvertCmd cmd = new ConvertCmd();
      cmd.run(op);
    }
    this.waveformXAxisFile = waveformXAxisFile;

    File waveformGradientFile = context.getAppFile("waveform-gradient.png");
    if(!waveformGradientFile.exists()) {
      // convert -size 140x960 xc:red -colorspace HSB gradient: -compose CopyRed -composite -colorspace RGB -rotate 90
      // waveform-gradient.png
      IMOperation op = new IMOperation();
      // reverse order of width and height because of -rotate 90
      op.size(WAVEFORM_HEIGHT, WAVEFORM_WIDTH);
      op.addRawArgs("xc:red");
      op.colorspace("HSB");
      op.addRawArgs("gradient:");
      op.compose("CopyRed");
      op.composite();
      op.colorspace("RGB");
      op.rotate(90.0);
      op.addImage(waveformGradientFile.getAbsolutePath());

      ConvertCmd cmd = new ConvertCmd();
      cmd.run(op);
    }
    this.waveformGradientFile = waveformGradientFile;
  }

  @Override
  public void generateWaveform(File audioFile, File waveformFile) throws IOException, InterruptedException, IM4JavaException
  {
    // uses ffmpeg to create waveform image with solid fill
    // ffmpeg -i ${audio-file} -filter_complex "aformat=channel_layouts=mono,showwavespic=s=960x140:colors=#999980"
    // -frames:v 1 ${waveform-file}

    FFmpegBuilder builder = new FFmpegBuilder();
    builder.setVerbosity(FFmpegBuilder.Verbosity.DEBUG);
    builder.setInput(audioFile.getAbsolutePath());
    builder.addOutput(waveformFile.getAbsolutePath());
    builder.setComplexFilter(String.format("aformat=channel_layouts=mono,showwavespic=size=%dx%d:colors=%s", WAVEFORM_WIDTH, WAVEFORM_HEIGHT, WAVEFORM_COLOR));

    FFmpegExecutor executor = new FFmpegExecutor();
    executor.createJob(builder).run();

    // uses image magick to replace waveform solid color with gradient
    //
    IMOperation op = new IMOperation();
    op.composite();
    op.compose("srcin");
    op.addImage(waveformFile.getAbsolutePath());
    op.addImage(waveformGradientFile.getAbsolutePath());
    op.addImage(waveformFile.getAbsolutePath());

    ConvertCmd cmd = new ConvertCmd();
    cmd.run(op);

    op = new IMOperation();
    op.addImage(waveformFile.getAbsolutePath());
    op.addImage(waveformXAxisFile.getAbsolutePath());
    op.composite();
    op.addImage(waveformFile.getAbsolutePath());

    cmd = new ConvertCmd();
    cmd.run(op);
  }

  public void generateWaveformEOL(File audioFile, File waveformFile) throws IOException
  {
    FFmpegBuilder builder = new FFmpegBuilder();
    builder.setVerbosity(FFmpegBuilder.Verbosity.DEBUG);
    builder.setInput(audioFile.getAbsolutePath());

    // File wavFile = File.createTempFile("sample", ".wav");
    // builder.addOutput(wavFile.getAbsolutePath());

    builder.addOutput(waveformFile.getAbsolutePath());
    builder.setComplexFilter("aformat=channel_layouts=mono,showwavespic=size=960x140:colors=#0000FF");

    FFmpegExecutor executor = new FFmpegExecutor();
    executor.createJob(builder).run();

    // WaveStream stream = new WaveStream(wavFile);
    // WaveForm waveform = new WaveForm(960, 140);
    //
    // try {
    // waveform.setSamplesCount(stream.getSamplesCount());
    // stream.setProgressListener(waveform);
    // stream.setSamplesListener(waveform);
    // stream.process();
    // }
    // finally {
    // stream.close();
    // }
    //
    // waveform.save(waveFormFile);
    // wavFile.delete();
  }
}
