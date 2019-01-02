package com.kidscademy.model;

public class Counters
{
  private final int likeCount;
  private final int dislikeCount;

  public Counters(int likeCount, int dislikeCount)
  {
    this.likeCount = likeCount;
    this.dislikeCount = dislikeCount;
  }

  public int getLikeCount()
  {
    return likeCount;
  }

  public int getDislikeCount()
  {
    return dislikeCount;
  }
}
