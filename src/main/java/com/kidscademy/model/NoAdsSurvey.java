package com.kidscademy.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("unused")
@Entity
@Table(name="no_ads_survey")
public class NoAdsSurvey
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name="deviceId")
  private Device device;

  private Timestamp timestamp;
  private String ip;
  private boolean agree;

  public NoAdsSurvey()
  {
  }

  public NoAdsSurvey(boolean agree)
  {
    super();
    this.agree = agree;
  }

  public void setIp(String ip)
  {
    this.ip = ip;
  }

  public void setDevice(Device device)
  {
    this.device = device;
  }
}
