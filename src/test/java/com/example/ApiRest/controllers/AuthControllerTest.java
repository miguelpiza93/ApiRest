package com.example.ApiRest.controllers;

import com.example.ApiRest.config.JwtService;
import com.example.ApiRest.dto.auth.authenticate.AuthRequest;
import com.example.ApiRest.dto.auth.authenticate.AuthResponse;
import com.example.ApiRest.dto.auth.register.PhoneRequest;
import com.example.ApiRest.dto.auth.register.RegisterRequest;
import com.example.ApiRest.dto.auth.register.RegisterResponse;
import com.example.ApiRest.entities.User;
import com.example.ApiRest.services.UserService;
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
        public UserService userService() {
            return mock(UserService.class);
        }

        @Bean
        public AuthenticationManager authenticationManager() {
            return mock(AuthenticationManager.class);
        }

        @Bean
        public UserDetailsService userDetailsService() {
            return mock(UserDetailsService.class);
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return mock(PasswordEncoder.class);
        }
    }

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthController authController;

    @Test
    void testAuthenticateSuccessful() {
        // Given
        AuthRequest authRequest = new AuthRequest("test@example.com", "password");
        User user = new User();
        user.setEmail("test@example.com");
        when(userService.findUserByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("test-token");

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
        RegisterRequest registerRequest = getCreateRequest();
        when(userService.findUserByEmail("user@domain.com")).thenReturn(Optional.empty());
        when(jwtService.generateToken(any(User.class))).thenReturn("test-token");

        // When
        ResponseEntity<Object> responseEntity = authController.registerUser(registerRequest);

        // Then
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertInstanceOf(RegisterResponse.class, responseEntity.getBody());
        RegisterResponse registerResponse = (RegisterResponse) responseEntity.getBody();
        assertNotNull(registerResponse.getToken());
    }

    @Test
    void testRegisterUserWithEmailAlreadyExists() {
        // Given
        RegisterRequest registerRequest = getCreateRequest();
        when(userService.findUserByEmail("user@domain.com")).thenReturn(Optional.of(new User()));

        // When
        Exception exception = assertThrows(Exception.class, () -> authController.registerUser(registerRequest));

        // Then
        assertEquals("El correo ya registrado", exception.getMessage());
    }

    @Test
    void testRegisterUserWithInvalidPassword() {
        // Given
        RegisterRequest registerRequest = getCreateRequest();
        registerRequest.setPassword("invalidpassword");

        // When
        Exception exception = assertThrows(Exception.class, () -> authController.registerUser(registerRequest));

        // Then
        assertEquals("La contraseña no cumple con los requerimientos", exception.getMessage());
    }

    @Test
    void testRegisterUserWithInvalidEmail() {
        // Given
        RegisterRequest registerRequest = getCreateRequest();
        registerRequest.setEmail("invalidemail");

        // When
        Exception exception = assertThrows(Exception.class, () -> authController.registerUser(registerRequest));

        // Then
        assertEquals("El correo no es válido", exception.getMessage());
    }

    @Test
    void testRegisterUserWithEmailNull() {
        // Given
        RegisterRequest registerRequest = getCreateRequest();
        registerRequest.setEmail(null);

        // When
        Exception exception = assertThrows(Exception.class, () -> authController.registerUser(registerRequest));

        // Then
        assertEquals("El email es un campo requerido", exception.getMessage());
    }

    @Test
    void testRegisterUserWithPasswordNull() {
        // Given
        RegisterRequest registerRequest = getCreateRequest();
        registerRequest.setPassword(null);

        // When
        Exception exception = assertThrows(Exception.class, () -> authController.registerUser(registerRequest));

        // Then
        assertEquals("La contraseña es un campo requerido", exception.getMessage());
    }


    private RegisterRequest getCreateRequest() {
        PhoneRequest phone = PhoneRequest.builder()
                .country("country")
                .cityCode("code")
                .number("4535413").build();

        return RegisterRequest
                .builder()
                .email("user@domain.com")
                .firstname("firstname")
                .lastname("lastname")
                .password("Qwertyui1")
                .phones(List.of(phone)).build();
    }
}
