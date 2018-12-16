package com.kidscademy.atlas;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Region
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private Integer objectId;

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

  public void setObjectId(int objectId)
  {
    this.objectId = objectId;
  }

  public int getObjectId()
  {
    return objectId;
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
