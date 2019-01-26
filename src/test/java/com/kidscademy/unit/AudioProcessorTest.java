package com.kidscademy.unit;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.io.FileMatchers.anExistingFile;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.kidscademy.media.AudioProcessor;
import com.kidscademy.media.AudioProcessorImpl;
import com.kidscademy.media.AudioSampleInfo;
import com.kidscademy.media.ImageProcessor;

import js.core.AppContext;

@RunWith(MockitoJUnitRunner.class)
public class AudioProcessorTest {
    @Mock
    private AppContext context;
    @Mock
    private ImageProcessor image;

    private AudioProcessor audio;

    @Before
    public void beforeTest() throws IOException {
	when(context.getAppFile(anyString())).thenAnswer(new Answer<File>() {
	    @Override
	    public File answer(InvocationOnMock invocation) throws Throwable {
		return new File("fixture/audio/" + (String) invocation.getArguments()[0]);
	    }
	});

	audio = new AudioProcessorImpl(context, image);
    }

    @After
    public void afterTest() {
	File targetFile = new File("fixture/audio/target.mp3");
	targetFile.delete();
    }

    @Test
    public void getAudioFileInfo() throws IOException {
	File audioFile = new File("fixture/audio/sample.mp3");
	AudioSampleInfo info = audio.getAudioFileInfo(audioFile);

	assertThat(info, notNullValue());
	assertThat(info.getFileName(), equalTo("fixture\\audio\\sample.mp3"));
	assertThat(info.getFileSize(), equalTo(3041906));
	assertThat(info.getCodec(), equalTo("MP3 (MPEG audio layer 3)"));
	assertThat(info.getDuration(), equalTo(190119));
	assertThat(info.getChannels(), equalTo(2));
	assertThat(info.getSampleRate(), equalTo(44100));
	assertThat(info.getBitRate(), equalTo(128000));
    }

    @Test
    public void trimSilence() throws IOException {
	File audioFile = new File("fixture/audio/silence.mp3");
	File targetFile = new File("fixture/audio/target.mp3");

	audio.trimSilence(audioFile, targetFile);
	assertThat(targetFile, anExistingFile());

	AudioSampleInfo info = audio.getAudioFileInfo(targetFile);
	assertThat((double) info.getDuration(), closeTo(28000, 100));
    }

    @Test
    public void convertToMono() throws IOException {
	File stereoFile = new File("fixture/audio/sample.mp3");
	File monoFile = new File("fixture/audio/target.mp3");

	audio.convertToMono(stereoFile, monoFile);
	assertThat(monoFile, anExistingFile());

	assertThat(audio.getAudioFileInfo(stereoFile).getChannels(), equalTo(2));
	assertThat(audio.getAudioFileInfo(monoFile).getChannels(), equalTo(1));
    }

    @Test
    public void normalizeLevel() throws IOException {
	File audioFile = new File("fixture/audio/sample.mp3");
	File targetFile = new File("fixture/audio/target.mp3");

	audio.normalizeLevel(audioFile, targetFile);
    }
}
