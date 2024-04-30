package com.example.ApiRest.domain;

import com.example.ApiRest.entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateUserRequest {
    private String name;
    private String email;
    private String password;
    private List<PhoneRequest> phones;

    public CreateUserRequest(){
        this.phones = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PhoneRequest> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneRequest> phones) {
        this.phones = phones;
    }

    public User toUserEntity(){
        User user = new User();
        user.setName(this.name);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setToken(UUID.randomUUID());
        phones.forEach(phoneRequest -> {
            user.getPhones().add(phoneRequest.toPhoneEntity());
        });
        return user;
    }
}

