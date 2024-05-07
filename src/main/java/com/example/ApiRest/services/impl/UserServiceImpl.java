package com.example.ApiRest.services.impl;

import com.example.ApiRest.repositories.UserRepository;
import com.example.ApiRest.entities.User;
import com.example.ApiRest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public User addUser(User user){
        return userRepository.save(user);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Iterable<User> findAll(){ return userRepository.findAll(); }

}
