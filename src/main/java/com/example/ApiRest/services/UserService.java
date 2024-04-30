package com.example.ApiRest.services;

import com.example.ApiRest.entities.User;

public interface UserService {

    User addUser(User user);
    User findUserByEmail(String email);
    Iterable<User> findAll();
}
