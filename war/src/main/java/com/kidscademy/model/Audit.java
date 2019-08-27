package com.kidscademy.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("unused")
@Entity
public class Audit
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int id;
  
  @ManyToOne
  @JoinColumn(name="appId")
  private App app;

  @ManyToOne
  @JoinColumn(name="deviceId")
  private Device device;

  private Timestamp timestamp;
  @Column(name="ip")
  private String remoteHost;
  private String event;
  private String parameter1;
  private String parameter2;

  public void setApp(App app)
  {
    this.app = app;
  }

  public void setRemoteHost(String remoteHost)
  {
    this.remoteHost = remoteHost;
  }

  public void setDevice(Device device)
  {
    this.device = device;
  }

  public void setEvent(String event)
  {
    this.event = event;
  }

  public void setParameter1(String parameter1)
  {
    this.parameter1 = parameter1;
  }

  public void setParameter2(String parameter2)
  {
    this.parameter2 = parameter2;
  }
}
