package com.example.ApiRest.dto.auth.register;

import com.example.ApiRest.entities.Phone;
import com.example.ApiRest.entities.Role;
import com.example.ApiRest.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private List<PhoneRequest> phones = new ArrayList<>();

    public User toUserEntity() {
        User user = User.builder()
                .firstname(this.firstname)
                .lastname(this.lastname)
                .email(this.email)
                .password(this.password)
                .role(Role.USER)
                .build();

        List<Phone> phones = this.phones.stream()
                .map(PhoneRequest::toPhoneEntity)
                .peek(phone -> phone.setUser(user))
                .toList();

        user.setPhones(phones);
        return user;
    }
}

