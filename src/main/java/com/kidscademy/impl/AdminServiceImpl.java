package com.kidscademy.impl;

import com.kidscademy.AdminService;
import com.kidscademy.atlas.Login;
import com.kidscademy.atlas.User;
import com.kidscademy.dao.AdminDao;

import js.core.AppContext;

public class AdminServiceImpl implements AdminService {
    private final AppContext context;
    private final AdminDao dao;

    public AdminServiceImpl(AppContext context, AdminDao dao) {
	this.context = context;
	this.dao = dao;
    }

    @Override
    public boolean login(Login login) {
	User user = dao.getUser(login);
	if (user != null) {
	    context.login(user);
	    return true;
	}
	return false;
    }

    @Override
    public boolean isAuthenticated() {
	return context.isAuthenticated();
    }
}
