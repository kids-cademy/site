package com.kidscademy;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.UnsupportedAudioFileException;

import org.im4java.core.IM4JavaException;

import com.kidscademy.atlas.GraphicObject;
import com.kidscademy.atlas.Instrument;
import com.kidscademy.atlas.Link;
import com.kidscademy.atlas.Login;

import js.annotation.Public;
import js.annotation.Service;
import js.http.form.Form;

@Service
public interface AdminService
{
  @Public
  boolean login(Login login);

  @Public
  boolean isAuthenticated();

  List<Instrument> getInstruments();

  /**
   * Get instrument entity. If ID is zero returns an empty instance.
   * 
   * @param instrumentId
   * @return instrument instance, possible empty or null.
   */
  Instrument getInstrument(int instrumentId);

  Instrument getInstrumentByName(String name);

  int saveInstrument(Instrument instrument);

  List<GraphicObject> getRelatedInstruments(List<String> names);

  List<GraphicObject> getAvailableInstruments(Instrument.Category category, List<GraphicObject> related);

  Map<String, String> uploadAudioSample(Form form) throws IOException, UnsupportedAudioFileException;

  String generateWaveform(String objectName) throws IOException, UnsupportedAudioFileException;

  String uploadPictureFile(Form form) throws IOException, InterruptedException, IM4JavaException;

  String uploadIconFile(Form form) throws IOException, InterruptedException, IM4JavaException;

  String uploadThumbnailFile(Form form) throws IOException, InterruptedException, IM4JavaException;

  /**
   * Create object icon from picture.
   * 
   * @param objectName
   * @return
   * @throws IM4JavaException
   * @throws InterruptedException
   * @throws IOException
   */
  String createObjectIcon(String objectName) throws IOException, InterruptedException, IM4JavaException;
  
  Link createLink(URL url);
}
