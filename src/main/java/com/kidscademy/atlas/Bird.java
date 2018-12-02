package com.kidscademy.atlas;

import javax.persistence.Entity;

@Entity
public class Bird extends AtlasObject
{
  private Wingspan wingspan;

  public Wingspan getWingspan()
  {
    return wingspan;
  }

  public void setWingspan(Wingspan wingspan)
  {
    this.wingspan = wingspan;
  }
}
