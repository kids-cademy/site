package com.kidscademy.atlas;

import java.util.List;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class AtlasObject
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected int id;
  protected String dtype;

  @ManyToOne
  protected User user;

  protected State state;
  protected int rank;
  protected String name;
  protected String display;
  protected String description;
  /**
   * Media file name for object picture. This is a picture of the object in its natural context. It may contain the object and related contextual content; e.g.
   * for a musical instrument it contains a player using the instrument. It has 16:9 aspect ration and usually is 902x560 pixels.
   * <p>
   * This fields contains only file name, including extension, but does not contain any path components. This is to avoid keeping path structure into database.
   * Path components are added when object is loaded from persistence by a specialized hook, from every concrete object class. Media file name plus path
   * components are a root-relative URL and is named media SRC; remember that root-relative URL is the URL path after server that starts from context.
   */
  protected String pictureName;
  /**
   * Media file name for object icon. Object icon has a small dimension and has 1:1 ratio; usually is 96x96 pixels. See {@link #pictureName} for details about
   * media file name.
   */
  protected String iconName;
  /**
   * Media file name for object thumbnail. Object thumbnail is a featured picture that have transparent background. It has fixed width - usually 560 pixels, but
   * variable height to accommodate picture content, hence aspect ratio is variable too. See {@link #pictureName} for details about media file name.
   */
  protected String thumbnailName;

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

  /**
   * For testing
   * 
   * @param id
   */
  public void setId(Integer id)
  {
    this.id = id;
  }

  public Integer getId()
  {
    return id;
  }

  public String getDtype()
  {
    return dtype;
  }

  public State getState()
  {
    return state;
  }

  public void setState(State state)
  {
    this.state = state;
  }

  public int getRank()
  {
    return rank;
  }

  public void setRank(int relevanceRank)
  {
    this.rank = relevanceRank;
  }

  public User getUser()
  {
    return user;
  }

  public void setUser(User user)
  {
    this.user = user;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public List<String> getAliases()
  {
    return aliases;
  }

  public void setAliases(List<String> aliases)
  {
    this.aliases = aliases;
  }

  public String getDisplay()
  {
    return display;
  }

  public void setDisplay(String display)
  {
    this.display = display;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public String getIconName()
  {
    return iconName;
  }

  public void setIconName(String iconName)
  {
    this.iconName = iconName;
  }

  public String getThumbnailName()
  {
    return thumbnailName;
  }

  public void setThumbnailName(String thumbnailName)
  {
    this.thumbnailName = thumbnailName;
  }

  public String getPictureName()
  {
    return pictureName;
  }

  public void setPictureName(String pictureName)
  {
    this.pictureName = pictureName;
  }

  public List<Region> getSpreading()
  {
    return spreading;
  }

  public void setSpreading(List<Region> spreading)
  {
    this.spreading = spreading;
  }

  public Map<String, String> getFacts()
  {
    return facts;
  }

  public void setFacts(Map<String, String> facts)
  {
    this.facts = facts;
  }

  public List<Link> getLinks()
  {
    return links;
  }

  public void setLinks(List<Link> links)
  {
    this.links = links;
  }

  public List<Related> getRelated()
  {
    return related;
  }

  public void setRelated(List<Related> related)
  {
    this.related = related;
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = prime * result + id;
    return result;
  }

  @Override
  public boolean equals(Object obj)
  {
    if(this == obj) return true;
    if(obj == null) return false;
    if(getClass() != obj.getClass()) return false;
    AtlasObject other = (AtlasObject)obj;
    if(id != other.id) return false;
    return true;
  }

  @Override
  public String toString()
  {
    return name;
  }

  public enum State
  {
    // ENUM('DEVELOPMENT','CREATED','PUBLISHED')
    DEVELOPMENT, CREATED, PUBLISHED
  }
}
