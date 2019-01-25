package com.kidscademy.unit;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
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
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.kidscademy.AdminService;
import com.kidscademy.atlas.MediaSRC;
import com.kidscademy.dao.AdminDao;
import com.kidscademy.impl.AdminServiceImpl;
import com.kidscademy.media.AudioProcessor;
import com.kidscademy.media.AudioSampleInfo;
import com.kidscademy.media.ImageProcessor;
import com.kidscademy.util.Files;

import js.core.AppContext;
import js.http.form.Form;
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
    Classes.setFieldValue(Files.class, "REPOSIOTRY_DIR", "fixture/tomcat/webapps");
  }

  @Before
  public void beforeTest()
  {
    service = new AdminServiceImpl(context, dao, audio, image);
  }

  @Test
  public void uploadAudioSample() throws IOException
  {
    Form form = Mockito.mock(Form.class);
    when(form.getValue("collection-name")).thenReturn("instrument");
    when(form.getValue("object-name")).thenReturn("test");
    File uploadFile = new File("fixture/upload.mp3");
    Files.copy(new File("fixture/sample.mp3"), uploadFile);
    when(form.getFile("file")).thenReturn(uploadFile);

    AudioSampleInfo info = new AudioSampleInfo();
    when(audio.getAudioFileInfo(any(File.class))).thenReturn(info);

    info = service.uploadAudioSample(form);
    assertFalse(uploadFile.exists());
    assertTrue(file("sample.mp3").exists());

    ArgumentCaptor<File> sampleFile = ArgumentCaptor.forClass(File.class);
    ArgumentCaptor<File> waveformFile = ArgumentCaptor.forClass(File.class);

    verify(audio).generateWaveform(sampleFile.capture(), waveformFile.capture());
    assertThat(sampleFile.getValue(), equalTo(file("sample.mp3")));
    assertThat(waveformFile.getValue(), equalTo(file("waveform.png")));

    assertThat(info, notNullValue());
    assertThat(info.getSampleSrc(), equalTo(src("sample.mp3")));
    assertThat(info.getWaveformSrc(), equalTo(src("waveform.png")));
  }

  @Test
  public void normalizeSample() throws IOException
  {
    Files.copy(new File("fixture/sample.mp3"), file("sample.mp3"));

    AudioSampleInfo info = new AudioSampleInfo();
    when(audio.getAudioFileInfo(any(File.class))).thenReturn(info);

    info = service.normalizeSample("instrument", "test");

    ArgumentCaptor<File> audioFile = ArgumentCaptor.forClass(File.class);
    ArgumentCaptor<File> targetFile = ArgumentCaptor.forClass(File.class);
    ArgumentCaptor<File> waveformFile = ArgumentCaptor.forClass(File.class);

    verify(audio).normalizeLevel(audioFile.capture(), targetFile.capture());
    assertThat(audioFile.getValue(), equalTo(file("sample.mp3")));
    assertThat(targetFile.getValue(), equalTo(file("sample_1.mp3")));

    verify(audio).generateWaveform(targetFile.capture(), waveformFile.capture());
    assertThat(targetFile.getValue(), equalTo(file("sample_1.mp3")));
    assertThat(waveformFile.getValue(), equalTo(file("waveform.png")));

    assertThat(info, notNullValue());
    assertThat(info.getSampleSrc(), equalTo(src("sample_1.mp3")));
    assertThat(info.getWaveformSrc(), equalTo(src("waveform.png")));
  }

  @Test
  public void convertToMono() throws IOException
  {
    Files.copy(new File("fixture/sample.mp3"), file("sample.mp3"));

    AudioSampleInfo info = new AudioSampleInfo();
    when(audio.getAudioFileInfo(any(File.class))).thenReturn(info);

    info = service.convertToMono("instrument", "test");

    ArgumentCaptor<File> audioFile = ArgumentCaptor.forClass(File.class);
    ArgumentCaptor<File> targetFile = ArgumentCaptor.forClass(File.class);
    ArgumentCaptor<File> waveformFile = ArgumentCaptor.forClass(File.class);

    verify(audio).convertToMono(audioFile.capture(), targetFile.capture());
    assertThat(audioFile.getValue(), equalTo(file("sample.mp3")));
    assertThat(targetFile.getValue(), equalTo(file("sample_1.mp3")));

    verify(audio).generateWaveform(targetFile.capture(), waveformFile.capture());
    assertThat(targetFile.getValue(), equalTo(file("sample_1.mp3")));
    assertThat(waveformFile.getValue(), equalTo(file("waveform.png")));

    assertThat(info, notNullValue());
    assertThat(info.getSampleSrc(), equalTo(src("sample_1.mp3")));
    assertThat(info.getWaveformSrc(), equalTo(src("waveform.png")));
  }

  @Test
  public void trimSilence() throws IOException
  {
    Files.copy(new File("fixture/sample.mp3"), file("sample.mp3"));

    AudioSampleInfo info = new AudioSampleInfo();
    when(audio.getAudioFileInfo(any(File.class))).thenReturn(info);

    info = service.trimSilence("instrument", "test");

    ArgumentCaptor<File> audioFile = ArgumentCaptor.forClass(File.class);
    ArgumentCaptor<File> targetFile = ArgumentCaptor.forClass(File.class);
    ArgumentCaptor<File> waveformFile = ArgumentCaptor.forClass(File.class);

    verify(audio).trimSilence(audioFile.capture(), targetFile.capture());
    assertThat(audioFile.getValue(), equalTo(file("sample.mp3")));
    assertThat(targetFile.getValue(), equalTo(file("sample_1.mp3")));

    verify(audio).generateWaveform(targetFile.capture(), waveformFile.capture());
    assertThat(targetFile.getValue(), equalTo(file("sample_1.mp3")));
    assertThat(waveformFile.getValue(), equalTo(file("waveform.png")));

    assertThat(info, notNullValue());
    assertThat(info.getSampleSrc(), equalTo(src("sample_1.mp3")));
    assertThat(info.getWaveformSrc(), equalTo(src("waveform.png")));
  }

  @Test
  public void generateWaveform() throws IOException
  {
    Files.copy(new File("fixture/sample.mp3"), file("sample.mp3"));

    MediaSRC waveformSrc = service.generateWaveform("instrument", "test");

    assertThat(waveformSrc, notNullValue());
    assertThat(waveformSrc, equalTo(src("waveform.png")));
  }

  @Test
  public void undoMediaProcessing() throws IOException
  {
    Files.copy(new File("fixture/sample.mp3"), file("sample.mp3"));
    Files.copy(new File("fixture/sample.mp3"), file("sample_1.mp3"));
    Files.copy(new File("fixture/sample.mp3"), file("sample_2.mp3"));


    AudioSampleInfo info = new AudioSampleInfo();
    when(audio.getAudioFileInfo(any(File.class))).thenReturn(info);

    info = service.undoMediaProcessing("instrument", "test");

    ArgumentCaptor<File> audioFile = ArgumentCaptor.forClass(File.class);
    ArgumentCaptor<File> waveformFile = ArgumentCaptor.forClass(File.class);

    verify(audio).generateWaveform(audioFile.capture(), waveformFile.capture());
    assertThat(audioFile.getValue(), equalTo(file("sample_1.mp3")));
    assertThat(waveformFile.getValue(), equalTo(file("waveform.png")));

    assertThat(info, notNullValue());
    assertThat(info.getSampleSrc(), equalTo(src("sample_1.mp3")));
    assertThat(info.getWaveformSrc(), equalTo(src("waveform.png")));
  }

  @Test
  public void commitMediaProcessing() throws IOException
  {
    Files.copy(new File("fixture/sample.mp3"), file("sample.mp3"));
    Files.copy(new File("fixture/sample.mp3"), file("sample_1.mp3"));
    Files.copy(new File("fixture/sample.mp3"), file("sample_2.mp3"));

    AudioSampleInfo info = new AudioSampleInfo();
    when(audio.getAudioFileInfo(any(File.class))).thenReturn(info);

    info = service.commitMediaProcessing("instrument", "test");
    assertTrue(file("sample.mp3").exists());
    assertFalse(file("sample_1.mp3").exists());
    assertFalse(file("sample_2.mp3").exists());

    ArgumentCaptor<File> audioFile = ArgumentCaptor.forClass(File.class);
    ArgumentCaptor<File> waveformFile = ArgumentCaptor.forClass(File.class);

    verify(audio).generateWaveform(audioFile.capture(), waveformFile.capture());
    assertThat(audioFile.getValue(), equalTo(file("sample.mp3")));
    assertThat(waveformFile.getValue(), equalTo(file("waveform.png")));

    assertThat(info, notNullValue());
    assertThat(info.getSampleSrc(), equalTo(src("sample.mp3")));
    assertThat(info.getWaveformSrc(), equalTo(src("waveform.png")));
  }

  @Test
  public void removeInstrumentSample() throws IOException
  {
    Files.copy(new File("fixture/sample.mp3"), file("sample.mp3"));
    Files.copy(new File("fixture/waveform.png"), file("waveform.png"));

    service.removeObjectSample("instrument", "test");

    assertFalse(file("sample.mp3").exists());
    assertFalse(file("waveform.png").exists());
  }

  // ----------------------------------------------------------------------------------------------

  private static File file(String fileName)
  {
    File dir = new File("fixture/tomcat/webapps/media/atlas/instrument/test/");
    dir.mkdirs();
    return new File(dir, fileName);
  }

  private static MediaSRC src(String fileName)
  {
    return new MediaSRC("/media/atlas/instrument/test/" + fileName);
  }
}
