package com.kidscademy;

import java.util.List;

import com.kidscademy.atlas.AtlasObject;
import com.kidscademy.atlas.Instrument;
import com.kidscademy.atlas.Link;

import js.annotation.Public;
import js.annotation.Service;

@Service
@Public
public interface AdminService
{
  List<Instrument> getInstruments();

  Instrument getInstrument(int instrumentId);

  Instrument getInstrumentByName(String name);

  List<AtlasObject> getRelatedInstruments(List<String> names);

  void saveInstrument(Instrument instrument);

  List<Link> createLink(int objectId, Link link);

  List<Link> updateLink(Link link);

  List<Link> removeLink(Link link);
}
