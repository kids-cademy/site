package com.kidscademy.test;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.kidscademy.ServiceController;
import com.kidscademy.dao.Dao;
import com.kidscademy.impl.ServiceControllerImpl;
import com.kidscademy.model.Device;
import com.kidscademy.model.Model;

import js.core.AppContext;
import js.util.Classes;

/**
 * Unit tests for application controller.
 * 
 * @author Iulian Rotaru
 */
@RunWith(MockitoJUnitRunner.class)
public class ServiceControllerTest
{
  @Mock
  private AppContext context;
  @Mock
  private Dao dao;
  
  /**
   * Controller instance under test.
   */
  private ServiceController controller;

  @Before
  public void beforeTest()
  {
    controller = new ServiceControllerImpl(context, dao);
  }

  @Test
  public void recordAuditEvent()
  {
    controller.recordAuditEvent("test", getDevice(), "APP_LOAD", "12345");
  }

  @Test
  public void dumpStackTrace()
  {
    try {
      Integer number = null;
      System.out.println(number.toString());
    }
    catch(Exception e) {
      StringWriter stackTrace = new StringWriter();
      e.printStackTrace(new PrintWriter(stackTrace));
      controller.dumpStackTrace("test", getDevice(), stackTrace.toString());
    }
  }

  @Test
  public void sendDeveloperMessage()
  {
    controller.sendDeveloperMessage("test", getDevice(), "John Doe", "test message", "john@email.com");
  }

  @Test
  public void agreeNoAdsManifest()
  {
    controller.agreeNoAdsManifest(getDevice(), true);
  }

  private static Device getDevice()
  {
    Model model = new Model();
    Classes.setFieldValue(model, "manufacturer", "Samsung");
    Classes.setFieldValue(model, "model", "S3 Mini");
    Classes.setFieldValue(model, "version", "4.3.1");
    Classes.setFieldValue(model, "apiLevel", 18);

    Device device = new Device();
    Classes.setFieldValue(device, "model", model);
    Classes.setFieldValue(device, "serial", "qwertyuiop");
    return device;
  }
}
