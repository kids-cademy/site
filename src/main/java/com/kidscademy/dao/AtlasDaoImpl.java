package com.kidscademy.dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;

import com.kidscademy.atlas.AtlasObject;
import com.kidscademy.atlas.Bird;
import com.kidscademy.atlas.Instrument;
import com.kidscademy.atlas.Link;
import com.kidscademy.atlas.Picture;
import com.kidscademy.atlas.UIObject;
import com.kidscademy.util.Classes;

import js.transaction.Immutable;
import js.transaction.Mutable;
import js.transaction.Transactional;

@Transactional
@Immutable
public class AtlasDaoImpl implements AtlasDao {
    private final EntityManager em;

    public AtlasDaoImpl(EntityManager em) {
	this.em = em;
    }

    @Override
    @Mutable
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
    public List<Link> getObjectLinks(UIObject object) {
	return em.createQuery("select o.links from AtlasObject o where o.dtype=:dtype and o.name=:name", Link.class)
		.setParameter("dtype", object.getDtype()).setParameter("name", object.getName()).getResultList();
    }

    @Override
    public <T extends AtlasObject> List<T> findObjectByType(Class<T> type) {
	return em.createQuery("select o from AtlasObject o where o.dtype=:dtype", type)
		.setParameter("dtype", Classes.dtype(type)).getResultList();
    }

    @Override
    public Bird getBird(int birdId) {
	return em.find(Bird.class, birdId);
    }

    @Override
    @Mutable
    public void removeObject(Object object) {
	if (!em.contains(object)) {
	    object = em.merge(object);
	}
	em.remove(object);
    }

    @Override
    public List<UIObject> findObjectsByName(Class<? extends AtlasObject> type, List<String> names) {
	if (names.isEmpty()) {
	    return Collections.emptyList();
	}
	String jpql = "select o from UIObject o where o.dtype=:dtype and o.name in :names";
	return em.createQuery(jpql, UIObject.class).setParameter("dtype", Classes.dtype(type))
		.setParameter("names", names).getResultList();
    }

    @Override
    public List<UIObject> getInstrumentsByCategory(Instrument.Category category) {
	String jpql = "select i.id from Instrument i where i.category=:category";
	List<Integer> ids = em.createQuery(jpql, Integer.class).setParameter("category", category).getResultList();

	jpql = "select o from UIObject o where o.id in :ids";
	return em.createQuery(jpql, UIObject.class).setParameter("ids", ids).getResultList();
    }

    @Override
    @Mutable
    public void resetObjectSample(String dtype, int id) {
	// by convention sample entity name is base entity plus 'Sample' suffix
	String jpql = String.format(
		"update %s o set o.sampleTitle=null,o.sampleName=null,o.waveformName=null where o.id=:id",
		Classes.entityName(dtype));
	em.createQuery(jpql).setParameter("id", id).executeUpdate();
    }

    @Override
    public AtlasObject getAtlasObject(int id) {
	return (AtlasObject) em.createQuery("select o from AtlasObject o where o.id=:id").setParameter("id", id)
		.getSingleResult();
    }

    @Override
    @Mutable
    public void saveAtlasObject(AtlasObject object) {
	if (object.getId() == 0) {
	    em.persist(object);
	} else {
	    em.merge(object).postMerge(object);
	}
    }

    @Override
    @Mutable
    public void removeObjectPicture(int objectId, Picture picture) {
	AtlasObject object = (AtlasObject) em.createQuery("select o from AtlasObject o where o.id=:id")
		.setParameter("id", objectId).getSingleResult();
	object.getPictures().remove(picture);
    }

    @Override
    @Mutable
    public void addObjectPicture(int objectId, Picture picture) {
	AtlasObject object = (AtlasObject) em.createQuery("select o from AtlasObject o where o.id=:id")
		.setParameter("id", objectId).getSingleResult();
	object.getPictures().add(picture);
    }

    @Override
    public Picture getPictureByName(int objectId, String name) {
	String jpql = "select p from AtlasObject o join o.pictures p where o.id=:id and p.name=:name";
	List<Picture> pictures = em.createQuery(jpql, Picture.class).setParameter("id", objectId)
		.setParameter("name", name).getResultList();
	return pictures.isEmpty() ? null : pictures.get(0);
    }
}
