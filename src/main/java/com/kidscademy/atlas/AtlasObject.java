package com.kidscademy.atlas;

import java.util.List;
import java.util.Map;

import javax.persistence.Column;
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

  protected boolean published;
  protected int rank;
  protected String name;
  protected String display;
  protected String description;
  protected String iconPath;
  protected String thumbnailPath;
  protected String picturePath;

  @ElementCollection()
  protected List<String> aliases;

  /** Optional object spreading, empty list if not applicable. */
  @ElementCollection()
  protected List<Region> spreading;

  @ElementCollection
  protected Map<String, String> facts;

  @ElementCollection()
  protected List<Link> links;

  @ElementCollection
  @Column(name = "atlasobject_name")
  protected List<String> related;

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

  public boolean isPublished()
  {
    return published;
  }

  public void setPublished(boolean published)
  {
    this.published = published;
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

  public String getIconPath()
  {
    return iconPath;
  }

  public void setIconPath(String iconPath)
  {
    this.iconPath = iconPath;
  }

  public String getThumbnailPath()
  {
    return thumbnailPath;
  }

  public void setThumbnailPath(String thumbnailPath)
  {
    this.thumbnailPath = thumbnailPath;
  }

  public String getPicturePath()
  {
    return picturePath;
  }

  public void setPicturePath(String picturePath)
  {
    this.picturePath = picturePath;
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

  public List<String> getRelated()
  {
    return related;
  }

  public void setRelated(List<String> related)
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
}
