package com.kidscademy.impl;

import java.util.List;

import com.kidscademy.ServiceController;
import com.kidscademy.dao.Dao;
import com.kidscademy.model.Audit;
import com.kidscademy.model.Counter;
import com.kidscademy.model.Counters;
import com.kidscademy.model.CrashReport;
import com.kidscademy.model.Device;
import com.kidscademy.model.DislikeReason;
import com.kidscademy.model.Feedback;
import com.kidscademy.model.NoAdsSurvey;
import com.kidscademy.model.Suggestion;

import js.core.AppContext;
import js.core.Factory;
import js.servlet.RequestContext;
import js.util.Params;

public class ServiceControllerImpl implements ServiceController
{
  private final AppContext context;
  private final Dao dao;

  public ServiceControllerImpl(AppContext context, Dao dao)
  {
    this.context = context;
    this.dao = dao;
  }

  @Override
  public void recordAuditEvent(String appName, Device device, String event, String... parameters)
  {
    Params.notEmpty(appName, "App name");
    Params.notNull(device, "Device");
    Params.notEmpty(event, "Audit event");

    Audit audit = new Audit();
    audit.setApp(dao.getApp(appName));
    audit.setRemoteHost(context.getRemoteAddr());
    audit.setDevice(dao.getDevice(device));
    audit.setEvent(event);

    // current database structure stores only two parameters but audit record interface allows for not limited
    if(parameters.length > 0) {
      audit.setParameter1(parameters[0]);
    }
    if(parameters.length > 1) {
      audit.setParameter2(parameters[1]);
    }

    dao.createAudit(audit);
  }

  @Override
  public void dumpStackTrace(String appName, Device device, String stackTrace)
  {
    Params.notEmpty(appName, "App name");
    Params.notNull(device, "Device");
    Params.notEmpty(stackTrace, "Stack trace");

    CrashReport crashReport = new CrashReport();
    crashReport.setApp(dao.getApp(appName));
    crashReport.setIp(context.getRemoteAddr());
    crashReport.setDevice(dao.getDevice(device));
    crashReport.setStackTrace(stackTrace);
    dao.createCrashReport(crashReport);
  }

  @Override
  public void sendDeveloperMessage(String appName, Device device, String developerName, String message, String senderEmail)
  {
    Params.notEmpty(appName, "App name");
    Params.notEmpty(message, "Message");

    Suggestion developerMessage = new Suggestion();
    developerMessage.setApp(dao.getApp(appName));
    developerMessage.setIp(context.getRemoteAddr());
    developerMessage.setDeveloperName(developerName);
    developerMessage.setText(message);
    developerMessage.setSenderEmail(senderEmail);
    dao.createDeveloperMessage(developerMessage);
  }

  @Override
  public void agreeNoAdsManifest(Device device, boolean agree)
  {
    Params.notNull(device, "Device");

    NoAdsSurvey noAdsSurvey = new NoAdsSurvey(agree);
    noAdsSurvey.setIp(context.getRemoteAddr());
    if(device != null) {
      noAdsSurvey.setDevice(dao.getDevice(device));
    }
    dao.createNoAdsSurvey(noAdsSurvey);
  }

  @Override
  public Feedback getFeedbackData()
  {
    return new Feedback(dao.getCounters(), dao.getSuggestions());
  }

  @Override
  public Counters incrementLikeCounter()
  {
    RequestContext context = Factory.getInstance(RequestContext.class);
    dao.createCounter(new Counter(true, context.getRemoteHost()));
    return dao.getCounters();
  }

  @Override
  public Counters incrementDislikeCounter(List<DislikeReason> reasons)
  {
    Params.notNull(reasons, "Dislike reasons list");

    RequestContext context = Factory.getInstance(RequestContext.class);
    dao.createCounter(new Counter(false, reasons, context.getRemoteHost()));
    return dao.getCounters();
  }
}
