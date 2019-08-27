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

import com.kidscademy.AtlasService;
import com.kidscademy.atlas.Instrument;
import com.kidscademy.atlas.MediaSRC;
import com.kidscademy.atlas.UIObject;
import com.kidscademy.dao.AtlasDao;
import com.kidscademy.impl.AtlasServiceImpl;
import com.kidscademy.tool.AudioProcessor;
import com.kidscademy.tool.AudioSampleInfo;
import com.kidscademy.tool.ImageProcessor;
import com.kidscademy.util.Files;
import com.kidscademy.www.CambridgeDictionary;
import com.kidscademy.www.SoftSchools;
import com.kidscademy.www.TheFreeDictionary;
import com.kidscademy.www.Wikipedia;

import js.core.AppContext;
import js.http.form.Form;
import js.util.Classes;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceTest {
    @Mock
    private AppContext context;
    @Mock
    private AtlasDao dao;
    @Mock
    private AudioProcessor audio;
    @Mock
    private ImageProcessor image;
    @Mock
    private Wikipedia wikipedia;
    @Mock
    private SoftSchools softSchools;
    @Mock
    private TheFreeDictionary freeDictionary;
    @Mock
    private CambridgeDictionary cambridgeDictionary;

    private AtlasService service;

    @BeforeClass
    public static void beforeClass() {
	Classes.setFieldValue(Files.class, "REPOSIOTRY_DIR", "fixture/tomcat/webapps");
    }

    @Before
    public void beforeTest() {
	service = new AtlasServiceImpl(context, dao, audio, image, wikipedia, softSchools, freeDictionary, cambridgeDictionary);
	file("sample.mp3").delete();
	file("sample_1.mp3").delete();
	file("sample_2.mp3").delete();
    }

    @Test
    public void uploadAudioSample() throws IOException {
	Form form = Mockito.mock(Form.class);
	when(form.getValue("dtype")).thenReturn("instrument");
	when(form.getValue("name")).thenReturn("test");
	File uploadFile = new File("fixture/upload.mp3");
	Files.copy(new File("fixture/audio/sample.mp3"), uploadFile);
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
    public void normalizeSample() throws IOException {
	Files.copy(new File("fixture/audio/sample.mp3"), file("sample.mp3"));

	AudioSampleInfo info = new AudioSampleInfo();
	when(audio.getAudioFileInfo(any(File.class))).thenReturn(info);

	info = service.normalizeAudioSample(new UIObject("instrument", "test"));

	ArgumentCaptor<File> audioFile = ArgumentCaptor.forClass(File.class);
	ArgumentCaptor<File> targetFile = ArgumentCaptor.forClass(File.class);
	ArgumentCaptor<File> waveformFile = ArgumentCaptor.forClass(File.class);

	verify(audio).normalizeLevel(audioFile.capture(), targetFile.capture());
	assertThat(audioFile.getValue(), equalTo(file("sample.mp3")));
	assertThat(targetFile.getValue(), equalTo(file("sample_1.mp3")));

	verify(audio).generateWaveform(targetFile.capture(), waveformFile.capture());
	assertThat(targetFile.getValue(), equalTo(file("sample.mp3")));
	assertThat(waveformFile.getValue(), equalTo(file("waveform.png")));

	assertThat(info, notNullValue());
	assertThat(info.getSampleSrc(), equalTo(src("sample.mp3")));
	assertThat(info.getWaveformSrc(), equalTo(src("waveform.png")));
    }

    @Test
    public void convertToMono() throws IOException {
	Files.copy(new File("fixture/audio/sample.mp3"), file("sample.mp3"));

	AudioSampleInfo info = new AudioSampleInfo();
	when(audio.getAudioFileInfo(any(File.class))).thenReturn(info);

	info = service.convertAudioSampleToMono(new UIObject("instrument", "test"));

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
    public void trimSilence() throws IOException {
	Files.copy(new File("fixture/audio/sample.mp3"), file("sample.mp3"));

	AudioSampleInfo info = new AudioSampleInfo();
	when(audio.getAudioFileInfo(any(File.class))).thenReturn(info);

	info = service.trimAudioSampleSilence(new UIObject("instrument", "test"));

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
    public void generateWaveform() throws IOException {
	Files.copy(new File("fixture/audio/sample.mp3"), file("sample.mp3"));

	MediaSRC waveformSrc = service.generateWaveform(new UIObject(1, "instrument", "test"));

	assertThat(waveformSrc, notNullValue());
	assertThat(waveformSrc, equalTo(src("waveform.png")));
    }

    @Test
    public void undoMediaProcessing() throws IOException {
	Files.copy(new File("fixture/audio/sample.mp3"), file("sample.mp3"));
	Files.copy(new File("fixture/audio/sample.mp3"), file("sample_1.mp3"));
	Files.copy(new File("fixture/audio/sample.mp3"), file("sample_2.mp3"));

	AudioSampleInfo info = new AudioSampleInfo();
	when(audio.getAudioFileInfo(any(File.class))).thenReturn(info);

	info = service.undoAudioSampleProcessing(new UIObject("instrument", "test"));

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
    public void saveInstrument() throws IOException {
	Files.copy(new File("fixture/audio/sample.mp3"), file("sample.mp3"));
	Files.copy(new File("fixture/audio/sample.mp3"), file("sample_1.mp3"));
	Files.copy(new File("fixture/audio/sample.mp3"), file("sample_2.mp3"));

	AudioSampleInfo info = new AudioSampleInfo();
	info.setSampleSrc(src("sample.mp3"));
	info.setWaveformSrc(src("waveform.png"));

	Instrument instrument = new Instrument();
	instrument.setName("test");
	instrument.setSampleSrc(src("sample.mp3"));
	instrument.setSampleInfo(info);

	instrument = service.saveInstrument(instrument);
	info = instrument.getSampleInfo();

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
    public void removeInstrumentSample() throws IOException {
	Files.copy(new File("fixture/audio/sample.mp3"), file("sample.mp3"));
	Files.copy(new File("fixture/image/waveform.png"), file("waveform.png"));

	service.removeAudioSample(new UIObject(1, "instrument", "test"));

	assertFalse(file("sample.mp3").exists());
	assertFalse(file("waveform.png").exists());
    }

    // ----------------------------------------------------------------------------------------------

    private static File file(String fileName) {
	File dir = new File("fixture/tomcat/webapps/media/atlas/instrument/test/");
	dir.mkdirs();
	return new File(dir, fileName);
    }

    private static MediaSRC src(String fileName) {
	return new MediaSRC("/media/atlas/instrument/test/" + fileName);
    }
}
