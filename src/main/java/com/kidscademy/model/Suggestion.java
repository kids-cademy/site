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
@Table(name="developer_message")
public class Suggestion
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ManyToOne
  @JoinColumn(name = "appId")
  private App app;

  private Timestamp timestamp;
  private String ip;
  private String text;
  private String developerName;
  private String senderEmail;

  public void setApp(App app)
  {
    this.app = app;
  }

  public void setIp(String ip)
  {
    this.ip = ip;
  }

  public void setText(String text)
  {
    this.text = text;
  }

  public void setDeveloperName(String developerName)
  {
    this.developerName = developerName;
  }

  public void setSenderEmail(String senderEmail)
  {
    this.senderEmail = senderEmail;
  }
}
