package com.kidscademy.dao;

import java.util.List;

import com.kidscademy.atlas.Bird;
import com.kidscademy.atlas.Instrument;
import com.kidscademy.atlas.Link;

public interface AtlasDao
{
  void saveInstrument(Instrument instrument);

  Instrument getInstrument(int instrumentId);

  <T> List<T> findObjectByType(Class<T> type);

  Bird getBird(int birdId);

  void saveLink(Link link);
}
