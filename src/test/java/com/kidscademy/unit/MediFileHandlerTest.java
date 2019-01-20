package com.kidscademy.unit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kidscademy.impl.MediaFileHandler;

import js.util.Classes;
import js.util.Files;
import js.util.Strings;

public class MediFileHandlerTest
{
  @BeforeClass
  public static void beforeClass()
  {
    Classes.setFieldValue(MediaFileHandler.class, "REPOSIOTRY_DIR", "fixture/tomcat/webapps");
  }

  private MediaFileHandler handler;

  @Before
  public void beforeTest()
  {
    handler = new MediaFileHandler("object", "media.ext");
  }

  @Test
  public void upload() throws IOException
  {
    File uploadFile = new File("fixture/upload.jpg");
    Files.copy(new File("fixture/icon.jpg"), uploadFile);
    handler.source().delete();
    Assert.assertFalse(handler.source().exists());

    handler.upload(new File("fixture/upload.jpg"));
    Assert.assertFalse(uploadFile.exists());
    Assert.assertTrue(handler.source().exists());

    int version = Classes.getFieldValue(handler, "version");
    assertThat(version, equalTo(0));
  }

  @Test
  public void source()
  {
    File file = handler.source();
    assertThat(file, notNullValue());
    assertThat(file, equalTo(file("media.ext")));
  }

  @Test
  public void sourcePath()
  {
    String path = handler.sourcePath();
    assertThat(path, notNullValue());
    assertThat(path, equalTo("/media/atlas/instruments/object/media.ext"));
  }

  @Test
  public void target()
  {
    File file = handler.target();
    assertThat(file, notNullValue());
    assertThat(file, equalTo(file("media_1.ext")));
  }

  @Test
  public void targetPath()
  {
    String path = handler.targetPath();
    assertThat(path, notNullValue());
    assertThat(path, equalTo("/media/atlas/instruments/object/media_1.ext"));
  }

  @Test
  public void commit() throws IOException
  {
    File mediaFile = file("media.ext");
    File mediaFile_1 = file("media_1.ext");
    File mediaFile_2 = file("media_2.ext");
    File mediaFile_3 = file("media_3.ext");

    Strings.save("media file", mediaFile);
    Strings.save("media file 1", mediaFile_1);
    Strings.save("media file 2", mediaFile_2);
    Strings.save("media file 3", mediaFile_3);

    MediaFileHandler handler = new MediaFileHandler("object", "media.ext");
    assertThat(Strings.load(mediaFile), equalTo("media file"));
    handler.commit();

    Assert.assertTrue(mediaFile.exists());
    Assert.assertFalse(mediaFile_1.exists());
    Assert.assertFalse(mediaFile_2.exists());
    Assert.assertFalse(mediaFile_3.exists());

    File source = Classes.getFieldValue(handler, "source");
    String target = Classes.getFieldValue(handler, "target");
    int version = Classes.getFieldValue(handler, "version");

    assertThat(source, nullValue());
    assertThat(target, nullValue());
    assertThat(version, equalTo(0));

    assertThat(Strings.load(mediaFile), equalTo("media file 3"));
    assertThat(Strings.load(handler.source()), equalTo("media file 3"));

    mediaFile.delete();
    mediaFile_1.delete();
    mediaFile_2.delete();
    mediaFile_3.delete();
  }

  @Test
  public void rollback() throws IOException
  {
    File mediaFile = file("media.ext");
    File mediaFile_1 = file("media_1.ext");
    File mediaFile_2 = file("media_2.ext");
    File mediaFile_3 = file("media_3.ext");

    Strings.save("media file", mediaFile);
    Strings.save("media file 1", mediaFile_1);
    Strings.save("media file 2", mediaFile_2);
    Strings.save("media file 3", mediaFile_3);

    MediaFileHandler handler = new MediaFileHandler("object", "media.ext");
    assertThat(Strings.load(handler.source()), equalTo("media file 3"));
    handler.rollback();

    File source = Classes.getFieldValue(handler, "source");
    String target = Classes.getFieldValue(handler, "target");
    int version = Classes.getFieldValue(handler, "version");

    assertThat(source, nullValue());
    assertThat(target, nullValue());
    assertThat(version, equalTo(2));

    assertThat(Strings.load(handler.source()), equalTo("media file 2"));

    Assert.assertTrue(mediaFile.exists());
    Assert.assertTrue(mediaFile_1.exists());
    Assert.assertTrue(mediaFile_2.exists());
    Assert.assertFalse(mediaFile_3.exists());

    mediaFile.delete();
    mediaFile_1.delete();
    mediaFile_2.delete();
    mediaFile_3.delete();
  }

  @Test
  public void delete() throws IOException
  {
    File mediaFile = file("media.ext");
    File mediaFile_1 = file("media_1.ext");
    File mediaFile_2 = file("media_2.ext");
    File mediaFile_3 = file("media_3.ext");

    Strings.save("media file", mediaFile);
    Strings.save("media file 1", mediaFile_1);
    Strings.save("media file 2", mediaFile_2);
    Strings.save("media file 3", mediaFile_3);

    MediaFileHandler handler = new MediaFileHandler("object", "media.ext");
    assertThat(Strings.load(handler.source()), equalTo("media file 3"));
    handler.delete();

    File source = Classes.getFieldValue(handler, "source");
    String target = Classes.getFieldValue(handler, "target");
    int version = Classes.getFieldValue(handler, "version");

    assertThat(source, nullValue());
    assertThat(target, nullValue());
    assertThat(version, equalTo(0));

    Assert.assertFalse(mediaFile.exists());
    Assert.assertFalse(mediaFile_1.exists());
    Assert.assertFalse(mediaFile_2.exists());
    Assert.assertFalse(mediaFile_3.exists());
  }

  private static File file(String fileName)
  {
    return new File("fixture/tomcat/webapps/media/atlas/instruments/object/" + fileName);
  }
}
