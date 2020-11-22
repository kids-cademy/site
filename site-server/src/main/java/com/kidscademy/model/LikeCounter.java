package com.kidscademy.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.kidscademy.dao.DislikeReasonConverter;

@SuppressWarnings("unused")
@Entity
public class LikeCounter
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private Timestamp timestamp;
  private String ip;
  private boolean value;

  @ElementCollection
  @Convert(converter = DislikeReasonConverter.class)
  private List<DislikeReason> reasons;

  public LikeCounter()
  {
  }

  public LikeCounter(boolean value, String ip)
  {
    this.value = value;
    this.ip = ip;
  }

  public LikeCounter(boolean value, String ip, List<DislikeReason> reasons)
  {
    this.value = value;
    this.ip = ip;
    this.reasons = reasons;
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
