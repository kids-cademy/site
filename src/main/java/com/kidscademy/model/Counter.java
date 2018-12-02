package com.kidscademy.model;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class Counter
{
  private int id;
  private Timestamp timestamp;
  private String ip;
  private boolean value;
  private List<DislikeReason> reasons = Collections.emptyList();

  public Counter(boolean value, String ip)
  {
    this.value = value;
    this.ip = ip;
  }

  public Counter(boolean value, List<DislikeReason> reasons, String ip)
  {
    this.value = value;
    this.reasons = reasons;
    this.ip = ip;
  }

  public int getId()
  {
    return id;
  }

  public List<DislikeReason> getReasons()
  {
    return reasons;
  }
}
