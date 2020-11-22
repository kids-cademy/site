package com.kidscademy;

import com.kidscademy.model.Login;

import js.tiny.container.annotation.Public;
import js.tiny.container.annotation.Service;

@Service
@Public
public class AdminService
{
  public boolean login(Login login) {
    return true;
  }

  public boolean isAuthenticated() {
    return true;
  }

  public void logout() {

  }
}
