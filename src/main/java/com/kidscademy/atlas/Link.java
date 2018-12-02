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

  @SuppressWarnings("unused")
  private Integer objectId;

  private URL url;
  private String name;
  private String description;
  private String iconPath;

  public Link()
  {
  }

  public Link(URL url, String name, String description, String iconPath)
  {
    this.url = url;
    this.name = name;
    this.description = description;
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
