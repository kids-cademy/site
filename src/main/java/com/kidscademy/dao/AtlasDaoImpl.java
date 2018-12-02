package com.kidscademy.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.kidscademy.atlas.Bird;
import com.kidscademy.atlas.Instrument;
import com.kidscademy.atlas.Link;

import js.transaction.Immutable;
import js.transaction.Transactional;

@Transactional
public class AtlasDaoImpl implements AtlasDao
{
  private final EntityManager em;

  public AtlasDaoImpl(EntityManager em)
  {
    this.em = em;
  }

  @Override
  public void saveInstrument(Instrument instrument)
  {
    em.persist(instrument);
  }

  @Override
  public Instrument getInstrument(int instrumentId)
  {
    return em.find(Instrument.class, instrumentId);
  }

  @Override
  public <T> List<T> findObjectByType(Class<T> type)
  {
    return em.createQuery("select o from AtlasObject o where type(o)=:type", type).setParameter("type", type).getResultList();
  }

  @Override
  @Immutable
  public Bird getBird(int birdId)
  {
    return em.find(Bird.class, birdId);
  }

  @Override
  public void saveLink(Link link)
  {
    em.persist(link);
  }
}
