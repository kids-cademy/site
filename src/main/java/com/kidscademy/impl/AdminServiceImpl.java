package com.kidscademy.impl;

import java.util.List;

import com.kidscademy.AdminService;
import com.kidscademy.atlas.AtlasObject;
import com.kidscademy.atlas.GraphicObject;
import com.kidscademy.atlas.Instrument;
import com.kidscademy.dao.AdminDao;

import js.log.Log;
import js.log.LogFactory;

public class AdminServiceImpl implements AdminService
{
  private static final Log log = LogFactory.getLog(AdminServiceImpl.class);

  private final AdminDao dao;

  public AdminServiceImpl(AdminDao dao)
  {
    log.trace("AtlasServiceImpl(AtlasDao)");
    this.dao = dao;
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
  public void saveInstrument(Instrument instrument)
  {
    dao.saveInstrument(instrument);
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
}
