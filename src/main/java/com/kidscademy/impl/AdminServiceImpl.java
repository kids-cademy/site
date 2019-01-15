package com.kidscademy.impl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.im4java.core.IM4JavaException;
import org.im4java.process.ProcessStarter;

import com.kidscademy.AdminService;
import com.kidscademy.AudioProcessor;
import com.kidscademy.ImageProcessor;
import com.kidscademy.atlas.AudioFileInfo;
import com.kidscademy.atlas.GraphicObject;
import com.kidscademy.atlas.Instrument;
import com.kidscademy.atlas.Link;
import com.kidscademy.atlas.Login;
import com.kidscademy.atlas.User;
import com.kidscademy.dao.AdminDao;

import js.annotation.ContextParam;
import js.core.AppContext;
import js.http.form.Form;
import js.lang.BugError;
import js.log.Log;
import js.log.LogFactory;
import js.util.Strings;

public class AdminServiceImpl implements AdminService
{
  private static final Log log = LogFactory.getLog(AdminServiceImpl.class);

  @ContextParam("objects.repository.path")
  private static File REPOSITORY_DIR;

  @ContextParam("image.magick.path")
  private static String IMAGE_MAGICK_PATH;

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
  public Instrument getInstrument(int instrumentId) throws IOException
  {
    if(instrumentId == 0) {
      User user = context.getUserPrincipal();
      return Instrument.create(user);
    }
    Instrument instrument = dao.getInstrument(instrumentId);

    if(instrument.getSamplePath() != null) {
      File sampleFile = new File(REPOSITORY_DIR, instrument.getSamplePath());
      if(sampleFile.exists()) {
        AudioFileInfo sampleInfo = audio.getAudioFileInfo(sampleFile);
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
  public Map<String, Object> uploadAudioSample(Form form) throws IOException, UnsupportedAudioFileException, InterruptedException, IM4JavaException
  {
    String objectName = form.getValue("name");
    String samplePath = Strings.concat("instruments/", objectName, "/sample.mp3");

    File sampleFile = new File(REPOSITORY_DIR, samplePath);
    sampleFile.getParentFile().mkdirs();
    sampleFile.delete();

    form.getUploadedFile("file").getFile().renameTo(sampleFile);

    Map<String, Object> result = new HashMap<>();
    result.put("samplePath", samplePath);
    result.put("waveformPath", generateWaveform(objectName));

    result.put("sampleInfo", audio.getAudioFileInfo(sampleFile));

    return result;
  }

  @Override
  public String generateWaveform(String objectName) throws IOException, UnsupportedAudioFileException, InterruptedException, IM4JavaException
  {
    File mp3File = new File(REPOSITORY_DIR, Strings.concat("instruments/", objectName, "/sample.mp3"));
    if(!mp3File.exists()) {
      throw new BugError("Database not consistent. Missing sample file |%s|.", mp3File);
    }

    String waveformPath = Strings.concat("instruments/", objectName, "/waveform.png");
    File waveformFile = new File(REPOSITORY_DIR, waveformPath);
    audio.generateWaveform(mp3File, waveformFile);
    return waveformPath;
  }

  @Override
  public String uploadPictureFile(Form form) throws IOException
  {
    String picturePath = Strings.concat("instruments/", form.getValue("name"), "/picture.jpg");
    File pictureFile = new File(REPOSITORY_DIR, picturePath);
    pictureFile.getParentFile().mkdirs();

    image.saveObjectPicture(form.getUploadedFile("file").getFile(), pictureFile);
    return picturePath;
  }

  @Override
  public String uploadIconFile(Form form) throws IOException
  {
    String iconPath = Strings.concat("instruments/", form.getValue("name"), "/icon.jpg");
    File iconFile = new File(REPOSITORY_DIR, iconPath);
    iconFile.getParentFile().mkdirs();

    image.saveObjectIcon(form.getUploadedFile("file").getFile(), iconFile);
    return iconPath;
  }

  @Override
  public String uploadThumbnailFile(Form form) throws IOException
  {
    String thumbnailPath = Strings.concat("instruments/", form.getValue("name"), "/thumbnail.png");
    File thumbnailFile = new File(REPOSITORY_DIR, thumbnailPath);
    thumbnailFile.getParentFile().mkdirs();

    image.saveObjectThumbnail(form.getUploadedFile("file").getFile(), thumbnailFile);
    return thumbnailPath;
  }

  @Override
  public String createObjectIcon(String objectName) throws IOException
  {
    File pictureFile = new File(REPOSITORY_DIR, Strings.concat("instruments/", objectName, "/picture.jpg"));
    String iconPath = Strings.concat("instruments/", objectName, "/icon.jpg");
    File iconFile = new File(REPOSITORY_DIR, iconPath);

    image.createObjectIcon(pictureFile, iconFile);
    return iconPath;
  }

  @Override
  public Link createLink(URL url)
  {
    return Link.create(url);
  }

  @Override
  public void removeInstrumentSample(String instrumentName)
  {
    dao.removeInstrumentSample(instrumentName);

    File instrumentDir = new File(REPOSITORY_DIR, Strings.concat("instruments/", instrumentName));
    new File(instrumentDir, "sample.mp3").delete();
    new File(instrumentDir, "waveform.png").delete();
  }

  @Override
  public Map<String, Object> normalizeSample(String objectName) throws IOException
  {
    String samplePath = Strings.concat("instruments/", objectName, "/sample.mp3");
    File sampleFile = new File(REPOSITORY_DIR, samplePath);

    String workingSamplePath = Strings.concat("instruments/", objectName, "/working-sample.mp3");
    File workingSampleFile = new File(REPOSITORY_DIR, workingSamplePath);
    workingSampleFile.delete();

    audio.convertToMono(sampleFile, workingSampleFile);
    //audio.trimSilence(workingSampleFile);
    audio.normalizeLevel(workingSampleFile);

    String waveformPath = Strings.concat("instruments/", objectName, "/working-waveform.png");
    File waveformFile = new File(REPOSITORY_DIR, waveformPath);
    audio.generateWaveform(workingSampleFile, waveformFile);

    Map<String, Object> result = new HashMap<>();
    result.put("samplePath", workingSamplePath);
    result.put("waveformPath", waveformPath);
    result.put("sampleInfo", audio.getAudioFileInfo(workingSampleFile));

    return result;
  }
}
