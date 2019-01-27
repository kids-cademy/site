package com.kidscademy;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.im4java.core.IM4JavaException;

import com.kidscademy.atlas.GraphicObject;
import com.kidscademy.atlas.Instrument;
import com.kidscademy.atlas.Link;
import com.kidscademy.atlas.Login;
import com.kidscademy.atlas.MediaSRC;
import com.kidscademy.media.AudioSampleInfo;

import js.annotation.Public;
import js.annotation.Service;
import js.http.form.Form;

@Service
public interface AtlasService {
    @Public
    boolean login(Login login);

    @Public
    boolean isAuthenticated();

    List<Instrument> getInstruments();

    /**
     * Get instrument entity. If ID is zero returns an empty instance.
     * 
     * @param instrumentId
     * @return instrument instance, possible empty or null.
     * @throws IOException
     */
    Instrument getInstrument(int instrumentId) throws IOException;

    Instrument getInstrumentByName(String name);

    int saveInstrument(Instrument instrument);

    List<GraphicObject> getRelatedInstruments(List<String> names);

    List<GraphicObject> getAvailableInstruments(Instrument.Category category, List<GraphicObject> related);

    MediaSRC uploadPictureFile(Form form) throws IOException, InterruptedException, IM4JavaException;

    MediaSRC uploadIconFile(Form form) throws IOException, InterruptedException, IM4JavaException;

    MediaSRC uploadThumbnailFile(Form form) throws IOException, InterruptedException, IM4JavaException;

    /**
     * Create object icon from picture.
     *
     * @param collectionName
     * @param objectName
     * @return
     * @throws IOException
     */
    MediaSRC createObjectIcon(String collectionName, String objectName) throws IOException;

    Link createLink(URL url);

    // ----------------------------------------------------------------------------------------------
    // Object Audio Sample Services

    AudioSampleInfo uploadAudioSample(Form form) throws IOException;

    AudioSampleInfo normalizeSample(String collectionName, String objectName) throws IOException;

    AudioSampleInfo convertToMono(String collectionName, String objectName) throws IOException;

    AudioSampleInfo trimSilence(String collectionName, String objectName) throws IOException;

    AudioSampleInfo cutAudioSample(String collectionName, String objectName, float start) throws IOException;

    AudioSampleInfo fadeInAudioSample(String collectionName, String objectName) throws IOException;

    AudioSampleInfo fadeOutAudioSample(String collectionName, String objectName) throws IOException;

    MediaSRC generateWaveform(String collectionName, String objectName) throws IOException;

    AudioSampleInfo undoMediaProcessing(String collectionName, String objectName) throws IOException;

    AudioSampleInfo commitMediaProcessing(String collectionName, String objectName) throws IOException;

    void removeObjectSample(String collectionName, String objectName) throws IOException;
}
