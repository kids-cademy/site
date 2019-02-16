package com.kidscademy.media;

import java.io.IOException;
import java.lang.reflect.Type;

public interface MediaProcess {
    void exec(String command) throws IOException;

    <T> T exec(Type type, String command) throws IOException;
}
