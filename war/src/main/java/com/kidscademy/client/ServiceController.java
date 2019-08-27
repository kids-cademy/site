package com.kidscademy.client;

import com.kidscademy.model.Device;

public interface ServiceController
{
  void recordAuditEvent(String appName, Device device, String event, String... parameters);

  void dumpStackTrace(String appName, Device device, String stackTrace);

  void sendDeveloperMessage(String appName, Device device, String developerName, String message, String senderEmail);

  void agreeNoAdsManifest(Device device, boolean agree);
}
