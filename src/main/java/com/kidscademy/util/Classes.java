package com.kidscademy.util;

import com.kidscademy.atlas.AtlasObject;

import js.util.Strings;

public final class Classes extends js.util.Classes {
    private Classes() {
    }

    public static String dtype(Class<? extends AtlasObject> type) {
	return type.getSimpleName().toLowerCase();
    }

    public static String entityName(String dtype) {
	return Strings.toTitleCase(dtype);
    }
}
