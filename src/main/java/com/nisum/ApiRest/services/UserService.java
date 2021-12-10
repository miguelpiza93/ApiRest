package com.nisum.ApiRest.services;

import com.nisum.ApiRest.entities.User;

public interface UserService {

    User addUser(User user);
    Iterable<User> getUsers();
    User findUserByEmail(String email);
}
