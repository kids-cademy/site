package com.kidscademy.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import org.eclipse.persistence.config.CacheUsage;
import org.eclipse.persistence.config.QueryHints;

import com.kidscademy.atlas.Bird;
import com.kidscademy.atlas.GraphicObject;
import com.kidscademy.atlas.Instrument;

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

  @Override
  @Immutable
  public Bird getBird(int birdId)
  {
    return em.find(Bird.class, birdId);
  }

  @Override
  public void removeObject(Object object)
  {
    if(!em.contains(object)) {
      object = em.merge(object);
    }
    em.remove(object);
  }

  @Override
  public List<GraphicObject> findObjectsByName(Class<?> type, List<String> names)
  {
    if(names.isEmpty()) {
      return Collections.emptyList();
    }
    String jpql = "select new com.kidscademy.atlas.GraphicObject(o.id,o.name,o.display,o.iconPath) from AtlasObject o where o.dtype=:type and o.name in :names";
    return em.createQuery(jpql, GraphicObject.class).setParameter("type", type.getSimpleName()).setParameter("names", names).getResultList();
  }

  @Override
  public List<GraphicObject> getInstrumentsByCategory(Instrument.Category category)
  {
    String jpql = "select new com.kidscademy.atlas.GraphicObject(i.id,i.name,i.display,i.iconPath) from Instrument i where i.category=:category";
    return em.createQuery(jpql, GraphicObject.class).setParameter("category", category).getResultList();
  }
}
