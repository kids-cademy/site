package com.kidscademy;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.im4java.core.IM4JavaException;

import com.kidscademy.atlas.Instrument;
import com.kidscademy.atlas.Link;
import com.kidscademy.atlas.MediaSRC;
import com.kidscademy.atlas.UIObject;
import com.kidscademy.media.AudioSampleInfo;

import js.annotation.Service;
import js.http.form.Form;

@Service
public interface AtlasService {
    List<UIObject> getInstruments();

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

    List<UIObject> getRelatedInstruments(List<String> names);

    List<UIObject> getAvailableInstruments(Instrument.Category category, List<UIObject> related);

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

    /**
     * Upload audio sample. Form should have <code>dtype</code> and
     * <code>name</code> values - see {@link UIObject}, that are used to create
     * uploaded file path. Also form should have <code>file</code> stream.
     * <p>
     * This method creates object audio sample file, overwriting any existing.
     * 
     * @param form
     *            web interface form.
     * @return audio sample info with status after upload.
     * @throws IOException
     *             if upload fail for whatever reason.
     */
    AudioSampleInfo uploadAudioSample(Form form) throws IOException;

    /**
     * Normalize object audio sample peak level to 0dB.
     * 
     * @param object
     *            object owning audio sample.
     * @return audio sample info with status after processing.
     * @throws IOException
     *             if processing fail.
     */
    AudioSampleInfo normalizeAudioSample(UIObject object) throws IOException;

    /**
     * Convert object audio sample to mono, if currently is stereo. Before mixing
     * both channels, level is reduced to half in order to avoid peak level
     * trimming.
     * <p>
     * This method does nothing if audio file has already a single channel.
     * 
     * @param object
     *            object owning audio sample.
     * @return audio sample info with status after processing.
     * @throws IOException
     *             if processing fail.
     */
    AudioSampleInfo convertAudioSampleToMono(UIObject object) throws IOException;

    /**
     * Trim silence from both start and end of object audio sample. Inside silence
     * is not processed.
     * <p>
     * Silence trim is performed till found at least half seconds of signal. This
     * allows for eliminating bursts of noises.
     * <p>
     * Implementation note: dues to implementation limitations files larger than
     * {@link CT#MAX_TRIM_FILE_SIZE} are not guaranteed to be processed correctly
     * and is possible to inadvertently remove inside silence.
     * 
     * @param object
     *            object owning audio sample.
     * @return audio sample info with status after processing.
     * @throws IOException
     *             if processing fail.
     */
    AudioSampleInfo trimAudioSampleSilence(UIObject object) throws IOException;

    /**
     * Extract 30 seconds sample from object audio file. Sample is extracted
     * starting from given timestamp, expressed in seconds with decimal.
     * 
     * @param object
     *            object owning audio sample.
     * @param start
     *            sample start timestamp, in seconds with decimals.
     * @return audio sample info with status after processing.
     * @throws IOException
     *             if processing fail.
     */
    AudioSampleInfo cutAudioSample(UIObject object, float start) throws IOException;

    /**
     * Add triangular fade-in of hard coded duration at the beginning of object
     * audio sample. Current hard coded duration is 2.5 seconds.
     * 
     * @param object
     *            object owning audio sample.
     * @return audio sample info with status after processing.
     * @throws IOException
     *             if processing fail.
     */
    AudioSampleInfo fadeInAudioSample(UIObject object) throws IOException;

    /**
     * Add triangular fade-out of hard coded duration at the end of object audio
     * sample. Current hard coded duration is 2.5 seconds.
     * 
     * @param object
     *            object owning audio sample.
     * @return audio sample info with status after processing.
     * @throws IOException
     *             if processing fail.
     */
    AudioSampleInfo fadeOutAudioSample(UIObject object) throws IOException;

    /**
     * Generate waveform image for object audio sample. Generated image file is
     * stored on object media directory - the same directory where audio sample file
     * resides.
     * 
     * @param object
     *            object owning audio sample.
     * @return root-relative media SRC for generated waveform image.
     * @throws IOException
     *             if processing fail.
     */
    MediaSRC generateWaveform(UIObject object) throws IOException;

    /**
     * Undo last processing on object audio sample.
     * 
     * @param object
     *            object owning audio sample.
     * @return audio sample info with status after undo.
     * @throws IOException
     *             if processing fail.
     */
    AudioSampleInfo undoAudioSampleProcessing(UIObject object) throws IOException;

    /**
     * Commit processing on object audio sample. Audio processing on object sample
     * is performed in sequence, saving previous state. On commit all intermediate
     * saved files are removed and all changes are copied to base sample file.
     * 
     * @param object
     *            object owning audio sample.
     * @return audio sample info with status after processing.
     * @throws IOException
     *             if processing fail.
     */
    AudioSampleInfo commitAudioSampleProcessing(UIObject object) throws IOException;

    /**
     * Remove object audio sample.
     * 
     * @param object
     *            object owning audio sample.
     * @throws IOException
     *             if processing fail.
     */
    void removeAudioSample(UIObject object) throws IOException;
}
