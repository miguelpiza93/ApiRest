package com.example.ApiRest.controllers;

import com.example.ApiRest.config.JwtService;
import com.example.ApiRest.entities.Role;
import com.example.ApiRest.entities.User;
import com.example.ApiRest.services.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;


    @Test
    public void getUsersWithValidToken() throws Exception {
        String email = "test@example.com";
        String password = "123";
        User registeredUser = User.builder().email(email).password(password).role(Role.ADMIN).build();
        String token = jwtService.generateToken(registeredUser);
        when(userDetailsService.loadUserByUsername(email)).thenReturn(registeredUser);

        User user = User.builder().email("admin@example.com").build();
        when(userService.findAll()).thenReturn(List.of(user));
        this.mockMvc.perform(get("/api/v1/user").header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.users[0].company_email").value("admin@example.com"));
    }

    @Test
    public void getUsersWithInvalidToken() throws Exception {
        String token = "invalidToken";
        User user = User.builder().email("admin@example.com").build();
        when(userService.findAll()).thenReturn(List.of(user));
        this.mockMvc.perform(get("/api/v1/user").header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}
