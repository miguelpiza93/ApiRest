package com.nisum.ApiRest.entities;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Phone {

    @Id
    @GeneratedValue
    private UUID id;
    private String number;
    private String cityCode;
    private String country;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
