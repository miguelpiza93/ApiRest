package com.example.ApiRest.dto;

import com.example.ApiRest.entities.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneDTO {
    private Integer id;
    private String number;
    private String cityCode;
    private String country;

    public static PhoneDTO toPhoneDTO(Phone phone) {
        return PhoneDTO.builder()
                .id(phone.getId())
                .number(phone.getNumber())
                .cityCode(phone.getCityCode())
                .country(phone.getCountry())
                .build();
    }
}
