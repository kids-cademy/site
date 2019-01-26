package com.kidscademy.media;

import java.io.File;
import java.io.IOException;

/**
 * Process audio files.
 * 
 * @author Iulian Rotaru
 */
public interface AudioProcessor {
    AudioSampleInfo getAudioFileInfo(File audioFile) throws IOException;

    void generateWaveform(File audioFile, File waveformFile) throws IOException;

    /**
     * Trim silence from both start and end of given audio file. Inside silence is
     * not processed. Trimmed audio is saved on target file.
     * <p>
     * Silence trim is performed till found at least half seconds of signal. This
     * allows for eliminating bursts of noises.
     * <p>
     * Implementation note: dues to implementation limitations files larger than
     * {@link CT#MAX_TRIM_FILE_SIZE} are not guaranteed to be processed correctly
     * and is possible to inadvertently remove inside silence.
     * 
     * @param audioFile
     *            source audio file,
     * @param targetFile
     *            target file.
     * @throws IOException
     *             if processing fails.
     */
    void trimSilence(File audioFile, File targetFile) throws IOException;

    void convertToMono(File audioFile, File targetFile) throws IOException;

    /**
     * Normalize audio level peak to 0dB. Normalized audio is saved on target file.
     * 
     * @param audioFile
     *            source audio file,
     * @param targetFile
     *            target file.
     * @throws IOException
     *             if processing fails.
     */
    void normalizeLevel(File audioFile, File targetFile) throws IOException;
}
