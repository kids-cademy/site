package com.kidscademy;

import com.kidscademy.atlas.Login;

import js.annotation.Public;
import js.annotation.Service;

@Service
public interface AdminService {
    @Public
    boolean login(Login login);

    @Public
    boolean isAuthenticated();
}
