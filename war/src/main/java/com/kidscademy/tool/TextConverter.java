package com.kidscademy.tool;

import java.io.File;
import java.io.IOException;

public interface TextConverter {
    void convertMdToHtm(File mdFile, File htmFile) throws IOException;

    void convertMdToStandaloneHtm(File mdFile, File htmFile) throws IOException;
}
