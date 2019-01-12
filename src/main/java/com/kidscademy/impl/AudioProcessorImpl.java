package com.kidscademy.impl;

import java.io.File;
import java.io.IOException;

import org.im4java.process.ProcessStarter;

import com.kidscademy.AudioProcessor;
import com.kidscademy.ImageProcessor;
import com.kidscademy.atlas.AudioFileInfo;

import js.annotation.ContextParam;
import js.core.AppContext;
import js.util.Files;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.builder.FFmpegOutputBuilder;

public class AudioProcessorImpl implements AudioProcessor
{
  private static final String TEMP_PREFIX = "audio-processor";

  private static final int WAVEFORM_WIDTH = 800;
  private static final int WAVEFORM_HEIGHT = 160;
  private static final String WAVEFORM_COLOR = "#0000FF";
  private static final String XAXIS_COLOR = "#FFFFFF";

  @ContextParam("image.magick.path")
  private static String IMAGE_MAGICK_PATH;

  private final File waveformXAxisFile;
  private final File waveformGradientFile;

  private final ImageProcessor image;

  public AudioProcessorImpl(AppContext context, ImageProcessor image) throws IOException
  {
    ProcessStarter.setGlobalSearchPath(IMAGE_MAGICK_PATH);
    this.image = image;

    waveformXAxisFile = context.getAppFile("waveform-x-axis.png");
    if(!waveformXAxisFile.exists()) {
      this.image.generateXAxis(waveformXAxisFile, WAVEFORM_WIDTH, WAVEFORM_HEIGHT, XAXIS_COLOR);
    }

    waveformGradientFile = context.getAppFile("waveform-gradient.png");
    if(!waveformGradientFile.exists()) {
      this.image.generateRainbowGradient(waveformGradientFile, WAVEFORM_HEIGHT, WAVEFORM_WIDTH);
    }
  }

  @Override
  public AudioFileInfo getAudioFileInfo(File audioFile) throws IOException
  {
    FFprobe probe = new FFprobe();
    return new AudioFileInfo(probe.probe(audioFile.getAbsolutePath()));
  }

  @Override
  public void generateWaveform(File audioFile, File waveformFile) throws IOException
  {
    // sample should be already mono

    // create waveform image with solid fill
    // sample file should already be mono
    // ffmpeg -i ${audio-file} -filter_complex "showwavespic=s=${width}x${height}:colors=${color}" ${waveform-file}

    // if not add filter aformat=channel_layouts=mono to mix down channels
    // ffmpeg -i ${audio-file} -filter_complex "aformat=channel_layouts=mono,showwavespic=s=${width}x${height}:colors=${color}" ${waveform-file}

    // hard to believe but there is a bug on waveform generation when use linear scale, that is default
    // generated waveform height is half of requested value
    // work around is to create waveform with double height then crop to original, i.e. requested value

    FFmpegBuilder ffmpeg = new FFmpegBuilder();
    ffmpeg.setInput(audioFile.getAbsolutePath());
    ffmpeg.addOutput(waveformFile.getAbsolutePath());
    // create waveform with double height to compensate ffmpeg bug
    ffmpeg.setComplexFilter(String.format("showwavespic=size=%dx%d:colors=%s", WAVEFORM_WIDTH, 2 * WAVEFORM_HEIGHT, WAVEFORM_COLOR));
    execute(ffmpeg);

    // crop waveform image to original height
    image.crop(waveformFile, WAVEFORM_WIDTH, WAVEFORM_HEIGHT, 0, WAVEFORM_HEIGHT / 2);

    // replace waveform solid color with gradient
    // SRC-IN alpha blending replace SRC solid color with gradient and leave SRC alpha as it is
    image.compose(waveformFile, waveformGradientFile, ImageProcessor.Compose.SRCIN);

    // overlap xaxis on waveform
    image.overlap(waveformFile, waveformXAxisFile);
  }

  @Override
  public void removeSilence(File audioFile, File... optionalTargetFile) throws IOException
  {
    File targetFile = createTempFile(audioFile, optionalTargetFile);

    // ffmpeg -i ${audioFile} -af silenceremove=start_periods=1:start_duration=1:start_threshold=0.02:stop_periods=1:stop_duration=1:stop_threshold=0.02 ${targetFile}
    FFmpegBuilder ffmpeg = new FFmpegBuilder();
    ffmpeg.setInput(audioFile.getAbsolutePath());
    // current version - 0.6.2 of FFmpeg wrapper has a bug mixing video and audio filters
    // work around is just to use video filter when in fact need audio
    ffmpeg.setVideoFilter("silenceremove=start_periods=1:start_duration=1:start_threshold=0.02:stop_periods=1:stop_duration=1:stop_threshold=0.02");
    ffmpeg.addOutput(targetFile.getAbsolutePath());
    execute(ffmpeg);

    if(optionalTargetFile.length == 0) {
      audioFile.delete();
      targetFile.renameTo(audioFile);
    }
  }

  @Override
  public void convertToMono(File audioFile, File... optionalTargetFile) throws IOException
  {
    File targetFile = createTempFile(audioFile, optionalTargetFile);

    // ffmpeg -i ${audioFile} -ac 1 ${targetFile}
    FFmpegBuilder ffmpeg = new FFmpegBuilder();
    ffmpeg.setInput(audioFile.getAbsolutePath());
    FFmpegOutputBuilder output = ffmpeg.addOutput(targetFile.getAbsolutePath());
    output.setAudioChannels(1);

    FFmpegExecutor executor = new FFmpegExecutor();
    executor.createJob(ffmpeg).run();

    if(optionalTargetFile.length == 0) {
      audioFile.delete();
      targetFile.renameTo(audioFile);
    }
  }

  // ----------------------------------------------------------------------------------------------

  private static void execute(FFmpegBuilder builder) throws IOException
  {
    FFmpegExecutor executor = new FFmpegExecutor();
    executor.createJob(builder).run();
  }

  private static File createTempFile(File audioFile, File... optionalTargetFile) throws IOException
  {
    return optionalTargetFile.length > 0 ? optionalTargetFile[0] : File.createTempFile(TEMP_PREFIX, "." + Files.getExtension(audioFile));
  }
}
