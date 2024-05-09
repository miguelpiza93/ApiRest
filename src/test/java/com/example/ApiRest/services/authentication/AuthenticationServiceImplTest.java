package com.example.ApiRest.services.authentication;

import com.example.ApiRest.config.JwtService;
import com.example.ApiRest.dto.auth.authenticate.AuthRequest;
import com.example.ApiRest.dto.auth.register.RegisterRequest;
import com.example.ApiRest.entities.User;
import com.example.ApiRest.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthenticationServiceImplTest {

    @Autowired
    private AuthenticationServiceImpl authenticationService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        User user = getUser();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(Mockito.any())).thenReturn("token");
    }

    @Test
    public void authenticateSuccessfully() {
        // Given
        AuthRequest authRequest = new AuthRequest("user@domain.com", "123");

        // When
        String token = authenticationService.authenticate(authRequest);

        // Then
        String expectedToken = "token";
        assertEquals(token, expectedToken);
    }

    @Test
    public void authenticateWithInvalidEmail() {
        // Given
        AuthRequest authRequest = new AuthRequest("unexisting@test.com", "123");
        when(authenticationManager.authenticate(Mockito.any())).thenReturn(null);

        // When
        Exception exception = assertThrows(Exception.class, () -> authenticationService.authenticate(authRequest));

        // Then
        assertEquals("No value present", exception.getMessage());
    }

    @Test
    public void registerSuccessfully() throws Exception {
        // Given
        RegisterRequest registerRequest = getRegisterRequest();
        when(userRepository.save(Mockito.any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        // When
        String token = authenticationService.register(registerRequest);

        // Then
        String expectedToken = "token";
        assertEquals(token, expectedToken);
    }

    private RegisterRequest getRegisterRequest() {
        return RegisterRequest.builder()
                .firstname("New")
                .lastname("User")
                .email("newuser@test.com")
                .password("Qwertyui1")
                .build();
    }

    private User getUser() {
        return User.builder()
                .firstname("Firstname")
                .lastname("Lastname")
                .email("user@domain.com")
                .password("$2a$10$kW4x5fPED6ljR0kRdk611e9XjM3AAu34JmfanlySpjgGWlW2wiWDO")
                .build();
    }
}