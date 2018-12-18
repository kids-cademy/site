package com.kidscademy.atlas;

import java.net.URL;

import javax.persistence.Embeddable;

@Embeddable
public class Link
{
  private int id;
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
  public Link(URL url, String name, String description, String iconPath)
  {
    this.url = url;
    this.name = name;
    this.description = description;
    this.iconPath = iconPath;
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
