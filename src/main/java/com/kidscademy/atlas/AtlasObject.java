package com.kidscademy.atlas;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class AtlasObject implements MediaWrapper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    /**
     * Discriminator type identify object class. Its value is derived from simple
     * class name to lower case.
     */
    protected String dtype;

    @ManyToOne
    protected User user;

    /** Object state, for now only in development and published. */
    protected State state;

    /** Last change timestamp. */
    protected Date lastUpdated;

    protected int rank;
    /**
     * Object name unique per dtype. This value is used internally and is not meant
     * to be displayed to user.
     */
    protected String name;

    /**
     * Object name as displayed on user interface. It is subject to
     * internationalization.
     */
    protected String display;

    /**
     * Object description is rich text, that is, it can contains images, links and
     * text formatting. It is stored as HTML.
     */
    protected String description;

    /**
     * Pictures associated with this object. There are three kinds of pictures:
     * object icon, one about object itself and one with object in its natural
     * context.
     * <p>
     * Object icon has a small dimension and has 1:1 ratio; usually is 96x96 pixels.
     * <p>
     * Object picture focused on object only and has transparent background. For
     * this reason it is of PNG type. It has fixed width - usually 560 pixels, but
     * variable height to accommodate picture content, hence aspect ratio is
     * variable too.
     * <p>
     * Contextual picture present object in its context, e.g. for a musical
     * instrument it contains a player using the instrument. It has 16:9 aspect
     * ration and usually is 920x560 pixels.
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    @OrderColumn
    protected List<Picture> pictures;

    @ElementCollection
    protected List<String> aliases;

    /** Optional object spreading, empty list if not applicable. */
    @ElementCollection
    protected List<Region> spreading;

    @ElementCollection
    protected Map<String, String> facts;

    @ElementCollection
    protected List<Link> links;

    @ElementCollection
    protected List<Related> related;

    public void postMerge(AtlasObject source) {
	if (pictures != null) {
	    for (int i = 0; i < pictures.size(); ++i) {
		pictures.get(i).postMerge(source.getPictures().get(i));
	    }
	}

	if (links != null) {
	    for (int i = 0; i < links.size(); ++i) {
		final MediaSRC iconSrc = source.links.get(i).getIconSrc();
		links.get(i).setIconName(iconSrc != null ? iconSrc.fileName() : null);
	    }
	}
    }

    /**
     * For testing
     * 
     * @param id
     */
    public void setId(Integer id) {
	this.id = id;
    }

    public Integer getId() {
	return id;
    }

    public String getDtype() {
	return dtype;
    }

    public State getState() {
	return state;
    }

    public void setState(State state) {
	this.state = state;
    }

    public Date getLastUpdated() {
	return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
	this.lastUpdated = lastUpdated;
    }

    public int getRank() {
	return rank;
    }

    public void setRank(int relevanceRank) {
	this.rank = relevanceRank;
    }

    public User getUser() {
	return user;
    }

    public void setUser(User user) {
	this.user = user;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public List<Picture> getPictures() {
	return pictures;
    }

    public void setPictures(List<Picture> pictures) {
	this.pictures = pictures;
    }

    public List<String> getAliases() {
	return aliases;
    }

    public void setAliases(List<String> aliases) {
	this.aliases = aliases;
    }

    public String getDisplay() {
	return display;
    }

    public void setDisplay(String display) {
	this.display = display;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public List<Region> getSpreading() {
	return spreading;
    }

    public void setSpreading(List<Region> spreading) {
	this.spreading = spreading;
    }

    public Map<String, String> getFacts() {
	return facts;
    }

    public void setFacts(Map<String, String> facts) {
	this.facts = facts;
    }

    public List<Link> getLinks() {
	return links;
    }

    public void setLinks(List<Link> links) {
	this.links = links;
    }

    public List<Related> getRelated() {
	return related;
    }

    public void setRelated(List<Related> related) {
	this.related = related;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + id;
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	AtlasObject other = (AtlasObject) obj;
	if (id != other.id)
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return name;
    }

    public enum State {
	// ENUM('DEVELOPMENT','PUBLISHED')
	DEVELOPMENT, PUBLISHED
    }
}
