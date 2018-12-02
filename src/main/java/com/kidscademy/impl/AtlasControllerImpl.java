package com.kidscademy.impl;

import com.kidscademy.AtlasController;
import com.kidscademy.atlas.Instrument;
import com.kidscademy.dao.AtlasDao;

public class AtlasControllerImpl implements AtlasController
{
  private final AtlasDao dao;

  public AtlasControllerImpl(AtlasDao dao)
  {
    this.dao = dao;
  }

  @Override
  public Instrument getInstrument(int instrumentId)
  {
    return dao.getInstrument(instrumentId);
  }
}
