package com.kidscademy.unit;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.kidscademy.AdminService;
import com.kidscademy.dao.AdminDao;
import com.kidscademy.impl.AdminServiceImpl;
import com.kidscademy.impl.MediaFileHandler;
import com.kidscademy.media.AudioProcessor;
import com.kidscademy.media.AudioSampleInfo;
import com.kidscademy.media.ImageProcessor;

import js.core.AppContext;
import js.util.Classes;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceTest
{
  @Mock
  private AppContext context;
  @Mock
  private AdminDao dao;
  @Mock
  private AudioProcessor audio;
  @Mock
  private ImageProcessor image;

  private AdminService service;

  @BeforeClass
  public static void beforeClass()
  {
    Classes.setFieldValue(MediaFileHandler.class, "REPOSIOTRY_DIR", "fixture/tomcat/webapps");
  }

  @Before
  public void beforeTest()
  {
    service = new AdminServiceImpl(context, dao, audio, image);
  }

  @Test
  public void normalizeSample() throws IOException
  {
    ArgumentCaptor<File> audioFile = ArgumentCaptor.forClass(File.class);
    ArgumentCaptor<File> targetFile = ArgumentCaptor.forClass(File.class);
    ArgumentCaptor<File> waveformFile = ArgumentCaptor.forClass(File.class);

    AudioSampleInfo info = new AudioSampleInfo();

    when(audio.getAudioFileInfo(any(File.class))).thenReturn(info);

    info = service.normalizeSample("test");

    verify(audio).normalizeLevel(audioFile.capture(), targetFile.capture());
    assertThat(audioFile.getValue(), equalTo(new File("fixture/tomcat/webapps/media/atlas/instruments/test/sample.mp3")));
    assertThat(targetFile.getValue(), equalTo(new File("fixture/tomcat/webapps/media/atlas/instruments/test/sample_1.mp3")));

    verify(audio).generateWaveform(targetFile.capture(), waveformFile.capture());
    assertThat(targetFile.getValue(), equalTo(new File("fixture/tomcat/webapps/media/atlas/instruments/test/sample_1.mp3")));
    assertThat(waveformFile.getValue(), equalTo(new File("fixture/tomcat/webapps/media/atlas/instruments/test/waveform.png")));

    assertThat(info, notNullValue());
    assertThat(info.getSampleSrc(), equalTo("/media/atlas/instruments/test/sample_1.mp3"));
    assertThat(info.getWaveformSrc(), equalTo("/media/atlas/instruments/test/waveform.png"));
  }
}
