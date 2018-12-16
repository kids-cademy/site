package com.kidscademy.atlas;

import java.net.URL;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Link
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private int objectId;

  private URL url;
  private String name;
  private String description;
  private String iconPath;

  public Link()
  {
  }

  /**
   * Test constructor.
   * 
   * @param objectId
   * @param url
   * @param name
   * @param description
   * @param iconPath
   */
  public Link(int objectId, URL url, String name, String description, String iconPath)
  {
    this.objectId = objectId;
    this.url = url;
    this.name = name;
    this.description = description;
    this.iconPath = iconPath;
  }

  public void setObjectId(int objectId)
  {
    this.objectId = objectId;
  }

  public int getObjectId()
  {
    return objectId;
  }

  public void setIconPath(String iconPath)
  {
    this.iconPath = iconPath;
  }

  public int getId()
  {
    return id;
  }

  public URL getUrl()
  {
    return url;
  }

  public String getName()
  {
    return name;
  }

  public String getDescription()
  {
    return description;
  }

  public String getIconPath()
  {
    return iconPath;
  }

  @Override
  public String toString()
  {
    return url != null ? url.toExternalForm() : "null";
  }
}
