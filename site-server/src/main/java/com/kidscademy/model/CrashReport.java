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
@Table(name="crash_report")
public class CrashReport
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
  private String ip;
  private String stackTrace;

  public void setApp(App app)
  {
    this.app = app;
  }

  public void setIp(String ip)
  {
    this.ip = ip;
  }

  public void setDevice(Device device)
  {
    this.device = device;
  }

  public void setStackTrace(String stackTrace)
  {
    this.stackTrace = stackTrace;
  }
}
