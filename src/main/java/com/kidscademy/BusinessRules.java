package com.kidscademy;

import java.io.File;
import java.io.IOException;

import com.kidscademy.tool.ImageProcessor;

import js.core.Factory;
import js.rmi.BusinessException;

public class BusinessRules {
    private static final int NOT_TRANSPARENT_FEATURED_PICTURE = 0x0001;

    private static final ImageProcessor image;
    static {
	image = Factory.getInstance(ImageProcessor.class);
    }

    public static void transparentFeaturedPicture(String imageKind, File imageFile)
	    throws BusinessException, IOException {
	if (imageKind.contentEquals("featured") && image.isOpaque(imageFile)) {
	    throw new BusinessException(NOT_TRANSPARENT_FEATURED_PICTURE);
	}
    }
}
