package com.example.ApiRest.dto;

import com.example.ApiRest.entities.Phone;
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
            List<Phone> phones = user.getPhones();
            List<PhoneDTO> phoneDTOS = phones.stream().map(PhoneDTO::toPhoneDTO).toList();
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
