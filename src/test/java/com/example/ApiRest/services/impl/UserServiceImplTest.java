package com.example.ApiRest.services.impl;

import com.example.ApiRest.entities.User;
import com.example.ApiRest.repositories.UserRepository;
import com.example.ApiRest.services.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        User user = getUser();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userRepository.save(Mockito.any(User.class))).thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    public void findUserTest() {
        String email = "user@domain.com";
        User user = userService.findUserByEmail(email).orElseThrow();
        assertEquals(user.getEmail(), email);
    }

    @Test
    public void addUserTest() {
        User user = getUser();
        User userCreated = userService.addUser(user);
        assertEquals(user.getEmail(), userCreated.getEmail());
    }

    @Test
    public void findAllTest() {
        // Given
        List<User> userList = List.of(getUser());
        when(userRepository.findAll()).thenReturn(userList);

        // When
        Iterable<User> allUsers = userService.findAll();

        // Then
        assertThat(allUsers).isNotNull().hasSize(1);
    }

    private User getUser() {
        return User.builder()
                .firstname("Firstname")
                .lastname("Lastname")
                .email("user@domain.com")
                .build();
    }
}