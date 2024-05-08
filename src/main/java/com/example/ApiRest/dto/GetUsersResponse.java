package com.example.ApiRest.dto;

import com.example.ApiRest.entities.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GetUsersResponse {
    private List<UserDTO> users;

    public GetUsersResponse(Iterable<User> userList) {
        this.users = new ArrayList<>();
        for (User user : userList) {
            List<PhoneDTO> phoneDTOS = new ArrayList<>();
            if (user.getPhones() != null) {
                phoneDTOS = user.getPhones().stream()
                        .map(PhoneDTO::toPhoneDTO)
                        .toList();
            }
            UserDTO userInfo = UserDTO.builder()
                    .id(user.getId())
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .phones(phoneDTOS)
                    .email(user.getEmail())
                    .build();
            this.users.add(userInfo);
        }
    }
}
