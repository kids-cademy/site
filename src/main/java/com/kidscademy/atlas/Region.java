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

  @SuppressWarnings("unused")
  private Integer objectId;

  private String name;
  private Area area;

  public Region()
  {
    area = Area.WHOLE;
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
    WHOLE, CENTRAL, N, NNE, NE, ENE, E, ESE, SE, SSE, S, SSW, SW, WSW, W, WNW, NW, NNW
  }
}
