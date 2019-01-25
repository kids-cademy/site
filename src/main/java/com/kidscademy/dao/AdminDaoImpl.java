package com.kidscademy.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import com.kidscademy.atlas.Bird;
import com.kidscademy.atlas.GraphicObject;
import com.kidscademy.atlas.Instrument;
import com.kidscademy.atlas.Login;
import com.kidscademy.atlas.User;

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
  public User getUser(Login login)
  {
    String jpql = "select u from User u where u.emailAddress=?1 and u.password=?2";
    try {
      return em.createQuery(jpql, User.class).setParameter(1, login.getEmailAddress()).setParameter(2, login.getPassword()).getSingleResult();
    }
    catch(NoResultException unused) {
      return null;
    }
  }

  @Override
  public User getUserById(int userId)
  {
    return em.find(User.class, userId);
  }

  @Override
  public void saveInstrument(Instrument instrument)
  {
    if(instrument.getId() == 0) {
      em.persist(instrument);
    }
    else {
      em.merge(instrument).postMerge(instrument);
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
    return em.createQuery("select i from Instrument i where i.name=:name", Instrument.class).setParameter("name", name).getSingleResult();
  }

  @Override
  public <T> List<T> findObjectByType(Class<T> type)
  {
    return em.createQuery("select o from AtlasObject o where o.dtype=:dtype", type).setParameter("dtype", type.getSimpleName().toLowerCase()).getResultList();
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
    String jpql = "select new com.kidscademy.atlas.GraphicObject(o.id,o.dtype,o.name,o.display,o.iconName) from AtlasObject o where o.dtype=:type and o.name in :names";
    return em.createQuery(jpql, GraphicObject.class).setParameter("type", type.getSimpleName().toLowerCase()).setParameter("names", names).getResultList();
  }

  @Override
  public List<GraphicObject> getInstrumentsByCategory(Instrument.Category category)
  {
    String jpql = "select new com.kidscademy.atlas.GraphicObject(i.id,i.dtype,i.name,i.display,i.iconName) from Instrument i where i.category=:category";
    return em.createQuery(jpql, GraphicObject.class).setParameter("category", category).getResultList();
  }

  @Override
  public void removeInstrumentSample(String instrumentName)
  {
    String jpql = "update Instrument i set i.sampleTitle=null,i.sampleName=null,i.waveformName=null where i.name=:name";
    em.createQuery(jpql).setParameter("name", instrumentName).executeUpdate();
  }
}
