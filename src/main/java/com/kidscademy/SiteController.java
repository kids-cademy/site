package com.kidscademy;

import javax.mail.internet.InternetAddress;

import js.annotation.Public;
import js.annotation.Remote;

@Remote
public interface SiteController
{
  @Public
  void forgetPassword(InternetAddress emailAddress);
}
