package com.kidscademy.atlas;

import javax.persistence.Embeddable;

@Embeddable
public class Related
{
  private String name;
  private float relevance;

  public Related()
  {
  }

  /**
   * Test constructor.
   * 
   * @param name
   * @param relevance
   */
  public Related(String name, float relevance)
  {
    this.name = name;
    this.relevance = relevance;
  }

  public String getName()
  {
    return name;
  }

  public float getRelevance()
  {
    return relevance;
  }
}
