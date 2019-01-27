package com.kidscademy.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import com.kidscademy.atlas.Bird;
import com.kidscademy.atlas.Instrument;
import com.kidscademy.atlas.UIObject;

import js.transaction.Immutable;
import js.transaction.Transactional;

@Transactional
public class AtlasDaoImpl implements AtlasDao {
    private final EntityManager em;

    public AtlasDaoImpl(EntityManager em) {
	this.em = em;
    }

    @Override
    public void saveInstrument(Instrument instrument) {
	if (instrument.getId() == 0) {
	    em.persist(instrument);
	} else {
	    em.merge(instrument).postMerge(instrument);
	}
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<UIObject> getInstruments() {
	return em.createQuery("select o from UIObject o where o.dtype='instrument'").getResultList();
    }

    @Override
    public Instrument getInstrument(int instrumentId) {
	return em.find(Instrument.class, instrumentId);
    }

    @Override
    public Instrument getInstrumentByName(String name) {
	return em.createQuery("select i from Instrument i where i.name=:name", Instrument.class)
		.setParameter("name", name).getSingleResult();
    }

    @Override
    public <T> List<T> findObjectByType(Class<T> type) {
	return em.createQuery("select o from AtlasObject o where o.dtype=:dtype", type)
		.setParameter("dtype", type.getSimpleName().toLowerCase()).getResultList();
    }

    @Override
    @Immutable
    public Bird getBird(int birdId) {
	return em.find(Bird.class, birdId);
    }

    @Override
    public void removeObject(Object object) {
	if (!em.contains(object)) {
	    object = em.merge(object);
	}
	em.remove(object);
    }

    @Override
    public List<UIObject> findObjectsByName(Class<?> type, List<String> names) {
	if (names.isEmpty()) {
	    return Collections.emptyList();
	}
	String jpql = "select o from UIObject o where o.dtype=:dtype and o.name in :names";
	return em.createQuery(jpql, UIObject.class).setParameter("dtype", type.getSimpleName().toLowerCase())
		.setParameter("names", names).getResultList();
    }

    @Override
    public List<UIObject> getInstrumentsByCategory(Instrument.Category category) {
	String jpql = "select new com.kidscademy.atlas.UIObject(i.id,i.dtype,i.name,i.display,i.iconName) from Instrument i where i.category=:category";
	return em.createQuery(jpql, UIObject.class).setParameter("category", category).getResultList();
    }

    @Override
    public void removeInstrumentSample(String instrumentName) {
	String jpql = "update Instrument i set i.sampleTitle=null,i.sampleName=null,i.waveformName=null where i.name=:name";
	em.createQuery(jpql).setParameter("name", instrumentName).executeUpdate();
    }
}
