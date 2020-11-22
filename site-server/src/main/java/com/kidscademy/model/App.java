package com.kidscademy.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class App
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  
  private String name;

  public App()
  {
  }

  public App(String name)
  {
    this.name = name;
  }

  public int getId()
  {
    return id;
  }

  public String getName()
  {
    return name;
  }
}
