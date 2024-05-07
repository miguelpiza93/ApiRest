package com.example.ApiRest.dto.auth.register;

import com.example.ApiRest.entities.Phone;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneRequest {

    private String number;
    @JsonProperty("citycode")
    private String cityCode;
    @JsonProperty("contrycode")
    private String country;

    public Phone toPhoneEntity(){
        return Phone.builder()
                .number(this.number)
                .cityCode(this.cityCode)
                .country(this.country)
                .build();

    }
}