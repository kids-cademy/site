package com.kidscademy.tool;

public class ImageInfo {
    /** Sample file name. */
    private String fileName;
    /** Sample file size, in bytes. */
    private int fileSize;

    private MediaType type;
    private int width;
    private int height;

    public String getFileName() {
	return fileName;
    }

    public void setFileName(String fileName) {
	this.fileName = fileName;
    }

    public int getFileSize() {
	return fileSize;
    }

    public void setFileSize(int fileSize) {
	this.fileSize = fileSize;
    }

    public MediaType getType() {
	return type;
    }

    public void setType(MediaType type) {
	this.type = type;
    }

    public int getWidth() {
	return width;
    }

    public void setWidth(int width) {
	this.width = width;
    }

    public int getHeight() {
	return height;
    }

    public void setHeight(int height) {
	this.height = height;
    }
}
