package com.kidscademy.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import com.kidscademy.atlas.AtlasObject;
import com.kidscademy.atlas.Bird;
import com.kidscademy.atlas.Instrument;
import com.kidscademy.atlas.Link;
import com.kidscademy.atlas.Picture;
import com.kidscademy.atlas.UIObject;

public interface AtlasDao {
    /**
     * Persist or merge instrument entity, depending on ID value. If ID is zero
     * instance is considered never created into database and
     * {@link EntityManager#persist(Object)} is enacted. Otherwise instance is
     * considered persisted but detached and {@link EntityManager#merge(Object)} is
     * performed.
     * <p>
     * Instrument entity has JPA event listener for {@link PostLoad} used to
     * initialize root-relative media SRC, used on web interface, from media file
     * names that are persisted. Also has {@link PrePersist} for reverse operation.
     * <p>
     * Because media SRC fields are {@link Transient}, merge is not able to detect
     * they are changed and {@link PreUpdate} cannot be used because will be not
     * triggered. This method takes care to explicitly invoke
     * {@link Instrument#postMerge(Instrument)} on attached entity.
     * 
     * @param instrument
     *            instrument instance.
     */
    void saveInstrument(Instrument instrument);

    Instrument getInstrument(int instrumentId);

    Instrument getInstrumentByName(String name);

    <T extends AtlasObject> List<T> findObjectByType(Class<T> type);

    Bird getBird(int birdId);

    List<UIObject> getInstruments();

    void removeObject(Object object);

    List<UIObject> findObjectsByName(Class<? extends AtlasObject> type, List<String> names);

    List<UIObject> getInstrumentsByCategory(Instrument.Category category);

    void resetObjectSample(String dtype, int id);

    List<Link> getObjectLinks(UIObject object);

    /**
     * Get polymorphic object. JPA uses discriminator dtype to instantiate the right
     * type.
     * 
     * @param id
     * @return
     */
    AtlasObject getAtlasObject(int id);

    void saveAtlasObject(AtlasObject object);

    void removeObjectPicture(int objectId, Picture picture);
}
