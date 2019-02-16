package com.kidscademy.it;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.io.FileMatchers.anExistingFile;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kidscademy.media.ConvertProcess;
import com.kidscademy.media.IdentifyProcess;
import com.kidscademy.media.ImageCompose;
import com.kidscademy.media.ImageInfo;
import com.kidscademy.media.ImageProcessor;
import com.kidscademy.media.ImageProcessorImpl;
import com.kidscademy.media.MediaType;

import js.util.Classes;
import js.util.Files;

public class ImageMagickTest {
    private ImageProcessor image;

    @BeforeClass
    public static void beforeClass() {
	Classes.setFieldValue(ConvertProcess.class, "BIN", "C://Program Files/ImageMagick-6.9.9-Q16-HDRI/convert.exe");
	Classes.setFieldValue(IdentifyProcess.class, "BIN",
		"C://Program Files/ImageMagick-6.9.9-Q16-HDRI/identify.exe");
    }

    @Before
    public void beforeTest() {
	image = new ImageProcessorImpl();
    }

    @Test
    public void patternForImageInfoResult() {
	Pattern FORMAT = Pattern.compile(
		"^(.+\\.(?i)(?:jpg|png)(?-i)) (JPEG|PNG) (\\d+)x(\\d+) (\\d+)x(\\d+)\\+(\\d+)\\+(\\d+) (8|16)-bit (sRGB|Grayscale Gray) (\\d+(?:B|MiB)) .+$");

	Matcher matcher = FORMAT.matcher("picture.jpg JPEG 920x560 920x560+0+0 8-bit sRGB 178854B 0.047u 0:00.045");
	assertThat(matcher, notNullValue());
	assertTrue(matcher.find());
	assertThat(matcher.group(1), equalTo("picture.jpg"));
	assertThat(matcher.group(2), equalTo("JPEG"));
	assertThat(matcher.group(3), equalTo("920"));
	assertThat(matcher.group(4), equalTo("560"));
	assertThat(matcher.group(5), equalTo("920"));
	assertThat(matcher.group(6), equalTo("560"));
	assertThat(matcher.group(7), equalTo("0"));
	assertThat(matcher.group(8), equalTo("0"));
	assertThat(matcher.group(9), equalTo("8"));
	assertThat(matcher.group(10), equalTo("sRGB"));
	assertThat(matcher.group(11), equalTo("178854B"));

	matcher = FORMAT.matcher("image.png PNG 800x140 800x140+0+0 16-bit Grayscale Gray 720B 0.000u 0:00.000");
	assertThat(matcher, notNullValue());
	assertTrue(matcher.find());
	assertThat(matcher.group(1), equalTo("image.png"));
	assertThat(matcher.group(2), equalTo("PNG"));
	assertThat(matcher.group(3), equalTo("800"));
	assertThat(matcher.group(4), equalTo("140"));
	assertThat(matcher.group(5), equalTo("800"));
	assertThat(matcher.group(6), equalTo("140"));
	assertThat(matcher.group(7), equalTo("0"));
	assertThat(matcher.group(8), equalTo("0"));
	assertThat(matcher.group(9), equalTo("16"));
	assertThat(matcher.group(10), equalTo("Grayscale Gray"));
	assertThat(matcher.group(11), equalTo("720B"));

	matcher = FORMAT.matcher("IMAGE.PNG PNG 800x140 800x140+0+0 16-bit Grayscale Gray 720B 0.000u 0:00.000");
	assertThat(matcher, notNullValue());
	assertTrue(matcher.find());
	assertThat(matcher.group(1), equalTo("IMAGE.PNG"));
    }

    @Test
    public void getImageInfo() throws IOException {
	File imageFile = new File("fixture/image/picture_hq.jpg");
	ImageInfo imageInfo = image.getImageInfo(imageFile);

	assertThat(imageInfo, notNullValue());
	assertThat(imageInfo.getFileName(), equalTo(imageFile.getAbsolutePath()));
	assertThat(imageInfo.getFileSize(), equalTo(178854));
	assertThat(imageInfo.getType(), equalTo(MediaType.JPEG));
	assertThat(imageInfo.getWidth(), equalTo(920));
	assertThat(imageInfo.getHeight(), equalTo(560));
    }

    @Test
    public void saveObjectPicture() throws IOException {
	File uploadFile = new File("fixture/image/picture_hq.jpg");
	File pictureFile = new File("fixture/image/picture.jpg");
	pictureFile.delete();

	image.saveObjectPicture(uploadFile, pictureFile);
	assertThat(pictureFile, anExistingFile());
	assertThat(image.perceptualHash(pictureFile), equalTo("89D05D533D55E75F9EC6181A1A9D772B"));

	ImageInfo imageInfo = image.getImageInfo(pictureFile);
	assertThat(imageInfo, notNullValue());
	assertThat(imageInfo.getFileName(), equalTo(pictureFile.getAbsolutePath()));
	assertThat(imageInfo.getFileSize(), equalTo(96946));
	assertThat(imageInfo.getType(), equalTo(MediaType.JPEG));
	assertThat(imageInfo.getWidth(), equalTo(920));
	assertThat(imageInfo.getHeight(), equalTo(560));

	pictureFile.delete();
    }

    @Test
    public void saveObjectIcon() throws IOException {
	File uploadFile = new File("fixture/image/icon_hq.jpg");
	File iconFile = new File("fixture/image/icon.jpg");
	iconFile.delete();

	image.saveObjectIcon(uploadFile, iconFile);
	assertThat(iconFile, anExistingFile());
	assertThat(image.perceptualHash(iconFile), equalTo("A92380F88E80B8E57FF64CF043925AE2"));

	ImageInfo imageInfo = image.getImageInfo(iconFile);
	assertThat(imageInfo, notNullValue());
	assertThat(imageInfo.getFileName(), equalTo(iconFile.getAbsolutePath()));
	assertThat(imageInfo.getFileSize(), equalTo(38935));
	assertThat(imageInfo.getType(), equalTo(MediaType.JPEG));
	assertThat(imageInfo.getWidth(), equalTo(96));
	assertThat(imageInfo.getHeight(), equalTo(96));

	iconFile.delete();
    }

    @Test
    public void saveObjectThumbnail() throws IOException {
	File uploadFile = new File("fixture/image/picture_hq.jpg");
	File thumbnailFile = new File("fixture/image/thumbnail.png");
	thumbnailFile.delete();

	image.saveObjectThumbnail(uploadFile, thumbnailFile);
	assertThat(thumbnailFile, anExistingFile());
	assertThat(image.perceptualHash(thumbnailFile), equalTo("9568BC2EDD8B899E4C4ECD55FEF50626"));

	ImageInfo imageInfo = image.getImageInfo(thumbnailFile);
	assertThat(imageInfo, notNullValue());
	assertThat(imageInfo.getFileName(), equalTo(thumbnailFile.getAbsolutePath()));
	assertThat(imageInfo.getFileSize(), equalTo(390921));
	assertThat(imageInfo.getType(), equalTo(MediaType.PNG));
	assertThat(imageInfo.getWidth(), equalTo(560));
	assertThat(imageInfo.getHeight(), equalTo(341));

	thumbnailFile.delete();
    }

    @Test
    public void createObjectIcon() throws IOException {
	File pictureFile = new File("fixture/image/picture_hq.jpg");
	File iconFile = new File("fixture/image/icon.jpg");
	iconFile.delete();

	image.createObjectIcon(pictureFile, iconFile);
	assertThat(iconFile, anExistingFile());
	assertThat(image.perceptualHash(iconFile), equalTo("07E823157FDE9A46B51300D87B6B4091"));

	ImageInfo imageInfo = image.getImageInfo(iconFile);
	assertThat(imageInfo, notNullValue());
	assertThat(imageInfo.getFileName(), equalTo(iconFile.getAbsolutePath()));
	assertThat(imageInfo.getFileSize(), equalTo(29899));
	assertThat(imageInfo.getType(), equalTo(MediaType.JPEG));
	assertThat(imageInfo.getWidth(), equalTo(96));
	assertThat(imageInfo.getHeight(), equalTo(96));

	iconFile.delete();
    }

    @Test
    public void generateXAxis() throws IOException {
	File imageFile = new File("fixture/image/image.png");
	imageFile.delete();

	image.generateXAxis(imageFile, 800, 140, "black");
	assertThat(imageFile, anExistingFile());
	assertThat(image.perceptualHash(imageFile), equalTo("5BC07977FB90464853310EA80CA8D694"));

	ImageInfo imageInfo = image.getImageInfo(imageFile);
	assertThat(imageInfo, notNullValue());
	assertThat(imageInfo.getFileName(), equalTo(imageFile.getAbsolutePath()));
	assertThat(imageInfo.getFileSize(), equalTo(720));
	assertThat(imageInfo.getType(), equalTo(MediaType.PNG));
	assertThat(imageInfo.getWidth(), equalTo(800));
	assertThat(imageInfo.getHeight(), equalTo(140));

	imageFile.delete();
    }

    @Test
    public void generateRainbowGradient() throws IOException {
	File imageFile = new File("fixture/image/image.jpg");
	imageFile.delete();

	image.generateRainbowGradient(imageFile, 800, 140);
	assertThat(imageFile, anExistingFile());
	assertThat(image.perceptualHash(imageFile), equalTo("95DA5B4BEB426916957AC6F41DDB7E15"));

	ImageInfo imageInfo = image.getImageInfo(imageFile);
	assertThat(imageInfo, notNullValue());
	assertThat(imageInfo.getFileName(), equalTo(imageFile.getAbsolutePath()));
	assertThat(imageInfo.getFileSize(), equalTo(9516));
	assertThat(imageInfo.getType(), equalTo(MediaType.JPEG));
	assertThat(imageInfo.getWidth(), equalTo(800));
	assertThat(imageInfo.getHeight(), equalTo(140));

	imageFile.delete();
    }

    @Test
    public void crop() throws IOException {
	File imageFile = new File("fixture/image/picture_hq.jpg");
	File targetFile = new File("fixture/image/picture.jpg");

	targetFile.delete();
	assertThat(targetFile, not(anExistingFile()));

	image.crop(imageFile, targetFile, 96, 128, 0, 0);
	assertThat(targetFile, anExistingFile());
	assertThat(image.perceptualHash(imageFile), equalTo("241022BD35F2CBDB0791E2AC871BC991"));

	ImageInfo imageInfo = image.getImageInfo(targetFile);
	assertThat(imageInfo, notNullValue());
	assertThat(imageInfo.getFileName(), equalTo(targetFile.getAbsolutePath()));
	assertThat(imageInfo.getFileSize(), equalTo(27700));
	assertThat(imageInfo.getType(), equalTo(MediaType.JPEG));
	assertThat(imageInfo.getWidth(), equalTo(96));
	assertThat(imageInfo.getHeight(), equalTo(128));

	targetFile.delete();
    }

    @Test
    public void compose() throws IOException {
	File maskFile = new File("fixture/image/mask.png");
	image.generateRainbowGradient(maskFile, 960, 140);

	File waveformFile = new File("fixture/image/waveform.png");
	File imageFile = new File("fixture/image/image.png");
	Files.copy(waveformFile, imageFile);
	
	image.compose(imageFile, maskFile, ImageCompose.SRCIN);
	assertThat(imageFile, anExistingFile());
	assertThat(image.perceptualHash(imageFile), equalTo("D64708411C8E66015A23FE4199553EB4"));

	ImageInfo imageInfo = image.getImageInfo(imageFile);
	assertThat(imageInfo, notNullValue());
	assertThat(imageInfo.getFileName(), equalTo(imageFile.getAbsolutePath()));
	assertThat(imageInfo.getFileSize(), equalTo(8086));
	assertThat(imageInfo.getType(), equalTo(MediaType.PNG));
	assertThat(imageInfo.getWidth(), equalTo(960));
	assertThat(imageInfo.getHeight(), equalTo(140));
	
	maskFile.delete();
	imageFile.delete();
    }
}
