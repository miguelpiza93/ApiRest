package com.example.ApiRest.services.authentication;

import com.example.ApiRest.dto.auth.authenticate.AuthRequest;
import com.example.ApiRest.dto.auth.register.RegisterRequest;

public interface AuthenticationService {

    String authenticate(AuthRequest authRequest);
    String register(RegisterRequest registerRequest) throws Exception;
}
