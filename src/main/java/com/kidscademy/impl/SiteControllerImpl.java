package com.kidscademy.impl;

import javax.mail.internet.InternetAddress;

import js.annotation.Inject;
import js.email.Email;
import js.email.EmailSender;
import js.log.Log;
import js.log.LogFactory;

import com.kidscademy.SiteController;
import com.kidscademy.model.Model;

public class SiteControllerImpl implements SiteController
{
  private static final Log log = LogFactory.getLog(SiteControllerImpl.class);

  @Inject
  private EmailSender emailSender;

  @Override
  public void forgetPassword(InternetAddress emailAddress)
  {
    log.trace("forgetPassword(InternetAddress)");
    log.debug(emailAddress);

    Model model = new Model();
    
    Email email = emailSender.getEmail("password-recovery");
    email.to(emailAddress.toString()).send(model);

    // processor.send("\"kids (a)cademy community\"<community@kids-cademy.com>", "iulian@gnotis.ro",
    // "test forgot password", "forgot password...");
    // processor.send("kids cademy<community@kids-cademy.com>", "iulian@gnotis.ro", "test forgot password",
    // "forgot password...");
  }
}
