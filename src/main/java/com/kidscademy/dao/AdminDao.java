package com.kidscademy.dao;

import java.util.List;

import com.kidscademy.atlas.AtlasObject;
import com.kidscademy.atlas.Bird;
import com.kidscademy.atlas.Instrument;
import com.kidscademy.atlas.Link;

public interface AdminDao
{
  void saveInstrument(Instrument instrument);

  Instrument getInstrument(int instrumentId);

  Instrument getInstrumentByName(String name);

  <T> List<T> findObjectByType(Class<T> type);

  Bird getBird(int birdId);

  void saveLink(Link link);

  List<AtlasObject> findObjectsByName(Class<?> type, List<String> names);

  List<Link> findLinksByObject(int objectId);

  void removeObject(Object object);

  List<Instrument> getInstruments();
}
