package com.kidscademy.unit;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.kidscademy.dao.Dao;
import com.kidscademy.dao.DaoImpl;
import com.kidscademy.model.App;
import com.kidscademy.model.Audit;
import com.kidscademy.model.CrashReport;
import com.kidscademy.model.Device;
import com.kidscademy.model.Model;
import com.kidscademy.model.NoAdsSurvey;
import com.kidscademy.model.Suggestion;

import js.transaction.TransactionFactory;
import js.transaction.eclipselink.TransactionFactoryImpl;
import js.unit.db.Database;
import js.util.Classes;

/**
 * Unit tests for DAO implementation.
 * 
 * @author Iulian Rotaru
 */
public class DaoTest
{
  private static final String HIBERNATE_CFG = "hibernate.test.cfg.xml";

  private static Database database;
  private static TransactionFactory factory;

  @BeforeClass
  public static void beforeClass() throws IOException
  {
    database = new Database(HIBERNATE_CFG);
    factory = new TransactionFactoryImpl();
  }

  private Dao dao;

  @Before
  public void beforeTest() throws SQLException
  {
    database.load(Classes.getResourceAsStream("data-set.xml"));
    dao = factory.newInstance(DaoImpl.class);
  }

  @Test
  public void getApp_Existing()
  {
    App app = dao.getApp("com.kidscademy.atlas.birds");
    assertThat(app, notNullValue());
    assertThat(app.getId(), equalTo(1));
    assertThat(app.getName(), equalTo("com.kidscademy.atlas.birds"));
  }

  @Test
  public void getApp_New()
  {
    App app = dao.getApp("com.kidscademy.atlas.animals");
    assertThat(app, notNullValue());
    assertThat(app.getId(), not(equalTo(0)));
    assertThat(app.getName(), equalTo("com.kidscademy.atlas.animals"));
  }

  @Test
  public void createAudit()
  {
    Audit audit = new Audit();
    audit.setApp(dao.getApp("test"));
    audit.setRemoteHost("192.168.1.3");
    audit.setDevice(getDevice());
    audit.setEvent("APP_ACTIVE");
    audit.setParameter1("123456");
    dao.createAudit(audit);
  }

  @SuppressWarnings("null")
  @Test
  public void createCrashReport()
  {
    try {
      Integer number = null;
      System.out.println(number.toString());
    }
    catch(Exception e) {
      StringWriter stackTrace = new StringWriter();
      e.printStackTrace(new PrintWriter(stackTrace));

      CrashReport crashReport = new CrashReport();
      crashReport.setApp(dao.getApp("test"));
      crashReport.setIp("192.168.1.3");
      crashReport.setDevice(getDevice());
      crashReport.setStackTrace(stackTrace.toString());
      dao.createCrashReport(crashReport);
    }
  }

  @Test
  public void createDeveloperMessage()
  {
    Suggestion developerMessage = new Suggestion();
    developerMessage.setApp(dao.getApp("test"));
    developerMessage.setIp("192.168.1.3");
    developerMessage.setDeveloperName("John Doe");
    developerMessage.setText("test message");
    developerMessage.setSenderEmail(null);
    dao.createDeveloperMessage(developerMessage);
  }

  @Test
  public void createNoAdsSurvey()
  {
    NoAdsSurvey noAdsSurvey = new NoAdsSurvey(true);
    noAdsSurvey.setIp("192.168.1.3");
    noAdsSurvey.setDevice(getDevice());
    dao.createNoAdsSurvey(noAdsSurvey);
  }

  private Device getDevice()
  {
    Model model = new Model();
    model.setManufacturer("Samsung");
    model.setModel("S3 Mini");
    model.setVersion("4.3.1");
    model.setApiLevel(18);

    Device device = new Device();
    device.setModel(model);
    device.setSerial("qwertyuiop");
    return dao.getDevice(device);
  }
}
