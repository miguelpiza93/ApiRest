package com.example.ApiRest.controllers;

import com.example.ApiRest.dto.auth.authenticate.AuthResponse;
import com.example.ApiRest.dto.auth.register.RegisterRequest;
import com.example.ApiRest.dto.auth.register.RegisterResponse;
import com.example.ApiRest.dto.auth.authenticate.AuthRequest;
import com.example.ApiRest.services.authentication.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
        var token = authenticationService.authenticate(authRequest);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody RegisterRequest body) throws Exception {
        var token = authenticationService.register(body);
        return new ResponseEntity<>(new RegisterResponse(token), HttpStatus.CREATED);
    }
}
