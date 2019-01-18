package com.kidscademy.unit;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.AfterClass;
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
import com.kidscademy.media.AudioProcessor;
import com.kidscademy.media.ImageProcessor;
import com.kidscademy.media.SampleFileInfo;

import js.core.AppContext;
import js.util.Classes;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceTest
{
  /** Save and restore static repository directory from admin service implementation. */
  private static String REPOSITORY_DIR;

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
    REPOSITORY_DIR = Classes.getFieldValue(AdminServiceImpl.class, "REPOSITORY_DIR");
    Classes.setFieldValue(AdminServiceImpl.class, "REPOSITORY_DIR", "fixture");
  }

  @AfterClass
  public static void afterClass()
  {
    // takes care to restore static state after test class concludes
    Classes.setFieldValue(AdminServiceImpl.class, "REPOSITORY_DIR", REPOSITORY_DIR);
  }

  @Before
  public void beforeTest()
  {
    service = new AdminServiceImpl(context, dao, audio, image);
  }

  @Test
  public void normalizeSample() throws IOException
  {
    ArgumentCaptor<File> sampleFile = ArgumentCaptor.forClass(File.class);
    ArgumentCaptor<File> workingSampleFile = ArgumentCaptor.forClass(File.class);
    ArgumentCaptor<File> waveformFile = ArgumentCaptor.forClass(File.class);

    when(audio.getAudioFileInfo(any(File.class))).thenReturn(null);

    SampleFileInfo info = service.normalizeSample("test");

    //verify(audio).trimSilence(sampleFile.capture());
    assertThat(sampleFile.getValue(), equalTo(new File("fixture/instruments/test/working-sample.mp3")));

    verify(audio).generateWaveform(workingSampleFile.capture(), waveformFile.capture());
    assertThat(workingSampleFile.getValue(), equalTo(new File("fixture/instruments/test/working-sample.mp3")));
    assertThat(waveformFile.getValue(), equalTo(new File("fixture/instruments/test/working-waveform.png")));

    assertThat(info, notNullValue());
    assertThat(info.getSamplePath(), equalTo("instruments/test/working-sample.mp3"));
    assertThat(info.getWaveformPath(), equalTo("instruments/test/working-waveform.png"));
  }
}
