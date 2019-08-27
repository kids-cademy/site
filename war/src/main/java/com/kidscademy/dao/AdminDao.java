package com.kidscademy.dao;

import com.kidscademy.atlas.Login;
import com.kidscademy.atlas.User;

public interface AdminDao {
    User getUser(Login login);

    User getUserById(int userId);
}
