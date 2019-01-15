package com.kidscademy.unit;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.io.FileMatchers.anExistingFile;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.kidscademy.AudioProcessor;
import com.kidscademy.ImageProcessor;
import com.kidscademy.atlas.AudioFileInfo;
import com.kidscademy.impl.AudioProcessorImpl;

import js.core.AppContext;
import js.util.Files;

@RunWith(MockitoJUnitRunner.class)
public class AudioProcessorTest
{
  @Mock
  private AppContext context;
  @Mock
  private ImageProcessor image;

  private AudioProcessor audio;

  @Before
  public void beforeTest() throws IOException
  {
    when(context.getAppFile(anyString())).thenAnswer(new Answer<File>()
    {
      @Override
      public File answer(InvocationOnMock invocation) throws Throwable
      {
        return new File("fixture/" + (String)invocation.getArguments()[0]);
      }
    });

    audio = new AudioProcessorImpl(context, image);
  }

  @Test
  public void getAudioFileInfo() throws IOException
  {
    File audioFile = new File("fixture/sample.mp3");
    AudioFileInfo info = audio.getAudioFileInfo(audioFile);

    assertThat(info, notNullValue());
    assertThat(info.getFileName(), equalTo("fixture\\sample.mp3"));
    assertThat(info.getFileSize(), equalTo(3041906));
    assertThat(info.getCodec(), equalTo("MP3 (MPEG audio layer 3)"));
    assertThat(info.getDuration(), equalTo(190119));
    assertThat(info.getChannels(), equalTo(2));
    assertThat(info.getSampleRate(), equalTo(44100));
    assertThat(info.getBitRate(), equalTo(128000));
  }

  @Test
  public void trimSilence() throws IOException
  {
    File audioFile = new File("fixture/tone-with-silence.mp3");
    File targetFile = new File("fixture/normalized-tone.mp3");

    audio.trimSilence(audioFile, targetFile);
    assertThat(targetFile, anExistingFile());

    AudioFileInfo info = audio.getAudioFileInfo(targetFile);
    assertThat((double)info.getDuration(), closeTo(2000, 100));
  }

  @Test
  public void trimSilence_selfTarget() throws IOException
  {
    File audioFile = new File("fixture/temp-tone-with-silence.mp3");
    Files.copy(new File("fixture/tone-with-silence.mp3"), audioFile);

    audio.trimSilence(audioFile);
    assertThat(audioFile, anExistingFile());

    AudioFileInfo info = audio.getAudioFileInfo(audioFile);
    assertThat((double)info.getDuration(), closeTo(2000, 100));

    audioFile.delete();
  }

  @Test
  public void convertToMono() throws IOException
  {
    File stereoFile = new File("fixture/stereo-tone.mp3");
    File monoFile = new File("fixture/mono-tone.mp3");

    audio.convertToMono(stereoFile, monoFile);
    assertThat(monoFile, anExistingFile());

    assertThat(audio.getAudioFileInfo(stereoFile).getChannels(), equalTo(2));
    assertThat(audio.getAudioFileInfo(monoFile).getChannels(), equalTo(1));

    monoFile.delete();
  }

  @Test
  public void normalizeLevel() throws IOException
  {
    File audioFile = new File("fixture/sample.mp3");
    File targetFile = new File("fixture/sample_normalize.mp3");
    // targetFile.delete();

    audio.normalizeLevel(audioFile, targetFile);
  }
}
