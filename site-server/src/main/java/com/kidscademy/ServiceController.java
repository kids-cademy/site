package com.kidscademy;

import java.util.List;

import com.kidscademy.model.Counters;
import com.kidscademy.model.Device;
import com.kidscademy.model.DislikeReason;
import com.kidscademy.model.Feedback;

import js.tiny.container.annotation.Public;
import js.tiny.container.annotation.Service;

@Service
@Public
public interface ServiceController
{
  void recordAuditEvent(String appName, Device device, String event, String... parameters);

  void dumpStackTrace(String appName, Device device, String stackTrace);

  void sendDeveloperMessage(String appName, Device device, String developerName, String message, String senderEmail);

  void agreeNoAdsManifest(Device device, boolean agree);

  Feedback getFeedbackData();

  Counters incrementLikeCounter();

  Counters incrementDislikeCounter(List<DislikeReason> reasons);
}