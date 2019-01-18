package com.kidscademy.impl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.im4java.core.IM4JavaException;
import org.im4java.process.ProcessStarter;

import com.kidscademy.AdminService;
import com.kidscademy.CT;
import com.kidscademy.atlas.GraphicObject;
import com.kidscademy.atlas.Instrument;
import com.kidscademy.atlas.Link;
import com.kidscademy.atlas.Login;
import com.kidscademy.atlas.User;
import com.kidscademy.dao.AdminDao;
import com.kidscademy.media.AudioProcessor;
import com.kidscademy.media.ImageProcessor;
import com.kidscademy.media.MediaFileHandler;
import com.kidscademy.media.SampleFileInfo;

import js.core.AppContext;
import js.http.form.Form;
import js.lang.BugError;
import js.log.Log;
import js.log.LogFactory;
import js.util.Strings;

public class AdminServiceImpl implements AdminService
{
  private static final Log log = LogFactory.getLog(AdminServiceImpl.class);

  private final AppContext context;
  private final AdminDao dao;
  private final AudioProcessor audio;
  private final ImageProcessor image;

  public AdminServiceImpl(AppContext context, AdminDao dao, AudioProcessor audio, ImageProcessor image)
  {
    log.trace("AtlasServiceImpl(AppContext, AtlasDao)");
    this.context = context;
    this.dao = dao;
    this.audio = audio;
    this.image = image;
    ProcessStarter.setGlobalSearchPath(CT.imageMagickPath());
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
  public Instrument getInstrument(int instrumentId) throws IOException
  {
    if(instrumentId == 0) {
      User user = context.getUserPrincipal();
      return Instrument.create(user);
    }
    Instrument instrument = dao.getInstrument(instrumentId);

    if(instrument.getSamplePath() != null) {
      File sampleFile = new File(CT.repositoryDir(), instrument.getSamplePath());
      if(sampleFile.exists()) {
        SampleFileInfo sampleInfo = audio.getAudioFileInfo(sampleFile);
        instrument.setSampleInfo(sampleInfo);
      }
    }

    return instrument;
  }

  @Override
  public Instrument getInstrumentByName(String name)
  {
    return dao.getInstrumentByName(name);
  }

  @Override
  public int saveInstrument(Instrument instrument)
  {
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
  public String uploadPictureFile(Form form) throws IOException
  {
    String picturePath = Strings.concat("instruments/", form.getValue("name"), "/picture.jpg");
    File pictureFile = new File(CT.repositoryDir(), picturePath);
    pictureFile.getParentFile().mkdirs();

    image.saveObjectPicture(form.getUploadedFile("file").getFile(), pictureFile);
    return picturePath;
  }

  @Override
  public String uploadIconFile(Form form) throws IOException
  {
    String iconPath = Strings.concat("instruments/", form.getValue("name"), "/icon.jpg");
    File iconFile = new File(CT.repositoryDir(), iconPath);
    iconFile.getParentFile().mkdirs();

    image.saveObjectIcon(form.getUploadedFile("file").getFile(), iconFile);
    return iconPath;
  }

  @Override
  public String uploadThumbnailFile(Form form) throws IOException
  {
    String thumbnailPath = Strings.concat("instruments/", form.getValue("name"), "/thumbnail.png");
    File thumbnailFile = new File(CT.repositoryDir(), thumbnailPath);
    thumbnailFile.getParentFile().mkdirs();

    image.saveObjectThumbnail(form.getUploadedFile("file").getFile(), thumbnailFile);
    return thumbnailPath;
  }

  @Override
  public String createObjectIcon(String objectName) throws IOException
  {
    File pictureFile = new File(CT.repositoryDir(), Strings.concat("instruments/", objectName, "/picture.jpg"));
    String iconPath = Strings.concat("instruments/", objectName, "/icon.jpg");
    File iconFile = new File(CT.repositoryDir(), iconPath);

    image.createObjectIcon(pictureFile, iconFile);
    return iconPath;
  }

  @Override
  public Link createLink(URL url)
  {
    return Link.create(url);
  }

  // ----------------------------------------------------------------------------------------------
  // Audio Services

  @Override
  public SampleFileInfo uploadAudioSample(Form form) throws IOException, UnsupportedAudioFileException, InterruptedException, IM4JavaException
  {
    String objectName = form.getValue("name");
    MediaFileHandler handler = new MediaFileHandler("instruments", objectName, "sample.mp3");
    handler.mkdirs();
    handler.reset();

    form.getUploadedFile("file").getFile().renameTo(handler.file());

    SampleFileInfo info = audio.getAudioFileInfo(handler.file());
    info.setSamplePath(handler.path());
    info.setWaveformPath(generateWaveform(objectName));
    return info;
  }

  @Override
  public SampleFileInfo normalizeSample(String objectName) throws IOException
  {
    MediaFileHandler handler = new MediaFileHandler("instruments", objectName, "sample.mp3");
    audio.normalizeLevel(handler);

    String waveformPath = Strings.concat("instruments/", objectName, "/waveform.png");
    File waveformFile = new File(CT.repositoryDir(), waveformPath);
    audio.generateWaveform(handler.target(), waveformFile);

    SampleFileInfo info = audio.getAudioFileInfo(handler.target());
    info.setSamplePath(handler.target().getPath());
    info.setWaveformPath(waveformPath);
    return info;
  }

  @Override
  public SampleFileInfo convertToMono(String objectName) throws IOException
  {
    MediaFileHandler handler = new MediaFileHandler("instruments", objectName, "sample.mp3");
    audio.convertToMono(handler);

    String waveformPath = Strings.concat("instruments/", objectName, "/waveform.png");
    File waveformFile = new File(CT.repositoryDir(), waveformPath);
    audio.generateWaveform(handler.target(), waveformFile);

    SampleFileInfo info = audio.getAudioFileInfo(handler.target());
    info.setSamplePath(handler.target().getPath());
    info.setWaveformPath(waveformPath);
    return info;
  }

  @Override
  public SampleFileInfo trimSilence(String objectName) throws IOException
  {
    MediaFileHandler handler = new MediaFileHandler("instruments", objectName, "sample.mp3");
    audio.trimSilence(handler);

    String waveformPath = Strings.concat("instruments/", objectName, "/waveform.png");
    File waveformFile = new File(CT.repositoryDir(), waveformPath);
    audio.generateWaveform(handler.target(), waveformFile);

    SampleFileInfo info = audio.getAudioFileInfo(handler.target());
    info.setSamplePath(handler.target().getPath());
    info.setWaveformPath(waveformPath);
    return info;
  }

  @Override
  public String generateWaveform(String objectName) throws IOException, UnsupportedAudioFileException, InterruptedException, IM4JavaException
  {
    File mp3File = new File(CT.repositoryDir(), Strings.concat("instruments/", objectName, "/sample.mp3"));
    if(!mp3File.exists()) {
      throw new BugError("Database not consistent. Missing sample file |%s|.", mp3File);
    }

    String waveformPath = Strings.concat("instruments/", objectName, "/waveform.png");
    File waveformFile = new File(CT.repositoryDir(), waveformPath);
    audio.generateWaveform(mp3File, waveformFile);
    return waveformPath;
  }

  @Override
  public void removeInstrumentSample(String instrumentName)
  {
    MediaFileHandler handler = new MediaFileHandler("instruments", instrumentName, "sample.mp3");
    handler.delete();

    dao.removeInstrumentSample(instrumentName);

    File instrumentDir = new File(CT.repositoryDir(), Strings.concat("instruments/", instrumentName));
    new File(instrumentDir, "sample.mp3").delete();
    new File(instrumentDir, "waveform.png").delete();
  }

  @Override
  public SampleFileInfo undoMediaProcessing(String objectName) throws IOException
  {
    MediaFileHandler handler = new MediaFileHandler("instruments", objectName, "sample.mp3");
    handler.rollback();

    String waveformPath = Strings.concat("instruments/", objectName, "/waveform.png");
    File waveformFile = new File(CT.repositoryDir(), waveformPath);
    audio.generateWaveform(handler.file(), waveformFile);

    SampleFileInfo info = audio.getAudioFileInfo(handler.file());
    info.setSamplePath(handler.file().getPath());
    info.setWaveformPath(waveformPath);
    return info;
  }

  @Override
  public SampleFileInfo commitMediaProcessing(String objectName) throws IOException
  {
    MediaFileHandler handler = new MediaFileHandler("instruments", objectName, "sample.mp3");
    handler.commit();

    String waveformPath = Strings.concat("instruments/", objectName, "/waveform.png");
    File waveformFile = new File(CT.repositoryDir(), waveformPath);
    audio.generateWaveform(handler.file(), waveformFile);

    SampleFileInfo info = audio.getAudioFileInfo(handler.file());
    info.setSamplePath(handler.file().getPath());
    info.setWaveformPath(waveformPath);
    return info;
  }
}
