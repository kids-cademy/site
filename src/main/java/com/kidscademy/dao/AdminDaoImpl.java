package com.kidscademy.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.eclipse.persistence.config.CacheUsage;
import org.eclipse.persistence.config.QueryHints;

import com.kidscademy.atlas.AtlasObject;
import com.kidscademy.atlas.Bird;
import com.kidscademy.atlas.Instrument;
import com.kidscademy.atlas.Link;

import js.transaction.Immutable;
import js.transaction.Transactional;

@Transactional
public class AdminDaoImpl implements AdminDao
{
  private final EntityManager em;

  public AdminDaoImpl(EntityManager em)
  {
    this.em = em;
  }

  @Override
  public void saveInstrument(Instrument instrument)
  {
    if(instrument.getId() == null) {
      em.persist(instrument);
    }
    else {
      em.merge(instrument);
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Instrument> getInstruments()
  {
    return em.createQuery("select i from Instrument i").getResultList();
  }

  @Override
  public Instrument getInstrument(int instrumentId)
  {
    return em.find(Instrument.class, instrumentId);
  }

  @Override
  public Instrument getInstrumentByName(String name)
  {
    return (Instrument)em.createQuery("select i from Instrument i where i.name=:name").setParameter("name", name)
        .setHint(QueryHints.CACHE_USAGE, CacheUsage.NoCache).getSingleResult();
  }

  @Override
  public <T> List<T> findObjectByType(Class<T> type)
  {
    return em.createQuery("select o from AtlasObject o where type(o)=:type", type).setParameter("type", type).getResultList();
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<AtlasObject> findObjectsByName(Class<?> type, List<String> names)
  {
    if(names.isEmpty()) {
      return Collections.emptyList();
    }
    return em.createQuery("select o from AtlasObject o where o.dtype=:type and o.name in :names").setParameter("type", type.getSimpleName())
        .setParameter("names", names).getResultList();
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
    if(link.getId() == 0) {
      em.persist(link);
    }
    else {
      em.merge(link);
    }
  }

  @Override
  public List<Link> findLinksByObject(int objectId)
  {
    return em.createQuery("select l from Link l where l.objectId=:objectId", Link.class).setParameter("objectId", objectId).getResultList();
  }

  @Override
  public void removeObject(Object object)
  {
    if(!em.contains(object)) {
      object = em.merge(object);
    }
    em.remove(object);
  }
}
