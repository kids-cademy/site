package com.kidscademy.media;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import js.lang.BugError;
import js.log.Log;
import js.log.LogFactory;
import js.util.Classes;
import js.util.Strings;
import js.util.Types;

public class FFmpegProcess extends AbstractMediaProcess {
    private static final Log log = LogFactory.getLog(FFmpegProcess.class);

    @Override
    public <T> T exec(Type resultClass, String command) throws IOException {
	if (!Types.isKindOf(resultClass, ResultParser.class)) {
	    throw new BugError("FFmpeg processor requires result parser class.");
	}

	List<String> args = Strings.split("ffmpeg -y " + command);
	final Process process = start(args);
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
			((ResultParser) result).parse(line);
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
}
