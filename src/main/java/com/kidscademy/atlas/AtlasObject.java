package com.kidscademy.atlas;

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
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class AtlasObject
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected int id;

  protected int repositoryIndex;
  protected int rank;
  protected String name;
  protected String display;
  protected String description;
  protected String iconPath;
  protected String thumbnailPath;
  protected String picturePath;

  @ElementCollection
  protected List<String> aliases;

  /** Optional object spreading, empty list if not applicable. */
  @OneToMany(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "objectId")
  protected List<Region> spreading;

  @ElementCollection
  protected Map<String, String> facts;

  @OneToMany(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "objectId")
  protected List<Link> links;

  @ElementCollection
  private List<Integer> related;

  public int getId()
  {
    return id;
  }

  public int getRepositoryIndex()
  {
    return repositoryIndex;
  }

  public void setRepositoryIndex(int index)
  {
    this.repositoryIndex = index;
  }

  public int getRank()
  {
    return rank;
  }

  public void setRank(int relevanceRank)
  {
    this.rank = relevanceRank;
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

  public List<Integer> getRelated()
  {
    return related;
  }

  public void setRelated(List<Integer> related)
  {
    this.related = related;
  }
}
