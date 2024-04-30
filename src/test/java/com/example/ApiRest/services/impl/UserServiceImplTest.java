package com.example.ApiRest.services.impl;

import com.example.ApiRest.entities.User;
import com.example.ApiRest.repositories.UserRepository;
import com.example.ApiRest.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration {
        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {
        User user = getUser();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(userRepository.save(Mockito.any(User.class))).thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    public void findUserTest() {
        String email = "user@domain.com";
        User user = userService.findUserByEmail(email);
        Assert.assertEquals(user.getEmail(), email);
    }

    @Test
    public void addUserTest() {
        User user = getUser();
        User userCreated = userService.addUser(user);
        Assert.assertEquals(user.getEmail(), userCreated.getEmail());
    }

    private User getUser(){
        User user = new User();
        user.setName("Username");
        user.setEmail("user@domain.com");
        return user;
    }
}