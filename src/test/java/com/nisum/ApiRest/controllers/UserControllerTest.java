package com.nisum.ApiRest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nisum.ApiRest.domain.CreateUserRequest;
import com.nisum.ApiRest.domain.PhoneRequest;
import com.nisum.ApiRest.entities.User;
import com.nisum.ApiRest.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void createUserSuccessfully()
            throws Exception {
        when(service.addUser(Mockito.any(User.class))).thenAnswer(i -> i.getArguments()[0]);
        CreateUserRequest userRequest = getCreateRequest();
        mvc.perform(post("/user")
                .content(objectMapper.writeValueAsString(userRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.active", is(true)));
    }

    @Test
    public void createUserEmailAlreadyExistsException()
            throws Exception {
        CreateUserRequest userRequest = getCreateRequest();

        when(service.findUserByEmail(userRequest.getEmail())).thenReturn(userRequest.toUserEntity());

        mvc.perform(post("/user")
                .content(objectMapper.writeValueAsString(userRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.mensaje", is("El correo ya registrado")));
    }

    @Test
    public void createUserEmailException()
            throws Exception {
        CreateUserRequest userRequest = getCreateRequest();
        userRequest.setEmail(null);

        when(service.findUserByEmail(userRequest.getEmail())).thenReturn(userRequest.toUserEntity());

        mvc.perform(post("/user")
                .content(objectMapper.writeValueAsString(userRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.mensaje", is("El email es un campo requerido")));
    }

    @Test
    public void createUserEmailException2()
            throws Exception {
        CreateUserRequest userRequest = getCreateRequest();
        userRequest.setPassword(null);

        when(service.findUserByEmail(userRequest.getEmail())).thenReturn(userRequest.toUserEntity());

        mvc.perform(post("/user")
                .content(objectMapper.writeValueAsString(userRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.mensaje", is("La contraseña es un campo requerido")));
    }

    @Test
    public void createUserEmailException3()
            throws Exception {
        CreateUserRequest userRequest = getCreateRequest();
        userRequest.setPassword("123");

        when(service.findUserByEmail(userRequest.getEmail())).thenReturn(userRequest.toUserEntity());

        mvc.perform(post("/user")
                .content(objectMapper.writeValueAsString(userRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.mensaje", is("La contraseña no cumple con los requerimientos")));
    }

    @Test
    public void createUserEmailException4()
            throws Exception {
        CreateUserRequest userRequest = getCreateRequest();
        userRequest.setEmail("correofalso.com");

        when(service.findUserByEmail(userRequest.getEmail())).thenReturn(userRequest.toUserEntity());

        mvc.perform(post("/user")
                .content(objectMapper.writeValueAsString(userRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.mensaje", is("El correo no es válido")));
    }

    private CreateUserRequest getCreateRequest(){
        CreateUserRequest userRequest = new CreateUserRequest();
        userRequest.setName("Username");
        userRequest.setEmail("user@domain.com");
        userRequest.setPassword("password2");
        PhoneRequest phone = new PhoneRequest();
        phone.setCountry("country");
        phone.setCityCode("code");
        phone.setNumber("4535413");
        userRequest.getPhones().add(phone);
        return userRequest;
    }

}