package com.nisum.ApiRest.services;

import com.nisum.ApiRest.entities.User;

public interface UserService {

    User addUser(User user);
    User findUserByEmail(String email);
}
