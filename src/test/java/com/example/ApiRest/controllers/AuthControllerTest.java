package com.example.ApiRest.controllers;

import com.example.ApiRest.dto.auth.authenticate.AuthRequest;
import com.example.ApiRest.dto.auth.register.RegisterRequest;
import com.example.ApiRest.services.authentication.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @Test
    void testAuthenticateSuccessful() throws Exception {
        String json = "{\"username\":\"admin\",\"password\":\"admin\"}";
        when(authenticationService.authenticate(any(AuthRequest.class))).thenReturn("token");
        this.mockMvc.perform(post("/api/v1/auth/authenticate")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("token")));
    }

    @Test
    void testRegisterUserSuccessful() throws Exception {
        String json = "{\"name\":\"John Doe\",\"email\":\"john@example.com\",\"password\":\"Qwerty1\"}";
        when(authenticationService.register(any(RegisterRequest.class))).thenReturn("token");
        this.mockMvc.perform(post("/api/v1/auth/register")
                        .content(json).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(content().string(containsString("token")));
    }
}
