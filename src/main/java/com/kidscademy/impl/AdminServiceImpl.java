package com.kidscademy.impl;

import java.util.List;

import com.kidscademy.AdminService;
import com.kidscademy.atlas.AtlasObject;
import com.kidscademy.atlas.Instrument;
import com.kidscademy.atlas.Link;
import com.kidscademy.dao.AdminDao;
import com.kidscademy.util.Icons;

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
  public List<AtlasObject> getRelatedInstruments(List<String> names)
  {
    return dao.findObjectsByName(Instrument.class, names);
  }

  @Override
  public void saveInstrument(Instrument instrument)
  {
    dao.saveInstrument(instrument);
  }

  @Override
  public List<Link> createLink(int objectId, Link link)
  {
    link.setObjectId(objectId);
    return updateLink(link);
  }

  @Override
  public List<Link> updateLink(Link link)
  {
    link.setIconPath(Icons.getIconPath(link));
    dao.saveLink(link);
    return dao.findLinksByObject(link.getObjectId());
  }

  @Override
  public List<Link> removeLink(Link link)
  {
    dao.removeObject(link);
    return dao.findLinksByObject(link.getObjectId());
  }
}
