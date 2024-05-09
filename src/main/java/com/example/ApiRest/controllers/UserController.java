package com.example.ApiRest.controllers;

import com.example.ApiRest.dto.GetUsersResponse;
import com.example.ApiRest.entities.User;
import com.example.ApiRest.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController(value = "/api/v1/user")
@Tag(name = "Users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "This method is used to get the users.")
    @GetMapping
    public ResponseEntity<GetUsersResponse> getUsers() throws Exception {
        Iterable<User> users = userService.findAll();
        return new ResponseEntity<>(new GetUsersResponse(users), HttpStatus.CREATED);
    }
}
