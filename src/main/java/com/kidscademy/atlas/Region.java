package com.kidscademy.atlas;

import javax.persistence.Embeddable;

@Embeddable
public class Region
{
  private int id;
  private String name;
  private Area area;

  public Region()
  {
    area = Area.ALL;
  }

  public Region(String name)
  {
    this();
    this.name = name;
  }

  public Region(String name, Area area)
  {
    this(name);
    this.area = area;
  }

  public int getId()
  {
    return id;
  }

  public String getName()
  {
    return name;
  }

  public Area getArea()
  {
    return area;
  }

  public enum Area
  {
    ALL, CENTRAL, NORTH, NORTH_EAST, EAST, SOUTH_EAST, SOUTH, SOUTH_WEST, WEST, NORTH_WEST
  }
}
