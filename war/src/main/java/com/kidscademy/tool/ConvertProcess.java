package com.kidscademy.tool;

import js.annotation.ContextParam;
import js.util.Strings;

public class ConvertProcess extends ImageMagickProcess {
    @ContextParam("image.magick.convert")
    private static String BIN;

    @Override
    protected String args(String command) {
	return Strings.concat(BIN, " ", command);
    }
}
