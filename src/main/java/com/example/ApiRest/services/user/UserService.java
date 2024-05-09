package com.example.ApiRest.services.user;

import com.example.ApiRest.entities.User;

import java.util.Optional;

public interface UserService {

    User addUser(User user);
    Optional<User> findUserByEmail(String email);
    Iterable<User> findAll();
}
