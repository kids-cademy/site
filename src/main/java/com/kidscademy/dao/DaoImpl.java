package com.kidscademy.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.kidscademy.model.App;
import com.kidscademy.model.Audit;
import com.kidscademy.model.Counter;
import com.kidscademy.model.Counters;
import com.kidscademy.model.CrashReport;
import com.kidscademy.model.Device;
import com.kidscademy.model.DislikeReason;
import com.kidscademy.model.Model;
import com.kidscademy.model.NoAdsSurvey;
import com.kidscademy.model.Suggestion;

import js.transaction.Immutable;
import js.transaction.Transactional;

@Transactional
public class DaoImpl implements Dao
{
  private final EntityManager em;

  public DaoImpl(EntityManager em)
  {
    this.em = em;
  }

  @Override
  public void createCounter(Counter counter)
  {
    em.persist(counter);
    for(DislikeReason reason : counter.getReasons()) {
      em.createNativeQuery("INSERT INTO dislike_reason SET counterId=?,value=?").setParameter(1, counter.getId()).setParameter(2, reason.name())
          .executeUpdate();
    }
  }

  @Override
  @Immutable
  public Counters getCounters()
  {
    long likeCount = (long)em.createNativeQuery("SELECT COUNT(value) AS likeCount FROM like_counter WHERE value=1").getSingleResult();
    long dislikeCount = (long)em.createNativeQuery("SELECT COUNT(value) AS dislikeCount FROM like_counter WHERE value=0").getSingleResult();
    return new Counters((int)likeCount, (int)dislikeCount);
  }

  @Override
  public void createDeveloperMessage(Suggestion suggestion)
  {
    em.persist(suggestion);
  }

  @SuppressWarnings("unchecked")
  @Override
  @Immutable
  public List<Suggestion> getSuggestions()
  {
    return em.createQuery("select s from Suggestion s where s.developerName is null and s.senderEmail is not null order by s.timestamp desc").setMaxResults(10).getResultList();
  }

  @Override
  public void createAudit(Audit audit)
  {
    em.persist(audit);
  }

  @Override
  public void createCrashReport(CrashReport crashReport)
  {
    em.persist(crashReport);
  }

  @Override
  public void createNoAdsSurvey(NoAdsSurvey noAdsSurvey)
  {
    em.persist(noAdsSurvey);
  }

  @Override
  public App getApp(String appName)
  {
    App app = null;
    try {
      app = (App)em.createQuery("select a from App a where a.name=:name").setParameter("name", appName).getSingleResult();
    }
    catch(NoResultException ignored) {}
    if(app == null) {
      app = new App(appName);
      em.persist(app);
    }
    return app;
  }

  @Override
  public Device getDevice(Device device)
  {
    Model model = device.getModel();
    assert model.getId() == 0;
    int modelId = getModelId(model);
    model.setId(modelId);

    Integer deviceId = null;
    final String query = "select d.id from Device d where d.model.id=?1 and d.serial=?2";
    try {
      deviceId = (Integer)em.createQuery(query).setParameter(1, modelId).setParameter(2, device.getSerial()).getSingleResult();
    }
    catch(NoResultException ignored) {}

    if(deviceId == null) {
      em.persist(device);
      deviceId = device.getId();
    }

    device.setId(deviceId);
    return device;
  }

  private int getModelId(Model model)
  {
    Query query = em.createQuery("select m.id from Model m where m.manufacturer=?1 and m.model=?2 and m.version=?3 and m.apiLevel=?4");
    query.setParameter(1, model.getManufacturer());
    query.setParameter(2, model.getModel());
    query.setParameter(3, model.getVersion());
    query.setParameter(4, model.getApiLevel());

    Integer id = null;
    try {
      id = (Integer)query.getSingleResult();
    }
    catch(NoResultException ignored) {}

    // final String query = "select id from Model where manufacturer=?1 and model=?2 and version=?3 and apiLevel=?4";
    // Integer id = (Integer)em.createQuery(query).setParameter(1, model.getManufacturer()).setParameter(2,
    // model.getModel()).setParameter(3, model.getVersion())
    // .setParameter(4, model.getApiLevel()).getSingleResult();

    if(id != null) {
      return id;
    }

    em.persist(model);
    return model.getId();
  }
}
