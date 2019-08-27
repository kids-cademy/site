package com.kidscademy.tool;

public enum ImageCompose {
    SRCIN("srcin");

    private String value;

    private ImageCompose(String value) {
        this.value = value;
    }

    public String value() {
        return value;
    }
}