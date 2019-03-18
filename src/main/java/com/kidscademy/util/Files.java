package com.kidscademy.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.kidscademy.atlas.MediaSRC;
import com.kidscademy.atlas.MediaWrapper;

import js.util.Params;
import js.util.Strings;

public final class Files extends js.util.Files {
    private Files() {
    }

    public static MediaSRC mediaSrc(MediaWrapper object, String mediaFile) {
	return mediaSrc(object.getDtype(), object.getName(), mediaFile);
    }

    public static MediaSRC mediaSrc(MediaWrapper object, String mediaFile, String annotation) {
	String basepath = Files.removeExtension(mediaFile);
	String extension = Files.getExtension(mediaFile);
	return mediaSrc(object.getDtype(), object.getName(), Strings.concat(basepath, '_', annotation, '.', extension));
    }

    public static MediaSRC mediaSrc(String collectionName, String objectName, String mediaFile) {
	Params.notNullOrEmpty(collectionName, "Collection name");
	Params.notNullOrEmpty(objectName, "Object name");
	if (mediaFile == null) {
	    return null;
	}
	return new MediaSRC(Strings.concat("/media/atlas/", collectionName, "/", objectName, '/', mediaFile));
    }

    public static MediaSRC linkSrc(String iconName) {
	return new MediaSRC("/media/link/" + iconName);
    }

    private static File REPOSIOTRY_DIR = new File(System.getProperty("catalina.base") + "/webapps");

    public static File mediaFile(MediaSRC mediaSrc) {
	// repository dir := ${catalina.base}/webapps
	// media SRC := /media/atlas/instrument/object/file
	return new File(REPOSIOTRY_DIR, mediaSrc.value());
    }

    public static File mediaFile(MediaWrapper object, String mediaFileName) {
	// repository dir := ${catalina.base}/webapps
	// path := /media/atlas/instrument/object/file
	return new File(REPOSIOTRY_DIR, Files.mediaSrc(object, mediaFileName).value());
    }

    private static final Map<String, String> MEDIA_TYPES = new HashMap<>();
    static {
	MEDIA_TYPES.put("jpg", "image/jpeg");
	MEDIA_TYPES.put("jpeg", "image/jpeg");
	MEDIA_TYPES.put("png", "image/png");
    }

    public static String probeContentType(String path) {
	return MEDIA_TYPES.get(getExtension(path));
    }
}
