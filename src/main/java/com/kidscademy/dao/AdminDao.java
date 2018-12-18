package com.kidscademy.dao;

import java.util.List;

import com.kidscademy.atlas.Bird;
import com.kidscademy.atlas.GraphicObject;
import com.kidscademy.atlas.Instrument;

public interface AdminDao
{
  void saveInstrument(Instrument instrument);

  Instrument getInstrument(int instrumentId);

  Instrument getInstrumentByName(String name);

  <T> List<T> findObjectByType(Class<T> type);

  Bird getBird(int birdId);

  List<Instrument> getInstruments();

  void removeObject(Object object);

  List<GraphicObject> findObjectsByName(Class<?> type, List<String> names);

  List<GraphicObject> getInstrumentsByCategory(Instrument.Category category);
}
