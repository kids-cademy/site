package com.kidscademy;

import com.kidscademy.atlas.User;
import com.kidscademy.dao.AdminDao;

import js.core.AppContext;
import js.lang.Callback;

public class WoodPreviewCallback implements Callback<AppContext>
{
  @Override
  public void handle(AppContext context)
  {
    AdminDao dao = context.getInstance(AdminDao.class);
    User user = dao.getUserById(1);
    context.login(user);
  }
}
