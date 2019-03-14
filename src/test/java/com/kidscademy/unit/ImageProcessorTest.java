package com.kidscademy.unit;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.kidscademy.tool.ImageInfo;
import com.kidscademy.tool.ImageInfoResult;
import com.kidscademy.tool.ImageMagickProcess;
import com.kidscademy.tool.ImageProcessor;
import com.kidscademy.tool.ImageProcessorImpl;
import com.kidscademy.tool.MediaType;

@RunWith(MockitoJUnitRunner.class)
public class ImageProcessorTest {
    @Mock
    private ImageMagickProcess convert;
    @Mock
    private ImageMagickProcess identify;

    private ImageProcessor processor;

    @Before
    public void beforeTest() {
	processor = new ImageProcessorImpl(convert, identify);
    }

    @Test
    public void getImageInfo() throws IOException {
	ImageInfoResult result = new ImageInfoResult();
	result.parse("picture.jpg JPEG 920x560 920x560+0+0 8-bit sRGB 178854B 0.047u 0:00.045");
	
	when(identify.exec(eq(ImageInfoResult.class), anyString())).thenReturn(result);

	File imageFile = new File("picture.jpg");
	ImageInfo imageInfo = processor.getImageInfo(imageFile);

	assertThat(imageInfo, notNullValue());
	assertThat(imageInfo.getFileName(), equalTo("picture.jpg"));
	assertThat(imageInfo.getFileSize(), equalTo(178854));
	assertThat(imageInfo.getType(), equalTo(MediaType.JPEG));
	assertThat(imageInfo.getWidth(), equalTo(920));
	assertThat(imageInfo.getHeight(), equalTo(560));
    }
}
