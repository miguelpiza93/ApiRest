package com.example.ApiRest.controllers;

import com.example.ApiRest.dto.GetUsersResponse;
import com.example.ApiRest.entities.User;
import com.example.ApiRest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<GetUsersResponse> getUsers() throws Exception {
        Iterable<User> users = userService.findAll();
        return new ResponseEntity<>(new GetUsersResponse(users), HttpStatus.CREATED);
    }
}
