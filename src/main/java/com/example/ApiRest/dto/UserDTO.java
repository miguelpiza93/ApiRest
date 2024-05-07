package com.example.ApiRest.dto;

import com.example.ApiRest.entities.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Integer id;

    @JsonProperty("company_email")
    private String email;

    private String firstname;

    private String lastname;

    private String password;

    private Role role;

    private List<PhoneDTO> phones;

}