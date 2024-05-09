package com.example.ApiRest.controllers;

import com.example.ApiRest.config.JwtService;
import com.example.ApiRest.dto.auth.authenticate.AuthRequest;
import com.example.ApiRest.dto.auth.authenticate.AuthResponse;
import com.example.ApiRest.dto.auth.register.PhoneRequest;
import com.example.ApiRest.dto.auth.register.RegisterRequest;
import com.example.ApiRest.dto.auth.register.RegisterResponse;
import com.example.ApiRest.entities.User;
import com.example.ApiRest.services.authentication.AuthenticationService;
import com.example.ApiRest.services.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @TestConfiguration
    static class AuthControllerTestContextConfiguration {

        @Bean
        public JwtService jwtService() {
            return mock(JwtService.class);
        }

        @Bean
        public AuthenticationService authenticationService() {
            return mock(AuthenticationService.class);
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return mock(PasswordEncoder.class);
        }
    }

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AuthController authController;

    @Test
    void testAuthenticateSuccessful() {
        // Given
        AuthRequest authRequest = mock(AuthRequest.class);
        when(authenticationService.authenticate(authRequest)).thenReturn("fake-token");

        // When
        ResponseEntity<?> responseEntity = authController.authenticate(authRequest);

        // Then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertInstanceOf(AuthResponse.class, responseEntity.getBody());
        AuthResponse authResponse = (AuthResponse) responseEntity.getBody();
        assertNotNull(authResponse.getToken());
    }

    @Test
    void testRegisterUserSuccessful() throws Exception {
        // Given
        RegisterRequest registerRequest = mock(RegisterRequest.class);
        when(authenticationService.register(registerRequest)).thenReturn("fake-token");

        // When
        ResponseEntity<Object> responseEntity = authController.registerUser(registerRequest);

        // Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertInstanceOf(RegisterResponse.class, responseEntity.getBody());
        RegisterResponse registerResponse = (RegisterResponse) responseEntity.getBody();
        assertNotNull(registerResponse.getToken());
    }
}
