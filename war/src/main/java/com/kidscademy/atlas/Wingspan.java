package com.kidscademy.atlas;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Wingspan
{
  /** Minimum wing span of a mature individual, in centimeters. */
  @Column(name="wingspan_minimum")
  private int minimum;
  /** Maximum wing span value of a mature individual, in centimeters. */
  @Column(name="wingspan_maximum")
  private int maximum;

  public int getMinimum()
  {
    return minimum;
  }

  public void setMinimum(int minimum)
  {
    this.minimum = minimum;
  }

  public int getMaximum()
  {
    return maximum;
  }

  public void setMaximum(int maximum)
  {
    this.maximum = maximum;
  }
}
