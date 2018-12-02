package com.kidscademy.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Device
{
  public static final Device SITE = new Device(0);

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  
  @ManyToOne
  @JoinColumn(name="modelId")
  private Model model = new Model();
  
  private String serial;

  public Device()
  {
  }

  public Device(int id)
  {
    this.id = id;
  }

  public void setId(int id)
  {
    this.id = id;
  }

  public void setModel(Model model)
  {
    this.model = model;
  }

  public void setSerial(String serial)
  {
    this.serial = serial;
  }

  public int getId()
  {
    return id;
  }

  public Model getModel()
  {
    return model;
  }

  public String getSerial()
  {
    return serial;
  }
}
