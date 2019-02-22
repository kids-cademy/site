package com.kidscademy.tool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import js.lang.BugError;

public class PerceptualHashResult implements ResultParser {
    private static final String PH_HEADER = "  Channel perceptual hash:";
    private static final String RED_HEADER = "    Red, Hue:";
    private static final String GREEN_HEADER = "    Green, Chroma:";
    private static final String BLUE_HEADER = "    Blue, Luma:";

    private State state = State.WAIT_PH_HEADER;
    private StringBuilder values = new StringBuilder();
    private int index;

    @Override
    public void parse(String line) {
	switch (state) {
	case WAIT_PH_HEADER:
	    if (line.equals(PH_HEADER)) {
		state = State.RED_HEADER;
	    }
	    break;

	case RED_HEADER:
	    if (line.equals(RED_HEADER)) {
		state = State.RED_VALUES;
		index = 0;
	    }
	    break;

	case GREEN_HEADER:
	    if (line.equals(GREEN_HEADER)) {
		state = State.GREEN_VALUES;
		index = 0;
	    }
	    break;

	case BLUE_HEADER:
	    if (line.equals(BLUE_HEADER)) {
		state = State.BLUE_VALUES;
		index = 0;
	    }
	    break;

	case RED_VALUES:
	    if (index == 6) {
		state = State.GREEN_HEADER;
		break;
	    }
	    addValues(line);
	    break;

	case GREEN_VALUES:
	    if (index == 6) {
		state = State.BLUE_HEADER;
		break;
	    }
	    addValues(line);
	    break;

	case BLUE_VALUES:
	    if (index == 6) {
		state = State.IDLE;
		break;
	    }
	    addValues(line);
	    break;

	case IDLE:
	    break;
	}
    }

    public String getHash() {
	try {
	    MessageDigest digest = MessageDigest.getInstance("MD5");
	    digest.update(values.toString().getBytes());
	    return DatatypeConverter.printHexBinary(digest.digest()).toUpperCase();
	} catch (NoSuchAlgorithmException e) {
	    throw new BugError("Java runtime without MD5 support.");
	}
    }

    private void addValues(String line) {
	int valuesPosition = line.indexOf(':') + 1;
	if (valuesPosition == 0) {
	    throw new BugError("Invalid perceptual hash value |%s|.", line);
	}

	String[] tuplu = line.substring(valuesPosition).split(",");
	values.append(tuplu[0].trim());
	values.append(tuplu[1].trim());
	++index;
    }

    // --------------------------------------------------------------------------------------------

    private enum State {
	WAIT_PH_HEADER, RED_HEADER, GREEN_HEADER, BLUE_HEADER, RED_VALUES, GREEN_VALUES, BLUE_VALUES, IDLE
    }
}
