package com.nisum.ApiRest.controllers;

import com.nisum.ApiRest.domain.CreateUserRequest;
import com.nisum.ApiRest.domain.CreateUserResponse;
import com.nisum.ApiRest.entities.User;
import com.nisum.ApiRest.exceptions.ApiError;
import com.nisum.ApiRest.services.UserService;
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
        Iterable<User> users = userService.getUsers();
        return new ResponseEntity<>(new CreateUserResponse(created), HttpStatus.CREATED);
    }

    private void validateRequest(CreateUserRequest body) throws Exception {
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
