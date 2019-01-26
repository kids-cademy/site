package com.kidscademy.media;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import com.kidscademy.CT;

import js.core.AppContext;
import js.json.Json;
import js.json.impl.JsonParserException;
import js.lang.BugError;
import js.log.Log;
import js.log.LogFactory;
import js.util.Classes;
import js.util.Strings;

public class AudioProcessorImpl implements AudioProcessor {
    private static final Log log = LogFactory.getLog(AudioProcessorImpl.class);

    private static final long FFMPEG_TIMEOUT = 16000L;

    private static final int WAVEFORM_WIDTH = 800;
    private static final int WAVEFORM_HEIGHT = 160;
    private static final String WAVEFORM_COLOR = "#0000FF";
    private static final String XAXIS_COLOR = "#FFFFFF";

    /** Duration of non-silence to stop audio silence processing. */
    private static final float SILENCE_DURATION = 0.5F;

    private final File waveformXAxisFile;
    private final File waveformGradientFile;

    private final ImageProcessor image;

    public AudioProcessorImpl(AppContext context, ImageProcessor image) throws IOException {
	log.trace("AudioProcessorImpl(AppContext, ImageProcessor)");
	this.image = image;

	waveformXAxisFile = context.getAppFile("waveform-x-axis.png");
	if (!waveformXAxisFile.exists()) {
	    this.image.generateXAxis(waveformXAxisFile, WAVEFORM_WIDTH, WAVEFORM_HEIGHT, XAXIS_COLOR);
	}

	waveformGradientFile = context.getAppFile("waveform-gradient.png");
	if (!waveformGradientFile.exists()) {
	    this.image.generateRainbowGradient(waveformGradientFile, WAVEFORM_WIDTH, WAVEFORM_HEIGHT);
	}
    }

    @Override
    public AudioSampleInfo getAudioFileInfo(File audioFile) throws IOException {
	Result result = probe(Result.class, "-show_format -show_streams ${audioFile}", audioFile);

	AudioSampleInfo info = new AudioSampleInfo();
	info.setFileName(result.format.filename);
	info.setFileSize(result.format.size);

	Stream stream = result.streams[0];
	info.setCodec(stream.codec_long_name);
	info.setDuration((int) Math.round(stream.duration * 1000));
	info.setChannels(stream.channels);
	info.setSampleRate(stream.sample_rate);
	info.setBitRate(stream.bit_rate);
	return info;
    }

    static class Result {
	Format format;
	Stream[] streams;
    }

    static class Format {
	String filename;
	int nb_streams;
	int nb_programs;
	String format_name;
	String format_long_name;
	double start_time;
	double duration;
	int size;
	int bit_rate;
	int probe_score;
    }

    static class Stream {
	int index;
	String codec_name;
	String codec_long_name;
	String codec_type;
	String codec_time_base;
	String codec_tag_string;
	String codec_tag;
	String sample_fmt;
	int sample_rate;
	int channels;
	String channel_layout;
	int bits_per_sample;
	String r_frame_rate;
	String avg_frame_rate;
	String time_base;
	double start_pts;
	double start_time;
	long duration_ts;
	double duration;
	int bit_rate;
	Map<String, Integer> disposition;
    }

    @Override
    public void generateWaveform(File audioFile, File waveformFile) throws IOException {
	// sample should be already mono

	// create waveform image with solid fill
	// sample file should already be mono
	// ffmpeg -i ${audio-file} -filter_complex
	// "showwavespic=s=${width}x${height}:colors=${color}" ${waveform-file}

	// if not add filter aformat=channel_layouts=mono to mix down channels
	// ffmpeg -i ${audio-file} -filter_complex
	// "aformat=channel_layouts=mono,showwavespic=s=${width}x${height}:colors=${color}"
	// ${waveform-file}

	// hard to believe but there is a bug on waveform generation when use linear
	// scale, that is default
	// generated waveform height is half of requested value
	// work around is to create waveform with double height then crop to original,
	// i.e. requested value

	// c := height adjustment coefficient
	// h := requested height
	// H := canvas height for generated waveform
	// o := vertical offset used to crop back to original height (h)

	// c = 0.707
	// h = H * c
	//
	// H = h / c
	// o = h*(1-c)/(2*c)

	float c = 0.707F;
	float h = WAVEFORM_HEIGHT;
	float H = h / c;
	float o = h * (1 - c) / (2 * c);

	// create waveform with double height to compensate ffmpeg bug
	String filter = format("showwavespic=size=${width}x${height}:colors=${color}:scale=lin", WAVEFORM_WIDTH,
		Math.round(H), WAVEFORM_COLOR);
	exec("-i ${audioFile} -filter_complex ${filter} ${waveformFile}", audioFile, filter, waveformFile);

	// crop waveform image to original height
	image.crop(waveformFile, WAVEFORM_WIDTH, WAVEFORM_HEIGHT, 0, Math.round(o));

	// replace waveform solid color with gradient
	// SRC-IN alpha blending replace SRC solid color with gradient and leave SRC
	// alpha as it is
	image.compose(waveformFile, waveformGradientFile, ImageProcessor.Compose.SRCIN);

	// overlap xaxis on waveform
	image.overlap(waveformFile, waveformXAxisFile);
    }

    @Override
    public void trimSilence(File audioFile, File targetFile) throws IOException {

	// first attempt was to use stop parameters - as stated in API, for end silence
	// remove but I was not able to avoid removing inside silence

	// the only solution found on net for end silence is to reverse audio sample and
	// perform start silence remove, that is working properly

	// https://superuser.com/questions/1362176/how-to-trim-silence-only-from-beginning-and-end-of-mp3-files-using-ffmpeg

	// anyway there is warning on reverse filter about memory consumption for which
	// reason limit audio file size to hard coded magic value
	// for large audio samples fall back to 'standard' silence remove and accept
	// risk for inside silence remove

	if (audioFile.length() > CT.MAX_TRIM_FILE_SIZE) {
	    String start = format(
		    "silenceremove=start_periods=1:start_duration=${start_duration}:start_threshold=-65dB:detection=peak",
		    SILENCE_DURATION);

	    String stop = format(
		    "silenceremove=stop_periods=1:stop_duration=${stop_duration}:stop_threshold=-65dB:detection=peak",
		    SILENCE_DURATION);

	    exec("-i ${audioFile} -af ${start},${stop} ${targetFile}", audioFile, start, stop, targetFile);
	    return;
	}

	String remove = format(
		"silenceremove=start_periods=1:start_duration=${start_duration}:start_threshold=-60dB:detection=peak",
		SILENCE_DURATION);

	String reverse = "aformat=dblp,areverse";

	exec("-i ${audioFile} -af ${remove},${reverse},${remove},${reverse} ${targetFile}", audioFile, remove, reverse,
		remove, reverse, targetFile);
    }

    @Override
    public void convertToMono(File audioFile, File targetFile) throws IOException {
	// do not use ffmpeg down mix because it adjust levels, accordingly
	// recommendations, with -3dB
	// exec("-i ${audioFile} -ac 1 ${targetFile}", audioFile, targetFile);

	// uses instead pan with half level to ensure peaks are not trimmed
	exec("-i ${audioFile} -af pan=mono|c0=0.5*c0+0.5*c1 ${targetFile}", audioFile, targetFile);

	// when save using 'mono' audio layout, audio codec reduce bit rate to half,
	// e.g. 64Kbit/s if original was 128Kbit/s
	// one can force audio bit rate with -b:a but do not see any reason to do it
	// exec("-i ${audioFile} -af pan=mono|c0=0.5*c0+0.5*c1 -b:a 128k ${targetFile}",
	// audioFile, targetFile);
    }

    @Override
    public void normalizeLevel(File audioFile, File targetFile) throws IOException {
	VolumeInfo volume = exec(VolumeInfo.class, "-i ${audioFile} -af volumedetect -f null /dev/null", audioFile);
	// do not see how peak can exceed 0dB but just to be sure...
	if (volume.getPeak() > 0 || volume.getPeak() < -0.4) {
	    exec("-i ${audioFile} -af volume=${adjustment}dB ${targetFile}", audioFile, -volume.getPeak(), targetFile);
	}
    }

    // ----------------------------------------------------------------------------------------------
    // UTILITY METHODS

    private static void exec(String format, Object... args) throws IOException {
	exec(VoidResult.class, format, args);
    }

    private static <T extends ResultParser> T exec(Class<T> resultClass, String format, Object... args)
	    throws IOException {
	List<String> command = Strings.split(format(format, args));
	command.add(0, "-y");
	command.add(0, "ffmpeg");

	final Process process = start(command);
	final T result = Classes.newInstance(resultClass);

	final Object lock = new Object();
	// create process standard output reader and wait process printout
	Thread stdinReader = new Thread(new Runnable() {
	    @Override
	    public void run() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

		try {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
			System.out.println(line);
			result.parse(line);
		    }
		} catch (IOException e) {
		    log.error(e);
		} finally {
		    close(reader);
		}

		synchronized (lock) {
		    lock.notify();
		}
	    }
	});

	wait(process, stdinReader, lock);
	return result;
    }

    private static <T> T probe(final Class<T> resultClass, String format, Object... args) throws IOException {
	List<String> command = Strings.split(format(format, args));
	command.add(0, "json");
	command.add(0, "-print_format");
	command.add(0, "quiet");
	command.add(0, "-v");
	command.add(0, "ffprobe");

	final Process process = start(command);
	final Object lock = new Object();

	class StdinReader implements Runnable {
	    private final Json json;
	    volatile T result;

	    StdinReader() {
		this.json = Classes.loadService(Json.class);
	    }

	    @Override
	    public void run() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

		try {
		    result = json.parse(reader, resultClass);
		} catch (IOException | JsonParserException e) {
		    log.error(e);
		} finally {
		    close(reader);
		}

		synchronized (lock) {
		    lock.notify();
		}
	    }
	}

	StdinReader stdinReader = new StdinReader();
	Thread stdinThread = new Thread(stdinReader);

	wait(process, stdinThread, lock);
	return stdinReader.result;
    }

    private static Process start(List<String> command) throws IOException {
	ProcessBuilder builder = new ProcessBuilder(command);
	// redirect STDERR to STDOUT so that reading process.getInputStream get them
	// both
	builder.redirectErrorStream(true);
	log.debug("Create process |%s|.", Strings.join(command));
	return builder.start();
    }

    private static void wait(Process process, Thread stdinThread, Object lock) throws IOException {
	long timeout = FFMPEG_TIMEOUT;
	long timestamp = System.currentTimeMillis() + timeout;

	synchronized (lock) {
	    stdinThread.start();
	    while (timeout > 0) {
		try {
		    lock.wait(timeout);
		    timeout = timestamp - System.currentTimeMillis();
		} catch (InterruptedException e) {
		    continue;
		}
		break;
	    }
	}

	if (timeout <= 0) {
	    process.destroy();
	    throw new IOException("FFmpeg process timeout. See stdout logs for process printout.");
	}

	int returnCode = -1;
	try {
	    returnCode = process.waitFor();
	} catch (InterruptedException e) {
	    log.error(e);
	}
	if (returnCode != 0) {
	    throw new IOException(String
		    .format("FFmpeg process fail. Exit code |%d|. See stdout logs for process printout.", returnCode));
	}
    }

    private static void close(Reader reader) {
	try {
	    reader.close();
	} catch (IOException e) {
	    log.error(e);
	}
    }

    private static String format(String format, Object... args) {
	// 0: NONE
	// 1: APPEND
	// 2: WAIT_OPEN_BRACE
	// 3: VARIABLE
	int state = 1;

	StringBuilder valueBuilder = new StringBuilder();
	for (int charIndex = 0, argIndex = 0; charIndex < format.length(); ++charIndex) {
	    char c = format.charAt(charIndex);
	    switch (state) {
	    case 1:
		if (c == '$') {
		    state = 2;
		    break;
		}
		valueBuilder.append(c);
		break;

	    case 2:
		if (c != '{') {
		    throw new BugError("Invalid command format...");
		}
		state = 3;

	    case 3:
		if (c == '}') {
		    if (argIndex == args.length) {
			throw new BugError("Arguments overflow...");
		    }
		    valueBuilder.append(args[argIndex]);
		    ++argIndex;
		    state = 1;
		}
		break;
	    }
	}
	return valueBuilder.toString();
    }

    // ----------------------------------------------------------------------------------------------
    // UTILITY TYPES

    private static interface ResultParser {
	void parse(String line);
    }

    public static class VolumeInfo implements ResultParser {
	private static final String LABEL_AVERAGE = "mean_volume:";
	private static final String LABEL_PEAK = "max_volume:";

	private float average;
	private float peak;

	@Override
	public void parse(String line) {
	    if (parseAverage(line)) {
		return;
	    }
	    if (parsePeak(line)) {
		return;
	    }
	}

	public float getAverage() {
	    return average;
	}

	public float getPeak() {
	    return peak;
	}

	private boolean parseAverage(String line) {
	    int index = line.indexOf(LABEL_AVERAGE);
	    if (index == -1) {
		return false;
	    }
	    average = parseFloat(line.substring(index + LABEL_AVERAGE.length()));
	    return true;
	}

	private boolean parsePeak(String line) {
	    int index = line.indexOf(LABEL_PEAK);
	    if (index == -1) {
		return false;
	    }
	    peak = parseFloat(line.substring(index + LABEL_PEAK.length()));
	    return true;
	}

	private static float parseFloat(String value) {
	    int unitsIndex = value.indexOf("dB");
	    return Float.parseFloat(value.substring(0, unitsIndex));
	}
    }

    private static class VoidResult implements ResultParser {
	@Override
	public void parse(String line) {
	}
    }
}
