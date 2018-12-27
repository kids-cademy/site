package com.kidscademy.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.ProcessStarter;

import com.kidscademy.AdminService;
import com.kidscademy.atlas.GraphicObject;
import com.kidscademy.atlas.Instrument;
import com.kidscademy.atlas.Login;
import com.kidscademy.atlas.User;
import com.kidscademy.dao.AdminDao;
import com.kidscademy.util.WaveForm;
import com.kidscademy.util.WaveStream;

import js.annotation.ContextParam;
import js.core.AppContext;
import js.http.form.Form;
import js.log.Log;
import js.log.LogFactory;
import js.util.Strings;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

public class AdminServiceImpl implements AdminService
{
  private static final Log log = LogFactory.getLog(AdminServiceImpl.class);

  @ContextParam("objects.repository.path")
  private static File REPOSITORY_DIR;

  @ContextParam("image.magick.path")
  private static String IMAGE_MAGICK_PATH;

  private final AppContext context;
  private final AdminDao dao;

  public AdminServiceImpl(AppContext context, AdminDao dao)
  {
    log.trace("AtlasServiceImpl(AppContext, AtlasDao)");
    this.context = context;
    this.dao = dao;
    ProcessStarter.setGlobalSearchPath(IMAGE_MAGICK_PATH);
  }

  @Override
  public boolean login(Login login)
  {
    User user = dao.getUser(login);
    if(user != null) {
      context.login(user);
      return true;
    }
    return false;
  }

  @Override
  public boolean isAuthenticated()
  {
    return context.isAuthenticated();
  }

  @Override
  public List<Instrument> getInstruments()
  {
    return dao.getInstruments();
  }

  @Override
  public Instrument getInstrument(int instrumentId)
  {
    return dao.getInstrument(instrumentId);
  }

  @Override
  public Instrument getInstrumentByName(String name)
  {
    return dao.getInstrumentByName(name);
  }

  @Override
  public int saveInstrument(Instrument instrument)
  {
    User user = context.getUserPrincipal();
    instrument.setUser(user);
    dao.saveInstrument(instrument);
    return instrument.getId();
  }

  @Override
  public List<GraphicObject> getRelatedInstruments(List<String> names)
  {
    return dao.findObjectsByName(Instrument.class, names);
  }

  @Override
  public List<GraphicObject> getAvailableInstruments(Instrument.Category category, List<GraphicObject> related)
  {
    List<GraphicObject> instruments = dao.getInstrumentsByCategory(category);
    instruments.removeAll(related);
    return instruments;
  }

  @Override
  public Map<String, String> uploadAudioSample(Form form) throws IOException, UnsupportedAudioFileException
  {
    String objectName = form.getValue("name");
    String samplePath = Strings.concat("instruments/", objectName, "/sample.mp3");

    File sampleFile = new File(REPOSITORY_DIR, samplePath);
    sampleFile.getParentFile().mkdirs();
    sampleFile.delete();

    form.getUploadedFile("file").getFile().renameTo(sampleFile);

    Map<String, String> result = new HashMap<>();
    result.put("samplePath", samplePath);
    result.put("waveformPath", generateWaveform(objectName));
    return result;
  }

  @Override
  public String generateWaveform(String objectName) throws IOException, UnsupportedAudioFileException
  {
    String waveformPath = Strings.concat("instruments/", objectName, "/waveform.png");
    File mp3File = new File(REPOSITORY_DIR, Strings.concat("instruments/", objectName, "/sample.mp3"));
    mp3File.getParentFile().mkdirs();

    File wavFile = File.createTempFile("sample", ".wav");

    FFmpegBuilder builder = new FFmpegBuilder();
    builder.setVerbosity(FFmpegBuilder.Verbosity.DEBUG);
    builder.setInput(mp3File.getAbsolutePath());
    builder.addOutput(wavFile.getAbsolutePath());

    FFmpegExecutor executor = new FFmpegExecutor();
    executor.createJob(builder).run();

    File waveFormFile = new File(REPOSITORY_DIR, waveformPath);
    WaveStream stream = new WaveStream(wavFile);
    WaveForm waveform = new WaveForm(960, 140);

    try {
      waveform.setSamplesCount(stream.getSamplesCount());
      stream.setProgressListener(waveform);
      stream.setSamplesListener(waveform);
      stream.process();
    }
    finally {
      stream.close();
    }

    waveform.save(waveFormFile);
    wavFile.delete();

    dao.updateWaveformPath(objectName, waveformPath);
    return waveformPath;
  }

  @Override
  public String uploadPictureFile(Form form) throws IOException
  {
    String picturePath = Strings.concat("instruments/", form.getValue("name"), "/picture.jpg");

    File pictureFile = new File(REPOSITORY_DIR, picturePath);
    pictureFile.getParentFile().mkdirs();
    pictureFile.delete();
    form.getUploadedFile("file").getFile().renameTo(pictureFile);

    return picturePath;
  }

  @Override
  public String uploadIconFile(Form form) throws IOException
  {
    String iconPath = Strings.concat("instruments/", form.getValue("name"), "/icon.jpg");

    File iconFile = new File(REPOSITORY_DIR, iconPath);
    iconFile.getParentFile().mkdirs();
    iconFile.delete();
    form.getUploadedFile("file").getFile().renameTo(iconFile);

    return iconPath;
  }

  @Override
  public String uploadThumbnailFile(Form form) throws IOException
  {
    String thumbnailPath = Strings.concat("instruments/", form.getValue("name"), "/thumbnail.png");

    File thumbnailFile = new File(REPOSITORY_DIR, thumbnailPath);
    thumbnailFile.getParentFile().mkdirs();
    thumbnailFile.delete();
    form.getUploadedFile("file").getFile().renameTo(thumbnailFile);

    return thumbnailPath;
  }

  @Override
  public String createObjectIcon(String objectName) throws IOException, InterruptedException, IM4JavaException
  {
    File pictureFile = new File(REPOSITORY_DIR, Strings.concat("instruments/", objectName, "/picture.jpg"));
    String iconPath = Strings.concat("instruments/", objectName, "/icon.jpg");
    File iconFile = new File(REPOSITORY_DIR, iconPath);

    IMOperation op = new IMOperation();
    op.addImage(pictureFile.getAbsolutePath());
    op.crop(560, 560, 180, 0);
    op.resize(96, 96);
    op.quality(80.0);
    op.addImage(iconFile.getAbsolutePath());

    ConvertCmd cmd = new ConvertCmd();
    cmd.run(op);

    return iconPath;
  }
}
