package com.example.ApiRest.controllers;

import com.example.ApiRest.config.JwtService;
import com.example.ApiRest.dto.GetUsersResponse;
import com.example.ApiRest.entities.User;
import com.example.ApiRest.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @TestConfiguration
    static class UserControllerTestContextConfiguration {
        @Bean
        public UserService userService() {
            return mock(UserService.class);
        }

        @Bean
        public JwtService jwtService() {
            return mock(JwtService.class);
        }
    }

    @Autowired
    private UserService userService;

    @Autowired
    private UserController userController;

    @Test
    public void getUsers() throws Exception {
        // Given
        List<User> userList = Arrays.asList(
                User.builder().email("test@example.com").build(),
                User.builder().email("test2@example.com").build()
        );
        when(userService.findAll()).thenReturn(userList);

        // When
        ResponseEntity<GetUsersResponse> responseEntity = userController.getUsers();

        // Then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getBody()).isInstanceOf(GetUsersResponse.class);
        GetUsersResponse getUsersResponse = responseEntity.getBody();
        assert getUsersResponse != null;
        assertThat(getUsersResponse.getUsers()).hasSize(2);
    }
}
