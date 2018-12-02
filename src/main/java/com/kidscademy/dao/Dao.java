package com.kidscademy.dao;

import java.util.List;

import com.kidscademy.model.App;
import com.kidscademy.model.Audit;
import com.kidscademy.model.Counter;
import com.kidscademy.model.Counters;
import com.kidscademy.model.CrashReport;
import com.kidscademy.model.Device;
import com.kidscademy.model.NoAdsSurvey;
import com.kidscademy.model.Suggestion;

public interface Dao
{
  void createCounter(Counter counter);

  Counters getCounters();

  void createDeveloperMessage(Suggestion suggestion);

  List<Suggestion> getSuggestions();

  void createAudit(Audit audit);

  void createCrashReport(CrashReport crashReport);

  /**
   * Get existing device with the same properties as the given one, creating it if is missing from database.
   * 
   * @param device device data.
   * @return database device.
   */
  Device getDevice(Device device);

  void createNoAdsSurvey(NoAdsSurvey noAdsSurvey);

  /**
   * Get existing named app instance, creating it if missing.
   * 
   * @param appName
   * @return
   */
  App getApp(String appName);
}
