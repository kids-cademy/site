package com.kidscademy;

import java.util.List;

import com.kidscademy.atlas.GraphicObject;
import com.kidscademy.atlas.Instrument;

import js.annotation.Public;
import js.annotation.Service;

@Service
@Public
public interface AdminService
{
  List<Instrument> getInstruments();

  Instrument getInstrument(int instrumentId);

  Instrument getInstrumentByName(String name);

  void saveInstrument(Instrument instrument);

  List<GraphicObject> getRelatedInstruments(List<String> names);

  List<GraphicObject> getAvailableInstruments(Instrument.Category category, List<GraphicObject> related);
}
