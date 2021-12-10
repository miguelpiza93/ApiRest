package com.nisum.ApiRest.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nisum.ApiRest.entities.Phone;

import java.util.UUID;

public class PhoneRequest {
    private String number;
    @JsonProperty("citycode")
    private String cityCode;
    @JsonProperty("contrycode")
    private String country;

    public PhoneRequest() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Phone toPhoneEntity(){
        Phone phone = new Phone();
        phone.setNumber(this.number);
        phone.setCityCode(this.cityCode);
        phone.setCountry(this.country);
        return phone;
    }
}