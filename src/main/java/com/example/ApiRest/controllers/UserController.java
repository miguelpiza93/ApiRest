package com.example.ApiRest.controllers;

import com.example.ApiRest.domain.CreateUserRequest;
import com.example.ApiRest.domain.CreateUserResponse;
import com.example.ApiRest.domain.GetUsersResponse;
import com.example.ApiRest.entities.User;
import com.example.ApiRest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${emailRegex}")
    private String emailRegex;

    @Value("${passwordRegex}")
    private String passwordRegex;

    @PostMapping
    public ResponseEntity<Object> registerUser(@RequestBody CreateUserRequest body) throws Exception {
        validateRequest(body);

        User found = userService.findUserByEmail(body.getEmail());
        if(found != null){
            throw new Exception("El correo ya registrado");
        }

        User created = userService.addUser(body.toUserEntity());
        return new ResponseEntity<>(new CreateUserResponse(created), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Object> getUsers() throws Exception {
        Iterable<User> users = userService.findAll();
        return new ResponseEntity<>(new GetUsersResponse(users), HttpStatus.CREATED);
    }

    private void validateRequest(CreateUserRequest body) throws Exception {
        if(body.getEmail() == null){
            throw new Exception("El email es un campo requerido");
        }

        if(body.getPassword() == null){
            throw new Exception("La contraseña es un campo requerido");
        }

        Pattern pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(body.getEmail());
        if(!matcher.find()){
            throw new Exception("El correo no es válido");
        }

        pattern = Pattern.compile(passwordRegex, Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(body.getPassword());
        if(!matcher.find()){
            throw new Exception("La contraseña no cumple con los requerimientos");
        }
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
