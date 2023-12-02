package com.projectment.service;

import com.projectment.security.User;

public interface UserService {

    void saveUser(User user);

    User getUserById(String id);

    User getUserByEmail(String email);

}
